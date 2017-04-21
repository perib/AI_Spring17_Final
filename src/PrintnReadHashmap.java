import java.io.*;
import java.util.*;


/**
 * Functions to save a hashmap to file and then read it back out.
 * Thanks to http://www.tutorialspoint.com/java/java_serialization.htm for helping me
 * */
public class PrintnReadHashmap {
	
	public static void main(String args[]){
		HashMap<String,Integer> potato = new HashMap<String,Integer>();
		potato.put("pizza", 9);
		potato.put("potato", 999999);
		potato.put("asparagus", -99);
		
		String filename = "potato.ser";
		
		print(potato,filename);
		
		HashMap recovered = read(filename);
		System.out.println(recovered.get("pizza"));
		System.out.println(recovered.get("potato"));
		System.out.println(recovered.get("asparagus"));
		
	}

	public static void print(HashMap printme, String filename){
		try{
		FileOutputStream fileOut =
        new FileOutputStream(filename);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(printme);
        out.close();
        fileOut.close();
        System.out.println("Saved correctly");
		}catch(IOException i){
			i.printStackTrace();
		}
		
		
	}
	
	public static HashMap read(String filename){
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
	
	
}
