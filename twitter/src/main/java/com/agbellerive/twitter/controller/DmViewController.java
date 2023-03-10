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
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class DmViewController {

    private final TwitterEngine twitterEngine = new TwitterEngine();
    private final Twitter twitter = twitterEngine.getTwitterinstance();
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
     * In the event this class is called the initilize method calls this method
     * to set up all the listeners needed to add functionality like checking
     * character count and classes to send the tweet
     */
    private void listenersSetUp() {
        this.dmTextArea.textProperty().addListener((textAreaBeingObserved, oldValue, newValue)
                -> {
            checkCharacterCount(oldValue, MAX_TWEET);
        });
        this.sendDmBtn.setOnAction(event -> {
            sendDm();
        });
    }

    /**
     * In the event the sendDmBtn is clicked this method will be called and if
     * the requirements are met it will send the dm with a method from the
     * TwitterEngine class, if not an error will appear on the screen
     */
    private void sendDm() {
        try {
            LOG.info("Direct Message result: Sent to : ||" + this.dmReciver.getText() + "|| with the message ||" + this.dmTextArea.getText() + "||");
            this.twitterEngine.sendDirectMessage(this.dmReciver.getText(), this.dmTextArea.getText());
            this.dmTextArea.clear();
        } // Exception is a place holder for TwitterException
        catch (TwitterException ex) {
            this.mainApp.startUpWarning();
            LOG.error("Unable to send direct message", ex);
        }
    }

    /**
     * In the event where the user starts writing in any of the TextAreas this
     * method is called to constantly check if the limit of characters is met,
     * if it is it calls a method to display an error
     *
     * @param oldText
     * @param limit
     */
    private void checkCharacterCount(String oldText, int limit) {
        int characters = this.dmTextArea.getLength();
        if (characters >= limit) {
            this.dmTextArea.setText(oldText);
            this.mainApp.startUpAlert();
        }
    }

    /**
     * If the dm name has to be preset in the case of looking at the users
     * profile this method is called to set that up and prevents the user to
     * remove that users name
     *
     * @param user
     */
    public void presetDmName(String user) {
        this.dmReciver.setText(user);
        this.dmReciver.setEditable(false);
        LOG.info("User : " + user + " has been preset as the sender");
    }

}
