package edu.project.models.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import edu.project.models.Vacancy;
import java.time.LocalDateTime;

@DatabaseTable(tableName = "Vacancy")
public class VacancyEntity {
    @DatabaseField(canBeNull = false)
    private String name;
    @DatabaseField(canBeNull = false)
    private double salaryRUB;
    @DatabaseField(canBeNull = false)
    private String areaName;
    @DatabaseField(id = true)
    private String publishedAt;

    public VacancyEntity() {}

    public VacancyEntity(Vacancy vacancy) {
        this.name = vacancy.name();
        this.salaryRUB = vacancy.salary().getAverageRUB();
        this.areaName = vacancy.areaName();
        this.publishedAt = vacancy.publishedAt().toString();
    }

    public String getName() {
        return name;
    }

    public double getSalaryRUB() {
        return salaryRUB;
    }

    public String getAreaName() {
        return areaName;
    }

    public LocalDateTime getPublishedAt() {
        return LocalDateTime.parse(publishedAt);
    }
}
