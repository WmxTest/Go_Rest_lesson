package utils;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import com.opencsv.bean.*;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.opencsv.exceptions.CsvValidationException;
import rest_assured.gorest_co_in.UserService;
import rest_assured.gorest_co_in.dto.User;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class CsvWorker {

    private static final String FILE_PATH = "src/main/resources/users.CSV";
    static Queue<User> usersQueue;
    private static CSVReader csvReader;

    private CsvWorker() {
    }

    public static void writeData(List<String[]> body, File file) throws IOException {
        CSVWriter csvWriter = new CSVWriter(new FileWriter(file));
        csvWriter.writeAll(body);
        csvWriter.close();
    }

    public static CSVReader getCsvReaderInstance() {
        if (csvReader == null) {
            try {
                csvReader = new CSVReaderBuilder(new FileReader(FILE_PATH))
                        .withSkipLines(0)
                        .build();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return csvReader;
    }

    public static synchronized String[] readLineByLineFromCsvFile() {
        try {
            return getCsvReaderInstance().readNext();
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> List<T> readValue(Reader reader, Class<T> cls, int skippedLines) {
        List<T> list;
        ColumnPositionMappingStrategy<T> mappingStrategy = new ColumnPositionMappingStrategy<>();
        mappingStrategy.setType(cls);

        CsvToBean<T> cb = new CsvToBeanBuilder<T>(reader)
                .withIgnoreQuotations(true)
                .withIgnoreEmptyLine(true)
                .withMappingStrategy(mappingStrategy)
                .withSkipLines(skippedLines)
                .build();

        list = cb.parse();
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static <T> void writeValue(Writer writer, List<T> list) {
        StatefulBeanToCsv<T> sbc = new StatefulBeanToCsvBuilder<T>(writer)
                .withApplyQuotesToAll(false)
                .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                .build();
        try {
            sbc.write(list);
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized static User getUser() {
        return usersQueue.poll();
    }

    @SuppressWarnings("all")
    public static void main(String[] args) throws Exception {
        FileWriter fileWriter = new FileWriter(FILE_PATH);
        writeValue(fileWriter, UserService.getUsers());

        FileReader fileReader = new FileReader(FILE_PATH);
        List<User> users = readValue(fileReader, User.class, 0);

        usersQueue = new LinkedList<>(users);

        users.parallelStream().forEach(user -> System.out.println(getUser().getMId()));
    }
}