package LunarVerse;

import java.util.ArrayList;

public class Battlefield {
	
	Location one;
	Location two;
	Location three;
	Location four;
	Location five;
	Location six;
	Player a;
	Player b;
	Player c;
	Player d;
	Player e;
	Player f;
	String[][] field = new String[41][41];
	ArrayList<Tile> tiles = new ArrayList<Tile>();
	ArrayList<Player> players = new ArrayList<Player>();
	String name;

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
		for(int i = 0; i < 41; i++) {
			for(int j = 0; j < 41; j++){
				field[i][j] = "∙";
			}
		}
		if(name.equals("Galactical Laboratories")) {
			Tile t = new Tile("Rift", new Location(9,9));
			Tile t2 = new Tile("Rift", new Location(30,9));
			Tile t3 = new Tile("Rift", new Location(9,30));
			Tile t4 = new Tile("Rift", new Location(30,30));
			tiles.add(t);
			tiles.add(t2);
			tiles.add(t3);
			tiles.add(t4);
		}
	}
	
	public void printField(Player a, Player b, Player c, Player d, Player e, Player f, ArrayList<Orb> orbLoc, ArrayList<Cover> coverLoc) {
		one = a.getLoc();
		two = b.getLoc();
		three = c.getLoc();
		four = d.getLoc();
		five = e.getLoc();
		six = f.getLoc();
		for(int i = 0; i < 41; i++) {
			for(int j = 0; j < 41; j++){
				field[i][j] = "∙";
			}
		}
		for(int i = 0; i < 41; i++) {
			for(int j = 0; j < 41; j++){
				for(Orb o: orbLoc) {
					if(i == o.getLoc().getY() && j == o.getLoc().getX()) {
						field[i][j] = "O";
					}
				}
				for(Cover w: coverLoc) {
					if(i == w.getLoc().getY() && j == w.getLoc().getX()) {
						if(w.getName().equals("Full")) {
							field[i][j] = "F";
						}
						if(w.getName().equals("Partial")) {
							field[i][j] = "P";
						}
					}
				}
				for(Tile t: tiles) {
					if(i == t.getLoc().getY() && j == t.getLoc().getX()) {
						if(t.getName().equals("Rift")) {
							field[i][j] = "R";
						}
					}
				}
				if(i == one.getY() && j == one.getX()) {
					if(!a.isAlive()) {
						field[i][j] = "D";
					}else {
						field[i][j] = "1";
					}
				}
				if(i == two.getY() && j == two.getX()) {
					if(!b.isAlive()) {
						field[i][j] = "D";
					}else {
						field[i][j] = "2";
					}
				}
				if(i == three.getY() && j == three.getX()) {
					if(!c.isAlive()) {
						field[i][j] = "D";
					}else {
						field[i][j] = "3";
					}
				}
				if(i == four.getY() && j == four.getX()) {
					if(!d.isAlive()) {
						field[i][j] = "D";
					}else {
						field[i][j] = "4";
					}
				}
				if(i == five.getY() && j == five.getX()) {
					if(!e.isAlive()) {
						field[i][j] = "D";
					}else {
						field[i][j] = "5";
					}
				}
				if(i == six.getY() && j == six.getX()) {
					if(!f.isAlive()) {
						field[i][j] = "D";
					}else {
						field[i][j] = "6";
					}
				}
				
			}
			
		}
		for(int i = 0; i < 41; i ++) {
			System.out.print(i + " ");
			if(i < 10) {
				System.out.print(" ");
			}
			for(int j = 0; j < 41; j++) {
				System.out.print(field[i][j] + " ");
			}
			System.out.println();
		}
		System.out.print("   ");
		
		for(int j = 0; j < 4; j++) {
			for(int i = 0; i < 10; i ++) {
				System.out.print(j + " ");	
			}
		}
		System.out.print("4");
		System.out.println();
		System.out.print("   ");
		for(int j = 0; j < 4; j++) {
			for(int i = 0; i < 10; i ++) {
				System.out.print(i + " ");	
			}
		}
		System.out.print("0");
		System.out.println();
		System.out.println();
		
	}
	
	public void checkTiles() {
		for(Player p: players) {
			for(Tile t: tiles) {
				if(p.getLoc().eqLoc(t.getLoc())) {
					if(t.getName().equals("Rift")) {
						int x = (int)(Math.random() * (40 - 0 + 1)) + 0;
						int y = (int)(Math.random() * (40 - 0 + 1)) + 0;
						p.getLoc().set(x, y);
						System.out.println(p.getName() + " rifted to " + p.getLoc() + ".");
						System.out.println();
					}
				}
			}
		}
	}
	
}