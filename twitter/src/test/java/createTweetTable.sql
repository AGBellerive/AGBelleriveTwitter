/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  1733565
 * Created: Nov 6, 2019
 */

DROP TABLE IF EXISTS TWEETS;

CREATE TABLE TWEEETS(
USERNAME VARCHAR2(50),
HANDLE VARCHAR2(15),
DATEPOSTED VARCHAR2(10),
TEXT VARCHAR2(280),
IMAGEURL VARCHAR2(250),
RETWEETS INT(11),
LIKES INT(11),
TWEETID BIGINT(11));