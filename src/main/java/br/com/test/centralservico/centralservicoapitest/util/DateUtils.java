package br.com.test.centralservico.centralservicoapitest.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class DateUtils {

    private static final String datePattern = "yyyy-MM-dd HH:mm:ss";

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern);

    public static Boolean isOnTime(String dateEndString) {

        LocalDateTime dateEnd = LocalDateTime.parse(dateEndString, formatter);

        LocalDateTime now = LocalDateTime.now();

        return now.isBefore(dateEnd);

    }

    public static String toFormattedString(LocalDateTime date) {

        return date.format(formatter);

    }

    public static String currentTimeStamp() {

        return LocalDateTime.now().format(formatter);

    }

}
