package com.agbellerive.twitter.business;

import com.agbellerive.twitter.controller.TweetViewController;
import com.agbellerive.twitter.controller.UserProfileViewController;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.logging.log4j.core.config.plugins.util.ResolverUtil;
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
public class TwitterInfoCell extends ListCell<TwitterStatusInfo> {

    // Real programmers use logging, not System.out.println
    private final static Logger LOG = LoggerFactory.getLogger(TwitterInfoCell.class);
    private TweetViewController tweetViewController;
    /**
     * This method is called when ever cells need to be updated
     *
     * @param item
     * @param empty
     */
    //http://twitter4j.org/en/code-examples.html
    
    @Override
    protected void updateItem(TwitterStatusInfo item, boolean empty) {
        super.updateItem(item, empty);

        LOG.debug("updateItem");

        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            setGraphic(getTwitterInfoCell(item));
        }
    }

    /**
     * This method determines what the cell will look like. Here is where you
     * can add buttons or any additional information
     *
     * @param info
     * @return The node to be placed into the ListView
     */
    private Node getTwitterInfoCell(TwitterStatusInfo info) {
        HBox hBox = new HBox();
        hBox.setSpacing(10);
        
        HBox actions = new HBox();
        actions.setSpacing(10);
        
        TwitterInfoCell tic = new TwitterInfoCell();
        
        Button userImage = new Button();
        userImage.setStyle("-fx-background-image: url("+info.getImageURL()+");");
        userImage.setPrefSize(42, 42);
        
        Label header = new Label(info.getName() +"  @" +info.getHandle() +" "+ info.getPostedDate());
        Text text = new Text(info.getText());
        text.setFill(Color.WHITE);
        
        text.setWrappingWidth(800);
        
        Button reTweet = new Button("Retweet " + info.getRetweetCount());
        Button reply = new Button("Reply");
        Button like = new Button("Like "+info.getLikeCount());
        
        listnerSetUp(info,reTweet,like,userImage);
        
        actions.getChildren().addAll(reply,reTweet,like);
        
        VBox vbox = new VBox();
        vbox.getChildren().addAll(header, text,actions);
        hBox.getChildren().addAll(userImage, vbox);
        
        return hBox;
    }
    
    private void listnerSetUp(TwitterStatusInfo info,Button reTweet,Button like,Button userImage){
        reTweet.setOnAction(event ->{
            try{
                info.reTweet();
            }
            catch(TwitterException ex){
                LOG.error("Tweet Was not able to be retweeted");
            }
        });
        
       like.setOnAction(event->{
            try {
                info.likeTweet();
            } catch (TwitterException ex) {
                LOG.info("Tweet Was Already liked");
            }
        });
       
       userImage.setOnAction(event-> {
            try {
                //tweetViewController.
                        display(info);
                LOG.info("Image Clicked");
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(TwitterInfoCell.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        });
        
    }
    //MOVE THIS METHOD SOMWEHRE ELSE
    public void display(TwitterStatusInfo info) throws IOException {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/UserProfileView.fxml"));
        loader.setResources(ResourceBundle.getBundle("MessagesBundle"));
        
        BorderPane userProfile = (BorderPane)loader.load();
        UserProfileViewController userProfileViewController = loader.getController();
        
        userProfileViewController.setUpView(info);
        
        Scene scene = new Scene(userProfile);
        
        popupStage.getIcons().add(new Image("/images/Twitter_Logo_Blue.png"));
        popupStage.setTitle("Twitter Profile Of " + info.getName());
        
        popupStage.setScene(scene);
        popupStage.showAndWait();
        
    }
}