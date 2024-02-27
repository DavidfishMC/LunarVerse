package LunarVerse;

import java.applet.AudioClip;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.stream.Stream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Player {
	
	double health;
	double maxHealth;
	double ogDamage;
	double damage;
	String name;
	String ogName;
	String cover = "None";
	boolean turn;
	boolean alive = true;
	boolean attacked = false;
	boolean ignite = false;
	boolean dazed = false;
	boolean stunned = false;
	boolean paralyzed = false;
	boolean reflection = false;
	boolean freezed = false;
	boolean refined = false;
	boolean ultActive = false;
	int sights = 0;
	int actionTokens = 1;
	int cooldown = 0;
	double protect = 1;
	double absorb = 1;
	Location curLoc;
	double range;
	double ogRange;
	int movement;
	int dashes = 1;
	int jumps = 1;
	int ogMovement;
	int orbCount = 0;
	int ultCharge;
	ArrayList<Effect> effects = new ArrayList<Effect>();
	
	public Player(int hp, int damage, boolean turn, String name, int x, int y, int r, int m, int u) {
		health = hp;
		this.damage = damage;
		this.turn = turn;
		this.name = name;
		effects = new ArrayList<Effect>();
		maxHealth = hp;
		ogDamage = damage;
		curLoc = new Location(x, y);
		range = r;
		movement = m;
		ogMovement = m;
		ultCharge = u;
		ogName = name;
		ogRange = r;
	}
	
	public void setAttacked() {
		attacked = true;
	}
	
	public void resetCover() {
		cover = "None";
	}
	
	public void setCover(String s) {
		cover = s;
	}
	
	public void resetJumps() {
		jumps = 1;
	}
	
	public void addJumps(int i) {
		jumps = jumps + i;
	}
	
	public void addDashes(int i) {
		dashes = dashes + i;
	}
	
	public void useJump() {
		jumps--;
	}
	
	public boolean canJump() {
		return jumps > 0;
	}
	
	public void resetDashes() {
		dashes = 1;
	}
	
	public void useDash() {
		dashes--;
	}
	
	public boolean canDash() {
		return dashes > 0;
	}
	
	public boolean inReach(Location l) {
		int x = l.getX();
		int y = l.getY();
		int d = Math.abs(x - curLoc.getX()) + Math.abs(y - curLoc.getY());
		if(movement >= d) {
			return true;
		}else {
			return false;
		}
	}
	
	public boolean inReach(Location l, int i) {
		int x = l.getX();
		int y = l.getY();
		int d = Math.abs(x - curLoc.getX()) + Math.abs(y - curLoc.getY());
		if(i >= d) {
			return true;
		}else {
			return false;
		}
	}
	
	public boolean inRange(Location l, double r) {
		double d = curLoc.distanceTo(l);
		if(r >= d) {
			return true;
		}else {
			return false;
		}
	}
	
	public boolean inRange(Player p, double r) {
		Location otherLoc = p.getLoc();
		double d = curLoc.distanceTo(otherLoc);
		if(r >= d) {
			return true;
		}else {
			return false;
		}
	}
	
	public void setUlt() {
		ultActive = true;
	}
	
	public boolean ultActive() {
		return ultActive;
	}
	
	public void resetCooldown() {
		cooldown = 0;
	}
	
	public void setName(String name2) {
		name = name2;
	}
	
	public void resetName() {
		name = ogName;
	}
	
	public boolean hasSights() {
		if(sights == 0) {
			return false;
		}else {
			return true;
		}
	}
	
	public void setSights(int i) {
		sights = i;
	}
	
	public void useSight() {
		sights--;
	}
	
	public int getMovement() {
		return movement;
	}
	
	public void resetAttack() {
		attacked = false;
	}
	
	public boolean isParalyzed() {
		return paralyzed;
	}
	
	public void resetMovement() {
		movement = ogMovement;
	}
	
	public int getOrbCount() {
		return orbCount;
	}
	
	public int getUltCharge() {
		return ultCharge;
	}
	
	public boolean ultReady() {
		return orbCount == ultCharge;
	}
	
	public void resetUlt() {
		orbCount = 0;
	}
	
	public void resetRange() {
		range = ogRange;
	}
	
	public void setRange(int i) {
		range = i;
	}
	
	public void increaseDPS(double d) {
		damage = damage + (ogDamage * d);
	}
	
	public double getMaxHP() {
		return maxHealth;
	}
	
	public void setMaxHP(double d) {
		maxHealth = d;
		health = d;
	}
	
	public void knockbacked() {
		if(!isAlive()) {
			return;
		}
		int randomX = (int)(Math.random() * (5 - (-5) + 1)) + -5;
		int randomY = (int)(Math.random() * (5 - (-5) + 1)) + -5;
		curLoc.adjust(randomX, randomY);
		if(curLoc.getX() > 40) {
			curLoc.setX(40);
			takeDamage(100);
		}
		if(curLoc.getX() < 0) {
			curLoc.setX(0);
			takeDamage(100);
		}
		if(curLoc.getY() > 40) {
			curLoc.setY(40);
			takeDamage(100);
		}
		if(curLoc.getY() < 0) {
			curLoc.setY(0);
			takeDamage(100);
		}
		resetCover();
		System.out.println(name + " has been knocked back.");
	}
	
	public void move(int x, int y) {
		int d = Math.abs(x - curLoc.getX()) + Math.abs(y - curLoc.getY());
		if(d > movement) {
			System.out.println("Too far to move!");
			System.out.println();
		}else {
			curLoc.moveIn(x, y);
			movement = movement - d; 
			System.out.println();
		}
	}
	
	public void getOrb() {
		orbCount++;
	}
	
	public boolean inRange(Player p) {
		Location otherLoc = p.getLoc();
		double d = curLoc.distanceTo(otherLoc);
		if(range >= d) {
			return true;
		}else {
			return false;
		}
	}
	
	public Location getLoc() {
		return curLoc;
	}
	
	
	public boolean hasAttacked() {
		return attacked;
	}
	
	public void takeDamage(double d) {
		if(!isAlive()) {
			return;
		}
		double protect2 = protect;
		if(protect <= 0) {
			protect2 = 0.001;
		}
		d = d * protect2;
		d = Math.round(d * 10.0) / 10.0;
		health = health - d;
		if(health < 0) {
			health = 0;
		}
		System.out.println(name + " has taken " + d + " damage.");
		if(health == 0) {
			System.out.println(name + " is downed!");
		}
	}
	
	public boolean isFreezed() {
		return freezed;
	}
	
	public boolean isReflecting() {
		return reflection;
	}
	
	public String getCover() {
		return cover;
	}
	
	public void attack(Player p) {
		if(!p.isAlive()) {
			return;
		}
		double c = 0;
		int randomNum = (int)(Math.random() * (10 - (-10) + 1)) + -10;
		double rand2 = Math.random();
		if(rand2 <= 0.1) {
			c = damage * 0.3;
		}
		
		if(p.getName().equals("Bedrock") && p.ultActive() && p.inRange(this)) {
			p.getLoc().set(curLoc.getX(), curLoc.getY());
		}
		if(name.equals("Sammi") && range > 100) {
			p.takeDamage(damage + randomNum + c);
			attacked = true;
			return;
		}
		if(p.getCover().equals("Full")) {
			System.out.println("Attacked was Covered!");
			attacked = true;
			return;
		}
		if(p.getCover().equals("Partial")) {
			double rand = Math.random();
			if(rand <= 0.5) {
				System.out.println("Attacked was Covered!");
				attacked = true;
				return;
			}
		}
		if(p.isReflecting()) {
			takeDamage((damage + randomNum + c) * 0.5);
		}
		p.takeDamage(damage + randomNum + c);
		attacked = true;
		if(c > 0) {
			System.out.println("Critical shot!");
		}
	}
	
	public boolean isDazed() {
		return dazed;
	}
	
	public void addEffects(ArrayList<Effect> e) {
		if(!isAlive()) {
			return;
		}
		for(int j = 0; j < e.size(); j++) {
			if(refined) {
				if(e.get(j).getName().equals("ignite") || e.get(j).getName().equals("weak") || e.get(j).getName().equals("freeze") || e.get(j).getName().equals("vulnerable") || e.get(j).getName().equals("paralyze") || e.get(j).getName().equals("daze") || e.get(j).getName().equals("blind") || e.get(j).getName().equals("stun")) {
					e.remove(e.get(j));
					j--;
				}
			}
		}
		for(int i = 0; i < e.size(); i++) {
			if(e.get(i).getName().equals("ignite") && ignite) {
				for(int j = 0; j < effects.size(); j++) {
					if(effects.get(i).getName().equals("ignite")) {
						effects.remove(effects.get(i));
					}
				}
			}
			if(e.get(i).getName().equals("ignite") && !ignite) {
				ignite = true;
				System.out.println(name + " has been ignited.");
			}
			
			if(e.get(i).getName().equals("daze") && dazed) {
				for(int j = 0; j < effects.size(); j++) {
					if(effects.get(i).getName().equals("daze")) {
						effects.remove(effects.get(i));
					}
				}
			}
			if(e.get(i).getName().equals("daze") && !dazed) {
				dazed = true;
				System.out.println(name + " has been dazed.");
			}
			
			if(e.get(i).getName().equals("stun") && stunned) {
				for(int j = 0; j < effects.size(); j++) {
					if(effects.get(i).getName().equals("stun")) {
						effects.remove(effects.get(i));
					}
				}
			}
			if(e.get(i).getName().equals("stun") && !stunned) {
				stunned = true;
				System.out.println(name + " has been stunned.");
			}
			if(e.get(i).getName().equals("paralyze") && paralyzed) {
				for(int j = 0; j < effects.size(); j++) {
					if(effects.get(i).getName().equals("paralyze")) {
						effects.remove(effects.get(i));
					}
				}
			}
			if(e.get(i).getName().equals("paralyze") && !paralyzed) {
				paralyzed = true;
				System.out.println(name + " has been paralyzed.");
			}
			if(e.get(i).getName().equals("reflection") && reflection) {
				for(int j = 0; j < effects.size(); j++) {
					if(effects.get(i).getName().equals("reflection")) {
						effects.remove(effects.get(i));
					}
				}
			}
			if(e.get(i).getName().equals("reflection") && !reflection) {
				reflection = true;
				System.out.println(name + " is now reflecting.");
			}
			if(e.get(i).getName().equals("freeze") && freezed) {
				for(int j = 0; j < effects.size(); j++) {
					if(effects.get(i).getName().equals("freeze")) {
						effects.remove(effects.get(i));
					}
				}
			}
			if(e.get(i).getName().equals("freeze") && !freezed) {
				freezed = true;
				System.out.println(name + " has been freezed.");
			}
			if(e.get(i).getName().equals("refine") && refined) {
				for(int j = 0; j < effects.size(); j++) {
					if(effects.get(i).getName().equals("refine")) {
						effects.remove(effects.get(i));
					}
				}
			}
			if(e.get(i).getName().equals("refine") && !refined) {
				refined = true;
				System.out.println(name + " has been refined.");
			}
			effects.add(e.get(i));
		}
	}
	
	public void applyRegen() {
		for(int i = 0; i < effects.size(); i++) {
			Effect e = effects.get(i);
			if(e.getName().equals("heal")) {
				heal(e.getIncrease());
			}
		}
	}
	
	public void applyEffects() {
		for(int i = 0; i < effects.size(); i++) {
			Effect e = effects.get(i);
			if(e.getName().equals("power") && !e.isUsed()) {
				damage = damage + (ogDamage * e.getIncrease());
				System.out.println(name + " has been powered.");
			}
			if(e.getName().equals("protect") && !e.isUsed()) {
				protect = protect - e.getIncrease();
				System.out.println(name + " has been protected.");
			}
			if(e.getName().equals("weak") && !e.isUsed()) {
				damage = damage - (ogDamage * e.getIncrease());
				System.out.println(name + " has been weakened.");
			}
			if(e.getName().equals("vulnerable") && !e.isUsed()) {
				protect = protect + e.getIncrease();
				System.out.println(name + " is now vulnerable.");
			}
			if(e.getName().equals("blind") && !e.isUsed()) {
				range = range - (ogRange * e.getIncrease());
				System.out.println(name + " has been blinded.");
			}
			if(e.getName().equals("poison") && !e.isUsed()) {
				absorb = absorb - e.getIncrease();
				System.out.println(name + " has been poisoned.");
			}
			e.used();
		}
	}
	
	public void reduceEffects() {
		for(int i = 0; i < effects.size(); i++) {
			Effect e = effects.get(i);
			e.reduceTurns();
			if(e.getTurns() == 0) {
				if(e.getName().equals("power")) {
					damage = damage - (ogDamage * e.getIncrease());
					System.out.println(name + " is no longer powered.");
					i--;
				}
				if(e.getName().equals("protect")) {
					protect = protect + e.getIncrease();
					System.out.println(name + " is no longer protected.");
					i--;
				}
				if(e.getName().equals("poison")) {
					absorb = absorb + e.getIncrease();
					System.out.println(name + " is no longer poisoned.");
					i--;
				}
				if(e.getName().equals("blind")) {
					range = range + (ogRange * e.getIncrease());
					System.out.println(name + " is no longer blinded.");
					i--;
				}
				if(e.getName().equals("vulnerable")) {
					protect = protect - e.getIncrease();
					System.out.println(name + " is no longer vulnerable.");
					i--;
				}
				if(e.getName().equals("weak")) {
					damage = damage + (ogDamage * e.getIncrease());
					System.out.println(name + " is no longer weakened.");
					i--;
				}
				if(e.getName().equals("ignite")) {
					ignite = false;
					System.out.println(name + " is no longer ignited.");
					i--;
				}
				if(e.getName().equals("daze")) {
					dazed = false;
					System.out.println(name + " is no longer dazed.");
					i--;
				}
				if(e.getName().equals("stun")) {
					stunned = false;
					System.out.println(name + " is no longer stunned.");
					i--;
				}
				if(e.getName().equals("paralyze")) {
					paralyzed = false;
					System.out.println(name + " is no longer paralyzed.");
					i--;
				}
				if(e.getName().equals("reflection")) {
					reflection = false;
					System.out.println(name + " is no longer reflecting.");
					i--;
				}
				if(e.getName().equals("freeze")) {
					freezed = false;
					System.out.println(name + " is no longer freezed.");
					i--;
				}
				if(e.getName().equals("refine")) {
					refined = false;
					System.out.println(name + " is no longer refined.");
					i--;
				}
				effects.remove(e);
			}
		}
	}
	
	public double getHealth() {
		return health;
	}
	
	public boolean isStunned() {
		return stunned;
	}
	
	public void passTurn(Player p) {
		if(p.isStunned()) {
			System.out.println();
			System.out.println(p.getName() + " is stunned! Cannot switch.");
			return;
		}
		if(!p.isAlive()) {
			System.out.println();
			System.out.println(p.getName() + " is downed! Cannot switch.");
		}else {
			turn = false;
			p.setTurn();
		}
		
	}
	
	public boolean isIgnite() {
		return ignite;
	}
	
	public void setTurn() {
		if(!isAlive()) {
			if(ultActive && name.equals("Alex")) {
				health = maxHealth * 0.75;
				ultActive = false;
				resetUlt();
				System.out.println("\"Ha, missed me?\"");
				System.out.println();
			}
			for(int i = 0; i < effects.size(); i++) {
				effects.get(i).setTurns(1);
			}
			reduceEffects();
		}else {
			turn = true;
		}
	}
	
	public void endTurn() {
		turn = false;
		attacked = false;
	}
	
	public boolean getTurn() {
		return turn;
	}
	
	public double getDamage() {
		return damage;
	}
	
	public String getName() {
		return name;
	}
	
	public void useToken() {
		actionTokens--;
	}
	
	public void cleanse() {
		if(!isAlive()) {
			return;
		}
		for(int i = 0; i < effects.size(); i++) {
			Effect e = effects.get(i);
			if(e.getName().equals("vulnerable")) {
				protect = protect - e.getIncrease();
				System.out.println(name + " is no longer vulnerable.");
				i--;
			}
			if(e.getName().equals("weak")) {
				damage = damage + (ogDamage * e.getIncrease());
				System.out.println(name + " is no longer weakened.");
				i--;
			}
			if(e.getName().equals("ignite")) {
				ignite = false;
				System.out.println(name + " is no longer ignited.");
				i--;
			}
			if(e.getName().equals("daze")) {
				dazed = false;
				System.out.println(name + " is no longer dazed.");
				i--;
			}
			if(e.getName().equals("stun")) {
				stunned = false;
				System.out.println(name + " is no longer stunned.");
				i--;
			}
			if(e.getName().equals("paralyze")) {
				paralyzed = false;
				System.out.println(name + " is no longer paralyzed.");
				i--;
			}
			if(e.getName().equals("freeze")) {
				freezed = false;
				System.out.println(name + " is no longer freezed.");
				i--;
			}
			if(e.getName().equals("blind")) {
				range = range + (ogRange * e.getIncrease());
				System.out.println(name + " is no longer blind.");
				i--;
			}
			if(e.getName().equals("poison")) {
				absorb = absorb + e.getIncrease();
				System.out.println(name + " is no longer poisoned.");
				i--;
			}
			effects.remove(e);
		}
		System.out.println(name + " has been cleansed.");
	}
	
	public void increaseMovement(int i) {
		movement = movement + i;
	}
	
	public void heal(double d) {
		if(!isAlive()) {
			
		}else {
			double absorb2 = absorb;
			if(protect <= 0) {
				absorb2 = 0.001;
			}
			double e = (maxHealth * d) * absorb2;
			health = health + e;
			System.out.println(name + " has healed for " + e);
			if(health > maxHealth) {
				health = maxHealth;
			}
		}	
	}
	
	public void heal(double d, double i) {
		if(!isAlive()) {
			
		}else {
			double absorb2 = absorb;
			if(protect <= 0) {
				absorb2 = 0.001;
			}
			double e = (d * i) * absorb2;
			health = health + e;
			System.out.println(name + " has healed for " + e);
			if(health > maxHealth) {
				health = maxHealth;
			}
		}	
	}
	
	public void setCooldown(int i) {
		cooldown = i;
	}
	
	public void reduceCooldown() {
		if(cooldown != 0) {
			cooldown--;
		}
	}
	
	public boolean onCooldown() {
		if(cooldown <= 0) {
			return false;
		}else {
			System.out.println(name + "'s ability is on Cooldown!");
			System.out.println();
			return true;
		}
	}
	
	public boolean isAlive() {
		if(health <= 0) {
			return false;
		}else {
			return true;
		}
	}
	
	public String toString() {
		String weapon = "Not Used ❎";
		String ability = "Ready ✅";
		String move = String.valueOf(movement);
		String healthshow = "Health ❤️: ";
		String damageshow = ", Damage ⚔️: ";
		String covershow = ", Cover: ";
		if(attacked) {
			weapon = "Used ✅";
		}
		if(cooldown > 0) {
			ability = "On Cooldown 🕒 (" + cooldown + ")";
		}
		if(freezed == true) {
			weapon = "Freezed ❄️";
		}
		if(dazed == true) {
			ability = "Dazed 🌀";
		}
		if(paralyzed == true) {
			move = "Paralyzed";
		}
		if(health <= (maxHealth * 0.35)) {
			healthshow = "Health 💔: ";
		}
		if(damage < ogDamage) {
			damageshow = ", Damage 😞: ";
		}
		if(damage > ogDamage) {
			damageshow = ", Damage 💪: ";
		}
		if(cover.equals("Full")) {
			covershow = ", Cover 🛡️: ";
		}
		if(cover.equals("Partial")) {
			covershow = ", Cover 🪨: ";
		}
		health = Math.round(health * 10.0) / 10.0;
		return (healthshow + health + "/" + maxHealth + damageshow + damage +  covershow + cover + "\n" +"Weapon: " + weapon + ". Ability: " + ability + ". Ultimate Orbs 🪩: " + orbCount + "/" + ultCharge + "\n" + "Location 🗺️: " + curLoc + ", Movement 👟: " + move + ", Dashes 💨: " + dashes + ", Jumps 🦿: " + jumps);
	}
	
	public String effects() {
		return ("Dazed: " + dazed + ", Freezed: " + freezed + ", Ignited: " + ignite + ", Paralyzed: " + paralyzed + ", Stunned: " + stunned);
	}
	
	public double getRange() {
		return range;
	}
	
	public String voiceline() {
		/*
		try {
			String audio = "hello.wav";
			Music audioPlayer = new Music(audio); 
			audioPlayer.play();
		}catch (Exception e) {
			System.out.println(e);
		}
		*/
		int randomNum = (int)(Math.random() * (3 - 1 + 1)) + 1;
		if(name.equals("Lunar")) {
			if(randomNum == 1) {
				return ("\"Nothing hits as hard as a recharge.\"");
			}
			if(randomNum == 2) {
				return ("\"Energy absorption in progress.\"");
			}
			if(randomNum == 3) {
				return ("\"I sure hope this stuff is good for you.\"");
			}
		}
		if(name.equals("Finley")) {
			if(randomNum == 1) {
				return ("\"They'll never see it coming. Trust me guys.\"");
			}
			if(randomNum == 2) {
				return ("\"This skateboard has a whole lot of BOOM!\"");
			}
			if(randomNum == 3) {
				return ("\"Enjoy the surprise nerds!\"");
			}
		}
		if(name.equals("Mack")) {
			if(randomNum == 1) {
				return ("\"Stay safe, I'm coming for you.\"");
			}
			if(randomNum == 2) {
				return ("\"Looking the wrong way again huh?\"");
			}
			if(randomNum == 3) {
				return ("\"Bet you didn't see me here!\"");
			}
		}
		if(name.equals("Solar")) {
			if(randomNum == 1) {
				return ("\"Light shield is up and running!\"");
			}
			if(randomNum == 2) {
				return ("\"Go hurt the others, I need a break.\"");
			}
			if(randomNum == 3) {
				return ("\"I am protected! Wait did he say that?\"");
			}
		}
		if(name.equals("Cherry")) {
			if(ultActive) {
				if(randomNum == 1) {
					return ("\"EMP Neutralization deployed.\"");
				}
				if(randomNum == 2) {
					return ("\"Disabling their abilities and power.\"");
				}
				if(randomNum == 3) {
					return ("\"I hope this doesn't short circuit my mech!\"");
				}
			}else {
				if(randomNum == 1) {
					return ("\"They're weak, fear nothing now friends.\"");
				}
				if(randomNum == 2) {
					return ("\"Enjoying the exhaust pipe?\"");
				}
				if(randomNum == 3) {
					return ("\"I'm supposed to stop global warming not join it!\"");
				}
			}
		}
		if(name.equals("Dylan")) {
			if(randomNum == 1) {
				return ("\"Greedy and bold, they're coming for your gold.\"");
			}
			if(randomNum == 2) {
				return ("\"Sick em boys!\"");
			}
			if(randomNum == 3) {
				return ("\"Behold my latest loyal pets!\"");
			}
		}
		if(name.equals("Burt")) {
			if(randomNum == 1) {
				return ("\"Hit me with your strongest attacks.\"");
			}
			if(randomNum == 2) {
				return ("\"I'm not afraid, come at me.\"");
			}
			if(randomNum == 3) {
				return ("\"How does a sword reflect everything?\"");
			}
		}
		if(name.equals("Bolo")) {
			if(randomNum == 1) {
				return ("\"If I see you, you're dead.\"");
			}
			if(randomNum == 2) {
				return ("\"This bow packs quite a punch.\"");
			}
			if(randomNum == 3) {
				return ("\"Don't doubt my aim if you want me to go easy on you!\"");
			}
		}
		if(name.equals("Zero")) {
			if(randomNum == 1) {
				return ("\"Applying my hacks one second. Ok we're all good!\"");
			}
			if(randomNum == 2) {
				return ("\"Enjoy the refresher, this is a rare nice moment from me.\"");
			}
			if(randomNum == 3) {
				return ("\"Cheating in progress. Blame the game.\"");
			}
		}
		if(name.equals("Max")) {
			if(randomNum == 1) {
				return ("\"Go get them tiger.\"");
			}
			if(randomNum == 2) {
				return ("\"The perseverence lies within you.\"");
			}
			if(randomNum == 3) {
				return ("\"Why can't I give myself my own guidance?\"");
			}
		}
		if(name.equals("Eli")) {
			if(randomNum == 1) {
				return ("\"Buddies, we need healing!\"");
			}
			if(randomNum == 2) {
				return ("\"The sea truly has great power.\"");
			}
			if(randomNum == 3) {
				return ("\"Feeling refreshed? Good, now attack!\"");
			}
		}
		if(name.equals("Via")) {
			if(randomNum == 1) {
				return ("\"Arro, round them up!\"");
			}
			if(randomNum == 2) {
				return ("\"Group hug!\"");
			}
			if(randomNum == 3) {
				return ("\"Group them up, then knock them down.\"");
			}
		}
		if(name.equals("Louis")) {
			if(randomNum == 1) {
				return ("\"Execuse me, don't you know who I am?\"");
			}
			if(randomNum == 2) {
				return ("\"Stay away!\"");
			}
			if(randomNum == 3) {
				return ("\033[3m*Horn Noises, Doo Doo Doo Doooooooo*\033[0m");
			}
		}
		if(name.equals("Alex")) {
			if(randomNum == 1) {
				return ("\"Heads up, potion coming in!\"");
			}
			if(randomNum == 2) {
				return ("\"Pray for something good!\"");
			}
			if(randomNum == 3) {
				return ("\"This was the last of my nether wart!\"");
			}
		}
		if(name.equals("Orion")) {
			if(randomNum == 1) {
				return ("\"Speed it up guys!\"");
			}
			if(randomNum == 2) {
				return ("\"The faster we crush them, the faster I get to go on lunch break!\"");
			}
			if(randomNum == 3) {
				return ("\"Let's run those back quicker yeah?\"");
			}
		}
		if(name.equals("Kailani")) {
			if(randomNum == 1) {
				return ("\"Shelly! Let's take them for a ride.\"");
			}
			if(randomNum == 2) {
				return ("\"Lifeguard isn't here today!\"");
			}
			if(randomNum == 3) {
				return ("\"Out of my way!\"");
			}
		}
		if(name.equals("Ashley")) {
			if(randomNum == 1) {
				return ("\"Sparks flowers being grown.\"");
			}
			if(randomNum == 2) {
				return ("\"Nature is beautiful isn't it? And cruel.\"");
			}
			if(randomNum == 3) {
				return ("\"This will cut the pain, and them!\"");
			}
		}
		if(name.equals("Rocco")) {
			if(randomNum == 1) {
				return ("\"Have fun with this guy!\"");
			}
			if(randomNum == 2) {
				return ("\"Run for your lives! Oh I meant them not us.\"");
			}
			if(randomNum == 3) {
				return ("\"Time to whip out combat tactic #245.\"");
			}
		}
		if(name.equals("Sammi")) {
			if(randomNum == 1) {
				return ("\"I never miss. Whoever said I did was lying.\"");
			}
			if(randomNum == 2) {
				return ("\"Activating aimbot, literally.\"");
			}
			if(randomNum == 3) {
				return ("\"Don't try to hide, it's pointless.\"");
			}
		}
		return "";
	}
	

}
