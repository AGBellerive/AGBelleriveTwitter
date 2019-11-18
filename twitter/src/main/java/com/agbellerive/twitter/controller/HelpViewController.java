/**
 * Sample Skeleton for 'HelpView.fxml' Controller Class
 */
package com.agbellerive.twitter.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelpViewController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="helpLable"
    private Label helpLable; // Value injected by FXMLLoader

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert helpLable != null : "fx:id=\"helpLable\" was not injected: check your FXML file 'HelpView.fxml'.";

    }
}
