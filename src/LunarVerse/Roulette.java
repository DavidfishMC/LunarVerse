package LunarVerse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Roulette {
	
	Player dealer;
	Player player;
	ArrayList<Card> dealerHand = new ArrayList<Card>();
	ArrayList<Card> playerHand = new ArrayList<Card>();
	ArrayList<Card> dealerHandHole = new ArrayList<Card>();
	ArrayList<Card> playerHandHole = new ArrayList<Card>();
	ArrayList<Card> deck = new ArrayList<Card>();
	ArrayList<Card> pile = new ArrayList<Card>();
	int dealerPoints = 0;
	int playerPoints = 0;
	String dealerHandWinning = "High Card";
	String playerHandWinning = "High Card";
	
	public Roulette(Player p, Player a) {
		dealer = p;
		player = a;
		for(int i = 1; i < 5; i++) {
			for(int j = 1; j < 14; j++) {
				Card c = new Card(j, i);
				deck.add(c);
			}
		}
	}
	
	public void startGame() {
		try {
			String audio = "poker.wav";
			Music victoryPlayer = new Music(audio, false);
			victoryPlayer.play();
		} catch (Exception e) {
			System.out.println(e);
		}
		Scanner input = new Scanner(System.in);
		String userInput = "";
		Collections.shuffle(deck);
		Collections.shuffle(deck);
		Collections.shuffle(deck);
		Collections.shuffle(deck);
		List<Card> firstTwoCards = deck.subList(0, 2);
		dealerHandHole.addAll(firstTwoCards);
	    deck.removeAll(firstTwoCards);
	    for(Card c: dealerHandHole) {
	    	System.out.println(c);
	    }
	    System.out.print("Here are your first 2 cards " + dealer.getSkin() + ", keep them in mind: ");
		String targetResponse = input.next();
		for(int i = 0; i < 100; i++) {
        	System.out.println();
        }
	    
	    List<Card> firstTwo2Cards2 = deck.subList(0, 2);
	    playerHandHole.addAll(firstTwo2Cards2);
	    deck.removeAll(firstTwo2Cards2);
	    for(Card c: playerHandHole) {
	    	System.out.println(c);
	    }
	    System.out.print("Here are your first 2 cards " + player.getSkin() + ", keep them in mind: ");
		String targetResponse2 = input.next();
		for(int i = 0; i < 100; i++) {
        	System.out.println();
        }
		
		List<Card> firstSixCards = deck.subList(0, 6);
		pile.addAll(firstSixCards);
		deck.removeAll(firstSixCards);
		
		int k = 0;
		while(k < 3) { //First round.
			System.out.println();
			for(int i = 1; i < pile.size() + 1; i++) {
	        	System.out.println(i + ": " + pile.get(i - 1));
	        }
			System.out.print(dealer.getSkin() + ", pick 3 cards from the community pile that you want: ");
			String response = input.next();
			int responseInt = Integer.valueOf(response) - 1;
			dealerHand.add(pile.get(responseInt));
			pile.remove(responseInt);
			k++;
			for(int i = 0; i < 100; i++) {
	        	System.out.println();
	        }
		}
		
		System.out.println();
		for(int i = 1; i < pile.size() + 1; i++) {
        	System.out.println(i + ": " + pile.get(i - 1));
        }
		System.out.print(player.getSkin() + ", you will get the rest of the pile.");
		playerHand.addAll(pile);
		pile.clear();
		System.out.println();
		
		for(int i = 1; i < 3; i++) {
			System.out.println();
			int u = 0;
			int h = 0;
			int o = 0;
			int q = 0;
			String response = "";
			String response2 = "";
			System.out.println("Round: " + i + ". Make sure to finish with 3 cards in hand by the end of round 2!");
			List<Card> newCards = deck.subList(0, 6);
			pile.addAll(newCards);
			deck.removeAll(newCards);
			
			while(u < 3 && !response.equals("c")) { //First round.
				System.out.println();
				System.out.println("Pile:");
				for(int t = 1; t < pile.size() + 1; t++) {
		        	System.out.println(t + ": " + pile.get(t - 1));
		        }
				System.out.println();
				System.out.println("Your hand:");
				for(int t = 1; t < dealerHand.size() + 1; t++) {
		        	System.out.println(t + ": " + dealerHand.get(t - 1));
		        }
				System.out.print(dealer.getSkin() + ", pick cards to remove from your hand if you wish: ");
				response = input.next();
				int responseInt = 0;
				try {
					responseInt = Integer.valueOf(response) - 1;
					dealerHand.remove(responseInt);
					u++;
				} catch (NumberFormatException e) {
				    
				}
				for(int a = 0; a < 100; a++) {
		        	System.out.println();
		        }
			}
			response = "";
			
			while(dealerHand.size() < 3) { //First round.
				System.out.println();
				System.out.println("Pile:");
				for(int t = 1; t < pile.size() + 1; t++) {
		        	System.out.println(t + ": " + pile.get(t - 1));
		        }
				System.out.println();
				System.out.println("Your hand:");
				for(int t = 1; t < dealerHand.size() + 1; t++) {
		        	System.out.println(t + ": " + dealerHand.get(t - 1));
		        }
				System.out.print(dealer.getSkin() + ", pick cards to fill your hand if you wish: ");
				response = input.next();
				int responseInt = 0;
				try {
					responseInt = Integer.valueOf(response) - 1;
					dealerHand.add(pile.get(responseInt));
					pile.remove(responseInt);
				} catch (NumberFormatException e) {
				    
				}
				for(int w = 0; w < 100; w++) {
		        	System.out.println();
		        }
			}
			
			while(o < 3 && !response2.equals("c")) { 
				System.out.println();
				System.out.println("Pile:");
				for(int t = 1; t < pile.size() + 1; t++) {
		        	System.out.println(t + ": " + pile.get(t - 1));
		        }
				System.out.println();
				System.out.println("Your hand:");
				for(int t = 1; t < playerHand.size() + 1; t++) {
		        	System.out.println(t + ": " + playerHand.get(t - 1));
		        }
				System.out.print(player.getSkin() + ", pick cards to remove from your hand if you wish: ");
				response2 = input.next();
				int responseInt = 0;
				try {
					responseInt = Integer.valueOf(response2) - 1;
					playerHand.remove(responseInt);
					o++;
				} catch (NumberFormatException e) {
				    
				}
				for(int f = 0; f < 100; f++) {
		        	System.out.println();
		        }
			}
			response2 = "";
			
			while(playerHand.size() < 3) { //First round.
				System.out.println();
				System.out.println("Pile:");
				for(int t = 1; t < pile.size() + 1; t++) {
		        	System.out.println(t + ": " + pile.get(t - 1));
		        }
				System.out.println();
				System.out.println("Your hand:");
				for(int t = 1; t < playerHand.size() + 1; t++) {
		        	System.out.println(t + ": " + playerHand.get(t - 1));
		        }
				System.out.print(player.getSkin() + ", pick cards to fill your hand if you wish: ");
				response = input.next();
				int responseInt = 0;
				try {
					responseInt = Integer.valueOf(response) - 1;
					playerHand.add(pile.get(responseInt));
					pile.remove(responseInt);
				} catch (NumberFormatException e) {
				    
				}
				for(int n = 0; n < 100; n++) {
		        	System.out.println();
		        }
			}
			
			pile.clear();
			
			
		}
		
		dealerHand.addAll(dealerHandHole);
		playerHand.addAll(playerHandHole);
		if (winner() == null) {
			player.takeDamage(1400);
			dealer.addDamage(1400);
			ArrayList<Effect> e = new ArrayList<Effect>();
			Effect SolarIgnite = new Effect("stun", 0, 1);
			e.add(SolarIgnite);
			player.addEffects(e);
			player.applyEffects();
			System.out.println("\"A tie!? Well you still lose by those terms!\"");
			System.out.println();
		}
		System.out.println();
		System.out.println("Here are the resulting hands:");
		System.out.println();
		System.out.println(dealer.getSkin() + "'s hand. This is a " + dealerHandWinning + " hand.");
		for(int t = 1; t < dealerHand.size() + 1; t++) {
        	System.out.println(t + ": " + dealerHand.get(t - 1));
        }
		System.out.println();
		System.out.println(player.getSkin() + "'s hand. This is a " + playerHandWinning + " hand.");
		for(int t = 1; t < playerHand.size() + 1; t++) {
        	System.out.println(t + ": " + playerHand.get(t - 1));
        }
		
		System.out.println();
		System.out.println("The winner is " + winner().getSkin() + "!");
		
		if(winner().equals(dealer)) {
			player.takeDamage(1400);
			dealer.addDamage(1400);
			ArrayList<Effect> e = new ArrayList<Effect>();
			Effect SolarIgnite = new Effect("stun", 0, 1);
			e.add(SolarIgnite);
			player.addEffects(e);
			player.applyEffects();
			System.out.println("\"Undefeated baby! Woooooo!\"");
			System.out.println();
		}else {
			player.takeDamage(500);
			dealer.addDamage(500);
			System.out.println("\"Damn what!?! Did you cheat!?\"");
			System.out.println();
		}
	    
	}
	
	public Player winner() {
		if (dealerHand.size() != 5) {
			return player;
		}
		if(playerHand.size() != 5) {
			return dealer;
		}
		
		if(isPair(dealerHand)) {
			dealerPoints = 2;
			dealerHandWinning = "Pair";
		}
		if(isPair(playerHand)) {
			playerPoints = 2;
			playerHandWinning = "Pair";
		}
		
		if(isTwoPair(dealerHand)) {
			dealerPoints = 3;
			dealerHandWinning = "Two Pair";
		}
		if(isTwoPair(playerHand)) {
			playerPoints = 3;
			playerHandWinning = "Two Pair";
		}
		
		if(isThreeOfAKind(dealerHand)) {
			dealerPoints = 4;
			dealerHandWinning = "Three Of A Kind";
		}
		if(isThreeOfAKind(playerHand)) {
			playerPoints = 4;
			playerHandWinning = "Three Of A Kind";
		}
		
		if(isStraight(dealerHand)) {
			dealerPoints = 5;
			dealerHandWinning = "Straight";
		}
		if(isStraight(playerHand)) {
			playerPoints = 5;
			playerHandWinning = "Straight";
		}
		
		if(isFlush(dealerHand)) {
			dealerPoints = 6;
			dealerHandWinning = "Flush";
		}
		if(isFlush(playerHand)) {
			playerPoints = 6;
			playerHandWinning = "Flush";
		}
		
		if(isFullHouse(dealerHand)) {
			dealerPoints = 7;
			dealerHandWinning = "Full House";
		}
		if(isFullHouse(playerHand)) {
			playerPoints = 7;
			playerHandWinning = "Full House";
		}
		
		if(isFourOfAKind(dealerHand)) {
			dealerPoints = 8;
			dealerHandWinning = "Four Of A Kind";
		}
		if(isFourOfAKind(playerHand)) {
			playerPoints = 8;
			playerHandWinning = "Four Of A Kind";
		}
		
		if(isStraightFlush(dealerHand)) {
			dealerPoints = 9;
			dealerHandWinning = "Straight Flush";
		}
		if(isStraightFlush(playerHand)) {
			playerPoints = 9;
			playerHandWinning = "Straight Flush";
		}
		
		if(hasRoyalFlush(dealerHand)) {
			dealerPoints = 10;
			dealerHandWinning = "Royal Flush";
		}
		if(hasRoyalFlush(playerHand)) {
			playerPoints = 10;
			playerHandWinning = "Royal Flush";
		}
		
		if(dealerPoints > playerPoints) {
			return dealer;
		}else if (playerPoints > dealerPoints) {
			return player;
		}else {
			return null;
		}
		
	}
	
	public static boolean hasRoyalFlush(ArrayList<Card> dealerHand) {
        // Check if the hand has exactly 5 cards
        if (dealerHand.size() != 5) {
            return false;
        }

        // Check if all cards have the same suit
        String firstSuit = dealerHand.get(0).getSuit();
        for (Card card : dealerHand) {
            if (!card.getSuit().equals(firstSuit)) {
                return false;
            }
        }

        // Check if the ranks are 10, J, Q, K, and A
        String[] expectedRanks = {"10", "J", "Q", "K", "A"};
        for (String rank : expectedRanks) {
            boolean found = false;
            for (Card card : dealerHand) {
                if (card.getRank().equals(rank)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }

        // If all conditions are met, it's a royal flush
        return true;
    }
	
	public static boolean isFlush(List<Card> hand) {
        String firstSuit = hand.get(0).getSuit();
        return hand.stream().allMatch(card -> card.getSuit().equals(firstSuit));
    }
	
	public static boolean isStraight(List<Card> hand) {
        int[] cardValues = new int[13]; // One entry for each rank (2 to A)
        for (Card card : hand) {
            int value = card.getRankValue(); // Map rank to an index (e.g., A=12)
            cardValues[value]++;
        }

        for (int i = 0; i <= 9; i++) {
            if (cardValues[i] > 0) {
                boolean isStraight = true;
                for (int j = i + 1; j < i + 5; j++) {
                    if (cardValues[j] == 0) {
                        isStraight = false;
                        break;
                    }
                }
                if (isStraight) {
                    return true;
                }
            }
        }
        return false;
    }
	
	public static boolean isStraightFlush(List<Card> hand) {
        return isFlush(hand) && isStraight(hand);
    }
	
	public static boolean isFourOfAKind(List<Card> hand) {
        int[] rankCounts = new int[13]; // One entry for each rank (2 to A)

        // Count the occurrences of each rank
        for (Card card : hand) {
            int rankValue = card.getRankValue(); // Map rank to an index
            rankCounts[rankValue]++;
        }

        // Check if any rank appears four times
        for (int count : rankCounts) {
            if (count == 4) {
                return true; // Found Four of a Kind
            }
        }

        return false; // Not Four of a Kind
    }
	
	public static boolean isFullHouse(List<Card> hand) {
        int[] rankCounts = new int[13]; // One entry for each rank (2 to A)

        // Count the occurrences of each rank
        for (Card card : hand) {
            int rankValue = card.getRankValue(); // Map rank to an index
            rankCounts[rankValue]++;
        }

        boolean hasThreeOfAKind = false;
        boolean hasPair = false;

        for (int count : rankCounts) {
            if (count == 3) {
                hasThreeOfAKind = true;
            } else if (count == 2) {
                hasPair = true;
            }
        }

        return hasThreeOfAKind && hasPair;
    }
	
	public static boolean isThreeOfAKind(List<Card> hand) {
        int[] rankCounts = new int[13]; // One entry for each rank (2 to A)

        // Count the occurrences of each rank
        for (Card card : hand) {
            int rankValue = card.getRankValue(); // Map rank to an index
            rankCounts[rankValue]++;
        }

        // Check if any rank appears three times
        for (int count : rankCounts) {
            if (count == 3) {
                return true; // Found Three of a Kind
            }
        }

        return false; // Not Three of a Kind
    }
	
	public static boolean isTwoPair(List<Card> hand) {
        int[] rankCounts = new int[13]; // One entry for each rank (2 to A)

        // Count the occurrences of each rank
        for (Card card : hand) {
            int rankValue = card.getRankValue(); // Map rank to an index
            rankCounts[rankValue]++;
        }

        int pairCount = 0;
        for (int count : rankCounts) {
            if (count == 2) {
                pairCount++;
            }
        }

        return pairCount == 2; // Two Pair if exactly two pairs found
    }
	
	public static boolean isPair(List<Card> hand) {
        int[] rankCounts = new int[13]; // One entry for each rank (2 to A)

        // Count the occurrences of each rank
        for (Card card : hand) {
            int rankValue = card.getRankValue(); // Map rank to an index
            rankCounts[rankValue]++;
        }

        // Check if any rank appears exactly twice
        for (int count : rankCounts) {
            if (count == 2) {
                return true; // Found a pair
            }
        }

        return false; // Not a pair
    }
	
	
	
	
	
	
	
	
	
	

}
