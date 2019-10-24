/**
 * Sample Skeleton for 'DmView.fxml' Controller Class
 */

package com.agbellerive.twitter.controller;

import com.agbellerive.twitter.business.TwitterEngine;
import com.agbellerive.twitter.presentation.MainApp;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.TwitterException;

public class DmViewController {
    private final TwitterEngine twitterEngine = new TwitterEngine();
    private final static Logger LOG = LoggerFactory.getLogger(DmViewController.class);
    private final MainApp mainApp = new MainApp();
    private static final int MAX_TWEET = 280;
    
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="dmPane"
    private BorderPane dmPane; // Value injected by FXMLLoader

    @FXML // fx:id="dmReciver"
    private TextField dmReciver; // Value injected by FXMLLoader

    @FXML // fx:id="dmTextArea"
    private TextArea dmTextArea; // Value injected by FXMLLoader

    @FXML // fx:id="sendDmBtn"
    private Button sendDmBtn; // Value injected by FXMLLoader

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert dmPane != null : "fx:id=\"dmPane\" was not injected: check your FXML file 'DmView.fxml'.";
        assert dmReciver != null : "fx:id=\"dmReciver\" was not injected: check your FXML file 'DmView.fxml'.";
        assert dmTextArea != null : "fx:id=\"dmTextArea\" was not injected: check your FXML file 'DmView.fxml'.";
        assert sendDmBtn != null : "fx:id=\"sendDmBtn\" was not injected: check your FXML file 'DmView.fxml'.";
        listenersSetUp();
    }
      /**
     * In the event this class is called the initilize
     * method calls this method to set up all the listeners
     * needed to add functionality like checking character count
     * and classes to send the tweet
     */
    private void listenersSetUp(){        
        dmTextArea.textProperty().addListener((textAreaBeingObserved, oldValue, newValue)
                -> {
                checkCharacterCount(oldValue,MAX_TWEET);
        });
        sendDmBtn.setOnAction(event->{sendDm();});
        LOG.info("Event Listeners for Dm initilized by ListenersSetUp");
    }

    /**
     * In the event the sendDmBtn is clicked
     * this method will be called and if the requirements
     * are met it will send the dm with a method from the TwitterEngine class, 
     * if not an error will appear on the screen
     */
    private void sendDm(){
        try {
            LOG.info("Direct Message result: Sent to : ||"+dmReciver.getText()+"|| with the message ||"+dmTextArea.getText()+"||");
            twitterEngine.sendDirectMessage(dmReciver.getText(), dmTextArea.getText());
        }
        // Exception is a place holder for TwitterException
        catch (TwitterException ex) {
            mainApp.startUpWarning();
            LOG.error("Unable to send direct message", ex);
        }
    }
    
    /**
     * In the event where the user starts writing in any of
     * the TextAreas this method is called to constantly check if 
     * the limit of characters is met, if it is it calls a method
     * to display an error
     * @param oldText
     * @param limit 
     */
    private void checkCharacterCount(String oldText,int limit) {
        int characters = dmTextArea.getLength();
        if (characters >= limit) {
            dmTextArea.setText(oldText);
            mainApp.startUpAlert();
        }
    }
    
}
