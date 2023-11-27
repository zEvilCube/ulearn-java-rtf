package edu.project.models;

import edu.project.utils.HTTP;

public enum Currency {
    AZN,
    BYN,
    EUR,
    GEL,
    KGS,
    KZT,
    RUB,
    UAH,
    USD,
    UZS;

    public static double convert(Currency from, Currency to, double amount) {
        if (from == to) {
            return amount;
        }
        return Double.parseDouble(HTTP.getCurrencyConversionResponse(from, to, amount));
    }
}
