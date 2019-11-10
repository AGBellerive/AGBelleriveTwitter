/**
 * Sample Skeleton for 'DatabaseTweets.fxml' Controller Class
 */

package com.agbellerive.twitter.controller;

import com.agbellerive.twitter.business.TwitterInfoCell;
import com.agbellerive.twitter.business.TwitterInfoInterface;
import com.agbellerive.twitter.business.TwitterInfoNoStatus;
import com.agbellerive.twitter.business.TwitterTimelineTask;
import com.agbellerive.twitter.persistence.TwitterDAOImpl;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import org.slf4j.LoggerFactory;

public class DatabaseTweetController {
    
    private final static org.slf4j.Logger LOG = LoggerFactory.getLogger(DatabaseTweetController.class);
    private TwitterTimelineTask timeLineTask;
    private List<TwitterInfoNoStatus> dbTweets;
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="dbTweetView"
    private BorderPane dbTweetView; // Value injected by FXMLLoader

    @FXML // fx:id="dbTweetList"
    private ListView<TwitterInfoInterface> dbTweetList; // Value injected by FXMLLoader
    
    

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert dbTweetView != null : "fx:id=\"dbTweetView\" was not injected: check your FXML file 'DatabaseTweets.fxml'.";
        assert dbTweetList != null : "fx:id=\"dbTweetList\" was not injected: check your FXML file 'DatabaseTweets.fxml'.";
        this.dbTweetView.setCenter(getHBoxView());
        LOG.info("DatabaseTweetController initilzied");
    }
    
    public void loadDbTweets(){
        TwitterDAOImpl twitterdao = new TwitterDAOImpl();
        dbTweets = twitterdao.findAll();
        LOG.info(dbTweets.size()+"");
        loadTweets();
    }
    
    private Node getHBoxView() {
        HBox hBox = new HBox();
        
        ObservableList<TwitterInfoInterface> list = FXCollections.observableArrayList();
        dbTweetList.setItems(list);
        dbTweetList.setPrefWidth(800);
        dbTweetList.setCellFactory(p -> new TwitterInfoCell());
        hBox.getChildren().addAll(dbTweetList);
        return hBox;
    }
    
    private void loadTweets(){
        dbTweetList.getItems().clear();
        if (timeLineTask == null) {
            timeLineTask = new TwitterTimelineTask(dbTweetList.getItems());
            LOG.info("Tweets have been loaded");
        }
        try {
            timeLineTask.fillDatabaseTweets();
        } catch (Exception ex) {
            LOG.error("Unable to display timeline", ex);
        }
    }
    
    
}
