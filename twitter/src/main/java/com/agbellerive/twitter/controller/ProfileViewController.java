/**
 * Sample Skeleton for 'ProfileView.fxml' Controller Class
 */

package com.agbellerive.twitter.controller;

import com.agbellerive.twitter.business.TwitterEngine;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

public class ProfileViewController {
    private User authenticatedUser;
    private final static TwitterEngine engine = new TwitterEngine();
    private final Twitter twitter = engine.getTwitterinstance();
    
    private MentionedViewController mentionedViewController;
    private BorderPane mentionedView;

    private final static Logger LOG = LoggerFactory.getLogger(ProfileViewController.class);
    
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="profileImageView"
    private ImageView profileImageView; // Value injected by FXMLLoader

    @FXML // fx:id="name"
    private Label name; // Value injected by FXMLLoader

    @FXML // fx:id="userName"
    private Label userName; // Value injected by FXMLLoader

    @FXML // fx:id="followers"
    private Label followers; // Value injected by FXMLLoader

    @FXML // fx:id="following"
    private Label following; // Value injected by FXMLLoader
    
    @FXML // fx:id="description"
    private Label description; // Value injected by FXMLLoader

    @FXML // fx:id="lowerPane"
    private BorderPane lowerPane; // Value injected by FXMLLoader

    @FXML // fx:id="mentionedBtn"
    private Button mentionedBtn; // Value injected by FXMLLoader

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert profileImageView != null : "fx:id=\"profileImageView\" was not injected: check your FXML file 'ProfileView.fxml'.";
        assert name != null : "fx:id=\"name\" was not injected: check your FXML file 'ProfileView.fxml'.";
        assert userName != null : "fx:id=\"userName\" was not injected: check your FXML file 'ProfileView.fxml'.";
        assert followers != null : "fx:id=\"followers\" was not injected: check your FXML file 'ProfileView.fxml'.";
        assert following != null : "fx:id=\"following\" was not injected: check your FXML file 'ProfileView.fxml'.";
    }
    
    public void setUpView() throws TwitterException{
        loadMentionedView();
        loadSelfMentioned();
        setProfilePicture();
        setDescription();
        setHandle();
        setCount();
    }
    
    public void setUser() throws TwitterException{
        this.authenticatedUser = twitter.showUser(twitter.getId());
    }
    
    private void setProfilePicture(){
        Image image = new Image(this.authenticatedUser.get400x400ProfileImageURL());
        this.profileImageView.imageProperty().set(image);
    }
    
    private void setDescription(){
        this.description.setText(this.authenticatedUser.getDescription());
    }
    
    private void setHandle(){
        this.name.setText(this.authenticatedUser.getName());
        this.userName.setText("@"+this.authenticatedUser.getScreenName());
    }
    
    private void setCount(){
        this.authenticatedUser.getFavouritesCount();
        this.followers.setText(this.authenticatedUser.getFollowersCount()+" " +this.followers.getText());
        this.following.setText(this.authenticatedUser.getFriendsCount() +" " + this.following.getText());
    }
    
    //https://github.com/Twitter4J/Twitter4J/blob/master/twitter4j-examples/src/main/java/twitter4j/examples/timeline/GetUserTimeline.java
    private void setTweets() throws TwitterException{
        List<Status> tweets = twitter.getUserTimeline();
    }
    
    private void setMentioned() throws TwitterException{
        Query search = new Query(this.authenticatedUser.getName());
        QueryResult mentionsQuerry = twitter.search(search);
        List<Status> mentions = mentionsQuerry.getTweets();
    }
    
    private void loadMentionedView(){
        try{
            FXMLLoader mentionedFXML = new FXMLLoader(getClass().getResource("/fxml/MentionedView.fxml"));
            mentionedFXML.setResources(ResourceBundle.getBundle("MessagesBundle"));
            this.mentionedView = (BorderPane) mentionedFXML.load();
            this.mentionedViewController = mentionedFXML.getController();
            LOG.info("MentionedView sucessfully created");
        }
        catch(IOException ex){
            LOG.error("Could not load MentionedView",ex);
            Platform.exit();
        }
    }
    
    private void loadSelfMentioned() throws TwitterException{
        LOG.info(this.authenticatedUser.getName());
    }
    
    @FXML
    private void mentionedClick(ActionEvent event) {
        lowerPane.setCenter(this.mentionedView);
        LOG.info("Mentioned Button Clicked");
    }
    
}
