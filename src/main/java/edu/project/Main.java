package edu.project;

import edu.project.models.Vacancy;
import edu.project.utils.CSV;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class Main {
    private final static Logger LOGGER = LogManager.getLogger();

    private Main() { }

    public static void main(String[] args) {
        List<Vacancy> vacancies = CSV.readVacancies("src/main/resources/vacancies.csv");
        vacancies.forEach(LOGGER::info);
    }
}
