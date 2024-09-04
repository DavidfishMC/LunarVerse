package LunarVerse;

import java.util.ArrayList;
import java.util.Collections;

public class Utility {
	
	Player owner;
	Player target;
	String name;
	int health = 0;
	int ogHealth = 0;
	ArrayList<Player> allies = new ArrayList<Player>();
	ArrayList<Player> enemies = new ArrayList<Player>();
	ArrayList<Player> trapped = new ArrayList<Player>();
	Location loc;
	boolean pickedUp = false;
	boolean activated = true;
	boolean escape = false;
	boolean spikes = false;
	boolean rookActive = true;
	String gemstone = "iron";
	String direction = "";
	String color = "";
	
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
		if (s.equals("Turret")) {
			health = 1;
			ogHealth = 1;
		}
		if (s.equals("Iron")) {
			for (Player e: enemies) {
				if (e.inRange(loc, 3)) {
					trapped.add(e);
					owner.addDagger();
				}
			}
		}
		for (Player e: trapped) {
			e.setTrapped(true);
		}
	}
	
	public String getDirection() {
		return direction;
	}
	
	public void setDirection(String s) {
		direction = s;
	}
	
	public void setRookActive(boolean b) {
		rookActive = b;
	}
	
	public boolean rookActive() {
		return rookActive;
	}
	
	public void setColor(String s) {
		color = s;
	}
	
	public Player getTarget() {
		return target;
	}
	
	public void removeIron() {
		for (Player e: trapped) {
			e.setTrapped(false);
		}
	}
	
	public void deactivate() {
		activated = false;
	}
	
	public void activate() {
		System.out.println("Turret reactivated.");
		activated = true;
		health = ogHealth;
	}
	
	public boolean isActivated() {
		return activated;
	}
	
	public void setSpikes() {
		spikes = true;
	}
	
	public boolean hasSpikes() {
		return spikes;
	}
	
	public void setHealth(int i) {
		ogHealth = i;
		health = i;
	}
	
	public void activateTurret() {
		Player e1 = enemies.get(0);
		Player e2 = enemies.get(1);
		Player e3 = enemies.get(2);
		Player a1 = allies.get(0);
		Player a2 = allies.get(1);
		Player a3 = allies.get(2);
		Player target = attackClosest(e1, e2, e3);
		if (target != null) {
			owner.attack(target);
		}
		owner.resetAttack();
		if (owner.getUltcharge() >= 8) {
			if (a1.inRange(loc, 15)){
				a1.heal(0.05);
				owner.addHealing(a1.getMaxHP() * 0.05);
			}
			if (a2.inRange(loc, 15)){
				a2.heal(0.05);
				owner.addHealing(a2.getMaxHP() * 0.05);
			}
			if (a3.inRange(loc, 15)){
				a3.heal(0.05);
				owner.addHealing(a3.getMaxHP() * 0.05);
			}
		}
	}
	
	public Player attackClosest(Player a, Player b, Player c) {
		double smallest = 10000;
		double smallest2 = 10000;
		double smallest3 = 10000;
		ArrayList<Double> values = new ArrayList<>();
		Player target = null;
		if (a.isAlive() && a.inRange(loc, 15)) {
			smallest = a.getLoc().distanceTo(loc);
			if (owner.getUltcharge() >= 7) {
				ArrayList<Effect> e = new ArrayList<Effect>();
				Effect RoccoParalyze = new Effect("ignite", 0, 2);
				e.add(RoccoParalyze);
				a.addEffects(e);
				a.applyEffects();
			}
		}
		if (b.isAlive() && a.inRange(loc, 15)) {
			smallest2 = b.getLoc().distanceTo(loc);
			if (owner.getUltcharge() >= 7) {
				ArrayList<Effect> e = new ArrayList<Effect>();
				Effect RoccoParalyze = new Effect("ignite", 0, 2);
				e.add(RoccoParalyze);
				b.addEffects(e);
				b.applyEffects();
			}
		}
		if (c.isAlive() && a.inRange(loc, 15)) {
			smallest3 = c.getLoc().distanceTo(loc);
			if (owner.getUltcharge() >= 7) {
				ArrayList<Effect> e = new ArrayList<Effect>();
				Effect RoccoParalyze = new Effect("ignite", 0, 2);
				e.add(RoccoParalyze);
				c.addEffects(e);
				c.applyEffects();
			}
		}
		if (smallest == 10000 && smallest2 == 10000 && smallest3 == 10000) {
			return null;
		}
		values.add(smallest);
		values.add(smallest2);
		values.add(smallest3);
		Collections.sort(values);
		if (values.get(0) == smallest) {
			target = a;
		}
		if (values.get(0) == smallest2) {
			target = b;
		}
		if (values.get(0) == smallest3) {
			target = c;
		}
		return target;
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
			}
		}
		for (Player p: allies) {
			if (p.inRange(loc, 8)) {
				p.setShield();
			}
		}
	}
	
	public void activateField() {
		for (Player p: allies) {
			if (p.inRange(loc, 7)) {
				p.setField(true);
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
					p.takeDamage(p.getMaxHP() * 0.35);
					owner.addDamage(p.getMaxHP() * 0.35);
					ArrayList<Effect> e = new ArrayList<Effect>();
					Effect RoccoParalyze = new Effect("stun", 0, 2);
					e.add(RoccoParalyze);
					p.addEffects(e);
					p.applyEffects();
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
					p.takeDamage(p.getMaxHP() * 0.35);
					owner.addDamage(p.getMaxHP() * 0.35);
					ArrayList<Effect> e = new ArrayList<Effect>();
					Effect RoccoParalyze = new Effect("stun", 0, 2);
					e.add(RoccoParalyze);
					p.addEffects(e);
					p.applyEffects();
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
	
	public void activateFulmination() {
		for (Player p: enemies) {
			if (p.inRange(loc, 8)) {
				ArrayList<Effect> e = new ArrayList<Effect>();
				Effect RoccoParalyze = new Effect("paralyze", 0, 1);
				e.add(RoccoParalyze);
				p.addEffects(e);
				p.applyEffects();
			}
		}
	}
	
	public int getHealth() {
		return health;
	}
	
	public Player getOwner() {
		return owner;
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
	
	public boolean isTrapped(Player p) {
		if (trapped.contains(p)) {
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
	
	public boolean atRange(Location l, double r) {
		double d = loc.distanceTo(l);
		if (r >= d && !(d <= (r - 1))) {
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
	
	public void activateDragonStart() {
		for (Player p: allies) {
			p.setChance(0.15);
			p.setDodge(0.5);
		}
	}
	
	public void activateDragon() {
		for (Player p: enemies) {
			p.takeDamage(350);
			owner.addDamage(350);
		}
		for (Player p: allies) {
			p.resetChance();
			p.resetDodge();
			p.cleanse();
			p.heal(0.1);
			owner.addHealing(p.getMaxHP() * 0.1);
		}
	}
	
	public void activateUmbrella() {
		int range = 7;
		if (owner.ultActive()) {
			range = 10;
		}
		for (Player p: allies) {
			if (p.inRange(loc, range)) {
				ArrayList<Effect> e = new ArrayList<Effect>();
				Effect SolarProtect = new Effect("sight", 0.15, 2);
				Effect SolarProtect2 = new Effect("refine", 0.15, 2);
				if (owner.ultActive) {
					Effect SolarProtect3 = new Effect("protect", 0.2, 2);
					e.add(SolarProtect3);
				}
				e.add(SolarProtect);
				e.add(SolarProtect2);
				p.addEffects(e);
				p.applyEffects();
			}
		}
	}
	
	public void activatePylon() {
		for (Player p: allies) {
			if (p.inRange(loc, 4)) {
				p.heal(0.1);
				owner.addHealing(p.getMaxHP() * 0.1);
			}
		}
	}
	
	public void activateStar() {
		for (Player p: enemies) {
			if (p.inRange(loc, 11)) {
				p.dragIn(loc, 4);
				if (p.getLoc().eqLoc(loc)) {
					p.takeDamage(350);
					owner.addDamage(350);
					p.knockbacked(loc);
				}
			}
		}
	}
	
	public void movePawn() {
		if (direction.equals("up")) {
			loc.adjust(0, 4);
			if (loc.getY() == 27) {
				for (Player p: allies) {
					ArrayList<Effect> e = new ArrayList<Effect>();
					Effect RoccoParalyze = new Effect("power", 0.15, 1);
					e.add(RoccoParalyze);
					p.addEffects(e);
					p.applyEffects();
				}
				direction = "done";
			}
		}else {
			loc.adjust(0, -4);
			if (loc.getY() == 11) {
				for (Player p: allies) {
					ArrayList<Effect> e = new ArrayList<Effect>();
					Effect RoccoParalyze = new Effect("power", 0.15, 1);
					e.add(RoccoParalyze);
					p.addEffects(e);
					p.applyEffects();
				}
				direction = "done";
			}
		}
	}
	
	public void activateTrap() {
		System.out.println("Graffiti trap activated!");
		for (Player p: enemies) {
			p.reduceMovement(4);
		}
		switch (color) {
		case "Orange":
			for (Player p: enemies) {
				p.takeDamage(250);
				owner.addDamage(250);
			}
			break;
		case "Green":
			for (Player p: enemies) {
				ArrayList<Effect> e = new ArrayList<Effect>();
				Effect RoccoParalyze = new Effect("vulnerable", 0.25, 2);
				e.add(RoccoParalyze);
				p.addEffects(e);
				p.applyEffects();
			}
			break;
		case "Purple":
			for (Player p: enemies) {
				ArrayList<Effect> e = new ArrayList<Effect>();
				Effect RoccoParalyze = new Effect("daze", 0.15, 1);
				e.add(RoccoParalyze);
				p.addEffects(e);
				p.applyEffects();
			}
			break;
		}
	}
	
	public void setTarget(Player p) {
		target = p;
	}
	
	public void moveTo(int i) {
		for(int k = 0; k < i; k++) {
			if (loc.getX() > target.getLoc().getX()) {
				loc.adjust(-1 * 1, 0);
			}
			if (loc.getX() < target.getLoc().getX()) {
				loc.adjust(1, 0);
			}
			if (loc.getY() > target.getLoc().getY()) {
				loc.adjust(0, -1 * 1);
			}
			if (loc.getY() < target.getLoc().getY()) {
				loc.adjust(0, 1);
			}
			if (this.inRange(target, 3)) {
				target.takeDamage(200);
				owner.addDamage(200);
				ArrayList<Effect> e = new ArrayList<Effect>();
				Effect RoccoParalyze = new Effect("freeze", 0.15, 1);
				e.add(RoccoParalyze);
				target.addEffects(e);
				target.applyEffects();
				target = null;
				System.out.println("\"Freeze, monkey style!\"");
				return;
			}
		}
	}

}
