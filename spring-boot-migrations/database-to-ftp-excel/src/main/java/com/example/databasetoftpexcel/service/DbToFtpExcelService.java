package com.example.databasetoftpexcel.service;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

@Service
public class DbToFtpExcelService {

    private static final Logger log = LoggerFactory.getLogger(DbToFtpExcelService.class);

    private final JdbcTemplate jdbcTemplate;

    @Value("${ftp.host:localhost}")
    private String ftpHost;

    @Value("${ftp.port:21}")
    private int ftpPort;

    @Value("${ftp.username:ftpuser}")
    private String ftpUsername;

    @Value("${ftp.password:ftpuser}")
    private String ftpPassword;

    @Value("${ftp.remote-file:EmployeeDBData.xlsx}")
    private String remoteFileName;

    public DbToFtpExcelService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Scheduled(fixedRate = 60000, initialDelay = 60000)
    public void exportAndUpload() {
        try {
            List<Map<String, Object>> employees = jdbcTemplate.queryForList("SELECT * FROM emp");
            log.info("Fetched {} employees from database", employees.size());

            byte[] xlsxBytes = generateXlsx(employees);
            uploadToFtp(xlsxBytes);

            log.info("Successfully uploaded {} to FTP", remoteFileName);
        } catch (Exception e) {
            log.error("Error in DB-to-FTP-Excel export: {}", e.getMessage());
        }
    }

    private byte[] generateXlsx(List<Map<String, Object>> data) throws Exception {
        try (XSSFWorkbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Employees");

            if (!data.isEmpty()) {
                Row headerRow = sheet.createRow(0);
                List<String> headers = List.copyOf(data.get(0).keySet());
                for (int i = 0; i < headers.size(); i++) {
                    headerRow.createCell(i).setCellValue(headers.get(i));
                }

                for (int rowIdx = 0; rowIdx < data.size(); rowIdx++) {
                    Row dataRow = sheet.createRow(rowIdx + 1);
                    Map<String, Object> row = data.get(rowIdx);
                    for (int colIdx = 0; colIdx < headers.size(); colIdx++) {
                        Object val = row.get(headers.get(colIdx));
                        dataRow.createCell(colIdx).setCellValue(val != null ? val.toString() : "");
                    }
                }
            }

            workbook.write(out);
            return out.toByteArray();
        }
    }

    private void uploadToFtp(byte[] fileBytes) throws Exception {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(ftpHost, ftpPort);
            ftpClient.login(ftpUsername, ftpPassword);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);

            try (ByteArrayInputStream bis = new ByteArrayInputStream(fileBytes)) {
                boolean stored = ftpClient.storeFile(remoteFileName, bis);
                if (!stored) {
                    log.warn("Failed to store file on FTP server");
                }
            }

            ftpClient.logout();
        } finally {
            if (ftpClient.isConnected()) {
                ftpClient.disconnect();
            }
        }
    }
}
