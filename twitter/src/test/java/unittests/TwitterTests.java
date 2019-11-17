/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unittests;

import com.agbellerive.twitter.business.TwitterInfoNoStatus;
import com.agbellerive.twitter.persistence.TwitterDAOImpl;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Alex
 */
public class TwitterTests {
    private final static Logger LOG = LoggerFactory.getLogger(TwitterTests.class);
    
    private final static String URL = "jdbc:mysql://localhost:3306/twitter?zeroDateTimeBehavior=convertToNull";
    private final static String USER = "alex";
    private final static String PASSWORD = "agb";
    private TwitterInfoNoStatus tweetA;
    private TwitterDAOImpl twitterdao;  
    
    /**
     * This method is ran after all the tests are done
     * cleaning out the database for user use
     */
    @AfterClass
    public static void seedAfterTestCompleted() {
        LOG.info("@AfterClass seeding");
        new TwitterTests().seedDatabase();
    }

    /**
     * This method initilizes some twitter data that is going 
     * to be used later 
     */
    @Before
    public void initilizeTwitterData() {
        this.tweetA = new TwitterInfoNoStatus("Tester","testerHandle","11/09/2019","I hope this is a sucessful test", "", 
                "", 10000 , 10000 ,99999 ,1, (Long.MAX_VALUE),"I am a Twitter Handle");
        this.twitterdao = new TwitterDAOImpl();
    }
    /**
     * This method tests that the database is completly empty so 
     * the size of the list from the db should be 0
     */
    @Test
    public void testFindAll() {
        LOG.info("findAll test started");
        List<TwitterInfoNoStatus> tweetsFromDb = this.twitterdao.findAll();
        assertEquals("testFindAll: ", 0, tweetsFromDb.size());
    }
    
    /**
     * This method inserts a single entry into the database
     * so the database should now have only one entry
     * @throws java.sql.SQLException
     */
    @Test
    public void testInsert() throws SQLException{
        LOG.info("testInsert test started");
        this.twitterdao.create(tweetA);
        List<TwitterInfoNoStatus> tweetsFromDb = this.twitterdao.findAll();
        assertEquals("testInsert: ", 1, tweetsFromDb.size());
    }
    
    /**
     * On the SQL table the tweet id is the primary key so the user
     * cannot save the same tweet twice since the tweet id is unique
     * to the specific tweet, so this method needs to throw and error
     * @throws java.sql.SQLException
     */
    @Test (expected = SQLException.class)
    public void testDuplications() throws SQLException{
        LOG.info("testDuplications test started");
        this.twitterdao.create(tweetA);
        this.twitterdao.create(tweetA);
        fail("The primary keys are the same this method needed to fail by SQLException");
    }
    /**
     * This method tests that the retrived information from the database is 
     * correct and not lost in translation
     * @throws SQLException 
     */
    @Test
    public void testRetrevial() throws SQLException {
        LOG.info("testRetrevial test started");
        this.twitterdao.create(tweetA);
        List<TwitterInfoNoStatus> tweetsFromDb = this.twitterdao.findAll();
        assertEquals("testRetrevial: ", "I hope this is a sucessful test", tweetsFromDb.get(0).getText());
    }
    
    /**
     * * Method inspired by https://gitlab.com/omniprof/jdbc_test_demo/blob/master/src/test/java/com/jdbc_test_demo/tests/TestDataBase.java
     */
    @Before
    public void seedDatabase() {
        LOG.info("Seeding Database");
        final String seedDataScript = loadAsString("createTweetTable.sql");
        try (Connection connection = DriverManager.getConnection(this.URL, this.USER, this.PASSWORD)) {
            for (String statement : splitStatements(new StringReader(seedDataScript), ";")) {
                connection.prepareStatement(statement).execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed seeding database", e);
        }
    }

    /**
     * Method inspired by https://gitlab.com/omniprof/jdbc_test_demo/blob/master/src/test/java/com/jdbc_test_demo/tests/TestDataBase.java
     * @param path
     * @return 
     */
    private String loadAsString(final String path) {
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
                Scanner scanner = new Scanner(inputStream);) {
            return scanner.useDelimiter("\\A").next();
        } catch (IOException e) {
            throw new RuntimeException("Unable to close input stream.", e);
        }
    }
    
    /**
     * Method inspired by https://gitlab.com/omniprof/jdbc_test_demo/blob/master/src/test/java/com/jdbc_test_demo/tests/TestDataBase.java
     * @param reader
     * @param statementDelimiter
     * @return 
     */
    private List<String> splitStatements(Reader reader,
            String statementDelimiter) {
        final BufferedReader bufferedReader = new BufferedReader(reader);
        final StringBuilder sqlStatement = new StringBuilder();
        final List<String> statements = new LinkedList<>();
        try {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || isComment(line)) {
                    continue;
                }
                sqlStatement.append(line);
                if (line.endsWith(statementDelimiter)) {
                    statements.add(sqlStatement.toString());
                    sqlStatement.setLength(0);
                }
            }
            return statements;
        } catch (IOException e) {
            throw new RuntimeException("Failed parsing sql", e);
        }
    }
    
    /**
     * Method inspired by https://gitlab.com/omniprof/jdbc_test_demo/blob/master/src/test/java/com/jdbc_test_demo/tests/TestDataBase.java
     * @param line
     * @return 
     */
    private boolean isComment(final String line) {
        return line.startsWith("--") || line.startsWith("//")
                || line.startsWith("/*");
    }
}
