package LunarVerse;

import java.util.Scanner;

public class TimedLoop {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis(); // Get the start time
        long elapsedTime = 0L;
        Scanner input = new Scanner(System.in);
        int score = 1;
        char []alphabet = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 
                'h', 'i', 'j', 'k', 'l', 'm', 'n',  
                'o', 'p', 'q', 'r', 's', 't', 'u', 
                'v', 'w', 'x', 'y', 'z' }; 

        while (elapsedTime < 10*1000) { // Check if less than 5 seconds have passed
            // Your repeated action goes here
        	String res = ""; 
        	for (int i = 0; i < 1; i++)  
            	res = res + alphabet[(int) (Math.random() * 100 % 26)]; 
            System.out.print("Charge up the attack by pressing " + res + ": ");
            String targetResponse = input.next();
            if(targetResponse.equals(String.valueOf(res))) {
            	score++;
            	System.out.println(score);
            }else {
            	break;
            }
            // Update the elapsed time
            elapsedTime = System.currentTimeMillis() - startTime;
        }
        System.out.println("Finished");
    }
}
