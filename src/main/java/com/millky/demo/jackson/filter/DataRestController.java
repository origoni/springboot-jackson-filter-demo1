package com.millky.demo.jackson.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class DataRestController {

    final private ObjectMapper objectMapper;

    List<TestData> dataList = new ArrayList<>();

    @PostConstruct
    void init() {
        dataList.add(new TestData("t1", 1));
        dataList.add(new TestData("t2", 2));
        dataList.add(new TestData("t3", 3));
    }

    // http://localhost:8080
    // http://localhost:8080/?fields=string
    // http://localhost:8080/?fields=string,an_int
    @GetMapping
    public String list(String fields) throws JsonProcessingException {

        return getObjectMapper(fields).writeValueAsString(dataList);
    }

    @GetMapping(value = "all")
    public String list2() throws JsonProcessingException {

        return getObjectMapper().writeValueAsString(dataList);
    }

    // http://localhost:8080/?fields=string,an_int
    // http://localhost:8080
    @GetMapping(value = "err")
    public String err() throws JsonProcessingException {

        return objectMapper.writeValueAsString(dataList);
    }

    private ObjectMapper getObjectMapper() {
        return getObjectMapper(null);
    }

    private ObjectMapper getObjectMapper(String fields) {

//        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
//        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
//        objectMapper.setPropertyNamingStrategy(new PropertyNamingStrategy.SnakeCaseStrategy());
//        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
//        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        SimpleFilterProvider filters = new SimpleFilterProvider();

        if (fields == null) {
            filters.setFailOnUnknownId(false);
        } else {
            filters.addFilter("dynamicFilter", SimpleBeanPropertyFilter.filterOutAllExcept(fields.replaceAll("[ ]+", "").split(",")));
        }
        return objectMapper.setFilterProvider(filters);
    }
}
