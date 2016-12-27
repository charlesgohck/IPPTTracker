package ippttracker.view;

import ippttracker.model.Result;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;

/**
 * FXML Controller class
 *
 * @author charlesgoh
 */
public class ResultsStatisticsController {
    
    @SuppressWarnings("FieldMayBeFinal")
    private ObservableList<LocalDate> dateEntries = FXCollections.observableArrayList();
    @SuppressWarnings("FieldMayBeFinal")
    private ObservableList<String> sortedDates = FXCollections.observableArrayList();
    
    @FXML private BarChart <String, Integer> barChart;
    @FXML private CategoryAxis xAxis;
    
    /**
     * Initializes the controller class.
     */
    public void initialize() {
    }
    
    public void setResultData(List <Result> results) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/mm/yyyy");
        
        results.forEach((result) -> {
            dateEntries.add(result.getIPPTDate());
        });
        Collections.sort(dateEntries);
        dateEntries.forEach((date) -> {
            sortedDates.add(date.toString());
        });
        
        xAxis.setCategories(sortedDates);
        
        //Initialize all series (categories)
        XYChart.Series <String, Integer> pushupSeries = new XYChart.Series<>();
        pushupSeries.setName("Push-Up Score");
        XYChart.Series <String, Integer> situpSeries = new XYChart.Series<>();
        situpSeries.setName("Sit-Up Score");
        XYChart.Series <String, Integer> runningSeries = new XYChart.Series<>();
        runningSeries.setName("2.4KM Run Score");
        
        results.forEach((result) -> {
            LocalDate date = result.getIPPTDate();
            pushupSeries.getData().add(new XYChart.Data(date.toString(), result.getPushup()));
            situpSeries.getData().add(new XYChart.Data(date.toString(), result.getSitup()));
            runningSeries.getData().add(new XYChart.Data(date.toString(), result.getRunning()));
        });
        
        barChart.getData().add(pushupSeries);
        barChart.getData().add(situpSeries);
        barChart.getData().add(runningSeries);
    }
    
}
