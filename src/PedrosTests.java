import java.util.Scanner;


public class PedrosTests {

    public static void main(String[] args) {
        
        System.out.print("Give me a hashtag: ");
        Scanner s = new Scanner(System.in);
        String hashtag = s.next();
        System.out.print("Give me a mood (happy, sad, angry, troll): ");
        String mood = s.next();
        Boolean generated = false;
        while(generated == false){
            if(mood.equals("happy")){
                //generate and print happy tweet
                generated = true;
            }
            else if(mood.equals("sad")){
                //generate and print sad tweet
                generated = true;
            }
            else if(mood.equals("angry")){
                //generate and print angry tweet
                generated = true;
            }
            else if(mood.equals("troll")){
                //generate and print troll tweet
                generated = true;
            }
            else{
                System.out.print("Please pick happy, sad, angry, or troll: ");
                mood = s.next();
            }
        }
        
        Boolean good = false;
        System.out.print("Do you like this tweet? (yes or no): ");
        String yn = s.next();
        if(yn.equals("yes")){
            good = true;
        }
        
    }
	
}
