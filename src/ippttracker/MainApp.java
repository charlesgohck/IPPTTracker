package ippttracker;

import ippttracker.model.Result;
import ippttracker.model.ResultListWrapper;
import ippttracker.view.ResultEditDialogController;
import ippttracker.view.ResultOverviewController;
import ippttracker.view.ResultsStatisticsController;
import ippttracker.view.RootLayoutController;
import ippttracker.view.SplashController;
import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author charlesgoh
 */
public class MainApp extends Application {
    
    @SuppressWarnings("FieldMayBeFinal")
    private ObservableList<Result> resultData;
    public MainApp() {
        this.resultData = FXCollections.observableArrayList();
//        resultData.add(new Result("Goh", "Charles", LocalDate.of(2012, 12, 25)));
//        resultData.add(new Result("Goh", "Charles", LocalDate.of(2013, 12, 12)));
//        resultData.add(new Result("Goh", "Charles", LocalDate.of(2016, 12, 3)));
//        resultData.add(new Result("Goh", "Charles", LocalDate.of(2014, 11, 11)));
//        resultData.add(new Result("Goh", "Charles", LocalDate.of(2015, 1, 10)));
    }
    
    //Add some sample data
    public ObservableList<Result> getResultData() {
        return resultData;
    }
    
    private Stage primaryStage;
    private BorderPane rootLayout;
    
    @Override
    public void start(Stage primaryStage) throws JAXBException {
        
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("IPPT Tracker");
        
        // Set the application icon
        this.primaryStage.getIcons().add(new Image("file:resources/graphics/Logo/IPPTTracker Logo v1.png"));
        
        //initSplashScreen();
        initRootLayout();
        
        showResultOverview();
    }
    
//    public void initSplashScreen() {
//        try {
//            //Load the fxml file and create a new stage for the popup dialog
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(MainApp.class.getResource("view/Splash.fxml"));
//            StackPane page = (StackPane) loader.load();
//            
//            //Create the dialog Stage
//            Stage splashStage = new Stage();
//            splashStage.setTitle("Splash Screen for IPPT Tracker");
//            //splashStage.initModality(Modality.WINDOW_MODAL);
//            splashStage.initStyle(StageStyle.UNDECORATED);
//            splashStage.initOwner(splashStage);
//            Scene scene = new Scene(page);
//            splashStage.setScene(scene);
//            
//            //Set logo of dialog Stage
//            splashStage.getIcons().add(new Image("file:resources/graphics/Logo/IPPTTracker Logo v1.png"));
//            
//            //Set the person into the controller
//            SplashController controller = loader.getController();
//            controller.setSplashStage(splashStage);
//            controller.executeTransition();
//            
//            //Show the dialog and wait until the user closes it
//            splashStage.showAndWait();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
    
    //Initializes root layout
    public void initRootLayout() throws JAXBException {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class
                    .getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            // Give the controller access to the main app.
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Try to load last opened person file.
        File file = getResultFilePath();
        if (file != null) {
            loadResultDataFromFile(file);
    }
}
    
    //Shows the ippt results overview inside the root layout.
    public void showResultOverview() {
        try {
            //Load results overview
           FXMLLoader loader = new FXMLLoader();
           loader.setLocation(MainApp.class.getResource("view/ResultOverview.fxml"));
           AnchorPane ResultOverview = (AnchorPane) loader.load();
           
           //Set person overview into the center of root layout.
           rootLayout.setCenter(ResultOverview);
           
           //Give the controller access to the main app
           ResultOverviewController controller = loader.getController();
           controller.setMainApp(this);
           
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public Stage getPrimaryStage() {
        return this.primaryStage;
    }
    
    public boolean showResultEditDialog(Result result) {
        try {
            //Load the fxml file and create a new stage for the popup dialog
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/ResultEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            
            //Create the dialog Stage
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit IPPT Entry");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            
            //Set logo of dialog Stage
            dialogStage.getIcons().add(new Image("file:resources/graphics/Logo/IPPTTracker Logo v1.png"));
            
            //Set the person into the controller
            ResultEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setResult(result);
            
            //Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            return controller.isDoneClick();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    //Returns the file preference, i.e. the file that was last opened
    public File getResultFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }
    
    //Sets the file path of the currently loaded file. 
    public void setResultFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());
            
            //Update the stage title
            primaryStage.setTitle("AddressApp - " + file.getName());
        } else {
            prefs.remove("filePath");
        }
        
        prefs.remove("filePath");
        //Update the stage title
        primaryStage.setTitle("IPPTTracker");
    }
    
    public void loadResultDataFromFile(File file) throws JAXBException {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(ResultListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();
            
            // Reading XML from the file and unmarhsalling
            ResultListWrapper wrapper = (ResultListWrapper) um.unmarshal(file);
            
            resultData.clear();
            resultData.addAll(wrapper.getResults());
            
            //Save the file path to the registry
            setResultFilePath(file);
        } catch (JAXBException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not load data");
            alert.setContentText("Could not load data from file:\n" + file.getPath());
            
            alert.showAndWait();
        }
    }
    
    //Saves the current result list of data to the specified file
    public void saveResultDataToFile(File file) throws PropertyException {
        try {
            JAXBContext context = JAXBContext.newInstance(ResultListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            
            //Wrapping our result data
            ResultListWrapper wrapper = new ResultListWrapper();
            wrapper.setResults(resultData);
            
            //Marshalling and saving XML to the file
            m.marshal(wrapper, file);
            
            //Save the file path to the registry
            setResultFilePath(file);
        } catch (JAXBException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not save data");
            alert.setContentText("Could not save data to file:\n" + file.getPath());
            
            alert.showAndWait();
        }
        
    }
    
     public void showResultsStatistics() throws IOException {
            try {
                //Load the txml file and create . new stage for the popup
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(MainApp.class.getResource("view/ResultsStatistics.fxml"));
                AnchorPane page = (AnchorPane) loader.load();
                Stage dialogStage = new Stage();
                dialogStage.setTitle("IPPT Statistics Chart");
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initOwner(primaryStage);
                Scene scene = new Scene(page);
                dialogStage.setScene(scene);
                dialogStage.getIcons().add(new Image("file:resources/graphics/Logo/IPPTTracker Logo v1.png"));
                
                //Set the persons into the controller
                ResultsStatisticsController controller = loader.getController();
                controller.setResultData(resultData);
                
                dialogStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    
    public static void main(String[] args) {
        launch(args);
    }
}
