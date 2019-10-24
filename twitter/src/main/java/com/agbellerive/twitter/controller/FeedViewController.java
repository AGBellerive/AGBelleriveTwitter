/**
 * Sample Skeleton for 'FeedView.fxml' Controller Class
 */

package com.agbellerive.twitter.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;

public class FeedViewController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="homePane"
    private BorderPane homePane; // Value injected by FXMLLoader

    @FXML // fx:id="tweetList"
    private ListView<?> tweetList; // Value injected by FXMLLoader

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert homePane != null : "fx:id=\"homePane\" was not injected: check your FXML file 'FeedView.fxml'.";
        assert tweetList != null : "fx:id=\"tweetList\" was not injected: check your FXML file 'FeedView.fxml'.";

    }
}
