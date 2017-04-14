import java.util.*;
import java.io.*;
import java.lang.*;
import java.util.Scanner;

public class TweetParser{

//Unigram counts - Key: Word, value: Counts of word occurances
	static HashMap<String, Integer> unigramCounts = new HashMap<String, Integer>();

//Bigram counts - Key: Phrase, Value counts of phrase occurances
	static HashMap<String, Integer> bigramCounts = new HashMap<String, Integer>();

//Bigram probabilities - Key: Token1|Token0, Value: probability
	static HashMap<String, Double> bigramProbs = new HashMap<String, Double>();

	/*
     * getProbability
     * 
     * @param String token0
     * 
     * @param String token1
     * 
     * @returns prob This function figures out the probability of the given
     * bigram
     */
	static double getProbability(String token0, String token1) {
		// token0 is the first word, token1 is the second, P(token1 | token0)
		String phrase = token0 + " " + token1;
		Integer biCount = bigramCounts.get(phrase);

		if (biCount == null) {
		    return 0.0; //Can be changed to unigram prob (count/total tokens)
		} else {
			int uniCount = unigramCounts.get(token0);
			double biC = (double) biCount;
			double uniC = (double) uniCount;
			return (biC / uniC);
		}
	}

	static public void main(String[] args) throws FileNotFoundException{

		Scanner parser = new Scanner(new File(args[0]));
		ArrayList<String> tweetListed = new ArrayList<String>();
		while (parser.hasNext()){
			//System.out.println(parser.next());
			tweetListed.add(parser.next());
		}

	//Variables!
		String prevToken = "";

		for (String token : tweetListed) {
		//Unigram counts:
			if (unigramCounts.get(token)==null){
				unigramCounts.put(token, 1);
			} else {
				unigramCounts.put(token, unigramCounts.get(token)+1);
			}

		//Bigram counts
			String phrase;
			if (prevToken == "") {
				phrase = token;
			} else {
				phrase = prevToken + " " + token;
			}

			Integer count = bigramCounts.get(phrase);

			if (count == null) {
				bigramCounts.put(phrase, 1);
			} else {
				bigramCounts.put(phrase, count + 1);
			}
			prevToken = token;
		}

		int tokenCount = tweetListed.size();

		String token0 = "";
		String token1 = "";
		String phrase = "";
		double prob;

		for (int i=0; i<tokenCount; i++){
			token1 = tweetListed.get(i);
			prob = getProbability (token0, token1);
			phrase = token0 + " " + token1;
			bigramProbs.put(phrase, prob);
			token0 = token1;
		}

		/*
		//Test
		//UniCounts:
		System.out.println("UNIGRAM COUNTS");
		Iterator keys1 = unigramCounts.keySet().iterator();
		while (keys1.hasNext()){
			String word1 = (String) keys1.next();
			System.out.println(word1 + ": " + unigramCounts.get(word1));
		}
		//BiCounts:
		System.out.println("BIGRAM COUNTS");
		Iterator keys2 = bigramCounts.keySet().iterator();
		while (keys2.hasNext()){
			String phrz = (String) keys2.next();
			System.out.println(phrz + ": " + bigramCounts.get(phrz));
		}
		//BiProbs:
		System.out.println("BIGRAM PROBS");
		Iterator keys3 = bigramProbs.keySet().iterator();
		while (keys3.hasNext()){
			String phrase2 = (String) keys3.next();
			System.out.println(phrase2 + ": " + bigramProbs.get(phrase2));
		}
		*/
	}
}
