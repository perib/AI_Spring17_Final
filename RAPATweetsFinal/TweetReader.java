import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
		System.out.println("???");
		tweetGetter.writeFile();
		
		// parse tweets, make hashmaps 
		TweetParser parsedTweets = new TweetParser("savedtweets.txt");
		System.out.println("Finished parser run");
		
		// generate tweets & learn!
//		ArrayList<String> generatedTweets = new ArrayList<String>();
//		for (int i = 0; i < 10; i++) {
//			// generate tweet and get feedback
//			String s = parsedTweets.gentext();
//			System.out.println(s);
////			System.out.println("Was this a good tweet? Y/N");
////			String response = scan.nextLine();
////			if (response.equals("Y")) {
////				generatedTweets.add(s);
////			}
//		}
//		
//		String s = parsedTweets.gentext();
//		System.out.println(s);
//		
//		//interfact judge it 
//		
//		for (int i = 0; i < generatedTweets.size(); i++) {
//			parsedTweets.updateCountsString(s);
//			parsedTweets.updateProbsString(s);
//		}
//		
//		System.out.println("Updated TweetParser with feedback. New Tweet:");
//		System.out.println(parsedTweets.gentext());
		
		// save hashmaps
		printHashmap(parsedTweets.unigramCounts, "unigramcounts.txt");
		printHashmap(parsedTweets.unigramProbs, "unigramprobs.txt");
		printHashmap(parsedTweets.bigramCounts, "bigramcounts.txt");
		printHashmap(parsedTweets.bigramProbs, "bigramprobs.txt");
		
		//Todo: Print these to file?
		int tokenCount = parsedTweets.tokenCount;
		int tweetCount = parsedTweets.tweetCount;
		
		
		//create moods:
		ArrayList<String> moods = new ArrayList<String>(Arrays.asList("happy", "sad", "troll", "angry"));
		//Save the stuff for batch training
		HashMap<String,ArrayList<String>> memory = new HashMap<String,ArrayList<String>>();
		
		HashMap<String,TweetParser> brain = new HashMap<String,TweetParser>();
		
		for(String mood:moods){
			HashMap<String, Integer> unigramCounts = readFile("unigramcounts.txt");
			HashMap<String, Double> unigramProbs = readFile("unigramprobs.txt");
			HashMap<String, HashMap<String, Integer>> bigramCounts = readFile("bigramcounts.txt");
			HashMap<String, HashMap<String, Double>> bigramProbs = readFile("bigramprobs.txt");
			
			TweetParser m = new TweetParser(unigramCounts,unigramProbs,bigramCounts,bigramProbs,tokenCount,tweetCount);
			brain.put(mood, m);
			ArrayList<String> memoryforMood = new ArrayList<String>(10);
		}
		
		//run the interface:
		Boolean done = false;
		while(!done){
			Scanner s = new Scanner(System.in);
			System.out.print("Give me a mood (happy, sad, angry, troll): ");
			String mood = s.next();
			
			if(mood.equals("done")){// close the system
				break;
			}else if(moods.contains(mood)){
				//do the things
				String generated = brain.get(mood).gentext();
				System.out.printf("Generaded tweet: %s\n", generated);
				System.out.print("Do you like this tweet? (yes or no): ");
		        String yn = s.next();
		        if(yn.equals("yes")){
		        	memory.get(mood).add(generated);
		        	
		        	if(memory.get(mood).size() >= 10){
		        		for(String update:memory.get(mood)){
		        			
		        			brain.get(mood).updateCountsString(update);
		        			brain.get(mood).updateProbsString(update);
		        			
		        		}
		        		//reset memory
		        		ArrayList<String> newmemory = new ArrayList<String>(10);
		        		memory.put(mood, newmemory);
		        		
		        	}
		        	
		        }//TODO: Negative reinforcement?
		        
				
				
			}else{ //if not done or one of the moods, we can't do anything. Reloop.
				System.out.println("That wasn't one of the supported moods");
				
				
			}
			
			
			
		}

		
		
		
		
	}
	
	// prints hashmap to file
	public static void printHashmap(HashMap printme, String filename){
		try{
		FileOutputStream fileOut =
        new FileOutputStream(filename);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(printme);
        out.close();
        fileOut.close();
        System.out.println("Hashmap saved correctly");
		}catch(IOException i){
			i.printStackTrace();
		}
	}
	
	// Returns hashmap from file
	public static HashMap readFile(String filename){
		HashMap thing = null;
	      try {
	          FileInputStream fileIn = new FileInputStream(filename);
	          ObjectInputStream in = new ObjectInputStream(fileIn);
	          thing = (HashMap) in.readObject();
	          in.close();
	          fileIn.close();
	       }catch(IOException i) {
	          i.printStackTrace();
	       } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     
	      return thing;
	      
		
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		TweetReader reader = new TweetReader(args[0]);
	}
	
}
