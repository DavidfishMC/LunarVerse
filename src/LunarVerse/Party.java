package LunarVerse;

import java.util.ArrayList;
import java.util.Scanner;

public class Party {

	boolean turn;
	boolean ebbFlow = false;
	boolean enhance = false;
	int count = 0;
	int orbCount = 0;
	double ebbFlowDamage = 0;
	Player[] roster = new Player[3];
	Player rotate = null;
	String name;
	Party enemy;
	
	public Party(boolean turn, Player p1, Player p2, Player p3) {
		this.turn = turn;
		roster[0] = p1;
		roster[1] = p2;
		roster[2] = p3;
		roster[0].setParty(this);
		roster[1].setParty(this);
		roster[2].setParty(this);
	}
	
	public void evolve() {
		roster[0].setEvolve();
		System.out.println(roster[0].getSkin() + " has evolved!");
		System.out.println();
	}
	
	public void addOrb() {
		orbCount++;
		if (orbCount % 3 == 0 && rotate != null) {
			rotate.addOrb();
		}
	}
	
	public void setRotate(Player p) {
		rotate = p;
		rotate.setParty(this);
	}
	
	public void rotate() {
		if (rotate == null) {
			return;
		}
		Player rotateOut = null;
		Scanner input = new Scanner(System.in);
		System.out.println("Rotating in: " + rotate.getSkin());
		System.out.println(rotate);
		System.out.println();
		showRoster();
		System.out.println();
		System.out.println("Who do you want to rotate out: ");
		String targetResponse = input.next();
		if(targetResponse.equals("1")) {
			rotateOut = roster[0];
		}
		if(targetResponse.equals("2")) {
			rotateOut = roster[1];
		}
		if(targetResponse.equals("3")) {
			rotateOut = roster[2];
		}
		if (rotateOut != null) {
			rotate.getLoc().set(rotateOut.getLoc().getX(), rotateOut.getLoc().getY());
			for (int i = 0; i < 3; i++) {
				if (roster[i].equals(rotateOut)) {
					roster[i] = rotate;
				}
			}
			rotateOut.getLoc().set(100, 100);
			rotate = rotateOut;
		}
		System.out.println();
	}
	
	public Player first() {
		return roster[0];
	}
	
	public Player second() {
		return roster[1];
	}
	
	public Player third() {
		return roster[2];
	}
	
	public void showTeam() {
		for(int i = 0; i < 50; i++) {
			System.out.println();
		}
		System.out.println(roster[0].getSkin() + "'s remaining kit:");
		System.out.println(roster[0]);
		System.out.println();
		System.out.println();
		System.out.println(roster[1].getSkin() + "'s remaining kit:");
		System.out.println(roster[1]);
		System.out.println();
		System.out.println();
		System.out.println(roster[2].getSkin() + "'s remaining kit:");
		System.out.println(roster[2]);
		System.out.println();
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
			roster[i].setJing(false);
			roster[i].resetStars();
			roster[i].setNebula(false);
			roster[i].setHover(false);
			roster[i].checkOverhealth();
			roster[i].setTremor(false);
			roster[i].setTectonic(false);
			roster[i].setSupress(false);
			roster[i].setFrag(false);
			roster[i].unhijack();
			roster[i].setCogwork(false);
			roster[i].setCorrupt(false);
			roster[i].setSpin(false);
			roster[i].removeAura();
			if (roster[i].getHarmony()) {
				roster[i].heal(0.1);
			}
			if (roster[i].drillDashed()) {
				roster[i].returnloc();
				roster[i].setDrill(false);
			}
			if(roster[i].getName().equals("Clara") && roster[i].ultActive()) {
				roster[i].ultDown();
			}
			if(roster[i].getName().equals("Pearl") && !roster[i].isResting()) {
				roster[i].heal(0.05);
			}
			if(roster[i].getName().equals("Pearl")) {
				roster[i].setEnhance(false);
			}
			if(roster[i].getCanon()) {
				roster[i].down();
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
			for(int q = 0; q < GameSim.utility.size(); q++) {
				if(GameSim.utility.get(q).getName().equals("Umbrella") && GameSim.utility.get(q).owner(roster[i])) {
					GameSim.utility.get(q).activateUmbrella();
				}
			}
			for(int q = 0; q < GameSim.utility.size(); q++) {
				if(GameSim.utility.get(q).getName().equals("Pylon") && GameSim.utility.get(q).owner(roster[i])) {
					GameSim.utility.get(q).activatePylon();
				}
			}
			for(int q = 0; q < GameSim.utility.size(); q++) {
				if(GameSim.utility.get(q).getName().equals("Starpull") && GameSim.utility.get(q).owner(roster[i])) {
					GameSim.utility.get(q).activateStar();
				}
			}
			for(int j = 0; j < GameSim.utility.size(); j++) {
				if(GameSim.utility.get(j).getName().equals("Rook") && GameSim.utility.get(j).owner(roster[i]) && !GameSim.utility.get(j).rookActive) {
					GameSim.utility.get(j).setRookActive(true);
				}
			}
			for(int j = 0; j < GameSim.utility.size(); j++) {
				if(GameSim.utility.get(j).getName().equals("Steam") && GameSim.utility.get(j).owner(roster[i])) {
					if (GameSim.utility.get(j).getOwner().isClockwork()) {
						GameSim.utility.get(j).steam();
					}
				}
			}
			for(int j = 0; j < GameSim.utility.size(); j++) {
				if(GameSim.utility.get(j).getName().equals("Sock") && GameSim.utility.get(j).owner(roster[i])) {
					GameSim.utility.get(j).moveTo(3);
					if (GameSim.utility.get(j).getTarget() == null) {
						GameSim.utility.remove(j);
						j--;
					}
				}
			}
			for(int j = 0; j < GameSim.utility.size(); j++) {
				if(GameSim.utility.get(j).getName().equals("Gum") && GameSim.utility.get(j).owner(roster[i])) {
					GameSim.utility.remove(j);
					j--;
				}
			}
			int range = 4;
			for(int j = 0; j < GameSim.utility.size(); j++) {
				if(GameSim.utility.get(j).getName().equals("Smoke") && GameSim.utility.get(j).isAlly(roster[i])) {
					if (GameSim.utility.get(j).owner.getDarkness()) {
						range = 6;
					}
					if (roster[i].inRange(GameSim.utility.get(j).getLoc(), range)) {
						roster[i].setDodge(0.25);
						break;
					}
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
						if (roster[j].inRange(roster[i], 1)) {
							roster[j].patProtect(roster[i]);
							System.out.println(roster[i].getSkin() + " is protecting " + roster[j].getSkin() + ".");
						}
						if (roster[j].inRange(roster[i], 6) && roster[i].getCooldown() > 0) {
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
	
	public void setEnhance() {
		enhance = true;
	}
	
	public void setEbbFlow() {
		ebbFlow = true;
		for(int i = 0; i < 3; i++) {
			roster[i].setEbbFlow(true);
		}
	}
	
	public boolean hasEbbFlow() {
		return ebbFlow;
	}
	
	public void setEnemyParty(Party p) {
		enemy = p;
	}
	
	public void setTurn() {
		if (ebbFlow) {
			count++;
		}
		if (count == 2) {
			count = 0;
			ebbFlow = false;
			for(int i = 0; i < 3; i++) {
				ebbFlowDamage = ebbFlowDamage + roster[i].getFlowDamage();
				roster[i].resetEbbFlow();
				roster[i].resetFlow();
				if (roster[i].getName().equals("Pearl")) {
					roster[i].resetUlt();
					roster[i].ultDown();
				}
			}
			if (enhance) {
				for(int i = 0; i < 3; i++) {
					roster[i].increaseHP(ebbFlowDamage * 0.25);
				}
			}
			for(int i = 0; i < 3; i++) {
				double stat = 0.15;
				if (enhance) {
					stat = 0.3;
				}
				enemy.getRoster()[i].takeDamage(ebbFlowDamage * stat);
			}
			for(int i = 0; i < 3; i++) {
				roster[i].resetEbbFlow();
				roster[i].resetFlow();
			}
			ebbFlowDamage = 0;
			enhance = false;
		}
		boolean sprint = false;
		boolean power = false;
		int randomNum = (int)(Math.random() * (3 - 1 + 1)) + 1;
		int randomNum2 = (int)(Math.random() * (3 - 1 + 1)) + 1;
		if(randomNum == 1) {
			if(randomNum2 == 1 && !oneLeft()) {
				roster[0].teamChat(this);
			}else if(roster[0].isAlive() && randomNum2 == 2){
				roster[0].chat();
			}else if(roster[0].isAlive() && randomNum2 == 3){
				roster[0].enemyChat(enemy);
			}
			
		}
		if(randomNum == 2) {
			if(randomNum2 == 1 && !oneLeft()) {
				roster[1].teamChat(this);
			}else if(roster[1].isAlive() && randomNum2 == 2){
				roster[1].chat();
			}else if(roster[1].isAlive() && randomNum2 == 3){
				roster[1].enemyChat(enemy);
			}
		}
		if(randomNum == 3) {
			if(randomNum2 == 1 && !oneLeft()) {
				roster[2].teamChat(this);
			}else if(roster[2].isAlive() && randomNum2 == 2){
				roster[2].chat();
			}else if(roster[2].isAlive() && randomNum2 == 3){
				roster[2].enemyChat(enemy);
			}
		}
		try {
			String audio = "audio/passturnedit.wav";
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
			if(roster[i].getName().equals("Jazz") && roster[i].ultActive()) {
				roster[i].ultDown();
				roster[i].resetUlt();
			}
			if(roster[i].getName().equals("Millie")) {
				roster[i].addDagger();
				if (GameSim.turns2 % 2 == 0) {
					roster[i].addIron();
					roster[i].addTrash();
				}
			}
			if(roster[i].getName().equals("Everest")) {
				if (GameSim.turns2 % 2 == 0) {
					roster[i].addBalance();
				}
			}
			if(roster[i].getName().equals("Orchid") && !roster[i].petal) {
				roster[i].chargeBlockade();
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
			if (roster[i].getName().equals("Thunder") && !roster[i].tookDamage() && roster[i].isCountering()) {
				roster[i].resetCooldown();
				roster[i].setThunder(false);
			}
			if (roster[i].getName().equals("Pearl")) {
				if (roster[i].isResting() && roster[i].getSmolluskDashes() < 3) {
					roster[i].setResting(false);
				}
				if(roster[i].getSmolluskDashes() == 3) {
					roster[i].resetSmolluskDashes();
				}
			}
			roster[i].setTookDamage(false);
			roster[i].resetDashes();
			roster[i].resetJumps();
			roster[i].resetCover();
			roster[i].resetPat();
			roster[i].setStep(false);
			roster[i].setRally(false);
			roster[i].setBee(false);
			roster[i].resetQuincy();
			roster[i].resetDodge();
			roster[i].reduceEMP();
			roster[i].setExpose(false);
			roster[i].setClockwork(false);
			roster[i].checkDecay();
			roster[i].addSugar(5);
			if (roster[i].getHitDarkness()) {
				roster[i].takeDamage(100);
			}
			if (roster[i].getName().equals("Ivy") && roster[i].getMedic()) {
				roster[i].setRes(true);
			}
			if (roster[i].getName().equals("Cloud") && roster[i].getDarkness()) {
				roster[i].setTeleport(true);
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
			for (Tile t: GameSim.b.getTiles()) {
				if(roster[i].getLoc().eqLoc(t.getLoc())) {
					if(t.getName().equals("Space")) {
						roster[i].takeDamage(roster[i].getMaxHP() * 0.1);
						System.out.println();
					}
				}
			}
			for(int j = 0; j < GameSim.utility.size(); j++) {
				if(GameSim.utility.get(j).getName().equals("Sphere") && GameSim.utility.get(j).owner(roster[i])) {
					GameSim.utility.get(j).activateSphere();
				}
			}
			for(int e = 0; e < GameSim.utility.size(); e++) {
				if(GameSim.utility.get(e).getName().equals("Butterfly") && GameSim.utility.get(e).owner(roster[i])) {
					GameSim.utility.remove(e);
					e--;
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
			for(int j = 0; j < GameSim.utility.size(); j++) {
				if(GameSim.utility.get(j).getName().equals("Vine") && GameSim.utility.get(j).owner(roster[i])) {
					GameSim.utility.remove(j);
				}
			}
			for(int j = 0; j < GameSim.utility.size(); j++) {
				if(GameSim.utility.get(j).getName().equals("Turret") && GameSim.utility.get(j).owner(roster[i]) && GameSim.utility.get(j).isActivated()) {
					GameSim.utility.get(j).activateTurret();
				}
			}
			for(int j = 0; j < GameSim.utility.size(); j++) {
				if(GameSim.utility.get(j).getName().equals("Iron") && GameSim.utility.get(j).owner(roster[i])) {
					GameSim.utility.get(j).removeIron();
					GameSim.utility.remove(j);
					j--;
				}
			}
			for(int j = 0; j < GameSim.utility.size(); j++) {
				if(GameSim.utility.get(j).getName().equals("Mine") && GameSim.utility.get(j).owner(roster[i])) {
					GameSim.utility.remove(j);
					j--;
				}
			}
			for(int j = 0; j < GameSim.utility.size(); j++) {
				if(GameSim.utility.get(j).getName().equals("Eclipse") && GameSim.utility.get(j).owner(roster[i])) {
					GameSim.utility.get(j).setPhase();
				}
			}
			for(int j = 0; j < GameSim.utility.size(); j++) {
				if(GameSim.utility.get(j).getName().equals("Smoke") && GameSim.utility.get(j).owner(roster[i])) {
					GameSim.utility.remove(j);
					j--;
				}
			}
			for(int j = 0; j < GameSim.utility.size(); j++) {
				if(GameSim.utility.get(j).getName().equals("Mochi") && GameSim.utility.get(j).owner(roster[i])) {
					GameSim.utility.get(j).resetPounce();
					GameSim.utility.get(j).resetDestroy();
				}
			}
			for(int j = 0; j < GameSim.utility.size(); j++) {
				if(GameSim.utility.get(j).getName().equals("Steam") && GameSim.utility.get(j).owner(roster[i])) {
					GameSim.utility.get(j).firework();
				}
			}
			for(int j = 0; j < GameSim.utility.size(); j++) {
				if(GameSim.utility.get(j).getName().equals("Peri") && GameSim.utility.get(j).owner(roster[i])) {
					GameSim.utility.get(j).activatePeri();
				}
			}
			for(int j = 0; j < GameSim.utility.size(); j++) {
				if(GameSim.utility.get(j).getName().equals("Matrix") && GameSim.utility.get(j).owner(roster[i])) {
					GameSim.utility.remove(j);
					j--;
				}
			}
			for(int j = 0; j < GameSim.utility.size(); j++) {
				if(GameSim.utility.get(j).getName().equals("Sphere2") && GameSim.utility.get(j).owner(roster[i])) {
					GameSim.utility.get(j).checkSphere();
					if (GameSim.utility.get(j).getLoc().getX() > 41 || GameSim.utility.get(j).getLoc().getX() < 0 || GameSim.utility.get(j).getLoc().getY() > 41 || GameSim.utility.get(j).getLoc().getY() < 0) {
						GameSim.utility.remove(j);
						j--;
					}
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
				for (int j = 0; j < roster[i].getFiretick(); j++) {
					roster[i].takeDamage(5);
				}
				roster[i].takeDamage(175);
				if(teamDown()) {
					GameSim.game = false;
				}
			}
		}
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).getName().equals("Pawn")) {
				GameSim.utility.get(j).movePawn();
				if (GameSim.utility.get(j).getDirection().equals("done")) {
					GameSim.utility.remove(j);
					j--;
				}
			}
		}
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).getName().equals("Support") && (GameSim.utility.get(j).isEnemy(roster[0]) || GameSim.utility.get(j).isEnemy(roster[1]) || GameSim.utility.get(j).isEnemy(roster[2]))) {
				for(int b = 0; b < 3; b++) {
					roster[b].removeOrb();
				}
				break;
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
		//getUpdates();
		reduceTeamEffectsPre();
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
	
	public void reduceTeamEffectsPre() {
		for(int i = 0; i < 3; i++) {
			roster[i].reduceEffectsPre();
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
	
	public void getUpdates() {
		
		if (roster[0].isAlive()) {
			if (roster[0].getCooldown() == 0) {
				System.out.println(roster[0].getSkin() + "'s ability is ready to use!");
			}
			if (roster[0].ultReady()) {
				System.out.println(roster[0].getSkin() + "'s ultimate is ready to use!");
			}
		}
		if (roster[1].isAlive()) {
			if (roster[1].getCooldown() == 0) {
				System.out.println(roster[1].getSkin() + "'s ability is ready to use!");
			}
			if (roster[1].ultReady()) {
				System.out.println(roster[1].getSkin() + "'s ultimate is ready to use!");
			}
		}
		if (roster[2].isAlive()) {
			if (roster[2].getCooldown() == 0) {
				System.out.println(roster[2].getSkin() + "'s ability is ready to use!");
			}
			if (roster[2].ultReady()) {
				System.out.println(roster[2].getSkin() + "'s ultimate is ready to use!");
			}
		}
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
