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
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
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
    }

    /**
     * This method loads all the tweets from the database and if it is empty it
     * will display a pop up so the user knows that they have no tweets saved in
     * the database
     */
    public void loadDbTweets() {
        TwitterDAOImpl twitterdao = new TwitterDAOImpl();
        this.dbTweets = twitterdao.findAll();
        LOG.info("In the database there is " + dbTweets.size() + " saved tweets");
        if (this.dbTweets.isEmpty()) {
            noTweetsSavedPopUp();
        }
        loadTweets();
    }

    /**
     * This pop up is inililized when there is no tweets retrived from the
     * datbase
     */
    private void noTweetsSavedPopUp() {
        Alert warning = new Alert(Alert.AlertType.WARNING);
        warning.getDialogPane().setMinWidth(500);
        warning.setTitle("Error");

        warning.setHeaderText("Cannot complete your request");
        warning.setContentText("You have not saved any Tweets into the database");
        warning.showAndWait();
    }

    private Node getHBoxView() {
        HBox hBox = new HBox();

        ObservableList<TwitterInfoInterface> list = FXCollections.observableArrayList();
        this.dbTweetList.setItems(list);
        this.dbTweetList.setPrefWidth(800);
        this.dbTweetList.setCellFactory(p -> new TwitterInfoCell());
        hBox.getChildren().addAll(this.dbTweetList);
        return hBox;
    }

    private void loadTweets() {
        this.dbTweetList.getItems().clear();
        if (this.timeLineTask == null) {
            this.timeLineTask = new TwitterTimelineTask(this.dbTweetList.getItems());
            LOG.info("Tweets have been loaded");
        }
        try {
            this.timeLineTask.fillDatabaseTweets();
        } catch (Exception ex) {
            LOG.error("Unable to display timeline", ex);
        }
    }
}
