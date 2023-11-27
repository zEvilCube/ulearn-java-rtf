package edu.project.models;

import java.time.LocalDateTime;
import java.util.Set;

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
}
