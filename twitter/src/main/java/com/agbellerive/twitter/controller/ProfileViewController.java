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

    @FXML // fx:id="tweetsBtn"
    private Button tweetsBtn; // Value injected by FXMLLoader

    @FXML // fx:id="reTweetsBtn"
    private Button reTweetsBtn; // Value injected by FXMLLoader

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
    
    /**
     * This method sets up the view of the specific user by calling helper methods
     * @throws TwitterException 
     */
    public void setUpView() throws TwitterException{
        loadMentionedView();
        setProfilePicture();
        setDescription();
        setHandle();
        setCount();
    }
    /**
     * This method gets the specific user that the porifle is going 
     * to be on
     * @throws TwitterException 
     */
    public void setUser() throws TwitterException{
        this.authenticatedUser = twitter.showUser(twitter.getId());
    }
    /**
     * This method sets the profile image of the user
     */
    private void setProfilePicture(){
        Image image = new Image(this.authenticatedUser.get400x400ProfileImageURL());
        this.profileImageView.imageProperty().set(image);
    }
    /**
     * This method sets the users description that is on their profile
     */
    private void setDescription(){
        this.description.setText(this.authenticatedUser.getDescription());
    }
    
    /**
     * This method sets the handle of the user
     */
    private void setHandle(){
        this.name.setText(this.authenticatedUser.getName());
        this.userName.setText("@"+this.authenticatedUser.getScreenName());
    }
    
    /**
     * This sets the count of the users followers and followed
     */
    private void setCount(){
        this.authenticatedUser.getFavouritesCount();
        this.followers.setText(this.authenticatedUser.getFollowersCount()+" " +this.followers.getText());
        this.following.setText(this.authenticatedUser.getFriendsCount() +" " + this.following.getText());
    }
    
    //https://github.com/Twitter4J/Twitter4J/blob/master/twitter4j-examples/src/main/java/twitter4j/examples/timeline/GetUserTimeline.java

    /**
     * This method loads the view to display the mentioned view
     * @throws TwitterException 
     */
    private void loadMentionedView() throws TwitterException{
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
    /**
     * This method sets the mentioned view as the center of the pane
     * @param event
     * @throws TwitterException 
     */
    @FXML
    private void mentionedClick(ActionEvent event) throws TwitterException {
        lowerPane.setCenter(this.mentionedView);
        LOG.info("Mentioned Button Clicked");
    }
    
}
