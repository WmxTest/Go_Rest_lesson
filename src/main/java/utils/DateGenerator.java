package utils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateGenerator {

    public static String getLocalDateTime(){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        ZonedDateTime zonedDateTimeLocal = ZonedDateTime.ofInstant(Instant.now(), ZoneId.systemDefault());
        return zonedDateTimeLocal.format(dateTimeFormatter);
    }
}