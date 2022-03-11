package utils;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateGenerator {

    public static String getLocalDateTime(String pattern){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of("UTC"));
        return zonedDateTime.format(dateTimeFormatter);
    }

    public static void main(String[] args) {
        System.out.println(getLocalDateTime("YYYY-MM-ddEHH:mm:ss.nnnx"));
    }
    //"due_on":"2022-04-07T00:00:00.000+05:30","status":"completed"}
}