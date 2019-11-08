package com.agbellerive.twitter.business;

import twitter4j.TwitterException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Alex
 */
public interface TwitterInfoInterface {
    public String getName();
    public String getScreenName();
    public String getPostedDate();
    public String getText();
    public String getImageURL();
    public String getLargeProfileImageURL();
    public Long getTweetId();
    public int getRetweetCount();
    public int getLikeCount();
    public String getDescription();
    public int getFollowersCount();
    public int getFriendsCount();
}
