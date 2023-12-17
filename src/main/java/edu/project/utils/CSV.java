package edu.project.utils;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import edu.project.models.Currency;
import edu.project.models.Experience;
import edu.project.models.Salary;
import edu.project.models.Vacancy;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public final class CSV {
    private CSV() {}

    public static List<Vacancy> readVacancies(String filePath) {
        try (CSVReader csvReader = new CSVReaderBuilder(new FileReader(filePath)).withSkipLines(1).build()) {
            return csvReader.readAll().stream().map(CSV::parseVacancy).toList();
        } catch (IOException | CsvException exception) {
            throw new RuntimeException(exception);
        }
    }

    @SuppressWarnings("MagicNumber")
    public static Vacancy parseVacancy(String[] fields) {
        return new Vacancy(
            fields[0],
            parseDescription(fields[1]),
            parseKeySkills(fields[2]),
            parseExperience(fields[3]),
            Boolean.parseBoolean(fields[4]),
            fields[5],
            parseSalary(fields[6], fields[7], fields[8], fields[9]),
            fields[10],
            parsePublishedAt(fields[11])
        );
    }

    public static String parseDescription(String description) {
        return description
            .replaceAll("<[^<>]*>", "")
            .replaceAll("\\s+", " ")
            .strip();
    }

    public static Experience parseExperience(String string) {
        return Map.of(
            "noExperience", Experience.NO_EXPERIENCE,
            "between1And3", Experience.BETWEEN_1_AND_3,
            "between3And6", Experience.BETWEEN_3_AND_6,
            "moreThan6", Experience.MORE_THAN_6
        ).get(string);
    }

    public static List<String> parseKeySkills(String keySkillsString) {
        return Arrays.stream(keySkillsString.split("\n")).toList();
    }

    public static Salary parseSalary(String fromString, String toString, String grossString, String currencyString) {
        return new Salary(
            Double.parseDouble(fromString),
            Double.parseDouble(toString),
            Boolean.parseBoolean(grossString),
            Currency.valueOf(currencyString.replace("BYR", "BYN").replace("RUR", "RUB"))
        );
    }

    public static LocalDateTime parsePublishedAt(String publishedAtString) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ");
        return LocalDateTime.parse(publishedAtString, dateTimeFormatter);
    }
}
