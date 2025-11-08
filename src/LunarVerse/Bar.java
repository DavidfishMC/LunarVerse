package LunarVerse;

import java.util.ArrayList;



public class Bar {
	static final String bold = "\u001b[1m";
	public static final String BLOCK = "\u2588";
	String type = "";
	String bar = "";
	double amount = 0;
	Player p;
	
	public Bar() {
		
	}
	
	public void clemBar() {
		type = "valor";
	}
	
	public boolean hasValor() {
		if (type.equals("boss")) {
			return true;
		}
		double left = p.getHealth() / p.getMaxHP();
		if (!type.equals("valor")) {
			return false;
		}
		if (left < 1 - amount) {
			return false;
		}
		return true;
	}

	public boolean hasArmor() {
		if (type.equals("boss")) {
			return true;
		}
		double left = p.getHealth() / p.getMaxHP();
		if (!type.equals("armor")) {
			return false;
		}
		if (left < 1 - amount) {
			return false;
		}
		return true;
	}
	
	public boolean hasEssence() {
		if (type.equals("boss")) {
			return true;
		}
		double left = p.getHealth() / p.getMaxHP();
		if (!type.equals("essence")) {
			return false;
		}
		if (left < 1 - amount) {
			return false;
		}
		return true;
	}
	
	public void updateBar() {
	    // Calculate how much health is left and round it down to the first decimal place
	    double left = Math.floor((p.getHealth() / p.getMaxHP()) * 10) / 10.0;
	    int totalBlocks = 20;
	    int specialBlocks2 = (int) Math.floor(amount * totalBlocks);

	    // Calculate the actual number of blocks based on current health
	    int actualBlocks = (int) Math.floor(left * totalBlocks);

	    // Ensure at least one block is printed if health is less than 5%
	    if (actualBlocks == 0 && left > 0) {
	        actualBlocks = 2;
	    }

	    String special = "";
	    String health = "";
	    String healthBlocks = "";
	    String specialBlocks = "";

	    // Calculate the number of standard health blocks
	    int standardBlocks = actualBlocks <= totalBlocks - specialBlocks2 ? actualBlocks : totalBlocks - specialBlocks2;

	    // Add standard color blocks first
	    for (int i = 0; i < standardBlocks; i++) {
	        healthBlocks = healthBlocks + BLOCK;
	    }

	    // Add special color blocks only if there are enough health blocks left
	    for (int i = standardBlocks; i < actualBlocks; i++) {
	        specialBlocks = specialBlocks + BLOCK;
	    }

	    String color = "";
	    if (type.equals("valor")) {
	        color = "#ff62b1";
	    }
	    if (type.equals("armor")) {
	        color = "#ffa539";
	    }
	    if (type.equals("essence")) {
	        color = "#78edff";
	    }
	    if (type.equals("boss")) {
	        color = "#4c33ff";
	    }
	    try {
	    	if (!specialBlocks.isEmpty()) {
		        special = getGradientName(specialBlocks, color, color);
		    }
	    }catch(Exception e) {
	    	
	    }
	    if (healthBlocks.isEmpty()) {
	        healthBlocks = BLOCK + BLOCK;
	    }
	    if (!healthBlocks.isEmpty()) {
	        health = getGradientName(healthBlocks, "#ebeaea", "#ebeaea");
	    }
	    String over = "";
	    if (p.hasOverhealth()) {
	        String overblocks = "";
	        double left2 = Math.floor((p.getOverhealth() / p.getMaxHP()) * 10) / 10.0;

	        // Calculate the number of overhealth blocks
	        int overhealthBlocks = (int) Math.floor(left2 * 20);  // 20 blocks representing 100% max health, each block is 5%

	        // Add overhealth blocks
	        for (int i = 0; i < overhealthBlocks; i++) {
	            overblocks = overblocks + BLOCK;
	        }
	        if (overblocks.isEmpty()) {
		        overblocks = BLOCK + BLOCK;
		    }
	        over = getGradientName(overblocks, "#5fff7a", "#5fff7a");
	    }
	    if (type.equals("boss")) {
	    	bar = special + over;
	    }else {
	    	bar = health + special + over;
	    }
	}





	
	public String getBar() {
		updateBar();
		return bar;
	}
	
	public void setBar(String name, Player player) {
		p = player;
		switch (name) {
		  case "Lunar":
			  type = "valor";
			  amount = 0.4;
		    break;
		  case "Aidan":
			  type = "valor";
			  amount = 0.35;
		    break;
		  case "Finley":
			  type = "valor";
			  amount = 0.4;
		    break;
		  case "Ayson":
			  type = "valor";
			  amount = 0.45;
		    break;
		  case "Alex":
			  type = "valor";
			  amount = 0.35;
		    break;
		  case "Jesse":
			  type = "valor";
			  amount = 0.3;
		    break;
		  case "Chief":
			  type = "armor";
			  amount = 0.3;
		    break;
		  case "Norman":
			  type = "essence";
			  amount = 0.5;
		    break;
		  case "Katrina":
			  type = "essence";
			  amount = 0.6;
		    break;
		  case "Sammi":
			  type = "valor";
			  amount = 0.4;
		    break;
		  case "Mack":
			  type = "valor";
			  amount = 0.5;
		    break;
		  case "Axol":
			  type = "essence";
			  amount = 0.4;
		    break;
		  case "Via":
			  type = "valor";
			  amount = 0.35;
		    break;
		  case "Hopper":
			  type = "armor";
			  amount = 0.4;
		    break;
		  case "Kailani":
			  type = "valor";
			  amount = 0.4;
		    break;
		  case "Zero":
			  type = "essence";
			  amount = 0.5;
		    break;
		  case "Ruby":
			  type = "essence";
			  amount = 0.5;
		    break;
		  case "Chloe":
			  type = "essence";
			  amount = 0.6;
		    break;
		  case "Mason":
			  type = "valor";
			  amount = 0.4;
		    break;
		  case "Max":
			  type = "essence";
			  amount = 0.5;
		    break;
		  case "Evil":
			  type = "valor";
			  amount = 0.4;
		    break;
		  case "Airic":
			  type = "valor";
			  amount = 0.35;
		    break;
		  case "Julian":
			  type = "valor";
			  amount = 0.3;
		    break;
		  case "Solar":
			  type = "valor";
			  amount = 0.35;
		    break;
		  case "Eli":
			  type = "essence";
			  amount = 0.65;
		    break;
		  case "Dylan":
			  type = "armor";
			  amount = 0.25;
		    break;
		  case "Orion":
			  type = "armor";
			  amount = 0.35;
		    break;
		  case "Grizz":
			  type = "armor";
			  amount = 0.25;
		    break;
		  case "Clara":
			  type = "valor";
			  amount = 0.45;
		    break;
		  case "Liam":
			  type = "essence";
			  amount = 0.5;
		    break;
		  case "Mayhem":
			  type = "armor";
			  amount = 0.4;
		    break;
		  case "Bedrock":
			  type = "armor";
			  amount = 0.35;
		    break;
		  case "Augie":
			  type = "armor";
			  amount = 0.4;
		    break;
		  case "Midnite":
			  type = "valor";
			  amount = 0.5;
		    break;
		  case "Ashley":
			  type = "essence";
			  amount = 0.7;
		    break;
		  case "Radar":
			  type = "valor";
			  amount = 0.35;
		    break;
		  case "Oona":
			  type = "essence";
			  amount = 0.55;
		    break;
		  case "Dimentio":
			  type = "armor";
			  amount = 0.25;
		    break;
		  case "Rocco":
			  type = "valor";
			  amount = 0.4;
		    break;
		  case "Xara":
			  type = "armor";
			  amount = 0.2;
		    break;
		  case "Thunder":
			  type = "armor";
			  amount = 0.2;
		    break;
		  case "Archer":
			  type = "valor";
			  amount = 0.3;
		    break;
		  case "Tom":
			  type = "armor";
			  amount = 0.4;
		    break;
		  case "Gates":
			  type = "armor";
			  amount = 0.35;
		    break;
		  case "Redgar":
			  type = "essence";
			  amount = 0.7;
		    break;
		  case "Cherry":
			  type = "essence";
			  amount = 0.5;
		    break;
		  case "Gash":
			  type = "armor";
			  amount = 0.3;
		    break;
		  case "Audrey":
			  type = "essence";
			  amount = 0.6;
		    break;
		  case "Louis":
			  type = "valor";
			  amount = 0.35;
		    break;
		  case "Kithara":
			  type = "armor";
			  amount = 0.3;
		    break;
		  case "Angelos":
			  type = "armor";
			  amount = 0.3;
		    break;
		  case "Burt":
			  type = "valor";
			  amount = 0.3;
		    break;
		  case "Bolo":
			  type = "valor";
			  amount = 0.3;
		    break;
		  case "Anjelika":
			  type = "armor";
			  amount = 0.4;
		    break;
		  case "Melony":
			  type = "essence";
			  amount = 0.4;
		    break;
		  case "Echo":
			  type = "valor";
			  amount = 0.5;
		    break;
		  case "Makani":
			  type = "valor";
			  amount = 0.4;
		    break;
		  case "Rhythm":
			  type = "essence";
			  amount = 0.55;
		    break;
		  case "Grenadine":
			  type = "armor";
			  amount = 0.35;
		    break;
		  case "Patitek":
			  type = "armor";
			  amount = 0.2;
		    break;
		  case "Crystal":
			  type = "armor";
			  amount = 0.4;
		    break;
		  case "Velvet":
			  type = "valor";
			  amount = 0.4;
		    break;
		  case "Drift":
			  type = "valor";
			  amount = 0.5;
		    break;
		  case "Snowfall":
			  type = "valor";
			  amount = 0.35;
		    break;
		  case "Shutter":
			  type = "essence";
			  amount = 0.5;
		    break;
		  case "Magnet":
			  type = "armor";
			  amount = 0.35;
		    break;
		  case "Jing":
			  type = "valor";
			  amount = 0.5;
		    break;
		  case "Folden":
			  type = "essence";
			  amount = 0.35;
		    break;
		  case "Bladee":
			  type = "armor";
			  amount = 0.2;
		    break;
		  case "Margo":
			  type = "essence";
			  amount = 0.4;
		    break;
		  case "Ivy":
			  type = "essence";
			  amount = 0.6;
		    break;
		  case "Petra":
			  type = "armor";
			  amount = 0.4;
		    break;
		  case "Quincy":
			  type = "armor";
			  amount = 0.3;
		    break;
		  case "Unice":
			  type = "valor";
			  amount = 0.4;
		    break;
		  case "Flor":
			  type = "essence";
			  amount = 0.6;
		    break;
		  case "Yuri":
			  type = "armor";
			  amount = 0.4;
		    break;
		  case "Millie":
			  type = "armor";
			  amount = 0.35;
		    break;
		  case "Leaf":
			  type = "armor";
			  amount = 0.3;
		    break;
		  case "Courtney":
			  type = "valor";
			  amount = 0.3;
		    break;
		  case "Divine":
			  type = "valor";
			  amount = 0.45;
		    break;
		  case "Gambit":
			  type = "armor";
			  amount = 0.4;
		    break;
		  case "Cloud":
			  type = "valor";
			  amount = 0.5;
		    break;
		  case "Winnie":
			  type = "essence";
			  amount = 0.55;
		    break;
		  case "Pearl":
			  type = "valor";
			  amount = 0.3;
		    break;
		  case "Andrew":
			  type = "valor";
			  amount = 0.35;
		    break;
		  case "Orchid":
			  type = "armor";
			  amount = 0.35;
		    break;
		  case "Everest":
			  type = "essence";
			  amount = 0.7;
		    break;
		  case "Clementine":
			  type = "armor";
			  amount = 0.4;
		    break;
		  case "Deny":
			  type = "armor";
			  amount = 0.35;
		    break;
		  case "Rin":
			  type = "essence";
			  amount = 0.65;
		    break;
		  case "Victor":
			  type = "armor";
			  amount = 0.3;
		    break;
		  case "Isabelle":
			  type = "essence";
			  amount = 0.5;
		    break;
		  case "Lumiere":
			  type = "essence";
			  amount = 0.5;
		    break;
		  case "Willow":
			  type = "valor";
			  amount = 0.3;
		    break;
		  case "Jazz":
			  type = "armor";
			  amount = 0.3;
		    break;
		  case "Harper":
			  type = "valor";
			  amount = 0.5;
		    break;
		  case "Noah":
			  type = "essence";
			  amount = 0.65;
		    break;
		  case "Jade":
			  type = "valor";
			  amount = 0.5;
		    break;
		  case "Stellar":
			  type = "armor";
			  amount = 0.3;
		    break;
		  case "Bonbon":
			  type = "valor";
			  amount = 0.5;
		    break;
		  case "Boss:Finley":
			  type = "boss";
			  amount = 1;
		    break;
		}
		updateBar();
	}
	
	public static int[] interpolate(int[] startRgb, int[] endRgb, float fraction) {
 		int r = (int) (startRgb[0] + (endRgb[0] - startRgb[0]) * fraction);
 		int g = (int) (startRgb[1] + (endRgb[1] - startRgb[1]) * fraction);
 		int b = (int) (startRgb[2] + (endRgb[2] - startRgb[2]) * fraction);
 		return new int[] { r, g, b };
 	}
 	
 	public static int[] hexToRgb(String colorStr) {
		return new int[] { Integer.valueOf(colorStr.substring(1, 3), 16), Integer.valueOf(colorStr.substring(3, 5), 16),
				Integer.valueOf(colorStr.substring(5, 7), 16) };
	}

 	// Method to create a gradient name string with bold formatting
 	public static String getGradientName(String name, String... hexColors) {
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
