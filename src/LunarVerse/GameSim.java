package LunarVerse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class GameSim {
	
	static ArrayList<Orb> orbs = new ArrayList<Orb>();
	static ArrayList<Cover> cover = new ArrayList<Cover>();
	static ArrayList<Utility> utility = new ArrayList<Utility>();
	int xBound;
	int yBound;
	static Music audioPlayer;
	static Music menuPlayer;
	static Example image;
	static int turns2 = 0;
	static final String reset = "\u001B[0m";
	static final String color = "\u001b[38;5;";
	static final String bold = "\u001b[1m";
	public static Battlefield b;
	public static String mode = "";
	public static long clock = 0L;
	public static boolean game;
	public static boolean reduceSpawn = false;
	public static boolean reduceCover = false;

	public static void main(String[] args) {
		String testPlayer1 = "Harper";
		String testPlayer2 = "Zero";
		String testPlayer3 = "Jing";
		/*
		try {
			image = new Example();
		} catch (IOException e) {
		}
		*/
		try {
			String audio = "audio/jade.wav";
			audioPlayer = new Music(audio, false);
		}catch (Exception e) {
			System.out.println(e);
		}
		try {
			String audio = "audio/lobby.wav";
			menuPlayer = new Music(audio, false);
			menuPlayer.play();
		}catch (Exception e) {
			System.out.println(e);
		}
		boolean play = true;
		boolean play2 = true;
		boolean play3 = true;
		boolean play4 = true;
		boolean play5 = true;
		String tD;
		int turns = 0;
		//HP, Damage, Turn, Name, X, Y, Range, Movement, Ult
		Player p1 = new Player(2650, 175, true, "Deny", 40, 40, 30, 100, 0);
		Player p3 = new Player(2900, 325, false, "Melony", 0, 3, 10, 100, 0);
		Player p5 = new Player(4850, 575, false, "Rocco", 3, 0, 6, 500, 0);
		
		Player p2 = new Player(10, 225, false, "Finley", 40, 40, 9, 100, 0);
		Player p4 = new Player(10, 200, false, "Louis", 40, 37, 10, 100, 0);
		Player p6 = new Player(10000, 200, false, "Solar", 37, 40, 10, 100, 0);
		
		Player p = new Player(10000, 200, false, "Solar", 37, 40, 10, 100, 0);
		Player t = new Player(10000, 200, false, "Solar", 37, 40, 10, 100, 0);
		game = false;
		Scanner input = new Scanner(System.in);
		String title = p1.getGradientName("LunarVerse", "#B68CB8", "#6461A0", "#314CB6", "#0A81D1");
		String david = p1.getGradientName("Davidfish", "#3BDCFC", "#5370FD");
		String sockyman = p1.getGradientName("sockyman", "#ABABAB", "#FD637A");
		String starstruck = p1.getGradientName("_starrstruckk_", "#C1A5A9", "#F08CAE", "#9A4C95", "#EB7BC0");
		String dilzee = p1.getGradientName("Dilzee", "#2DDF2A", "#1EC23F", "#3594ED", "#4F8CA1");
		String ashy = p1.getGradientName("ashyxin", "#4ECAB6", "#4AE5E8", "#A296B7", "#F070E5");
		System.out.println(title);
		System.out.println("Developed by "+david+" and "+sockyman+". Artwork by "+starstruck+", "+dilzee+", and "+ashy+".");
		System.out.println("Inspired by the Mario and Rabbids games and the V.C., R.C., and D.C. trilogies.");
		System.out.println("Music and Sound Effects taken from the Mario and Rabbids games, Valorant, and various shows.");
		String url = p1.getGradientName("bit.ly/LunarVerseWiki", "#B68CB8", "#6461A0", "#314CB6", "#0A81D1");
        System.out.println("Learn the game at the wiki here: " +url);
		System.out.print("Enter any key to start: ");
		String temp = input.next();
		System.out.println();
		String mapName = "";
		if(temp.equals("a")) {
			System.out.print("Select the gamemode to be played: ");
			String temp8 = input.next();
			switch (temp8) {
			case "Comet":
				mode = "Comet";
			    break;
			case "Charm":
				mode = "Charm";
			    break;
			case "Rotate":
				mode = "Rotate";
			    break;
			case "Evolution":
				mode = "Evolution";
			    break;
			case "Boss":
				mode = "Boss";
			    break;
			}
			System.out.println();
			System.out.println("Team A, pick your characters.");
			System.out.print("Character Selection 1: ");
			String temp2 = input.next();
			p1 = CharacterSelection(p1, temp2, true, 0, 0);
			//p1.image().open();
			System.out.print("Character Selection 2: ");
			String temp3 = input.next();
			p3 = CharacterSelection(p3, temp3, false, 0, 0);
			//p1.image().close();
			System.out.print("Character Selection 3: ");
			String temp4 = input.next();
			p5 = CharacterSelection(p5, temp4, false, 0, 0);
			System.out.println();
			if (mode.equals("Boss")) {
				System.out.println("Team B, pick your Boss.");
				System.out.print("Character Selection 1: ");
				String temp5 = input.next();
				p2 = CharacterSelection(p2, temp5, true, 41, 41);
				p4 = CharacterSelection(p4, "Bot", false, 141, 141);
				p6 = CharacterSelection(p6, "Bot", false, 141, 141);
				System.out.println();
			}else {
				System.out.println("Team B, pick your characters.");
				System.out.print("Character Selection 1: ");
				String temp5 = input.next();
				p2 = CharacterSelection(p2, temp5, true, 41, 41);
				System.out.print("Character Selection 2: ");
				String temp6 = input.next();
				p4 = CharacterSelection(p4, temp6, false, 41, 41);
				System.out.print("Character Selection 3: ");
				String temp7 = input.next();
				p6 = CharacterSelection(p6, temp7, false, 41, 41);
				System.out.println();
			}
			p1.changeSkin("awt");
			p3.changeSkin("twa");
			p5.changeSkin("twda");
			p2.changeSkin("Militi");
			p4.changeSkin("Milita");
			p6.changeSkin("Miliia");
			b = new Battlefield("Merge Caste", p1, p3, p5, p2, p4, p6);
			b.setStart(true);
			System.out.println("Team A, where do you want to deploy " + p1.getSkin() + ".");
			Location l = SetCursor(p1, p2, p4, p6, p3, p5, 0);
			p1.getLoc().set(l.getX(), l.getY());
			System.out.println("Team A, where do you want to deploy " + p3.getSkin() + ".");
			Location l2 = SetCursor(p3, p2, p4, p6, p1, p5, 0);
			p3.getLoc().set(l2.getX(), l2.getY());
			System.out.println("Team A, where do you want to deploy " + p5.getSkin() + ".");
			Location l3 = SetCursor(p5, p2, p4, p6, p3, p1, 0);
			p5.getLoc().set(l3.getX(), l3.getY());
			
			if (mode.equals("Boss")) {
				System.out.println("Team B, where do you want to deploy " + p2.getSkin() + ".");
				Location l4 = SetCursor(p2, p1, p3, p5, p4, p6, 0);
				p2.getLoc().set(l4.getX(), l4.getY());
			}else {
				System.out.println("Team B, where do you want to deploy " + p2.getSkin() + ".");
				Location l4 = SetCursor(p2, p1, p3, p5, p4, p6, 0);
				p2.getLoc().set(l4.getX(), l4.getY());
				System.out.println("Team B, where do you want to deploy " + p4.getSkin() + ".");
				Location l5 = SetCursor(p4, p1, p3, p5, p2, p6, 0);
				p4.getLoc().set(l5.getX(), l5.getY());
				System.out.println("Team B, where do you want to deploy " + p6.getSkin() + ".");
				Location l6 = SetCursor(p6, p1, p3, p5, p4, p2, 0);
				p6.getLoc().set(l6.getX(), l6.getY());
			}
			b.setStart(false);
			if(mode.equals("Charm")) {
				System.out.println(p1.getSkin() + ", pick your charm.");
				System.out.print("Charm Selection: ");
				String charm1 = input.next();
				p1.setCharm(charm1);
				System.out.println(p3.getSkin() + ", pick your charm.");
				System.out.print("Charm Selection: ");
				String charm2 = input.next();
				p3.setCharm(charm2);
				System.out.println(p5.getSkin() + ", pick your charm.");
				System.out.print("Charm Selection: ");
				String charm3 = input.next();
				p5.setCharm(charm3);
				System.out.println(p2.getSkin() + ", pick your charm.");
				System.out.print("Charm Selection: ");
				String charm4 = input.next();
				p2.setCharm(charm4);
				System.out.println(p4.getSkin() + ", pick your charm.");
				System.out.print("Charm Selection: ");
				String charm5 = input.next();
				p4.setCharm(charm5);
				System.out.println(p6.getSkin() + ", pick your charm.");
				System.out.print("Charm Selection: ");
				String charm6 = input.next();
				p6.setCharm(charm6);
			}
			if(mode.equals("Rotate")) {
				System.out.println();
				System.out.print("Team A, pick your rotate: ");
				String temp9 = input.next();
				p = CharacterSelection(p, temp9, true, 100, 100);
				System.out.println();
				System.out.print("Team B, pick your characters: ");
				String temp12 = input.next();
				t = CharacterSelection(t, temp12, true, 100, 100);
				System.out.println();
			}
		}else {
			p1 = new Player(2000, 200, true, testPlayer1, 20, 20, 13, 10, 0);
			//p1.takeDamage(950);
			p1.addRole("tank");
			p3 = new Player(2400, 475, false, testPlayer2, 20, 20, 10, 10, 0);
			p5 = new Player(3600, 175, false, testPlayer3, 20, 20, 11, 9, 8);
			
			p2 = new Player(100, 1250, false, "Boss:Finley", 25, 20, 10, 10, 7);
			p4 = new Player(9000, 250, false, "Zero", 23, 20, 10, 10, 0);
			p6 = new Player(10000, 200, false, "Kithara", 20, 8, 10, 10, 7);
			//mode = "Evolution";
			String skinName = "Sakura";
			p1.changeSkin(skinName);
			p3.changeSkin(skinName);
			p5.changeSkin(skinName);
		}
		p1.setAb("a");
		p3.setAb("a");
		p5.setAb("a");
		p2.setAb("b");
		p4.setAb("b");
		p6.setAb("b");
		if (p1.getName().equals("Gambit")) {
			p1.setRooks(p2, p4, p6, p3, p5);
		}
		if (p3.getName().equals("Gambit")) {
			p3.setRooks(p2, p4, p6, p1, p5);
		}
		if (p5.getName().equals("Gambit")) {
			p5.setRooks(p2, p4, p6, p3, p1);
		}
		if (p2.getName().equals("Gambit")) {
			p2.setRooks(p1, p3, p5, p4, p6);
		}
		if (p4.getName().equals("Gambit")) {
			p4.setRooks(p1, p3, p5, p2, p6);
		}
		if (p6.getName().equals("Gambit")) {
			p6.setRooks(p1, p3, p5, p4, p2);
		}
		
		if (p1.getName().equals("Rin")) {
			p1.setMochi(p2, p4, p6, p3, p5);
		}
		if (p3.getName().equals("Rin")) {
			p3.setMochi(p2, p4, p6, p1, p5);
		}
		if (p5.getName().equals("Rin")) {
			p5.setMochi(p2, p4, p6, p3, p1);
		}
		if (p2.getName().equals("Rin")) {
			p2.setMochi(p1, p3, p5, p4, p6);
		}
		if (p4.getName().equals("Rin")) {
			p4.setMochi(p1, p3, p5, p2, p6);
		}
		if (p6.getName().equals("Rin")) {
			p6.setMochi(p1, p3, p5, p4, p2);
		}
		
		if (p1.getName().equals("Victor")) {
			p1.setSteam(p2, p4, p6, p3, p5);
		}
		if (p3.getName().equals("Victor")) {
			p3.setSteam(p2, p4, p6, p1, p5);
		}
		if (p5.getName().equals("Victor")) {
			p5.setSteam(p2, p4, p6, p3, p1);
		}
		if (p2.getName().equals("Victor")) {
			p2.setSteam(p1, p3, p5, p4, p6);
		}
		if (p4.getName().equals("Victor")) {
			p4.setSteam(p1, p3, p5, p2, p6);
		}
		if (p6.getName().equals("Victor")) {
			p6.setSteam(p1, p3, p5, p4, p2);
		}
		
		if (p1.getName().equals("Jazz")) {
			p1.setBubble(p2, p4, p6, p3, p5);
		}
		if (p3.getName().equals("Jazz")) {
			p3.setBubble(p2, p4, p6, p1, p5);
		}
		if (p5.getName().equals("Jazz")) {
			p5.setBubble(p2, p4, p6, p3, p1);
		}
		if (p2.getName().equals("Jazz")) {
			p2.setBubble(p1, p3, p5, p4, p6);
		}
		if (p4.getName().equals("Jazz")) {
			p4.setBubble(p1, p3, p5, p2, p6);
		}
		if (p6.getName().equals("Jazz")) {
			p6.setBubble(p1, p3, p5, p4, p2);
		}
		p1.setPlayers(p2, p4, p6, p3, p5);
		p3.setPlayers(p2, p4, p6, p1, p5);
		p5.setPlayers(p2, p4, p6, p3, p1);
		p2.setPlayers(p1, p3, p5, p4, p6);
		p4.setPlayers(p1, p3, p5, p2, p6);
		p6.setPlayers(p1, p3, p5, p4, p2);
		b = new Battlefield("Nexus Villae", p1, p3, p5, p2, p4, p6);
		Party party1 = new Party(true, p1, p3, p5);
		Party party2 = new Party(false, p2, p4, p6);
		Player players[] = {p1, p2, p3, p4, p5, p6};
		party1.setEnemyParty(party2);
		party2.setEnemyParty(party1);
		if(mode.equals("Rotate")) {
			if (p != null && t != null) {
				party1.setRotate(p);
				party2.setRotate(t);
			}
		}
		if(mode.equals("Evolution")) {
			party1.evolve();
			party2.evolve();
		}
		checkDynamics(party1);
		checkDynamics(party2);
		System.out.println();
		System.out.print("Team A, enter your team name: ");
		String teamA = input.next();
		party1.setPartyName(teamA);
		System.out.println();
		System.out.print("Team B, enter your team name: ");
		String teamB = input.next();
		party2.setPartyName(teamB);
		System.out.println();
		game = true;
		try {
			String audio = "audio/matchstart.wav";
			Music victoryPlayer = new Music(audio, false);
			victoryPlayer.play();
		}catch (Exception e) {
			System.out.println(e);
		}
		audioPlayer.play();
		try {
			menuPlayer.stop();
		}catch (Exception e) {
			System.out.println(e);
		}
		createController();
		while(game) {
			if(turns % 2 == 0) {
				turns2++;
			}
			if(turns2 >= 15) {
				tD = bold + color + 17 + "m" + String.valueOf(turns2) + reset;
			}else if(turns2 >= 10) {
				tD = bold + color + 196 + "m" + String.valueOf(turns2) + reset;
			}else if(turns2 >= 5) {
				tD = bold + color + 214 + "m" + String.valueOf(turns2) + reset;
			}else if (turns2 >= 1) {
				tD = bold +  color + 46 + "m" + String.valueOf(turns2) + reset;
			}else {
				tD = bold +  color + 46 + "m" + String.valueOf(turns2) + reset;
			}			
			while(party1.getTurn()) {
				if(party1.teamDown()) {
					break;
				}
				for(int j = 0; j < GameSim.utility.size(); j++) {
					if(GameSim.utility.get(j).getName().equals("Support") && (GameSim.utility.get(j).isEnemy(p1) || GameSim.utility.get(j).isEnemy(p3) || GameSim.utility.get(j).isEnemy(p5))) {
						GameSim.cover.clear();
						break;
					}
				}
				if (p2.getEMP() > 0 || p4.getEMP() > 0 || p6.getEMP() > 0) {
					p1.setSupress(true);
					p3.setSupress(true);
					p5.setSupress(true);
				}
				if(party1.oneLeft() && play) {
					try {
						audioPlayer.stop();
						String audio = "audio/lastplayertheme2.wav";
						audioPlayer = new Music(audio, false);
						audioPlayer.play();
						String audio2 = "audio/lastplayer.wav";
						Music audioPlayer = new Music(audio2, false);
						audioPlayer.play();
						play = false;
					}catch (Exception e) {
						System.out.println(e);
					}
				}
				if((p1.isStunned() || !p1.isAlive()) && (p3.isStunned() || !p3.isAlive()) && (p5.isStunned() || !p5.isAlive())) {
					party1.passTurn(party2);
					party1.reduceTeamEffects();
					party1.reduceTeamCooldowns();
					turns++;
					SpawnOrbs();
					SpawnCover();
					b.checkSpace();
				}
				while(p1.getTurn() && !p1.isStunned()) {
					//p1.image().open();
					System.out.println(party1.getPartyName() + "'s Turn: " + tD);
					//System.out.println(party1.getPartyName() + "'s Turn (Go " + p1.getSkin() + "!)");

					System.out.println(p1);
					System.out.print("What would " + p1.getSkin() + " like to do: ");
					String response = input.next();
					System.out.println();
					if(response.equals("b")){
						b.printField(p1, p2, p3, p4, p5, p6, orbs, cover, p1, p2, p4, p6, utility);
					}
					if(response.equals("s")) {
						party1.showRoster();
						System.out.print("Who do you want to switch to: ");
						String switchResponse = input.next();
						if(switchResponse.equals("1")) {
							p1.passTurn(p1);
							System.out.println();
						}
						if(switchResponse.equals("2")) {
							p1.passTurn(p3);
							if (p3.isAlive() && !p3.isStunned()) {
								b.printField(p1, p2, p3, p4, p5, p6, orbs, cover, p3, p2, p4, p6, utility);
							}
							//p1.image().close();
							System.out.println();
						}
						if(switchResponse.equals("3")) {
							p1.passTurn(p5);
							if (p5.isAlive() && !p5.isStunned()) {
								b.printField(p1, p2, p3, p4, p5, p6, orbs, cover, p5, p2, p4, p6 , utility);
							}
							//p1.image().close();
							System.out.println();
						}
					}
					if (response.equals("t")) {
						party1.showTeam();
					}
					if (response.equals("e")) {
						p1.enable();
						if (p1.getName().equals("Everest")) {
							EverestPassive(p1, p3, p5);
						}
						if (p1.getName().equals("Rin")) {
							RinPassive(p1, p2, p4, p6, p3, p5);
						}
						if (p1.getName().equals("Jazz")) {
							JazzPassive(p1);
						}
						if (p1.getName().equals("Jade")) {
							JadePassive(p1, p2, p4, p6);
						}
						ReviveDeny(p1, p3, p5);
					}
					if (response.equals("e2")) {
						if (p1.getName().equals("Stellar")) {
							StellarPassive(p1);
						}
					}
					if (response.equals("rotate")) {
						party1.rotate();
						p1 = party1.first();
						p3 = party1.second();
						p5 = party1.third();
					}
					if (response.equals("l")) {
						b.setLight();
					}
					if(response.equals("f")) {
						System.out.print("Are you sure you want to forfeit this game: ");
						String switchResponse = input.next();
						if(switchResponse.equals("forfeit")) {
							System.out.println();
							System.out.println("Team B Wins!");
							System.out.println();
							party1.passTurn(party2);
							game = false;
						}
					}
					if(response.equals("p")) {
						party1.showTeam();
						System.out.print("Are you sure you want to pass to the enemy's turn: ");
						String switchResponse = input.next();
						if(switchResponse.equals("p")) {
							b.checkSpace();
							if(party2.teamDown()) {
								System.out.println();
								System.out.println("Team A Wins!");
								party1.passTurn(party2);
								game = false;
								//p1.image().close();
							}else {
								party1.passTurn(party2);
								party1.reduceTeamEffects();
								party1.reduceTeamCooldowns();
								turns++;
								SpawnOrbs();
								SpawnCover();
								b.checkTiles();
								//p1.image().close();
							}
						}
					}
					if(response.equals("h")) {
						try {
							audioPlayer.stop();
						} catch (UnsupportedAudioFileException e) {
						} catch (IOException e) {
						} catch (LineUnavailableException e) {
						}
					}
					if(response.equals("k")) {
						try {
							audioPlayer.resumeAudio();
						} catch (UnsupportedAudioFileException e) {
						} catch (IOException e) {
						} catch (LineUnavailableException e) {
						}
					}
					if(response.equals("vr")) {
						ViewRange(p1, p2, p4, p6, p3, p5, 0);
					}
					if(response.equals("i")) {
						party2.showTeam();
					}
					if(response.equals("m")) {
						Movement(p1, p2, p4, p6, p3, p5);
					}
					if(response.equals("o")) {
						ShowOrbs(p1);
					}
					if(response.equals("c")) {
						ShowCover(p1);
					}
					if(response.equals("r")) {
						Dash(p1, p2, p4, p6);
					}
					if(response.equals("j")) {
						Location l = SetCursor(p1, p2, p4, p6, p3, p5, p1.getRange());
						Jump(p1, p3, p5, l, p2, p4, p6);
					}
					if(response.equals("dy")) {
						p1.useDynamic();
					}
					if(response.equals("u")) {
						if(!p1.ultReady() || p1.ultActive()) {
							System.out.println("My ultimate cannot be used!");
							System.out.println();
						}else {
							ultimateFX();
							runUltimates(p1, p2, p4, p6, p3, p5, party1, party2);
						}
					}
					if(response.equals("a") && (!p1.onCooldown() || (p1.getName().equals("Makani") && p1.getWind()))) {
						if(p1.isDazed()) {
							System.out.println("Cannot use ability when dazed!");
							System.out.println();
						}else {
							abilityFX();
							runAbilities(p1, p2, p4, p6, p3, p5);
						}
					}
					if(response.equals("w")) {
						if(p1.isFreezed()) {
							System.out.println("Cannot use weapon while freezed!");
							System.out.println();
						}else {
							if(p1.hasAttacked()) {
								System.out.println(p1.getSkin() + " has already attacked this turn!");
								System.out.println();
							}else {
								if(p1.getName().equals("Stellar")) {
									StellarAttack(p1, p2, p4 ,p6);
									weaponFX();
								}else if(p1.getName().equals("Victor")) {
									VictorAttack(p1, p2, p4 ,p6);
									weaponFX();
								}else if(p1.getName().equals("Deny")) {
									Location l = SetCursor(p1, p2, p4, p6, p3, p5, 4);
									DenyAttack(p1, p2, p4 ,p6, l);
									weaponFX();
								}else if(p1.getName().equals("Everest")) {
									EverestAttack(p1, p2, p4 ,p6);
									weaponFX();
								}else if(p1.getName().equals("Millie")) {
									MillieAttack(p1, p2, p4 ,p6);
									weaponFX();
								}else if (p1.getName().equals("Yuri")) {
									Location l = SetCursor(p1, p2, p4, p6, p3, p5, 15);
									YuriAttack(p1, p2, p4 ,p6, p3, p5, l);
								}else if (p1.getName().equals("Flor")) {
									FlorAttack(p1, p2, p4, p6);
									weaponFX();
								}else if (p1.getName().equals("Ivy")) {
									IvyAttack(p1, p2, p4 ,p6, p3, p5);
									weaponFX();
								}else if (p1.getName().equals("Bladee")) {
									BladeeAttack(p1, p2, p4 ,p6);
									weaponFX();
								}else if (p1.getName().equals("Makani")) {
									MakaniAttack(p1, p2, p4 ,p6);
									weaponFX();
								}else if(p1.getName().equals("Angelos")) {
									AngelosAttack(p1, p2, p4, p6);
									weaponFX();
								}else if(p1.getName().equals("Jesse")) {
									JesseAttack(p1, p2, p4, p6);
									weaponFX();
								}else if(p1.getName().equals("Audrey")) {
									AudreyAttack(p1, p2, p4, p6);
									weaponFX();
								}else if(p1.getName().equals("Bedrock")) {
									BedrockAttack(p1, p2, p4 ,p6);
									weaponFX();
								}else if(p1.getName().equals("Max")) {
									MaxAttack(p1, p2, p4 ,p6);
									weaponFX();
								}else if(p1.getName().equals("Cherry")) {
									CherryAttack(p1, p2, p4, p6, p3, p5);
									weaponFX();
								}else {
									party2.showRoster(p1);
									System.out.print("Who do you want to attack: ");
									String attackResponse = input.next();
									if(attackResponse.equals("1")) {
										if(!p1.inRange(p2)) {
											System.out.println();
											System.out.println("Target is out of range!");
											System.out.println();
										}else {
											weaponFX();
											runAttacks(p1, p2, p4, p6, p3, p5);
										}
									}
									if(attackResponse.equals("2")) {
										if(!p1.inRange(p4)) {
											System.out.println();
											System.out.println("Target is out of range!");
											System.out.println();
										}else {
											weaponFX();
											runAttacks(p1, p4, p2, p6, p3, p5);
										}
									}
									if(attackResponse.equals("3")) {
										if(!p1.inRange(p6)) {
											System.out.println();
											System.out.println("Target is out of range!");
											System.out.println();
										}else {
											weaponFX();
											runAttacks(p1, p6, p4, p2, p3, p5);
										}
									}
								}
							}
						}
					}
					if(!p1.isAlive()) {
						p1.endTurn();
						if(party1.teamDown()) {
							System.out.println("Team B Wins!");
							game = false;
							//p1.image().close();
						}else {
							party1.nextPlayer();
						}
					}
				}
				
				while(p3.getTurn()) {
					System.out.println(party1.getPartyName() + "'s Turn: " + tD);
					System.out.println(p3);
					System.out.print("What would " + p3.getSkin() + " like to do: ");
					String response = input.next();
					System.out.println();
					if(response.equals("b")){
						b.printField(p1, p2, p3, p4, p5, p6, orbs, cover, p3, p2, p4, p6, utility);
					}
					if(response.equals("s")) {
						party1.showRoster();
						System.out.print("Who do you want to switch to: ");
						String switchResponse = input.next();
						if(switchResponse.equals("1")) {
							p3.passTurn(p1);
							if (p1.isAlive() && !p1.isStunned()) {
								b.printField(p1, p2, p3, p4, p5, p6, orbs, cover, p1, p2, p4, p6, utility);
							}
							System.out.println();
						}
						if(switchResponse.equals("2")) {
							p3.passTurn(p3);
							System.out.println();
						}
						if(switchResponse.equals("3")) {
							p3.passTurn(p5);
							if (p5.isAlive() && !p5.isStunned()) {
								b.printField(p1, p2, p3, p4, p5, p6, orbs, cover, p5, p2, p4, p6, utility);
							}
							System.out.println();
						}
					}
					if (response.equals("t")) {
						party1.showTeam();
					}
					if (response.equals("e")) {
						p3.enable();
						if (p3.getName().equals("Everest")) {
							EverestPassive(p3, p1, p5);
						}
						if (p3.getName().equals("Rin")) {
							RinPassive(p3, p2, p4, p6, p1, p5);
						}
						if (p3.getName().equals("Jazz")) {
							JazzPassive(p3);
						}
						if (p3.getName().equals("Jade")) {
							JadePassive(p3, p2, p4, p6);
						}
						ReviveDeny(p3, p1, p5);
					}
					if (response.equals("e2")) {
						if (p3.getName().equals("Stellar")) {
							StellarPassive(p3);
						}
					}
					if (response.equals("rotate")) {
						party1.rotate();
						p1 = party1.first();
						p3 = party1.second();
						p5 = party1.third();
					}
					if (response.equals("l")) {
						b.setLight();
					}
					if(response.equals("f")) {
						System.out.print("Are you sure you want to forfeit this game: ");
						String switchResponse = input.next();
						if(switchResponse.equals("forfeit")) {
							System.out.println();
							System.out.println("Team B Wins!");
							System.out.println();
							party1.passTurn(party2);
							game = false;
						}
					}
					if(response.equals("p")) {
						party1.showTeam();
						System.out.print("Are you sure you want to pass to the enemy's turn: ");
						String switchResponse = input.next();
						if(switchResponse.equals("p")) {
							b.checkSpace();
							if(party2.teamDown()) {
								System.out.println();
								System.out.println("Team A Wins!");
								party1.passTurn(party2);
								game = false;
							}else {
								party1.passTurn(party2);
								party1.reduceTeamEffects();
								party1.reduceTeamCooldowns();
								turns++;
								SpawnOrbs();
								SpawnCover();
								b.checkTiles();
							}
						}
					}
					if(response.equals("vr")) {
						ViewRange(p3, p2, p4, p6, p1, p5, 0);
					}
					if(response.equals("i")) {
						party2.showTeam();
					}
					if(response.equals("o")) {
						ShowOrbs(p3);
					}
					if(response.equals("c")) {
						ShowCover(p3);
					}
					if(response.equals("r")) {
						Dash(p3, p2, p4, p6);
					}
					if(response.equals("j")) {
						Location l = SetCursor(p3, p2, p4, p6, p1, p5, p3.getRange());
						Jump(p3, p1, p5, l, p2, p4, p6);
					}
					if(response.equals("dy")) {
						p3.useDynamic();
					}
					if(response.equals("u")) {
						if(!p3.ultReady() || p3.ultActive()) {
							System.out.println("My ultimate cannot be used!");
							System.out.println();
						}else {
							ultimateFX();
							runUltimates(p3, p2, p4, p6, p1, p5, party1, party2);
						}
					}
					if(response.equals("m")) {
						Movement(p3, p2, p4, p6, p1, p5);
					}
					if(response.equals("a") && (!p3.onCooldown() || (p3.getName().equals("Makani") && p3.getWind()))) {
						if(p3.isDazed()) {
							System.out.println("Cannot use ability when dazed!");
							System.out.println();
						}else {
							abilityFX();
							runAbilities(p3, p2, p4, p6, p1, p5);
						}
					}
					if(response.equals("w")) {
						if(p3.isFreezed()) {
							System.out.println("Cannot use weapon while freezed!");
							System.out.println();
						}else {
							if(p3.hasAttacked()) {
								System.out.println(p3.getSkin() + " has already attacked this turn!");
								System.out.println();
							}else {
								if(p3.getName().equals("Stellar")) {
									StellarAttack(p3, p2, p4 ,p6);
									weaponFX();
								}else if(p3.getName().equals("Victor")) {
									VictorAttack(p3, p2, p4 ,p6);
									weaponFX();
								}else if(p3.getName().equals("Deny")) {
									Location l = SetCursor(p3, p2, p4, p6, p1, p5, 4);
									DenyAttack(p3, p2, p4 ,p6, l);
									weaponFX();
								}else if(p3.getName().equals("Everest")) {
									EverestAttack(p3, p2, p4 ,p6);
									weaponFX();
								}else if(p3.getName().equals("Millie")) {
									MillieAttack(p3, p2, p4 ,p6);
									weaponFX();
								}else if (p3.getName().equals("Yuri")) {
									Location l = SetCursor(p3, p2, p4, p6, p1, p5, 15);
									YuriAttack(p3, p2, p4 ,p6, p1, p5, l);
								}else if (p3.getName().equals("Flor")) {
									FlorAttack(p3, p2, p4, p6);
									weaponFX();
								}else if (p3.getName().equals("Ivy")) {
									IvyAttack(p3, p2, p4 ,p6, p1, p5);
									weaponFX();
								}else if (p3.getName().equals("Bladee")) {
									BladeeAttack(p3, p2, p4 ,p6);
									weaponFX();
								}else if (p3.getName().equals("Makani")) {
									MakaniAttack(p3, p2, p4 ,p6);
									weaponFX();
								}else if(p3.getName().equals("Angelos")) {
									AngelosAttack(p3, p2, p4, p6);
									weaponFX();
								}else if(p3.getName().equals("Jesse")) {
									JesseAttack(p3, p2, p4, p6);
									weaponFX();
								}else if(p3.getName().equals("Audrey")) {
									AudreyAttack(p3, p2, p4, p6);
									weaponFX();
								}else if(p3.getName().equals("Bedrock")) {
									BedrockAttack(p3, p2, p4 ,p6);
									weaponFX();
								}else if(p3.getName().equals("Max")) {
									MaxAttack(p3, p2, p4 ,p6);
									weaponFX();
								}else if(p3.getName().equals("Cherry")) {
									CherryAttack(p3, p2, p4, p6, p1, p5);
									weaponFX();
								}else {
									party2.showRoster(p3);
									System.out.print("Who do you want to attack: ");
									String attackResponse = input.next();
									if(attackResponse.equals("1")) {
										if(!p3.inRange(p2)) {
											System.out.println();
											System.out.println("Target is out of range!");
											System.out.println();
										}else {
											weaponFX();
											runAttacks(p3, p2, p4, p6, p1, p5);
										}
									}
									if(attackResponse.equals("2")) {
										if(!p3.inRange(p4)) {
											System.out.println();
											System.out.println("Target is out of range!");
											System.out.println();
										}else {
											weaponFX();
											runAttacks(p3, p4, p2, p6, p1, p5);
										}
									}
									if(attackResponse.equals("3")) {
										if(!p3.inRange(p6)) {
											System.out.println();
											System.out.println("Target is out of range!");
											System.out.println();
										}else {
											weaponFX();
											runAttacks(p3, p6, p4, p2, p1, p5);
										}
									}
								}
							}
						}
					}
					if(!p3.isAlive()) {
						p3.endTurn();
						if(party1.teamDown()) {
							System.out.println("Team B Wins!");
							game = false;
							//p1.image().close();
						}else {
							party1.nextPlayer();
						}
					}
				}
				
				while(p5.getTurn()) {
					System.out.println(party1.getPartyName() + "'s Turn: " + tD);
					System.out.println(p5);
					System.out.print("What would " + p5.getSkin() + " like to do: ");
					String response = input.next();
					System.out.println();
					if(response.equals("b")){
						b.printField(p1, p2, p3, p4, p5, p6, orbs, cover, p5, p2, p4, p6, utility);
					}
					if(response.equals("s")) {
						party1.showRoster();
						System.out.print("Who do you want to switch to: ");
						String switchResponse = input.next();
						if(switchResponse.equals("1")) {
							p5.passTurn(p1);
							if (p1.isAlive() && !p1.isStunned()) {
								b.printField(p1, p2, p3, p4, p5, p6, orbs, cover, p1, p2, p4, p6, utility);
							}
							System.out.println();
						}
						if(switchResponse.equals("2")) {
							p5.passTurn(p3);
							if (p3.isAlive() && !p3.isStunned()) {
								b.printField(p1, p2, p3, p4, p5, p6, orbs, cover, p3, p2, p4, p6, utility);
							}
							System.out.println();
						}
						if(switchResponse.equals("3")) {
							p5.passTurn(p5);
							System.out.println();
						}
					}
					if (response.equals("t")) {
						party1.showTeam();
					}
					if (response.equals("e")) {
						p5.enable();
						if (p5.getName().equals("Everest")) {
							EverestPassive(p5, p3, p1);
						}
						if (p5.getName().equals("Rin")) {
							RinPassive(p5, p2, p4, p6, p3, p1);
						}
						if (p5.getName().equals("Jazz")) {
							JazzPassive(p5);
						}
						if (p5.getName().equals("Jade")) {
							JadePassive(p5, p2, p4, p6);
						}
						ReviveDeny(p5, p3, p1);
					}
					if (response.equals("e2")) {
						if (p5.getName().equals("Stellar")) {
							StellarPassive(p5);
						}
					}
					if (response.equals("rotate")) {
						party1.rotate();
						p1 = party1.first();
						p3 = party1.second();
						p5 = party1.third();
					}
					if (response.equals("l")) {
						b.setLight();
					}
					if(response.equals("f")) {
						System.out.print("Are you sure you want to forfeit this game: ");
						String switchResponse = input.next();
						if(switchResponse.equals("forfeit")) {
							System.out.println();
							System.out.println("Team B Wins!");
							System.out.println();
							party1.passTurn(party2);
							game = false;
						}
					}
					if(response.equals("p")) {
						party1.showTeam();
						System.out.print("Are you sure you want to pass to the enemy's turn: ");
						String switchResponse = input.next();
						if(switchResponse.equals("p")) {
							b.checkSpace();
							if(party2.teamDown()) {
								System.out.println();
								System.out.println("Team A Wins!");
								party1.passTurn(party2);
								game = false;
							}else {
								party1.passTurn(party2);
								party1.reduceTeamEffects();
								party1.reduceTeamCooldowns();
								turns++;
								SpawnOrbs();
								SpawnCover();
								b.checkTiles();
							}
						}
					}
					if(response.equals("vr")) {
						ViewRange(p5, p2, p4, p6, p3, p1, 0);
					}
					if(response.equals("i")) {
						party1.showTeam();
					}
					if(response.equals("m")) {
						Movement(p5, p2, p4, p6, p1, p3);
					}
					if(response.equals("o")) {
						ShowOrbs(p5);
					}
					if(response.equals("c")) {
						ShowCover(p5);
					}
					if(response.equals("r")) {
						Dash(p5, p2, p4, p6);
					}
					if(response.equals("j")) {
						Location l = SetCursor(p5, p2, p4, p6, p3, p1, p5.getRange());
						Jump(p5, p3, p1, l, p2, p4, p6);
					}
					if(response.equals("dy")) {
						p5.useDynamic();
					}
					if(response.equals("u")) {
						if(!p5.ultReady() || p5.ultActive()) {
							System.out.println("My ultimate cannot be used!");
							System.out.println();
						}else {
							ultimateFX();
							runUltimates(p5, p2, p4, p6, p3, p1, party1, party2);
						}
					}
					if(response.equals("a") && (!p5.onCooldown() || (p5.getName().equals("Makani") && p5.getWind()))) {
						if(p5.isDazed()) {
							System.out.println("Cannot use ability when dazed!");
							System.out.println();
						}else {
							abilityFX();
							runAbilities(p5, p2, p4, p6, p1, p3);
						}
					}
					if(response.equals("w")) {
						if(p5.isFreezed()) {
							System.out.println("Cannot use weapon while freezed!");
							System.out.println();
						}else {
							if(p5.hasAttacked()) {
								System.out.println(p5.getSkin() + " has already attacked this turn!");
								System.out.println();
							}else {
								if(p5.getName().equals("Stellar")) {
									StellarAttack(p5, p2, p4 ,p6);
									weaponFX();
								}else if(p5.getName().equals("Victor")) {
									VictorAttack(p5, p2, p4 ,p6);
									weaponFX();
								}else if(p5.getName().equals("Deny")) {
									Location l = SetCursor(p5, p2, p4, p6, p3, p1, 4);
									DenyAttack(p5, p2, p4 ,p6, l);
									weaponFX();
								}else if(p5.getName().equals("Everest")) {
									EverestAttack(p5, p2, p4 ,p6);
									weaponFX();
								}else if(p5.getName().equals("Millie")) {
									MillieAttack(p5, p2, p4 ,p6);
									weaponFX();
								}else if (p5.getName().equals("Yuri")) {
									Location l = SetCursor(p5, p2, p4, p6, p3, p1, 15);
									YuriAttack(p5, p2, p4 ,p6, p3, p1, l);
								}else if (p5.getName().equals("Flor")) {
									FlorAttack(p5, p2, p4, p6);
									weaponFX();
								}else if (p5.getName().equals("Ivy")) {
									IvyAttack(p5, p2, p4 ,p6, p3, p1);
									weaponFX();
								}else if (p5.getName().equals("Bladee")) {
									BladeeAttack(p5, p2, p4 ,p6);
									weaponFX();
								}else if (p5.getName().equals("Makani")) {
									MakaniAttack(p5, p2, p4 ,p6);
									weaponFX();
								}else if(p5.getName().equals("Angelos")) {
									AngelosAttack(p5, p2, p4, p6);
									weaponFX();
								}else if(p5.getName().equals("Jesse")) {
									JesseAttack(p5, p2, p4, p6);
									weaponFX();
								}else if(p5.getName().equals("Audrey")) {
									AudreyAttack(p5, p2, p4, p6);
									weaponFX();
								}else if(p5.getName().equals("Bedrock")) {
									BedrockAttack(p5, p2, p4 ,p6);
									weaponFX();
								}else if(p5.getName().equals("Max")) {
									MaxAttack(p5, p2, p4 ,p6);
									weaponFX();
								}else if(p5.getName().equals("Cherry")) {
									CherryAttack(p5, p2, p4, p6, p1, p3);
									weaponFX();
								}else {
									party2.showRoster(p5);
									System.out.print("Who do you want to attack: ");
									String attackResponse = input.next();
									if(attackResponse.equals("1")) {
										if(!p5.inRange(p2)) {
											System.out.println();
											System.out.println("Target is out of range!");
											System.out.println();
										}else {
											weaponFX();
											runAttacks(p5, p2, p4, p6, p1, p3);
										}
									}
									if(attackResponse.equals("2")) {
										if(!p5.inRange(p4)) {
											System.out.println();
											System.out.println("Target is out of range!");
											System.out.println();
										}else {
											weaponFX();
											runAttacks(p5, p4, p2, p6, p1, p3);
										}
									}
									if(attackResponse.equals("3")) {
										if(!p5.inRange(p6)) {
											System.out.println();
											System.out.println("Target is out of range!");
											System.out.println();
										}else {
											weaponFX();
											runAttacks(p5, p6, p4, p2, p1, p3);
										}
									}
								}
							}
						}
					}
					if(!p5.isAlive()) {
						p5.endTurn();
						if(party1.teamDown()) {
							System.out.println("Team B Wins!");
							game = false;
							//p1.image().close();
						}else {
							party1.nextPlayer();
						}
					}
				}
			}
			
			if(turns >= 9 && play2) {
				try {
					audioPlayer.stop();
					String audio = "audio/overtimetheme.wav";
					audioPlayer = new Music(audio, false);
					audioPlayer.play();
					String audio2 = "audio/overtimeline.wav";
					Music audioPlayer = new Music(audio2, false);
					audioPlayer.play();
					play2 = false;
				}catch (Exception e) {
					System.out.println(e);
				}
			}
			if(turns >= 19 && play4) {
				try {
					b.endGame();
					audioPlayer.stop();
					String audio = "audio/endgametheme.wav";
					audioPlayer = new Music(audio, false);
					audioPlayer.play();
					String audio2 = "audio/endgameline.wav";
					Music audioPlayer = new Music(audio2, false);
					audioPlayer.play();
					play4 = false;
				}catch (Exception e) {
					System.out.println(e);
				}
			}
			if(turns >= 29 && play5) {
				try {
					b.suddenDeath();
					audioPlayer.stop();
					String audio = "audio/suddendeath.wav";
					audioPlayer = new Music(audio, false);
					audioPlayer.play();
					String audio2 = "audio/suddendeathline.wav";
					Music audioPlayer = new Music(audio2, false);
					audioPlayer.play();
					play5 = false;
				}catch (Exception e) {
					System.out.println(e);
				}
			}
			while(party2.getTurn()) {
				if(party2.teamDown()) {
					break;
				}
				for(int j = 0; j < GameSim.utility.size(); j++) {
					if(GameSim.utility.get(j).getName().equals("Support") && (GameSim.utility.get(j).isEnemy(p2) || GameSim.utility.get(j).isEnemy(p4) || GameSim.utility.get(j).isEnemy(p6))) {
						GameSim.cover.clear();
						break;
					}
				}
				if (p1.getEMP() > 0 || p3.getEMP() > 0 || p5.getEMP() > 0) {
					p2.setSupress(true);
					p4.setSupress(true);
					p6.setSupress(true);
				}
				if(party2.oneLeft() && play && !mode.equals("Boss")) {
					try {
						audioPlayer.stop();
						String audio = "audio/lastplayertheme2.wav";
						audioPlayer = new Music(audio, false);
						audioPlayer.play();
						String audio2 = "audio/lastplayer.wav";
						Music audioPlayer = new Music(audio2, false);
						audioPlayer.play();
						play = false;
					}catch (Exception e) {
						System.out.println(e);
					}
				}
				if((p2.isStunned() || !p2.isAlive()) && (p4.isStunned() || !p4.isAlive()) && (p6.isStunned() || !p6.isAlive())) {
					party2.passTurn(party1);
					party2.reduceTeamEffects();
					party2.reduceTeamCooldowns();
					turns++;
					SpawnOrbs();
					SpawnCover();
					b.checkSpace();
				}
				while(p2.getTurn()) {
					System.out.println(party2.getPartyName() + "'s Turn: " + tD);
					System.out.println(p2);
					System.out.print("What would " + p2.getSkin() + " like to do: ");
					String response = input.next();
					System.out.println();
					if(response.equals("b")){
						b.printField(p1, p2, p3, p4, p5, p6, orbs, cover, p2, p1, p3, p5, utility);
					}
					if(response.equals("s")) {
						party2.showRoster();
						System.out.print("Who do you want to switch to: ");
						String switchResponse = input.next();
						if(switchResponse.equals("1")) {
							p2.passTurn(p2);
							System.out.println();
						}
						if(switchResponse.equals("2")) {
							p2.passTurn(p4);
							if (p4.isAlive() && !p4.isStunned()) {
								b.printField(p1, p2, p3, p4, p5, p6, orbs, cover, p4, p1, p3, p5, utility);
							}
							System.out.println();
						}
						if(switchResponse.equals("3")) {
							p2.passTurn(p6);
							if (p6.isAlive() && !p6.isStunned()) {
								b.printField(p1, p2, p3, p4, p5, p6, orbs, cover, p6, p1, p3, p5, utility);
							}
							System.out.println();
						}
					}
					if (response.equals("t")) {
						party2.showTeam();
					}
					if (response.equals("e")) {
						p2.enable();
						if (p2.getName().equals("Everest")) {
							EverestPassive(p2, p4, p6);
						}
						if (p2.getName().equals("Rin")) {
							RinPassive(p2, p1, p3, p5, p4, p6);
						}
						if (p2.getName().equals("Jazz")) {
							JazzPassive(p2);
						}
						if (p2.getName().equals("Jade")) {
							JadePassive(p2, p1, p3, p5);
						}
						if (p2.getName().equals("Boss:Finley")) {
							FinleyBossPassive(p2, p1, p3, p5, p4, p6);
						}
						ReviveDeny(p2, p4, p6);
					}
					if (response.equals("e2")) {
						if (p2.getName().equals("Stellar")) {
							StellarPassive(p2);
						}
					}
					if (response.equals("rotate")) {
						party2.rotate();
						p2 = party2.first();
						p4 = party2.second();
						p6 = party2.third();
					}
					if (response.equals("l")) {
						b.setLight();
					}
					if(response.equals("f")) {
						System.out.print("Are you sure you want to forfeit this game: ");
						String switchResponse = input.next();
						if(switchResponse.equals("forfeit")) {
							System.out.println();
							System.out.println("Team A Wins!");
							System.out.println();
							party2.passTurn(party1);
							game = false;
						}
					}
					if(response.equals("p")) {
						party2.showTeam();
						System.out.print("Are you sure you want to pass to the enemy's turn: ");
						String switchResponse = input.next();
						if(switchResponse.equals("p")) {
							b.checkSpace();
							if(party1.teamDown()) {
								System.out.println();
								System.out.println("Team B Wins!");
								party2.passTurn(party1);
								game = false;
							}else {
								party2.passTurn(party1);
								party2.reduceTeamEffects();
								party2.reduceTeamCooldowns();
								turns++;
								SpawnOrbs();
								SpawnCover();
								b.checkTiles();
							}
						}
					}
					if(response.equals("vr")) {
						ViewRange(p2, p1, p3, p5, p4, p6, 0);
					}
					if(response.equals("i")) {
						party1.showTeam();
					}
					if(response.equals("m")) {
						Movement(p2, p1, p3, p5, p4, p6);
					}
					if(response.equals("o")) {
						ShowOrbs(p2);
					}
					if(response.equals("c")) {
						ShowCover(p2);
					}
					if(response.equals("r")) {
						Dash(p2, p1, p3, p5);
					}
					if(response.equals("j")) {
						Location l = SetCursor(p2, p1, p3, p5, p4, p6, p2.getRange());
						Jump(p2, p4, p6, l, p1, p3, p5);
					}
					if(response.equals("dy")) {
						p2.useDynamic();
					}
					if(response.equals("u")) {
						if(!p2.ultReady() || p2.ultActive()) {
							System.out.println("My ultimate cannot be used!");
							System.out.println();
						}else {
							ultimateFX();
							runUltimates(p2, p1, p3, p5, p4, p6, party2, party1);
						}
					}
					if(response.equals("a") && (!p2.onCooldown() || (p2.getName().equals("Makani") && p2.getWind()))) {
						if(p2.isDazed()) {
							System.out.println("Cannot use ability when dazed!");
							System.out.println();
						}else {
							abilityFX();
							runAbilities(p2, p1, p3, p5, p4, p6);
						}
					}
					if(response.equals("w")) {
						if(p2.isFreezed()) {
							System.out.println("Cannot use weapon while freezed!");
							System.out.println();
						}else {
							if(p2.hasAttacked()) {
								System.out.println(p2.getSkin() + " has already attacked this turn!");
								System.out.println();
							}else {
								if(p2.getName().equals("Stellar")) {
									StellarAttack(p2, p1, p3 ,p5);
									weaponFX();
								}else if(p2.getName().equals("Victor")) {
									VictorAttack(p2, p1, p3 ,p5);
									weaponFX();
								}else if(p2.getName().equals("Deny")) {
									Location l = SetCursor(p2, p1, p3, p5, p4, p6, 4);
									DenyAttack(p2, p1, p3 ,p5, l);
									weaponFX();
								}else if(p2.getName().equals("Everest")) {
									EverestAttack(p2, p1, p3 ,p5);
									weaponFX();
								}else if(p2.getName().equals("Millie")) {
									MillieAttack(p2, p1, p3 ,p5);
									weaponFX();
								}else if (p2.getName().equals("Yuri")) {
									Location l = SetCursor(p2, p1, p3, p5, p4, p6, 15);
									YuriAttack(p2, p1, p3 ,p5, p4, p6, l);
								}else if (p2.getName().equals("Flor")) {
									FlorAttack(p2, p1, p3, p5);
									weaponFX();
								}else if (p2.getName().equals("Ivy")) {
									IvyAttack(p2, p1, p3 ,p5, p4, p6);
									weaponFX();
								}else if (p2.getName().equals("Bladee")) {
									BladeeAttack(p2, p1, p3 ,p5);
									weaponFX();
								}else if (p2.getName().equals("Makani")) {
									MakaniAttack(p2, p1, p3 ,p5);
									weaponFX();
								}else if(p2.getName().equals("Angelos")) {
									AngelosAttack(p2, p1, p3, p5);
									weaponFX();
								}else if(p2.getName().equals("Jesse")) {
									JesseAttack(p2, p1, p3, p5);
									weaponFX();
								}else if(p2.getName().equals("Audrey")) {
									AudreyAttack(p2, p1, p3, p5);
									weaponFX();
								}else if(p2.getName().equals("Bedrock")) {
									BedrockAttack(p2, p1, p3 ,p5);
									weaponFX();
								}else if(p2.getName().equals("Max")) {
									MaxAttack(p2, p1, p3 ,p5);
									weaponFX();
								}else if(p2.getName().equals("Cherry")) {
									CherryAttack(p2, p1, p3, p5, p4, p6);
									weaponFX();
								}else {
									party1.showRoster(p2);
									System.out.print("Who do you want to attack: ");
									String attackResponse = input.next();
									if(attackResponse.equals("1")) {
										if(!p2.inRange(p1)) {
											System.out.println();
											System.out.println("Target is out of range!");
											System.out.println();
										}else {
											weaponFX();
											runAttacks(p2, p1, p3, p5, p4, p6);
										}
									}
									if(attackResponse.equals("2")) {
										if(!p2.inRange(p3)) {
											System.out.println();
											System.out.println("Target is out of range!");
											System.out.println();
										}else {
											weaponFX();
											runAttacks(p2, p3, p1, p5, p4, p6);
										}
									}
									if(attackResponse.equals("3")) {
										if(!p2.inRange(p5)) {
											System.out.println();
											System.out.println("Target is out of range!");
											System.out.println();
										}else {
											weaponFX();
											runAttacks(p2, p5, p3, p1, p4, p6);
										}
									}
								}
							}
						}
					}
					if(!p2.isAlive()) {
						p2.endTurn();
						if(party2.teamDown()) {
							System.out.println("Team A Wins!");
							game = false;
							//p1.image().close();
						}else {
							party2.nextPlayer();
						}
					}
				}
				
				while(p4.getTurn()) {
					System.out.println(party2.getPartyName() + "'s Turn: " + tD);
					System.out.println(p4);
					System.out.print("What would " + p4.getSkin() + " like to do: ");
					String response = input.next();
					System.out.println();
					if(response.equals("b")){
						b.printField(p1, p2, p3, p4, p5, p6, orbs, cover, p4, p1, p3, p5, utility);
					}
					if(response.equals("s")) {
						party2.showRoster();
						System.out.print("Who do you want to switch to: ");
						String switchResponse = input.next();
						if(switchResponse.equals("1")) {
							p4.passTurn(p2);
							if (p2.isAlive() && !p2.isStunned()) {
								b.printField(p1, p2, p3, p4, p5, p6, orbs, cover, p2, p1, p3, p5, utility);
							}
							System.out.println();
						}
						if(switchResponse.equals("2")) {
							p4.passTurn(p4);
							System.out.println();
						}
						if(switchResponse.equals("3")) {
							p4.passTurn(p6);
							if (p6.isAlive() && !p6.isStunned()) {
								b.printField(p1, p2, p3, p4, p5, p6, orbs, cover, p6, p1, p3, p5, utility);
							}
							System.out.println();
						}
					}
					if (response.equals("t")) {
						party2.showTeam();
					}
					if (response.equals("e")) {
						p4.enable();
						if (p4.getName().equals("Everest")) {
							EverestPassive(p4, p2, p6);
						}
						if (p4.getName().equals("Rin")) {
							RinPassive(p4, p1, p3, p5, p2, p6);
						}
						if (p4.getName().equals("Jazz")) {
							JazzPassive(p4);
						}
						if (p4.getName().equals("Jade")) {
							JadePassive(p4, p1, p3, p5);
						}
						ReviveDeny(p4, p2, p6);
					}
					if (response.equals("e2")) {
						if (p4.getName().equals("Stellar")) {
							StellarPassive(p4);
						}
					}
					if (response.equals("rotate")) {
						party2.rotate();
						p2 = party2.first();
						p4 = party2.second();
						p6 = party2.third();
					}
					if (response.equals("l")) {
						b.setLight();
					}
					if(response.equals("f")) {
						System.out.print("Are you sure you want to forfeit this game: ");
						String switchResponse = input.next();
						if(switchResponse.equals("forfeit")) {
							System.out.println();
							System.out.println("Team A Wins!");
							System.out.println();
							party2.passTurn(party1);
							game = false;
						}
					}
					if(response.equals("p")) {
						party2.showTeam();
						System.out.print("Are you sure you want to pass to the enemy's turn: ");
						String switchResponse = input.next();
						if(switchResponse.equals("p")) {
							b.checkSpace();
							if(party1.teamDown()) {
								System.out.println();
								System.out.println("Team B Wins!");
								party2.passTurn(party1);
								game = false;
							}else {
								party2.passTurn(party1);
								party2.reduceTeamEffects();
								party2.reduceTeamCooldowns();
								turns++;
								SpawnOrbs();
								SpawnCover();
								b.checkTiles();
							}
						}
					}
					if(response.equals("vr")) {
						ViewRange(p4, p1, p3, p5, p2, p6, 0);
					}
					if(response.equals("i")) {
						party1.showTeam();
					}
					if(response.equals("m")) {
						Movement(p4, p1, p3, p5, p2, p6);
					}
					if(response.equals("o")) {
						ShowOrbs(p4);
					}
					if(response.equals("c")) {
						ShowCover(p4);
					}
					if(response.equals("r")) {
						Dash(p4, p1, p3, p5);
					}
					if(response.equals("j")) {
						Location l = SetCursor(p4, p1, p3, p5, p2, p6, p4.getRange());
						Jump(p4, p2, p6, l, p1, p3, p5);
					}
					if(response.equals("dy")) {
						p4.useDynamic();
					}
					if(response.equals("u")) {
						if(!p4.ultReady() || p4.ultActive()) {
							System.out.println("My ultimate cannot be used!");
							System.out.println();
						}else {
							ultimateFX();
							runUltimates(p4, p1, p3, p5, p2, p6, party2, party1);
						}
					}
					if(response.equals("a") && (!p4.onCooldown() || (p4.getName().equals("Makani") && p4.getWind()))) {
						if(p4.isDazed()) {
							System.out.println("Cannot use ability when dazed!");
							System.out.println();
						}else {
							abilityFX();
							runAbilities(p4, p1, p3, p5, p2, p6);
						}
					}
					if(response.equals("w")) {
						if(p4.isFreezed()) {
							System.out.println("Cannot use weapon while freezed!");
							System.out.println();
						}else {
							if(p4.hasAttacked()) {
								System.out.println(p4.getSkin() + " has already attacked this turn!");
								System.out.println();
							}else {
								if(p4.getName().equals("Stellar")) {
									StellarAttack(p4, p1, p3 ,p5);
									weaponFX();
								}else if(p4.getName().equals("Victor")) {
									VictorAttack(p4, p1, p3 ,p5);
									weaponFX();
								}else if(p4.getName().equals("Deny")) {
									Location l = SetCursor(p4, p1, p3, p5, p2, p6, 4);
									DenyAttack(p4, p1, p3 ,p5, l);
									weaponFX();
								}else if(p4.getName().equals("Everest")) {
									EverestAttack(p4, p1, p3 ,p5);
									weaponFX();
								}else if(p4.getName().equals("Millie")) {
									MillieAttack(p4, p1, p3 ,p5);
									weaponFX();
								}else if (p4.getName().equals("Yuri")) {
									Location l = SetCursor(p4, p1, p3, p5, p2, p6, 15);
									YuriAttack(p4, p1, p3 ,p5, p2, p6, l);
								}else if (p4.getName().equals("Flor")) {
									FlorAttack(p4, p1, p3, p5);
									weaponFX();
								}else if (p4.getName().equals("Ivy")) {
									IvyAttack(p4, p1, p3 ,p5, p2, p6);
									weaponFX();
								}else if (p4.getName().equals("Bladee")) {
									BladeeAttack(p4, p1, p3 ,p5);
									weaponFX();
								}else if (p4.getName().equals("Makani")) {
									MakaniAttack(p4, p1, p3 ,p5);
									weaponFX();
								}else if(p4.getName().equals("Angelos")) {
									AngelosAttack(p4, p1, p3, p5);
									weaponFX();
								}else if(p4.getName().equals("Jesse")) {
									JesseAttack(p4, p1, p3, p5);
									weaponFX();
								}else if(p4.getName().equals("Audrey")) {
									AudreyAttack(p4, p1, p3, p5);
									weaponFX();
								}else if(p4.getName().equals("Bedrock")) {
									BedrockAttack(p4, p1, p3 ,p5);
									weaponFX();
								}else if(p4.getName().equals("Max")) {
									MaxAttack(p4, p1, p3 ,p5);
									weaponFX();
								}else if(p4.getName().equals("Cherry")) {
									CherryAttack(p4, p1, p3, p5, p2, p6);
									weaponFX();
								}else {
									party1.showRoster(p4);
									System.out.print("Who do you want to attack: ");
									String attackResponse = input.next();
									if(attackResponse.equals("1")) {
										if(!p4.inRange(p1)) {
											System.out.println();
											System.out.println("Target is out of range!");
											System.out.println();
										}else {
											weaponFX();
											runAttacks(p4, p1, p3, p5, p2, p6);
										}
									}
									if(attackResponse.equals("2")) {
										if(!p4.inRange(p3)) {
											System.out.println();
											System.out.println("Target is out of range!");
											System.out.println();
										}else {
											weaponFX();
											runAttacks(p4, p3, p1, p5, p2, p6);
										}
									}
									if(attackResponse.equals("3")) {
										if(!p4.inRange(p5)) {
											System.out.println();
											System.out.println("Target is out of range!");
											System.out.println();
										}else {
											weaponFX();
											runAttacks(p4, p5, p3, p1, p2, p6);
										}
									}
								}
							}
						}
					}
					if(!p4.isAlive()) {
						p4.endTurn();
						if(party2.teamDown()) {
							System.out.println("Team A Wins!");
							game = false;
							//p1.image().close();
						}else {
							party2.nextPlayer();
						}
					}
				}
				
				while(p6.getTurn()) {
					System.out.println(party2.getPartyName() + "'s Turn: " + tD);
					System.out.println(p6);
					System.out.print("What would " + p6.getSkin() + " like to do: ");
					String response = input.next();
					System.out.println();
					if(response.equals("b")){
						b.printField(p1, p2, p3, p4, p5, p6, orbs, cover, p6, p1, p3, p5, utility);
					}
					if(response.equals("s")) {
						party2.showRoster();
						System.out.print("Who do you want to switch to: ");
						String switchResponse = input.next();
						if(switchResponse.equals("1")) {
							p6.passTurn(p2);
							if (p2.isAlive() && !p2.isStunned()) {
								b.printField(p1, p2, p3, p4, p5, p6, orbs, cover, p2, p1, p3, p5, utility);
							}
							System.out.println();
						}
						if(switchResponse.equals("2")) {
							p6.passTurn(p4);
							if (p4.isAlive() && !p4.isStunned()) {
								b.printField(p1, p2, p3, p4, p5, p6, orbs, cover, p4, p1, p3, p5, utility);
							}
							System.out.println();
						}
						if(switchResponse.equals("3")) {
							p6.passTurn(p6);
							System.out.println();
						}
					}
					if (response.equals("t")) {
						party2.showTeam();
					}
					if (response.equals("e")) {
						p6.enable();
						if (p6.getName().equals("Everest")) {
							EverestPassive(p6, p4, p2);
						}
						if (p6.getName().equals("Rin")) {
							RinPassive(p6, p1, p3, p5, p4, p2);
						}
						if (p6.getName().equals("Jazz")) {
							JazzPassive(p6);
						}
						if (p6.getName().equals("Jade")) {
							JadePassive(p6, p1, p3, p5);
						}
						ReviveDeny(p6, p2, p4);
					}
					if (response.equals("e2")) {
						if (p6.getName().equals("Stellar")) {
							StellarPassive(p6);
						}
					}
					if (response.equals("rotate")) {
						party2.rotate();
						p2 = party2.first();
						p4 = party2.second();
						p6 = party2.third();
					}
					if (response.equals("l")) {
						b.setLight();
					}
					if(response.equals("f")) {
						System.out.print("Are you sure you want to forfeit this game: ");
						String switchResponse = input.next();
						if(switchResponse.equals("forfeit")) {
							System.out.println();
							System.out.println("Team A Wins!");
							System.out.println();
							party2.passTurn(party1);
							game = false;
						}
					}
					if(response.equals("p")) {
						party2.showTeam();
						System.out.print("Are you sure you want to pass to the enemy's turn: ");
						String switchResponse = input.next();
						if(switchResponse.equals("p")) {
							b.checkSpace();
							if(party1.teamDown()) {
								System.out.println();
								System.out.println("Team B Wins!");
								party2.passTurn(party1);
								game = false;
							}else {
								party2.passTurn(party1);
								party2.reduceTeamEffects();
								party2.reduceTeamCooldowns();
								turns++;
								SpawnOrbs();
								SpawnCover();
								b.checkTiles();
							}
						}
					}
					if(response.equals("vr")) {
						ViewRange(p6, p1, p3, p5, p4, p2, 0);
					}
					if(response.equals("i")) {
						party1.showTeam();
					}
					if(response.equals("m")) {
						Movement(p6, p1, p3, p5, p2, p4);
					}
					if(response.equals("o")) {
						ShowOrbs(p6);
					}
					if(response.equals("c")) {
						ShowCover(p6);
					}
					if(response.equals("r")) {
						Dash(p6, p1, p3, p5);
					}
					if(response.equals("j")) {
						Location l = SetCursor(p6, p1, p3, p5, p2, p4, p6.getRange());
						Jump(p6, p4, p2, l, p1, p3, p5);
					}
					if(response.equals("dy")) {
						p6.useDynamic();
					}
					if(response.equals("u")) {
						if(!p6.ultReady() || p6.ultActive()) {
							System.out.println("My ultimate cannot be used!");
							System.out.println();
						}else {
							ultimateFX();
							runUltimates(p6, p1, p3, p5, p4, p2, party2, party1);
						}
					}
					if(response.equals("a") && (!p6.onCooldown() || (p6.getName().equals("Makani") && p6.getWind()))) {
						if(p6.isDazed()) {
							System.out.println("Cannot use ability when dazed!");
							System.out.println();
						}else {
							abilityFX();
							runAbilities(p6, p1, p3, p5, p2, p4);
						}
					}
					if(response.equals("w")) {
						if(p6.isFreezed()) {
							System.out.println("Cannot use weapon while freezed!");
							System.out.println();
						}else {
							if(p6.hasAttacked()) {
								System.out.println(p6.getSkin() + " has already attacked this turn!");
								System.out.println();
							}else {
								if(p6.getName().equals("Stellar")) {
									StellarAttack(p6, p1, p3 ,p5);
									weaponFX();
								}else if(p6.getName().equals("Victor")) {
									VictorAttack(p6, p1, p3 ,p5);
									weaponFX();
								}else if(p6.getName().equals("Deny")) {
									Location l = SetCursor(p6, p1, p3, p5, p4, p2, 4);
									DenyAttack(p6, p1, p3 ,p5, l);
									weaponFX();
								}else if(p6.getName().equals("Everest")) {
									EverestAttack(p6, p1, p3 ,p5);
									weaponFX();
								}else if(p6.getName().equals("Millie")) {
									MillieAttack(p6, p1, p3 ,p5);
									weaponFX();
								}else if (p6.getName().equals("Yuri")) {
									Location l = SetCursor(p6, p1, p3, p5, p4, p2, 15);
									YuriAttack(p6, p1, p3 ,p5, p4, p2, l);
								}else if (p6.getName().equals("Flor")) {
									FlorAttack(p6, p1, p3, p5);
									weaponFX();
								}else if (p6.getName().equals("Ivy")) {
									IvyAttack(p6, p1, p3 ,p5, p4, p2);
									weaponFX();
								}else if (p6.getName().equals("Bladee")) {
									BladeeAttack(p6, p1, p3 ,p5);
									weaponFX();
								}else if (p6.getName().equals("Makani")) {
									MakaniAttack(p6, p1, p3 ,p5);
									weaponFX();
								}else if(p6.getName().equals("Angelos")) {
									AngelosAttack(p6, p1, p3, p5);
									weaponFX();
								}else if(p6.getName().equals("Jesse")) {
									JesseAttack(p6, p1, p3, p5);
									weaponFX();
								}else if(p6.getName().equals("Audrey")) {
									AudreyAttack(p6, p1, p3, p5);
									weaponFX();
								}else if(p6.getName().equals("Bedrock")) {
									BedrockAttack(p6, p1, p3 ,p5);
									weaponFX();
								}else if(p6.getName().equals("Max")) {
									MaxAttack(p6, p1, p3 ,p5);
									weaponFX();
								}else if(p6.getName().equals("Cherry")) {
									CherryAttack(p6, p1, p3, p5, p2, p4);
									weaponFX();
								}else {
									party1.showRoster(p6);
									System.out.print("Who do you want to attack: ");
									String attackResponse = input.next();
									if(attackResponse.equals("1")) {
										if(!p6.inRange(p1)) {
											System.out.println();
											System.out.println("Target is out of range!");
											System.out.println();
										}else {
											weaponFX();
											runAttacks(p6, p1, p3, p5, p2, p4);
										}
									}
									if(attackResponse.equals("2")) {
										if(!p6.inRange(p3)) {
											System.out.println();
											System.out.println("Target is out of range!");
											System.out.println();
										}else {
											weaponFX();
											runAttacks(p6, p3, p1, p5, p2, p4);
										}
									}
									if(attackResponse.equals("3")) {
										if(!p6.inRange(p5)) {
											System.out.println();
											System.out.println("Target is out of range!");
											System.out.println();
										}else {
											weaponFX();
											runAttacks(p6, p5, p3, p1, p2, p4);
										}
									}
								}
							}
						}
					}
					if(!p6.isAlive()) {
						p6.endTurn();
						if(party2.teamDown()) {
							System.out.println("Team A Wins!");
							game = false;
							//p1.image().close();
						}else {
							party2.nextPlayer();
						}
					}
				}
			}
		}
		input.close();
		try {
			audioPlayer.stop();
		} catch (UnsupportedAudioFileException e) {
		} catch (IOException e) {
		} catch (LineUnavailableException e) {
		}
		try {
			String audio = "audio/victoryedit.wav";
			Music victoryPlayer = new Music(audio, false); 
			victoryPlayer.play();
			//String audio2 = "victoryvoice.wav";
			//Music victoryPlayer2 = new Music(audio2, false);
			//victoryPlayer2.play();
		}catch (Exception e) {
			System.out.println(e);
		}
		System.out.println();
		System.out.println("Game Summary: ");
		System.out.println();
		System.out.println("Team A:");
		System.out.println(p1.stats());
		System.out.println();
		System.out.println(p3.stats());
		System.out.println();
		System.out.println(p5.stats());
		System.out.println();
		System.out.println();
		System.out.println("Team B:");
		System.out.println(p2.stats());
		System.out.println();
		System.out.println(p4.stats());
		System.out.println();
		System.out.println(p6.stats());
		System.out.println();
		double largestTank = 0;
		double largestBrawler = 0;
		double largestSupport = -1;
		int largestDive = 0;
		Player tank = null;
		Player brawler = null;
		Player support = null;
		Player dive = null;
		
		for(int i = 0; i < 6; i++) {
			if(players[i].totalTank() > largestTank) {
				largestTank = players[i].totalTank();
				tank = players[i];
			}
			if(players[i].totalDamage() > largestBrawler) {
				largestBrawler = players[i].totalDamage();
				brawler = players[i];
			}
			if(players[i].totalHealing() > largestSupport) {
				largestSupport = players[i].totalHealing();
				support = players[i];
			}
			if(players[i].totalMovement() > largestDive) {
				largestDive = players[i].totalMovement();
				dive = players[i];
			}
		}
		
		System.out.println("Tank MVP: " + tank.getSkin());
		System.out.println("Brawler MVP: " + brawler.getSkin());
		System.out.println("Support MVP: " + support.getSkin());
		System.out.println("Dive MVP: " + dive.getSkin());
		while(true) {
		}
		
	}
	
	public static void createController() {
		new Thread(() -> {
        }).start();

        // Create the main frame
        JFrame frame = new JFrame("LunarVerse Game Controller");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(515, 385);
        frame.setLayout(null); // Use null layout for custom positioning
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = screenSize.width - frame.getWidth() - (screenSize.width / 10);
        int y = (screenSize.height - frame.getHeight()) / 2; // Center vertically
        frame.setLocation(x, y);

        // Create buttons
        JLabel buttonBackground = new JLabel(new ImageIcon("images/controller.png"));
        JButton buttonJump = new JButton(new ImageIcon("images/jumpbutton.png"));
        JButton buttonDash = new JButton(new ImageIcon("images/dashbutton.png"));
        JButton buttonSwitch = new JButton(new ImageIcon("images/switchbutton.png"));
        JButton buttonWeapon = new JButton(new ImageIcon("images/weaponbutton.png"));
        JButton buttonAbility = new JButton(new ImageIcon("images/clockbutton.png"));
        JButton buttonUltimate = new JButton(new ImageIcon("images/ultimatebutton.png"));
        JButton buttonPass = new JButton(new ImageIcon("images/passbutton.png"));
        JButton buttonSearch = new JButton(new ImageIcon("images/searchbutton.png"));
        JButton buttonMovement = new JButton(new ImageIcon("images/movebutton.png"));
        JButton buttonUp = new JButton(new ImageIcon("images/upbutton.png"));
        JButton buttonLeft = new JButton(new ImageIcon("images/leftbutton.png"));
        JButton buttonDown = new JButton(new ImageIcon("images/downbutton.png"));
        JButton buttonRight = new JButton(new ImageIcon("images/rightbutton.png"));
        JButton buttonMap = new JButton(new ImageIcon("images/mapbutton.png"));
        JButton buttonConfirm = new JButton(new ImageIcon("images/confirmbutton.png"));
        JButton buttonSelectTwo = new JButton(new ImageIcon("images/selecttwobutton.png"));
        JButton buttonSelectOne = new JButton(new ImageIcon("images/selectonebutton.png"));
        JButton buttonSelectThree = new JButton(new ImageIcon("images/selectthreebutton.png"));

        // Set bounds for buttons
        buttonJump.setBounds(65,60,75,75);
        buttonDash.setBounds(290,140,75,75);
        buttonSwitch.setBounds(360,120,50,50);
        buttonBackground.setBounds(0, 0, 500, 350);
        buttonWeapon.setBounds(360,50,50,50);
        buttonAbility.setBounds(325,85,50,50);
        buttonUltimate.setBounds(395,85,50,50);
        buttonMovement.setBounds(140,165,25,25);
        buttonMap.setBounds(300, 400, 100, 30);
        buttonPass.setBounds(300, 50, 40, 40);
        buttonSearch.setBounds(280, 85, 40, 40);
        buttonMap.setBounds(170, 50, 40, 40);
        buttonConfirm.setBounds(190, 85, 40, 40);
        buttonSelectTwo.setBounds(238, 210, 30, 30);
        buttonSelectOne.setBounds(208, 210, 30, 30);
        buttonSelectThree.setBounds(266, 210, 30, 30);
        buttonUp.setBounds(140, 140, 25, 25);
        buttonLeft.setBounds(117, 165, 25, 25);
        buttonDown.setBounds(140, 190, 25, 25);
        buttonRight.setBounds(164, 165, 25, 25);
        
        buttonJump.setOpaque(false);
        buttonJump.setContentAreaFilled(false);
        buttonJump.setBorderPainted(false);
        buttonJump.setFocusPainted(false);
        buttonDash.setOpaque(false);
        buttonDash.setContentAreaFilled(false);
        buttonDash.setBorderPainted(false);
        buttonDash.setFocusPainted(false);
        buttonSwitch.setOpaque(false);
        buttonSwitch.setContentAreaFilled(false);
        buttonSwitch.setBorderPainted(false);
        buttonSwitch.setFocusPainted(false);
        buttonAbility.setOpaque(false);
        buttonAbility.setContentAreaFilled(false);
        buttonAbility.setBorderPainted(false);
        buttonAbility.setFocusPainted(false);
        buttonUltimate.setOpaque(false);
        buttonUltimate.setContentAreaFilled(false);
        buttonUltimate.setBorderPainted(false);
        buttonUltimate.setFocusPainted(false);
        buttonWeapon.setOpaque(false);
        buttonWeapon.setContentAreaFilled(false);
        buttonWeapon.setBorderPainted(false);
        buttonWeapon.setFocusPainted(false);
        buttonPass.setOpaque(false);
        buttonPass.setContentAreaFilled(false);
        buttonPass.setBorderPainted(false);
        buttonPass.setFocusPainted(false);
        buttonSearch.setOpaque(false);
        buttonSearch.setContentAreaFilled(false);
        buttonSearch.setBorderPainted(false);
        buttonSearch.setFocusPainted(false);
        buttonMap.setOpaque(false);
        buttonMap.setContentAreaFilled(false);
        buttonMap.setBorderPainted(false);
        buttonMap.setFocusPainted(false);
        buttonConfirm.setOpaque(false);
        buttonConfirm.setContentAreaFilled(false);
        buttonConfirm.setBorderPainted(false);
        buttonConfirm.setFocusPainted(false);
        buttonSelectTwo.setOpaque(false);
        buttonSelectTwo.setContentAreaFilled(false);
        buttonSelectTwo.setBorderPainted(false);
        buttonSelectTwo.setFocusPainted(false);
        buttonSelectOne.setOpaque(false);
        buttonSelectOne.setContentAreaFilled(false);
        buttonSelectOne.setBorderPainted(false);
        buttonSelectOne.setFocusPainted(false);
        buttonSelectThree.setOpaque(false);
        buttonSelectThree.setContentAreaFilled(false);
        buttonSelectThree.setBorderPainted(false);
        buttonSelectThree.setFocusPainted(false);
        buttonMovement.setOpaque(false);
        buttonMovement.setContentAreaFilled(false);
        buttonMovement.setBorderPainted(false);
        buttonMovement.setFocusPainted(false);
        buttonUp.setOpaque(false);
        buttonUp.setContentAreaFilled(false);
        buttonUp.setBorderPainted(false);
        buttonUp.setFocusPainted(false);
        buttonLeft.setOpaque(false);
        buttonLeft.setContentAreaFilled(false);
        buttonLeft.setBorderPainted(false);
        buttonLeft.setFocusPainted(false);
        buttonDown.setOpaque(false);
        buttonDown.setContentAreaFilled(false);
        buttonDown.setBorderPainted(false);
        buttonDown.setFocusPainted(false);
        buttonRight.setOpaque(false);
        buttonRight.setContentAreaFilled(false);
        buttonRight.setBorderPainted(false);
        buttonRight.setFocusPainted(false);
        
        // Add buttons to frame
        frame.getContentPane().add(buttonJump);
        frame.getContentPane().add(buttonDash);
        frame.getContentPane().add(buttonSwitch);
        frame.getContentPane().add(buttonWeapon);
        frame.getContentPane().add(buttonAbility);
        frame.getContentPane().add(buttonUltimate);
        frame.getContentPane().add(buttonPass);
        frame.getContentPane().add(buttonSearch);
        frame.getContentPane().add(buttonConfirm);
        frame.getContentPane().add(buttonSelectTwo);
        frame.getContentPane().add(buttonSelectOne);
        frame.getContentPane().add(buttonSelectThree);
        frame.getContentPane().add(buttonMovement);
        frame.getContentPane().add(buttonUp);
        frame.getContentPane().add(buttonLeft);
        frame.getContentPane().add(buttonDown);
        frame.getContentPane().add(buttonRight);
        frame.getContentPane().add(buttonMap);
        frame.getContentPane().add(buttonBackground);

        // Add action listeners to buttons
        addButtonActionListener(buttonSelectThree, frame, KeyEvent.VK_3);
        addButtonActionListener(buttonSelectOne, frame, KeyEvent.VK_1);
        addButtonActionListener(buttonSelectTwo, frame, KeyEvent.VK_2);
        addButtonActionListener(buttonConfirm, frame, KeyEvent.VK_C);
        addButtonActionListener(buttonSearch, frame, KeyEvent.VK_T);
        addButtonActionListener(buttonPass, frame, KeyEvent.VK_P);
        addButtonActionListener(buttonSwitch, frame, KeyEvent.VK_S);
        addButtonActionListener(buttonDash, frame, KeyEvent.VK_R);
        addButtonActionListener(buttonJump, frame, KeyEvent.VK_J);
        addButtonActionListener(buttonWeapon, frame, KeyEvent.VK_W);
        addButtonActionListener(buttonAbility, frame, KeyEvent.VK_A);
        addButtonActionListener(buttonUltimate, frame, KeyEvent.VK_U);
        addButtonActionListener(buttonMovement, frame, KeyEvent.VK_M);
        addButtonActionListener(buttonUp, frame, KeyEvent.VK_W);
        addButtonActionListener(buttonLeft, frame, KeyEvent.VK_A);
        addButtonActionListener(buttonDown, frame, KeyEvent.VK_S);
        addButtonActionListener(buttonRight, frame, KeyEvent.VK_D);
        addButtonActionListener(buttonMap, frame, KeyEvent.VK_B);

        // Make the frame visible
        frame.setVisible(true);
	}
	
	private static void addButtonActionListener(JButton button, JFrame frame, int keyEvent) {
	    button.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            try {
	                // Create a Robot instance
	                Robot robot = new Robot();
	                // Hide the GUI window
	                frame.setVisible(false);
	                // Short delay to allow focus change
	                robot.delay(20);
	                // Simulate pressing the key
	                robot.keyPress(keyEvent);
	                robot.keyRelease(keyEvent);
	                // Simulate pressing Enter
	                robot.keyPress(KeyEvent.VK_ENTER);
	                robot.keyRelease(KeyEvent.VK_ENTER);
	                // Restore the GUI window
	                frame.setVisible(true);
	            } catch (AWTException ex) {
	                ex.printStackTrace();
	            }
	        }
	    });
	}

	public static void runAttacks(Player p, Player a, Player b, Player c, Player d, Player e) {

		if (p.isCorrupt()) {
			int randomNum = (int) (Math.random() * (6 - 1 + 1)) + 1;
			switch (randomNum) {
			case 1:
				a = d;
				b = e;
				break;
			case 2:
				a = e;
				b = d;
				break;
			case 3:
				b = d;
				c = e;
				break;
			case 4:
				c = d;
				b = e;
				break;
			case 5:
				a = d;
				c = e;
				break;
			case 6:
				c = d;
				a = e;
				break;
			}
		}

		if(p.getName().equals("Lunar")) {
			LunarAttack(p, a, b, c);
		}
		if(p.getName().equals("Solar")) {
			SolarAttack(p, a);
		}
		if(p.getName().equals("Mack")) {
			MackAttack(p, a, b, c);
		}
		if(p.getName().equals("Finley")) {
			FinleyAttack(p, a);
		}
		if(p.getName().equals("Burt")) {
			BurtAttack(p, a);
		}
		if(p.getName().equals("Bolo")) {
			BoloAttack(p, a);
		}
		if(p.getName().equals("Dylan")) {
			DylanAttack(p, a);
		}
		if(p.getName().equals("Zero")) {
			ZeroAttack(p, a, b, c);
		}
		if(p.getName().equals("Eli")) {
			EliAttack(p, a);
		}
		if(p.getName().equals("Via")) {
			ViaAttack(p, a, b, c);
		}
		if(p.getName().equals("Louis")) {
			LouisAttack(p, a);
		}
		if(p.getName().equals("Alex")) {
			AlexAttack(p, a);
		}
		if(p.getName().equals("Orion")) {
			OrionAttack(p, a);
		}
		if(p.getName().equals("Kailani")) {
			KailaniAttack(p,a);
		}
		if(p.getName().equals("Ashley")) {
			AshleyAttack(p, a);
		}
		if(p.getName().equals("Rocco")) {
			RoccoAttack(p, a, b, c);
		}
		if(p.getName().equals("Sammi")) {
			SammiAttack(p, a);
		}
		if(p.getName().equals("Clara")) {
			ClaraAttack(p, a);
		}
		if(p.getName().equals("Thunder")) {
			ThunderAttack(p, a, b, c);
		}
		if(p.getName().equals("Aidan")) {
			AidanAttack(p, a);
		}
		if(p.getName().equals("Liam")) {
			LiamAttack(p, a, b, c);
		}
		if(p.getName().equals("Axol")) {
			AxolAttack(p, a);
		}
		if(p.getName().equals("Katrina")) {
			KatrinaAttack(p, a);
		}
		if(p.getName().equals("Midnite")) {
			MidniteAttack(p, a);
		}
		if(p.getName().equals("Xara")) {
			XaraAttack(p, a);
		}
		if(p.getName().equals("Kithara")) {
			KitharaAttack(p, a);
		}
		if(p.getName().equals("Anjelika")) {
			AnjelikaAttack(p, a);
		}
		if(p.getName().equals("Archer")) {
			ArcherAttack(p, a);
		}
		if(p.getName().equals("Tom")) {
			TomAttack(p, a);
		}
		if(p.getName().equals("Dimentio")) {
			DimentioAttack(p, a);
		}
		if(p.getName().equals("Grizz")) {
			GrizzAttack(p, a, b, c);
		}
		if(p.getName().equals("Evil")) {
			EvilAttack(p, a, b, c);
		}
		if(p.getName().equals("Mason")) {
			MasonAttack(p, a);
		}
		if(p.getName().equals("Airic")) {
			AiricAttack(p, a);
		}
		if(p.getName().equals("Julian")) {
			JulianAttack(p, a, b, c);
		}
		if(p.getName().equals("Gash")) {
			GashAttack(p, a);
		}
		if(p.getName().equals("Mayhem")) {
			MayhemAttack(p, a);
		}
		if(p.getName().equals("Gates")) {
			Location l = SetCursor(p, a, b, c, d, e, 0);
			GatesAttack(p, a, l);
		}
		if(p.getName().equals("Ayson")) {
			AysonAttack(p, a);
		}
		if(p.getName().equals("Chloe")) {
			ChloeAttack(p, a, d, e);
		}
		if(p.getName().equals("Hopper")) {
			HopperAttack(p, a);
		}
		if(p.getName().equals("Redgar")) {
			RedgarAttack(p, a);
		}
		if(p.getName().equals("Radar")) {
			RadarAttack(p, a);
		}
		if(p.getName().equals("Oona")) {
			OonaAttack(p, a);
		}
		if(p.getName().equals("Augie")) {
			AugieAttack(p, a);
		}
		if(p.getName().equals("Ruby")) {
			RubyAttack(p, a);
		}
		if(p.getName().equals("Norman")) {
			NormanAttack(p, a, d, e);
		}
		if(p.getName().equals("Chief")) {
			ChiefAttack(p, a);
		}
		if(p.getName().equals("Melony")) {
			MelonyAttack(p, a);
		}
		if(p.getName().equals("Echo")) {
			EchoAttack(p, a, b, c);
		}
		if(p.getName().equals("Rhythm")) {
			RhythmAttack(p, a);
		}
		if(p.getName().equals("Grenadine")) {
			GrenadineAttack(p, a);
		}
		if(p.getName().equals("Patitek")) {
			PatitekAttack(p, a);
		}
		if(p.getName().equals("Crystal")) {
			CrystalAttack(p, a);
		}
		if(p.getName().equals("Velvet")) {
			VelvetAttack(p, a);
		}
		if(p.getName().equals("Drift")) {
			DriftAttack(p, a);
		}
		if(p.getName().equals("Snowfall")) {
			SnowfallAttack(p, a);
		}
		if(p.getName().equals("Shutter")) {
			ShutterAttack(p, a, d, e);
		}
		if(p.getName().equals("Magnet")) {
			MagnetAttack(p, a, b, c);
		}
		if(p.getName().equals("Jing")) {
			JingAttack(p, a);
		}
		if(p.getName().equals("Folden")) {
			FoldenAttack(p, a);
		}
		if(p.getName().equals("Margo")) {
			MargoAttack(p, a, b, c);
		}
		if(p.getName().equals("Petra")) {
			PetraAttack(p, a, b, c);
		}
		if(p.getName().equals("Quincy")) {
			QuincyAttack(p, a);
		}
		if(p.getName().equals("Unice")) {
			UniceAttack(p, a, b, c);
		}
		if(p.getName().equals("Leaf")) {
			LeafAttack(p, a);
		}
		if(p.getName().equals("Courtney")) {
			CourtneyAttack(p, a, b, c);
		}
		if(p.getName().equals("Divine")) {
			DivineAttack(p, a, b, c);
		}
		if(p.getName().equals("Gambit")) {
			GambitAttack(p, a, d, e);
		}
		if(p.getName().equals("Cloud")) {
			CloudAttack(p, a);
		}
		if(p.getName().equals("Winnie")) {
			WinnieAttack(p, a);
		}
		if(p.getName().equals("Pearl")) {
			PearlAttack(p, a, b, c);
		}
		if(p.getName().equals("Andrew")) {
			AndrewAttack(p, a, d, e);
		}
		if(p.getName().equals("Orchid")) {
			OrchidAttack(p, a, b, c);
		}
		if(p.getName().equals("Clementine")) {
			ClementineAttack(p, a);
		}
		if(p.getName().equals("Rin")) {
			RinAttack(p, a, b, c);
		}
		if(p.getName().equals("Isabelle")) {
			IsabelleAttack(p, a, d, e);
		}
		if(p.getName().equals("Lumiere")) {
			LumiereAttack(p, a);
		}
		if(p.getName().equals("Willow")) {
			WillowAttack(p, a, b, c);
		}
		if(p.getName().equals("Jazz")) {
			JazzAttack(p, a, b, c);
		}
		if(p.getName().equals("Harper")) {
			HarperAttack(p, a);
		}
		if(p.getName().equals("Noah")) {
			NoahAttack(p, a);
		}
		if(p.getName().equals("Jade")) {
			JadeAttack(p, a);
		}
		if(p.getName().equals("Bonbon")) {
			BonbonAttack(p, a, d, e);
		}
		if(p.getName().equals("Boss:Finley")) {
			FinleyBossAttack(p, a, d, e);
		}
	}
	
	public static void runAbilities(Player p, Player a, Player b, Player c, Player d, Player e) {
		if(p.getName().equals("Lunar")) {
			LunarAbility(p);
		}
		if(p.getName().equals("Solar")) {
			SolarAbility(p);
		}
		if(p.getName().equals("Mack")) {
			Location l = SetCursor(p, a, b, c, d, e, p.getRange());
			MackAbility(p, a, b, c, l);
		}
		if(p.getName().equals("Cherry")) {
			CherryAbility(p, a, b, c);
		}
		if(p.getName().equals("Finley")) {
			FinleyAbility(p, a, b, c);
		}
		if(p.getName().equals("Burt")) {
			BurtAbility(p);
		}
		if(p.getName().equals("Bolo")) {
			BoloAbility(p);
		}
		if(p.getName().equals("Dylan")) {
			DylanAbility(p, a, b ,c);
		}
		if(p.getName().equals("Zero")) {
			ZeroAbility(p, d, e);
		}
		if(p.getName().equals("Max")) {
			MaxAbility(p, d, e);
		}
		if(p.getName().equals("Eli")) {
			EliAbility(p, d, e);
		}
		if(p.getName().equals("Louis")) {
			LouisAbility(p, a, b ,c);
		}
		if(p.getName().equals("Alex")) {
			AlexAbility(p, d, e);
		}
		if(p.getName().equals("Orion")) {
			OrionAbility(p, d, e);
		}
		if(p.getName().equals("Via")) {
			Location l = SetCursor(p, a, b, c, d, e, 8);
			ViaAbility(p, a, b, c, l);
		}
		if(p.getName().equals("Kailani")) {
			Location l = SetCursor(p, a, b, c, d, e, 5);
			KailaniAbility(p, a, b, c, l);
		}
		if(p.getName().equals("Ashley")) {
			AshleyAbility(p, a, b, c, d, e);
		}
		if(p.getName().equals("Bedrock")) {
			BedrockAbility(p);
		}
		if(p.getName().equals("Rocco")) {
			RoccoAbility(p, a, b ,c);
		}
		if(p.getName().equals("Sammi")) {
			SammiAbility(p);
		}
		if(p.getName().equals("Clara")) {
			ClaraAbility(p);
		}
		if(p.getName().equals("Thunder")) {
			ThunderAbility(p);
		}
		if(p.getName().equals("Aidan")) {
			AidanAbility(p, d, e);
		}
		if(p.getName().equals("Liam")) {
			LiamAbility(p, d, e);
		}
		if(p.getName().equals("Axol")) {
			AxolAbility(p, d, e);
		}
		if(p.getName().equals("Katrina")) {
			KatrinaAbility(p, d, e);
		}
		if(p.getName().equals("Midnite")) {
			MidniteAbility(p, a, b, c, d, e);
		}
		if(p.getName().equals("Xara")) {
			XaraAbility(p, d, e);
		}
		if(p.getName().equals("Kithara")) {
			KitharaAbility(p, d, e);
		}
		if(p.getName().equals("Anjelika")) {
			AnjelikaAbility(p, a, b ,c);
		}
		if(p.getName().equals("Archer")) {
			ArcherAbility(p, d, e);
		}
		if(p.getName().equals("Tom")) {
			TomAbility(p);
		}
		if(p.getName().equals("Dimentio")) {
			DimentioAbility(p, a, b ,c);
		}
		if(p.getName().equals("Grizz")) {
			GrizzAbility(p, d, e);
		}
		if(p.getName().equals("Evil")) {
			EvilAbility(p);
		}
		if(p.getName().equals("Mason")) {
			Location l = SetCursor(p, a, b, c, d, e, 4);
			MasonAbility(p, a, b, c, l);
		}
		if(p.getName().equals("Airic")) {
			Location l = SetCursor(p, a, b, c, d, e, 0);
			AiricAbility(p, d, e, l);
		}
		if(p.getName().equals("Julian")) {
			JulianAbility(p);
		}
		if(p.getName().equals("Gash")) {
			GashAbility(p, d, e);
		}
		if(p.getName().equals("Mayhem")) {
			MayhemAbility(p, a, b ,c);
		}
		if(p.getName().equals("Gates")) {
			GatesAbility(p, d, e);
		}
		if(p.getName().equals("Audrey")) {
			AudreyAbility(p, a, b ,c);
		}
		if(p.getName().equals("Ayson")) {
			AysonAbility(p, a, b, c, d, e);
		}
		if(p.getName().equals("Chloe")) {
			ChloeAbility(p, d, e);
		}
		if(p.getName().equals("Hopper")) {
			HopperAbility(p, d, e);
		}
		if(p.getName().equals("Redgar")) {
			RedgarAbility(p, d, e);
		}
		if(p.getName().equals("Radar")) {
			RadarAbility(p, a, b ,c);
		}
		if(p.getName().equals("Oona")) {
			OonaAbility(p, a, b ,c);
		}
		if(p.getName().equals("Augie")) {
			AugieAbility(p, d, e);
		}
		if(p.getName().equals("Ruby")) {
			Location l = SetCursor(p, a, b, c, d, e, 0);
			RubyAbility(p, l);
		}
		if(p.getName().equals("Norman")) {
			NormanAbility(p, a, b ,c);
		}
		if(p.getName().equals("Jesse")) {
			JesseAbility(p);
		}
		if(p.getName().equals("Chief")) {
			ChiefAbility(p, d, e);
		}
		if(p.getName().equals("Angelos")) {
			AngelosAbility(p, d, e);
		}
		if(p.getName().equals("Melony")) {
			Location l = SetCursor(p, a, b, c, d, e, 0);
			MelonyAbility(p, a, b, c, d, e, l);
		}
		if(p.getName().equals("Echo")) {
			Location l = SetCursor(p, a, b, c, d, e, 0);
			Location l2 = SetCursor(p, a, b, c, d, e, 0);
			EchoAbility(p, a, b, c, l, l2);
		}
		if(p.getName().equals("Makani")) {
			MakaniAbility(p, a, b, c, d, e);
		}
		if(p.getName().equals("Rhythm")) {
			RhythmAbility(p);
		}
		if(p.getName().equals("Grenadine")) {
			GrenadineAbility(p);
		}
		if(p.getName().equals("Patitek")) {
			PatitekAbility(p, d, e);
		}
		if(p.getName().equals("Crystal")) {
			CrystalAbility(p, a, b, c, d, e);
		}
		if(p.getName().equals("Velvet")) {
			VelvetAbility(p, a, b ,c);
		}
		if(p.getName().equals("Drift")) {
			DriftAbility(p);
		}
		if(p.getName().equals("Snowfall")) {
			SnowfallAbility(p);
		}
		if(p.getName().equals("Shutter")) {
			ShutterAbility(p);
		}
		if(p.getName().equals("Magnet")) {
			MagnetAbility(p, a, b, c, d, e);
		}
		if(p.getName().equals("Jing")) {
			JingAbility(p, d, e);
		}
		if(p.getName().equals("Folden")) {
			FoldenAbility(p, a, b, c, d, e);
		}
		if(p.getName().equals("Bladee")) {
			BladeeAbility(p, a, b, c, d, e);
		}
		if(p.getName().equals("Margo")) {
			MargoAbility(p, d, e);
		}
		if(p.getName().equals("Ivy")) {
			IvyAbility(p, d, e);
		}
		if(p.getName().equals("Petra")) {
			PetraAbility(p, a, b, c, d, e);
		}
		if(p.getName().equals("Quincy")) {
			QuincyAbility(p, d, e);
		}
		if(p.getName().equals("Unice")) {
			int range = 7;
			if (p.ultActive()) {
				range = 10;
			}
			Location l = SetCursor(p, a, b, c, d, e, range);
			UniceAbility(p, a, b, c, d, e, l);
		}
		if(p.getName().equals("Flor")) {
			Location l = SetCursor(p, a, b, c, d, e, 4);
			FlorAbility(p, a, b, c, d, e, l);
		}
		if(p.getName().equals("Yuri")) {
			YuriAbility(p, d, e);
		}
		if (p.getName().equals("Millie")) {
			if (p.getIron() == 0) {
				System.out.println("I'm out of iron!");
				System.out.println();
				return;
			}else {
				Location l = SetCursor(p, a, b, c, d, e, 3);
				MillieAbility(p, a, b, c, d, e, l);
			}
		}
		if(p.getName().equals("Leaf")) {
			LeafAbility(p, a, b ,c);
		}
		if(p.getName().equals("Courtney")) {
			CourtneyAbility(p);
		}
		if(p.getName().equals("Divine")) {
			DivineAbility(p, d, e);
		}
		if(p.getName().equals("Gambit")) {
			GambitAbility(p, d, e, a, b, c);
		}
		if(p.getName().equals("Cloud")) {
			int range = 4;
			if (p.getDarkness()) {
				range = 6;
			}
			Location l = SetCursor(p, a, b, c, d, e, range);
			CloudAbility(p, a, b, c, d, e, l);
		}
		if(p.getName().equals("Winnie")) {
			Location l = SetCursor(p, a, b, c, d, e, 4);
			WinnieAbility(p, a, b, c, d, e, l);
		}
		if(p.getName().equals("Pearl")) {
			PearlAbility(p, a, b, c);
		}
		if(p.getName().equals("Andrew")) {
			AndrewAbility(p, a, b, c);
		}
		if(p.getName().equals("Orchid")) {
			OrchidAbility(p, a, b, c, d, e);
		}
		if(p.getName().equals("Everest")) {
			EverestAbility(p, a, b, c, d, e);
		}
		if(p.getName().equals("Clementine")) {
			ClementineAbility(p, a, b, c, d, e);
		}
		if(p.getName().equals("Deny")) {
			DenyAbility(p, a, b, c, d, e);
		}
		if(p.getName().equals("Rin")) {
			RinAbility(p, a, b, c);
		}
		if(p.getName().equals("Victor")) {
			VictorAbility(p);
		}
		if(p.getName().equals("Isabelle")) {
			Location l = SetCursor(p, a, b, c, d, e, 6);
			IsabelleAbility(p, a, b, c, d, e, l);
		}
		if(p.getName().equals("Lumiere")) {
			LumiereAbility(p, a, b, c, d, e);
		}
		if(p.getName().equals("Willow")) {
			WillowAbility(p, d, e);
		}
		if(p.getName().equals("Jazz")) {
			JazzAbility(p, a, b, c);
		}
		if(p.getName().equals("Harper")) {
			Location l = SetCursor(p, a, b, c, d, e, 5);
			HarperAbility(p, a, b ,c, l);
		}
		if(p.getName().equals("Noah")) {
			Location l = SetCursor(p, a, b, c, d, e, 4);
			NoahAbility(p, d , e, l);
		}
		if(p.getName().equals("Jade")) {
			JadeAbility(p);
		}
		if(p.getName().equals("Stellar")) {
			StellarAbility(p, a, b, c, d, e, false);
		}
		if(p.getName().equals("Bonbon")) {
			BonbonAbility(p, a, b ,c);
		}
		if(p.getName().equals("Boss:Finley")) {
			FinleyBossAbility(p, a, b ,c);
		}
	}
	
	public static void runUltimates(Player p, Player a, Player b, Player c, Player d, Player e, Party one, Party two) {
		if(p.getName().equals("Boss:Finley")) {
			Location l = SetCursor(p, a, b, c, d, e, 0);
			FinleyBossUltimate(p, a, b, c, l);
		}
		if(p.getName().equals("Bonbon")) {
			System.out.println("This ultimate must be used while moving!");
		}
		if(p.getName().equals("Stellar")) {
			StellarUltimate(p, a, b, c, d, e);
		}
		if(p.getName().equals("Jade")) {
			Location l = SetCursor(p, a, b, c, d, e, 4);
			JadeUltimate(p, a, b, c, d, e, l);
		}
		if(p.getName().equals("Noah")) {
			NoahUltimate(p, a, b, c);
		}
		if(p.getName().equals("Harper")) {
			Location l = SetCursor(p, a, b, c, d, e, 12);
			HarperUltimate(p, a, b, c, d, e, l);
		}
		if(p.getName().equals("Jazz")) {
			JazzUltimate(p, a, b, c);
		}
		if(p.getName().equals("Willow")) {
			WillowUltimate(p);
		}
		if(p.getName().equals("Lumiere")) {
			LumiereUltimate(p, a, b, c, d, e);
		}
		if(p.getName().equals("Isabelle")) {
			IsabelleUltimate(p, a, b, c, d, e);
		}
		if(p.getName().equals("Victor")) {
			VictorUltimate(p);
		}
		if(p.getName().equals("Rin")) {
			RinUltimate(p, a, b);
		}
		if(p.getName().equals("Deny")) {
			DenyUltimate(p, a, b, c);
		}
		if(p.getName().equals("Clementine")) {
			ClementineUltimate(p, a, b, c, false);
		}
		if(p.getName().equals("Everest")) {
			EverestUltimate(p);
		}
		if(p.getName().equals("Orchid")) {
			OrchidUltimate(p, a, b, c, d, e);
		}
		if(p.getName().equals("Andrew")) {
			AndrewUltimate(p, a, b, c);
		}
		if(p.getName().equals("Pearl")) {
			PearlUltimate(p, one, two);
		}
		if(p.getName().equals("Winnie")) {
			WinnieUltimate(p, a, b, c, d, e);
		}
		if(p.getName().equals("Cloud")) {
			CloudUltimate(p, a, b, c);
		}
		if(p.getName().equals("Gambit")) {
			GambitUltimate(p, a, b, c, d, e);
		}
		if(p.getName().equals("Divine")) {
			Location l = SetCursor(p, a, b, c, d, e, 11);
			DivineUltimate(p, a, b, c, l);
		}
		if(p.getName().equals("Courtney")) {
			CourtneyUltimate(p, a, b, c, d, e);
		}
		if(p.getName().equals("Leaf")) {
			Location l = SetCursor(p, a, b, c, d, e, 0);
			LeafUltimate(p, a, b, c, l);
		}
		if(p.getName().equals("Millie")) {
			MillieUltimate(p, a, b, c);
		}
		if(p.getName().equals("Yuri")) {
			YuriUltimate(p);
		}
		if(p.getName().equals("Flor")) {
			FlorUltimate(p, d, e);
		}
		if(p.getName().equals("Unice")) {
			UniceUltimate(p);
		}
		if(p.getName().equals("Quincy")) {
			QuincyUltimate(p, d, e);
		}
		if(p.getName().equals("Petra")) {
			PetraUltimate(p, a, b, c, d, e);
		}
		if(p.getName().equals("Ivy")) {
			IvyUltimate(p, d, e);
		}
		if(p.getName().equals("Margo")) {
			MargoUltimate(p, a, b, c);
		}
		if(p.getName().equals("Bladee")) {
			BladeeUltimate(p, a, b, c, d, e);
		}
		if(p.getName().equals("Folden")) {
			FoldenUltimate(p, a, b, c, d, e);
		}
		if(p.getName().equals("Jing")) {
			JingUltimate(p, a, b, c, d, e);
		}
		if(p.getName().equals("Magnet")) {
			MagnetUltimate(p, a, b, c);
		}
		if(p.getName().equals("Shutter")) {
			ShutterUltimate(p, a, b, c);
		}
		if(p.getName().equals("Snowfall")) {
			SnowfallUltimate(p, a, b, c);
		}
		if(p.getName().equals("Drift")) {
			DriftUltimate(p);
		}
		if(p.getName().equals("Velvet")) {
			VelvetUltimate(p, a, b, c);
		}
		if(p.getName().equals("Crystal")) {
			Location l = SetCursor(p, a, b, c, d, e, 8);
			CrystalUltimate(p, a, b, c, l);
		}
		if(p.getName().equals("Patitek")) {
			PatitekUltimate(p, d, e);
		}
		if(p.getName().equals("Grenadine")) {
			Location l = SetCursor(p, a, b, c, d, e, 0);
			GrenadineUltimate(p, a, b, c, l);
		}
		if(p.getName().equals("Rhythm")) {
			RhythmUltimate(p, d, e);
		}
		if(p.getName().equals("Makani")) {
			MakaniUltimate(p, a, b ,c);
		}
		if(p.getName().equals("Echo")) {
			EchoUltimate(p, a, b, c, d, e);
		}
		if(p.getName().equals("Melony")) {
			Location l = SetCursor(p, a, b, c, d, e, 6);
			MelonyUltimate(p, a, b, c, l);
		}
		if(p.getName().equals("Angelos")) {
			AngelosUltimate(p, d, e);
		}
		if(p.getName().equals("Chief")) {
			ChiefUltimate(p, a, b, c, d, e);
		}
		if(p.getName().equals("Jesse")) {
			JesseUltimate(p);
		}
		if(p.getName().equals("Norman")) {
			NormanUltimate(p, d, e);
		}
		if(p.getName().equals("Ruby")) {
			RubyUltimate(p, d, e);
		}
		if(p.getName().equals("Augie")) {
			AugieUltimate(p, a, b ,c);
		}
		if(p.getName().equals("Oona")) {
			OonaUltimate(p, d, e);
		}
		if(p.getName().equals("Radar")) {
			RadarUltimate(p, a, b, c, d, e);
		}
		if(p.getName().equals("Redgar")) {
			RedgarUltimate(p);
		}
		if(p.getName().equals("Hopper")) {
			HopperUltimate(p, d, e);
		}
		if(p.getName().equals("Chloe")) {
			ChloeUltimate(p, d, e);
		}
		if(p.getName().equals("Ayson")) {
			AysonUltimate(p, a, b ,c);
		}
		if(p.getName().equals("Audrey")) {
			AudreyUltimate(p, d, e);
		}
		if(p.getName().equals("Mayhem")) {
			MayhemUltimate(p);
		}
		if(p.getName().equals("Gash")) {
			GashUltimate(p, a, b, c);
		}
		if(p.getName().equals("Julian")) {
			JulianUltimate(p, a, b, c, d, e);
		}
		if(p.getName().equals("Airic")) {
			AiricUltimate(p, a, b, c);
		}
		if(p.getName().equals("Mason")) {
			MasonUltimate(p, a, b, c, d, e);
		}
		if(p.getName().equals("Evil")) {
			EvilUltimate(p, a, b, c);
		}
		if(p.getName().equals("Grizz")) {
			GrizzUltimate(p);
		}
		if(p.getName().equals("Dimentio")) {
			DimentioUltimate(p);
		}
		if(p.getName().equals("Tom")) {
			Location l = SetCursor(p, a, b, c, d, e, 7);
			TomUltimate(p, a, b, c, l);
		}
		if(p.getName().equals("Archer")) {
			ArcherUltimate(p, a, b, c);
		}
		if(p.getName().equals("Anjelika")) {
			AnjelikaUltimate(p);
		}
		if(p.getName().equals("Kithara")) {
			KitharaUltimate(p, a, b, c);
		}
		if(p.getName().equals("Xara")) {
			XaraUltimate(p, a, b, c);
		}
		if(p.getName().equals("Midnite")) {
			MidniteUltimate(p);
		}
		if(p.getName().equals("Katrina")) {
			KatrinaUltimate(p, d, e);
		}
		if(p.getName().equals("Axol")) {
			AxolUltimate(p, d, e);
		}
		if(p.getName().equals("Liam")) {
			LiamUltimate(p, a, b, c, d, e);
		}
		if(p.getName().equals("Aidan")) {
			AidanUltimate(p, a, b, c, d, e);
		}
		if(p.getName().equals("Thunder")) {
			ThunderUltimate(p, a, b, c);
		}
		if(p.getName().equals("Clara")) {
			ClaraUltimate(p);
		}
		if(p.getName().equals("Sammi")) {
			SammiUltimate(p);
		}
		if(p.getName().equals("Rocco")) {
			RoccoUltimate(p, a, b, c);
		}
		if(p.getName().equals("Bedrock")) {
			BedrockUltimate(p);
		}
		if(p.getName().equals("Ashley")) {
			AshleyUltimate(p, a, b, c, d, e);
		}
		if(p.getName().equals("Kailani")) {
			KailaniUltimate(p, a, b, c);
		}
		if(p.getName().equals("Orion")) {
			OrionUltimate(p, d, e);
		}
		if(p.getName().equals("Alex")) {
			AlexUltimate(p);
		}
		if(p.getName().equals("Louis")) {
			Location l = SetCursor(p, a, b, c, d, e, 10);
			LouisUltimate(p, a, b, c, l);
		}
		if(p.getName().equals("Via")) {
			ViaUltimate(p, a, b ,c);
		}
		if(p.getName().equals("Max")) {
			MaxUltimate(p, a, b ,c);
		}
		if(p.getName().equals("Cherry")) {
			CherryUltimate(p);
		}
		if(p.getName().equals("Bolo")) {
			BoloUltimate(p, a, b ,c);
		}
		if(p.getName().equals("Mack")) {
			MackUltimate(p);
		}
		if(p.getName().equals("Finley")) {
			FinleyUltimate(p, a, b, c);
		}
		if(p.getName().equals("Zero")) {
			ZeroUltimate(p);
		}
		if(p.getName().equals("Burt")) {
			BurtUltimate(p, a, b, c);
		}
		if(p.getName().equals("Eli")) {
			EliUltimate(p, d, e);
		}
		if(p.getName().equals("Solar")) {
			SolarUltimate(p, a, b, c);
		}
		if(p.getName().equals("Dylan")) {
			DylanUltimate(p, a, b, c, d, e);
		}
		if(p.getName().equals("Gates")) {
			GatesUltimate(p);
		}
		if(p.getName().equals("Lunar")) {
			LunarUltimate(p, a, b, c);
		}
	}
	
	public static void weaponFX() {
		try {
			String audio = "audio/weapon.wav";
			Music victoryPlayer = new Music(audio, false); 
			victoryPlayer.play();
		}catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public static void abilityFX() {
		try {
			String audio = "audio/ability.wav";
			Music victoryPlayer = new Music(audio, false); 
			victoryPlayer.play();
		}catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public static void ultimateFX() {
		try {
			String audio = "audio/ultimate.wav";
			Music victoryPlayer = new Music(audio, false); 
			victoryPlayer.play();
		}catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public static void runClock() {
		long startTime = System.currentTimeMillis(); // Get the start time
        while (clock < 5*1000) { // Check if less than 5 seconds have passed
            // Your repeated action goes here
        	
            // Update the elapsed time
            clock = System.currentTimeMillis() - startTime;
        }
	}
	
	public static void checkDynamics(Party p) {
		if (p.has("Lunar") && p.has("Evil")) {
			p.makeDynamic("Evil", "Lunar");
		}
		if (p.has("Zero") && p.has("Aidan")) {
			p.makeDynamic("Zero", "Aidan");
		}
		if (p.has("Dylan") && p.has("Jing")) {
			p.makeDynamic("Dylan", "Jing");
		}
		if (p.has("Hopper") && p.has("Rocco")) {
			p.makeDynamic("Hopper", "Rocco");
		}
		if (p.has("Kithara") && p.has("Julian")) {
			p.makeDynamic("Kithara", "Julian");
		}
		if (p.has("Snowfall") && p.has("Eli")) {
			p.makeDynamic("Snowfall", "Eli");
		}
		if (p.has("Solar") && p.has("Ashley")) {
			p.makeDynamic("Solar", "Ashley");
		}
		if (p.has("Cloud") && p.has("Midnite")) {
			p.makeDynamic("Cloud", "Midnite");
		}
		if (p.has("Mack") && p.has("Sammi")) {
			p.makeDynamic("Mack", "Sammi");
		}
		if (p.has("Andrew") && p.has("Liam")) {
			p.makeDynamic("Andrew", "Liam");
		}
		if (p.has("Axol") && p.has("Finley")) {
			p.makeDynamic("Axol", "Finley");
		}
		if (p.has("Clementine") && p.has("Cherry")) {
			p.makeDynamic("Clementine", "Cherry");
		}
		if (p.has("Bedrock") && p.has("Echo")) {
			p.makeDynamic("Bedrock", "Echo");
		}
		if (p.has("Jesse") && p.has("Margo")) {
			p.makeDynamic("Jesse", "Margo");
		}
		if (p.has("Kailani") && p.has("Makani")) {
			p.makeDynamic("Kailani", "Makani");
		}
		if (p.has("Thunder") && p.has("Radar")) {
			p.makeDynamic("Thunder", "Radar");
		}
		if (p.has("Deny") && p.has("Dimentio")) {
			p.makeDynamic("Deny", "Dimentio");
		}
		if (p.has("Alex") && p.has("Clara")) {
			p.makeDynamic("Alex", "Clara");
		}
		if (p.has("Gates") && p.has("Airic")) {
			p.makeDynamic("Gates", "Airic");
		}
		if (p.has("Flor") && p.has("Orchid")) {
			p.makeDynamic("Flor", "Orchid");
		}
		if (p.has("Archer") && p.has("Harper")) {
			p.makeDynamic("Archer", "Harper");
		}
		if (p.has("Patitek") && p.has("Louis")) {
			p.makeDynamic("Patitek", "Louis");
		}
		if (p.has("Noah") && p.has("Tom")) {
			p.makeDynamic("Noah", "Tom");
		}
		if (p.has("Oona") && p.has("Xara")) {
			p.makeDynamic("Oona", "Xara");
		}
		if (p.has("Gambit") && p.has("Chloe")) {
			p.makeDynamic("Gambit", "Chloe");
		}
		if (p.has("Victor") && p.has("Grenadine")) {
			p.makeDynamic("Victor", "Grenadine");
		}
		if (p.has("Millie") && p.has("Velvet")) {
			p.makeDynamic("Millie", "Velvet");
		}
		if (p.has("Max") && p.has("Redgar")) {
			p.makeDynamic("Max", "Redgar");
		}
		
		if (p.has("Chief") && p.has("Dylan")) {
			p.makeDynamic("Chief", "Dylan");
		}
	}
	
	public static void deathWillowUltimate(Player p) {
		ultimateFX();
		Player a = p.getPlayers().get(0);
		Player b = p.getPlayers().get(1);
		Player c = p.getPlayers().get(2);
		Player d = p.getPlayers().get(3);
		Player e = p.getPlayers().get(4);
		p.setMovementCurrent(15);
		Movement(p, a, b, c, d, e);
		Utility Support = new Utility("Spirit", new Location(14, 19), p, p, p, a, b, c);
		utility.add(Support);
		Utility Support2 = new Utility("Spirit", new Location(14, 23), p, p, p, a, b, c);
		utility.add(Support2);
		Utility Support3 = new Utility("Spirit", new Location(28, 19), p, p, p, a, b, c);
		utility.add(Support3);
		Utility Support4 = new Utility("Spirit", new Location(28, 23), p, p, p, a, b, c);
		utility.add(Support4);
	}
	
	public static void EverestPassive(Player p, Player a, Player b) {
		if (p.getBalance() == 0) {
			System.out.println("I do not have any Balance Orbs right now.");
			System.out.println();
			return;
		}
		if (p.isSupressed()) {
			System.out.println("Cannot use passive skill while supressed!");
			System.out.println();
			return;
		}
		Scanner input = new Scanner(System.in);
		System.out.println("1: " + a.getSkin() +a.showHP() +  a.getHealth() + "/" + a.getMaxHP() + ".");
		System.out.println("2: " + b.getSkin() +b.showHP() +  b.getHealth() + "/" + b.getMaxHP() + ".");
		System.out.println("3: " + p.getSkin() +b.showHP() +  p.getHealth() + "/" + p.getMaxHP() + ".");
		System.out.print("Who do you want to give a Balance Orb to: ");
		String targetResponse = input.next();
		if(targetResponse.equals("1")) {
			p.useBalance();
			a.cleanse();
			System.out.println("\"Let me clean that off for you, " + a.getSkin() + ".\"");
		}
		if(targetResponse.equals("2")) {
			p.useBalance();
			b.cleanse();
			System.out.println("\"Let me clean that off for you, " + b.getSkin() + ".\"");
		}
		if(targetResponse.equals("3")) {
			p.useBalance();
			p.cleanse();
			System.out.println("\"At peace, once again.\"");
		}
		System.out.println();
	}
	
	public static void ReviveDeny(Player p, Player a, Player b) {
		if (a.getEMP() > 0 && !a.isAlive()) {
			if (p.getLoc().eqLoc(a.getLoc())) {
				a.reviveDeny();
			}else {
				return;
			}
		}
		if (b.getEMP() > 0 && !b.isAlive()) {
			if (p.getLoc().eqLoc(b.getLoc())) {
				b.reviveDeny();
			}else {
				return;
			}
		}
		System.out.println();
	}
	
	public static void RinPassive(Player p, Player a, Player z, Player c, Player d, Player e) {
		if (p.isSupressed()) {
			System.out.println("Cannot use passive skill while supressed!");
			System.out.println();
			return;
		}
		Utility u = null;
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).getName().equals("Mochi") && GameSim.utility.get(j).owner(p)) {
				u = utility.get(j);
			}
		}
		String s = "";
		boolean move = true;
		boolean hasMoved = false;
		String lastMoved = "";
		double rand = Math.random();
		Scanner input = new Scanner(System.in);
		if(p.getMochiMovement() <= 0) {
			System.out.println("Mochi has no more movement left!");
			System.out.println();
		}else {
			int originalMovement = p.getMovement();
			b.printMochiField(p, a, z, c, d, e, orbs, cover, p, a, z, c, utility);
			while(move) {
				System.out.print("Which way does Mochi want to move: ");
				String moveResponse = input.next();
				switch (moveResponse) {
				  case "a":
					  if(b.hasTrench(u.getLoc().getX() - 1, u.getLoc().getY())) {
						  System.out.println("Cannot move onto a Trench tile!");
						  b.printMochiField(p, a, z, c, d, e, orbs, cover, p, a, z, c, utility);
						  break;
					  }
					  u.move(u.getLoc().getX() - 1, u.getLoc().getY());
						if(b.hasCar(u.getLoc().getX(), u.getLoc().getY())) {
							u.getLoc().set(u.getLoc().getX() - 3, u.getLoc().getY());
						}
					  hasMoved = true;
					  b.printMochiField(p, a, z, c, d, e, orbs, cover, p, a, z, c, utility);
					  lastMoved = "left";
					  System.out.println("Relocated to " + u.getLoc() + ". " + p.getMochiMovement() + " movement left.");
				    break;
				  case "d":
					  if(b.hasTrench(u.getLoc().getX() + 1, u.getLoc().getY())) {
						  System.out.println("Cannot move onto a Trench tile!");
						  b.printMochiField(p, a, z, c, d, e, orbs, cover, p, a, z, c, utility);
						  break;
					  }
					  u.move(u.getLoc().getX() + 1, u.getLoc().getY());
					  if(b.hasCar(u.getLoc().getX(), u.getLoc().getY())) {
							u.getLoc().set(u.getLoc().getX() + 3, u.getLoc().getY());
						}
					  hasMoved = true;
					  b.printMochiField(p, a, z, c, d, e, orbs, cover, p, a, z, c, utility);
					  lastMoved = "right";
					  System.out.println("Relocated to " + u.getLoc() + ". " + p.getMochiMovement() + " movement left.");
				    break;
				  case "w":
					  if(b.hasTrench(u.getLoc().getX(), u.getLoc().getY() - 1)) {
						  System.out.println("Cannot move onto a Trench tile!");
						  b.printMochiField(p, a, z, c, d, e, orbs, cover, p, a, z, c, utility);
						  break;
					  }
					  u.move(u.getLoc().getX(), u.getLoc().getY() - 1);
					  if(b.hasCar(u.getLoc().getX(), u.getLoc().getY())) {
							u.getLoc().set(u.getLoc().getX(), u.getLoc().getY() - 3);
						}
					  hasMoved = true;
					  b.printMochiField(p, a, z, c, d, e, orbs, cover, p, a, z, c, utility);
					  lastMoved = "up";
					  System.out.println("Relocated to " + p.getLoc() + ". " + p.getMochiMovement() + " movement left.");
				    break;
				  case "s":
					  if(b.hasTrench(u.getLoc().getX(), u.getLoc().getY() + 1)) {
						  System.out.println("Cannot move onto a Trench tile!");
						  b.printMochiField(p, a, z, c, d, e, orbs, cover, p, a, z, c, utility);
						  break;
					  }
					  u.move(u.getLoc().getX(), u.getLoc().getY() + 1);
					  if(b.hasCar(u.getLoc().getX(), u.getLoc().getY())) {
						  u.getLoc().set(u.getLoc().getX(), u.getLoc().getY() + 3);
						}
					  hasMoved = true;
					  b.printMochiField(p, a, z, c, d, e, orbs, cover, p, a, z, c, utility);
					  lastMoved = "down";
					  System.out.println("Relocated to " + p.getLoc() + ". " + p.getMochiMovement() + " movement left.");
				    break;
				  case "r":
					  u.pounce();
					  b.printMochiField(p, a, z, c, d, e, orbs, cover, p, a, z, c, utility);
				    break;
				  case "u":
					  u.destroyUtility();
					  b.printMochiField(p, a, z, c, d, e, orbs, cover, p, a, z, c, utility);
				    break;
				  case "o":
					  u.returnTo();
					  b.printMochiField(p, a, z, c, d, e, orbs, cover, p, a, z, c, utility);
				    break;
				  case "p":
					  p.increaseMochiMovement();
				    break;
				  case "m":
					  System.out.println("Mochi movement completed.");
					  hasMoved = false;
					  move = false;
				    break;
				}
				int hor = 0;
				int ver = 0;
				if(hasMoved) {
					int range = 4;
					
					
				}
				for(int i = 0; i < utility.size(); i++) {
					if(p.getLoc().eqLoc(utility.get(i).getLoc())) {
						if(utility.get(i).getName().equals("Crane") && utility.get(i).isAlly(p) && !utility.get(i).getPickUp()) {
							System.out.println("Got a crane!");
							utility.get(i).getOwner().increaseDPSNum(5);
							utility.get(i).pickedUp();
							break;
						}
					}
				}
				System.out.println();
				if(p.getMochiMovement() <= 0) {
					System.out.println("Mochi is out of movement.");
					move = false;
					System.out.println();
				}
			}
		}
	}
	
	public static void JazzPassive(Player p) {
		if (p.isSupressed()) {
			System.out.println("Cannot use passive skill while supressed!");
			System.out.println();
			return;
		}
		Utility u = null;
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).getName().equals("Bubble") && GameSim.utility.get(j).owner(p)) {
				u = utility.get(j);
			}
		}
		Scanner input = new Scanner(System.in);
		System.out.println("1: Change Radius");
		System.out.println("2: Recall Bubble");
		System.out.print("What do you want to do with the Kinetic Bubble: ");
		String targetResponse = input.next();
		if(targetResponse.equals("1")) {
			u.changeSize();
		}
		if(targetResponse.equals("2")) {
			u.returnBubble();
		}
		System.out.println();
	}
	
	public static void JadePassive(Player p, Player a, Player b, Player c) {
		if (p.isSupressed()) {
			System.out.println("Cannot use passive skill while supressed!");
			System.out.println();
			return;
		}
		if (p.getLoc().eqLoc(a.getLoc())) {
			a.removeJavelins();
		}
		if (p.getLoc().eqLoc(b.getLoc())) {
			b.removeJavelins();
		}
		if (p.getLoc().eqLoc(c.getLoc())) {
			c.removeJavelins();
		}
		System.out.println("\"Hand me those back!\"");
	}
	
	public static void StellarPassive(Player p) {
		if (p.isSupressed()) {
			System.out.println("Cannot use passive skill while supressed!");
			System.out.println();
			return;
		}
		p.useShield();
	}
	
	public static void FinleyBossPassive(Player p, Player a, Player b, Player c, Player d, Player e) {
		if (p.isSupressed()) {
			System.out.println("Cannot use passive skill while supressed!");
			System.out.println();
			return;
		}
		if (p.getTeleportBoss() == 0) {
			Location l = SetCursor(p, d, e, a, b, c, 0);
			p.getLoc().set(l.getX(), l.getY());
			System.out.println("Teleported!");
			p.setTeleport();
			if (a.inRange(l, 5)) {
				a.weak(0.15, 1);
			}
			if (b.inRange(l, 5)) {
				b.weak(0.15, 1);
			}
			if (c.inRange(l, 5)) {
				c.weak(0.15, 1);
			}
		}else {
			System.out.println("Teleport not ready!");
		}
		System.out.println();
	}
	
	public static void PolarisClash(Player p, Player a, Player b, Player c, Player d, Player e) {
		try {
			String audio = "audio/poker.wav";
			Music victoryPlayer = new Music(audio, false);
			//victoryPlayer.play();
		} catch (Exception e1) {
			System.out.println(e1);
		}
		int healthOne = 4;
		int healthTwo = 4;
		int blocksOne = 4;
		int blocksTwo = 4;
		String showBlocksOne = "";
		String showBlocksTwo = "";
		String ability = "";
		Scanner input = new Scanner(System.in);
		while (healthOne > 0 && healthTwo > 0) {
			if (blocksOne > 0) {
				showBlocksOne = ", (B)lock " + blocksOne;
			}else {
				showBlocksOne = "";
			}
			if (blocksTwo > 0) {
				showBlocksTwo = ", (B)lock " + blocksTwo;
			}else {
				showBlocksTwo = "";
			}
			if (p.getCooldown() == 0) {
				ability = ", (A)bility";
			}else {
				ability = "";
			}
			System.out.println("Enter your choices for the round!");
			System.out.println(p.getSkin() + " current health: " + healthOne + ".");
			System.out.println(a.getSkin() + " current health: " + healthTwo + ".");
			System.out.println();
			System.out.println(p.getSkin() + ": (W)eapon "+ showBlocksOne +", (N)othing" + ability);
			System.out.println(a.getSkin() + ": (W)eapon "+ showBlocksTwo +", (N)othing");
			System.out.println();
			System.out.print(p.getSkin() + ", enter your action: ");
			String pAction = input.next();
			System.out.print(a.getSkin() + ", enter your action: ");
			String aAction = input.next();
			if (pAction.equals("n") && aAction.equals("n")) {
				System.out.println("Nothing happens. Next round begins!");
			}else if (pAction.equals("b") && aAction.equals("n")) {
				blocksOne--;
				System.out.println(p.getSkin() + " blocks! Next round begins!");
			}else if (pAction.equals("n") && aAction.equals("b")) {
				blocksTwo--;
				System.out.println(a.getSkin() + " blocks! Next round begins!");
			}else if (pAction.equals("b") && aAction.equals("b")) {
				blocksOne--;
				blocksTwo--;
				System.out.println("Both players block! Next round begins!");
			}else if (pAction.equals("w") && aAction.equals("n")) {
				healthTwo--;
				System.out.println(p.getSkin() + " strikes! "+ a.getSkin() +" is down to " + healthTwo+" health. Next round begins!");
			}else if (pAction.equals("n") && aAction.equals("w")) {
				healthOne--;
				System.out.println(a.getSkin() + " strikes! "+ p.getSkin() +" is down to " + healthOne+" health. Next round begins!");
			}else if (pAction.equals("w") && aAction.equals("b")) {
				blocksTwo--;
				System.out.println(a.getSkin() + " blocks! "+ p.getSkin() +" must skip their next action. Next round begins!");
			}else if (pAction.equals("b") && aAction.equals("w")) {
				blocksOne--;
				System.out.println(p.getSkin() + " blocks! "+ a.getSkin() +" must skip their next action. Next round begins!");
			}else if (pAction.equals("a") && aAction.equals("w")) {
				StellarAbility(p, a, b, c, d, e, true);
				abilityFX();
				System.out.println(p.getSkin() + " blocks! "+ a.getSkin() +" must skip their next action. Next round begins!");
			}else if (pAction.equals("a") && aAction.equals("n")) {
				StellarAbility(p, a, b, c, d, e, true);
				abilityFX();
				System.out.println(p.getSkin() + " uses his ability! Next round begins!");
			}else if (pAction.equals("w") && aAction.equals("w")) {
				healthOne--;
				healthTwo--;
				System.out.println("Both players strike! "+ p.getSkin() +" is down to " + healthOne+" health. " + a.getSkin() +" is down to " + healthTwo+" health. Next round begins!");
			}
			System.out.println();
		}
		if (healthTwo == 0) {
			a.transferStats(p, true);
			System.out.println("\"Your fall was no surprise.\"");
		}else if (healthOne == 0) {
			a.transferStats(p, false);
			System.out.println("\"There seems to be potential in you after all.\"");
		}
		System.out.println();
	}
	
	public static Player CharacterSelection(Player p, String name, boolean start, int x, int y) {
		//p.setImage("backupdancer.png");
		switch (name) {
		  case "Lunar":
			  p = new Player(2350, 250, start, name, x, y, 10, 10, 5);
			  name = bold + color + 147 + "m" + "Lun" + color + 87 + "m" + "ar" + reset;
			  name = p.getGradientName("Lunar", "#A4A8DF", "#A89FD5", "#38E8FF");
			  p.skin(name);
			  p.setC(147);
			  //p.setImage("lunar.png");
			  System.out.println("\"They’ll get a taste of their own medicine soon.\"");
			  System.out.println();
			  p.addRole("brawler");
		    break;
		  case "Aidan":
			  p = new Player(2300, 175, start, name, x, y, 11, 9, 6);
			  name = p.getGradientName("Aidan", "#34A0D5", "#F5903D", "#2796CE");
			  p.skin(name);
			  p.setC(209);
			  System.out.println("\"I’ll be cranking 90s all day around them.\"");
			  System.out.println();
			  p.addRole("brawler");
		    break;
		  case "Finley":
			  p = new Player(2525, 200, start, name, x, y, 9, 9, 6);
			  name = p.getGradientName("Finley", "#F2C511", "#FF4747");
			  p.skin(name);
			  p.setC(223);
			  System.out.println("\"That guy, that guy, and that guy. All dead soon.\"");
			  System.out.println();
			  p.addRole("brawler");
		    break;
		  case "Ayson":
			  p = new Player(2650, 250, start, name, x, y, 10, 10, 6);
			  name = p.getGradientName("Gray & Jay", "#8F2D2D", "#78CDDE", "#48C8FE");
			  p.skin(name);
			  p.setC(45);
			  System.out.println("\"Let’s show them our real power.\"" + " " + "\"Right with you brother.\"");
			  System.out.println();
			  p.addRole("brawler");
		    break;
		  case "Alex":
			  p = new Player(2350, 250, start, name, x, y, 11, 10, 7);
			  name = p.getGradientName("Alex", "#FF4242", "#FFC524", "#F7C308", "#F92424");
			  p.skin(name);
			  p.setC(196);
			  System.out.println("\"Time to fish a good catch!\"");
			  System.out.println();
			  p.addRole("brawler");
			  p.addRole("support");
		    break;
		  case "Jesse":
			  p = new Player(2300, 75, start, name, x, y, 10, 10, 7);
			  name = p.getGradientName("Jesse", "#CF0202", "#FF4242", "#FE6410", "#FFC524", "#F7C308");
			  p.skin(name);
			  p.setC(160);
			  System.out.println("\"No one can last long in hell except me.\"");
			  System.out.println();
			  p.addRole("brawler");
		    break;
		  case "Chief":
			  p = new Player(3800, 200, start, name, x, y, 9, 9, 7);
			  name = p.getGradientName("Chief", "#2EA4FF", "#397BFE", "#0544C2");
			  p.skin(name);
			  p.setC(69);
			  System.out.println("\"As long as we stick together, no one can run us down.\"");
			  System.out.println();
			  p.addRole("dive");
			  p.addRole("tank");
			  p.addRole("support");
		    break;
		  case "Norman":
			  p = new Player(2250, 175, start, name, x, y, 10, 10, 7);
			  name = p.getGradientName("Norman", "#4BEA1F", "#39FEA9", "#1FF6F9");
			  p.skin(name);
			  p.setC(46);
			  System.out.println("\"I can turn sticks into stones, and stones into potential.\"");
			  System.out.println();
			  p.addRole("support");
		    break;
		  case "Katrina":
			  p = new Player(2350, 175, start, name, x, y, 10, 10, 6);
			  name = p.getGradientName("Katrina", "#1F63EA", "#2C9CF2", "#FE67DD", "#F165EC", "#FD75FF", "#1FB1F9", "#3874FF");
			  p.skin(name);
			  p.setC(32);
			  System.out.println("\"As they always say, Woomy!\"");
			  System.out.println();
			  p.addRole("support");
		    break;
		  case "Sammi":
			  p = new Player(2350, 275, start, name, x, y, 10, 10, 6);
			  name = p.getGradientName("Sammi", "#C20000", "#F75026", "#F26380", "#FB9B41");
			  p.skin(name);
			  p.setC(160);
			  System.out.println("\"I just have cracked aim, what can I say?\"");
			  System.out.println();
			  p.addRole("brawler");
		    break;
		  case "Mack":
			  p = new Player(2500, 275, start, name, x, y, 11, 10, 6);
			  name = p.getGradientName("Mack", "#FF24C5", "#FB41F5");
			  p.skin(name);
			  p.setC(200);
			  System.out.println("\"Alright Dalton, let’s hunt them down!\"");
			  System.out.println();
			  p.addRole("brawler");
		    break;
		  case "Axol":
			  p = new Player(2250, 175, start, name, x, y, 10, 10, 6);
			  name = p.getGradientName("Axol", "#F9B92F", "#CE6D2C", "#A98992");
			  p.skin(name);
			  p.setC(222);
			  System.out.println("\"Fear my axolotls, but also love them.\"");
			  System.out.println();
			  p.addRole("support");
		    break;
		  case "Via":
			  p = new Player(2500, 325, start, name, x, y, 10, 10, 7);
			  name = p.getGradientName("Via", "#A32900", "#A20121");
			  p.skin(name);
			  p.setC(124);
			  System.out.println("\"Let’s send them to sleep with the fishes.\"");
			  System.out.println();
			  p.addRole("brawler");
		    break;
		  case "Hopper":
			  p = new Player(3800, 200, start, name, x, y, 10, 10, 7);
			  name = p.getGradientName("Hopper", "#28D006", "#289F19", "#0D6402");
			  p.skin(name);
			  p.setC(40);
			  System.out.println("\"Never back down. Never surrender.\"");
			  System.out.println();
			  p.addRole("tank");
			  p.addRole("support");
		    break;
		  case "Kailani":
			  p = new Player(2350, 250, start, name, x, y, 25, 10, 6);
			  name = p.getGradientName("Kailani", "#DDC136", "#C4BB5A", "#3BDEDB", "#0876BA");
			  p.skin(name);
			  p.setC(187);
			  System.out.println("\"Stand in our way? We’ll wash you over.\"");
			  System.out.println();
			  p.addRole("dive");
			  p.addRole("brawler");
		    break;
		  case "Zero":
			  p = new Player(2375, 250, start, name, x, y, 10, 10, 6);
			  name = p.getGradientName("Zero", "#F11E1E", "#9E9E9E", "#7F5757");
			  p.skin(name);
			  p.setC(196);
			  System.out.println("\"Walk near me. I dare you.\"");
			  System.out.println();
			  p.addRole("brawler");
			  p.addRole("support");
		    break;
		  case "Ruby":
			  p = new Player(2350, 200, start, name, x, y, 10, 10, 5);
			  name = p.getGradientName("Ruby", "#F22C2C", "#B120BC", "#C85DD0");
			  p.skin(name);
			  p.setC(161);
			  System.out.println("\"This is free map control for us!\"");
			  System.out.println();
			  p.addRole("support");
		    break;
		  case "Chloe":
			  p = new Player(2250, 200, start, name, x, y, 10, 10, 5);
			  name = p.getGradientName("Chloe", "#D17000", "#B97704", "#EC9CEA", "#DB6EE2");
			  p.skin(name);
			  p.setC(136);
			  System.out.println("\"Let’s hope the royal guards are on their best performance today.\"");
			  System.out.println();
			  p.addRole("support");
		    break;
		  case "Mason":
			  p = new Player(2450, 300, start, name, x, y, 11, 10, 6);
			  name = p.getGradientName("Mason", "#D963D5", "#A747F5", "#9718EC", "#330080");
			  p.skin(name);
			  p.setC(129);
			  System.out.println("\"They’re gonna be helpless soon enough.\"");
			  System.out.println();
			  p.addRole("brawler");
		    break;
		  case "Max":
			  p = new Player(2600, 25, start, name, x, y, 9, 9, 6);
			  name = p.getGradientName("Max", "#D28B28", "#EA9E34");
			  p.skin(name);
			  p.setC(180);
			  System.out.println("\"Call on my guidance. We’ll need it.\"");
			  System.out.println();
			  p.addRole("brawler");
			  p.addRole("support");
		    break;
		  case "Evil":
			  p = new Player(2500, 275, start, name, x, y, 11, 9, 7);
			  name = p.getGradientName("Evil Lunar", "#F81616", "#A4A8DF", "#A89FD5", "#38E8FF");
			  p.skin(name);
			  p.setC(196);
			  System.out.println("\"The Roche Limit will be their deciding fate.\"");
			  System.out.println();
			  p.addRole("brawler");
		    break;
		  case "Airic":
			  p = new Player(2350, 325, start, name, x, y, 10, 10, 5);
			  name = p.getGradientName("Airic", "#473AF2", "#4367F9", "#A3A6F5", "#C2C2C2");
			  p.skin(name);
			  p.setC(19);
			  System.out.println("\"Get in. Get out.\"");
			  System.out.println();
			  p.addRole("dive");
			  p.addRole("brawler");
		    break;
		  case "Julian":
			  p = new Player(2400, 200, start, name, x, y, 10, 10, 7);
			  name = p.getGradientName("Julian", "#FF8B1F", "#E89F64", "#74DEEC", "#6DF3EA");
			  p.skin(name);
			  p.setC(214);
			  System.out.println("\"Karma’s a pain. Don’t they know?\"");
			  System.out.println();
			  p.addRole("dive");
			  p.addRole("brawler");
		    break;
		  case "Solar":
			  p = new Player(2400, 175, start, name, x, y, 10, 10, 6);
			  name = p.getGradientName("Solar", "#E3E625", "#F47CFD");
			  p.skin(name);
			  p.setC(220);
			  System.out.println("\"Let’s give them a light show!\"");
			  System.out.println();
			  p.addRole("brawler");
		    break;
		  case "Eli":
			  p = new Player(2250, 200, start, name, x, y, 10, 10, 7);
			  name = p.getGradientName("Eli", "#51C0F0", "#35A7ED");
			  p.skin(name);
			  p.setC(39);
			  System.out.println("\"Buddies for days. Buddies for life!\"");
			  System.out.println();
			  p.addRole("support");
		    break;
		  case "Dylan":
			  p = new Player(2400, 200, start, name, x, y, 10, 10, 6);
			  name = p.getGradientName("Dylan", "#2DDF2A", "#1EC23F", "#3594ED", "#4F8CA1");
			  p.skin(name);
			  p.setC(41);
			  System.out.println("\"Time to roll the die.\"");
			  System.out.println();
			  p.addRole("brawler");
		    break;
		  case "Orion":
			  p = new Player(3775, 225, start, name, x, y, 10, 10, 7);
			  name = p.getGradientName("Orion", "#707070", "#8F84A4", "#7264AA", "#426BAE");
			  p.skin(name);
			  p.setC(101);
			  System.out.println("\"Push them out of our way.\"");
			  System.out.println();
			  p.addRole("dive");
			  p.addRole("tank");
			  p.addRole("support");
		    break;
		  case "Grizz":
			  p = new Player(3800, 200, start, name, x, y, 10, 10, 9);
			  name = p.getGradientName("Mr.Grizz", "#9D3F01", "#7E390C", "#471D95", "#741F7A");
			  p.skin(name);
			  p.setC(88);
			  System.out.println("\"Overtime starts now.\"");
			  System.out.println();
			  p.addRole("tank");
			  p.addRole("support");
		    break;
		  case "Clara":
			  p = new Player(2450, 300, start, name, x, y, 10, 10, 6);
			  name = p.getGradientName("Clara", "#F584DD", "#F8AFEE", "#F5AA42");
			  p.skin(name);
			  p.setC(219);
			  System.out.println("\"Me against them? Easy.\"");
			  System.out.println();
			  p.addRole("dive");
			  p.addRole("brawler");
		    break;
		  case "Liam":
			  p = new Player(2425, 250, start, name, x, y, 10, 10, 6);
			  name = p.getGradientName("Liam", "#F53D3D", "#F76526");
			  p.skin(name);
			  p.setC(196);
			  System.out.println("\"Nobody can escape the law.\"");
			  System.out.println();
			  p.addRole("brawler");
			  p.addRole("support");
		    break;
		  case "Mayhem":
			  p = new Player(3600, 225, start, name, x, y, 10, 10, 9);
			  name = p.getGradientName("Mayhem", "#D748FE", "#B423D1", "#9A21FD");
			  p.skin(name);
			  p.setC(171);
			  System.out.println("\"Everybody fears in the darkmess.\"");
			  System.out.println();
			  p.addRole("tank");
		    break;
		  case "Bedrock":
			  p = new Player(4100, 400, start, name, x, y, 7, 7, 9);
			  name = p.getGradientName("Bedrock", "#525252", "#787878", "#B0B0B0");
			  p.skin(name);
			  p.setC(240);
			  System.out.println("\"...\"");
			  System.out.println();
			  p.addRole("tank");
			  p.addRole("brawler");
		    break;
		  case "Augie":
			  p = new Player(3650, 175, start, name, x, y, 10, 10, 8);
			  name = p.getGradientName("Captain Augie", "#307DF8", "#51C4F5", "#B5B5B5", "#F0BC2D");
			  p.skin(name);
			  p.setC(34);
			  System.out.println("\"Set sail for winning!\"");
			  System.out.println();
			  p.addRole("tank");
			  p.addRole("support");
		    break;
		  case "Midnite":
			  p = new Player(2500, 275, start, name, x, y, 12, 10, 7);
			  name = p.getGradientName("Midnite", "#011CE4", "#1E88BF1", "#CE92DD", "#B8B7B7");
			  p.skin(name);
			  p.setC(21);
			  System.out.println("\"They’ll believe in ghosts soon.\"");
			  System.out.println();
			  p.addRole("brawler");
		    break;
		  case "Ashley":
			  p = new Player(2275, 200, start, name, x, y, 10, 10, 7);
			  name = p.getGradientName("Ashley", "#4ECAB6", "#4AE5E8", "#A296B7", "#F070E5");
			  p.skin(name);
			  p.setC(80);
			  System.out.println("\"Nature is our most precious resource. Let’s use it.\"");
			  System.out.println();
			  p.addRole("support");
		    break;
		  case "Radar":
			  p = new Player(2400, 300, start, name, x, y, 10, 10, 3);
			  name = p.getGradientName("Radar", "#8C79A0", "#AAA1A1", "#59854C");
			  p.skin(name);
			  p.setC(147);
			  System.out.println("\"They’ll never see me coming.\"");
			  System.out.println();
			  p.addRole("dive");
			  p.addRole("brawler");
		    break;
		  case "Oona":
			  p = new Player(2400, 225, start, name, x, y, 10, 10, 7);
			  name = p.getGradientName("Oona", "#F88A30", "#F54242", "#F755E1");
			  p.skin(name);
			  p.setC(202);
			  System.out.println("\"Time to turn this into our turf.\"");
			  System.out.println();
			  p.addRole("support");
		    break;
		  case "Dimentio":
			  p = new Player(3500, 250, start, name, x, y, 10, 10, 9);
			  name = p.getGradientName("Dimentio", "#F54242", "#ED390C", "#30F83E", "#55F785");
			  p.skin(name);
			  p.setC(202);
			  System.out.println("\"I always get what I want.\"");
			  System.out.println();
			  p.addRole("tank");
			  p.addRole("brawler");
		    break;
		  case "Rocco":
			  p = new Player(2325, 175, start, name, x, y, 10, 10, 5);
			  name = p.getGradientName("Rocco", "#457F15", "#4ADEAD", "#2C7696");
			  p.skin(name);
			  p.setC(65);
			  System.out.println("\"Wild tactics work 50% of the time.\"");
			  System.out.println();
			  p.addRole("brawler");
		    break;
		  case "Xara":
			  p = new Player(3500, 200, start, name, x, y, 10, 10, 6);
			  name = p.getGradientName("Xara", "#F38B2B", "#9345F2");
			  p.skin(name);
			  p.setC(129);
			  System.out.println("\"Nobody messes with an admin.\"");
			  System.out.println();
			  p.addRole("tank");
			  p.addRole("support");
		    break;
		  case "Thunder":
			  p = new Player(3900, 300, start, name, x, y, 9, 9, 7);
			  name = p.getGradientName("Thunder", "#F1D627", "#E1DC37", "#B0B0B0", "#696969");
			  p.skin(name);
			  p.setC(226);
			  System.out.println("\"Let’s crush them. Double time.\"");
			  System.out.println();
			  p.addRole("tank");
			  p.addRole("brawler");
		    break;
		  case "Archer":
			  p = new Player(2300, 175, start, name, x, y, 15, 8, 6);
			  name = p.getGradientName("Archer", "#57CF17", "#30C606", "#BC5B01", "#696969");
			  p.skin(name);
			  p.setC(76);
			  System.out.println("\"They have nowhere to hide.\"");
			  System.out.println();
			  p.addRole("brawler");
			  p.addRole("support");
		    break;
		  case "Tom":
			  p = new Player(3825, 275, start, name, x, y, 10, 10, 6);
			  name = p.getGradientName("Tom Phan", "#E01A1A", "#B70606", "#420000");
			  p.skin(name);
			  p.setC(196);
			  System.out.println("\"I will take everything away from them.\"");
			  System.out.println();
			  p.addRole("tank");
			  p.addRole("brawler");
		    break;
		  case "Gates":
			  p = new Player(3450, 200, start, name, x, y, 10, 10, 5);
			  name = p.getGradientName("Dr.Gates", "#06B794", "#49E4CA", "#3478A2");
			  p.skin(name);
			  p.setC(44);
			  System.out.println("\"Time to play with dimensional power.\"");
			  System.out.println();
			  p.addRole("tank");
			  p.addRole("support");
		    break;
		  case "Redgar":
			  p = new Player(2200, 150, start, name, x, y, 10, 10, 4);
			  name = p.getGradientName("Redgar", "#2980B9", "#637FBF", "#45D3D1");
			  p.skin(name);
			  p.setC(33);
			  System.out.println("\"Gonna show off my true worth today!\"");
			  System.out.println();
			  p.addRole("support");
		    break;
		  case "Cherry":
			  p = new Player(2300, 100, start, name, x, y, 12, 9, 9);
			  name = p.getGradientName("Cherry", "#EA98F0", "#F25FA6", "#F72222");
			  p.skin(name);
			  p.setC(196);
			  System.out.println("\"They won’t like the taste of my cherries after this.\"");
			  System.out.println();
			  p.addRole("support");
		    break;
		  case "Gash":
			  p = new Player(3400, 200, start, name, x, y, 10, 10, 7);
			  name = p.getGradientName("Gash", "#B72E01", "#B2552C", "#F72222");
			  p.skin(name);
			  p.setC(130);
			  System.out.println("\"Finish them off one by one.\"");
			  System.out.println();
			  p.addRole("tank");
			  p.addRole("brawler");
		    break;
		  case "Audrey":
			  p = new Player(2250, 150, start, name, x, y, 10, 10, 6);
			  name = p.getGradientName("Audrey", "#BC9CF7", "#72CBC9", "#40F2CE");
			  p.skin(name);
			  p.setC(111);
			  System.out.println("\"We are worlds and wonders above them.\"");
			  System.out.println();
			  p.addRole("support");
		    break;
		  case "Louis":
			  p = new Player(2350, 225, start, name, x, y, 12, 10, 6);
			  name = p.getGradientName("Louis", "#F0B128", "#F5D7B2", "#A1B0FC", "#9877F3");
			  p.skin(name);
			  p.setC(226);
			  System.out.println("\"Keep them back and away.\"");
			  System.out.println();
			  p.addRole("brawler");
		    break;
		  case "Kithara":
			  p = new Player(3450, 275, start, name, x, y, 10, 10, 7);
			  name = p.getGradientName("Kithara", "#5C1CF2", "#9877F3", "#3084C5", "#47799E");
			  p.skin(name);
			  p.setC(93);
			  System.out.println("\"The stories about me are about to become a reality.\"");
			  System.out.println();
			  p.addRole("tank");
			  p.addRole("brawler");
		    break;
		  case "Angelos":
			  p = new Player(3700, 150, start, name, x, y, 12, 12, 8);
			  name = p.getGradientName("Angelos", "#A9C3FE", "#647EFF", "#74F8FB");
			  p.skin(name);
			  p.setC(105);
			  System.out.println("\"I will worry about the dimensions. Leave it to me.\"");
			  System.out.println();
			  p.addRole("tank");
			  p.addRole("support");
		    break;
		  case "Burt":
			  p = new Player(2400, 250, start, name, x, y, 10, 11, 5);
			  name = p.getGradientName("Burt", "#004CFF", "#5093B4");
			  p.skin(name);
			  p.setC(21);
			  System.out.println("\"Slicing and dicing. Just how I like it.\"");
			  System.out.println();
			  p.addRole("brawler");
		    break;
		  case "Bolo":
			  p = new Player(2500, 275, start, name, x, y, 13, 9, 7);
			  name = p.getGradientName("Bolo", "#00FF1E", "#66B450");
			  p.skin(name);
			  p.setC(47);
			  System.out.println("\"I’m still young enough to hit my shots.\"");
			  System.out.println();
			  p.addRole("brawler");
		    break;
		  case "Anjelika":
			  p = new Player(3500, 250, start, name, x, y, 10, 10, 6);
			  name = p.getGradientName("Anjelika", "#F0C014", "#E09B06", "#F2975A");
			  p.skin(name);
			  p.setC(214);
			  System.out.println("\"Good always rises above evil.\"");
			  System.out.println();
			  p.addRole("dive");
			  p.addRole("tank");
			  p.addRole("brawler");
		    break;
		  case "Melony":
			  p = new Player(2350, 175, start, name, x, y, 11, 9, 6);
			  name = p.getGradientName("Melony", "#F4E68B", "#FF9EF2", "#4725F4");
			  p.skin(name);
			  p.setC(227);
			  System.out.println("\"My creatures are gonna mow them down.\"");
			  System.out.println();
			  p.addRole("support");
		    break;
		  case "Echo":
			  p = new Player(2450, 200, start, name, x, y, 12, 10, 7);
			  name = p.getGradientName("Echo", "#70F5EC", "#9ED7E6", "#BDC8FF");
			  p.skin(name);
			  p.setC(87);
			  System.out.println("\"I hear everything they do.\"");
			  System.out.println();
			  p.addRole("brawler");
			  p.addRole("engineer");
		    break;
		  case "Makani":
			  p = new Player(2350, 175, start, name, x, y, 10, 10, 6);
			  name = p.getGradientName("Makani", "#0876BA", "#3BDEDB", "#C4BB5A", "#BEE2F3");
			  p.skin(name);
			  p.setC(27);
			  System.out.println("\"Blow them out of the way.\"");
			  System.out.println();
			  p.addRole("dive");
			  p.addRole("brawler");
		    break;
		  case "Rhythm":
			  p = new Player(2350, 175, start, name, x, y, 11, 11, 5);
			  name = p.getGradientName("Rhythm", "#00A0FE", "#F61732", "#FDB10E", "#169A13");
			  p.skin(name);
			  p.setC(45);
			  System.out.println("\"Let’s keep our beat going!\"");
			  System.out.println();
			  p.addRole("support");
		    break;
		  case "Grenadine":
			  p = new Player(2525, 200, start, name, x, y, 10, 10, 6);
			  name = p.getGradientName("Grenadine", "#FF944D", "#F05CD0", "#D41C1C");
			  p.skin(name);
			  p.setC(202);
			  System.out.println("\"Boom Boom Boom!\"");
			  System.out.println();
			  p.addRole("dive");
			  p.addRole("brawler");
			  p.addRole("engineer");
		    break;
		  case "Patitek":
			  p = new Player(3900, 200, start, name, x, y, 10, 10, 7);
			  name = p.getGradientName("Patitek", "#D76304", "#FF5C5C", "#D77C14");
			  p.skin(name);
			  p.setC(196);
			  System.out.println("\"They gotta get past me first.\"");
			  System.out.println();
			  p.addRole("tank");
		    break;
		  case "Crystal":
			  p = new Player(3625, 200, start, name, x, y, 10, 10, 7);
			  name = p.getGradientName("Crystal", "#F186F9", "#647EFF", "#DA9A10");
			  p.skin(name);
			  p.setC(219);
			  System.out.println("\"Today, we make history!\"");
			  System.out.println();
			  p.addRole("tank");
			  p.addRole("support");
			  p.addRole("engineer");
		    break;
		  case "Velvet":
			  p = new Player(2500, 200, start, name, x, y, 10, 10, 7);
			  name = p.getGradientName("Velvet", "#F7C5C5", "#E7AAB7", "#DB8192", "#C62744", "#79113A");
			  p.skin(name);
			  p.setC(203);
			  System.out.println("\"You miss 100% of the shots you don’t take.\"");
			  System.out.println();
			  p.addRole("brawler");
		    break;
		  case "Drift":
			  p = new Player(2400, 175, start, name, x, y, 8, 11, 6);
			  name = p.getGradientName("Drift", "#F71D1D", "#A7A439", "#000000");
			  p.skin(name);
			  p.setC(160);
			  System.out.println("\"Speed, I am Speed.\"");
			  System.out.println();
			  p.addRole("dive");
			  p.addRole("brawler");
		    break;
		  case "Snowfall":
			  p = new Player(2375, 300, start, name, x, y, 10, 10, 7);
			  name = p.getGradientName("Snowfall", "#BFF7DE", "#98DCF8", "#52BDFF", "#2D97FB");
			  p.skin(name);
			  p.setC(85);
			  System.out.println("\"I’m not cold hearted, I’m just chill.\"");
			  System.out.println();
			  p.addRole("brawler");
		    break;
		  case "Shutter":
			  p = new Player(2400, 175, start, name, x, y, 10, 10, 5);
			  name = p.getGradientName("Shutter", "#619AF1", "#E0C4CD", "#9299A3");
			  p.skin(name);
			  p.setC(75);
			  System.out.println("\"Say Cheese!\"");
			  System.out.println();
			  p.addRole("support");
		    break;
		  case "Magnet":
			  p = new Player(3475, 200, start, name, x, y, 10, 10, 7);
			  name = p.getGradientName("Magnet", "#F07777", "#DDDDDD", "#CCCCCC", "#8792E5");
			  p.skin(name);
			  p.setC(208);
			  System.out.println("\"Let’s shock those guys out there.\"");
			  System.out.println();
			  p.addRole("tank");
			  p.addRole("brawler");
			  p.addRole("engineer");
		    break;
		  case "Jing":
			  p = new Player(2425, 225, start, name, x, y, 11, 10, 7);
			  name = p.getGradientName("Li Jing", "#F3B63F", "#BE4939", "#DC6949", "#F3B63F");
			  p.skin(name);
			  p.setC(220);
			  System.out.println("\"Today is a gift. That’s why they call it the present.\"");
			  System.out.println();
			  p.addRole("brawler");
			  p.addRole("support");
		    break;
		  case "Folden":
			  p = new Player(2375, 150, start, name, x, y, 10, 10, 7);
			  name = p.getGradientName("Folden", "#FBC396", "#7DDDF5", "#AEA0E8");
			  p.skin(name);
			  p.setC(229);
			  System.out.println("\"Joy is something you create yourself.\"");
			  System.out.println();
			  p.addRole("brawler");
			  p.addRole("support");
		    break;
		  case "Bladee":
			  p = new Player(2350, 100, start, name, x, y, 9, 9, 7);
			  name = p.getGradientName("Bladee", "#F4E4CC", "#D5BE98", "#55AF7B");
			  p.skin(name);
			  p.setC(223);
			  System.out.println("\"No utility stands a chance against me.\"");
			  System.out.println();
			  p.addRole("brawler");
		    break;
		  case "Margo":
			  p = new Player(2350, 125, start, name, x, y, 10, 10, 6);
			  name = p.getGradientName("Margo", "#8C8CFC", "#8494C4", "#011697");
			  p.skin(name);
			  p.setC(69);
			  System.out.println("\"Pull up a stool, night’s just getting started.\"");
			  System.out.println();
			  p.addRole("brawler");
			  p.addRole("support");
		    break;
		  case "Ivy":
			  p = new Player(2425, 200, start, name, x, y, 10, 10, 9);
			  name = p.getGradientName("Ivy", "#26C485", "#E18AD4");
			  p.skin(name);
			  p.setC(77);
			  System.out.println("\"Medic by your shoulder!\"");
			  System.out.println();
			  p.addRole("dive");
			  p.addRole("support");
		    break;
		  case "Petra":
			  p = new Player(3525, 175, start, name, x, y, 11, 11, 7);
			  name = p.getGradientName("Petra", "#55868C", "#C8AB83", "#7F636E");
			  p.skin(name);
			  p.setC(112);
			  System.out.println("\"Adventure is out there!\"");
			  System.out.println();
			  p.addRole("dive");
			  p.addRole("tank");
			  p.addRole("brawler");
		    break;
		  case "Quincy":
			  p = new Player(2825, 225, start, name, x, y, 10, 10, 5);
			  name = p.getGradientName("Quincy", "#C7AC92", "#FFB997");
			  p.skin(name);
			  p.setC(136);
			  System.out.println("\"I got our backline, promise.\"");
			  System.out.println();
			  p.addRole("support");
		    break;
		  case "Unice":
			  p = new Player(2475, 250, start, name, x, y, 10, 10, 9);
			  name = p.getGradientName("Unice", "#635255", "#CE7B91", "#9F2042");
			  p.skin(name);
			  p.setC(58);
			  System.out.println("\"Rain or shine, one gust at a time.\"");
			  System.out.println();
			  p.addRole("dive");
			  p.addRole("brawler");
			  p.addRole("engineer");
		    break;
		  case "Flor":
			  p = new Player(2375, 125, start, name, x, y, 11, 10, 6);
			  name = p.getGradientName("Flor", "#FCD757", "#5E5B52");
			  p.skin(name);
			  p.setC(136);
			  System.out.println("\"Just bee yourself!\"");
			  System.out.println();
			  p.addRole("support");
			  p.addRole("engineer");
		    break;
		  case "Yuri":
			  p = new Player(2450, 350, start, name, x, y, 9, 9, 5);
			  name = p.getGradientName("Yuri", "#D0DB97", "#69B578", "#3A7D44");
			  p.skin(name);
			  p.setC(154);
			  System.out.println("\"Upgrades people, upgrades.\"");
			  System.out.println();
			  p.addRole("brawler");
			  p.addRole("support");
			  p.addRole("engineer");
		    break;
		  case "Millie":
			  p = new Player(3375, 75, start, name, x, y, 11, 10, 4);
			  name = p.getGradientName("Millie", "#FD77D9", "#CD914C", "#808085");
			  p.skin(name);
			  p.setC(208);
			  System.out.println("\"I make my own options.\"");
			  System.out.println();
			  p.addRole("tank");
			  p.addRole("brawler");
			  p.addRole("engineer");
		    break;
		  case "Leaf":
			  p = new Player(3675, 75, start, name, x, y, 11, 11, 6);
			  name = p.getGradientName("Professor Leaf", "#EDEEC0", "#433E0E", "#7C9082");
			  p.skin(name);
			  p.setC(149);
			  System.out.println("\"Earth’s mysteries, revealed.\"");
			  System.out.println();
			  p.addRole("tank");
			  p.addRole("support");
		    break;
		  case "Courtney":
			  p = new Player(2350, 350, start, name, x, y, 8, 12, 6);
			  name = p.getGradientName("Courtney", "#DAA89B", "#690375", "#2C0E37");
			  p.skin(name);
			  p.setC(112);
			  System.out.println("\"Strap in and hold tight!\"");
			  System.out.println();
			  p.addRole("dive");
			  p.addRole("brawler");
		    break;
		  case "Divine":
			  p = new Player(2500, 25, start, name, x, y, 10, 10, 7);
			  name = p.getGradientName("Divine", "#F786AA", "#FFA8A9", "#CDB2AB");
			  p.skin(name);
			  p.setC(219);
			  System.out.println("\"The stars will guide our path!\"");
			  System.out.println();
			  p.addRole("brawler");
			  p.addRole("support");
			  p.addRole("engineer");
		    break;
		  case "Gambit":
			  p = new Player(3550, 200, start, name, x, y, 9, 8, 6);
			  name = p.getGradientName("Sir Gambit", "#A17C6B", "#B0B5B3", "#706F6F");
			  p.skin(name);
			  p.setC(136);
			  System.out.println("\"This will be an easy checkmate.\"");
			  System.out.println();
			  p.addRole("dive");
			  p.addRole("tank");
			  p.addRole("support");
		    break;
		  case "Cloud":
			  p = new Player(2500, 100, start, name, x, y, 8, 7, 9);
			  name = p.getGradientName("Cloud", "#5E00BD", "#300693");
			  p.skin(name);
			  p.setC(56);
			  System.out.println("\"Welcome to your final hour.\"");
			  System.out.println();
			  p.addRole("brawler");
			  p.addRole("engineer");
		    break;
		  case "Winnie":
			  p = new Player(2400, 200, start, name, x, y, 11, 10, 6);
			  name = p.getGradientName("Winnie", "#C1A5A9", "#F08CAE", "#9A4C95", "#EB7BC0");
			  p.skin(name);
			  p.setC(223);
			  System.out.println("\"Now this, this is art!\"");
			  System.out.println();
			  p.addRole("dive");
			  p.addRole("support");
			  p.addRole("engineer");
		    break;
		  case "Pearl":
			  p = new Player(2425, 275, start, name, x, y, 10, 10, 5);
			  name = p.getGradientName("Pearl", "#CDA667", "#C7B6A5", "#F37EA8");
			  p.skin(name);
			  p.setC(3);
			  System.out.println("\"Every shell tells a story.\"");
			  System.out.println("\"ᵂᵉ ᵃʳᵉ ᵍᵒⁿⁿᵃ ʷᵘᶦⁿ ᵗʰᵉᶦʳ ᶜʷᵉʷ!\"");
			  System.out.println();
			  p.addRole("brawler");
		    break;
		  case "Andrew":
			  p = new Player(2400, 275, start, name, x, y, 10, 10, 7);
			  name = p.getGradientName("Andrew", "#ABABAB", "#FD637A");
			  p.skin(name);
			  p.setC(250);
			  System.out.println("\"They will hear of Sockyman’s wrath.\"");
			  System.out.println();
			  p.addRole("brawler");
			  p.addRole("engineer");
		    break;
		  case "Orchid":
			  p = new Player(2150, 250, start, name, x, y, 9, 9, 7);
			  name = p.getGradientName("Orchid", "#F8AD2A", "#C48021", "#64F218");
			  p.skin(name);
			  p.setC(214);
			  System.out.println("\"Their paths end with me.\"");
			  System.out.println();
			  p.addRole("brawler");
			  p.addRole("support");
		    break;
		  case "Everest":
			  p = new Player(2275, 150, start, name, x, y, 10, 10, 7);
			  name = p.getGradientName("Everest", "#66F0B2", "#5671F5", "#2A134E");
			  p.skin(name);
			  p.setC(85);
			  System.out.println("\"Amid chaos, we will find harmony.\"");
			  System.out.println();
			  p.addRole("support");
		    break;
		  case "Clementine":
			  p = new Player(2900, 225, start, name, x, y, 11, 10, 6);
			  name = p.getGradientName("Clementine", "#F3A639", "#FB9550", "#E760F0", "#363CE7");
			  p.skin(name);
			  p.setC(208);
			  System.out.println("\"Rolling out. Let’s do this!\"");
			  System.out.println();
			  p.addRole("tank");
		    break;
		  case "Deny":
			  p = new Player(2550, 100, start, name, x, y, 11, 11, 7);
			  name = p.getGradientName("D3/NY", "#A3EAFF", "#A6B0CE", "#6C2920");
			  p.skin(name);
			  p.setC(81);
			  System.out.println("\"Suppress them to get the last laugh.\"");
			  System.out.println();
			  p.addRole("brawler");
		    break;
		  case "Rin":
			  p = new Player(2450, 250, start, name, x, y, 10, 10, 9);
			  name = p.getGradientName("Rin", "#F3952B", "#C66D24");
			  p.skin(name);
			  p.setC(216);
			  System.out.println("\"Our bond is our strength.\"");
			  System.out.println();
			  p.addRole("dive");
			  p.addRole("brawler");
		    break;
		  case "Victor":
			  p = new Player(3750, 50, start, name, x, y, 10, 10, 5);
			  name = p.getGradientName("Victor", "#52494C", "#B9A167", "#AF8561");
			  p.skin(name);
			  p.setC(58);
			  System.out.println("\"Steam and steel, ready to defend and conquer.\"");
			  System.out.println();
			  p.addRole("tank");
			  p.addRole("brawler");
		    break;
		  case "Isabelle":
			  p = new Player(2300, 125, start, name, x, y, 12, 9, 6);
			  name = p.getGradientName("Isabelle", "#DAB6FC", "#BBADFF", "#9FA0FF", "#8E94F2");
			  p.skin(name);
			  p.setC(177);
			  System.out.println("\"Another day, another battlefield.\"");
			  System.out.println();
			  p.addRole("support");
			  p.addRole("engineer");
		    break;
		  case "Lumiere":
			  p = new Player(2375, 275, start, name, x, y, 9, 9, 7);
			  name = p.getGradientName("Lumière", "#73EEDC", "#67AAF9", "#9BBDF9");
			  p.skin(name);
			  p.setC(121);
			  System.out.println("\"My light will set the stage.\"");
			  System.out.println();
			  p.addRole("brawler");
			  p.addRole("support");
			  p.addRole("engineer");
		    break;
		  case "Willow":
			  p = new Player(2325, 225, start, name, x, y, 10, 12, 6);
			  name = p.getGradientName("Willow", "#E35BF9", "#6D33B4");
			  p.skin(name);
			  p.setC(201);
			  System.out.println("\"It’s never over until it’s over.\"");
			  System.out.println();
			  p.addRole("dive");
			  p.addRole("brawler");
			  p.addRole("support");
		    break;
		  case "Jazz":
			  p = new Player(3800, 75, start, name, x, y, 11, 10, 5);
			  name = p.getGradientName("Jazz", "#5D737E", "#4C5454");
			  p.skin(name);
			  p.setC(7);
			  System.out.println("\"All forces unleashed!\"");
			  System.out.println();
			  p.addRole("dive");
			  p.addRole("tank");
			  p.addRole("engineer");
		    break;
		  case "Harper":
			  p = new Player(2400, 250, start, name, x, y, 13, 8, 7);
			  name = p.getGradientName("Harper", "#FE4E00", "#F9627D", "#5B3758");
			  p.skin(name);
			  p.setC(202);
			  System.out.println("\"This town ain’t big enough for them.\"");
			  System.out.println();
			  p.addRole("brawler");
			  p.addRole("engineer");
		    break;
		  case "Noah":
			  p = new Player(2375, 175, start, name, x, y, 8, 10, 6);
			  name = p.getGradientName("Noah", "#5B618A", "#41658A");
			  p.skin(name);
			  p.setC(75);
			  System.out.println("\"I will break any friendships they have.\"");
			  System.out.println();
			  p.addRole("support");
		    break;
		  case "Jade":
			  p = new Player(2475, 200, start, name, x, y, 10, 10, 5);
			  name = p.getGradientName("Jade", "#2EBFA5", "#AAFFE5");
			  p.skin(name);
			  p.setC(79);
			  System.out.println("\"Every throw counts.\"");
			  System.out.println();
			  p.addRole("dive");
			  p.addRole("brawler");
		    break;
		  case "Stellar":
			  p = new Player(3700, 225, start, name, x, y, 7, 10, 7);
			  name = p.getGradientName("Stellar", "#7692FF", "#2B3A67", "#29335C");
			  p.skin(name);
			  p.setC(7);
			  System.out.println("\"Aura unmatched.\"");
			  System.out.println();
			  p.addRole("tank");
			  p.addRole("brawler");
		    break;
		  case "Bonbon":
			  p = new Player(2350, 275, start, name, x, y, 10, 10, 6);
			  name = p.getGradientName("Bonbon", "#FF4D4D", "#6E39FE", "#CC7700");
			  p.skin(name);
			  p.setC(197);
			  System.out.println("\"I won’t sugarcoat it. They are in for a treat.\"");
			  System.out.println();
			  p.addRole("dive");
			  p.addRole("brawler");
			  p.addRole("support");
		    break;
		  case "Boss:Finley":
			  p = new Player(8550, 675, start, name, x, y, 15, 15, 12);
			  name = p.getGradientName("Finley - Merge Monster", "#A40000", "#450093");
			  p.skin(name);
			  p.setC(124);
			  System.out.println("\"This world shall be no more.\"");
			  System.out.println();
			  p.addRole("dive");
			  p.addRole("tank");
			  p.addRole("brawler");
			  p.addRole("support");
			  p.addRole("engineer");
			  p.addRole("boss");
		    break;
		  case "Bot":
			  p = new Player(0, 275, start, name, 100, 100, 10, 10, 6);
			  name = "bot";
			  p.skin(name);
			  p.setC(197);
		    break;
		}
		return p;
	}
	
	public static void CheckProfile(Player x, Party p) {
		p.showRoster(x);
		Scanner input = new Scanner(System.in);
		System.out.print("Who's profile do you want to see: ");
		String targetResponse = input.next();
		System.out.println();
		if(targetResponse.equals("1")) {
			if(p.getRoster()[0].isAlive()) {
				System.out.println(p.getRoster()[0].getSkin() + "'s Profile:");
				System.out.println(p.getRoster()[0]);
			}else {
				System.out.println(p.getRoster()[0].getSkin() + " is downed!");
			}
		}
		if(targetResponse.equals("2")) {
			if(p.getRoster()[1].isAlive()) {
				System.out.println(p.getRoster()[1].getSkin() + "'s Profile:");
				System.out.println(p.getRoster()[1]);
			}else {
				System.out.println(p.getRoster()[1].getSkin() + " is downed!");
			}
		}
		if(targetResponse.equals("3")) {
			if(p.getRoster()[2].isAlive()) {
				System.out.println(p.getRoster()[2].getSkin() + "'s Profile:");
				System.out.println(p.getRoster()[2]);
			}else {
				System.out.println(p.getRoster()[2].getSkin() + " is downed!");
			}
		}
		System.out.println();
	}
	
	public static void Jump(Player p, Player a, Player b, Location l, Player e1, Player e2, Player e3) {
		p.resetCover();
		boolean umbrella = false;
		boolean mochi = false;
		boolean peri = false;
		Scanner input = new Scanner(System.in);
		int jumpD = 6;
		if (p.getName().equals("Unice")) {
			jumpD = 9;
		}
		for(int q = 0; q < GameSim.utility.size(); q++) {
			if(GameSim.utility.get(q).getName().equals("Umbrella") && GameSim.utility.get(q).isAlly(p) && p.getLoc().eqLoc(GameSim.utility.get(q).getLoc())) {
				umbrella = true;
			}
		}
		for(int q = 0; q < GameSim.utility.size(); q++) {
			if(GameSim.utility.get(q).getName().equals("Mochi") && GameSim.utility.get(q).isAlly(p) && p.getLoc().eqLoc(GameSim.utility.get(q).getLoc())) {
				mochi = true;
			}
		}
		for(int q = 0; q < GameSim.utility.size(); q++) {
			if(GameSim.utility.get(q).getName().equals("Peri") && GameSim.utility.get(q).isAlly(p) && p.getLoc().eqLoc(GameSim.utility.get(q).getLoc())) {
				mochi = true;
			}
		}
		for(int q = 0; q < GameSim.utility.size(); q++) {
			if(GameSim.utility.get(q).getName().equals("Support") && GameSim.utility.get(q).isEnemy(p)) {
				System.out.println("Cannot jump with the enemy Sockyman on the field!");
				System.out.println();
				return;
			}
		}
		
		if(!p.canJump()) {
			System.out.println("No more jumps left!");
			System.out.println();
			return;
		}
		
		if (!p.isHoverDashing() && !p.hasDoubleJumped() && !p.getName().equals("Courtney")) {
			if((!p.getLoc().eqLoc(a.getLoc()) || a.isCorrupt()) && (!p.getLoc().eqLoc(b.getLoc()) || b.isCorrupt()) && !GameSim.b.hasBounce(p.getLoc().getX(), p.getLoc().getY()) && ! peri && !umbrella && !mochi && !p.getName().equals("Gambit")) {
				System.out.println("Nothing in range to use a jump with!");
				System.out.println();
				return;
			}
			if (p.getName().equals("Gambit") && p.isParalyzed()) {
				System.out.println("Cannot jump with the Knight's horse while paralyzed!");
				System.out.println();
				return;
			}
			if (p.drillDashed()) {
				System.out.println("Cannot jump after drill dashing!");
				System.out.println();
				return;
			}

			if(!p.inReach(l, jumpD)) {
				System.out.println("Can't jump there!");
				System.out.println();
				return;
			}
			if(GameSim.b.hasTrench(l.getX(), l.getY()) && !p.getHover()) {
				System.out.println("Cannot jump into a trench!");
				System.out.println();
				return;
			}
		}
		if(peri && p.canJump()) {
			int d = p.getLoc().distance(l);
			p.increaseTotalMovement(d);
			p.getLoc().set(l.getX(), l.getY());
			p.useJump();
		}else if(mochi && p.canJump()) {
			int d = p.getLoc().distance(l);
			p.increaseTotalMovement(d);
			p.getLoc().set(l.getX(), l.getY());
			p.useJump();
		}else if(umbrella && p.canJump()) {
			int d = p.getLoc().distance(l);
			p.increaseTotalMovement(d);
			p.getLoc().set(l.getX(), l.getY());
			p.useJump();
			for(int q = 0; q < GameSim.utility.size(); q++) {
				if(GameSim.utility.get(q).getName().equals("Umbrella") && GameSim.utility.get(q).isAlly(p)) {
					GameSim.utility.get(q).getOwner().resetAttack();
					if (GameSim.utility.get(q).getOwner().ultActive()) {
						GameSim.utility.get(q).getOwner().reduceCooldown();
					}
					GameSim.utility.remove(q);
				}
			}
		}else if(GameSim.b.hasBounce(p.getLoc().getX(), p.getLoc().getY()) && p.canJump()) {
			int d = p.getLoc().distance(l);
			p.increaseTotalMovement(d);
			p.getLoc().set(l.getX(), l.getY());
			p.useJump();
		}else if(p.getLoc().eqLoc(a.getLoc()) && p.canJump()) {
			if(!a.isAlive() || a.isStunned()) {
				System.out.println("Ally is downed or stunned! Cannot perform a jump.");
				System.out.println();
				return;
			}else {
				int d = p.getLoc().distance(l);
				p.increaseTotalMovement(d);
				p.getLoc().set(l.getX(), l.getY());
				p.useJump();
			}
		}else if(p.getLoc().eqLoc(b.getLoc()) && p.canJump()) {
			if(!b.isAlive() || b.isStunned()) {
				System.out.println("Ally is downed or stunned! Cannot perform a jump.");
				System.out.println();
				return;
			}else {
				int d = p.getLoc().distance(l);
				p.increaseTotalMovement(d);
				p.getLoc().set(l.getX(), l.getY());
				p.useJump();
			}
		}else if(p.canJump() && p.getName().equals("Gambit")) {
			int d = p.getLoc().distance(l);
			p.increaseTotalMovement(d);
			p.getLoc().set(l.getX(), l.getY());
			p.useJump();
		}
		for(int i = 0; i < cover.size(); i++) {
			if(p.getLoc().eqLoc(cover.get(i).getLoc())) {
				if(cover.get(i).getName().equals("Full")) {
					p.setCover("Full");
					break;
				}
				if(cover.get(i).getName().equals("Partial")) {
					p.setCover("Partial");
					break;
				}
			}else {
				p.resetCover();
			}
		}
		for(int q = 0; q < GameSim.utility.size(); q++) {
			if(GameSim.utility.get(q).getName().equals("Peri") && GameSim.utility.get(q).isAlly(p) && p.getLoc().eqLoc(GameSim.utility.get(q).getLoc())&& p.getCover().equals("None")) {
				p.setCover("Partial");
			}
		}
		for(int i = 0; i < orbs.size(); i++) {
			if(p.getLoc().eqLoc(orbs.get(i).getLoc()) && !p.ultReady()) {
				p.getOrb();
				orbs.remove(i);
			}
		}
		if(p.jumpHeal() && p.getName().equals("Melony")) {
			p.heal(0.05);
			p.addHealing(p.getMaxHP() * 0.05);
			if (p.inRange(a)) {
				a.heal(0.05);
				p.addHealing(a.getMaxHP() * 0.05);
			}
			if (p.inRange(b)) {
				b.heal(0.05);
				p.addHealing(b.getMaxHP() * 0.05);
			}
		}
		for(int i = 0; i < utility.size(); i++) {
			if(p.getLoc().eqLoc(utility.get(i).getLoc())) {
				if(utility.get(i).getName().equals("Swiftwing") && utility.get(i).owner(p) && !utility.get(i).getPickUp()) {
					System.out.println("There's my buddy!");
					p.resetCooldown();
					utility.get(i).pickedUp();
					System.out.println();
					break;
				}
				if(utility.get(i).getName().equals("Howler") && utility.get(i).owner(p) && !utility.get(i).getPickUp()) {
					System.out.println("You want more? Here's more!");
					p.refreshUlt();
					utility.get(i).pickedUp();
					System.out.println();
					break;
				}
			}
		}
		
		if(p.inRange(e1) && p.getJumpDamage()) {
			e1.takeDamage(e1.getMaxHP() * 0.05);
			p.addDamage(e1.getMaxHP() * 0.05);
		}
		if(p.inRange(e2) && p.getJumpDamage()) {
			e2.takeDamage(e2.getMaxHP() * 0.05);
			p.addDamage(e2.getMaxHP() * 0.05);
		}
		if(p.inRange(e3) && p.getJumpDamage()) {
			e3.takeDamage(e3.getMaxHP() * 0.05);
			p.addDamage(e3.getMaxHP() * 0.05);
		}
		
		if (p.isHoverDashing()) {
			if(p.getLoc().eqLoc(e1.getLoc())) {
				e1.takeDamage(175);
				p.addDamage(175);
			}
			if(p.getLoc().eqLoc(e2.getLoc())) {
				e2.takeDamage(175);
				p.addDamage(175);
			}
			if(p.getLoc().eqLoc(e3.getLoc())) {
				e3.takeDamage(175);
				p.addDamage(175);
			}
		}
		int d2 = p.getLoc().distance(l);
		if (p.getName().equals("Clementine") && p.isTank() && d2 < 4) {
			if (p.inRange(e1)) {
				e1.knockbacked(l);
			}
			if (p.inRange(e2)) {
				e2.knockbacked(l);
			}
			if (p.inRange(e3)) {
				e3.knockbacked(l);
			}
			System.out.println("\"Piledriver!\"");
		}
		
		try {
			String audio = "audio/jump.wav";
			Music victoryPlayer = new Music(audio, false); 
			victoryPlayer.play();
		}catch (Exception e) {
			System.out.println(e);
		}
		System.out.println("Jumped to " + p.getLoc() + ".");
		System.out.println();
		if (p.isHoverDashing() && !p.hasDoubleJumped()) {
			p.setDoubleJump();
			p.addJumps(1);
			Location l2 = SetCursor(p, e1, e2, e3, a, b, p.getRange());
			if(umbrella && p.canJump()) {
				int d = p.getLoc().distance(l2);
				p.increaseTotalMovement(d);
				p.getLoc().set(l2.getX(), l2.getY());
				p.useJump();
				for(int q = 0; q < GameSim.utility.size(); q++) {
					if(GameSim.utility.get(q).getName().equals("Umbrella") && GameSim.utility.get(q).isAlly(p)) {
						GameSim.utility.get(q).getOwner().resetAttack();
						if (GameSim.utility.get(q).getOwner().ultActive()) {
							GameSim.utility.get(q).getOwner().reduceCooldown();
						}
						GameSim.utility.remove(q);
					}
				}
			}else {
				int d = p.getLoc().distance(l2);
				p.increaseTotalMovement(d);
				p.getLoc().set(l2.getX(), l2.getY());
				p.useJump();
			}
			for(int i = 0; i < cover.size(); i++) {
				if(p.getLoc().eqLoc(cover.get(i).getLoc())) {
					if(cover.get(i).getName().equals("Full")) {
						p.setCover("Full");
						break;
					}
					if(cover.get(i).getName().equals("Partial")) {
						p.setCover("Partial");
						break;
					}
				}else {
					p.resetCover();
				}
			}
			for(int q = 0; q < GameSim.utility.size(); q++) {
				if(GameSim.utility.get(q).getName().equals("Peri") && GameSim.utility.get(q).isAlly(p) && p.getLoc().eqLoc(GameSim.utility.get(q).getLoc())&& p.getCover().equals("None")) {
					p.setCover("Partial");
					break;
				}
			}
			for(int i = 0; i < orbs.size(); i++) {
				if(p.getLoc().eqLoc(orbs.get(i).getLoc()) && !p.ultReady()) {
					p.getOrb();
					orbs.remove(i);
				}
			}
			
			if (p.isHoverDashing()) {
				if(p.getLoc().eqLoc(e1.getLoc())) {
					e1.takeDamage(175);
					p.addDamage(175);
				}
				if(p.getLoc().eqLoc(e2.getLoc())) {
					e2.takeDamage(175);
					p.addDamage(175);
				}
				if(p.getLoc().eqLoc(e3.getLoc())) {
					e3.takeDamage(175);
					p.addDamage(175);
				}
			}
			
			try {
				String audio = "audio/jump.wav";
				Music victoryPlayer = new Music(audio, false); 
				victoryPlayer.play();
			}catch (Exception e) {
				System.out.println(e);
			}
			System.out.println("Jumped to " + p.getLoc() + ".");
			System.out.println();
		}
	}
	
	public static void Dash(Player p, Player a, Player b, Player c) {
		if(!p.canDash()) {
			System.out.println("No more dashes left!");
			System.out.println();
			return;
		}
		if (p.isSupressed()) {
			System.out.println("Cannot dash while supressed!");
			System.out.println();
			return;
		}
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).getName().equals("Turret") && utility.get(j).owner(p) && p.getLoc().eqLoc(GameSim.utility.get(j).getLoc())) {
				GameSim.utility.get(j).activate();
				System.out.println("Turret back online.");
				try {
					String audio = "audio/dash.wav";
					Music victoryPlayer = new Music(audio, false); 
					victoryPlayer.play();
				}catch (Exception e) {
					System.out.println(e);
				}
			}
		}
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).getName().equals("Rook") && (utility.get(j).owner(a) || utility.get(j).owner(b) || utility.get(j).owner(c)) && p.getLoc().eqLoc(GameSim.utility.get(j).getLoc())) {
				GameSim.utility.get(j).setRookActive(false);
				System.out.println("Enemy Rook Tower deactivated.");
				try {
					String audio = "audio/dash.wav";
					Music victoryPlayer = new Music(audio, false); 
					victoryPlayer.play();
				}catch (Exception e) {
					System.out.println(e);
				}
			}
		}
		ArrayList<Player> dashPlayers = new ArrayList<Player>();
		double damage = 100;
		if(p.getLoc().eqLoc(a.getLoc()) && p.canDash() && a.isAlive()) {
			dashPlayers.add(a);
		}
		if(p.getLoc().eqLoc(b.getLoc()) && p.canDash() && b.isAlive()) {
			dashPlayers.add(b);
		}
		if(p.getLoc().eqLoc(c.getLoc()) && p.canDash() && c.isAlive()) {
			dashPlayers.add(c);
		}
		if(dashPlayers.size() == 2) {
			damage = 75;
		}
		if(dashPlayers.size() == 3) {
			damage = 50;
		}
		for(Player e: dashPlayers) {
			e.dashedOn();
			if (e.isFortified()) {
				e.takeDamage(damage / 2);
				p.addDamage(damage / 2);
			}else {
				e.takeDamage(damage);
				p.addDamage(damage);
			}
			if (e.getBee()) {
				p.takeDamage(350);
				ArrayList<Effect> e1 = new ArrayList<Effect>();
				Effect MaxParalyze = new Effect("paralyze", 0, 1);
				e1.add(MaxParalyze);
				p.addEffects(e1);
				p.applyEffects();
			}
			if (e.getName().equals("Pearl")) {
				e.smolluskDash();
			}
			for(int j = 0; j < GameSim.utility.size(); j++) {
				if(GameSim.utility.get(j).getName().equals("Peri") && GameSim.utility.get(j).owner(e) && p.inRange(GameSim.utility.get(j).getLoc(), 12)) {
					p.takeDamage(275);
					GameSim.utility.get(j).getOwner().addDamage(275);
				}
			}
			e.dashOff();
		}
		if(dashPlayers.size() > 0) {
			try {
				String audio = "audio/dash.wav";
				Music victoryPlayer = new Music(audio, false); 
				victoryPlayer.play();
			}catch (Exception e) {
				System.out.println(e);
			}
			p.useDash();
		}
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).getName().equals("Sensor") && (utility.get(j).owner(a) || utility.get(j).owner(b) || utility.get(j).owner(c)) && p.getLoc().eqLoc(GameSim.utility.get(j).getLoc()) && (p.canDash() || p.isHoverDashing())) {
				GameSim.utility.remove(j);
				System.out.println("Enemy sound sensor destroyed.");
				p.useDash();
				try {
					String audio = "audio/dash.wav";
					Music victoryPlayer = new Music(audio, false); 
					victoryPlayer.play();
				}catch (Exception e) {
					System.out.println(e);
				}
			}
		}
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).getName().equals("Sphere") && (utility.get(j).owner(a) || utility.get(j).owner(b) || utility.get(j).owner(c)) && p.getLoc().eqLoc(GameSim.utility.get(j).getLoc()) && (p.canDash() || p.isHoverDashing())) {
				GameSim.utility.get(j).takeHit();
				System.out.println("Enemy Symphony Sphere has " + GameSim.utility.get(j).getHealth() + " more health left.");
				if (GameSim.utility.get(j).getHealth() <= 0) {
					GameSim.utility.remove(j);
					System.out.println("Enemy Symphony Sphere destroyed.");
				}
				p.useDash();
				try {
					String audio = "audio/dash.wav";
					Music victoryPlayer = new Music(audio, false); 
					victoryPlayer.play();
				}catch (Exception e) {
					System.out.println(e);
				}
			}
		}
		
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).getName().equals("Gemstone") && (utility.get(j).owner(a) || utility.get(j).owner(b) || utility.get(j).owner(c)) && p.getLoc().eqLoc(GameSim.utility.get(j).getLoc()) && (p.canDash() || p.isHoverDashing())) {
				GameSim.utility.get(j).takeHit();
				System.out.println("Enemy Gemstone Lode has " + GameSim.utility.get(j).getHealth() + " more health left.");
				if (GameSim.utility.get(j).getHealth() <= 0) {
					GameSim.utility.remove(j);
					System.out.println("Enemy Gemstone Lode destroyed.");
				}
				p.useDash();
				try {
					String audio = "audio/dash.wav";
					Music victoryPlayer = new Music(audio, false); 
					victoryPlayer.play();
				}catch (Exception e) {
					System.out.println(e);
				}
			}
		}
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).getName().equals("Field") && (utility.get(j).owner(a) || utility.get(j).owner(b) || utility.get(j).owner(c)) && p.getLoc().eqLoc(GameSim.utility.get(j).getLoc()) && (p.canDash() || p.isHoverDashing())) {
				GameSim.utility.remove(j);
				System.out.println("Enemy Electromagnetic field destroyed.");
				p.useDash();
				try {
					String audio = "audio/dash.wav";
					Music victoryPlayer = new Music(audio, false); 
					victoryPlayer.play();
				}catch (Exception e) {
					System.out.println(e);
				}
			}
		}
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).getName().equals("Umbrella") && (utility.get(j).owner(a) || utility.get(j).owner(b) || utility.get(j).owner(c)) && p.getLoc().eqLoc(GameSim.utility.get(j).getLoc()) && (p.canDash() || p.isHoverDashing())) {
				if (GameSim.utility.get(j).getOwner().ultActive()) {
					GameSim.utility.get(j).getOwner().setSights(2);
				}else {
					GameSim.utility.get(j).getOwner().setSights(1);
				}
				GameSim.utility.remove(j);
				System.out.println("Enemy Umbrella destroyed.");
				p.useDash();
				try {
					String audio = "audio/dash.wav";
					Music victoryPlayer = new Music(audio, false); 
					victoryPlayer.play();
				}catch (Exception e) {
					System.out.println(e);
				}
			}
		}
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).getName().equals("Pylon") && (utility.get(j).owner(a) || utility.get(j).owner(b) || utility.get(j).owner(c)) && p.getLoc().eqLoc(GameSim.utility.get(j).getLoc()) && (p.canDash() || p.isHoverDashing())) {
				GameSim.utility.remove(j);
				System.out.println("Enemy Honey Pylon destroyed.");
				p.useDash();
				try {
					String audio = "audio/dash.wav";
					Music victoryPlayer = new Music(audio, false); 
					victoryPlayer.play();
				}catch (Exception e) {
					System.out.println(e);
				}
			}
		}
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).getName().equals("Turret") && (utility.get(j).owner(a) || utility.get(j).owner(b) || utility.get(j).owner(c)) && p.getLoc().eqLoc(GameSim.utility.get(j).getLoc()) && (p.canDash() || p.isHoverDashing())) {
				GameSim.utility.get(j).takeHit();
				System.out.println("Enemy Turret has " + GameSim.utility.get(j).getHealth() + " more health left.");
				if (GameSim.utility.get(j).getHealth() <= 0) {
					GameSim.utility.get(j).deactivate();
					System.out.println("Enemy Turret deactivated.");
				}
				p.useDash();
				try {
					String audio = "audio/dash.wav";
					Music victoryPlayer = new Music(audio, false); 
					victoryPlayer.play();
				}catch (Exception e) {
					System.out.println(e);
				}
			}
		}
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).getName().equals("Pawn") && (utility.get(j).owner(a) || utility.get(j).owner(b) || utility.get(j).owner(c)) && p.getLoc().eqLoc(GameSim.utility.get(j).getLoc()) && (p.canDash() || p.isHoverDashing())) {
				GameSim.utility.remove(j);
				System.out.println("Enemy Pawn destroyed.");
				p.useDash();
				try {
					String audio = "audio/dash.wav";
					Music victoryPlayer = new Music(audio, false); 
					victoryPlayer.play();
				}catch (Exception e) {
					System.out.println(e);
				}
			}
		}
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).getName().equals("Mural") && (utility.get(j).owner(a) || utility.get(j).owner(b) || utility.get(j).owner(c)) && p.getLoc().eqLoc(GameSim.utility.get(j).getLoc()) && (p.canDash() || p.isHoverDashing())) {
				GameSim.utility.remove(j);
				System.out.println("Enemy Mural destroyed.");
				p.useDash();
				try {
					String audio = "audio/dash.wav";
					Music victoryPlayer = new Music(audio, false); 
					victoryPlayer.play();
				}catch (Exception e) {
					System.out.println(e);
				}
			}
		}
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).getName().equals("Sock") && (utility.get(j).owner(a) || utility.get(j).owner(b) || utility.get(j).owner(c)) && p.getLoc().eqLoc(GameSim.utility.get(j).getLoc()) && (p.canDash() || p.isHoverDashing())) {
				GameSim.utility.remove(j);
				System.out.println("Enemy Sock Monkey destroyed.");
				p.useDash();
				try {
					String audio = "audio/dash.wav";
					Music victoryPlayer = new Music(audio, false); 
					victoryPlayer.play();
				}catch (Exception e) {
					System.out.println(e);
				}
			}
		}
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).getName().equals("Support") && (utility.get(j).owner(a) || utility.get(j).owner(b) || utility.get(j).owner(c)) && p.getLoc().eqLoc(GameSim.utility.get(j).getLoc()) && (p.canDash() || p.isHoverDashing())) {
				GameSim.utility.remove(j);
				System.out.println("Enemy Sockyman Support destroyed.");
				p.useDash();
				try {
					String audio = "audio/dash.wav";
					Music victoryPlayer = new Music(audio, false); 
					victoryPlayer.play();
				}catch (Exception e) {
					System.out.println(e);
				}
			}
		}
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).getName().equals("Matrix") && (utility.get(j).owner(a) || utility.get(j).owner(b) || utility.get(j).owner(c)) && p.getLoc().eqLoc(GameSim.utility.get(j).getLoc()) && (p.canDash() || p.isHoverDashing())) {
				GameSim.utility.remove(j);
				System.out.println("Enemy Immortality Matrix destroyed.");
				p.useDash();
				try {
					String audio = "audio/dash.wav";
					Music victoryPlayer = new Music(audio, false); 
					victoryPlayer.play();
				}catch (Exception e) {
					System.out.println(e);
				}
			}
		}
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).getName().equals("Dynamite") && (utility.get(j).owner(a) || utility.get(j).owner(b) || utility.get(j).owner(c)) && p.getLoc().eqLoc(GameSim.utility.get(j).getLoc()) && (p.canDash() || p.isHoverDashing())) {
				GameSim.utility.get(j).takeHit();
				System.out.println("Enemy Dynamite has " + GameSim.utility.get(j).getHealth() + " more health left.");
				if (GameSim.utility.get(j).getHealth() <= 0) {
					GameSim.utility.remove(j);
					System.out.println("Enemy Dynamite destroyed.");
				}
				p.useDash();
				for(int q = 0; q < GameSim.utility.size(); q++) {
					if(GameSim.utility.get(q).getName().equals("Peri") && GameSim.utility.get(j).owner(GameSim.utility.get(j).getOwner()) && p.inRange(GameSim.utility.get(j).getLoc(), 12)) {
						p.takeDamage(275);
						GameSim.utility.get(q).getOwner().addDamage(275);
					}
				}
				try {
					String audio = "audio/dash.wav";
					Music victoryPlayer = new Music(audio, false); 
					victoryPlayer.play();
				}catch (Exception e) {
					System.out.println(e);
				}
			}
		}
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).getName().equals("Peri") && (utility.get(j).owner(a) || utility.get(j).owner(b) || utility.get(j).owner(c)) && p.getLoc().eqLoc(GameSim.utility.get(j).getLoc()) && (p.canDash() || p.isHoverDashing())) {
				GameSim.utility.get(j).takeHit();
				System.out.println("Enemy Peri has " + GameSim.utility.get(j).getHealth() + " more health left.");
				if (GameSim.utility.get(j).getHealth() <= 0) {
					GameSim.utility.remove(j);
					System.out.println("Enemy Peri destroyed.");
				}
				p.useDash();
				try {
					String audio = "audio/dash.wav";
					Music victoryPlayer = new Music(audio, false); 
					victoryPlayer.play();
				}catch (Exception e) {
					System.out.println(e);
				}
			}
		}
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).getName().equals("Finley") && (utility.get(j).owner(a) || utility.get(j).owner(b) || utility.get(j).owner(c)) && p.getLoc().eqLoc(GameSim.utility.get(j).getLoc()) && (p.canDash() || p.isHoverDashing())) {
				GameSim.utility.get(j).takeHit();
				System.out.println("Enemy Finley Clone has " + GameSim.utility.get(j).getHealth() + " more health left.");
				if (GameSim.utility.get(j).getHealth() <= 0) {
					GameSim.utility.remove(j);
					System.out.println("Enemy Finley Clone destroyed.");
				}
				p.useDash();
				try {
					String audio = "audio/dash.wav";
					Music victoryPlayer = new Music(audio, false); 
					victoryPlayer.play();
				}catch (Exception e) {
					System.out.println(e);
				}
			}
		}
		
		System.out.println();
	}
	
	public static void Movement(Player p, Player a, Player z, Player c, Player d, Player e) {
		p.resetCover();
		for(int i = 0; i < orbs.size(); i++) {
			if(p.getLoc().eqLoc(orbs.get(i).getLoc()) && !p.ultReady()) {
				p.getOrb();
				orbs.remove(i);
			}
		}
		for(int i = 0; i < cover.size(); i++) {
			if(p.getLoc().eqLoc(cover.get(i).getLoc())) {
				if(cover.get(i).getName().equals("Full")) {
					p.setCover("Full");
					break;
				}
				if(cover.get(i).getName().equals("Partial")) {
					p.setCover("Partial");
					break;
				}
			}else {
				p.resetCover();
			}
		}
		for(int q = 0; q < GameSim.utility.size(); q++) {
			if(GameSim.utility.get(q).getName().equals("Peri") && GameSim.utility.get(q).isAlly(p) && p.getLoc().eqLoc(GameSim.utility.get(q).getLoc()) && p.getCover().equals("None")) {
				p.setCover("Partial");
				break;
			}
		}
		if(p.isParalyzed() && !p.isHoverDashing()) {
			System.out.println("Cannot move while paralyzed!");
			System.out.println();
			return;
		}
		if(p.drillDashed()) {
			System.out.println("Cannot move after drill dashing!");
			System.out.println();
			return;
		}
		if (p.getSpin()) {
			p.setSpinMove(true);
		}
		String s = "";
		boolean move = true;
		boolean hasMoved = false;
		String lastMoved = "";
		ArrayList<Effect> e1 = new ArrayList<Effect>();
		Effect ZeroIgnite = new Effect("ignite", 0, 1);
		Effect ZeroDaze = new Effect("daze", 0, 1);
		e1.add(ZeroDaze);
		e1.add(ZeroIgnite);
		double rand = Math.random();
		Scanner input = new Scanner(System.in);
		if(p.getMovement() <= 0) {
			System.out.println("No more movement left!");
			System.out.println();
		}else {
			int originalMovement = p.getMovement();
			b.printField(p, a, z, c, d, e, orbs, cover, p, a, z, c, utility);
			while(move) {
				System.out.print("Which way do you want to move: ");
				String moveResponse = input.next();
				switch (moveResponse) {
				  case "a":
					  if(b.hasTrench(p.getLoc().getX() - 1, p.getLoc().getY()) && !p.getHover()) {
						  System.out.println("Cannot move onto a Trench tile!");
						  b.printField(p, a, z, c, d, e, orbs, cover, p, a, z, c, utility);
						  break;
					  }
					  p.move(p.getLoc().getX() - 1, p.getLoc().getY());
						if(b.hasCar(p.getLoc().getX(), p.getLoc().getY()) && !p.getHover()) {
							p.getLoc().set(p.getLoc().getX() - 3, p.getLoc().getY());
						}
					  hasMoved = true;
					  b.printField(p, a, z, c, d, e, orbs, cover, p, a, z, c, utility);
					  lastMoved = "left";
					  System.out.println("Relocated to " + p.getLoc() + ". " + p.getMovement() + " movement left.");
				    break;
				  case "d":
					  if(b.hasTrench(p.getLoc().getX() + 1, p.getLoc().getY()) && !p.getHover()) {
						  System.out.println("Cannot move onto a Trench tile!");
						  b.printField(p, a, z, c, d, e, orbs, cover, p, a, z, c, utility);
						  break;
					  }
					  p.move(p.getLoc().getX() + 1, p.getLoc().getY());
					  if(b.hasCar(p.getLoc().getX(), p.getLoc().getY()) && !p.getHover()) {
							p.getLoc().set(p.getLoc().getX() + 3, p.getLoc().getY());
						}
					  hasMoved = true;
					  b.printField(p, a, z, c, d, e, orbs, cover, p, a, z, c, utility);
					  lastMoved = "right";
					  System.out.println("Relocated to " + p.getLoc() + ". " + p.getMovement() + " movement left.");
				    break;
				  case "w":
					  if(b.hasTrench(p.getLoc().getX(), p.getLoc().getY() - 1) && !p.getHover()) {
						  System.out.println("Cannot move onto a Trench tile!");
						  b.printField(p, a, z, c, d, e, orbs, cover, p, a, z, c, utility);
						  break;
					  }
					  p.move(p.getLoc().getX(), p.getLoc().getY() - 1);
					  if(b.hasCar(p.getLoc().getX(), p.getLoc().getY()) && !p.getHover()) {
							p.getLoc().set(p.getLoc().getX(), p.getLoc().getY() - 3);
						}
					  hasMoved = true;
					  b.printField(p, a, z, c, d, e, orbs, cover, p, a, z, c, utility);
					  lastMoved = "up";
					  System.out.println("Relocated to " + p.getLoc() + ". " + p.getMovement() + " movement left.");
				    break;
				  case "s":
					  if(b.hasTrench(p.getLoc().getX(), p.getLoc().getY() + 1) && !p.getHover()) {
						  System.out.println("Cannot move onto a Trench tile!");
						  b.printField(p, a, z, c, d, e, orbs, cover, p, a, z, c, utility);
						  break;
					  }
					  p.move(p.getLoc().getX(), p.getLoc().getY() + 1);
					  if(b.hasCar(p.getLoc().getX(), p.getLoc().getY()) && !p.getHover()) {
						  p.getLoc().set(p.getLoc().getX(), p.getLoc().getY() + 3);
						}
					  hasMoved = true;
					  b.printField(p, a, z, c, d, e, orbs, cover, p, a, z, c, utility);
					  lastMoved = "down";
					  System.out.println("Relocated to " + p.getLoc() + ". " + p.getMovement() + " movement left.");
				    break;
				  case "r":
					  Dash(p, a, z, c);
					  hasMoved = false;
				    break;
				  case "j":
					  Location l = SetCursor(p, a, z, c, d, e, p.getRange());
					  Jump(p, d, e, l, a, z, c);
					  b.printField(p, a, z, c, d, e, orbs, cover, p, a, z, c, utility);
				    break;
				  case "p":
					  p.increaseMovement(1);
				    break;
				  case "u":
					  if (hasMoved && p.getName().equals("Bonbon")) {
						  BonbonUltimate(p, a, z, c, lastMoved);
					  }
				    break;
				  case "m":
					  System.out.println("Movement completed.");
					  hasMoved = false;
					  move = false;
				    break;
				}
				if(b.hasTower(p.getLoc().getX(), p.getLoc().getY()) && !p.getHover()) {
					p.setTower();
				}else {
					p.removeTower();
				}
				if(b.hasTime(p.getLoc().getX(), p.getLoc().getY()) && !p.getHover()) {
					p.reduceMovement(2);
				}
				int hor = 0;
				int ver = 0;
				if(hasMoved) {
					for(int j = 0; j < GameSim.utility.size(); j++) {
						if(GameSim.utility.get(j).getName().equals("Pylon") && GameSim.utility.get(j).isEnemy(p) && GameSim.utility.get(j).inRange(p, 4)) {
							if (p.getMovement() > 0) {
								p.reduceMovement(1);
							}
						}
					}
					for(int j = 0; j < GameSim.utility.size(); j++) {
						if(GameSim.utility.get(j).getName().equals("Fulmination") && GameSim.utility.get(j).isEnemy(p) && GameSim.utility.get(j).inRange(p, 8)) {
							p.removeJumpDash();
							p.takeDamage(25);
							GameSim.utility.get(j).getOwner().addDamage(25);
						}
					}
					for(int j = 0; j < GameSim.utility.size(); j++) {
						if(GameSim.utility.get(j).getName().equals("Flame") && GameSim.utility.get(j).isEnemy(p) && GameSim.utility.get(j).inRange(p, 5) && !p.isIgnite()) {
							p.ignite(2);
						}
					}
					for(int j = 0; j < GameSim.utility.size(); j++) {
						if(GameSim.utility.get(j).getName().equals("Turret") && GameSim.utility.get(j).isEnemy(p) && GameSim.utility.get(j).inRange(p, 3) && GameSim.utility.get(j).isActivated() && GameSim.utility.get(j).hasSpikes()) {
							p.takeDamage(75);
							GameSim.utility.get(j).getOwner().addDamage(75);
						}
					}
					for(int j = 0; j < GameSim.utility.size(); j++) {
						if(GameSim.utility.get(j).getName().equals("Trap") && GameSim.utility.get(j).isEnemy(p) && GameSim.utility.get(j).inRange(p, 4)) {
							GameSim.utility.get(j).activateTrap();
							GameSim.utility.remove(j);
						}
					}
					for(int j = 0; j < GameSim.utility.size(); j++) {
						if(GameSim.utility.get(j).getName().equals("Mural") && GameSim.utility.get(j).isAlly(p) && GameSim.utility.get(j).inRange(p, 4)) {
							p.keepMovement(originalMovement);
						}
					}
					if (p.getNebula()) {
						Utility Nebula = new Utility("Nebula", new Location(p.getLoc().getX(), p.getLoc().getY()), p, d, e, a, z, c);
						utility.add(Nebula);
					}
					for(int j = 0; j < GameSim.utility.size(); j++) {
						if(GameSim.utility.get(j).getName().equals("Vine") && GameSim.utility.get(j).isEnemy(p) && GameSim.utility.get(j).inRange(p, 2)) {
							p.takeDamage(25);
							GameSim.utility.get(j).getOwner().addDamage(25);
						}
					}
					for(int j = 0; j < GameSim.utility.size(); j++) {
						if(GameSim.utility.get(j).getName().equals("Mine") && GameSim.utility.get(j).isEnemy(p) && GameSim.utility.get(j).inRange(p, 0)) {
							p.takeDamage(175);
							GameSim.utility.get(j).getOwner().addDamage(175);
							p.knockbacked(GameSim.utility.get(j).getLoc());
							GameSim.utility.remove(j);
						}
					}
					for(int j = 0; j < GameSim.utility.size(); j++) {
						if(GameSim.utility.get(j).getName().equals("Steam") && GameSim.utility.get(j).isEnemy(p) && GameSim.utility.get(j).getOwner().inRange(p)) {
							if (GameSim.utility.get(j).getOwner().isClockwork()){
								GameSim.utility.get(j).steam();
							}
						}
					}
					if (lastMoved.equals("left")) {
						hor = 1;
					}
					if (lastMoved.equals("right")) {
						hor = -1;
					}
					if (lastMoved.equals("up")) {
						ver = 1;
					}
					if (lastMoved.equals("down")) {
						ver = -1;
					}
					boolean inCover = false;
					int range = 4;
					for(int j = 0; j < GameSim.utility.size(); j++) {
						if(GameSim.utility.get(j).getName().equals("Smoke") && GameSim.utility.get(j).isAlly(p)) {
							if (GameSim.utility.get(j).owner.getDarkness()) {
								range = 6;
							}
							if (p.inRange(GameSim.utility.get(j).getLoc(), range)) {
								inCover = true;
							}
						}
					}
					
					if(a.getName().equals("Bedrock") && a.ultActive() && a.inRange(p, 3) && !a.isStunned()) {
						if (!p.getStep()) {
							p.takeDamage(75);
							p.takeDamage(75);
							p.takeDamage(75);
							p.takeDamage(75);
							p.takeDamage(75);
							p.addDamage(375);
							p.knockbacked(a.getLoc());
							p.knockbacked(a.getLoc());
						}
					}
					if(z.getName().equals("Bedrock") && z.ultActive() && z.inRange(p, 3) && !z.isStunned()) {
						if (!p.getStep()) {
							p.takeDamage(75);
							p.takeDamage(75);
							p.takeDamage(75);
							p.takeDamage(75);
							p.takeDamage(75);
							p.addDamage(375);
							p.knockbacked(z.getLoc());
							p.knockbacked(z.getLoc());
						}
					}
					if(c.getName().equals("Bedrock") && c.ultActive() && c.inRange(p, 3) && !c.isStunned()) {
						if (!p.getStep()) {
							p.takeDamage(75);
							p.takeDamage(75);
							p.takeDamage(75);
							p.takeDamage(75);
							p.takeDamage(75);
							p.addDamage(375);
							p.knockbacked(c.getLoc());
							p.knockbacked(c.getLoc());
						}
					}
					
					
					if (!inCover) {
						if(a.hasSights() && a.inRange(p) && !a.isStunned()) {
							if (!p.getStep()) {
								p.takeDamage(a.getDamage() * 0.9);
								a.addDamage(a.getDamage() * 0.9);
								if(a.getName().equals("Zero") && rand <= 0.3) {
									p.ignite(1);
									p.daze(1);
								}
								if(a.getName().equals("Aidan")) {
									p.weak(0.5, 1);
									p.blind(0.5, 1);
								}
							}
							a.useSight();
						}
						if(z.hasSights() && z.inRange(p) && !z.isStunned()) {
							if (!p.getStep()) {
								p.takeDamage(z.getDamage() * 0.9);
								z.addDamage(z.getDamage() * 0.9);
								if(z.getName().equals("Zero") && rand <= 0.3) {
									p.ignite(1);
									p.daze(1);
								}
								if(z.getName().equals("Aidan")) {
									p.weak(0.5, 1);
									p.blind(0.5, 1);
								}
							}
							z.useSight();
						}
						if(c.hasSights() && c.inRange(p) && !c.isStunned()) {
							if (!p.getStep()) {
								p.takeDamage(c.getDamage() * 0.9);
								c.addDamage(c.getDamage() * 0.9);
								if(c.getName().equals("Zero") && rand <= 0.3) {
									p.ignite(1);
									p.daze(1);
								}
								if(c.getName().equals("Aidan")) {
									p.weak(0.5, 1);
									p.blind(0.5, 1);
								}
							}
							c.useSight();
						}
					}
					
					if (p.getName().equals("Mayhem") && p.ultActive()) {
						if (p.inRange(a)) {
							a.takeDamage(a.getMaxHP() * 0.0075);
							p.addDamage(a.getMaxHP() * 0.0075);
						}
						if (p.inRange(z)) {
							z.takeDamage(z.getMaxHP() * 0.0075);
							p.addDamage(z.getMaxHP() * 0.0075);
						}
						if (p.inRange(c)) {
							c.takeDamage(c.getMaxHP() * 0.0075);
							p.addDamage(c.getMaxHP() * 0.0075);
						}
					}
					
					if (p.getName().equals("Stellar") && p.hasAura()) {
						if (p.inRange(a)) {
							a.takeDamage(25);
							p.addDamage(25);
						}
						if (p.inRange(z)) {
							z.takeDamage(25);
							p.addDamage(25);
						}
						if (p.inRange(c)) {
							c.takeDamage(25);
							p.addDamage(25);
						}
					}
					
					if (a.isTranscend() || z.isTranscend() || c.isTranscend()){
						p.takeDamage(p.getMaxHP() * 0.02);
					}
					if (d.isTranscend() || e.isTranscend()){
						p.increaseHP(p.getMaxHP() * 0.02);
					}
					
					if (p.getSpin()) {
						if (p.getLoc().eqLoc(a.getLoc())) {
							a.resetCover();
						}
						if (p.getLoc().eqLoc(z.getLoc())) {
							z.resetCover();
						}
						if (p.getLoc().eqLoc(c.getLoc())) {
							c.resetCover();
						}
					}
					
					if (p.getBump() > 0) {
						boolean bumped = false;
						if (p.getLoc().eqLoc(a.getLoc())) {
							a.knockbacked(new Location(p.getLoc().getX() + hor, p.getLoc().getY() + ver));
							bumped = true;
							a.vulnerable(0.05, 1);
							if (p.ultActive()) {
								a.takeDamage(175);
								p.addDamage(175);
							}
						}
						if (p.getLoc().eqLoc(z.getLoc())) {
							z.knockbacked(new Location(p.getLoc().getX() + hor, p.getLoc().getY() + ver));
							bumped = true;
							z.vulnerable(0.05, 1);
							if (p.ultActive()) {
								z.takeDamage(175);
								p.addDamage(175);
							}
						}
						if (p.getLoc().eqLoc(c.getLoc())) {
							c.knockbacked(new Location(p.getLoc().getX() + hor, p.getLoc().getY() + ver));
							bumped = true;
							c.vulnerable(0.05, 1);
							if (p.ultActive()) {
								c.takeDamage(175);
								p.addDamage(175);
							}
						}
						if (bumped) {
							p.useBump();
						}
						b.printField(p, a, z, c, d, e, orbs, cover, p, a, z, c, utility);
					}
					
					
				}
				for(int i = 0; i < orbs.size(); i++) {
					if(p.getLoc().eqLoc(orbs.get(i).getLoc()) && !p.ultReady()) {
						p.getOrb();
						orbs.remove(i);
					}
				}
				for(int i = 0; i < cover.size(); i++) {
					if(p.getLoc().eqLoc(cover.get(i).getLoc())) {
						if(cover.get(i).getName().equals("Full")) {
							p.setCover("Full");
							break;
						}
						if(cover.get(i).getName().equals("Partial")) {
							p.setCover("Partial");
							break;
						}
					}else {
						p.resetCover();
					}
				}
				for(int q = 0; q < GameSim.utility.size(); q++) {
					if(GameSim.utility.get(q).getName().equals("Peri") && GameSim.utility.get(q).isAlly(p) && p.getLoc().eqLoc(GameSim.utility.get(q).getLoc()) && p.getCover().equals("None")) {
						p.setCover("Partial");
						break;
					}
				}
				for(int i = 0; i < utility.size(); i++) {
					if(p.getLoc().eqLoc(utility.get(i).getLoc())) {
						if(utility.get(i).getName().equals("Swiftwing") && utility.get(i).owner(p) && !utility.get(i).getPickUp()) {
							System.out.println("There's my buddy!");
							p.resetCooldown();
							utility.get(i).pickedUp();
							System.out.println();
							break;
						}
						if(utility.get(i).getName().equals("Howler") && utility.get(i).owner(p) && !utility.get(i).getPickUp()) {
							System.out.println("You want more? Here's more!");
							p.refreshUlt();
							utility.get(i).pickedUp();
							System.out.println();
							break;
						}
						if(utility.get(i).getName().equals("Star") && utility.get(i).owner(p) && !utility.get(i).getPickUp()) {
							utility.get(i).pickedUp();
							p.pickupStar();
							if (p.getStar() >= 2) {
								p.heal(0.05);
								p.addHealing(p.getMaxHP() * 0.05);
								p.setShield();
								System.out.println("Got my ninja stars back!");
							}
							break;
						}
						if(utility.get(i).getName().equals("Spirit") && utility.get(i).owner(p) && !utility.get(i).getPickUp()) {
							utility.get(i).pickedUp();
							p.pickupSpirit();
							break;
						}
						if(utility.get(i).getName().equals("Butterfly") && utility.get(i).isEnemy(p) && !utility.get(i).getPickUp()) {
							utility.get(i).pickedUp();
							p.poison(0.2, 1);
							break;
						}
						if(utility.get(i).getName().equals("Crane") && utility.get(i).isAlly(p) && !utility.get(i).getPickUp()) {
							System.out.println("Got a crane!");
							utility.get(i).getOwner().increaseDPSNum(5);
							utility.get(i).pickedUp();
							break;
						}
						if(utility.get(i).getName().equals("Nebula") && utility.get(i).isEnemy(p) && !utility.get(i).getPickUp()) {
							p.takeDamage(50);
							p.blind(0.05, 1);
							utility.get(i).getOwner().addDamage(50);
							utility.get(i).pickedUp();
							break;
						}
					}
				}
				System.out.println();
				if(p.getMovement() <= 0) {
					System.out.println("Out of movement.");
					move = false;
					p.setSpinMove(false);
					System.out.println();
				}
				if(p.isParalyzed() && !p.isHoverDashing()) {
					System.out.println("Paralyzed!");
					move = false;
					p.setSpinMove(false);
					System.out.println();
				}
				if(!p.isAlive()) {
					p.setSpinMove(false);
					move = false;
				}
			}
		}
	}
	
	public static Location SetCursor(Player p, Player a, Player z, Player c, Player d, Player e, double i) {
		b.setRange(i);
		Music victoryPlayer = null;
		Music victoryPlayer2 = null;
		try {
			String audio = "audio/wind.wav";
			victoryPlayer = new Music(audio, false); 
			victoryPlayer.play();
			audio = "audio/heli.wav";
			victoryPlayer2 = new Music(audio, false);
			victoryPlayer2.play();
		}catch (Exception t) {
			System.out.println(t);
		}
		Scanner input = new Scanner(System.in);
		boolean move = true;
		Location l2 = new Location(p.getLoc().getX(), p.getLoc().getY());
		Location l = new Location(p.getLoc().getX(), p.getLoc().getY());
		b.setCursor(l);
		while(move) {
			b.printField(p, a, z, c, d, e, orbs, cover, p, a, z, c, utility);
			System.out.print("Select a point on the battlefield with the cursor: ");
			String moveResponse = input.next();
			switch (moveResponse) {
			  case "a":
				  l.adjust(-1, 0);
				  b.moveCursor(l);
				  b.printField(p, a, z, c, d, e, orbs, cover, p, a, z, c, utility);
				  System.out.println("Moved cursor to " + l + ".");
			    break;
			  case "d":
				  l.adjust(1, 0);
				  b.moveCursor(l);
				  b.printField(p, a, z, c, d, e, orbs, cover, p, a, z, c, utility);
				  System.out.println("Moved cursor to " + l + ".");
			    break;
			  case "w":
				  l.adjust(0, -1);
				  b.moveCursor(l);
				  b.printField(p, a, z, c, d, e, orbs, cover, p, a, z, c, utility);
				  System.out.println("Moved cursor to " + l + ".");
			    break;
			  case "s":
				  l.adjust(0, 1);
				  b.moveCursor(l);
				  b.printField(p, a, z, c, d, e, orbs, cover, p, a, z, c, utility);
				  System.out.println("Moved cursor to " + l + ".");
			    break;
			  case "u":
				  if (p.getName().equals("Clementine") && p.ultReady() && l2.distance(l) < 4) {
					  ClementineUltimate(p, a, z, c, true);
					  ultimateFX();
				  }
				  b.printField(p, a, z, c, d, e, orbs, cover, p, a, z, c, utility);
				  break;
			  case "c":
				  System.out.println("Cursor set to " + l + ".");
				  move = false;
			    break;
			}
			System.out.println();
		}
		try {
			victoryPlayer.stop();
			victoryPlayer2.stop();
		} catch (UnsupportedAudioFileException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (LineUnavailableException e1) {
			e1.printStackTrace();
		}
		b.removeCursor();
		return l;
	}
	
	public static Location ViewRange(Player p, Player a, Player z, Player c, Player d, Player e, double i) {
		b.setRange(i);
		Music victoryPlayer = null;
		Music victoryPlayer2 = null;
		try {
			String audio = "audio/wind.wav";
			victoryPlayer = new Music(audio, false); 
			victoryPlayer.play();
			audio = "audio/heli.wav";
			victoryPlayer2 = new Music(audio, false);
			victoryPlayer2.play();
		}catch (Exception t) {
			System.out.println(t);
		}
		Scanner input = new Scanner(System.in);
		boolean move = true;
		Location l2 = new Location(p.getLoc().getX(), p.getLoc().getY());
		Location l = new Location(p.getLoc().getX(), p.getLoc().getY());
		b.setCursor(l);
		while(move) {
			b.printField(p, a, z, c, d, e, orbs, cover, p, a, z, c, utility);
			System.out.print("Select a point on the battlefield with the cursor: ");
			String moveResponse = input.next();
			switch (moveResponse) {
			  case "a":
				  l.adjust(-1, 0);
				  b.moveCursor(l);
				  b.printField(p, a, z, c, d, e, orbs, cover, p, a, z, c, utility);
				  System.out.println("Moved cursor to " + l + ".");
			    break;
			  case "d":
				  l.adjust(1, 0);
				  b.moveCursor(l);
				  b.printField(p, a, z, c, d, e, orbs, cover, p, a, z, c, utility);
				  System.out.println("Moved cursor to " + l + ".");
			    break;
			  case "w":
				  l.adjust(0, -1);
				  b.moveCursor(l);
				  b.printField(p, a, z, c, d, e, orbs, cover, p, a, z, c, utility);
				  System.out.println("Moved cursor to " + l + ".");
			    break;
			  case "s":
				  l.adjust(0, 1);
				  b.moveCursor(l);
				  b.printField(p, a, z, c, d, e, orbs, cover, p, a, z, c, utility);
				  System.out.println("Moved cursor to " + l + ".");
			    break;
			  case "a1":
				  l.set(p.getLoc().getX(), p.getLoc().getY());
				  b.printField(p, a, z, c, d, e, orbs, cover, p, a, z, c, utility);
				  System.out.println("Moved cursor to " + l + ".");
			    break;
			  case "a2":
				  l.set(d.getLoc().getX(), d.getLoc().getY());
				  b.printField(p, a, z, c, d, e, orbs, cover, p, a, z, c, utility);
				  System.out.println("Moved cursor to " + l + ".");
			    break;
			  case "a3":
				  l.set(e.getLoc().getX(), e.getLoc().getY());
				  b.printField(p, a, z, c, d, e, orbs, cover, p, a, z, c, utility);
				  System.out.println("Moved cursor to " + l + ".");
			    break;
			  case "e1":
				  l.set(a.getLoc().getX(), a.getLoc().getY());
				  b.printField(p, a, z, c, d, e, orbs, cover, p, a, z, c, utility);
				  System.out.println("Moved cursor to " + l + ".");
			    break;
			  case "e2":
				  l.set(z.getLoc().getX(), z.getLoc().getY());
				  b.printField(p, a, z, c, d, e, orbs, cover, p, a, z, c, utility);
				  System.out.println("Moved cursor to " + l + ".");
			    break;
			  case "e3":
				  l.set(c.getLoc().getX(), c.getLoc().getY());
				  b.printField(p, a, z, c, d, e, orbs, cover, p, a, z, c, utility);
				  System.out.println("Moved cursor to " + l + ".");
			    break;
			  case "1":
				  b.setRange(1);
				  b.printField(p, a, z, c, d, e, orbs, cover, p, a, z, c, utility);
				  System.out.println("Range adjusted to " + i + ".");
			    break;
			  case "0":
				  b.setRange(0);
				  b.printField(p, a, z, c, d, e, orbs, cover, p, a, z, c, utility);
				  System.out.println("Range adjusted to " + i + ".");
			    break;
			  case "2":
				  b.setRange(2);
				  b.printField(p, a, z, c, d, e, orbs, cover, p, a, z, c, utility);
				  System.out.println("Range adjusted to " + i + ".");
			    break;
			  case "3":
				  b.setRange(3);
				  b.printField(p, a, z, c, d, e, orbs, cover, p, a, z, c, utility);
				  System.out.println("Range adjusted to " + i + ".");
			    break;
			  case "4":
				  b.setRange(4);
				  b.printField(p, a, z, c, d, e, orbs, cover, p, a, z, c, utility);
				  System.out.println("Range adjusted to " + i + ".");
			    break;
			  case "5":
				  b.setRange(5);
				  b.printField(p, a, z, c, d, e, orbs, cover, p, a, z, c, utility);
				  System.out.println("Range adjusted to " + i + ".");
			    break;
			  case "6":
				  b.setRange(6);
				  b.printField(p, a, z, c, d, e, orbs, cover, p, a, z, c, utility);
				  System.out.println("Range adjusted to " + i + ".");
			    break;
			  case "7":
				  b.setRange(7);
				  b.printField(p, a, z, c, d, e, orbs, cover, p, a, z, c, utility);
				  System.out.println("Range adjusted to " + i + ".");
			    break;
			  case "8":
				  b.setRange(8);
				  b.printField(p, a, z, c, d, e, orbs, cover, p, a, z, c, utility);
				  System.out.println("Range adjusted to " + i + ".");
			    break;
			  case "9":
				  b.setRange(9);
				  b.printField(p, a, z, c, d, e, orbs, cover, p, a, z, c, utility);
				  System.out.println("Range adjusted to " + i + ".");
			    break;
			  case "10":
				  b.setRange(10);
				  b.printField(p, a, z, c, d, e, orbs, cover, p, a, z, c, utility);
				  System.out.println("Range adjusted to " + i + ".");
			    break;
			  case "11":
				  b.setRange(11);
				  b.printField(p, a, z, c, d, e, orbs, cover, p, a, z, c, utility);
				  System.out.println("Range adjusted to " + i + ".");
			    break;
			  case "12":
				  b.setRange(12);
				  b.printField(p, a, z, c, d, e, orbs, cover, p, a, z, c, utility);
				  System.out.println("Range adjusted to " + i + ".");
			    break;
			  case "13":
				  b.setRange(13);
				  b.printField(p, a, z, c, d, e, orbs, cover, p, a, z, c, utility);
				  System.out.println("Range adjusted to " + i + ".");
			    break;
			  case "14":
				  b.setRange(14);
				  b.printField(p, a, z, c, d, e, orbs, cover, p, a, z, c, utility);
				  System.out.println("Range adjusted to " + i + ".");
			    break;
			  case "15":
				  b.setRange(15);
				  b.printField(p, a, z, c, d, e, orbs, cover, p, a, z, c, utility);
				  System.out.println("Range adjusted to " + i + ".");
			    break;
			  case "16":
				  b.setRange(16);
				  b.printField(p, a, z, c, d, e, orbs, cover, p, a, z, c, utility);
				  System.out.println("Range adjusted to " + i + ".");
			    break;
			  case "17":
				  b.setRange(17);
				  b.printField(p, a, z, c, d, e, orbs, cover, p, a, z, c, utility);
				  System.out.println("Range adjusted to " + i + ".");
			    break;
			  case "18":
				  b.setRange(18);
				  b.printField(p, a, z, c, d, e, orbs, cover, p, a, z, c, utility);
				  System.out.println("Range adjusted to " + i + ".");
			    break;
			  case "19":
				  b.setRange(19);
				  b.printField(p, a, z, c, d, e, orbs, cover, p, a, z, c, utility);
				  System.out.println("Range adjusted to " + i + ".");
			    break;
			  case "20":
				  b.setRange(20);
				  b.printField(p, a, z, c, d, e, orbs, cover, p, a, z, c, utility);
				  System.out.println("Range adjusted to " + i + ".");
			    break;
			  case "c":
				  System.out.println("Cursor set to " + l + ".");
				  move = false;
			    break;
			}
			System.out.println();
		}
		try {
			victoryPlayer.stop();
			victoryPlayer2.stop();
		} catch (UnsupportedAudioFileException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (LineUnavailableException e1) {
			e1.printStackTrace();
		}
		b.removeCursor();
		return l;
	}
	
	public static void SpawnOrbs() {
		int d = 2;
		int e = 0;
		if(turns2 >= 5) {
			e = 2;
		}
		if(turns2 >= 10) {
			e = 6;
			d = 0;
		}
		if(turns2 >= 15) {
			e = 10;
		}
		if (reduceSpawn) {
			d = d / 2;
			e = e / 2;
		}
		if(turns2 >= 2) {
			orbs.clear();
			// Bottom half: y from 21 to 23
			for (int i = 0; i < Math.floor((d + e) / 2); i++) {
			    Location l;
			    boolean duplicate;
			    do {
			        int x = (int)(Math.random() * (23 - 18 + 1)) + 18;
			        int y = (int)(Math.random() * (23 - 21 + 1)) + 21;
			        l = new Location(x, y);
			        duplicate = false;
			        for (Orb existing : orbs) {
			            if (existing.getLoc().eqLoc(l)) {
			                duplicate = true;
			                break;
			            }
			        }
			    } while (duplicate);
			    orbs.add(new Orb(l));
			}

			// Top half: y from 18 to 20
			for (int i = 0; i < Math.ceil((d + e) / 2); i++) {
			    Location l;
			    boolean duplicate;
			    do {
			        int x = (int)(Math.random() * (23 - 18 + 1)) + 18;
			        int y = (int)(Math.random() * (20 - 18 + 1)) + 18;
			        l = new Location(x, y);
			        duplicate = false;
			        for (Orb existing : orbs) {
			            if (existing.getLoc().eqLoc(l)) {
			                duplicate = true;
			                break;
			            }
			        }
			    } while (duplicate);
			    orbs.add(new Orb(l));
			}
			// Orb 1: x between 16–25, y between 5–9
			int x1 = (int)(Math.random() * (25 - 16 + 1)) + 16;
			int y1 = (int)(Math.random() * (6 - 2 + 1)) + 2;
			orbs.add(new Orb(new Location(x1, y1)));

			// Orb 2: x between 16–25, y between 32–36
			int x2 = (int)(Math.random() * (25 - 16 + 1)) + 16;
			int y2 = (int)(Math.random() * (39 - 35 + 1)) + 35;
			orbs.add(new Orb(new Location(x2, y2)));

			// Orb 3: y between 16–25, x between 5–9
			int x3 = (int)(Math.random() * (6 - 2 + 1)) + 2;
			int y3 = (int)(Math.random() * (25 - 16 + 1)) + 16;
			orbs.add(new Orb(new Location(x3, y3)));

			// Orb 4: y between 16–25, x between 32–36
			int x4 = (int)(Math.random() * (39 - 35 + 1)) + 35;
			int y4 = (int)(Math.random() * (25 - 16 + 1)) + 16;
			orbs.add(new Orb(new Location(x4, y4)));
			System.out.println("New orbs have spawned!");
			if (reduceSpawn) {
				reduceSpawn = false;
			}
		}
	}
	
	public static void SpawnCover() {
		int d1 = 2;
		int d2 = 4;
		if(turns2 >= 5) {
			d1 = 1;
			d2 = 2;
		}
		if (reduceCover) {
			d1 = d1 / 2;
			d2 = d2 / 2;
		}
		if(turns2 >= 10) {
			d1 = 0;
			d2 = 0;
		}
		cover.clear();
		for(int i = 0; i < d1; i++) {
			int randomX = (int)(Math.random() * (32 - 9 + 1)) + 9;
			int randomY = (int)(Math.random() * (32 - 9 + 1)) + 9;
			Location l = new Location(randomX, randomY);
			Cover c = new Cover("Full", l);
			cover.add(c);
		}
		for(int i = 0; i < d2; i++) {
			int randomX = (int)(Math.random() * (32 - 9 + 1)) + 9;
			int randomY = (int)(Math.random() * (32 - 9 + 1)) + 9;
			Location l = new Location(randomX, randomY);
			Cover c = new Cover("Partial", l);
			cover.add(c);
		}
		System.out.println("New cover has been created!");
		System.out.println();
		if (reduceCover) {
			reduceCover = false;
		}
	}
	
	public static void ShowOrbs(Player p) {
		for(int i = 0; i < orbs.size(); i++) {
			String range = "No";
			if(p.inReach(orbs.get(i).getLoc())) {
				range = "Yes";
			}
			System.out.println("Orb " + (i+1) + " is at " + orbs.get(i).getLoc() + ". In Range: " + range + ".");
		}
		System.out.println();
	}
	
	public static void ShowCover(Player p) {
		for(int i = 0; i < cover.size(); i++) {
			String range = "No";
			if(p.inReach(cover.get(i).getLoc())) {
				range = "Yes";
			}
			System.out.println(cover.get(i).getName() + " Cover at " + cover.get(i).getLoc() + ". In Range: " + range + ".");
		}
		System.out.println();
	}
	
	public static void LunarAttack(Player p, Player a, Player b, Player c) {
		int chainHits = 0;
		p.attack(a);
		if(a.inRange(b, 5)) {
			p.attack(b);
			chainHits++;
		}
		if(a.inRange(c, 5)) {
			p.attack(c);
			chainHits++;
		}
		if (p.isEvolved()) {
			for (int i = 0; i < chainHits; i++) {
				a.takeDamage(75);
				p.addDamage(75);
			}
		}
		System.out.println();
	}
	
	public static void LunarAbility(Player p) {
		p.power(0.2, 2);
		p.heal(0.2);
		p.addHealing(p.getMaxHP() * 0.2);
		p.setCooldown(3);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void LunarUltimate(Player p, Player a, Player b, Player c) {
		p.cleanse();
		Scanner input = new Scanner(System.in);
		System.out.println("1: " + a.getSkin() +". Health: " +  a.getHealth() + "/" + a.getMaxHP());
		System.out.println("2: " + b.getSkin() +". Health: " +  b.getHealth() + "/" + b.getMaxHP());
		System.out.println("3: " + c.getSkin() +". Health: " +  c.getHealth() + "/" + c.getMaxHP());
		System.out.print("Who do you want to make a copy of: ");
		String targetResponse = input.next();
		if(targetResponse.equals("1")) {
			p.setLunarUlt();
			p.setName(a.getName());
			p.resetCooldown();
			p.resetAttack();
			p.resetUlt();
			System.out.println();
			System.out.println("\"Time to meet your match, " + a.getSkin() + "!\"");
			System.out.println();
		}
		if(targetResponse.equals("2")) {
			p.setLunarUlt();
			p.setName(b.getName());
			p.resetCooldown();
			p.resetAttack();
			p.resetUlt();
			System.out.println();
			System.out.println("\"Time to meet your match, " + b.getSkin() + "!\"");
			System.out.println();
		}
		if(targetResponse.equals("3")) {
			p.setLunarUlt();
			p.setName(c.getName());
			p.resetCooldown();
			p.resetAttack();
			p.resetUlt();
			System.out.println();
			System.out.println("\"Time to meet your match, " + c.getSkin() + "!\"");
			System.out.println();
		}
	}
	
	public static void SolarAttack(Player p, Player a) {
		p.attack(a);
		a.ignite(2);
		System.out.println();
	}
	
	public static void SolarAbility(Player p) {
		p.protect(0.5, 2);
		p.setCooldown(3);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void SolarUltimate(Player p, Player a, Player b, Player c) {
		Scanner input = new Scanner(System.in);
		System.out.println("1: " + a.getName() +a.showHP() +  a.getHealth() + "/" + a.getMaxHP());
		System.out.println("2: " + b.getName() +b.showHP() +  b.getHealth() + "/" + b.getMaxHP());
		System.out.println("3: " + c.getName() +c.showHP() +  c.getHealth() + "/" + c.getMaxHP());
		System.out.print("Who do you want to see the Sunrise: ");
		String targetResponse = input.next();
		if(targetResponse.equals("1")) {
			a.takeDamage(700);
			p.addDamage(700);
			a.ignite(2);
			b.ignite(2);
			c.ignite(2);
			p.heal(0.2);
			p.addHealing(p.getMaxHP() * 0.2);
			p.resetUlt();
		}
		if(targetResponse.equals("2")) {
			b.takeDamage(700);
			p.addDamage(700);
			a.ignite(2);
			b.ignite(2);
			c.ignite(2);
			p.heal(0.2);
			p.addHealing(p.getMaxHP() * 0.2);
			p.resetUlt();
		}
		if(targetResponse.equals("3")) {
			c.takeDamage(700);
			p.addDamage(700);
			a.ignite(2);
			b.ignite(2);
			c.ignite(2);
			p.heal(0.2);
			p.addHealing(p.getMaxHP() * 0.2);
			p.resetUlt();
		}
		System.out.println();
		System.out.println("\"Face the Sunrise!\"");
		System.out.println();
	}
	
	public static void MackAttack(Player p, Player a, Player b, Player c) {
		p.attack(a);
		double rand = Math.random();
		if(rand <= 0.1) {
			a.daze(1);
		}
		if (p.isEvolved()) {
			if (b.inRange(a, 3)){
				p.attack(b);
				b.getLoc().set(a.getLoc().getX(), a.getLoc().getY());
			}
			if (c.inRange(a, 3)){
				p.attack(c);
				c.getLoc().set(a.getLoc().getX(), a.getLoc().getY());
			}
		}
		System.out.println();
	}
	
	public static void MackAbility(Player p, Player a, Player b, Player c, Location l) {
		if(GameSim.b.hasTrench(l.getX(), l.getY())) {
			System.out.println("Can't go there!");
			System.out.println();
			return;
		}
		int d = p.getLoc().distance(l);
		p.increaseTotalMovement(d);
		p.getLoc().set(l.getX(), l.getY());
		for(int i = 0; i < cover.size(); i++) {
			if(p.getLoc().eqLoc(cover.get(i).getLoc())) {
				if(cover.get(i).getName().equals("Full")) {
					p.setCover("Full");
					break;
				}
				if(cover.get(i).getName().equals("Partial")) {
					p.setCover("Partial");
					break;
				}
			}else {
				p.resetCover();
			}
		}
		for(int i = 0; i < orbs.size(); i++) {
			if(p.getLoc().eqLoc(orbs.get(i).getLoc()) && !p.ultReady()) {
				p.getOrb();
				orbs.remove(i);
			}
		}
		for(int i = 0; i < cover.size(); i++) {
			if(p.getLoc().eqLoc(cover.get(i).getLoc())) {
				if(cover.get(i).getName().equals("Full")) {
					p.setCover("Full");
				}
				if(cover.get(i).getName().equals("Partial")) {
					p.setCover("Partial");
				}
			}
		}
		if(p.inRange(a)) {
			p.attack(a);
			p.resetAttack();
			double rand = Math.random();
			if(rand <= 0.1) {
				a.daze(1);
			}
		}
		if(p.inRange(b)) {
			p.attack(b);
			p.resetAttack();
			double rand = Math.random();
			if(rand <= 0.1) {
				b.daze(1);
			}
		}
		if(p.inRange(c)) {
			p.attack(c);
			p.resetAttack();
			double rand = Math.random();
			if(rand <= 0.1) {
				c.daze(1);
			}
		}
		p.setCooldown(3);
		System.out.println();
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void MackUltimate(Player p) {
		p.setSights(3);
		p.resetUlt();
		System.out.println("\"Dalton! Rip them apart!\"");
		System.out.println();
	}
	
	public static void CherryAttack(Player p, Player a, Player b, Player c, Player d, Player e) {
		Scanner input = new Scanner(System.in);
		String range = "No";
		if(p.inRange(a)) {
			range = "Yes";
		}
		String range2 = "No";
		if(p.inRange(b)) {
			range2 = "Yes";
		}
		String range3 = "No";
		if(p.inRange(c)) {
			range3 = "Yes";
		}
		String range4 = "No";
		if(p.inRange(d)) {
			range4 = "Yes";
		}
		String range5 = "No";
		if(p.inRange(e)) {
			range5 = "Yes";
		}
		if(p.ultActive()) {
			System.out.println("1: " + a.getSkin() +a.showHP() +  a.getHealth() + "/" + a.getMaxHP() + ". Cover: " + a.getCover());
			System.out.println("2: " + b.getSkin() +b.showHP() +  b.getHealth() + "/" + b.getMaxHP() + ". Cover: " + b.getCover());
			System.out.println("3: " + c.getSkin() +c.showHP() +  c.getHealth() + "/" + c.getMaxHP() + ". Cover: " + c.getCover());
			System.out.println("4: " + d.getSkin() +d.showHP() +  d.getHealth() + "/" + d.getMaxHP() + ". Cover: " + d.getCover());
			System.out.println("5: " + e.getSkin() +e.showHP() +  e.getHealth() + "/" + e.getMaxHP() + ". Cover: " + e.getCover());
			for(int i = 0; i < 2; i++) {
				System.out.print("Who do you want to send a Cherry Missile to: ");
				String targetResponse = input.next();
				if(targetResponse.equals("1")) {
					p.attack(a);
				}
				if(targetResponse.equals("2")) {
					p.attack(b);
				}
				if(targetResponse.equals("3")) {
					p.attack(c);
				}
				if(targetResponse.equals("4")) {
					d.increaseHP(400);
					p.addHealing(400);
				}
				if(targetResponse.equals("5")) {
					e.increaseHP(400);
					p.addHealing(400);
				}
			}
		}else {
			System.out.println("1: " + a.getSkin() +a.showHP() +  a.getHealth() + "/" + a.getMaxHP() + ". In Range: " + range + ". Cover: " + a.getCover());
			System.out.println("2: " + b.getSkin() +b.showHP() +  b.getHealth() + "/" + b.getMaxHP() + ". In Range: " + range2 + ". Cover: " + b.getCover());
			System.out.println("3: " + c.getSkin() +c.showHP() +  c.getHealth() + "/" + c.getMaxHP() + ". In Range: " + range3 + ". Cover: " + c.getCover());
			System.out.println("4: " + d.getSkin() +d.showHP() +  d.getHealth() + "/" + d.getMaxHP() + ". In Range: " + range4 + ". Cover: " + d.getCover());
			System.out.println("5: " + e.getSkin() +e.showHP() +  e.getHealth() + "/" + e.getMaxHP() + ". In Range: " + range5 + ". Cover: " + e.getCover());
			if(!p.inRange(a) && !p.inRange(b) && !p.inRange(c) && !p.inRange(d) && !p.inRange(e)) {
				System.out.println();
				System.out.println("No targets in range!");
				System.out.println();
				return;
			}
			for(int i = 0; i < 3; i++) {
				System.out.print("Who do you want to throw a Cherry Bomb at: ");
				String targetResponse = input.next();
				if(targetResponse.equals("1")) {
					if(p.inRange(a)) {
						p.attack(a);
					}else {
						i--;
						System.out.println();
						System.out.println("Target is out of range!");
						System.out.println();
					}
				}
				if(targetResponse.equals("2")) {
					if(p.inRange(b)) {
						p.attack(b);
					}else {
						i--;
						System.out.println();
						System.out.println("Target is out of range!");
						System.out.println();
					}
				}
				if(targetResponse.equals("3")) {
					if(p.inRange(c)) {
						p.attack(c);
					}else {
						i--;
						System.out.println();
						System.out.println("Target is out of range!");
						System.out.println();
					}
				}
				if(targetResponse.equals("4")) {
					if(p.inRange(d)) {
						d.increaseHP(200);
						p.addHealing(200);
					}else {
						i--;
						System.out.println();
						System.out.println("Target is out of range!");
						System.out.println();
					}
				}
				if(targetResponse.equals("5")) {
					if(p.inRange(e)) {
						e.increaseHP(200);
						p.addHealing(200);
					}else {
						i--;
						System.out.println();
						System.out.println("Target is out of range!");
						System.out.println();
					}
				}
			}
		}
		p.setAttacked();
		System.out.println();
	}
	
	public static void CherryAbility(Player p, Player a, Player b, Player c) {
		if(p.ultActive()) {
			a.weak(0.4, 1);
			a.daze(1);
			b.weak(0.4, 1);
			b.daze(1);
			c.weak(0.4, 1);
			c.daze(1);
			p.setCooldown(3);
			System.out.println(p.voiceline());
			System.out.println();
		}else {
			if(!a.inRange(p, 15) && !b.inRange(p, 15) && !c.inRange(p, 15)) {
				System.out.println("No targets in range!");
				System.out.println();
				return;
			}
			if(a.inRange(p, 15)) {
				a.weak(0.3, 1);
			}
			if(b.inRange(p, 15)) {
				b.weak(0.3, 1);
			}
			if(c.inRange(p, 15)) {
				c.weak(0.3, 1);
			}
			p.setCooldown(3);
			System.out.println(p.voiceline());
			System.out.println();
		}
	}
	
	public static void CherryUltimate(Player p) {
		p.setUlt();
		p.setMaxHP(3300);
		p.power(1, 100);
		System.out.println("\"We fight on my terms now!\"");
		System.out.println();
	}
	
	public static void FinleyAttack(Player p, Player a) {
		if(a.inRange(p, 2)) {
			p.attack(a);
			p.resetAttack();
			p.attack(a);
			double rand = Math.random();
			if(rand <= 0.1) {
				a.stun(1);
			}
			if (p.isEvolved()) {
				p.protect(0.3, 1);
	        }
		}else {
			double rand = Math.random();
			if(rand <= 0.05) {
				a.stun(1);
			}
			p.attack(a);
		}
		System.out.println();
	}
	
	public static void FinleyAbility(Player p, Player a, Player b, Player c) {
		Scanner input = new Scanner(System.in);
		if(!p.inRange(a, 20) && !p.inRange(b, 20) && !p.inRange(c, 20)) {
			System.out.println("No targets in range!");
			System.out.println();
			return;
		}
		String range = "No";
		if(p.inRange(a, 20)) {
			range = "Yes";
		}
		String range2 = "No";
		if(p.inRange(b, 20)) {
			range2 = "Yes";
		}
		String range3 = "No";
		if(p.inRange(c, 20)) {
			range3 = "Yes";
		}
		System.out.println("1: " + a.getSkin() +". Health: " +  a.getHealth() + "/" + a.getMaxHP() + ". In Range: " + range + ".");
		System.out.println("2: " + b.getSkin() +". Health: " +  b.getHealth() + "/" + b.getMaxHP() + ". In Range: " + range2 + ".");
		System.out.println("3: " + c.getSkin() +". Health: " +  c.getHealth() + "/" + c.getMaxHP() + ". In Range: " + range3 + ".");
		System.out.println("Where do you want to send the Skateboard Surprise: ");
		String targetResponse = input.next();
		if(targetResponse.equals("1")) {
			if(p.inRange(a, 20)) {
				a.takeDamage(200);
				p.addDamage(200);
				a.vulnerable(0.1, 1);
				if(b.inRange(a, 3)) {
					b.takeDamage(200);
					p.addDamage(200);
					b.vulnerable(0.1, 1);
				}
				if(c.inRange(a, 3)) {
					c.takeDamage(200);
					p.addDamage(200);
					c.vulnerable(0.1, 1);
				}
				p.setCooldown(3);
			}else {
				System.out.println();
				System.out.println("Target is out of range!");
				System.out.println();
			}
		}
		if(targetResponse.equals("2")) {
			if(p.inRange(b, 20)) {
				b.takeDamage(200);
				p.addDamage(200);
				b.vulnerable(0.1, 1);
				if(a.inRange(b, 3)) {
					a.takeDamage(200);
					p.addDamage(200);
					a.vulnerable(0.1, 1);
				}
				if(c.inRange(b, 3)) {
					c.takeDamage(200);
					p.addDamage(200);
					c.vulnerable(0.1, 1);
				}
				p.setCooldown(3);
			}else {
				System.out.println();
				System.out.println("Target is out of range!");
				System.out.println();
			}
		}
		if(targetResponse.equals("3")) {
			if(p.inRange(c, 20)) {
				c.takeDamage(200);
				p.addDamage(200);
				c.vulnerable(0.1, 1);
				if(a.inRange(c, 3)) {
					a.takeDamage(200);
					p.addDamage(200);
					a.vulnerable(0.1, 1);
				}
				if(b.inRange(c, 3)) {
					b.takeDamage(200);
					p.addDamage(200);
					b.vulnerable(0.1, 1);
				}
				p.setCooldown(3);
			}else {
				System.out.println();
				System.out.println("Target is out of range!");
				System.out.println();
			}
		}
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void FinleyUltimate(Player p, Player a, Player b, Player c) {
		Scanner input = new Scanner(System.in);
		System.out.println("1: " + a.getName() +a.showHP() +  a.getHealth() + "/" + a.getMaxHP());
		System.out.println("2: " + b.getName() +b.showHP() +  b.getHealth() + "/" + b.getMaxHP());
		System.out.println("3: " + c.getName() +c.showHP() +  c.getHealth() + "/" + c.getMaxHP());
		System.out.print("Who do you want to death punch: ");
		String targetResponse = input.next();
		if(targetResponse.equals("1")) {
			a.takeDamage(800);
			p.addDamage(800);
			b.knockbacked(a.getLoc());
			c.knockbacked(a.getLoc());
			p.resetUlt();
		}
		if(targetResponse.equals("2")) {
			b.takeDamage(800);
			p.addDamage(800);
			a.knockbacked(b.getLoc());
			c.knockbacked(b.getLoc());
			p.resetUlt();
		}
		if(targetResponse.equals("3")) {
			c.takeDamage(800);
			p.addDamage(800);
			b.knockbacked(c.getLoc());
			a.knockbacked(c.getLoc());
			p.resetUlt();
		}
		System.out.println();
		System.out.println("\"Incoming Meteor!\"");
		System.out.println();
	}
	
	public static void BurtAttack(Player p, Player a) {
		p.attack(a);
		double rand = Math.random();
		if(rand <= 0.15) {
			a.paralyze(1);
		}
		System.out.println();
	}
	
	public static void BurtAbility(Player p) {
		ArrayList<Effect> e = new ArrayList<Effect>();
		Effect BurtProtect = new Effect("protect", 0.5, 2);
		Effect BurtReflect = new Effect("reflection", 0, 2);
		e.add(BurtProtect);
		e.add(BurtReflect);
		p.addEffects(e);
		p.applyEffects();
		p.protect(0.5, 1);
		p.reflect(1);
		p.setCooldown(3);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void BurtUltimate(Player p, Player a, Player b, Player c) {
		Scanner input = new Scanner(System.in);
		System.out.println("1: " + a.getSkin() +a.showHP() +  a.getHealth() + "/" + a.getMaxHP());
		System.out.println("2: " + b.getSkin() +b.showHP() +  b.getHealth() + "/" + b.getMaxHP());
		System.out.println("3: " + c.getSkin() +c.showHP() +  c.getHealth() + "/" + c.getMaxHP());
		System.out.print("Who do you want to suffer the wrath of dual wielding: ");
		String targetResponse = input.next();
		if(targetResponse.equals("1")) {
			a.takeDamage(700);
			b.takeDamage(350);
			c.takeDamage(350);
			p.addDamage(1400);
			p.resetUlt();
		}
		if(targetResponse.equals("2")) {
			b.takeDamage(700);
			a.takeDamage(350);
			c.takeDamage(350);
			p.addDamage(1400);
			p.resetUlt();
		}
		if(targetResponse.equals("3")) {
			c.takeDamage(700);
			b.takeDamage(350);
			a.takeDamage(350);
			p.addDamage(1400);
			p.resetUlt();
		}
		System.out.println();
		System.out.println("\"We'll see who's better at combat!\"");
		System.out.println();
	}
	
	public static void BoloAttack(Player p, Player a) {
		p.attack(a);
		double rand = Math.random();
		if(rand <= 0.1) {
			a.paralyze(1);
			a.ignite(1);
		}
		System.out.println();
	}
	
	public static void BoloAbility(Player p) {
		p.setSights(2);
		p.setCooldown(3);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void BoloUltimate(Player p, Player a, Player b, Player c) {
		int randomX = (int)(Math.random() * (3 - 1 + 1)) + 1;
		int randomY = (int)(Math.random() * (3 - 1 + 1)) + 1;
		if(randomX == 1) {
			if(randomY == 1) {
				if(!a.isAlive()) {
					if(b.isAlive()) {
						b.takeDamage(b.getMaxHP() * 0.3);
						p.addDamage(b.getMaxHP() * 0.3);
					}else {
						c.takeDamage(c.getMaxHP() * 0.3);
						p.addDamage(c.getMaxHP() * 0.3);
					}
				}else {
					a.takeDamage(a.getMaxHP() * 0.3);
					p.addDamage(a.getMaxHP() * 0.3);
				}
			}
			if(randomY == 2) {
				if(!b.isAlive()) {
					if(c.isAlive()) {
						c.takeDamage(c.getMaxHP() * 0.3);
						p.addDamage(c.getMaxHP() * 0.3);
					}else {
						a.takeDamage(a.getMaxHP() * 0.3);
						p.addDamage(a.getMaxHP() * 0.3);
					}
				}else {
					b.takeDamage(b.getMaxHP() * 0.3);
					p.addDamage(b.getMaxHP() * 0.3);
				}
			}
			if(randomY == 3) {
				if(!c.isAlive()) {
					if(a.isAlive()) {
						a.takeDamage(a.getMaxHP() * 0.3);
						p.addDamage(a.getMaxHP() * 0.3);
					}else {
						b.takeDamage(b.getMaxHP() * 0.3);
						p.addDamage(b.getMaxHP() * 0.3);
					}
				}else {
					c.takeDamage(c.getMaxHP() * 0.3);
					p.addDamage(c.getMaxHP() * 0.3);
				}
			}
		}
		if(randomX == 2) {
			if(randomY == 1) {
				if(!a.isAlive() && !b.isAlive()) {
					c.takeDamage(c.getMaxHP() * 0.3);
					p.addDamage(c.getMaxHP() * 0.3);
				}else {
					a.takeDamage(a.getMaxHP() * 0.3);
					p.addDamage(a.getMaxHP() * 0.3);
					b.takeDamage(b.getMaxHP() * 0.3);
					p.addDamage(b.getMaxHP() * 0.3);
				}
			}
			if(randomY == 2) {
				if(!b.isAlive() && !c.isAlive()) {
					a.takeDamage(a.getMaxHP() * 0.3);
					p.addDamage(a.getMaxHP() * 0.3);
				}else {
					b.takeDamage(b.getMaxHP() * 0.3);
					p.addDamage(b.getMaxHP() * 0.3);
					c.takeDamage(c.getMaxHP() * 0.3);
					p.addDamage(c.getMaxHP() * 0.3);
				}
			}
			if(randomY == 3) {
				if(!a.isAlive() && !c.isAlive()) {
					b.takeDamage(b.getMaxHP() * 0.3);
					p.addDamage(b.getMaxHP() * 0.3);
				}else {
					a.takeDamage(a.getMaxHP() * 0.3);
					p.addDamage(a.getMaxHP() * 0.3);
					c.takeDamage(c.getMaxHP() * 0.3);
					p.addDamage(c.getMaxHP() * 0.3);
				}
			}
		}
		if(randomX == 3) {
			a.takeDamage(a.getMaxHP() * 0.3);
			p.addDamage(a.getMaxHP() * 0.3);
			b.takeDamage(b.getMaxHP() * 0.3);
			p.addDamage(b.getMaxHP() * 0.3);
			c.takeDamage(c.getMaxHP() * 0.3);
			p.addDamage(c.getMaxHP() * 0.3);
		}
		p.resetUlt();
		System.out.println("\"You'll truely want to hope I miss this time!\"");
		System.out.println();
	}
	
	public static void DylanAttack(Player p, Player a) {
		System.out.println();
		Scanner input = new Scanner(System.in);
		System.out.print("Would you like to use fire or ice: ");
		String attackResponse = input.next();
		if(attackResponse.equals("f")) {
			p.attack(a);
			a.ignite(1);
		}else {
			p.attack(a);
			double rand = Math.random();
			if(rand <= 0.4) {
				a.freeze(1);
			}
		}
		System.out.println();
	}
	
	public static void DylanAbility(Player p, Player a, Player b, Player c) {
		Scanner input = new Scanner(System.in);
		if(!p.inRange(a, 15) && !p.inRange(b, 15) && !p.inRange(c, 15)) {
			System.out.println("No targets in range!");
			System.out.println();
			return;
		}
		String range = "No";
		if(p.inRange(a)) {
			range = "Yes";
		}
		String range2 = "No";
		if(p.inRange(b)) {
			range2 = "Yes";
		}
		String range3 = "No";
		if(p.inRange(c)) {
			range3 = "Yes";
		}
		System.out.println("1: " + a.getSkin() +a.showHP() +  a.getHealth() + "/" + a.getMaxHP() + ". In Range: " + range + ".");
		System.out.println("2: " + b.getSkin() +b.showHP() +  b.getHealth() + "/" + b.getMaxHP() + ". In Range: " + range2 + ".");
		System.out.println("3: " + c.getSkin() +c.showHP() +  c.getHealth() + "/" + c.getMaxHP() + ". In Range: " + range3 + ".");
		System.out.println("Where do you want to send the Goblins: ");
		String targetResponse = input.next();
		if(targetResponse.equals("1")) {
			if(p.inRange(a)) {
				a.takeDamage(350);
				p.addDamage(350);
				a.vulnerable(0.1, 1);
				if (a.isIgnite() || a.isFreezed()) {
					a.takeDamage(100);
					p.addDamage(100);
				}
				p.setCooldown(3);
			}else {
				System.out.println();
				System.out.println("Target is out of range!");
				System.out.println();
			}
		}
		if(targetResponse.equals("2")) {
			if(p.inRange(b)) {
				b.takeDamage(350);
				p.addDamage(350);
				b.vulnerable(0.1, 1);
				if (b.isIgnite() || b.isFreezed()) {
					b.takeDamage(100);
					p.addDamage(100);
				}
				p.setCooldown(3);
			}else {
				System.out.println();
				System.out.println("Target is out of range!");
				System.out.println();
			}
		}
		if(targetResponse.equals("3")) {
			if(p.inRange(c)) {
				c.takeDamage(350);
				p.addDamage(350);
				c.vulnerable(0.1, 1);
				if (c.isIgnite() || c.isFreezed()) {
					c.takeDamage(100);
					p.addDamage(100);
				}
				p.setCooldown(3);
			}else {
				System.out.println();
				System.out.println("Target is out of range!");
				System.out.println();
			}
		}
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void DylanUltimate(Player p, Player a, Player b, Player c, Player d, Player e) {
		p.power(0.25, 1);
		d.power(0.25, 1);
		e.power(0.25, 1);
		a.ignite(2);
		a.freeze(1);
		b.ignite(2);
		b.freeze(1);
		c.ignite(2);
		c.freeze(1);
		p.resetUlt();
		System.out.println("\"Rolling a Nat20 is your only hope now!\"");
		System.out.println();
	}
	
	public static void ZeroAttack(Player p, Player a, Player b, Player c) {
		int totalAttacks = 1;
		p.attack(a);
		if(a.inRange(b, 3)) {
			p.attack(b);
			totalAttacks++;
		}
		if(a.inRange(c, 3)) {
			p.attack(c);
			totalAttacks++;
		}
		for(int i = 0; i < totalAttacks; i++) {
			p.heal(0.05);
		}
		System.out.println();
	}
	
	public static void ZeroAbility(Player p, Player a, Player b) {
		p.cleanse();
		a.cleanse();
		b.cleanse();
		p.heal(0.10);
		a.heal(0.10);
		b.heal(0.10);
		p.addHealing(p.getMaxHP() * 0.1);
		p.addHealing(a.getMaxHP() * 0.1);
		p.addHealing(b.getMaxHP() * 0.1);
		p.power(0.1, 1);
		a.power(0.1, 1);
		b.power(0.1, 1);
		p.setCooldown(3);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void ZeroUltimate(Player p) {
		p.resetUlt();
		p.setSights(6);
		System.out.println("\"Come closer I insist!\"");
		System.out.println();
	}
	
	public static void MaxAttack(Player p, Player a, Player b, Player c) {
		Scanner input = new Scanner(System.in);
		String range = "No";
		if(p.inRange(a)) {
			range = "Yes";
		}
		String range2 = "No";
		if(p.inRange(b)) {
			range2 = "Yes";
		}
		String range3 = "No";
		if(p.inRange(c)) {
			range3 = "Yes";
		}
		System.out.println("1: " + a.getSkin() +". Health: " +  a.getHealth() + "/" + a.getMaxHP() + ". In Range: " + range + ". Cover: " + a.getCover());
		System.out.println("2: " + b.getSkin() +". Health: " +  b.getHealth() + "/" + b.getMaxHP() + ". In Range: " + range2 + ". Cover: " + b.getCover());
		System.out.println("3: " + c.getSkin() +". Health: " +  c.getHealth() + "/" + c.getMaxHP() + ". In Range: " + range3 + ". Cover: " + c.getCover());
		if(!p.inRange(a) && !p.inRange(b) && !p.inRange(c)) {
			System.out.println();
			System.out.println("No targets in range!");
			System.out.println();
			return;
		}
		for(int i = 0; i < 4; i++) {
			int randomNum = (int)(Math.random() * (50 - 25 + 1)) + 25;
			System.out.print("Who do you want to open a rift over: ");
			String targetResponse = input.next();
			if(targetResponse.equals("1")) {
				if(p.inRange(a)) {
					p.attack(a);
					a.takeDamage(randomNum);
					p.addDamage(randomNum);
				}else {
					i--;
					System.out.println();
					System.out.println("Target is out of range!");
					System.out.println();
				}
			}
			if(targetResponse.equals("2")) {
				if(p.inRange(b)) {
					p.attack(b);
					b.takeDamage(randomNum);
					p.addDamage(randomNum);
				}else {
					i--;
					System.out.println();
					System.out.println("Target is out of range!");
					System.out.println();
				}
			}
			if(targetResponse.equals("3")) {
				if(p.inRange(c)) {
					p.attack(c);
					c.takeDamage(randomNum);
					p.addDamage(randomNum);
				}else {
					i--;
					System.out.println();
					System.out.println("Target is out of range!");
					System.out.println();
				}
			}
			if(targetResponse.equals("c")) {
				System.out.println();
				break;
			}
		}
		System.out.println();
	}
	
	public static void MaxAbility(Player p, Player a, Player b) {
		Scanner input = new Scanner(System.in);
		if(!a.isAlive() && !b.isAlive()) {
			p.cleanse();
			p.power(0.5, 1);
			p.protect(0.5, 1);
			p.setCooldown(3);
			System.out.println("\"Finally, I can start thinking about myself.\"");
			System.out.println();
			return;
		}
		System.out.println("1: " + a.getSkin() +a.showHP() +  a.getHealth() + "/" + a.getMaxHP() + ".");
		System.out.println("2: " + b.getSkin() +b.showHP() +  b.getHealth() + "/" + b.getMaxHP() + ".");
		System.out.println("Who do you want to give guidance to: ");
		String targetResponse = input.next();
		if(targetResponse.equals("1")) {
			a.cleanse();
			a.power(0.5, 1);
			a.protect(0.5, 1);
			p.setCooldown(3);
		}
		if(targetResponse.equals("2")) {
			b.cleanse();
			b.power(0.5, 1);
			b.protect(0.5, 1);
			p.setCooldown(3);
		}
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void MaxUltimate(Player p, Player a, Player b, Player c) {
		Scanner input = new Scanner(System.in);
		System.out.println("1: " + a.getSkin() +a.showHP() +  a.getHealth() + "/" + a.getMaxHP());
		System.out.println("2: " + b.getSkin() +b.showHP() +  b.getHealth() + "/" + b.getMaxHP());
		System.out.println("3: " + c.getSkin() +c.showHP() +  c.getHealth() + "/" + c.getMaxHP());
		System.out.print("Who do you want to send the Failed Experiement to: ");
		String targetResponse = input.next();
		if(targetResponse.equals("1")) {
			a.takeDamage(750);
			p.addDamage(750);
			p.resetUlt();
		}
		if(targetResponse.equals("2")) {
			b.takeDamage(750);
			p.addDamage(750);
			p.resetUlt();
		}
		if(targetResponse.equals("3")) {
			c.takeDamage(750);
			p.addDamage(750);
			p.resetUlt();
		}
		a.paralyze(2);
		b.paralyze(2);
		c.paralyze(2);
		System.out.println();
		System.out.println("\"You will fail like my creation did!\"");
		System.out.println();
	}
	
	public static void EliAttack(Player p, Player a) {
		System.out.println();
		Scanner input = new Scanner(System.in);
		System.out.println("1: Little Buddy");
		System.out.println("2: Medium Buddy");
		System.out.println("3: Big Buddy");
		System.out.print("Which buddy do you want to summon: ");
		String targetResponse = input.next();
		if(targetResponse.equals("1")) {
			p.attack(a);
			double rand = Math.random();
			if(rand <= 0.2) {
				a.paralyze(1);
				a.freeze(1);
			}
		}
		if(targetResponse.equals("2")) {
			p.attack(a);
			double rand = Math.random();
			if(rand <= 0.2) {
				a.vulnerable(0.25, 2);
			}
		}
		if(targetResponse.equals("3")) {
			p.attack(a);
			double rand = Math.random();
			if(rand <= 0.2) {
				a.blind(0.3, 2);
			}
		}
		System.out.println();
	}
	
	public static void EliAbility(Player p, Player a, Player b) {
		p.addHealing(p.getMaxHP() * 0.3);
		p.addHealing(a.getMaxHP() * 0.3);
		p.addHealing(b.getMaxHP() * 0.3);
		p.regen(0.15, 2);
		a.regen(0.15, 2);
		b.regen(0.15, 2);
		p.setCooldown(4);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void EliUltimate(Player p, Player a, Player b) {
		p.addHealing(p.getMaxHP() * 0.4);
		p.addHealing(a.getMaxHP() * 0.4);
		p.addHealing(b.getMaxHP() * 0.4);
		p.cleanse();
		a.cleanse();
		b.cleanse();
		p.power(0.35, 1);
		p.regen(0.2, 2);
		a.power(0.35, 1);
		a.regen(0.2, 2);
		b.power(0.35, 1);
		b.regen(0.2, 2);
		p.resetUlt();
		System.out.println("\"Time to make a splash!\"");
		System.out.println();
	}
	
	public static void ViaAttack(Player p, Player a, Player b, Player c) {
		p.attack(a);
		double rand = Math.random();
		if(rand <= 0.1) {
			a.paralyze(1);
		}
		Location l = a.getLoc();
		Location ln = new Location(l.getX() + 3, l.getY());
		Location ln2 = new Location(l.getX(), l.getY() + 3);
		Location ln3 = new Location(l.getX() - 3, l.getY());
		Location ln4 = new Location(l.getX(), l.getY() - 3);
		if(b.getLoc().eqLoc(ln) || b.getLoc().eqLoc(ln2) || b.getLoc().eqLoc(ln3) || b.getLoc().eqLoc(ln4)) {
			b.takeDamage(200);
			p.addDamage(200);
		}
		if(c.getLoc().eqLoc(ln) || c.getLoc().eqLoc(ln2) || c.getLoc().eqLoc(ln3) || c.getLoc().eqLoc(ln4)) {
			c.takeDamage(200);
			p.addDamage(200);
		}
		System.out.println();
	}
	
	public static void ViaAbility(Player p, Player a, Player b, Player c, Location l) {
		if(GameSim.b.hasTrench(l.getX(), l.getY())) {
			System.out.println("Can't send Arro there!");
			System.out.println();
			return;
		}
		if(!p.inRange(l)) {
			System.out.println("Can't send Arro that far!");
			System.out.println();
			return;
		}
		if(a.inRange(l, 8) && a.isAlive() && !a.isFortified()) {
			a.getLoc().set(l.getX(), l.getY());
			a.vulnerable(0.1, 1);
			a.resetCover();
		}
		if(b.inRange(l, 8) && b.isAlive() && !b.isFortified()) {
			b.getLoc().set(l.getX(), l.getY());
			b.vulnerable(0.1, 1);
			b.resetCover();
		}
		if(c.inRange(l, 8) && c.isAlive() && !c.isFortified()) {
			c.getLoc().set(l.getX(), l.getY());
			c.vulnerable(0.1, 1);
			c.resetCover();
		}
		p.setCooldown(4);
		System.out.println();
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void ViaUltimate(Player p, Player a, Player b, Player c) {
		p.power(0.5, 1);
		a.paralyze(2);
		a.daze(2);
		b.paralyze(2);
		b.daze(2);
		c.paralyze(2);
		c.daze(2);
		p.resetUlt();
		System.out.println("\"Killer Wail incoming!\"");
		System.out.println();
	}
	
	public static void LouisAttack(Player p, Player a) {
		p.attack(a);
		if(turns2 >= 5) {
			p.increaseDPSNum(25);
		}
		System.out.println();
	}
	
	public static void LouisAbility(Player p, Player a, Player b, Player c) {
		Location l = p.getLoc();
		if(!a.inRange(p) && !b.inRange(p) && !c.inRange(p)) {
			System.out.println("No targets in range!");
			System.out.println();
			return;
		}
		if(a.inRange(l)) {
			a.freeze(1);
			a.vulnerable(0.2, 1);
			a.knockbacked(p.getLoc());
		}
		if(b.inRange(l)) {
			b.freeze(1);
			b.vulnerable(0.2, 1);
			b.knockbacked(p.getLoc());
		}
		if(c.inRange(l)) {
			c.freeze(1);
			c.vulnerable(0.2, 1);
			c.knockbacked(p.getLoc());
		}
		p.setCooldown(3);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void LouisUltimate(Player p, Player a, Player b, Player c, Location l) {
		if(a.inRange(l, 10)) {
			a.takeDamage(600);
			p.addDamage(600);
			a.knockbacked(l);
		}
		if(b.inRange(l, 10)) {
			b.takeDamage(600);
			p.addDamage(600);
			b.knockbacked(l);
		}
		if(c.inRange(l, 10)) {
			c.takeDamage(600);
			p.addDamage(600);
			c.knockbacked(l);
		}
		p.setSights(3);
		p.resetUlt();
		System.out.println("\"Ohhh Guardiannnn!\"");
		System.out.println();
	}
	
	public static void AlexAttack(Player p, Player a) {
		if (p.isEvolved()) {
			a.dragIn(p.getLoc(), 12);
			p.attack(a);
			a.knockbacked(p.getLoc());
			p.increaseDPS(0.03);
			System.out.println();
			return;
        }
		a.dragIn(p.getLoc(), 6);
		p.attack(a);
		a.knockbacked(p.getLoc());
		p.increaseDPS(0.01);
		System.out.println();
	}
	
	public static void AlexAbility(Player p, Player a, Player b) {
		ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i=1; i<6; i++) list.add(i);
        Collections.shuffle(list);
        Collections.shuffle(list);
        Collections.shuffle(list);
        for (int i=0; i<3; i++) {
        	if(list.get(i) == 1) {
        		p.heal(0.2);
        		a.heal(0.2);
        		b.heal(0.2);
        		p.addHealing(p.getMaxHP() * 0.2);
        		p.addHealing(a.getMaxHP() * 0.2);
        		p.addHealing(b.getMaxHP() * 0.2);
        	}
        	if(list.get(i) == 2) {
        		p.cleanse();
        		a.cleanse();
        		b.cleanse();
        	}
        	if(list.get(i) == 3) {
        		p.refine(2);
        		a.refine(2);
        		b.refine(2);
        	}
        	if(list.get(i) == 4) {
        		p.power(0.1, 2);
        		a.power(0.1, 2);
        		b.power(0.1, 2);
        	}
        	if(list.get(i) == 5) {
        		p.protect(0.2, 2);
        		a.protect(0.2, 2);
        		b.protect(0.2, 2);
        	}
        }
        p.setCooldown(4);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void AlexUltimate(Player p) {
		p.setUlt();
		p.protect(0.35, 100);
		System.out.println("\"I always come back.\"");
		System.out.println();
	}
	
	public static void OrionAttack(Player p, Player a) {
		p.attack(a);
		int d = p.getLoc().distance(a.getLoc());
		p.increaseTotalMovement(d);
		Location l = new Location(p.getLoc().getX(), p.getLoc().getY());
		p.getLoc().set(a.getLoc().getX(), a.getLoc().getY());
		a.knockbacked(l);
		for(int i = 0; i < cover.size(); i++) {
			if(p.getLoc().eqLoc(cover.get(i).getLoc())) {
				if(cover.get(i).getName().equals("Full")) {
					p.setCover("Full");
					break;
				}
				if(cover.get(i).getName().equals("Partial")) {
					p.setCover("Partial");
					break;
				}
			}else {
				p.resetCover();
			}
		}
		double rand = Math.random();
		if(rand <= 0.15) {
			a.daze(1);
		}
		System.out.println();
	}
	
	public static void OrionAbility(Player p, Player a, Player b) {
		a.reduceCooldown();
		b.reduceCooldown();	
		if(!a.ultReady()) {
			a.getOrb();
		}
		if(!b.ultReady()) {
			b.getOrb();
		}
		if(!p.ultReady()) {
			p.getOrb();
		}
		p.setCooldown(3);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void OrionUltimate(Player p, Player a, Player b) {
		p.increaseMovement(6);
		a.increaseMovement(6);
		b.increaseMovement(6);
		p.heal(0.15);
		a.heal(0.15);
		b.heal(0.15);
		p.addHealing(p.getMaxHP() * 0.15);
		p.addHealing(a.getMaxHP() * 0.15);
		p.addHealing(b.getMaxHP() * 0.15);
		p.protect(0.35, 1);
		p.power(0.25, 1);
		a.protect(0.35, 1);
		a.power(0.25, 1);
		b.protect(0.35, 1);
		b.power(0.25, 1);
		p.resetUlt();
		System.out.println("\"Show them that we aren't backing down!\"");
		System.out.println();
	}
	
	public static void KailaniAttack(Player p, Player a) {
		p.attack(a);
		double rand = Math.random();
		if(rand <= 0.15) {
			a.daze(1);
		}
		System.out.println();
	}
	
	public static void KailaniAbility(Player p, Player a, Player b, Player c, Location l) {
		if(!p.inReach(l, 25)) {
			System.out.println("Can't riptide there!");
			System.out.println();
			return;
		}
		if(GameSim.b.hasTrench(l.getX(), l.getY())) {
			System.out.println("Can't riptide there!");
			System.out.println();
			return;
		}
		if(p.inRange(a, 5)) {
			a.takeDamage(a.getMaxHP() * 0.075);
			p.addDamage(a.getMaxHP() * 0.075);
			a.knockbacked(p.getLoc());
		}
		if(p.inRange(b, 5)) {
			b.takeDamage(b.getMaxHP() * 0.075);
			p.addDamage(b.getMaxHP() * 0.075);
			b.knockbacked(p.getLoc());
		}
		if(p.inRange(c, 5)) {
			c.takeDamage(c.getMaxHP() * 0.075);
			p.addDamage(c.getMaxHP() * 0.075);
			c.knockbacked(p.getLoc());
		}
		int d = p.getLoc().distance(l);
		p.increaseTotalMovement(d);
		p.getLoc().set(l.getX(), l.getY());
		for(int i = 0; i < orbs.size(); i++) {
			if(p.getLoc().eqLoc(orbs.get(i).getLoc()) && !p.ultReady()) {
				p.getOrb();
				orbs.remove(i);
			}
		}
		for(int i = 0; i < cover.size(); i++) {
			if(p.getLoc().eqLoc(cover.get(i).getLoc())) {
				if(cover.get(i).getName().equals("Full")) {
					p.setCover("Full");
				}
				if(cover.get(i).getName().equals("Partial")) {
					p.setCover("Partial");
				}
			}
		}
		if(p.inRange(a, 5)) {
			a.takeDamage(a.getMaxHP() * 0.075);
			p.addDamage(a.getMaxHP() * 0.075);
			a.knockbacked(p.getLoc());
		}
		if(p.inRange(b, 5)) {
			b.takeDamage(b.getMaxHP() * 0.075);
			p.addDamage(b.getMaxHP() * 0.075);
			b.knockbacked(p.getLoc());
		}
		if(p.inRange(c, 5)) {
			c.takeDamage(c.getMaxHP() * 0.075);
			p.addDamage(c.getMaxHP() * 0.075);
			c.knockbacked(p.getLoc());
		}
		p.setCooldown(3);
		System.out.println();
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void KailaniUltimate(Player p, Player a, Player b, Player c) {
		a.takeDamage(550);
		p.addDamage(550);
		a.knockbacked(a.getLoc());
		b.takeDamage(550);
		p.addDamage(550);
		b.knockbacked(b.getLoc());
		c.takeDamage(550);
		p.addDamage(550);
		c.knockbacked(c.getLoc());
		p.protect(0.25, 2);
		p.resetUlt();
		System.out.println("\"Flood their victory out of here!\"");
		System.out.println();
	}
	
	public static void AshleyAttack(Player p, Player a) {
		p.attack(a);
		a.poison(0.30, 1);
		System.out.println();
	}
	
	public static void AshleyAbility(Player p, Player a, Player b, Player c, Player d, Player e) {
		p.cleanse();
		d.cleanse();
		e.cleanse();
		p.heal(0.15);
		d.heal(0.15);
		e.heal(0.15);
		p.addHealing(p.getMaxHP() * 0.15);
		p.addHealing(d.getMaxHP() * 0.15);
		p.addHealing(e.getMaxHP() * 0.15);
		a.weak(0.15, 1);
		b.weak(0.15, 1);
		c.weak(0.15, 1);
		p.setCooldown(3);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void AshleyUltimate(Player p, Player a, Player b, Player c, Player d, Player e) {
		p.power(0.15, 2);
		d.power(0.15, 2);
		e.power(0.15, 2);
		p.protect(0.1, 2);
		d.protect(0.1, 2);
		e.protect(0.1, 2);
		a.poison(0.3, 2);
		a.blind(0.3, 2);
		b.poison(0.3, 2);
		b.blind(0.3, 2);
		c.poison(0.3, 2);
		c.blind(0.3, 2);
		p.heal(0.25);
		d.heal(0.25);
		e.heal(0.25);
		p.addHealing(p.getMaxHP() * 0.25);
		p.addHealing(d.getMaxHP() * 0.25);
		p.addHealing(e.getMaxHP() * 0.25);
		p.resetUlt();
		System.out.println("\"The magical tree has our backs!\"");
		System.out.println();
	}
	
	public static void BedrockAttack(Player p, Player a, Player b, Player c) {
		if(!p.inRange(a) && !p.inRange(b) && !p.inRange(c)) {
			System.out.println("No targets in range!");
			System.out.println();
			return;
		}
		if(a.inRange(p, 1)) {
			p.attack(a);
			p.resetAttack();
			a.knockbacked(p.getLoc());
		}else if(p.inRange(a)){
			a.takeDamage(150);
			p.addDamage(150);
		}
		if(b.inRange(p, 1)) {
			p.attack(b);
			p.resetAttack();
			b.knockbacked(p.getLoc());
		}else if(p.inRange(b)) {
			b.takeDamage(150);
			p.addDamage(150);
		}
		if(c.inRange(p, 1)) {
			p.attack(c);
			c.knockbacked(p.getLoc());
		}else if(p.inRange(c)) {
			c.takeDamage(150);
			p.addDamage(150);
		}
		p.setAttacked();
		System.out.println();
	}
	
	public static void BedrockAbility(Player p) {
		p.heal(0.10);
		p.addHealing(p.getMaxHP() * 0.10);
		p.protect(0.7, 1);
		p.setCooldown(4);
		System.out.println("\"...\"");
		System.out.println();
	}
	
	public static void BedrockUltimate(Player p) {
		p.setUlt();
		System.out.println("\"... ... ...!\" 👊");
		System.out.println();
	}
	
	public static void RoccoAttack(Player p, Player a, Player b, Player c) {
		p.attack(a);
		a.blind(0.1, 1);
		a.ignite(1);
		if(a.inRange(b, 5)) {
			double rand = Math.random();
			if(rand <= 0.1) {
				b.blind(0.1, 1);
				b.ignite(1);
			}
		}
		if(a.inRange(c, 5)) {
			double rand = Math.random();
			if(rand <= 0.1) {
				c.blind(0.1, 1);
				c.ignite(1);
			}
		}
		System.out.println();
	}
	
	public static void RoccoAbility(Player p, Player a, Player b, Player c) {
		a.vulnerable(0.1, 1);
		a.weak(0.1, 1);
		a.paralyze(1);
		b.vulnerable(0.1, 1);
		b.weak(0.1, 1);
		b.paralyze(1);
		c.vulnerable(0.1, 1);
		c.weak(0.1, 1);
		c.paralyze(1);
		p.setCooldown(4);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void RoccoUltimate(Player p, Player a, Player b, Player c) {
		Scanner input = new Scanner(System.in);
		String answer = "";
		String output = "";
		String result = "";
		int randomX = (int)(Math.random() * (3 - 1 + 1)) + 1;
		if(randomX == 1) {
			answer = "r";
			output = "Rocco";
		}
		if(randomX == 2) {
			answer = "p";
			output = "Paper";
		}
		if(randomX == 3) {
			answer = "s";
			output = "Scissors";
		}
		System.out.println("1: " + a.getSkin() +a.showHP() +  a.getHealth() + "/" + a.getMaxHP());
		System.out.println("2: " + b.getSkin() +b.showHP() +  b.getHealth() + "/" + b.getMaxHP());
		System.out.println("3: " + c.getSkin() +c.showHP() +  c.getHealth() + "/" + c.getMaxHP());
		System.out.print("Who do you want to play Rocco Paper Scissors with: ");
		String targetResponse = input.next();
		System.out.println();
		System.out.println("\"Time for Rocco Paper Scissors!\"");
		System.out.println();
		System.out.print("Pick between Rocco, Paper, or Scissors: ");
		String response = input.next();
		if(response.equals("r")) {
			if(answer.equals("r")) {
				result = "draw";
			}
			if(answer.equals("p")) {
				result = "win";
			}
			if(answer.equals("s")) {
				result = "lose";
			}
		}
		if(response.equals("p")) {
			if(answer.equals("r")) {
				result = "lose";
			}
			if(answer.equals("p")) {
				result = "draw";
			}
			if(answer.equals("s")) {
				result = "win";
			}
		}
		if(response.equals("s")) {
			if(answer.equals("r")) {
				result = "win";
			}
			if(answer.equals("p")) {
				result = "lose";
			}
			if(answer.equals("s")) {
				result = "draw";
			}
		}
		if(result.equals("win")) {
			p.heal(0.3);
			System.out.println("You have beat Rocco.");
			System.out.println("\"Refreshing, I'll get you next time.\"");
			System.out.println();
			p.addHealing(p.getMaxHP() * 0.3);
			p.resetUlt();
		}
		if(targetResponse.equals("1")) {
			if(result.equals("draw")) {
				a.poison(0.5, 3);
				System.out.println("You and Rocco tied.");
				System.out.println("\"The poison trap is a classic. Good Choice!\"");
				System.out.println();
				p.resetUlt();
			}
			if(result.equals("lose")) {
				a.takeDamage(a.getMaxHP() * 0.35);
				p.addDamage(a.getMaxHP() * 0.35);
				System.out.println("Rocco beat you.");
				System.out.println("\"BOOM sucker! Better luck next time!\"");
				System.out.println();
				p.resetUlt();
			}
		}
		if(targetResponse.equals("2")) {
			if(result.equals("draw")) {
				b.poison(0.5, 3);
				System.out.println("You and Rocco tied.");
				System.out.println("\"The poison trap is a classic. Good Choice!\"");
				System.out.println();
				p.resetUlt();
			}
			if(result.equals("lose")) {
				b.takeDamage(b.getMaxHP() * 0.35);
				p.addDamage(b.getMaxHP() * 0.35);
				System.out.println("Rocco beat you.");
				System.out.println("\"BOOM sucker! Better luck next time!\"");
				System.out.println();
				p.resetUlt();
			}
			
		}
		if(targetResponse.equals("3")) {
			if(result.equals("draw")) {
				c.poison(0.5, 3);
				System.out.println("\"The poison trap is a classic. Good Choice!\"");
				System.out.println();
				p.resetUlt();
			}
			if(result.equals("lose")) {
				c.takeDamage(c.getMaxHP() * 0.35);
				p.addDamage(c.getMaxHP() * 0.35);
				System.out.println("\"BOOM sucker! Better luck next time!\"");
				System.out.println();
				p.resetUlt();
			}
		}
	}
	
	public static void SammiAttack(Player p, Player a) {
		p.attack(a);
		double rand = Math.random();
		if(rand <= 0.4) {
			a.ignite(1);
			a.poison(0.3, 1);
		}
		System.out.println();
	}
	
	public static void SammiAbility(Player p) {
		if (p.isEvolved()) {
			p.increaseRange(2);
		}
		p.setRange(200);
		p.setCooldown(2);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void SammiUltimate(Player p) {
		p.setRange(200);
		p.cleanse();
		p.addHealing(p.getMaxHP() * 0.2);
		p.power(0.5, 2);
		p.protect(0.3, 2);
		p.heal(0.1, 2);
		p.resetUlt();
		System.out.println("\"Locking in. It's over for them now.\"");
		System.out.println();
	}
	
	public static void ClaraAttack(Player p, Player a) {
		p.attack(a);
		double rand = Math.random();
		if(rand <= 0.08) {
			a.paralyze(1);
		}
		System.out.println();
	}
	
	public static void ClaraAbility(Player p) {
		p.increaseMovement(7);
		p.addDashes(2);
		p.addJumps(2);
		p.setCooldown(2);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void ClaraUltimate(Player p) {
		p.increaseMovement(14);
		p.addDashes(4);
		p.addJumps(4);
		p.setUlt();
		p.power(0.25, 1);
		p.resetUlt();
		System.out.println("\"Hold on, let me cook.\"");
		System.out.println();
	}
	
	public static void ThunderAttack(Player p, Player a, Player b, Player c) {
		p.attack(a);
		a.resetCover();
		if(a.inRange(b, 4)) {
			b.takeDamage(p.getDamage() * 0.75);
			p.addDamage(p.getDamage() * 0.75);
		}
		if(a.inRange(c, 4)) {
			c.takeDamage(p.getDamage() * 0.75);
			p.addDamage(p.getDamage() * 0.75);
		}
		System.out.println();
	}
	
	public static void ThunderAbility(Player p) {
		p.setThunder(true);
		p.counter(1);
		p.protect(0.75, 1);
		p.setCooldown(3);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void ThunderUltimate(Player p, Player a, Player b, Player c) {
		Scanner input = new Scanner(System.in);
		System.out.println("1: " + a.getSkin() +a.showHP() +  a.getHealth() + "/" + a.getMaxHP());
		System.out.println("2: " + b.getSkin() +b.showHP() +  b.getHealth() + "/" + b.getMaxHP());
		System.out.println("3: " + c.getSkin() +c.showHP() +  c.getHealth() + "/" + c.getMaxHP());
		System.out.print("Who do you want to trap in the Energy Cage: ");
		String targetResponse = input.next();
		if(targetResponse.equals("1")) {
			a.takeDamage(750);
			p.addDamage(750);
			p.increaseDPSNum(a.getDamage() * 0.5);
			p.resetUlt();
		}
		if(targetResponse.equals("2")) {
			b.takeDamage(750);
			p.addDamage(750);
			p.increaseDPSNum(b.getDamage() * 0.5);
			p.resetUlt();
		}
		if(targetResponse.equals("3")) {
			c.takeDamage(750);
			p.addDamage(750);
			p.increaseDPSNum(c.getDamage() * 0.5);
			p.resetUlt();
		}
		System.out.println();
		System.out.println("\"You've been... Thunderstruck!\"");
		System.out.println();
	}
	
	public static void AidanAttack(Player p, Player a) {
		System.out.println();
		Scanner input = new Scanner(System.in);
		System.out.println("1: Striker Assault Rifle");
		System.out.println("2: Frenzy Auto Shotgun");
		System.out.println("3: Reaper Sniper Rifle");
		System.out.print("Which gun do you want to use: ");
		String targetResponse = input.next();
		if(targetResponse.equals("1")) {
			p.attack(a);
			double rand = Math.random();
			if(rand <= 0.2) {
				a.daze(1);
			}
		}
		if(targetResponse.equals("2")) {
			p.attack(a);
			if(p.inRange(a, 4)) {
				a.takeDamage(175);
				p.addDamage(175);
			}
		}
		if(targetResponse.equals("3")) {
			if(p.overRange(a, 10)) {
				p.attack(a);
				a.takeDamage(175);
				p.addDamage(175);
				double rand = Math.random();
				if(rand <= 0.1) {
					a.paralyze(1);
				}
			}else {
				p.attack(a);
				double rand = Math.random();
				if(rand <= 0.1) {
					a.paralyze(1);
				}
			}
		}
		System.out.println();
	}
	
	public static void AidanAbility(Player p, Player a, Player b) {
		if (p.isEvolved()) {
			Cover c = new Cover("Full", new Location(p.getLoc().getX(), p.getLoc().getY()));
			Cover c2 = new Cover("Full", new Location(a.getLoc().getX(), a.getLoc().getY()));
			Cover c3 = new Cover("Full", new Location(b.getLoc().getX(), b.getLoc().getY()));
			p.setCover("Full");
			a.setCover("Full");
			b.setCover("Full");
			cover.add(c);
			cover.add(c2);
			cover.add(c3);
			p.heal(0.15);
			a.heal(0.15);
			b.heal(0.15);
			p.addHealing(p.getMaxHP() * 0.15);
			p.addHealing(a.getMaxHP() * 0.15);
			p.addHealing(b.getMaxHP() * 0.15);
		}else {
			Cover c = new Cover("Partial", new Location(p.getLoc().getX(), p.getLoc().getY()));
			Cover c2 = new Cover("Partial", new Location(a.getLoc().getX(), a.getLoc().getY()));
			Cover c3 = new Cover("Partial", new Location(b.getLoc().getX(), b.getLoc().getY()));
			p.setCover("Partial");
			a.setCover("Partial");
			b.setCover("Partial");
			cover.add(c);
			cover.add(c2);
			cover.add(c3);
			p.heal(0.1);
			a.heal(0.1);
			b.heal(0.1);
			p.addHealing(p.getMaxHP() * 0.10);
			p.addHealing(a.getMaxHP() * 0.10);
			p.addHealing(b.getMaxHP() * 0.10);
		}
		p.setCooldown(3);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void AidanUltimate(Player p, Player a, Player b, Player c, Player d, Player e) {
		p.refine(1);
		d.refine(1);
		e.refine(1);
		a.vulnerable(0.1, 2);
		a.weak(0.3, 1);
		b.vulnerable(0.1, 2);
		b.weak(0.3, 1);
		c.vulnerable(0.1, 2);
		c.weak(0.3, 1);
		p.heal(0.10);
		d.heal(0.10);
		e.heal(0.10);
		p.addHealing(p.getMaxHP() * 0.1);
		p.addHealing(d.getMaxHP() * 0.1);
		p.addHealing(e.getMaxHP() * 0.1);
		a.takeDamage(250);
		b.takeDamage(250);
		c.takeDamage(250);
		p.addDamage(750);
		p.resetUlt();
		System.out.println("\"It's the final ring now!\"");
		System.out.println();
	}
	
	public static void LiamAttack(Player p, Player a, Player b, Player c) {
		p.attack(a);
		double rand = Math.random();
		if(rand <= 0.1) {
			a.ignite(1);
			a.knockbacked(p.getLoc());
		}
		if(a.inRange(b, 3)) {
			b.takeDamage(200);
			p.addDamage(200);
			double rand2 = Math.random();
			if(rand2 <= 0.1) {
				b.ignite(1);
				b.knockbacked(p.getLoc());
			}
		}
		if(a.inRange(c, 3)) {
			c.takeDamage(200);
			p.addDamage(200);
			double rand3 = Math.random();
			if(rand3 <= 0.1) {
				c.ignite(1);
				c.knockbacked(p.getLoc());
			}
		}
		System.out.println();
	}
	
	public static void LiamAbility(Player p, Player a, Player b) {
		p.cleanse();
		a.cleanse();
		b.cleanse();
		p.protect(0.4, 1);
		a.protect(0.4, 1);
		b.protect(0.4, 1);
		p.setCooldown(3);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void LiamUltimate(Player p, Player a, Player b, Player c, Player d, Player e) {
		Scanner input = new Scanner(System.in);
		double randomX = (int)(Math.random() * (5000 - 0 + 1)) + 0;
		System.out.println("\"The court trial is in session!\"");
		System.out.println();
		if(a.isAlive()) {
			System.out.print(a.getSkin() + ", do you plead guilty or not guilty to your damage amount of " + randomX + ": ");
			String response = input.next();
			if(response.equals("g")) {
				if(randomX < a.getTotalDPS()) {
					p.heal(0.15);
					d.heal(0.15);
					e.heal(0.15);
					p.addHealing(p.getMaxHP() * 0.15);
					p.addHealing(d.getMaxHP() * 0.15);
					p.addHealing(e.getMaxHP() * 0.15);
					a.takeDamage(250);
					p.addDamage(250);
					System.out.println("\"Guilty as charged!\"");
				}else {
					System.out.println("\"The evidence says otherwise!\"");
					a.takeDamage(650);
					p.addDamage(650);
				}
			}
			if(response.equals("n")) {
				if(randomX > a.getTotalDPS()) {
					p.heal(0.15);
					d.heal(0.15);
					e.heal(0.15);
					p.addHealing(p.getMaxHP() * 0.15);
					p.addHealing(d.getMaxHP() * 0.15);
					p.addHealing(e.getMaxHP() * 0.15);
					a.takeDamage(250);
					p.addDamage(250);
					System.out.println("\"Innocence proven!\"");
				}else {
					System.out.println("\"The evidence says otherwise!\"");
					a.takeDamage(650);
					p.addDamage(650);
				}
			}
		}
		if(b.isAlive()) {
			System.out.print(b.getSkin() + ", do you plead guilty or not guilty to your damage amount of " + randomX +": ");
			String response = input.next();
			if(response.equals("g")) {
				if(randomX < b.getTotalDPS()) {
					p.heal(0.15);
					d.heal(0.15);
					e.heal(0.15);
					p.addHealing(p.getMaxHP() * 0.15);
					p.addHealing(d.getMaxHP() * 0.15);
					p.addHealing(e.getMaxHP() * 0.15);
					b.takeDamage(250);
					p.addDamage(250);
					System.out.println("\"Guilty as charged!\"");
				}else {
					System.out.println("\"The evidence says otherwise!\"");
					b.takeDamage(650);
					p.addDamage(650);
				}
			}
			if(response.equals("n")) {
				if(randomX > b.getTotalDPS()) {
					p.heal(0.15);
					d.heal(0.15);
					e.heal(0.15);
					p.addHealing(p.getMaxHP() * 0.15);
					p.addHealing(d.getMaxHP() * 0.15);
					p.addHealing(e.getMaxHP() * 0.15);
					b.takeDamage(250);
					p.addDamage(250);
					System.out.println("\"Innocence proven!\"");
				}else {
					System.out.println("\"The evidence says otherwise!\"");
					b.takeDamage(650);
					p.addDamage(650);
				}
			}
		}
		if(c.isAlive()) {
			System.out.print(c.getSkin() + ", do you plead guilty or not guilty to your damage amount of " + randomX + ": ");
			String response = input.next();
			if(response.equals("g")) {
				if(randomX < c.getTotalDPS()) {
					p.heal(0.15);
					d.heal(0.15);
					e.heal(0.15);
					p.addHealing(p.getMaxHP() * 0.15);
					p.addHealing(d.getMaxHP() * 0.15);
					p.addHealing(e.getMaxHP() * 0.15);
					c.takeDamage(250);
					p.addDamage(250);
					System.out.println("\"Guilty as charged!\"");
				}else {
					System.out.println("\"The evidence says otherwise!\"");
					c.takeDamage(650);
					p.addDamage(650);
				}
			}
			if(response.equals("n")) {
				if(randomX > c.getTotalDPS()) {
					p.heal(0.15);
					d.heal(0.15);
					e.heal(0.15);
					p.addHealing(p.getMaxHP() * 0.15);
					p.addHealing(d.getMaxHP() * 0.15);
					p.addHealing(e.getMaxHP() * 0.15);
					c.takeDamage(250);
					p.addDamage(250);
					System.out.println("\"Innocence proven!\"");
				}else {
					System.out.println("\"The evidence says otherwise!\"");
					c.takeDamage(650);
					p.addDamage(650);
				}
			}
			p.resetUlt();
			System.out.println();
		}
	}
	
	public static void AxolAttack(Player p, Player a) {
		p.attack(a);
		a.poison(0.1, 1);
		System.out.println();
	}
	
	public static void AxolAbility(Player p, Player a, Player b) {
		p.mend(0.8, 2);
		a.mend(0.8, 2);
		b.mend(0.8, 2);
		p.setCooldown(3);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void AxolUltimate(Player p, Player a, Player b) {
		if (p.isEvolved()) {
			p.mend(0.2, 100);
			a.mend(0.2, 100);
			b.mend(0.2, 100);
		}
		p.power(0.25,  1);
		a.power(0.25,  1);
		b.power(0.25,  1);
		p.setOverhealth(p.getMaxHP() * 0.35);
		a.setOverhealth(a.getMaxHP() * 0.35);
		b.setOverhealth(b.getMaxHP() * 0.35);
		p.addHealing(p.getMaxHP() * 0.35);
		p.addHealing(a.getMaxHP() * 0.35);
		p.addHealing(b.getMaxHP() * 0.35);
		p.resetUlt();
		System.out.println("\"Oh yeah, we vibing with this upgrade.\"");
		System.out.println();
	}
	
	public static void KatrinaAttack(Player p, Player a) {
		p.attack(a);
		a.weak(0.15, 1);
		System.out.println();
	}
	
	public static void KatrinaAbility(Player p, Player a, Player b) {
		Scanner input = new Scanner(System.in);
		System.out.println("1: " + a.getSkin() +". Health: " +  a.getHealth() + "/" + a.getMaxHP() + ".");
		System.out.println("2: " + b.getSkin() +". Health: " +  b.getHealth() + "/" + b.getMaxHP() + ".");
		System.out.print("Who do you want to slide to: ");
		String targetResponse = input.next();
		System.out.println();
		if(targetResponse.equals("1")) {
			int d = p.getLoc().distance(a.getLoc());
			p.increaseTotalMovement(d);
			p.getLoc().set(a.getLoc().getX(), a.getLoc().getY());
			p.heal(0.1);
			a.heal(0.1);
			p.addHealing(p.getMaxHP() * 0.10);
			p.addHealing(a.getMaxHP() * 0.10);
			p.cleanse();
			a.cleanse();
			if (p.isEvolved()) {
				p.increaseMovement(4);
				a.increaseMovement(4);
	        }
			p.setCooldown(2);
		}
		if(targetResponse.equals("2")) {
			int d = p.getLoc().distance(b.getLoc());
			p.increaseTotalMovement(d);
			p.getLoc().set(b.getLoc().getX(), b.getLoc().getY());
			p.heal(0.1);
			b.heal(0.1);
			p.addHealing(p.getMaxHP() * 0.10);
			p.addHealing(b.getMaxHP() * 0.1);
			p.cleanse();
			b.cleanse();
			if (p.isEvolved()) {
				p.increaseMovement(4);
				b.increaseMovement(4);
	        }
			p.setCooldown(2);
		}
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void KatrinaUltimate(Player p, Player a, Player b) {
		p.power(0.35, 1);
		p.refine(1);
		a.power(0.35, 1);
		a.refine(1);
		b.power(0.35, 1);
		b.refine(1);
		p.heal(0.5);
		a.heal(0.5);
		b.heal(0.5);
		p.addHealing(p.getMaxHP() * 0.5);
		p.addHealing(a.getMaxHP() * 0.5);
		p.addHealing(b.getMaxHP() * 0.5);
		p.resetUlt();
		System.out.println("\"100,000 bells on this? Let's make it count.\"");
		System.out.println();
	}
	
	public static void MidniteAttack(Player p, Player a) {
		p.attack(a);
		a.knockbacked(p.getLoc());
		double rand = Math.random();
		if(rand <= 0.1) {
			a.daze(1);
		}
		System.out.println();
	}
	
	public static void MidniteAbility(Player p, Player a, Player b, Player c, Player d, Player e) {
		p.power(0.2, 1);
		d.power(0.2, 1);
		e.power(0.2, 1);
		if(p.inRange(a)) {
			a.takeDamage(125);
			p.addDamage(125);
			a.knockbacked(p.getLoc());
		}
		if(p.inRange(b)) {
			b.takeDamage(125);
			p.addDamage(125);
			b.knockbacked(p.getLoc());
		}
		if(p.inRange(c)) {
			c.takeDamage(125);
			p.addDamage(125);
			c.knockbacked(p.getLoc());
		}
		a.takeDamage(125);
		p.addDamage(125);
		b.takeDamage(125);
		p.addDamage(125);
		c.takeDamage(125);
		p.addDamage(125);
		a.knockbacked(p.getLoc());
		b.knockbacked(p.getLoc());
		c.knockbacked(p.getLoc());
		p.setCooldown(3);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void MidniteUltimate(Player p) {
		p.cleanse();
		p.protect(1, 2);
		p.refine(2);
		p.fortify(2);
		p.power(1, 2);
		p.resetUlt();
		System.out.println("\"This will be your last haunt!\"");
		System.out.println();
	}
	
	public static void XaraAttack(Player p, Player a) {
		p.attack(a);
		a.vulnerable(0.1, 1);
		System.out.println();
	}
	
	public static void XaraAbility(Player p, Player a, Player b) {
		p.setSights(1);
		p.protect(0.25, 2);
		p.fortify(2);
		a.protect(0.25, 2);
		a.fortify(2);
		b.protect(0.25, 2);
		b.fortify(2);
		p.heal(0.1);
		p.addHealing(p.getMaxHP() * 0.1);
		p.setCooldown(4);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void XaraUltimate(Player p, Player a, Player b, Player c) {
		a.takeDamage(300);
		b.takeDamage(300);
		c.takeDamage(300);
		p.addDamage(900);
		a.stun(1);
		b.stun(1);
		c.stun(1);
		p.resetUlt();
		System.out.println("\"Enemy team is down for maintenance.\"");
		System.out.println();
	}
	
	public static void KitharaAttack(Player p, Player a) {
		p.attack(a);
		double rand = Math.random();
		if(rand <= 0.08) {
			a.takeDamage(a.getMaxHP() * 0.10);
			p.addDamage(a.getMaxHP() * 0.10);
		}
		System.out.println();
	}
	
	public static void KitharaAbility(Player p, Player a, Player b) {
		p.protect(0.8, 1);
		a.protect(1, 1);
		b.protect(1, 1);
		p.setCooldown(4);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void KitharaUltimate(Player p, Player a, Player b, Player c) {
		a.takeDamage(300);
		p.addDamage(300);
		b.takeDamage(300);
		p.addDamage(300);
		c.takeDamage(300);
		p.addDamage(300);
		a.weak(0.35, 2);
		a.ignite(2);
		b.weak(0.35, 2);
		b.ignite(2);
		c.weak(0.35, 2);
		c.ignite(2);
		a.knockbacked(a.getLoc());
		b.knockbacked(b.getLoc());
		c.knockbacked(c.getLoc());
		p.resetUlt();
		System.out.println("\"Thank you all for coming. SORRY FOR THE WAIT!\"");
		System.out.println();
	}
	
	public static void AnjelikaAttack(Player p, Player a) {
		p.attack(a);
		int d = p.getLoc().distance(a.getLoc());
		p.increaseTotalMovement(d);
		Location l = new Location(p.getLoc().getX(), p.getLoc().getY());
		p.getLoc().set(a.getLoc().getX(), a.getLoc().getY());
		a.knockbacked(l);
		for(int i = 0; i < cover.size(); i++) {
			if(p.getLoc().eqLoc(cover.get(i).getLoc())) {
				if(cover.get(i).getName().equals("Full")) {
					p.setCover("Full");
					break;
				}
				if(cover.get(i).getName().equals("Partial")) {
					p.setCover("Partial");
					break;
				}
			}else {
				p.resetCover();
			}
		}
		double rand = Math.random();
		if(rand <= 0.15) {
			a.blind(0.3, 1);
		}
		System.out.println();
	}
	
	public static void AnjelikaAbility(Player p, Player a, Player b, Player c) {
		if(!a.inRange(p, 10) && !b.inRange(p, 10) && !c.inRange(p, 10)) {
			System.out.println("No targets in range!");
			System.out.println();
			return;
		}
		if(a.inRange(p, 10)) {
			a.ignite(1);
			a.takeDamage(a.getMaxHP() * 0.1);
			p.addDamage(a.getMaxHP() * 0.10);
		}
		if(b.inRange(p, 10)) {
			b.ignite(1);
			b.takeDamage(b.getMaxHP() * 0.1);
			p.addDamage(b.getMaxHP() * 0.10);
		}
		if(c.inRange(p, 10)) {
			c.ignite(1);
			c.takeDamage(c.getMaxHP() * 0.1);
			p.addDamage(c.getMaxHP() * 0.10);
		}
		p.setCooldown(3);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void AnjelikaUltimate(Player p) {
		p.cleanse();
		p.addHealing(p.getMaxHP() * 0.3);
		p.protect(0.3, 3);
		p.refine(3);
		p.power(0.3, 3);
		p.heal(0.1, 3);
		p.resetUlt();
		System.out.println("\"See you in heaven!\"");
		System.out.println();
	}
	
	public static void ArcherAttack(Player p, Player a) {
		if(p.overRange(a, 14)) {
			p.attack(a);
			a.takeDamage(225);
			p.addDamage(225);
			double rand = Math.random();
			if(rand <= 0.1) {
				a.paralyze(1);
			}
		}else {
			p.attack(a);
			double rand = Math.random();
			if(rand <= 0.1) {
				a.paralyze(1);
			}
		}
		System.out.println();
	}
	
	public static void ArcherAbility(Player p, Player a, Player b) {
		p.power(0.15, 1);
		p.sightsee(0.25, 1);
		a.power(0.15, 1);
		a.sightsee(0.25, 1);
		b.power(0.15, 1);
		b.sightsee(0.25, 1);
		p.increaseMovement(3);
		a.increaseMovement(3);
		b.increaseMovement(3);
		p.setCooldown(3);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void ArcherUltimate(Player p, Player a, Player b, Player c) {
		a.weak(0.15, 1);
		a.vulnerable(0.1, 1);
		a.ignite(1);
		a.poison(0.15, 1);
		b.weak(0.15, 1);
		b.vulnerable(0.1, 1);
		b.ignite(1);
		b.poison(0.15, 1);
		c.weak(0.15, 1);
		c.vulnerable(0.1, 1);
		c.ignite(1);
		c.poison(0.15, 1);
		a.takeDamage(225);
		b.takeDamage(225);
		c.takeDamage(225);
		p.addDamage(675);
		p.resetUlt();
		System.out.println("\"I'll always be one step arrowhead.\"");
		System.out.println();
	}
	
	public static void TomAttack(Player p, Player a) {
		p.attack(a);
		double rand = Math.random();
		if(rand <= 0.10) {
			a.paralyze(1);
			a.vulnerable(0.1, 1);
		}
		System.out.println();
	}
	
	public static void TomAbility(Player p) {
		p.mend(1.25, 2);
		p.protect(0.55, 2);
		p.setCooldown(4);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void TomUltimate(Player p, Player a, Player b, Player c, Location l) {
		if(a.inRange(l, 7)) {
			a.takeDamage(a.getMaxHP() * 0.3);
			p.addDamage(a.getMaxHP() * 0.3);
		}
		if(b.inRange(l, 7)) {
			b.takeDamage(b.getMaxHP() * 0.3);
			p.addDamage(b.getMaxHP() * 0.3);
		}
		if(c.inRange(l, 7)) {
			c.takeDamage(c.getMaxHP() * 0.3);
			p.addDamage(c.getMaxHP() * 0.3);
		}
		p.resetUlt();
		System.out.println("\"I will take EVERYTHING from you!\"");
		System.out.println();
	}
	
	public static void DimentioAttack(Player p, Player a) {
		if((a.getDamage() > p.getDamage() && p.inRange(a, 3)) || p.ultActive()) {
			p.attack(a);
			a.takeDamage(a.getDamage() * 0.50);
			p.addDamage(a.getDamage() * 0.50);
		}else {
			p.attack(a);
		}
		double rand = Math.random();
		if(rand <= 0.10) {
			a.freeze(1);
		}
		System.out.println();
	}
	
	public static void DimentioAbility(Player p, Player a, Player b, Player c) {
		Scanner input = new Scanner(System.in);
		System.out.println("1: " + a.getName() +a.showHP() +  a.getHealth() + "/" + a.getMaxHP());
		System.out.println("2: " + b.getName() +b.showHP() +  b.getHealth() + "/" + b.getMaxHP());
		System.out.println("3: " + c.getName() +c.showHP() +  c.getHealth() + "/" + c.getMaxHP());
		System.out.print("Who do you want to ban to the Shadow Realm: ");
		String targetResponse = input.next();
		System.out.println();
		if(targetResponse.equals("1")) {
			if(!a.isAlive()) {
				System.out.println("Target is downed.");
				System.out.println();
				return;
			}
			a.stun(1);
			a.takeDamage(a.getMaxHP() * 0.1);
			p.addDamage(a.getMaxHP() * 0.1);
			p.setCooldown(4);
			System.out.println(p.voiceline());
			System.out.println();
		}
		if(targetResponse.equals("2")) {
			if(!b.isAlive()) {
				System.out.println("Target is downed.");
				System.out.println();
				return;
			}
			b.stun(1);
			b.takeDamage(b.getMaxHP() * 0.1);
			p.addDamage(b.getMaxHP() * 0.1);
			p.setCooldown(4);
			System.out.println(p.voiceline());
			System.out.println();
		}
		if(targetResponse.equals("3")) {
			if(!c.isAlive()) {
				System.out.println("Target is downed.");
				System.out.println();
				return;
			}
			c.stun(1);
			c.takeDamage(c.getMaxHP() * 0.1);
			p.addDamage(c.getMaxHP() * 0.1);
			p.setCooldown(4);
			System.out.println(p.voiceline());
			System.out.println();
		}
	}
	
	public static void DimentioUltimate(Player p) {
		p.setUlt();
		p.protect(0.1, 100);
		p.heal(0.1, 100);
		p.refine(100);
		System.out.println("\"I'm now the most powerful VIRUS in the digital world!\"");
		System.out.println();
	}
	
	public static void GrizzAttack(Player p, Player a, Player b, Player c) {
		p.attack(a);
		if(a.inRange(b, 3)) {
			b.takeDamage(p.getDamage() / 2);
			p.addDamage(p.getDamage() / 2);
		}
		if(a.inRange(c, 3)) {
			c.takeDamage(p.getDamage() / 2);
			p.addDamage(p.getDamage() / 2);
		}
		System.out.println();
	}
	
	public static void GrizzAbility(Player p, Player a, Player b) {
		double d = 0.2;
		if(p.ultActive()) {
			d = 0.4;
			p.increaseMovement(5);
			a.increaseMovement(5);
			b.increaseMovement(5);
		}
		p.increaseMovement(5);
		a.increaseMovement(5);
		b.increaseMovement(5);
		p.power(d, 2);
		a.power(d, 2);
		b.power(d, 2);
		if(p.getHealth() < (p.getMaxHP() * 0.5)) {
			p.heal(d);
			p.addHealing(p.getMaxHP() * d);
		}
		if(a.getHealth() < (a.getMaxHP() * 0.5)) {
			a.heal(d);
			p.addHealing(a.getMaxHP() * d);
		}
		if(b.getHealth() < (b.getMaxHP() * 0.5)) {
			b.heal(d);
			p.addHealing(b.getMaxHP() * d);
		}
		p.setCooldown(4);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void GrizzUltimate(Player p) {
		p.setUlt();
		p.increaseMaxHP(p.getMaxHP() * 0.35);
		p.addHealing(p.getMaxHP() * 0.35);
		p.power(0.4,  100);
		System.out.println("\"This is a GROWTH industry!\"");
		System.out.println();
	}
	
	public static void EvilAttack(Player p, Player a, Player b, Player c) {
		p.attack(a);
		double rand = Math.random();
		if(rand <= 0.1) {
			a.paralyze(1);
			a.ignite(1);
		}
		if(a.inRange(b, 3)) {
			b.knockbacked(p.getLoc());
		}
		if(a.inRange(c, 3)) {
			c.knockbacked(p.getLoc());
		}
		System.out.println();
	}
	
	public static void EvilAbility(Player p) {
		p.protect(0.5, 1);
		p.setSights(3);
		p.setCooldown(3);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void EvilUltimate(Player p, Player a, Player b, Player c) {
		a.takeDamage(a.getMaxHP() * 0.2);
		b.takeDamage(b.getMaxHP() * 0.2);
		c.takeDamage(c.getMaxHP() * 0.2);
		p.addDamage(a.getMaxHP() * 0.2);
		p.addDamage(b.getMaxHP() * 0.2);
		p.addDamage(c.getMaxHP() * 0.2);
		a.ignite(2);
		b.ignite(2);
		c.ignite(2);
		p.resetUlt();
		System.out.println("\"The Roche Limit approaches, and so will your fates!\"");
		System.out.println();
	}
	
	public static void MasonAttack(Player p, Player a) {
		p.attack(a);
		if(p.inRange(a, 3)) {
			p.heal(0.10);
			p.addHealing(p.getMaxHP() * 0.10);
		}
		System.out.println();
	}
	
	public static void MasonAbility(Player p, Player a, Player b, Player c, Location l) {

		if(a.inRange(l, 4)) {
			a.takeDamage(250);
			p.addDamage(250);
			a.daze(1);
		}
		if(b.inRange(l, 4)) {
			b.takeDamage(250);
			p.addDamage(250);
			b.daze(1);
		}
		if(c.inRange(l, 4)) {
			c.takeDamage(250);
			p.addDamage(250);
			c.daze(1);
		}
		p.setCooldown(3);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void MasonUltimate(Player p, Player a, Player b, Player c, Player d, Player e) {
		if(p.inRange(a)) {
			a.takeDamage(a.getMaxHP() * 0.15);
			p.addDamage(a.getMaxHP() * 0.15);
			a.knockbacked(p.getLoc());
		}
		if(p.inRange(b)) {
			b.takeDamage(b.getMaxHP() * 0.15);
			p.addDamage(b.getMaxHP() * 0.15);
			b.knockbacked(p.getLoc());
		}
		if(p.inRange(c)) {
			c.takeDamage(c.getMaxHP() * 0.15);
			p.addDamage(c.getMaxHP() * 0.15);
			c.knockbacked(p.getLoc());
		}
		if(p.inRange(d)) {
			d.protect(0.1, 2);
		}
		if(p.inRange(e)) {
			e.protect(0.1, 2);
		}
		p.resetUlt();
		System.out.println("\"Tech Terror in the house!\"");
		System.out.println();
	}
	
	public static void AiricAttack(Player p, Player a) {
		p.attack(a);
		double rand = Math.random();
		if(rand <= 0.05) {
			a.paralyze(1);
			a.daze(1);
		}
		System.out.println();
	}
	
	public static void AiricAbility(Player p, Player a, Player b, Location l) {
		Scanner input = new Scanner(System.in);
		if(!p.inReach(l, 30)) {
			System.out.println("Can't send the portal that far!");
			System.out.println();
			return;
		}
		if(!a.isAlive() && !b.isAlive()) {
			p.getLoc().set(l.getX(), l.getY());
			for(int i = 0; i < orbs.size(); i++) {
				if(p.getLoc().eqLoc(orbs.get(i).getLoc()) && !p.ultReady()) {
					p.getOrb();
					orbs.remove(i);
				}
			}
			for(int i = 0; i < cover.size(); i++) {
				if(p.getLoc().eqLoc(cover.get(i).getLoc())) {
					if(cover.get(i).getName().equals("Full")) {
						p.setCover("Full");
					}
					if(cover.get(i).getName().equals("Partial")) {
						p.setCover("Partial");
					}
				}
			}
			p.power(0.05, 1);
			p.setCooldown(3);
			System.out.println("\"Oh hell naw I'm outta here.\"");
			System.out.println();
			return;
		}
		System.out.println("1: " + a.getSkin() +a.showHP() +  a.getHealth() + "/" + a.getMaxHP() + ".");
		System.out.println("2: " + b.getSkin() +b.showHP() +  b.getHealth() + "/" + b.getMaxHP() + ".");
		System.out.print("Who do you want to transport: ");
		String targetResponse = input.next();
		if(targetResponse.equals("1")) {
			a.getLoc().set(l.getX(), l.getY());
			for(int i = 0; i < orbs.size(); i++) {
				if(a.getLoc().eqLoc(orbs.get(i).getLoc()) && !a.ultReady()) {
					a.getOrb();
					orbs.remove(i);
				}
			}
			for(int i = 0; i < cover.size(); i++) {
				if(a.getLoc().eqLoc(cover.get(i).getLoc())) {
					if(cover.get(i).getName().equals("Full")) {
						a.setCover("Full");
					}
					if(cover.get(i).getName().equals("Partial")) {
						a.setCover("Partial");
					}
				}
			}
			a.power(0.15,  1);
			p.setCooldown(3);
		}
		if(targetResponse.equals("2")) {
			b.getLoc().set(l.getX(), l.getY());
			for(int i = 0; i < orbs.size(); i++) {
				if(b.getLoc().eqLoc(orbs.get(i).getLoc()) && !b.ultReady()) {
					b.getOrb();
					orbs.remove(i);
				}
			}
			for(int i = 0; i < cover.size(); i++) {
				if(b.getLoc().eqLoc(cover.get(i).getLoc())) {
					if(cover.get(i).getName().equals("Full")) {
						b.setCover("Full");
					}
					if(cover.get(i).getName().equals("Partial")) {
						b.setCover("Partial");
					}
				}
			}
			b.power(0.15,  1);
			p.setCooldown(3);
		}
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void AiricUltimate(Player p, Player a, Player b, Player c) {
		Scanner input = new Scanner(System.in);
		System.out.println("1: " + a.getName() +a.showHP() +  a.getHealth() + "/" + a.getMaxHP());
		System.out.println("2: " + b.getName() +b.showHP() +  b.getHealth() + "/" + b.getMaxHP());
		System.out.println("3: " + c.getName() +c.showHP() +  c.getHealth() + "/" + c.getMaxHP());
		System.out.print("Who do you want to turn the tables on: ");
		String targetResponse = input.next();
		if(targetResponse.equals("1")) {
			p.heal(0.2);
			p.addHealing(p.getMaxHP() * 0.2);
			Location l1 = new Location(p.getLoc().getX(), p.getLoc().getY());
			Location l2 = new Location(a.getLoc().getX(), a.getLoc().getY());
			int d = p.getLoc().distance(l2);
			p.increaseTotalMovement(d);
			p.getLoc().set(l2.getX(), l2.getY());
			a.getLoc().set(l1.getX(), l1.getY());
			p.protect(0.5, 1);
			a.vulnerable(0.2, 1);
			p.resetUlt();
		}
		if(targetResponse.equals("2")) {
			p.heal(0.2);
			p.addHealing(p.getMaxHP() * 0.2);
			Location l1 = new Location(p.getLoc().getX(), p.getLoc().getY());
			Location l2 = new Location(b.getLoc().getX(), b.getLoc().getY());
			int d = p.getLoc().distance(l2);
			p.increaseTotalMovement(d);
			p.getLoc().set(l2.getX(), l2.getY());
			b.getLoc().set(l1.getX(), l1.getY());
			p.protect(0.5, 1);
			b.vulnerable(0.2, 1);
			p.resetUlt();
		}
		if(targetResponse.equals("3")) {
			p.heal(0.2);
			p.addHealing(p.getMaxHP() * 0.2);
			Location l1 = new Location(p.getLoc().getX(), p.getLoc().getY());
			Location l2 = new Location(c.getLoc().getX(), c.getLoc().getY());
			int d = p.getLoc().distance(l2);
			p.increaseTotalMovement(d);
			p.getLoc().set(l2.getX(), l2.getY());
			c.getLoc().set(l1.getX(), l1.getY());
			p.protect(0.5, 1);
			c.vulnerable(0.2, 1);
			p.resetUlt();
		}
		System.out.println();
		System.out.println("\"Get over here!\"");
		System.out.println();
	}
	
	public static void JulianAttack(Player p, Player a, Player b, Player c) {
		p.attack(a);
		double rand = Math.random();
		if(rand > -1) {
			a.ignite(1);
			p.addDamage(175);
		}
		if(a.inRange(b, 5)) {
			b.ignite(1);
			b.takeDamage(100);
			p.addDamage(100);
		}
		if(a.inRange(c, 5)) {
			c.ignite(1);
			c.takeDamage(100);
			p.addDamage(100);
		}
		System.out.println();
	}
	
	public static void JulianAbility(Player p) {
		p.increaseMovement(7);
		p.protect(0.8, 1);
		p.setCooldown(3);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void JulianUltimate(Player p, Player a, Player b, Player c, Player d, Player e) {
		try {
			String audio = "audio/karma.wav";
			Music victoryPlayer = new Music(audio, false); 
			victoryPlayer.play();
		}catch (Exception e1) {
			System.out.println(e1);
		}
		p.heal(0.25);
		d.heal(0.25);
		e.heal(0.25);
		p.addHealing(p.getMaxHP() * 0.25);
		p.addHealing(d.getMaxHP() * 0.25);
		p.addHealing(e.getMaxHP() * 0.25);
		a.takeDamage(450);
		a.knockbacked(a.getLoc());
		b.takeDamage(450);
		b.knockbacked(b.getLoc());
		c.takeDamage(450);
		c.knockbacked(c.getLoc());
		p.addDamage(1350);
		p.resetUlt();
		System.out.println("\033[3m🎵 I hope you get everything you deserve, Karma's a bitch, I heard! 🎵\033[0m");
		System.out.println();
	}
	
	public static void GashAttack(Player p, Player a) {
		p.attack(a);
		p.power(0.1, 2);
		a.vulnerable(0.05, 1);
		System.out.println();
	}
	
	public static void GashAbility(Player p, Player a, Player b) {
		if(p.inRange(a, 3)) {
			a.protect(0.5, 2);	
		}else {
			a.protect(0.25, 2);
		}
		if(p.inRange(b, 3)) {
			b.protect(0.5, 2);		
		}else {
			b.protect(0.25, 2);		
		}
		p.protect(0.25, 2);	
		p.setCooldown(4);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void GashUltimate(Player p, Player a, Player b, Player c) {
		Scanner input = new Scanner(System.in);
		System.out.println("1: " + a.getName() +a.showHP() +  a.getHealth() + "/" + a.getMaxHP());
		System.out.println("2: " + b.getName() +b.showHP() +  b.getHealth() + "/" + b.getMaxHP());
		System.out.println("3: " + c.getName() +c.showHP() +  c.getHealth() + "/" + c.getMaxHP());
		System.out.print("Who do you want to use the Ray of Doom on: ");
		String targetResponse = input.next();
		if(targetResponse.equals("1")) {
			if(a.getHealth() < (a.getMaxHP() * 0.5)) {
				a.takeDamage(1200);
				p.addDamage(1200);
			}else {
				a.takeDamage(600);
				p.addDamage(600);
			}
			p.resetUlt();
		}
		if(targetResponse.equals("2")) {
			if(b.getHealth() < (b.getMaxHP() * 0.5)) {
				b.takeDamage(1200);
				p.addDamage(1200);
			}else {
				b.takeDamage(600);
				p.addDamage(600);
			}
			p.resetUlt();
		}
		if(targetResponse.equals("3")) {
			if(c.getHealth() < (c.getMaxHP() * 0.5)) {
				c.takeDamage(1200);
				p.addDamage(1200);
			}else {
				c.takeDamage(600);
				p.addDamage(600);
			}
			p.resetUlt();
		}
		System.out.println("\"I will put a Gash in you!\"");
		System.out.println();
	}
	
	public static void MayhemAttack(Player p, Player a) {
		p.attack(a);
		a.knockbacked(p.getLoc());
		double rand = Math.random();
		if(rand <= 0.1) {
			a.daze(1);
		}
		System.out.println();
	}
	
	public static void MayhemAbility(Player p, Player a, Player b, Player c) {
		if(!a.inRange(p) && !b.inRange(p) && !c.inRange(p)) {
			System.out.println("No targets in range!");
			System.out.println();
			return;
		}
		Scanner input = new Scanner(System.in);
		System.out.println("1: " + a.getSkin() +a.showHP() +  a.getHealth() + "/" + a.getMaxHP());
		System.out.println("2: " + b.getSkin() +b.showHP() +  b.getHealth() + "/" + b.getMaxHP());
		System.out.println("3: " + c.getSkin() +c.showHP() +  c.getHealth() + "/" + c.getMaxHP());
		System.out.print("Who do you want to throw the darkmess puddle on: ");
		String targetResponse = input.next();
		if(targetResponse.equals("1")) {
			if(p.inRange(a)) {
				a.weak(1, 1);
				a.vulnerable(0.3, 1);
				p.setCooldown(3);
				System.out.println(p.voiceline());
				System.out.println();
			}else {
				System.out.println(a.getSkin() + " is not in range!");
				System.out.println();
			}
		}
		if(targetResponse.equals("2")) {
			if(p.inRange(b)) {
				b.weak(1, 1);
				b.vulnerable(0.3, 1);
				p.setCooldown(3);
				System.out.println(p.voiceline());
				System.out.println();
			}else {
				System.out.println(b.getSkin() + " is not in range!");
				System.out.println();
			}
		}
		if(targetResponse.equals("3")) {
			if(p.inRange(c)) {
				c.weak(1, 1);
				c.vulnerable(0.3, 1);
				p.setCooldown(3);
				System.out.println(p.voiceline());
				System.out.println();
			}else {
				System.out.println(c.getSkin() + " is not in range!");
				System.out.println();
			}
		}
	}
	
	public static void MayhemUltimate(Player p) {
		p.setUlt();
		p.increaseMaxHP(p.getMaxHP() * 0.4);
		p.addHealing(p.getMaxHP() * 0.4);
		p.protect(0.2, 100);
		p.fortify(100);
		System.out.println("\"Good day to cause some Mayhem!\"");
		System.out.println();
	}
	
	public static void GatesAttack(Player p, Player a, Location l) {
		if(!a.inRange(l, 4)) {
			System.out.println("Too far to pull the target!");
			System.out.println();
			return;
		}
		if(!a.isFortified()) {
			a.getLoc().set(l.getX(), l.getY());
			a.resetCover();
		}
		p.attack(a);
		System.out.println();
	}
	
	public static void GatesAbility(Player p, Player a, Player b) {
		Scanner input = new Scanner(System.in);
		if(!a.isAlive() && !b.isAlive()) {
			p.setShield();
			p.fortify(1);
			p.setCooldown(1);
			System.out.println("\"Send in your worst now!\"");
			System.out.println();
			return;
		}
		System.out.println("1: " + a.getSkin() +a.showHP() +  a.getHealth() + "/" + a.getMaxHP() + ".");
		System.out.println("2: " + b.getSkin() +b.showHP() +  b.getHealth() + "/" + b.getMaxHP() + ".");
		System.out.println("Who do you want to give the Aegis Array to: ");
		String targetResponse = input.next();
		if(targetResponse.equals("1")) {
			a.setShield();
			a.fortify(1);
			p.setCooldown(1);
		}
		if(targetResponse.equals("2")) {
			b.setShield();
			b.fortify(1);
			p.setCooldown(1);
		}
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void GatesUltimate(Player p) {
		p.setAttacked();
		if(!p.onCooldown()) {
			p.setCooldown(1);
		}
		String[] chars = {"Lunar", "Aidan", "Finley", "Katrina", "Sammi", "Mack", "Axol", "Via", "Kailani", "Zero", "Mason", "Max", "Evil",
				"Airic", "Julian", "Solar", "Eli", "Dylan", "Orion", "Clara", "Liam", "Midnite", "Ashley", "Rocco", "Xara", "Thunder",
				"Archer", "Tom", "Gash", "Louis", "Kithara", "Audrey", "Ayson", "Chloe", "Hopper", "Radar", "Augie", "Ruby", "Norman", "Chief", "Oona", "Angelos",
				"Melony", "Echo", "Makani", "Rhythm", "Grenadine", "Patitek", "Crystal", "Velvet", "Snowfall", "Shutter", "Margo", "Petra", "Quincy"};
		//System.out.println(chars.length);
		int randomX = (int)(Math.random() * (54 - 0 + 1)) + 0;
		System.out.println("\"I have brought \"" +chars[randomX] +"\" to help us out!\"");
		System.out.println();
		p.setName(chars[randomX]);
	}
	
	public static void AudreyAttack(Player p, Player a, Player b, Player c) {
		p.attack(a);
		p.resetAttack();
		p.attack(b);
		p.resetAttack();
		p.attack(c);
		double rand = Math.random();
		if(rand <= 0.05) {
			a.knockbacked(a.getLoc());
		}
		double rand2 = Math.random();
		if(rand2 <= 0.05) {
			b.knockbacked(b.getLoc());
		}
		double rand3 = Math.random();
		if(rand3 <= 0.05) {
			c.knockbacked(c.getLoc());
		}
		System.out.println();
	}
	
	public static void AudreyAbility(Player p, Player a, Player b, Player c) {
		if(!a.inRange(p, 10) && !b.inRange(p, 10) && !c.inRange(p, 10)) {
			System.out.println("No targets in range!");
			System.out.println();
			return;
		}
		if(a.inRange(p, 10)) {
			a.weak(0.5, 1);
			a.vulnerable(0.3, 1);
		}
		if(b.inRange(p, 10)) {
			b.weak(0.5, 1);
			b.vulnerable(0.3, 1);
		}
		if(c.inRange(p, 10)) {
			c.weak(0.5, 1);
			c.vulnerable(0.3, 1);
		}
		p.setCooldown(3);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void AudreyUltimate(Player p, Player a, Player b) {
		Scanner input = new Scanner(System.in);
		System.out.println("1: " + a.getSkin() +a.showHP() +  a.getHealth() + "/" + a.getMaxHP() + ".");
		System.out.println("2: " + b.getSkin() +b.showHP() +  b.getHealth() + "/" + b.getMaxHP() + ".");
		System.out.println("3: " + p.getSkin() +b.showHP() +  p.getHealth() + "/" + p.getMaxHP() + ".");
		System.out.print("Who do you want to use the Wonder Flower on: ");
		String targetResponse = input.next();
		if(targetResponse.equals("1")) {
			if(a.revive()) {
				p.addHealing(a.getMaxHP() * 0.35);
				p.resetUlt();
				System.out.println();
				System.out.println("\"Job's not done yet " + a.getSkin() + "!\"");
			}else {
				a.heal(0.5);
				a.addHealing(a.getMaxHP() * 0.5);
				a.protect(0.75, 1);
				a.power(0.75, 1);
				p.resetUlt();
				System.out.println("\"You're unstoppable now!\"");
			}
		}
		if(targetResponse.equals("2")) {
			if(b.revive()) {
				p.addHealing(b.getMaxHP() * 0.35);
				p.resetUlt();
				System.out.println();
				System.out.println("\"Job's not done yet " + b.getSkin() + "!\"");
			}else {
				b.heal(0.5);
				b.addHealing(b.getMaxHP() * 0.5);
				b.protect(0.75, 1);
				b.power(0.75, 1);
				p.resetUlt();
				System.out.println("\"You're unstoppable now!\"");
			}
		}
		if(targetResponse.equals("3")) {
			p.heal(0.5);
			p.addHealing(p.getMaxHP() * 0.5);
			p.protect(0.75, 1);
			p.power(0.75, 1);
			p.resetUlt();
			System.out.println("\"Energized again!\"");
		}
		System.out.println();
	}
	
	public static void AysonAttack(Player p, Player a) {
		long startTime = System.currentTimeMillis(); // Get the start time
        long elapsedTime = 0L;
        Scanner input = new Scanner(System.in);
        int damage = 0;
        int score = 0;
        char []alphabet = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 
                'h', 'i', 'j', 'k', 'l', 'm', 'n',  
                'o', 'p', 'q', 'r', 's', 't', 'u', 
                'v', 'w', 'x', 'y', 'z' }; 

        while (elapsedTime < 5*1000) { // Check if less than 5 seconds have passed
            // Your repeated action goes here
        	String res = ""; 
        	for (int i = 0; i < 1; i++)  
            	res = res + alphabet[(int) (Math.random() * 100 % 26)]; 
            System.out.print("Charge up the attack by pressing " + res + ": ");
            String targetResponse = input.next();
            if(targetResponse.equals(String.valueOf(res))) {
            	damage = damage + 25;
            	score++;
            }else {
            	break;
            }
            // Update the elapsed time
            elapsedTime = System.currentTimeMillis() - startTime;
        }
        System.out.println("Accumlated a " + score + " hit combo!");
        p.attack(a);
        a.takeDamage(damage);
        if(p.hasMend()) {
			for(int i = 0; i < p.getEffects().size(); i++) {
				if(p.getEffects().get(i).getName().equals("mend")) {
					p.heal(damage, p.getEffects().get(i).getIncrease());
				}
			}
		}
        p.addDamage(damage);
        double rand = Math.random();
        if(score > 7) {
        	if(rand <= 0.4) {
        		a.freeze(1);
    			a.daze(1);
    		}
        }else {
        	if(rand <= 0.08) {
    			a.freeze(1);
    			a.daze(1);
    		}
        }
		System.out.println();
	}
	
	public static void AysonAbility(Player p, Player a, Player b, Player c, Player d, Player e) {
		p.increaseMovement(5);
		d.increaseMovement(5);
		e.increaseMovement(5);
		p.power(0.1, 1);
		d.power(0.1, 1);
		e.power(0.1, 1);
		a.weary(1);
		a.weak(0.1,  1);
		b.weary(1);
		b.weak(0.1,  1);
		c.weary(1);
		c.weak(0.1,  1);
		if (p.isEvolved()) {
			p.addDashes(1);
			a.knockbacked(p.getLoc());
			b.knockbacked(p.getLoc());
			c.knockbacked(p.getLoc());
        }
		p.setCooldown(3);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void AysonUltimate(Player p, Player a, Player b, Player c) {
		Scanner input = new Scanner(System.in);
		System.out.println("1: " + a.getName() +a.showHP() +  a.getHealth() + "/" + a.getMaxHP());
		System.out.println("2: " + b.getName() +b.showHP() +  b.getHealth() + "/" + b.getMaxHP());
		System.out.println("3: " + c.getName() +c.showHP() +  c.getHealth() + "/" + c.getMaxHP());
		System.out.print("Who do you want to tag team on: ");
		String targetResponse = input.next();
		if(targetResponse.equals("1")) {
			a.takeDamage(800);
			p.addDamage(800);
			a.stun(1);
			p.resetUlt();
		}
		if(targetResponse.equals("2")) {
			b.takeDamage(800);
			p.addDamage(800);
			b.stun(1);
			p.resetUlt();
		}
		if(targetResponse.equals("3")) {
			c.takeDamage(800);
			p.addDamage(800);
			c.stun(1);
			p.resetUlt();
		}
		System.out.println();
		System.out.println("\"Prepare for trouble... \"" +" "+ "\"and make it double!\"");
		System.out.println();
	}
	
	public static void ChloeAttack(Player p, Player a, Player b, Player c) {
		p.attack(a);
		b.power(0.05, 1);
		c.power(0.05, 1);
		System.out.println();
	}
	
	public static void ChloeAbility(Player p, Player a, Player b) {
		p.heal(0.2);
		a.heal(0.2);
		b.heal(0.2);
		p.addHealing(p.getMaxHP() * 0.2);
		p.addHealing(a.getMaxHP() * 0.2);
		p.addHealing(b.getMaxHP() * 0.2);
		p.counter(1);
		p.fortify(1);
		a.counter(1);
		a.fortify(1);
		b.counter(1);
		b.fortify(1);
		p.setCooldown(3);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void ChloeUltimate(Player p, Player a, Player b) {
		p.protect(0.4, 2);
		p.power(0.6, 2);
		a.protect(0.4, 2);
		a.power(0.6, 2);
		b.protect(0.4, 2);
		b.power(0.6, 2);
		p.resetUlt();
		System.out.println("\"If you need something done right, do it yourself!\"");
		System.out.println();
	}
	
	public static void HopperAttack(Player p, Player a) {
		p.attack(a);
		a.poison(0.2, 1);
		System.out.println();
	}
	
	public static void HopperAbility(Player p, Player a, Player b) {
		Scanner input = new Scanner(System.in);
		System.out.println("1: " + a.getSkin() +a.showHP() +  a.getHealth() + "/" + a.getMaxHP() + ".");
		System.out.println("2: " + b.getSkin() +b.showHP() +  b.getHealth() + "/" + b.getMaxHP() + ".");
		System.out.println("3: " + p.getSkin() +b.showHP() +  p.getHealth() + "/" + p.getMaxHP() + ".");
		System.out.print("Who do you want to lead the charge: ");
		String targetResponse = input.next();
		if(targetResponse.equals("1")) {
			a.resetAttack();
			p.setCooldown(2);
			System.out.println(p.voiceline());
		}
		if(targetResponse.equals("2")) {
			b.resetAttack();
			p.setCooldown(2);
			System.out.println(p.voiceline());
		}
		if(targetResponse.equals("3")) {
			p.resetAttack();
			p.setCooldown(2);
			System.out.println(p.voiceline());
		}
		System.out.println();
	}
	
	public static void HopperUltimate(Player p, Player a, Player b) {
		p.addHealing(p.getMaxHP() * 0.3);
		p.addHealing(a.getMaxHP() * 0.3);
		p.addHealing(b.getMaxHP() * 0.3);
		p.reflect(2);
		p.protect(0.5, 2);
		p.heal(0.1, 3);
		a.heal(0.1, 3);
		b.heal(0.1, 3);
		p.resetUlt();
		System.out.println("\"For the Crusaders!\"");
		System.out.println();
	}
	
	public static void RedgarAttack(Player p, Player a) {
		p.attack(a);
		double rand = Math.random();
		if(rand <= 0.1) {
			a.blind(0.2, 1);
			a.paralyze(1);
		}
		if(p.ultActive()) {
			p.increaseDPSNum(10);
			p.increaseMaxHP(25);
		}
		System.out.println();
	}
	
	public static void RedgarAbility(Player p, Player a, Player b) {
		p.cleanse();
		a.cleanse();
		b.cleanse();
		p.heal(0.2);
		a.heal(0.2);
		b.heal(0.2);
		p.addHealing(p.getMaxHP() * 0.2);
		p.addHealing(a.getMaxHP() * 0.2);
		p.addHealing(b.getMaxHP() * 0.2);
		p.refine(2);
		a.refine(2);
		b.refine(2);
		p.setCooldown(4);
		System.out.println(p.voiceline());
		System.out.println();
		if(p.ultActive()) {
			p.increaseDPSNum(10);
			p.increaseMaxHP(25);
		}
	}
	
	public static void RedgarUltimate(Player p) {
		p.setUlt();
		System.out.println("\"I will prove my worth!\"");
		System.out.println();
	}
	
	public static void RadarAttack(Player p, Player a) {
		p.attack(a);
		p.getLoc().set(a.getLoc().getX(), a.getLoc().getY());
		p.increaseMovement(3);
		for(int i = 0; i < cover.size(); i++) {
			if(p.getLoc().eqLoc(cover.get(i).getLoc())) {
				if(cover.get(i).getName().equals("Full")) {
					p.setCover("Full");
					break;
				}
				if(cover.get(i).getName().equals("Partial")) {
					p.setCover("Partial");
					break;
				}
			}else {
				p.resetCover();
			}
		}
		System.out.println();
	}
	
	public static void RadarAbility(Player p, Player a, Player b, Player c) {
		a.daze(1);
		a.weary(1);
		b.daze(1);
		b.weary(1);
		c.daze(1);
		c.weary(1);
		p.setCooldown(3);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void RadarUltimate(Player p, Player a, Player b, Player c, Player d, Player e) {
		Scanner input = new Scanner(System.in);
		int totalOrbs = 0;
		if(!d.isAlive() && !e.isAlive()) {
			ArrayList<Effect> e1 = new ArrayList<Effect>();
			Effect HopperProtect = new Effect("power", 0.6, 1);
			e1.add(HopperProtect);
			p.addEffects(e1);
			p.applyEffects();
			p.increaseMovement(10);
			System.out.println("\"All me? It's showtime!\"");
			System.out.println();
			p.resetUlt();
			return;
		}
		if(a.getOrbCount() > 0) {
			a.removeOrb();
			totalOrbs++;
		}
		if(b.getOrbCount() > 0) {
			b.removeOrb();
			totalOrbs++;
		}
		if(c.getOrbCount() > 0) {
			c.removeOrb();
			totalOrbs++;
		}
		if(totalOrbs == 0) {
			System.out.println("\"Wow... they are broke.\"");
			System.out.println();
			return;
		}
		System.out.println("1: " + d.getName() +d.showHP() +  d.getHealth() + "/" + d.getMaxHP() + ". Cover: " + d.getCover());
		System.out.println("2: " + e.getName() +e.showHP() +  e.getHealth() + "/" + e.getMaxHP() + ". Cover: " + e.getCover());
		for(int i = 0; i < totalOrbs; i++) {
			System.out.print("Who do you want to give an orb to: ");
			String targetResponse = input.next();
			if(targetResponse.equals("1") && d.isAlive()) {
				if(d.ultReady()) {
					d.increaseMovement(5);
				}else {
					d.getOrb();
				}
			}
			if(targetResponse.equals("2") && e.isAlive()) {
				if(e.ultReady()) {
					e.increaseMovement(5);
				}else {
					e.getOrb();
				}
			}
		}
		System.out.println("\"Never even saw me take what is ours.\"");
		System.out.println();
		p.resetUlt();
	}
	
	public static void OonaAttack(Player p, Player a) {
		p.attack(a);
		a.knockbacked(p.getLoc());
		System.out.println();
	}
	
	public static void OonaAbility(Player p, Player a, Player b, Player c) {
		Scanner input = new Scanner(System.in);
		System.out.println("1: " + a.getSkin() +a.showHP() +  a.getHealth() + "/" + a.getMaxHP());
		System.out.println("2: " + b.getSkin() +b.showHP() +  b.getHealth() + "/" + b.getMaxHP());
		System.out.println("3: " + c.getSkin() +c.showHP() +  c.getHealth() + "/" + c.getMaxHP());
		System.out.print("Who do you want to throw the VR Goggles on: ");
		String targetResponse = input.next();
		if(targetResponse.equals("1")) {
			a.blind(0.75, 1);
			a.daze(1);
			p.setCooldown(3);
			System.out.println(p.voiceline());
			System.out.println();
		}
		if(targetResponse.equals("2")) {
			b.blind(0.75, 1);
			b.daze(1);
			p.setCooldown(3);
			System.out.println(p.voiceline());
			System.out.println();
		}
		if(targetResponse.equals("3")) {
			c.blind(0.75, 1);
			c.daze(1);
			p.setCooldown(3);
			System.out.println(p.voiceline());
			System.out.println();
		}
		a.setChance(-0.1);
		b.setChance(-0.1);
		c.setChance(-0.1);
	}
	
	public static void OonaUltimate(Player p, Player a, Player b) {
		p.protect(0.1, 100);
		p.power(0.1, 100);
		p.heal(0.1, 100);
		a.protect(0.1, 100);
		a.power(0.1, 100);
		a.heal(0.1, 100);
		b.protect(0.1, 100);
		b.power(0.1, 100);
		b.heal(0.1, 100);
		p.resetUlt();
		System.out.println("\"Welcome to my World!\"");
		System.out.println();
	}
	
	public static void AugieAttack(Player p, Player a) {
		p.attack(a);
		p.setShield();
		System.out.println();
	}
	
	public static void AugieAbility(Player p, Player a, Player b) {
		p.sightsee(0.75, 2);
		a.sightsee(0.75, 2);
		b.sightsee(0.75, 2);
		p.setCooldown(4);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void AugieUltimate(Player p, Player a, Player b, Player c) {
		Scanner input = new Scanner(System.in);
		System.out.println("1: " + a.getName() +a.showHP() +  a.getHealth() + "/" + a.getMaxHP() +  ". Cover: " + a.getCover());
		System.out.println("2: " + b.getName() +b.showHP() +  b.getHealth() + "/" + b.getMaxHP() +  ". Cover: " + b.getCover());
		System.out.println("3: " + c.getName() +c.showHP() +  c.getHealth() + "/" + c.getMaxHP() +  ". Cover: " + c.getCover());
		for(int i = 0; i < 2; i++) {
			int x = 0;
			int y = 0;
			System.out.print("Who do you want to fire the cannon on: ");
			String targetResponse = input.next();
			if(targetResponse.equals("1")) {
				p.addDamage(400);
				double rand = Math.random();
				if(rand <= 0.5) {
					x = 100;
				}else {
					x = -100;
				}
				double rand2 = Math.random();
				if(rand2 <= 0.5) {
					y = 100;
				}else {
					y = -100;
				}
				if(!a.isFortified()) {
					a.getLoc().set(x, y);
					a.knockbacked(p.getLoc());
				}
			}
			if(targetResponse.equals("2")) {
				p.addDamage(400);
				double rand = Math.random();
				if(rand <= 0.5) {
					x = 100;
				}else {
					x = -100;
				}
				double rand2 = Math.random();
				if(rand2 <= 0.5) {
					y = 100;
				}else {
					y = -100;
				}
				if(!b.isFortified()) {
					b.getLoc().set(x, y);
					b.knockbacked(p.getLoc());
				}
			}
			if(targetResponse.equals("3")) {
				p.addDamage(400);
				double rand = Math.random();
				if(rand <= 0.5) {
					x = 100;
				}else {
					x = -100;
				}
				double rand2 = Math.random();
				if(rand2 <= 0.5) {
					y = 100;
				}else {
					y = -100;
				}
				if(!c.isFortified()) {
					c.getLoc().set(x, y);
					c.knockbacked(p.getLoc());
				}
			}
		}
		p.resetUlt();
		System.out.println("\"To the Mariana Trench with you!\"");
		System.out.println();
	}
	
	public static void RubyAttack(Player p, Player a) {
		p.attack(a);
		double rand = Math.random();
		if(rand <= 0.75) {
			a.blind(0.05, 1);
			a.vulnerable(0.05, 1);
			a.weak(0.05,  1);
		}
		System.out.println();
	}
	
	public static void RubyAbility(Player p, Location l) {
		Scanner input = new Scanner(System.in);
		System.out.print("What tile do you want to make: ");
		String targetResponse = input.next();
		b.addTile(targetResponse, l);
		p.setCooldown(2);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void RubyUltimate(Player p, Player a, Player b) {
		p.resetCooldown();
		a.resetCooldown();
		b.resetCooldown();
		p.protect(1, 1);
		a.protect(1, 1);
		b.protect(1, 1);
		p.resetUlt();
		System.out.println("\"Gamemode Creative activated!\"");
		System.out.println();
	}
	
	public static void NormanAttack(Player p, Player a, Player b, Player c) {
		p.attack(a);
		p.heal(0.1);
		b.heal(0.05);
		c.heal(0.05);
		p.addHealing(p.getMaxHP() * 0.1);
		p.addHealing(b.getMaxHP() * 0.05);
		p.addHealing(c.getMaxHP() * 0.05);
		System.out.println();
	}
	
	public static void NormanAbility(Player p, Player a, Player b, Player c) {
		a.poison(1,  1);
		a.blind(0.5, 1);
		b.poison(1,  1);
		b.blind(0.5, 1);
		c.poison(1,  1);
		c.blind(0.5, 1);
		p.setCooldown(4);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void NormanUltimate(Player p, Player a, Player b) {
		double power = 2.5;
		System.out.println(p.normanPower());
		if (p.isEvolved()) {
			for(int i = 0; i < p.normanPower(); i++) {
				power = power + 0.1;
			}
        }
		Scanner input = new Scanner(System.in);
		System.out.println("1: " + a.getSkin() +a.showHP() +  a.getHealth() + "/" + a.getMaxHP() + ".");
		System.out.println("2: " + b.getSkin() +b.showHP() +  b.getHealth() + "/" + b.getMaxHP() + ".");
		System.out.println("3: " + p.getSkin() +b.showHP() +  p.getHealth() + "/" + p.getMaxHP() + ".");
		System.out.print("Who do you want to be able to one punch: ");
		String targetResponse = input.next();
		if(targetResponse.equals("1")) {
			a.power(power, 1);
			p.resetUlt();
			System.out.println();
			System.out.println("\"You have one turn kill now!\"");
		}
		if(targetResponse.equals("2")) {
			b.power(power, 1);
			p.resetUlt();
			System.out.println();
			System.out.println("\"You have one turn kill now!\"");
		}
		if(targetResponse.equals("3")) {
			p.power(power, 1);
			p.resetUlt();
			System.out.println("\"Why did I give myself this power?\"");
		}
		System.out.println();
	}
	
	public static void JesseAttack(Player p, Player a, Player b, Player c) {
		p.attack(a);
		p.resetAttack();
		p.attack(b);
		p.resetAttack();
		p.attack(c);
		if (p.isEvolved()) {
			if (p.inRange(a)) {
				p.attack(a);
				p.resetAttack();
			}
			if (p.inRange(b)) {
				p.attack(b);
				p.resetAttack();
			}
			if (p.inRange(c)) {
				p.attack(c);
				p.resetAttack();
			}
        }
		if (p.inRange(a)) {
			a.ignite(1);
			p.addDamage(175);
		}
		if (p.inRange(b)) {
			b.ignite(1);
			p.addDamage(175);
		}
		if (p.inRange(c)) {
			c.ignite(1);
			p.addDamage(175);
		}
		if(p.ultActive()) {
			a.knockbacked(a.getLoc());
			b.knockbacked(b.getLoc());
			c.knockbacked(c.getLoc());
		}
		System.out.println();
	}
	
	public static void JesseAbility(Player p) {
		p.power(2, 1);
		p.protect(0.7, 1);
		p.setCooldown(4);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void JesseUltimate(Player p) {
		p.power(3,  1);
		p.protect(0.9, 1);
		p.setUlt();
		p.resetUlt();
		System.out.println("\"Rise from the depths of hell Ignis!\"");
		System.out.println();
	}
	
	public static void ChiefAttack(Player p, Player a) {
		p.attack(a);
		int d = p.getLoc().distance(a.getLoc());
		p.increaseTotalMovement(d);
		Location l = new Location(p.getLoc().getX(), p.getLoc().getY());
		p.getLoc().set(a.getLoc().getX(), a.getLoc().getY());
		a.knockbacked(l);
		for(int i = 0; i < cover.size(); i++) {
			if(p.getLoc().eqLoc(cover.get(i).getLoc())) {
				if(cover.get(i).getName().equals("Full")) {
					p.setCover("Full");
					break;
				}
				if(cover.get(i).getName().equals("Partial")) {
					p.setCover("Partial");
					break;
				}
			}else {
				p.resetCover();
			}
		}
		double rand = Math.random();
		if(rand <= 0.05) {
			a.daze(1);
		}
		System.out.println();
	}
	
	public static void ChiefAbility(Player p, Player a, Player b) {
		if(a.isAlive()) {
			a.getLoc().set(p.getLoc().getX(), p.getLoc().getY());
		}
		if(b.isAlive()) {
			b.getLoc().set(p.getLoc().getX(), p.getLoc().getY());
		}
		p.heal(0.1);
		a.heal(0.1);
		b.heal(0.1);
		p.addHealing(p.getMaxHP() * 0.1);
		p.addHealing(a.getMaxHP() * 0.1);
		p.addHealing(b.getMaxHP() * 0.1);
		if (p.isEvolved()) {
			p.setShield();
			a.setShield();
			b.setShield();
			p.power(0.15, 1);
			a.power(0.15, 1);
			b.power(0.15, 1);
			p.setCooldown(3);
        }else {
        	p.setCooldown(4);
        }
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void ChiefUltimate(Player p, Player a, Player b, Player c, Player d, Player e) {
		if(a.inRange(p, 3)) {
			a.stun(1);
		}else if(p.inRange(a)){
			double rand = Math.random();
			if(rand <= 0.75) {
				a.stun(1);
			}
		}
		if(b.inRange(p, 3)) {
			b.stun(1);
		}else if(p.inRange(b)) {
			double rand = Math.random();
			if(rand <= 0.75) {
				b.stun(1);
			}
		}
		if(c.inRange(p, 3)) {
			c.stun(1);
		}else if(p.inRange(c)) {
			double rand = Math.random();
			if(rand <= 0.75) {
				c.stun(1);
			}
		}
		if(p.getHealth() < (p.getMaxHP() * 0.25)) {
			p.heal(0.5);
			p.addHealing(p.getMaxHP() * 0.5);
		}
		if(d.getHealth() < (d.getMaxHP() * 0.25)) {
			d.heal(0.5);
			p.addHealing(d.getMaxHP() * 0.5);
		}
		if(e.getHealth() < (e.getMaxHP() * 0.25)) {
			e.heal(0.5);
			p.addHealing(e.getMaxHP() * 0.5);
		}
		p.resetUlt();
		System.out.println("\"Hammer down!\"");
		System.out.println();
	}
	
	public static void AngelosAttack(Player p, Player a, Player b, Player c) {
		Scanner input = new Scanner(System.in);
		String range = "No";
		if(p.inRange(a)) {
			range = "Yes";
		}
		String range2 = "No";
		if(p.inRange(b)) {
			range2 = "Yes";
		}
		String range3 = "No";
		if(p.inRange(c)) {
			range3 = "Yes";
		}
		System.out.println("1: Nightfall Snare");
		System.out.println("2: Celestial Cyclone");
		System.out.print("Which technique do you want to use with the chains: ");
		String targetResponse = input.next();
		if(targetResponse.equals("2")) {
			if(!p.inRange(a) && !p.inRange(b) && !p.inRange(c)) {
				System.out.println();
				System.out.println("No targets in range!");
				System.out.println();
				return;
			}
			if(p.inRange(a)) {
				p.attack(a);
				a.knockbacked(p.getLoc());
				a.weak(0.1,  1);
				a.poison(0.1,  1);
			}
			if(p.inRange(b)) {
				p.attack(b);
				b.knockbacked(p.getLoc());
				b.weak(0.1,  1);
				b.poison(0.1,  1);
			}
			if(p.inRange(c)) {
				p.attack(c);
				c.knockbacked(p.getLoc());
				c.weak(0.1,  1);
				c.poison(0.1,  1);
			}
		}
		if(targetResponse.equals("1")) {
			System.out.println("1: " + a.getSkin() +". Health: " +  a.getHealth() + "/" + a.getMaxHP() + ". In Range: " + range + ". Cover: " + a.getCover());
			System.out.println("2: " + b.getSkin() +". Health: " +  b.getHealth() + "/" + b.getMaxHP() + ". In Range: " + range2 + ". Cover: " + b.getCover());
			System.out.println("3: " + c.getSkin() +". Health: " +  c.getHealth() + "/" + c.getMaxHP() + ". In Range: " + range3 + ". Cover: " + c.getCover());
			System.out.print("Who do you want to snare: ");
			String targetResponse2 = input.next();
			if(targetResponse2.equals("1")) {
				if(p.inRange(a)) {
					if(!a.isFortified()) {
						a.resetCover();
						a.getLoc().set(p.getLoc().getX(), p.getLoc().getY());
					}
					p.attack(a);
				}else {
					System.out.println();
					System.out.println("Target is out of range!");
					System.out.println();
				}
			}
			if(targetResponse2.equals("2")) {
				if(p.inRange(b)) {
					if(!b.isFortified()) {
						b.resetCover();
						b.getLoc().set(p.getLoc().getX(), p.getLoc().getY());
					}
					p.attack(b);
				}else {
					System.out.println();
					System.out.println("Target is out of range!");
					System.out.println();
				}
			}
			if(targetResponse2.equals("3")) {
				if(p.inRange(c)) {
					if(!c.isFortified()) {
						c.resetCover();
						c.getLoc().set(p.getLoc().getX(), p.getLoc().getY());
					}
					p.attack(c);
				}else {
					System.out.println();
					System.out.println("Target is out of range!");
					System.out.println();
				}
			}
		}
		System.out.println();
	}
	
	public static void AngelosAbility(Player p, Player a, Player b) {
		p.setShield();
		a.setShield();
		b.setShield();
		p.cleanse();
		a.cleanse();
		b.cleanse();
		p.power(0.1, 1);
		a.power(0.1, 1);
		b.power(0.1, 1);
		p.setCooldown(3);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void AngelosUltimate(Player p, Player a, Player b) {
		p.heal(0.2);
		a.setCanon();
		b.setCanon();
		p.resetUlt();
		System.out.println();
		System.out.println("\"We save either one person or the entire world!\"");
		System.out.println();
	}
	
	public static void MelonyAttack(Player p, Player a) {
		p.attack(a);
		p.setJumpHeal();
		System.out.println(p.getSkin() + "'s jumping heal is now active.");
		System.out.println();
	}
	
	public static void MelonyAbility(Player p, Player a, Player b, Player c, Player d, Player e, Location l) {
		if(!p.inReach(l, 7)) {
			System.out.println("Can't send Swiftwing that far!");
			System.out.println();
			return;
		}
		Scanner input = new Scanner(System.in);
		boolean first = true;
		for(int i = 0; i < utility.size(); i++) {
			if(utility.get(i).getName().equals("Swiftwing") && utility.get(i).owner(p)) {
				first = false;
				break;
			}
		}
		if (l.eqLoc(p.getLoc()) || l.eqLoc(d.getLoc()) || l.eqLoc(e.getLoc())) {
			if (l.eqLoc(p.getLoc())) {
				p.heal(0.1);
				p.addHealing(d.getMaxHP() * 0.1);
			}
			if (l.eqLoc(d.getLoc())) {
				d.heal(0.1);
				p.addHealing(d.getMaxHP() * 0.1);
			}
			if (l.eqLoc(e.getLoc())) {
				e.heal(0.1);
				p.addHealing(d.getMaxHP() * 0.1);
			}
			p.setCooldown(3);
			System.out.println(p.voiceline());
			System.out.println();
			if(p.usedAbility()) {
				if(first) {
					Utility SwiftWing = new Utility("Swiftwing", l, p, d, e, a, b, c);
					utility.add(SwiftWing);
				}
			}
			return;
		}
		
		boolean pickup = false;
		boolean orb = false;
		String gotCover = "";
		
		for(int i = 0; i < cover.size(); i++) {
			if(l.eqLoc(cover.get(i).getLoc())) {
				if(cover.get(i).getName().equals("Full")) {
					pickup = true;
					cover.remove(i);
					gotCover = "Full";
					break;
				}
				if(cover.get(i).getName().equals("Partial")) {
					pickup = true;
					cover.remove(i);
					gotCover = "Partial";
					break;
				}
			}
		}
		if(!pickup) {
			for(int i = 0; i < orbs.size(); i++) {
				if(l.eqLoc(orbs.get(i).getLoc())) {
					orbs.remove(i);
					orb = true;
				}
			}
		}
		
		if(pickup) {
			System.out.println("1: " + d.getSkin() +d.showHP() +  d.getHealth() + "/" + d.getMaxHP() + ".");
			System.out.println("2: " + e.getSkin() +e.showHP() +  e.getHealth() + "/" + e.getMaxHP() + ".");
			System.out.println("3: " + p.getSkin() +p.showHP() +  p.getHealth() + "/" + p.getMaxHP() + ".");
			System.out.print("Who do you want to give the " + gotCover + " cover to: ");
			String targetResponse = input.next();
			if(targetResponse.equals("1")) {
				Cover block = new Cover(gotCover, new Location(d.getLoc().getX(), d.getLoc().getY()));
				d.setCover(gotCover);
				p.setCooldown(3);
				cover.add(block);
				System.out.println(p.voiceline());
				System.out.println();
			}
			if(targetResponse.equals("2")) {
				Cover block = new Cover(gotCover, new Location(e.getLoc().getX(), e.getLoc().getY()));
				e.setCover(gotCover);
				cover.add(block);
				p.setCooldown(3);
				System.out.println(p.voiceline());
				System.out.println();
			}
			if(targetResponse.equals("3")) {
				Cover block = new Cover(gotCover, new Location(p.getLoc().getX(), p.getLoc().getY()));
				p.setCover(gotCover);
				p.setCooldown(3);
				cover.add(block);
				System.out.println(p.voiceline());
				System.out.println();
			}
		}else if (orb){
			System.out.println("1: " + d.getSkin() +d.showHP() +  d.getHealth() + "/" + d.getMaxHP() + ".");
			System.out.println("2: " + e.getSkin() +e.showHP() +  e.getHealth() + "/" + e.getMaxHP() + ".");
			System.out.println("3: " + p.getSkin() +b.showHP() +  p.getHealth() + "/" + p.getMaxHP() + ".");
			System.out.print("Who do you want to give the orb to: ");
			String targetResponse = input.next();
			if(targetResponse.equals("1")) {
				if(!d.ultReady()) {
					d.getOrb();
				}
				p.setCooldown(3);
				System.out.println(p.voiceline());
				System.out.println();
			}
			if(targetResponse.equals("2")) {
				if(!e.ultReady()) {
					e.getOrb();
				}
				p.setCooldown(3);
				System.out.println(p.voiceline());
				System.out.println();
			}
			if(targetResponse.equals("3")) {
				if(!p.ultReady()) {
					p.getOrb();
				}
				p.setCooldown(3);
				System.out.println(p.voiceline());
				System.out.println();
			}
		}
		
		if(p.usedAbility()) {
			if(first) {
				Utility SwiftWing = new Utility("Swiftwing", l, p, d, e, a, b, c);
				utility.add(SwiftWing);
			}
		}
	}
	
	public static void MelonyUltimate(Player p, Player a, Player b, Player c, Location l) {
		if(!p.inReach(l, 10)) {
			System.out.println("Can't send Howler that far!");
			System.out.println();
			return;
		}
		boolean first = true;
		for(int i = 0; i < utility.size(); i++) {
			if(utility.get(i).getName().equals("Howler") && utility.get(i).owner(p)) {
				first = false;
				break;
			}
		}
		if(a.inRange(l, 6)) {
			a.takeDamage(250);
			p.addDamage(250);
			a.weak(0.15, 1);
			a.vulnerable(0.15, 1);
		}
		if(b.inRange(l, 6)) {
			b.takeDamage(250);
			p.addDamage(250);
			b.weak(0.15, 1);
			b.vulnerable(0.15, 1);
		}
		if(c.inRange(l, 6)) {
			c.takeDamage(250);
			p.addDamage(250);
			c.weak(0.15, 1);
			c.vulnerable(0.15, 1);
		}
		p.resetUlt();
		if(first) {
			System.out.println();
			System.out.println("\"Monster on the loose!\"");
			System.out.println();
		}else {
			System.out.println();
			System.out.println("\"Howler's not finished with you!\"");
			System.out.println();
		}
		
		if(p.usedUlt()) {
			if(first) {
				Utility Howler = new Utility("Howler", l, p, p, p, a, b, c);
				utility.add(Howler);
			}
		}
	}
	
	public static void EchoAttack(Player p, Player a, Player b, Player c) {
		if (p.soundwaveReady()) {
			System.out.println("Soundwave Barrier unleashed!");
			if (p.inRange(a)) {
				a.takeDamage(200);
				p.addDamage(200);
				a.knockbacked(p.getLoc());
			}
			if (p.inRange(b)) {
				b.takeDamage(200);
				p.addDamage(200);
				b.knockbacked(p.getLoc());
			}
			if (p.inRange(c)) {
				c.takeDamage(200);
				p.addDamage(200);
				c.knockbacked(p.getLoc());
			}
			p.resetCharge();
			System.out.println();
		}
		p.attack(a);
		System.out.println();
	}
	
	public static void EchoAbility(Player p, Player a, Player b, Player c, Location l, Location l2) {
		if(!p.inRange(l) || !p.inRange(l2)) {
			System.out.println("Can't send Sound Sensors that far!");
			System.out.println();
			return;
		}
		Utility Sensor = new Utility("Sensor", l, p, p, p, a, b, c);
		utility.add(Sensor);
		Utility Sensor2 = new Utility("Sensor", l2, p, p, p, a, b, c);
		utility.add(Sensor2);
		p.setCooldown(3);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void EchoUltimate(Player p, Player a, Player b, Player c, Player d, Player e) {
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).getName().equals("Sphere") && GameSim.utility.get(j).owner(p)) {
				GameSim.utility.remove(j);
			}
		}
		Utility Sphere = new Utility("Sphere", new Location(p.getLoc().getX(), p.getLoc().getY()), p, d, e, a, b, c);
		utility.add(Sphere);
		p.resetUlt();
		System.out.println("\"Blow up into smithereens and spew my tiny symphony!\"");
		System.out.println();
	}
	
	public static void MakaniAttack(Player p, Player a, Player b, Player c) {
		if(!p.inRange(a) && !p.inRange(b) && !p.inRange(c)) {
			System.out.println("No targets in range!");
			System.out.println();
			return;
		}
		if(p.inRange(a)){
			p.attack(a);
			a.dragIn(p.getLoc(), 1);
		}
		if(p.inRange(b)){
			p.attack(b);
			b.dragIn(p.getLoc(), 1);
		}
		if(p.inRange(c)){
			p.attack(c);
			c.dragIn(p.getLoc(), 1);
		}
		System.out.println();
	}
	
	public static void MakaniAbility(Player p, Player a, Player b, Player c, Player d, Player e) {
		if(p.onCooldown()) {
			for(int j = 0; j < GameSim.utility.size(); j++) {
				if(GameSim.utility.get(j).getName().equals("Wind") && GameSim.utility.get(j).owner(p)) {
					GameSim.utility.get(j).activateWind();
					p.setWind();
					GameSim.utility.remove(j);
				}
			}
			System.out.println(p.voiceline());
			System.out.println();
			return;
		}
		Scanner input = new Scanner(System.in);
		System.out.println("1: Gust Escape");
		System.out.println("2: Tempest Surge");
		System.out.print("What do you want to do with the wind point: ");
		String attackResponse = input.next();
		if(attackResponse.equals("1")) {
			Utility Wind = new Utility("Wind", new Location(p.getLoc().getX(), p.getLoc().getY()), p, d, e, a, b, c);
			utility.add(Wind);
			Wind.setEscape(true);
			System.out.println();
			System.out.println(p.voiceline());
			p.setCooldown(4);
			System.out.println();
		}
		if(attackResponse.equals("2")) {
			Location l = SetCursor(p, a, b, c, d, e, p.getRange());
			if(!p.inReach(l, 15)) {
				System.out.println("Can't send the Wind Point that far!");
				System.out.println();
				return;
			}
			Utility Wind = new Utility("Wind", l, p, d, e, a, b, c);
			utility.add(Wind);
			System.out.println(p.voiceline());
			p.setCooldown(4);
			System.out.println();
		}
	}
	
	public static void MakaniUltimate(Player p, Player a, Player b, Player c) {
		if(!p.inRange(a) && !p.inRange(b) && !p.inRange(c)) {
			System.out.println("No one in range to use the Typhoon!");
			System.out.println();
			return;
		}
		if(p.inRange(a) && a.isAlive()) {
			a.takeDamage(225);
			a.takeDamage(225);
			a.takeDamage(225);
			p.addDamage(675);
			if(!a.isFortified()) {
				a.resetCover();
				a.getLoc().set(p.getLoc().getX(), p.getLoc().getY());
			}
		}
		if(p.inRange(b) && b.isAlive()) {
			b.takeDamage(225);
			b.takeDamage(225);
			b.takeDamage(225);
			p.addDamage(675);
			if(!b.isFortified()) {
				b.resetCover();
				b.getLoc().set(p.getLoc().getX(), p.getLoc().getY());
			}
		}
		if(p.inRange(c) && c.isAlive()) {
			c.takeDamage(225);
			c.takeDamage(225);
			c.takeDamage(225);
			p.addDamage(675);
			if(!c.isFortified()) {
				c.resetCover();
				c.getLoc().set(p.getLoc().getX(), p.getLoc().getY());
			}
		}
		p.resetUlt();
		System.out.println("\"Reports call for a Typhoon!\"");
		System.out.println();
	}
	
	public static void RhythmAttack(Player p, Player a) {
		long startTime = System.currentTimeMillis(); // Get the start time
        long elapsedTime = 0L;
        Scanner input = new Scanner(System.in);
        StringBuilder binaryString = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 8; i++) {
            int randomBit = random.nextInt(2); // Generates 0 or 1
            binaryString.append(randomBit);
        }

        System.out.println("Memorize the beat: " + binaryString);
        while (elapsedTime < 3*1000) { // Check if less than 5 seconds have passed
            // Your repeated action goes here	
            elapsedTime = System.currentTimeMillis() - startTime;
        }
        for(int i = 0; i < 100; i++) {
        	System.out.println();
        }
        System.out.print("Type in the beat: ");
		String targetResponse = input.next();
		if(targetResponse.equals(String.valueOf(binaryString))) {
			System.out.println("\"Oh yeah! That's the groove!\"");
			p.attack(a);
			a.takeDamage(p.getDamage() * 0.5);
	        p.addDamage(p.getDamage() * 0.5);
		}else {
			System.out.println("\"Damn I missed my extra shot there!\"");
			p.attack(a);
		}
		System.out.println();
		
	}
	
	public static void RhythmAbility(Player p) {
		if(p.getFitbit().equals("Recovery")) {
			p.setFitbit("Sprint");
			System.out.println("Fitbit set to Sprint.");
		}else if(p.getFitbit().equals("Sprint")){
			p.setFitbit("Powerburn");
			System.out.println("Fitbit set to Powerburn.");
		}else if(p.getFitbit().equals("Powerburn")){
			p.setFitbit("Recovery");
			System.out.println("Fitbit set to Recovery.");
		}
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void RhythmUltimate(Player p, Player a, Player b) {
		p.cleanse();
		a.cleanse();
		b.cleanse();
		p.increaseMovement(3);
		a.increaseMovement(3);
		b.increaseMovement(3);
		p.setHeartburn();
		a.setHeartburn();
		b.setHeartburn();
		p.refine(1);
		a.refine(1);
		b.refine(1);
		p.resetUlt();
		System.out.println("\"Time to break this and them down!\"");
		System.out.println();
	}
	
	public static void GrenadineAttack(Player p, Player a) {
		p.attack(a);
		a.setBlastpack();
		System.out.println(a.getSkin() + " has been blastpacked.");
		System.out.println();
	}
	
	public static void GrenadineAbility(Player p) {
		p.setJumpDamage(true);
		p.addJumps(1);
		p.setCooldown(2);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void GrenadineUltimate(Player p, Player a, Player b, Player c, Location l) {
		if(!p.inReach(l, 15)) {
			System.out.println("Can't send the explosive that far!");
			System.out.println();
			return;
		}
		Utility Explosive = new Utility("Explosive", l, p, p, p, a, b, c);
		utility.add(Explosive);
		p.resetUlt();
		System.out.println("\"Now I am become Grenade-Nadine, the destroyer of worlds!\"");
		System.out.println();
	}
	
	public static void PatitekAttack(Player p, Player a) {
		p.attack(a);
		p.protect(0.05,1);
		System.out.println();
	}
	
	public static void PatitekAbility(Player p, Player a, Player b) {
		p.fortify(1);
		a.fortify(1);
		b.fortify(1);
		p.setSights(2);
		p.setCooldown(4);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void PatitekUltimate(Player p, Player a, Player b) {
		a.giveTacCharge();
		b.giveTacCharge();
		p.reflect(1);
		p.protect(0.5,1);
		p.resetUlt();
		System.out.println("\"No falling down on my watch!\"");
		System.out.println();
	}
	
	public static void CrystalAttack(Player p, Player a) {
		if(p.drillDashed()) {
			p.returnloc();
			p.setDrill(false);
			System.out.println("Drilled back!");
			System.out.println();
			p.setAttacked();
			return;
		}
		p.attack(a);
		p.resetAttack();
		p.setReturnloc(new Location(p.getLoc().getX(), p.getLoc().getY()));
		p.setDrill(true);
		p.getLoc().set(a.getLoc().getX(), a.getLoc().getY());
		System.out.println("Drilled over to " + a.getSkin());
		System.out.println();
	}
	
	public static void CrystalAbility(Player p, Player a, Player b, Player c, Player d, Player e) {
		Location l = SetCursor(p, a, b, c, d, e, 4);
		if(!p.inRange(l)) {
			System.out.println("Can't plant the Gemstone that far!");
			System.out.println();
			return;
		}
		Utility Gem = new Utility("Gemstone", l, p, d, e, a, b, c);
		utility.add(Gem);
		System.out.println(p.voiceline());
		p.setCooldown(4);
		System.out.println();
	}
	
	public static void CrystalUltimate(Player p, Player a, Player b, Player c, Location l) {
		if(!p.inRange(l)) {
			System.out.println("Can't plant the create the Prismatic Shatter that far!");
			System.out.println();
			return;
		}
		GameSim.b.trenchDrill(l);
		if (a.inRange(l, 8)) {
			a.takeDamage(400);
			p.addDamage(400);
		}
		if (b.inRange(l, 8)) {
			b.takeDamage(400);
			p.addDamage(400);
		}
		if (c.inRange(l, 8)) {
			c.takeDamage(400);
			p.addDamage(400);
		}
		p.resetUlt();
		System.out.println("\"Time to make history!\"");
		System.out.println();
	}
	
	public static void VelvetAttack(Player p, Player a) {
		p.attack(a);
		int randomSuit = 0;
		int randomRank = 0;
		for(int i = 0; i < 10; i++) {
			randomSuit = (int) (Math.random() * 4) + 1;
			randomRank = (int) (Math.random() * (13 - 1 + 1)) + 1;
			Card c = new Card(randomRank, randomSuit);
			System.out.println("Fired a " + c + " card.");
		}
		switch (randomSuit) {
			case 1:
				p.heal(0.1);
				p.addHealing(p.getMaxHP() * 0.1);
				System.out.println("Last card fired was hearts!");
				break;
			case 2:
				p.setShield();
				System.out.println("Last card fired was spades!");
				break;
			case 3:
				a.knockbacked(p.getLoc());
				System.out.println("Last card fired was clubs!");
				break;
			case 4:
				a.takeDamage(p.getDamage() * 0.75);
				p.addDamage(p.getDamage() * 0.75);
				System.out.println("Last card fired was diamonds!");
				break;
		}
		System.out.println();
	}
	
	public static void VelvetAbility(Player p, Player a, Player b, Player c) {
		int[] rouletteNumbers = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36};
		String[] rouletteColors = {"red", "black","red", "black","red", "black","red", "black","red", "black","black","red","black","red","black","red","black","red","red","black","red","black","red","black","red","black","red","black","black","red","black","red","black","red","black","red"};
		boolean color = false;
		boolean num = false;
		boolean size = false;
		boolean colorWin = false;
		boolean numWin = false;
		boolean sizeWin = false;
		int betsLost = 0;
		int totalBets = 0;
		Scanner input = new Scanner(System.in);
		if (!p.inRange(a) && !p.inRange(b) && !p.inRange(c)) {
			System.out.println("Nobody in range to bet against!");
			System.out.println();
			return;
		}
		System.out.println(p.voiceline());
		System.out.println();
		System.out.print("Do you want to bet on black or red: ");
		String aRes = input.next();
		System.out.print("Do you want to bet on even or odd: ");
		String aRes2 = input.next();
		System.out.print("Do you want to bet on high or low: ");
		String aRes3 = input.next();
		System.out.print("How much do you want to bet: ");
		String aRes4 = input.next();
		int bet = Integer.valueOf(aRes4);
		if(bet == 0) {
			System.out.println("Feeling safe today? That's ok...");
			System.out.println();
			return;
		}
		if(!aRes.equals("n")) {
			color = true;
			totalBets++;
		}
		if(!aRes2.equals("n")) {
			num = true;
			totalBets++;
		}
		if(!aRes3.equals("n")) {
			size = true;
			totalBets++;
		}
		int randomRoulette = (int) (Math.random() * (35 - 0 + 0)) + 0;
		System.out.println("The ball landed on " + rouletteNumbers[randomRoulette] + " " + rouletteColors[randomRoulette] + ".");
		if(aRes.equals("b")) {
			if(rouletteColors[randomRoulette].equals("black")) {
				colorWin = true;
			}
		}else if(aRes.equals("r")) {
			if(rouletteColors[randomRoulette].equals("red")) {
				colorWin = true;
			}
		}
		if(aRes2.equals("e")) {
			if(rouletteNumbers[randomRoulette] % 2 == 0) {
				numWin = true;
			}
		}else if(aRes2.equals("o")) {
			if(rouletteNumbers[randomRoulette] % 2 != 0) {
				numWin = true;
			}
		}
		if(aRes3.equals("l")) {
			if(rouletteNumbers[randomRoulette] < 19) {
				sizeWin = true;
			}
		}else if(aRes3.equals("h")) {
			if(rouletteNumbers[randomRoulette] > 18) {
				sizeWin = true;
			}
		}
		if(color && !colorWin) {
			betsLost++;
		}
		if(num && !numWin) {
			betsLost++;
		}
		if(size && !sizeWin) {
			betsLost++;
		}
		if(betsLost == totalBets) {
			System.out.println("Uh oh, you lost it all!");
			for(int i = 0; i < totalBets; i++) {
				p.takeDamage(bet * 0.25);
			}
			p.setCooldown(2);
			System.out.println();
			return;
		}
		if(colorWin && color) {
			if(p.inRange(a)) {
				a.takeDamage(bet * 0.25);
				p.addDamage(bet * 0.25);
			}
			if(p.inRange(b)) {
				b.takeDamage(bet * 0.25);
				p.addDamage(bet * 0.25);
			}
			if(p.inRange(c)) {
				c.takeDamage(bet * 0.25);
				p.addDamage(bet * 0.25);
			}
		}
		if(numWin && num) {
			if(p.inRange(a)) {
				a.takeDamage(bet * 0.25);
				p.addDamage(bet * 0.25);
			}
			if(p.inRange(b)) {
				b.takeDamage(bet * 0.25);
				p.addDamage(bet * 0.25);
			}
			if(p.inRange(c)) {
				c.takeDamage(bet * 0.25);
				p.addDamage(bet * 0.25);
			}
		}
		if(sizeWin && size) {
			if(p.inRange(a)) {
				a.takeDamage(bet * 0.25);
				p.addDamage(bet * 0.25);
			}
			if(p.inRange(b)) {
				b.takeDamage(bet * 0.25);
				p.addDamage(bet * 0.25);
			}
			if(p.inRange(c)) {
				c.takeDamage(bet * 0.25);
				p.addDamage(bet * 0.25);
			}
		}
		p.setCooldown(2);
		System.out.println();
	}
	
	public static void VelvetUltimate(Player p, Player a, Player b, Player c) {
		try {
			Scanner input = new Scanner(System.in);
			System.out.println("1: " + a.getName() +a.showHP() +  a.getHealth() + "/" + a.getMaxHP());
			System.out.println("2: " + b.getName() +b.showHP() +  b.getHealth() + "/" + b.getMaxHP());
			System.out.println("3: " + c.getName() +c.showHP() +  c.getHealth() + "/" + c.getMaxHP());
			System.out.print("Who do you want to faceoff in the Poker Showdown: ");
			String targetResponse = input.next();
			System.out.println();
			System.out.println("\"Don’t get mad that you'll lose, get mad because you won't win!\"");
			System.out.println();
			if(targetResponse.equals("1")) {
				Roulette r = new Roulette(p, a);
				r.startGame();
				p.resetUlt();
			}
			if(targetResponse.equals("2")) {
				Roulette r = new Roulette(p, b);
				r.startGame();
				p.resetUlt();
			}
			if(targetResponse.equals("3")) {
				Roulette r = new Roulette(p, c);
				r.startGame();
				p.resetUlt();
			}
		}catch(Exception e) {
			return;
		}
	}
	
	public static void DriftAttack(Player p, Player a) {
		p.attack(a);
		if(!p.attackedOnce()) {
			p.resetAttack();
			p.setAttackOnce(true);
		}
		double rand = Math.random();
		double rand2 = Math.random();
		if(rand2 <= 0.1) {
			a.ignite(1);
		}
		if(rand <= 0.3) {
			a.knockbacked(p.getLoc());
		}
		System.out.println();
	}
	
	public static void DriftAbility(Player p) {
		p.increaseMovement(3);
		if(!p.ultActive()) {
			p.setBumps(3);
		}
		p.setCooldown(3);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void DriftUltimate(Player p) {
		p.setBumps(3);
		p.setMotorcycle();
		p.setUlt();
		System.out.println("\"They'll never catch up. Not when I shift into MAXIMUM OVERDRIVE!\"");
		System.out.println();
	}
	
	public static void SnowfallAttack(Player p, Player a) {
		p.attack(a);
		a.knockbacked(p.getLoc());
		double rand = Math.random();
		if(rand <= 0.15) {
			a.freeze(1);
			a.weary(1);
		}
		System.out.println();
	}
	
	public static void SnowfallAbility(Player p) {
		p.permaFrost();
		p.setFrost(true);
		p.setCooldown(4);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void SnowfallUltimate(Player p, Player a, Player b, Player c) {
		if(a.inRange(p, 6)) {
			a.vulnerable(0.15, 2);
			a.takeDamage(275);
		}
		if(b.inRange(p, 6)) {
			b.vulnerable(0.15, 2);
			b.takeDamage(275);
		}
		if(c.inRange(p, 6)) {
			c.vulnerable(0.15, 2);
			c.takeDamage(275);
		}
		a.takeDamage(275);
		b.takeDamage(275);
		c.takeDamage(275);
		a.freeze(1);
		a.weary(2);
		b.freeze(1);
		b.weary(2);
		c.freeze(1);
		c.weary(2);
		p.resetUlt();
		if (p.getFrost()) {
			p.permaFrost();
		}
		System.out.println("\"Freeze! Everybody clap them now!\"");
		System.out.println();
		
	}
	
	public static void ShutterAttack(Player p, Player a, Player b, Player c) {
		p.mend(0.5,1);
		b.mend(0.5,1);
		c.mend(0.5,1);
		p.attack(a);
		p.setChance(0.05);
		b.setChance(0.05);
		c.setChance(0.05);
		System.out.println();
	}
	
	public static void ShutterAbility(Player p) {
		reduceSpawn = true;
		reduceCover = true;
		p.setCooldown(3);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void ShutterUltimate(Player p, Player a, Player b, Player c) {
		a.paralyze(1);
		a.daze(1);
		a.blind(0.8, 1);
		b.paralyze(1);
		b.daze(1);
		b.blind(0.8, 1);
		c.paralyze(1);
		c.daze(1);
		c.blind(0.8, 1);
		p.resetUlt();
		System.out.println("\"Smile!\"");
		System.out.println();
	}
	
	public static void MagnetAttack(Player p, Player a, Player b, Player c) {
		boolean bAlive = false;
		boolean cAlive = false;
		a.takeDamage(25);
		p.addDamage(25);
		p.attack(a);
		double rand = Math.random();
		if(rand <= 0.1) {
			a.daze(1);
		}
		if(a.inRange(b, 5)) {
			b.takeDamage(75);
			p.addDamage(75);
			bAlive= true;
		}
		if(a.inRange(c, 5)) {
			c.takeDamage(75);
			p.addDamage(75);
			cAlive = true;
		}
		if(bAlive && cAlive) {
			p.setSights(1);
		}
		System.out.println();
	}
	
	public static void MagnetAbility(Player p, Player a, Player b, Player c, Player d, Player e) {
		Utility Field = new Utility("Field", new Location(p.getLoc().getX(), p.getLoc().getY()), p, d, e, a, b, c);
		utility.add(Field);
		System.out.println(p.voiceline());
		p.setCooldown(3);
		System.out.println();
	}
	
	public static void MagnetUltimate(Player p, Player a, Player b, Player c) {
		p.setUlt();
		Utility Fulmination = new Utility("Fulmination", p.getLoc(), p, p, p, a, b, c);
		utility.add(Fulmination);
		System.out.println("\"This is a shocking fulmination!\"");
		System.out.println();
	}
	
	public static void JingAttack(Player p, Player a) {
		p.attack(a);
		a.setJing(true);
		System.out.println();
	}
	
	public static void JingAbility(Player p, Player a, Player b) {
		p.heal(0.2);
		a.heal(0.2);
		b.heal(0.2);
		p.addHealing(p.getMaxHP() * 0.2);
		p.addHealing(a.getMaxHP() * 0.2);
		p.addHealing(b.getMaxHP() * 0.2);
		p.setChance(0.2);
		a.setChance(0.2);
		b.setChance(0.2);
		p.setDodge(0.2);
		a.setDodge(0.2);
		b.setDodge(0.2);
		p.setCooldown(3);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void JingUltimate(Player p, Player a, Player b, Player c, Player d, Player e) {
		p.setUlt();
		p.setChance(0.25);
		d.setChance(0.25);
		e.setChance(0.25);
		p.setDodge(0.5);
		d.setDodge(0.5);
		e.setDodge(0.5);
		Utility Dragon = new Utility("Dragon", p.getLoc(), p, d, e, a, b, c);
		utility.add(Dragon);
		System.out.println("\"I am the Dragon Warrior!\"");
		System.out.println();
	}
	
	public static void FoldenAttack(Player p, Player a) {
		p.attack(a);
		for(int i = 0; i < 2; i++) {
			int randomX = (int)(Math.random() * ((a.getLoc().getX() + 3) - (a.getLoc().getX() - 3) + 1)) + (a.getLoc().getX() - 3);
			int randomY = (int)(Math.random() * ((a.getLoc().getY() + 3) - (a.getLoc().getY() - 3) + 1)) + (a.getLoc().getY() - 3);
			Location l = new Location(randomX, randomY);
			Utility Sensor = new Utility("Star", l, p, p, p, a, a, a);
			utility.add(Sensor);
		}
		System.out.println();
	}
	
	public static void FoldenAbility(Player p, Player a, Player b, Player c, Player d, Player e) {
		p.addHealing(p.getMaxHP() * 0.2);
		p.addHealing(d.getMaxHP() * 0.2);
		p.addHealing(e.getMaxHP() * 0.2);
		p.protect(0.2,2);
		p.heal(0.1,2);
		d.protect(0.2,2);
		d.heal(0.1,2);
		e.protect(0.2,2);
		e.heal(0.1,2);
		for(int i = 0; i < 15; i++) {
			int randomX = (int)(Math.random() * (28 - 13 + 1)) + 13;
			int randomY = (int)(Math.random() * (28 - 13 + 1)) + 13;
			Location l = new Location(randomX, randomY);
			Utility Crane = new Utility("Crane", l, p, d, e, a, b, c);
			utility.add(Crane);
		}
		p.setCooldown(4);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void FoldenUltimate(Player p, Player a, Player b, Player c, Player d, Player e) {
		Scanner input = new Scanner(System.in);
		String dragon = "Not Used.";
		String butterfly = "Not Used.";
		String tank = "Not Used.";
		if(p.usedDragon()) {
			dragon = "Used.";
		}
		if(p.usedButterfly()) {
			butterfly = "Used.";
		}
		if(p.usedTank()) {
			tank = "Used.";
		}
		System.out.println("1: Origami Dragons. " + dragon);
		System.out.println("2: Origami Butterflies. " + butterfly);
		System.out.println("3: Origami Tank. " + tank);
		System.out.print("Which origami creations would you like to craft: ");
		String targetResponse = input.next();
		if(targetResponse.equals("1")) {
			if(p.usedDragon()) {
				System.out.println("Already used the Origami Dragons!");
				System.out.println();
				return;
			}
			a.takeDamage(300);
			p.addDamage(300);
			b.takeDamage(300);
			p.addDamage(300);
			c.takeDamage(300);
			p.addDamage(300);
			p.addDamage(175);
			p.addDamage(175);
			p.addDamage(175);
			a.ignite(1);
			b.ignite(1);
			c.ignite(1);
			p.useDragon();
			System.out.println();
			System.out.println("\"Release the Origami Dragons!\"");
			System.out.println();
		}
		if(targetResponse.equals("2")) {
			if(p.usedButterfly()) {
				System.out.println("Already used the Origami Butterflies!");
				System.out.println();
				return;
			}
			p.cleanse();
			p.increaseMovement(3);
			d.cleanse();
			d.increaseMovement(3);
			e.cleanse();
			e.increaseMovement(3);
			p.mend(0.5,2);
			d.mend(0.5,2);
			e.mend(0.5,2);
			p.useButterfly();
			System.out.println();
			System.out.println("\"Release the Origami Butterflies!\"");
			System.out.println();
		}
		if(targetResponse.equals("3")) {
			if(p.usedTank()) {
				System.out.println("Already used the Origami Tank!");
				System.out.println();
				return;
			}
			a.knockbacked(p.getLoc());
			b.knockbacked(p.getLoc());
			c.knockbacked(p.getLoc());
			a.daze(1);
			b.daze(1);
			c.daze(1);
			p.useTank();
			System.out.println();
			System.out.println("\"Release the Origami Tank!\"");
			System.out.println();
		}
		if (p.usedDragon() && p.usedButterfly() && p.usedTank()) {
			p.resetUlt();
		}
	}
	
	public static void BladeeAttack(Player p, Player a, Player b, Player c) {
		if(!p.inRange(a) && !p.inRange(b) && !p.inRange(c)) {
			System.out.println("No targets in range!");
			System.out.println();
			return;
		}
		if(p.inRange(a)){
			p.attack(a);
		}
		if(p.inRange(b)) {
			p.attack(b);
		}
		if(p.inRange(c)) {
			p.attack(c);
		}
		p.addFuel(250);
		System.out.println();
	}
	
	public static void BladeeAbility(Player p, Player a, Player b, Player c, Player d, Player e) {
		if(p.getFuel() < 350) {
			System.out.println("Not enough fuel for a flight!");
			System.out.println();
			return;
		}
		Scanner input = new Scanner(System.in);
		System.out.println("1: RC Chopper (75 Fuel Per Tile)");
		System.out.println("2: Malfunction Mantis (50 Fuel Per Tile)");
		System.out.print("Which flying gadget do you want to use: ");
		String attackResponse = input.next();
		if(attackResponse.equals("1")) {
			Location l = SetCursor(p, a, b, c, d, e, 5);
			if(p.getLoc().distance(l) < 7) {
				System.out.println("Gadgets must be sent at least 7 tiles away!");
				System.out.println();
				return;
			}
			if(p.getFuel() < p.getLoc().distance(l) * 75) {
				System.out.println("Not enough fuel to reach there!");
				System.out.println();
				return;
			}
			if(a.inRange(l, 5)) {
				a.takeDamage(p.getDamage() * 2.5);
				p.addDamage(p.getDamage() * 2.5);
			}
			if(b.inRange(l, 5)) {
				b.takeDamage(p.getDamage() * 2.5);
				p.addDamage(p.getDamage() * 2.5);
			}
			if(c.inRange(l, 5)) {
				c.takeDamage(p.getDamage() * 2.5);
				p.addDamage(p.getDamage() * 2.5);
			}
			System.out.println(p.voiceline());
			p.useFuel(p.getLoc().distance(l) * 75);
			System.out.println();
		}
		if(attackResponse.equals("2")) {
			Location l = SetCursor(p, a, b, c, d, e, 3);
			if(p.getLoc().distance(l) < 7) {
				System.out.println("Gadgets must be sent at least 7 tiles away!");
				System.out.println();
				return;
			}
			if(p.getFuel() < p.getLoc().distance(l) * 50) {
				System.out.println("Not enough fuel to reach there!");
				System.out.println();
				return;
			}
			if(a.inRange(l, 3)) {
				a.resetCover();
			}
			if(b.inRange(l, 3)) {
				b.resetCover();
			}
			if(c.inRange(l, 3)) {
				c.resetCover();
			}
			for(int j = 0; j < GameSim.utility.size(); j++) {
				if(GameSim.utility.get(j).getName().equals("Sensor") && (utility.get(j).owner(a) || utility.get(j).owner(b) || utility.get(j).owner(c)) && utility.get(j).getLoc().inRange(l, 3)) {
					GameSim.utility.remove(j);
					System.out.println("Enemy sound sensor destroyed.");
					j--;
				}
			}
			for(int j = 0; j < GameSim.utility.size(); j++) {
				if(GameSim.utility.get(j).getName().equals("Sphere") && (utility.get(j).owner(a) || utility.get(j).owner(b) || utility.get(j).owner(c)) && utility.get(j).getLoc().inRange(l, 3)) {
					GameSim.utility.get(j).takeHit();
					System.out.println("Enemy Symphony Sphere has " + GameSim.utility.get(j).getHealth() + " more health left.");
					if (GameSim.utility.get(j).getHealth() <= 0) {
						GameSim.utility.remove(j);
						System.out.println("Enemy Symphony Sphere destroyed.");
					}
				}
			}
			
			for(int j = 0; j < GameSim.utility.size(); j++) {
				if(GameSim.utility.get(j).getName().equals("Gemstone") && (utility.get(j).owner(a) || utility.get(j).owner(b) || utility.get(j).owner(c)) && utility.get(j).getLoc().inRange(l, 3)) {
					GameSim.utility.get(j).takeHit();
					System.out.println("Enemy Gemstone Lode has " + GameSim.utility.get(j).getHealth() + " more health left.");
					if (GameSim.utility.get(j).getHealth() <= 0) {
						GameSim.utility.remove(j);
						System.out.println("Enemy Gemstone Lode destroyed.");
					}
				}
			}
			for(int j = 0; j < GameSim.utility.size(); j++) {
				if(GameSim.utility.get(j).getName().equals("Field") && (utility.get(j).owner(a) || utility.get(j).owner(b) || utility.get(j).owner(c)) && utility.get(j).getLoc().inRange(l, 3)) {
					GameSim.utility.remove(j);
					System.out.println("Enemy Electromagnetic field destroyed.");
					j--;
				}
			}
			for(int j = 0; j < GameSim.utility.size(); j++) {
				if(GameSim.utility.get(j).getName().equals("Umbrella") && (utility.get(j).owner(a) || utility.get(j).owner(b) || utility.get(j).owner(c)) && utility.get(j).getLoc().inRange(l, 3)) {
					GameSim.utility.remove(j);
					System.out.println("Enemy Umbrella destroyed.");
					j--;
				}
			}
			for(int j = 0; j < GameSim.utility.size(); j++) {
				if(GameSim.utility.get(j).getName().equals("Pylon") && (utility.get(j).owner(a) || utility.get(j).owner(b) || utility.get(j).owner(c)) && utility.get(j).getLoc().inRange(l, 3)) {
					GameSim.utility.remove(j);
					System.out.println("Enemy Honey Pylon destroyed.");
					j--;
				}
			}
			for(int j = 0; j < GameSim.utility.size(); j++) {
				if(GameSim.utility.get(j).getName().equals("Turret") && (utility.get(j).owner(a) || utility.get(j).owner(b) || utility.get(j).owner(c)) && utility.get(j).getLoc().inRange(l, 3)) {
					GameSim.utility.get(j).takeHit();
					System.out.println("Enemy Turret has " + GameSim.utility.get(j).getHealth() + " more health left.");
					if (GameSim.utility.get(j).getHealth() <= 0) {
						GameSim.utility.get(j).deactivate();
						System.out.println("Enemy Turret deactivated.");
					}
				}
			}
			for(int j = 0; j < GameSim.utility.size(); j++) {
				if(GameSim.utility.get(j).getName().equals("Pawn") && (utility.get(j).owner(a) || utility.get(j).owner(b) || utility.get(j).owner(c)) && utility.get(j).getLoc().inRange(l, 3)) {
					GameSim.utility.remove(j);
					System.out.println("Enemy Pawn destroyed.");
					j--;
				}
			}
			for(int j = 0; j < GameSim.utility.size(); j++) {
				if(GameSim.utility.get(j).getName().equals("Mural") && (utility.get(j).owner(a) || utility.get(j).owner(b) || utility.get(j).owner(c)) && utility.get(j).getLoc().inRange(l, 3)) {
					GameSim.utility.remove(j);
					System.out.println("Enemy Mural destroyed.");
					j--;
				}
			}
			for(int j = 0; j < GameSim.utility.size(); j++) {
				if(GameSim.utility.get(j).getName().equals("Sock") && (utility.get(j).owner(a) || utility.get(j).owner(b) || utility.get(j).owner(c)) && utility.get(j).getLoc().inRange(l, 3)) {
					GameSim.utility.remove(j);
					System.out.println("Enemy Sock Monkey destroyed.");
					j--;
				}
			}
			for(int j = 0; j < GameSim.utility.size(); j++) {
				if(GameSim.utility.get(j).getName().equals("Support") && (utility.get(j).owner(a) || utility.get(j).owner(b) || utility.get(j).owner(c)) && utility.get(j).getLoc().inRange(l, 3)) {
					GameSim.utility.remove(j);
					System.out.println("Enemy Sockyman Support destroyed.");
					j--;
				}
			}
			for(int j = 0; j < GameSim.utility.size(); j++) {
				if(GameSim.utility.get(j).getName().equals("Matrix") && (utility.get(j).owner(a) || utility.get(j).owner(b) || utility.get(j).owner(c)) && utility.get(j).getLoc().inRange(l, 3)) {
					GameSim.utility.remove(j);
					System.out.println("Enemy Immortality Matrix destroyed.");
					j--;
				}
			}
			for(int j = 0; j < GameSim.utility.size(); j++) {
				if(GameSim.utility.get(j).getName().equals("Dynamite") && (utility.get(j).owner(a) || utility.get(j).owner(b) || utility.get(j).owner(c)) && utility.get(j).getLoc().inRange(l, 3)) {
					GameSim.utility.get(j).takeHit();
					System.out.println("Enemy Dynamite has " + GameSim.utility.get(j).getHealth() + " more health left.");
					if (GameSim.utility.get(j).getHealth() <= 0) {
						GameSim.utility.remove(j);
						System.out.println("Enemy Dynamite destroyed.");
					}
				}
			}
			for(int j = 0; j < GameSim.utility.size(); j++) {
				if(GameSim.utility.get(j).getName().equals("Peri") && (utility.get(j).owner(a) || utility.get(j).owner(b) || utility.get(j).owner(c)) && utility.get(j).getLoc().inRange(l, 3)) {
					GameSim.utility.get(j).takeHit();
					System.out.println("Enemy Peri has " + GameSim.utility.get(j).getHealth() + " more health left.");
					if (GameSim.utility.get(j).getHealth() <= 0) {
						GameSim.utility.remove(j);
						System.out.println("Enemy Peri destroyed.");
					}
				}
			}
			for(int j = 0; j < GameSim.utility.size(); j++) {
				if(GameSim.utility.get(j).getName().equals("Finley") && (utility.get(j).owner(a) || utility.get(j).owner(b) || utility.get(j).owner(c)) && p.getLoc().eqLoc(GameSim.utility.get(j).getLoc()) && (p.canDash() || p.isHoverDashing())) {
					GameSim.utility.get(j).takeHit();
					System.out.println("Enemy Finley Clone has " + GameSim.utility.get(j).getHealth() + " more health left.");
					if (GameSim.utility.get(j).getHealth() <= 0) {
						GameSim.utility.remove(j);
						System.out.println("Enemy Finley Clone destroyed.");
					}
				}
			}
			p.useFuel(p.getLoc().distance(l) * 50);
			System.out.println(p.voiceline());
			System.out.println();
		}
	}
	
	public static void BladeeUltimate(Player p, Player a, Player b, Player c, Player d, Player e) {
		if(p.getFuel() < 700) {
			System.out.println("Not enough fuel for a flight!");
			System.out.println();
			return;
		}
		Location l = SetCursor(p, a, b, c, d, e, 10);
		if(p.getLoc().distance(l) < 7) {
			System.out.println("Gadgets must be sent at least 7 tiles away!");
			System.out.println();
			return;
		}
		if(p.getFuel() < p.getLoc().distance(l) * 100) {
			System.out.println("Not enough fuel to reach there!");
			System.out.println();
			return;
		}
		if(a.inRange(l, 10)) {
			a.takeDamage(750);
			p.addDamage(750);
		}
		if(b.inRange(l, 10)) {
			b.takeDamage(750);
			p.addDamage(750);
		}
		if(c.inRange(l, 10)) {
			c.takeDamage(750);
			p.addDamage(750);
		}
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).getName().equals("Sensor") && (utility.get(j).owner(a) || utility.get(j).owner(b) || utility.get(j).owner(c)) && utility.get(j).getLoc().inRange(l, 10)) {
				GameSim.utility.remove(j);
				System.out.println("Enemy sound sensor destroyed.");
				j--;
			}
		}
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).getName().equals("Sphere") && (utility.get(j).owner(a) || utility.get(j).owner(b) || utility.get(j).owner(c)) && utility.get(j).getLoc().inRange(l, 10)) {
				System.out.println("Enemy Symphony Sphere has " + GameSim.utility.get(j).getHealth() + " more health left.");
				GameSim.utility.remove(j);
				System.out.println("Enemy Symphony Sphere destroyed.");
			}
		}
			
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).getName().equals("Gemstone") && (utility.get(j).owner(a) || utility.get(j).owner(b) || utility.get(j).owner(c)) && utility.get(j).getLoc().inRange(l, 10)) {
				System.out.println("Enemy Gemstone Lode has " + GameSim.utility.get(j).getHealth() + " more health left.");
				GameSim.utility.remove(j);
				System.out.println("Enemy Gemstone Lode destroyed.");
			}
		}
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).getName().equals("Field") && (utility.get(j).owner(a) || utility.get(j).owner(b) || utility.get(j).owner(c)) && utility.get(j).getLoc().inRange(l, 10)) {
				GameSim.utility.remove(j);
				System.out.println("Enemy Electromagnetic field destroyed.");
				j--;
			}
		}
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).getName().equals("Umbrella") && (utility.get(j).owner(a) || utility.get(j).owner(b) || utility.get(j).owner(c)) && utility.get(j).getLoc().inRange(l, 10)) {
				GameSim.utility.remove(j);
				System.out.println("Enemy Umbrella destroyed.");
				j--;
			}
		}
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).getName().equals("Pylon") && (utility.get(j).owner(a) || utility.get(j).owner(b) || utility.get(j).owner(c)) && utility.get(j).getLoc().inRange(l, 10)) {
				GameSim.utility.remove(j);
				System.out.println("Enemy Honey Pylon destroyed.");
				j--;
			}
		}
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).getName().equals("Turret") && (utility.get(j).owner(a) || utility.get(j).owner(b) || utility.get(j).owner(c)) && utility.get(j).getLoc().inRange(l, 19)) {
				GameSim.utility.get(j).deactivate();
				System.out.println("Enemy Turret deactivated.");
			}
		}
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).getName().equals("Pawn") && (utility.get(j).owner(a) || utility.get(j).owner(b) || utility.get(j).owner(c)) && utility.get(j).getLoc().inRange(l, 10)) {
				GameSim.utility.remove(j);
				System.out.println("Enemy Pawn destroyed.");
				j--;
			}
		}
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).getName().equals("Mural") && (utility.get(j).owner(a) || utility.get(j).owner(b) || utility.get(j).owner(c)) && utility.get(j).getLoc().inRange(l, 10)) {
				GameSim.utility.remove(j);
				System.out.println("Enemy Mural destroyed.");
				j--;
			}
		}
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).getName().equals("Sock") && (utility.get(j).owner(a) || utility.get(j).owner(b) || utility.get(j).owner(c)) && utility.get(j).getLoc().inRange(l, 10)) {
				GameSim.utility.remove(j);
				System.out.println("Enemy Sock Monkey destroyed.");
				j--;
			}
		}
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).getName().equals("Support") && (utility.get(j).owner(a) || utility.get(j).owner(b) || utility.get(j).owner(c)) && utility.get(j).getLoc().inRange(l, 10)) {
				GameSim.utility.remove(j);
				System.out.println("Enemy Sockyman Support destroyed.");
				j--;
			}
		}
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).getName().equals("Matrix") && (utility.get(j).owner(a) || utility.get(j).owner(b) || utility.get(j).owner(c)) && utility.get(j).getLoc().inRange(l, 10)) {
				GameSim.utility.remove(j);
				System.out.println("Enemy Immortality Matrix destroyed.");
				j--;
			}
		}
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).getName().equals("Dynamite") && (utility.get(j).owner(a) || utility.get(j).owner(b) || utility.get(j).owner(c)) && utility.get(j).getLoc().inRange(l, 10)) {
				GameSim.utility.remove(j);
				System.out.println("Enemy Dynamite destroyed.");
				j--;
			}
		}
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).getName().equals("Peri") && (utility.get(j).owner(a) || utility.get(j).owner(b) || utility.get(j).owner(c)) && utility.get(j).getLoc().inRange(l, 10)) {
				GameSim.utility.remove(j);
				System.out.println("Enemy Peri destroyed.");
				j--;
			}
		}
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).getName().equals("Finley") && (utility.get(j).owner(a) || utility.get(j).owner(b) || utility.get(j).owner(c)) && p.getLoc().eqLoc(GameSim.utility.get(j).getLoc()) && (p.canDash() || p.isHoverDashing())) {
				GameSim.utility.remove(j);
				System.out.println("Enemy Finley Clone destroyed.");
				j--;
			}
		}
		p.resetUlt();
		p.useFuel(p.getLoc().distance(l) * 100);
		System.out.println("\"Open up the sky!\"");
		System.out.println();
	}
	
	public static void MargoAttack(Player p, Player a, Player b, Player c) {
		p.attack(a);
		Utility Flame = new Utility("Flame", new Location(a.getLoc().getX(), a.getLoc().getY()), p, p, p, a, b, c);
		utility.add(Flame);
		System.out.println();
	}
	
	public static void MargoAbility(Player p, Player a, Player b) {
		ArrayList<Effect> e = new ArrayList<Effect>();
		ArrayList<Effect> e2 = new ArrayList<Effect>();
		ArrayList<Effect> e3 = new ArrayList<Effect>();
		int random = (int) (Math.random() * (4 - 1 + 1)) + 1;
		int move = 2;
		double heal = 0.1;
		double power = 0.1;
		double protect = 0.15;
		switch (random) {
			case 1:
				move = move * 2;
				System.out.println("The drinks doubled movement!");
				break;
			case 2:
				heal = heal * 2;
				System.out.println("The drinks doubled healing!");
				break;
			case 3:
				power = power * 2;
				System.out.println("The drinks doubled power!");
				break;
			case 4:
				protect = protect * 2;
				System.out.println("The drinks doubled protection!");
				break;
		}
        p.heal(heal);
		a.heal(heal);
		b.heal(heal);
		p.addHealing(p.getMaxHP() * heal);
		p.addHealing(a.getMaxHP() * heal);
		p.addHealing(b.getMaxHP() * heal);
		p.increaseMovement(move);
		a.increaseMovement(move);
		b.increaseMovement(move);
		p.power(power, 1);
		p.protect(protect, 1);
		a.power(power, 1);
		a.protect(protect, 1);
		b.power(power, 1);
		b.protect(protect, 1);
        p.setCooldown(4);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void MargoUltimate(Player p, Player a, Player b, Player c) {
		int drinks = 6;
		ArrayList<Effect> e = new ArrayList<Effect>();
		ArrayList<Effect> e2 = new ArrayList<Effect>();
		ArrayList<Effect> e3 = new ArrayList<Effect>();
		double weak = 0;
		double poison = 0;
		double vulnerable = 0;
		double blind = 0;
		boolean daze = false;
		boolean paralyze = false;
		Scanner input = new Scanner(System.in);
		System.out.println("1: Helium Cube (10% Weaken)");
		System.out.println("2: Mercury Cube (10% Poison)");
		System.out.println("3: Fluorine Cube (5% Vulnerability)");
		System.out.println("4: Lead Cube (15% Blind)");
		System.out.println("5: 2 Chlorine Cubes (1 Turn Paralyze. Can only be added once.)");
		System.out.println("6: 2 Carbon Cubes (1 Turn Daze. Can only be added once.)");
		System.out.print("What do you want to add in this Concoction: ");
		while (drinks > 0) {
			String targetResponse = input.next();
			if(targetResponse.equals("1")) {
				weak = weak + 0.1;
				drinks--;
				System.out.println("Helium cube added. " + drinks + " more cube(s) can be added.");
			}
			if(targetResponse.equals("2")) {
				poison = poison + 0.1;
				drinks--;
				System.out.println("Mercury cube added. " + drinks + " more cube(s) can be added.");
			}
			if(targetResponse.equals("3")) {
				vulnerable = vulnerable + 0.1;
				drinks--;
				System.out.println("Fluorine cube added. " + drinks + " more cube(s) can be added.");
			}
			if(targetResponse.equals("4")) {
				blind = blind + 0.15;
				drinks--;
				System.out.println("Lead cube added. " + drinks + " more cube(s) can be added.");
			}
			if(targetResponse.equals("5")) {
				if (!paralyze && drinks >= 2) {
					paralyze = true;
					drinks--;
					drinks--;
					System.out.println("2 Chlorine cubes added. " + drinks + " more cube(s) can be added.");
				}else {
					System.out.println("Not enough room for chlorine cubes, or already added chlorine cubes!");
				}
			}
			if(targetResponse.equals("6")) {
				if (!daze && drinks >= 2) {
					daze = true;
					drinks--;
					drinks--;
					System.out.println("2 Carbon cubes added. " + drinks + " more cube(s) can be added.");
				}else {
					System.out.println("Not enough room for carbon cubes, or already added carbon cubes!");
				}
			}
		}
		
		if (weak > 0) {
			a.weak(weak, 1);
			b.weak(weak, 1);
			c.weak(weak, 1);
		}
		if (poison > 0) {
			a.poison(poison, 1);
			b.poison(poison, 1);
			c.poison(poison, 1);
		}
		if (vulnerable > 0) {
			a.vulnerable(vulnerable, 1);
			b.vulnerable(vulnerable, 1);
			c.vulnerable(vulnerable, 1);
		}
		if (blind > 0) {
			a.blind(blind, 1);
			b.blind(blind, 1);
			c.blind(blind, 1);
		}
		if(paralyze) {
			a.paralyze(1);
			b.paralyze(1);
			c.paralyze(1);
		}
		if(daze) {
			a.daze(1);
			b.daze(1);
			c.daze(1);
		}
		p.resetUlt();
		System.out.println("\"These drinks are on the HOUSE!\"");
		System.out.println();
	}
	
	public static void IvyAttack(Player p, Player a, Player b, Player c, Player d, Player e) {
		Scanner input = new Scanner(System.in);
		String range = "No";
		if(p.inRange(a)) {
			range = "Yes";
		}
		String range2 = "No";
		if(p.inRange(b)) {
			range2 = "Yes";
		}
		String range3 = "No";
		if(p.inRange(c)) {
			range3 = "Yes";
		}
		String range4 = "No";
		if(p.inRange(d, 3)) {
			range4 = "Yes";
		}
		String range5 = "No";
		if(p.inRange(e, 3)) {
			range5 = "Yes";
		}
		System.out.println("1: Prescription Pill");
		System.out.println("2: Medic Pistol");
		System.out.print("Which part of your weapon would you like to use: ");
		String targetResponse = input.next();
		if(targetResponse.equals("2")) {
			if(!p.inRange(a) && !p.inRange(b) && !p.inRange(c)) {
				System.out.println();
				System.out.println("No targets in range!");
				System.out.println();
				return;
			}
			System.out.println();
			System.out.println("1: " + a.getSkin() +". Health: " +  a.getHealth() + "/" + a.getMaxHP() + ". In Range: " + range + ". Location: " + a.getLoc() + ". Cover: " + a.getCover());
			System.out.println("2: " + b.getSkin() +". Health: " +  b.getHealth() + "/" + b.getMaxHP() + ". In Range: " + range2 + ". Location: " + b.getLoc() + ". Cover: " + b.getCover());
			System.out.println("3: " + c.getSkin() +". Health: " +  c.getHealth() + "/" + c.getMaxHP() + ". In Range: " + range3 + ". Location: " + c.getLoc() + ". Cover: " + c.getCover());
			System.out.println("Who do you want to attack: ");
			String targetResponse2 = input.next();
			if(targetResponse2.equals("1")) {
				if(p.inRange(a)) {
					p.attack(a);
				}else {
					System.out.println();
					System.out.println("Target is out of range!");
					System.out.println();
				}
			}
			if(targetResponse2.equals("2")) {
				if(p.inRange(b)) {
					p.attack(b);
				}else {
					System.out.println();
					System.out.println("Target is out of range!");
					System.out.println();
				}
			}
			if(targetResponse2.equals("3")) {
				if(p.inRange(c)) {
					p.attack(c);
				}else {
					System.out.println();
					System.out.println("Target is out of range!");
					System.out.println();
				}
			}
		}
		if(targetResponse.equals("1")) {
			Boolean pill = false;
			System.out.println();
			System.out.println("1: Healing Pill" );
			System.out.println("2: Power Pill");
			System.out.print("Which pill do you want to use: ");
			String targetResponse2 = input.next();
			if(targetResponse2.equals("1")) {
				pill = true;
			}
			if(targetResponse2.equals("c")) {
				return;
			}
			System.out.println();
			System.out.println("1: " + d.getSkin() +". Health: " +  d.getHealth() + "/" + d.getMaxHP() + ". In Range: " + range4 + ". Location: " + d.getLoc() + ". Cover: " + d.getCover());
			System.out.println("2: " + e.getSkin() +". Health: " +  e.getHealth() + "/" + e.getMaxHP() + ". In Range: " + range5 + ". Location: " + e.getLoc() + ". Cover: " + e.getCover());
			System.out.println("Who do you want to give the pill to: ");
			String targetResponse3 = input.next();
			if(targetResponse3.equals("1")) {
				if(p.inRange(d, 3)) {
					if (pill) {
						d.increaseHP(400);
						p.addHealing(400);
						d.cleanse();
						if (p.getMedic()) {
							e.increaseHP(400);
							p.addHealing(400);
							e.cleanse();
						}
					}else {
						d.power(0.5, 1);
						if (p.getMedic()) {
							e.power(0.5, 1);
						}
					}
					p.setAttacked();
				}else {
					System.out.println();
					System.out.println("Target is out of range!");
					System.out.println();
				}
			}
			if(targetResponse3.equals("2")) {
				if(p.inRange(e, 3)) {
					if (pill) {
						e.increaseHP(400);
						p.addHealing(400);
						e.cleanse();
						if (p.getMedic()) {
							d.increaseHP(400);
							p.addHealing(400);
							d.cleanse();
						}
					}else {
						e.power(0.5, 1);
						if (p.getMedic()) {
							d.power(0.5, 1);
						}
					}
					p.setAttacked();
				}else {
					System.out.println();
					System.out.println("Target is out of range!");
					System.out.println();
				}
			}
		}
		System.out.println();
	}
	
	public static void IvyAbility(Player p, Player a, Player b) {
		Scanner input = new Scanner(System.in);
		System.out.println("1: " + a.getSkin() +a.showHP() +  a.getHealth() + "/" + a.getMaxHP() + ".");
		System.out.println("2: " + b.getSkin() +b.showHP() +  b.getHealth() + "/" + b.getMaxHP() + ".");
		System.out.println("Who do you want to combat run towards: ");
		String targetResponse = input.next();
		if(targetResponse.equals("1")) {
			if (p.inRange(a)) {
				p.getLoc().set(a.getLoc().getX(), a.getLoc().getY());
				if (!p.getMedic()) {
					p.setCooldown(1);
				}
			}else {
				System.out.println("That friend is too far!");
				System.out.println();
				return;
			}
		}
		if(targetResponse.equals("2")) {
			if (p.inRange(b)) {
				p.getLoc().set(b.getLoc().getX(), b.getLoc().getY());
				if (!p.getMedic()) {
					p.setCooldown(1);
				}
			}else {
				System.out.println("That friend is too far!");
				System.out.println();
				return;
			}
		}
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void IvyUltimate(Player p, Player a, Player b) {
		if (p.getMedic()) {
			if (p.getRes()) {
				if (p.inRange(a, 0) && !a.isAlive()) {
					a.reviveIvy();
					p.setRes(false);
				}
				if (p.inRange(b, 0) && !b.isAlive()) {
					b.reviveIvy();
					p.setRes(false);
				}
				System.out.println("\"No time for slacking guys!\"");
				System.out.println();
			}else {
				System.out.println("Can't revive anyone right now.");
				System.out.println();
			}
		}else {
			p.refine(100);
			p.setMedic();
			p.setRes(true);
			System.out.println("\"Here comes the Intensive Care Unit!\"");
			System.out.println();
		}
	}
	
	public static void PetraAttack(Player p, Player a, Player b, Player c) {
		if (a.getCover().equals("Partial")) {
			a.resetCover();
		}
		if(a.inRange(b, 4) && b.getCover().equals("Partial")) {
			b.resetCover();
		}
		if(a.inRange(c, 4) && c.getCover().equals("Partial")) {
			c.resetCover();
		}
		p.attack(a);
		if (a.getCover().equals("Full")) {
			a.resetCover();
		}
		p.attack(a);
		if(a.inRange(b, 4) && b.getCover().equals("Full")) {
			b.resetCover();
		}
		if(a.inRange(c, 4) && c.getCover().equals("Full")) {
			c.resetCover();
		}
		System.out.println();
	}
	
	public static void PetraAbility(Player p, Player a, Player b, Player c, Player d, Player e) {
		Scanner input = new Scanner(System.in);
		if(!p.inRange(a, 25) && !p.inRange(b, 25) && !p.inRange(c, 25)) {
			System.out.println("No targets in range!");
			System.out.println();
			return;
		}
		String range = "No";
		if(p.inRange(a, 25)) {
			range = "Yes";
		}
		String range2 = "No";
		if(p.inRange(b, 25)) {
			range2 = "Yes";
		}
		String range3 = "No";
		if(p.inRange(c, 25)) {
			range3 = "Yes";
		}
		System.out.println("1: " + a.getSkin() +". Health: " +  a.getHealth() + "/" + a.getMaxHP() + ". In Range: " + range + ".");
		System.out.println("2: " + b.getSkin() +". Health: " +  b.getHealth() + "/" + b.getMaxHP() + ". In Range: " + range2 + ".");
		System.out.println("3: " + c.getSkin() +". Health: " +  c.getHealth() + "/" + c.getMaxHP() + ". In Range: " + range3 + ".");
		System.out.println("Who do you want to Glyph Step towards: ");
		String targetResponse = input.next();
		if(targetResponse.equals("1")) {
			if(p.inRange(a, 25)) {
				p.setStep(true);
				p.addDashes(1);
				p.increaseMovement(4);
				p.getLoc().set(a.getLoc().getX(), a.getLoc().getY());
				p.protect(0.25,1);
				d.protect(0.25,1);
				e.protect(0.25,1);
				System.out.println(p.voiceline());
				p.setCooldown(3);
			}else {
				System.out.println();
				System.out.println("Target is out of range!");
				System.out.println();
			}
		}
		if(targetResponse.equals("2")) {
			if(p.inRange(b, 25)) {
				p.setStep(true);
				p.addDashes(1);
				p.increaseMovement(4);
				p.getLoc().set(b.getLoc().getX(), b.getLoc().getY());
				p.protect(0.25,1);
				d.protect(0.25,1);
				e.protect(0.25,1);
				System.out.println(p.voiceline());
				p.setCooldown(3);
			}else {
				System.out.println();
				System.out.println("Target is out of range!");
				System.out.println();
			}
		}
		if(targetResponse.equals("3")) {
			if(p.inRange(c, 25)) {
				p.setStep(true);
				p.addDashes(1);
				p.increaseMovement(4);
				p.getLoc().set(c.getLoc().getX(), c.getLoc().getY());
				p.protect(0.25,1);
				d.protect(0.25,1);
				e.protect(0.25,1);
				System.out.println(p.voiceline());
				p.setCooldown(3);
			}else {
				System.out.println();
				System.out.println("Target is out of range!");
				System.out.println();
			}
		}
		System.out.println();
	}
	
	public static void PetraUltimate(Player p, Player a, Player b1, Player c, Player d, Player e) {
		Scanner input = new Scanner(System.in);
		Location l;
		Location l2;
		Location l3;
		Battlefield.temple = true;
		b.printField(p, a, b1, c, d, e, orbs, cover, p, a, b1, c, utility);
		System.out.println("1: Left");
		System.out.println("2: Right");
		System.out.println("3: Up");
		System.out.println("4: Down");
		System.out.println("Which direction do you want to send the boulder in: ");
		String targetResponse = input.next();
		if(targetResponse.equals("1")) {
			l = new Location(-1, a.getLoc().getY());
			l2 = new Location(-1, b1.getLoc().getY());
			l3 = new Location(-1, c.getLoc().getY());
			if(a.getLoc().getY() >= 14 && a.getLoc().getY() <= 27) {
				a.knockbacked(l);
				a.knockbacked(l);
				a.knockbacked(l);
				a.paralyze(1);
			}
			if(b1.getLoc().getY() >= 14 && b1.getLoc().getY() <= 27) {
				b1.knockbacked(l2);
				b1.knockbacked(l2);
				b1.knockbacked(l2);
				b1.paralyze(1);
			}
			if(c.getLoc().getY() >= 14 && c.getLoc().getY() <= 27) {
				c.knockbacked(l3);
				c.knockbacked(l3);
				c.knockbacked(l3);
				c.paralyze(1);
			}
		}
		if(targetResponse.equals("2")) {
			l = new Location(100, a.getLoc().getY());
			l2 = new Location(100, b1.getLoc().getY());
			l3 = new Location(100, c.getLoc().getY());
			if(a.getLoc().getY() >= 14 && a.getLoc().getY() <= 27) {
				a.knockbacked(l);
				a.knockbacked(l);
				a.knockbacked(l);
				a.paralyze(1);
			}
			if(b1.getLoc().getY() >= 14 && b1.getLoc().getY() <= 27) {
				b1.knockbacked(l2);
				b1.knockbacked(l2);
				b1.knockbacked(l2);
				b1.paralyze(1);
			}
			if(c.getLoc().getY() >= 14 && c.getLoc().getY() <= 27) {
				c.knockbacked(l3);
				c.knockbacked(l3);
				c.knockbacked(l3);
				c.paralyze(1);
			}
		}
		if(targetResponse.equals("4")) {
			l = new Location(a.getLoc().getX(), -1);
			l2 = new Location(b1.getLoc().getX(), -1);
			l3 = new Location(c.getLoc().getX(), -1);
			if(a.getLoc().getX() >= 14 && a.getLoc().getX() <= 27) {
				a.knockbacked(l);
				a.knockbacked(l);
				a.knockbacked(l);
				a.paralyze(1);
			}
			if(b1.getLoc().getX() >= 14 && b1.getLoc().getX() <= 27) {
				b1.knockbacked(l2);
				b1.knockbacked(l2);
				b1.knockbacked(l2);
				b1.paralyze(1);
			}
			if(c.getLoc().getX() >= 14 && c.getLoc().getX() <= 27) {
				c.knockbacked(l3);
				c.knockbacked(l3);
				c.knockbacked(l3);
				c.paralyze(1);
			}
		}
		if(targetResponse.equals("3")) {
			l = new Location(a.getLoc().getX(), 100);
			l2 = new Location(b1.getLoc().getX(), 100);
			l3 = new Location(c.getLoc().getX(), 100);
			if(a.getLoc().getX() >= 14 && a.getLoc().getX() <= 27) {
				a.knockbacked(l);
				a.knockbacked(l);
				a.knockbacked(l);
				a.paralyze(1);
			}
			if(b1.getLoc().getX() >= 14 && b1.getLoc().getX() <= 27) {
				b1.knockbacked(l2);
				b1.knockbacked(l2);
				b1.knockbacked(l2);
				b1.paralyze(1);
			}
			if(c.getLoc().getX() >= 14 && c.getLoc().getX() <= 27) {
				c.knockbacked(l3);
				c.knockbacked(l3);
				c.knockbacked(l3);
				c.paralyze(1);
			}
		}
		Battlefield.temple = false;
		p.cleanse();
		p.heal(0.1);
		d.cleanse();
		d.heal(0.1);
		e.cleanse();
		e.heal(0.1);
		p.addHealing(p.getMaxHP() * 0.1);
		p.addHealing(d.getMaxHP() * 0.1);
		p.addHealing(e.getMaxHP() * 0.1);
		p.power(0.5, 1);
		p.resetUlt();
		System.out.println("\"Yall better run for the hills!\"");
		System.out.println();
	}
	
	public static void QuincyAttack(Player p, Player a) {
		p.attack(a);
		a.knockbacked(p.getLoc());
		if (p.getDamage() > 225) {
			p.addQuincyAttack();
		}
		if (p.getQuincy() > 0) {
			for(int i = 0; i < p.getQuincy(); i++) {
				p.increaseHP(75);
				p.addHealing(75);
			}
		}
		System.out.println();
	}
	
	public static void QuincyAbility(Player p, Player a, Player b) {
		Scanner input = new Scanner(System.in);
		if(!a.isAlive() && !b.isAlive()) {
			p.setShield();
			p.heal(0.1);
			p.addHealing(p.getMaxHP() * 0.1);
			p.setCooldown(1);
			System.out.println("\"I will stand until the end!\"");
			System.out.println();
			return;
		}
		System.out.println("1: " + a.getSkin() +a.showHP() +  a.getHealth() + "/" + a.getMaxHP() + ".");
		System.out.println("2: " + b.getSkin() +b.showHP() +  b.getHealth() + "/" + b.getMaxHP() + ".");
		System.out.println("3: Remove Current Link.");
		System.out.println("Who do you want to link onto: ");
		String targetResponse = input.next();
		if(targetResponse.equals("1")) {
			a.setQuincy(p);
			a.heal(0.1);
			p.addHealing(a.getMaxHP() * 0.1);
			p.setShield();
			p.setCooldown(1);
		}
		if(targetResponse.equals("2")) {
			b.setQuincy(p);
			b.heal(0.1);
			p.addHealing(b.getMaxHP() * 0.1);
			p.setShield();
			p.setCooldown(1);
		}
		if(targetResponse.equals("3")) {
			a.resetQuincy();
			b.resetQuincy();
			System.out.println("\"Stay strong for now!\"");
			System.out.println();
			return;
		}
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void QuincyUltimate(Player p, Player a, Player b) {
		p.protect(0.5,1);
		p.setRally(true);
		p.cleanse();
		p.heal(0.1);
		p.increaseMovement(3);
		if (p.inRange(a)) {
			a.cleanse();
			a.heal(0.15);
			p.addHealing(a.getMaxHP() * 0.15);
			a.increaseMovement(3);
			a.fortify(1);
		}
		if (p.inRange(b)) {
			b.cleanse();
			b.heal(0.15);
			p.addHealing(b.getMaxHP() * 0.15);
			b.increaseMovement(3);
			b.fortify(1);
		}
		p.addHealing(p.getMaxHP() * 0.1);
		p.resetUlt();
		System.out.println("\"We have a royal decree to make!\"");
		System.out.println();
	}
	
	public static void UniceAttack(Player p, Player a, Player b, Player c) {
		p.attack(a);
		if (p.ultActive()) {
			a.knockbacked(p.getLoc());
		}
		if(a.inRange(b, 4)) {
			p.attack(b);
			if (p.ultActive()) {
				b.knockbacked(p.getLoc());
			}
		}
		if(a.inRange(c, 4)) {
			p.attack(c);
			if (p.ultActive()) {
				c.knockbacked(p.getLoc());
			}
		}
		System.out.println();
	}
	
	public static void UniceAbility(Player p, Player a, Player b, Player c, Player d, Player e, Location l) {
		if(!p.inRange(l)) {
			System.out.println("Can't send Gale there!");
			System.out.println();
			return;
		}
		if(GameSim.b.hasTrench(l.getX(), l.getY())) {
			System.out.println("Can't send Gale there!");
			System.out.println();
			return;
		}
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).getName().equals("Umbrella") && GameSim.utility.get(j).owner(p)) {
				GameSim.utility.remove(j);
			}
		}
		Utility Umbrella = new Utility("Umbrella", l, p, d, e, a, b, c);
		utility.add(Umbrella);
		p.setCooldown(4);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void UniceUltimate(Player p) {
		p.setUlt();
		p.power(0.5, 100);
		System.out.println("\"Rain on me, Tsunami.\"");
		System.out.println();
	}
	
	public static void FlorAttack(Player p, Player a, Player b, Player c) {
		if(!p.inRange(a) && !p.inRange(b) && !p.inRange(c)) {
			System.out.println("No targets in range!");
			System.out.println();
			return;
		}
		if(p.inRange(a)){
			p.attack(a);
			a.blind(0.05, 1);
			a.poison(0.05, 1);
		}
		if(p.inRange(b)){
			p.attack(b);
			b.blind(0.05, 1);
			b.poison(0.05, 1);
		}
		if(p.inRange(c)){
			p.attack(c);
			c.blind(0.05, 1);
			c.poison(0.05, 1);
		}
		System.out.println();
	}
	
	public static void FlorAbility(Player p, Player a, Player b, Player c, Player d, Player e, Location l) {
		if(GameSim.b.hasTrench(l.getX(), l.getY())) {
			System.out.println("Can't send the pylon there!");
			System.out.println();
			return;
		}
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).getName().equals("Pylon") && GameSim.utility.get(j).owner(p) && GameSim.utility.get(j).getLoc().eqLoc(l)) {
				System.out.println("Can't send the pylon there!");
				System.out.println();
				return;
			}
		}
		Utility Pylon = new Utility("Pylon", l, p, d, e, a, b, c);
		utility.add(Pylon);
		p.setCooldown(3);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void FlorUltimate(Player p, Player a, Player b) {
		p.protect(0.3,1);
		p.counter(1);
		a.protect(0.3,1);
		a.counter(1);
		b.protect(0.3,1);
		b.counter(1);
		p.setBee(true);
		a.setBee(true);
		b.setBee(true);
		p.resetUlt();
		System.out.println("\"Buzz off twerps!\"");
		System.out.println();
	}
	
	public static void YuriAttack(Player p, Player a, Player b, Player c, Player d, Player e, Location l) {
		boolean madeTurret = false;
		if(!p.inRange(l)) {
			System.out.println("Can't put the Turret that far!");
			System.out.println();
			return;
		}
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).getName().equals("Turret") && GameSim.utility.get(j).owner(p)) {
				madeTurret = true;
			}
		}
		if (madeTurret) {
			for(int j = 0; j < GameSim.utility.size(); j++) {
				if(GameSim.utility.get(j).getName().equals("Turret") && GameSim.utility.get(j).owner(p)) {
					if (!GameSim.utility.get(j).isActivated()) {
						System.out.println("Turret cannot be relocated while deactivated!");
						System.out.println();
						return;
					}else {
						GameSim.utility.get(j).getLoc().set(l.getX(), l.getY());
						System.out.println("Turret relocated!");
						System.out.println();
						p.setAttacked();
						return;
					}
				}
			}
		}else {
			Utility Turret = new Utility("Turret", l, p, d, e, a, b, c);
			utility.add(Turret);
			System.out.println("Turret deployed.");
			p.setAttacked();
		}
		System.out.println();
	}
	
	public static void YuriAbility(Player p, Player a, Player b) {
		p.sightsee(0.25, 1);
		a.sightsee(0.25, 1);
		b.sightsee(0.25, 1);
		p.heal(0.1);
		a.heal(0.1);
		b.heal(0.1);
		p.addHealing(p.getMaxHP() * 0.1);
		p.addHealing(a.getMaxHP() * 0.1);
		p.addHealing(b.getMaxHP() * 0.1);
		p.setChance(0.1);
		a.setChance(0.1);
		b.setChance(0.1);
		p.setCooldown(3);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void YuriUltimate(Player p) {
		boolean madeTurret = false;
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).getName().equals("Turret") && GameSim.utility.get(j).owner(p)) {
				madeTurret = true;
			}
		}
		if (madeTurret) {
			for(int j = 0; j < GameSim.utility.size(); j++) {
				if(GameSim.utility.get(j).getName().equals("Turret") && GameSim.utility.get(j).owner(p)) {
					GameSim.utility.get(j).activate();
					if (p.getUltcharge() == 5) {
						GameSim.utility.get(j).setSpikes();
						p.increaseUltcharge();
						p.resetUlt();
						System.out.println("\"This turret now has sharp security!\"");
						System.out.println();
						return;
					}
					if (p.getUltcharge() == 6) {
						p.increaseUltcharge();
						p.resetUlt();
						System.out.println("\"Why didn't this turret have a flamethrower before? I'll add one now!\"");
						System.out.println();
						return;
					}
					if (p.getUltcharge() == 7) {
						GameSim.utility.get(j).setHealth(2);
						p.increaseUltcharge();
						p.resetUlt();
						System.out.println("\"Try breaking through us now!\"");
						System.out.println();
						return;
					}
					if (p.getUltcharge() == 8) {
						ArrayList<Effect> e = new ArrayList<Effect>();
						Effect MidnitePower = new Effect("power", 0.5, 100);
						e.add(MidnitePower);
						p.addEffects(e);
						p.applyEffects();
						p.resetUlt();
						System.out.println("\"Molten core overload!\"");
						System.out.println();
						return;
					}
				}
			}
		}else {
			System.out.println("You need your turret placed down to use your ultimate!");
			System.out.println();
			return;
		}
	}
	
	public static void MillieAttack(Player p, Player a, Player b, Player c) {
		if (p.getDaggers() == 0) {
			System.out.println("I'm out of daggers!");
			System.out.println();
			return;
		}
		Scanner input = new Scanner(System.in);
		String range = "No";
		if(p.inRange(a)) {
			range = "Yes";
		}
		String range2 = "No";
		if(p.inRange(b)) {
			range2 = "Yes";
		}
		String range3 = "No";
		if(p.inRange(c)) {
			range3 = "Yes";
		}
		System.out.println("1: " + a.getSkin() +". Health: " +  a.getHealth() + "/" + a.getMaxHP() + ". In Range: " + range + ". Cover: " + a.getCover());
		System.out.println("2: " + b.getSkin() +". Health: " +  b.getHealth() + "/" + b.getMaxHP() + ". In Range: " + range2 + ". Cover: " + b.getCover());
		System.out.println("3: " + c.getSkin() +". Health: " +  c.getHealth() + "/" + c.getMaxHP() + ". In Range: " + range3 + ". Cover: " + c.getCover());
		if(!p.inRange(a) && !p.inRange(b) && !p.inRange(c)) {
			System.out.println();
			System.out.println("No targets in range!");
			System.out.println();
			return;
		}
		while(p.getDaggers() > 0) {
			System.out.print("Who do you want to throw a dagger at (" + p.getDaggers() + " dagger(s) left): ");
			String targetResponse = input.next();
			if(targetResponse.equals("1")) {
				if(p.inRange(a)) {
					p.attack(a);
					p.useDagger();
				}else {
					System.out.println();
					System.out.println("Target is out of range!");
					System.out.println();
				}
			}
			if(targetResponse.equals("2")) {
				if(p.inRange(b)) {
					p.attack(b);
					p.useDagger();
				}else {
					System.out.println();
					System.out.println("Target is out of range!");
					System.out.println();
				}
			}
			if(targetResponse.equals("3")) {
				if(p.inRange(c)) {
					p.attack(c);
					p.useDagger();
				}else {
					System.out.println();
					System.out.println("Target is out of range!");
					System.out.println();
				}
			}
			if(targetResponse.equals("c")) {
				break;
			}
		}
		if (p.getDaggers() == 0) {
			System.out.println("Out of daggers!");
		}
		p.resetAttack();
		System.out.println();
	}
	
	public static void MillieAbility(Player p, Player a, Player b, Player c, Player d, Player e, Location l) {
		if(!p.inRange(l)) {
			System.out.println("Can't send the Iron Shield that far!");
			System.out.println();
			return;
		}
		Utility Iron = new Utility("Iron", l, p, d, e, a, b, c);
		utility.add(Iron);
		p.useIron();
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void MillieUltimate(Player p, Player a, Player b, Player c) {
		if (p.getTrash() == 0) {
			System.out.println("I'm out of trash!");
			System.out.println();
			return;
		}
		Scanner input = new Scanner(System.in);
		System.out.println("1: " + a.getSkin() +". Health: " +  a.getHealth() + "/" + a.getMaxHP());
		System.out.println("2: " + b.getSkin() +". Health: " +  b.getHealth() + "/" + b.getMaxHP());
		System.out.println("3: " + c.getSkin() +". Health: " +  c.getHealth() + "/" + c.getMaxHP());
		while(p.getTrash() > 0) {
			System.out.print("Who do you want to throw trash at (" + p.getTrash() + " trash piece(s) left): ");
			String targetResponse = input.next();
			if(targetResponse.equals("1")) {
				a.takeDamage(250);
				p.addDamage(250);
				a.poison(0.15, 1);
				p.useTrash();
			}
			if(targetResponse.equals("2")) {
				b.takeDamage(250);
				p.addDamage(250);
				b.poison(0.15, 1);
				p.useTrash();
			}
			if(targetResponse.equals("3")) {
				c.takeDamage(250);
				p.addDamage(250);
				c.poison(0.15, 1);
				p.useTrash();
			}
			if(targetResponse.equals("c")) {
				break;
			}
		}
		if (p.getTrash() == 0) {
			System.out.println("Out of trash!");
		}
		System.out.println("\"Trash IS Treasure!\"");
		p.resetUlt();
		System.out.println();
	}
	
	public static void LeafAttack(Player p, Player a) {
		String saveCover = a.getCover();
		a.setCover("None");
		p.attack(a);
		p.attack(a);
		p.attack(a);
		a.setCover(saveCover);
		System.out.println();
	}
	
	public static void LeafAbility(Player p, Player a, Player b, Player c) {
		a.weary(1);
		b.weary(1);
		c.weary(1);
		a.setTremor(true);
		b.setTremor(true);
		c.setTremor(true);
		p.setCooldown(3);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void LeafUltimate(Player p, Player a, Player b, Player c, Location l) {
		a.daze(1);
		b.daze(1);
		c.daze(1);
		a.knockbacked(l);
		a.knockbacked(l);
		b.knockbacked(l);
		b.knockbacked(l);
		c.knockbacked(l);
		c.knockbacked(l);
		a.setTectonic(true);
		b.setTectonic(true);
		c.setTectonic(true);
		p.resetUlt();
		System.out.println("\"No slouching around!\"");
		System.out.println();
	}
	
	public static void CourtneyAttack(Player p, Player a, Player b, Player c) {
		p.attack(a);
		if(b.getLoc().getX() == a.getLoc().getX() || b.getLoc().getY() == a.getLoc().getY()) {
			b.takeDamage(p.getDamage() * 0.75);
			p.addDamage(p.getDamage() * 0.75);
		}
		if(c.getLoc().getX() == a.getLoc().getX() || c.getLoc().getY() == a.getLoc().getY()) {
			c.takeDamage(p.getDamage() * 0.75);
			p.addDamage(p.getDamage() * 0.75);
		}
		System.out.println();
	}
	
	public static void CourtneyAbility(Player p) {
		p.addDashes(1);
		p.setHover(true);
		p.setCooldown(3);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void CourtneyUltimate(Player p, Player a, Player b, Player c, Player d, Player e) {
		p.setNebula(true);
		for(int k = 0; k < 42; k++) {
			for(int h = 0; h < 42; h++){
				Location l = new Location(k, h);
				if(p.getLoc().atRange(l, 3)) {
					Utility Nebula = new Utility("Nebula", l, p, d, e, a, b, c);
					utility.add(Nebula);
				}
				if(p.getLoc().atRange(l, 6)) {
					Utility Nebula = new Utility("Nebula", l, p, d, e, a, b, c);
					utility.add(Nebula);
				}
				if(p.getLoc().atRange(l, 9)) {
					Utility Nebula = new Utility("Nebula", l, p, d, e, a, b, c);
					utility.add(Nebula);
				}
			}
		}
		p.addDashes(1);
		p.setHover(true);
		p.resetUlt();
		System.out.println("\"Lighting up this party!\"");
		System.out.println();
	}
	
	public static void DivineAttack(Player p, Player a, Player b, Player c) {
		for (int i = 0; i < 10; i++) {
			double rand = Math.random();
			double rand2 = Math.random();
			double rand3 = Math.random();
			p.attack(a);
			if(a.inRange(b, 10) && rand < 0.25) {
				p.attack(b);
			}
			if(a.inRange(c, 10) && rand2 < 0.25) {
				p.attack(c);
			}
			if (rand3 < 0.1) {
				p.power(0.1, 1);
			}
		}
		System.out.println();
	}
	
	public static void DivineAbility(Player p, Player a, Player b) {
		Scanner input = new Scanner(System.in);
		if(!a.isAlive() && !b.isAlive()) {
			p.cleanse();
			p.heal(0.1);
			p.addHealing(p.getMaxHP() * 0.1);
			p.setShield();
			p.setCooldown(2);
			System.out.println("\"Sigh, noone to pull again.\"");
			System.out.println();
			return;
		}
		System.out.println("1: " + a.getSkin() +a.showHP() +  a.getHealth() + "/" + a.getMaxHP() + ".");
		System.out.println("2: " + b.getSkin() +b.showHP() +  b.getHealth() + "/" + b.getMaxHP() + ".");
		System.out.println("Who do you want to stargrip back: ");
		String targetResponse = input.next();
		if(targetResponse.equals("1")) {
			a.getLoc().set(p.getLoc().getX(), p.getLoc().getY());
			a.cleanse();
			a.heal(0.1);
			p.addHealing(a.getMaxHP() * 0.1);
			a.setShield();
			p.setCooldown(2);
		}
		if(targetResponse.equals("2")) {
			b.getLoc().set(p.getLoc().getX(), p.getLoc().getY());
			b.cleanse();
			b.heal(0.1);
			p.addHealing(b.getMaxHP() * 0.1);
			b.setShield();
			p.setCooldown(2);
		}
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void DivineUltimate(Player p, Player a, Player b, Player c, Location l) {
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).getName().equals("Starpull") && GameSim.utility.get(j).owner(p)) {
				GameSim.utility.remove(j);
			}
		}
		Utility Star = new Utility("Starpull", l, p, p, p, a, b, c);
		utility.add(Star);
		p.resetUlt();
		System.out.println("\"The stars align for your doom!\"");
		System.out.println();
	}
	
	public static void GambitAttack(Player p, Player a, Player b, Player c) {
		p.attack(a);
		if (a.getLoc().getX() != p.getLoc().getX() && a.getLoc().getY() != p.getLoc().getY()) {
			a.takeDamage(p.getDamage() * 0.1);
			p.addDamage(p.getDamage() * 0.1);
		}
		for(int j = 0; j < utility.size(); j++) {
			if(utility.get(j).getName().equals("Rook") && utility.get(j).owner(p) && utility.get(j).rookActive()) {
				if (b.getLoc().eqLoc(utility.get(j).getLoc())) {
					b.cleanse();
				}
				if (c.getLoc().eqLoc(utility.get(j).getLoc())) {
					c.cleanse();
				}
			}
		}
		System.out.println();
	}
	
	public static void GambitAbility(Player p, Player a, Player b, Player c, Player d, Player e) {
		if (c.getLoc().eqLoc(p.getLoc()) && c.getHealth() < c.getMaxHP() * 0.1) {
			c.down();
			System.out.println("\"Consider that a checkmate!\"");
		}
		if (d.getLoc().eqLoc(p.getLoc()) && d.getHealth() < d.getMaxHP() * 0.1) {
			d.down();
			System.out.println("\"Consider that a checkmate!\"");
		}
		if (e.getLoc().eqLoc(p.getLoc()) && e.getHealth() < e.getMaxHP() * 0.1) {
			e.down();
			System.out.println("\"Consider that a checkmate!\"");
		}
		System.out.println();
		for(int j = 0; j < utility.size(); j++) {
			if(utility.get(j).getName().equals("Rook") && utility.get(j).owner(p) && utility.get(j).rookActive()) {
				if (a.getLoc().eqLoc(utility.get(j).getLoc())) {
					a.heal(0.1);
					a.power(0.1, 1);
				}
				if (b.getLoc().eqLoc(utility.get(j).getLoc())) {
					b.heal(0.1);
					b.power(0.1, 1);
				}
			}
		}
        p.heal(0.1);
		a.heal(0.1);
		b.heal(0.1);
		p.addHealing(p.getMaxHP() * 0.1);
		p.addHealing(a.getMaxHP() * 0.1);
		p.addHealing(b.getMaxHP() * 0.1);
		b.applyEffects();
		p.power(0.1, 1);
		a.power(0.1, 1);
		b.power(0.1, 1);
        p.setCooldown(3);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void GambitUltimate(Player p, Player a, Player b, Player c, Player d, Player e) {
		if (p.getAb().equals("a")) {
			Utility Pawn = new Utility("Pawn", new Location(10, 11), p, d, e, a, b, c);
			utility.add(Pawn);
			Pawn.setDirection("up");
			Utility Pawn2 = new Utility("Pawn", new Location(13, 11), p, d, e, a, b, c);
			utility.add(Pawn2);
			Pawn2.setDirection("up");
			Utility Pawn3 = new Utility("Pawn", new Location(16, 11), p, d, e, a, b, c);
			utility.add(Pawn3);
			Pawn3.setDirection("up");
			Utility Pawn4 = new Utility("Pawn", new Location(19, 11), p, d, e, a, b, c);
			utility.add(Pawn4);
			Pawn4.setDirection("up");
			Utility Pawn5 = new Utility("Pawn", new Location(22, 11), p, d, e, a, b, c);
			utility.add(Pawn5);
			Pawn5.setDirection("up");
			Utility Pawn6 = new Utility("Pawn", new Location(25, 11), p, d, e, a, b, c);
			utility.add(Pawn6);
			Pawn6.setDirection("up");
			Utility Pawn7 = new Utility("Pawn", new Location(28, 11), p, d, e, a, b, c);
			utility.add(Pawn7);
			Pawn7.setDirection("up");
			Utility Pawn8 = new Utility("Pawn", new Location(31, 11), p, d, e, a, b, c);
			utility.add(Pawn8);
			Pawn8.setDirection("up");
		}else {
			Utility Pawn = new Utility("Pawn", new Location(10, 27), p, d, e, a, b, c);
			utility.add(Pawn);
			Pawn.setDirection("down");
			Utility Pawn2 = new Utility("Pawn", new Location(13, 27), p, d, e, a, b, c);
			utility.add(Pawn2);
			Pawn2.setDirection("down");
			Utility Pawn3 = new Utility("Pawn", new Location(16, 27), p, d, e, a, b, c);
			utility.add(Pawn3);
			Pawn3.setDirection("down");
			Utility Pawn4 = new Utility("Pawn", new Location(19, 27), p, d, e, a, b, c);
			utility.add(Pawn4);
			Pawn4.setDirection("down");
			Utility Pawn5 = new Utility("Pawn", new Location(22, 27), p, d, e, a, b, c);
			utility.add(Pawn5);
			Pawn5.setDirection("down");
			Utility Pawn6 = new Utility("Pawn", new Location(25, 27), p, d, e, a, b, c);
			utility.add(Pawn6);
			Pawn6.setDirection("down");
			Utility Pawn7 = new Utility("Pawn", new Location(28, 27), p, d, e, a, b, c);
			utility.add(Pawn7);
			Pawn7.setDirection("down");
			Utility Pawn8 = new Utility("Pawn", new Location(31, 27), p, d, e, a, b, c);
			utility.add(Pawn8);
			Pawn8.setDirection("down");
		}
		p.resetUlt();
		System.out.println("\"Let the promotion ceremony begin!\"");
		System.out.println();
	}
	
	public static void CloudAttack(Player p, Player a) {
		int times = 2;
		if (a.getLoc().eqLoc(p.getLoc())) {
			times = 4;
		}
		for (int i = 0; i < times; i++) {
			double rand = Math.random();
			p.attack(a);
			if (rand < 0.15) {
				a.blind(0.05, 1);
			}
		}
		System.out.println();
	}
	
	public static void CloudAbility(Player p, Player a, Player b, Player c, Player d, Player e, Location l) {
		Utility Smoke = new Utility("Smoke", l, p, d, e, a, b, c);
		utility.add(Smoke);
		p.makeSmoke();
		if (p.getSmokes() == 2) {
			p.setCooldown(3);
			p.setSmokes(0);
		}
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void CloudUltimate(Player p, Player a, Player b, Player c) {
		Scanner input = new Scanner(System.in);
		if (p.getDarkness()) {
			if (p.getTeleport()) {
				System.out.println("1: " + a.getSkin() +". Health: " +  a.getHealth() + "/" + a.getMaxHP());
				System.out.println("2: " + b.getSkin() +". Health: " +  b.getHealth() + "/" + b.getMaxHP());
				System.out.println("3: " + c.getSkin() +". Health: " +  c.getHealth() + "/" + c.getMaxHP());
				System.out.print("Who do you want to teleport to: ");
				String targetResponse = input.next();
				if(targetResponse.equals("1")) {
					p.getLoc().set(a.getLoc().getX(), a.getLoc().getY());
					p.setTeleport(false);
					System.out.println("\"If I must live in this nightmare, you WILL join me too!\"");
					System.out.println();
				}
				if(targetResponse.equals("2")) {
					p.getLoc().set(b.getLoc().getX(), b.getLoc().getY());
					p.setTeleport(false);
					System.out.println("\"If I must live in this nightmare, you WILL join me too!\"");
					System.out.println();
				}
				if(targetResponse.equals("3")) {
					p.getLoc().set(c.getLoc().getX(), c.getLoc().getY());
					p.setTeleport(false);
					System.out.println("\"If I must live in this nightmare, you WILL join me too!\"");
					System.out.println();
				}
			}else {
				System.out.println("Can't teleport right now.");
				System.out.println();
			}
		}else {
			p.mend(0.5,100);
			p.sightsee(1, 100);
			p.setDarkness();
			a.setHitDarkness();
			b.setHitDarkness();
			c.setHitDarkness();
			p.setTeleport(true);
			System.out.println("\"PAIN is my superpower.\"");
			System.out.println();
		}
	}
	
	public static void WinnieAttack(Player p, Player a) {
		p.attack(a);
		double rand = Math.random();
		switch (p.getColor()) {
		case "Red":
			if (p.getRed() == 0) {
				System.out.println("Not enough red charges!");
				break;
			}
			p.useRed();
			a.takeDamage(50);
			p.addDamage(50);
			if (rand < 0.15) {
				a.ignite(1);
			}
			break;
		case "Blue":
			if (p.getBlue() == 0) {
				System.out.println("Not enough blue charges!");
				break;
			}
			p.useBlue();
			a.takeDamage(50);
			p.addDamage(50);
			if (rand < 0.25) {
				a.freeze(1);
			}
			break;
		case "Yellow":
			if (p.getYellow() == 0) {
				System.out.println("Not enough yellow charges!");
				break;
			}
			p.useYellow();
			a.takeDamage(50);
			p.addDamage(50);
			a.blind(0.15, 1);
			break;
		case "Orange":
			if (p.getRed() == 0 || p.getYellow() == 0) {
				System.out.println("Not enough red/yellow charges!");
				break;
			}
			p.useRed();
			p.useYellow();
			a.takeDamage(75);
			p.addDamage(75);
			a.weak(0.25, 1);
			break;
		case "Green":
			if (p.getBlue() == 0 || p.getYellow() == 0) {
				System.out.println("Not enough blue/yellow charges!");
				break;
			}
			p.useBlue();
			p.useYellow();
			a.takeDamage(75);
			p.addDamage(75);
			a.poison(0.25, 1);
			break;
		case "Purple":
			if (p.getRed() == 0 || p.getBlue() == 0) {
				System.out.println("Not enough red/blue charges!");
				break;
			}
			p.useRed();
			p.useBlue();
			a.takeDamage(75);
			p.addDamage(75);
			a.knockbacked(p.getLoc());
			break;
		}
		System.out.println();
	}
	
	public static void WinnieAbility(Player p, Player a, Player b, Player c, Player d, Player e, Location l) {
		if(!p.inReach(l, 20)) {
			System.out.println("Can't graffiti there!");
			System.out.println();
			return;
		}
		Utility Trap = new Utility("Trap", l, p, d, e, a, b, c);
		utility.add(Trap);
		switch (p.getColor()) {
		case "Red":
			if (p.getRed() == 0) {
				System.out.println("Not enough red charges!");
				break;
			}
			p.useRed();
			p.power(0.15, 1);
			d.power(0.15, 1);
			e.power(0.15, 1);
			break;
		case "Blue":
			if (p.getBlue() == 0) {
				System.out.println("Not enough blue charges!");
				break;
			}
			p.useBlue();
			p.setShield();
			d.setShield();
			e.setShield();
			break;
		case "Yellow":
			if (p.getYellow() == 0) {
				System.out.println("Not enough yellow charges!");
				break;
			}
			p.useYellow();
			p.heal(0.2);
			d.heal(0.2);
			e.heal(0.2);
			p.addHealing(p.getMaxHP() * 0.2);
			p.addHealing(d.getMaxHP() * 0.2);
			p.addHealing(e.getMaxHP() * 0.2);
			break;
		case "Orange":
			if (p.getRed() == 0 || p.getYellow() == 0) {
				System.out.println("Not enough red/yellow charges!");
				break;
			}
			p.useRed();
			p.useYellow();
			break;
		case "Green":
			if (p.getBlue() == 0 || p.getYellow() == 0) {
				System.out.println("Not enough blue/yellow charges!");
				break;
			}
			p.useBlue();
			p.useYellow();
			break;
		case "Purple":
			if (p.getRed() == 0 || p.getBlue() == 0) {
				System.out.println("Not enough red/blue charges!");
				break;
			}
			p.useRed();
			p.useBlue();
			break;
		}
		Trap.setColor(p.getColor());
		p.setCooldown(3);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void WinnieUltimate(Player p, Player a, Player b, Player c, Player d, Player e) {
		for (int i = 0; i < 5; i++) {
			Location l = SetCursor(p, a, b, c, d, e, 4);
			Utility Mural = new Utility("Mural", l, p, d, e, a, b, c);
			utility.add(Mural);
		}
		p.cleanse();
		d.cleanse();
		e.cleanse();
		p.refillCans();
		p.increaseOgMovement(2);
		d.increaseOgMovement(2);
		e.increaseOgMovement(2);
		p.resetUlt();
		System.out.println("\"Enjoy the masterpiece!\"");
		System.out.println();
	}
	
	public static void PearlAttack(Player p, Player a, Player b, Player c) {
		p.attack(a);
		a.poison(0.15, 1);
		if (p.hasEnhance()) {
			if(a.inRange(b, 7)) {
				p.attack(b);
				b.poison(0.15, 1);
			}
			if(a.inRange(c, 7)) {
				p.attack(c);
				c.poison(0.15, 1);
			}
		}
		System.out.println();
	}
	
	public static void PearlAbility(Player p, Player a, Player b, Player c) {
		if (p.getTide().equals("")) {
			Scanner input = new Scanner(System.in);
			System.out.println("1: High Tide (Knockback)");
			System.out.println("2: Low Tide (Drag In)");
			System.out.println("Which tide do you want to start with: ");
			String targetResponse = input.next();
			if(targetResponse.equals("1")) {
				p.setTide("high");
			}
			if(targetResponse.equals("2")) {
				p.setTide("low");
			}
			System.out.println("\"Great starting choice!\"");
			System.out.println();
			return;
		}else {
			if (p.getTide().equals("high")) {
				if (p.inRange(a)) {
					a.knockbacked(p.getLoc());
				}
				if (p.inRange(b)) {
					b.knockbacked(p.getLoc());
				}
				if (p.inRange(c)) {
					c.knockbacked(p.getLoc());
				}
				if (p.hasEnhance()) {
					if (p.inRange(a)) {
						a.knockbacked(p.getLoc());
						a.takeDamage(150);
						p.addDamage(150);
					}
					if (p.inRange(b)) {
						b.knockbacked(p.getLoc());
						b.takeDamage(150);
						p.addDamage(150);
					}
					if (p.inRange(c)) {
						c.knockbacked(p.getLoc());
						c.takeDamage(150);
						p.addDamage(150);
					}
				}
				p.setTide("low");
			}else {
				if (p.inRange(a)) {
					a.dragIn(p.getLoc(), 4);
					a.resetCover();
				}
				if (p.inRange(b)) {
					b.dragIn(p.getLoc(), 4);
					b.resetCover();
				}
				if (p.inRange(c)) {
					c.dragIn(p.getLoc(), 4);
					c.resetCover();
				}
				if (p.hasEnhance()) {
					if (p.inRange(a)) {
						a.dragIn(p.getLoc(), 4);
						a.takeDamage(150);
						p.addDamage(150);
					}
					if (p.inRange(b)) {
						b.dragIn(p.getLoc(), 4);
						b.takeDamage(150);
						p.addDamage(150);
					}
					if (p.inRange(c)) {
						c.dragIn(p.getLoc(), 4);
						c.takeDamage(150);
						p.addDamage(150);
					}
				}
				p.setTide("high");
			}
			p.setCooldown(2);
			System.out.println(p.voiceline());
			System.out.println();
		}
	}
	
	public static void PearlUltimate(Player p, Party one, Party two) {
		one.setEbbFlow();
		one.setEnemyParty(two);
		if (p.hasEnhance()) {
			one.setEnhance();
		}
		p.setUlt();
		System.out.println("\"Ebb 'till you're ready!\"");
		System.out.println("\"ᶠˡᵒʷ ʷʰᵉⁿ ʸᵒᵘ ᵍᵉᵗ ᶦᵗ!\"");
		System.out.println();
	}
	
	public static void AndrewAttack(Player p, Player a, Player b, Player c) {
		p.attack(a);
		a.setEnemies(p, b, c);
		a.hijack();
		System.out.println();
	}
	
	public static void AndrewAbility(Player p, Player a, Player b, Player c) {
		Utility Sock = new Utility("Sock", new Location(p.getLoc().getX() - 1, p.getLoc().getY()), p, p, p, a, b, c);
		utility.add(Sock);
		Utility Sock2 = new Utility("Sock", new Location(p.getLoc().getX() + 1, p.getLoc().getY()), p, p, p, a, b, c);
		utility.add(Sock2);
		Scanner input = new Scanner(System.in);
		System.out.println("1: " + a.getSkin() +". Health: " +  a.getHealth() + "/" + a.getMaxHP());
		System.out.println("2: " + b.getSkin() +". Health: " +  b.getHealth() + "/" + b.getMaxHP());
		System.out.println("3: " + c.getSkin() +". Health: " +  c.getHealth() + "/" + c.getMaxHP());
		System.out.print("Select the first target: ");
		String targetResponse = input.next();
		if(targetResponse.equals("1")) {
			Sock.setTarget(a);
		}
		if(targetResponse.equals("2")) {
			Sock.setTarget(b);
		}
		if(targetResponse.equals("3")) {
			Sock.setTarget(c);
		}
		System.out.println();
		System.out.println("1: " + a.getSkin() +". Health: " +  a.getHealth() + "/" + a.getMaxHP());
		System.out.println("2: " + b.getSkin() +". Health: " +  b.getHealth() + "/" + b.getMaxHP());
		System.out.println("3: " + c.getSkin() +". Health: " +  c.getHealth() + "/" + c.getMaxHP());
		System.out.print("Select the second target: ");
		String targetResponse2 = input.next();
		if(targetResponse2.equals("1")) {
			Sock2.setTarget(a);
		}
		if(targetResponse2.equals("2")) {
			Sock2.setTarget(b);
		}
		if(targetResponse2.equals("3")) {
			Sock2.setTarget(c);
		}
		p.setCooldown(3);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void AndrewUltimate(Player p, Player a, Player b, Player c) {
		if (Battlefield.endgame) {
			Utility Support = new Utility("Support", new Location(10, 17), p, p, p, a, b, c);
			utility.add(Support);
			Utility Support2 = new Utility("Support", new Location(10, 25), p, p, p, a, b, c);
			utility.add(Support2);
			Utility Support3 = new Utility("Support", new Location(32, 17), p, p, p, a, b, c);
			utility.add(Support3);
			Utility Support4 = new Utility("Support", new Location(32, 25), p, p, p, a, b, c);
			utility.add(Support4);
		}else {
			Utility Support = new Utility("Support", new Location(5, 17), p, p, p, a, b, c);
			utility.add(Support);
			Utility Support2 = new Utility("Support", new Location(5, 25), p, p, p, a, b, c);
			utility.add(Support2);
			Utility Support3 = new Utility("Support", new Location(37, 17), p, p, p, a, b, c);
			utility.add(Support3);
			Utility Support4 = new Utility("Support", new Location(37, 25), p, p, p, a, b, c);
			utility.add(Support4);
		}
		p.resetUlt();
		System.out.println("\"Introducing my friend Sockyman!\"");
		System.out.println();
	}
	
	public static void OrchidAttack(Player p, Player a, Player b, Player c) {
		p.attack(a);
		Utility Vine = new Utility("Vine", new Location(a.getLoc().getX(), a.getLoc().getY()), p, p, p, a, b, c);
		utility.add(Vine);
		System.out.println();
	}
	
	public static void OrchidAbility(Player p, Player a, Player b, Player c, Player d, Player e) {
		a.freeze(1);
		b.freeze(1);
		c.freeze(1);
		a.setExpose(true);
		b.setExpose(true);
		c.setExpose(true);
		p.heal(0.1);
		d.heal(0.1);
		e.heal(0.1);
		p.addHealing(p.getMaxHP() * 0.1);
		p.addHealing(d.getMaxHP() * 0.1);
		p.addHealing(e.getMaxHP() * 0.1);
		p.setCooldown(4);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void OrchidUltimate(Player p, Player a, Player b, Player c, Player d, Player e) {
		a.vulnerable(0.05, 4);
		b.vulnerable(0.05, 4);
		c.vulnerable(0.05, 4);
		p.setChance(1);
		d.setChance(1);
		e.setChance(1);
		p.addChance(0.05);
		d.addChance(0.05);
		e.addChance(0.05);
		p.resetUlt();
		System.out.println("\"Your journey stops right now!\"");
		System.out.println();
	}
	
	public static void EverestAttack(Player p, Player a, Player b, Player c) {
		Scanner input = new Scanner(System.in);
		String range = "No";
		if(p.inRange(a)) {
			range = "Yes";
		}
		String range2 = "No";
		if(p.inRange(b)) {
			range2 = "Yes";
		}
		String range3 = "No";
		if(p.inRange(c)) {
			range3 = "Yes";
		}
		System.out.println("1: Single Attack");
		System.out.println("2: Charge Attack");
		System.out.println("3: Use Charged Attack (" + p.getOrbCharges() + " charge(s) stored)");
		System.out.print("What would you like to do with the Destruction Orbs: ");
		String targetResponse = input.next();
		if(targetResponse.equals("1")) {
			if(!p.inRange(a) && !p.inRange(b) && !p.inRange(c)) {
				System.out.println();
				System.out.println("No targets in range!");
				System.out.println();
				return;
			}
			System.out.println();
			System.out.println("1: " + a.getSkin() +". Health: " +  a.getHealth() + "/" + a.getMaxHP() + ". In Range: " + range + ". Location: " + a.getLoc() + ". Cover: " + a.getCover());
			System.out.println("2: " + b.getSkin() +". Health: " +  b.getHealth() + "/" + b.getMaxHP() + ". In Range: " + range2 + ". Location: " + b.getLoc() + ". Cover: " + b.getCover());
			System.out.println("3: " + c.getSkin() +". Health: " +  c.getHealth() + "/" + c.getMaxHP() + ". In Range: " + range3 + ". Location: " + c.getLoc() + ". Cover: " + c.getCover());
			System.out.println("Who do you want to attack: ");
			String targetResponse2 = input.next();
			if(targetResponse2.equals("1")) {
				if(p.inRange(a)) {
					p.attack(a);
					double rand = Math.random();
					if(rand <= 0.05) {
						a.daze(1);
					}
				}else {
					System.out.println();
					System.out.println("Target is out of range!");
					System.out.println();
				}
			}
			if(targetResponse2.equals("2")) {
				if(p.inRange(b)) {
					p.attack(b);
					double rand = Math.random();
					if(rand <= 0.05) {
						b.daze(1);
					}
				}else {
					System.out.println();
					System.out.println("Target is out of range!");
					System.out.println();
				}
			}
			if(targetResponse2.equals("3")) {
				if(p.inRange(c)) {
					p.attack(c);
					double rand = Math.random();
					if(rand <= 0.05) {
						c.daze(1);
					}
				}else {
					System.out.println();
					System.out.println("Target is out of range!");
					System.out.println();
				}
			}
		}
		if(targetResponse.equals("2")) {
			p.addOrbCharge();
			p.setAttacked();
		}
		if(targetResponse.equals("3")) {
			if ((int) ((p.getOrbCharges() * 10) % 10) == 5) {
			    System.out.println("Need a whole number of orbs to use the charged orbs attack.");
			    System.out.println();
			    return;
			}
			if(!p.inRange(a) && !p.inRange(b) && !p.inRange(c)) {
				System.out.println();
				System.out.println("No targets in range!");
				System.out.println();
				return;
			}
			System.out.println();
			System.out.println("1: " + a.getSkin() +". Health: " +  a.getHealth() + "/" + a.getMaxHP() + ". In Range: " + range + ". Location: " + a.getLoc() + ". Cover: " + a.getCover());
			System.out.println("2: " + b.getSkin() +". Health: " +  b.getHealth() + "/" + b.getMaxHP() + ". In Range: " + range2 + ". Location: " + b.getLoc() + ". Cover: " + b.getCover());
			System.out.println("3: " + c.getSkin() +". Health: " +  c.getHealth() + "/" + c.getMaxHP() + ". In Range: " + range3 + ". Location: " + c.getLoc() + ". Cover: " + c.getCover());
			System.out.println("Who do you want to attack: ");
			String targetResponse2 = input.next();
			if(targetResponse2.equals("1")) {
				if(p.inRange(a)) {
					for(int i = 0; i < p.getOrbCharges(); i++) {
						p.attack(a);
						double rand = Math.random();
						if(rand <= 0.05) {
							a.daze(1);
						}
					}
					p.useOrbCharges();
				}else {
					System.out.println();
					System.out.println("Target is out of range!");
					System.out.println();
				}
			}
			if(targetResponse2.equals("2")) {
				if(p.inRange(b)) {
					for(int i = 0; i < p.getOrbCharges(); i++) {
						p.attack(b);
						double rand = Math.random();
						if(rand <= 0.05) {
							b.daze(1);
						}
					}
					p.useOrbCharges();
				}else {
					System.out.println();
					System.out.println("Target is out of range!");
					System.out.println();
				}
			}
			if(targetResponse2.equals("3")) {
				if(p.inRange(c)) {
					p.attack(c);
					for(int i = 0; i < p.getOrbCharges(); i++) {
						p.attack(c);
						double rand = Math.random();
						if(rand <= 0.05) {
							c.daze(1);
						}
					}
					p.useOrbCharges();
				}else {
					System.out.println();
					System.out.println("Target is out of range!");
					System.out.println();
				}
			}
		}
		System.out.println();
	}
	
	public static void EverestAbility(Player p, Player a, Player b, Player c, Player d, Player e) {
		Scanner input = new Scanner(System.in);
		System.out.println("1: Harmony Orb");
		System.out.println("2: Chaos Orb");
		System.out.print("Which orb do you want to use: ");
		String targetResponse = input.next();
		if(targetResponse.equals("2")) {
			System.out.println();
			System.out.println("1: " + a.getSkin() +". Health: " +  a.getHealth() + "/" + a.getMaxHP());
			System.out.println("2: " + b.getSkin() +". Health: " +  b.getHealth() + "/" + b.getMaxHP());
			System.out.println("3: " + c.getSkin() +". Health: " +  c.getHealth() + "/" + c.getMaxHP());
			System.out.println("Who do you want to give the Chaos Orb to: ");
			String targetResponse2 = input.next();
			if(targetResponse2.equals("1")) {
				a.setChaos(true);
				b.setChaos(false);
				c.setChaos(false);
				System.out.println("\"A shadow hangs over your head.\"");
			}
			if(targetResponse2.equals("2")) {
				a.setChaos(false);
				b.setChaos(true);
				c.setChaos(false);
				System.out.println("\"A shadow hangs over your head.\"");
			}
			if(targetResponse2.equals("3")) {
				a.setChaos(false);
				b.setChaos(false);
				c.setChaos(true);
				System.out.println("\"A shadow hangs over your head.\"");
			}
		}
		if(targetResponse.equals("1")) {
			System.out.println();
			System.out.println("1: " + d.getSkin() +". Health: " +  d.getHealth() + "/" + d.getMaxHP());
			System.out.println("2: " + e.getSkin() +". Health: " +  e.getHealth() + "/" + e.getMaxHP());
			System.out.println("Who do you want to give the Harmony Orb to: ");
			String targetResponse2 = input.next();
			if(targetResponse2.equals("1")) {
				d.setHarmony(true);
				e.setHarmony(false);
				System.out.println("\"Harmony is with you, my friend.\"");
			}
			if(targetResponse2.equals("2")) {
				d.setHarmony(false);
				e.setHarmony(true);
				System.out.println("\"Harmony is with you, my friend.\"");
			}
		}
		System.out.println();
	}
	
	public static void EverestUltimate(Player p) {
		p.sightsee(1, 2);
		p.setDodge(100);
		p.setTranscend(true);
		p.resetUlt();
		System.out.println("\"Experience my full power.\"");
		System.out.println();
	}
	
	public static void ClementineAttack(Player p, Player a) {
		p.attack(a);
		double rand = Math.random();
		if(rand <= 0.1) {
			a.paralyze(1);
		}
		if (p.isBrawler()) {
			p.attack(a);
			double rand2 = Math.random();
			if(rand2 <= 0.1) {
				a.paralyze(1);
			}
		}
		System.out.println();
	}
	
	public static void ClementineAbility(Player p, Player a, Player b, Player c, Player d, Player e) {
		if (p.isBrawler()) {
			System.out.println("Can't use ability in brawler form!");
			System.out.println();
			return;
		}
		double health = 0;
		int reduce = 0;
		if (p.inRange(a)) {
			health = health + 300;
		}
		if (p.inRange(b)) {
			health = health + 300;
		}
		if (p.inRange(c)) {
			health = health + 300;
		}
		if (d.isAlive()) {
			d.increaseHP(health * 0.3);
			reduce++;
		}
		double newHealth = health * 0.3;
		if (e.isAlive()) {
			e.increaseHP(health * 0.3);
			reduce++;
		}
		for(int i = 0; i < reduce; i++) {
			health = health - newHealth;
		}
		p.increaseHP(health);
		p.protect(0.25,1);
		d.protect(0.25,1);
		e.protect(0.25,1);
		p.setCooldown(3);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void ClementineUltimate(Player p, Player a, Player b, Player c, boolean jumped) {
		if (p.isBrawler()) {
			System.out.println("Can't use ultimate in brawler form!");
			System.out.println();
			return;
		}
		if (jumped) {
			int[][] offsets3 = {
				    {-6, 0}, {6, 0}, {0, -6}, {0, 6}, {-6, -6}, {-6, 6}, {2, -6}, {2, 6}, {6, -6}, {6, 6}, {-2, -6}, {-2, 6}, {-6, -2}, {-6, 2}, {6, -2}, {6, 2}, {-6, -4}, {-6, 4}, {-4, -6}, {-4, 6}, {4, -6}, {4, 6}, {6, -4}, {6, -2}, {6, 4}
				};
			for (int[] offset : offsets3) {
			    int x = p.getLoc().getX() + offset[0];
			    int y = p.getLoc().getY() + offset[1];
			    Utility Mine = new Utility("Mine", new Location(x, y), p, p, p, a, b, c);
			    utility.add(Mine);
			}
		}
			// Offsets for the first 2x2 box
	        int[][] offsets1 = {
	            {0, 0}, {-2, 0}, {2, 0}, {0, -2}, {0, 2}, {-2, -2}, {-2, 2}, {2, -2}, {2, 2}
	        };

	        // Offsets for the second 2x2 box
	        int[][] offsets2 = {
	            {-4, 0}, {4, 0}, {0, -4}, {0, 4}, {-4, -4}, {-4, 4}, {2, -4}, {2, 4}, {4, -4}, {4, 4}, {-2, -4}, {-2, 4}, {-4, -2}, {-4, 2}, {4, -2}, {4, 2}
	        };

	        // Place mines for the first 2x2 box
	        for (int[] offset : offsets1) {
	            int x = p.getLoc().getX() + offset[0];
	            int y = p.getLoc().getY()  + offset[1];
	            Utility Mine = new Utility("Mine", new Location(x, y), p, p, p, a, b, c);
	            utility.add(Mine);
	        }

	        // Place mines for the second 2x2 box
	        for (int[] offset : offsets2) {
	            int x = p.getLoc().getX()  + offset[0];
	            int y = p.getLoc().getY()  + offset[1];
	            Utility Mine = new Utility("Mine", new Location(x, y), p, p, p, a, b, c);
	            utility.add(Mine);
	        }
		
		p.resetUlt();
		System.out.println("\"Area, DENIED!\"");
		System.out.println();
	}
	
	public static void DenyAttack(Player p, Player a, Player b, Player c, Location l) {
		if(!p.inRange(l)) {
			System.out.println("Can't throw the Pulse Grenade that far!");
			System.out.println();
			return;
		}
		if(!a.inRange(l, 4) && !b.inRange(l, 4) && !c.inRange(l, 4)) {
			System.out.println("No targets in range!");
			System.out.println();
			return;
		}
		if (a.inRange(l, 4)) {
			p.attack(a);
			p.attack(a);
			a.setFrag(true);
		}
		if (b.inRange(l, 4)) {
			p.attack(b);
			p.attack(b);
			b.setFrag(true);
		}
		if (c.inRange(l, 4)) {
			p.attack(c);
			p.attack(c);
			c.setFrag(true);
		}
		if (a.getLoc().eqLoc(l)) {
			p.attack(a);
		}
		if (b.getLoc().eqLoc(l)) {
			p.attack(b);
		}
		if (c.getLoc().eqLoc(l)) {
			p.attack(c);
		}
		System.out.println();
	}
	
	public static void DenyAbility(Player p, Player a, Player b, Player c, Player d, Player e) {
		Scanner input = new Scanner(System.in);
		String s1 = "Ready for use.";
		String s2 = "Ready for use.";
		String s3 = "Ready for use.";
		if (p.getFlash() > 0) {
			s1 = "On Cooldown (" + p.getFlash() + ").";
		}
		if (p.getBarrier() > 0) {
			s2 = "On Cooldown (" + p.getBarrier() + ").";
		}
		if (p.getSupressBolt() > 0) {
			s3 = "On Cooldown (" + p.getSupressBolt() + ").";
		}
		System.out.println("1: Flashbang. " + s1);
		System.out.println("2: Energy Barrier. " + s2);
		System.out.println("3: Supression Bolt. " + s3);
		System.out.print("Which tool do you want to use: ");
		String targetResponse = input.next();
		if(targetResponse.equals("1")) {
			if (p.getFlash() > 0) {
				System.out.println("Flashbang is on cooldown!");
				System.out.println();
				return;
			}
			Location l = SetCursor(p, a, b, c, d, e, 2);
			if (a.inRange(l, 2)) {
				ArrayList<Effect> e1 = new ArrayList<Effect>();
				Effect AnjelikaBlind = new Effect("blind", 0.25, 1);
				e1.add(AnjelikaBlind);
				a.addEffects(e1);
				a.applyEffects();
			}
			if (b.inRange(l, 2)) {
				ArrayList<Effect> e1 = new ArrayList<Effect>();
				Effect AnjelikaBlind = new Effect("blind", 0.25, 1);
				e1.add(AnjelikaBlind);
				b.addEffects(e1);
				b.applyEffects();
			}
			if (c.inRange(l, 2)) {
				ArrayList<Effect> e1 = new ArrayList<Effect>();
				Effect AnjelikaBlind = new Effect("blind", 0.25, 1);
				e1.add(AnjelikaBlind);
				c.addEffects(e1);
				c.applyEffects();
			}
			System.out.println("\"Flashbang!\"");
			p.setFlash();
		}
		if(targetResponse.equals("2")) {
			if (p.getBarrier() > 0) {
				System.out.println("Energy Barrier is on cooldown!");
				System.out.println();
				return;
			}
			p.setShield();
			d.setShield();
			e.setShield();
			p.setBarrier();
			System.out.println("\"Energy Barrier created.\"");
		}
		if(targetResponse.equals("3")) {
			if (p.getSupressBolt() > 0) {
				System.out.println("Supression Bolt is on cooldown!");
				System.out.println();
				return;
			}
			Location l = SetCursor(p, a, b, c, d, e, 5);
			if (a.inRange(l, 5)) {
				a.setSupress(true);
			}
			if (b.inRange(l, 5)) {
				b.setSupress(true);
			}
			if (c.inRange(l, 5)) {
				c.setSupress(true);
			}
			System.out.println("\"Shutting them down!\"");
			p.setSupressBolt();
		}
		System.out.println();
	}
	
	public static void DenyUltimate(Player p, Player a, Player b, Player c) {
		p.power(0.25, 3);
		p.setUlt();
		p.setEMP();
		a.setSupress(true);
		b.setSupress(true);
		c.setSupress(true);
		System.out.println("\"No one walks away!\"");
		System.out.println();
	}
	
	public static void RinAttack(Player p, Player a, Player b, Player c) {
		p.attack(a);
		double rand = Math.random();
		if(rand <= 0.25) {
			a.ignite(1);
		}
		ArrayList<Location> validTargets = new ArrayList<>();

        // Collect all valid targets within range
        for(int i = 0; i < 42; i++) {
            for(int j = 0; j < 42; j++) {
                Location l = new Location(i, j);
                if(l.inRange(a.getLoc(), 6)) {
                    validTargets.add(l);
                }
            }
        }

        // Randomly select 9 unique targets
        Random rand2 = new Random();
        Set<Location> selectedTargets = new HashSet<>();
        int numArrows = 10;
        if (p.ultActive()) {
        	numArrows = 30;
        }

        while(selectedTargets.size() < numArrows && !validTargets.isEmpty()) {
            int randomIndex = rand2.nextInt(validTargets.size());
            if (!p.ultActive()) {
            	selectedTargets.add(validTargets.remove(randomIndex));
            }else {
            	selectedTargets.add(validTargets.get(randomIndex));
            }
        }
        for (Location l: selectedTargets) {
        	if (b.getLoc().eqLoc(l)) {
        		p.attack(b);
        		double rand3 = Math.random();
        		if(rand3 <= 0.25) {
        			b.ignite(1);
        		}
        	}
        	if (c.getLoc().eqLoc(l)) {
        		p.attack(c);
        		double rand3 = Math.random();
        		if(rand3 <= 0.25) {
        			c.ignite(1);
        		}
        	}
        }
		System.out.println();
	}
	
	public static void RinAbility(Player p, Player a, Player b, Player c) {
		Scanner input = new Scanner(System.in);

		if (a.hasDagger() || b.hasDagger() || c.hasDagger()) {
			int damage = 175;
			if (p.ultActive()) {
				damage = 350;
			}
			if (a.hasDagger()) {
				a.dragIn(p.getLoc(), 7);
				a.takeDamage(damage);
				p.addDamage(damage);
				a.setDagger(false);
				a.resetCover();
			}
			if (b.hasDagger()) {
				b.dragIn(p.getLoc(), 7);
				b.takeDamage(damage);
				p.addDamage(damage);
				b.setDagger(false);
				b.resetCover();
			}
			if (c.hasDagger()) {
				c.dragIn(p.getLoc(), 7);
				c.takeDamage(damage);
				p.addDamage(damage);
				c.setDagger(false);
				c.resetCover();
			}
			p.setCooldown(2);
			System.out.println("\"There's my sweet tooth!\"");
			System.out.println();
			return;
		}
		
		if(!p.inRange(a) && !p.inRange(b) && !p.inRange(c)) {
			System.out.println("No targets in range!");
			System.out.println();
			return;
		}
		String range = "No";
		if(p.inRange(a)) {
			range = "Yes";
		}
		String range2 = "No";
		if(p.inRange(b)) {
			range2 = "Yes";
		}
		String range3 = "No";
		if(p.inRange(c)) {
			range3 = "Yes";
		}
		System.out.println("1: " + a.getSkin() +a.showHP() +  a.getHealth() + "/" + a.getMaxHP() + ". In Range: " + range + ".");
		System.out.println("2: " + b.getSkin() +b.showHP() +  b.getHealth() + "/" + b.getMaxHP() + ". In Range: " + range2 + ".");
		System.out.println("3: " + c.getSkin() +c.showHP() +  c.getHealth() + "/" + c.getMaxHP() + ". In Range: " + range3 + ".");
		System.out.println("Who do you want to throw the Fang Dagger at: ");
		String targetResponse = input.next();
		if(targetResponse.equals("1")) {
			if(p.inRange(a)) {
				a.setDagger(true);
			}else {
				System.out.println();
				System.out.println("Target is out of range!");
				System.out.println();
			}
		}
		if(targetResponse.equals("2")) {
			if(p.inRange(b)) {
				b.setDagger(true);
			}else {
				System.out.println();
				System.out.println("Target is out of range!");
				System.out.println();
			}
		}
		if(targetResponse.equals("3")) {
			if(p.inRange(c)) {
				c.setDagger(true);
			}else {
				System.out.println();
				System.out.println("Target is out of range!");
				System.out.println();
			}
		}
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void RinUltimate(Player p, Player a, Player b) {
		p.setUlt();
		p.power(0.15, 100);
		a.power(0.15, 100);
		b.power(0.15, 100);
		System.out.println("\"Mochi and I, we are unstoppable!\"");
		System.out.println();
	}
	
	public static void VictorAttack(Player p, Player a, Player b, Player c) {
		if (p.isOverheat()) {
			System.out.println("Steam Gatling is recharged for next turn!");
			System.out.println();
			p.setOverheat(false);
			p.setAttacked();
			return;
		}
		if(!p.inRange(a) && !p.inRange(b) && !p.inRange(c)) {
			System.out.println();
			System.out.println("No targets in range!");
			System.out.println();
			return;
		}
		Scanner input = new Scanner(System.in);
		String range = "No";
		if(p.inRange(a)) {
			range = "Yes";
		}
		String range2 = "No";
		if(p.inRange(b)) {
			range2 = "Yes";
		}
		String range3 = "No";
		if(p.inRange(c)) {
			range3 = "Yes";
		}
		Player target = null;
		System.out.println("1: " + a.getSkin() +". Health: " +  a.getHealth() + "/" + a.getMaxHP() + ". In Range: " + range + ". Cover: " + a.getCover());
		System.out.println("2: " + b.getSkin() +". Health: " +  b.getHealth() + "/" + b.getMaxHP() + ". In Range: " + range2 + ". Cover: " + b.getCover());
		System.out.println("3: " + c.getSkin() +". Health: " +  c.getHealth() + "/" + c.getMaxHP() + ". In Range: " + range3 + ". Cover: " + c.getCover());
		System.out.print("Who do you want to attack: ");
		String targetResponse = input.next();
		if(targetResponse.equals("1")) {
			if(p.inRange(a)) {
				target = a;
			}else {
				System.out.println();
				System.out.println("Target is out of range!");
				System.out.println();
			}
		}
		if(targetResponse.equals("2")) {
			if(p.inRange(b)) {
				target = b;
			}else {
				System.out.println();
				System.out.println("Target is out of range!");
				System.out.println();
			}
		}
		if(targetResponse.equals("3")) {
			if(p.inRange(c)) {
				target = c;
			}else {
				System.out.println();
				System.out.println("Target is out of range!");
				System.out.println();
			}
		}
		int number = 0;
		boolean valid = false;
		while (!valid) {
			if (target != null) {
				System.out.print("How many bullets do you want to fire (5 - 10): ");
				if (input.hasNextInt()) {
	                number = input.nextInt();
	                valid = true;
	            } else {
	                System.out.println("Invalid input. Please enter a valid integer.");
	                input.next(); // Clear the invalid input
	            }
			}
		}
		if (target != null && number != 0) {
			for (int i = 0; i < number; i++) {
				p.attack(target);
				if (i > 4) {
					double chance = 0.2;
					if (p.isCogwork()) {
						chance = 0.1;
					}
					if (p.isClockwork()) {
						chance = -1;
					}
					double rand = Math.random();
					if (rand < chance) {
						p.setOverheat(true);
						System.out.println("Steam Gatling is overheated!");
						break;
					}
				}
			}
		}
		if (number == 10 && !p.isClockwork()) {
			System.out.println("Steam Gatling is overheated!");
			p.setOverheat(true);
		}
		System.out.println();
	}
	
	public static void VictorAbility(Player p) {
		p.protect(0.5,2);
		p.refine(2);
		p.fortify(2);
		p.setCogwork(true);
		p.setCooldown(4);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void VictorUltimate(Player p) {
		p.setClockwork(true);
		p.setOverheat(false);
		p.resetUlt();
		System.out.println("\"Gears in motion, enemies in retreat!\"");
		System.out.println();
	}
	
	public static void IsabelleAttack(Player p, Player a, Player b, Player c) {
		p.attack(a);
		p.attack(a);
		System.out.println();
		if (!p.inRange(b) && !p.inRange(c)) {
			System.out.println("No ally in range for burst healing!");
			System.out.println();
			return;
		}
		Scanner input = new Scanner(System.in);
		System.out.println("1: " + b.getSkin() +b.showHP() +  b.getHealth() + "/" + b.getMaxHP() + ".");
		System.out.println("2: " + c.getSkin() +c.showHP() +  c.getHealth() + "/" + c.getMaxHP() + ".");
		System.out.print("Which ally do you want to send the burst healing to: ");
		String targetResponse = input.next();
		if(targetResponse.equals("1")) {
			b.increaseHP(250);
			p.addHealing(250);
			if (p.inRange(b, 4)) {
				p.increaseHP(100);
				p.addHealing(100);
			}
			if (c.inRange(b, 4)) {
				c.increaseHP(100);
				p.addHealing(100);
			}
		}
		if(targetResponse.equals("2")) {
			c.increaseHP(250);
			p.addHealing(250);
			if (p.inRange(c, 4)) {
				p.increaseHP(100);
				p.addHealing(100);
			}
			if (b.inRange(c, 4)) {
				b.increaseHP(100);
				p.addHealing(100);
			}
		}
		System.out.println();
	}
	
	public static void IsabelleAbility(Player p, Player a, Player b, Player c, Player d, Player e, Location l) {
		if(GameSim.b.hasTrench(l.getX(), l.getY())) {
			System.out.println("Can't send the Matrix there!");
			System.out.println();
			return;
		}
		if (!p.inReach(l, 6)) {
			System.out.println("Can't send the Matrix there!");
			System.out.println();
			return;
		}
		Utility Matrix = new Utility("Matrix", l, p, d, e, a, b, c);
		utility.add(Matrix);
		p.setCooldown(3);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void IsabelleUltimate(Player p, Player a, Player b, Player c, Player d, Player e) {
		System.out.println("Where do you want to place " + d.getSkin() + ".");
		Location l1 = SetCursor(p, a, b, c, d, e, 0);
		d.getLoc().set(l1.getX(), l1.getY());
		System.out.println("Where do you want to place " + e.getSkin() + ".");
		Location l2 = SetCursor(p, a, b, c, d, e, 0);
		e.getLoc().set(l2.getX(), l2.getY());
		System.out.println("Where do you want to place " + a.getSkin() + ".");
		Location l3 = SetCursor(p, a, b, c, d, e, 0);
		a.getLoc().set(l3.getX(), l3.getY());
		System.out.println("Where do you want to place " + b.getSkin() + ".");
		Location l4 = SetCursor(p, a, b, c, d, e, 0);
		b.getLoc().set(l4.getX(), l4.getY());
		System.out.println("Where do you want to place " + c.getSkin() + ".");
		Location l5 = SetCursor(p, a, b, c, d, e, 0);
		c.getLoc().set(l5.getX(), l5.getY());
		p.setShield();
		p.increaseMovement(5);
		p.cleanse();
		p.addPermheal();
		d.setShield();
		d.increaseMovement(5);
		d.cleanse();
		d.addPermheal();
		e.setShield();
		e.increaseMovement(5);
		e.cleanse();
		e.addPermheal();
		p.resetUlt();
		System.out.println("\"Quantum shift engaged!\"");
		System.out.println();
	}
	
	public static void LumiereAttack(Player p, Player a) {
		p.attack(a);
		a.blind(0.1, 1);
		System.out.println();
	}
	
	public static void LumiereAbility(Player p, Player a, Player b, Player c, Player d, Player e) {
		Scanner input = new Scanner(System.in);
		System.out.println("1: Left");
		System.out.println("2: Right");
		System.out.println("3: Up");
		System.out.println("4: Down");
		System.out.println("Which direction do you want to send the Sphère in: ");
		String targetResponse = input.next();
		if(targetResponse.equals("1")) {
			System.out.println("1: Bright");
			System.out.println("2: Shadow");
			System.out.println("Which Sphère do you want to make: ");
			String targetResponse2 = input.next();
			if (targetResponse2.equals("1")) {
				Utility Sphere = new Utility("Sphere2", new Location(p.getLoc().getX(), p.getLoc().getY()), p, d, e, a, b, c);
				utility.add(Sphere);
				p.setCooldown(2);
				System.out.println(p.voiceline());
				Sphere.setDirection("left");
			}
			if (targetResponse2.equals("2")) {
				Utility Sphere = new Utility("Sphere2", new Location(p.getLoc().getX(), p.getLoc().getY()), p, d, e, a, b, c);
				utility.add(Sphere);
				p.setCooldown(2);
				System.out.println(p.voiceline());
				Sphere.setDirection("left");
				Sphere.setSphere(false);
			}
		}
		if(targetResponse.equals("2")) {
			System.out.println("1: Bright");
			System.out.println("2: Shadow");
			System.out.println("Which Sphère do you want to make: ");
			String targetResponse2 = input.next();
			if (targetResponse2.equals("1")) {
				Utility Sphere = new Utility("Sphere2", new Location(p.getLoc().getX(), p.getLoc().getY()), p, d, e, a, b, c);
				utility.add(Sphere);
				p.setCooldown(2);
				System.out.println(p.voiceline());
				Sphere.setDirection("right");
			}
			if (targetResponse2.equals("2")) {
				Utility Sphere = new Utility("Sphere2", new Location(p.getLoc().getX(), p.getLoc().getY()), p, d, e, a, b, c);
				utility.add(Sphere);
				p.setCooldown(2);
				System.out.println(p.voiceline());
				Sphere.setDirection("right");
				Sphere.setSphere(false);
			}
		}
		if(targetResponse.equals("3")) {
			System.out.println("1: Bright");
			System.out.println("2: Shadow");
			System.out.println("Which Sphère do you want to make: ");
			String targetResponse2 = input.next();
			if (targetResponse2.equals("1")) {
				Utility Sphere = new Utility("Sphere2", new Location(p.getLoc().getX(), p.getLoc().getY()), p, d, e, a, b, c);
				utility.add(Sphere);
				p.setCooldown(2);
				System.out.println(p.voiceline());
				Sphere.setDirection("up");
			}
			if (targetResponse2.equals("2")) {
				Utility Sphere = new Utility("Sphere2", new Location(p.getLoc().getX(), p.getLoc().getY()), p, d, e, a, b, c);
				utility.add(Sphere);
				p.setCooldown(2);
				System.out.println(p.voiceline());
				Sphere.setDirection("up");
				Sphere.setSphere(false);
			}
		}
		if(targetResponse.equals("4")) {
			System.out.println("1: Bright");
			System.out.println("2: Shadow");
			System.out.println("Which Sphère do you want to make: ");
			String targetResponse2 = input.next();
			if (targetResponse2.equals("1")) {
				Utility Sphere = new Utility("Sphere2", new Location(p.getLoc().getX(), p.getLoc().getY()), p, d, e, a, b, c);
				utility.add(Sphere);
				p.setCooldown(2);
				System.out.println(p.voiceline());
				Sphere.setDirection("down");
			}
			if (targetResponse2.equals("2")) {
				Utility Sphere = new Utility("Sphere2", new Location(p.getLoc().getX(), p.getLoc().getY()), p, d, e, a, b, c);
				utility.add(Sphere);
				p.setCooldown(2);
				System.out.println(p.voiceline());
				Sphere.setDirection("down");
				Sphere.setSphere(false);
			}
		}
		System.out.println();
	}
	
	public static void LumiereUltimate(Player p, Player a, Player b, Player c, Player d, Player e) {
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).getName().equals("Eclipse") && GameSim.utility.get(j).owner(p)) {
				GameSim.utility.remove(j);
			}
		}
		Utility Eclipse = new Utility("Eclipse", new Location(p.getLoc().getX(), p.getLoc().getY()), p, d, e, a, b, c);
		utility.add(Eclipse);
		p.resetUlt();
		System.out.println("\"Witness the dawn and dusk of battle!\"");
		System.out.println();
	}
	
	public static void WillowAttack(Player p, Player a, Player b, Player c) {
		a.resetCover();
		p.attack(a);
		for(int i = 0; i < 5; i++) {
			int randomX = (int)(Math.random() * ((a.getLoc().getX() + 3) - (a.getLoc().getX() - 5) + 1)) + (a.getLoc().getX() - 3);
			int randomY = (int)(Math.random() * ((a.getLoc().getY() + 3) - (a.getLoc().getY() - 5) + 1)) + (a.getLoc().getY() - 3);
			Location l = new Location(randomX, randomY);
			Utility Sensor = new Utility("Butterfly", l, p, p, p, a, b, c);
			utility.add(Sensor);
		}
		a.knockbacked(p.getLoc());
		System.out.println();
	}
	
	public static void WillowAbility(Player p, Player a, Player b) {
		p.cleanse();
		a.cleanse();
		b.cleanse();
		p.refine(1);
		a.refine(1);
		b.refine(1);
		p.setOverhealth(p.getMaxHP() * 0.2);
		a.setOverhealth(p.getMaxHP() * 0.2);
		b.setOverhealth(p.getMaxHP() * 0.2);
		p.addHealing(1200);;
		p.setCooldown(4);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void WillowUltimate(Player p) {
		p.setUlt();
		p.increaseDPSNum(125);
		p.setLife();
		p.increaseMovement(7);
		Utility Sphere = new Utility("Respawn", new Location(p.getLoc().getX(), p.getLoc().getY()), p, p, p, p, p, p);
		utility.add(Sphere);
		System.out.println("\"Enough hiding, let's do this!\"");
		System.out.println();
	}
	
	public static void JazzAttack(Player p, Player a, Player b, Player c) {
		if (a.inRange(b, 4) || a.inRange(c, 4)) {
			p.attack(a);
			p.attack(a);
			double rand = Math.random();
			if(rand <= 0.1) {
				a.daze(1);
			}
			if (a.inRange(b, 4)) {
				p.attack(b);
				double rand2 = Math.random();
				if(rand2 <= 0.1) {
					b.daze(1);
				}
			}
			if (a.inRange(c, 4)) {
				p.attack(c);
				double rand3 = Math.random();
				if(rand3 <= 0.1) {
					c.daze(1);
				}
			}
		}else {
			p.attack(a);
			p.attack(a);
			p.attack(a);
			p.attack(a);
			double rand = Math.random();
			if(rand <= 0.1) {
				a.daze(1);
			}
		}
		System.out.println();
	}
	
	public static void JazzAbility(Player p, Player a, Player b, Player c) {
		Scanner input = new Scanner(System.in);
		if(!p.inRange(a, 15) && !p.inRange(b, 15) && !p.inRange(c, 15)) {
			System.out.println("No targets in range!");
			System.out.println();
			return;
		}
		String range = "No";
		if(p.inRange(a, 15)) {
			range = "Yes";
		}
		String range2 = "No";
		if(p.inRange(b, 15)) {
			range2 = "Yes";
		}
		String range3 = "No";
		if(p.inRange(c, 15)) {
			range3 = "Yes";
		}
		System.out.println("1: " + a.getSkin() +". Health: " +  a.getHealth() + "/" + a.getMaxHP() + ". In Range: " + range + ".");
		System.out.println("2: " + b.getSkin() +". Health: " +  b.getHealth() + "/" + b.getMaxHP() + ". In Range: " + range2 + ".");
		System.out.println("3: " + c.getSkin() +". Health: " +  c.getHealth() + "/" + c.getMaxHP() + ". In Range: " + range3 + ".");
		System.out.println("Where do you want to Potential Jump towards: ");
		String targetResponse = input.next();
		if(targetResponse.equals("1")) {
			if(p.inRange(a, 15)) {
				a.resetCover();
				int d = p.getLoc().distance(a.getLoc());
				p.increaseTotalMovement(d);
				Location l = new Location(p.getLoc().getX(), p.getLoc().getY());
				p.getLoc().set(a.getLoc().getX(), a.getLoc().getY());
				if(b.inRange(a, 4)) {
					b.knockbacked(l);
				}
				if(c.inRange(a, 4)) {
					c.knockbacked(l);
				}
				if(b.inRange(a, 4)) {
					b.knockbacked(l);
				}
				if(c.inRange(a, 4)) {
					c.knockbacked(l);
				}
				p.setCooldown(3);
				System.out.println(p.voiceline());
			}else {
				System.out.println();
				System.out.println("Target is out of range!");
				System.out.println();
			}
		}
		if(targetResponse.equals("2")) {
			if(p.inRange(b, 15)) {
				b.resetCover();
				int d = p.getLoc().distance(b.getLoc());
				p.increaseTotalMovement(d);
				Location l = new Location(p.getLoc().getX(), p.getLoc().getY());
				p.getLoc().set(b.getLoc().getX(), b.getLoc().getY());
				if(a.inRange(b, 4)) {
					a.knockbacked(l);
				}
				if(c.inRange(b, 4)) {
					c.knockbacked(l);
				}
				if(a.inRange(b, 4)) {
					a.knockbacked(l);
				}
				if(c.inRange(b, 4)) {
					c.knockbacked(l);
				}
				p.setCooldown(3);
				System.out.println(p.voiceline());
			}else {
				System.out.println();
				System.out.println("Target is out of range!");
				System.out.println();
			}
		}
		if(targetResponse.equals("3")) {
			if(p.inRange(c, 15)) {
				c.resetCover();
				int d = p.getLoc().distance(c.getLoc());
				p.increaseTotalMovement(d);
				Location l = new Location(p.getLoc().getX(), p.getLoc().getY());
				p.getLoc().set(c.getLoc().getX(), c.getLoc().getY());
				if(a.inRange(c, 4)) {
					a.knockbacked(l);
				}
				if(b.inRange(c, 4)) {
					b.knockbacked(l);
				}
				if(a.inRange(c, 4)) {
					a.knockbacked(l);
				}
				if(b.inRange(c, 4)) {
					b.knockbacked(l);
				}
				p.setCooldown(3);
				System.out.println(p.voiceline());
			}else {
				System.out.println();
				System.out.println("Target is out of range!");
				System.out.println();
			}
		}
		System.out.println();
	}
	
	public static void JazzUltimate(Player p, Player a, Player b, Player c) {
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).getName().equals("Bubble") && GameSim.utility.get(j).owner(p)) {
				GameSim.utility.get(j).setForce();
			}
		}
		Player stunTarget = null;
		Player dazeTarget = null;
		Player paralyzeTarget = null;
		Scanner input = new Scanner(System.in);
		System.out.println("1: " + a.getSkin() +". Health: " +  a.getHealth() + "/" + a.getMaxHP());
		System.out.println("2: " + b.getSkin() +". Health: " +  b.getHealth() + "/" + b.getMaxHP());
		System.out.println("3: " + c.getSkin() +". Health: " +  c.getHealth() + "/" + c.getMaxHP());
		System.out.print("Select a target to be stunned: ");
		String targetResponse = input.next();
		if(targetResponse.equals("1")) {
			stunTarget = a;
		}
		if(targetResponse.equals("2")) {
			stunTarget = b;
		}
		if(targetResponse.equals("3")) {
			stunTarget = c;
		}
		System.out.println();
		System.out.print("Select a different target to be dazed: ");
		String targetResponse2 = input.next();
		if(targetResponse2.equals("1")) {
			dazeTarget = a;
		}
		if(targetResponse2.equals("2")) {
			dazeTarget = b;
		}
		if(targetResponse2.equals("3")) {
			dazeTarget = c;
		}
		System.out.println();
		System.out.print("Select a different target to be paralyzed: ");
		String targetResponse3 = input.next();
		if(targetResponse3.equals("1")) {
			paralyzeTarget = a;
		}
		if(targetResponse3.equals("2")) {
			paralyzeTarget = b;
		}
		if(targetResponse3.equals("3")) {
			paralyzeTarget = c;
		}
		if (stunTarget != null && paralyzeTarget != null && dazeTarget != null) {
			stunTarget.stun(1);
			paralyzeTarget.paralyze(1);
			dazeTarget.daze(1);
			p.setUlt();
			System.out.println("\"I hope they like Jazz!\"");
			System.out.println();
		}
	}
	
	public static void HarperAttack(Player p, Player a) {
		p.attack(a);
		if (p.getLoc().getX() > a.getLoc().getX()) {
			p.getLoc().adjust(2, 0);
		}else if (p.getLoc().getX() < a.getLoc().getX()){
			p.getLoc().adjust(-2, 0);
		}
		if (p.getLoc().getY() > a.getLoc().getY()) {
			p.getLoc().adjust(0, 2);
		}else if (p.getLoc().getY() < a.getLoc().getY()){
			p.getLoc().adjust(0, -2);
		}
		if (p.inRange(a, 3)) {
			a.knockbacked(p.getLoc());
		}
		System.out.println();
	}
	
	public static void HarperAbility(Player p, Player a, Player b, Player c, Location l) {
		if(!p.inRange(l)) {
			System.out.println("Can't throw the dynamite that far!");
			System.out.println();
			return;
		}
		Utility Dynamite = new Utility("Dynamite", l, p, p, p, a, b, c);
		utility.add(Dynamite);
		p.setCooldown(3);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void HarperUltimate(Player p, Player a, Player b, Player c, Player d, Player e, Location l) {
		if(!p.inRange(l)) {
			System.out.println("Can't send Peri that far!");
			System.out.println();
			return;
		}
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).getName().equals("Peri") && GameSim.utility.get(j).owner(p)) {
				GameSim.utility.remove(j);
			}
		}
		Utility Peri = new Utility("Peri", l, p, d, e, a, b, c);
		utility.add(Peri);
		p.resetUlt();
		System.out.println("\"Peri this you filthy casuals!\"");
		System.out.println();
	}
	
	public static void NoahAttack(Player p, Player a) {
		p.attack(a);
		a.addMarker();
		System.out.println();
	}
	
	public static void NoahAbility(Player p, Player a, Player b, Location l) {
		double heal = 0.05;
		if (p.inRange(l)) {
			if (p.inRange(l, 4)) {
				p.heal(heal);
				p.addHealing(p.getMaxHP() * heal);
			}
			if (a.inRange(l, 4)) {
				a.heal(heal);
				p.addHealing(a.getMaxHP() * heal);
			}
			if (b.inRange(l, 4)) {
				b.heal(heal);
				p.addHealing(b.getMaxHP() * heal);

			}
			p.setCooldown(1);
		}else {
			heal = 0.1;
			if (a.inRange(l, 4)) {
				a.heal(heal);
				p.addHealing(a.getMaxHP() * heal);
				a.cleanse();
			}
			if (b.inRange(l, 4)) {
				b.heal(heal);
				p.addHealing(b.getMaxHP() * heal);
				b.cleanse();
			}
			p.setCooldown(2);
		}
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void NoahUltimate(Player p, Player a, Player b, Player c) {
		a.setCorrupt(true);
		b.setCorrupt(true);
		c.setCorrupt(true);
		p.resetUlt();
		System.out.println("\"Your friendships mean nothing!\"");
		System.out.println();
	}
	
	public static void JadeAttack(Player p, Player a) {
		p.attack(a);
		a.addJavelin();
		a.knockbacked(p.getLoc());
		if (a.isParalyzed()) {
			a.knockbacked(p.getLoc());
		}
		System.out.println();
	}
	
	public static void JadeAbility(Player p) {
		p.setSpin(true);
		p.increaseMovement(8);
		p.setCooldown(2);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void JadeUltimate(Player p, Player a, Player b, Player c, Player d, Player e, Location l) {
		if (!p.inRange(l, 20)) {
			System.out.println("Can't throw the Javelin that far!");
			System.out.println();
			return;
		}
		int damage = 300;
		if (!a.isAlive() && !b.isAlive()) {
			damage = 600;
		}
		if (a.inRange(l, 4)) {
			a.takeDamage(damage);
			a.resetCover();
			p.addDamage(damage);
		}
		if (b.inRange(l, 4)) {
			b.takeDamage(damage);
			b.resetCover();
			p.addDamage(damage);
		}
		if (c.inRange(l, 4)) {
			c.takeDamage(damage);
			c.resetCover();
			p.addDamage(damage);
		}
		if (!a.isAlive() && !b.isAlive()) {
			p.resetUlt();
			System.out.println("\"Eat this Javelin!\"");
			System.out.println();
			return;
		}
		System.out.println();
		Scanner input = new Scanner(System.in);
		System.out.println("1: " + d.getSkin() +a.showHP() +  d.getHealth() + "/" + d.getMaxHP() + ".");
		System.out.println("2: " + e.getSkin() +b.showHP() +  e.getHealth() + "/" + e.getMaxHP() + ".");
		System.out.print("Who do you want to send with the Javelin: ");
		String targetResponse = input.next();
		if(targetResponse.equals("1")) {
			d.getLoc().set(l.getX(), l.getY());
			p.resetUlt();
			System.out.println("\"Go screw them up!\"");
			System.out.println();
		}
		if(targetResponse.equals("2")) {
			e.getLoc().set(l.getX(), l.getY());
			p.resetUlt();
			System.out.println("\"Go screw them up!\"");
			System.out.println();
		}
		System.out.println();
	}
	
	public static void StellarAttack(Player p, Player a, Player b, Player c) {
		boolean aRange = false;
		boolean bRange = false;
		boolean cRange = false;
		if (a.getLoc().getX() == p.getLoc().getX() || a.getLoc().getX() == p.getLoc().getX() - 1 || a.getLoc().getX() == p.getLoc().getX() + 1 || a.getLoc().getY() == p.getLoc().getY() || a.getLoc().getY() == p.getLoc().getY() - 1 || a.getLoc().getY() == p.getLoc().getY() + 1) {
			aRange = true;
		}
		if (b.getLoc().getX() == p.getLoc().getX() || b.getLoc().getX() == p.getLoc().getX() - 1 || b.getLoc().getX() == p.getLoc().getX() + 1 || b.getLoc().getY() == p.getLoc().getY() || b.getLoc().getY() == p.getLoc().getY() - 1 || b.getLoc().getY() == p.getLoc().getY() + 1) {
			bRange = true;
		}
		if (c.getLoc().getX() == p.getLoc().getX() || c.getLoc().getX() == p.getLoc().getX() - 1 || c.getLoc().getX() == p.getLoc().getX() + 1 || c.getLoc().getY() == p.getLoc().getY() || c.getLoc().getY() == p.getLoc().getY() - 1 || c.getLoc().getY() == p.getLoc().getY() + 1) {
			cRange = true;
		}
		if (!aRange && !bRange && !cRange) {
			System.out.println("No enemies in range!");
			System.out.println();
			return;
		}
		if (aRange) {
			p.attack(a);
			a.dragIn(p.getLoc(), 4);
			double rand = Math.random();
			if (rand < 0.1) {
				a.daze(1);
			}
			p.addAura();
		}
		if (bRange) {
			p.attack(b);
			b.dragIn(p.getLoc(), 4);
			double rand = Math.random();
			if (rand < 0.1) {
				b.daze(1);
			}
			p.addAura();
		}
		if (cRange) {
			p.attack(c);
			c.dragIn(p.getLoc(), 4);
			double rand = Math.random();
			if (rand < 0.1) {
				c.daze(1);
			}
			p.addAura();
		}
		System.out.println();
	}
	
	public static void StellarAbility(Player p, Player a, Player b, Player c, Player d, Player e, boolean check) {
		if (check) {
			p.setShield();
			d.setShield();
			e.setShield();
			a.takeDamage(a.getMaxHP() * 0.25);
			p.addDamage(a.getMaxHP() * 0.25);
			p.setCooldown(3);
			p.addAura();
			p.addAura();
			p.addAura();
			System.out.println("\"Now alone, this is true pain!\"");
			System.out.println();
		}else {
			p.setShield();
			d.setShield();
			e.setShield();
			a.takeDamage(a.getMaxHP() * 0.05);
			p.addDamage(a.getMaxHP() * 0.05);
			b.takeDamage(b.getMaxHP() * 0.05);
			p.addDamage(b.getMaxHP() * 0.05);
			c.takeDamage(c.getMaxHP() * 0.05);
			p.addDamage(c.getMaxHP() * 0.05);
			p.setCooldown(3);
			p.addAura();
			p.addAura();
			p.addAura();
			System.out.println(p.voiceline());
			System.out.println();
		}
	}
	
	public static void StellarUltimate(Player p, Player a, Player b, Player c, Player d, Player e) {
		Scanner input = new Scanner(System.in);
		System.out.println("1: " + a.getName() +a.showHP() +  a.getHealth() + "/" + a.getMaxHP());
		System.out.println("2: " + b.getName() +b.showHP() +  b.getHealth() + "/" + b.getMaxHP());
		System.out.println("3: " + c.getName() +c.showHP() +  c.getHealth() + "/" + c.getMaxHP());
		System.out.print("Who do you want to bring into the Polaris Clash: ");
		String targetResponse = input.next();
		System.out.println();
		System.out.println("\"You will fall before me!\"");
		System.out.println();
		if(targetResponse.equals("1")) {
			PolarisClash(p, a, b, c, d, e);
			p.resetUlt();
		}
		if(targetResponse.equals("2")) {
			PolarisClash(p, b, a, c, d, e);
			p.resetUlt();
		}
		if(targetResponse.equals("3")) {
			PolarisClash(p, c, b, a, d, e);
			p.resetUlt();
		}
	}
	
	public static void BonbonAttack(Player p, Player a, Player b, Player c) {
		p.attack(a);
		if (a.isDazed()) {
			a.takeDamage(100);
			p.addDamage(100);
		}
		double rand = Math.random();
		if (rand < 0.1) {
			a.paralyze(1);
		}
		p.increaseMovement(1);
		b.increaseMovement(1);
		c.increaseMovement(1);
		p.heal(0.02);
		b.heal(0.02);
		c.heal(0.02);
		p.addHealing(p.getMaxHP() * 0.02);
		p.addHealing(b.getMaxHP() * 0.02);
		p.addHealing(c.getMaxHP() * 0.02);
		System.out.println();
	}
	
	public static void BonbonAbility(Player p, Player a, Player b, Player c) {
		Scanner input = new Scanner(System.in);
		if(!p.inRange(a, 20) && !p.inRange(b, 20) && !p.inRange(c, 20)) {
			System.out.println("No targets in range!");
			System.out.println();
			return;
		}
		String range = "No";
		if(p.inRange(a, 20)) {
			range = "Yes";
		}
		String range2 = "No";
		if(p.inRange(b, 20)) {
			range2 = "Yes";
		}
		String range3 = "No";
		if(p.inRange(c, 20)) {
			range3 = "Yes";
		}
		System.out.println("1: " + a.getSkin() +". Health: " +  a.getHealth() + "/" + a.getMaxHP() + ". In Range: " + range + ".");
		System.out.println("2: " + b.getSkin() +". Health: " +  b.getHealth() + "/" + b.getMaxHP() + ". In Range: " + range2 + ".");
		System.out.println("3: " + c.getSkin() +". Health: " +  c.getHealth() + "/" + c.getMaxHP() + ". In Range: " + range3 + ".");
		System.out.println("Who do you want to sling bubblegum at: ");
		String targetResponse = input.next();
		if(targetResponse.equals("1")) {
			if(p.inRange(a, 20)) {
				Utility Gum = new Utility("Gum", a.getLoc(), p, p, p, a, b, c);
				utility.add(Gum);
				Gum.activateGum(a);
				a.daze(1);
				p.setCooldown(3);
			}else {
				System.out.println();
				System.out.println("Target is out of range!");
				System.out.println();
			}
		}
		if(targetResponse.equals("2")) {
			if(p.inRange(b, 20)) {
				Utility Gum = new Utility("Gum", a.getLoc(), p, p, p, a, b, c);
				utility.add(Gum);
				Gum.activateGum(b);
				b.daze(1);
				p.setCooldown(3);
			}else {
				System.out.println();
				System.out.println("Target is out of range!");
				System.out.println();
			}
		}
		if(targetResponse.equals("3")) {
			if(p.inRange(c, 20)) {
				Utility Gum = new Utility("Gum", a.getLoc(), p, p, p, a, b, c);
				utility.add(Gum);
				Gum.activateGum(c);
				c.daze(1);
				p.setCooldown(3);
			}else {
				System.out.println();
				System.out.println("Target is out of range!");
				System.out.println();
			}
		}
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void BonbonUltimate(Player p, Player a, Player b, Player c, String d) {
		boolean z = false;
		ArrayList<Player> list = new ArrayList<Player>();
		switch (d) {
		case "left":
			for (int i = p.getLoc().getX(); i > -1; i--) {
				if ((a.getLoc().getX() == i && a.getLoc().getY() == p.getLoc().getY()) || (b.getLoc().getX() == i && b.getLoc().getY() == p.getLoc().getY()) || (c.getLoc().getX() == i && c.getLoc().getY() == p.getLoc().getY())) {
					z = true;
					Location l = new Location(i, p.getLoc().getY());
					if (a.getLoc().eqLoc(l)) {
						list.add(a);
					}
					if (b.getLoc().eqLoc(l)) {
						list.add(b);
					}
					if (c.getLoc().eqLoc(l)) {
						list.add(c);
					}
				}
			}
			break;
		case "right":
			for (int i = p.getLoc().getX(); i < 42; i++) {
				if ((a.getLoc().getX() == i && a.getLoc().getY() == p.getLoc().getY()) || (b.getLoc().getX() == i && b.getLoc().getY() == p.getLoc().getY()) || (c.getLoc().getX() == i && c.getLoc().getY() == p.getLoc().getY())) {
					z = true;
					Location l = new Location(i, p.getLoc().getY());
					if (a.getLoc().eqLoc(l)) {
						list.add(a);
					}
					if (b.getLoc().eqLoc(l)) {
						list.add(b);
					}
					if (c.getLoc().eqLoc(l)) {
						list.add(c);
					}
				}
			}
			break;
		case "up":
			for (int i = p.getLoc().getY(); i > -1; i--) {
				if ((a.getLoc().getY() == i && a.getLoc().getX() == p.getLoc().getX()) || (b.getLoc().getY() == i && b.getLoc().getX() == p.getLoc().getX()) || (c.getLoc().getY() == i && c.getLoc().getX() == p.getLoc().getX())) {
					z = true;
					Location l = new Location(p.getLoc().getX(), i);
					if (a.getLoc().eqLoc(l)) {
						list.add(a);
					}
					if (b.getLoc().eqLoc(l)) {
						list.add(b);
					}
					if (c.getLoc().eqLoc(l)) {
						list.add(c);
					}
				}
			}
			break;
		case "down":
			for (int i = p.getLoc().getY(); i < 42; i++) {
				if ((a.getLoc().getY() == i && a.getLoc().getX() == p.getLoc().getX()) || (b.getLoc().getY() == i && b.getLoc().getX() == p.getLoc().getX()) || (c.getLoc().getY() == i && c.getLoc().getX() == p.getLoc().getX())) {
					z = true;
					Location l = new Location(p.getLoc().getX(), i);
					if (a.getLoc().eqLoc(l)) {
						list.add(a);
					}
					if (b.getLoc().eqLoc(l)) {
						list.add(b);
					}
					if (c.getLoc().eqLoc(l)) {
						list.add(c);
					}
				}
			}
			break;
		}
		if (!z) {
			System.out.println("No enemies in the path for the JawBreaker!");
			System.out.println();
			return;
		}
		int stun = 1;
		if (list.size() > 1) {
			Player minHealthPlayer = Collections.min(list, Comparator.comparing(Player::getHealth)); // Remove all players except the one with the smallest health 
			list.removeIf(player -> player != minHealthPlayer);
		}
		if (!list.contains(a)) {
			if (a.inRange(list.get(0), 6)) {
				a.takeDamage(225);
				p.addDamage(225);
				a.weary(1);
			}
		}
		if (!list.contains(b)) {
			if (b.inRange(list.get(0), 6)) {
				b.takeDamage(225);
				p.addDamage(225);
				b.weary(1);
			}
		}
		if (!list.contains(c)) {
			if (c.inRange(list.get(0), 6)) {
				c.takeDamage(225);
				p.addDamage(225);
				c.weary(1);
			}
		}
		if (p.getLoc().distance(list.get(0).getLoc()) >= 15) {
			stun = 2;
		}
		list.get(0).takeDamage(450);
		p.addDamage(450);
		list.get(0).stun(stun);
		System.out.println("\"Sweet destruction, straight ahead!\"");
		System.out.println();
		ultimateFX();
	}
	
	public static void FinleyBossAttack(Player p, Player a, Player b, Player c) {
		p.attack(a);
		a.weary(1);
		a.vulnerable(0.15, 1);
		if (a.inRange(b, 6)) {
			p.attack(b);
		}
		if (a.inRange(c, 6)) {
			p.attack(c);
		}
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).owner(p) && GameSim.utility.get(j).getName().equals("Finley")) {
				p.attack(a);
				a.weary(1);
				a.vulnerable(0.15, 1);
				if (a.inRange(b, 6)) {
					p.attack(b);
				}
				if (a.inRange(c, 6)) {
					p.attack(c);
				}
			}
		}
		System.out.println();
	}
	
	public static void FinleyBossAbility(Player p, Player a, Player b, Player c) {
		a.knockbacked(p.getLoc());
		a.ignite(2);
		b.knockbacked(p.getLoc());
		b.ignite(2);
		c.knockbacked(p.getLoc());
		c.ignite(2);
		for(int j = 0; j < GameSim.utility.size(); j++) {
			if(GameSim.utility.get(j).owner(p) && GameSim.utility.get(j).getName().equals("Finley")) {
				a.knockbacked(GameSim.utility.get(j).getLoc());
				a.ignite(2);
				b.knockbacked(GameSim.utility.get(j).getLoc());
				b.ignite(2);
				c.knockbacked(GameSim.utility.get(j).getLoc());
				c.ignite(2);
			}
		}
		p.setCooldown(3);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void FinleyBossUltimate(Player p, Player a, Player b, Player c, Location l) {
		if (!p.inRange(l, 6)) {
			System.out.println("Can't create a clone that far!");
			System.out.println();
			return;
		}
		Utility Finley = new Utility("Finley", l, p, p, p, a, b, c);
		utility.add(Finley);
		p.resetUlt();
		System.out.println("\"If you can't handle one of me, how can you expect to take on another of me!\"");
		System.out.println();
	}
}
