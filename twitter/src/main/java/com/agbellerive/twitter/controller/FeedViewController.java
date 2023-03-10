/**
 * Sample Skeleton for 'FeedView.fxml' Controller Class
 */
package com.agbellerive.twitter.controller;

import com.agbellerive.twitter.business.TwitterInfoCell;
import com.agbellerive.twitter.business.TwitterInfoInterface;
import com.agbellerive.twitter.business.TwitterTimelineTask;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FeedViewController {

    private final static Logger LOG = LoggerFactory.getLogger(FeedViewController.class);
    private TwitterTimelineTask timeLineTask;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="homePane"
    private BorderPane homePane; // Value injected by FXMLLoader

    @FXML // fx:id="tweetList"
    private ListView<TwitterInfoInterface> tweetList; // Value injected by FXMLLoader

    @FXML // fx:id="refreshBtn"
    private Button refreshBtn; // Value injected by FXMLLoader

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert homePane != null : "fx:id=\"homePane\" was not injected: check your FXML file 'FeedView.fxml'.";
        assert tweetList != null : "fx:id=\"tweetList\" was not injected: check your FXML file 'FeedView.fxml'.";
        assert refreshBtn != null : "fx:id=\"refreshBtn\" was not injected: check your FXML file 'FeedView.fxml'.";

        this.homePane.setCenter(getHBoxView());
        loadTweets();
    }

    /**
     * This method gets called when the user clicks on the refresh button
     *
     * @param event
     */
    @FXML
    private void refreshClick(ActionEvent event) {
        loadTweets();
        LOG.info("Refresh button clicked");
    }

    /**
     * This method is called when the view is loaded and it displays all the
     * tweets and when this is called it removes the old tweets being displayed
     */
    private void loadTweets() {
        this.tweetList.getItems().clear();
        if (this.timeLineTask == null) {
            this.timeLineTask = new TwitterTimelineTask(this.tweetList.getItems());
        }
        try {
            this.timeLineTask.fillTimeLine();
        } catch (Exception ex) {
            LOG.error("Unable to display timeline", ex);
        }
    }

    /**
     * This method creates the Hbox view for all the tweets
     *
     * @return Node
     */
    private Node getHBoxView() {
        HBox hBox = new HBox();

        ObservableList<TwitterInfoInterface> list = FXCollections.observableArrayList();
        this.tweetList.setItems(list);
        this.tweetList.setPrefWidth(800);
        this.tweetList.setCellFactory(p -> new TwitterInfoCell());
        hBox.getChildren().addAll(this.tweetList);

        return hBox;
    }
}
