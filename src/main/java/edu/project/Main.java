package edu.project;

import edu.project.models.Currency;
import edu.project.models.Vacancy;
import edu.project.models.db.CurrencyEntity;
import edu.project.models.db.VacancyEntity;
import edu.project.utils.CSV;
import edu.project.utils.db.DAO;
import edu.project.utils.db.DB;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class Main {
    private final static Logger LOGGER = LogManager.getLogger();
    private final static String CSV_PATH = "src/main/resources/vacancies.csv";
    private static final String DB_PATH = "jdbc:sqlite:src/main/resources/database.db";

    private Main() { }

    public static void main(String[] args) {
        DB.setConnectionSource(DB_PATH);

        DAO<VacancyEntity> vacancyDao = new DAO<>(VacancyEntity.class);
        DAO<CurrencyEntity> currencyDao = new DAO<>(CurrencyEntity.class);
        DB.createAll(vacancyDao, currencyDao);

        updateVacancies(vacancyDao);

        List<VacancyEntity> dbVacancies = vacancyDao.getAll();
        dbVacancies.forEach(
            vacancy -> {
                String name = vacancy.getName();
                double salaryRUB = vacancy.getSalaryRUB();
                String areaName = vacancy.getAreaName();
                LocalDateTime publishedAt = vacancy.getPublishedAt();
                LOGGER.info(String.format("%s - %f - %s - %s", name, salaryRUB, areaName, publishedAt));
            }
        );

        Arrays.stream(Currency.values()).forEach(
            currency -> {
                double amount = 1d;
                double rate = Currency.convertToRUB(currency, amount);
                LOGGER.info(String.format("%f %s = %f RUB", amount, currency, rate));
            }
        );
    }

    public static void updateVacancies(DAO<VacancyEntity> vacancyDao) {
        List<Vacancy> csvVacancies = CSV.readVacancies(CSV_PATH);
        vacancyDao.updateAll(csvVacancies.stream().map(VacancyEntity::new).toList());
        // csvVacancies.forEach(LOGGER::info);
    }
}
