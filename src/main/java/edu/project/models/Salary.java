package edu.project.models;

public record Salary(
    double from,
    double to,
    boolean gross,
    Currency currency
) {
    public static Salary parseSalary(String fromString, String toString, String grossString, String currencyString) {
        double from = Double.parseDouble(fromString);
        double to = Double.parseDouble(toString);
        boolean gross = Boolean.parseBoolean(grossString);
        Currency currency = Currency.valueOf(currencyString);
        return new Salary(from, to, gross, currency);
    }
}
