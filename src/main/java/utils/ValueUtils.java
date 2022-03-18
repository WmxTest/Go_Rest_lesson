package utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

public class ValueUtils {
    private final static ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T jsonFileToObject(String filePath, Class<T> cls)  {
        InputStream inputStream = ValueUtils.class.getResourceAsStream(filePath);
        try {
            return objectMapper.readValue(inputStream, cls);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
