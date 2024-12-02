package com.example.test.helper;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateFormatted {

    public static Integer DAY_IN_WEEK = 7;

    public static String get(Long millis) {
        LocalDate date = Instant.ofEpochMilli(millis)
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(formatter);
    }

    public static String get(LocalDate startDate, LocalDate endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd");
        String a = startDate.format(formatter);
        formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String b = endDate.format(formatter);
        return a + " - " + b;
    }

    public static String get(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(formatter);
    }
}
