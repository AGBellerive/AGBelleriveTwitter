/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agbellerive.twitter.persistence;

import com.agbellerive.twitter.business.TwitterInfoInterface;
import com.agbellerive.twitter.business.TwitterInfoNoStatus;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Alex Bellerive
 */
public interface TwitterDAO {

    /**
     * This method will add to the database
     *
     * @param tweet
     * @throws SQLException
     */
    public void create(TwitterInfoInterface tweet) throws SQLException;

    /**
     * This method will read from the database
     *
     * @return
     * @throws SQLException
     */
    public List<TwitterInfoNoStatus> findAll() throws SQLException;

}
