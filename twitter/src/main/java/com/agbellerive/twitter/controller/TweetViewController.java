/**
 * Sample Skeleton for 'TweetView.fxml' Controller Class
 */

package com.agbellerive.twitter.controller;

import com.agbellerive.twitter.business.TwitterEngine;
import com.agbellerive.twitter.presentation.MainApp;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.TwitterException;

public class TweetViewController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="tweetPane"
    private BorderPane tweetPane; // Value injected by FXMLLoader

    @FXML // fx:id="tweetTextArea"
    private TextArea tweetTextArea; // Value injected by FXMLLoader

    @FXML // fx:id="sendTweetBtn"
    private Button sendTweetBtn; // Value injected by FXMLLoader
    
    private final TwitterEngine twitterEngine = new TwitterEngine();
    private final static Logger LOG = LoggerFactory.getLogger(TweetViewController.class);
    private final MainApp mainApp = new MainApp();
    private static final int MAX_TWEET = 280;
    
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert tweetPane != null : "fx:id=\"tweetPane\" was not injected: check your FXML file 'TweetView.fxml'.";
        assert tweetTextArea != null : "fx:id=\"tweetTextArea\" was not injected: check your FXML file 'TweetView.fxml'.";
        assert sendTweetBtn != null : "fx:id=\"sendTweetBtn\" was not injected: check your FXML file 'TweetView.fxml'.";
        listenersSetUp();

    }
    
        /**
     * In the event the sendTweetBtn is clicked
     * this method will be called and if the requirements
     * are met it will send the tweet with a method from the TwitterEngine class,
     * if not an error will appear on the screen
     */
    private void sendTweet(){
        try{
            LOG.info("TextArea result: "+tweetTextArea.getText());
            twitterEngine.createTweet(tweetTextArea.getText());
        }
        // Exception is a place holder for TwitterException
        catch (TwitterException ex){
            mainApp.startUpWarning();
            LOG.error("Unable to send Tweet",ex);
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
        int characters = tweetTextArea.getLength();
        if (characters >= limit) {
            tweetTextArea.setText(oldText);
            mainApp.startUpAlert();
        }
    }
     private void listenersSetUp(){
        tweetTextArea.textProperty().addListener((textAreaBeingObserved, oldValue, newValue)
                -> {
                checkCharacterCount(oldValue,MAX_TWEET);
        });
        
        sendTweetBtn.setOnAction( event -> {sendTweet();});
        LOG.info("Tweet Listners assigned by ListenersSetUp");
    }
}
