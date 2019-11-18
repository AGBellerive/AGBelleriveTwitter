/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agbellerive.twitter.controller;

/**
 * @author Alex
 */
import com.agbellerive.twitter.presentation.MainApp;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.slf4j.LoggerFactory;

public class FormController {

    private final static org.slf4j.Logger LOG = LoggerFactory.getLogger(FormController.class);
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="createFile"
    private Button createFile; // Value injected by FXMLLoader

    @FXML // fx:id="consumerKey"
    private TextField consumerKey; // Value injected by FXMLLoader

    @FXML // fx:id="consumerSecret"
    private TextField consumerSecret; // Value injected by FXMLLoader

    @FXML // fx:id="acessToken"
    private TextField acessToken; // Value injected by FXMLLoader

    @FXML // fx:id="acessTokenSecret"
    private TextField acessTokenSecret; // Value injected by FXMLLoader

    /**
     * This method writes the file twitter4j.properties according to the users
     * input in the textfields and it proceeds to write it to the root directory
     * of the project
     *
     * @param event
     */
    @FXML
    void createFileClick(ActionEvent event) {
        try ( OutputStream output = new FileOutputStream("twitter4j.properties")) {
            Properties prop = new Properties();

            prop.setProperty("oauth.consumerKey", this.consumerKey.getText().trim());
            prop.setProperty("oauth.consumerSecret", this.consumerSecret.getText().trim());
            prop.setProperty("oauth.acessToken", this.acessToken.getText().trim());
            prop.setProperty("oauth.acessTokenSecret", this.acessTokenSecret.getText().trim());

            prop.store(output, "Twitter Properties file");
            switchScenes();
        } catch (IOException io) {
            LOG.error("Try catch failed in createFileClick");
        }
    }

    /**
     * This method is to switch to the Twitter view after the file is written to
     * the proper directory by the method createFileClick
     */
    private void switchScenes() {
        MainApp main = new MainApp();
        try {
            Stage stage = (Stage) this.createFile.getScene().getWindow();
            stage.close();
            main.start(new Stage());
        } catch (Exception ex) {
            LOG.error("Scence wasnt able to be built in switchScenes");
        }
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert createFile != null : "fx:id=\"createFile\" was not injected: check your FXML file 'form.fxml'.";
        assert consumerKey != null : "fx:id=\"consumerKey\" was not injected: check your FXML file 'form.fxml'.";
        assert consumerSecret != null : "fx:id=\"consumerSecret\" was not injected: check your FXML file 'form.fxml'.";
        assert acessToken != null : "fx:id=\"acessToken\" was not injected: check your FXML file 'form.fxml'.";
        assert acessTokenSecret != null : "fx:id=\"acessTokenSecret\" was not injected: check your FXML file 'form.fxml'.";

    }
}
