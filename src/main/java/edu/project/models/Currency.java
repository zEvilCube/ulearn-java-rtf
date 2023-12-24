package edu.project.models;

import edu.project.models.db.CurrencyEntity;
import edu.project.utils.HTTP;
import edu.project.utils.db.DAOManager;

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

    public static double convertToRUB(Currency from, double amount) {
        if (from == Currency.RUB) {
            return amount;
        }
        DAOManager<CurrencyEntity> dao = new DAOManager<>(CurrencyEntity.class);
        CurrencyEntity entity = dao.get(CurrencyEntity.CURRENCY_COLUMN_NAME, from);
        if (entity != null) {
            return entity.getRateToRUB() * amount;
        }
        double rate = Double.parseDouble(HTTP.getCurrencyConversionResponse(from, RUB, 1));
        dao.update(new CurrencyEntity(from, rate));
        return rate * amount;
    }
}
