package br.com.test.centralservico.centralservicoapitest.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class MyDateUtils {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static Boolean isOnTime(String dateEndString) {

        LocalDateTime dateEnd = LocalDateTime.parse(dateEndString, formatter);

        LocalDateTime now = LocalDateTime.now();

        return now.isBefore(dateEnd);

    }

    public static String toFormattedString(LocalDateTime date) {

        return date.format(formatter);

    }

}
