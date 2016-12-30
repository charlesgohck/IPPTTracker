/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ippttracker.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author charlesgoh
 */
public class SplashController implements Initializable {
    
    private Stage splashStage;
    @FXML private StackPane splash;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setSplashStage(Stage splashStage) {
        this.splashStage = splashStage;
    }

    public void executeTransition() {
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(2), splash);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.setCycleCount(1);
        
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(2), splash);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setCycleCount(1);
        
        fadeIn.play();
        fadeOut.play();
        
        splashStage.close();
    }

    
    
}
