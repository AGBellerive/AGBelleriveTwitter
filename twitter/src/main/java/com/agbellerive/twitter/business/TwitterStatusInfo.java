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
    
    private final static TwitterEngine engine = new TwitterEngine();
    private final Twitter twitter;
    
    
    private final static Logger LOG = LoggerFactory.getLogger(TwitterStatusInfo.class);
    
    private final Status status;

    public TwitterStatusInfo(Status status) {
        this.status = status;
        this.twitter = engine.getTwitterinstance();
        LOG.info("TwitterStatus Instantiated");
    }
    /**
     * This method returns the string of the users name
     * @return String
     */
    public String getName() {
        return status.getUser().getName();
    }
    
    /**
     * This method returns a string of the users screen name
     * @UserName
     * @return String 
     */
    public String getScreenName(){
        return status.getUser().getScreenName();
    }
    
    /**
     * This method returns a string of the description of 
     * the user
     * @return String 
     */
    public String getDescription(){
        return status.getUser().getDescription();
    }
    
    /**
     * This method returns the follower count of the specific user
     * @return int 
     */
    public int getFollowersCount(){
        return status.getUser().getFollowersCount();
    }
    
    /**
     * This method returns the followed account of the specific user
     * @return int
     */
    public int getFriendsCount(){
        return status.getUser().getFriendsCount();
    }
    /**
     * This method returns the tweet of the user
     * @return 
     */
    public String getText(){
        return status.getText();
    }
    
    /**
     * This method returns the image url for the user
     * @return String
     */
    public String getImageURL(){
        return status.getUser().getProfileImageURL();
    }

    /**
     * This method returns the image url for the user, but this is a
     * higher density image
     * @return String
     */
    public String getLargeProfileImageURL(){
        return status.getUser().get400x400ProfileImageURL();
    }
    
    /**
     * This method returns a string of the users screen name
     * @UserName
     * @return String 
     */
    public String getHandle() {
      return status.getUser().getScreenName();
    }
    /**
     * This method returns a int of the like count
     * @return int
     */
    public int getLikeCount(){
        return status.getFavoriteCount();
    }

    /**
     * This method returns posted date 
     * @return int
     */
    public String getPostedDate(){
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        return df.format(status.getCreatedAt());
    }
    
    /**
     * This method returns the tweet id of the specific status
     * @return 
     */
    public Long getTweetId(){
        return this.status.getId();
    }
    
    /**
     * This method likes a tweet
     * inspired by:
     * http://www.tothenew.com/blog/mark-tweet-as-favorite-using-twitter4j/
     * @throws TwitterException 
     */
    public void likeTweet() throws TwitterException{
        twitter.createFavorite(status.getId());
    }
    /**
     * This method returns a retweet count on the status
     * @return int
     */    
    public int getRetweetCount(){
       return status.getRetweetCount();
    }
    /**
     * This method retweets the specified status
     * http://twitter4j.org/oldjavadocs/2.2.3/twitter4j/api/StatusMethods.html
     * @throws TwitterException 
     */
    public void reTweet() throws TwitterException{
        twitter.retweetStatus(status.getId());
    }
    
    /**
     * This method returns if the tweet is favorited already
     * @return boolean
     */
    public boolean isFavorited(){
        return this.status.isFavorited();
    }
    
    /**
     * This method creates a comment (reply) on the specific tweet
     * @param reply
     * @throws TwitterException 
     */
    public void makeComment(String reply) throws TwitterException{
       StatusUpdate tweetReply = new StatusUpdate(reply);
       tweetReply.inReplyToStatusId(this.status.getId());
       
       Status statusRepliedTo = twitter.updateStatus(tweetReply);
       
        //http://www.tothenew.com/blog/reply-to-a-user-tweet-using-twitter4j/
    }
}
//http://twitter4j.org/oldjavadocs/2.2.6/twitter4j/api/UserMethods.html