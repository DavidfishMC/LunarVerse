package LunarVerse;

import java.util.ArrayList;

public class Party {

	boolean turn;
	Player[] roster = new Player[3];
	
	public Party(boolean turn, Player p1, Player p2, Player p3) {
		this.turn = turn;
		roster[0] = p1;
		roster[1] = p2;
		roster[2] = p3;
	}
	
	public void passTurn(Party p) {
		boolean heal = false;
		Player a = null;
		for(int i = 0; i < 3; i++) {
			roster[i].endTurn();
			roster[i].applyRegen();
			roster[i].resetJumpHeal();
			if (roster[i].drillDashed()) {
				roster[i].returnloc();
				roster[i].setDrill(false);
			}
			if(roster[i].getName().equals("Clara") && roster[i].ultActive()) {
				roster[i].ultDown();
			}
			if(roster[i].getCanon()) {
				roster[i].takeDamage(50000);
				roster[i].takeDamage(50000);
				roster[i].resetCanon();
			}
			for(int j = 0; j < GameSim.utility.size(); j++) {
				if(GameSim.utility.get(j).getName().equals("Swiftwing") && GameSim.utility.get(j).owner(roster[i])) {
					GameSim.utility.remove(j);
					roster[i].setCooldown(3);
				}
			}
			for(int k = 0; k < GameSim.utility.size(); k++) {
				if(GameSim.utility.get(k).getName().equals("Howler") && GameSim.utility.get(k).owner(roster[i])) {
					GameSim.utility.remove(k);
					roster[i].resetUlt();
				}
			}
			for(int y = 0; y < GameSim.utility.size(); y++) {
				if(GameSim.utility.get(y).getName().equals("Explosive") && GameSim.utility.get(y).isEnemy(roster[i])) {
					GameSim.utility.get(y).activateNuke();
					GameSim.utility.remove(y);
				}
			}
			if(roster[i].getName().equals("Rhythm") && !roster[i].isDazed() && roster[i].isAlive()) {
				if (roster[i].getFitbit().equals("Recovery")){
					heal = true;
					a = roster[i];
				}
			}
			if (heal) {
				roster[i].heal(0.05);
				a.addHealing(roster[i].getMaxHP() * 0.05);
				ArrayList<Effect> e = new ArrayList<Effect>();
				Effect RoccoParalyze = new Effect("protect", 0.1, 2);
				e.add(RoccoParalyze);
				roster[i].addEffects(e);
				roster[i].applyEffects();
			}
			roster[i].resetHeartburn();
			if(roster[i].getPack()) {
				roster[i].takeDamage(100);
				for(int j = 0; j < 3; j++) {
					if (!roster[j].equals(roster[i])) {
						if (roster[j].inRange(roster[i], 5)) {
							roster[j].takeDamage(150);
						}
					}
				}
			}
			if(roster[i].getName().equals("Patitek")) {
				for(int j = 0; j < 3; j++) {
					if (!roster[j].equals(roster[i])) {
						if (roster[j].inRange(roster[i], 2)) {
							roster[j].patProtect(roster[i]);
							System.out.println(roster[i].getSkin() + " will protect " + roster[j].getSkin() + " this turn.");
						}
						if (roster[j].inRange(roster[i], 6) && roster[i].onCooldown()) {
							roster[j].setPat(true);
							System.out.println(roster[j].getSkin() + " is within guard by " + roster[i].getSkin() + " this turn.");
						}
					}
				}
			}
			roster[i].resetBlastpack();
		}
		turn = false;
		p.setTurn();
	}
	
	public Player[] getRoster() {
		return roster;
	}
	
	public boolean oneLeft() {
		int num = 0;
		for(int i = 0; i < 3; i++) {
			if(roster[i].isAlive()) {
				num++;
			}
		}
		if(num == 1) {
			return true;
		}else {
			return false;
		}
	}
	
	public void nextPlayer() {
		if((!roster[0].isAlive() || roster[0].isStunned()) && (roster[1].isAlive() && !roster[1].isStunned())) {
			roster[1].setTurn();
		}else if(!roster[0].isAlive() && roster[1].isStunned()) {
			roster[2].setTurn();
		}else if((!roster[0].isAlive() && !roster[1].isAlive()) || ((roster[0].isStunned() && roster[1].isStunned()))) {
			roster[2].setTurn();
		}else {
			roster[0].setTurn();
		}
	}
	
	public void setTurn() {
		boolean sprint = false;
		boolean power = false;
		int randomNum = (int)(Math.random() * (3 - 1 + 1)) + 1;
		int randomNum2 = (int)(Math.random() * (2 - 1 + 1)) + 1;
		if(randomNum == 1) {
			if(randomNum2 == 1 && !oneLeft()) {
				roster[0].teamChat(this);
			}else if(roster[0].isAlive()){
				roster[0].chat();
			}
			
		}
		if(randomNum == 2) {
			if(randomNum2 == 1 && !oneLeft()) {
				roster[1].teamChat(this);
			}else if(roster[1].isAlive()){
				roster[1].chat();
			}
		}
		if(randomNum == 3) {
			if(randomNum2 == 1 && !oneLeft()) {
				roster[2].teamChat(this);
			}else if(roster[2].isAlive()){
				roster[2].chat();
			}
		}
		try {
			String audio = "passturnedit.wav";
			Music victoryPlayer = new Music(audio, false); 
			victoryPlayer.play();
		}catch (Exception e) {
			System.out.println(e);
		}
		for(int i = 0; i < 3; i++) {
			roster[i].resetMovement();
			if(!roster[i].getName().equals("Zero")) {
				roster[i].setSights(0);
			}
			roster[i].resetName();
			if(roster[i].getName().equals("Lunar") && roster[i].lunarUlt()) {
				roster[i].ultDown();
			}
			if(roster[i].getName().equals("Jesse") && roster[i].ultActive()) {
				roster[i].ultDown();
			}
			if(roster[i].getName().equals("Gates") && roster[i].ultActive()) {
				roster[i].ultDown();
			}
			roster[i].resetDashes();
			roster[i].resetJumps();
			roster[i].resetCover();
			roster[i].resetPat();
			roster[i].setPat(false);
			if(roster[i].getName().equals("Sammi")) {
				roster[i].resetRange();
			}
			for(int j = 0; j < GameSim.utility.size(); j++) {
				if(GameSim.utility.get(j).getName().equals("Sphere") && GameSim.utility.get(j).owner(roster[i])) {
					GameSim.utility.get(j).activateSphere();
				}
			}
			for(int j = 0; j < GameSim.utility.size(); j++) {
				if(GameSim.utility.get(j).getName().equals("Gemstone") && GameSim.utility.get(j).owner(roster[i])) {
					GameSim.utility.get(j).envolveStone();
					GameSim.utility.get(j).activateStone();
				}
			}
			for(int t = 0; t < GameSim.utility.size(); t++) {
				if(GameSim.utility.get(t).getName().equals("Wind") && GameSim.utility.get(t).owner(roster[i]) && !roster[i].onCooldown()) {
					GameSim.utility.remove(t);
					roster[i].resetWind();
				}
			}
			if(roster[i].getName().equals("Rhythm") && !roster[i].isDazed() && roster[i].isAlive()) {
				if (roster[i].getFitbit().equals("Sprint")){
					sprint = true;
				}
				if (roster[i].getFitbit().equals("Powerburn")){
					power = true;
				}
			}
			if(sprint) {
				roster[i].increaseMovement(1);
				roster[i].addJumps(1);
			}
			if (power) {
				ArrayList<Effect> e = new ArrayList<Effect>();
				Effect RoccoParalyze = new Effect("power", 0.1, 1);
				Effect RoccoParalyze2 = new Effect("sight", 0.2, 1);
				e.add(RoccoParalyze);
				e.add(RoccoParalyze2);
				roster[i].addEffects(e);
				roster[i].applyEffects();
			}
		}
		turn = true;
		for(int i = 0; i < 3; i++) {
			if(roster[i].isIgnite()) {
				roster[i].takeDamage(175);
				if(teamDown()) {
					GameSim.game = false;
				}
			}
		}
		if((!roster[0].isAlive() || roster[0].isStunned()) && (roster[1].isAlive() && !roster[1].isStunned())) {
			roster[1].setTurn();
		}else if(!roster[0].isAlive() && roster[1].isStunned()) {
			roster[2].setTurn();
		}else if((!roster[0].isAlive() && !roster[1].isAlive()) || ((roster[0].isStunned() && roster[1].isStunned()))) {
			roster[2].setTurn();
		}else {
			roster[0].setTurn();
		}
	}
	
	public void checkDown() {
		if(!roster[0].isAlive()) {
			roster[0].passTurn(roster[1]);
		}else if(!roster[0].isAlive() && !roster[1].isAlive()) {
			roster[0].passTurn(roster[2]);
		}
	}
	
	public boolean getTurn() {
		return turn;
	}
	
	public void reduceTeamEffects() {
		for(int i = 0; i < 3; i++) {
			roster[i].reduceEffects();
		}
	}
	
	public void reduceTeamCooldowns() {
		for(int i = 0; i < 3; i++) {
			roster[i].reduceCooldown();
		}
	}
	
	
	public void showRoster(Player p) {
		for(int i = 0; i < 3; i++) {
			String range = "No";
			if(p.inRange(roster[i])) {
				range = "Yes";
			}
			System.out.println(i+1 + ": " + roster[i].getSkin() +roster[i].showHP() + roster[i].getHealth() + "/" + roster[i].getMaxHP() + ". Location: " + roster[i].getLoc() + ". In Range: " + range + ". Cover: " + roster[i].getCover());
		}
	}
	
	public void showRoster() {
		for(int i = 0; i < 3; i++) {
			System.out.println(i+1 + ": " + roster[i].getSkin() +roster[i].showHP() + roster[i].getHealth() + "/" + roster[i].getMaxHP() + ". Location: " + roster[i].getLoc() + ". Cover: " + roster[i].getCover());
		}
	}
	
	public void applyTeamEffects(ArrayList<Effect> e) {
		for(int i = 0; i < 3; i++) {
			roster[i].addEffects(e);
			roster[i].applyEffects();
		}
	}
	
	public boolean teamDown() {
		for(int i = 0; i < 3; i++) {
			if(roster[i].isAlive()) {
				return false;
			}
		}
		return true;
	}
	
	public ArrayList<Player> partyNames(Player used){
		ArrayList<Player> names = new ArrayList<Player>();
		names.add(roster[0]);
		names.add(roster[1]);
		names.add(roster[2]);
		names.remove(used);
		return names;
	}
	
}
