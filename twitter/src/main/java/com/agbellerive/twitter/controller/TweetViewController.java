/**
 * Sample Skeleton for 'TweetView.fxml' Controller Class
 */

package com.agbellerive.twitter.controller;

import com.agbellerive.twitter.business.TwitterEngine;
import com.agbellerive.twitter.business.TwitterInfoInterface;
import com.agbellerive.twitter.business.TwitterStatusInfo;
import com.agbellerive.twitter.presentation.MainApp;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.TwitterException;

public class TweetViewController {
    
    private final TwitterEngine twitterEngine = new TwitterEngine();
    private final static Logger LOG = LoggerFactory.getLogger(TweetViewController.class);
    private final MainApp mainApp = new MainApp();
    private static final int MAX_TWEET = 280;
    
    private String currentAction;
    private TwitterInfoInterface userInfo;
    private Long tweetId;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="tweetPane"
    private BorderPane tweetPane; // Value injected by FXMLLoader

    @FXML // fx:id="tweetPrompt"
    private Label tweetPrompt; // Value injected by FXMLLoader

    @FXML // fx:id="tweetTextArea"
    private TextArea tweetTextArea; // Value injected by FXMLLoader

    @FXML // fx:id="sendTweetBtn"
    private Button sendTweetBtn; // Value injected by FXMLLoader
    
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert tweetPane != null : "fx:id=\"tweetPane\" was not injected: check your FXML file 'TweetView.fxml'.";
        assert tweetTextArea != null : "fx:id=\"tweetTextArea\" was not injected: check your FXML file 'TweetView.fxml'.";
        assert sendTweetBtn != null : "fx:id=\"sendTweetBtn\" was not injected: check your FXML file 'TweetView.fxml'.";
        listenersSetUp();
        this.currentAction = "Tweet";
    }
    
        /**
     * In the event the sendTweetBtn is clicked
     * this method will be called and if the requirements
     * are met it will send the tweet with a method from the TwitterEngine class,
     * if not an error will appear on the screen
     */
    private void sendTweet(){
        try{
            if(this.currentAction.equals("Tweet")){
            LOG.info("TextArea result: "+tweetTextArea.getText() +" Has been tweeted");
            twitterEngine.createTweet(tweetTextArea.getText());
            }
            else if(this.currentAction.equals("Retweet")){
                if(this.tweetTextArea.getText().isEmpty()){
                     if(this.userInfo instanceof TwitterStatusInfo){
                        TwitterStatusInfo userWithstatus = (TwitterStatusInfo)this.userInfo;
                        userWithstatus.reTweet();
                        LOG.info("Tweet Liked");
                    }
                    else{
                        LOG.info("Can not Retweet a Saved Tweet");
                    }
                    LOG.info("TextArea result is empty so a regular retweet");
                }
                else{
                   String statusBeingRetweeted = "https://twitter.com/"+this.userInfo.getScreenName()+"/status/"+this.userInfo.getTweetId();
                   twitterEngine.createTweet(tweetTextArea.getText() +" " +statusBeingRetweeted);
                   LOG.info("TextArea result: "+tweetTextArea.getText() +" Has been attached to the retweet");
                }
            }
            else if (this.currentAction.equals("Reply")){
                if(this.userInfo instanceof TwitterStatusInfo){
                        TwitterStatusInfo userWithstatus = (TwitterStatusInfo)this.userInfo;
                        userWithstatus.makeComment(tweetTextArea.getText());
                        LOG.info("A reply has been made with the content: "+tweetTextArea.getText() );
                }
                else{
                     LOG.info("Can not Reply a Saved Tweet");
                }
            }

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
    /**
     * This method sets up the listener on the textbox
     */
     private void listenersSetUp(){
        tweetTextArea.textProperty().addListener((textAreaBeingObserved, oldValue, newValue)
                -> {
                checkCharacterCount(oldValue,MAX_TWEET);
        });
        
        sendTweetBtn.setOnAction( event -> {sendTweet();});
        LOG.info("Tweet Listners assigned by ListenersSetUp");
    }
     /**
      * This method changes the view of the labels depending on 
      * if it is called by retweet or reply
      * @param user
      * @param action 
      */
     public void actionOnTweet(TwitterInfoInterface user,String action){
         if(action.equals("Retweet")){
             reTweeting(user);
         }
         else if(action.equals("Reply")){
             replying(user);
         }
         this.currentAction = action;
     }
     
     /**
      * This method sets up the view if it is a retweet
      * @param user 
      */
     public void reTweeting(TwitterInfoInterface user){
         this.tweetId = user.getTweetId();
         this.userInfo = user;
         this.tweetPrompt.setText(resources.getString("RetweetPrompt"));
         this.sendTweetBtn.setText(resources.getString("Retweet"));
     }

     /**
      * This method sets up the view if it is a replying
      * @param user 
      */
     public void replying(TwitterInfoInterface user){
         this.tweetId = user.getTweetId();
         this.userInfo = user;
         this.tweetPrompt.setText(resources.getString("ReplyPrompt"));
         this.sendTweetBtn.setText(resources.getString("Reply"));
     }     

}
