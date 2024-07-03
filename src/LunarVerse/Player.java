package LunarVerse;

import java.applet.AudioClip;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
	String nameSkin;
	String cover = "None";
	String charm = "";
	int turndead = 0;
	int weaponuse = 0;
	int abilityuse = 0;
	int ultuse = 0;
	int dashed = 0;
	int jumped = 0;
	int totalMovement = 0;
	double echoCharge = 0;
	double damageOutput = 0;
	double healingOutput = 0;
	double healingIn = 0;
	double damageIn = 0;
	boolean turn;
	boolean lunarUlt = false;
	boolean tower = false;
	boolean reduce = false;
	boolean alive = true;
	boolean weary = false;
	boolean shield = false;
	boolean attacked = false;
	boolean ignite = false;
	boolean dazed = false;
	boolean stunned = false;
	boolean paralyzed = false;
	boolean reflection = false;
	boolean counter = false;
	boolean freezed = false;
	boolean refined = false;
	boolean fortify = false;
	boolean ultActive = false;
	boolean mend = false;
	boolean canon = false;
	boolean jumpHeal = false;
	boolean charge = false;
	boolean wind = true;
	boolean patProtect = false;
	boolean heartburn = false;
	boolean blastpack = false;
	boolean jumpDamage = false;
	boolean drillDash = false;
	int sights = 0;
	int actionTokens = 1;
	int cooldown = 0;
	double protect = 1;
	double absorb = 1;
	Location curLoc;
	Location returnLoc = null;
	double range;
	double ogRange;
	int movement;
	int dashes = 1;
	int jumps = 1;
	int ogMovement;
	int orbCount = 0;
	int ultCharge;
	int tacCharge = 0;
	double totalDamage = 0;
	ArrayList<Effect> effects = new ArrayList<Effect>();
	public static final String reset = "\u001B[0m";
	static final String bold = "\u001b[1m";
	int cNum = 0;
	Example image;
	String fitbit = "Recovery";
	Player pat = null;

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
		nameSkin = name;
		if (GameSim.mode.equals("Comet")) {
			this.damage = damage + 100;
			ogDamage = damage + 100;
			range = r + 2;
			movement = m + 5;
			ogMovement = m + 5;
			ultCharge = u - 2;
			ogRange = r + 2;
		}
	}

	public void setImage(String name) {
		try {
			image = new Example(name);
		} catch (IOException e1) {
		}
	}

	public void changeSkin(String type) {
		String tempName = name;
		if (name.equals("Evil")) {
			tempName = "Evil Lunar";
		}
		if (name.equals("Grizz")) {
			tempName = "Mr.Grizz";
		}
		if (name.equals("Augie")) {
			tempName = "Captain Augie";
		}
		if (name.equals("Tom")) {
			tempName = "Tom Phan";
		}
		if (name.equals("Gates")) {
			tempName = "Dr.Gates";
		}
		switch (type) {
		case "Sakura":
			nameSkin = getGradientName(tempName, "#F9BDEE", "#FFA3F7", "#F58AA4");
			break;
		case "Candy":
			nameSkin = getGradientName(tempName, "#F9BDEE", "#E0B4FD", "#5DFEEB");
			break;
		case "Pride":
			nameSkin = getGradientName(tempName, "#FF7A7A", "#FEA55D", "#ECE16A", "#86F35E", "#49F3F1", "#9C3CFB");
			break;
		case "Steampunk":
			nameSkin = getGradientName(tempName, "#CD7F32", "#BD6705", "#ADADAD", "#49F3E5");
			break;
		case "Terra Ground":
			nameSkin = getGradientName(tempName, "#21E843", "#49F3E5", "#CD7F32");
			break;
		case "Hades' Ruins":
			nameSkin = getGradientName(tempName, "#1BECD7", "#0054AD", "#6E8784");
			break;
		case "Lofi":
			nameSkin = getGradientName(tempName, "#FF7E98", "#FD84BB", "#D183DA", "#9480FD", "#6760FC");
			break;
		case "Breeze":
			nameSkin = getGradientName(tempName, "#BCF0FB", "#58D4F3", "#38F2FF");
			break;
		case "Enchantment Chaos":
			nameSkin = getGradientName(tempName, "#FD1A90", "#A12E8D", "#7E18E6", "#2C10A1", "#1E2367");
			break;
		case "Electropop":
			nameSkin = getGradientName(tempName, "#E1BC78", "#04E7EE", "#5953CD", "#A013C2", "#EF01F5");
			break;
		case "Cryo Chamber":
			nameSkin = getGradientName(tempName, "#0BC5CB", "#00CACD", "#01C4C9", "#55D7D2", "#00367A");
			break;
		case "Militia":
			nameSkin = getGradientName(tempName, "#CCB782", "#938753", "#8E834D", "#4BAC0F", "#504B35");
			break;
		case "Aurora":
			nameSkin = getGradientName(tempName, "#C3E9D2", "#12B5C8", "#8585C5", "#EE86A7");
			break;
		case "Crimson Tide":
			nameSkin = getGradientName(tempName, "#C6426E", "#642B73");
			break;
		case "Duo's Love":
			nameSkin = getGradientName(tempName, "#FF1476", "#FF819F", "#FF8FFF", "#FFB2D7", "#CBB2FF");
			break;
		case "Pastel Plum":
			nameSkin = getGradientName(tempName, "#C495FE", "#F57AAF", "#C670F5");
			break;
		case "Arthur's Honour":
			nameSkin = getGradientName(tempName, "#A59B8B", "#E1C483", "#E0E2EC", "#8C8C9C", "#513C58");
			break;
		case "Night Fang":
			nameSkin = getGradientName(tempName, "#270AFF", "#5533FF", "#9900FF", "#750C83");
			break;
		case "Ironclad":
			nameSkin = getGradientName(tempName, "#B5B5B5", "#D6D6D6", "#D9995E", "#F4B062");
			break;
		case "Wave Rider":
			nameSkin = getGradientName(tempName, "#DDBEAA", "#E5E3E4", "#BBC6C8", "#6EC4BA", "#50C1C3");
			break;
		case "Retrowave":
			nameSkin = getGradientName(tempName, "#F7C70D", "#F76C05", "#F6110C", "#631799", "#1F30C8");
			break;
		case "Flare":
			nameSkin = getGradientName(tempName, "#FF7024", "#FEBE34", "#FE3434");
			break;
		case "Sunset":
			nameSkin = getGradientName(tempName, "#FFAB15", "#F5723A", "#799AAB", "#22689C", "#524758");
			break;
		case "Interstellar":
			nameSkin = getGradientName(tempName, "#A3FFF5", "#7CB4C8", "#154367", "#5F6670", "#2D2D2F");
			break;
		case "Celestial":
			nameSkin = getGradientName(tempName, "#F29C87", "#F4AF72", "#7D7BC0", "#3559BF");
			break;
		case "Fantasy Myth":
			nameSkin = getGradientName(tempName, "#33FDFF", "#3CCFFF", "#727DFF", "#8E45FF", "#A501FF");
			break;
		case "Grotto":
			nameSkin = getGradientName(tempName, "#71838F", "#84BFC3", "#7F8DBE", "#C7C0BA", "#686A83");
			break;
		case "Agent":
			nameSkin = getGradientName(tempName, "#B4AFAB", "#8797B1", "#4D556A", "#43404F");
			break;
		case "Moss":
			nameSkin = getGradientName(tempName, "#D2D459", "#91B247", "#80A142", "#668D2E");
			break;
		case "Mocha":
			nameSkin = getGradientName(tempName, "#F8E59A", "#DBA75B", "#C3883D", "#5C2F27", "#3A170D");
			break;
		case "Royal":
			nameSkin = getGradientName(tempName, "#FFE5B2", "#FFC0A1", "#4869E1", "#BCD7E3", "#F2F2EB");
			break;
		case "Starlight":
			nameSkin = getGradientName(tempName, "#f7e9b7", "#FFEE88", "#FFE67B", "#D4AF37");
			break;
		case "Poseidon":
			nameSkin = getGradientName(tempName, "#90F8FB", "#00C6CC", "#008D92", "#E5C33B", "#F8E59A");
			break;
		case "Tulip Blossom":
			nameSkin = getGradientName(tempName, "#F9CEEE", "#E0CDFF", "#C1F0FB", "#DCF9A8", "#FFEBAF");
			break;
		case "Melon Blast":
			nameSkin = getGradientName(tempName, "#FF90C8", "#FFACC6", "#FFC7C5", "#A8EB9E", "#49CF8B");
			break;
		case "Cheesecake Bliss":
			nameSkin = getGradientName(tempName, "#F68787", "#FDE488", "#D4973A", "#A16317");
			break;
		default:
			break;
		}
	}

	public String getFirstColoredCharacter(String text) {
		// This pattern matches ANSI escape codes
		String ansiEscapeCodePattern = "\u001B\\[[;\\d]*m";

		// Find all ANSI escape codes in the text
		Pattern pattern = Pattern.compile(ansiEscapeCodePattern);
		Matcher matcher = pattern.matcher(text);
		// StringBuilder to hold the escape codes and the first character
		StringBuilder firstCharWithCodes = new StringBuilder();
		// Find all escape codes before the first visible character
		while (matcher.find()) {
			firstCharWithCodes.append(matcher.group());
			// Check if the next character after the escape code is not another escape code
			if (!text.substring(matcher.end()).startsWith("\u001B")) {
				// Append the first visible character after the escape codes
				firstCharWithCodes.append(text.charAt(matcher.end()));
				break;
			}
		}
		// Append the reset code to end the formatting
		firstCharWithCodes.append("\u001B[0m");
		return firstCharWithCodes.toString();
	}

	// Method to convert hex color code to RGB values
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
	
	public boolean drillDashed() {
		return drillDash;
	}
	
	public void setDrill(boolean b) {
		drillDash = b;
	}
	
	public void giveTacCharge() {
		tacCharge++;
	}
	
	public void returnloc() {
		if (returnLoc == null) {
			return;
		}
		curLoc.set(returnLoc.getX(), returnLoc.getY());
	}
	
	public void setReturnloc(Location l) {
		returnLoc = new Location(l.getX(), l.getY());
	}
	
	
	public void patProtect(Player p) {
		pat = p;
	}
	
	public void resetPat() {
		pat = null;
	}
	
	public Player returnPat() {
		return pat;
	}
	
	public void setJumpDamage(boolean b) {
		jumpDamage = b;
	}
	
	public boolean getJumpDamage() {
		return jumpDamage;
	}
	
	public void setBlastpack() {
		blastpack = true;
	}
	
	public void resetBlastpack() {
		blastpack = false;
	}
	
	public boolean getPack() {
		return blastpack;
	}
	
	public void setHeartburn() {
		heartburn = true;
	}
	
	public void resetHeartburn() {
		heartburn = false;
	}
	
	public boolean getHeartburn() {
		return heartburn;
	}
	
	public void setFitbit(String s) {
		fitbit = s;
	}
	
	public String getFitbit() {
		return fitbit;
	}
	
	public void setPat(boolean b) {
		patProtect = b;
	}
	
	public boolean getPat() {
		return patProtect;
	}
	
	public void resetWind() {
		wind = true;
	}
	
	public void setWind() {
		wind = false;
	}
	
	public boolean getWind() {
		return wind;
	}
	
	public void dragIn(Location l, int i) {
		if (!isAlive()) {
			return;
		}
		if (fortify) {
			return;
		}
		if (curLoc.getX() > l.getX()) {
			curLoc.adjust(-1 * i, 0);
		}
		if (curLoc.getX() < l.getX()) {
			curLoc.adjust(i, 0);
		}
		if (curLoc.getY() > l.getY()) {
			curLoc.adjust(0, -1 * i);
		}
		if (curLoc.getY() < l.getY()) {
			curLoc.adjust(0, i);
		}
	}
	
	public void addCharge(double d) {
		echoCharge = echoCharge + d;
		if (echoCharge > 800 && name.equals("Echo") && !charge) {
			System.out.println(nameSkin + "'s soundwave barrier is fully charged.");
		}
		if (echoCharge > 800 && name.equals("Echo")) {
			charge = true;
		}
	}
	
	public boolean soundwaveReady() {
		return charge;
	}
	
	public void resetCharge() {
		echoCharge = 0;
		charge = false;
	}
	
	public void setJumpHeal() {
		jumpHeal = true;
	}

	public boolean jumpHeal() {
		return jumpHeal;
	}
	
	public void resetJumpHeal() {
		jumpHeal = false;
	}

	public void setCanon() {
		if (isAlive()) {
			return;
		}
		canon = true;
		turndead = 0;
		health = health + (maxHealth * 1);
		alive = true;
		attacked = false;
		resetCooldown();
		orbCount = ultCharge - 4;
	}

	public boolean getCanon() {
		return canon;
	}
	
	public void resetCanon() {
		canon = false;
	}

	public void setCharm(String s) {
		charm = s;
		if (charm.equals("Armadillo")) {
			ArrayList<Effect> e = new ArrayList<Effect>();
			Effect DimentioProtect = new Effect("protect", 0.05, 100);
			e.add(DimentioProtect);
			addEffects(e);
			applyEffects();
		}
		if (charm.equals("Axolotl")) {
			maxHealth = maxHealth + 300;
			health = health + 300;
		}
		if (charm.equals("Bunny")) {
			jumps = 2;
		}
		if (charm.equals("Cheetah")) {
			movement = movement + 3;
		}
		if (charm.equals("Dragonfly")) {
			ultCharge--;
		}
		if (charm.equals("Falcon")) {
			range = range + 2;
			ogRange = ogRange + 2;
		}
		if (charm.equals("Rhino")) {
			dashes = 2;
		}
		if (charm.equals("Tiger")) {
			damage = damage + 75;
			ogDamage = ogDamage + 75;
		}
	}

	public Example image() {
		return image;
	}

	public void setC(int c) {
		cNum = c;
	}

	public void setAttacked() {
		attacked = true;
	}

	public void resetCover() {
		cover = "None";
	}

	public void setReduce() {
		reduce = true;
	}

	public void setCover(String s) {
		cover = s;
	}

	public void resetJumps() {
		jumps = 1;
		if (charm.equals("Bunny")) {
			jumps++;
		}
	}

	public void addJumps(int i) {
		jumps = jumps + i;
	}

	public void addDashes(int i) {
		dashes = dashes + i;
	}

	public void useJump() {
		jumped++;
		jumps--;
	}

	public void skin(String s) {
		nameSkin = s;
	}

	public String getSkin() {
		return nameSkin;
	}

	public boolean canJump() {
		return jumps > 0;
	}

	public void resetDashes() {
		dashes = 1;
		if (charm.equals("Rhino")) {
			dashes++;
		}
	}

	public void reduceMovement(int i) {
		movement = movement - i;
		if (movement < 0) {
			movement = 0;
		}
	}

	public void useDash() {
		dashed++;
		dashes--;
	}

	public boolean canDash() {
		return dashes > 0;
	}

	public void setShield() {
		System.out.println(nameSkin + " is now shielded.");
		shield = true;
	}

	public boolean inReach(Location l) {
		int x = l.getX();
		int y = l.getY();
		int d = Math.abs(x - curLoc.getX()) + Math.abs(y - curLoc.getY());
		if (movement >= d) {
			return true;
		} else {
			return false;
		}
	}

	public boolean inReach(Location l, int i) {
		int x = l.getX();
		int y = l.getY();
		int d = Math.abs(x - curLoc.getX()) + Math.abs(y - curLoc.getY());
		if (i >= d) {
			return true;
		} else {
			return false;
		}
	}

	public boolean inRange(Location l, double r) {
		double d = curLoc.distanceTo(l);
		if (r >= d) {
			return true;
		} else {
			return false;
		}
	}

	public boolean inRange(Location l) {
		double d = curLoc.distanceTo(l);
		if (range >= d) {
			return true;
		} else {
			return false;
		}
	}

	public boolean inRange(Player p, double r) {
		Location otherLoc = p.getLoc();
		double d = curLoc.distanceTo(otherLoc);
		if (r >= d) {
			return true;
		} else {
			return false;
		}
	}

	public boolean overRange(Player p, double r) {
		Location otherLoc = p.getLoc();
		double d = curLoc.distanceTo(otherLoc);
		if (d >= r) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean atRange(Location l, double r) {
		double d = curLoc.distanceTo(l);
		if (r >= d && !(d <= (r - 1))) {
			return true;
		} else {
			return false;
		}
	}

	public void setUlt() {
		ultActive = true;
		if (name.equals("Lunar")) {
			ultCharge = 2;
		} else {
			ultuse++;
			for(int j = 0; j < GameSim.utility.size(); j++) {
				if(GameSim.utility.get(j).getName().equals("Sensor") && GameSim.utility.get(j).isEnemy(this) && GameSim.utility.get(j).inRange(this, 5)) {
					GameSim.utility.get(j).activateSound();
					GameSim.utility.remove(j);
				}
			}
		}
	}

	public void setLunarUlt() {
		lunarUlt = true;
		if (name.equals("Lunar")) {
			ultCharge = 2;
		} else {
			ultuse++;
			for(int j = 0; j < GameSim.utility.size(); j++) {
				if(GameSim.utility.get(j).getName().equals("Sensor") && GameSim.utility.get(j).isEnemy(this) && GameSim.utility.get(j).inRange(this, 5)) {
					GameSim.utility.get(j).activateSound();
					GameSim.utility.remove(j);
				}
			}
		}
	}

	public boolean lunarUlt() {
		return lunarUlt;
	}

	public boolean ultActive() {
		return ultActive;
	}

	public void ultDown() {
		ultActive = false;
		lunarUlt = false;
		if (name.equals("Lunar")) {
			ultCharge = 5;
			orbCount = 0;
		}
	}
	
	public boolean usedUlt() {
		return orbCount != ultCharge;
	}
	
	public void refreshUlt() {
		orbCount = ultCharge;
	}

	public void resetCooldown() {
		cooldown = 0;
	}

	public boolean usedAbility() {
		return cooldown > 0;
	}

	public void setName(String name2) {
		name = name2;
	}

	public void resetName() {
		name = ogName;
	}

	public boolean hasSights() {
		if (sights == 0) {
			return false;
		} else {
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
		if (weary) {
			movement = movement - 5;
		}
		if (charm.equals("Cheetah")) {
			movement = movement + 3;
		}
	}

	public void setMovement(int i) {
		ogMovement = i;
	}

	public int getOrbCount() {
		return orbCount;
	}

	public int getUltCharge() {
		return ultCharge;
	}

	public boolean ultReady() {
		return orbCount >= ultCharge;
	}

	public void resetUlt() {
		ultuse++;
		orbCount = 0;
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).getName().equals("Sensor") && GameSim.utility.get(j).isEnemy(this) && GameSim.utility.get(j).inRange(this, 5)) {
				GameSim.utility.get(j).activateSound();
				GameSim.utility.remove(j);
			}
		}
	}

	public void removeOrb() {
		orbCount--;
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

	public void increaseMaxHP(double d) {
		if (isAlive()) {
			maxHealth = maxHealth + d;
			if (Battlefield.endgame) {
				d = d / 2;
			}
			health = health + d;
		}
	}

	public void knockbacked(Location l) {
		if (!isAlive()) {
			return;
		}
		if (fortify) {
			return;
		}
		int h = 1;
		int v = 1;
		int randomX = (int) (Math.random() * (4 - 1 + 1)) + 1;
		int randomY = (int) (Math.random() * (4 - 1 + 1)) + 1;
		if (l.getX() > curLoc.getX()) {
			h = -1;
		}
		if (l.getY() > curLoc.getY()) {
			v = -1;
		}
		if (l.getX() == curLoc.getX()) {
			h = 0;
		}
		if (l.getY() == curLoc.getY()) {
			v = 0;
		}
		randomX = randomX * h;
		randomY = randomY * v;
		if (l.eqLoc(curLoc)) {
			this.randomKnockback();
		} else {
			curLoc.adjust(randomX, randomY);
		}
		if (curLoc.getX() > 41) {
			curLoc.setX(41);
			takeDamage(200);
		}
		if (curLoc.getX() < 0) {
			curLoc.setX(0);
			takeDamage(200);
		}
		if (curLoc.getY() > 41) {
			curLoc.setY(41);
			takeDamage(200);
		}
		if (curLoc.getY() < 0) {
			curLoc.setY(0);
			takeDamage(200);
		}
		if (GameSim.b.hasTrench(curLoc.getX(), curLoc.getY())) {
			takeDamage(150);
			this.randomKnockback();
		}
		resetCover();
		System.out.println(nameSkin + " has been knocked back.");
	}

	public void randomKnockback() {
		int randomX = (int) (Math.random() * (5 - (-5) + 1)) + (-5);
		int randomY = (int) (Math.random() * (5 - (-5) + 1)) + (-5);
		curLoc.adjust(randomX, randomY);
		if (GameSim.b.hasTrench(curLoc.getX(), curLoc.getY())) {
			takeDamage(150);
			this.randomKnockback();
		}
	}

	public void setTower() {
		if (!tower) {
			tower = true;
			range = range + 3;
		}
	}

	public void removeTower() {
		if (tower) {
			tower = false;
			range = range - 3;
		}
	}

	public boolean isFortified() {
		return fortify;
	}

	public void move(int x, int y) {
		curLoc.set(x, y);
		movement = movement - 1;
		totalMovement++;
		System.out.println();
	}

	public void increaseTotalMovement(int i) {
		totalMovement = totalMovement + i;
	}

	public void getOrb() {
		try {
			String audio = "orbedit.wav";
			Music victoryPlayer = new Music(audio, false);
			victoryPlayer.play();
		} catch (Exception e) {
			System.out.println(e);
		}
		orbCount++;
	}

	public boolean inRange(Player p) {
		Location otherLoc = p.getLoc();
		double d = curLoc.distanceTo(otherLoc);
		if (range >= d) {
			return true;
		} else {
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
		if (!isAlive()) {
			return;
		}
		if (shield) {
			System.out.println("Shield broken!");
			shield = false;
			return;
		}
		for (int i = 0; i < effects.size(); i++) {
			if (effects.get(i).getName().equals("protect")) {
				d = d * (1 - effects.get(i).getIncrease());
			}
			if (effects.get(i).getName().equals("vulnerable")) {
				d = d + (d * effects.get(i).getIncrease());
			}
		}
		if (patProtect) {
			d = d * 0.8;
		}
		d = Math.round(d * 10.0) / 10.0;
		health = health - d;
		if (health < 0) {
			health = 0;
		}
		System.out.println(nameSkin + " has taken " + d + " damage.");
		if (d < 40000) {
			damageIn = damageIn + d;
			addCharge(d);
		}
		if (health == 0) {
			try {
				String audio = "downed.wav";
				Music victoryPlayer = new Music(audio, false);
				victoryPlayer.play();
			} catch (Exception e) {
				System.out.println(e);
			}
			alive = false;
			System.out.println(nameSkin + " is downed!");
			turndead = GameSim.turns2;
			if (name.equals("Alex") && ultActive) {
				reviveAlex();
			}else if (tacCharge > 0) {
				turndead = 0;
				health = health + (maxHealth * 0.1);
				alive = true;
				System.out.println(nameSkin + " has used a tactical resurgence charge.");
				tacCharge--;
			}
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

	public int skinC() {
		return cNum;
	}

	public void attack(Player p) {
		if (!p.isAlive()) {
			return;
		}
		if(p.returnPat() != null) {
			p = p.returnPat();
		}
		double c = 0;
		int randomNum = (int) (Math.random() * (10 - (-10) + 1)) + -10;
		if (damage <= 0) {
			randomNum = 0;
		}
		double rand2 = Math.random();
		if (rand2 <= 0.1) {
			c = damage * 0.3;
		}

		if (p.getName().equals("Bedrock") && p.ultActive() && p.inRange(this)) {
			p.getLoc().set(curLoc.getX(), curLoc.getY());
		}
		if (name.equals("Sammi") && range > 100) {
			p.takeDamage(damage + randomNum + c);
			addDamage(damage + randomNum + c);
			if (p.getName().equals("Thunder") && p.isCountering()) {
				takeDamage(p.getDamage() * 1.25);
				p.addDamage(p.getDamage() * 1.25);
			}
			if (p.isReflecting()) {
				takeDamage((damage + randomNum + c) * 0.5);
				p.addDamage((damage + randomNum + c) * 0.5);
				totalDamage = totalDamage + (damage + randomNum + c) * 0.5;
				if (mend) {
					for (int i = 0; i < effects.size(); i++) {
						if (effects.get(i).getName().equals("mend")) {
							heal((damage + randomNum + c) * 0.5, effects.get(i).getIncrease());
						}
					}
				}
			} else {
				totalDamage = totalDamage + (damage + randomNum + c);
				if (mend) {
					for (int i = 0; i < effects.size(); i++) {
						if (effects.get(i).getName().equals("mend")) {
							heal((damage + randomNum + c), effects.get(i).getIncrease());
						}
					}
				}
			}
			attacked = true;
			weaponuse++;
			for(int j = 0; j < GameSim.utility.size(); j++) {
				if(GameSim.utility.get(j).getName().equals("Sensor") && GameSim.utility.get(j).isEnemy(this) && GameSim.utility.get(j).inRange(this, 5)) {
					GameSim.utility.get(j).activateSound();
					GameSim.utility.remove(j);
				}
			}
			return;
		}
		if (p.getCover().equals("Full")) {
			System.out.println("Attacked was Covered!");
			attacked = true;
			weaponuse++;
			for(int j = 0; j < GameSim.utility.size(); j++) {
				if(GameSim.utility.get(j).getName().equals("Sensor") && GameSim.utility.get(j).isEnemy(this) && GameSim.utility.get(j).inRange(this, 5)) {
					GameSim.utility.get(j).activateSound();
					GameSim.utility.remove(j);
				}
			}
			return;
		}
		if (p.getCover().equals("Partial")) {
			if (name.equals("Thunder")) {
				p.takeDamage((damage * 0.5) + randomNum + c);
				addDamage((damage * 0.5) + randomNum + c);
				if (mend) {
					for (int i = 0; i < effects.size(); i++) {
						if (effects.get(i).getName().equals("mend")) {
							heal((damage * 0.5) + randomNum + c, effects.get(i).getIncrease());
						}
					}
				}
				attacked = true;
				if (p.isReflecting()) {
					takeDamage((damage + randomNum + c) * 0.5);
					p.addDamage((damage + randomNum + c) * 0.5);
					totalDamage = totalDamage + (damage + randomNum + c) * 0.5;
					if (mend) {
						for (int i = 0; i < effects.size(); i++) {
							if (effects.get(i).getName().equals("mend")) {
								heal((damage + randomNum + c) * 0.5, effects.get(i).getIncrease());
							}
						}
					}
				}
				if (p.getName().equals("Thunder") && p.isCountering()) {
					takeDamage(p.getDamage() * 1.25);
					p.addDamage(p.getDamage() * 1.25);
				}
				weaponuse++;
				for(int j = 0; j < GameSim.utility.size(); j++) {
					if(GameSim.utility.get(j).getName().equals("Sensor") && GameSim.utility.get(j).isEnemy(this) && GameSim.utility.get(j).inRange(this, 5)) {
						GameSim.utility.get(j).activateSound();
						GameSim.utility.remove(j);
					}
				}
				return;
			} else {
				double rand = Math.random();
				if (name.equals("Archer") || tower) {
					rand = 1;
				}
				if (rand <= 0.5) {
					System.out.println("Attacked was Covered!");
					attacked = true;
					weaponuse++;
					for(int j = 0; j < GameSim.utility.size(); j++) {
						if(GameSim.utility.get(j).getName().equals("Sensor") && GameSim.utility.get(j).isEnemy(this) && GameSim.utility.get(j).inRange(this, 5)) {
							GameSim.utility.get(j).activateSound();
							GameSim.utility.remove(j);
						}
					}
					return;
				}
			}
		}
		if (p.isReflecting()) {
			takeDamage((damage + randomNum + c) * 0.5);
			p.addDamage((damage + randomNum + c) * 0.5);
		}
		p.takeDamage(damage + randomNum + c);
		addDamage(damage + randomNum + c);
		totalDamage = totalDamage + (damage + randomNum + c);
		attacked = true;
		if (mend) {
			for (int i = 0; i < effects.size(); i++) {
				if (effects.get(i).getName().equals("mend")) {
					heal((damage + randomNum + c), effects.get(i).getIncrease());
				}
			}
		}
		if (p.getName().equals("Thunder") && p.isCountering()) {
			takeDamage(p.getDamage() * 1.25);
			p.addDamage(p.getDamage() * 1.25);
		}
		if (c > 0) {
			System.out.println("Critical shot!");
		}
		weaponuse++;
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).getName().equals("Sensor") && GameSim.utility.get(j).isEnemy(this) && GameSim.utility.get(j).inRange(this, 5)) {
				GameSim.utility.get(j).activateSound();
				GameSim.utility.remove(j);
			}
		}
	}

	public double getTotalDPS() {
		return totalDamage;
	}

	public boolean isCountering() {
		return counter;
	}

	public boolean isDazed() {
		return dazed;
	}

	public boolean hasMend() {
		return mend;
	}

	public ArrayList<Effect> getEffects() {
		return effects;
	}

	public void increaseDPSNum(double d) {
		damage = damage + d;
	}

	public void addEffects(ArrayList<Effect> e) {
		if (!isAlive()) {
			return;
		}
		for (int j = 0; j < e.size(); j++) {
			if (refined) {
				if (e.get(j).getName().equals("ignite") || e.get(j).getName().equals("weak")
						|| e.get(j).getName().equals("freeze") || e.get(j).getName().equals("vulnerable")
						|| e.get(j).getName().equals("paralyze") || e.get(j).getName().equals("daze")
						|| e.get(j).getName().equals("blind") || e.get(j).getName().equals("stun")
						|| e.get(j).getName().equals("poison")) {
					e.remove(e.get(j));
					j--;
				}
			}
		}
		for (int i = 0; i < e.size(); i++) {
			if (heartburn) {
				e.get(i).increaseTurns();
			}
			if (e.get(i).getName().equals("ignite") && ignite) {
				for (int j = 0; j < effects.size(); j++) {
					if (effects.get(j).getName().equals("ignite")) {
						effects.remove(effects.get(j));
						j--;
						ignite = false;
					}
				}
			}
			if (e.get(i).getName().equals("ignite") && !ignite) {
				ignite = true;
				System.out.println(nameSkin + " has been ignited for " + e.get(i).getTurns() + " turn(s).");
			}

			if (e.get(i).getName().equals("daze") && dazed) {
				for (int j = 0; j < effects.size(); j++) {
					if (effects.get(j).getName().equals("daze")) {
						effects.remove(effects.get(j));
						j--;
						dazed = false;
					}
				}
			}
			if (e.get(i).getName().equals("daze") && !dazed) {
				dazed = true;
				System.out.println(nameSkin + " has been dazed for " + e.get(i).getTurns() + " turn(s).");
			}

			if (e.get(i).getName().equals("stun") && stunned) {
				for (int j = 0; j < effects.size(); j++) {
					if (effects.get(j).getName().equals("stun")) {
						effects.remove(effects.get(j));
						j--;
						stunned = false;
					}
				}
			}
			if (e.get(i).getName().equals("stun") && !stunned) {
				stunned = true;
				System.out.println(nameSkin + " has been stunned.");
			}
			if (e.get(i).getName().equals("paralyze") && paralyzed) {
				for (int j = 0; j < effects.size(); j++) {
					if (effects.get(j).getName().equals("paralyze")) {
						effects.remove(effects.get(j));
						j--;
						paralyzed = false;
					}
				}
			}
			if (e.get(i).getName().equals("paralyze") && !paralyzed) {
				paralyzed = true;
				System.out.println(nameSkin + " has been paralyzed for " + e.get(i).getTurns() + " turn(s).");
			}
			if (e.get(i).getName().equals("reflection") && reflection) {
				for (int j = 0; j < effects.size(); j++) {
					if (effects.get(j).getName().equals("reflection")) {
						effects.remove(effects.get(j));
						j--;
						reflection = false;
					}
				}
			}
			if (e.get(i).getName().equals("reflection") && !reflection) {
				reflection = true;
				System.out.println(nameSkin + " is now reflecting for " + e.get(i).getTurns() + " turn(s).");
			}
			if (e.get(i).getName().equals("freeze") && freezed) {
				for (int j = 0; j < effects.size(); j++) {
					if (effects.get(j).getName().equals("freeze")) {
						effects.remove(effects.get(j));
						j--;
						freezed = false;
					}
				}
			}
			if (e.get(i).getName().equals("freeze") && !freezed) {
				freezed = true;
				System.out.println(nameSkin + " has been freezed for " + e.get(i).getTurns() + " turn(s).");
			}
			if (e.get(i).getName().equals("refine") && refined) {
				for (int j = 0; j < effects.size(); j++) {
					if (effects.get(j).getName().equals("refine")) {
						effects.remove(effects.get(j));
						j--;
						refined = false;
					}
				}
			}
			if (e.get(i).getName().equals("refine") && !refined) {
				refined = true;
				System.out.println(nameSkin + " has been refined for " + e.get(i).getTurns() + " turn(s).");
			}
			if (e.get(i).getName().equals("counter") && counter) {
				for (int j = 0; j < effects.size(); j++) {
					if (effects.get(j).getName().equals("counter")) {
						effects.remove(effects.get(j));
						j--;
						counter = false;
					}
				}
			}
			if (e.get(i).getName().equals("counter") && !counter) {
				counter = true;
				System.out.println(nameSkin + " is now countering for " + e.get(i).getTurns() + " turn(s).");
			}
			if (e.get(i).getName().equals("mend") && mend) {
				for (int j = 0; j < effects.size(); j++) {
					if (effects.get(j).getName().equals("mend")) {
						effects.remove(effects.get(j));
						j--;
						mend = false;
					}
				}
			}
			if (e.get(i).getName().equals("mend") && !mend) {
				mend = true;
				System.out.println(nameSkin + " is now mending by " + e.get(i).getIncrease() * 100 + "% for "
						+ e.get(i).getTurns() + " turn(s).");
			}
			if (e.get(i).getName().equals("fortify") && fortify) {
				for (int j = 0; j < effects.size(); j++) {
					if (effects.get(j).getName().equals("fortify")) {
						effects.remove(effects.get(j));
						j--;
						fortify = false;
					}
				}
			}
			if (e.get(i).getName().equals("fortify") && !fortify) {
				fortify = true;
				System.out.println(nameSkin + " is fortified for " + e.get(i).getTurns() + " turn(s).");
			}
			if (e.get(i).getName().equals("weary") && weary) {
				for (int j = 0; j < effects.size(); j++) {
					if (effects.get(j).getName().equals("weary")) {
						effects.remove(effects.get(j));
						j--;
						weary = false;
					}
				}
			}
			if (e.get(i).getName().equals("weary") && !weary) {
				weary = true;
				System.out.println(nameSkin + " is now weary for " + e.get(i).getTurns() + " turn(s).");
			}
			effects.add(e.get(i));
		}
	}

	public void applyRegen() {
		for (int i = 0; i < effects.size(); i++) {
			Effect e = effects.get(i);
			if (e.getName().equals("heal")) {
				heal(e.getIncrease());
				healingOutput = healingOutput + (e.getIncrease() * maxHealth);
			}
		}
	}

	public void applyEffects() {
		for (int i = 0; i < effects.size(); i++) {
			Effect e = effects.get(i);
			if (e.getName().equals("power") && !e.isUsed()) {
				damage = damage + (ogDamage * e.getIncrease());
				System.out.println(nameSkin + " has been powered by " + effects.get(i).getIncrease() * 100 + "% for "
						+ effects.get(i).getTurns() + " turn(s).");
			}
			if (e.getName().equals("protect") && !e.isUsed()) {
				protect = protect - e.getIncrease();
				System.out.println(nameSkin + " has been protected by " + effects.get(i).getIncrease() * 100 + "% for "
						+ effects.get(i).getTurns() + " turn(s).");
			}
			if (e.getName().equals("weak") && !e.isUsed()) {
				damage = damage - (ogDamage * e.getIncrease());
				System.out.println(nameSkin + " has been weakened by " + effects.get(i).getIncrease() * 100 + "% for "
						+ effects.get(i).getTurns() + " turn(s).");
			}
			if (e.getName().equals("vulnerable") && !e.isUsed()) {
				protect = protect + e.getIncrease();
				System.out.println(nameSkin + " is now vulnerable by " + effects.get(i).getIncrease() * 100 + "% for "
						+ effects.get(i).getTurns() + " turn(s).");
			}
			if (e.getName().equals("blind") && !e.isUsed()) {
				range = range - (ogRange * e.getIncrease());
				System.out.println(nameSkin + " has been blinded by " + effects.get(i).getIncrease() * 100 + "% for "
						+ effects.get(i).getTurns() + " turn(s).");
			}
			if (e.getName().equals("poison") && !e.isUsed()) {
				absorb = absorb - e.getIncrease();
				System.out.println(nameSkin + " has been poisoned by " + effects.get(i).getIncrease() * 100 + "% for "
						+ effects.get(i).getTurns() + " turn(s).");
			}
			if (e.getName().equals("sight") && !e.isUsed()) {
				range = range + (ogRange * e.getIncrease());
				System.out.println(nameSkin + " is now sightseeing by " + effects.get(i).getIncrease() * 100 + "% for "
						+ effects.get(i).getTurns() + " turn(s).");
			}
			e.used();
		}
	}

	public void reduceEffects() {
		for (int i = 0; i < effects.size(); i++) {
			Effect e = effects.get(i);
			e.reduceTurns();
			if (e.getTurns() <= 0) {
				if (e.getName().equals("power")) {
					damage = damage - (ogDamage * e.getIncrease());
					System.out.println(nameSkin + " is no longer powered.");
					i--;
				}
				if (e.getName().equals("protect")) {
					protect = protect + e.getIncrease();
					System.out.println(nameSkin + " is no longer protected.");
					i--;
				}
				if (e.getName().equals("poison")) {
					absorb = absorb + e.getIncrease();
					System.out.println(nameSkin + " is no longer poisoned.");
					i--;
				}
				if (e.getName().equals("blind")) {
					range = range + (ogRange * e.getIncrease());
					System.out.println(nameSkin + " is no longer blinded.");
					i--;
				}
				if (e.getName().equals("vulnerable")) {
					protect = protect - e.getIncrease();
					System.out.println(nameSkin + " is no longer vulnerable.");
					i--;
				}
				if (e.getName().equals("weak")) {
					damage = damage + (ogDamage * e.getIncrease());
					System.out.println(nameSkin + " is no longer weakened.");
					i--;
				}
				if (e.getName().equals("ignite")) {
					ignite = false;
					System.out.println(nameSkin + " is no longer ignited.");
					i--;
				}
				if (e.getName().equals("daze")) {
					dazed = false;
					System.out.println(nameSkin + " is no longer dazed.");
					i--;
				}
				if (e.getName().equals("stun")) {
					stunned = false;
					System.out.println(nameSkin + " is no longer stunned.");
					i--;
				}
				if (e.getName().equals("paralyze")) {
					paralyzed = false;
					System.out.println(nameSkin + " is no longer paralyzed.");
					i--;
				}
				if (e.getName().equals("reflection")) {
					reflection = false;
					System.out.println(nameSkin + " is no longer reflecting.");
					i--;
				}
				if (e.getName().equals("freeze")) {
					freezed = false;
					System.out.println(nameSkin + " is no longer freezed.");
					i--;
				}
				if (e.getName().equals("refine")) {
					refined = false;
					System.out.println(nameSkin + " is no longer refined.");
					i--;
				}
				if (e.getName().equals("counter")) {
					counter = false;
					System.out.println(nameSkin + " is no longer countering.");
					i--;
				}
				if (e.getName().equals("mend")) {
					mend = false;
					System.out.println(nameSkin + " is no longer mending.");
					i--;
				}
				if (e.getName().equals("fortify")) {
					fortify = false;
					System.out.println(nameSkin + " is no longer fortified.");
					i--;
				}
				if (e.getName().equals("sight")) {
					range = range - (ogRange * e.getIncrease());
					System.out.println(nameSkin + " is no longer sightseeing.");
					i--;
				}
				if (e.getName().equals("weary")) {
					weary = false;
					System.out.println(nameSkin + " is no longer weary.");
					i--;
				}
				effects.remove(e);
			}
		}
	}

	public double getHealth() {
		return Math.round(health * 10.0) / 10.0;
	}

	public boolean isStunned() {
		return stunned;
	}

	public void passTurn(Player p) {
		if (p.isStunned()) {
			System.out.println();
			System.out.println(p.getSkin() + " is stunned! Cannot switch.");
			return;
		}
		if (!p.isAlive() && (!p.getName().equals("Alex") || !p.ultActive()) && !p.getName().equals("Angelos")) {
			System.out.println();
			System.out.println(p.getSkin() + " is downed! Cannot switch.");
		} else {
			turn = false;
			p.setTurn();
		}

	}

	public boolean isIgnite() {
		return ignite;
	}

	public void setTurn() {
		if (!isAlive() && !name.equals("Angelos")) {
			if (ultActive && name.equals("Alex")) {
				health = maxHealth * 0.75;
				ultActive = false;
				resetUlt();
				System.out.println("\"Ha, missed me?\"");
				System.out.println();
				turn = true;
			}
			for (int i = 0; i < effects.size(); i++) {
				effects.get(i).setTurns(1);
			}
			reduceEffects();
		} else {
			turn = true;
		}
	}

	public void reviveAlex() {
		health = maxHealth * 0.35;
		ultActive = false;
		resetUlt();
		System.out.println("\"Back like I never left!\"");
		System.out.println();
		turn = true;
		for (int i = 0; i < effects.size(); i++) {
			effects.get(i).setTurns(1);
		}
		reduceEffects();
	}

	public boolean revive() {
		if (isAlive()) {
			return false;
		}
		turndead = 0;
		health = health + (maxHealth * 0.35);
		alive = true;
		attacked = false;
		resetCooldown();
		return true;
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
		if (!isAlive()) {
			return;
		}
		for (int i = 0; i < effects.size(); i++) {
			Effect e = effects.get(i);
			if (e.getName().equals("vulnerable")) {
				protect = protect - e.getIncrease();
				effects.remove(e);
				System.out.println(nameSkin + " is no longer vulnerable.");
				i--;
			}
			if (e.getName().equals("weak")) {
				damage = damage + (ogDamage * e.getIncrease());
				effects.remove(e);
				System.out.println(nameSkin + " is no longer weakened.");
				i--;
			}
			if (e.getName().equals("ignite")) {
				ignite = false;
				effects.remove(e);
				System.out.println(nameSkin + " is no longer ignited.");
				i--;
			}
			if (e.getName().equals("daze")) {
				dazed = false;
				effects.remove(e);
				System.out.println(nameSkin + " is no longer dazed.");
				i--;
			}
			if (e.getName().equals("stun")) {
				stunned = false;
				effects.remove(e);
				System.out.println(nameSkin + " is no longer stunned.");
				i--;
			}
			if (e.getName().equals("paralyze")) {
				paralyzed = false;
				effects.remove(e);
				System.out.println(nameSkin + " is no longer paralyzed.");
				i--;
			}
			if (e.getName().equals("freeze")) {
				freezed = false;
				effects.remove(e);
				System.out.println(nameSkin + " is no longer freezed.");
				i--;
			}
			if (e.getName().equals("blind")) {
				range = range + (ogRange * e.getIncrease());
				effects.remove(e);
				System.out.println(nameSkin + " is no longer blind.");
				i--;
			}
			if (e.getName().equals("poison")) {
				absorb = absorb + e.getIncrease();
				effects.remove(e);
				System.out.println(nameSkin + " is no longer poisoned.");
				i--;
			}
			if (e.getName().equals("weary")) {
				increaseMovement(5);
				weary = false;
				effects.remove(e);
				System.out.println(nameSkin + " is no longer weary.");
				i--;
			}
		}
		System.out.println(nameSkin + " has been cleansed.");
	}

	public void increaseMovement(int i) {
		movement = movement + i;
	}

	public void decreaseMovement(int i) {
		movement = movement - i;
		if (movement < 0) {
			movement = 0;
		}
	}

	public void heal(double d) {
		if (!isAlive()) {

		} else {
			double absorb2 = absorb;
			if (absorb <= 0) {
				absorb2 = 0;
			}
			double e = (maxHealth * d) * absorb2;
			if (Battlefield.endgame) {
				e = e / 2;
			}
			health = health + e;
			healingIn = healingIn + e;
			System.out.println(nameSkin + " has healed for " + e);
			if (health > maxHealth) {
				health = maxHealth;
			}
		}
	}

	public void increaseHP(double d) {
		if (isAlive()) {
			double absorb2 = absorb;
			if (absorb <= 0) {
				absorb2 = 0.001;
			}
			double e = d * absorb2;
			if (Battlefield.endgame) {
				e = e / 2;
			}
			health = health + e;
			System.out.println(nameSkin + " has healed for " + e);
			if (health > maxHealth) {
				health = maxHealth;
			}
			healingIn = healingIn + e;
		}
	}

	public void heal(double d, double i) {
		if (!isAlive()) {

		} else {
			double absorb2 = absorb;
			if (absorb2 <= 0) {
				absorb2 = 0.001;
			}
			double e = (d * i) * absorb2;
			if (Battlefield.endgame) {
				e = e / 2;
			}
			health = health + e;
			healingIn = healingIn + e;
			System.out.println(nameSkin + " has healed for " + e);
			if (health > maxHealth) {
				health = maxHealth;
			}
		}
	}

	public void setCooldown(int i) {
		if (reduce) {
			reduce = false;
			i--;
		}
		abilityuse++;
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).getName().equals("Sensor") && GameSim.utility.get(j).isEnemy(this) && GameSim.utility.get(j).inRange(this, 5)) {
				GameSim.utility.get(j).activateSound();
				GameSim.utility.remove(j);
			}
		}
		if (GameSim.mode.equals("Comet")) {
			i--;
		}
		if (charm.equals("Starfish")) {
			i--;
		}
		if (i < 0) {
			i = 0;
		}
		cooldown = i;
	}

	public void reduceCooldown() {
		if (cooldown != 0) {
			cooldown--;
		}
		if (cooldown == 0) {
			wind = true;
		}
	}

	public boolean onCooldown() {
		if (cooldown <= 0) {
			return false;
		} else {
			if(!name.equals("Makani") || !wind && !name.equals("Patitek")) {
				System.out.println(nameSkin + "'s ability is on Cooldown!");
			}
			System.out.println();
			return true;
		}
	}

	public boolean isAlive() {
		if (health <= 0) {
			return false;
		} else {
			return true;
		}
	}

	public void addDamage(double d) {
		damageOutput = damageOutput + d;
		addCharge(d);
	}

	public void addHealing(double d) {
		healingOutput = healingOutput + d;
	}

	public double totalDamage() {
		return damageOutput;
	}

	public double totalHealing() {
		return healingOutput;
	}

	public int totalMovement() {
		return totalMovement;
	}

	public double totalTank() {
		return damageIn;
	}

	public String toString() {
		String weapon = "Not Used " + "\u001b[38;5;" + 197 + "m" + "❎" + reset;
		String ability = "Ready " + "\u001b[38;5;" + 10 + "m" + "✅" + reset;
		String move = String.valueOf(movement);
		String healthshow = "Health " + bold + "\u001b[38;5;" + 196 + "m" + "❤️" + reset;
		String damageshow = ", Damage " + "\u001b[38;5;" + 124 + "m" + "⚔️" + reset + ": ";
		String covershow = ", Cover: ";
		String ultimate = ". Ultimate " + bold + "\u001b[38;5;" + 189 + "m" + "🪩" + reset + ": ";
		String dash = ", Dashes " + "\u001b[38;5;" + 248 + "m" + "💨" + reset + ": ";
		String moveshow = ", Movement " + "\u001b[38;5;" + 81 + "m" + "👟" + reset + ": ";
		String jump = ", Jumps " + bold + "\u001b[38;5;" + 241 + "m" + "🦿" + reset + ": ";
		String loc = "Location " + "\u001b[38;5;" + 130 + "m" + "🗺️" + reset + ": ";
		String weaponShow = "Weapon";
		String charms = "";
		if (GameSim.mode.equals("Charm")) {
			charms = ". Charm: " + charm;
		}
		if (attacked) {
			weapon = "Used " + "\u001b[38;5;" + 10 + "m" + "✅" + reset;
		}
		if (cooldown > 0) {
			ability = "On Cooldown " + "\u001b[38;5;" + 220 + "m" + "🕒" + reset + "(" + cooldown + ")";
		}
		if (freezed == true) {
			weapon = "Freezed " + bold + "\u001b[38;5;" + 87 + "m" + "❄️" + reset;
		}
		if (dazed == true) {
			ability = "Dazed " + "\u001b[38;5;" + 175 + "m" + "🌀" + reset;
		}
		if (paralyzed == true) {
			move = "Paralyzed";
		}
		if (health <= (maxHealth * 0.35)) {
			healthshow = "\u001b[38;5;" + 196 + "m" + "💔" + reset;
		}
		if (damage < ogDamage) {
			damageshow = ", Damage " + "\u001b[38;5;" + 220 + "m" + "😞" + reset + ": ";
		}
		if (damage > ogDamage) {
			damageshow = ", Damage " + bold + "\u001b[38;5;" + 9 + "m" + "💪" + reset + ": ";
		}
		if (cover.equals("Full")) {
			covershow = ", Cover 🛡️: ";
			covershow = ", Cover " + "\u001b[38;5;" + 21 + "m" + "🛡️" + reset + ": ";
		}
		if (cover.equals("Partial")) {
			covershow = ", Cover " + bold + "\u001b[38;5;" + 243 + "m" + "🪨" + reset + ": ";
		}
		if (orbCount == ultCharge) {
			ultimate = ". Ultimate " + "\u001b[38;5;" + 221 + "m" + "✨" + reset + ": ";
		}
		health = Math.round(health * 10.0) / 10.0;
		maxHealth = Math.round(maxHealth * 10.0) / 10.0;
		if (health <= (maxHealth * 0.35)) {
			if (protect < 1) {
				healthshow = "Health " + "\u001b[38;5;" + 247 + "m" + "💔" + reset;
			}
			if (protect > 1) {
				healthshow = "Health " + "\u001b[38;5;" + 213 + "m" + "💔" + reset;
			}
			if (shield) {
				healthshow = "Health " + "\u001b[38;5;" + 51 + "m" + "💔" + reset;
			}
		} else {
			if (protect < 1) {
				healthshow = "Health " + bold + "\u001b[38;5;" + 247 + "m" + "❤️" + reset;
			}
			if (protect > 1) {
				healthshow = "Health " + bold + "\u001b[38;5;" + 213 + "m" + "❤️" + reset;
			}
			if (shield) {
				healthshow = "Health " + bold + "\u001b[38;5;" + 51 + "m" + "❤️" + reset;
			}
		}
		if (counter || reflection || (range < ogRange) || (sights > 0) || (range > ogRange)) {
			weaponShow = weaponShow + " ";
		}
		if (counter || reflection) {
			weaponShow = weaponShow + "\u001b[38;5;" + 196 + "m" + "🥊" + reset;
		}
		if (range > ogRange) {
			weaponShow = weaponShow + "\u001b[38;5;" + 49 + "m" + "👀" + reset;
		}
		if (range < ogRange) {
			weaponShow = weaponShow + "\u001b[38;5;" + 62 + "m" + "😴" + reset;
		}
		if (sights > 0) {
			weaponShow = weaponShow + "\u001b[38;5;" + 88 + "m" + "🔭" + reset;
		}
		if (fortify) {
			moveshow = ", Movement " + "\u001b[38;5;" + 52 + "m" + "🪵" + reset + ": ";
		}
		if (mend) {
			healthshow = healthshow + bold + "\u001b[38;5;" + 51 + "m" + "🩹" + reset;
		}
		if (refined) {
			healthshow = healthshow + "\u001b[38;5;" + 220 + "m" + "😇" + reset;
		}
		if (ignite) {
			healthshow = healthshow + bold + "\u001b[38;5;" + 220 + "m" + "🔥" + reset;
		}
		if (absorb < 1) {
			healthshow = healthshow + bold + "\u001b[38;5;" + 46 + "m" + "💧" + reset;
		}

		healthshow = healthshow + ": ";
		weaponShow = weaponShow + ": ";

		if (name.equals("Angelos") && !isAlive()) {
			return "Ability: " + ability;
		} else {
			return (healthshow + health + "/" + maxHealth + damageshow + damage + covershow + cover + charms + "\n"
					+ weaponShow + weapon + ". Ability: " + ability + ultimate + orbCount + "/" + ultCharge + "\n" + loc
					+ curLoc + moveshow + move + dash + dashes + jump + jumps);
		}

	}

	public String showHP() {
		String healthshow = ". Health " + bold + "\u001b[38;5;" + 196 + "m" + "❤️" + reset + ": ";
		if (health <= (maxHealth * 0.35)) {
			healthshow = ". Health " + "\u001b[38;5;" + 196 + "m" + "💔" + reset + ": ";
			if (protect < 1) {
				healthshow = ". Health " + "\u001b[38;5;" + 247 + "m" + "💔" + reset + ": ";
			}
			if (protect > 1) {
				healthshow = ". Health " + "\u001b[38;5;" + 213 + "m" + "💔" + reset + ": ";
			}
			if (shield) {
				healthshow = ". Health " + "\u001b[38;5;" + 51 + "m" + "💔" + reset + ": ";
			}
		} else {
			if (protect < 1) {
				healthshow = ". Health " + bold + "\u001b[38;5;" + 247 + "m" + "❤️" + reset + ": ";
			}
			if (protect > 1) {
				healthshow = ". Health " + bold + "\u001b[38;5;" + 213 + "m" + "❤️" + reset + ": ";
			}
			if (shield) {
				healthshow = ". Health " + bold + "\u001b[38;5;" + 51 + "m" + "❤️" + reset + ": ";
			}
		}
		return healthshow;
	}

	public double getRange() {
		return range;
	}

	public String voiceline() {
		int randomNum = (int) (Math.random() * (3 - 1 + 1)) + 1;
		if (name.equals("Lunar")) {
			if (randomNum == 1) {
				return ("\"Nothing hits as hard as a recharge.\"");
			}
			if (randomNum == 2) {
				return ("\"Energy absorption in progress.\"");
			}
			if (randomNum == 3) {
				return ("\"I sure hope this stuff is good for you.\"");
			}
		}
		if (name.equals("Finley")) {
			if (randomNum == 1) {
				return ("\"They'll never see it coming. Trust me guys.\"");
			}
			if (randomNum == 2) {
				return ("\"This skateboard has a whole lot of BOOM!\"");
			}
			if (randomNum == 3) {
				return ("\"Enjoy the surprise nerds!\"");
			}
		}
		if (name.equals("Mack")) {
			if (randomNum == 1) {
				return ("\"Stay safe, I'm coming for you.\"");
			}
			if (randomNum == 2) {
				return ("\"Looking the wrong way again huh?\"");
			}
			if (randomNum == 3) {
				return ("\"Bet you didn't see me here!\"");
			}
		}
		if (name.equals("Solar")) {
			if (randomNum == 1) {
				return ("\"Light shield is up and running!\"");
			}
			if (randomNum == 2) {
				return ("\"Go hurt the others, I need a break.\"");
			}
			if (randomNum == 3) {
				return ("\"I am protected! Wait did he say that?\"");
			}
		}
		if (name.equals("Cherry")) {
			if (ultActive) {
				if (randomNum == 1) {
					return ("\"EMP Neutralization deployed.\"");
				}
				if (randomNum == 2) {
					return ("\"Disabling their abilities and power.\"");
				}
				if (randomNum == 3) {
					return ("\"I hope this doesn't short circuit my mech!\"");
				}
			} else {
				if (randomNum == 1) {
					return ("\"They're weak, fear nothing now friends.\"");
				}
				if (randomNum == 2) {
					return ("\"Enjoying the exhaust pipe?\"");
				}
				if (randomNum == 3) {
					return ("\"I'm supposed to stop global warming not join it!\"");
				}
			}
		}
		if (name.equals("Dylan")) {
			if (randomNum == 1) {
				return ("\"Greedy and bold, they're coming for your gold.\"");
			}
			if (randomNum == 2) {
				return ("\"Sick em boys!\"");
			}
			if (randomNum == 3) {
				return ("\"Behold my latest loyal pets!\"");
			}
		}
		if (name.equals("Burt")) {
			if (randomNum == 1) {
				return ("\"Hit me with your strongest attacks.\"");
			}
			if (randomNum == 2) {
				return ("\"I'm not afraid, come at me.\"");
			}
			if (randomNum == 3) {
				return ("\"How does a sword reflect everything?\"");
			}
		}
		if (name.equals("Bolo")) {
			if (randomNum == 1) {
				return ("\"If I see you, you're dead.\"");
			}
			if (randomNum == 2) {
				return ("\"Peek me, I dare you.\"");
			}
			if (randomNum == 3) {
				return ("\"Don't doubt my aim if you want me to go easy on you!\"");
			}
		}
		if (name.equals("Zero")) {
			if (randomNum == 1) {
				return ("\"Applying my hacks one second. Ok we're all good!\"");
			}
			if (randomNum == 2) {
				return ("\"Enjoy the refresher, this is a rare nice moment from me.\"");
			}
			if (randomNum == 3) {
				return ("\"Cheating in progress. Blame the game.\"");
			}
		}
		if (name.equals("Max")) {
			if (randomNum == 1) {
				return ("\"Go get them tiger.\"");
			}
			if (randomNum == 2) {
				return ("\"The perseverence lies within you.\"");
			}
			if (randomNum == 3) {
				return ("\"Why can't I give myself my own guidance?\"");
			}
		}
		if (name.equals("Eli")) {
			if (randomNum == 1) {
				return ("\"Buddies, lend us a hand!\"");
			}
			if (randomNum == 2) {
				return ("\"The sea is freaking awesome.\"");
			}
			if (randomNum == 3) {
				return ("\"Feeling refreshed? Good, now attack!\"");
			}
		}
		if (name.equals("Via")) {
			if (randomNum == 1) {
				return ("\"Arro, round them up!\"");
			}
			if (randomNum == 2) {
				return ("\"Group hug!\"");
			}
			if (randomNum == 3) {
				return ("\"Group them up, then knock them down.\"");
			}
		}
		if (name.equals("Louis")) {
			if (randomNum == 1) {
				return ("\"Execuse me, don't you know who I am?\"");
			}
			if (randomNum == 2) {
				return ("\"Stay away!\"");
			}
			if (randomNum == 3) {
				return ("\033[3m*Horn Noises, Doo Doo Doo Doooooooo*\033[0m");
			}
		}
		if (name.equals("Alex")) {
			if (randomNum == 1) {
				return ("\"Heads up, potion coming in!\"");
			}
			if (randomNum == 2) {
				return ("\"Pray for something good!\"");
			}
			if (randomNum == 3) {
				return ("\"This was the last of my nether wart!\"");
			}
		}
		if (name.equals("Orion")) {
			if (randomNum == 1) {
				return ("\"Speed it up guys!\"");
			}
			if (randomNum == 2) {
				return ("\"The faster we crush them, the faster I get to go on lunch break!\"");
			}
			if (randomNum == 3) {
				return ("\"Let's run those back quicker yeah?\"");
			}
		}
		if (name.equals("Kailani")) {
			if (randomNum == 1) {
				return ("\"Shelly! Let's take them for a ride.\"");
			}
			if (randomNum == 2) {
				return ("\"Lifeguard isn't here today!\"");
			}
			if (randomNum == 3) {
				return ("\"Out of my way!\"");
			}
		}
		if (name.equals("Ashley")) {
			if (randomNum == 1) {
				return ("\"Sparks flowers being grown.\"");
			}
			if (randomNum == 2) {
				return ("\"Nature is beautiful isn't it? And cruel.\"");
			}
			if (randomNum == 3) {
				return ("\"This will cut the pain, and them!\"");
			}
		}
		if (name.equals("Rocco")) {
			if (randomNum == 1) {
				return ("\"Have fun with this guy!\"");
			}
			if (randomNum == 2) {
				return ("\"Run for your lives! Oh I meant them not us.\"");
			}
			if (randomNum == 3) {
				return ("\"Time to whip out combat tactic #245.\"");
			}
		}
		if (name.equals("Sammi")) {
			if (randomNum == 1) {
				return ("\"I never miss. Whoever said I did was lying.\"");
			}
			if (randomNum == 2) {
				return ("\"Activating aimbot, literally.\"");
			}
			if (randomNum == 3) {
				return ("\"Don't try to hide, it's pointless.\"");
			}
		}
		if (name.equals("Clara")) {
			if (randomNum == 1) {
				return ("\"Look out, I'm coming through!\"");
			}
			if (randomNum == 2) {
				return ("\"I got your backs, just from the front.\"");
			}
			if (randomNum == 3) {
				return ("\"No 4k camera will see me zoom by them.\"");
			}
		}
		if (name.equals("Thunder")) {
			if (randomNum == 1) {
				return ("\"Mess with me? I'll mess with you.\"");
			}
			if (randomNum == 2) {
				return ("\"You sure you want to hit me now?\"");
			}
			if (randomNum == 3) {
				return ("\"Go on, attack! You'll meet your fate sooner.\"");
			}
		}
		if (name.equals("Aidan")) {
			if (randomNum == 1) {
				return ("\"Look at me, I'm cranking 90s!\"");
			}
			if (randomNum == 2) {
				return ("\"I'm gonna make Tilted Towers the way I be building.\"");
			}
			if (randomNum == 3) {
				try {
					String audio = "fortedit.wav";
					Music victoryPlayer = new Music(audio, false);
					victoryPlayer.play();
				} catch (Exception e) {
					System.out.println(e);
				}
				return ("\033[3m🎵 I said right foot creep, ooh, I'm walking with that heater 🎵\033[0m");
			}
		}
		if (name.equals("Liam")) {
			if (randomNum == 1) {
				return ("\"Your honor, my client is innocent!\"");
			}
			if (randomNum == 2) {
				return ("\"The jury is here to defend us!\"");
			}
			if (randomNum == 3) {
				return ("\"Objection!\"");
			}
		}
		if (name.equals("Axol")) {
			if (randomNum == 1) {
				return ("\"Go help them out little fellas.\"");
			}
			if (randomNum == 2) {
				return ("\"The healing power of friendship is here.\"");
			}
			if (randomNum == 3) {
				return ("\"Aren't they so cute?\"");
			}
		}
		if (name.equals("Katrina")) {
			if (randomNum == 1) {
				return ("\"Woomy!\"");
			}
			if (randomNum == 2) {
				return ("\"I'm with you.\"");
			}
			if (randomNum == 3) {
				return ("\"What's up?\"");
			}
		}
		if (name.equals("Midnite")) {
			if (randomNum == 1) {
				return ("\"Boo!\"");
			}
			if (randomNum == 2) {
				return ("\"Ghost power isn't anything to mess with.\"");
			}
			if (randomNum == 3) {
				return ("\"I'll become your greatest fear.\"");
			}
		}
		if (name.equals("Xara")) {
			if (randomNum == 1) {
				return ("\"Cyber Security protocols in place.\"");
			}
			if (randomNum == 2) {
				return ("\"Stay back, I'll show them what they're messing with.\"");
			}
			if (randomNum == 3) {
				return ("\"Never cross the line between an admin.\"");
			}
		}
		if (name.equals("Kithara")) {
			if (randomNum == 1) {
				return ("\"Wonder Shield activated!\"");
			}
			if (randomNum == 2) {
				return ("\"I'll show them what bulletproof means.\"");
			}
			if (randomNum == 3) {
				return ("\"Try breaking through this!\"");
			}
		}
		if (name.equals("Anjelika")) {
			if (randomNum == 1) {
				return ("\"Angelic Ray spreading out.\"");
			}
			if (randomNum == 2) {
				return ("\"Not so pure is it?\"");
			}
			if (randomNum == 3) {
				return ("\"The more evil they are, the more pain they will feel.\"");
			}
		}
		if (name.equals("Archer")) {
			if (randomNum == 1) {
				return ("\"Don't let them out of your sights!\"");
			}
			if (randomNum == 2) {
				return ("\"Carrots for the win!\"");
			}
			if (randomNum == 3) {
				return ("\"Out range them. Out gun them.\"");
			}
		}
		if (name.equals("Tom")) {
			if (randomNum == 1) {
				return ("\"Your mistake for messing with me!\"");
			}
			if (randomNum == 2) {
				return ("\"Microbot protection enhanced.\"");
			}
			if (randomNum == 3) {
				return ("\"I control these bots. You're doomed.\"");
			}
		}
		if (name.equals("Dimentio")) {
			if (randomNum == 1) {
				return ("\"To the Shadow Realm with you!\"");
			}
			if (randomNum == 2) {
				return ("\"Feel free to appeal your ban with a moderator!\"");
			}
			if (randomNum == 3) {
				return ("\"Not in my server you don't!\"");
			}
		}
		if (name.equals("Grizz")) {
			if (randomNum == 1) {
				return ("\"Do your job!\"");
			}
			if (randomNum == 2) {
				return ("\"You're not clocking out that easily.\"");
			}
			if (randomNum == 3) {
				return ("\"Overtime is mandatory today!\"");
			}
		}
		if (name.equals("Evil")) {
			if (randomNum == 1) {
				return ("\"The brigade is here!\"");
			}
			if (randomNum == 2) {
				return ("\"Happiness must be taken. I will take mine right now!\"");
			}
			if (randomNum == 3) {
				return ("\"Bots, fire at them!\"");
			}
		}
		if (name.equals("Mason")) {
			if (randomNum == 1) {
				return ("\"Delivery for you!\"");
			}
			if (randomNum == 2) {
				return ("\"Disable them, then strike!\"");
			}
			if (randomNum == 3) {
				return ("\"Same day shipping for this package!\"");
			}
		}
		if (name.equals("Airic")) {
			if (randomNum == 1) {
				return ("\"Need a lift?\"");
			}
			if (randomNum == 2) {
				return ("\"They'll never see you coming!\"");
			}
			if (randomNum == 3) {
				return ("\"Don't lose a limb out there!\"");
			}
		}
		if (name.equals("Julian")) {
			if (randomNum == 1) {
				return ("\"I still got it in me!\"");
			}
			if (randomNum == 2) {
				return ("\"Prioritize obsidian first!\"");
			}
			if (randomNum == 3) {
				return ("\"I'm gonna break their bed, protect the base.\"");
			}
		}
		if (name.equals("Gash")) {
			if (randomNum == 1) {
				return ("\"Laser precision guards are up.\"");
			}
			if (randomNum == 2) {
				return ("\"Initating contigency plan protocols.\"");
			}
			if (randomNum == 3) {
				return ("\"Stay close if you want to be hurt less out there!\"");
			}
		}
		if (name.equals("Mayhem")) {
			if (randomNum == 1) {
				return ("\"You stand no chance now!\"");
			}
			if (randomNum == 2) {
				return ("\"I developed this stuff myself!\"");
			}
			if (randomNum == 3) {
				return ("\"Target neutralized, almost.\"");
			}
		}
		if (name.equals("Gates")) {
			if (randomNum == 1) {
				return ("\"Enough hiding, you can take on anything.\"");
			}
			if (randomNum == 2) {
				return ("\"Hope they waste something big on that.\"");
			}
			if (randomNum == 3) {
				return ("\"Big or small, it can absorb it all!\"");
			}
		}
		if (name.equals("Audrey")) {
			if (randomNum == 1) {
				return ("\"Not so tough anymore!\"");
			}
			if (randomNum == 2) {
				return ("\"Wonder what they feel like now.\"");
			}
			if (randomNum == 3) {
				return ("\"Maybe we should go easy on them now yeah? Kidding!\"");
			}
		}
		if (name.equals("Ayson")) {
			if (randomNum == 1) {
				return ("\"We outnumber you all!\"" + " " + "\"Extra bodies on the field!\"");
			}
			if (randomNum == 2) {
				return ("\"Teamwork really does-.\"" + " " + "\"Less talking more punching!\"");
			}
			if (randomNum == 3) {
				return ("\"I'll go for the left if you take the right.\"" + " " + "\"You talking to our team or me?\"");
			}
		}
		if (name.equals("Chloe")) {
			if (randomNum == 1) {
				return ("\"Calling in the royal guard.\"");
			}
			if (randomNum == 2) {
				return ("\"I hope they trained enough for this.\"");
			}
			if (randomNum == 3) {
				return ("\"They'll treat you nicely. Otherwise, I'll have a word with them.\"");
			}
		}
		if (name.equals("Hopper")) {
			if (randomNum == 1) {
				return ("\"Never back down never what?!\"");
			}
			if (randomNum == 2) {
				return ("\"Never surrender!\"");
			}
			if (randomNum == 3) {
				return ("\"The warriors of justice would be ashamed if we backed down.\"");
			}
		}
		if (name.equals("Redgar")) {
			if (randomNum == 1) {
				return ("\"Bought this off some guy on the Metaverse, hope it works!\"");
			}
			if (randomNum == 2) {
				return ("\"Cleansing core is out!\"");
			}
			if (randomNum == 3) {
				return ("\"We'll be free from that toxic envrionment now.\"");
			}
		}
		if (name.equals("Radar")) {
			if (randomNum == 1) {
				return ("\"Can't keep up huh?\"");
			}
			if (randomNum == 2) {
				return ("\"They're moving at snail's pace now!\"");
			}
			if (randomNum == 3) {
				return ("\"You'll never get me alive!\"");
			}
		}
		if (name.equals("Oona")) {
			if (randomNum == 1) {
				return ("\"You're legally blind now!\"");
			}
			if (randomNum == 2) {
				return ("\"Strike while they're distracted!\"");
			}
			if (randomNum == 3) {
				return ("\"They can't see a damn thing. Awesome!\"");
			}
		}
		if (name.equals("Augie")) {
			if (randomNum == 1) {
				return ("\"No excuse for not hitting your shots!\"");
			}
			if (randomNum == 2) {
				return ("\"I see treasure! Wait that's just the enemy.\"");
			}
			if (randomNum == 3) {
				return ("\"Shoot your shot now!\"");
			}
		}
		if (name.equals("Ruby")) {
			if (randomNum == 1) {
				return ("\"Changing up the map!\"");
			}
			if (randomNum == 2) {
				return ("\"This should switch things up.\"");
			}
			if (randomNum == 3) {
				return ("\"Editing our world as we speak.\"");
			}
		}
		if (name.equals("Norman")) {
			if (randomNum == 1) {
				return ("\"Toxins going up!\"");
			}
			if (randomNum == 2) {
				return ("\"Get bamboozled!\"");
			}
			if (randomNum == 3) {
				return ("\"No healing for you!\"");
			}
		}
		if (name.equals("Jesse")) {
			if (randomNum == 1) {
				return ("\"Burn!\"");
			}
			if (randomNum == 2) {
				return ("\"Am I too hot for you?\"");
			}
			if (randomNum == 3) {
				return ("\"I will engulf you in flames!\"");
			}
		}
		if (name.equals("Chief")) {
			if (randomNum == 1) {
				return ("\"Together, they will fall!\"");
			}
			if (randomNum == 2) {
				return ("\"Let's tank you guys up!\"");
			}
			if (randomNum == 3) {
				return ("\"Stand back, I will handle this.\"");
			}
		}
		if (name.equals("Angelos")) {
			if (isAlive()) {
				if (randomNum == 1) {
					return ("\"Protect the canon, protect each other.\"");
				}
				if (randomNum == 2) {
					return ("\"They think they're scary? We won't falter.\"");
				}
				if (randomNum == 3) {
					return ("\"Orbit barriers are up and ready.\"");
				}
			} else {
				if (randomNum == 1) {
					return ("\"I'm still watching over you all.\"");
				}
				if (randomNum == 2) {
					return ("\"Don't let me down!\"");
				}
				if (randomNum == 3) {
					return ("\"Little do they know, I'm not done yet.\"");
				}
			}
		}
		if (name.equals("Melony")) {
			if (randomNum == 1) {
				return ("\"Run little man!\"");
			}
			if (randomNum == 2) {
				return ("\"Get 'em, Swiftwing!\"");
			}
			if (randomNum == 3) {
				return ("\"Swiftwing for the win!\"");
			}
		}
		if (name.equals("Echo")) {
			if (randomNum == 1) {
				return ("\"Sound Sensors out.\"");
			}
			if (randomNum == 2) {
				return ("\"Absolutely no surprises here.\"");
			}
			if (randomNum == 3) {
				return ("\"Why seek them out when you can hear their fear.\"");
			}
		}
		if (name.equals("Makani")) {
			if (onCooldown()) {
				if (randomNum == 1) {
					return ("\"Coming in! Or out.\"");
				}
				if (randomNum == 2) {
					return ("\"What pushovers you lot are.\"");
				}
				if (randomNum == 3) {
					return ("\"Kailani was right, this is fun!\"");
				}
			} else {
				if (randomNum == 1) {
					return ("\"Wind Point deployed.\"");
				}
				if (randomNum == 2) {
					return ("\"I'll be back, or in!\"");
				}
				if (randomNum == 3) {
					return ("\"Protect my wind point! I need it later.\"");
				}
			}
		}
		if (name.equals("Rhythm")) {
			if (fitbit.equals("Recovery")) {
				return ("\"Good vibes. Good healing!\"");
			}
			if (fitbit.equals("Sprint")) {
				return ("\"Faster pace, let's go!\"");
			}
			if (fitbit.equals("Powerburn")) {
				return ("\"Time to up the tempo!\"");
			}
		}
		if (name.equals("Grenadine")) {
			if (randomNum == 1) {
				return ("\"Blasting off!\"");
			}
			if (randomNum == 2) {
				return ("\"We have liftoff!\"");
			}
			if (randomNum == 3) {
				return ("\"Woo! This is fun.\"");
			}
		}
		if (name.equals("Patitek")) {
			if (randomNum == 1) {
				return ("\"Everyone stay close to me!\"");
			}
			if (randomNum == 2) {
				return ("\"I'll take the punches, you guys have no worry.\"");
			}
			if (randomNum == 3) {
				return ("\"Hey, hit me!\"");
			}
		}
		if (name.equals("Crystal")) {
			if (randomNum == 1) {
				return ("\"Keep the Gemstone safe!\"");
			}
			if (randomNum == 2) {
				return ("\"It'll grow with time. Just watch!\"");
			}
			if (randomNum == 3) {
				return ("\"If you hurt my Gemstone you WILL pay!\"");
			}
		}
		if (name.equals("Velvet")) {
			if (randomNum == 1) {
				return ("\"When playing roulette... ALWAYS bet on black!\"");
			}
			if (randomNum == 2) {
				return ("\"You can't beat my roulette table! Unless you steal from it.\"");
			}
			if (randomNum == 3) {
				return ("\"Please please please please please.\"");
			}
		}
		return "";
	}

	public void chat() {
		if (!isAlive()) {
			return;
		}
		System.out.println();
		if (name.equals("Lunar")) {
			int randomNum = (int) (Math.random() * (3 - 1 + 1)) + 1;
			switch (randomNum) {
			case 1:
				System.out.println(nameSkin + ": " + "\"Alright, let's do this guys!\"");
				break;
			case 2:
				System.out.println(nameSkin + ": " + "\"I've seen all of them fight before, we're better then them.\"");
				break;
			case 3:
				System.out.println(
						nameSkin + ": " + "\"Let's not make this too long, I want to make a new creation later.\"");
				break;
			}
		}
		if (name.equals("Aidan")) {
			int randomNum = (int) (Math.random() * (3 - 1 + 1)) + 1;
			switch (randomNum) {
			case 1:
				System.out.println(nameSkin + ": " + "\"Let's get this victory royale!\"");
				break;
			case 2:
				System.out.println(nameSkin + ": " + "\"They are no match for my building skills!\"");
				break;
			case 3:
				System.out.println(nameSkin + ": " + "\"Once we kill them all, then we can emote.\"");
				break;
			}
		}
		if (name.equals("Alex")) {
			int randomNum = (int) (Math.random() * (3 - 1 + 1)) + 1;
			switch (randomNum) {
			case 1:
				System.out.println(nameSkin + ": " + "\"I'll reel them in for us.\"");
				break;
			case 2:
				System.out.println(nameSkin + ": " + "\"I can't wait to show off my totem!\"");
				break;
			case 3:
				System.out.println(nameSkin + ": " + "\"Another day of fighting, nothing new for me.\"");
				break;
			}
		}
		if (name.equals("Clara")) {
			int randomNum = (int) (Math.random() * (3 - 1 + 1)) + 1;
			switch (randomNum) {
			case 1:
				System.out.println(nameSkin + ": " + "\"Time to get this party started!\"");
				break;
			case 2:
				System.out.println(nameSkin + ": " + "\"Don't worry if you can't keep up. I'll be moving fast.\"");
				break;
			case 3:
				System.out.println(nameSkin + ": " + "\"I'm gonna run circles around them.\"");
				break;
			}
		}
		if (name.equals("Zero")) {
			int randomNum = (int) (Math.random() * (3 - 1 + 1)) + 1;
			switch (randomNum) {
			case 1:
				System.out.println(nameSkin + ": " + "\"Can we make this quick?\"");
				break;
			case 2:
				System.out.println(nameSkin + ": " + "\"They'll regret going against a virus.\"");
				break;
			case 3:
				System.out.println(nameSkin + ": " + "\"If you need a cheat, just let me know.\"");
				break;
			}
		}
		if (name.equals("Kithara")) {
			int randomNum = (int) (Math.random() * (3 - 1 + 1)) + 1;
			switch (randomNum) {
			case 1:
				System.out.println(nameSkin + ": " + "\"They will regret challenging the Dead Lilac.\"");
				break;
			case 2:
				System.out.println(nameSkin + ": " + "\"I will make them feel the Wonder Power.\"");
				break;
			case 3:
				System.out.println(nameSkin + ": " + "\"I'm known as bulletproof for a reason.\"");
				break;
			}
		}
		if (name.equals("Hopper")) {
			int randomNum = (int) (Math.random() * (3 - 1 + 1)) + 1;
			switch (randomNum) {
			case 1:
				System.out.println(nameSkin + ": " + "\"Nobody goes up against the crusaders and survives.\"");
				break;
			case 2:
				System.out.println(nameSkin + ": " + "\"We're all warriors now. I will lead the charge.\"");
				break;
			case 3:
				System.out.println(nameSkin + ": " + "\"No hestiation, charge forwards!\"");
				break;
			}
		}
		if (name.equals("Thunder")) {
			int randomNum = (int) (Math.random() * (3 - 1 + 1)) + 1;
			switch (randomNum) {
			case 1:
				System.out.println(nameSkin + ": " + "\"I will talk to them, with my fists!\"");
				break;
			case 2:
				System.out.println(nameSkin + ": " + "\"Here comes the Thunder!\"");
				break;
			case 3:
				System.out.println(nameSkin + ": " + "\"We beat them, we go home. All in a day's work.\"");
				break;
			}
		}
		if (name.equals("Solar")) {
			int randomNum = (int) (Math.random() * (3 - 1 + 1)) + 1;
			switch (randomNum) {
			case 1:
				System.out.println(nameSkin + ": " + "\"Here to shine the light!\"");
				break;
			case 2:
				System.out.println(nameSkin + ": " + "\"Kick their butts? You got it!\"");
				break;
			case 3:
				System.out.println(nameSkin + ": " + "\"They are going to leave in flames.\"");
				break;
			}
		}
		if (name.equals("Kailani")) {
			int randomNum = (int) (Math.random() * (3 - 1 + 1)) + 1;
			switch (randomNum) {
			case 1:
				System.out.println(nameSkin + ": " + "\"I'll shred through these guys, just watch me.\"");
				break;
			case 2:
				System.out.println(nameSkin + ": " + "\"They don't know that I've got my eyes on them already.\"");
				break;
			case 3:
				System.out.println(nameSkin + ": " + "\"Don't mind the waves that come in.\"");
				break;
			}
		}
		if (name.equals("Dimentio")) {
			int randomNum = (int) (Math.random() * (3 - 1 + 1)) + 1;
			switch (randomNum) {
			case 1:
				System.out.println(nameSkin + ": " + "\"No worries, professional admin here.\"");
				break;
			case 2:
				System.out.println(nameSkin + ": " + "\"The metaverse was so much better under my rule.\"");
				break;
			case 3:
				System.out.println(nameSkin + ": "
						+ "\"If you have any questions on anything let me know. Otherwise leave me alone.\"");
				break;
			}
		}
		if (name.equals("Tom")) {
			int randomNum = (int) (Math.random() * (3 - 1 + 1)) + 1;
			switch (randomNum) {
			case 1:
				System.out.println(nameSkin + ": " + "\"With Lunar's bots here, we will be safe.\"");
				break;
			case 2:
				System.out.println(nameSkin + ": " + "\"They will not get past us!\"");
				break;
			case 3:
				System.out.println(nameSkin + ": " + "\"I will not let them take it away from us.\"");
				break;
			}
		}
		if (name.equals("Finley")) {
			int randomNum = (int) (Math.random() * (3 - 1 + 1)) + 1;
			switch (randomNum) {
			case 1:
				System.out.println(nameSkin + ": " + "\"I will dig their grave, just watch.\"");
				break;
			case 2:
				System.out.println(nameSkin + ": " + "\"Being a jock is not easy that's for sure.\"");
				break;
			case 3:
				System.out.println(nameSkin + ": " + "\"Can't wait to flex my skateboard tricks on them.\"");
				break;
			}
		}
		if (name.equals("Max")) {
			int randomNum = (int) (Math.random() * (3 - 1 + 1)) + 1;
			switch (randomNum) {
			case 1:
				System.out.println(nameSkin + ": " + "\"Let's just outsmart them.\"");
				break;
			case 2:
				System.out.println(nameSkin + ": " + "\"Rift power is no joke!\"");
				break;
			case 3:
				System.out.println(nameSkin + ": " + "\"I wish I could just send them away.\"");
				break;
			}
		}
		if (name.equals("Midnite")) {
			int randomNum = (int) (Math.random() * (3 - 1 + 1)) + 1;
			switch (randomNum) {
			case 1:
				System.out.println(nameSkin + ": " + "\"I will spook them out of here!\"");
				break;
			case 2:
				System.out.println(nameSkin + ": " + "\"Ugh, I just put my makeup on.\"");
				break;
			case 3:
				System.out.println(nameSkin + ": " + "\"Being a ghost is so tiring.\"");
				break;
			}
		}
		if (name.equals("Chief")) {
			int randomNum = (int) (Math.random() * (3 - 1 + 1)) + 1;
			switch (randomNum) {
			case 1:
				System.out.println(nameSkin + ": " + "\"Protect our turf!\"");
				break;
			case 2:
				System.out.println(nameSkin + ": " + "\"I could use a coffee break after this.\"");
				break;
			case 3:
				System.out.println(nameSkin + ": " + "\"I've seen enough. They are threats.\"");
				break;
			}
		}
		if (name.equals("Cherry")) {
			int randomNum = (int) (Math.random() * (3 - 1 + 1)) + 1;
			switch (randomNum) {
			case 1:
				System.out.println(nameSkin + ": " + "\"Time to show off my mech!\"");
				break;
			case 2:
				System.out.println(nameSkin + ": " + "\"Anyone want a cherry?\"");
				break;
			case 3:
				System.out.println(nameSkin + ": " + "\"If you need a heal let me know!\"");
				break;
			}
		}
		if (name.equals("Orion")) {
			int randomNum = (int) (Math.random() * (3 - 1 + 1)) + 1;
			switch (randomNum) {
			case 1:
				System.out.println(nameSkin + ": " + "\"Nobody pushes us down.\"");
				break;
			case 2:
				System.out.println(nameSkin + ": " + "\"Don't worry, I'll be back after I charge in.\"");
				break;
			case 3:
				System.out.println(nameSkin + ": " + "\"If you need some motivation, let me know!\"");
				break;
			}
		}
		if (name.equals("Louis")) {
			int randomNum = (int) (Math.random() * (3 - 1 + 1)) + 1;
			switch (randomNum) {
			case 1:
				System.out.println(nameSkin + ": " + "\"They better not make me call my guardian.\"");
				break;
			case 2:
				System.out.println(nameSkin + ": " + "\"If they get to close, I'll push them off.\"");
				break;
			case 3:
				System.out.println(nameSkin + ": " + "\"Let's drag this out and I can end it faster.\"");
				break;
			}
		}
		if (name.equals("Sammi")) {
			int randomNum = (int) (Math.random() * (3 - 1 + 1)) + 1;
			switch (randomNum) {
			case 1:
				System.out.println(nameSkin + ": " + "\"Time to lock in guys.\"");
				break;
			case 2:
				System.out.println(nameSkin + ": " + "\"No distractions.\"");
				break;
			case 3:
				System.out.println(nameSkin + ": " + "\"Anyone here wants to do a listening party?\"");
				break;
			}
		}
		if (name.equals("Xara")) {
			int randomNum = (int) (Math.random() * (3 - 1 + 1)) + 1;
			switch (randomNum) {
			case 1:
				System.out.println(nameSkin + ": " + "\"Let's see them out.\"");
				break;
			case 2:
				System.out.println(nameSkin + ": " + "\"This is what admin abuse really looks like.\"");
				break;
			case 3:
				System.out.println(nameSkin + ": " + "\"Shut them down.\"");
				break;
			}
		}
		if (name.equals("Julian")) {
			int randomNum = (int) (Math.random() * (3 - 1 + 1)) + 1;
			switch (randomNum) {
			case 1:
				System.out.println(nameSkin + ": " + "\"Rock them out of here!\"");
				break;
			case 2:
				System.out.println(nameSkin + ": " + "\"Don't mind the TNT cannon. It's 40% stable.\"");
				break;
			case 3:
				System.out.println(nameSkin + ": " + "\"Do you think they'll wanna hear some of my music?\"");
				break;
			}
		}
		if (name.equals("Via")) {
			int randomNum = (int) (Math.random() * (3 - 1 + 1)) + 1;
			switch (randomNum) {
			case 1:
				System.out.println(nameSkin + ": " + "\"Arro will get these guys for us!\"");
				break;
			case 2:
				System.out.println(nameSkin + ": "
						+ "\"Does anyone here want to join my pirate crew? Free desserts every week!\"");
				break;
			case 3:
				System.out.println(nameSkin + ": " + "\"They'll regret messing with the seven seas.\"");
				break;
			}
		}
		if (name.equals("Mack")) {
			int randomNum = (int) (Math.random() * (3 - 1 + 1)) + 1;
			switch (randomNum) {
			case 1:
				System.out.println(nameSkin + ": "
						+ "\"I got 3 kill contracts and we aren't leaving until I complete them all.\"");
				break;
			case 2:
				System.out.println(nameSkin + ": " + "\"I got your Macks, I mean backs*.\"");
				break;
			case 3:
				System.out.println(nameSkin + ": " + "\"They'll never see me coming.\"");
				break;
			}
		}
		if (name.equals("Rocco")) {
			int randomNum = (int) (Math.random() * (3 - 1 + 1)) + 1;
			switch (randomNum) {
			case 1:
				System.out.println(nameSkin + ": " + "\"Let's give them a good show shall we?\"");
				break;
			case 2:
				System.out.println(nameSkin + ": " + "\"Rocco Paper Scissors, any option is bad for them.\"");
				break;
			case 3:
				System.out.println(
						nameSkin + ": " + "\"When I summon the Warden don't freak out, he won't hurt you guys.\"");
				break;
			}
		}
		if (name.equals("Evil")) {
			int randomNum = (int) (Math.random() * (3 - 1 + 1)) + 1;
			switch (randomNum) {
			case 1:
				System.out.println(nameSkin + ": " + "\"No mercy onto these fools!\"");
				break;
			case 2:
				System.out.println(nameSkin + ": " + "\"World ending collisions and me mix quite well.\"");
				break;
			case 3:
				System.out.println(nameSkin + ": " + "\"The bot army will take care of them, no worries.\"");
				break;
			}
		}
		if (name.equals("Gash")) {
			int randomNum = (int) (Math.random() * (3 - 1 + 1)) + 1;
			switch (randomNum) {
			case 1:
				System.out.println(nameSkin + ": " + "\"Focus fire on one!\"");
				break;
			case 2:
				System.out.println(nameSkin + ": " + "\"They won't like it when my Ray of Doom is online.\"");
				break;
			case 3:
				System.out.println(nameSkin + ": " + "\"Stay close, and I got you.\"");
				break;
			}
		}
		if (name.equals("Radar")) {
			int randomNum = (int) (Math.random() * (3 - 1 + 1)) + 1;
			switch (randomNum) {
			case 1:
				System.out.println(nameSkin + ": " + "\"Let's run circles around them.\"");
				break;
			case 2:
				System.out.println(nameSkin + ": " + "\"Hope you guys did leg day already!\"");
				break;
			case 3:
				System.out.println(nameSkin + ": " + "\"They'll never catch up. No way.\"");
				break;
			}
		}
		if (name.equals("Dylan")) {
			int randomNum = (int) (Math.random() * (3 - 1 + 1)) + 1;
			switch (randomNum) {
			case 1:
				System.out.println(nameSkin + ": " + "\"Let's rob them.\"");
				break;
			case 2:
				System.out.println(nameSkin + ": " + "\"Goblins, do something!\"");
				break;
			case 3:
				System.out.println(nameSkin + ": " + "\"If I can roll a D20, then we can win this thing.\"");
				break;
			}
		}
		if (name.equals("Chloe")) {
			int randomNum = (int) (Math.random() * (3 - 1 + 1)) + 1;
			switch (randomNum) {
			case 1:
				System.out.println(nameSkin + ": " + "\"Where are my guards? It's ok, we'll be fine.\"");
				break;
			case 2:
				System.out.println(nameSkin + ": " + "\"I'll power you guys up. Do the heavy hitting for me!\"");
				break;
			case 3:
				System.out.println(nameSkin + ": " + "\"If you need a refresher I'll send a guard over.\"");
				break;
			}
		}
		if (name.equals("Mayhem")) {
			int randomNum = (int) (Math.random() * (3 - 1 + 1)) + 1;
			switch (randomNum) {
			case 1:
				System.out.println(
						nameSkin + ": " + "\"They have no idea what it's like to put everything on the line.\"");
				break;
			case 2:
				System.out.println(nameSkin + ": " + "\"I'll take the hits, then stomp them right after!\"");
				break;
			case 3:
				System.out.println(nameSkin + ": "
						+ "\"If you don't like one of them let me know. I will treat them with some darkmess.\"");
				break;
			}
		}
		if (name.equals("Anjelika")) {
			int randomNum = (int) (Math.random() * (3 - 1 + 1)) + 1;
			switch (randomNum) {
			case 1:
				System.out.println(nameSkin + ": " + "\"After this I'm gonna take a huge nap.\"");
				break;
			case 2:
				System.out.println(nameSkin + ": " + "\"The grind is never over clearly.\"");
				break;
			case 3:
				System.out.println(nameSkin + ": " + "\"Gonna make some nice pasta after this for sure.\"");
				break;
			}
		}
	}

	public void teamChat(Party p) {
		System.out.println();
		String name1 = p.partyNames(this).get(0).getName();
		String name2 = p.partyNames(this).get(1).getName();
		String name3 = "";
		int cur = -1;
		String responseName = null;
		int randomNum = (int) (Math.random() * (2 - 1 + 1)) + 1;
		if (randomNum == 1 && p.partyNames(this).get(0).isAlive()) {
			name3 = name1;
			cur = 0;
			responseName = p.partyNames(this).get(0).getSkin();
		} else if (randomNum == 2 && p.partyNames(this).get(1).isAlive()) {
			name3 = name2;
			cur = 1;
			responseName = p.partyNames(this).get(1).getSkin();
		}
		if (name.equals("Lunar")) {
			switch (name3) {
			case "Solar":
				System.out.println(nameSkin + ": " + "\"Solar, light them up!\"");
				break;
			case "Evil":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": " + "\"I still don't know what made you so corrupt Evil Lunar.\"");
					System.out.println(
							responseName + ": " + "\"You just haven't understood the true meaning of power yet.\"");
				}
				break;
			case "Dylan":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": " + "\"Dylan, we should run a game of DND after this yeah?\"");
					System.out.println(responseName + ": " + "\"For sure for sure.\"");
				}
				break;
			case "Cherry":
				System.out.println(
						nameSkin + ": " + "\"I need to show you my latest invention Cherry. I think you'd like it.\"");
				break;
			case "Finley":
				System.out.println(nameSkin + ": " + "\"Finley, show them no mercy.\"");
				break;
			case "Kailani":
				System.out.println(nameSkin + ": " + "\"Kailani, don't wait for us! You're too fast.\"");
				break;
			case "Rocco":
				System.out.println(nameSkin + ": " + "\"Rocco, let's see some craziness out there today.\"");
				break;
			}
		}
		if (name.equals("Aidan")) {
			switch (name3) {
			case "Julian":
				System.out.println(nameSkin + ": " + "\"Julian, this is just like band practice!\"");
				break;
			case "Airic":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": " + "\"Roblox fruits here? I like your thinking.\"");
					System.out.println(responseName + ": " + "\"Thanks, we'll get you one too one day.\"");
				}
				break;
			case "Dylan":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": " + "\"Dylan, want to try using one of my guns?\"");
					System.out.println(responseName + ": " + "\"We can play Fortnite after the fight Aidan.\"");
				}
				break;
			case "Liam":
				System.out.println(nameSkin + ": " + "\"Liam, you're a criminal laywer huh? So badass.\"");
				break;
			case "Mason":
				System.out.println(nameSkin + ": " + "\"Mason, show them what you got.\"");
				break;
			}
		}
		if (name.equals("Alex")) {
			switch (name3) {
			case "Solar":
				System.out.println(nameSkin + ": " + "\"Solar pop off girl!\"");
				break;
			case "Lunar":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": " + "\"Lunar, good to be fighting with you again!\"");
					System.out.println(responseName + ": " + "\"I've missed it too! Just like when we first met.\"");
				}
				break;
			case "Dylan":
				System.out.println(
						nameSkin + ": " + "\"Dylan, I find those DND games quite interesting. Teach me one day!\"");
				break;
			case "Sammi":
				System.out.println(nameSkin + ": " + "\"Sammi! My favorite scientist. We won't lose with her.\"");
				break;
			case "Finley":
				System.out.println(nameSkin + ": " + "\"Finley, how are things with Axol? You know.\"");
				break;
			case "Via":
				System.out.println(
						nameSkin + ": " + "\"Via I must join your pirate crew one day. Sailing the seas is awesome!\"");
				break;
			case "Zero":
				System.out.println(nameSkin + ": "
						+ "\"You may have tried to destroy our world Zero, but it's good to have you here.\"");
				break;
			}
		}
		if (name.equals("Clara")) {
			switch (name3) {
			case "Solar":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": " + "\"Solar, teach me how to use fire power!\"");
					System.out.println(responseName + ": " + "\"Haha, only if you teach me about the Darkmess.\"");
				}
				break;
			case "Liam":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": " + "\"Liam, I hope your lawyer career is doing good still.\"");
					System.out.println(responseName + ": " + "\"As good as ever.\"");
				}
				break;
			case "Midnite":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": " + "\"Midnite, let's show them how we do things around here.\"");
					System.out.println(responseName + ": " + "\"They'll be so spooked!\"");
				}
				break;
			case "Bedrock":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": " + "\"Bedrock, are you going to crush them as always?\"");
					System.out.println(responseName + ": " + "\"...!\"");
				}
				break;
			case "Mayhem":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": " + "\"See Mayhem, we're better as a team!\"");
					System.out.println(responseName + ": " + "\"Perhaps, but I still miss Alterna.\"");
				}
				break;
			case "Ashley":
				System.out.println(nameSkin + ": " + "\"Let's see that spark power Ashley!\"");
				break;
			case "Audrey":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": " + "\"So Audrey, how strong is Wonder Power?\"");
					System.out.println(
							responseName + ": " + "\"Picture those sparks of yours, multiplied by infinity.\"");
				}
				break;
			}
		}
		if (name.equals("Zero")) {
			switch (name3) {
			case "Lunar":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": " + "\"Lunar, why have you dragged me along?\"");
					System.out.println(responseName + ": "
							+ "\"We could use your power Zero. Like cheats? That's insane to have!\"");
				}
				break;
			case "Alex":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": "
							+ "\"I suppose this doesn't make up for the part of your base I destroyed?\"");
					System.out.println(responseName + ": " + "\"Get a kill or two, and them we will see.\"");
				}
				break;
			case "Dimentio":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(
							nameSkin + ": " + "\"You're quite resistant Dimentio. Even I can't get through.\"");
					System.out.println(responseName + ": " + "\"What can I say, I have a one of a kind firewall!\"");
				}
				break;
			case "Rocco":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": " + "\"I got put with you, really?\"");
					System.out.println(responseName + ": " + "\"I feel the exact same way.\"");
				}
				break;
			case "Hopper":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out
							.println(nameSkin + ": " + "\"Hopper, you really are one of the best assistants I had.\"");
					System.out.println(responseName + ": " + "\"I don't bow down to anyone, especially not you.\"");
				}
				break;
			case "Axol":
				System.out.println(
						nameSkin + ": " + "\"Axol, use those Axolotls wisely. I won't be able to bring them back.\"");
				break;
			case "Xara":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": " + "\"What else do you know about me Xara?\"");
					System.out
							.println(responseName + ": " + "\"You have quite the personality Zero I will say that.\"");
				}
				break;
			}
		}
		if (name.equals("Kithara")) {
			switch (name3) {
			case "Thunder":
				System.out.println(nameSkin + ": " + "\"Thunder, take them out!\"");
				break;
			case "Audrey":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": " + "\"Sorry about our past Audrey. I hope you can forgive me.\"");
					System.out.println(responseName + ": "
							+ "\"You're always my friend Kithara! Let's have them taste our Wonder Power.\"");
				}
				break;
			case "Louis":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": " + "\"Louis, what do you see now? For being a douche.\"");
					System.out.println(responseName + ": " + "\"I see the monster I have created now.\"");
				}
				break;
			case "Cherry":
				System.out.println(nameSkin + ": " + "\"Cherry, let's weaken them together.\"");
				break;
			case "Sammi":
				System.out.println(nameSkin + ": " + "\"Sammi, I can spice up your lab with my power if you want.\"");
				break;
			case "Gates":
				System.out.println(nameSkin + ": " + "\"Dr. Gates, thanks for letting me use the Wonder Power.\"");
				break;
			case "Anjelika":
				System.out.println(nameSkin + ": " + "\"Anjelika, let's see that power put in the work!\"");
				break;
			}
		}
		if (name.equals("Hopper")) {
			switch (name3) {
			case "Lunar":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": " + "\"Lunar, thank you for saving the AI world.\"");
					System.out.println(responseName + ": " + "\"Of course! That's what heroes do right?\"");
				}
				break;
			case "Alex":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": " + "\"Alex, your base is quite spectacular.\"");
					System.out.println(responseName + ": " + "\"Thank you! You're base is uh, something.\"");
				}
				break;
			case "Dimentio":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": " + "\"An admin huh? I've never seen one before.\"");
					System.out.println(responseName + ": " + "\"Hopper! Your base was a nice spot for my prison!\"");
				}
				break;
			case "Rocco":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": " + "\"Brother, with me!\"");
					System.out.println(responseName + ": " + "\"Right by your side Hopper!\"");
				}
				break;
			case "Zero":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": " + "\"I look down on you Zero.\"");
					System.out.println(responseName + ": " + "\"You were more fun when working for me anyways.\"");
				}
				break;
			case "Kailani":
				System.out.println(nameSkin + ": " + "\"Kailani, turn the tides for us!\"");
				break;
			case "Via":
				System.out.println(nameSkin + ": " + "\"Via, bring the boom!\"");
				break;
			}
		}
		if (name.equals("Thunder")) {
			switch (name3) {
			case "Solar":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": " + "\"Solar! No hard feelings of course yeah?\"");
					System.out.println(responseName + ": " + "\"It was a good rivalry I'll say that.\"");
				}
				break;
			case "Orion":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": "
							+ "\"Hey bro, whoever gets the least amount of kills has to pay for dinner tonight!\"");
					System.out.println(responseName + ": " + "\"So be it. I like my steak medium rare by the way.\"");
				}
				break;
			case "Grizz":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": " + "\"Good to see the old boss on the field!\"");
					System.out.println(responseName + ": "
							+ "\"You were my best assassin Thunder. I will see you in action now.\"");
				}
				break;
			case "Radar":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": " + "\"Look who decided to show up.\"");
					System.out.println(responseName + ": " + "\"You do your thing, and I will do mine.\"");
				}
				break;
			case "Zero":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": "
							+ "\"Zero, can you fix my gear after? I think there's a problem with my armor.\"");
					System.out.println(responseName + ": " + "\"I'm a virus not a mechanic. Cherry would know more.\"");
				}
				break;
			case "Max":
				System.out.println(nameSkin + ": " + "\"Max, don't hold back on those rifts!\"");
				break;
			case "Bedrock":
				System.out.println(nameSkin + ": " + "\"Bedrock, let's crush some skulls!\"");
				break;
			}
		}
		if (name.equals("Solar")) {
			switch (name3) {
			case "Lunar":
				System.out.println(nameSkin + ": " + "\"Let's go Lunar!\"");
				break;
			case "Sammi":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": " + "\"Sammi! I'm so happy to be on your team.\"");
					System.out.println(responseName + ": " + "\"I'm working with the goat, what else can I say.\"");
				}
				break;
			case "Max":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": " + "\"Max, I think there's a problem with my systems.\"");
					System.out.println(responseName + ": "
							+ "\"There's a sugar pile in there. Did you eat something sweet last night?\"");
				}
				break;
			case "Cherry":
				System.out.println(nameSkin + ": " + "\"Cherry, spread that love!\"");
				break;
			case "Clara":
				System.out.println(nameSkin + ": " + "\"Clara, our sparks and flames together will take them out!\"");
				break;
			case "Orion":
				System.out.println(nameSkin + ": " + "\"Orion, watch our backs!\"");
				break;
			case "Jesse":
				System.out.println(nameSkin + ": " + "\"Jesse, double burn them with me!\"");
				break;
			}
		}
		if (name.equals("Kailani")) {
			switch (name3) {
			case "Via":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(
							nameSkin + ": " + "\"Via, I'll speed up the tides for your ship. Then you can strike!\"");
					System.out.println(responseName + ": " + "\"I like that idea!\"");
				}
				break;
			case "Axol":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": " + "\"Axol! When is your date!?\"");
					System.out.println(responseName + ": " + "\"Date? I don't know what you're talking about.\"");
				}
				break;
			case "Alex":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": " + "\"Let's show off our gear Alex!\"");
					System.out.println(
							responseName + ": " + "\"My stuff compared to your tridents is not a fair comparison.\"");
				}
				break;
			case "Rocco":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": " + "\"How's your brother doing Rocco?\"");
					System.out.println(
							responseName + ": " + "\"Doing good. I hope uh Shelly and Sheldon are doing good too.\"");
				}
				break;
			case "Zero":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(
							nameSkin + ": " + "\"I'm still upset you tried to destroy our world, but I forgive you!\"");
					System.out.println(responseName + ": " + "\"Thank you. That... means a lot.\"");
				}
				break;
			case "Augie":
				System.out.println(nameSkin + ": " + "\"Augie, let's see those boating skills!\"");
				break;
			case "Dimentio":
				System.out.println(nameSkin + ": " + "\"Keep the Cybug stuff away from me Dimentio!\"");
				break;
			}
		}
		if (name.equals("Dimentio")) {
			switch (name3) {
			case "Xara":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": " + "\"Xara, just like the good old days right?\"");
					System.out.println(responseName + ": "
							+ "\"You locked me in jail for so long, don't think I forgot about that!\"");
				}
				break;
			case "Zero":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": " + "\"A virus on our team? Don't try anything.\"");
					System.out.println(responseName + ": " + "\"I could say the same for you.\"");
				}
				break;
			case "Radar":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": " + "\"Radar, that was a sneaky plan we had indeed.\"");
					System.out.println(responseName + ": " + "\"Sure was.\"");
				}
				break;
			case "Lunar":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": " + "\"You had such great potential as a teammate Lunar.\"");
					System.out.println(
							responseName + ": " + "\"No chance. After this fight, we aren't working together.\"");
				}
				break;
			case "Oona":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": " + "\"I might need some repairs Oona!\"");
					System.out.println(responseName + ": " + "\"Alright, when we are in the lab again sure.\"");
				}
				break;
			case "Evil":
				System.out.println(nameSkin + ": " + "\"Swarm them with bots Evil Lunar!\"");
				break;
			case "Axol":
				System.out.println(nameSkin + ": " + "\"I ran a scan, fun relationship you have Axol.\"");
				break;
			}
		}
		if (name.equals("Tom")) {
			switch (name3) {
			case "Gates":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": " + "\"Dr. Gates, I believe we are now even.\"");
					System.out.println(responseName + ": " + "\"I accept that.\"");
				}
				break;
			case "Audrey":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": " + "\"Sweetie, stay safe out there.\"");
					System.out.println(responseName + ": " + "\"It's ok dad, I got the Wonder Power on my side.\"");
				}
				break;
			case "Lunar":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": " + "\"I am sorry for taking your bots Lunar.\"");
					System.out.println(
							responseName + ": " + "\"It's ok Tom. The past is behind us. I'm still a huge fan!\"");
				}
				break;
			case "Gash":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": " + "\"You're a good employee Gash.\"");
					System.out.println(responseName + ": " + "\"Thank you sir. Just doing my job.\"");
				}
				break;
			case "Redgar":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": " + "\"Redgar, you are quite the learner.\"");
					System.out.println(
							responseName + ": " + "\"Being alongside you guys is so cool! I will do my best.\"");
				}
				break;
			case "Finley":
				System.out.println(nameSkin + ": " + "\"Finley, deliver those punches!\"");
				break;
			case "Midnite":
				System.out.println(nameSkin + ": " + "\"Midnite, let's show off our ghost power!\"");
				break;
			}
		}
		if (name.equals("Finley")) {
			switch (name3) {
			case "Lunar":
				System.out.println(nameSkin + ": " + "\"Show me what you got nerd boy.\"");
				break;
			case "Axol":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": " + "\"Axol! So happy to see you!\"");
					System.out.println(responseName + ": " + "\"I saved some extra axolotls just for you.\"");
				}
				break;
			case "Mack":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": " + "\"What is the secret to your build Mack?\"");
					System.out.println(responseName + ": " + "\"Leg day 3 times a week.\"");
				}
				break;
			case "Ayson":
				System.out.println(nameSkin + ": " + "\"Grayson and Jayson huh? The whole crew is back together!\"");
				break;
			case "Bedrock":
				System.out.println(nameSkin + ": " + "\"Bedrock, let's punch them to hell!\"");
				break;
			case "Liam":
				System.out.println(nameSkin + ": " + "\"Liam, blow them up!\"");
				break;
			case "Cherry":
				System.out.println(nameSkin + ": " + "\"Cherry, let's see that mech in action!\"");
				break;
			}
		}
		if (name.equals("Max")) {
			switch (name3) {
			case "Lunar":
				System.out.println(nameSkin + ": " + "\"So proud of you Lunar and always will be!\"");
				break;
			case "Solar":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": " + "\"Solar, let's see those new upgrades in action!\"");
					System.out.println(responseName + ": " + "\"I'm gonna show off soon just wait!\"");
				}
				break;
			case "Gates":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": "
							+ "\"I've read many of your papers Dr. Gates. It's all very fascinating.\"");
					System.out.println(responseName + ": " + "\"You are quite the scientist yourself!\"");
				}
				break;
			case "Orion":
				System.out.println(nameSkin + ": " + "\"Orion, beat them back!\"");
				break;
			case "Eli":
				System.out.println(
						nameSkin + ": " + "\"Is little buddy here with us Eli? He would win us this battle!\"");
				break;
			case "Evil":
				System.out.println(nameSkin + ": " + "\"If you have to be evil, then unleash the fury at them.\"");
				break;
			case "Mason":
				System.out.println(nameSkin + ": " + "\"Impressive technology Mason.\"");
				break;
			}
		}
		if (name.equals("Midnite")) {
			switch (name3) {
			case "Clara":
				System.out.println(nameSkin + ": " + "\"Show them who's boss Clara!\"");
				break;
			case "Mayhem":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": " + "\"Mayhem, that was a nasty plan you had us doing.\"");
					System.out.println(responseName + ": " + "\"Whatever must be done will be done.\"");
				}
				break;
			case "Tom":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": " + "\"So Tom, how were you able to replicate ghost power.\"");
					System.out.println(responseName + ": "
							+ "\"I've made some extreme breakthroughs after retiring my TV show!\"");
				}
				break;
			case "Bedrock":
				System.out.println(nameSkin + ": " + "\"No holding back Bedrock!\"");
				break;
			case "Ashley":
				System.out.println(nameSkin + ": " + "\"Keep us running Ashley!\"");
				break;
			case "Radar":
				System.out.println(nameSkin + ": " + "\"See if you can keep up with a ghost Radar!\"");
				break;
			case "Xara":
				System.out.println(nameSkin + ": " + "\"Give me a tour of the metaverse one day Xara!\"");
				break;
			}
		}
		if (name.equals("Chief")) {
			switch (name3) {
			case "Lunar":
				System.out.println(nameSkin + ": " + "\"Lunar, our hero!\"");
				break;
			case "Norman":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": " + "\"Norman! How is that new research going along?\"");
					System.out.println(responseName + ": " + "\"Pretty good! Testing will begin soon.\"");
				}
				break;
			case "Archer":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": " + "\"Archer, keep an eye on them, and us!\"");
					System.out.println(responseName + ": " + "\"Don't worry Chief. You can count on me.\"");
				}
				break;
			case "Zero":
				System.out.println(nameSkin + ": " + "\"Show me if Archer was right to bring you back, Zero.\"");
				break;
			case "Axol":
				System.out.println(nameSkin + ": " + "\"Let's see those axolotls Axol!\"");
				break;
			case "Ruby":
				System.out.println(nameSkin + ": " + "\"Ruby, with us!\"");
				break;
			case "Mack":
				System.out.println(nameSkin + ": " + "\"Mack, hunt them out.\"");
				break;
			}
		}
		if (name.equals("Cherry")) {
			switch (name3) {
			case "Lunar":
				System.out.println(nameSkin + ": " + "\"Lunar, time to pop off!\"");
				break;
			case "Finley":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": " + "\"Finley! Good to see you here.\"");
					System.out.println(responseName + ": " + "\"It's been a while. Let's take them out!\"");
				}
				break;
			case "Solar":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": " + "\"Solar I'm so intrigued by your design! \"");
					System.out.println(responseName + ": " + "\"Thanks! I can give you a rundown one day.\"");
				}
				break;
			case "Audrey":
				System.out.println(nameSkin + ": " + "\"Audrey, I need some of that wonder power one day!\"");
				break;
			case "Thunder":
				System.out.println(nameSkin + ": " + "\"Run them over Thunder!\"");
				break;
			case "Gash":
				System.out.println(nameSkin + ": " + "\"Gash, guard us!\"");
				break;
			case "Aidan":
				System.out.println(nameSkin + ": " + "\"Aidan, let's see that cover!\"");
				break;
			}
		}
		if (name.equals("Orion")) {
			switch (name3) {
			case "Thunder":
				System.out.println(nameSkin + ": " + "\"Come on Thunder, show me how it's done!\"");
				break;
			case "Alex":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": "
							+ "\"I still find it interesting how you live in a world of blocks Alex.\"");
					System.out.println(responseName + ": " + "\"Your world is pretty cool too I can't lie.\"");
				}
				break;
			case "Max":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out
							.println(nameSkin + ": " + "\"Hey boss, I caught one of them intruders the other night.\"");
					System.out.println(responseName + ": "
							+ "\"Ah good work. I knew hiring you was a good choice. As if I had a choice.\"");
				}
				break;
			case "Lunar":
				System.out.println(nameSkin + ": " + "\"Lunar, lead us to victory.\"");
				break;
			case "Radar":
				System.out.println(nameSkin + ": " + "\"Radar, I'll run in with you.\"");
				break;
			case "Archer":
				System.out.println(nameSkin + ": " + "\"Archer, keep our sights in check.\"");
				break;
			case "Midnite":
				System.out.println(nameSkin + ": " + "\"Midnite, let's kick them out of hiding!\"");
				break;
			}
		}
		if (name.equals("Louis")) {
			switch (name3) {
			case "Gates":
				System.out.println(nameSkin + ": " + "\"Oi Gates, I need a recharge of wonder soon.\"");
				break;
			case "Audrey":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": " + "\"Your use of wonder is amazing Audrey.\"");
					System.out.println(responseName + ": " + "\"Your use of it is, something alright.\"");
				}
				break;
			case "Kithara":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": " + "\"Hope you can forgive how I treated you Kithara.\"");
					System.out.println(responseName + ": " + "\"The past is in the past. We share common goals now.\"");
				}
				break;
			case "Dylan":
				System.out.println(nameSkin + ": " + "\"Dylan, let's chill them out.\"");
				break;
			case "Cherry":
				System.out.println(nameSkin + ": " + "\"Cherry, pop off with your mech!\"");
				break;
			case "Jesse":
				System.out.println(nameSkin + ": " + "\"Fire and ice Jesse, let's show them both!\"");
				break;
			case "Midnite":
				System.out.println(nameSkin + ": " + "\"It's not wonder power, but spook them Midnite!\"");
				break;
			}
		}
		if (name.equals("Sammi")) {
			switch (name3) {
			case "Solar":
				System.out.println(nameSkin + ": " + "\"Solar, pop off girl!\"");
				break;
			case "Mack":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": " + "\"Mack, anyone tell you how awesome you are?\"");
					System.out.println(
							responseName + ": " + "\"I see someone awesome all right. They're right in front of me.\"");
				}
				break;
			case "Clara":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": " + "\"Roll them over Clara!\"");
					System.out.println(responseName + ": " + "\"Yessir!\"");
				}
				break;
			case "Lunar":
				System.out.println(nameSkin + ": " + "\"Lunar, valorant after this yeah?\"");
				break;
			case "Liam":
				System.out.println(nameSkin + ": " + "\"Liam, take them to court!\"");
				break;
			case "Axol":
				System.out.println(nameSkin + ": " + "\"Axol, I'm keeping this axolotl after.\"");
				break;
			case "Katrina":
				System.out.println(nameSkin + ": " + "\"Katrina please come save me.\"");
				break;
			}
		}
		if (name.equals("Xara")) {
			switch (name3) {
			case "Rocco":
				System.out.println(nameSkin + ": " + "\"You're one crazy dude Rocco.\"");
				break;
			case "Dimentio":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": " + "\"We had some good times before didn't we?\"");
					System.out.println(responseName + ": " + "\"I suppose we once did.\"");
				}
				break;
			case "Oona":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": " + "\"How do you like my thinking Oona?\"");
					System.out.println(responseName + ": " + "\"You turned out great!\"");
				}
				break;
			case "Zero":
				System.out.println(nameSkin + ": " + "\"Zero, hack them.\"");
				break;
			case "Solar":
				System.out.println(nameSkin + ": " + "\"Solar, with me!\"");
				break;
			case "Max":
				System.out.println(nameSkin + ": " + "\"Max, let's see those rifts!\"");
				break;
			case "Ruby":
				System.out.println(nameSkin + ": " + "\"Ruby, bring my world here!\"");
				break;
			}
		}
		if (name.equals("Julian")) {
			switch (name3) {
			case "Lunar":
				System.out.println(nameSkin + ": " + "\"Lunar, time to rock out!\"");
				break;
			case "Dylan":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": " + "\"Dylan, how is that bass going?\"");
					System.out.println(responseName + ": " + "\"Going good! Good until my goblin broke it.\"");
				}
				break;
			case "Aidan":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": " + "\"Aidan, cover us!\"");
					System.out.println(responseName + ": " + "\"Got it!\"");
				}
				break;
			case "Ruby":
				System.out.println(nameSkin + ": " + "\"Ruby, turn the tides for us!\"");
				break;
			case "Audrey":
				System.out.println(nameSkin + ": " + "\"If I die Audrey, bring me back!\"");
				break;
			case "Kithara":
				System.out.println(nameSkin + ": " + "\"Kithara, let's put on a show for these frauds.\"");
				break;
			case "Airic":
				System.out.println(nameSkin + ": " + "\"Get the last laugh on them Airic!\"");
				break;
			}
		}
		if (name.equals("Via")) {
			switch (name3) {
			case "Solar":
				System.out.println(nameSkin + ": " + "\"Solar, blast them out of here girl!\"");
				break;
			case "Sammi":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": " + "\"Sammi, you should visit the ship again some day.\"");
					System.out
							.println(responseName + ": " + "\"Of course Via! Right after I fix the mess at my lab.\"");
				}
				break;
			case "Rocco":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": " + "\"Was your brother ever the type to venture the seas?\"");
					System.out.println(responseName + ": " + "\"Not when on my ship ha!\"");
				}
				break;
			case "Ashley":
				System.out.println(nameSkin + ": " + "\"Ashley, keep us safe.\"");
				break;
			case "Kailani":
				System.out.println(nameSkin + ": " + "\"Bring the water power Kailani!\"");
				break;
			case "Augie":
				System.out.println(nameSkin + ": " + "\"Show off that captain spirit Augie!\"");
				break;
			case "Chloe":
				System.out.println(nameSkin + ": " + "\"Sorry about breaking your kingdom Chloe!\"");
				break;
			}
		}
		if (name.equals("Mack")) {
			switch (name3) {
			case "Solar":
				System.out.println(nameSkin + ": " + "\"Solar, with me!\"");
				break;
			case "Sammi":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": " + "\"Sammi! Let's go out after this.\"");
					System.out.println(responseName + ": " + "\"I would love too!\"");
				}
				break;
			case "Bolo":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": " + "\"How you doing Bolo?\"");
					System.out.println(responseName + ": " + "\"Pretty good, let's get on overwatch after.\"");
				}
				break;
			case "Gash":
				System.out.println(nameSkin + ": " + "\"Gash, bring that guard energy!\"");
				break;
			case "Tom":
				System.out.println(nameSkin + ": "
						+ "\"Hey Tom, I'll forgive ya for what you did if you fix Dalton's doghouse.\"");
				break;
			case "Zero":
				System.out.println(nameSkin + ": " + "\"Zero, you are one cold guy. I like it.\"");
				break;
			case "Radar":
				System.out.println(nameSkin + ": " + "\"Let's sneak our way around them Radar.\"");
				break;
			}
		}
		if (name.equals("Rocco")) {
			switch (name3) {
			case "Zero":
				System.out.println(nameSkin + ": " + "\"Zero, I suppose I will fight alongside you just this once.\"");
				break;
			case "Hopper":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": " + "\"Mess them up Hopper!\"");
					System.out.println(responseName + ": "
							+ "\"How about you lend me a hand as well this time. You're quite explosive!\"");
				}
				break;
			case "Dimentio":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": " + "\"Another virus huh? Surely you must be better then Zero!\"");
					System.out.println(responseName + ": " + "\"I would say so but my ego must be kept in check.\"");
				}
				break;
			case "Axol":
				System.out.println(nameSkin + ": " + "\"Axol, let's use these creatures!\"");
				break;
			case "Kailani":
				System.out.println(nameSkin + ": " + "\"Dash circles around them Kailani!\"");
				break;
			case "Lunar":
				System.out.println(nameSkin + ": " + "\"Glad to see you here Lunar.\"");
				break;
			case "Via":
				System.out.println(nameSkin + ": " + "\"Via, impressive ship. Not as good as mine though.\"");
				break;
			}
		}
		if (name.equals("Evil")) {
			switch (name3) {
			case "Mason":
				System.out.println(nameSkin + ": " + "\"Good to see you again Mason, despite how we ended things.\"");
				break;
			case "Lunar":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": " + "\"I still don't understand how you prevailed.\"");
					System.out.println(responseName + ": " + "\"The good guys always win Evil Lunar.\"");
				}
				break;
			case "Ruby":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": " + "\"Ruby, thank you for allowing me to build my army!\"");
					System.out.println(responseName + ": "
							+ "\"Mojang never granted you access to our factories, but whatever.\"");
				}
				break;
			case "Max":
				System.out.println(nameSkin + ": " + "\"Embrace this power Max.\"");
				break;
			case "Chloe":
				System.out.println(nameSkin + ": " + "\"That was some cute kingdom you had Chloe.\"");
				break;
			case "Oona":
				System.out.println(nameSkin + ": "
						+ "\"How could you work like this for Mojang, there are far greater powers out there.\"");
				break;
			case "Solar":
				System.out.println(nameSkin + ": " + "\"A sibling huh? I should have an evil sister too.\"");
				break;
			}
		}
		if (name.equals("Gash")) {
			switch (name3) {
			case "Gates":
				System.out.println(nameSkin + ": " + "\"I got your back Dr.Gates.\"");
				break;
			case "Audrey":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(
							nameSkin + ": " + "\"Hope that space trial wasn't too hard on your side Audrey.\"");
					System.out.println(responseName + ": " + "\"All good! I got the wonder power on my side.\"");
				}
				break;
			case "Louis":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out
							.println(nameSkin + ": " + "\"Louis, your guardian is quite helpful. What's their name?\"");
					System.out.println(responseName + ": " + "\"To be fully honest, I don't know!\"");
				}
				break;
			case "Mack":
				System.out.println(nameSkin + ": " + "\"Assassinate them for real Mack!\"");
				break;
			case "Cherry":
				System.out.println(nameSkin + ": " + "\"Let's get that mech online Cherry!\"");
				break;
			case "Katrina":
				System.out.println(nameSkin + ": " + "\"Slide over if you need some shielding Katrina!\"");
				break;
			case "Airic":
				System.out.println(nameSkin + ": " + "\"Airic, get us out there quick!\"");
				break;
			}
		}
		if (name.equals("Radar")) {
			switch (name3) {
			case "Lunar":
				System.out.println(nameSkin + ": " + "\"Chill Lunar. Let me get the orbs.\"");
				break;
			case "Thunder":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": " + "\"So Thunder, we can forgive and forget right?\"");
					System.out.println(responseName + ": " + "\"Get my cage ready faster, and we'll be good.\"");
				}
				break;
			case "Dimentio":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": "
							+ "\"Dimentio, the Cybugs were never meant to enter, that was an honest mistake!\"");
					System.out.println(responseName + ": " + "\"Whatever. It helped us in the end.\"");
				}
				break;
			case "Bedrock":
				System.out.println(nameSkin + ": " + "\"Damn you're pretty slow Bedrock. I can help out.\"");
				break;
			case "Solar":
				System.out.println(nameSkin + ": " + "\"Light them up Solar!\"");
				break;
			case "Max":
				System.out.println(
						nameSkin + ": " + "\"You have some great ideas in the lab Max. Let's see it out there!\"");
				break;
			case "Oona":
				System.out.println(nameSkin + ": " + "\"The Mojang security could use some work Oona.\"");
				break;
			}
		}
		if (name.equals("Dylan")) {
			switch (name3) {
			case "Lunar":
				System.out.println(nameSkin + ": " + "\"Alright Lunar, show them out!\"");
				break;
			case "Julian":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": " + "\"Julian, don't hold back.\"");
					System.out.println(responseName + ": " + "\"They'll never see me coming!\"");
				}
				break;
			case "Liam":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": " + "\"Liam, you're the best lawyer I've seen.\"");
					System.out.println(responseName + ": " + "\"That's why I'm criminal.\"");
				}
				break;
			case "Eli":
				System.out.println(nameSkin + ": " + "\"Eli, keep us running!\"");
				break;
			case "Grizz":
				System.out.println(nameSkin + ": " + "\"You're a scary boss even while on the same team Grizz.\"");
				break;
			case "Orion":
				System.out.println(nameSkin + ": " + "\"Run them over Orion!\"");
				break;
			case "Thunder":
				System.out.println(nameSkin + ": " + "\"Thunderstruck them Thunder!\"");
				break;
			}
		}
		if (name.equals("Chloe")) {
			switch (name3) {
			case "Lunar":
				System.out.println(nameSkin + ": " + "\"Thank you for saving my kingdom Lunar.\"");
				break;
			case "Evil":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": " + "\"So that was you who stole my file power? How could you!\"");
					System.out.println(responseName + ": " + "\"Chill, it was needed in better hands anyways.\"");
				}
				break;
			case "Solar":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": " + "\"Solar, you're one of the fun ones huh?\"");
					System.out.println(responseName + ": " + "\"That's right!\"");
				}
				break;
			case "Kailani":
				System.out.println(nameSkin + ": " + "\"Kailani, send a guard of mine on a riptide too!\"");
				break;
			case "Axol":
				System.out.println(nameSkin + ": " + "\"Axol, keep our friends here alive.\"");
				break;
			case "Hopper":
				System.out.println(nameSkin + ": " + "\"I've heard many great stories about you Hopper!\"");
				break;
			case "Louis":
				System.out.println(nameSkin + ": " + "\"Louis, overwhelm them with our guards!\"");
				break;
			}
		}
		if (name.equals("Mayhem")) {
			switch (name3) {
			case "Bedrock":
				System.out.println(nameSkin + ": " + "\"Bedrock, show them what you're made of.\"");
				break;
			case "Clara":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": "
							+ "\"Clara, you were my finest spark hunter. Think about what we could've been.\"");
					System.out.println(responseName + ": " + "\"I would rather be free instead Mayhem.\"");
				}
				break;
			case "Midnite":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": " + "\"Out of all the powers you chose to be a ghost.\"");
					System.out.println(responseName + ": " + "\"It's quite useful, and spooky!\"");
				}
				break;
			case "Liam":
				System.out.println(nameSkin + ": " + "\"Liam, show me that you're more then just a lawyer.\"");
				break;
			case "Ashley":
				System.out.println(nameSkin + ": " + "\"Impressive spark power Ashley. Let's see it.\"");
				break;
			case "Radar":
				System.out.println(nameSkin + ": " + "\"Help me with that boost Radar, and I will return the favor.\"");
				break;
			case "Grizz":
				System.out.println(nameSkin + ": " + "\"I won't forgive you for what you did to our planet Grizz.\"");
				break;
			}
		}
		if (name.equals("Anjelika")) {
			switch (name3) {
			case "Solar":
				System.out.println(nameSkin + ": " + "\"Burn bright Solar!\"");
				break;
			case "Lunar":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(nameSkin + ": " + "\"Lunar, you like pasta yeah? Let's get some after this.\"");
					System.out.println(responseName + ": " + "\"That would be lovely!\"");
				}
				break;
			case "Zero":
				if (p.partyNames(this).get(cur).isAlive()) {
					System.out.println(
							nameSkin + ": " + "\"Zero, gotta let loose of all that negativity you know you know.\"");
					System.out.println(responseName + ": " + "\"Trying, it's working. Maybe.\"");
				}
				break;
			case "Oona":
				System.out.println(nameSkin + ": " + "\"Oona, you gotta show me the metaverse one day.\"");
				break;
			case "Rocco":
				System.out.println(nameSkin + ": " + "\"We're night and day Rocco, a good combination.\"");
				break;
			case "Louis":
				System.out.println(nameSkin + ": " + "\"Louis, keep them away!\"");
				break;
			case "Max":
				System.out.println(nameSkin + ": " + "\"You raised your children well Max.\"");
				break;
			}
		}
	}

	public String stats() {
		String dead = "";
		if (isAlive()) {
			dead = "Alive";
		} else {
			dead = "Downed on turn " + turndead;
		}
		damageIn = Math.round(damageIn * 10.0) / 10.0;
		healingIn = Math.round(healingIn * 10.0) / 10.0;
		damageOutput = Math.round(damageOutput * 10.0) / 10.0;
		return (nameSkin + "'s Statistics:" + "\n" + "Ended: " + dead + "\n" + "Weapons Used: " + weaponuse + "\n"
				+ "Abilities Used: " + abilityuse + "\n" + "Ultimates Used: " + ultuse + "\n" + "Times Dashed: "
				+ dashed + "\n" + "Times Jumped: " + jumped + "\n" + "Total Movement: " + totalMovement + "\n"
				+ "Healing Output: " + healingOutput + "\n" + "Healing Received: " + healingIn + "\n"
				+ "Damage Output: " + damageOutput + "\n" + "Damage Received: " + damageIn);
	}

}
