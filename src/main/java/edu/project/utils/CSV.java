package edu.project.utils;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import edu.project.models.Vacancy;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public final class CSV {
    private CSV() {}

    public static List<Vacancy> readVacancies(String filePath) {
        try (CSVReader csvReader = new CSVReaderBuilder(new FileReader(filePath)).withSkipLines(1).build()) {
            return csvReader
                .readAll()
                .stream()
                .map(Vacancy::parseVacancy)
                .toList();
        } catch (IOException | CsvException exception) {
            throw new RuntimeException(exception);
        }
    }
}
