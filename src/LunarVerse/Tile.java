package LunarVerse;

public class Tile {
	
	String name;
	Location loc;
	
	public Tile(String nameIn, Location locIn) {
		name = nameIn;
		loc = locIn;
	}
	
	public Location getLoc() {
		return loc;
	}
	
	public String getName() {
		return name;
	}

}
