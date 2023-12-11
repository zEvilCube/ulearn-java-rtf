package edu.project.models;

import java.time.LocalDateTime;
import java.util.List;

public record Vacancy(
    String name,
    String description,
    List<String> keySkills,
    Experience experience,
    boolean premium,
    String employerName,
    Salary salary,
    String areaName,
    LocalDateTime publishedAt
) {
}
