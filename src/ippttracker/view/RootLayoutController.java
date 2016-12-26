package ippttracker.view;

import ippttracker.MainApp;
import java.io.File;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
    private void handleNew() {
        mainApp.getResultData().clear();
        mainApp.setResultFilePath(null);
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
    private void handleExit() {
        System.exit(0);
    }
    
}
