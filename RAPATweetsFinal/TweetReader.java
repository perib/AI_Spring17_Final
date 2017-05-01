import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * TweetReader.java
 * Main class for RAPA Tweets project
 * 
 * @author RAPA
 * CS 364 
 * Spring 2017
 */
public class TweetReader {
	
	public TweetReader(String tag) throws FileNotFoundException {
		Scanner scan = new Scanner(System.in);
		// get a hashtag & get tweets with that hashtag
		if (!tag.substring(0, 1).equals("#")) {
			tag = "#" + tag;
		}
		GetTweet tweetGetter = new GetTweet(tag);
		tweetGetter.writeFile();
		
		// parse tweets, make hashmaps 
		TweetParser parsedTweets = new TweetParser("savedtweets.txt");
		System.out.println("Finished parser run");
		
		// generate tweets & learn!
		ArrayList<String> generatedTweets = new ArrayList<String>();
		for (int i = 0; i < 10; i++) {
			// generate tweet and 
			String s = parsedTweets.gentext();
			System.out.println(s);
			System.out.println("Was this a good tweet? Y/N");
			String response = scan.nextLine();
			if (response.equals("Y")) {
				generatedTweets.add(s);
			}
		}
		
		String s = parsedTweets.gentext();
		System.out.println(s);
		
		//interfact judge it 
		
		for (int i = 0; i < generatedTweets.size(); i++) {
			parsedTweets.updateCountsString(s);
			parsedTweets.updateProbsString(s);
			
		}
		
		System.out.println("Updated TweetParser with feedback. New Tweet:");
		System.out.println(parsedTweets.gentext());
		
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		TweetReader reader = new TweetReader(args[0]);
	}
	
}
