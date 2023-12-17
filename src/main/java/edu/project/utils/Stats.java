package edu.project.utils;

import edu.project.models.Vacancy;
import edu.project.models.db.VacancyEntity;
import edu.project.utils.db.DAOManager;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Stats {
    private static final int TOP_MAP_LIMIT = 10;
    public static final int MAX_PERCENTAGE = 100;

    private Stats() {}

    public static List<VacancyEntity> getFromCSV(String csvPath) {
        List<Vacancy> csvVacancies = CSV.readVacancies(csvPath);
        DAOManager<VacancyEntity> vacancyDao = new DAOManager<>(VacancyEntity.class);
        vacancyDao.updateAll(csvVacancies.stream().map(VacancyEntity::new).toList());
        return vacancyDao.getAll();
    }

    public static List<VacancyEntity> getFromDB() {
        return new DAOManager<>(VacancyEntity.class).getAll();
    }

    public static List<VacancyEntity> filterByName(List<VacancyEntity> entities, String name) {
        String nameLowerCase = name.toLowerCase();
        return entities.stream()
            .filter(entity -> entity.getName().toLowerCase().contains(nameLowerCase))
            .toList();
    }

    public static <K> Map<K, List<VacancyEntity>> groupBy(
        Function<VacancyEntity, K> key, List<VacancyEntity> entities
    ) {
        return entities.stream()
            .collect(Collectors.groupingBy(key));
    }

    public static <K> Map<K, List<VacancyEntity>> filterByCount(Map<K, List<VacancyEntity>> map, int threshold) {
        return map.entrySet().stream()
            .filter(entry -> entry.getValue().size() >= threshold)
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static <K> Map<K, Integer> countEach(Map<K, List<VacancyEntity>> map) {
        return map.entrySet().stream()
            .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().size()));
    }

    public static <K> Map<K, Integer> averageEach(Map<K, List<VacancyEntity>> map) {
        return map.entrySet().stream()
            .collect(
                Collectors.toMap(
                    Map.Entry::getKey,
                    entry -> Math.round((float)
                        entry.getValue().stream()
                            .mapToDouble(VacancyEntity::getSalaryRUB)
                            .average().orElse(0)
                    )

                )
            );
    }

    public static Map<String, Integer> sortedKeys(Map<String, Integer> map, boolean reverse) {
        var sortedStream = map.entrySet().stream()
            .sorted((reverse ? Map.Entry.comparingByKey(Comparator.reverseOrder()) : Map.Entry.comparingByKey()));
        return collectSorted(sortedStream);
    }

    public static Map<String, Integer> sortedValues(Map<String, Integer> map, boolean reverse) {
        var sortedStream = map.entrySet().stream()
            .sorted((reverse ? Map.Entry.comparingByValue(Comparator.reverseOrder()) : Map.Entry.comparingByValue()));
        return collectSorted(sortedStream);
    }

    private static Map<String, Integer> collectSorted(Stream<Map.Entry<String, Integer>> sortedStream) {
        return sortedStream.limit(TOP_MAP_LIMIT)
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    public static Map<String, Integer> salaryByYear(List<VacancyEntity> entities) {
        var grouped = groupBy(VacancyEntity::getPublishYear, entities);
        return averageEach(grouped).entrySet().stream()
            .collect(Collectors.toMap(entry -> entry.getKey().toString(), Map.Entry::getValue));
    }

    public static Map<String, Integer> countByYear(List<VacancyEntity> entities) {
        var grouped = groupBy(VacancyEntity::getPublishYear, entities);
        return countEach(grouped).entrySet().stream()
            .collect(Collectors.toMap(entry -> entry.getKey().toString(), Map.Entry::getValue));
    }

    public static Map<String, Integer> salaryByCity(List<VacancyEntity> entities) {
        var grouped = groupBy(VacancyEntity::getAreaName, entities);
        var filtered = filterByCount(grouped, entities.size() / MAX_PERCENTAGE);
        return averageEach(filtered);
    }

    public static Map<String, Integer> percentageByCity(List<VacancyEntity> entities) {
        var sumOfVacancies = entities.size();
        var grouped = groupBy(VacancyEntity::getAreaName, entities);
        var filtered = filterByCount(grouped, entities.size() / MAX_PERCENTAGE);
        return filtered.entrySet().stream()
            .collect(
                Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().size() * MAX_PERCENTAGE / sumOfVacancies)
            );
    }
}
