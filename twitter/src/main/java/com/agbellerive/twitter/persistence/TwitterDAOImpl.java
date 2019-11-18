/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agbellerive.twitter.persistence;

import com.agbellerive.twitter.business.TwitterInfoInterface;
import com.agbellerive.twitter.business.TwitterInfoNoStatus;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author 1733565
 */
public class TwitterDAOImpl implements TwitterDAO {

    private final static Logger LOG = LoggerFactory.getLogger(TwitterDAOImpl.class);

    private final static String URL = "jdbc:mysql://localhost:3306/twitter?zeroDateTimeBehavior=convertToNull";
    private final static String USER = "alex";
    private final static String PASSWORD = "agb";

    /**
     * This method connects to the database and insert a value with the provided
     * input which is a TwitterInfoInterface which is a TwitterInfo or
     * TwitterInfoWithNoStatus
     *
     * @param tweet
     * @throws SQLException
     */
    @Override
    public void create(TwitterInfoInterface tweet) throws SQLException {
        String insertQuery = "INSERT INTO TWEETS VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
        try ( Connection connection = DriverManager.getConnection(this.URL, this.USER, this.PASSWORD);  PreparedStatement pStatement = connection.prepareStatement(insertQuery)) {
            pStatement.setString(1, tweet.getName());
            pStatement.setString(2, tweet.getScreenName());
            pStatement.setString(3, tweet.getPostedDate());
            pStatement.setString(4, tweet.getText());
            pStatement.setString(5, tweet.getImageURL());
            pStatement.setString(6, tweet.getLargeProfileImageURL());
            pStatement.setInt(7, tweet.getRetweetCount());
            pStatement.setInt(8, tweet.getLikeCount());
            pStatement.setInt(9, tweet.getFollowersCount());
            pStatement.setInt(10, tweet.getFriendsCount());
            pStatement.setString(11, tweet.getDescription());
            pStatement.setString(12, Long.toString(tweet.getTweetId()));

            int res = pStatement.executeUpdate();
            LOG.info("Entry added to database");
            LOG.info("Tweet By :" + tweet.getName() + " has been saved Sucessfully");
        }

    }

    /**
     * This method connects to the database and retrives all rows of all tweets
     * in the database and returns a list of the object
     *
     * @return List<TwitterInfoNoStatus>
     */
    @Override
    public List<TwitterInfoNoStatus> findAll() {
        List<TwitterInfoNoStatus> rows = new ArrayList<>();
        String selectQuery = "SELECT * FROM TWEETS";
        try ( Connection connection = DriverManager.getConnection(this.URL, this.USER, this.PASSWORD);  PreparedStatement pStatement = connection.prepareStatement(selectQuery);  ResultSet resultSet = pStatement.executeQuery()) {

            while (resultSet.next()) {
                rows.add(createTweetData(resultSet));
            }
        } catch (SQLException ex) {
            LOG.info("Cannot Rertrive from database " + ex);
        }
        return rows;
    }

    /**
     * This method is called by the readAll method to create a specific object
     * of TwitterInfoNoStatus to be returned and added into a list
     *
     * @param resultSet
     * @return
     * @throws SQLException
     */
    private TwitterInfoNoStatus createTweetData(ResultSet resultSet) throws SQLException {
        TwitterInfoNoStatus tweet = new TwitterInfoNoStatus();
        //Tweet id stored as varchar, rertived and converted into a long
        tweet.setName((resultSet.getString("USERNAME")));
        tweet.setScreenName((resultSet.getString("HANDLE")));
        tweet.setPostedDate((resultSet.getString("DATEPOSTED")));
        tweet.setText((resultSet.getString("TEXT")));
        tweet.setImageURL((resultSet.getString("IMAGEURL")));
        tweet.setLargeProfileImageURL((resultSet.getString("LARGEIMAGEURL")));
        tweet.setRetweetCount((resultSet.getInt("RETWEETS")));
        tweet.setLikeCount((resultSet.getInt("LIKES")));
        tweet.setFollowersCount((resultSet.getInt("FOLLOWERS")));
        tweet.setFriendCount((resultSet.getInt("FOLLOWING")));
        tweet.setDescription((resultSet.getString("DESCRIPTION")));
        tweet.setTweetId(Long.parseLong((resultSet.getString("TWEETID"))));

        return tweet;
    }

}
