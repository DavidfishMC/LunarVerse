package LunarVerse;

import java.awt.geom.Point2D;

public class Location {
	
	int x;
	int y;
	
	public Location(int inX, int inY) {
		x = inX;
		y = inY;
	}
	
	public void moveIn(int hX, int vY) {
		x = hX;
		y = vY;
	}
	
	public void set(int newX, int newY) {
		x = newX;
		y = newY;
	}
	
	public void setX(int i) {
		x = i;
	}
	
	public void setY(int i) {
		y = i;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public double distanceTo(Location l) {
		return Point2D.distance(x, y, l.getX(), l.getY());
	}
	
	public int distance(Location l) {
		return Math.abs(x - l.getX()) + Math.abs(y - l.getY());
	}
	
	public boolean inRange(Location l, double r) {
		double d = distanceTo(l);
		if(r >= d) {
			return true;
		}else {
			return false;
		}
	}
	
	public boolean atRange(Location l, double r) {
		double d = distanceTo(l);
		if (r >= d && !(d <= (r - 1))) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean eqLoc(Location l) {
		if(x == l.getX() && y == l.getY()) {
			return true;
		}else {
			return false;
		}
	}
	
	public void adjust(int x2, int y2) {
		x = x + x2;
		y = y + y2;
	}
	
	public String toString() {
		return ("(" + x + ", " + y + ")");
	}

}