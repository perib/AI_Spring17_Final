/**
 * GetTweet class: takes hashtag, writes file of tweets with that hashtag
 * Some code taken from http://stackoverflow.com/questions/18800610/how-to-retrieve-more-than-100-results-using-twitter4j?noredirect=1&lq=1
 * @author rperezpadilla Rita M. Perez-Padilla
 */

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

public class GetTweet {
	String ht; // Hashtag
	List<Status> tweetsStatus;
	
	/**
	 * Constructor class - gets tweets based on search for hashtag
	 * @param hashtag 
	 */
	public GetTweet(String hashtag) {
		ht = hashtag;
		
		// Opens configuration stream to twitter
		// all of these codes are from an account I made, @RAPATweets
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setOAuthConsumerKey("xBE3hpTKtWqKhVcLJIBAvH3g9");
		cb.setOAuthConsumerSecret("cOaejr3YwtICFVtpe7thL7saPCc2G2iEVCXZn4gG5bfXQw11B7");
		cb.setOAuthAccessToken("852351824166309897-ALtIPuCIKQAWHXLk7kE8bep4QfvNAtQ");
		cb.setOAuthAccessTokenSecret("rqGNUEuJidfVk1B1c4plum5jqa3mdAKP25j3r5l39CZNC");

		// gets the twitter factory 
		Twitter twitter = new TwitterFactory(cb.build()).getInstance();
		Query query = new Query(hashtag);
		query.setCount(100); // gets the maximum of 100 tweets

		try {
			QueryResult result = twitter.search(query);
			tweetsStatus = result.getTweets();
			
			// FOR TESTING: uncomment everything here to print every tweet
//			for (int i = 0; i < tweetsStatus.size(); i++) {
//				Status st = tweetsStatus.get(i);
//				
//				// for testing/print statements
//				String user = st.getUser().getScreenName();
//				String msg = st.getText();
//				
//				
//				// prints all tweets: for testing
//				System.out.println("USER: " + user + " wrote: " + msg);
//			}
		}

	  catch (Throwable te) {
		  System.out.println("Couldn't connect: " + te);
	  };
		  
	}
	
	/**
	 * Writes file with 1 tweet / line
	 */
	public void writeFile() {
		Iterator<Status> tweetIterator = tweetsStatus.iterator();
		
		try {
			PrintWriter writer = new PrintWriter("savedtweets.txt", "UTF-8");
			
			while (tweetIterator.hasNext()) {
				// print the text of each tweet, as it appears originally (INCLUDES emojis, links, etc)
				Status curTweet = tweetIterator.next();
				writer.println(curTweet.getText());
			}
			writer.close();
			System.out.println("File written successfully.");
			
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			System.out.println("Something went wrong in PrintWriter from GetTweet.java!");
			e.printStackTrace();
		}
	}
	
	/**
	 * Main class
	 * @param args should just be [hashtag]
	 */
	public static void main(String[] args) {
		// input = hashtag
		String tag = args[0];
		if (!tag.substring(0, 1).equals("#")) {
			tag = "#" + tag;
		}
		
		// read in 100 tweets with hashtag "tag"
		GetTweet current = new GetTweet(tag); 
		
		// put tweets in text file, 1 tweet per line
		current.writeFile();
		
	}
	
}
