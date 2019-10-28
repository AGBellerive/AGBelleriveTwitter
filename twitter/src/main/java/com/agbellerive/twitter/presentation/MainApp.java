/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agbellerive.twitter.presentation;

import com.agbellerive.twitter.beans.TwitterProperties;
import com.agbellerive.twitter.controller.FormController;
import com.agbellerive.twitter.controller.MainTwitterViewController;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * http://twitter4j.org/en/code-examples.html
 * @author Alex Bellerive
 */
public class MainApp extends Application {
    
    private final static Logger LOG = LoggerFactory.getLogger(MainApp.class);
    
    private MainTwitterViewController MTViewController;
    BorderPane masterLayout;
    
    private FormController FormController;
    private Stage primaryStage;
    
    private TwitterProperties userKeys;
    
    @Override
    public void start(Stage primaryStage)throws Exception  {
        this.primaryStage = primaryStage;
        
        if(!checkProperties()){
            createForm();
        }
        else{
            createTwitter();
        }
        
        Scene scene = new Scene(this.masterLayout);
        this.primaryStage.setScene(scene);
        
        this.primaryStage.getIcons().add(new Image("/images/Twitter_Logo_Blue.png"));
        this.primaryStage.setTitle("Twitter");
        this.primaryStage.show();
    }
     /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        launch(args);
    }
    
    /**
     * This method creates the twitter view that 
     * is going to allow the twitter functionality
     * @return Scene
     * @throws Exception 
     */
    private void createTwitter() throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainTwitterView.fxml"));
            loader.setResources(ResourceBundle.getBundle("MessagesBundle"));
        
            this.masterLayout =(BorderPane)loader.load();
            this.MTViewController = loader.getController();
        
            this.MTViewController.setMainView(masterLayout);
        
            LOG.info("Twitter Scene Built");
    }
     /**
      * This method creates the form that is going to be 
      * used to ask the user to enter their information
      * @return Scene
      * @throws Exception 
      */
     private void createForm() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/form.fxml"));
        loader.setResources(ResourceBundle.getBundle("MessagesBundle"));
        
        this.masterLayout = (BorderPane) loader.load();
        this.FormController = loader.getController();
        
        LOG.info("Forum Scene Built");  
     }
     
     /**
      * This method checks if the file twitter4j.properties exists
      * if it doesn't exist this method returns a false boolean
      * which tells the start method to redirect to the page to
      * make the user enter information to create the file
      * @return boolean
      * @throws IOException 
      */
     private boolean checkProperties() throws IOException{
        boolean fileFound = false;
        Properties prop = new Properties();
        this.userKeys = new TwitterProperties(); 
        try(FileInputStream file = new FileInputStream("twitter4j.properties") ) {
           prop.load(file);
            
           userKeys.setConsumerKey(prop.getProperty("oauth.consumerKey"));
           userKeys.setConsumerSecret(prop.getProperty("oauth.consumerKey"));
           userKeys.setAcessToken(prop.getProperty("oauth.accessToken"));
           userKeys.setAcessTokenSecret(prop.getProperty("oauth.accessTokenSecret"));
            
            fileFound = true;
            LOG.info("Properties File Found redirecting to Twitter");
        } 
        catch (FileNotFoundException ex) {
            LOG.info("Couldnt find properties file redirecting to Form");
        }
         return fileFound;
     }
    /**
     * This method is for creating a pop up window when the 
     * user enters too many characters for their tweet
     */
    public void startUpAlert() {
        Alert warning = new Alert(AlertType.WARNING);
        warning.getDialogPane().setMinWidth(500);
        warning.setTitle("Warning ");
        warning.setHeaderText("Tweet Character Warning");
        warning.setContentText("You Have reached the max character limit");
        warning.showAndWait();
    }
    /**
     * This method is fired up when there is
     * an error like not being able to send a tweet
     * or send a direct message 
     */
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
