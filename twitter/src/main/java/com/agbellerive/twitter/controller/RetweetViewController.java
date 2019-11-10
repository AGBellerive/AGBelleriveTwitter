/**
 * Sample Skeleton for 'RetweetView.fxml' Controller Class
 */

package com.agbellerive.twitter.controller;

import com.agbellerive.twitter.business.TwitterEngine;
import com.agbellerive.twitter.business.TwitterInfoInterface;
import com.agbellerive.twitter.business.TwitterStatusInfo;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.LoggerFactory;
import twitter4j.TwitterException;

public class RetweetViewController {
    
    private RetweetViewController retweetViewController;
    
    private TwitterInfoInterface currentUser;
    
    private TweetViewController tweetViewController;
    private BorderPane tweetView;
    
    private final static TwitterEngine engine = new TwitterEngine();
    
    
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
    private void initialize() {
        assert userImage != null : "fx:id=\"userImage\" was not injected: check your FXML file 'RetweetView.fxml'.";
        assert headerLabel != null : "fx:id=\"headerLabel\" was not injected: check your FXML file 'RetweetView.fxml'.";
        assert tweetText != null : "fx:id=\"tweetText\" was not injected: check your FXML file 'RetweetView.fxml'.";
        assert likeBtn != null : "fx:id=\"likeBtn\" was not injected: check your FXML file 'RetweetView.fxml'.";

    }
    /**
     * This method gets launched when the user clicks the like button
     * @param event 
     */
    @FXML
    private void likeBtnClick(ActionEvent event) {
        try {
            this.engine.likeTweet(this.currentUser.getTweetId());
            LOG.info("Tweet Liked");
        } 
        catch (TwitterException ex) {
            LOG.info("Tweet Wasnt Liked");
        }
    }
    
    /**
     * This method sets up the view of the pop up
     * @param action 
     */
    public void setUpView(String action){
        setProfilePicture();
        setHeader();
        setText();
        initializeTweetView();
        setTweetArea(action);
    }
    
    /**
     * This method sets up the private variable of the current user
     * @param user 
     */
    public void setUpUser(TwitterInfoInterface user){
        this.currentUser = user;
    }
    
    /**
     * This method sets up the profile picture of the user
     */
    private void setProfilePicture(){
        Image image = new Image(this.currentUser.getLargeProfileImageURL());
        this.userImage.imageProperty().set(image);
    }
    /**
     * This method sets up the text next to the users name
     */
    private void setHeader(){
        this.headerLabel.setText(this.currentUser.getName() +"  @" +this.currentUser.getScreenName()+" "+ this.currentUser.getPostedDate());
    }
    /**
     * This method sets the tweet of the user going to be retweeted
     */
    private void setText(){
        this.tweetText.setText(this.currentUser.getText());
        this.tweetText.setWrappingWidth(500);
    }
    
    /**
     * This method uses the TweetView fxml and replaces strings on the screen
     * to indicate you are in the retweetview
     * @param action 
     */
    private void setTweetArea(String action){
        this.tweetViewController.actionOnTweet(this.currentUser,action);
        this.lowerBorderPane.setCenter(this.tweetView);
    }
    /**
     * This method initilizes the tweet view that is being used
     */
    private void initializeTweetView(){
        try {
            FXMLLoader tweetFxml = new FXMLLoader(getClass().getResource("/fxml/TweetView.fxml"));
            tweetFxml.setResources(ResourceBundle.getBundle("MessagesBundle"));
            this.tweetView = (BorderPane) tweetFxml.load();
            this.tweetViewController= tweetFxml.getController();
        } 
        catch (IOException ex) {
            LOG.info("IOException in initilizeTweetView");
        }   
    }
    /**
     * This method returns a Stage so it can be used as a pop up
     * @param info
     * @return
     * @throws IOException 
     */
    public Stage loadRetweetView(TwitterInfoInterface info) throws IOException{
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
    
    /**
     * This method returns the reply view changing information to indicate the 
     * user is about to reply
     * @param info
     * @return
     * @throws IOException 
     */
    public Stage loadReplyView(TwitterInfoInterface info) throws IOException{
        Stage popUpReply = loadRetweetView(info);
        popUpReply.initModality(Modality.APPLICATION_MODAL);
        
        this.retweetViewController.setUpView("Reply");
        popUpReply.setTitle("Replying to "+info.getName());
        
        return popUpReply;
    }
}
