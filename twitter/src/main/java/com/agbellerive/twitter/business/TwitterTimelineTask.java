package com.agbellerive.twitter.business;

import com.agbellerive.twitter.persistence.TwitterDAOImpl;
import com.agbellerive.twitter.controller.YourRetweetsController;
import java.util.List;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.Status;
import twitter4j.TwitterException;

/**
 * Task of retrieving user's timeline
 *
 * @author tomo
 */
public class TwitterTimelineTask {

    private final static Logger LOG = LoggerFactory.getLogger(TwitterTimelineTask.class);

    private final ObservableList<TwitterInfoInterface> list;

    private final TwitterEngine twitterEngine;
    
    private YourRetweetsController yourRetweetsController = new YourRetweetsController();

    private int page;

    /**
     * Non-default constructor initializes instance variables.
     *
     * @param list
     */
    public TwitterTimelineTask(ObservableList<TwitterInfoInterface> list) {
        twitterEngine = new TwitterEngine();
        this.list = list;
        page = 1;
    }

    /**
     * Add new Status objects to the ObservableList. Additions occur at the end
     * of the list.
     *
     * @throws Exception
     */
    public void fillTimeLine() throws TwitterException {
        List<Status> homeline = twitterEngine.getTimeLine(page);
        homeline.forEach((status) -> {
            list.add(list.size(), new TwitterStatusInfo(status));
        });
        page += 1;
    }

    /**
     * Add new Status objects to the ObservableList. Additions occur at the end
     * of the list of a specific search result.
     *
     * @throws Exception
     */
    public void fillSearchResult(String search) throws TwitterException {
        List<Status> searchResult = twitterEngine.searchtweets(search);
        searchResult.forEach((status) -> {
            list.add(list.size(), new TwitterStatusInfo(status));
        });
        page += 1;
    }

    /**
     * This method calls and creates the TwitterInfoNoStatus and inililizes each
     * field
     *
     * @throws TwitterException
     */
    public void fillDatabaseTweets() throws TwitterException {
        TwitterDAOImpl twitterdao = new TwitterDAOImpl();
        List<TwitterInfoNoStatus> dbTweets = twitterdao.findAll();
        dbTweets.forEach((tweet) -> {
            list.add(list.size(), new TwitterInfoNoStatus(tweet.getName(), tweet.getScreenName(), tweet.getPostedDate(), tweet.getText(),
                    tweet.getImageURL(), tweet.getLargeProfileImageURL(), tweet.getRetweetCount(), tweet.getLikeCount(), tweet.getFollowersCount(), tweet.getFriendsCount(),
                    tweet.getTweetId(), tweet.getDescription()));
        });
        page += 1;
    }

    public void fillOwnRetweets() throws TwitterException {
        List<Status> retweets = twitterEngine.retweetsByMe();
        if(retweets.isEmpty()) yourRetweetsController.noTweetsPopUp();
        retweets.forEach((status) -> {
            list.add(list.size(), new TwitterStatusInfo(status));
        });
        page += 1;
    }

    public void fillTweetsRetweeted() throws TwitterException {
        List<Status> retweeted = twitterEngine.retweetsByOthers();
        if(retweeted.isEmpty()) yourRetweetsController.noTweetsPopUp();
        retweeted.forEach((status) -> {
            list.add(list.size(), new TwitterStatusInfo(status));
        });
        page += 1;
    }
}
