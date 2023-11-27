package edu.project.models;

public record Salary(
    double salary_from,
    double salary_to,
    boolean salary_gross,
    Currency salary_currency
) {
}
