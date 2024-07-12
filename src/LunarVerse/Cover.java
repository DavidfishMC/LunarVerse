package LunarVerse;

public class Cover {
	
	Location l;
	String name;
	boolean used = false;
	
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
	
	public void setUsed(boolean b) {
		used = b;
	}
	
	public boolean isUsed() {
		return used;
	}

}