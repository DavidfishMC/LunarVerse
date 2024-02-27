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
		for(int i = 0; i < 3; i++) {
			roster[i].endTurn();
			roster[i].applyRegen();
		}
		turn = false;
		p.setTurn();
	}
	
	public Player[] getRoster() {
		return roster;
	}
	
	public void setTurn() {
		for(int i = 0; i < 3; i++) {
			roster[i].resetMovement();
			if(!roster[i].getName().equals("Zero")) {
				roster[i].setSights(0);
			}
			roster[i].resetName();
			roster[i].resetDashes();
			roster[i].resetJumps();
			roster[i].resetCover();
			roster[i].resetRange();
		}
		turn = true;
		for(int i = 0; i < 3; i++) {
			if(roster[i].isIgnite()) {
				roster[i].takeDamage(175);
			}
		}
		if(!roster[0].isAlive() || roster[0].isStunned()) {
			System.out.print("here");
			roster[1].setTurn();
		}else if(!roster[0].isAlive() && roster[1].isStunned()) {
			roster[2].setTurn();
		}else if(!roster[0].isAlive() && !roster[1].isAlive() || (roster[0].isStunned() && roster[1].isStunned())) {
			roster[2].setTurn();
		}else {
			roster[0].setTurn();
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
			System.out.println(i+1 + ": " + roster[i].getName() +". Health: " + roster[i].getHealth() + "/" + roster[i].getMaxHP() + ". Location: " + roster[i].getLoc() + ". In Range: " + range + ". Cover: " + roster[i].getCover());
		}
	}
	
	public void showRoster() {
		for(int i = 0; i < 3; i++) {
			System.out.println(i+1 + ": " + roster[i].getName() +". Health: " + roster[i].getHealth() + "/" + roster[i].getMaxHP() + ". Location: " + roster[i].getLoc() + ". Cover: " + roster[i].getCover());
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
	
}