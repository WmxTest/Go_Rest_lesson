package utils;

import lombok.SneakyThrows;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.Properties;

public class PropertyLoader {

    private static final String PATH = "src/main/resources/application.properties";
    private static Properties properties;

    private PropertyLoader() {

    }

    @SneakyThrows
    public static void loadProperty() {
        properties = new Properties();
        properties.load(new FileReader(PATH));
    }

    public static synchronized String getProperty(String propertyName) {
        if (properties == null) {
            System.out.println(Thread.currentThread().getName());
            loadProperty();
        }
        String value = properties.getProperty(propertyName);
        if (value != null) {
            return value;
        } else {
            throw new IllegalArgumentException(String.format("Property %s is not exist", propertyName));
        }
    }

    public static void setProperty(String propertyName, String propertyValue) {
        if (properties == null) {
            loadProperty();
        }
        properties.setProperty(propertyName, propertyValue);
    }

    @SneakyThrows
    public static synchronized void storeProperty(String propertyName, String propertyValue) {
        properties.setProperty(propertyName, propertyValue);
        properties.store(new FileOutputStream("src/main/resources/application.properties"), null);
    }
}