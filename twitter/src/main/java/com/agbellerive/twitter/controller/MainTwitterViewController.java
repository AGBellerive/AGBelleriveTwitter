package com.agbellerive.twitter.controller;
/**
 * Sample Skeleton for 'MainTwitterView.fxml' Controller Class
 */

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class MainTwitterViewController {
    
    private final String BUTTON_BACKGROUND_COLOR = "#15202b";
    private final String BUTTON_OUTLINE_COLOR = "#1da1f2";
    
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="mainPane"
    private BorderPane mainPane; // Value injected by FXMLLoader
    
    @FXML // fx:id="buttonLayoutHbox"
    private HBox buttonLayoutHbox; // Value injected by FXMLLoader
    
    @FXML // fx:id="homeBtn"
    private Button homeBtn; // Value injected by FXMLLoader

    @FXML // fx:id="tweetBtn"
    private Button tweetBtn; // Value injected by FXMLLoader

    @FXML // fx:id="dmBtn"
    private Button dmBtn; // Value injected by FXMLLoader

    @FXML // fx:id="helpBtn"
    private Button helpBtn; // Value injected by FXMLLoader

    @FXML // fx:id="homePane"
    private BorderPane homePane; // Value injected by FXMLLoader

    @FXML // fx:id="tweetPane"
    private BorderPane tweetPane; // Value injected by FXMLLoader

    @FXML // fx:id="sendTweetBtn"
    private Button sendTweetBtn; // Value injected by FXMLLoader

    @FXML // fx:id="dmPane"
    private BorderPane dmPane; // Value injected by FXMLLoader

    @FXML // fx:id="sendDmBtn"
    private Button sendDmBtn; // Value injected by FXMLLoaderjected by FXMLLoader

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert mainPane != null : "fx:id=\"mainPane\" was not injected: check your FXML file 'MainTwitterView.fxml'.";
        assert buttonLayoutHbox != null : "fx:id=\"buttonLayoutHbox\" was not injected: check your FXML file 'MainTwitterView.fxml'.";
        assert homeBtn != null : "fx:id=\"homeBtn\" was not injected: check your FXML file 'MainTwitterView.fxml'.";
        assert tweetBtn != null : "fx:id=\"tweetBtn\" was not injected: check your FXML file 'MainTwitterView.fxml'.";
        assert dmBtn != null : "fx:id=\"dmBtn\" was not injected: check your FXML file 'MainTwitterView.fxml'.";
        assert helpBtn != null : "fx:id=\"helpBtn\" was not injected: check your FXML file 'MainTwitterView.fxml'.";
        assert homePane != null : "fx:id=\"homePane\" was not injected: check your FXML file 'MainTwitterView.fxml'.";
        assert tweetPane != null : "fx:id=\"tweetPane\" was not injected: check your FXML file 'MainTwitterView.fxml'.";
        assert sendTweetBtn != null : "fx:id=\"sendTweetBtn\" was not injected: check your FXML file 'MainTwitterView.fxml'.";
        assert dmPane != null : "fx:id=\"dmPane\" was not injected: check your FXML file 'MainTwitterView.fxml'.";
        assert sendDmBtn != null : "fx:id=\"sendDmBtn\" was not injected: check your FXML file 'MainTwitterView.fxml'.";
   
        homePane.setVisible(true);
        tweetPane.setVisible(false);
        dmPane.setVisible(false);
    }
    
    @FXML
    void dmBtnClick(ActionEvent event) {
        homePane.setVisible(false);
        tweetPane.setVisible(false);
        dmPane.setVisible(true);
       
        dmBtn.setStyle("-fx-border-color:"+BUTTON_OUTLINE_COLOR+";");
        homeBtn.setStyle("-fx-border-color:"+BUTTON_BACKGROUND_COLOR+";");
        tweetBtn.setStyle("-fx-border-color:"+BUTTON_BACKGROUND_COLOR+";");
    }

    @FXML
    void homeBtnClick(ActionEvent event) {
        homePane.setVisible(true);
        tweetPane.setVisible(false);
        dmPane.setVisible(false);
        
        dmBtn.setStyle("-fx-border-color:"+BUTTON_BACKGROUND_COLOR+";");
        homeBtn.setStyle("-fx-border-color:"+BUTTON_OUTLINE_COLOR+";");
        tweetBtn.setStyle("-fx-border-color:"+BUTTON_BACKGROUND_COLOR+";");
    }

    @FXML
    void tweetBtnClick(ActionEvent event) {
        homePane.setVisible(false);
        tweetPane.setVisible(true);
        dmPane.setVisible(false);
        
        dmBtn.setStyle("-fx-border-color:"+BUTTON_BACKGROUND_COLOR+";");
        homeBtn.setStyle("-fx-border-color:"+BUTTON_BACKGROUND_COLOR+";");
        tweetBtn.setStyle("-fx-border-color:"+BUTTON_OUTLINE_COLOR+";");

    }
}
