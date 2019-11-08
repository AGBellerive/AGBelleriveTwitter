package com.agbellerive.twitter.controller;
/**
 * Sample Skeleton for 'MainTwitterView.fxml' Controller Class
 */
import com.agbellerive.twitter.business.TwitterEngine;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.AccountSettings;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

public class MainTwitterViewController {
    
    private TweetViewController tweetViewController;
    private BorderPane tweetView;
    
    private DmViewController dmViewController;
    private BorderPane dmView;
    
    private FeedViewController feedViewController;
    private BorderPane feedView;
    
    private ProfileViewController profileViewController;
    private BorderPane profileView;
    
    private SearchViewController searchViewController;
    private BorderPane searchView;
    
    
    private final String BUTTON_BACKGROUND_COLOR = "#15202b";
    private final String BUTTON_OUTLINE_COLOR = "#1da1f2";
    
    private final static TwitterEngine engine = new TwitterEngine();
    private final Twitter twitter = engine.getTwitterinstance();
    
    private final static Logger LOG = LoggerFactory.getLogger(MainTwitterViewController.class);
    
    private String url;
    

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

    @FXML // fx:id="profileBtn"
    private Button profileBtn; // Value injected by FXMLLoader
    
    @FXML // fx:id="helpBtn"
    private Button helpBtn; // Value injected by FXMLLoader

    @FXML // fx:id="searchBtn"
    private Button searchBtn; // Value injected by FXMLLoader

    @FXML // fx:id="exitBtn"
    private Button exitBtn; // Value injected by FXMLLoader

    @FXML // fx:id="helpPane"
    private BorderPane helpPane; // Value injected by FXMLLoader

    @FXML 
    /**
     * This method is called by the FXMLLoader when initialization is complete 
     * and it sets the initial visibility of the panes
     */
    void initialize() throws TwitterException {
        assert mainPane != null : "fx:id=\"mainPane\" was not injected: check your FXML file 'MainTwitterView.fxml'.";
        assert buttonLayoutHbox != null : "fx:id=\"buttonLayoutHbox\" was not injected: check your FXML file 'MainTwitterView.fxml'.";
        assert homeIconBtn != null : "fx:id=\"homeBtn\" was not injected: check your FXML file 'MainTwitterView.fxml'.";
        assert tweetIconBtn != null : "fx:id=\"tweetBtn\" was not injected: check your FXML file 'MainTwitterView.fxml'.";
        assert dmIconBtn != null : "fx:id=\"dmBtn\" was not injected: check your FXML file 'MainTwitterView.fxml'.";
        assert helpBtn != null : "fx:id=\"helpBtn\" was not injected: check your FXML file 'MainTwitterView.fxml'.";
        
        createTwitterView();
        createDmView();
        createFeedView();
        createProfileView();
        createSearchView();
        
        this.mainPane.setCenter(this.feedView);
        LOG.info("Class fully initialized");
    }
    /**
     * In the context of the dm icon is clicked 
     * I decided to place that current pane in the center
     * @param event 
     */
    @FXML
    private void dmIconBtnClick(ActionEvent event) {
       this.mainPane.setCenter(this.dmView);
       
       dmIconBtn.setStyle("-fx-border-color:"+BUTTON_OUTLINE_COLOR+";");
       homeIconBtn.setStyle("-fx-border-color:"+BUTTON_BACKGROUND_COLOR+";");
       tweetIconBtn.setStyle("-fx-border-color:"+BUTTON_BACKGROUND_COLOR+";");
       helpBtn.setStyle("-fx-border-color:"+BUTTON_BACKGROUND_COLOR+";");
       profileBtn.setStyle("-fx-border-color:"+BUTTON_BACKGROUND_COLOR+";-fx-background-image: url("+url+");");
       searchBtn.setStyle("-fx-border-color:"+BUTTON_BACKGROUND_COLOR+";");
       
       LOG.info("DmIcon Clicked");
    }
    /**
     * In the context of the home icon is clicked 
     * I decided to place that current pane in the center
     * @param event 
     */
    @FXML
    private void homeIconBtnClick(ActionEvent event) {
        this.mainPane.setCenter(this.feedView);
        
        dmIconBtn.setStyle("-fx-border-color:"+BUTTON_BACKGROUND_COLOR+";");
        homeIconBtn.setStyle("-fx-border-color:"+BUTTON_OUTLINE_COLOR+";");
        tweetIconBtn.setStyle("-fx-border-color:"+BUTTON_BACKGROUND_COLOR+";");
        helpBtn.setStyle("-fx-border-color:"+BUTTON_BACKGROUND_COLOR+";");
        profileBtn.setStyle("-fx-border-color:"+BUTTON_BACKGROUND_COLOR+";-fx-background-image: url("+url+");");
        searchBtn.setStyle("-fx-border-color:"+BUTTON_BACKGROUND_COLOR+";");
        
        LOG.info("HomeIcon Clicked");
    }
    
    /**
     * In the context of the profile icon is clicked 
     * I decided to place that current pane in the center
     * @param event 
     */
    
    @FXML
    private void profileBtnClick(ActionEvent event) {
        this.mainPane.setCenter(this.profileView);
        
        dmIconBtn.setStyle("-fx-border-color:"+BUTTON_BACKGROUND_COLOR+";");
        homeIconBtn.setStyle("-fx-border-color:"+BUTTON_BACKGROUND_COLOR+";");
        tweetIconBtn.setStyle("-fx-border-color:"+BUTTON_BACKGROUND_COLOR+";");
        helpBtn.setStyle("-fx-border-color:"+BUTTON_BACKGROUND_COLOR+";");
        profileBtn.setStyle("-fx-border-color:"+BUTTON_OUTLINE_COLOR+";-fx-background-image: url("+url+");");
        searchBtn.setStyle("-fx-border-color:"+BUTTON_BACKGROUND_COLOR+";");
        
        LOG.info("ProfileIcon Cliked");
    }
    
    
    /**
     * In the context of the tweet icon is clicked 
     * I decided to place that current pane in the center
     * @param event 
     */
    @FXML
    private void tweetIconBtnClick(ActionEvent event) {       
       this.mainPane.setCenter(this.tweetView);
       
       dmIconBtn.setStyle("-fx-border-color:"+BUTTON_BACKGROUND_COLOR+";");
       homeIconBtn.setStyle("-fx-border-color:"+BUTTON_BACKGROUND_COLOR+";");
       tweetIconBtn.setStyle("-fx-border-color:"+BUTTON_OUTLINE_COLOR+";");
       helpBtn.setStyle("-fx-border-color:"+BUTTON_BACKGROUND_COLOR+";");
       profileBtn.setStyle("-fx-border-color:"+BUTTON_BACKGROUND_COLOR+";-fx-background-image: url("+url+");");
       searchBtn.setStyle("-fx-border-color:"+BUTTON_BACKGROUND_COLOR+";");
       
       LOG.info("TweetIcon Clicked");
    }
        /**
     * In the context of the search icon is clicked 
     * I decided to place that current pane in the center
     * @param event 
     */
    @FXML
    private void searchClick(ActionEvent event) {
        
        this.mainPane.setCenter(this.searchView);
        
        dmIconBtn.setStyle("-fx-border-color:"+BUTTON_BACKGROUND_COLOR+";");
        homeIconBtn.setStyle("-fx-border-color:"+BUTTON_BACKGROUND_COLOR+";");
        tweetIconBtn.setStyle("-fx-border-color:"+BUTTON_BACKGROUND_COLOR+";");
        helpBtn.setStyle("-fx-border-color:"+BUTTON_BACKGROUND_COLOR+";");
        profileBtn.setStyle("-fx-border-color:"+BUTTON_BACKGROUND_COLOR+";-fx-background-image: url("+url+");");
        searchBtn.setStyle("-fx-border-color:"+BUTTON_OUTLINE_COLOR+";");
       
       LOG.info("SearchIcon Clicked");
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
     * I decided to place that current pane in the center
     * THIS IS NOT YET IMPLIMENED
     * @param event 
     */
    @FXML
    private void helpBtnClick(ActionEvent event) {
        
        dmIconBtn.setStyle("-fx-border-color:"+BUTTON_BACKGROUND_COLOR+";");
        homeIconBtn.setStyle("-fx-border-color:"+BUTTON_BACKGROUND_COLOR+";");
        tweetIconBtn.setStyle("-fx-border-color:"+BUTTON_BACKGROUND_COLOR+";");
        helpBtn.setStyle("-fx-border-color:"+BUTTON_OUTLINE_COLOR+";");
        profileBtn.setStyle("-fx-border-color:"+BUTTON_BACKGROUND_COLOR+";-fx-background-image: url("+url+");");
        searchBtn.setStyle("-fx-border-color:"+BUTTON_BACKGROUND_COLOR+";");
       
        LOG.info("HelpIcon Clicked");
    }
    
    public void setMainView(BorderPane mainPane){
        this.mainPane=mainPane;
    }
    /**
     * This method creates the twitter view to be used later in switching
     */
    private void createTwitterView(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TweetView.fxml"));
            loader.setResources(ResourceBundle.getBundle("MessagesBundle"));
            this.tweetView = (BorderPane) loader.load();
            this.tweetViewController = loader.getController();
            LOG.info("Twitter View sucessfully created");
        }
        catch(IOException ex){
            LOG.error("Could not load TweetView",ex);
            Platform.exit();
        }
    }

    /**
     * This method creates the dm view to be used later in switching
     */
    private void createDmView(){
       try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DmView.fxml"));
            loader.setResources(ResourceBundle.getBundle("MessagesBundle"));
            this.dmView = (BorderPane) loader.load();
            this.dmViewController = loader.getController();
            LOG.info("Dm View sucessfully created");
        }
        catch(IOException ex){
            LOG.error("Could not load DmView",ex);
            Platform.exit();
        } 
    }

    /**
     * This method creates the feed view to be used later in switching
     */
    private void createFeedView(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FeedView.fxml"));
            loader.setResources(ResourceBundle.getBundle("MessagesBundle"));
            this.feedView = (BorderPane) loader.load();
            this.feedViewController = loader.getController();
            LOG.info("Feed View sucessfully created");
        }
        catch(IOException ex){
            LOG.error("Could not load DmView",ex);
            Platform.exit();
        }  
    }
    
    /**
     * This method creates the profile view to be used later in switching
     */
    private void createProfileView() throws TwitterException{
        User loggedInUser = twitter.showUser(twitter.getId());
        url = loggedInUser.get400x400ProfileImageURL();
        this.profileBtn.setStyle("-fx-background-image: url("+url+");");
        
        try{            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ProfileView.fxml"));
            loader.setResources(ResourceBundle.getBundle("MessagesBundle"));
            this.profileView = (BorderPane) loader.load();
            this.profileViewController = loader.getController();
            
            LOG.info("Profile view Sucessfully created");
        }
        catch(IOException ex){
            LOG.error("Could not load ProfileView",ex);
        }
        this.profileViewController.setUser();
        this.profileViewController.setUpView();
        
    }
    /**
     * This method creates the search view to be used later in switching
     */
    private void createSearchView(){
        try{
            FXMLLoader searchViewFXML = new FXMLLoader (getClass().getResource("/fxml/SearchView.fxml"));
            searchViewFXML.setResources(ResourceBundle.getBundle("MessagesBundle"));
            this.searchView = (BorderPane) searchViewFXML.load();
            this.searchViewController = searchViewFXML.getController();
            
            LOG.info("Search view Sucessfully created");
        } 
        catch (IOException ex) {
            LOG.error("Could not load SearchView",ex);
        }
    }
    
    
    
}
