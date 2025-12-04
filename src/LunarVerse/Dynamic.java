package LunarVerse;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Dynamic {
	
	Player init;
	Player acti;
	Player ally;
	Player e1;
	Player e2;
	Player e3;
	ArrayList<Player> enemies = new ArrayList<Player>();
	String name;
	int cooldown = 0;

	public Dynamic(Player i, Player ac, Player a, Player e, Player e2, Player e3) {
		init = i;
		acti = ac;
		ally = a;
		e1 = e;
		this.e2 = e2;
		this.e3 = e3;
		System.out.println("Dynamic between " + init.getSkin() + " and " + acti.getSkin() + " is activated!");
		name = init.getSkin() + " and " + acti.getSkin();
	}
	
	public Player getActi() {
		return acti;
	}
	
	public String getName() {
		return name;
	}
	
	public int getCooldown() {
		return cooldown;
	}
	
	public void reduceCooldown() {
		if (cooldown != 0) {
			cooldown--;
		}
	}
	
	public void useDynamic() {
		Scanner input = new Scanner(System.in);
		if (cooldown == 0 && init.isAlive()) {
			try {
				String audio = "audio/dynamic.wav";
				Music victoryPlayer = new Music(audio, false); 
				victoryPlayer.play();
			}catch (Exception e) {
				System.out.println(e);
			}
			switch (acti.getName()) {
			  case "Lunar":
				  if (e1.inRange(acti.getLoc(), 7)) {
					  e1.takeDamage(init.getDamage() * 1.25);
				  }
				  if (e2.inRange(acti.getLoc(), 7)) {
					  e2.takeDamage(init.getDamage() * 1.25);
				  }
				  if (e3.inRange(acti.getLoc(), 7)) {
					  e3.takeDamage(init.getDamage() * 1.25);
				  }
				  acti.setShield();
				  System.out.println(acti.getSkin() + ": " + "\"Full merge chaos!\"");
				  System.out.println(init.getSkin() + ": " + "\"Feel the corruption!\"");
			    break;
			  case "Aidan":
				  acti.setSights(5);
				  System.out.println(acti.getSkin() + ": " + "\"You're in my storm now!\"");
				  System.out.println(init.getSkin() + ": " + "\"The storm is at its peak!\"");
			    break;
			  case "Jing":
				  e1.ignite(3);
				  e2.ignite(3);
				  e3.ignite(3);
				  acti.heal(0.15);
				  System.out.println(acti.getSkin() + ": " + "\"Two fire breathing dragons? Awesome!\"");
				  System.out.println(init.getSkin() + ": " + "\"The more the better!.\"");
			    break;
			  case "Rocco":
				  e1.knockbacked(acti.getLoc());
				  e2.knockbacked(acti.getLoc());
				  e3.knockbacked(acti.getLoc());
				  acti.resetAttack();
				  acti.reduceCooldown();
				  System.out.println(acti.getSkin() + ": " + "\"Let's run this back again!\"");
				  System.out.println(init.getSkin() + ": " + "\"Area is all clear for you brother!\"");
			    break;
			  case "Julian":
				  e1.daze(1);
				  e2.daze(1);
				  e3.daze(1);
				  input = new Scanner(System.in);
					if(!acti.inRange(e1, 20) && !acti.inRange(e2, 20) && !acti.inRange(e3, 20)) {
						System.out.println("Can't stage dive to anyone!");
						System.out.println();
					}else {
						
					}
					String range = "No";
					if(acti.inRange(e1)) {
						range = "Yes";
					}
					String range2 = "No";
					if(acti.inRange(e2)) {
						range2 = "Yes";
					}
					String range3 = "No";
					if(acti.inRange(e3)) {
						range3 = "Yes";
					}
					System.out.println();
					System.out.println("1: " + e1.getSkin() +". Health: " +  e1.getHealth() + "/" + e1.getMaxHP() + ". In Range: " + range + ".");
					System.out.println("2: " + e2.getSkin() +". Health: " +  e2.getHealth() + "/" + e2.getMaxHP() + ". In Range: " + range2 + ".");
					System.out.println("3: " + e3.getSkin() +". Health: " +  e3.getHealth() + "/" + e3.getMaxHP() + ". In Range: " + range3 + ".");
					System.out.println("Who do you want to stage dive on: ");
					String targetResponse = input.next();
					if(targetResponse.equals("1")) {
						if(acti.inRange(e1, 20)) {
							acti.getLoc().set(e1.getLoc().getX(), e1.getLoc().getY());
							acti.protect(0.5, 1);
						}else {
							System.out.println();
							System.out.println("Target is out of range!");
							System.out.println();
						}
					}
					if(targetResponse.equals("2")) {
						if(acti.inRange(e2, 20)) {
							acti.getLoc().set(e2.getLoc().getX(), e2.getLoc().getY());
							acti.protect(0.5, 1);
						}else {
							System.out.println();
							System.out.println("Target is out of range!");
							System.out.println();
						}
					}
					if(targetResponse.equals("3")) {
						if(acti.inRange(e3, 20)) {
							acti.getLoc().set(e3.getLoc().getX(), e3.getLoc().getY());
							acti.protect(0.5, 1);
						}else {
							System.out.println();
							System.out.println("Target is out of range!");
							System.out.println();
						}
					}
				  System.out.println(acti.getSkin() + ": " + "\"Stage dive!\"");
				  System.out.println(init.getSkin() + ": " + "\"This is NOT my style of music but whatever!\"");
			    break;
			  case "Eli":
				  init.regen(0.05, 4);
				  acti.regen(0.05, 4);
				  ally.regen(0.05, 4);
				  e1.freeze(1);
				  e2.freeze(1);
				  e3.freeze(1);
				  System.out.println(acti.getSkin() + ": " + "\"Take a chill pill!\"");
				  System.out.println(init.getSkin() + ": " + "\"Damn, you're cold.\"");
			    break;
			  case "Ashley":
				  init.heal(0.25);
				  acti.heal(0.25);
				  ally.heal(0.25);
				  e1.weak(0.25, 1);
				  e2.weak(0.25, 1);
				  e3.weak(0.25, 1);
				  if (acti.inRange(e1)) {
					  e1.ignite(1);
				  }
				  if (acti.inRange(e2)) {
					  e2.ignite(1);
				  }
				  if (acti.inRange(e3)) {
					  e3.ignite(1);
				  }
				  System.out.println(acti.getSkin() + ": " + "\"Let's light this place up!\"");
				  System.out.println(init.getSkin() + ": " + "\"Feeling the heat now!\"");
			    break;
			  case "Midnite":
				  Location l = GameSim.SetCursor(acti, init, ally, e1, e2, e3, 0);
				  if (!acti.inRange(l)) {
					  System.out.println("Can't scare them here!");
					  System.out.println();
					  return;
				  }
				  if (e1.inRange(acti)) {
					  e1.takeDamage(200);
					  e1.paralyze(1);
					  e1.getLoc().set(l.getX(), l.getY());
				  }
				  if (e2.inRange(acti)) {
					  e2.takeDamage(200);
					  e2.paralyze(1);
					  e2.getLoc().set(l.getX(), l.getY());
				  }
				  if (e3.inRange(acti)) {
					  e3.takeDamage(200);
					  e3.paralyze(1);
					  e3.getLoc().set(l.getX(), l.getY());
				  }
				  System.out.println(acti.getSkin() + ": " + "\"Time for the horror show!\"");
				  System.out.println(init.getSkin() + ": " + "\"Finally, something more my vibe.\"");
			    break;
			  case "Sammi":
				  acti.setSights(3);
				  acti.protect(0.4, 1);
				  acti.power(0.4, 1);
				  System.out.println(acti.getSkin() + ": " + "\"Dalton! Come here boy!\"");
				  System.out.println(init.getSkin() + ": " + "\"Have fun out there Dalton!\"");
			    break;
			  case "Liam":
				  acti.protect(0.5, 1);
				  acti.cleanse();
				  acti.refine(1);
				  init.protect(0.5, 1);
				  init.cleanse();
				  init.refine(1);
				  ally.protect(0.5, 1);
				  ally.cleanse();
				  ally.refine(1);
				  Utility Sock = new Utility("Sock", new Location(acti.getLoc().getX(), acti.getLoc().getY()), acti, acti, acti, e1, e2, e3);
				  GameSim.utility.add(Sock);
				  Utility Sock2 = new Utility("Sock", new Location(acti.getLoc().getX(), acti.getLoc().getY()), acti, acti, acti, e1, e2, e3);
				  GameSim.utility.add(Sock2);
				  Utility Sock3 = new Utility("Sock", new Location(acti.getLoc().getX(), acti.getLoc().getY()), acti, acti, acti, e1, e2, e3);
				  GameSim.utility.add(Sock3);
				  if (e1.isAlive()) {
					  Sock.setTarget(e1);
				  }
				  if (e2.isAlive()) {
					  Sock2.setTarget(e2);
				  }
				  if (e3.isAlive()) {
					  Sock3.setTarget(e3);
				  }
				  System.out.println(acti.getSkin() + ": " + "\"The witnesses aren't happy with you criminals!\"");
				  System.out.println(init.getSkin() + ": " + "\"Robots, get them!\"");
			    break;
			  case "Finley":
				  acti.mend(0.25, 100);
				  if (e1.inRange(acti)) {
					  acti.attack(e1);
				  }
				  if (e2.inRange(acti)) {
					  acti.attack(e2);
				  }
				  if (e3.inRange(acti)) {
					  acti.attack(e3);
				  }
				  System.out.println(acti.getSkin() + ": " + "\"The power of love is not to be underestimated!\"");
				  System.out.println(init.getSkin() + ": " + "\"Keep my axolotl safe out there!\"");
			    break;
			  case "Cherry":
				  if (acti.ultActive() || acti.getMaxHP() == 600) {
					  System.out.println("Cannot summon the cherry tank right now!");
					  System.out.println();
					  return;
				  }
				  acti.setCherryTank();
				  System.out.println(acti.getSkin() + ": " + "\"Time for a change of pace!\"");
				  System.out.println(init.getSkin() + ": " + "\"Run them over girl!\"");
			    break;
			  case "Echo":
				  acti.addCharge(1200);
				  acti.resetAttack();
				  e1.dragIn(acti.getLoc(), 7);
				  e2.dragIn(acti.getLoc(), 7);
				  e3.dragIn(acti.getLoc(), 7);
				  System.out.println(acti.getSkin() + ": " + "\"Sound levels are off the charts!\"");
				  System.out.println(init.getSkin() + ": " + "\"..., ...!\"");
			    break;
			  case "Margo":
				  acti.power(0.25, 1);
				  acti.heal(0.1);
				  acti.setFireImmune();
				  init.power(0.25, 1);
				  init.heal(0.1);
				  init.setFireImmune();
				  ally.power(0.25, 1);
				  ally.heal(0.1);
				  ally.setFireImmune();
				  System.out.println(acti.getSkin() + ": " + "\"Thanks for the heatup!\"");
				  System.out.println(init.getSkin() + ": " + "\"Let's bring the heat to them!\"");
			    break;
			  case "Makani":
				  if (e1.inRange(acti)) {
					  e1.dragIn(acti.getLoc(), 5);
					  e1.takeDamage(350);
				  }
				  if (e2.inRange(acti)) {
					  e2.dragIn(acti.getLoc(), 5);
					  e2.takeDamage(350);
				  }
				  if (e3.inRange(acti)) {
					  e3.dragIn(acti.getLoc(), 5);
					  e3.takeDamage(350);
				  }
				  acti.cleanse();
				  System.out.println(acti.getSkin() + ": " + "\"Keep that storm coming sister!\"");
				  System.out.println(init.getSkin() + ": " + "\"Flood these suckers back home!\"");
			    break;
			  case "Radar":
				  int orbCount = 0;
				  Location loc3 = GameSim.SetCursor(init, acti, ally, e1, e2, e3, 7);
				  if(!init.inRange(loc3)) {
						System.out.println("Can't throw Radar that far!");
						System.out.println();
						return;
				  }
				  acti.getLoc().set(loc3.getX(), loc3.getY());
				  for(int i = 0; i < GameSim.orbs.size(); i++) {
						if(acti.inRange(GameSim.orbs.get(i).getLoc())) {
							GameSim.orbs.remove(i);
							i--;
							orbCount++;
						}
					}
				  System.out.println();
				  System.out.println("1: " + init.getName() +init.showHP() +  init.getHealth() + "/" + init.getMaxHP() + ". Cover: " + init.getCover());
				  System.out.println("2: " + ally.getName() +ally.showHP() +  ally.getHealth() + "/" + ally.getMaxHP() + ". Cover: " + ally.getCover());
				  for(int i = 0; i < orbCount; i++) {
						System.out.print("Who do you want to give an orb to: ");
						String targetResponse2 = input.next();
						if(targetResponse2.equals("1") && init.isAlive()) {
							if(init.ultReady()) {
								init.increaseMovement(10);
							}else {
								init.getOrb();
								init.getOrb();
							}
						}
						if(targetResponse2.equals("2") && ally.isAlive()) {
							if(ally.ultReady()) {
								ally.increaseMovement(10);
							}else {
								ally.getOrb();
								ally.getOrb();
							}
						}
					}
				  init.increaseMovement(7);
				  acti.increaseMovement(7);
				  ally.increaseMovement(7);
				  System.out.println(acti.getSkin() + ": " + "\"Orbs are coming up!\"");
				  System.out.println(init.getSkin() + ": " + "\"" + acti.getSkin() + ", get us a good catch!\"");
			    break;
			  case "Dimentio":
				  Location l4 = GameSim.SetCursor(acti, init, ally, e1, e2, e3, 20);
				  acti.getLoc().set(l4.getX(), l4.getY());
				  if (e1.inRange(l4, 20)) {
					  e1.freeze(1);
					  e1.setSupress(true);
				  }
				  if (e2.inRange(l4, 20)) {
					  e2.freeze(1);
					  e2.setSupress(true);
				  }
				  if (e3.inRange(l4, 20)) {
					  e3.freeze(1);
					  e3.setSupress(true);
				  }
				  System.out.println(acti.getSkin() + ": " + "\"Your powers are gone!\"");
				  System.out.println(init.getSkin() + ": " + "\"Supression systems all ready admin.\"");
			    break;
			  case "Clara":
				  ArrayList<Player> people = new ArrayList<Player>();
				  if (acti.inRange(e1)) {
					  people.add(e1);
				  }
				  if (acti.inRange(e2)) {
					  people.add(e2);
				  }
				  if (acti.inRange(e3)) {
					  people.add(e3);
				  }
				  if (people.size() == 0) {
					  System.out.println("Noone in range for the Katana Dance!");
					  System.out.println();
					  return;
				  }
				  double damage = 100;
				  int slashes = 9;
				  if (people.size() == 1) {
					  damage = 75;
				  }
				  while (slashes > 0) {
					  if (e1.isAlive() && people.contains(e1)) {
						  e1.takeDamage(damage);
						  slashes--;
						  acti.getLoc().set(e1.getLoc().getX(), e1.getLoc().getY());
						  if (!e1.isAlive()) {
							  people.remove(e1);
							  if (people.size() == 1) {
								  damage = 75;
							  }
						  }
						  if (people.size() > 1) {
							  acti.power(0.05, 2);
						  }
					  }
					  if (e2.isAlive() && people.contains(e2)) {
						  e2.takeDamage(damage);
						  slashes--;
						  acti.getLoc().set(e2.getLoc().getX(), e2.getLoc().getY());
						  if (!e2.isAlive()) {
							  people.remove(e2);
							  if (people.size() == 1) {
								  damage = 75;
							  }
						  }
						  if (people.size() > 1) {
							  acti.power(0.05, 2);
						  }
					  }
					  if (e3.isAlive() && people.contains(e3)) {
						  e3.takeDamage(damage);
						  slashes--;
						  acti.getLoc().set(e3.getLoc().getX(), e3.getLoc().getY());
						  if (!e3.isAlive()) {
							  people.remove(e3);
							  if (people.size() == 1) {
								  damage = 75;
							  }
						  }
						  if (people.size() > 1) {
							  acti.power(0.05, 2);
						  }
					  }
				  }
				  System.out.println(acti.getSkin() + ": " + "\"Slashing right through!\"");
				  System.out.println(init.getSkin() + ": " + "\"Time for a speed boost girl!\"");
			    break;
			  case "Airic":
				  acti.airicDynamic();
				  System.out.println(acti.getSkin() + ": " + "\"It's rewind time!\"");
				  System.out.println(init.getSkin() + ": " + "\"A jump through time will catch them off guard!\"");
			    break;
			  case "Orchid":
				  acti.orchidDynamic();
				  init.heal(0.15);
				  acti.heal(0.15);
				  ally.heal(0.15);
				  init.increaseMovement(3);
				  acti.increaseMovement(3);
				  ally.increaseMovement(3);
				  System.out.println(acti.getSkin() + ": " + "\"Ah, pollination domination!\"");
				  System.out.println(init.getSkin() + ": " + "\"Time to bloom!\"");
			    break;
			  case "Harper":
				  acti.setSights(3);
				  acti.sightsee(1, 1);
				  acti.power(0.5, 1);
				  System.out.println(acti.getSkin() + ": " + "\"Don't move, I might let you live!\"");
				  System.out.println(init.getSkin() + ": " + "\"We have the high range now!\"");
			    break;
			  case "Louis":
				  input = new Scanner(System.in);
					if(!acti.inRange(e1) && !acti.inRange(e2) && !acti.inRange(e3)) {
						System.out.println("Can't triple fire on anyone!");
						System.out.println();
					}else {
						
					}
					String range1 = "No";
					if(acti.inRange(e1)) {
						range1 = "Yes";
					}
					String range21 = "No";
					if(acti.inRange(e2)) {
						range21 = "Yes";
					}
					String range31 = "No";
					if(acti.inRange(e3)) {
						range31 = "Yes";
					}
					System.out.println();
					System.out.println("1: " + e1.getSkin() +". Health: " +  e1.getHealth() + "/" + e1.getMaxHP() + ". In Range: " + range1 + ".");
					System.out.println("2: " + e2.getSkin() +". Health: " +  e2.getHealth() + "/" + e2.getMaxHP() + ". In Range: " + range21 + ".");
					System.out.println("3: " + e3.getSkin() +". Health: " +  e3.getHealth() + "/" + e3.getMaxHP() + ". In Range: " + range31 + ".");
					System.out.println("Who do you want to triple fire on: ");
					String targetResponse2 = input.next();
					if(targetResponse2.equals("1")) {
						if(acti.inRange(e1)) {
							acti.attack(e1);
							acti.attack(e1);
							acti.attack(e1);
							acti.protect(0.75, 1);
						}else {
							System.out.println();
							System.out.println("Target is out of range!");
							System.out.println();
						}
					}
					if(targetResponse2.equals("2")) {
						if(acti.inRange(e2)) {
							acti.attack(e2);
							acti.attack(e2);
							acti.attack(e2);
							acti.protect(0.75, 1);
						}else {
							System.out.println();
							System.out.println("Target is out of range!");
							System.out.println();
						}
					}
					if(targetResponse2.equals("3")) {
						if(acti.inRange(e3)) {
							acti.attack(e3);
							acti.attack(e3);
							acti.attack(e3);
							acti.protect(0.75, 1);
						}else {
							System.out.println();
							System.out.println("Target is out of range!");
							System.out.println();
						}
					}
				  System.out.println(acti.getSkin() + ": " + "\"This is real firepower!\"");
				  System.out.println(init.getSkin() + ": " + "\"Whatever, fall back behind me now!\"");
			    break;
			  case "Tom":
				  input = new Scanner(System.in);
					if(!acti.inRange(e1) && !acti.inRange(e2) && !acti.inRange(e3)) {
						System.out.println("Can't take control on anyone!");
						System.out.println();
					}else {
						
					}
					range1 = "No";
					if(acti.inRange(e1)) {
						range1 = "Yes";
					}
					range21 = "No";
					if(acti.inRange(e2)) {
						range21 = "Yes";
					}
					range31 = "No";
					if(acti.inRange(e3)) {
						range31 = "Yes";
					}
					System.out.println();
					System.out.println("1: " + e1.getSkin() +". Health: " +  e1.getHealth() + "/" + e1.getMaxHP() + ". In Range: " + range1 + ".");
					System.out.println("2: " + e2.getSkin() +". Health: " +  e2.getHealth() + "/" + e2.getMaxHP() + ". In Range: " + range21 + ".");
					System.out.println("3: " + e3.getSkin() +". Health: " +  e3.getHealth() + "/" + e3.getMaxHP() + ". In Range: " + range31 + ".");
					System.out.println("Who do you want to take control of: ");
					targetResponse2 = input.next();
					if(targetResponse2.equals("1")) {
						if(acti.inRange(e1)) {
							Location l1 = GameSim.SetCursor(acti, init, ally, e1, e2, e3, 0);
							e1.getLoc().set(l1.getX(), l1.getY());
							e1.takeDamage(300);
							e1.stun(1);
						}else {
							System.out.println();
							System.out.println("Target is out of range!");
							System.out.println();
						}
					}
					if(targetResponse2.equals("2")) {
						if(acti.inRange(e2)) {
							Location l1 = GameSim.SetCursor(acti, init, ally, e1, e2, e3, 0);
							e2.getLoc().set(l1.getX(), l1.getY());
							e2.takeDamage(300);
							e2.stun(1);
						}else {
							System.out.println();
							System.out.println("Target is out of range!");
							System.out.println();
						}
					}
					if(targetResponse2.equals("3")) {
						if(acti.inRange(e3)) {
							Location l1 = GameSim.SetCursor(acti, init, ally, e1, e2, e3, 0);
							e3.getLoc().set(l1.getX(), l1.getY());
							e3.takeDamage(300);
							e3.stun(1);
						}else {
							System.out.println();
							System.out.println("Target is out of range!");
							System.out.println();
						}
					}
				  System.out.println(acti.getSkin() + ": " + "\"I have control of you now!\"");
				  System.out.println(init.getSkin() + ": " + "\"Tremble over our control!\"");
			    break;
			  case "Xara":
				  acti.setOverhealth(1500);
				  acti.xaraDynamic();
				  System.out.println(acti.getSkin() + ": " + "\"Admin overload complete!\"");
				  System.out.println(init.getSkin() + ": " + "\"Show them what it means to be an admin " + acti.getSkin() + "\"" + "!");
			    break;
			  case "Chloe":
				  acti.protect(0.5, 1);
				  acti.increaseMovement(20);
				  acti.resetAttack();
				  acti.resetCooldown();
				  System.out.println(acti.getSkin() + ": " + "\"Pure royal power!\"");
				  System.out.println(init.getSkin() + ": " + "\"Her royal highness appears!\"");
			    break;
			  case "Grenadine":
				  System.out.println(acti.getSkin() + ": " + "\"This is more fun than using my own explosives!\"");
				  System.out.println(init.getSkin() + ": " + "\"The steam will grant you new heights!\"");
				  for (int i = 0; i < 3; i++) {
					  Location loc = GameSim.SetCursor(acti, init, ally, e1, e2, e3, 5);
					  if(!acti.inReach(loc, 14)) {
							System.out.println("Can't Steam Jump that far!");
							System.out.println();

					   }else {
						   if (e1.inRange(loc, 5)) {
							   e1.takeDamage(200);
							   acti.addDamage(200);
							   e1.knockbacked(loc);
						   }
						   if (e2.inRange(loc, 5)) {
							   e2.takeDamage(200);
							   acti.addDamage(200);
							   e2.knockbacked(loc);
						   }
						   if (e3.inRange(loc, 5)) {
							   e3.takeDamage(200);
							   acti.addDamage(200);
							   e3.knockbacked(loc);
						   }
						   acti.getLoc().set(loc.getX(), loc.getY());
					   }
				  }
			    break;
			  case "Velvet":
				  System.out.println(acti.getSkin() + ": " + "\"If this is a fail, we are cooked!\"");
				  System.out.println(init.getSkin() + ": " + "\"Focus, you got this " + acti.getSkin() + "\"" + "!");
				  int finalIndex = 0;
				  String userInput = "";
				  String[] lots = new String[5];
				  String[] finalLots = new String[3];
				  lots[0] = "🍒";
				  lots[1] = "🍊";
				  lots[2] = "🧲";
				  lots[3] = "🍉";
				  lots[4] = "🔔";
				  System.out.print("Press any button to start rolling on the slot machine: ");
				  userInput = input.next();
				  while (finalIndex < finalLots.length) {
			            for (String emoji : lots) {
			                if (finalIndex >= finalLots.length) {
			                    break;
			                }

			                System.out.print("\r" + emoji); // Print the emoji on the same line
			                try {
								TimeUnit.MILLISECONDS.sleep(100);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} // 1-second delay

			                try {
								if (System.in.available() > 0) { // Check if there is any user input
								    String selection = input.next();
								    if ("c".equalsIgnoreCase(selection)) {
								        finalLots[finalIndex] = emoji;
								        finalIndex++;
								        System.out.println("\nYou picked: " + emoji);
								    }
								}
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
			            }
			        }
				  int bell = 0;
			        System.out.println("You rolled the following:");
			        for (String finalEmoji : finalLots) {
			            System.out.print(finalEmoji + " ");
			        }
			        System.out.println();
			        for (String finalEmoji : finalLots) {
			            if (finalEmoji.equals(lots[0])) {
			            	acti.heal(0.1);
			            	init.heal(0.1);
			            	ally.heal(0.1);
			            }
			            if (finalEmoji.equals(lots[1])) {
			            	acti.power(0.3, 1);
			            	init.power(0.3, 1);
			            	ally.power(0.3, 1);
			            }
			            if (finalEmoji.equals(lots[2])) {
			            	e1.daze(1);
			            	e2.daze(1);
			            	e3.daze(1);
			            }
			            if (finalEmoji.equals(lots[3])) {
			            	acti.increaseMovement(8);
			            	acti.getOrb();
			            	init.increaseMovement(8);
			            	init.getOrb();
			            	ally.increaseMovement(8);
			            	ally.getOrb();
			            }
			            if (finalEmoji.equals(lots[4])) {
			            	bell++;
			            }
			        }
			        if (bell == 3) {
			        	System.out.println("You won the jackpot!");
			        	e1.takeDamage(e1.getMaxHP() * 0.3);
			        	e2.takeDamage(e2.getMaxHP() * 0.3);
			        	e3.takeDamage(e3.getMaxHP() * 0.3);
			        }
			    break;
			  case "Redgar":
				  acti.increaseMaxHP(300);
				  acti.increaseDPSNum(50);
				  acti.heal(0.4);
				  System.out.println(acti.getSkin() + ": " + "\"Faster learning, I like this!\"");
				  System.out.println(init.getSkin() + ": " + "\"You will soon reach your full potential now!\"");
			    break;
			  case "Dylan":
				  System.out.println(acti.getSkin() + ": " + "\"Dragon's Breath Impact!\"");
				  System.out.println(init.getSkin() + ": " + "\"Crash in from above!\"");
				  int distance = 8;
				  int radius = 4;
				  boolean hit = true;
				  double rand = Math.random();
				  double rand2 = Math.random();
				  while (hit) {
					  if (distance < 8) {
						  System.out.println(acti.getSkin() + ": " + "\"Again!\"");
						  try {
								String audio = "audio/dynamic.wav";
								Music victoryPlayer = new Music(audio, false); 
								victoryPlayer.play();
							}catch (Exception e) {
								System.out.println(e);
							}
						  try {
								String audio = "audio/again.wav";
								Music victoryPlayer = new Music(audio, false); 
								victoryPlayer.play();
							}catch (Exception e) {
								System.out.println(e);
							}
					  }
					  hit = false;
					  Location loc = GameSim.SetCursor(acti, init, ally, e1, e2, e3, radius);
					  if(!acti.inReach(loc, distance)) {
							System.out.println("Can't Dragon Impact that far!");
							System.out.println();
							hit = true;
					   }else {
						   if (e1.inRange(loc, radius)) {
							   e1.takeDamage(25);
							   acti.addDamage(25);
							   rand = Math.random();
							   rand2 = Math.random();
								if(rand <= 0.05) {
									e1.freeze(1);
								}
								if(rand2 <= 0.05) {
									e1.ignite(1);
								}
								e1.knockbacked(loc);
								hit = true;
						   }
						   if (e2.inRange(loc, radius)) {
							   e2.takeDamage(25);
							   acti.addDamage(25);
							   rand = Math.random();
							   rand2 = Math.random();
								if(rand <= 0.05) {
									e2.freeze(1);
								}
								if(rand2 <= 0.05) {
									e2.ignite(1);
								}
								e2.knockbacked(loc);
								hit = true;
						   }
						   if (e3.inRange(loc, radius)) {
							   e3.takeDamage(25);
							   acti.addDamage(25);
							   rand = Math.random();
							   rand2 = Math.random();
								if(rand <= 0.05) {
									e3.freeze(1);
								}
								if(rand2 <= 0.05) {
									e3.ignite(1);
								}
								e3.knockbacked(loc);
								hit = true;
						   }
						   acti.getLoc().set(loc.getX(), loc.getY());
						   if (distance > 0) {
							   distance--;
						   }
						   if (distance == 0 && radius > 0) {
							   radius--;
						   }
					   }
				  }
			    break;
			}
			cooldown = 5;
			System.out.println();
		}else {
			System.out.println("Dynamic is not active!");
			System.out.println();
		}
	}
	
	
}
