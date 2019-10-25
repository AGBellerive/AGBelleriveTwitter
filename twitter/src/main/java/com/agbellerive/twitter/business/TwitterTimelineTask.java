package com.agbellerive.twitter.business;

import java.util.List;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.Status;

/**
 * Task of retrieving user's timeline
 *
 * @author tomo
 */
public class TwitterTimelineTask {

    // Real programmers use logging, not System.out.println
    private final static Logger LOG = LoggerFactory.getLogger(TwitterTimelineTask.class);

    private final ObservableList<TwitterStatusInfo> list;

    private final TwitterEngine twitterEngine;

    private int page;

    /**
     * Non-default constructor initializes instance variables.
     *
     * @param list
     */
    public TwitterTimelineTask(ObservableList<TwitterStatusInfo> list) {
        twitterEngine = new TwitterEngine();
        this.list = list;
        page = 1;
    }

    /**
     * Add new Status objects to the ObservableList. Additions occur at the end
     * of the list.
     *
     * @throws Exception
     */
    public void fillTimeLine() throws Exception {
        List<Status> homeline = twitterEngine.getTimeLine(page);
        homeline.forEach((status) -> {
            list.add(list.size(), new TwitterStatusInfo(status));
        });
        page += 1;
    }
}
