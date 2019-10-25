/**
 * Sample Skeleton for 'UserProfileView.fxml' Controller Class
 */

package com.agbellerive.twitter.controller;

import com.agbellerive.twitter.business.TwitterStatusInfo;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class UserProfileViewController {
    
    private TwitterStatusInfo currentUser;
    
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
    
    public void setUpView(TwitterStatusInfo info){
        this.currentUser = info;
        setProfilePicture();
        setDescription();
        setHandle();
        setCount();
    }
    private void setProfilePicture(){
        Image image = new Image(this.currentUser.getLargeProfileImageURL());
        
        //LOG.info(this.authenticatedUser.get400x400ProfileImageURL() +"");
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
}
