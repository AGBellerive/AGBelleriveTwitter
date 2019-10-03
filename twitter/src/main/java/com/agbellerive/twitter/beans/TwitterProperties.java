/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agbellerive.twitter.beans;

/**
 *
 * @author Alex
 */
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TwitterProperties {
    private StringProperty consumerKey;
    private StringProperty consumerSecret;
    private StringProperty accessToken;
    private StringProperty accessTokenSecret;
    
    
    public TwitterProperties() {
        this("", "", "","");
    }
    
    public TwitterProperties(String consumerKey,String consumerSecret,String acessToken,String acessTokenSecret){
        super();
        this.consumerKey = new SimpleStringProperty(consumerKey);
        this.consumerSecret = new SimpleStringProperty(consumerSecret);
        this.accessToken = new SimpleStringProperty(acessToken);
        this.accessTokenSecret = new SimpleStringProperty(acessTokenSecret);
    }
    
    public String getConsumerKey(){
        return this.consumerKey.get();
    }
    
    public String getConsumerSecret(){
        return this.consumerSecret.get();
    }
    
    public String getAcessToken(){
        return this.accessToken.get();
    }
    
    public String getAcessTokenSecret(){
        return this.accessTokenSecret.get();
    }
    
    public void setConsumerKey(String cKey){
        this.consumerKey.set(cKey);
    }
    
    public void setConsumerSecret(String cSecret){
        this.consumerSecret.set(cSecret);
    }
    
    public void setAcessToken(String aToken){
        this.accessToken.set(aToken);
    }
    
    public void setAcessTokenSecret(String aTokenSecret){
        this.accessTokenSecret.set(aTokenSecret);
    }

}
