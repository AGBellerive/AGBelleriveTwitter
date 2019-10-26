package com.agbellerive.twitter.business;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.AccountSettings;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;

/**
 * Twitter information for ListCell
 * This class defines which members of the Status object you wish to display.
 * 
 * This is based on the following
 * https://github.com/tomoTaka01/TwitterListViewSample.git
 * 
 * @author tomo
 */
public class TwitterStatusInfo {
    
    // Real programmers use logging, not System.out.println
    private final static TwitterEngine engine = new TwitterEngine();
    private final Twitter twitter;
    
    
    private final static Logger LOG = LoggerFactory.getLogger(TwitterStatusInfo.class);
    
    private final Status status;

    public TwitterStatusInfo(Status status) {
        this.status = status;
        this.twitter = engine.getTwitterinstance();
        LOG.info("TwitterStatus Instantiated");
    }

    public String getName() {
        return status.getUser().getName();
    }
    
    public String getScreenName(){
        return status.getUser().getScreenName();
    }
    
    public String getDescription(){
        return status.getUser().getDescription();
    }
    
    public int getFollowersCount(){
        return status.getUser().getFollowersCount();
    }
    
    public int getFriendsCount(){
        return status.getUser().getFriendsCount();
    }

    public String getText(){
        return status.getText();
    }

    public String getImageURL(){
        return status.getUser().getProfileImageURL();
    }
    
    public String getLargeProfileImageURL(){
        return status.getUser().get400x400ProfileImageURL();
    }
    
    public String getHandle() {
      return status.getUser().getScreenName();
    }
    
    public int getLikeCount(){
        return status.getFavoriteCount();
    }
    
    public String getPostedDate(){
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        return df.format(status.getCreatedAt());
    }
    
    //http://www.tothenew.com/blog/mark-tweet-as-favorite-using-twitter4j/
    public void likeTweet() throws TwitterException{
        twitter.createFavorite(status.getId());
    }
    
    public int getRetweetCount(){
       return status.getRetweetCount();
    }
    //http://twitter4j.org/oldjavadocs/2.2.3/twitter4j/api/StatusMethods.html
    public void reTweet() throws TwitterException{
        twitter.retweetStatus(status.getId());
    }
    public boolean isFavorited(){
        return this.status.isFavorited();
    }
    
    public void makeComment(String reply) throws TwitterException{
       StatusUpdate tweetReply = new StatusUpdate(reply);
       tweetReply.inReplyToStatusId(this.status.getId());
       
       Status statusRepliedTo = twitter.updateStatus(tweetReply);
       
        //http://www.tothenew.com/blog/reply-to-a-user-tweet-using-twitter4j/
    }
    
    public Long getTweetId(){
        return this.status.getId();
    }
    
}
//http://twitter4j.org/oldjavadocs/2.2.6/twitter4j/api/UserMethods.html