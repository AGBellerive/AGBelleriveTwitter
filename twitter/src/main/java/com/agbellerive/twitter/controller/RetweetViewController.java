/**
 * Sample Skeleton for 'RetweetView.fxml' Controller Class
 */

package com.agbellerive.twitter.controller;

import com.agbellerive.twitter.business.TwitterStatusInfo;
import com.sun.javafx.scene.shape.TextHelper;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.LoggerFactory;
import twitter4j.TwitterException;

public class RetweetViewController {
    
    private RetweetViewController retweetViewController;
    
    private TwitterStatusInfo currentUser;
    
    private TweetViewController tweetViewController;
    private BorderPane tweetView;
    
    
    private final static org.slf4j.Logger LOG = LoggerFactory.getLogger(RetweetViewController.class);
    
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="userImage"
    private ImageView userImage; // Value injected by FXMLLoader

    @FXML // fx:id="headerLabel"
    private Label headerLabel; // Value injected by FXMLLoader

    @FXML // fx:id="tweetText"
    private Text tweetText; // Value injected by FXMLLoader

    @FXML // fx:id="likeBtn"
    private Button likeBtn; // Value injected by FXMLLoader
    
    @FXML // fx:id="lowerBorderPane"
    private BorderPane lowerBorderPane; // Value injected by FXMLLoader


    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert userImage != null : "fx:id=\"userImage\" was not injected: check your FXML file 'RetweetView.fxml'.";
        assert headerLabel != null : "fx:id=\"headerLabel\" was not injected: check your FXML file 'RetweetView.fxml'.";
        assert tweetText != null : "fx:id=\"tweetText\" was not injected: check your FXML file 'RetweetView.fxml'.";
        assert likeBtn != null : "fx:id=\"likeBtn\" was not injected: check your FXML file 'RetweetView.fxml'.";

    }
    
    @FXML
    void likeBtnClick(ActionEvent event) {
        try {
            this.currentUser.likeTweet();
            LOG.info("Tweet Liked");
        } 
        catch (TwitterException ex) {
            LOG.info("Tweet Wasnt Liked");
        }
    }
    
    public void setUpView(String action){
        setProfilePicture();
        setHeader();
        setText();
        initializeTweetView();
        setTweetArea(action);
    }
    
    public void setUpUser(TwitterStatusInfo user){
        this.currentUser = user;
    }
    
    private void setProfilePicture(){
        Image image = new Image(this.currentUser.getLargeProfileImageURL());
        this.userImage.imageProperty().set(image);
    }
    
    private void setHeader(){
        this.headerLabel.setText(this.currentUser.getName() +"  @" +this.currentUser.getHandle() +" "+ this.currentUser.getPostedDate());
    }
    
    private void setText(){
        this.tweetText.setText(this.currentUser.getText());
        this.tweetText.setWrappingWidth(500);
    }
    
    private void setTweetArea(String action){
        this.tweetViewController.actionOnTweet(this.currentUser,action);
        this.lowerBorderPane.setCenter(this.tweetView);
    }
    
    private void initializeTweetView(){
        try {
            FXMLLoader tweetFxml = new FXMLLoader(getClass().getResource("/fxml/TweetView.fxml"));
            tweetFxml.setResources(ResourceBundle.getBundle("MessagesBundle"));
            this.tweetView = (BorderPane) tweetFxml.load();
            this.tweetViewController= tweetFxml.getController();
        } 
        catch (IOException ex) {
            java.util.logging.Logger.getLogger(UserProfileViewController.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
    
    public Stage loadRetweetView(TwitterStatusInfo info) throws IOException{
        Stage popUpRetweet = new Stage();
        popUpRetweet.initModality(Modality.APPLICATION_MODAL);
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/RetweetView.fxml"));
        loader.setResources(ResourceBundle.getBundle("MessagesBundle"));
        
        BorderPane reTweet = (BorderPane)loader.load();
        retweetViewController = loader.getController();
        
        retweetViewController.setUpUser(info);
        
        retweetViewController.setUpView("Retweet");
        //popUpRetweet.setTitle("Retweet " + this.currentUser.getName()+"'s Tweet!");
        Scene scene = new Scene(reTweet);
        
        popUpRetweet.getIcons().add(new Image("/images/Twitter_Logo_Blue.png"));
        
        popUpRetweet.setScene(scene);
        return popUpRetweet;
    }
    
    public Stage loadReplyView(TwitterStatusInfo info) throws IOException{
        Stage popUpReply = loadRetweetView(info);
        popUpReply.initModality(Modality.APPLICATION_MODAL);
        
        retweetViewController.setUpView("Reply");
        popUpReply.setTitle("Replying to "+info.getName());
        
        return popUpReply;
    }
}
