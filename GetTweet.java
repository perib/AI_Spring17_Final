/**
 * GetTweet class: takes hashtag, writes file of tweets with that hashtag
 * Some code taken from http://stackoverflow.com/questions/18800610/how-to-retrieve-more-than-100-results-using-twitter4j?noredirect=1&lq=1
 * @author rperezpadilla Rita M. Perez-Padilla
 */

import java.util.ArrayList;
import twitter4j.*;
import twitter4j.TwitterException;
import twitter4j.conf.ConfigurationBuilder;

public class GetTweet {
	String ht; // Hashtag
	ArrayList<Tweet> tweets;
	
	/**
	 * Constructor class - gets tweets based on search for hashtag
	 * @param hashtag 
	 */
	public GetTweet(String hashtag) {
		ht = hashtag;
		tweets = new ArrayList<Tweet>(100);
		// cool
		
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setOAuthConsumerKey("xBE3hpTKtWqKhVcLJIBAvH3g9");
		cb.setOAuthConsumerSecret("cOaejr3YwtICFVtpe7thL7saPCc2G2iEVCXZn4gG5bfXQw11B7");
		cb.setOAuthAccessToken("852351824166309897-ALtIPuCIKQAWHXLk7kE8bep4QfvNAtQ");
		cb.setOAuthAccessTokenSecret("rqGNUEuJidfVk1B1c4plum5jqa3mdAKP25j3r5l39CZNC");

		  Twitter twitter = new TwitterFactory(cb.build()).getInstance();
		  Query query = new Query(hashtag);
		  query.setCount(100);

		  try {
		    QueryResult result = twitter.search(query);
		    ArrayList tweets = (ArrayList) result.getTweets();

		    for (int i = 0; i < tweets.size(); i++) {
		      Status t = (Status) tweets.get(i);
		      String user = t.getUser().getScreenName();
		      String msg = t.getText();

		      System.out.println("USER: " + user + " wrote: " + msg);
		    }
		  }

		  catch (Throwable te) {
		    System.out.println("Couldn't connect: " + te);
		  };
		
	}
	
	/**
	 * Writes file with 1 tweet / line
	 */
	public void writeFile() {
		// 1 tweet per line
		
		
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
