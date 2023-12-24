package edu.project.models.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import edu.project.models.Currency;

@DatabaseTable(tableName = "Currency")
public class CurrencyEntity {
    public static final String CURRENCY_COLUMN_NAME = "numCode";

    @DatabaseField(columnName = CURRENCY_COLUMN_NAME, id = true)
    private Currency numCode;

    @DatabaseField(canBeNull = false)
    private double rateToRUB;

    public CurrencyEntity() {}

    public CurrencyEntity(Currency currency, double rateToRUB) {
        this.numCode = currency;
        this.rateToRUB = rateToRUB;
    }

    public double getRateToRUB() {
        return rateToRUB;
    }
}
