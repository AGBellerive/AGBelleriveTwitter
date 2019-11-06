/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agbellerive.twitter.persistence;

import com.agbellerive.twitter.business.TwitterInfoNoStatus;
import com.agbellerive.twitter.business.TwitterStatusInfo;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author 1733565
 */
public interface TwitterDAO {
    
    // Create
    public int create(TwitterStatusInfo tweet) throws SQLException;

    // Read
    public List<TwitterInfoNoStatus> findAll() throws SQLException;
    
}
