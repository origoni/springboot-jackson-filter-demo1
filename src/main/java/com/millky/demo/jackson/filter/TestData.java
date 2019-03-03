package com.millky.demo.jackson.filter;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@JsonFilter("dynamicFilter")
public class TestData {

    String string;

    int anInt;

    ZonedDateTime createAt;

    public TestData(String string, int anInt) {
        this.string = string;
        this.anInt = anInt;
        this.setCreateAt(ZonedDateTime.now());
    }
}
