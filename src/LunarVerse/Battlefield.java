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
	static final String reset = "\u001B[0m";
	static final String color = "\u001b[38;5;";
	static final String back = "\u001b[48;5;";
	static final String clear = "\u001b[22m";
	static final String bold = "\u001b[1m";

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
	
	public void printField(Player a, Player b, Player c, Player d, Player e, Player f, ArrayList<Orb> orbLoc, ArrayList<Cover> coverLoc, Player s, Player e1, Player e2, Player e3) {
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
		if(cursor != null && cursor.getX() < 42 && cursor.getY() < 42) {
			field[cursor.getY()][cursor.getX()] = "✦";
		}
		
		for(int i = 0; i < 42; i++) {
			for(int j = 0; j < 42; j++){
				for(Tile t: tiles) {
					if(i == t.getLoc().getY() && j == t.getLoc().getX()) {
						if(t.getName().equals("Rift")) {
							field[i][j] = "🍥";
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
						field[i][j] = "💀";
					}else {
						field[i][j] = bold + a.getName().substring(0,1) + clear;
						foreground[i][j] = a.skinC();
						//background[i][j] = 255;
					}
				}
				if(i == two.getY() && j == two.getX()) {
					if(!b.isAlive()) {
						field[i][j] = "💀";
					}else {
						field[i][j] = bold + b.getName().substring(0,1) + clear;
						foreground[i][j] = b.skinC();
						//background[i][j] = 255;
					}
				}
				if(i == three.getY() && j == three.getX()) {
					if(!c.isAlive()) {
						field[i][j] = "💀";
					}else {
						field[i][j] = bold + c.getName().substring(0,1) + clear;
						foreground[i][j] = c.skinC();
						//background[i][j] = 255;
					}
				}
				if(i == four.getY() && j == four.getX()) {
					if(!d.isAlive()) {
						field[i][j] = "💀";
					}else {
						field[i][j] = bold + d.getName().substring(0,1) + clear;
						foreground[i][j] = d.skinC();
						//background[i][j] = 255;
					}
				}
				if(i == five.getY() && j == five.getX()) {
					if(!e.isAlive()) {
						field[i][j] = "💀";
					}else {
						field[i][j] = bold + e.getName().substring(0,1) + clear;
						foreground[i][j] = e.skinC();
						//background[i][j] = 255;
					}
				}
				if(i == six.getY() && j == six.getX()) {
					if(!f.isAlive()) {
						field[i][j] = "💀";
					}else {
						field[i][j] = bold + f.getName().substring(0,1) + clear;
						foreground[i][j] = f.skinC();
						//background[i][j] = 255;
					}
				}
				
				
			}
			
		}
		for(int i = 0; i < 42; i++) {
			for(int j = 0; j < 42; j++){
				Location l = new Location(i, j);
				if(s.inRange(l)) {
					//field[j][i] = "∙";
					//field[j][i]= "\u001B[41m" + " " + reset;
					background[j][i] = 255;
				}
			}
		}
		
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
		tiles.clear();
		for(int i = 0; i < 42; i++) {
			for(int j = 0; j < 10; j++) {
				Tile t = new Tile("Space", new Location(i, j));
				tiles.add(t);
			}
		}
		for(int i = 0; i < 42; i++) {
			for(int j = 32; j < 42; j++) {
				Tile t = new Tile("Space", new Location(i, j));
				tiles.add(t);
			}
		}
		for(int i = 0; i < 10; i++) {
			for(int j = 10; j < 32; j++) {
				Tile t = new Tile("Space", new Location(i, j));
				tiles.add(t);
			}
		}
		for(int i = 32; i < 42; i++) {
			for(int j = 10; j < 32; j++) {
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
	
	public void checkTiles() {
		for(Player p: players) {
			for(Tile t: tiles) {
				if(p.getLoc().eqLoc(t.getLoc())) {
					if(t.getName().equals("Rift")) {
						int x = (int)(Math.random() * (41 - 0 + 1)) + 0;
						int y = (int)(Math.random() * (41 - 0 + 1)) + 0;
						p.getLoc().set(x, y);
						System.out.println(p.getSkin() + " rifted to " + p.getLoc() + ".");
						System.out.println();
					}
					if(t.getName().equals("Space")) {
						p.takeDamage(p.getMaxHP() * 0.1);
						System.out.println();
					}
					if(t.getName().equals("Trench")) {
						p.takeDamage(150);
						p.knockbacked();
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
	
}