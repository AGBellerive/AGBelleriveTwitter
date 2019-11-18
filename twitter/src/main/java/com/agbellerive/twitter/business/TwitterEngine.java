package com.agbellerive.twitter.business;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import twitter4j.DirectMessage;
import twitter4j.Paging;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

/**
 * Based on code from https://www.baeldung.com/twitter4j
 *
 * @author Ken Fogel
 */
public class TwitterEngine {

    // Real programmers use logging, not System.out.println
    private final static Logger LOG = LoggerFactory.getLogger(TwitterEngine.class);

    public Twitter getTwitterinstance() {
        /**
         * if not using properties file, we can set access token by following
         * way
         */
//	ConfigurationBuilder cb = new ConfigurationBuilder();
//	cb.setDebugEnabled(true)
//	  .setOAuthConsumerKey("//TODO")
//	  .setOAuthConsumerSecret("//TODO")
//	  .setOAuthAccessToken("//TODO")
//	  .setOAuthAccessTokenSecret("//TODO");
//	TwitterFactory tf = new TwitterFactory(cb.build());
//	Twitter twitter = tf.getSingleton();

        Twitter twitter = TwitterFactory.getSingleton();
        return twitter;

    }

    /**
     * Send a tweet
     *
     * @param tweet
     * @return
     * @throws TwitterException
     */
    public String createTweet(String tweet) throws TwitterException {
        LOG.debug("createTweet: " + tweet);
        Twitter twitter = getTwitterinstance();
        Status status = twitter.updateStatus(tweet);
        // LOG.debug("Status: " + status.toString());
        return status.getText();
    }

    /**
     * Get the timeline
     *
     * @return
     * @throws TwitterException
     */
    public List<Status> getTimeLine() throws TwitterException {
        LOG.debug("getTimeLine");
        Twitter twitter = getTwitterinstance();
        List<Status> statuses = twitter.getHomeTimeline();
        return statuses;
//        return statuses.stream().map(
//                item -> item.getText()).collect(
//                        Collectors.toList());
    }

    /**
     * Send direct message
     *
     * @param recipientName
     * @param msg
     * @return
     * @throws TwitterException
     */
    public String sendDirectMessage(String recipientName, String msg) throws TwitterException {
        LOG.debug("sendDirectMessage");
        Twitter twitter = getTwitterinstance();
        DirectMessage message = twitter.sendDirectMessage(recipientName, msg);
        return message.getText();
    }

    /**
     * Search for tweets with specific contents
     *
     * @param searchTerm
     * @return
     * @throws TwitterException
     */
    public List<Status> searchtweets(String searchTerm) throws TwitterException {
        LOG.debug("searchtweets");
        Twitter twitter = getTwitterinstance();
        Query query = new Query(searchTerm);
        //Query query = new Query("source:twitter4j baeldung");
        QueryResult result = twitter.search(query);
        List<Status> statuses = result.getTweets();
        return statuses;
    }

    /**
     * Utility Method to display status
     *
     * @param timeLine
     */
    public void displayTimeLine(List<Status> timeLine) {
        System.out.println("Length of timeline: " + timeLine.size());
        timeLine.stream().map((s) -> {
            System.out.println("User: " + s.getUser().getName());
            return s;
        }).forEachOrdered((s) -> {
            System.out.println("Text: " + s.getSource());
        });
    }

    /**
     * Utility Method to get time line
     *
     * @param timeLine
     */
    public List<Status> getTimeLine(int page) throws TwitterException {
        LOG.debug("getTimeLine");
        Twitter twitter = getTwitterinstance();
        Paging paging = new Paging();
        paging.setCount(15);
        paging.setPage(page);
        List<Status> statuses = twitter.getHomeTimeline(paging);
        return statuses;
    }

    /**
     * This method likes a tweet given a tweetid inspired by:
     * http://www.tothenew.com/blog/mark-tweet-as-favorite-using-twitter4j/
     *
     * @param tweetId
     * @throws TwitterException
     */
    public void likeTweet(Long tweetId) throws TwitterException {
        Twitter twitter = getTwitterinstance();
        twitter.createFavorite(tweetId);
    }

    /**
     * This method retweets the specified status
     * http://twitter4j.org/oldjavadocs/2.2.3/twitter4j/api/StatusMethods.html
     *
     * @param tweetId
     * @throws TwitterException
     */
    public void reTweet(Long tweetId) throws TwitterException {
        Twitter twitter = getTwitterinstance();
        twitter.retweetStatus(tweetId);
    }

    /**
     * This method creates a comment (reply) on the specific tweet
     *
     * @param reply
     * @param tweetid
     * @throws TwitterException
     */
    public void makeComment(String reply, Long tweetid) throws TwitterException {
        Twitter twitter = getTwitterinstance();
        StatusUpdate tweetReply = new StatusUpdate(reply);
        tweetReply.inReplyToStatusId(tweetid);

        Status statusRepliedTo = twitter.updateStatus(tweetReply);

        //http://www.tothenew.com/blog/reply-to-a-user-tweet-using-twitter4j/
    }

    /**
     * This method obtains the users timeline and finds which is a retweet and
     * returns it to be displayed
     *
     * @return List <Status>
     * @throws TwitterException
     */
    public List<Status> retweetsByMe() throws TwitterException {
        Twitter twitter = getTwitterinstance();
        List<Status> timeline = twitter.getHomeTimeline();
        List<Status> retweets = new ArrayList();
        for (Status stat : timeline) {
            if (stat.isRetweetedByMe()) {
                retweets.add(stat);
            }
        }
        return retweets;
    }

    /**
     * This method obtains the retweets that are retweted by others
     *
     * @return List <Status>
     * @throws TwitterException
     */
    public List<Status> retweetsByOthers() throws TwitterException {
        Twitter twitter = getTwitterinstance();
        List<Status> timeline = twitter.getUserTimeline();
        List<Status> retweted = twitter.getRetweetsOfMe();
        return retweted;
    }

}
