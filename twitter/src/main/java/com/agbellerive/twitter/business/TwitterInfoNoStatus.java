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
public class TwitterInfoNoStatus {
    
    private final static Logger LOG = LoggerFactory.getLogger(TwitterInfoNoStatus.class);
    
    private String name;
    private String handle;
    private String postedDate;
    private String text;
    private String image;
    private int reTweetCount;
    private int likeCount;
    private Long tweetId;
    

    public TwitterInfoNoStatus(String name,String handle,String date,String text, String img, int retweets, int likes, Long tweetid) {
        this.name = name;
        this.handle = handle;
        this.postedDate = date;
        this.text = text;
        this.image = img;
        this.reTweetCount = retweets;
        this.likeCount = likes;
        this.tweetId= tweetid;
    }
     public TwitterInfoNoStatus(){
         
     }
    
    public void setName(String givenName) {
       this.name = givenName;
    }
    
    public String getName() {
       return this.name;
    }
    
    public void setScreenName(String screenName){
        this.handle = screenName;
    }
    
    public String getScreenName(){
        return this.handle;
    }
    
    public void setPostedDate(String date){
        this.postedDate = date;
    }

    public String getPostedDate(){
        return this.postedDate;
    }
    
    public void setText(String text){
        this.text = text;
    }

    public String getText(){
        return this.text;
    }
    
    public void setImageURL(String imgurl){
        this.image = imgurl;
    }
    
    public String getImageURL(){
        return this.image;
    }
    
    public void setLikeCount(int likes){
        this.likeCount = likes;
    }
    
    public int getLikeCount(){
        return this.likeCount;
    }
    
    public void setTweetId(Long tweetId){
        this.tweetId = tweetId;
    }
    
    public Long getTweetId(){
        return this.tweetId;
    }
       
    public void setRetweetCount(int reTweets){
        this.reTweetCount = reTweets;
    }
       
    public int getRetweetCount(){
        return this.reTweetCount;
    }
}
