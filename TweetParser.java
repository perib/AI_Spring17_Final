import java.util.*;
import java.io.*;
import java.lang.*;
import java.util.Scanner;

public class TweetParser{

	//Unigram counts - Key: Word, Value: Counts of word occurances
	static HashMap<String, Integer> unigramCounts = new HashMap<String, Integer>();

	//Unigram Probabilities - Key: Token, Value: Unigram Probability
	static HashMap<String, Double> unigramProbs = new HashMap<String, Double>();

	//Bigram counts - Key: Token0, Value: (HashMap - Key: Token1, Value: counts of bigram occurances)
	static HashMap<String, HashMap<String, Integer>> bigramCounts = new HashMap<String, HashMap<String, Integer>>();

	//Bigram probabilities - Key: Token0, Value: (HashMap - Key: Token1, Value: Bigram Probability
	static HashMap<String, HashMap<String, Double>> bigramProbs = new HashMap<String, HashMap<String, Double>>();

	//Total number of words seen in the unigram counts map
	static Integer tokenCount;

	//Total number of tweets in tweetListed
	static Integer tweetCount;
	
	/*
	 * getUnigramProbability
	 *
	 * @param String token0
	 * 
	 *@returns prob
	 *
	 *This function prigures out the probability of the given unigram
	 */
	static double getUnigramProbability(String token0, int totalTokens) {
		double u = (double) unigramCounts.get(token0);
		return (u/totalTokens);
	}


	/*
     * getBigramProbability
     * 
     * @param String token0
     * 
     * @param String token1
     * 
     * @returns prob 
     *
     * This function figures out the probability of the given
     * bigram
     */
	static double getBigramProbability(String token0, String token1, int totalTokens) {
		// token0 is the first word, token1 is the second, P(token1 | token0)
		Integer biCount = bigramCounts.get(token0).get(token1);

		if (biCount == null) {
		    return getUnigramProbability(token1, totalTokens); //Can be changed to unigram prob (count token1/total tokens)
		} else {
			if (unigramCounts.get(token0)==null){
				return getUnigramProbability(token1, totalTokens);
			}
			int uniCount = unigramCounts.get(token0);
			double biC = (double) biCount;
			double uniC = (double) uniCount;
			return (biC / uniC);
		}
	}

	static ArrayList<ArrayList<String>> processTweet(int tweetCount, String curLine, ArrayList<ArrayList<String>> tweetListed){
		boolean end = false;
		boolean abv = false;
		char punct = 'x';
		String finalWord = "";
		String[] abbv = {"Mr.", "Mrs.", "Ms.", "Dr.", "Ph.D.", "Drs.", "Cmdr.", "Rev.", "Jr.", "Sr.", "Inc.", "St.", "Rd.", "Ave.", "Ct.", "Ft.", "In.", "in.", "Co.", "a.m.", "p.m.", "e.g.", "i.e.", "etc.", "Jan.", "Feb.", "Mar.", "Apr.", "Jun.", "Jul.", "Aug.", "Sep.", "Oct.", "Nov.", "Dec."};



		//Add start tag
		tweetListed.get(tweetCount).add("<s>");
		tokenCount++;

		Scanner parser = new Scanner(curLine);
		while (parser.hasNext()){
				//Check if end of sentence
			String cur = parser.next();
			if (cur.endsWith(".")||cur.endsWith("?")||cur.endsWith("!")){
				for (String abbrev : abbv){
					if (cur.equals(abbrev)){
						abv = true;
					}
				} 
				if (abv == false){
					punct = cur.charAt(cur.length()-1);
					//System.out.println("PUNCT: *" + punct + "*");
					int index = cur.indexOf(punct);
					end = true;
					cur = cur.substring(0, cur.length()-1);
				}
			}

			//Add word to list
			cur = cur.toLowerCase();
			tweetListed.get(tweetCount).add(cur);
			tokenCount++;

			if (end==true){
				tweetListed.get(tweetCount).add(Character.toString(punct));
				tokenCount++;
				if (parser.hasNext()){
					tweetListed.get(tweetCount).add("<s>");
					tokenCount++;
				}
			}

				//Reset booleans
			end=false;
			abv = false;
		}
		tweetCount++;

		System.out.println("Printing all tweets:");
		for (int i=0; i<tweetListed.size(); i++){
			ArrayList<String> a = tweetListed.get(i);
			for (int j=0; j<a.size(); j++){
				System.out.print(a.get(j) + " ");
			}
			System.out.println();
		}
		return tweetListed;
	}

	/*
	 * update Counts
	 *
	 * This method updates both unigram and bigram counts
	 */
	static void updateCounts(ArrayList<ArrayList<String>> tweetListed){
		//Variables:
		String prevToken = "";

		//Loop through tweets in tweetlisted
		for (ArrayList<String> tweet : tweetListed){
			//Loop through words in tweet
			for (String token : tweet) {

				//Unigram counts:
				if (unigramCounts.get(token)==null){
					unigramCounts.put(token, 1);
				} else {
					unigramCounts.put(token, unigramCounts.get(token)+1);
				}

				//Bigram counts
				if (bigramCounts.get(prevToken)==null){
					bigramCounts.put(prevToken, new HashMap<String, Integer>());
				}

				Integer count = bigramCounts.get(prevToken).get(token);

				if (count == null) {
					bigramCounts.get(prevToken).put(token, 1);
				} else {
					bigramCounts.get(prevToken).put(token, count+1);
				}
				prevToken = token;
			}
			prevToken="";
		}
	}

	/*
	 * update Counts
	 *
	 * This method updates both unigram and bigram counts
	 */
	static void updateProbabilities(ArrayList<ArrayList<String>> tweetListed){
		int twCount = tweetListed.size();

		String token0 = "";
		String token1 = "";
		String phrase = "";
		double prob;

			//Bigram Probs
		for (int i=0; i<twCount; i++){
			for (int j=0; j<tweetListed.get(i).size(); j++){
					//Get prob of token1|token0
				token1 = tweetListed.get(i).get(j);
				prob = getBigramProbability(token0, token1, tokenCount);

					//Add to HashMap
				if (bigramProbs.get(token0)==null){
					bigramProbs.put(token0, new HashMap<String, Double>());
				}

				bigramProbs.get(token0).put(token1, prob);
				token0 = token1;
			}
		}
		//Unigram Probs
		for (int i=0; i<twCount; i++){
			for (int j=0; j<tweetListed.get(i).size(); j++){
				token0=tweetListed.get(i).get(j);
				prob = getUnigramProbability (token0, tokenCount);
				unigramProbs.put(token0, prob);
			}
		}
	}

	static void testParser(){
		//UniCounts:
		System.out.println("UNIGRAM COUNTS");
		Iterator keys1 = unigramCounts.keySet().iterator();
		while (keys1.hasNext()){
			String word1 = (String) keys1.next();
			System.out.println(word1 + ": " + unigramCounts.get(word1));
		}

		System.out.println();

		//BiCounts:
		System.out.println("BIGRAM COUNTS");
		Iterator keys2 = bigramCounts.keySet().iterator();
		while (keys2.hasNext()){
			String t = (String) keys2.next();
			Iterator keys2b = bigramCounts.get(t).keySet().iterator();
			while (keys2b.hasNext()){
				String phrz = (String) keys2b.next();
				System.out.println(phrz + "|" + t + ": " + bigramCounts.get(t).get(phrz));
			}		
		}

		System.out.println();

		//UniProbs:
		System.out.println("UNGIRAM PROBS");
		Iterator keys3 = unigramProbs.keySet().iterator();
		while (keys3.hasNext()){
			String word2 = (String) keys3.next();
			System.out.println(word2 + ": " + unigramProbs.get(word2));
		}
		System.out.println();

		//BiProbs:
		System.out.println("BIGRAM PROBS");
		Iterator keys4 = bigramProbs.keySet().iterator();
		while (keys4.hasNext()){
			String phrase2 = (String) keys4.next();
			Iterator keys4b = bigramProbs.get(phrase2).keySet().iterator();
			while (keys4b.hasNext()){
				String phrz2 = (String) keys4b.next();
				System.out.println(phrase2 + "|" + phrz2 + ": " + bigramProbs.get(phrase2).get(phrz2));
			}
		}
	}


	static public void main(String[] args) throws FileNotFoundException{

		//Variables
		Scanner parser1 = new Scanner(new File(args[0]));
		ArrayList<ArrayList<String>> tweetListed = new ArrayList<ArrayList<String>>(); //List of words from tweet (Outer ArrayList is list of tweets, inner is list of words in one tweet)
		tokenCount = 0;
		tweetCount = 0;

		//Take in tweet one tweet and process
		while (parser1.hasNextLine()){
			String curLine = parser1.nextLine();
			
			//Add new array to tweetListed (represents current tweet)
			tweetListed.add(new ArrayList<String>());
			tweetListed = processTweet(tweetCount, curLine, tweetListed);
		}
		
		//Update counts and probabilities
		updateCounts(tweetListed);
		updateProbabilities(tweetListed);

		testParser(); //Can be commented out during actual running of program	
	}
	/*
	1. I think we need to save total token counts between sessions - since hashmaps can't demonstrate the size
	*/
}
