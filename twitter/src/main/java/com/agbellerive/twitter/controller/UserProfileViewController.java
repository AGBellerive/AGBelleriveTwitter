/**
 * Sample Skeleton for 'UserProfileView.fxml' Controller Class
 */
package com.agbellerive.twitter.controller;

import com.agbellerive.twitter.business.TwitterEngine;
import com.agbellerive.twitter.business.TwitterInfoInterface;
import com.agbellerive.twitter.business.TwitterStatusInfo;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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

    private TwitterInfoInterface currentUser;

    private DmViewController dmViewController;
    private BorderPane dmView;

    private final static TwitterEngine engine = new TwitterEngine();
    private final Twitter twitter = engine.getTwitterinstance();
    ;
    
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

    /**
     * This method sets up the view for the user youre looking at
     *
     * @throws TwitterException
     */
    public void setUpView() throws TwitterException {
        initilizeFollowBtnStatus();
        initializeDmView();
        setProfilePicture();
        setDescription();
        setHandle();
        setCount();
    }

    /**
     * This method sets up the user
     *
     * @param info
     */
    public void setUpUser(TwitterInfoInterface info) {
        this.currentUser = info;
    }

    /**
     * This method is called when the user clicks on the follow button following
     * or unfollowing that specific user
     *
     * @param event
     * @throws TwitterException
     */
    @FXML
    private void followClick(ActionEvent event) throws TwitterException {
        if (this.followBtn.getText().equals("Follow")) {
            this.twitter.createFriendship(this.currentUser.getScreenName());
            this.followBtn.setText("Unfollow");
            LOG.info("You Followed " + this.currentUser.getScreenName());
        } else {
            this.twitter.destroyFriendship(this.currentUser.getScreenName());
            this.followBtn.setText("Follow");
            LOG.info("You Unfollowed " + this.currentUser.getScreenName());
        }
    }

    /**
     * This method is called when the user clicks on message
     *
     * @param event
     */
    @FXML
    private void messageClick(ActionEvent event) {
        this.dmViewController.presetDmName(this.currentUser.getScreenName());
        this.displayPane.setCenter(this.dmView);
        LOG.info("Displaying Dm view");
    }

    /**
     * This method sets up the users profile
     */
    private void setProfilePicture() {
        Image image = new Image(this.currentUser.getLargeProfileImageURL());
        this.profileImageView.imageProperty().set(image);
    }

    /**
     * This method sets up the users description
     */
    private void setDescription() {
        this.description.setText(this.currentUser.getDescription());
    }

    /**
     * This method sets the users handle
     */
    private void setHandle() {
        this.name.setText(this.currentUser.getName());
        this.userName.setText("@" + this.currentUser.getScreenName());
    }

    /**
     * This method sets up the counts of the follower and followed
     */
    private void setCount() {
        this.followers.setText(this.currentUser.getFollowersCount() + " " + this.followers.getText());
        this.following.setText(this.currentUser.getFriendsCount() + " " + this.following.getText());
    }

    /**
     * This method initilizes the button and checks if the user is following the
     * user being looked at, if so it says unfollow, if they are not follwoing
     * it says follow
     *
     * @throws TwitterException
     */
    private void initilizeFollowBtnStatus() throws TwitterException {
        User authenticated = twitter.showUser(twitter.getId());
        Relationship relation = twitter.showFriendship(authenticated.getScreenName(), this.currentUser.getScreenName());
        // relation.isSourceFollowedByTarget();
        if (relation.isSourceFollowingTarget()) {
            followBtn.setText("Unfollow");
        }
    }

    /**
     * This method initilizes the dm view
     */
    private void initializeDmView() {
        try {
            FXMLLoader dmFxml = new FXMLLoader(getClass().getResource("/fxml/DmView.fxml"));
            dmFxml.setResources(ResourceBundle.getBundle("MessagesBundle"));
            this.dmView = (BorderPane) dmFxml.load();
            this.dmViewController = dmFxml.getController();
        } catch (IOException ex) {
            LOG.error("Dm View could not be initilized");
        }
    }

    /**
     * This method sets up the pop up for loading the specific users profile
     *
     * @param info
     * @return stage
     * @throws IOException
     * @throws TwitterException
     */
    public Stage loadUsersProfile(TwitterInfoInterface info) throws IOException, TwitterException {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/UserProfileView.fxml"));
        loader.setResources(ResourceBundle.getBundle("MessagesBundle"));

        BorderPane userProfile = (BorderPane) loader.load();
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
