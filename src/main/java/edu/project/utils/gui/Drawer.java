package edu.project.utils.gui;

import edu.project.models.db.VacancyEntity;
import edu.project.utils.Stats;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.PieDataset;

public class Drawer extends JFrame {
    private static final int PANEL_GAP_PX = 10;
    private final String vacancyName;
    private final List<VacancyEntity> entitiesDB;

    public Drawer(List<VacancyEntity> entities, String name) {
        super("Кучка графиков");
        entitiesDB = entities;
        vacancyName = name;
        setContentPane(createMainPanel());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    public void updateName(String name) {
        new Drawer(entitiesDB, name).setVisible(true);
        setVisible(false);
        dispose();
    }

    public static JPanel createPieChart(String title, Map<String, Integer> data) {
        PieDataset dataset = Mapper.createPie(data);
        JFreeChart chart = ChartFactory.createPieChart(
            title,
            dataset,
            false,
            true,
            false
        );
        return new ChartPanel(chart);
    }

    public static JPanel createBarChart(String title, List<Map<String, Integer>> dataList, List<String> dataTitles) {
        CategoryDataset dataset = Mapper.createMultiBar(dataList, dataTitles);
        JFreeChart chart = ChartFactory.createBarChart(
            title,
            null,
            null,
            dataset,
            PlotOrientation.VERTICAL,
            true,
            false,
            false
        );
        return new ChartPanel(chart);
    }

    public static JPanel createBarChart(String title, Map<String, Integer> data) {
        CategoryDataset dataset = Mapper.createBar(data);
        JFreeChart chart = ChartFactory.createBarChart(
            title,
            null,
            null,
            dataset,
            PlotOrientation.HORIZONTAL,
            false,
            false,
            false
        );
        return new ChartPanel(chart);
    }

    public JPanel createChartPanel() {
        List<VacancyEntity> entitiesPR = Stats.filterByName(entitiesDB, vacancyName);

        JPanel chartPanel = new JPanel();
        chartPanel.setLayout(new GridLayout(0, 2, PANEL_GAP_PX, PANEL_GAP_PX));

        chartPanel.add(
            createBarChart(
                "Уровень зарплат по годам (руб.)",
                List.of(Stats.salaryByYear(entitiesDB), Stats.salaryByYear(entitiesPR)),
                List.of("Средняя з/п", String.format("Средняя з/п (%s)", vacancyName))
            )
        );
        chartPanel.add(
            createBarChart(
                "Количество вакансий по годам",
                List.of(Stats.countByYear(entitiesDB), Stats.countByYear(entitiesPR)),
                List.of("Количество вакансий", String.format("Количество вакансий (%s)", vacancyName))
            )
        );
        chartPanel.add(createBarChart("Уровень зарплат по городам (>1%)", Stats.salaryByCity(entitiesPR)));
        chartPanel.add(createPieChart("Доля вакансий по городам (>1%)", Stats.percentageByCity(entitiesPR)));

        return chartPanel;
    }

    public JPanel createControlPanel() {
        JTextField nameField = new JTextField(vacancyName);
        Font nameFont = nameField.getFont();
        Font newFont = new Font(nameFont.getName(), nameFont.getStyle(), nameFont.getSize() * 2);
        nameField.setFont(newFont);
        nameField.setHorizontalAlignment(JTextField.CENTER);

        JButton buttonName = new JButton("Найти вакансию");
        buttonName.setFont(newFont);
        buttonName.addActionListener((event) -> updateName(nameField.getText()));

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(1, 0, PANEL_GAP_PX, PANEL_GAP_PX));
        controlPanel.add(nameField);
        controlPanel.add(buttonName);

        return controlPanel;
    }

    public JPanel createMainPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        mainPanel.add(createChartPanel());
        mainPanel.add(createControlPanel());
        return mainPanel;
    }
}
