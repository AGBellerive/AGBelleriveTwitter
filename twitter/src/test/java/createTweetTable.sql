/**
 * Author:  1733565
 * Created: Nov 6, 2019
 */

DROP TABLE IF EXISTS TWEETS;

CREATE TABLE TWEEETS(
ID Int auto_increment,
USERNAME VARCHAR2(50),
HANDLE VARCHAR2(15),
DATEPOSTED VARCHAR2(10),
TEXT VARCHAR2(280),
IMAGEURL VARCHAR2(250),
RETWEETS INT,
LIKES INT,
TWEETID VARCHAR2(50)
PRIMARY KEY  (ID));