package LunarVerse;

public class Card {
	
	String suit;
	String suitInfo;
	String rank;
	int rankValue;
	
	public Card(int i, String s) {
		suitInfo = s;
		switch (i) {
		case 1:
			rank = "A";
			break;
		case 2:
			rank = "2";
			break;
		case 3:
			rank = "3";
			break;
		case 4:
			rank = "4";
			break;
		case 5:
			rank = "4";
			break;
		case 6:
			rank = "6";
			break;
		case 7:
			rank = "7";
			break;
		case 8:
			rank = "8";
			break;
		case 9:
			rank = "9";
			break;
		case 10:
			rank = "10";
			break;
		case 11:
			rank = "J";
			break;
		case 12:
			rank = "Q";
			break;
		case 13:
			rank = "K";
			break;
		}
		switch (s) {
		case "hearts":
			suit = "♥";
			break;
		case "spades":
			suit = "♠";
			break;
		case "clubs":
			suit = "♣";
			break;
		case "diamonds":
			suit = "♦";
			break;
		}
	}
	
	public Card(int i, int s) {
		switch (i) {
		case 1:
			rank = "A";
			rankValue = 12;
			break;
		case 2:
			rank = "2";
			rankValue = 0;
			break;
		case 3:
			rank = "3";
			rankValue = 1;
			break;
		case 4:
			rank = "4";
			rankValue = 2;
			break;
		case 5:
			rank = "4";
			rankValue = 3;
			break;
		case 6:
			rank = "6";
			rankValue = 4;
			break;
		case 7:
			rank = "7";
			rankValue = 5;
			break;
		case 8:
			rank = "8";
			rankValue = 6;
			break;
		case 9:
			rank = "9";
			rankValue = 7;
			break;
		case 10:
			rank = "10";
			rankValue = 8;
			break;
		case 11:
			rank = "J";
			rankValue = 9;
			break;
		case 12:
			rank = "Q";
			rankValue = 10;
			break;
		case 13:
			rank = "K";
			rankValue = 11;
			break;
		}
		switch (s) {
		case 1:
			suit = "♥";
			suitInfo = "hearts";
			break;
		case 2:
			suit = "♠";
			suitInfo = "spades";
			break;
		case 3:
			suit = "♣";
			suitInfo = "clubs";
			break;
		case 4:
			suit = "♦";
			suitInfo = "diamonds";
			break;
		}
	}
	
	public String getSuit() {
		return suitInfo;
	}
	
	public String getRank() {
		return rank;
	}
	
	public int getRankValue() {
		return rankValue;
	}
	
	public String toString() {
		return suit + " " + rank + " " + suit;
	}

}
