/**
 * Sample Skeleton for 'MentionedView.fxml' Controller Class
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
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class MentionedViewController {

    private TwitterTimelineTask timeLineTask;
    private final static Logger LOG = LoggerFactory.getLogger(MentionedViewController.class);

    private final static TwitterEngine engine = new TwitterEngine();
    private final Twitter twitter = engine.getTwitterinstance();

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="mainPane"
    private BorderPane mainPane; // Value injected by FXMLLoader

    @FXML // fx:id="mentionedList"
    private ListView<TwitterInfoInterface> mentionedList; // Value injected by FXMLLoader

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws TwitterException {
        assert mainPane != null : "fx:id=\"mainPane\" was not injected: check your FXML file 'MentionedView.fxml'.";
        assert mentionedList != null : "fx:id=\"mentionedList\" was not injected: check your FXML file 'MentionedView.fxml'.";
        this.mainPane.setCenter(getHBoxView());
    }

    /**
     * This method creates the Hbox view for all the tweets
     *
     * @return Node
     */
    private Node getHBoxView() {
        HBox hBox = new HBox();

        ObservableList<TwitterInfoInterface> list = FXCollections.observableArrayList();
        this.mentionedList.setItems(list);
        this.mentionedList.setPrefWidth(1000);
        this.mentionedList.setCellFactory(p -> new TwitterInfoCell());
        hBox.getChildren().addAll(mentionedList);
        return hBox;
    }

    /**
     * This method loads all the mentions of the specific users handle
     *
     * @param user
     * @throws TwitterException
     */
    public void loadSelfMentionedList(String user) throws TwitterException {
        this.mentionedList.getItems().clear();
        if (this.timeLineTask == null) {
            this.timeLineTask = new TwitterTimelineTask(this.mentionedList.getItems());
        }
        try {
            this.timeLineTask.fillSearchResult("@" + user);

        } catch (TwitterException ex) {
            LOG.error("Unable to display Your own retweets", ex);
        }
    }

    public void loadTweetsRetweeted() throws TwitterException {
        this.mentionedList.getItems().clear();
        if (this.timeLineTask == null) {
            this.timeLineTask = new TwitterTimelineTask(this.mentionedList.getItems());
        }
        this.timeLineTask.fillTweetsRetweeted();
    }

}
