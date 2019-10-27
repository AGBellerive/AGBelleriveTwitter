package com.agbellerive.twitter.business;

import com.agbellerive.twitter.controller.RetweetViewController;
import com.agbellerive.twitter.controller.TweetViewController;
import com.agbellerive.twitter.controller.UserProfileViewController;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
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
public class TwitterInfoCell extends ListCell<TwitterStatusInfo> {

    // Real programmers use logging, not System.out.println
    private final static Logger LOG = LoggerFactory.getLogger(TwitterInfoCell.class);
    private TweetViewController tweetViewController;
    
    private UserProfileViewController userProvileViewController;
    private RetweetViewController retweetViewController;
    
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
    protected void updateItem(TwitterStatusInfo item, boolean empty) {
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
     * This method determines what the cell will look like. Here is where you
     * can add buttons or any additional information
     *
     * @param info
     * @return The node to be placed into the ListView
     */
    private Node getTwitterInfoCell(TwitterStatusInfo info) throws IOException, TwitterException {
        controllerLoader();
        this.popUpUser = userProvileViewController.loadUsersProfile(info);
        this.popUpRetweet = retweetViewController.loadRetweetView(info);
        this.popUpReply = retweetViewController.loadReplyView(info);
        
        
        HBox hBox = new HBox();
        hBox.setSpacing(10);
        
        HBox actions = new HBox();
        actions.setSpacing(10);
        
        
        Button userImage = new Button();
        userImage.setStyle("-fx-background-image: url("+info.getImageURL()+");");
        userImage.setPrefSize(42, 42);
        
        Label header = new Label(info.getName() +"  @" +info.getHandle() +" "+ info.getPostedDate());
        Text text = new Text(info.getText());
        text.setFill(Color.WHITE);
        
        text.setWrappingWidth(600);
        
        Button reTweet = new Button("Retweet " + info.getRetweetCount());
        Button reply = new Button("Reply");
        Button like = new Button("Like "+info.getLikeCount());
        
        try {
            listnerSetUp(info,reTweet,like,userImage,reply);
        } catch (TwitterException ex) {
            java.util.logging.Logger.getLogger(TwitterInfoCell.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        actions.getChildren().addAll(reply,reTweet,like);
        
        VBox vbox = new VBox();
        vbox.getChildren().addAll(header, text,actions);
        hBox.getChildren().addAll(userImage, vbox);
        
        return hBox;
    }
    
    private void listnerSetUp(TwitterStatusInfo info,Button reTweet,Button like,Button userImage,Button reply)throws IOException, TwitterException{
        reTweet.setOnAction(event ->{
                displayRetweet();
        });
        
       like.setOnAction(event->{
            try {
                info.likeTweet();
                if(info.isFavorited()){
                    like.setDisable(true);
                }
            } catch (TwitterException ex) {
                LOG.info("Tweet Was Already liked");
            }
        });
       
       reply.setOnAction(event -> {
                displayReply();
       });
       
       userImage.setOnAction(event-> {
            displayUser();
        });
        
    }
    
    public void displayUser(){
        this.popUpUser.showAndWait();
    }
    
    public void displayRetweet(){
        //retweetViewController.setRetweetText();
        popUpRetweet.showAndWait();
    }
    
    public void displayReply(){
        
        popUpReply.showAndWait();
    }
    
    private void controllerLoader() throws IOException{
        FXMLLoader retweetFXML = new FXMLLoader(getClass().getResource("/fxml/RetweetView.fxml"));
        retweetFXML.setResources(ResourceBundle.getBundle("MessagesBundle"));
        
        BorderPane reTweetView = (BorderPane)retweetFXML.load();
        retweetViewController = retweetFXML.getController();
        
        FXMLLoader userFXML = new FXMLLoader(getClass().getResource("/fxml/UserProfileView.fxml"));
        userFXML.setResources(ResourceBundle.getBundle("MessagesBundle"));
        
        BorderPane userProfile = (BorderPane)userFXML.load();
        this.userProvileViewController = userFXML.getController();
        
        
    }
    
}