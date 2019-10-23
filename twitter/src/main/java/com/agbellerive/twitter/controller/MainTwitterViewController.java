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
import javafx.scene.control.ListView;
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
    private final MainApp mainApp = new MainApp();
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

    @FXML // fx:id="tweetList"
    private ListView<?> tweetList; // Value injected by FXMLLoader

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

    @FXML // fx:id="helpPane"
    private BorderPane helpPane; // Value injected by FXMLLoader

    @FXML 
    /**
     * This method is called by the FXMLLoader when initialization is complete 
     * and it sets the initial visibility of the panes
     */
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
        helpPane.setVisible(false);
        
        listenersSetUp();
        LOG.info("Class fully initialized");
    }
    /**
     * In the context of the dm icon is clicked 
     * I decided to set all the other panes to be invisible
     * to have the dm pane be the only visible pane at the moment
     * as well as set the border color of the clicked button 
     * to indicate that this icon was pressed
     * @param event 
     */
    @FXML
    private void dmIconBtnClick(ActionEvent event) {
        homePane.setVisible(false);
        tweetPane.setVisible(false);
        dmPane.setVisible(true);
        helpPane.setVisible(false);
       
        dmIconBtn.setStyle("-fx-border-color:"+BUTTON_OUTLINE_COLOR+";");
        homeIconBtn.setStyle("-fx-border-color:"+BUTTON_BACKGROUND_COLOR+";");
        tweetIconBtn.setStyle("-fx-border-color:"+BUTTON_BACKGROUND_COLOR+";");
        helpBtn.setStyle("-fx-border-color:"+BUTTON_BACKGROUND_COLOR+";");
        LOG.info("DmIcon Clicked");
    }
    /**
     * In the context of the home icon is clicked 
     * I decided to set all the other panes to be invisible
     * to have the home pane be the only visible pane at the moment
     * as well as set the border color of the clicked button 
     * to indicate that this icon was pressed
     * @param event 
     */
    @FXML
    private void homeIconBtnClick(ActionEvent event) {
        homePane.setVisible(true);
        tweetPane.setVisible(false);
        dmPane.setVisible(false);
        helpPane.setVisible(false);
        
        dmIconBtn.setStyle("-fx-border-color:"+BUTTON_BACKGROUND_COLOR+";");
        homeIconBtn.setStyle("-fx-border-color:"+BUTTON_OUTLINE_COLOR+";");
        tweetIconBtn.setStyle("-fx-border-color:"+BUTTON_BACKGROUND_COLOR+";");
        helpBtn.setStyle("-fx-border-color:"+BUTTON_BACKGROUND_COLOR+";");
        LOG.info("HomeIcon Clicked");
    }
    
     /**
     * In the context of the tweet icon is clicked 
     * I decided to set all the other panes to be invisible
     * to have the tweet pane be the only visible pane at the moment
     * as well as set the border color of the clicked button 
     * to indicate that this icon was pressed
     * @param event 
     */
    @FXML
    private void tweetIconBtnClick(ActionEvent event) {
        homePane.setVisible(false);
        tweetPane.setVisible(true);
        dmPane.setVisible(false);
        helpPane.setVisible(false);
        
        dmIconBtn.setStyle("-fx-border-color:"+BUTTON_BACKGROUND_COLOR+";");
        homeIconBtn.setStyle("-fx-border-color:"+BUTTON_BACKGROUND_COLOR+";");
        tweetIconBtn.setStyle("-fx-border-color:"+BUTTON_OUTLINE_COLOR+";");
        helpBtn.setStyle("-fx-border-color:"+BUTTON_BACKGROUND_COLOR+";");
        LOG.info("TweetIcon Clicked");
    }
    
    /**
     * In the event the exit button is clicked the pane 
     * closes itself
     * @param event 
     */
    @FXML
    private void exitBtnClick(ActionEvent event) {
         LOG.info("ExitIcon Clicked");
         Platform.exit();
    }
    
     /**
     * In the context of the help icon is clicked 
     * I decided to set all the other panes to be invisible
     * to have the home pane be the only visible pane at the moment
     * as well as set the border color of the clicked button 
     * to indicate that this icon was pressed
     * @param event 
     */
    @FXML
    private void helpBtnClick(ActionEvent event) {
        homePane.setVisible(false);
        tweetPane.setVisible(false);
        dmPane.setVisible(false);
        helpPane.setVisible(true);
        
        dmIconBtn.setStyle("-fx-border-color:"+BUTTON_BACKGROUND_COLOR+";");
        homeIconBtn.setStyle("-fx-border-color:"+BUTTON_BACKGROUND_COLOR+";");
        tweetIconBtn.setStyle("-fx-border-color:"+BUTTON_BACKGROUND_COLOR+";");
        helpBtn.setStyle("-fx-border-color:"+BUTTON_OUTLINE_COLOR+";");
        LOG.info("HelpIcon Clicked");
    }

    
     /**
     * In the event this class is called the initilize
     * method calls this method to set up all the listeners
     * needed to add functionality like checking character count
     * and classes to send the tweet
     */
    private void listenersSetUp(){
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
        LOG.info("Event Listeners assigned by ListenersSetUp");
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
}
