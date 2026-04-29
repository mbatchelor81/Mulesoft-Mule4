package com.example.ftpapp.service;

import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Service
public class FtpReaderService {

    private static final Logger log = LoggerFactory.getLogger(FtpReaderService.class);

    @Value("${ftp.host:localhost}")
    private String ftpHost;

    @Value("${ftp.port:21}")
    private int ftpPort;

    @Value("${ftp.username:ftpuser}")
    private String ftpUsername;

    @Value("${ftp.password:ftpuser}")
    private String ftpPassword;

    @Value("${ftp.file-path:Employees.csv}")
    private String ftpFilePath;

    @Scheduled(fixedRate = 60000, initialDelay = 60000)
    public void readFileFromFtp() {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(ftpHost, ftpPort);
            ftpClient.login(ftpUsername, ftpPassword);
            ftpClient.enterLocalPassiveMode();

            log.info("Connected to FTP server at {}:{}", ftpHost, ftpPort);

            try (InputStream is = ftpClient.retrieveFileStream(ftpFilePath)) {
                if (is != null) {
                    String content = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))
                        .lines()
                        .collect(Collectors.joining("\n"));
                    log.info("File content from '{}':\n{}", ftpFilePath, content);
                } else {
                    log.warn("File '{}' not found on FTP server", ftpFilePath);
                }
            }

            ftpClient.completePendingCommand();
            ftpClient.logout();
        } catch (Exception e) {
            log.error("Error reading file from FTP: {}", e.getMessage());
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.disconnect();
                }
            } catch (Exception e) {
                log.error("Error disconnecting from FTP: {}", e.getMessage());
            }
        }
    }
}
