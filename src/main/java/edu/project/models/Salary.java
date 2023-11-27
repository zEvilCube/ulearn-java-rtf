package edu.project.models;

public record Salary(
    double from,
    double to,
    boolean gross,
    Currency currency
) {
    public static Salary parseSalary(String fromString, String toString, String grossString, String currencyString) {
        return new Salary(
            Double.parseDouble(fromString),
            Double.parseDouble(toString),
            Boolean.parseBoolean(grossString),
            Currency.valueOf(fixCurrencyString(currencyString))
        );
    }

    public static String fixCurrencyString(String currencyString) {
        return currencyString
            .replace("BYR", "BYN")
            .replace("RUR", "RUB");
    }
}
