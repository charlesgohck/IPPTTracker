/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ippttracker.view;

import ippttracker.MainApp;
import ippttracker.model.Result;
import ippttracker.util.DateUtil;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author charlesgoh
 */
public class ResultOverviewController {
    
    @FXML
    private TableView <Result> resultTable;
    @FXML
    private TableColumn <Result, String> firstNameColumn;
    @FXML
    private TableColumn <Result, String> lastNameColumn;
    @FXML
    private TableColumn <Result, LocalDate> dateColumn;
    
    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label pushupLabel;
    @FXML
    private Label situpLabel;
    @FXML
    private Label runningLabel;
    @FXML
    private Label totalScoreLabel;
    @FXML
    private Label ageLabel;
    @FXML
    private Label isSFLabel;
    @FXML
    private Label ipptDateLabel;
    
    DateTimeFormatter myDateFormatter;
    
    //Reference to the main application
    private MainApp mainApp;
    
    public ResultOverviewController() { }
    
     /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
        myDateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().ipptDateProperty());
        //setupChoiceBoxes();
        
        // Clear person details
        showResultDetails(null);
        
        // Listen for changes in results
        resultTable.getSelectionModel().selectedItemProperty()
                   .addListener((observable, oldValue, newValue) -> showResultDetails(newValue));
    }
    
    //Called when teh user clicks on the delete button
    @FXML
    private void handleDeleteResult() {
        int selectedIndex = resultTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            resultTable.getItems().remove(selectedIndex);
        }
        else {
            //Nothing selected
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner((mainApp.getPrimaryStage()));
            alert.setTitle("No Result Selected");
            alert.setHeaderText("You have not selected an IPPT result.");
            alert.setContentText("Please select a valid result in the table.");
            alert.showAndWait();
        }
    }
    
    /**
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        //Add observable list data to the table
        resultTable.setItems(mainApp.getResultData());
    }
    
    public void showResultDetails(Result result) {
        if (result != null) {
            firstNameLabel.setText(result.getFirstName());
            lastNameLabel.setText(result.getLastName());
            pushupLabel.setText(result.getPushup().toString());
            situpLabel.setText(result.getSitup().toString());
            runningLabel.setText(result.getRunning().toString());
            totalScoreLabel.setText(result.getTotalScore().toString() + "/100");
            ageLabel.setText(result.getAge().toString());
            if (result.getIsSF()) {
                isSFLabel.setText("YES");
            } else {
                isSFLabel.setText("NO");
            }
            //ipptDateLabel.setText(DateUtil.format(result.getIPPTDate()));
            //ipptDateLabel.setText(result.getIPPTDate().toString());
        } else {
            //Result is null, remove all the text.
            firstNameLabel.setText("");
            lastNameLabel.setText("");
            pushupLabel.setText("0");
            situpLabel.setText("0");
            runningLabel.setText("0");
            totalScoreLabel.setText("0");
            ageLabel.setText("18");
            isSFLabel.setText("NO");
            ipptDateLabel.setText("11/11/2016"); //Dummy DateLabel
        }
    }
    
    @FXML
    private void handleNewResult() {
        Result tempResult = new Result(null, null, null);
        boolean doneClicked = mainApp.showResultEditDialog(tempResult);
        System.out.println("Passed after boolean");
        if (doneClicked) {
            mainApp.getResultData().add(tempResult);
        }
    }
    
    @FXML
    private void handleEditResult() {
        Result selectedResult = resultTable.getSelectionModel().getSelectedItem();
        if (selectedResult != null) {
            boolean doneClicked = mainApp.showResultEditDialog(selectedResult);
            if (doneClicked) {
                showResultDetails(selectedResult);
            }
        } else {
            //Nothing selected
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select an IPPT entry from the table...");
            
            alert.showAndWait();
        }
    }
    
}
