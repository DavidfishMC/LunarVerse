package LunarVerse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Utility {
	
	Player owner;
	Player target;
	String name;
	int health = 0;
	int ogHealth = 0;
	int size = 3;
	ArrayList<Player> allies = new ArrayList<Player>();
	ArrayList<Player> enemies = new ArrayList<Player>();
	ArrayList<Player> trapped = new ArrayList<Player>();
	Location loc;
	boolean pickedUp = false;
	boolean activated = true;
	boolean escape = false;
	boolean spikes = false;
	boolean rookActive = true;
	boolean pounced = false;
	boolean destroy = false;
	boolean bright = true;
	boolean eclipse = true;
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
		if (s.equals("Dynamite")) {
			health = 2;
		}
		if (s.equals("Peri")) {
			health = 3;
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
	
	public void resetPounce() {
		pounced = false;
	}
	
	public void resetDestroy() {
		destroy = false;
	}
	
	public void setDirection(String s) {
		direction = s;
	}
	
	public boolean getSphere() {
		return bright;
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
	
	public void setForce() {
			for (Player p: enemies) {
				p.getLoc().set(loc.getX(), loc.getY());
			}
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
			target.resetCover();
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
				a.ignite(2);
			}
		}
		if (b.isAlive() && a.inRange(loc, 15)) {
			smallest2 = b.getLoc().distanceTo(loc);
			if (owner.getUltcharge() >= 7) {
				a.ignite(2);
			}
		}
		if (c.isAlive() && a.inRange(loc, 15)) {
			smallest3 = c.getLoc().distanceTo(loc);
			if (owner.getUltcharge() >= 7) {
				a.ignite(2);
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
				p.paralyze(1);
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
					p.weak(0.1, 1);
				}
			}
			owner.getLoc().set(loc.getX(), loc.getY());
		}else {
			owner.getLoc().set(loc.getX(), loc.getY());
			for (Player p: enemies) {
				if (owner.inRange(p)) {
					p.takeDamage(175);
					owner.addDamage(175);
					p.weak(0.1, 1);
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
					p.stun(2);
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
					p.stun(2);
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
				p.paralyze(1);
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
				p.protect(0.1, 1);
			}else if (gemstone.equals("diamond") && p.inRange(loc, 6)) {
				p.protect(0.1, 1);
				p.increaseMovement(2);
			}if (gemstone.equals("kunzite") && p.inRange(loc, 8)) {
				p.protect(0.1, 1);
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
				if (owner.ultActive) {
					p.protect(0.2,1);
				}
				p.sightsee(0.15,2);
				p.refine(2);
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
					p.power(0.15, 1);
				}
				direction = "done";
			}
		}else {
			loc.adjust(0, -4);
			if (loc.getY() == 11) {
				for (Player p: allies) {
					p.power(0.15, 1);
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
				p.vulnerable(0.25, 2);
			}
			break;
		case "Purple":
			for (Player p: enemies) {
				p.daze(1);
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
				target.freeze(1);
				target = null;
				System.out.println("\"Freeze, monkey style!\"");
				return;
			}
		}
	}
	
	public void move(int x, int y) {
		Location l = new Location(x, y);
		loc.set(x, y);
		owner.mochiMove();
		System.out.println();
	}
	
	public boolean inRange(Location l) {
		double d = loc.distanceTo(l);
		if (4 >= d) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean inReach(Location l) {
		int x = l.getX();
		int y = l.getY();
		int d = Math.abs(x - loc.getX()) + Math.abs(y - loc.getY());
		if (owner.getMochiMovement() >= d) {
			return true;
		} else {
			return false;
		}
	}
	
	public void pounce() {
		Scanner input = new Scanner(System.in);
		if(pounced) {
			System.out.println("Mochi has already pounced this turn!");
			System.out.println();
			return;
		}
		if(!this.inRange(enemies.get(0), 20) && !this.inRange(enemies.get(1), 20) && !this.inRange(enemies.get(2), 20)) {
			System.out.println("No targets in range!");
			System.out.println();
			return;
		}
		String range = "No";
		if(this.inRange(enemies.get(0), 4)) {
			range = "Yes";
		}
		String range2 = "No";
		if(this.inRange(enemies.get(1), 4)) {
			range2 = "Yes";
		}
		String range3 = "No";
		if(this.inRange(enemies.get(2), 4)) {
			range3 = "Yes";
		}
		System.out.println("1: " + enemies.get(0).getSkin() +". Health: " +  enemies.get(0).getHealth() + "/" + enemies.get(0).getMaxHP() + ". In Range: " + range + ".");
		System.out.println("2: " + enemies.get(1).getSkin() +". Health: " +  enemies.get(1).getHealth() + "/" + enemies.get(1).getMaxHP() + ". In Range: " + range2 + ".");
		System.out.println("3: " + enemies.get(2).getSkin() +". Health: " +  enemies.get(2).getHealth() + "/" + enemies.get(2).getMaxHP() + ". In Range: " + range3 + ".");
		System.out.println("Who should Mochi pounce on: ");
		String targetResponse = input.next();
		if(targetResponse.equals("1")) {
			if(this.inRange(enemies.get(0), 4)) {
				enemies.get(0).takeDamage(100);
				owner.addDamage(100);
				pounced = true;
				loc.set(enemies.get(0).getLoc().getX(), enemies.get(0).getLoc().getY());
				if (owner.ultActive()) {
					if (allies.get(0).inRange(enemies.get(0), 5)) {
						allies.get(0).heal(0.05);
						owner.addHealing(allies.get(0).getMaxHP() * 0.05);
					}
					if (allies.get(1).inRange(enemies.get(0), 5)) {
						allies.get(1).heal(0.05);
						owner.addHealing(allies.get(1).getMaxHP() * 0.05);
					}
					if (allies.get(2).inRange(enemies.get(0), 5)) {
						allies.get(2).heal(0.05);
						owner.addHealing(allies.get(2).getMaxHP() * 0.05);
					}
					owner.setShield();
				}
				System.out.println("\"No mercy Mochi!\"");
			}else {
				System.out.println();
				System.out.println("Target is out of range!");
				System.out.println();
			}
		}
		if(targetResponse.equals("2")) {
			if(this.inRange(enemies.get(1), 4)) {
				enemies.get(1).takeDamage(100);
				owner.addDamage(100);
				pounced = true;
				loc.set(enemies.get(1).getLoc().getX(), enemies.get(1).getLoc().getY());
				if (owner.ultActive()) {
					if (allies.get(0).inRange(enemies.get(1), 5)) {
						allies.get(0).heal(0.05);
						owner.addHealing(allies.get(0).getMaxHP() * 0.05);
					}
					if (allies.get(1).inRange(enemies.get(1), 5)) {
						allies.get(1).heal(0.05);
						owner.addHealing(allies.get(1).getMaxHP() * 0.05);
					}
					if (allies.get(2).inRange(enemies.get(1), 5)) {
						allies.get(2).heal(0.05);
						owner.addHealing(allies.get(2).getMaxHP() * 0.05);
					}
					owner.setShield();
				}
				System.out.println("\"No mercy Mochi!\"");
			}else {
				System.out.println();
				System.out.println("Target is out of range!");
				System.out.println();
			}
		}
		if(targetResponse.equals("3")) {
			if(this.inRange(enemies.get(2), 4)) {
				enemies.get(2).takeDamage(100);
				owner.addDamage(100);
				pounced = true;
				loc.set(enemies.get(2).getLoc().getX(), enemies.get(2).getLoc().getY());
				if (owner.ultActive()) {
					if (allies.get(0).inRange(enemies.get(2), 5)) {
						allies.get(0).heal(0.05);
						owner.addHealing(allies.get(0).getMaxHP() * 0.05);
					}
					if (allies.get(1).inRange(enemies.get(2), 5)) {
						allies.get(1).heal(0.05);
						owner.addHealing(allies.get(1).getMaxHP() * 0.05);
					}
					if (allies.get(2).inRange(enemies.get(2), 5)) {
						allies.get(2).heal(0.05);
						owner.addHealing(allies.get(2).getMaxHP() * 0.05);
					}
					owner.setShield();
				}
				System.out.println("\"No mercy Mochi!\"");
			}else {
				System.out.println();
				System.out.println("Target is out of range!");
				System.out.println();
			}
		}
		System.out.println();
	}
	
	public void destroyUtility() {
		if(destroy) {
			System.out.println("Mochi has already destroyed utility this turn!");
			System.out.println();
			return;
		}
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).getName().equals("Sensor") && (GameSim.utility.get(j).owner(enemies.get(0)) || GameSim.utility.get(j).owner(enemies.get(1)) || GameSim.utility.get(j).owner(enemies.get(2))) && GameSim.utility.get(j).getLoc().eqLoc(loc)) {
				GameSim.utility.remove(j);
				System.out.println("Enemy sound sensor destroyed.");
				j--;
				destroy = true;
				System.out.println("\"Good work Mochi!\"");
				return;
			}
		}
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).getName().equals("Sphere") && (GameSim.utility.get(j).owner(enemies.get(0)) || GameSim.utility.get(j).owner(enemies.get(1)) || GameSim.utility.get(j).owner(enemies.get(2))) && GameSim.utility.get(j).getLoc().eqLoc(loc)) {
				GameSim.utility.get(j).takeHit();
				System.out.println("Enemy Symphony Sphere has " + GameSim.utility.get(j).getHealth() + " more health left.");
				if (GameSim.utility.get(j).getHealth() <= 0) {
					GameSim.utility.remove(j);
					System.out.println("Enemy Symphony Sphere destroyed.");
				}
				destroy = true;
				System.out.println("\"Good work Mochi!\"");
				return;
			}
		}
		
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).getName().equals("Gemstone") && (GameSim.utility.get(j).owner(enemies.get(0)) || GameSim.utility.get(j).owner(enemies.get(1)) || GameSim.utility.get(j).owner(enemies.get(2))) && GameSim.utility.get(j).getLoc().eqLoc(loc)) {
				GameSim.utility.get(j).takeHit();
				System.out.println("Enemy Gemstone Lode has " + GameSim.utility.get(j).getHealth() + " more health left.");
				if (GameSim.utility.get(j).getHealth() <= 0) {
					GameSim.utility.remove(j);
					System.out.println("Enemy Gemstone Lode destroyed.");
				}
				destroy = true;
				System.out.println("\"Good work Mochi!\"");
				return;
			}
		}
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).getName().equals("Field") && (GameSim.utility.get(j).owner(enemies.get(0)) || GameSim.utility.get(j).owner(enemies.get(1)) || GameSim.utility.get(j).owner(enemies.get(2))) && GameSim.utility.get(j).getLoc().eqLoc(loc)) {
				GameSim.utility.remove(j);
				System.out.println("Enemy Electromagnetic field destroyed.");
				j--;
				destroy = true;
				System.out.println("\"Good work Mochi!\"");
				return;
			}
		}
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).getName().equals("Umbrella") && (GameSim.utility.get(j).owner(enemies.get(0)) || GameSim.utility.get(j).owner(enemies.get(1)) || GameSim.utility.get(j).owner(enemies.get(2))) && GameSim.utility.get(j).getLoc().eqLoc(loc)) {
				GameSim.utility.remove(j);
				System.out.println("Enemy Umbrella destroyed.");
				j--;
				destroy = true;
				System.out.println("\"Good work Mochi!\"");
				return;
			}
		}
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).getName().equals("Pylon") && (GameSim.utility.get(j).owner(enemies.get(0)) || GameSim.utility.get(j).owner(enemies.get(1)) || GameSim.utility.get(j).owner(enemies.get(2))) && GameSim.utility.get(j).getLoc().eqLoc(loc)) {
				GameSim.utility.remove(j);
				System.out.println("Enemy Honey Pylon destroyed.");
				j--;
				destroy = true;
				System.out.println("\"Good work Mochi!\"");
				return;
			}
		}
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).getName().equals("Turret") && (GameSim.utility.get(j).owner(enemies.get(0)) || GameSim.utility.get(j).owner(enemies.get(1)) || GameSim.utility.get(j).owner(enemies.get(2))) && GameSim.utility.get(j).getLoc().eqLoc(loc)) {
				GameSim.utility.get(j).takeHit();
				System.out.println("Enemy Turret has " + GameSim.utility.get(j).getHealth() + " more health left.");
				if (GameSim.utility.get(j).getHealth() <= 0) {
					GameSim.utility.get(j).deactivate();
					System.out.println("Enemy Turret deactivated.");
				}
				destroy = true;
				System.out.println("\"Good work Mochi!\"");
				return;
			}
		}
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).getName().equals("Pawn") && (GameSim.utility.get(j).owner(enemies.get(0)) || GameSim.utility.get(j).owner(enemies.get(1)) || GameSim.utility.get(j).owner(enemies.get(2))) && GameSim.utility.get(j).getLoc().eqLoc(loc)) {
				GameSim.utility.remove(j);
				System.out.println("Enemy Pawn destroyed.");
				j--;
				destroy = true;
				System.out.println("\"Good work Mochi!\"");
				return;
			}
		}
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).getName().equals("Mural") && (GameSim.utility.get(j).owner(enemies.get(0)) || GameSim.utility.get(j).owner(enemies.get(1)) || GameSim.utility.get(j).owner(enemies.get(2))) && GameSim.utility.get(j).getLoc().eqLoc(loc)) {
				GameSim.utility.remove(j);
				System.out.println("Enemy Mural destroyed.");
				j--;
				destroy = true;
				System.out.println("\"Good work Mochi!\"");
				return;
			}
		}
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).getName().equals("Sock") && (GameSim.utility.get(j).owner(enemies.get(0)) || GameSim.utility.get(j).owner(enemies.get(1)) || GameSim.utility.get(j).owner(enemies.get(2))) && GameSim.utility.get(j).getLoc().eqLoc(loc)) {
				GameSim.utility.remove(j);
				System.out.println("Enemy Sock Monkey destroyed.");
				j--;
				destroy = true;
				System.out.println("\"Good work Mochi!\"");
				return;
			}
		}
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).getName().equals("Support") && (GameSim.utility.get(j).owner(enemies.get(0)) || GameSim.utility.get(j).owner(enemies.get(1)) || GameSim.utility.get(j).owner(enemies.get(2))) && GameSim.utility.get(j).getLoc().eqLoc(loc)) {
				GameSim.utility.remove(j);
				System.out.println("Enemy Sockyman Support destroyed.");
				j--;
				destroy = true;
				System.out.println("\"Good work Mochi!\"");
				return;
			}
		}
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).getName().equals("Matrix") && (GameSim.utility.get(j).owner(enemies.get(0)) || GameSim.utility.get(j).owner(enemies.get(1)) || GameSim.utility.get(j).owner(enemies.get(2))) && GameSim.utility.get(j).getLoc().eqLoc(loc)) {
				GameSim.utility.remove(j);
				System.out.println("Enemy Immortality Matrix destroyed.");
				j--;
				destroy = true;
				System.out.println("\"Good work Mochi!\"");
				return;
			}
		}
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).getName().equals("Peri") && (GameSim.utility.get(j).owner(enemies.get(0)) || GameSim.utility.get(j).owner(enemies.get(1)) || GameSim.utility.get(j).owner(enemies.get(2))) && GameSim.utility.get(j).getLoc().eqLoc(loc)) {
				GameSim.utility.get(j).takeHit();
				System.out.println("Enemy Peri has " + GameSim.utility.get(j).getHealth() + " more health left.");
				if (GameSim.utility.get(j).getHealth() <= 0) {
					GameSim.utility.remove(j);
					System.out.println("Enemy Peri destroyed.");
				}
				destroy = true;
				System.out.println("\"Good work Mochi!\"");
				return;
			}
		}
	}
	
	public void returnTo() {
		loc.set(owner.getLoc().getX(), owner.getLoc().getY());
		System.out.println("\"Back to me Mochi!\"");
	}
	
	public void returnBubble() {
		loc.set(owner.getLoc().getX(), owner.getLoc().getY());
		System.out.println("\"Kinetic Bubble has returned!\"");
		if (owner.ultActive()) {
			setForce();
		}
	}
	
	public int getSize() {
		return size;
	}
	
	public void changeSize() {
	    Scanner input = new Scanner(System.in);
	    int number = 0;
	    boolean valid = false;

	    while (!valid) {
	        System.out.print("What size do you want to change the bubble to (3 - 15): ");
	        if (input.hasNextInt()) {
	            number = input.nextInt();
	            if (number >= 3 && number <= 15) {
	                valid = true;
	            } else {
	                System.out.println("Invalid size. Please enter a number between 3 and 15.");
	            }
	        } else {
	            System.out.println("Invalid input. Please enter a valid integer.");
	            input.next(); // Clear the invalid input
	        }
	    }

	    size = number;
	    System.out.print("Kinetic Bubble radius changed to " + size);
	}

	
	public void firework() {
		int randomNum = (int) (Math.random() * (2 - 0 + 0)) + 0;
		Player target = enemies.get(randomNum);
		if (owner.inRange(target)) {
			target.resetCover();
			target.weak(0.15,1);
			System.out.println(target.getSkin() + " was fireworked!");
		}
	}
	
	public void steam() {
		for (Player e: enemies) {
			while (!e.isFortified() && owner.inRange(e)) {
				e.knockbacked(owner.getLoc());
			}
		}
	}
	
	public void setSphere(boolean b) {
		bright = b;
	}
	
	public boolean isEclipse() {
		return eclipse;
	}
	
	public void checkSphere() {
		if (bright) {
			for (Player e: allies) {
				if (e.inRange(loc, 4)) {
					e.heal(0.05);
					owner.addHealing(e.getMaxHP() * 0.05);
				}
			}
		}else {
			for (Player e: enemies) {
				if (e.inRange(loc, 4)) {
					e.takeDamage(200);
					owner.addDamage(200);
				}
			}
		}
		if (direction.equals("left")) {
			loc.adjust(-6, 0);
		}
		if (direction.equals("right")) {
			loc.adjust(6, 0);
		}
		if (direction.equals("up")) {
			loc.adjust(0, -6);
		}
		if (direction.equals("down")) {
			loc.adjust(0, 6);
		}
	}
	
	public void setPhase() {
		eclipse = !eclipse;
	}
	
	public void activateDynamite() {
		System.out.println("Dyanmite blasted!");
		for (Player p: enemies) {
			if (!p.isIgnite()) {
				p.ignite(2);
				owner.addDamage(350);
			}else {
				p.addFiretick();
				owner.addDamage(25);
			}
		}
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).getName().equals("Sensor") && (GameSim.utility.get(j).owner(enemies.get(0)) || GameSim.utility.get(j).owner(enemies.get(1)) || GameSim.utility.get(j).owner(enemies.get(2))) && GameSim.utility.get(j).getLoc().inRange(loc, 5)) {
				GameSim.utility.remove(j);
				System.out.println("Enemy sound sensor destroyed.");
				j--;
			}
		}
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).getName().equals("Sphere") && (GameSim.utility.get(j).owner(enemies.get(0)) || GameSim.utility.get(j).owner(enemies.get(1)) || GameSim.utility.get(j).owner(enemies.get(2))) && GameSim.utility.get(j).getLoc().inRange(loc, 5)) {
				GameSim.utility.get(j).takeHit();
				System.out.println("Enemy Symphony Sphere has " + GameSim.utility.get(j).getHealth() + " more health left.");
				if (GameSim.utility.get(j).getHealth() <= 0) {
					GameSim.utility.remove(j);
					System.out.println("Enemy Symphony Sphere destroyed.");
				}
			}
		}
		
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).getName().equals("Gemstone") && (GameSim.utility.get(j).owner(enemies.get(0)) || GameSim.utility.get(j).owner(enemies.get(1)) || GameSim.utility.get(j).owner(enemies.get(2))) && GameSim.utility.get(j).getLoc().inRange(loc, 5)) {
				GameSim.utility.get(j).takeHit();
				System.out.println("Enemy Gemstone Lode has " + GameSim.utility.get(j).getHealth() + " more health left.");
				if (GameSim.utility.get(j).getHealth() <= 0) {
					GameSim.utility.remove(j);
					System.out.println("Enemy Gemstone Lode destroyed.");
				}
			}
		}
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).getName().equals("Field") && (GameSim.utility.get(j).owner(enemies.get(0)) || GameSim.utility.get(j).owner(enemies.get(1)) || GameSim.utility.get(j).owner(enemies.get(2))) && GameSim.utility.get(j).getLoc().inRange(loc, 5)) {
				GameSim.utility.remove(j);
				System.out.println("Enemy Electromagnetic field destroyed.");
				j--;
			}
		}
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).getName().equals("Umbrella") && (GameSim.utility.get(j).owner(enemies.get(0)) || GameSim.utility.get(j).owner(enemies.get(1)) || GameSim.utility.get(j).owner(enemies.get(2))) && GameSim.utility.get(j).getLoc().inRange(loc, 5)) {
				GameSim.utility.remove(j);
				System.out.println("Enemy Umbrella destroyed.");
				j--;
			}
		}
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).getName().equals("Pylon") && (GameSim.utility.get(j).owner(enemies.get(0)) || GameSim.utility.get(j).owner(enemies.get(1)) || GameSim.utility.get(j).owner(enemies.get(2))) && GameSim.utility.get(j).getLoc().inRange(loc, 5)) {
				GameSim.utility.remove(j);
				System.out.println("Enemy Honey Pylon destroyed.");
				j--;
			}
		}
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).getName().equals("Turret") && (GameSim.utility.get(j).owner(enemies.get(0)) || GameSim.utility.get(j).owner(enemies.get(1)) || GameSim.utility.get(j).owner(enemies.get(2))) && GameSim.utility.get(j).getLoc().inRange(loc, 5)) {
				GameSim.utility.get(j).takeHit();
				System.out.println("Enemy Turret has " + GameSim.utility.get(j).getHealth() + " more health left.");
				if (GameSim.utility.get(j).getHealth() <= 0) {
					GameSim.utility.get(j).deactivate();
					System.out.println("Enemy Turret deactivated.");
				}
			}
		}
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).getName().equals("Pawn") && (GameSim.utility.get(j).owner(enemies.get(0)) || GameSim.utility.get(j).owner(enemies.get(1)) || GameSim.utility.get(j).owner(enemies.get(2))) && GameSim.utility.get(j).getLoc().inRange(loc, 5)) {
				GameSim.utility.remove(j);
				System.out.println("Enemy Pawn destroyed.");
				j--;
			}
		}
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).getName().equals("Mural") && (GameSim.utility.get(j).owner(enemies.get(0)) || GameSim.utility.get(j).owner(enemies.get(1)) || GameSim.utility.get(j).owner(enemies.get(2))) && GameSim.utility.get(j).getLoc().inRange(loc, 5)) {
				GameSim.utility.remove(j);
				System.out.println("Enemy Mural destroyed.");
				j--;
			}
		}
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).getName().equals("Sock") && (GameSim.utility.get(j).owner(enemies.get(0)) || GameSim.utility.get(j).owner(enemies.get(1)) || GameSim.utility.get(j).owner(enemies.get(2))) && GameSim.utility.get(j).getLoc().inRange(loc, 5)) {
				GameSim.utility.remove(j);
				System.out.println("Enemy Sock Monkey destroyed.");
				j--;
			}
		}
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).getName().equals("Support") && (GameSim.utility.get(j).owner(enemies.get(0)) || GameSim.utility.get(j).owner(enemies.get(1)) || GameSim.utility.get(j).owner(enemies.get(2))) && GameSim.utility.get(j).getLoc().inRange(loc, 5)) {
				GameSim.utility.remove(j);
				System.out.println("Enemy Sockyman Support destroyed.");
				j--;
			}
		}
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).getName().equals("Matrix") && (GameSim.utility.get(j).owner(enemies.get(0)) || GameSim.utility.get(j).owner(enemies.get(1)) || GameSim.utility.get(j).owner(enemies.get(2))) && GameSim.utility.get(j).getLoc().inRange(loc, 5)) {
				GameSim.utility.remove(j);
				System.out.println("Enemy Immortality Matrix destroyed.");
				j--;
			}
		}
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).getName().equals("Dynamite") && (GameSim.utility.get(j).owner(enemies.get(0)) || GameSim.utility.get(j).owner(enemies.get(1)) || GameSim.utility.get(j).owner(enemies.get(2))) && GameSim.utility.get(j).getLoc().inRange(loc, 5)) {
				GameSim.utility.get(j).takeHit();
				System.out.println("Enemy Dynamite has " + GameSim.utility.get(j).getHealth() + " more health left.");
				if (GameSim.utility.get(j).getHealth() <= 0) {
					GameSim.utility.remove(j);
					System.out.println("Enemy Dynamite destroyed.");
				}
			}
		}
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).getName().equals("Peri") && (GameSim.utility.get(j).owner(enemies.get(0)) || GameSim.utility.get(j).owner(enemies.get(1)) || GameSim.utility.get(j).owner(enemies.get(2))) && GameSim.utility.get(j).getLoc().inRange(loc, 5)) {
				GameSim.utility.get(j).takeHit();
				System.out.println("Enemy Peri has " + GameSim.utility.get(j).getHealth() + " more health left.");
				if (GameSim.utility.get(j).getHealth() <= 0) {
					GameSim.utility.remove(j);
					System.out.println("Enemy Peri destroyed.");
				}
			}
		}
	}
	
	public void activatePeri() {
		for (Player p: enemies) {
			if (p.inRange(loc, 12)) {
				p.resetCover();
			}
		}
		owner.setShield();
	}

}
