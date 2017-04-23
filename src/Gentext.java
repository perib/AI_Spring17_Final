import java.io.*;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

import com.sun.crypto.provider.HmacPKCS12PBESHA1;

import java.util.*;

public class Gentext {

	public static void main(String args[]){
		System.out.println();
		HashMap<String,Double> hm1 = new HashMap<String,Double>();
		
		hm1.put("the", .5);
		hm1.put("i", .5);

		
		
		HashMap<String,HashMap<String,Double>> hm2 = new HashMap<String,HashMap<String,Double>>();
		
		HashMap<String,Double> temp = new HashMap<String,Double>();
		temp.put("apple", .3);
		temp.put("tree", .3);
		temp.put("potato", .3);
		temp.put("best", 1.0);
		
		hm2.put("the", temp);
		
		temp = new HashMap<String,Double>();
		temp.put("is", .5);
		temp.put(null, .5);
		
		hm2.put("apple", temp);
		
		temp = new HashMap<String,Double>();
		temp.put("is", 1.0);
		
		hm2.put("tree", temp);
		
		temp = new HashMap<String,Double>();
		temp.put("is", .5);
		temp.put(null, .5);
		
		hm2.put("potato", temp);
		
		temp = new HashMap<String,Double>();
		temp.put("red", .3);
		temp.put("tall", .3);
		temp.put("awesome", .3);
		temp.put("the", .1);

		hm2.put("is", temp);
		
		temp = new HashMap<String,Double>();
		temp.put(null, 1.0);
		
		hm2.put("hungry", temp);
		hm2.put("red", temp);
		hm2.put("tall", temp);
		hm2.put("awesome", temp);
		hm2.put("best", temp);
		hm2.put("cool", temp);
		hm2.put("smart", temp);
		
		temp = new HashMap<String,Double>();
		temp.put("ate", .3);
		temp.put("am", .3);
		temp.put("like", .4);
		
		hm2.put("i", temp);
		
		temp = new HashMap<String,Double>();
		temp.put("an", 1.0);
		
		hm2.put("ate", temp);

		temp = new HashMap<String,Double>();
		temp.put("hungry", 1.0);
		
		hm2.put("am", temp);
		
		temp = new HashMap<String,Double>();
		temp.put("potato", .5);
		temp.put("apple", .5);
		
		hm2.put("like", temp);
		
		temp = new HashMap<String,Double>();
		temp.put("apple", .5);
		
		hm2.put("an", temp);
		
		
		//****************************************8
		
		System.out.print(gentext(hm1,hm2,140));
		
	}
	
	/**
	 * This function generates a string less than maxChars long.
	 * HM1 is a unigram (P(word))
	 * HM2 is a bigram (P(next word|prev word)
	 * */
	public static String gentext(HashMap hm1, HashMap hm2,int maxChars){
		StringBuilder sb = new StringBuilder();	
		int count = 0;
		while(maxChars> count){
			
			for(int i = 0; i<5;i++){
				String next = genSentence(hm1,hm2,maxChars-count);
				
				if(count + next.length() <= maxChars){
					sb.append(next);
					count = count+ next.length();
					break;
				}else{
					if(i == 4){
						return sb.toString();
					}
				}
				
				
			}
			
		}
		
		return sb.toString();
	}
	
	public static String genSentence(HashMap hm1, HashMap hm2,int maxChars){
		StringBuilder sb = new StringBuilder();	
		
		int count = 0;
		String prev = getFirstWord(hm1);
		count = prev.length() + 1;
		
		sb.append(prev);
		sb.append(" ");
		while(count < maxChars){
			String next;
			for(int i = 0; i<5; i++){
				next = getNextWord(hm2,prev);
				
				//if we ended the sentence
				if(next == null){
					sb.setCharAt(sb.length()-1, '.');
					return sb.toString();
				}
				
				if(next.length() + count <= maxChars){
					sb.append(next);
					sb.append(" ");
					prev = next;
					count = count + prev.length() + 1;
					break;
				}else if(i == 4){
					return sb.toString();
				}
				
			}
			
		}
		
		return sb.toString();
	}
	
	public static String getFirstWord(HashMap<String,Double> hm1){
		String s = null;
		
		Set<String> possibleWords = hm1.keySet();
		Random gen = new Random();
		double e = gen.nextDouble();
		double total = 0.0;
		for(String ss:possibleWords){
			total = total + hm1.get(ss);
			if(e < total){
				return ss;
			}
			s = ss;
			
		}
			
		return s;
	}
	
	public static String getNextWord(HashMap<String,HashMap<String,Double>> hm2, String prevWord){
		if(hm2.containsKey(prevWord)){
			return getFirstWord(hm2.get(prevWord));
		}else{
			return null;
		}

	}
	
}
