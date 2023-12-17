package edu.project.models.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import edu.project.models.Currency;
import edu.project.models.Vacancy;
import java.time.LocalDateTime;

@DatabaseTable(tableName = "Vacancy")
public class VacancyEntity {
    @DatabaseField(canBeNull = false)
    private String name;
    @DatabaseField(canBeNull = false)
    private double salary;
    @DatabaseField(canBeNull = false)
    private Currency salaryCurrency;
    @DatabaseField(canBeNull = false)
    private String areaName;
    @DatabaseField(id = true)
    private String publishedAt;

    public VacancyEntity() {}

    public VacancyEntity(Vacancy vacancy) {
        this.name = vacancy.name();
        this.salary = vacancy.salary().getAverage();
        this.salaryCurrency = vacancy.salary().currency();
        this.areaName = vacancy.areaName();
        this.publishedAt = vacancy.publishedAt().toString();
    }

    public String getName() {
        return name;
    }

    public double getSalaryRUB() {
        return Currency.convertToRUB(salaryCurrency, salary);
    }

    public String getAreaName() {
        return areaName;
    }

    public int getPublishYear() {
        return LocalDateTime.parse(publishedAt).getYear();
    }

    @Override
    public String toString() {
        return String.format("%s - %.0f руб - %s - %d г.", name, getSalaryRUB(), areaName, getPublishYear());
    }
}
