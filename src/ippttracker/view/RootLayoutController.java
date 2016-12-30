package ippttracker.view;

import ippttracker.MainApp;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javax.xml.bind.JAXBException;
import javax.xml.bind.PropertyException;

/**
 *
 * @author charlesgoh
 */
public class RootLayoutController {
    private MainApp mainApp;
    
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    
    @FXML
    private void handleNew() throws PropertyException {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Save changes to record?");
        alert.setHeaderText("Would you like to save your records?");
        alert.setContentText("Any unsaved data will be lost and cannot be retrieved");

        ButtonType saveButton = new ButtonType("Save");
        ButtonType noSaveButton = new ButtonType("Don't Save");
        ButtonType cancelButton = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(saveButton, noSaveButton, cancelButton);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == saveButton){
            handleSave();
            mainApp.getResultData().clear();
            mainApp.setResultFilePath(null);
        } else if (result.get() == noSaveButton) {
            mainApp.getResultData().clear();
            mainApp.setResultFilePath(null);
        } else if (result.get() == cancelButton) {
            // ... user chose "Three"
        } else { }
    }
    
    //OPens a filechooser to let the user select xml file
    @FXML
    private void handleOpen() throws JAXBException {
        FileChooser fileChooser = new FileChooser();
        
        //Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);
        
        //Show open file dialog
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());
        
        if (file != null) {
            mainApp.loadResultDataFromFile(file);
        }
    }
    
    @FXML 
    void handleSave() throws PropertyException {
        File resultFile = mainApp.getResultFilePath();
        if (resultFile != null) {
            mainApp.saveResultDataToFile(resultFile);
        } else {
            handleSaveAs();
        }
    }
    
    @FXML
    private void handleSaveAs() throws PropertyException {
        FileChooser fileChooser = new FileChooser();
        
        //Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser
                .ExtensionFilter("XML files (*.xml)", "*.xml");
        
        //Show save file dialog
        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());
        
        if (file != null) {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            mainApp.saveResultDataToFile(file);
        }
    }
    
    @FXML
    private void handleAbout() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("IPPTTracker");
        alert.setHeaderText("About");
        alert.setContentText("Author: Charles Goh\nWebsite: https://charlesgohck.github.io");
        alert.showAndWait();
    }
    
    @FXML
    private void handleExit() throws PropertyException {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Save changes to record?");
        alert.setHeaderText("Would you like to save your records?");
        alert.setContentText("Any unsaved data will be lost and cannot be retrieved");

        ButtonType saveButton = new ButtonType("Save");
        ButtonType noSaveButton = new ButtonType("Don't Save");
        ButtonType cancelButton = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(saveButton, noSaveButton, cancelButton);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == saveButton){
            handleSave();
            System.exit(0);
        } else if (result.get() == noSaveButton) {
            System.exit(0);
        } else if (result.get() == cancelButton) {
            // ... user chose "Three"
        } else { }
    }
    
    @FXML
    private void handleShowStatistics() throws IOException {
        System.out.println("System Message: Running handleShowStatistics()");
        mainApp.showResultsStatistics();
    }
    
}
