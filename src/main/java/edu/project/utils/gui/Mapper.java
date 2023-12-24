package edu.project.utils.gui;

import edu.project.utils.Stats;
import java.util.List;
import java.util.Map;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

public final class Mapper {
    private Mapper() {}

    public static PieDataset createPie(Map<String, Integer> data) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Другие", Stats.MAX_PERCENTAGE - data.values().stream().mapToInt(v -> v).sum());
        Stats.sortedValues(data, true).forEach(dataset::setValue);
        return dataset;
    }

    public static CategoryDataset createMultiBar(List<Map<String, Integer>> dataList, List<String> titles) {
        if (dataList.size() != titles.size()) {
            throw new RuntimeException("Количество словарей и заголовков должно совпадать");
        }
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < dataList.size(); i++) {
            var map = dataList.get(i);
            var title = titles.get(i);
            Stats.sortedKeys(map, false).forEach((key, value) -> dataset.addValue(value, title, key));
        }
        return dataset;
    }

    public static CategoryDataset createBar(Map<String, Integer> data) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        Stats.sortedValues(data, true).forEach((key, value) -> dataset.addValue(value, key, key));
        return dataset;
    }
}
