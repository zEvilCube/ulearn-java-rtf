package edu.project.models;

public record Salary(
    double from,
    double to,
    boolean gross,
    Currency currency
) {
    public double getAverageRUB() {
        return Currency.convertToRUB(currency, (from + to) / 2);
    }
}
