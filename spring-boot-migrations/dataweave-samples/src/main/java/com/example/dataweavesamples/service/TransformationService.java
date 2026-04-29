package com.example.dataweavesamples.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TransformationService {

    private final ObjectMapper jsonMapper = new ObjectMapper()
        .enable(SerializationFeature.INDENT_OUTPUT);
    private final XmlMapper xmlMapper = new XmlMapper();
    private final CsvMapper csvMapper = new CsvMapper();

    // JSON to XML
    public String jsonToXml(String json) throws IOException {
        JsonNode node = jsonMapper.readTree(json);
        return xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(node);
    }

    // XML to JSON
    public String xmlToJson(String xml) throws IOException {
        JsonNode node = xmlMapper.readTree(xml.getBytes());
        return jsonMapper.writeValueAsString(node);
    }

    // JSON to CSV
    public String jsonToCsv(String json) throws IOException {
        JsonNode tree = jsonMapper.readTree(json);
        if (!tree.isArray() || tree.isEmpty()) {
            return "";
        }

        CsvSchema.Builder schemaBuilder = CsvSchema.builder();
        JsonNode first = tree.get(0);
        first.fieldNames().forEachRemaining(schemaBuilder::addColumn);
        CsvSchema schema = schemaBuilder.build().withHeader();

        List<Map<String, String>> rows = new ArrayList<>();
        for (JsonNode node : tree) {
            Map<String, String> row = new LinkedHashMap<>();
            node.fields().forEachRemaining(e -> row.put(e.getKey(), e.getValue().asText()));
            rows.add(row);
        }
        return csvMapper.writer(schema).writeValueAsString(rows);
    }

    // CSV to JSON
    public String csvToJson(String csv) throws IOException {
        CsvSchema schema = CsvSchema.emptySchema().withHeader();
        List<Map<String, String>> rows = csvMapper.readerFor(Map.class)
            .with(schema)
            .readValues(csv)
            .readAll()
            .stream()
            .map(obj -> {
                @SuppressWarnings("unchecked")
                Map<String, String> map = (Map<String, String>) obj;
                return map;
            })
            .collect(Collectors.toList());
        return jsonMapper.writeValueAsString(rows);
    }

    // JSON to XLSX
    public byte[] jsonToXlsx(String json) throws IOException {
        JsonNode tree = jsonMapper.readTree(json);
        try (XSSFWorkbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Data");

            if (tree.isArray() && !tree.isEmpty()) {
                JsonNode first = tree.get(0);
                Row headerRow = sheet.createRow(0);
                List<String> headers = new ArrayList<>();
                first.fieldNames().forEachRemaining(headers::add);
                for (int i = 0; i < headers.size(); i++) {
                    headerRow.createCell(i).setCellValue(headers.get(i));
                }

                int rowIdx = 1;
                for (JsonNode node : tree) {
                    Row dataRow = sheet.createRow(rowIdx++);
                    for (int i = 0; i < headers.size(); i++) {
                        Cell cell = dataRow.createCell(i);
                        JsonNode val = node.get(headers.get(i));
                        if (val != null && val.isNumber()) {
                            cell.setCellValue(val.doubleValue());
                        } else {
                            cell.setCellValue(val != null ? val.asText() : "");
                        }
                    }
                }
            }
            workbook.write(out);
            return out.toByteArray();
        }
    }

    // Stream-based map equivalent
    public <T, R> List<R> map(List<T> input, Function<T, R> mapper) {
        return input.stream().map(mapper).collect(Collectors.toList());
    }

    // Stream-based filter equivalent
    public <T> List<T> filter(List<T> input, Predicate<T> predicate) {
        return input.stream().filter(predicate).collect(Collectors.toList());
    }

    // mapObject equivalent: transform keys/values of a map
    public <K, V, NK, NV> Map<NK, NV> mapObject(
            Map<K, V> input,
            Function<Map.Entry<K, V>, NK> keyMapper,
            Function<Map.Entry<K, V>, NV> valueMapper) {
        return input.entrySet().stream()
            .collect(Collectors.toMap(keyMapper, valueMapper, (a, b) -> a, LinkedHashMap::new));
    }

    // pluck equivalent: extract values from object into array
    public List<Object> pluck(Map<String, Object> input) {
        return new ArrayList<>(input.values());
    }

    // flatten equivalent
    public <T> List<T> flatten(List<List<T>> input) {
        return input.stream().flatMap(List::stream).collect(Collectors.toList());
    }

    // Type coercion: String to Integer
    public int coerceToInt(String value) {
        return Integer.parseInt(value.trim());
    }

    // Type coercion: String to Double
    public double coerceToDouble(String value) {
        return Double.parseDouble(value.trim());
    }

    // Type coercion: Object to JSON string
    public String coerceToJsonString(Object value) throws JsonProcessingException {
        return jsonMapper.writeValueAsString(value);
    }
}
