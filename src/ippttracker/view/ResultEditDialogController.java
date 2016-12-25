package ippttracker.view;

import ippttracker.model.Result;
import ippttracker.util.DateUtil;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class for ResultEditDialog
 *
 * @author charlesgoh
 * Used http://code.makery.ch/library/javafx-8-tutorial/part3/ as reference
 */
public class ResultEditDialogController {
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private ChoiceBox <Integer> pushupChoiceBox;
    @FXML private ChoiceBox <Integer> situpChoiceBox;
    @FXML private ChoiceBox <Integer> runningChoiceBox;
    @FXML private ChoiceBox <Integer> ageChoiceBox;
    @FXML private Label totalScoreLabel;
    Integer totalScore;
    @FXML private CheckBox isSFCheckBox;
    @FXML private DatePicker datepicker;
    private LocalDate tempDate;
    
    ObservableList <Integer> staticPoints;
    ObservableList <Integer> runningPoints;
    
    private Stage dialogStage;
    private Result result;
    @SuppressWarnings("FieldMayBeFinal")
    private boolean doneClicked = false;
    
    @FXML
    private void initialize(URL location, ResourceBundle resources) {
        
    }
    
    //Sets the dialog box stage
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
        setupChoiceBoxes();
    }
    
    //String pattern = "dd/MM/yyyy";
    //Sets the results to be edited in the dialog opened
    public void setResult(Result result) {
        this.result = result;
        firstNameField.setText(result.getFirstName());
        lastNameField.setText(result.getLastName());
        pushupChoiceBox.setValue(result.getPushup());
        situpChoiceBox.setValue(result.getSitup());
        runningChoiceBox.setValue(result.getRunning());
        ageChoiceBox.setValue(result.getAge());
        refreshTotalScore();
        
        System.out.println("result.getIsSF() is " + result.getIsSF());
        isSFCheckBox = new CheckBox(result.isSFProperty().getName());
        isSFCheckBox.setSelected(result.getIsSF());
        if (result.getIsSF()) { isSFCheckBox.fire(); }
        System.out.println("setPromptText is " + datepicker.getPromptText());
        datepicker.setPromptText(result.getIPPTDate().toString());
    
        System.out.println("datepicker initialized as " + datepicker.getValue());
        tempDate = result.getIPPTDate();
        System.out.println("tempDate initialized as: " + result.getIPPTDate().toString());
//        datepicker.setConverter(new StringConverter<LocalDate>() {
//            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
//            
//            @Override
//            public String toString(LocalDate date) {
//                if (date != null) {
//                    return dateFormatter.format(date);
//                } else {
//                    return "";
//                }
//            }
//            
//            @Override
//            public LocalDate fromString(String string) {
//                if (string != null && !string.isEmpty()) {
//                    return LocalDate.parse(string, dateFormatter);
//                } else {
//                    return null;
//                }
//            }
//        });
    }
    
    @SuppressWarnings("Convert2Diamond")
    private void setupChoiceBoxes() {
        //Points for static stations
        for (int i = 0; i < 51; i++) {
            if (i <= 25) {
                pushupChoiceBox.getItems().add(i);
                situpChoiceBox.getItems().add(i);
            }
            if (i > 17 && i < 51) {
                ageChoiceBox.getItems().add(i);
            }
            runningChoiceBox.getItems().add(i);
        }
    }
    
    @FXML
    public void refreshTotalScore() {
        totalScore = pushupChoiceBox.getValue() + situpChoiceBox.getValue() + runningChoiceBox.getValue();
        System.out.println("Refreshing total Score. New Score is: " + totalScore);
        totalScoreLabel.setText(totalScore.toString() + "/100");
    }
    
    @FXML
    public void handleChangeDate() {
        tempDate = datepicker.getValue();
    }
    
    //Returns true if the user clicked, false otherwise
    public boolean isDoneClick() { return doneClicked; }
    
    @FXML
    private void handleDone() {
        if (isInputValid()) {
            result.setFirstName(firstNameField.getText());
            result.setLastName(lastNameField.getText());
            result.setPushup(pushupChoiceBox.getValue());
            result.setSitup(situpChoiceBox.getValue());
            result.setRunning(runningChoiceBox.getValue());
            refreshTotalScore();
            result.setTotalScore(totalScore);
            result.setAge(ageChoiceBox.getValue());
            result.setIsSF(isSFCheckBox.isSelected());
            System.out.println("isSFCheckBox is " + isSFCheckBox.isSelected());
            
            System.out.println("tempDate is " + tempDate.toString());
            //result.setIPPTDate(tempDate);
            doneClicked = true;
            dialogStage.close();
        }
    }
    
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
    
    @FXML
    private void handleSFToggle() {
        isSFCheckBox.setSelected(!isSFCheckBox.isSelected());
    }
    
    //Validates the user input in all fields
    //Return true if the input is valid
    private boolean isInputValid() {
        String errormsg = "";
        if (firstNameField.getText() == null || firstNameField.getText().length() == 0) {
            errormsg += "No valid first name!\n";
        }
        if (lastNameField.getText() == null || lastNameField.getText().length() == 0) {
            errormsg += "No valid last name!\n";
        }
        if (pushupChoiceBox.getValue() < 0 || pushupChoiceBox.getValue() > 25) {
            errormsg += "Push-Up score is invalid";
        }
        if (situpChoiceBox.getValue() < 0 || situpChoiceBox.getValue() > 25) {
            errormsg += "Sit-Up score is invalid";
        }
        if (runningChoiceBox.getValue() < 0 || runningChoiceBox.getValue() > 50) {
            errormsg += "2.4km Run score is invalid";
        }
        if (tempDate.getYear() < 2014) {
            errormsg += "The new IPPT started in 2014. Please choose a valid date";
        }
        
        if (errormsg.length() == 0) {
            return true;
        } else {
            //Show the error message alert
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errormsg);
            
            alert.showAndWait();
            return false;
        }  
    }
    
}
