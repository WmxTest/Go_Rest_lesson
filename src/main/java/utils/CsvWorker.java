package utils;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import com.opencsv.bean.*;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.opencsv.exceptions.CsvValidationException;

import java.io.*;
import java.util.List;

public class CsvWorker {

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

    public static CSVReader getCsvReaderInstance(String filePath) {
        if (csvReader == null) {
            try {
                csvReader = new CSVReaderBuilder(new FileReader(filePath))
                        .withSkipLines(0)
                        .build();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return csvReader;
    }

    public static synchronized String[] readLineByLineFromCsvFile_2(String filePath) {
        try {
            return getCsvReaderInstance(filePath).readNext();
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
            return null;
        }
    }

    @SuppressWarnings("ConstantConditions")
    public static <T> List<T> getObjectList(Class<T> cls, int skippedLines, String filePath) {
        List<T> list;
        ColumnPositionMappingStrategy<T> mappingStrategy = new ColumnPositionMappingStrategy<>();
        mappingStrategy.setType(cls);

        Reader reader = null;
        try {
            reader = new FileReader(filePath);
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