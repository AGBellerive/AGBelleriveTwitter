/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  1733565
 * Created: Nov 6, 2019
 */

DROP DATABASE IF EXISTS TWITTER;
CREATE DATABASE TWITTER;

USE TWITTER;

DROP USER IF EXISTS alex@localhost;
CREATE USER alex@'localhost' IDENTIFIED BY 'agb';
GRANT ALL ON TWITTER.* TO alex@'localhost';

FLUSH PRIVILEGES;
