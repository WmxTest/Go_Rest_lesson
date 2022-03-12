package utils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateGenerator {

    public static String getDateTime(String pattern){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        ZonedDateTime zonedDateTimeLocal = ZonedDateTime.ofInstant(Instant.now(), ZoneId.systemDefault());
        return zonedDateTimeLocal.format(dateTimeFormatter);
    }
}