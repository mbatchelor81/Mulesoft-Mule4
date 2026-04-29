package com.example.dataweavesamples;

import com.example.dataweavesamples.service.TransformationService;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class TransformationServiceTest {

    private final TransformationService service = new TransformationService();

    @Test
    void jsonToXmlAndBack() throws Exception {
        String json = "{\"name\":\"John\",\"age\":30}";
        String xml = service.jsonToXml(json);
        assertThat(xml).contains("John");

        String backToJson = service.xmlToJson(xml);
        assertThat(backToJson).contains("John");
    }

    @Test
    void jsonToCsvAndBack() throws Exception {
        String json = "[{\"name\":\"Alice\",\"age\":\"25\"},{\"name\":\"Bob\",\"age\":\"30\"}]";
        String csv = service.jsonToCsv(json);
        assertThat(csv).contains("Alice");
        assertThat(csv).contains("Bob");

        String backToJson = service.csvToJson(csv);
        assertThat(backToJson).contains("Alice");
    }

    @Test
    void jsonToXlsx() throws Exception {
        String json = "[{\"name\":\"Alice\",\"salary\":50000}]";
        byte[] xlsx = service.jsonToXlsx(json);
        assertThat(xlsx).isNotEmpty();
    }

    @Test
    void mapFilterFlatten() {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5);
        List<Integer> doubled = service.map(numbers, n -> n * 2);
        assertThat(doubled).containsExactly(2, 4, 6, 8, 10);

        List<Integer> evens = service.filter(numbers, n -> n % 2 == 0);
        assertThat(evens).containsExactly(2, 4);

        List<List<Integer>> nested = List.of(List.of(1, 2), List.of(3, 4));
        List<Integer> flat = service.flatten(nested);
        assertThat(flat).containsExactly(1, 2, 3, 4);
    }

    @Test
    void pluck() {
        Map<String, Object> input = Map.of("a", 1, "b", 2);
        List<Object> values = service.pluck(input);
        assertThat(values).hasSize(2);
    }

    @Test
    void typeCoercion() {
        assertThat(service.coerceToInt("42")).isEqualTo(42);
        assertThat(service.coerceToDouble("3.14")).isEqualTo(3.14);
    }
}
