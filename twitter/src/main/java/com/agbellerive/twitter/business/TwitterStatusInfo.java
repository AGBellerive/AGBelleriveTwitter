package com.agbellerive.twitter.business;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.Status;
import twitter4j.Twitter;

/**
 * Twitter information for ListCell This class defines which members of the
 * Status object you wish to display.
 *
 * This is based on the following
 * https://github.com/tomoTaka01/TwitterListViewSample.git
 *
 * @author tomo
 */
public class TwitterStatusInfo implements TwitterInfoInterface {

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
     *
     * @return String
     */
    @Override
    public String getName() {
        return this.status.getUser().getName();
    }

    /**
     * This method returns a string of the users screen name
     *
     * @UserName
     * @return String
     */
    @Override
    public String getScreenName() {
        return this.status.getUser().getScreenName();
    }

    /**
     * This method returns a string of the description of the user
     *
     * @return String
     */
    @Override
    public String getDescription() {
        return this.status.getUser().getDescription();
    }

    /**
     * This method returns the follower count of the specific user
     *
     * @return int
     */
    @Override
    public int getFollowersCount() {
        return this.status.getUser().getFollowersCount();
    }

    /**
     * This method returns the followed account of the specific user
     *
     * @return int
     */
    @Override
    public int getFriendsCount() {
        return this.status.getUser().getFriendsCount();
    }

    /**
     * This method returns the tweet of the user
     *
     * @return
     */
    @Override
    public String getText() {
        return this.status.getText();
    }

    /**
     * This method returns the image url for the user
     *
     * @return String
     */
    @Override
    public String getImageURL() {
        return this.status.getUser().getProfileImageURL();
    }

    /**
     * This method returns the image url for the user, but this is a higher
     * density image
     *
     * @return String
     */
    @Override
    public String getLargeProfileImageURL() {
        return this.status.getUser().get400x400ProfileImageURL();
    }

    /**
     * This method returns a string of the users screen name
     *
     * @UserName
     * @return String
     */
    public String getHandle() {
        return this.status.getUser().getScreenName();
    }

    /**
     * This method returns a int of the like count
     *
     * @return int
     */
    @Override
    public int getLikeCount() {
        return this.status.getFavoriteCount();
    }

    /**
     * This method returns posted date
     *
     * @return int
     */
    @Override
    public String getPostedDate() {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        return df.format(this.status.getCreatedAt());
    }

    /**
     * This method returns the tweet id of the specific status
     *
     * @return
     */
    @Override
    public Long getTweetId() {
        return this.status.getId();
    }

    /**
     * This method returns a retweet count on the status
     *
     * @return int
     */
    @Override
    public int getRetweetCount() {
        return this.status.getRetweetCount();
    }
}
//http://twitter4j.org/oldjavadocs/2.2.6/twitter4j/api/UserMethods.html
