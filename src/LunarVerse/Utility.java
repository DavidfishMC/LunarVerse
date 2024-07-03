package LunarVerse;

import java.util.ArrayList;

public class Utility {
	
	Player owner;
	String name;
	int health = 0;
	ArrayList<Player> allies = new ArrayList<Player>();
	ArrayList<Player> enemies = new ArrayList<Player>();
	Location loc;
	boolean pickedUp = false;
	boolean activated = false;
	boolean escape = false;
	String gemstone = "iron";
	
	public Utility(String s, Location l, Player p, Player a1, Player a2, Player e1, Player e2, Player e3) {
		owner = p;
		name = s;
		loc = l;
		allies.add(p);
		allies.add(a1);
		allies.add(a2);
		enemies.add(e1);
		enemies.add(e2);
		enemies.add(e3);
		if (s.equals("Sensor")) {
			health = 1;
		}
		if (s.equals("Sphere")) {
			health = 3;
		}
		if (s.equals("Gemstone")) {
			health = 2;
		}
	}
	
	public void setEscape(boolean b) {
		escape = b;
	}
	
	public void takeHit() {
		health = health - 1;
	}
	
	public Location getLoc() {
		return loc;
	}
	
	public String getName() {
		return name;
	}
	
	public void pickedUp() {
		pickedUp = true;
	}
	
	public boolean getPickUp() {
		return pickedUp;
	}
	
	public boolean owner(Player p) {
		if (owner.equals(p)){
			return true;
		}else {
			return false;
		}
	}
	
	public void activateSound() {
		for (Player p: enemies) {
			if (p.inRange(loc, 6)) {
				p.takeDamage(200);
				owner.addDamage(200);
				ArrayList<Effect> e = new ArrayList<Effect>();
				Effect RoccoParalyze = new Effect("paralyze", 0, 1);
				e.add(RoccoParalyze);
				p.addEffects(e);
				p.applyEffects();
			}
		}
	}
	
	public void activateSphere() {
		for (Player p: enemies) {
			if (p.inRange(loc, 8)) {
				p.takeDamage(200);
				owner.addDamage(200);
				p.knockbacked(loc);
			}
		}
		for (Player p: allies) {
			if (p.inRange(loc, 8)) {
				p.setShield();
			}
		}
	}
	
	public void activateWind() {
		if(escape) {
			for (Player p: enemies) {
				if (owner.inRange(p)) {
					p.takeDamage(175);
					owner.addDamage(175);
					ArrayList<Effect> e = new ArrayList<Effect>();
					Effect RoccoParalyze = new Effect("weak", 0.1, 1);
					e.add(RoccoParalyze);
					p.addEffects(e);
					p.applyEffects();
				}
			}
			owner.getLoc().set(loc.getX(), loc.getY());
		}else {
			owner.getLoc().set(loc.getX(), loc.getY());
			for (Player p: enemies) {
				if (owner.inRange(p)) {
					p.takeDamage(175);
					owner.addDamage(175);
					ArrayList<Effect> e = new ArrayList<Effect>();
					Effect RoccoParalyze = new Effect("weak", 0.1, 1);
					e.add(RoccoParalyze);
					p.addEffects(e);
					p.applyEffects();
				}
			}
		}
	}
	
	public void activateNuke() {
		for (Player p: enemies) {
			if(Battlefield.endgame) {
				if (p.inRange(loc, 0)) {
					p.takeDamage(p.getMaxHP() * 0.5);
					owner.addDamage(p.getMaxHP() * 0.5);
				}else if (p.inRange(loc, 5)) {
					p.takeDamage(p.getMaxHP() * 0.35);
					owner.addDamage(p.getMaxHP() * 0.35);
				}else if (p.inRange(loc, 10)) {
					p.takeDamage(p.getMaxHP() * 0.2);
					owner.addDamage(p.getMaxHP() * 0.2);
				}else {
					p.takeDamage(p.getMaxHP() * 0.1);
					owner.addDamage(p.getMaxHP() * 0.1);
				}
			}else {
				if (p.inRange(loc, 0)) {
					p.takeDamage(p.getMaxHP() * 0.5);
					owner.addDamage(p.getMaxHP() * 0.5);
				}else if (p.inRange(loc, 8)) {
					p.takeDamage(p.getMaxHP() * 0.35);
					owner.addDamage(p.getMaxHP() * 0.35);
				}else if (p.inRange(loc, 15)) {
					p.takeDamage(p.getMaxHP() * 0.2);
					owner.addDamage(p.getMaxHP() * 0.2);
				}else {
					p.takeDamage(p.getMaxHP() * 0.1);
					owner.addDamage(p.getMaxHP() * 0.1);
				}
			}
		}
	}
	
	public int getHealth() {
		return health;
	}
	
	public boolean inRange(Location l, double r) {
		double d = loc.distanceTo(l);
		if (r >= d) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isEnemy(Player p) {
		if (enemies.contains(p)) {
			return true;
		}else {
			return false;
		}
	}
	
	public boolean isAlly(Player p) {
		if (allies.contains(p)) {
			return true;
		}else {
			return false;
		}
	}
	
	public boolean inRange(Player p, double r) {
		double d = loc.distanceTo(p.getLoc());
		if (r >= d) {
			return true;
		} else {
			return false;
		}
	}
	
	public void envolveStone() {
		if (gemstone.equals("iron")) {
			gemstone = "emerald";
			System.out.println("Gemstone has envoled to Emerald.");
		}else if (gemstone.equals("emerald")) {
			gemstone = "diamond";
			System.out.println("Gemstone has envoled to Diamond.");
		}else if (gemstone.equals("diamond")) {
			gemstone = "kunzite";
			System.out.println("Gemstone has envoled to Kunzite.");
		}
	}
	
	public String getStone(){
		return gemstone;
	}
	
	public void activateStone() {
		for (Player p: allies) {
			if (gemstone.equals("emerald") && p.inRange(loc, 4)) {
				ArrayList<Effect> e = new ArrayList<Effect>();
				Effect SolarProtect = new Effect("protect", 0.1, 2);
				e.add(SolarProtect);
				p.addEffects(e);
				p.applyEffects();
			}else if (gemstone.equals("diamond") && p.inRange(loc, 6)) {
				ArrayList<Effect> e = new ArrayList<Effect>();
				Effect SolarProtect = new Effect("protect", 0.1, 2);
				e.add(SolarProtect);
				p.addEffects(e);
				p.applyEffects();
				p.increaseMovement(2);
			}if (gemstone.equals("kunzite") && p.inRange(loc, 8)) {
				ArrayList<Effect> e = new ArrayList<Effect>();
				Effect SolarProtect = new Effect("protect", 0.1, 2);
				e.add(SolarProtect);
				p.addEffects(e);
				p.applyEffects();
				p.increaseMovement(2);
				p.heal(0.05);
				owner.addHealing(p.getMaxHP() * 0.05);
			}
		}
	}

}
