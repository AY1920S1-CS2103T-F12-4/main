package seedu.billboard.ui.charts;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.billboard.commons.core.date.DateInterval;
import seedu.billboard.commons.core.observable.ObservableData;
import seedu.billboard.model.expense.Expense;
import seedu.billboard.model.statistics.generators.BreakdownGenerator;
import seedu.billboard.model.statistics.generators.TimelineGenerator;
import seedu.billboard.model.statistics.formats.StatisticsFormat;
import seedu.billboard.ui.UiPart;


/**
 * Container for charts. Controls type of chart being displayed.
 */
public class ChartBox extends UiPart<Region> {

    private static final String FXML = "ChartBox.fxml";

    @FXML
    private AnchorPane chartContainer;

    private ObservableList<Expense> expenses;
    private ObservableData<DateInterval> dateInterval;
    private ExpenseChart currentChart;

    public ChartBox(ObservableData<StatisticsFormat> statsType, ObservableList<Expense> expenses) {
        super(FXML);

        this.expenses = expenses;
        this.dateInterval = new ObservableData<>();
        dateInterval.setValue(DateInterval.MONTH);

        statsType.observe(this::onStatsTypeChanged);
    }

    /**
     * Callback which is called when the observed statistics type changes.
     * @param type New statistic type to display.
     */
    private void onStatsTypeChanged(StatisticsFormat type) {
        if (currentChart != null) {
            chartContainer.getChildren().remove(currentChart.getRoot());
        }

        switch (type) {
        case TIMELINE:
            currentChart = new ExpenseTimelineChart(expenses, dateInterval, new TimelineGenerator());
            break;
        case BREAKDOWN:
            currentChart = new ExpenseBreakdownChart(expenses, new BreakdownGenerator());
            break;
        default:
            throw new UnsupportedOperationException("Chart not implemented for selected statistic");
        }

        chartContainer.getChildren().add(currentChart.getRoot());
    }
}
