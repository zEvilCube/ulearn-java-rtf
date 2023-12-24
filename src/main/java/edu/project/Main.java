package edu.project;

import edu.project.models.db.CurrencyEntity;
import edu.project.models.db.VacancyEntity;
import edu.project.utils.Stats;
import edu.project.utils.db.DAOManager;
import edu.project.utils.db.DBManager;
import edu.project.utils.gui.Drawer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class Main {
    private final static Logger LOGGER = LogManager.getLogger(Main.class);
    private final static String CSV_PATH = "src/main/resources/vacancies.csv";
    private static final String DB_PATH = "jdbc:sqlite:src/main/resources/database.db";

    private Main() { }

    public static void main(String[] args) {
        DBManager.setConnectionSource(DB_PATH);
        DBManager.createAll(new DAOManager<>(VacancyEntity.class), new DAOManager<>(CurrencyEntity.class));
        LOGGER.info("База данных инициализирована\n");

        // DBManager.clearTable(new DAOManager<>(CurrencyEntity.class));
        // LOGGER.info("Курс валют обновлён\n");

        var vacancies = Stats.getFromDB();
        if (!vacancies.isEmpty()) {
            LOGGER.info("Вакансии считаны из базы.");
            vacancies.forEach(vacancy -> LOGGER.info(String.format("Вакансия из БД: %s", vacancy)));
            LOGGER.info(String.format("Всего в базе: %d вакансий\n", vacancies.size()));
        } else {
            LOGGER.warn("В базе вакансий нет, загружаем из csv-файла...");
            vacancies = Stats.getFromCSV(CSV_PATH);
            vacancies.forEach(vacancy -> LOGGER.info(String.format("Вакансия из CSV: %s", vacancy)));
            LOGGER.info("Загрузка окончена\n");
        }

        new Drawer(vacancies, "Программист").setVisible(true);
    }
}
