package com.jfonzuer.dto.mapper;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by pgm on 20/09/16.
 */
public class DateMapper {

    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static LocalDateTime toLocalDateTime(String s) {
        return LocalDateTime.parse(s, dateTimeFormatter);
    }

    public static LocalDate toLocalDate(String s) {
        return LocalDate.parse(s, dateFormatter);
    }


}
