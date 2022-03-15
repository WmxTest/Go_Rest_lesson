package utils;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvWorker {

    public static void writeData(List<String[]> body, File file) throws IOException {
        CSVWriter csvWriter = new CSVWriter(new FileWriter(file));
        csvWriter.writeAll(body);
        csvWriter.close();
    }

    private static final String FILE_PATH = "src/main/resources/users.CSV";
    private static CSVReader csvReader;

    private CsvWorker() {
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

}