package LunarVerse;

import java.util.ArrayList;

public class Battlefield {
	
	Location one;
	Location two;
	Location three;
	Location four;
	Location five;
	Location six;
	Location cursor;
	Player a;
	Player b;
	Player c;
	Player d;
	Player e;
	Player f;
	String[][] field = new String[42][42];
	int[][] foreground = new int[42][42];
	int[][] background = new int[42][42];
	ArrayList<Tile> tiles = new ArrayList<Tile>();
	ArrayList<Player> players = new ArrayList<Player>();
	String name;
	double cursorRange = 0;
	static final String reset = "\u001B[0m";
	static final String color = "\u001b[38;5;";
	static final String back = "\u001b[48;5;";
	static final String clear = "\u001b[22m";
	static final String bold = "\u001b[1m";
	public static boolean endgame = false;
	public static boolean temple = false;
	boolean start = false;
	Party trenchParty = null;
	Location trenchLoc = null;

	public Battlefield(String nameIn, Player aIn, Player bIn, Player cIn, Player dIn, Player eIn, Player fIn) {
		a = aIn;
		b = bIn;
		c = cIn;
		d = dIn;
		e = eIn;
		f = fIn;
		players.add(a);
		players.add(b);
		players.add(c);
		players.add(d);
		players.add(e);
		players.add(f);
		name = nameIn;
		
		if(name.equals("Galactical Laboratories")) {
			Tile t = new Tile("Rift", new Location(9,9));
			Tile t2 = new Tile("Rift", new Location(32,9));
			Tile t3 = new Tile("Rift", new Location(9,32));
			Tile t4 = new Tile("Rift", new Location(32,32));
			tiles.add(t);
			tiles.add(t2);
			tiles.add(t3);
			tiles.add(t4);
		}
		if(name.equals("Alterna Outlands")){
			for(int i = 10; i < 31; i++) {
				Tile t = new Tile("Trench", new Location(i, 13));
				tiles.add(t);
			}
			for(int i = 10; i < 31; i++) {
				Tile t = new Tile("Trench", new Location(i, 28));
				tiles.add(t);
			}
			for(int i = 14; i < 28; i++) {
				Tile t = new Tile("Trench", new Location(10, i));
				tiles.add(t);
			}
			for(int i = 14; i < 28; i++) {
				Tile t = new Tile("Trench", new Location(30, i));
				tiles.add(t);
			}
			
			for(int i = 9; i < 32; i++) {
				Tile t = new Tile("Trench", new Location(i, 12));
				tiles.add(t);
			}
			for(int i = 9; i < 32; i++) {
				Tile t = new Tile("Trench", new Location(i, 29));
				tiles.add(t);
			}
			for(int i = 13; i < 29; i++) {
				Tile t = new Tile("Trench", new Location(9, i));
				tiles.add(t);
			}
			for(int i = 13; i < 29; i++) {
				Tile t = new Tile("Trench", new Location(31, i));
				tiles.add(t);
			}
			for(int i = 0; i < tiles.size(); i++) {
				Tile t = tiles.get(i);
				if(t.getLoc().eqLoc(new Location(9,25))) {
					tiles.remove(t);
					i--;
				}
				if(t.getLoc().eqLoc(new Location(9,26))) {
					tiles.remove(t);
					i--;
				}
				if(t.getLoc().eqLoc(new Location(10,25))) {
					tiles.remove(t);
					i--;
				}
				if(t.getLoc().eqLoc(new Location(10,26))) {
					tiles.remove(t);
					i--;
				}
				if(t.getLoc().eqLoc(new Location(30,15))) {
					tiles.remove(t);
					i--;
				}
				if(t.getLoc().eqLoc(new Location(30,16))) {
					tiles.remove(t);
					i--;
				}
				if(t.getLoc().eqLoc(new Location(31,15))) {
					tiles.remove(t);
					i--;
				}
				if(t.getLoc().eqLoc(new Location(31,16))) {
					tiles.remove(t);
					i--;
				}
			}
		}
		
		if(name.equals("Merge Castle")) {
			for(int i = 17; i < 25; i++) {
				Tile t = new Tile("Firepower", new Location(i, 4));
				tiles.add(t);
			}
			for(int i = 17; i < 25; i++) {
				Tile t = new Tile("Firepower", new Location(i, 37));
				tiles.add(t);
			}
			for(int i = 17; i < 25; i++) {
				Tile t = new Tile("Firepower", new Location(4, i));
				tiles.add(t);
			}
			for(int i = 17; i < 25; i++) {
				Tile t = new Tile("Firepower", new Location(37, i));
				tiles.add(t);
			}
		}
		
		if(name.equals("Nexus Village")) {
			Tile t = new Tile("Tower", new Location(20,14));
			Tile t2 = new Tile("Tower", new Location(21,14));
			Tile t3 = new Tile("Tower", new Location(20,27));
			Tile t4 = new Tile("Tower", new Location(21,27));
			tiles.add(t);
			tiles.add(t2);
			tiles.add(t3);
			tiles.add(t4);
		}
		
		if(name.equals("Novafuel Industries")) {
			Tile t = new Tile("Bounce", new Location(17,17));
			Tile t2 = new Tile("Bounce", new Location(24,17));
			Tile t3 = new Tile("Bounce", new Location(17,24));
			Tile t4 = new Tile("Bounce", new Location(24,24));
			tiles.add(t);
			tiles.add(t2);
			tiles.add(t3);
			tiles.add(t4);
		}
		
		if(name.equals("Timed Ruins")) {
			for(int i = 16; i < 26; i++) {
				Tile t = new Tile("Time", new Location(i, 16));
				tiles.add(t);
			}
			for(int i = 16; i < 26; i++) {
				Tile t = new Tile("Time", new Location(i, 25));
				tiles.add(t);
			}
			for(int i = 16; i < 26; i++) {
				Tile t = new Tile("Time", new Location(16, i));
				tiles.add(t);
			}
			for(int i = 16; i < 26; i++) {
				Tile t = new Tile("Time", new Location(25, i));
				tiles.add(t);
			}
		}
		
		if(name.equals("Velocity Ville")) {
			Tile t5 = new Tile("Car", new Location(13,14));
			Tile t6 = new Tile("Car", new Location(14,13));
			Tile t7 = new Tile("Car", new Location(15,14));
			Tile t8 = new Tile("Car", new Location(14,15));
			Tile t = new Tile("Car", new Location(14,14));
			
			Tile t2 = new Tile("Car", new Location(27,14));
			Tile f = new Tile("Car", new Location(26,14));
			Tile f1 = new Tile("Car", new Location(14,26));
			Tile f2 = new Tile("Car", new Location(28,14));
			Tile f3 = new Tile("Car", new Location(14,28));
			
			Tile f4 = new Tile("Car", new Location(13,27));
			Tile f5 = new Tile("Car", new Location(15,27));
			
			Tile f6 = new Tile("Car", new Location(27,13));
			Tile f7 = new Tile("Car", new Location(27,15));
			
			Tile r = new Tile("Car", new Location(26,27));
			Tile r1 = new Tile("Car", new Location(28,27));
			
			Tile r2 = new Tile("Car", new Location(27,26));
			Tile r3 = new Tile("Car", new Location(27,28));
			
			Tile t3 = new Tile("Car", new Location(14,27));
			
			Tile t4 = new Tile("Car", new Location(27,27));
			
			tiles.add(t);
			tiles.add(t2);
			tiles.add(t3);
			tiles.add(t4);
			tiles.add(t5);
			tiles.add(t6);
			tiles.add(t7);
			tiles.add(t8);
			tiles.add(f);
			tiles.add(f1);
			tiles.add(f2);
			tiles.add(f3);
			tiles.add(f4);
			tiles.add(f5);
			tiles.add(f6);
			tiles.add(f7);
			tiles.add(r);
			tiles.add(r1);
			tiles.add(r2);
			tiles.add(r3);
		}
	}
	
	public void setCursor(Location l) {
		cursor = l;
	}
	
	public void moveCursor(Location l) {
		cursor = l;
	}
	
	public void removeCursor() {
		cursor = null;
	}
	
	public void printMochiField(Player a, Player b, Player c, Player d, Player e, Player f, ArrayList<Orb> orbLoc, ArrayList<Cover> coverLoc, Player s, Player e1, Player e2, Player e3, ArrayList<Utility> utilityLoc) {
		one = a.getLoc();
		two = b.getLoc();
		three = c.getLoc();
		four = d.getLoc();
		five = e.getLoc();
		six = f.getLoc();
		for(int i = 0; i < 42; i++) {
			for(int j = 0; j < 42; j++){
				field[i][j] = " ";
				foreground[i][j] = 0;
				background[i][j] = 999;
				for(Utility u: utilityLoc) {
					if(u.getName().equals("Dragon")) {
						//background[i][j] = 220;
					}
				}
			}
		}
		
		for (Utility rook : utilityLoc) {
	        if (rook.getName().equals("Rook")) {
	        	switch (rook.getDirection()) {
	        	case "left":
	        		rook.getLoc().set(rook.owner.getLoc().getX() - 2, rook.owner.getLoc().getY());
	        		break;
	        	case "right":
	        		rook.getLoc().set(rook.owner.getLoc().getX() + 2, rook.owner.getLoc().getY());
	        		break;
	        	case "up":
	        		rook.getLoc().set(rook.owner.getLoc().getX(), rook.owner.getLoc().getY() - 2);
	        		break;
	        	case "down":
	        		rook.getLoc().set(rook.owner.getLoc().getX(), rook.owner.getLoc().getY() + 2);
	        		break;
	        	}
	        }
	    }
		
		if (temple) {
			for(int i = 14; i < 28; i++) {
				background[i][0] = 196;
				background[0][i] = 196;
				background[i][41] = 196;
				background[41][i] = 196;
			}
		}
		
		for(int i = 0; i < 42; i++) {
			for(int j = 0; j < 42; j++){
				Location l = new Location(i, j);
				if(e1.inRange(l) && e1.hasSights() && !e1.isStunned()) {
					field[j][i] = "∙";
					foreground[j][i] = 196;
				}
			}
		}
		for(int i = 0; i < 42; i++) {
			for(int j = 0; j < 42; j++){
				Location l = new Location(i, j);
				if(e2.inRange(l) && e2.hasSights() && !e2.isStunned()) {
					field[j][i] = "∙";
					foreground[j][i] = 196;
				}
			}
		}
		for(int i = 0; i < 42; i++) {
			for(int j = 0; j < 42; j++){
				Location l = new Location(i, j);
				if(e3.inRange(l) && e3.hasSights() && !e3.isStunned()) {
					field[j][i] = "∙";
					foreground[j][i] = 196;
				}
			}
		}
		
		Utility mochi = null;
		
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).getName().equals("Mochi") && GameSim.utility.get(j).owner(s)) {
				mochi = GameSim.utility.get(j);
			}
		}
		
		for(int i = 0; i < 42; i++) {
			for(int j = 0; j < 42; j++){
				Location l = new Location(i, j);
				if(mochi.inRange(l)) {
					if(!l.eqLoc(mochi.getLoc())) {
						background[j][i] = 255;
					}
				}
			}
		}
		
		
		
			for(int i = 0; i < 42; i++) {
				for(int j = 0; j < 42; j++){
					Location l = new Location(i, j);
					if(mochi.inReach(l) && !s.ultActive()) {
						if(!l.eqLoc(mochi.getLoc())) {
							background[j][i] = 254;
						}
					}
				}
			}
		
		if(s.getPack()) {
			for(int i = 0; i < 42; i++) {
				for(int j = 0; j < 42; j++){
					Location l = new Location(i, j);
					if(s.inRange(l, 5)) {
						if(!l.eqLoc(s.getLoc())) {
							background[j][i] = 223;
						}
					}
				}
			}
		}
		
		if(s.getMochiMovement() > 4){
			for(int i = 0; i < 42; i++) {
				for(int j = 0; j < 42; j++){
					Location l = new Location(i, j);
					if(mochi.inRange(l, 4)) {
						//field[j][i] = "∙";
						//field[j][i]= "\u001B[41m" + " " + reset;
						background[j][i] = 255;
					}
				}
			}
		}
		
		int closeRange = -1;
		int farRange = -1;
		
		switch (s.getName()) {
			case "Aidan":
				closeRange = 4;
				farRange = 10;
				break;
			default:
				break;
		}
		
		if (start) {
			for(int i = 0; i < 42; i++) {
				for(int j = 0; j < 42; j++){
					Location l = new Location(i, j);
					if(new Location(4, 4).inRange(l, 3)) {
						background[j][i] = 195;
					}
				}
				for(int j = 0; j < 42; j++){
					Location l = new Location(i, j);
					if(new Location(37, 37).inRange(l, 3)) {
						background[j][i] = 195;
					}
				}
			}
		}
		
		
		
		for(int i = 0; i < 42; i++) {
			for(int j = 0; j < 42; j++){
				
				
				
				for(Utility u: utilityLoc) {
					if(i == u.getLoc().getY() && j == u.getLoc().getX()) {
						if(u.getName().equals("Swiftwing") && !u.getPickUp()) {
							field[i][j] = "S";
							foreground[i][j] = 0;
							background[i][j] = 213;
						}
						if(u.getName().equals("Howler") && !u.getPickUp()) {
							field[i][j] = "H";
							foreground[i][j] = 0;
							background[i][j] = 93;
						}
						if(u.getName().equals("Star") && !u.getPickUp()) {
							field[i][j] = "★";
							foreground[i][j] = 0;
							background[i][j] = 194;
						}
						if(u.getName().equals("Butterfly") && !u.getPickUp()) {
							field[i][j] = "🦋";
							foreground[i][j] = 0;
							background[i][j] = 183;
						}
						if(u.getName().equals("Spirit") && !u.getPickUp()) {
							field[i][j] = "S";
							foreground[i][j] = 0;
							background[i][j] = 218;
						}
						if(u.getName().equals("Crane") && !u.getPickUp() && u.isAlly(s)) {
							field[i][j] = "c";
							foreground[i][j] = 0;
							background[i][j] = 224;
						}
						if(u.getName().equals("Nebula") && !u.getPickUp()) {
							field[i][j] = "&";
							foreground[i][j] = 165;
						}
						if(u.getName().equals("Sensor")) {
							field[i][j] = "S";
							foreground[i][j] = 0;
							background[i][j] = 193;
							for(int k = 0; k < 42; k++) {
								for(int h = 0; h < 42; h++){
									Location l = new Location(k, h);
									if(u.atRange(l, 5) && u.isEnemy(s)) {
										background[h][k] = 193;
									}
								}
							}
						}
						if(u.getName().equals("Sphere")) {
							field[i][j] = "Y";
							foreground[i][j] = 0;
							background[i][j] = 299;
							for(int k = 0; k < 42; k++) {
								for(int h = 0; h < 42; h++){
									Location l = new Location(k, h);
									if(u.atRange(l, 8) && u.isEnemy(s)) {
										background[h][k] = 229;
									}
								}
							}
						}
						if(u.getName().equals("Starpull")) {
							field[i][j] = "A";
							foreground[i][j] = 0;
							background[i][j] = 141;
							for(int k = 0; k < 42; k++) {
								for(int h = 0; h < 42; h++){
									Location l = new Location(k, h);
									if(u.atRange(l, 11) && u.isEnemy(s)) {
										background[h][k] = 141;
									}
								}
							}
						}
						if(u.getName().equals("Wind")) {
							field[i][j] = "W";
							foreground[i][j] = 0;
							background[i][j] = 220;
						}
						if(u.getName().equals("Respawn")) {
							field[i][j] = "R";
							foreground[i][j] = 0;
							background[i][j] = 225;
						}
						if(u.getName().equals("Explosive")) {
							field[i][j] = "E";
							foreground[i][j] = 0;
							background[i][j] = 196;
							for(int k = 0; k < 42; k++) {
								for(int h = 0; h < 42; h++){
									Location l = new Location(k, h);
									if(endgame) {
										if(u.atRange(l, 5) && u.isEnemy(s)) {
											background[h][k] = 214;
										}else if(u.atRange(l, 10) && u.isEnemy(s)) {
											background[h][k] = 220;
										}
										if(u.atRange(l, 0) && u.isEnemy(s)) {
											background[h][k] = 196;
										}
									}else {
										if(u.atRange(l, 8) && u.isEnemy(s)) {
											background[h][k] = 214;
										}else if(u.atRange(l, 15) && u.isEnemy(s)) {
											background[h][k] = 220;
										}
										if(u.atRange(l, 0) && u.isEnemy(s)) {
											background[h][k] = 196;
										}
									}
								}
							}
						}
						if(u.getName().equals("Gemstone")) {
							field[i][j] = "G";
							foreground[i][j] = 0;
							background[i][j] = 252;
							for(int k = 0; k < 42; k++) {
								for(int h = 0; h < 42; h++){
									Location l = new Location(k, h);
									if(u.atRange(l, 0) && u.isAlly(s) && u.getStone().equals("iron")) {
										field[i][j] = "I";
										background[h][k] = 252;
									}
									if(u.atRange(l, 4) && u.isAlly(s) && u.getStone().equals("emerald")) {
										field[i][j] = "E";
										background[h][k] = 155;
									}
									if(u.atRange(l, 6) && u.isAlly(s) && u.getStone().equals("diamond")) {
										field[i][j] = "D";
										background[h][k] = 159;
									}
									if(u.atRange(l, 8) && u.isAlly(s) && u.getStone().equals("kunzite")) {
										field[i][j] = "K";
										background[h][k] = 225;
									}
								}
							}
						}
						if(u.getName().equals("Field")) {
							field[i][j] = "F";
							foreground[i][j] = 0;
							background[i][j] = 153;
							for(int k = 0; k < 42; k++) {
								for(int h = 0; h < 42; h++){
									Location l = new Location(k, h);
									if(u.atRange(l, 7) && u.isAlly(s)) {
										background[h][k] = 153;
									}
								}
							}
						}
						if(u.getName().equals("Dynamite")) {
							field[i][j] = "D";
							foreground[i][j] = 0;
							background[i][j] = 197;
							for(int k = 0; k < 42; k++) {
								for(int h = 0; h < 42; h++){
									Location l = new Location(k, h);
									if(u.atRange(l, 5) && u.isEnemy(s)) {
										background[h][k] = 197;
									}
								}
							}
						}
						if(u.getName().equals("Peri")) {
							field[i][j] = "P";
							foreground[i][j] = 0;
							background[i][j] = 111;
							for(int k = 0; k < 42; k++) {
								for(int h = 0; h < 42; h++){
									Location l = new Location(k, h);
									if(u.atRange(l, 12) && u.isEnemy(s)) {
										background[h][k] = 111;
									}
								}
							}
						}
						if(u.getName().equals("Fulmination")) {
							for(int k = 0; k < 42; k++) {
								for(int h = 0; h < 42; h++){
									Location l = new Location(k, h);
									if(u.atRange(l, 8) && u.isEnemy(s)) {
										background[h][k] = 63;
									}
								}
							}
						}
						if(u.getName().equals("Bubble")) {
							for(int k = 0; k < 42; k++) {
								for(int h = 0; h < 42; h++){
									Location l = new Location(k, h);
									if(u.atRange(l, u.getSize())) {
										background[h][k] = 87;
									}
								}
							}
						}
						if(u.getName().equals("Steam")) {
							for(int k = 0; k < 42; k++) {
								for(int h = 0; h < 42; h++){
									Location l = new Location(k, h);
									if(u.atRange(l, u.getOwner().getRange()) && u.isEnemy(s) && u.getOwner().isClockwork()) {
										background[h][k] = 136;
									}
								}
							}
						}
						if(u.getName().equals("Flame")) {
							for(int k = 0; k < 42; k++) {
								for(int h = 0; h < 42; h++){
									Location l = new Location(k, h);
									if(u.atRange(l, 5) && u.isEnemy(s)) {
										background[h][k] = 216;
									}
								}
							}
						}
						if(u.getName().equals("Umbrella")) {
							int range = 7;
							if (u.getOwner().ultActive()) {
								range = 10;
							}
							field[i][j] = "U";
							foreground[i][j] = 0;
							background[i][j] = 147;
							for(int k = 0; k < 42; k++) {
								for(int h = 0; h < 42; h++){
									Location l = new Location(k, h);
									if(u.atRange(l, range) && u.isAlly(s)) {
										background[h][k] = 147;
									}
								}
							}
						}
						if(u.getName().equals("Pylon")) {
							field[i][j] = "P";
							foreground[i][j] = 0;
							background[i][j] = 226;
							for(int k = 0; k < 42; k++) {
								for(int h = 0; h < 42; h++){
									Location l = new Location(k, h);
									if(u.atRange(l, 4)) {
										background[h][k] = 226;
									}
								}
							}
						}
						if(u.getName().equals("Matrix")) {
							field[i][j] = "M";
							foreground[i][j] = 0;
							background[i][j] = 225;
							for(int k = 0; k < 42; k++) {
								for(int h = 0; h < 42; h++){
									Location l = new Location(k, h);
									if(u.atRange(l, 6)) {
										background[h][k] = 225;
									}
								}
							}
						}
						if(u.getName().equals("Turret")) {
							field[i][j] = "T";
							foreground[i][j] = 0;
							background[i][j] = 7;
							for(int k = 0; k < 42; k++) {
								for(int h = 0; h < 42; h++){
									Location l = new Location(k, h);
									if(u.atRange(l, 3) && u.isActivated() && u.hasSpikes() && u.isEnemy(s)) {
										background[h][k] = 7;
									}
									if(u.atRange(l, 15) && u.isActivated() && u.isEnemy(s)) {
										background[h][k] = 7;
									}
								}
							}
						}
						if(u.getName().equals("Iron")) {
							foreground[i][j] = 0;
							for(int k = 0; k < 42; k++) {
								for(int h = 0; h < 42; h++){
									Location l = new Location(k, h);
									if(u.atRange(l, 3)) {
										background[h][k] = 65;
									}
								}
							}
						}
						if(u.getName().equals("Rook")) {
							field[i][j] = "R";
							foreground[i][j] = 0;
							if (u.rookActive()) {
								background[i][j] = 220;
							}else {
								background[i][j] = 242;
							}
						}
						if(u.getName().equals("Mochi")) {
							field[i][j] = "🦊";
							foreground[i][j] = 214;
						}
						if(u.getName().equals("Pawn")) {
							field[i][j] = "P";
							foreground[i][j] = 0;
							background[i][j] = 229;
						}
						if(u.getName().equals("Mural")) {
							field[i][j] = "M";
							foreground[i][j] = 0;
							background[i][j] = 217;
							for(int k = 0; k < 42; k++) {
								for(int h = 0; h < 42; h++){
									Location l = new Location(k, h);
								}
							}
						}
						if(u.getName().equals("Sock")) {
							field[i][j] = "S";
							foreground[i][j] = 0;
							background[i][j] = 7;
							for(int k = 0; k < 42; k++) {
								for(int h = 0; h < 42; h++){
									Location l = new Location(k, h);
									if(u.atRange(l, 3) && u.isEnemy(s)) {
										background[h][k] = 7;
									}
								}
							}
						}
						if(u.getName().equals("Support")) {
							field[i][j] = "S";
							foreground[i][j] = 0;
							background[i][j] = 160;
						}
						if(u.getName().equals("Vine")) {
							for(int k = 0; k < 42; k++) {
								for(int h = 0; h < 42; h++){
									Location l = new Location(k, h);
									if(u.atRange(l, 2) && u.isEnemy(s)) {
										background[h][k] = 130;
									}
								}
							}
						}
						if(u.getName().equals("Mine")) {
							field[i][j] = "M";
							foreground[i][j] = 0;
							background[i][j] = 217;
						}
						if(u.getName().equals("Sphere2")) {
							for(int k = 0; k < 42; k++) {
								for(int h = 0; h < 42; h++){
									Location l = new Location(k, h);
									if(u.atRange(l, 4)) {
										if (u.getSphere()) {
											background[h][k] = 121;
										}else {
											background[h][k] = 63;
										}
									}
								}
							}
						}
						if(u.getName().equals("Eclipse")) {
							for(int k = 0; k < 42; k++) {
								for(int h = 0; h < 42; h++){
									Location l = new Location(k, h);
									if(u.atRange(l, 15)) {
										if (u.isEclipse()) {
											background[h][k] = 121;
										}else {
											background[h][k] = 63;
										}
									}
								}
							}
						}
					}
				}
				
				for(Tile t: tiles) {
					if(i == t.getLoc().getY() && j == t.getLoc().getX()) {
						if(t.getName().equals("Rift")) {
							field[i][j] = "@";
							foreground[i][j] = 117;
						}
						if(t.getName().equals("Trench")) {
							field[i][j] = "*" ;
						}
						if(t.getName().equals("Space")) {
							field[i][j] = "^";
						}
						if(t.getName().equals("Firepower")) {
							field[i][j] = ",";
							foreground[i][j] = 202;
						}
						if(t.getName().equals("Tower")) {
							field[i][j] = "#";
							foreground[i][j] = 40;
						}
						if(t.getName().equals("Bounce")) {
							field[i][j] = "%";
							foreground[i][j] = 98;
						}
						if(t.getName().equals("Time")) {
							field[i][j] = "$";
							foreground[i][j] = 30;
						}
						if(t.getName().equals("Car")) {
							field[i][j] = "=";
							foreground[i][j] = 200;
						}
					}
				}
				for(Orb o: orbLoc) {
					if(i == o.getLoc().getY() && j == o.getLoc().getX()) {
						//field[i][j] = bold + "\u001b[38;5;" + 189 + "m" + "🪩" + reset;
						//field[i][j] = "🪩";
						background[i][j] = 225;
						foreground[i][j] = 0;
						field[i][j] = "O";
					}
				}
				for(Cover w: coverLoc) {
					if(i == w.getLoc().getY() && j == w.getLoc().getX()) {
						if(w.getName().equals("Full")) {
							//field[i][j] = "\u001b[38;5;" + 46 + "m" + "🛡️" + reset;
							field[i][j] = "F";
							//field[i][j] = "🛡️";
							foreground[i][j] = 0;
							background[i][j] = 226;
						}
						if(w.getName().equals("Partial")) {
							//field[i][j] = bold + "\u001b[38;5;" + 46 + "m" + "🪨" + reset;
							field[i][j] = "P";
							//field[i][j] = "🪨";
							foreground[i][j] = 0;
							background[i][j] = 195;
						}
					}
				}
				
				
				if(i == one.getY() && j == one.getX()) {
					if(!a.isAlive()) {
						field[i][j] = "-";
					}else {
						field[i][j] = bold + a.getName().substring(0,1) + clear;
						foreground[i][j] = a.skinC();
						//background[i][j] = 255;
					}
				}
				if(i == two.getY() && j == two.getX()) {
					if(!b.isAlive()) {
						field[i][j] = "-";
					}else {
						field[i][j] = bold + b.getName().substring(0,1) + clear;
						foreground[i][j] = b.skinC();
						//background[i][j] = 255;
					}
				}
				if(i == three.getY() && j == three.getX()) {
					if(!c.isAlive()) {
						field[i][j] = "-";
					}else {
						field[i][j] = bold + c.getName().substring(0,1) + clear;
						foreground[i][j] = c.skinC();
						//background[i][j] = 255;
					}
				}
				if(i == four.getY() && j == four.getX()) {
					if(!d.isAlive()) {
						field[i][j] = "-";
					}else {
						field[i][j] = bold + d.getName().substring(0,1) + clear;
						foreground[i][j] = d.skinC();
						//background[i][j] = 255;
					}
				}
				if(i == five.getY() && j == five.getX()) {
					if(!e.isAlive()) {
						field[i][j] = "-";
					}else {
						field[i][j] = bold + e.getName().substring(0,1) + clear;
						foreground[i][j] = e.skinC();
						//background[i][j] = 255;
					}
				}
				if(i == six.getY() && j == six.getX()) {
					if(!f.isAlive()) {
						field[i][j] = "-";
					}else {
						field[i][j] = bold + f.getName().substring(0,1) + clear;
						foreground[i][j] = f.skinC();
						//background[i][j] = 255;
					}
				}
				
				
			}
			
		}
		for(Utility u: utilityLoc) {
			if(u.getName().equals("Smoke")) {
				for(int k = 0; k < 42; k++) {
					for(int h = 0; h < 42; h++){
						Location l = new Location(k, h);
						int range = 4;
						if (u.getOwner().getDarkness()) {
							range = 6;
						}
						if(u.inRange(l, range) && u.isEnemy(s)) {
							background[h][k] = 17;
							field[h][k] = " ";
						}
						if(u.atRange(l, range) && u.isAlly(s)) {
							background[h][k] = 17;
						}
					}
				}
			}
		}
		
		if(cursor != null && cursor.getX() < 42 && cursor.getY() < 42) {
			field[cursor.getY()][cursor.getX()] = "✦";
			for(int i = 0; i < 42; i++) {
				for(int j = 0; j < 42; j++){
					Location l = new Location(i, j);
					if(cursor.inRange(l, cursorRange)) {
						background[j][i] = 195;
					}
				}
			}
		}
		for(int i = 0; i < 42; i ++) {
			System.out.print(i + " ");
			if(i < 10) {
				System.out.print(" ");
			}
			int prev = 0;
			int prevBack = 999;
			for(int j = 0; j < 42; j++) {
				if(foreground[i][j] != prev) {
					System.out.print(color + foreground[i][j] + "m");
					prev = foreground[i][j];
				}
				if(background[i][j] != prevBack) {
					if(background[i][j] == 999) {
						System.out.print(reset);
						System.out.print(color + foreground[i][j] + "m");
						prevBack = background[i][j];
					}else {
						System.out.print(back + background[i][j] + "m");
						prevBack = background[i][j];
					}
				}
				System.out.print(field[i][j] + " ");
			}
			System.out.println(reset);
		}
		System.out.println();
	}
	
	public void printField(Player a, Player b, Player c, Player d, Player e, Player f, ArrayList<Orb> orbLoc, ArrayList<Cover> coverLoc, Player s, Player e1, Player e2, Player e3, ArrayList<Utility> utilityLoc) {
		one = a.getLoc();
		two = b.getLoc();
		three = c.getLoc();
		four = d.getLoc();
		five = e.getLoc();
		six = f.getLoc();
		for(int i = 0; i < 42; i++) {
			for(int j = 0; j < 42; j++){
				field[i][j] = " ";
				foreground[i][j] = 0;
				background[i][j] = 999;
				for(Utility u: utilityLoc) {
					if(u.getName().equals("Dragon")) {
						//background[i][j] = 220;
					}
				}
			}
		}
		
		for (Utility rook : utilityLoc) {
	        if (rook.getName().equals("Rook")) {
	        	switch (rook.getDirection()) {
	        	case "left":
	        		rook.getLoc().set(rook.owner.getLoc().getX() - 2, rook.owner.getLoc().getY());
	        		break;
	        	case "right":
	        		rook.getLoc().set(rook.owner.getLoc().getX() + 2, rook.owner.getLoc().getY());
	        		break;
	        	case "up":
	        		rook.getLoc().set(rook.owner.getLoc().getX(), rook.owner.getLoc().getY() - 2);
	        		break;
	        	case "down":
	        		rook.getLoc().set(rook.owner.getLoc().getX(), rook.owner.getLoc().getY() + 2);
	        		break;
	        	}
	        }
	    }
		
		if (temple) {
			for(int i = 14; i < 28; i++) {
				background[i][0] = 196;
				background[0][i] = 196;
				background[i][41] = 196;
				background[41][i] = 196;
			}
		}
		
		for(int i = 0; i < 42; i++) {
			for(int j = 0; j < 42; j++){
				Location l = new Location(i, j);
				if(e1.inRange(l) && e1.hasSights() && !e1.isStunned()) {
					field[j][i] = "∙";
					foreground[j][i] = 196;
				}
			}
		}
		for(int i = 0; i < 42; i++) {
			for(int j = 0; j < 42; j++){
				Location l = new Location(i, j);
				if(e2.inRange(l) && e2.hasSights() && !e2.isStunned()) {
					field[j][i] = "∙";
					foreground[j][i] = 196;
				}
			}
		}
		for(int i = 0; i < 42; i++) {
			for(int j = 0; j < 42; j++){
				Location l = new Location(i, j);
				if(e3.inRange(l) && e3.hasSights() && !e3.isStunned()) {
					field[j][i] = "∙";
					foreground[j][i] = 196;
				}
			}
		}
		
		for(int i = 0; i < 42; i++) {
			for(int j = 0; j < 42; j++){
				Location l = new Location(i, j);
				if(s.inRange(l)) {
					if(!l.eqLoc(s.getLoc())) {
						background[j][i] = 255;
					}
				}
			}
		}
		
		
		
		if(!s.isParalyzed() && !s.drillDashed()) {
			for(int i = 0; i < 42; i++) {
				for(int j = 0; j < 42; j++){
					Location l = new Location(i, j);
					if(s.inReach(l)) {
						if(!l.eqLoc(s.getLoc())) {
							background[j][i] = 254;
						}
					}
				}
			}
		}
		
		if(s.getPack()) {
			for(int i = 0; i < 42; i++) {
				for(int j = 0; j < 42; j++){
					Location l = new Location(i, j);
					if(s.inRange(l, 5)) {
						if(!l.eqLoc(s.getLoc())) {
							background[j][i] = 223;
						}
					}
				}
			}
		}
		
		if(s.getMovement() > s.getRange()){
			for(int i = 0; i < 42; i++) {
				for(int j = 0; j < 42; j++){
					Location l = new Location(i, j);
					if(s.inRange(l, s.getRange())) {
						//field[j][i] = "∙";
						//field[j][i]= "\u001B[41m" + " " + reset;
						background[j][i] = 255;
					}
				}
			}
		}
		
		int closeRange = -1;
		int farRange = -1;
		
		switch (s.getName()) {
			case "Aidan":
				closeRange = 4;
				farRange = 10;
				break;
			default:
				break;
		}
		
		if (start) {
			for(int i = 0; i < 42; i++) {
				for(int j = 0; j < 42; j++){
					Location l = new Location(i, j);
					if(new Location(4, 4).inRange(l, 3)) {
						background[j][i] = 195;
					}
				}
				for(int j = 0; j < 42; j++){
					Location l = new Location(i, j);
					if(new Location(37, 37).inRange(l, 3)) {
						background[j][i] = 195;
					}
				}
			}
		}
		
		
		
		for(int i = 0; i < 42; i++) {
			for(int j = 0; j < 42; j++){
				
				
				
				for(Utility u: utilityLoc) {
					if(i == u.getLoc().getY() && j == u.getLoc().getX()) {
						if(u.getName().equals("Swiftwing") && !u.getPickUp()) {
							field[i][j] = "S";
							foreground[i][j] = 0;
							background[i][j] = 213;
						}
						if(u.getName().equals("Howler") && !u.getPickUp()) {
							field[i][j] = "H";
							foreground[i][j] = 0;
							background[i][j] = 93;
						}
						if(u.getName().equals("Star") && !u.getPickUp()) {
							field[i][j] = "★";
							foreground[i][j] = 0;
							background[i][j] = 194;
						}
						if(u.getName().equals("Butterfly") && !u.getPickUp()) {
							field[i][j] = "🦋";
							foreground[i][j] = 0;
							background[i][j] = 183;
						}
						if(u.getName().equals("Spirit") && !u.getPickUp()) {
							field[i][j] = "S";
							foreground[i][j] = 0;
							background[i][j] = 218;
						}
						if(u.getName().equals("Crane") && !u.getPickUp() && u.isAlly(s)) {
							field[i][j] = "c";
							foreground[i][j] = 0;
							background[i][j] = 224;
						}
						if(u.getName().equals("Nebula") && !u.getPickUp()) {
							field[i][j] = "&";
							foreground[i][j] = 165;
						}
						if(u.getName().equals("Sensor")) {
							field[i][j] = "S";
							foreground[i][j] = 0;
							background[i][j] = 193;
							for(int k = 0; k < 42; k++) {
								for(int h = 0; h < 42; h++){
									Location l = new Location(k, h);
									if(u.atRange(l, 5) && u.isEnemy(s)) {
										background[h][k] = 193;
									}
								}
							}
						}
						if(u.getName().equals("Sphere")) {
							field[i][j] = "Y";
							foreground[i][j] = 0;
							background[i][j] = 299;
							for(int k = 0; k < 42; k++) {
								for(int h = 0; h < 42; h++){
									Location l = new Location(k, h);
									if(u.atRange(l, 8) && u.isEnemy(s)) {
										background[h][k] = 229;
									}
								}
							}
						}
						if(u.getName().equals("Starpull")) {
							field[i][j] = "A";
							foreground[i][j] = 0;
							background[i][j] = 141;
							for(int k = 0; k < 42; k++) {
								for(int h = 0; h < 42; h++){
									Location l = new Location(k, h);
									if(u.atRange(l, 11) && u.isEnemy(s)) {
										background[h][k] = 141;
									}
								}
							}
						}
						if(u.getName().equals("Wind")) {
							field[i][j] = "W";
							foreground[i][j] = 0;
							background[i][j] = 220;
						}
						if(u.getName().equals("Respawn")) {
							field[i][j] = "R";
							foreground[i][j] = 0;
							background[i][j] = 225;
						}
						if(u.getName().equals("Explosive")) {
							field[i][j] = "E";
							foreground[i][j] = 0;
							background[i][j] = 196;
							for(int k = 0; k < 42; k++) {
								for(int h = 0; h < 42; h++){
									Location l = new Location(k, h);
									if(endgame) {
										if(u.atRange(l, 5) && u.isEnemy(s)) {
											background[h][k] = 214;
										}else if(u.atRange(l, 10) && u.isEnemy(s)) {
											background[h][k] = 220;
										}
										if(u.atRange(l, 0) && u.isEnemy(s)) {
											background[h][k] = 196;
										}
									}else {
										if(u.atRange(l, 8) && u.isEnemy(s)) {
											background[h][k] = 214;
										}else if(u.atRange(l, 15) && u.isEnemy(s)) {
											background[h][k] = 220;
										}
										if(u.atRange(l, 0) && u.isEnemy(s)) {
											background[h][k] = 196;
										}
									}
								}
							}
						}
						if(u.getName().equals("Gemstone")) {
							field[i][j] = "G";
							foreground[i][j] = 0;
							background[i][j] = 252;
							for(int k = 0; k < 42; k++) {
								for(int h = 0; h < 42; h++){
									Location l = new Location(k, h);
									if(u.atRange(l, 0) && u.isAlly(s) && u.getStone().equals("iron")) {
										field[i][j] = "I";
										background[h][k] = 252;
									}
									if(u.atRange(l, 4) && u.isAlly(s) && u.getStone().equals("emerald")) {
										field[i][j] = "E";
										background[h][k] = 155;
									}
									if(u.atRange(l, 6) && u.isAlly(s) && u.getStone().equals("diamond")) {
										field[i][j] = "D";
										background[h][k] = 159;
									}
									if(u.atRange(l, 8) && u.isAlly(s) && u.getStone().equals("kunzite")) {
										field[i][j] = "K";
										background[h][k] = 225;
									}
								}
							}
						}
						if(u.getName().equals("Field")) {
							field[i][j] = "F";
							foreground[i][j] = 0;
							background[i][j] = 153;
							for(int k = 0; k < 42; k++) {
								for(int h = 0; h < 42; h++){
									Location l = new Location(k, h);
									if(u.atRange(l, 7) && u.isAlly(s)) {
										background[h][k] = 153;
									}
								}
							}
						}
						if(u.getName().equals("Dynamite")) {
							field[i][j] = "D";
							foreground[i][j] = 0;
							background[i][j] = 197;
							for(int k = 0; k < 42; k++) {
								for(int h = 0; h < 42; h++){
									Location l = new Location(k, h);
									if(u.atRange(l, 5) && u.isEnemy(s)) {
										background[h][k] = 197;
									}
								}
							}
						}
						if(u.getName().equals("Peri")) {
							field[i][j] = "P";
							foreground[i][j] = 0;
							background[i][j] = 111;
							for(int k = 0; k < 42; k++) {
								for(int h = 0; h < 42; h++){
									Location l = new Location(k, h);
									if(u.atRange(l, 12) && u.isEnemy(s)) {
										background[h][k] = 111;
									}
								}
							}
						}
						if(u.getName().equals("Fulmination")) {
							for(int k = 0; k < 42; k++) {
								for(int h = 0; h < 42; h++){
									Location l = new Location(k, h);
									if(u.atRange(l, 8) && u.isEnemy(s)) {
										background[h][k] = 63;
									}
								}
							}
						}
						if(u.getName().equals("Bubble")) {
							for(int k = 0; k < 42; k++) {
								for(int h = 0; h < 42; h++){
									Location l = new Location(k, h);
									if(u.atRange(l, u.getSize())) {
										background[h][k] = 87;
									}
								}
							}
						}
						if(u.getName().equals("Steam")) {
							for(int k = 0; k < 42; k++) {
								for(int h = 0; h < 42; h++){
									Location l = new Location(k, h);
									if(u.atRange(l, u.getOwner().getRange()) && u.isEnemy(s) && u.getOwner().isClockwork()) {
										background[h][k] = 100;
									}
								}
							}
						}
						if(u.getName().equals("Flame")) {
							for(int k = 0; k < 42; k++) {
								for(int h = 0; h < 42; h++){
									Location l = new Location(k, h);
									if(u.atRange(l, 5) && u.isEnemy(s)) {
										background[h][k] = 216;
									}
								}
							}
						}
						if(u.getName().equals("Umbrella")) {
							int range = 7;
							if (u.getOwner().ultActive()) {
								range = 10;
							}
							field[i][j] = "U";
							foreground[i][j] = 0;
							background[i][j] = 147;
							for(int k = 0; k < 42; k++) {
								for(int h = 0; h < 42; h++){
									Location l = new Location(k, h);
									if(u.atRange(l, range) && u.isAlly(s)) {
										background[h][k] = 147;
									}
								}
							}
						}
						if(u.getName().equals("Pylon")) {
							field[i][j] = "P";
							foreground[i][j] = 0;
							background[i][j] = 226;
							for(int k = 0; k < 42; k++) {
								for(int h = 0; h < 42; h++){
									Location l = new Location(k, h);
									if(u.atRange(l, 4)) {
										background[h][k] = 226;
									}
								}
							}
						}
						if(u.getName().equals("Matrix")) {
							field[i][j] = "M";
							foreground[i][j] = 0;
							background[i][j] = 225;
							for(int k = 0; k < 42; k++) {
								for(int h = 0; h < 42; h++){
									Location l = new Location(k, h);
									if(u.atRange(l, 6)) {
										background[h][k] = 225;
									}
								}
							}
						}
						if(u.getName().equals("Turret")) {
							field[i][j] = "T";
							foreground[i][j] = 0;
							background[i][j] = 7;
							for(int k = 0; k < 42; k++) {
								for(int h = 0; h < 42; h++){
									Location l = new Location(k, h);
									if(u.atRange(l, 3) && u.isActivated() && u.hasSpikes() && u.isEnemy(s)) {
										background[h][k] = 7;
									}
									if(u.atRange(l, 15) && u.isActivated() && u.isEnemy(s)) {
										background[h][k] = 7;
									}
								}
							}
						}
						if(u.getName().equals("Iron")) {
							foreground[i][j] = 0;
							for(int k = 0; k < 42; k++) {
								for(int h = 0; h < 42; h++){
									Location l = new Location(k, h);
									if(u.atRange(l, 3)) {
										background[h][k] = 65;
									}
								}
							}
						}
						if(u.getName().equals("Rook")) {
							field[i][j] = "R";
							foreground[i][j] = 0;
							if (u.rookActive()) {
								background[i][j] = 220;
							}else {
								background[i][j] = 242;
							}
						}
						if(u.getName().equals("Mochi")) {
							field[i][j] = "🦊";
							foreground[i][j] = 214;
						}
						if(u.getName().equals("Pawn")) {
							field[i][j] = "P";
							foreground[i][j] = 0;
							background[i][j] = 229;
						}
						if(u.getName().equals("Mural")) {
							field[i][j] = "M";
							foreground[i][j] = 0;
							background[i][j] = 217;
							for(int k = 0; k < 42; k++) {
								for(int h = 0; h < 42; h++){
									Location l = new Location(k, h);
								}
							}
						}
						if(u.getName().equals("Sock")) {
							field[i][j] = "S";
							foreground[i][j] = 0;
							background[i][j] = 7;
							for(int k = 0; k < 42; k++) {
								for(int h = 0; h < 42; h++){
									Location l = new Location(k, h);
									if(u.atRange(l, 3) && u.isEnemy(s)) {
										background[h][k] = 7;
									}
								}
							}
						}
						if(u.getName().equals("Support")) {
							field[i][j] = "S";
							foreground[i][j] = 0;
							background[i][j] = 160;
						}
						if(u.getName().equals("Vine")) {
							for(int k = 0; k < 42; k++) {
								for(int h = 0; h < 42; h++){
									Location l = new Location(k, h);
									if(u.atRange(l, 2) && u.isEnemy(s)) {
										background[h][k] = 130;
									}
								}
							}
						}
						if(u.getName().equals("Mine")) {
							field[i][j] = "M";
							foreground[i][j] = 0;
							background[i][j] = 217;
						}
						if(u.getName().equals("Sphere2")) {
							for(int k = 0; k < 42; k++) {
								for(int h = 0; h < 42; h++){
									Location l = new Location(k, h);
									if(u.atRange(l, 4)) {
										if (u.getSphere()) {
											background[h][k] = 121;
										}else {
											background[h][k] = 63;
										}
									}
								}
							}
						}
						if(u.getName().equals("Eclipse")) {
							for(int k = 0; k < 42; k++) {
								for(int h = 0; h < 42; h++){
									Location l = new Location(k, h);
									if(u.atRange(l, 15)) {
										if (u.isEclipse()) {
											background[h][k] = 121;
										}else {
											background[h][k] = 63;
										}
									}
								}
							}
						}
					}
				}
				
				for(Tile t: tiles) {
					if(i == t.getLoc().getY() && j == t.getLoc().getX()) {
						if(t.getName().equals("Rift")) {
							field[i][j] = "@";
							foreground[i][j] = 117;
						}
						if(t.getName().equals("Trench")) {
							field[i][j] = "*" ;
						}
						if(t.getName().equals("Space")) {
							field[i][j] = "^";
						}
						if(t.getName().equals("Firepower")) {
							field[i][j] = ",";
							foreground[i][j] = 202;
						}
						if(t.getName().equals("Tower")) {
							field[i][j] = "#";
							foreground[i][j] = 40;
						}
						if(t.getName().equals("Bounce")) {
							field[i][j] = "%";
							foreground[i][j] = 98;
						}
						if(t.getName().equals("Time")) {
							field[i][j] = "$";
							foreground[i][j] = 30;
						}
						if(t.getName().equals("Car")) {
							field[i][j] = "=";
							foreground[i][j] = 200;
						}
					}
				}
				for(Orb o: orbLoc) {
					if(i == o.getLoc().getY() && j == o.getLoc().getX()) {
						//field[i][j] = bold + "\u001b[38;5;" + 189 + "m" + "🪩" + reset;
						//field[i][j] = "🪩";
						background[i][j] = 225;
						foreground[i][j] = 0;
						field[i][j] = "O";
					}
				}
				for(Cover w: coverLoc) {
					if(i == w.getLoc().getY() && j == w.getLoc().getX()) {
						if(w.getName().equals("Full")) {
							//field[i][j] = "\u001b[38;5;" + 46 + "m" + "🛡️" + reset;
							field[i][j] = "F";
							//field[i][j] = "🛡️";
							foreground[i][j] = 0;
							background[i][j] = 226;
						}
						if(w.getName().equals("Partial")) {
							//field[i][j] = bold + "\u001b[38;5;" + 46 + "m" + "🪨" + reset;
							field[i][j] = "P";
							//field[i][j] = "🪨";
							foreground[i][j] = 0;
							background[i][j] = 195;
						}
					}
				}
				
				
				if(i == one.getY() && j == one.getX()) {
					if(!a.isAlive()) {
						field[i][j] = "-";
					}else {
						field[i][j] = bold + a.getName().substring(0,1) + clear;
						foreground[i][j] = a.skinC();
						//background[i][j] = 255;
					}
				}
				if(i == two.getY() && j == two.getX()) {
					if(!b.isAlive()) {
						field[i][j] = "-";
					}else {
						field[i][j] = bold + b.getName().substring(0,1) + clear;
						foreground[i][j] = b.skinC();
						//background[i][j] = 255;
					}
				}
				if(i == three.getY() && j == three.getX()) {
					if(!c.isAlive()) {
						field[i][j] = "-";
					}else {
						field[i][j] = bold + c.getName().substring(0,1) + clear;
						foreground[i][j] = c.skinC();
						//background[i][j] = 255;
					}
				}
				if(i == four.getY() && j == four.getX()) {
					if(!d.isAlive()) {
						field[i][j] = "-";
					}else {
						field[i][j] = bold + d.getName().substring(0,1) + clear;
						foreground[i][j] = d.skinC();
						//background[i][j] = 255;
					}
				}
				if(i == five.getY() && j == five.getX()) {
					if(!e.isAlive()) {
						field[i][j] = "-";
					}else {
						field[i][j] = bold + e.getName().substring(0,1) + clear;
						foreground[i][j] = e.skinC();
						//background[i][j] = 255;
					}
				}
				if(i == six.getY() && j == six.getX()) {
					if(!f.isAlive()) {
						field[i][j] = "-";
					}else {
						field[i][j] = bold + f.getName().substring(0,1) + clear;
						foreground[i][j] = f.skinC();
						//background[i][j] = 255;
					}
				}
				
				
			}
			
		}
		for(Utility u: utilityLoc) {
			if(u.getName().equals("Smoke")) {
				for(int k = 0; k < 42; k++) {
					for(int h = 0; h < 42; h++){
						Location l = new Location(k, h);
						int range = 4;
						if (u.getOwner().getDarkness()) {
							range = 6;
						}
						if(u.inRange(l, range) && u.isEnemy(s)) {
							background[h][k] = 17;
							field[h][k] = " ";
						}
						if(u.atRange(l, range) && u.isAlly(s)) {
							background[h][k] = 17;
						}
					}
				}
			}
		}
		
		if(cursor != null && cursor.getX() < 42 && cursor.getY() < 42) {
			field[cursor.getY()][cursor.getX()] = "✦";
			for(int i = 0; i < 42; i++) {
				for(int j = 0; j < 42; j++){
					Location l = new Location(i, j);
					if(cursor.inRange(l, cursorRange)) {
						background[j][i] = 195;
					}
				}
			}
		}
		for(int i = 0; i < 42; i ++) {
			System.out.print(i + " ");
			if(i < 10) {
				System.out.print(" ");
			}
			int prev = 0;
			int prevBack = 999;
			for(int j = 0; j < 42; j++) {
				if(foreground[i][j] != prev) {
					System.out.print(color + foreground[i][j] + "m");
					prev = foreground[i][j];
				}
				if(background[i][j] != prevBack) {
					if(background[i][j] == 999) {
						System.out.print(reset);
						System.out.print(color + foreground[i][j] + "m");
						prevBack = background[i][j];
					}else {
						System.out.print(back + background[i][j] + "m");
						prevBack = background[i][j];
					}
				}
				System.out.print(field[i][j] + " ");
			}
			System.out.println(reset);
		}
		/*
		//Print the x axis.
		System.out.print("   ");
		
		for(int j = 0; j < 4; j++) {
			for(int i = 0; i < 10; i ++) {
				System.out.print(j + " ");	
			}
		}
		System.out.print("4 4");
		System.out.println();
		System.out.print("   ");
		for(int j = 0; j < 4; j++) {
			for(int i = 0; i < 10; i ++) {
				System.out.print(i + " ");	
			}
		}
		System.out.print("0 1");
		System.out.println();
		*/
		System.out.println();
	}
	
	public void setRange(double i) {
		cursorRange = i;
	}
	
	public void addTile(String s, Location l) {
		for(int i = 0; i < tiles.size(); i++) {
			if(tiles.get(i).getLoc().eqLoc(l)) {
				tiles.remove(i);
				i--;
			}
		}
		Tile t = new Tile(s, l);
		tiles.add(t);
	}
	
	public void endGame() {
		endgame = true;
		tiles.clear();
		for(int i = 0; i < 42; i++) {
			for(int j = 0; j < 13; j++) {
				Tile t = new Tile("Space", new Location(i, j));
				tiles.add(t);
			}
		}
		for(int i = 0; i < 42; i++) {
			for(int j = 29; j < 42; j++) {
				Tile t = new Tile("Space", new Location(i, j));
				tiles.add(t);
			}
		}
		for(int i = 0; i < 13; i++) {
			for(int j = 13; j < 29; j++) {
				Tile t = new Tile("Space", new Location(i, j));
				tiles.add(t);
			}
		}
		for(int i = 29; i < 42; i++) {
			for(int j = 13; j < 29; j++) {
				Tile t = new Tile("Space", new Location(i, j));
				tiles.add(t);
			}
		}
	}
	
	public void suddenDeath() {
		tiles.clear();
		for(int i = 0; i < 42; i++) {
			for(int j = 0; j < 42; j++) {
				Tile t = new Tile("Space", new Location(i, j));
				tiles.add(t);
			}
		}
	}
	
	public boolean hasTrench(int x, int y) {
		for(Tile t: tiles) {
			if(t.getLoc().eqLoc(new Location(x,y)) && t.getName().equals("Trench")) {
				return true;
			}
		}
		return false;
	}
	
	public boolean hasTower(int x, int y) {
		for(Tile t: tiles) {
			if(t.getLoc().eqLoc(new Location(x,y)) && t.getName().equals("Tower")) {
				return true;
			}
		}
		return false;
	}
	
	public boolean hasTime(int x, int y) {
		for(Tile t: tiles) {
			if(t.getLoc().eqLoc(new Location(x,y)) && t.getName().equals("Time")) {
				return true;
			}
		}
		return false;
	}
	
	public boolean hasCar(int x, int y) {
		for(Tile t: tiles) {
			if(t.getLoc().eqLoc(new Location(x,y)) && t.getName().equals("Car")) {
				return true;
			}
		}
		return false;
	}
	
	public boolean hasBounce(int x, int y) {
		for(Tile t: tiles) {
			if(t.getLoc().eqLoc(new Location(x,y)) && t.getName().equals("Bounce")) {
				return true;
			}
		}
		return false;
	}
	
	public void checkSpace() {
		
	}
	
	public ArrayList<Tile> getTiles(){
		return tiles;
	}
	
	public void checkTiles() {
		for(Player p: players) {
			for(Tile t: tiles) {
				if(p.getLoc().eqLoc(t.getLoc()) && !p.getHover()) {
					if(t.getName().equals("Rift")) {
						int x = (int)(Math.random() * (41 - 0 + 1)) + 0;
						int y = (int)(Math.random() * (41 - 0 + 1)) + 0;
						p.getLoc().set(x, y);
						System.out.println(p.getSkin() + " rifted to " + p.getLoc() + ".");
						System.out.println();
					}
					/*
					if(t.getName().equals("Space")) {
						p.takeDamage(p.getMaxHP() * 0.1);
						System.out.println();
					}
					*/
					if(t.getName().equals("Trench")) {
						p.takeDamage(150);
						p.knockbacked(p.getLoc());
						System.out.println();
					}
					if(t.getName().equals("Firepower")) {
						ArrayList<Effect> e = new ArrayList<Effect>();
						Effect Fire = new Effect("ignite", 0, 1);
						Effect Fire2 = new Effect("power", 0.05, 1);
						e.add(Fire);
						e.add(Fire2);
						p.addEffects(e);
						p.applyEffects();
						System.out.println();
					}
				}
			}
		}
	}
	
	public boolean isStart() {
		return start;
	}
	
	public void setStart(boolean b) {
		start = b;
	}
	
	
	public void trenchDrill(Location l) {
		for(int i = 0; i < 42; i++) {
			for(int j = 0; j < 42; j++){
				Location l2 = new Location(i, j);
				if(l2.inRange(l, 3)) {
					Tile t = new Tile("Trench", new Location(i, j));
					tiles.add(t);
				}
			}
		}
	}
	
	
}