package com.jfonzuer.dto.mapper;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by pgm on 20/09/16.
 */
public class DateMapper {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public static LocalDateTime toLocalDateTime(String s) {
        return LocalDateTime.parse(s, formatter);
    }


}
