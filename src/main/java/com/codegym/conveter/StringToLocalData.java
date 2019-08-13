package com.codegym.conveter;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class StringToLocalData implements Converter<String, LocalDate> {
//    private String datePattern;
//
//    public StringToLocalData(String datePattern) {
//        this.datePattern = datePattern;
//    }

    @Override
    public LocalDate convert(String date) {
        try {
            return LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("invalid date format. Please use this pattern\""
                    + DateTimeFormatter.ISO_LOCAL_DATE + "\"");
        }
    }
}
