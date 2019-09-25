/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agbellerive.twitter.presentation;

import com.agbellerive.twitter.controller.MainTwitterViewController;
import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author 1733565
 */
public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainTwitterView.fxml"));
            Parent root = loader.load();
            MainTwitterViewController controller = loader.getController();
            Scene scene = new Scene(root);
            primaryStage.setTitle("Twitter");
            primaryStage.setScene(scene);
            primaryStage.show();
        } 
        catch (IOException | IllegalStateException ex) {
            //Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
            // See code samples for displaying an Alert box if an exception is thrown
        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    public void startUpAlert() {
        Alert warning = new Alert(AlertType.WARNING);
        warning.getDialogPane().setMinWidth(500);
        warning.setTitle("Warning ");
        warning.setHeaderText("Tweet Character Warning");
        warning.setContentText("You Have reached the max character limit");
        warning.showAndWait();
    }
    
    public void startUpWarning(){
        Alert warning = new Alert(AlertType.ERROR);
        warning.getDialogPane().setMinWidth(500);
        warning.setTitle("Error");
        
        warning.setHeaderText("Cannot complete your request");
        warning.setContentText("An unexpected error has occured while trying to "
                + "complete your request");
        warning.showAndWait();
    }
    
    

}
