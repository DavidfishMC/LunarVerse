package LunarVerse;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class TimedLoop {
    public static void main(String[] args) throws InterruptedException, IOException {
        Scanner input = new Scanner(System.in);
        String[] lots = {"🍒", "🍊", "🧲", "🍉", "🔔"};
        String[] finalLots = new String[3];
        int finalIndex = 0;

        System.out.print("Press any button to start rolling on the slot machine!");
        input.next(); // Wait for the user to press any button

        while (finalIndex < finalLots.length) {
            for (String emoji : lots) {
                if (finalIndex >= finalLots.length) {
                    break;
                }

                System.out.print("\r" + emoji); // Print the emoji on the same line
                TimeUnit.MILLISECONDS.sleep(200); // 1-second delay

                if (System.in.available() > 0) { // Check if there is any user input
                    String selection = input.next();
                    if ("c".equalsIgnoreCase(selection)) {
                        finalLots[finalIndex] = emoji;
                        finalIndex++;
                        System.out.println("\nYou picked: " + emoji);
                    }
                }
            }
        }

        System.out.println("Final selection:");
        for (String finalEmoji : finalLots) {
            System.out.println(finalEmoji);
        }
    }
}

