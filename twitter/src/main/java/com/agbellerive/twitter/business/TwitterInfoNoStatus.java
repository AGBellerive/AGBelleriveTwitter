/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agbellerive.twitter.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Alex Bellerive
 */
public class TwitterInfoNoStatus implements TwitterInfoInterface {

    private final static Logger LOG = LoggerFactory.getLogger(TwitterInfoNoStatus.class);

    private String name;
    private String handle;
    private String postedDate;
    private String text;
    private String imageSmall;
    private String imageLarge;
    private int reTweetCount;
    private int likeCount;
    private int followerCount;
    private int followingCount;
    private Long tweetId;
    private String description;

    public TwitterInfoNoStatus(String name, String handle, String date, String text, String img,
            String largeImg, int retweets, int likes, int followers, int following, Long tweetid, String des) {
        this.name = name;
        this.handle = handle;
        this.postedDate = date;
        this.text = text;
        this.imageSmall = img;
        this.imageLarge = largeImg;
        this.reTweetCount = retweets;
        this.likeCount = likes;
        this.followerCount = followers;
        this.followingCount = following;
        this.tweetId = tweetid;
        this.description = des;
    }

    public TwitterInfoNoStatus() {
    }

    public void setName(String givenName) {
        this.name = givenName;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public void setScreenName(String screenName) {
        this.handle = screenName;
    }

    @Override
    public String getScreenName() {
        return this.handle;
    }

    public void setPostedDate(String date) {
        this.postedDate = date;
    }

    @Override
    public String getPostedDate() {
        return this.postedDate;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String getText() {
        return this.text;
    }

    public void setImageURL(String imgurl) {
        this.imageSmall = imgurl;
    }

    @Override
    public String getImageURL() {
        return this.imageSmall;
    }

    public void setLikeCount(int likes) {
        this.likeCount = likes;
    }

    @Override
    public int getLikeCount() {
        return this.likeCount;
    }

    public void setTweetId(Long tweetId) {
        this.tweetId = tweetId;
    }

    @Override
    public Long getTweetId() {
        return this.tweetId;
    }

    public void setRetweetCount(int reTweets) {
        this.reTweetCount = reTweets;
    }

    @Override
    public int getRetweetCount() {
        return this.reTweetCount;
    }

    public void setLargeProfileImageURL(String img) {
        this.imageLarge = img;
    }

    @Override
    public String getLargeProfileImageURL() {
        return this.imageLarge;
    }

    public void setDescription(String des) {
        this.description = des;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    public void setFollowersCount(int followers) {
        this.followerCount = followers;
    }

    @Override
    public int getFollowersCount() {
        return this.followerCount;
    }

    public void setFriendCount(int following) {
        this.followingCount = following;
    }

    @Override
    public int getFriendsCount() {
        return this.followingCount;
    }
}
