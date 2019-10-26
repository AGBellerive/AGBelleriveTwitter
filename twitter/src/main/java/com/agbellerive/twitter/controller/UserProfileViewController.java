/**
 * Sample Skeleton for 'UserProfileView.fxml' Controller Class
 */

package com.agbellerive.twitter.controller;

import com.agbellerive.twitter.business.TwitterEngine;
import com.agbellerive.twitter.business.TwitterStatusInfo;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.Relationship;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

public class UserProfileViewController {
    
    private TwitterStatusInfo currentUser;
    
    private DmViewController dmViewController;
    private BorderPane dmView;
    
    private final static TwitterEngine engine = new TwitterEngine();
    private final Twitter twitter=engine.getTwitterinstance();;
    
    private final static Logger LOG = LoggerFactory.getLogger(TwitterStatusInfo.class);
    
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

    @FXML // fx:id="following"
    private Label following; // Value injected by FXMLLoader

    @FXML // fx:id="followers"
    private Label followers; // Value injected by FXMLLoader

    @FXML // fx:id="description"
    private Label description; // Value injected by FXMLLoader
    
    @FXML // fx:id="displayPane"
    private BorderPane displayPane; // Value injected by FXMLLoader

    @FXML // fx:id="followBtn"
    private Button followBtn; // Value injected by FXMLLoader

    @FXML // fx:id="messageBtn"
    private Button messageBtn; // Value injected by FXMLLoader

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert profileImageView != null : "fx:id=\"profileImageView\" was not injected: check your FXML file 'UserProfileView.fxml'.";
        assert name != null : "fx:id=\"name\" was not injected: check your FXML file 'UserProfileView.fxml'.";
        assert userName != null : "fx:id=\"userName\" was not injected: check your FXML file 'UserProfileView.fxml'.";
        assert following != null : "fx:id=\"following\" was not injected: check your FXML file 'UserProfileView.fxml'.";
        assert followers != null : "fx:id=\"followers\" was not injected: check your FXML file 'UserProfileView.fxml'.";
        assert description != null : "fx:id=\"description\" was not injected: check your FXML file 'UserProfileView.fxml'.";
        assert followBtn != null : "fx:id=\"followBtn\" was not injected: check your FXML file 'UserProfileView.fxml'.";
        assert messageBtn != null : "fx:id=\"messageBtn\" was not injected: check your FXML file 'UserProfileView.fxml'.";
    }
    
    public void setUpView() throws TwitterException{
        initilizeFollowBtnStatus();
        initializeDmView();
        setProfilePicture();
        setDescription();
        setHandle();
        setCount();
        LOG.info("UserProfileViewController set up");
    }
    
    public void setUpUser(TwitterStatusInfo info){
        this.currentUser = info;
        LOG.info("currentUser initilized");
    }
    
    @FXML
    private void followClick(ActionEvent event) throws TwitterException {
        if(followBtn.getText().equals("Follow")){
            twitter.createFriendship(this.currentUser.getHandle());
            followBtn.setText("Unfollow");
            LOG.info("You Followed " +this.currentUser.getHandle());
        }
        else{
            twitter.destroyFriendship(this.currentUser.getHandle());
            followBtn.setText("Follow");
            LOG.info("You Unfollowed " +this.currentUser.getHandle());
        }
    }

    @FXML
    private void messageClick(ActionEvent event) {
        this.dmViewController.presetDmName(this.currentUser.getHandle());
        this.displayPane.setCenter(this.dmView);
        LOG.info("Displaying Dm view");
    }
    
    private void setProfilePicture(){
        Image image = new Image(this.currentUser.getLargeProfileImageURL());
        this.profileImageView.imageProperty().set(image);
    }
    
    private void setDescription(){
        this.description.setText(this.currentUser.getDescription());
    }
    
    private void setHandle(){
        this.name.setText(this.currentUser.getName());
        this.userName.setText("@"+this.currentUser.getScreenName());
    }
    
    private void setCount(){
        this.followers.setText(this.currentUser.getFollowersCount()+" " +this.followers.getText());
        this.following.setText(this.currentUser.getFriendsCount() +" " + this.following.getText());
    }
    
    
    private void initilizeFollowBtnStatus() throws TwitterException{
        User authenticated = twitter.showUser(twitter.getId());
        Relationship relation = twitter.showFriendship(authenticated.getScreenName(), this.currentUser.getScreenName());
        // relation.isSourceFollowedByTarget();
        if(relation.isSourceFollowingTarget()){
            followBtn.setText("Unfollow");
        }
        
    }
    
    private void initializeDmView(){
        try {
            FXMLLoader dmFxml = new FXMLLoader(getClass().getResource("/fxml/DmView.fxml"));
            dmFxml.setResources(ResourceBundle.getBundle("MessagesBundle"));
            this.dmView = (BorderPane) dmFxml.load();
            this.dmViewController= dmFxml.getController();
        } 
        catch (IOException ex) {
            java.util.logging.Logger.getLogger(UserProfileViewController.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
    
    public Stage loadUsersProfile(TwitterStatusInfo info) throws IOException, TwitterException {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/UserProfileView.fxml"));
        loader.setResources(ResourceBundle.getBundle("MessagesBundle"));
        
        BorderPane userProfile = (BorderPane)loader.load();
        UserProfileViewController userProfileViewController = loader.getController();
        
        userProfileViewController.setUpUser(info);
        userProfileViewController.setUpView();
        
        Scene scene = new Scene(userProfile);
        
        popupStage.getIcons().add(new Image("/images/Twitter_Logo_Blue.png"));
        popupStage.setTitle("Twitter Profile Of " + info.getName());
        
        popupStage.setScene(scene);
        
        return popupStage;
    }
}
/**
 *         
 */