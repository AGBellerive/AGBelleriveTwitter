/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agbellerive.twitter.persistence;

import com.agbellerive.twitter.business.TwitterInfoNoStatus;
import com.agbellerive.twitter.business.TwitterStatusInfo;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 1733565
 */
public class TwitterDAOImpl implements  TwitterDAO{
    private final static String URL = "jdbc:mysql://localhost:3306/AQUARIUM?autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true";
    private final static String USER = "ahhhhh";
    private final static String PASSWORD = "ahhh";

    @Override
    public int create(TwitterStatusInfo tweet) throws SQLException {
         return 1;
    }
    // Read
    @Override
    public List<TwitterInfoNoStatus> findAll() throws SQLException {  
       List<TwitterInfoNoStatus> rows = new ArrayList<>();
         String selectQuery = "SELECT * FROM TWEETS";
         try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement pStatement = connection.prepareStatement(selectQuery);
                ResultSet resultSet = pStatement.executeQuery()) {
             
            while (resultSet.next()) {
                rows.add(createTweetData(resultSet));
            }
        }
        return rows;
    }
    
    private TwitterInfoNoStatus createTweetData(ResultSet resultSet) throws SQLException{
        TwitterInfoNoStatus tweet = new TwitterInfoNoStatus();
        
        tweet.setName((resultSet.getString("USERNAME")));
        tweet.setScreenName((resultSet.getString("HANDLE")));
        tweet.setPostedDate((resultSet.getString("DATEPOSTED")));
        tweet.setText((resultSet.getString("TWEET")));
        tweet.setImageURL((resultSet.getString("IMAGEURL")));
        tweet.setLikeCount((resultSet.getInt("LIKES")));
        tweet.setRetweetCount((resultSet.getInt("RETWEETS")));
        tweet.setTweetId((resultSet.getLong("TWEETID")));
        
        return tweet;
    }
    
}
