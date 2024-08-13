package LunarVerse;

import java.util.ArrayList;

public class Party {

	boolean turn;
	Player[] roster = new Player[3];
	String name;
	
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
			roster[i].setAttackOnce(false);
			roster[i].setBumps(0);
			roster[i].resetChance();
			roster[i].resetDodge();
			roster[i].setJing(false);
			roster[i].resetStars();
			roster[i].checkOverhealth();
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
			for(int e = 0; e < GameSim.utility.size(); e++) {
				if(GameSim.utility.get(e).getName().equals("Star") && GameSim.utility.get(e).owner(roster[i])) {
					GameSim.utility.remove(e);
					e--;
				}
			}
			for(int y = 0; y < GameSim.utility.size(); y++) {
				if(GameSim.utility.get(y).getName().equals("Explosive") && GameSim.utility.get(y).isEnemy(roster[i])) {
					GameSim.utility.get(y).activateNuke();
					GameSim.utility.remove(y);
				}
			}
			for(int t = 0; t < GameSim.utility.size(); t++) {
				if(GameSim.utility.get(t).getName().equals("Field") && GameSim.utility.get(t).isAlly(roster[i])) {
					GameSim.utility.get(t).activateField();
				}
			}
			for(int q = 0; q < GameSim.utility.size(); q++) {
				if(GameSim.utility.get(q).getName().equals("Dragon") && GameSim.utility.get(q).owner(roster[i])) {
					GameSim.utility.get(q).activateDragonStart();
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
			if (!roster[i].tookDamage) {
				if (roster[i].isTank() && roster[i].isSupport()) {
					roster[i].heal(0.025);
				}else if (roster[i].isHybrid() && roster[i].isSupport() && !roster[i].isTank()) {
					roster[i].heal(0.05);
				}else if (roster[i].isSupport()) {
					roster[i].heal(0.075);
				}
			}
			roster[i].setTookDamage(false);
			roster[i].resetDashes();
			roster[i].resetJumps();
			roster[i].resetCover();
			roster[i].resetPat();
			if (roster[i].getName().equals("Ivy") && roster[i].getMedic()) {
				roster[i].setRes(true);
			}
			roster[i].setField(false);
			roster[i].setPat(false);
			if(roster[i].getName().equals("Sammi") && roster[i].getRange() > 100) {
				roster[i].resetRange();
			}
			if(roster[i].getName().equals("Snowfall") && roster[i].getCooldown() == 0) {
				roster[i].setFrost(false);
			}
			if(roster[i].getName().equals("Drift") && roster[i].ultActive()) {
				roster[i].setBumps(3);
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
			for(int j = 0; j < GameSim.utility.size(); j++) {
				if(GameSim.utility.get(j).getName().equals("Flame") && GameSim.utility.get(j).owner(roster[i])) {
					GameSim.utility.remove(j);
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
	
	public void setPartyName(String s) {
		name = getGradientName(s, "#50d5f7", "#b6abfe", "#647eff");
	}
	
	public String getPartyName() {
		return name;
	}
	
	public int[] hexToRgb(String colorStr) {
		return new int[] { Integer.valueOf(colorStr.substring(1, 3), 16), Integer.valueOf(colorStr.substring(3, 5), 16),
				Integer.valueOf(colorStr.substring(5, 7), 16) };
	}

	// Method to interpolate between two RGB colors
	public int[] interpolate(int[] startRgb, int[] endRgb, float fraction) {
		int r = (int) (startRgb[0] + (endRgb[0] - startRgb[0]) * fraction);
		int g = (int) (startRgb[1] + (endRgb[1] - startRgb[1]) * fraction);
		int b = (int) (startRgb[2] + (endRgb[2] - startRgb[2]) * fraction);
		return new int[] { r, g, b };
	}

	// Method to create a gradient name string with bold formatting
	public String getGradientName(String name, String... hexColors) {
		StringBuilder coloredName = new StringBuilder();
		ArrayList<int[]> rgbColors = new ArrayList<>();
		for (String hex : hexColors) {
			rgbColors.add(hexToRgb(hex));
		}
		// ANSI escape code for bold text
		String boldCode = "\u001B[1m";
		// Start the string with the bold code
		coloredName.append(boldCode);
		for (int i = 0; i < name.length(); i++) {
			float fraction = (float) i / (name.length() - 1);
			int colorIndex = (int) (fraction * (rgbColors.size() - 1));
			int[] startColor = rgbColors.get(colorIndex);
			int[] endColor = rgbColors.get(Math.min(colorIndex + 1, rgbColors.size() - 1));
			float colorFraction = (fraction * (rgbColors.size() - 1)) - colorIndex;
			int[] rgb = interpolate(startColor, endColor, colorFraction);
			coloredName.append(getColorCode(rgb[0], rgb[1], rgb[2])).append(name.charAt(i));
		}
		// Reset the color and formatting at the end
		coloredName.append("\u001B[0m");
		return coloredName.toString();
	}

	// Method to get the ANSI color code for RGB values
	private static String getColorCode(int r, int g, int b) {
		return String.format("\u001B[38;2;%d;%d;%dm", r, g, b);
	}
	
}
