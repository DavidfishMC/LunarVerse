package LunarVerse;

public class Effect {
	
	double numberIncrease = 0;
	int turnCount = 0;
	String name;
	boolean used = false;
	
	public Effect(String name, double power, int turns) {
		numberIncrease = power;
		turnCount = turns;
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void reduceTurns() {
		turnCount--;
	}
	
	public void setTurns(int i) {
		turnCount = i;
	}
	
	public double getIncrease() {
		return numberIncrease;
	}
	
	public int getTurns() {
		return turnCount;
	}
	
	public void used() {
		used = true;
	}
	
	public boolean isUsed() {
		return used;
	}
	

}