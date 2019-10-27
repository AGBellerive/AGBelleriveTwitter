/**
 * Sample Skeleton for 'SearchView.fxml' Controller Class
 */

package com.agbellerive.twitter.controller;

import com.agbellerive.twitter.business.TwitterEngine;
import com.agbellerive.twitter.business.TwitterInfoCell;
import com.agbellerive.twitter.business.TwitterStatusInfo;
import com.agbellerive.twitter.business.TwitterTimelineTask;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class SearchViewController {
    
    private TwitterTimelineTask timeLineTask;
    private final static Logger LOG = LoggerFactory.getLogger(SearchViewController.class);
    private final static TwitterEngine engine = new TwitterEngine();
    
    private final Twitter twitter = engine.getTwitterinstance();

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;
    
    @FXML // fx:id="mainPane"
    private BorderPane mainPane; // Value injected by FXMLLoader
    @FXML // fx:id="searchBox"
    private TextField searchBox; // Value injected by FXMLLoader

    @FXML // fx:id="searchBtn"
    private Button searchBtn; // Value injected by FXMLLoader

    @FXML // fx:id="tweetDisplayArea"
    private ListView<TwitterStatusInfo> tweetDisplayArea; // Value injected by FXMLLoader

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert searchBox != null : "fx:id=\"searchBox\" was not injected: check your FXML file 'SearchView.fxml'.";
        assert searchBtn != null : "fx:id=\"searchBtn\" was not injected: check your FXML file 'SearchView.fxml'.";
        assert tweetDisplayArea != null : "fx:id=\"tweetDisplayArea\" was not injected: check your FXML file 'SearchView.fxml'.";
        
        mainPane.setCenter(getHBoxView());
    }
    @FXML
    private void searchBtnClick(ActionEvent event) throws TwitterException {
        tweetDisplayArea.getItems().clear();
        if (timeLineTask == null) {
            timeLineTask = new TwitterTimelineTask(tweetDisplayArea.getItems());
        }
        try {
            timeLineTask.fillSearchResult(searchBox.getText());
            LOG.info("Search Results Obtained");
            
        } catch (TwitterException ex) {
            LOG.error("Unable to display Search", ex);
        }

    }
    
    private Node getHBoxView() {
        HBox hBox = new HBox();
        
        ObservableList<TwitterStatusInfo> list = FXCollections.observableArrayList();
        tweetDisplayArea.setItems(list);
        tweetDisplayArea.setPrefWidth(800);
        tweetDisplayArea.setCellFactory(p -> new TwitterInfoCell());
        hBox.getChildren().addAll(tweetDisplayArea);
        return hBox;
    }
}
