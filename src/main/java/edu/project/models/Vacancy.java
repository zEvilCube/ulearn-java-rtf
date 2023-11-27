package edu.project.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public record Vacancy(
    String name,
    String description,
    Set<String> keySkills,
    Experience experience,
    boolean premium,
    String employerName,
    Salary salary,
    String areaName,
    LocalDateTime publishedAt
    ) {

    @SuppressWarnings("MagicNumber")
    public static Vacancy parseVacancy(String[] fields) {
        return new Vacancy(
            fields[0],
            parseDescription(fields[1]),
            parseKeySkills(fields[2]),
            Experience.parseExperience(fields[3]),
            Boolean.parseBoolean(fields[4]),
            fields[5],
            Salary.parseSalary(fields[6], fields[7], fields[8], fields[9]),
            fields[10],
            parsePublishedAt(fields[11])
        );
    }

    private static String parseDescription(String description) {
        return description
            .replaceAll("<[^<>]*>", "")
            .replaceAll("\\s+", " ")
            .strip();
    }

    private static Set<String> parseKeySkills(String keySkillsString) {
        return Arrays.stream(keySkillsString.split("\n")).collect(Collectors.toSet());
    }

    private static LocalDateTime parsePublishedAt(String publishedAtString) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ");
        return LocalDateTime.parse(publishedAtString, dateTimeFormatter);
    }
}
