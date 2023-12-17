package edu.project.models;

public record Salary(
    double from,
    double to,
    boolean gross,
    Currency currency
) {
    public double getAverage() {
        return (from + to) / 2;
    }
}
