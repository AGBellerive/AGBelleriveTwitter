package com.agbellerive.twitter.controller;
/**
 * Sample Skeleton for 'MainTwitterView.fxml' Controller Class
 */
import com.agbellerive.twitter.business.TwitterEngine;
import com.agbellerive.twitter.presentation.MainApp;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.TwitterException;

public class MainTwitterViewController {
    
    private final String BUTTON_BACKGROUND_COLOR = "#15202b";
    private final String BUTTON_OUTLINE_COLOR = "#1da1f2";
    private static final int MAX_TWEET = 280;
    private final MainApp ma = new MainApp();
    private final TwitterEngine twitterEngine = new TwitterEngine();
    private final static Logger LOG = LoggerFactory.getLogger(MainTwitterViewController.class);
     @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="mainPane"
    private BorderPane mainPane; // Value injected by FXMLLoader

    @FXML // fx:id="buttonLayoutHbox"
    private HBox buttonLayoutHbox; // Value injected by FXMLLoader

   @FXML // fx:id="homeIconBtn"
    private Button homeIconBtn; // Value injected by FXMLLoader

    @FXML // fx:id="tweetIconBtn"
    private Button tweetIconBtn; // Value injected by FXMLLoader

    @FXML // fx:id="dmIconBtn"
    private Button dmIconBtn; // Value injected by FXMLLoader

    @FXML // fx:id="helpBtn"
    private Button helpBtn; // Value injected by FXMLLoader

    @FXML // fx:id="exitBtn"
    private Button exitBtn; // Value injected by FXMLLoader

    @FXML // fx:id="homePane"
    private BorderPane homePane; // Value injected by FXMLLoader

    @FXML // fx:id="tweetPane"
    private BorderPane tweetPane; // Value injected by FXMLLoader

    @FXML // fx:id="tweetTextArea"
    private TextArea tweetTextArea; // Value injected by FXMLLoader

    @FXML // fx:id="sendTweetBtn"
    private Button sendTweetBtn; // Value injected by FXMLLoader

    @FXML // fx:id="dmPane"
    private BorderPane dmPane; // Value injected by FXMLLoader

    @FXML // fx:id="dmTextArea"
    private TextArea dmTextArea; // Value injected by FXMLLoader

    @FXML // fx:id="sendDmBtn"
    private Button sendDmBtn; // Value injected by FXMLLoader
    
    @FXML // fx:id="dmReciver"
    private TextField dmReciver; // Value injected by FXMLLoader


    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert mainPane != null : "fx:id=\"mainPane\" was not injected: check your FXML file 'MainTwitterView.fxml'.";
        assert buttonLayoutHbox != null : "fx:id=\"buttonLayoutHbox\" was not injected: check your FXML file 'MainTwitterView.fxml'.";
        assert homeIconBtn != null : "fx:id=\"homeBtn\" was not injected: check your FXML file 'MainTwitterView.fxml'.";
        assert tweetIconBtn != null : "fx:id=\"tweetBtn\" was not injected: check your FXML file 'MainTwitterView.fxml'.";
        assert dmIconBtn != null : "fx:id=\"dmBtn\" was not injected: check your FXML file 'MainTwitterView.fxml'.";
        assert helpBtn != null : "fx:id=\"helpBtn\" was not injected: check your FXML file 'MainTwitterView.fxml'.";
        assert homePane != null : "fx:id=\"homePane\" was not injected: check your FXML file 'MainTwitterView.fxml'.";
        assert tweetPane != null : "fx:id=\"tweetPane\" was not injected: check your FXML file 'MainTwitterView.fxml'.";
        assert sendTweetBtn != null : "fx:id=\"sendTweetBtn\" was not injected: check your FXML file 'MainTwitterView.fxml'.";
        assert dmPane != null : "fx:id=\"dmPane\" was not injected: check your FXML file 'MainTwitterView.fxml'.";
        assert sendDmBtn != null : "fx:id=\"sendDmBtn\" was not injected: check your FXML file 'MainTwitterView.fxml'.";
        //This sets the homePane to be the default visible pane
        homePane.setVisible(true);
        tweetPane.setVisible(false);
        dmPane.setVisible(false);
        
        ListenersSetUp();
    }
    
    @FXML
    private void dmIconBtnClick(ActionEvent event) {
        homePane.setVisible(false);
        tweetPane.setVisible(false);
        dmPane.setVisible(true);
       
        dmIconBtn.setStyle("-fx-border-color:"+BUTTON_OUTLINE_COLOR+";");
        homeIconBtn.setStyle("-fx-border-color:"+BUTTON_BACKGROUND_COLOR+";");
        tweetIconBtn.setStyle("-fx-border-color:"+BUTTON_BACKGROUND_COLOR+";");
    }

    @FXML
    private void homeIconBtnClick(ActionEvent event) {
        homePane.setVisible(true);
        tweetPane.setVisible(false);
        dmPane.setVisible(false);
        
        dmIconBtn.setStyle("-fx-border-color:"+BUTTON_BACKGROUND_COLOR+";");
        homeIconBtn.setStyle("-fx-border-color:"+BUTTON_OUTLINE_COLOR+";");
        tweetIconBtn.setStyle("-fx-border-color:"+BUTTON_BACKGROUND_COLOR+";");
    }

    @FXML
    private void tweetIconBtnClick(ActionEvent event) {
        homePane.setVisible(false);
        tweetPane.setVisible(true);
        dmPane.setVisible(false);
        
        dmIconBtn.setStyle("-fx-border-color:"+BUTTON_BACKGROUND_COLOR+";");
        homeIconBtn.setStyle("-fx-border-color:"+BUTTON_BACKGROUND_COLOR+";");
        tweetIconBtn.setStyle("-fx-border-color:"+BUTTON_OUTLINE_COLOR+";");
    }
    
    @FXML
    private void exitBtnClick(ActionEvent event) {
         Platform.exit();
    }
    
    private void ListenersSetUp(){
        tweetTextArea.textProperty().addListener((textAreaBeingObserved, oldValue, newValue)
                -> {
                checkCharacterCount(oldValue,MAX_TWEET);
        });
        
        dmTextArea.textProperty().addListener((textAreaBeingObserved, oldValue, newValue)
                -> {
                checkCharacterCount(oldValue,MAX_TWEET);
        });
        
        sendTweetBtn.setOnAction( event -> {sendTweet();});
        sendDmBtn.setOnAction(event->{sendDm();});
    }
    
    private void checkCharacterCount(String oldText,int limit) {
        int characters = tweetTextArea.getLength();
        if (characters >= limit) {
            tweetTextArea.setText(oldText);
            ma.startUpAlert();
        }
    }
    private void sendTweet(){
        try{
            LOG.debug("TextArea result: "+tweetTextArea.getText());
            twitterEngine.createTweet(tweetTextArea.getText());
        }
        catch (TwitterException ex){
            ma.startUpWarning();
            LOG.error("Unable to send Tweet",ex);
        }
    }
    
    private void sendDm(){
        try {
            LOG.debug("Direct Message result: Sent to : ||"+dmReciver.getText()+"|| with the message ||"+dmTextArea.getText()+"||");
            twitterEngine.sendDirectMessage(dmReciver.getText(), dmTextArea.getText());
        }
        catch (TwitterException ex) {
            ma.startUpWarning();
            LOG.error("Unable to send direct message", ex);
        }
    }
    
    
}
