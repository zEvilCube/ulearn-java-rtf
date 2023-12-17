package edu.project;

import edu.project.models.Currency;
import edu.project.models.Experience;
import edu.project.models.Salary;
import edu.project.models.Vacancy;
import edu.project.utils.CSV;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

public class CSVTest {
    @Test
    @DisplayName("Парсинг описания")
    void testParseDescription() {
        // given
        final String GIVEN_DESCRIPTION = "Some    <b>cool</b>,    <br>      but <s>html'ed</s> description";

        // when
        String parsedDescription = CSV.parseDescription(GIVEN_DESCRIPTION);

        // then
        final String EXPECTED_DESCRIPTION = "Some cool, but html'ed description";
        assertThat(parsedDescription).isEqualTo(EXPECTED_DESCRIPTION);
    }

    @Test
    @DisplayName("Парсинг опыта")
    void testParseExperience() {
        // given
        final List<String> GIVEN_EXPERIENCES = List.of("noExperience", "between1And3", "between3And6", "moreThan6");

        // when
        List<Experience> parsedExperiences = GIVEN_EXPERIENCES.stream().map(CSV::parseExperience).toList();

        // then
        final List<Experience> EXPECTED_EXPERIENCES = List.of(
            Experience.NO_EXPERIENCE, Experience.BETWEEN_1_AND_3, Experience.BETWEEN_3_AND_6, Experience.MORE_THAN_6
        );
        assertThat(parsedExperiences).isEqualTo(EXPECTED_EXPERIENCES);
    }

    @Test
    @DisplayName("Парсинг скиллов")
    void testParseKeySkills() {
        // given
        final String GIVEN_KEY_SKILLS_STRING = "Some skill\nSome other skill\nSome useless skill";

        // when
        List<String> parsedSkills = CSV.parseKeySkills(GIVEN_KEY_SKILLS_STRING);

        // then
        final List<String> EXPECTED_SKILLS = List.of("Some skill", "Some other skill", "Some useless skill");
        assertThat(parsedSkills).isEqualTo(EXPECTED_SKILLS);
    }

    @Test
    @DisplayName("Парсинг зарплаты")
    void testParseSalary() {
        // given
        final String GIVEN_FROM = "12.345";
        final String GIVEN_TO = "678.90";
        final String GIVEN_GROSS = "tRuE";
        final String GIVEN_CURRENCY = "RUR";

        // when
        Salary parsedSalary = CSV.parseSalary(GIVEN_FROM, GIVEN_TO, GIVEN_GROSS, GIVEN_CURRENCY);

        // then
        final Salary EXPECTED_SALARY = new Salary(12.345, 678.90, true, Currency.RUB);
        assertThat(parsedSalary.toString()).isEqualTo(EXPECTED_SALARY.toString());
    }

    @Test
    @DisplayName("Парсинг даты")
    void testParsePublishedAt() {
        // given
        final String GIVEN_DATE = "2023-12-17T17:20:37+0500";

        // when
        LocalDateTime parsedDate = CSV.parsePublishedAt(GIVEN_DATE);

        // then
        final LocalDateTime EXPECTED_DATE = LocalDateTime.of(2023, 12, 17, 17, 20, 37);
        assertThat(parsedDate).isEqualTo(EXPECTED_DATE);
    }

    @Test
    @DisplayName("Парсинг вакансии")
    void testParseVacancy() {
        // given
        final String[] GIVEN_VACANCY_DATA = {
            "PRO CODER 2007",
            "Cool <i>Description</i>",
            "Git\nJava, Maven",
            "noExperience",
            "FaLsE",
            "CubeCorp",
            "0", "1000", "fAlSe", "RUR",
            "Ekb, obv",
            "2023-12-17T16:35:37+0500"
        };

        // when
        Vacancy parsedVacancy = CSV.parseVacancy(GIVEN_VACANCY_DATA);

        // then
        final Vacancy EXPECTED_VACANCY = new Vacancy(
            "PRO CODER 2007",
            "Cool Description",
            List.of("Git", "Java", "Maven"),
            Experience.NO_EXPERIENCE,
            false,
            "CubeCorp",
            new Salary(0d, 1000d, false, Currency.RUB),
            "Ekb, obv",
            LocalDateTime.of(2023, 12, 17, 16, 35, 37)
        );
        assertThat(parsedVacancy.toString()).isEqualTo(EXPECTED_VACANCY.toString());
    }

    @Test
    @DisplayName("Парсинг файла с вакансиями")
    void testReadVacancies() {
        // given
        final String GIVEN_CSV_PATH = "src/test/resources/vacancies.csv";

        // when
        List<Vacancy> parsedVacancies = CSV.readVacancies(GIVEN_CSV_PATH);

        // then
        final int EXPECTED_LIST_SIZE = 10;
        assertThat(parsedVacancies.size()).isEqualTo(EXPECTED_LIST_SIZE);
    }
}
