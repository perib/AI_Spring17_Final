import java.util.ArrayList;

public class Tweet {
	
	String fullTweet; // full tweet, including hashtag 
	String text; // main text of tweet, not including hashtag, @mentions
	String hashtag; 
	ArrayList<String> tweetWords; // fullTweet split into words
	
	public Tweet(String origTweet) {
		fullTweet = origTweet;
		text = "";
		hashtag = "";
		
		tweetWords = new ArrayList<String>();
		String[] split = fullTweet.split("\\s+");
		for (String x : split) {
			if (x.substring(0,1) == "#") {
				tweetWords.add(x.substring(1));
				hashtag = x.substring(1);
			} else {
				tweetWords.add(x);
				text += x + " ";
			}
		}
		
	}
	
	public String getTweetText() {
		return text;
	}
	
	public String getOrigTweet() {
		return fullTweet;
	}
	
	public String getHashtag() {
		return hashtag;
	}
	
	
}
