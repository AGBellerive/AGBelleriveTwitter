/**
 * Sample Skeleton for 'YourRetweetsView.fxml' Controller Class
 */

package com.agbellerive.twitter.controller;

import com.agbellerive.twitter.business.TwitterEngine;
import com.agbellerive.twitter.business.TwitterInfoCell;
import com.agbellerive.twitter.business.TwitterInfoInterface;
import com.agbellerive.twitter.business.TwitterTimelineTask;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class YourRetweetsController {
    private TwitterTimelineTask timeLineTask;
    private final static Logger LOG = LoggerFactory.getLogger(YourRetweetsController.class);
    
    private final static TwitterEngine engine = new TwitterEngine();
    private final Twitter twitter = engine.getTwitterinstance();

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;
    
    @FXML // fx:id="mainPane"
    private BorderPane mainPane; // Value injected by FXMLLoader

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="retweetsList"
    private ListView<TwitterInfoInterface> retweetsList; // Value injected by FXMLLoader

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws TwitterException {
        assert mainPane != null : "fx:id=\"mainPane\" was not injected: check your FXML file 'YourRetweetsView.fxml'.";
        assert retweetsList != null : "fx:id=\"retweetsList\" was not injected: check your FXML file 'YourRetweetsView.fxml'.";
        this.mainPane.setCenter(getHBoxView());
    }
    
    private Node getHBoxView() {
        HBox hBox = new HBox();
        
        ObservableList<TwitterInfoInterface> list = FXCollections.observableArrayList();
        this.retweetsList.setItems(list);
        this.retweetsList.setPrefWidth(1000);
        this.retweetsList.setCellFactory(p -> new TwitterInfoCell());
        hBox.getChildren().addAll(retweetsList);
        return hBox;
    }
    
    public void loadOwnRetweets() throws TwitterException {
        this.retweetsList.getItems().clear();
        if (this.timeLineTask == null) {
            this.timeLineTask = new TwitterTimelineTask(this.retweetsList.getItems());
        }
        this.timeLineTask.fillOwnRetweets();
    }
    
    public void loadTweetsRetweeted() throws TwitterException{
        this.retweetsList.getItems().clear();
        if (this.timeLineTask == null) {
            this.timeLineTask = new TwitterTimelineTask(this.retweetsList.getItems());
        }
        this.timeLineTask.fillTweetsRetweeted();
    }
}
