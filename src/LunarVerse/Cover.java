package LunarVerse;

public class Cover {
	
	Location l;
	String name;
	
	public Cover(String nameIn, Location loc) {
		l = loc;
		name = nameIn;
	}
	
	public Location getLoc() {
		return l;
	}
	
	public String getName() {
		return name;
	}

}