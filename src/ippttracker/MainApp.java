package ippttracker;


import ippttracker.model.Result;
import ippttracker.view.ResultEditDialogController;
import ippttracker.view.ResultOverviewController;
import java.io.IOException;
import java.time.LocalDate;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author charlesgoh
 */
public class MainApp extends Application {
    
    @SuppressWarnings("FieldMayBeFinal")
    private ObservableList<Result> resultData;
    public MainApp() {
        this.resultData = FXCollections.observableArrayList();
        resultData.add(new Result("Goh", "Charles", LocalDate.of(2012, 12, 25)));
        resultData.add(new Result("Goh", "Charles", LocalDate.of(2013, 12, 12)));
        resultData.add(new Result("Goh", "Charles", LocalDate.of(2014, 11, 11)));
        resultData.add(new Result("Goh", "Charles", LocalDate.of(2015, 1, 10)));
        resultData.add(new Result("Goh", "Charles", LocalDate.of(2016, 12, 3)));
    }
    
    //Add some sample data
    public ObservableList<Result> getResultData() {
        return resultData;
    }
    
    private Stage primaryStage;
    private BorderPane rootLayout;
    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("IPPT Tracker");
        
        initRootLayout();
        
        showResultOverview();
    }
    
    //Initializes root layout
    public void initRootLayout() {
        try {
            //Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            
            //Show the scene containing the root layout
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
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
    
    public static void main(String[] args) {
        launch(args);
    }
}
