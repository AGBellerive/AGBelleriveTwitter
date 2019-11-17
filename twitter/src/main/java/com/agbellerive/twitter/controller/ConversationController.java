/**
 * Sample Skeleton for 'DmConvoView.fxml' Controller Class
 */

package com.agbellerive.twitter.controller;

import com.agbellerive.twitter.business.TwitterEngine;
import com.agbellerive.twitter.presentation.MainApp;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.DirectMessage;
import twitter4j.DirectMessageList;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

public class ConversationController {
    private final TwitterEngine twitterEngine = new TwitterEngine();
    private final Twitter twitter = twitterEngine.getTwitterinstance();
    private final static Logger LOG = LoggerFactory.getLogger(ConversationController.class);
    private List<DirectMessage> dms;
    private List<String> friends = new ArrayList();
    HashMap<String, Long> friendsId = new HashMap<String,Long>();
    private final MainApp mainApp = new MainApp();
    

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="chatsList"
    private ListView<String> chatsList; // Value injected by FXMLLoader

    @FXML // fx:id="messagesList"
    private ListView<String> messagesList; // Value injected by FXMLLoader
    
    @FXML // fx:id="textArea"
    private TextArea textArea; // Value injected by FXMLLoader
    
    @FXML // fx:id="sendDmBtn"
    private Button sendDmBtn; // Value injected by FXMLLoader

    @FXML // fx:id="dmReciver"
    private TextField dmReciver; // Value injected by FXMLLoader

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws TwitterException {
        assert chatsList != null : "fx:id=\"chatsList\" was not injected: check your FXML file 'DmConvoView.fxml'.";
        assert messagesList != null : "fx:id=\"messagesList\" was not injected: check your FXML file 'DmConvoView.fxml'.";
        assert textArea != null : "fx:id=\"textArea\" was not injected: check your FXML file 'DmConvoView.fxml'.";
        getConvoList();
        getChat();

    }
    
    private void getConvoList(){
        User person = null;
        try{
            this.dms = twitter.getDirectMessages(50);
            
            for(DirectMessage dm : this.dms){
                person = twitter.showUser(dm.getSenderId());
                if(!(this.friends.contains(person.getScreenName())) && person.getId() != twitter.getId()){
                    this.friends.add(person.getScreenName());
                    this.friendsId.put(person.getScreenName(),person.getId());
                    this.chatsList.getItems().add(person.getScreenName());
                }
            }
        }
        catch(TwitterException ex){
            LOG.info("Cannot load the conversation list " +ex);
        }
    }
    
    private void getChat(){
        chatsList.getSelectionModel().selectedItemProperty().addListener((o,s,sl) ->{
            messagesList.getItems().clear();
            for(DirectMessage mess : this.dms){
             if(mess.getSenderId() == this.friendsId.get(this.chatsList.getSelectionModel().getSelectedItem())
                        || mess.getRecipientId() == this.friendsId.get(this.chatsList.getSelectionModel().getSelectedItem())){
                    if(mess.getSenderId() == this.friendsId.get(this.chatsList.getSelectionModel().getSelectedItem())){
                        messagesList.getItems().add("Sender :"+mess.getText());
                    }
                    else{
                        messagesList.getItems().add("You :"+mess.getText());
                    }
                }
            }
    });
    }
    
    @FXML
    private void sendBtnClick(ActionEvent event) {
        try {
            String reciver = this.dmReciver.getText();
            if(reciver.isBlank()){
                reciver = this.chatsList.getSelectionModel().getSelectedItem();
            }
            LOG.info("Direct Message result: Sent to : ||"+reciver+"|| with the message ||"+this.textArea.getText()+"||");
            this.twitterEngine.sendDirectMessage(reciver, this.textArea.getText());
            this.textArea.clear();
        }
        
        //Exception is a place holder for TwitterException
        catch (TwitterException ex) {
            this.mainApp.startUpWarning();
            LOG.error("Unable to send direct message", ex);
        }
    } 
}
