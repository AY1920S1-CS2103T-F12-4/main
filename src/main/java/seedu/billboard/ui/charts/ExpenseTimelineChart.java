package seedu.billboard.ui.charts;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import seedu.billboard.commons.core.date.DateInterval;
import seedu.billboard.commons.core.date.DateRange;
import seedu.billboard.model.expense.Amount;
import seedu.billboard.model.expense.Expense;
import seedu.billboard.model.statistics.EmptyExpenseTimeline;
import seedu.billboard.model.statistics.ExpenseTimeline;

/**
 * Represents a chart showing the timeline for expenses.
 */
public class ExpenseTimelineChart extends ExpenseChart<ExpenseTimeline> {

    private static final String FXML = "ExpenseTimelineChart.fxml";

    @FXML
    private LineChart<String, BigDecimal> timelineChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    private final EnumMap<DateInterval, DateTimeFormatter> dateIntervalFormats;
    private XYChart.Series<String, BigDecimal> series;


    public ExpenseTimelineChart(ObservableList<Expense> expenses) {
        super(FXML, expenses);

        dateIntervalFormats = new EnumMap<>(DateInterval.class);
        dateIntervalFormats.put(DateInterval.DAY, DateTimeFormatter.ofPattern("dd/MM/yy"));
        dateIntervalFormats.put(DateInterval.WEEK, DateTimeFormatter.ofPattern("dd/MM/yy"));
        dateIntervalFormats.put(DateInterval.MONTH, DateTimeFormatter.ofPattern("MM/yy"));
        dateIntervalFormats.put(DateInterval.YEAR, DateTimeFormatter.ofPattern("yyyy"));

        ExpenseTimeline expenseTimeline = new EmptyExpenseTimeline();
        series = new XYChart.Series<>();
        series.setName("All Expenses");
        series.getData().setAll(getData(expenseTimeline.getTimeline(), expenseTimeline.getDateInterval()));
        timelineChart.getData().add(series);
    }


    @Override
    public void onDataChange(ExpenseTimeline newData) {
        Map<DateRange, Amount> timeline = newData.getTimeline();
        List<XYChart.Data<String, BigDecimal>> data = getData(timeline, newData.getDateInterval());
        series.getData().setAll(data);
    }

    /**
     * Extracts a list of {@code XYChart.Data} of {@code String} and {@code BigDecimal} from the input map of
     * {@code DateRange} to {@code Amount}. The date range is formatted as a string according to the given
     * date interval.
     */
    private List<XYChart.Data<String, BigDecimal>> getData(Map<DateRange, Amount> timeline, DateInterval interval) {
        return timeline.entrySet()
                .stream()
                .map(entry -> entryToData(entry, interval))
                .collect(Collectors.toList());
    }

    /**
     * Transforms a single map entry of {@code DateRange} and {@code Amount} to a data object. The date range is
     * formatted as a string according to the given date interval.
     */
    private XYChart.Data<String, BigDecimal> entryToData(Map.Entry<DateRange, Amount> entry,
                                                         DateInterval interval) {

        return new XYChart.Data<>(formatDate(entry.getKey().getStartDate(), interval), entry.getValue().amount);
    }

    /**
     * Formats a date as a string using the given date interval, as defined in {@code dateIntervalFormats}.
     */
    private String formatDate(LocalDate date, DateInterval dateInterval) {
        return dateIntervalFormats.get(dateInterval).format(date);
    }
}
