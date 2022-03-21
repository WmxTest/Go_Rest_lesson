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
import java.util.List;

public class CsvWorker {

    private static final String FILE_PATH = "src/main/resources/users.CSV";
    private static CSVReader csvReader;

    private CsvWorker() {
    }

    public static void writeData(List<String[]> body, File file) throws IOException {
        CSVWriter csvWriter = new CSVWriter(new FileWriter(file));
        csvWriter.writeAll(body);
        csvWriter.close();
    }

    public static <T> void writeData2(List<T> body, Writer writer) {
        StatefulBeanToCsv<T> sbc = new StatefulBeanToCsvBuilder<T>(writer)
                .withApplyQuotesToAll(false)
                .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                .build();
        try {
            sbc.write(body);
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

    public static void main(String[] args) throws IOException {
        List<User> users = UserService.getUsers();
        writeData2(users, new FileWriter(FILE_PATH));
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

    public static synchronized String[] readLineByLineFromCsvFile_2() {
        try {
            return getCsvReaderInstance().readNext();
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static <T> List<T> getObjectList(Class<T> cls, int skippedLines) {
        List<T> list;
        ColumnPositionMappingStrategy<T> mappingStrategy = new ColumnPositionMappingStrategy<>();
        mappingStrategy.setType(cls);

        Reader reader = null;
        try {
            reader = new FileReader(FILE_PATH);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

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
}