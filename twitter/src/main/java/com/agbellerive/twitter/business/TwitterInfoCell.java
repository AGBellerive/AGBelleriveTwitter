package com.agbellerive.twitter.business;

import com.agbellerive.twitter.controller.RetweetViewController;
import com.agbellerive.twitter.controller.TweetViewController;
import com.agbellerive.twitter.controller.UserProfileViewController;
import com.agbellerive.twitter.persistence.TwitterDAOImpl;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.TwitterException;

/**
 * ListCell for TwitterInfo This class represents the contents of an HBox that
 * contains tweet info
 *
 * This is based on the following
 * https://github.com/tomoTaka01/TwitterListViewSample.git
 *
 * @author tomo
 * @author Ken Fogel
 */
public class TwitterInfoCell extends ListCell<TwitterInfoInterface> {

    private final static Logger LOG = LoggerFactory.getLogger(TwitterInfoCell.class);
    private final static TwitterEngine engine = new TwitterEngine();

    private TweetViewController tweetViewController;
    private UserProfileViewController userProvileViewController;
    private RetweetViewController retweetViewController;
    private ResourceBundle resource;

    private Stage popUpUser;
    private Stage popUpRetweet;
    private Stage popUpReply;

    /**
     * This method is called when ever cells need to be updated
     *
     * @param item
     * @param empty
     */
    //http://twitter4j.org/en/code-examples.html

    @Override
    protected void updateItem(TwitterInfoInterface item, boolean empty) {
        super.updateItem(item, empty);

        LOG.debug("updateItem");

        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            try {
                setGraphic(getTwitterInfoCell(item));
            } catch (IOException | TwitterException ex) {
                java.util.logging.Logger.getLogger(TwitterInfoCell.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * This method determines what the cell will look like. This sets the image
     * for the user and initilizes all the needed controllers for the popup
     * windows
     *
     * @param info
     * @return The node to be placed into the ListView
     */
    private Node getTwitterInfoCell(TwitterInfoInterface info) throws IOException, TwitterException {
        controllerLoader();
        this.popUpUser = userProvileViewController.loadUsersProfile(info);
        this.popUpRetweet = retweetViewController.loadRetweetView(info);
        this.popUpReply = retweetViewController.loadReplyView(info);

        HBox hBox = new HBox();
        hBox.setSpacing(10);

        HBox actions = new HBox();
        actions.setSpacing(10);

        Button userImage = new Button();
        userImage.setStyle("-fx-background-image: url(" + info.getImageURL() + ");");
        userImage.setPrefSize(48, 48);
        userImage.setTooltip(new Tooltip(info.getScreenName()));

        Label header = new Label(info.getName() + "  @" + info.getScreenName() + " " + info.getPostedDate());
        Text text = new Text(info.getText());
        text.setFill(Color.WHITE);

        text.setWrappingWidth(600);

        Button reTweet = new Button("" + info.getRetweetCount());
        reTweet.setId("reTweetBtn");
        reTweet.setPrefSize(42, 42);
        reTweet.setTooltip(new Tooltip(resource.getString("retweetToolTip")));

        Button reply = new Button();
        reply.setPrefSize(42, 42);
        reply.setId("replyBtn");
        reply.setTooltip(new Tooltip(resource.getString("replyToolTip")));

        Button like = new Button("" + info.getLikeCount());
        like.setPrefSize(42, 42);
        like.setId("likeBtn");
        like.setTooltip(new Tooltip(resource.getString("likeToolTip")));

        Button save = new Button();
        save.setPrefSize(21, 21);
        save.setId("saveBtn");
        save.setTooltip(new Tooltip(resource.getString("saveToolTip")));

        try {
            listnerSetUp(info, reTweet, like, userImage, reply, save);
        } catch (TwitterException ex) {
            LOG.warn("An Error occured " + ex);
        }

        if (info instanceof TwitterInfoNoStatus) {
            //If the Tweet is a TwitterInfoNoStatus then 
            //you wouldnt need to save it again
            save.setId("saveBtnInDb");
            save.setDisable(true);
        }

        actions.getChildren().addAll(reply, reTweet, like);

        VBox vbox = new VBox();
        vbox.getChildren().addAll(header, text, actions);
        hBox.getChildren().addAll(userImage, vbox, save);

        return hBox;
    }

    /**
     * This method attaches all the required listners for the buttons the user
     * can interact with
     *
     * @param info
     * @param reTweet
     * @param like
     * @param userImage
     * @param reply
     * @throws IOException
     * @throws TwitterException
     */
    private void listnerSetUp(TwitterInfoInterface info, Button reTweet, Button like, Button userImage, Button reply, Button save) throws IOException, TwitterException {
        reTweet.setOnAction(event -> {
            displayRetweet();
        });

        like.setOnAction(event -> {
            try {
                this.engine.likeTweet(info.getTweetId());
            } catch (TwitterException ex) {
                LOG.info("Tweet Was Already liked");
            }
        });

        reply.setOnAction(event -> {
            displayReply();
        });

        userImage.setOnAction(event -> {
            displayUser();
        });

        save.setOnAction(event -> {
            TwitterDAOImpl dao = new TwitterDAOImpl();
            try {
                dao.create((TwitterStatusInfo) info);
                save.setStyle("-fx-border-color:#1da1f2;");
            } catch (SQLException ex) {
                LOG.warn("Could not save tweet " + ex);
            }
        });
    }

    /**
     * This method displays the pop up window to show the user profile
     */
    public void displayUser() {
        this.popUpUser.showAndWait();
    }

    /**
     * This method displays the pop up window to show the retweet window
     */
    public void displayRetweet() {
        this.popUpRetweet.showAndWait();
    }

    /**
     * This method displays the pop up window to show the reply window
     */
    public void displayReply() {
        this.popUpReply.showAndWait();
    }

    /**
     * This method loads all the controllers that are needed to properly use the
     * pop up windows
     *
     * @throws IOException
     */

    private void controllerLoader() throws IOException {
        FXMLLoader retweetFXML = new FXMLLoader(getClass().getResource("/fxml/RetweetView.fxml"));
        retweetFXML.setResources(ResourceBundle.getBundle("MessagesBundle"));

        this.resource = ResourceBundle.getBundle("MessagesBundle");

        BorderPane reTweetView = (BorderPane) retweetFXML.load();
        this.retweetViewController = retweetFXML.getController();

        FXMLLoader userFXML = new FXMLLoader(getClass().getResource("/fxml/UserProfileView.fxml"));
        userFXML.setResources(ResourceBundle.getBundle("MessagesBundle"));

        BorderPane userProfile = (BorderPane) userFXML.load();
        this.userProvileViewController = userFXML.getController();
    }

}
