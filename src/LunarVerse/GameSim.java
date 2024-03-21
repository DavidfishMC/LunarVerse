package LunarVerse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class GameSim {
	
	static ArrayList<Orb> orbs = new ArrayList<Orb>();
	static ArrayList<Cover> cover = new ArrayList<Cover>();
	int xBound;
	int yBound;
	static Music audioPlayer;
	static Example image;
	static int turns2 = 0;
	static final String reset = "\u001B[0m";
	static final String color = "\u001b[38;5;";
	static final String bold = "\u001b[1m";
	public static Battlefield b;

	public static void main(String[] args) {
		/*
		try {
			image = new Example();
		} catch (IOException e) {
		}
		*/
		try {
			String audio = "happyedit.wav";
			audioPlayer = new Music(audio, false);
			audioPlayer.play();
			audioPlayer.pause();
		}catch (Exception e) {
			System.out.println(e);
		}
		boolean play = true;
		boolean play2 = true;
		boolean play3 = true;
		boolean play4 = true;
		String tD;
		int turns = 0;
		//HP, Damage, Turn, Name, X, Y, Range, Movement, Ult
		Player p1 = new Player(2650, 175, true, "Clara", 40, 40, 30, 100, 0);
		Player p3 = new Player(2900, 325, false, "Cherry", 0, 3, 10, 100, 0);
		Player p5 = new Player(4850, 575, false, "Rocco", 3, 0, 6, 500, 0);
		
		Player p2 = new Player(10, 225, false, "Finley", 40, 40, 9, 100, 0);
		Player p4 = new Player(10, 200, false, "Louis", 40, 37, 10, 100, 0);
		Player p6 = new Player(10000, 200, false, "Solar", 37, 40, 10, 100, 0);
		boolean game = false;
		Scanner input = new Scanner(System.in);
		System.out.println("\u001b[38;5;" + 87 + "m" + "\033[3mLunarVerse\033[0m");
		System.out.println("Created by Davidfish. Inspired by the Mario and Rabbids games and the V.C., R.C., and D.C. trilogies.");
		System.out.println("Music taken from the Mario and Rabbids games.");
		System.out.print("Enter any key to start: ");
		String temp = input.next();
		System.out.println();
		if(temp.equals("a")) {
			System.out.println("Team A, pick your characters.");
			System.out.print("Character Selection 1: ");
			String temp2 = input.next();
			p1 = CharacterSelection(p1, temp2, true, 5, 5);
			//p1.image().open();
			System.out.print("Character Selection 2: ");
			String temp3 = input.next();
			p3 = CharacterSelection(p3, temp3, false, 5, 8);
			//p1.image().close();
			System.out.print("Character Selection 3: ");
			String temp4 = input.next();
			p5 = CharacterSelection(p5, temp4, false, 8, 5);
			System.out.println();
			System.out.println("Team B, pick your characters.");
			System.out.print("Character Selection 1: ");
			String temp5 = input.next();
			p2 = CharacterSelection(p2, temp5, true, 36, 36);
			System.out.print("Character Selection 2: ");
			String temp6 = input.next();
			p4 = CharacterSelection(p4, temp6, false, 36, 33);
			System.out.print("Character Selection 3: ");
			String temp7 = input.next();
			p6 = CharacterSelection(p6, temp7, false, 33, 36);
		}else {
			p1 = new Player(1000, 175, true, "Mayhem", 40, 40, 10, 100, 0);
			p3 = new Player(0, 325, false, "Midnite", 40, 40, 10, 100, 1);
			p5 = new Player(0, 575, false, "Cherry", 40, 40, 6, 500, 1);
			
			p2 = new Player(10000, 225, false, "Bolo", 37, 37, 9, 100, 1);
			p4 = new Player(10000, 100, false, "Rocco", 37, 37, 10, 100, 1);
			p6 = new Player(10000, 200, false, "Kailani", 40, 40, 10, 100, 1);

		}
		b = new Battlefield("Merge Castle", p1, p3, p5, p2, p4, p6);
		Party party1 = new Party(true, p1, p3, p5);
		Party party2 = new Party(false, p2, p4, p6);
		System.out.println();
		game = true;
		audioPlayer.play();
		while(game) {
			if(turns % 2 == 0) {
				turns2++;
			}
			if(turns2 >= 15) {
				tD = bold + color + 88 + "m" + String.valueOf(turns2) + reset;
			}else if(turns2 >= 8) {
				tD = bold + color + 196 + "m" + String.valueOf(turns2) + reset;
			}else if (turns2 >= 4) {
				tD = bold +  color + 214 + "m" + String.valueOf(turns2) + reset;
			}else {
				tD = bold +  color + 46 + "m" + String.valueOf(turns2) + reset;
			}
			while(party1.getTurn()) {
				if(party1.teamDown()) {
					break;
				}
				if(party1.oneLeft() && play) {
					try {
						audioPlayer.stop();
						String audio = "dramaticedit.wav";
						audioPlayer = new Music(audio, false);
						audioPlayer.play();
						String audio2 = "lastplayer.wav";
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
				}
				while(p1.getTurn() && !p1.isStunned()) {
					//p1.image().open();
					System.out.println("Turn: " + tD);
					System.out.println("Team A's Turn (Go " + p1.getSkin() + "!)");
					System.out.println(p1);
					System.out.print("What would " + p1.getName() + " like to do: ");
					String response = input.next();
					System.out.println();
					if(response.equals("b")){
						b.printField(p1, p2, p3, p4, p5, p6, orbs, cover, p1, p2, p4, p6);
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
							//p1.image().close();
							System.out.println();
						}
						if(switchResponse.equals("3")) {
							p1.passTurn(p5);
							//p1.image().close();
							System.out.println();
						}
					}
					if(response.equals("p")) {
						System.out.print("Are you sure you want to pass to the enemy's turn: ");
						String switchResponse = input.next();
						if(switchResponse.equals("p")) {
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
					if(response.equals("i")) {
						CheckProfile(p1, party2);
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
					if(response.equals("d")) {
						Dash(p1, p2, p4, p6);
					}
					if(response.equals("j")) {
						Location l = SetCursor(p1, p2, p4, p6, p3, p5);
						Jump(p1, p3, p5, l);
					}
					if(response.equals("u")) {
						if(!p1.ultReady() || p1.ultActive()) {
							System.out.println("My ultimate cannot be used!");
							System.out.println();
						}else {
							if(p1.getName().equals("Chief")) {
								ChiefUltimate(p1, p2, p4, p6, p3, p5);
							}
							if(p1.getName().equals("Jesse")) {
								JesseUltimate(p1);
							}
							if(p1.getName().equals("Norman")) {
								NormanUltimate(p1, p3, p5);
							}
							if(p1.getName().equals("Ruby")) {
								RubyUltimate(p1, p3, p5);
							}
							if(p1.getName().equals("Augie")) {
								AugieUltimate(p1, p2, p4 ,p6);
							}
							if(p1.getName().equals("Oona")) {
								OonaUltimate(p1, p3, p5);
							}
							if(p1.getName().equals("Radar")) {
								RadarUltimate(p1, p2, p4, p6, p3, p5);
							}
							if(p1.getName().equals("Redgar")) {
								RedgarUltimate(p1);
							}
							if(p1.getName().equals("Hopper")) {
								HopperUltimate(p1, p3, p5);
							}
							if(p1.getName().equals("Chloe")) {
								ChloeUltimate(p1, p3, p5);
							}
							if(p1.getName().equals("Ayson")) {
								AysonUltimate(p1, p2, p4 ,p6);
							}
							if(p1.getName().equals("Audrey")) {
								AudreyUltimate(p1, p3, p5);
							}
							if(p1.getName().equals("Mayhem")) {
								MayhemUltimate(p1);
							}
							if(p1.getName().equals("Gash")) {
								GashUltimate(p1, p2, p4, p6);
							}
							if(p1.getName().equals("Julian")) {
								JulianUltimate(p1, p2, p4, p6, p3, p5);
							}
							if(p1.getName().equals("Airic")) {
								AiricUltimate(p1, p2, p4, p6);
							}
							if(p1.getName().equals("Mason")) {
								MasonUltimate(p1, p2, p4, p6, p3, p5);
							}
							if(p1.getName().equals("Evil")) {
								EvilUltimate(p1, p2, p4, p6);
							}
							if(p1.getName().equals("Grizz")) {
								GrizzUltimate(p1);
							}
							if(p1.getName().equals("Dimentio")) {
								DimentioUltimate(p1);
							}
							if(p1.getName().equals("Tom")) {
								Location l = SetCursor(p1, p2, p4, p6, p3, p5);
								TomUltimate(p1, p2, p4, p6, l);
							}
							if(p1.getName().equals("Archer")) {
								ArcherUltimate(p1, p2, p4, p6);
							}
							if(p1.getName().equals("Anjelika")) {
								AnjelikaUltimate(p1);
							}
							if(p1.getName().equals("Kithara")) {
								KitharaUltimate(p1, p2, p4, p6);
							}
							if(p1.getName().equals("Xara")) {
								XaraUltimate(p1, p2, p4, p6);
							}
							if(p1.getName().equals("Midnite")) {
								MidniteUltimate(p1);
							}
							if(p1.getName().equals("Katrina")) {
								KatrinaUltimate(p1, p3, p5);
							}
							if(p1.getName().equals("Axol")) {
								AxolUltimate(p1, p3, p5);
							}
							if(p1.getName().equals("Liam")) {
								LiamUltimate(p1, p2, p4, p6, p3, p5);
							}
							if(p1.getName().equals("Aidan")) {
								AidanUltimate(p1, p2, p4, p6, p3, p5);
							}
							if(p1.getName().equals("Thunder")) {
								ThunderUltimate(p1, p2, p4, p6);
							}
							if(p1.getName().equals("Clara")) {
								ClaraUltimate(p1);
							}
							if(p1.getName().equals("Sammi")) {
								SammiUltimate(p1);
							}
							if(p1.getName().equals("Rocco")) {
								RoccoUltimate(p1, p2, p4, p6);
							}
							if(p1.getName().equals("Bedrock")) {
								BedrockUltimate(p1);
							}
							if(p1.getName().equals("Ashley")) {
								AshleyUltimate(p1, p2, p4, p6, p3, p5);
							}
							if(p1.getName().equals("Kailani")) {
								KailaniUltimate(p1, p2, p4, p6);
							}
							if(p1.getName().equals("Orion")) {
								OrionUltimate(p1, p3, p5);
							}
							if(p1.getName().equals("Alex")) {
								AlexUltimate(p1);
							}
							if(p1.getName().equals("Louis")) {
								Location l = SetCursor(p1, p2, p4, p6, p3, p5);
								LouisUltimate(p1, p2, p4, p6, l);
							}
							if(p1.getName().equals("Via")) {
								ViaUltimate(p1, p2, p4 ,p6);
							}
							if(p1.getName().equals("Max")) {
								MaxUltimate(p1, p2, p4 ,p6);
							}
							if(p1.getName().equals("Cherry")) {
								CherryUltimate(p1);
							}
							if(p1.getName().equals("Bolo")) {
								BoloUltimate(p1, p2, p4 ,p6);
							}
							if(p1.getName().equals("Mack")) {
								MackUltimate(p1);
							}
							if(p1.getName().equals("Finley")) {
								FinleyUltimate(p1, p2, p4, p6);
							}
							if(p1.getName().equals("Zero")) {
								ZeroUltimate(p1);
							}
							if(p1.getName().equals("Burt")) {
								BurtUltimate(p1, p2, p4, p6);
							}
							if(p1.getName().equals("Eli")) {
								EliUltimate(p1, p3, p5);
							}
							if(p1.getName().equals("Solar")) {
								SolarUltimate(p1, p2, p4, p6);
							}
							if(p1.getName().equals("Dylan")) {
								DylanUltimate(p1, p2, p4, p6, p3, p5);
							}
							if(p1.getName().equals("Gates")) {
								GatesUltimate(p1);
							}
							if(p1.getName().equals("Lunar")) {
								LunarUltimate(p1, p2, p4, p6);
							}
						}
					}
					if(response.equals("a") && !p1.onCooldown()) {
						if(p1.isDazed()) {
							System.out.println("Cannot use ability when dazed!");
							System.out.println();
						}else {
							if(p1.getName().equals("Lunar")) {
								LunarAbility(p1);
							}
							if(p1.getName().equals("Solar")) {
								SolarAbility(p1);
							}
							if(p1.getName().equals("Mack")) {
								Location l = SetCursor(p1, p2, p4, p6, p3, p5);
								MackAbility(p1, p2, p4, p6, l);
							}
							if(p1.getName().equals("Cherry")) {
								CherryAbility(p1, p2, p4, p6);
							}
							if(p1.getName().equals("Finley")) {
								FinleyAbility(p1, p2, p4, p6);
							}
							if(p1.getName().equals("Burt")) {
								BurtAbility(p1);
							}
							if(p1.getName().equals("Bolo")) {
								BoloAbility(p1);
							}
							if(p1.getName().equals("Dylan")) {
								DylanAbility(p1, p2, p4 ,p6);
							}
							if(p1.getName().equals("Zero")) {
								ZeroAbility(p1, p3, p5);
							}
							if(p1.getName().equals("Max")) {
								MaxAbility(p1, p3, p5);
							}
							if(p1.getName().equals("Eli")) {
								EliAbility(p1, p3, p5);
							}
							if(p1.getName().equals("Via")) {
								Location l = SetCursor(p1, p2, p4, p6, p3, p5);
								ViaAbility(p1, p2, p4, p6, l);
							}
							if(p1.getName().equals("Louis")) {
								LouisAbility(p1, p2, p4 ,p6);
							}
							if(p1.getName().equals("Alex")) {
								AlexAbility(p1, p3, p5);
							}
							if(p1.getName().equals("Orion")) {
								OrionAbility(p1, p3, p5);
							}
							if(p1.getName().equals("Kailani")) {
								Location l = SetCursor(p1, p2, p4, p6, p3, p5);
								KailaniAbility(p1, p2, p4, p6, l);
							}
							if(p1.getName().equals("Ashley")) {
								AshleyAbility(p1, p2, p4, p6, p3, p5);
							}
							if(p1.getName().equals("Bedrock")) {
								BedrockAbility(p1);
							}
							if(p1.getName().equals("Rocco")) {
								RoccoAbility(p1, p2, p4 ,p6);
							}
							if(p1.getName().equals("Sammi")) {
								SammiAbility(p1);
							}
							if(p1.getName().equals("Clara")) {
								ClaraAbility(p1);
							}
							if(p1.getName().equals("Thunder")) {
								ThunderAbility(p1);
							}
							if(p1.getName().equals("Aidan")) {
								AidanAbility(p1, p3, p5);
							}
							if(p1.getName().equals("Liam")) {
								LiamAbility(p1, p3, p5);
							}
							if(p1.getName().equals("Axol")) {
								AxolAbility(p1, p3, p5);
							}
							if(p1.getName().equals("Katrina")) {
								KatrinaAbility(p1, p3, p5);
							}
							if(p1.getName().equals("Midnite")) {
								MidniteAbility(p1, p2, p4, p6, p3, p5);
							}
							if(p1.getName().equals("Xara")) {
								XaraAbility(p1, p3, p5);
							}
							if(p1.getName().equals("Kithara")) {
								KitharaAbility(p1, p3, p5);
							}
							if(p1.getName().equals("Anjelika")) {
								AnjelikaAbility(p1, p2, p4 ,p6);
							}
							if(p1.getName().equals("Archer")) {
								ArcherAbility(p1, p3, p5);
							}
							if(p1.getName().equals("Tom")) {
								TomAbility(p1);
							}
							if(p1.getName().equals("Dimentio")) {
								DimentioAbility(p1, p2, p4 ,p6);
							}
							if(p1.getName().equals("Grizz")) {
								GrizzAbility(p1, p3, p5);
							}
							if(p1.getName().equals("Evil")) {
								EvilAbility(p1);
							}
							if(p1.getName().equals("Mason")) {
								Location l = SetCursor(p1, p2, p4, p6, p3, p5);
								MasonAbility(p1, p2, p4, p6, l);
							}
							if(p1.getName().equals("Airic")) {
								Location l = SetCursor(p1, p2, p4, p6, p3, p5);
								AiricAbility(p1, p3, p5, l);
							}
							if(p1.getName().equals("Julian")) {
								JulianAbility(p1);
							}
							if(p1.getName().equals("Gash")) {
								GashAbility(p1, p3, p5);
							}
							if(p1.getName().equals("Mayhem")) {
								MayhemAbility(p1, p2, p4 ,p6);
							}
							if(p1.getName().equals("Gates")) {
								GatesAbility(p1, p3, p5);
							}
							if(p1.getName().equals("Audrey")) {
								AudreyAbility(p1, p2, p4 ,p6);
							}
							if(p1.getName().equals("Ayson")) {
								AysonAbility(p1, p2, p4, p6, p3, p5);
							}
							if(p1.getName().equals("Chloe")) {
								ChloeAbility(p1, p3, p5);
							}
							if(p1.getName().equals("Hopper")) {
								HopperAbility(p1, p3, p5);
							}
							if(p1.getName().equals("Redgar")) {
								RedgarAbility(p1, p3, p5);
							}
							if(p1.getName().equals("Radar")) {
								RadarAbility(p1, p2, p4 ,p6);
							}
							if(p1.getName().equals("Oona")) {
								OonaAbility(p1, p2, p4 ,p6);
							}
							if(p1.getName().equals("Augie")) {
								AugieAbility(p1, p3, p5);
							}
							if(p1.getName().equals("Ruby")) {
								Location l = SetCursor(p1, p2, p4, p6, p3, p5);
								RubyAbility(p1, l);
							}
							if(p1.getName().equals("Norman")) {
								NormanAbility(p1, p2, p4 ,p6);
							}
							if(p1.getName().equals("Jesse")) {
								JesseAbility(p1);
							}
							if(p1.getName().equals("Chief")) {
								ChiefAbility(p1, p3, p5);
							}
						}
					}
					if(response.equals("w")) {
						if(p1.isFreezed()) {
							System.out.println("Cannot use weapon while freezed!");
							System.out.println();
						}else {
							if(p1.hasAttacked()) {
								System.out.println(p1.getName() + " has already attacked this turn!");
								System.out.println();
							}else {
								if(p1.getName().equals("Jesse")) {
									JesseAttack(p1, p2, p4, p6);
								}else if(p1.getName().equals("Audrey")) {
									AudreyAttack(p1, p2, p4, p6);
								}else if(p1.getName().equals("Bedrock")) {
									BedrockAttack(p1, p2, p4 ,p6);
								}else if(p1.getName().equals("Max")) {
									MaxAttack(p1, p2, p4 ,p6);
								}else if(p1.getName().equals("Cherry")) {
									CherryAttack(p1, p2, p4, p6, p3, p5);
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
											if(p1.getName().equals("Lunar")) {
												LunarAttack(p1, p2, p4, p6);
											}
											if(p1.getName().equals("Solar")) {
												SolarAttack(p1, p2);
											}
											if(p1.getName().equals("Mack")) {
												MackAttack(p1, p2);
											}
											if(p1.getName().equals("Finley")) {
												FinleyAttack(p1, p2);
											}
											if(p1.getName().equals("Burt")) {
												BurtAttack(p1, p2);
											}
											if(p1.getName().equals("Bolo")) {
												BoloAttack(p1, p2);
											}
											if(p1.getName().equals("Dylan")) {
												DylanAttack(p1, p2);
											}
											if(p1.getName().equals("Zero")) {
												ZeroAttack(p1, p2, p4, p6);
											}
											if(p1.getName().equals("Eli")) {
												EliAttack(p1, p2);
											}
											if(p1.getName().equals("Via")) {
												ViaAttack(p1, p2, p4 ,p6);
											}
											if(p1.getName().equals("Louis")) {
												LouisAttack(p1, p2);
											}
											if(p1.getName().equals("Alex")) {
												AlexAttack(p1, p2);
											}
											if(p1.getName().equals("Orion")) {
												OrionAttack(p1, p2);
											}
											if(p1.getName().equals("Kailani")) {
												KailaniAttack(p1, p2);
											}
											if(p1.getName().equals("Ashley")) {
												AshleyAttack(p1, p2);
											}
											if(p1.getName().equals("Rocco")) {
												RoccoAttack(p1, p2, p4, p6);
											}
											if(p1.getName().equals("Sammi")) {
												SammiAttack(p1, p2);
											}
											if(p1.getName().equals("Clara")) {
												ClaraAttack(p1, p2);
											}
											if(p1.getName().equals("Thunder")) {
												ThunderAttack(p1, p2, p4, p6);
											}
											if(p1.getName().equals("Aidan")) {
												AidanAttack(p1, p2);
											}
											if(p1.getName().equals("Liam")) {
												LiamAttack(p1, p2, p4, p6);
											}
											if(p1.getName().equals("Axol")) {
												AxolAttack(p1, p2);
											}
											if(p1.getName().equals("Katrina")) {
												KatrinaAttack(p1, p2);
											}
											if(p1.getName().equals("Midnite")) {
												MidniteAttack(p1, p2);
											}
											if(p1.getName().equals("Xara")) {
												XaraAttack(p1, p2);
											}
											if(p1.getName().equals("Kithara")) {
												KitharaAttack(p1, p2);
											}
											if(p1.getName().equals("Anjelika")) {
												AnjelikaAttack(p1, p2);
											}
											if(p1.getName().equals("Archer")) {
												ArcherAttack(p1, p2);
											}
											if(p1.getName().equals("Tom")) {
												TomAttack(p1, p2);
											}
											if(p1.getName().equals("Dimentio")) {
												DimentioAttack(p1, p2);
											}
											if(p1.getName().equals("Grizz")) {
												GrizzAttack(p1, p2, p4, p6);
											}
											if(p1.getName().equals("Evil")) {
												EvilAttack(p1, p2, p4, p6);
											}
											if(p1.getName().equals("Mason")) {
												MasonAttack(p1, p2);
											}
											if(p1.getName().equals("Airic")) {
												AiricAttack(p1, p2);
											}
											if(p1.getName().equals("Julian")) {
												JulianAttack(p1, p2, p4, p6);
											}
											if(p1.getName().equals("Gash")) {
												GashAttack(p1, p2);
											}
											if(p1.getName().equals("Mayhem")) {
												MayhemAttack(p1, p2);
											}
											if(p1.getName().equals("Gates")) {
												Location l = SetCursor(p1, p2, p4, p6, p3, p5);
												GatesAttack(p1, p2, l);
											}
											if(p1.getName().equals("Ayson")) {
												AysonAttack(p1, p2);
											}
											if(p1.getName().equals("Chloe")) {
												ChloeAttack(p1, p2, p3, p5);
											}
											if(p1.getName().equals("Hopper")) {
												HopperAttack(p1, p2);
											}
											if(p1.getName().equals("Redgar")) {
												RedgarAttack(p1, p2);
											}
											if(p1.getName().equals("Radar")) {
												RadarAttack(p1, p2);
											}
											if(p1.getName().equals("Oona")) {
												OonaAttack(p1, p2);
											}
											if(p1.getName().equals("Augie")) {
												AugieAttack(p1, p2);
											}
											if(p1.getName().equals("Ruby")) {
												RubyAttack(p1, p2);
											}
											if(p1.getName().equals("Norman")) {
												NormanAttack(p1, p2, p3, p5);
											}
											if(p1.getName().equals("Chief")) {
												ChiefAttack(p1, p2);
											}
										}
									}
									if(attackResponse.equals("2")) {
										if(!p1.inRange(p4)) {
											System.out.println();
											System.out.println("Target is out of range!");
											System.out.println();
										}else {
											if(p1.getName().equals("Lunar")) {
												LunarAttack(p1, p4, p2, p6);
											}
											if(p1.getName().equals("Solar")) {
												SolarAttack(p1, p4);
											}
											if(p1.getName().equals("Mack")) {
												MackAttack(p1, p4);
											}if(p1.getName().equals("Finley")) {
												FinleyAttack(p1, p4);
											}
											if(p1.getName().equals("Burt")) {
												BurtAttack(p1, p4);
											}
											if(p1.getName().equals("Bolo")) {
												BoloAttack(p1, p4);
											}
											if(p1.getName().equals("Dylan")) {
												DylanAttack(p1, p4);
											}
											if(p1.getName().equals("Zero")) {
												ZeroAttack(p1, p4, p2, p6);
											}
											if(p1.getName().equals("Eli")) {
												EliAttack(p1, p4);
											}
											if(p1.getName().equals("Via")) {
												ViaAttack(p1, p4, p2 ,p6);
											}
											if(p1.getName().equals("Louis")) {
												LouisAttack(p1, p4);
											}
											if(p1.getName().equals("Alex")) {
												AlexAttack(p1, p4);
											}
											if(p1.getName().equals("Orion")) {
												OrionAttack(p1, p4);
											}
											if(p1.getName().equals("Kailani")) {
												KailaniAttack(p1, p4);
											}
											if(p1.getName().equals("Ashley")) {
												AshleyAttack(p1, p4);
											}
											if(p1.getName().equals("Rocco")) {
												RoccoAttack(p1, p4, p2, p6);
											}
											if(p1.getName().equals("Sammi")) {
												SammiAttack(p1, p4);
											}
											if(p1.getName().equals("Clara")) {
												ClaraAttack(p1, p4);
											}
											if(p1.getName().equals("Thunder")) {
												ThunderAttack(p1, p4, p2, p6);
											}
											if(p1.getName().equals("Aidan")) {
												AidanAttack(p1, p4);
											}
											if(p1.getName().equals("Liam")) {
												LiamAttack(p1, p4, p2, p6);
											}
											if(p1.getName().equals("Axol")) {
												AxolAttack(p1, p4);
											}
											if(p1.getName().equals("Katrina")) {
												KatrinaAttack(p1, p4);
											}
											if(p1.getName().equals("Midnite")) {
												MidniteAttack(p1, p4);
											}
											if(p1.getName().equals("Xara")) {
												XaraAttack(p1, p4);
											}
											if(p1.getName().equals("Kithara")) {
												KitharaAttack(p1, p4);
											}
											if(p1.getName().equals("Anjelika")) {
												AnjelikaAttack(p1, p4);
											}
											if(p1.getName().equals("Archer")) {
												ArcherAttack(p1, p4);
											}
											if(p1.getName().equals("Tom")) {
												TomAttack(p1, p4);
											}
											if(p1.getName().equals("Dimentio")) {
												DimentioAttack(p1, p4);
											}
											if(p1.getName().equals("Grizz")) {
												GrizzAttack(p1, p4, p2, p6);
											}
											if(p1.getName().equals("Evil")) {
												EvilAttack(p1, p4, p2, p6);
											}
											if(p1.getName().equals("Mason")) {
												MasonAttack(p1, p4);
											}
											if(p1.getName().equals("Airic")) {
												AiricAttack(p1, p4);
											}
											if(p1.getName().equals("Julian")) {
												JulianAttack(p1, p4, p2, p6);
											}
											if(p1.getName().equals("Gash")) {
												GashAttack(p1, p4);
											}
											if(p1.getName().equals("Mayhem")) {
												MayhemAttack(p1, p4);
											}
											if(p1.getName().equals("Gates")) {
												Location l = SetCursor(p1, p2, p4, p6, p3, p5);
												GatesAttack(p1, p4, l);
											}
											if(p1.getName().equals("Ayson")) {
												AysonAttack(p1, p4);
											}
											if(p1.getName().equals("Chloe")) {
												ChloeAttack(p1, p4, p3, p5);
											}
											if(p1.getName().equals("Hopper")) {
												HopperAttack(p1, p4);
											}
											if(p1.getName().equals("Redgar")) {
												RedgarAttack(p1, p4);
											}
											if(p1.getName().equals("Radar")) {
												RadarAttack(p1, p4);
											}
											if(p1.getName().equals("Oona")) {
												OonaAttack(p1, p4);
											}
											if(p1.getName().equals("Augie")) {
												AugieAttack(p1, p4);
											}
											if(p1.getName().equals("Ruby")) {
												RubyAttack(p1, p4);
											}
											if(p1.getName().equals("Norman")) {
												NormanAttack(p1, p4, p3, p5);
											}
											if(p1.getName().equals("Chief")) {
												ChiefAttack(p1, p4);
											}
										}
									}
									if(attackResponse.equals("3")) {
										if(!p1.inRange(p6)) {
											System.out.println();
											System.out.println("Target is out of range!");
											System.out.println();
										}else {
											if(p1.getName().equals("Lunar")) {
												LunarAttack(p1, p6, p4, p2);
											}
											if(p1.getName().equals("Solar")) {
												SolarAttack(p1, p6);
											}
											if(p1.getName().equals("Mack")) {
												MackAttack(p1, p6);
											}
											if(p1.getName().equals("Finley")) {
												FinleyAttack(p1, p6);
											}
											if(p1.getName().equals("Burt")) {
												BurtAttack(p1, p6);
											}
											if(p1.getName().equals("Bolo")) {
												BoloAttack(p1, p6);
											}
											if(p1.getName().equals("Dylan")) {
												DylanAttack(p1, p6);
											}
											if(p1.getName().equals("Zero")) {
												ZeroAttack(p1, p6, p4, p2);
											}
											if(p1.getName().equals("Eli")) {
												EliAttack(p1, p6);
											}
											if(p1.getName().equals("Via")) {
												ViaAttack(p1, p6, p4 ,p2);
											}
											if(p1.getName().equals("Louis")) {
												LouisAttack(p1, p6);
											}
											if(p1.getName().equals("Alex")) {
												AlexAttack(p1, p6);
											}
											if(p1.getName().equals("Orion")) {
												OrionAttack(p1, p6);
											}
											if(p1.getName().equals("Kailani")) {
												KailaniAttack(p1, p6);
											}
											if(p1.getName().equals("Ashley")) {
												AshleyAttack(p1, p6);
											}
											if(p1.getName().equals("Rocco")) {
												RoccoAttack(p1, p6, p4, p2);
											}
											if(p1.getName().equals("Sammi")) {
												SammiAttack(p1, p6);
											}
											if(p1.getName().equals("Clara")) {
												ClaraAttack(p1, p6);
											}
											if(p1.getName().equals("Thunder")) {
												ThunderAttack(p1, p6, p4, p2);
											}
											if(p1.getName().equals("Aidan")) {
												AidanAttack(p1, p6);
											}
											if(p1.getName().equals("Liam")) {
												LiamAttack(p1, p6, p4, p2);
											}
											if(p1.getName().equals("Axol")) {
												AxolAttack(p1, p6);
											}
											if(p1.getName().equals("Katrina")) {
												KatrinaAttack(p1, p6);
											}
											if(p1.getName().equals("Midnite")) {
												MidniteAttack(p1, p6);
											}
											if(p1.getName().equals("Xara")) {
												XaraAttack(p1, p6);
											}
											if(p1.getName().equals("Kithara")) {
												KitharaAttack(p1, p6);
											}
											if(p1.getName().equals("Anjelika")) {
												AnjelikaAttack(p1, p6);
											}
											if(p1.getName().equals("Archer")) {
												ArcherAttack(p1, p6);
											}
											if(p1.getName().equals("Tom")) {
												TomAttack(p1, p6);
											}
											if(p1.getName().equals("Dimentio")) {
												DimentioAttack(p1, p6);
											}
											if(p1.getName().equals("Grizz")) {
												GrizzAttack(p1, p6, p4, p2);
											}
											if(p1.getName().equals("Evil")) {
												EvilAttack(p1, p6, p4, p2);
											}
											if(p1.getName().equals("Mason")) {
												MasonAttack(p1, p6);
											}
											if(p1.getName().equals("Airic")) {
												AiricAttack(p1, p6);
											}
											if(p1.getName().equals("Julian")) {
												JulianAttack(p1, p6, p4, p2);
											}
											if(p1.getName().equals("Gash")) {
												GashAttack(p1, p6);
											}
											if(p1.getName().equals("Mayhem")) {
												MayhemAttack(p1, p6);
											}
											if(p1.getName().equals("Gates")) {
												Location l = SetCursor(p1, p2, p4, p6, p3, p5);
												GatesAttack(p1, p6, l);
											}
											if(p1.getName().equals("Ayson")) {
												AysonAttack(p1, p6);
											}
											if(p1.getName().equals("Chloe")) {
												ChloeAttack(p1, p6, p3, p5);
											}
											if(p1.getName().equals("Hopper")) {
												HopperAttack(p1, p6);
											}
											if(p1.getName().equals("Redgar")) {
												RedgarAttack(p1, p6);
											}
											if(p1.getName().equals("Radar")) {
												RadarAttack(p1, p6);
											}
											if(p1.getName().equals("Oona")) {
												OonaAttack(p1, p6);
											}
											if(p1.getName().equals("Augie")) {
												AugieAttack(p1, p6);
											}
											if(p1.getName().equals("Ruby")) {
												RubyAttack(p1, p6);
											}
											if(p1.getName().equals("Norman")) {
												NormanAttack(p1, p6, p3, p5);
											}
											if(p1.getName().equals("Chief")) {
												ChiefAttack(p1, p6);
											}
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
					System.out.println("Turn: " + tD);
					System.out.println("Team A's Turn (Go " + p3.getSkin() + "!)");
					System.out.println(p3);
					System.out.print("What would " + p3.getName() + " like to do: ");
					String response = input.next();
					System.out.println();
					if(response.equals("b")){
						b.printField(p1, p2, p3, p4, p5, p6, orbs, cover, p3, p2, p4, p6);
					}
					if(response.equals("s")) {
						party1.showRoster();
						System.out.print("Who do you want to switch to: ");
						String switchResponse = input.next();
						if(switchResponse.equals("1")) {
							p3.passTurn(p1);
							System.out.println();
						}
						if(switchResponse.equals("2")) {
							p3.passTurn(p3);
							System.out.println();
						}
						if(switchResponse.equals("3")) {
							p3.passTurn(p5);
							System.out.println();
						}
					}
					if(response.equals("p")) {
						System.out.print("Are you sure you want to pass to the enemy's turn: ");
						String switchResponse = input.next();
						if(switchResponse.equals("p")) {
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
					if(response.equals("i")) {
						CheckProfile(p3, party2);
					}
					if(response.equals("o")) {
						ShowOrbs(p3);
					}
					if(response.equals("c")) {
						ShowCover(p3);
					}
					if(response.equals("d")) {
						Dash(p3, p2, p4, p6);
					}
					if(response.equals("j")) {
						Location l = SetCursor(p3, p2, p4, p6, p1, p5);
						Jump(p3, p1, p5, l);
					}
					if(response.equals("u")) {
						if(!p3.ultReady() || p3.ultActive()) {
							System.out.println("My ultimate cannot be used!");
							System.out.println();
						}else {
							if(p3.getName().equals("Chief")) {
								ChiefUltimate(p3, p2, p4, p6, p1, p5);
							}
							if(p3.getName().equals("Jesse")) {
								JesseUltimate(p3);
							}
							if(p3.getName().equals("Norman")) {
								NormanUltimate(p3, p1, p5);
							}
							if(p3.getName().equals("Ruby")) {
								RubyUltimate(p3, p1, p5);
							}
							if(p3.getName().equals("Augie")) {
								AugieUltimate(p3, p2, p4 ,p6);
							}
							if(p3.getName().equals("Oona")) {
								OonaUltimate(p3, p1, p5);
							}
							if(p3.getName().equals("Radar")) {
								RadarUltimate(p3, p2, p4, p6, p1, p5);
							}
							if(p3.getName().equals("Redgar")) {
								RedgarUltimate(p3);
							}
							if(p3.getName().equals("Hopper")) {
								HopperUltimate(p3, p1, p5);
							}
							if(p3.getName().equals("Chloe")) {
								ChloeUltimate(p3, p1, p5);
							}
							if(p3.getName().equals("Ayson")) {
								AysonUltimate(p3, p2, p4 ,p6);
							}
							if(p3.getName().equals("Audrey")) {
								AudreyUltimate(p3, p1, p5);
							}
							if(p3.getName().equals("Mayhem")) {
								MayhemUltimate(p3);
							}
							if(p3.getName().equals("Gash")) {
								GashUltimate(p3, p2, p4, p6);
							}
							if(p3.getName().equals("Julian")) {
								JulianUltimate(p3, p2, p4, p6, p1, p5);
							}
							if(p3.getName().equals("Airic")) {
								AiricUltimate(p3, p2, p4, p6);
							}
							if(p3.getName().equals("Mason")) {
								MasonUltimate(p3, p2, p4, p6, p1, p5);
							}
							if(p3.getName().equals("Evil")) {
								EvilUltimate(p3, p2, p4, p6);
							}
							if(p3.getName().equals("Grizz")) {
								GrizzUltimate(p3);
							}
							if(p3.getName().equals("Dimentio")) {
								DimentioUltimate(p3);
							}
							if(p3.getName().equals("Tom")) {
								Location l = SetCursor(p3, p2, p4, p6, p1, p5);
								TomUltimate(p3, p2, p4, p6, l);
							}
							if(p3.getName().equals("Archer")) {
								ArcherUltimate(p3, p2, p4, p6);
							}
							if(p3.getName().equals("Anjelika")) {
								AnjelikaUltimate(p3);
							}
							if(p3.getName().equals("Kithara")) {
								KitharaUltimate(p3, p2, p4, p6);
							}
							if(p3.getName().equals("Xara")) {
								XaraUltimate(p3, p2, p4, p6);
							}
							if(p3.getName().equals("Midnite")) {
								MidniteUltimate(p3);
							}
							if(p3.getName().equals("Katrina")) {
								KatrinaUltimate(p3, p1, p5);
							}
							if(p3.getName().equals("Axol")) {
								AxolUltimate(p3, p1, p5);
							}
							if(p3.getName().equals("Liam")) {
								LiamUltimate(p3, p2, p4, p6, p1, p5);
							}
							if(p3.getName().equals("Aidan")) {
								AidanUltimate(p3, p2, p4, p6, p1, p5);
							}
							if(p3.getName().equals("Thunder")) {
								ThunderUltimate(p3, p2, p4, p6);
							}
							if(p3.getName().equals("Clara")) {
								ClaraUltimate(p3);
							}
							if(p3.getName().equals("Sammi")) {
								SammiUltimate(p3);
							}
							if(p3.getName().equals("Rocco")) {
								RoccoUltimate(p3, p2, p4, p6);
							}
							if(p3.getName().equals("Bedrock")) {
								BedrockUltimate(p3);
							}
							if(p3.getName().equals("Ashley")) {
								AshleyUltimate(p3, p2, p4, p6, p1, p5);
							}
							if(p3.getName().equals("Kailani")) {
								KailaniUltimate(p3, p2, p4, p6);
							}
							if(p3.getName().equals("Orion")) {
								OrionUltimate(p3, p1, p5);
							}
							if(p3.getName().equals("Alex")) {
								AlexUltimate(p3);
							}
							if(p3.getName().equals("Louis")) {
								Location l = SetCursor(p3, p2, p4, p6, p1, p5);
								LouisUltimate(p3, p2, p4, p6, l);
							}
							if(p3.getName().equals("Via")) {
								ViaUltimate(p3, p2, p4 ,p6);
							}
							if(p3.getName().equals("Max")) {
								MaxUltimate(p3, p2, p4 ,p6);
							}
							if(p3.getName().equals("Cherry")) {
								CherryUltimate(p3);
							}
							if(p3.getName().equals("Bolo")) {
								BoloUltimate(p3, p2, p4 ,p6);
							}
							if(p3.getName().equals("Mack")) {
								MackUltimate(p3);
							}
							if(p3.getName().equals("Finley")) {
								FinleyUltimate(p3, p2, p4, p6);
							}
							if(p3.getName().equals("Zero")) {
								ZeroUltimate(p3);
							}
							if(p3.getName().equals("Burt")) {
								BurtUltimate(p3, p2, p4, p6);
							}
							if(p3.getName().equals("Eli")) {
								EliUltimate(p3, p1, p5);
							}
							if(p3.getName().equals("Solar")) {
								SolarUltimate(p3, p2, p4, p6);
							}
							if(p3.getName().equals("Dylan")) {
								DylanUltimate(p3, p2, p4, p6, p1, p5);
							}
							if(p3.getName().equals("Gates")) {
								GatesUltimate(p3);
							}
							if(p3.getName().equals("Lunar")) {
								LunarUltimate(p3, p2, p4, p6);
							}
						}
					}
					if(response.equals("m")) {
						Movement(p3, p2, p4, p6, p1, p5);
					}
					if(response.equals("a") && !p3.onCooldown()) {
						if(p3.isDazed()) {
							System.out.println("Cannot use ability when dazed!");
							System.out.println();
						}else {
							if(p3.getName().equals("Lunar")) {
								LunarAbility(p3);
							}
							if(p3.getName().equals("Solar")) {
								SolarAbility(p3);
							}
							if(p3.getName().equals("Mack")) {
								Location l = SetCursor(p3, p2, p4, p6, p1, p5);
								MackAbility(p3, p2, p4, p6, l);
							}
							if(p3.getName().equals("Cherry")) {
								CherryAbility(p3, p2, p4, p6);
							}
							if(p3.getName().equals("Finley")) {
								FinleyAbility(p3, p2, p4, p6);
							}
							if(p3.getName().equals("Burt")) {
								BurtAbility(p3);
							}
							if(p3.getName().equals("Bolo")) {
								BoloAbility(p3);
							}
							if(p3.getName().equals("Dylan")) {
								DylanAbility(p3, p2, p4 ,p6);
							}
							if(p3.getName().equals("Zero")) {
								ZeroAbility(p3, p1, p5);
							}
							if(p3.getName().equals("Max")) {
								MaxAbility(p3, p1, p5);
							}
							if(p3.getName().equals("Eli")) {
								EliAbility(p3, p1, p5);
							}
							if(p3.getName().equals("Via")) {
								Location l = SetCursor(p3, p2, p4, p6, p1, p5);
								ViaAbility(p3, p2, p4, p6, l);
							}
							if(p3.getName().equals("Louis")) {
								LouisAbility(p3, p2, p4 ,p6);
							}
							if(p3.getName().equals("Alex")) {
								AlexAbility(p3, p1, p5);
							}
							if(p3.getName().equals("Orion")) {
								OrionAbility(p3, p1, p5);
							}
							if(p3.getName().equals("Kailani")) {
								Location l = SetCursor(p3, p2, p4, p6, p1, p5);
								KailaniAbility(p3, p2, p4, p6, l);
							}
							if(p3.getName().equals("Ashley")) {
								AshleyAbility(p3, p2, p4, p6, p1, p5);
							}
							if(p3.getName().equals("Bedrock")) {
								BedrockAbility(p3);
							}
							if(p3.getName().equals("Rocco")) {
								RoccoAbility(p3, p2, p4 ,p6);
							}
							if(p3.getName().equals("Sammi")) {
								SammiAbility(p3);
							}
							if(p3.getName().equals("Clara")) {
								ClaraAbility(p3);
							}
							if(p3.getName().equals("Thunder")) {
								ThunderAbility(p3);
							}
							if(p3.getName().equals("Aidan")) {
								AidanAbility(p3, p1, p5);
							}
							if(p3.getName().equals("Liam")) {
								LiamAbility(p3, p1, p5);
							}
							if(p3.getName().equals("Axol")) {
								AxolAbility(p3, p1, p5);
							}
							if(p3.getName().equals("Katrina")) {
								KatrinaAbility(p3, p1, p5);
							}
							if(p3.getName().equals("Midnite")) {
								MidniteAbility(p3, p2, p4, p6, p1, p5);
							}
							if(p3.getName().equals("Xara")) {
								XaraAbility(p3, p1, p5);
							}
							if(p3.getName().equals("Kithara")) {
								KitharaAbility(p3, p1, p5);
							}
							if(p3.getName().equals("Anjelika")) {
								AnjelikaAbility(p3, p2, p4 ,p6);
							}
							if(p3.getName().equals("Archer")) {
								ArcherAbility(p3, p1, p5);
							}
							if(p3.getName().equals("Tom")) {
								TomAbility(p3);
							}
							if(p3.getName().equals("Dimentio")) {
								DimentioAbility(p3, p2, p4 ,p6);
							}
							if(p3.getName().equals("Grizz")) {
								GrizzAbility(p3, p1, p5);
							}
							if(p3.getName().equals("Evil")) {
								EvilAbility(p3);
							}
							if(p3.getName().equals("Mason")) {
								Location l = SetCursor(p3, p2, p4, p6, p1, p5);
								MasonAbility(p3, p2, p4, p6, l);
							}
							if(p3.getName().equals("Airic")) {
								Location l = SetCursor(p3, p2, p4, p6, p1, p5);
								AiricAbility(p3, p1, p5, l);
							}
							if(p3.getName().equals("Julian")) {
								JulianAbility(p3);
							}
							if(p3.getName().equals("Gash")) {
								GashAbility(p3, p1, p5);
							}
							if(p3.getName().equals("Mayhem")) {
								MayhemAbility(p3, p2, p4 ,p6);
							}
							if(p3.getName().equals("Gates")) {
								GatesAbility(p3, p1, p5);
							}
							if(p3.getName().equals("Audrey")) {
								AudreyAbility(p3, p2, p4 ,p6);
							}
							if(p3.getName().equals("Ayson")) {
								AysonAbility(p3, p2, p4, p6, p1, p5);
							}
							if(p3.getName().equals("Chloe")) {
								ChloeAbility(p3, p1, p5);
							}
							if(p3.getName().equals("Hopper")) {
								HopperAbility(p3, p1, p5);
							}
							if(p3.getName().equals("Redgar")) {
								RedgarAbility(p3, p1, p5);
							}
							if(p3.getName().equals("Radar")) {
								RadarAbility(p3, p2, p4 ,p6);
							}
							if(p3.getName().equals("Oona")) {
								OonaAbility(p3, p2, p4 ,p6);
							}
							if(p3.getName().equals("Augie")) {
								AugieAbility(p3, p1, p5);
							}
							if(p3.getName().equals("Ruby")) {
								Location l = SetCursor(p3, p2, p4, p6, p1, p5);
								RubyAbility(p3, l);
							}
							if(p3.getName().equals("Norman")) {
								NormanAbility(p3, p2, p4 ,p6);
							}
							if(p3.getName().equals("Jesse")) {
								JesseAbility(p3);
							}
							if(p3.getName().equals("Chief")) {
								ChiefAbility(p3, p1, p5);
							}
						}
					}
					if(response.equals("w")) {
						if(p3.isFreezed()) {
							System.out.println("Cannot use weapon while freezed!");
							System.out.println();
						}else {
							if(p3.hasAttacked()) {
								System.out.println(p3.getName() + " has already attacked this turn!");
								System.out.println();
							}else {
								if(p3.getName().equals("Jesse")) {
									JesseAttack(p3, p2, p4, p6);
								}else if(p3.getName().equals("Audrey")) {
									AudreyAttack(p3, p2, p4, p6);
								}else if(p3.getName().equals("Bedrock")) {
									BedrockAttack(p3, p2, p4 ,p6);
								}else if(p3.getName().equals("Max")) {
									MaxAttack(p3, p2, p4 ,p6);
								}else if(p3.getName().equals("Cherry")) {
									CherryAttack(p3, p2, p4, p6, p1, p5);
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
											if(p3.getName().equals("Lunar")) {
												LunarAttack(p3, p2, p4, p6);
											}
											if(p3.getName().equals("Solar")) {
												SolarAttack(p3, p2);
											}
											if(p3.getName().equals("Mack")) {
												MackAttack(p3, p2);
											}
											if(p3.getName().equals("Finley")) {
												FinleyAttack(p3, p2);
											}
											if(p3.getName().equals("Burt")) {
												BurtAttack(p3, p2);
											}
											if(p3.getName().equals("Bolo")) {
												BoloAttack(p3, p2);
											}
											if(p3.getName().equals("Zero")) {
												ZeroAttack(p3, p2, p4, p6);
											}
											if(p3.getName().equals("Dylan")) {
												DylanAttack(p3, p2);
											}
											if(p3.getName().equals("Eli")) {
												EliAttack(p3, p2);
											}
											if(p3.getName().equals("Via")) {
												ViaAttack(p3, p2, p4 ,p6);
											}
											if(p3.getName().equals("Louis")) {
												LouisAttack(p3, p2);
											}
											if(p3.getName().equals("Alex")) {
												AlexAttack(p3, p2);
											}
											if(p3.getName().equals("Orion")) {
												OrionAttack(p3, p2);
											}
											if(p3.getName().equals("Kailani")) {
												KailaniAttack(p3, p2);
											}
											if(p3.getName().equals("Ashley")) {
												AshleyAttack(p3, p2);
											}
											if(p3.getName().equals("Rocco")) {
												RoccoAttack(p3, p2, p4, p6);
											}
											if(p3.getName().equals("Sammi")) {
												SammiAttack(p3, p2);
											}
											if(p3.getName().equals("Clara")) {
												ClaraAttack(p3, p2);
											}
											if(p3.getName().equals("Thunder")) {
												ThunderAttack(p3, p2, p4, p6);
											}
											if(p3.getName().equals("Aidan")) {
												AidanAttack(p3, p2);
											}
											if(p3.getName().equals("Liam")) {
												LiamAttack(p3, p2, p4, p6);
											}
											if(p3.getName().equals("Axol")) {
												AxolAttack(p3, p2);
											}
											if(p3.getName().equals("Katrina")) {
												KatrinaAttack(p3, p2);
											}
											if(p3.getName().equals("Midnite")) {
												MidniteAttack(p3, p2);
											}
											if(p3.getName().equals("Kithara")) {
												KitharaAttack(p3, p2);
											}
											if(p3.getName().equals("Anjelika")) {
												AnjelikaAttack(p3, p2);
											}
											if(p3.getName().equals("Xara")) {
												XaraAttack(p3, p2);
											}
											if(p3.getName().equals("Archer")) {
												ArcherAttack(p3, p2);
											}
											if(p3.getName().equals("Tom")) {
												TomAttack(p3, p2);
											}
											if(p3.getName().equals("Dimentio")) {
												DimentioAttack(p3, p2);
											}
											if(p3.getName().equals("Grizz")) {
												GrizzAttack(p3, p2, p4, p6);
											}
											if(p3.getName().equals("Evil")) {
												EvilAttack(p3, p2, p4, p6);
											}
											if(p3.getName().equals("Mason")) {
												MasonAttack(p3, p2);
											}
											if(p3.getName().equals("Airic")) {
												AiricAttack(p3, p2);
											}
											if(p3.getName().equals("Julian")) {
												JulianAttack(p3, p2, p4, p6);
											}
											if(p3.getName().equals("Gash")) {
												GashAttack(p3, p2);
											}
											if(p3.getName().equals("Mayhem")) {
												MayhemAttack(p3, p2);
											}
											if(p3.getName().equals("Gates")) {
												Location l = SetCursor(p3, p2, p4, p6, p1, p5);
												GatesAttack(p3, p2, l);
											}
											if(p3.getName().equals("Ayson")) {
												AysonAttack(p3, p2);
											}
											if(p3.getName().equals("Chloe")) {
												ChloeAttack(p3, p2, p1, p5);
											}
											if(p3.getName().equals("Hopper")) {
												HopperAttack(p3, p2);
											}
											if(p3.getName().equals("Redgar")) {
												RedgarAttack(p3, p2);
											}
											if(p3.getName().equals("Radar")) {
												RadarAttack(p3, p2);
											}
											if(p3.getName().equals("Oona")) {
												OonaAttack(p3, p2);
											}
											if(p3.getName().equals("Augie")) {
												AugieAttack(p3, p2);
											}
											if(p3.getName().equals("Ruby")) {
												RubyAttack(p3, p2);
											}
											if(p3.getName().equals("Norman")) {
												NormanAttack(p3, p2, p1, p5);
											}
											if(p3.getName().equals("Chief")) {
												ChiefAttack(p3, p2);
											}
										}
									}
									if(attackResponse.equals("2")) {
										if(!p3.inRange(p4)) {
											System.out.println();
											System.out.println("Target is out of range!");
											System.out.println();
										}else {
											if(p3.getName().equals("Lunar")) {
												LunarAttack(p3, p4, p2, p6);
											}
											if(p3.getName().equals("Solar")) {
												SolarAttack(p3, p4);
											}
											if(p3.getName().equals("Mack")) {
												MackAttack(p3, p4);
											}
											if(p3.getName().equals("Finley")) {
												FinleyAttack(p3, p4);
											}
											if(p3.getName().equals("Burt")) {
												BurtAttack(p3, p4);
											}
											if(p3.getName().equals("Bolo")) {
												BoloAttack(p3, p4);
											}
											if(p3.getName().equals("Zero")) {
												ZeroAttack(p3, p4, p2, p6);
											}
											if(p3.getName().equals("Dylan")) {
												DylanAttack(p3, p4);
											}
											if(p3.getName().equals("Eli")) {
												EliAttack(p3, p4);
											}
											if(p3.getName().equals("Via")) {
												ViaAttack(p3, p4, p2 ,p6);
											}
											if(p3.getName().equals("Louis")) {
												LouisAttack(p3, p4);
											}
											if(p3.getName().equals("Alex")) {
												AlexAttack(p3, p4);
											}
											if(p3.getName().equals("Orion")) {
												OrionAttack(p3, p4);
											}
											if(p3.getName().equals("Kailani")) {
												KailaniAttack(p3, p4);
											}
											if(p3.getName().equals("Ashley")) {
												AshleyAttack(p3, p4);
											}
											if(p3.getName().equals("Rocco")) {
												RoccoAttack(p3, p4, p2, p6);
											}
											if(p3.getName().equals("Sammi")) {
												SammiAttack(p3, p4);
											}
											if(p3.getName().equals("Clara")) {
												ClaraAttack(p3, p4);
											}
											if(p3.getName().equals("Thunder")) {
												ThunderAttack(p3, p4, p2, p6);
											}
											if(p3.getName().equals("Aidan")) {
												AidanAttack(p3, p4);
											}
											if(p3.getName().equals("Liam")) {
												LiamAttack(p3, p4, p2, p6);
											}
											if(p3.getName().equals("Axol")) {
												AxolAttack(p3, p4);
											}
											if(p3.getName().equals("Katrina")) {
												KatrinaAttack(p3, p4);
											}
											if(p3.getName().equals("Midnite")) {
												MidniteAttack(p3, p4);
											}
											if(p3.getName().equals("Xara")) {
												XaraAttack(p3, p4);
											}
											if(p3.getName().equals("Kithara")) {
												KitharaAttack(p3, p4);
											}
											if(p3.getName().equals("Anjelika")) {
												AnjelikaAttack(p3, p4);
											}
											if(p3.getName().equals("Archer")) {
												ArcherAttack(p3, p4);
											}
											if(p3.getName().equals("Tom")) {
												TomAttack(p3, p4);
											}
											if(p3.getName().equals("Dimentio")) {
												DimentioAttack(p3, p4);
											}
											if(p3.getName().equals("Grizz")) {
												GrizzAttack(p3, p4, p2, p6);
											}
											if(p3.getName().equals("Evil")) {
												EvilAttack(p3, p4, p2, p6);
											}
											if(p3.getName().equals("Mason")) {
												MasonAttack(p3, p4);
											}
											if(p3.getName().equals("Airic")) {
												AiricAttack(p3, p4);
											}
											if(p3.getName().equals("Julian")) {
												JulianAttack(p3, p4, p2, p6);
											}
											if(p3.getName().equals("Gash")) {
												GashAttack(p3, p4);
											}
											if(p3.getName().equals("Mayhem")) {
												MayhemAttack(p3, p4);
											}
											if(p3.getName().equals("Gates")) {
												Location l = SetCursor(p3, p2, p4, p6, p1, p5);
												GatesAttack(p3, p4, l);
											}
											if(p3.getName().equals("Ayson")) {
												AysonAttack(p3, p4);
											}
											if(p3.getName().equals("Chloe")) {
												ChloeAttack(p3, p4, p1, p5);
											}
											if(p3.getName().equals("Hopper")) {
												HopperAttack(p3, p4);
											}
											if(p3.getName().equals("Redgar")) {
												RedgarAttack(p3, p4);
											}
											if(p3.getName().equals("Radar")) {
												RadarAttack(p3, p4);
											}
											if(p3.getName().equals("Oona")) {
												OonaAttack(p3, p4);
											}
											if(p3.getName().equals("Augie")) {
												AugieAttack(p3, p4);
											}
											if(p3.getName().equals("Ruby")) {
												RubyAttack(p3, p4);
											}
											if(p3.getName().equals("Norman")) {
												NormanAttack(p3, p4, p1, p5);
											}
											if(p3.getName().equals("Chief")) {
												ChiefAttack(p3, p4);
											}
										}
									}
									if(attackResponse.equals("3")) {
										if(!p3.inRange(p6)) {
											System.out.println();
											System.out.println("Target is out of range!");
											System.out.println();
										}else {
											if(p3.getName().equals("Lunar")) {
												LunarAttack(p3, p6, p4, p2);
											}
											if(p3.getName().equals("Solar")) {
												SolarAttack(p3, p6);
											}
											if(p3.getName().equals("Mack")) {
												MackAttack(p3, p6);
											}
											if(p3.getName().equals("Finley")) {
												FinleyAttack(p3, p6);
											}
											if(p3.getName().equals("Burt")) {
												BurtAttack(p3, p6);
											}
											if(p3.getName().equals("Bolo")) {
												BoloAttack(p3, p6);
											}
											if(p3.getName().equals("Zero")) {
												ZeroAttack(p3, p6, p4, p2);
											}
											if(p3.getName().equals("Dylan")) {
												DylanAttack(p3, p6);
											}
											if(p3.getName().equals("Eli")) {
												EliAttack(p3, p6);
											}
											if(p3.getName().equals("Via")) {
												ViaAttack(p3, p6, p4 ,p2);
											}
											if(p3.getName().equals("Louis")) {
												LouisAttack(p3, p6);
											}
											if(p3.getName().equals("Alex")) {
												AlexAttack(p3, p6);
											}
											if(p3.getName().equals("Orion")) {
												OrionAttack(p3, p6);
											}
											if(p3.getName().equals("Kailani")) {
												KailaniAttack(p3, p6);
											}
											if(p3.getName().equals("Ashley")) {
												AshleyAttack(p3, p6);
											}
											if(p3.getName().equals("Rocco")) {
												RoccoAttack(p3, p6, p4, p2);
											}
											if(p3.getName().equals("Sammi")) {
												SammiAttack(p3, p6);
											}
											if(p3.getName().equals("Clara")) {
												ClaraAttack(p3, p6);
											}
											if(p3.getName().equals("Thunder")) {
												ThunderAttack(p3, p6, p4, p2);
											}
											if(p3.getName().equals("Aidan")) {
												AidanAttack(p3, p6);
											}
											if(p3.getName().equals("Liam")) {
												LiamAttack(p3, p6, p4, p2);
											}
											if(p3.getName().equals("Axol")) {
												AxolAttack(p3, p6);
											}
											if(p3.getName().equals("Katrina")) {
												KatrinaAttack(p3, p6);
											}
											if(p3.getName().equals("Midnite")) {
												MidniteAttack(p3, p6);
											}
											if(p3.getName().equals("Xara")) {
												XaraAttack(p3, p6);
											}
											if(p3.getName().equals("Kithara")) {
												KitharaAttack(p3, p6);
											}
											if(p3.getName().equals("Anjelika")) {
												AnjelikaAttack(p3, p6);
											}
											if(p3.getName().equals("Archer")) {
												ArcherAttack(p3, p6);
											}
											if(p3.getName().equals("Tom")) {
												TomAttack(p3, p6);
											}
											if(p3.getName().equals("Dimentio")) {
												DimentioAttack(p3, p6);
											}
											if(p3.getName().equals("Grizz")) {
												GrizzAttack(p3, p6, p4, p2);
											}
											if(p3.getName().equals("Evil")) {
												EvilAttack(p3, p6, p4, p2);
											}
											if(p3.getName().equals("Mason")) {
												MasonAttack(p3, p6);
											}
											if(p3.getName().equals("Airic")) {
												AiricAttack(p3, p6);
											}
											if(p3.getName().equals("Julian")) {
												JulianAttack(p3, p6, p4, p2);
											}
											if(p3.getName().equals("Gash")) {
												GashAttack(p3, p6);
											}
											if(p3.getName().equals("Mayhem")) {
												MayhemAttack(p3, p6);
											}
											if(p3.getName().equals("Gates")) {
												Location l = SetCursor(p3, p2, p4, p6, p1, p5);
												GatesAttack(p3, p6, l);
											}
											if(p3.getName().equals("Ayson")) {
												AysonAttack(p3, p6);
											}
											if(p3.getName().equals("Chloe")) {
												ChloeAttack(p3, p6, p1, p5);
											}
											if(p3.getName().equals("Hopper")) {
												HopperAttack(p3, p6);
											}
											if(p3.getName().equals("Redgar")) {
												RedgarAttack(p3, p6);
											}
											if(p3.getName().equals("Radar")) {
												RadarAttack(p3, p6);
											}
											if(p3.getName().equals("Oona")) {
												OonaAttack(p3, p6);
											}
											if(p3.getName().equals("Augie")) {
												AugieAttack(p3, p6);
											}
											if(p3.getName().equals("Ruby")) {
												RubyAttack(p3, p6);
											}
											if(p3.getName().equals("Norman")) {
												NormanAttack(p3, p6, p1, p5);
											}
											if(p3.getName().equals("Chief")) {
												ChiefAttack(p3, p6);
											}
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
					System.out.println("Turn: " + tD);
					System.out.println("Team A's Turn (Go " + p5.getSkin() + "!)");
					System.out.println(p5);
					System.out.print("What would " + p5.getName() + " like to do: ");
					String response = input.next();
					System.out.println();
					if(response.equals("b")){
						b.printField(p1, p2, p3, p4, p5, p6, orbs, cover, p5, p2, p4, p6);
					}
					if(response.equals("s")) {
						party1.showRoster();
						System.out.print("Who do you want to switch to: ");
						String switchResponse = input.next();
						if(switchResponse.equals("1")) {
							p5.passTurn(p1);
							System.out.println();
						}
						if(switchResponse.equals("2")) {
							p5.passTurn(p3);
							System.out.println();
						}
						if(switchResponse.equals("3")) {
							p5.passTurn(p5);
							System.out.println();
						}
					}
					if(response.equals("p")) {
						System.out.print("Are you sure you want to pass to the enemy's turn: ");
						String switchResponse = input.next();
						if(switchResponse.equals("p")) {
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
					if(response.equals("i")) {
						CheckProfile(p5, party2);
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
					if(response.equals("d")) {
						Dash(p5, p2, p4, p6);
					}
					if(response.equals("j")) {
						Location l = SetCursor(p5, p2, p4, p6, p3, p1);
						Jump(p5, p3, p1, l);
					}
					if(response.equals("u")) {
						if(!p5.ultReady() || p5.ultActive()) {
							System.out.println("My ultimate cannot be used!");
							System.out.println();
						}else {
							if(p5.getName().equals("Chief")) {
								ChiefUltimate(p5, p2, p4, p6, p3, p1);
							}
							if(p5.getName().equals("Jesse")) {
								JesseUltimate(p5);
							}
							if(p5.getName().equals("Norman")) {
								NormanUltimate(p5, p3, p1);
							}
							if(p5.getName().equals("Ruby")) {
								RubyUltimate(p5, p3, p1);
							}
							if(p5.getName().equals("Augie")) {
								AugieUltimate(p5, p2, p4 ,p6);
							}
							if(p5.getName().equals("Oona")) {
								OonaUltimate(p5, p3, p1);
							}
							if(p5.getName().equals("Radar")) {
								RadarUltimate(p5, p2, p4, p6, p3, p1);
							}
							if(p5.getName().equals("Redgar")) {
								RedgarUltimate(p5);
							}
							if(p5.getName().equals("Hopper")) {
								HopperUltimate(p5, p3, p1);
							}
							if(p5.getName().equals("Chloe")) {
								ChloeUltimate(p5, p3, p1);
							}
							if(p5.getName().equals("Ayson")) {
								AysonUltimate(p5, p2, p4 ,p6);
							}
							if(p5.getName().equals("Audrey")) {
								AudreyUltimate(p5, p3, p1);
							}
							if(p5.getName().equals("Mayhem")) {
								MayhemUltimate(p5);
							}
							if(p5.getName().equals("Gash")) {
								GashUltimate(p5, p2, p4, p6);
							}
							if(p5.getName().equals("Julian")) {
								JulianUltimate(p5, p2, p4, p6, p3, p1);
							}
							if(p5.getName().equals("Airic")) {
								AiricUltimate(p5, p2, p4, p6);
							}
							if(p5.getName().equals("Mason")) {
								MasonUltimate(p5, p2, p4, p6, p3, p1);
							}
							if(p5.getName().equals("Evil")) {
								EvilUltimate(p5, p2, p4, p6);
							}
							if(p5.getName().equals("Grizz")) {
								GrizzUltimate(p5);
							}
							if(p5.getName().equals("Dimentio")) {
								DimentioUltimate(p5);
							}
							if(p5.getName().equals("Tom")) {
								Location l = SetCursor(p5, p2, p4, p6, p3, p1);
								TomUltimate(p5, p2, p4, p6, l);
							}
							if(p5.getName().equals("Archer")) {
								ArcherUltimate(p5, p2, p4, p6);
							}
							if(p5.getName().equals("Anjelika")) {
								AnjelikaUltimate(p5);
							}
							if(p5.getName().equals("Kithara")) {
								KitharaUltimate(p5, p2, p4, p6);
							}
							if(p5.getName().equals("Xara")) {
								XaraUltimate(p5, p2, p4, p6);
							}
							if(p5.getName().equals("Midnite")) {
								MidniteUltimate(p5);
							}
							if(p5.getName().equals("Katrina")) {
								KatrinaUltimate(p5, p3, p1);
							}
							if(p5.getName().equals("Axol")) {
								AxolUltimate(p5, p3, p1);
							}
							if(p5.getName().equals("Liam")) {
								LiamUltimate(p5, p2, p4, p6, p3, p1);
							}
							if(p1.getName().equals("Aidan")) {
								AidanUltimate(p5, p2, p4, p6, p3, p1);
							}
							if(p5.getName().equals("Thunder")) {
								ThunderUltimate(p5, p2, p4, p6);
							}
							if(p5.getName().equals("Clara")) {
								ClaraUltimate(p5);
							}
							if(p5.getName().equals("Sammi")) {
								SammiUltimate(p5);
							}
							if(p5.getName().equals("Rocco")) {
								RoccoUltimate(p5, p2, p4, p6);
							}
							if(p5.getName().equals("Bedrock")) {
								BedrockUltimate(p5);
							}
							if(p5.getName().equals("Ashley")) {
								AshleyUltimate(p5, p2, p4, p6, p3, p1);
							}
							if(p5.getName().equals("Kailani")) {
								KailaniUltimate(p5, p2, p4, p6);
							}
							if(p5.getName().equals("Orion")) {
								OrionUltimate(p5, p3, p1);
							}
							if(p5.getName().equals("Alex")) {
								AlexUltimate(p5);
							}
							if(p5.getName().equals("Louis")) {
								Location l = SetCursor(p5, p2, p4, p6, p3, p1);
								LouisUltimate(p5, p2, p4, p6, l);
							}
							if(p5.getName().equals("Via")) {
								ViaUltimate(p5, p2, p4 ,p6);
							}
							if(p5.getName().equals("Max")) {
								MaxUltimate(p5, p2, p4 ,p6);
							}
							if(p5.getName().equals("Cherry")) {
								CherryUltimate(p5);
							}
							if(p5.getName().equals("Bolo")) {
								BoloUltimate(p5, p2, p4 ,p6);
							}
							if(p5.getName().equals("Mack")) {
								MackUltimate(p5);
							}
							if(p5.getName().equals("Finley")) {
								FinleyUltimate(p5, p2, p4, p6);
							}
							if(p5.getName().equals("Zero")) {
								ZeroUltimate(p5);
							}
							if(p5.getName().equals("Burt")) {
								BurtUltimate(p5, p2, p4, p6);
							}
							if(p5.getName().equals("Eli")) {
								EliUltimate(p5, p3, p1);
							}
							if(p5.getName().equals("Solar")) {
								SolarUltimate(p5, p2, p4, p6);
							}
							if(p5.getName().equals("Dylan")) {
								DylanUltimate(p5, p2, p4, p6, p3, p1);
							}
							if(p5.getName().equals("Gates")) {
								GatesUltimate(p5);
							}
							if(p5.getName().equals("Lunar")) {
								LunarUltimate(p5, p2, p4, p6);
							}
						}
					}
					if(response.equals("a") && !p5.onCooldown()) {
						if(p5.isDazed()) {
							System.out.println("Cannot use ability when dazed!");
							System.out.println();
						}else {
							if(p5.getName().equals("Lunar")) {
								LunarAbility(p5);
							}
							if(p5.getName().equals("Solar")) {
								SolarAbility(p5);
							}
							if(p5.getName().equals("Mack")) {
								Location l = SetCursor(p5, p2, p4, p6, p3, p1);
								MackAbility(p5, p2, p4, p6, l);
							}
							if(p5.getName().equals("Cherry")) {
								CherryAbility(p5, p2, p4, p6);
							}
							if(p5.getName().equals("Finley")) {
								FinleyAbility(p5, p2, p4, p6);
							}
							if(p5.getName().equals("Burt")) {
								BurtAbility(p5);
							}
							if(p5.getName().equals("Bolo")) {
								BoloAbility(p5);
							}
							if(p5.getName().equals("Dylan")) {
								DylanAbility(p5, p2, p4 ,p6);
							}
							if(p5.getName().equals("Zero")) {
								ZeroAbility(p5, p3, p1);
							}
							if(p5.getName().equals("Max")) {
								MaxAbility(p5, p1, p3);
							}
							if(p5.getName().equals("Eli")) {
								EliAbility(p5, p3, p1);
							}
							if(p5.getName().equals("Via")) {
								Location l = SetCursor(p5, p2, p4, p6, p3, p1);
								ViaAbility(p5, p2, p4, p6, l);
							}
							if(p5.getName().equals("Louis")) {
								LouisAbility(p5, p2, p4 ,p6);
							}
							if(p5.getName().equals("Alex")) {
								AlexAbility(p5, p3, p1);
							}
							if(p5.getName().equals("Orion")) {
								OrionAbility(p5, p3, p1);
							}
							if(p5.getName().equals("Kailani")) {
								Location l = SetCursor(p5, p2, p4, p6, p3, p1);
								KailaniAbility(p5, p2, p4, p6, l);
							}
							if(p5.getName().equals("Ashley")) {
								AshleyAbility(p5, p2, p4, p6, p3, p1);
							}
							if(p5.getName().equals("Bedrock")) {
								BedrockAbility(p5);
							}
							if(p5.getName().equals("Rocco")) {
								RoccoAbility(p5, p2, p4 ,p6);
							}
							if(p5.getName().equals("Sammi")) {
								SammiAbility(p5);
							}
							if(p5.getName().equals("Clara")) {
								ClaraAbility(p5);
							}
							if(p5.getName().equals("Thunder")) {
								ThunderAbility(p5);
							}
							if(p5.getName().equals("Aidan")) {
								AidanAbility(p5, p3, p1);
							}
							if(p5.getName().equals("Liam")) {
								LiamAbility(p5, p3, p1);
							}
							if(p5.getName().equals("Axol")) {
								AxolAbility(p5, p3, p1);
							}
							if(p5.getName().equals("Katrina")) {
								KatrinaAbility(p5, p3, p1);
							}
							if(p5.getName().equals("Midnite")) {
								MidniteAbility(p5, p2, p4, p6, p3, p1);
							}
							if(p5.getName().equals("Xara")) {
								XaraAbility(p5, p3, p1);
							}
							if(p5.getName().equals("Kithara")) {
								KitharaAbility(p5, p3, p1);
							}
							if(p5.getName().equals("Anjelika")) {
								AnjelikaAbility(p5, p2, p4 ,p6);
							}
							if(p5.getName().equals("Archer")) {
								ArcherAbility(p5, p3, p1);
							}
							if(p5.getName().equals("Tom")) {
								TomAbility(p5);
							}
							if(p5.getName().equals("Dimentio")) {
								DimentioAbility(p5, p2, p4 ,p6);
							}
							if(p5.getName().equals("Grizz")) {
								GrizzAbility(p5, p3, p1);
							}
							if(p5.getName().equals("Evil")) {
								EvilAbility(p5);
							}
							if(p5.getName().equals("Mason")) {
								Location l = SetCursor(p5, p2, p4, p6, p3, p1);
								MasonAbility(p5, p2, p4, p6, l);
							}
							if(p5.getName().equals("Airic")) {
								Location l = SetCursor(p5, p2, p4, p6, p3, p1);
								AiricAbility(p5, p3, p1, l);
							}
							if(p5.getName().equals("Julian")) {
								JulianAbility(p5);
							}
							if(p5.getName().equals("Gash")) {
								GashAbility(p5, p1, p3);
							}
							if(p5.getName().equals("Mayhem")) {
								MayhemAbility(p5, p2, p4 ,p6);
							}
							if(p5.getName().equals("Gates")) {
								GatesAbility(p5, p3, p1);
							}
							if(p5.getName().equals("Audrey")) {
								AudreyAbility(p5, p2, p4 ,p6);
							}
							if(p5.getName().equals("Ayson")) {
								AysonAbility(p5, p2, p4, p6, p3, p1);
							}
							if(p5.getName().equals("Chloe")) {
								ChloeAbility(p5, p3, p1);
							}
							if(p5.getName().equals("Hopper")) {
								HopperAbility(p5, p3, p1);
							}
							if(p5.getName().equals("Redgar")) {
								RedgarAbility(p5, p3, p1);
							}
							if(p5.getName().equals("Radar")) {
								RadarAbility(p5, p2, p4 ,p6);
							}
							if(p5.getName().equals("Oona")) {
								OonaAbility(p5, p2, p4 ,p6);
							}
							if(p5.getName().equals("Augie")) {
								AugieAbility(p5, p3, p1);
							}
							if(p5.getName().equals("Ruby")) {
								Location l = SetCursor(p5, p2, p4, p6, p3, p1);
								RubyAbility(p5, l);
							}
							if(p5.getName().equals("Norman")) {
								NormanAbility(p5, p2, p4 ,p6);
							}
							if(p5.getName().equals("Jesse")) {
								JesseAbility(p5);
							}
							if(p5.getName().equals("Chief")) {
								ChiefAbility(p5, p3, p1);
							}
						}
					}
					if(response.equals("w")) {
						if(p5.isFreezed()) {
							System.out.println("Cannot use weapon while freezed!");
							System.out.println();
						}else {
							if(p5.hasAttacked()) {
								System.out.println(p5.getName() + " has already attacked this turn!");
								System.out.println();
							}else {
								if(p5.getName().equals("Jesse")) {
									JesseAttack(p5, p2, p4, p6);
								}else if(p5.getName().equals("Audrey")) {
									AudreyAttack(p5, p2, p4, p6);
								}else if(p5.getName().equals("Bedrock")) {
									BedrockAttack(p5, p2, p4 ,p6);
								}else if(p5.getName().equals("Max")) {
									MaxAttack(p5, p2, p4 ,p6);
								}else if(p5.getName().equals("Cherry")) {
									CherryAttack(p5, p2, p4, p6, p1, p3);
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
											if(p5.getName().equals("Lunar")) {
												LunarAttack(p5, p2, p4, p6);
											}
											if(p5.getName().equals("Solar")) {
												SolarAttack(p5, p2);
											}
											if(p5.getName().equals("Mack")) {
												MackAttack(p5, p2);
											}
											if(p5.getName().equals("Finley")) {
												FinleyAttack(p5, p2);
											}
											if(p5.getName().equals("Burt")) {
												BurtAttack(p5, p2);
											}
											if(p5.getName().equals("Bolo")) {
												BoloAttack(p5, p2);
											}
											if(p5.getName().equals("Zero")) {
												ZeroAttack(p5, p2, p4, p6);
											}
											if(p5.getName().equals("Dylan")) {
												DylanAttack(p5, p2);
											}
											if(p5.getName().equals("Eli")) {
												EliAttack(p5, p2);
											}
											if(p5.getName().equals("Via")) {
												ViaAttack(p5, p2, p4 ,p6);
											}
											if(p5.getName().equals("Louis")) {
												LouisAttack(p5, p2);
											}
											if(p5.getName().equals("Alex")) {
												AlexAttack(p5, p2);
											}
											if(p5.getName().equals("Orion")) {
												OrionAttack(p5, p2);
											}
											if(p5.getName().equals("Kailani")) {
												KailaniAttack(p5, p2);
											}
											if(p5.getName().equals("Ashley")) {
												AshleyAttack(p5, p2);
											}
											if(p5.getName().equals("Rocco")) {
												RoccoAttack(p5, p2, p4, p6);
											}
											if(p5.getName().equals("Sammi")) {
												SammiAttack(p5, p2);
											}
											if(p5.getName().equals("Clara")) {
												ClaraAttack(p5, p2);
											}
											if(p5.getName().equals("Thunder")) {
												ThunderAttack(p5, p2, p4, p6);
											}
											if(p5.getName().equals("Aidan")) {
												AidanAttack(p5, p2);
											}
											if(p5.getName().equals("Liam")) {
												LiamAttack(p5, p2, p4, p6);
											}
											if(p5.getName().equals("Axol")) {
												AxolAttack(p5, p2);
											}
											if(p5.getName().equals("Katrina")) {
												KatrinaAttack(p5, p2);
											}
											if(p5.getName().equals("Midnite")) {
												MidniteAttack(p5, p2);
											}
											if(p5.getName().equals("Xara")) {
												XaraAttack(p5, p2);
											}
											if(p5.getName().equals("Kithara")) {
												KitharaAttack(p5, p2);
											}
											if(p5.getName().equals("Anjelika")) {
												AnjelikaAttack(p5, p2);
											}
											if(p5.getName().equals("Archer")) {
												ArcherAttack(p5, p2);
											}
											if(p5.getName().equals("Tom")) {
												TomAttack(p5, p2);
											}
											if(p5.getName().equals("Dimentio")) {
												DimentioAttack(p5, p2);
											}
											if(p5.getName().equals("Grizz")) {
												GrizzAttack(p5, p2, p4, p6);
											}
											if(p5.getName().equals("Evil")) {
												EvilAttack(p5, p2, p4, p6);
											}
											if(p5.getName().equals("Mason")) {
												MasonAttack(p5, p2);
											}
											if(p5.getName().equals("Airic")) {
												AiricAttack(p5, p2);
											}
											if(p5.getName().equals("Julian")) {
												JulianAttack(p5, p2, p4, p6);
											}
											if(p5.getName().equals("Gash")) {
												GashAttack(p5, p2);
											}
											if(p5.getName().equals("Mayhem")) {
												MayhemAttack(p5, p2);
											}
											if(p5.getName().equals("Gates")) {
												Location l = SetCursor(p5, p2, p4, p6, p1, p3);
												GatesAttack(p5, p2, l);
											}
											if(p5.getName().equals("Ayson")) {
												AysonAttack(p5, p2);
											}
											if(p5.getName().equals("Chloe")) {
												ChloeAttack(p5, p2, p1, p3);
											}
											if(p5.getName().equals("Hopper")) {
												HopperAttack(p5, p2);
											}
											if(p5.getName().equals("Redgar")) {
												RedgarAttack(p5, p2);
											}
											if(p5.getName().equals("Radar")) {
												RadarAttack(p5, p2);
											}
											if(p5.getName().equals("Oona")) {
												OonaAttack(p5, p2);
											}
											if(p5.getName().equals("Augie")) {
												AugieAttack(p5, p2);
											}
											if(p5.getName().equals("Ruby")) {
												RubyAttack(p5, p2);
											}
											if(p5.getName().equals("Norman")) {
												NormanAttack(p5, p2, p1, p3);
											}
											if(p5.getName().equals("Chief")) {
												ChiefAttack(p5, p2);
											}
										}
									}
									if(attackResponse.equals("2")) {
										if(!p5.inRange(p4)) {
											System.out.println();
											System.out.println("Target is out of range!");
											System.out.println();
										}else {
											if(p5.getName().equals("Lunar")) {
												LunarAttack(p5, p4, p2, p6);
											}
											if(p5.getName().equals("Solar")) {
												SolarAttack(p5, p4);
											}
											if(p5.getName().equals("Mack")) {
												MackAttack(p5, p4);
											}
											if(p5.getName().equals("Finley")) {
												FinleyAttack(p5, p4);
											}
											if(p5.getName().equals("Burt")) {
												BurtAttack(p5, p4);
											}
											if(p5.getName().equals("Bolo")) {
												BoloAttack(p5, p4);
											}
											if(p5.getName().equals("Zero")) {
												ZeroAttack(p5, p4, p2, p6);
											}
											if(p5.getName().equals("Dylan")) {
												DylanAttack(p5, p4);
											}
											if(p5.getName().equals("Eli")) {
												EliAttack(p5, p4);
											}
											if(p5.getName().equals("Via")) {
												ViaAttack(p5, p4, p2 ,p6);
											}
											if(p5.getName().equals("Louis")) {
												LouisAttack(p5, p4);
											}
											if(p5.getName().equals("Alex")) {
												AlexAttack(p5, p4);
											}
											if(p5.getName().equals("Orion")) {
												OrionAttack(p5, p4);
											}
											if(p5.getName().equals("Kailani")) {
												KailaniAttack(p5, p4);
											}
											if(p5.getName().equals("Ashley")) {
												AshleyAttack(p5, p4);
											}
											if(p5.getName().equals("Rocco")) {
												RoccoAttack(p5, p4, p2, p6);
											}
											if(p5.getName().equals("Sammi")) {
												SammiAttack(p5, p4);
											}
											if(p5.getName().equals("Clara")) {
												ClaraAttack(p5, p4);
											}
											if(p5.getName().equals("Thunder")) {
												ThunderAttack(p5, p4, p2, p6);
											}
											if(p5.getName().equals("Aidan")) {
												AidanAttack(p5, p4);
											}
											if(p5.getName().equals("Liam")) {
												LiamAttack(p5, p4, p2, p6);
											}
											if(p5.getName().equals("Axol")) {
												AxolAttack(p5, p4);
											}
											if(p5.getName().equals("Katrina")) {
												KatrinaAttack(p5, p4);
											}
											if(p5.getName().equals("Midnite")) {
												MidniteAttack(p5, p4);
											}
											if(p5.getName().equals("Xara")) {
												XaraAttack(p5, p4);
											}
											if(p5.getName().equals("Kithara")) {
												KitharaAttack(p5, p4);
											}
											if(p5.getName().equals("Anjelika")) {
												AnjelikaAttack(p5, p4);
											}
											if(p5.getName().equals("Archer")) {
												ArcherAttack(p5, p4);
											}
											if(p5.getName().equals("Tom")) {
												TomAttack(p5, p4);
											}
											if(p5.getName().equals("Dimentio")) {
												DimentioAttack(p5, p4);
											}
											if(p5.getName().equals("Grizz")) {
												GrizzAttack(p5, p4, p2, p6);
											}
											if(p5.getName().equals("Evil")) {
												EvilAttack(p5, p4, p2, p6);
											}
											if(p5.getName().equals("Mason")) {
												MasonAttack(p5, p4);
											}
											if(p5.getName().equals("Airic")) {
												AiricAttack(p5, p4);
											}
											if(p5.getName().equals("Julian")) {
												JulianAttack(p5, p4, p2, p6);
											}
											if(p5.getName().equals("Gash")) {
												GashAttack(p5, p4);
											}
											if(p5.getName().equals("Mayhem")) {
												MayhemAttack(p5, p4);
											}
											if(p5.getName().equals("Gates")) {
												Location l = SetCursor(p5, p2, p4, p6, p1, p3);
												GatesAttack(p5, p4, l);
											}
											if(p5.getName().equals("Ayson")) {
												AysonAttack(p5, p4);
											}
											if(p5.getName().equals("Chloe")) {
												ChloeAttack(p5, p4, p1, p3);
											}
											if(p5.getName().equals("Hopper")) {
												HopperAttack(p5, p4);
											}
											if(p5.getName().equals("Redgar")) {
												RedgarAttack(p5, p4);
											}
											if(p5.getName().equals("Radar")) {
												RadarAttack(p5, p4);
											}
											if(p5.getName().equals("Oona")) {
												OonaAttack(p5, p4);
											}
											if(p5.getName().equals("Augie")) {
												AugieAttack(p5, p4);
											}
											if(p5.getName().equals("Ruby")) {
												RubyAttack(p5, p4);
											}
											if(p5.getName().equals("Norman")) {
												NormanAttack(p5, p4, p1, p3);
											}
											if(p5.getName().equals("Chief")) {
												ChiefAttack(p5, p4);
											}
										}
									}
									if(attackResponse.equals("3")) {
										if(!p5.inRange(p6)) {
											System.out.println();
											System.out.println("Target is out of range!");
											System.out.println();
										}else {
											if(p5.getName().equals("Lunar")) {
												LunarAttack(p5, p6, p4, p2);
											}
											if(p5.getName().equals("Solar")) {
												SolarAttack(p5, p6);
											}
											if(p5.getName().equals("Mack")) {
												MackAttack(p5, p6);
											}
											if(p5.getName().equals("Finley")) {
												FinleyAttack(p5, p6);
											}
											if(p5.getName().equals("Burt")) {
												BurtAttack(p5, p6);
											}
											if(p5.getName().equals("Bolo")) {
												BoloAttack(p5, p6);
											}
											if(p5.getName().equals("Zero")) {
												ZeroAttack(p5, p6, p4, p2);
											}
											if(p5.getName().equals("Dylan")) {
												DylanAttack(p5, p6);
											}
											if(p5.getName().equals("Eli")) {
												EliAttack(p5, p6);
											}
											if(p5.getName().equals("Via")) {
												ViaAttack(p5, p6, p4 ,p2);
											}
											if(p5.getName().equals("Louis")) {
												LouisAttack(p5, p6);
											}
											if(p5.getName().equals("Alex")) {
												AlexAttack(p5, p6);
											}
											if(p5.getName().equals("Orion")) {
												OrionAttack(p5, p6);
											}
											if(p5.getName().equals("Kailani")) {
												KailaniAttack(p5, p6);
											}
											if(p5.getName().equals("Ashley")) {
												AshleyAttack(p5, p6);
											}
											if(p5.getName().equals("Rocco")) {
												RoccoAttack(p5, p6, p4, p2);
											}
											if(p5.getName().equals("Sammi")) {
												SammiAttack(p5, p6);
											}
											if(p5.getName().equals("Clara")) {
												ClaraAttack(p5, p6);
											}
											if(p5.getName().equals("Thunder")) {
												ThunderAttack(p5, p6, p4, p2);
											}
											if(p5.getName().equals("Aidan")) {
												AidanAttack(p5, p6);
											}
											if(p5.getName().equals("Liam")) {
												LiamAttack(p5, p6, p4, p2);
											}
											if(p5.getName().equals("Axol")) {
												AxolAttack(p5, p6);
											}
											if(p5.getName().equals("Katrina")) {
												KatrinaAttack(p5, p6);
											}
											if(p5.getName().equals("Midnite")) {
												MidniteAttack(p5, p6);
											}
											if(p5.getName().equals("Xara")) {
												XaraAttack(p5, p6);
											}
											if(p5.getName().equals("Kithara")) {
												KitharaAttack(p5, p6);
											}
											if(p5.getName().equals("Anjelika")) {
												AnjelikaAttack(p5, p6);
											}
											if(p5.getName().equals("Archer")) {
												ArcherAttack(p5, p6);
											}
											if(p5.getName().equals("Tom")) {
												TomAttack(p5, p6);
											}
											if(p5.getName().equals("Dimentio")) {
												DimentioAttack(p5, p6);
											}
											if(p5.getName().equals("Grizz")) {
												GrizzAttack(p5, p6, p4, p2);
											}
											if(p5.getName().equals("Evil")) {
												EvilAttack(p5, p6, p4, p2);
											}
											if(p5.getName().equals("Mason")) {
												MasonAttack(p5, p6);
											}
											if(p5.getName().equals("Airic")) {
												AiricAttack(p5, p6);
											}
											if(p5.getName().equals("Julian")) {
												JulianAttack(p5, p6, p4, p2);
											}
											if(p5.getName().equals("Gash")) {
												GashAttack(p5, p6);
											}
											if(p5.getName().equals("Mayhem")) {
												MayhemAttack(p5, p6);
											}
											if(p5.getName().equals("Gates")) {
												Location l = SetCursor(p5, p2, p4, p6, p1, p3);
												GatesAttack(p5, p6, l);
											}
											if(p5.getName().equals("Ayson")) {
												AysonAttack(p5, p6);
											}
											if(p5.getName().equals("Chloe")) {
												ChloeAttack(p5, p6, p1, p3);
											}
											if(p5.getName().equals("Hopper")) {
												HopperAttack(p5, p6);
											}
											if(p5.getName().equals("Redgar")) {
												RedgarAttack(p5, p6);
											}
											if(p5.getName().equals("Radar")) {
												RadarAttack(p5, p6);
											}
											if(p5.getName().equals("Oona")) {
												OonaAttack(p5, p6);
											}
											if(p5.getName().equals("Augie")) {
												AugieAttack(p5, p6);
											}
											if(p5.getName().equals("Ruby")) {
												RubyAttack(p5, p6);
											}
											if(p5.getName().equals("Norman")) {
												NormanAttack(p5, p6, p1, p3);
											}
											if(p5.getName().equals("Chief")) {
												ChiefAttack(p5, p6);
											}
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
			if(turns >= 5 && play3) {
				try {
					audioPlayer.stop();
					String audio = "orbtime.wav";
					audioPlayer = new Music(audio, false);
					audioPlayer.play();
					play3 = false;
				}catch (Exception e) {
					System.out.println(e);
				}
			}
			if(turns >= 15 && play2) {
				try {
					audioPlayer.stop();
					String audio = "overtime.wav";
					audioPlayer = new Music(audio, false);
					audioPlayer.play();
					String audio2 = "overtimeline.wav";
					Music audioPlayer = new Music(audio2, false);
					audioPlayer.play();
					play2 = false;
				}catch (Exception e) {
					System.out.println(e);
				}
			}
			if(turns >= 29 && play4) {
				try {
					b.endGame();
					audioPlayer.stop();
					String audio = "endgame.wav";
					audioPlayer = new Music(audio, false);
					audioPlayer.play();
					String audio2 = "endgameline.wav";
					Music audioPlayer = new Music(audio2, false);
					audioPlayer.play();
					play4 = false;
				}catch (Exception e) {
					System.out.println(e);
				}
			}
			while(party2.getTurn()) {
				if(party2.teamDown()) {
					break;
				}
				if(party2.oneLeft() && play) {
					try {
						audioPlayer.stop();
						String audio = "dramaticedit.wav";
						audioPlayer = new Music(audio, false);
						audioPlayer.play();
						String audio2 = "lastplayer.wav";
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
				}
				while(p2.getTurn()) {
					System.out.println("Turn: " + tD);
					System.out.println("Team B's Turn (Go " + p2.getSkin() + "!)");
					System.out.println(p2);
					System.out.print("What would " + p2.getName() + " like to do: ");
					String response = input.next();
					System.out.println();
					if(response.equals("b")){
						b.printField(p1, p2, p3, p4, p5, p6, orbs, cover, p2, p1, p3, p5);
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
							System.out.println();
						}
						if(switchResponse.equals("3")) {
							p2.passTurn(p6);
							System.out.println();
						}
					}
					if(response.equals("p")) {
						System.out.print("Are you sure you want to pass to the enemy's turn: ");
						String switchResponse = input.next();
						if(switchResponse.equals("p")) {
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
					if(response.equals("i")) {
						CheckProfile(p2, party1);
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
					if(response.equals("d")) {
						Dash(p2, p1, p3, p5);
					}
					if(response.equals("j")) {
						Location l = SetCursor(p2, p1, p3, p5, p4, p6);
						Jump(p2, p4, p6, l);
					}
					if(response.equals("u")) {
						if(!p2.ultReady() || p2.ultActive()) {
							System.out.println("My ultimate cannot be used!");
							System.out.println();
						}else {
							if(p2.getName().equals("Chief")) {
								ChiefUltimate(p2, p1, p3, p5, p4, p6);
							}
							if(p2.getName().equals("Jesse")) {
								JesseUltimate(p2);
							}
							if(p2.getName().equals("Norman")) {
								NormanUltimate(p2, p4, p6);
							}
							if(p2.getName().equals("Ruby")) {
								RubyUltimate(p2, p4, p6);
							}
							if(p2.getName().equals("Augie")) {
								AugieUltimate(p2, p1, p3 ,p5);
							}
							if(p2.getName().equals("Oona")) {
								OonaUltimate(p2, p4, p6);
							}
							if(p2.getName().equals("Radar")) {
								RadarUltimate(p2, p1, p3, p5, p4, p6);
							}
							if(p2.getName().equals("Redgar")) {
								RedgarUltimate(p2);
							}
							if(p2.getName().equals("Hopper")) {
								HopperUltimate(p2, p4, p6);
							}
							if(p2.getName().equals("Chloe")) {
								ChloeUltimate(p2, p4, p6);
							}
							if(p2.getName().equals("Ayson")) {
								AysonUltimate(p2, p1, p3 ,p5);
							}
							if(p2.getName().equals("Audrey")) {
								AudreyUltimate(p2, p4, p6);
							}
							if(p2.getName().equals("Mayhem")) {
								MayhemUltimate(p2);
							}
							if(p2.getName().equals("Gash")) {
								GashUltimate(p2, p1, p3, p5);
							}
							if(p2.getName().equals("Julian")) {
								JulianUltimate(p2, p1, p3, p5, p4, p6);
							}
							if(p2.getName().equals("Airic")) {
								AiricUltimate(p2, p1, p3, p5);
							}
							if(p2.getName().equals("Mason")) {
								MasonUltimate(p2, p1, p3, p5, p4, p6);
							}
							if(p2.getName().equals("Evil")) {
								EvilUltimate(p2, p1, p3, p5);
							}
							if(p2.getName().equals("Grizz")) {
								GrizzUltimate(p2);
							}
							if(p2.getName().equals("Dimentio")) {
								DimentioUltimate(p2);
							}
							if(p2.getName().equals("Tom")) {
								Location l = SetCursor(p2, p1, p3, p5, p4, p6);
								TomUltimate(p2, p1, p3, p5, l);
							}
							if(p2.getName().equals("Archer")) {
								ArcherUltimate(p2, p1, p3, p5);
							}
							if(p2.getName().equals("Anjelika")) {
								AnjelikaUltimate(p2);
							}
							if(p2.getName().equals("Kithara")) {
								KitharaUltimate(p2, p1, p3, p5);
							}
							if(p2.getName().equals("Xara")) {
								XaraUltimate(p2, p1, p3, p5);
							}
							if(p2.getName().equals("Midnite")) {
								MidniteUltimate(p2);
							}
							if(p2.getName().equals("Katrina")) {
								KatrinaUltimate(p2, p4, p6);
							}
							if(p2.getName().equals("Axol")) {
								AxolUltimate(p2, p4, p6);
							}
							if(p2.getName().equals("Liam")) {
								LiamUltimate(p2, p1, p3, p5, p4, p6);
							}
							if(p2.getName().equals("Aidan")) {
								AidanUltimate(p2, p1, p3, p5, p4, p6);
							}
							if(p2.getName().equals("Thunder")) {
								ThunderUltimate(p2, p1, p3, p5);
							}
							if(p2.getName().equals("Clara")) {
								ClaraUltimate(p2);
							}
							if(p2.getName().equals("Sammi")) {
								SammiUltimate(p2);
							}
							if(p2.getName().equals("Rocco")) {
								RoccoUltimate(p2, p1, p3, p5);
							}
							if(p2.getName().equals("Bedrock")) {
								BedrockUltimate(p2);
							}
							if(p2.getName().equals("Ashley")) {
								AshleyUltimate(p2, p1, p3, p5, p4, p6);
							}
							if(p2.getName().equals("Kailani")) {
								KailaniUltimate(p2, p1, p3, p5);
							}
							if(p2.getName().equals("Orion")) {
								OrionUltimate(p2, p4, p6);
							}
							if(p2.getName().equals("Alex")) {
								AlexUltimate(p2);
							}
							if(p2.getName().equals("Louis")) {
								Location l = SetCursor(p2, p1, p3, p5, p4, p6);
								LouisUltimate(p2, p1, p3, p5, l);
							}
							if(p2.getName().equals("Via")) {
								ViaUltimate(p2, p1, p3 ,p5);
							}
							if(p2.getName().equals("Max")) {
								MaxUltimate(p2, p1, p3 ,p5);
							}
							if(p2.getName().equals("Cherry")) {
								CherryUltimate(p2);
							}
							if(p2.getName().equals("Bolo")) {
								BoloUltimate(p2, p1, p3 ,p5);
							}
							if(p2.getName().equals("Mack")) {
								MackUltimate(p2);
							}
							if(p2.getName().equals("Finley")) {
								FinleyUltimate(p2, p1, p3, p5);
							}
							if(p2.getName().equals("Zero")) {
								ZeroUltimate(p2);
							}
							if(p2.getName().equals("Burt")) {
								BurtUltimate(p2, p1, p3, p5);
							}
							if(p2.getName().equals("Eli")) {
								EliUltimate(p2, p4, p6);
							}
							if(p2.getName().equals("Solar")) {
								SolarUltimate(p2, p1, p3, p5);
							}
							if(p2.getName().equals("Dylan")) {
								DylanUltimate(p2, p1, p3, p5, p4, p6);
							}
							if(p2.getName().equals("Gates")) {
								GatesUltimate(p2);
							}
							if(p2.getName().equals("Lunar")) {
								LunarUltimate(p2, p1, p3, p5);
							}
						}
					}
					if(response.equals("a") && !p2.onCooldown()) {
						if(p2.isDazed()) {
							System.out.println("Cannot use ability when dazed!");
							System.out.println();
						}else {
							if(p2.getName().equals("Lunar")) {
								LunarAbility(p2);
							}
							if(p2.getName().equals("Solar")) {
								SolarAbility(p2);
							}
							if(p2.getName().equals("Mack")) {
								Location l = SetCursor(p2, p1, p3, p5, p4, p6);
								MackAbility(p2, p1, p3, p5, l);
							}
							if(p2.getName().equals("Cherry")) {
								CherryAbility(p2, p1, p3, p5);
							}
							if(p2.getName().equals("Finley")) {
								FinleyAbility(p2, p1, p3, p5);
							}
							if(p2.getName().equals("Burt")) {
								BurtAbility(p2);
							}
							if(p2.getName().equals("Bolo")) {
								BoloAbility(p2);
							}
							if(p2.getName().equals("Dylan")) {
								DylanAbility(p2, p1, p3 ,p5);
							}
							if(p2.getName().equals("Zero")) {
								ZeroAbility(p2, p4, p6);
							}
							if(p2.getName().equals("Max")) {
								MaxAbility(p2, p4, p6);
							}
							if(p2.getName().equals("Eli")) {
								EliAbility(p2, p4, p6);
							}
							if(p2.getName().equals("Via")) {
								Location l = SetCursor(p2, p1, p3, p5, p4, p6);
								ViaAbility(p2, p1, p3, p5, l);
							}
							if(p2.getName().equals("Louis")) {
								LouisAbility(p2, p1, p3 ,p5);
							}
							if(p2.getName().equals("Alex")) {
								AlexAbility(p2, p4, p6);
							}
							if(p2.getName().equals("Orion")) {
								OrionAbility(p2, p4, p6);
							}
							if(p2.getName().equals("Kailani")) {
								Location l = SetCursor(p2, p1, p3, p5, p4, p6);
								KailaniAbility(p2, p1, p3, p5, l);
							}
							if(p2.getName().equals("Ashley")) {
								AshleyAbility(p2, p1, p3, p5, p4, p6);
							}
							if(p2.getName().equals("Bedrock")) {
								BedrockAbility(p2);
							}
							if(p2.getName().equals("Rocco")) {
								RoccoAbility(p2, p1, p3 ,p5);
							}
							if(p2.getName().equals("Sammi")) {
								SammiAbility(p2);
							}
							if(p2.getName().equals("Clara")) {
								ClaraAbility(p2);
							}
							if(p2.getName().equals("Thunder")) {
								ThunderAbility(p2);
							}
							if(p2.getName().equals("Aidan")) {
								AidanAbility(p2, p4, p6);
							}
							if(p2.getName().equals("Liam")) {
								LiamAbility(p2, p4, p6);
							}
							if(p2.getName().equals("Axol")) {
								AxolAbility(p2, p4, p6);
							}
							if(p2.getName().equals("Katrina")) {
								KatrinaAbility(p2, p4, p6);
							}
							if(p2.getName().equals("Midnite")) {
								MidniteAbility(p2, p1, p3, p5, p4, p6);
							}
							if(p2.getName().equals("Xara")) {
								XaraAbility(p2, p4, p6);
							}
							if(p2.getName().equals("Kithara")) {
								KitharaAbility(p2, p4, p6);
							}
							if(p2.getName().equals("Anjelika")) {
								AnjelikaAbility(p2, p1, p3 ,p5);
							}
							if(p2.getName().equals("Archer")) {
								ArcherAbility(p2, p4, p6);
							}
							if(p2.getName().equals("Tom")) {
								TomAbility(p2);
							}
							if(p2.getName().equals("Dimentio")) {
								DimentioAbility(p2, p1, p3 ,p5);
							}
							if(p2.getName().equals("Grizz")) {
								GrizzAbility(p2, p4, p6);
							}
							if(p2.getName().equals("Evil")) {
								EvilAbility(p2);
							}
							if(p2.getName().equals("Mason")) {
								Location l = SetCursor(p2, p1, p3, p5, p4, p6);
								MasonAbility(p2, p1, p3, p5, l);
							}
							if(p2.getName().equals("Airic")) {
								Location l = SetCursor(p2, p1, p3, p5, p4, p6);
								AiricAbility(p2, p4, p6, l);
							}
							if(p2.getName().equals("Julian")) {
								JulianAbility(p2);
							}
							if(p2.getName().equals("Gash")) {
								GashAbility(p2, p4, p6);
							}
							if(p2.getName().equals("Mayhem")) {
								MayhemAbility(p2, p1, p3 ,p5);
							}
							if(p2.getName().equals("Gates")) {
								GatesAbility(p2, p4, p6);
							}
							if(p2.getName().equals("Audrey")) {
								AudreyAbility(p2, p1, p3 ,p5);
							}
							if(p2.getName().equals("Ayson")) {
								AysonAbility(p2, p1, p3, p5, p4, p6);
							}
							if(p2.getName().equals("Chloe")) {
								ChloeAbility(p2, p4, p6);
							}
							if(p2.getName().equals("Hopper")) {
								HopperAbility(p2, p4, p6);
							}
							if(p2.getName().equals("Redgar")) {
								RedgarAbility(p2, p4, p6);
							}
							if(p2.getName().equals("Radar")) {
								RadarAbility(p2, p1, p3 ,p5);
							}
							if(p2.getName().equals("Oona")) {
								OonaAbility(p2, p1, p3 ,p5);
							}
							if(p2.getName().equals("Augie")) {
								AugieAbility(p2, p4, p6);
							}
							if(p2.getName().equals("Ruby")) {
								Location l = SetCursor(p2, p1, p3, p5, p4, p6);
								RubyAbility(p2, l);
							}
							if(p2.getName().equals("Norman")) {
								NormanAbility(p2, p1, p3 ,p5);
							}
							if(p2.getName().equals("Jesse")) {
								JesseAbility(p2);
							}
							if(p2.getName().equals("Chief")) {
								ChiefAbility(p2, p4, p6);
							}
						}
					}
					if(response.equals("w")) {
						if(p2.isFreezed()) {
							System.out.println("Cannot use weapon while freezed!");
							System.out.println();
						}else {
							if(p2.hasAttacked()) {
								System.out.println(p2.getName() + " has already attacked this turn!");
								System.out.println();
							}else {
								if(p2.getName().equals("Jesse")) {
									JesseAttack(p2, p1, p3, p5);
								}else if(p2.getName().equals("Audrey")) {
									AudreyAttack(p2, p1, p3, p5);
								}else if(p2.getName().equals("Bedrock")) {
									BedrockAttack(p2, p1, p3 ,p5);
								}else if(p2.getName().equals("Max")) {
									MaxAttack(p2, p1, p3 ,p5);
								}else if(p2.getName().equals("Cherry")) {
									CherryAttack(p2, p1, p3, p5, p4, p6);
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
											if(p2.getName().equals("Lunar")) {
												LunarAttack(p2, p1, p3, p5);
											}
											if(p2.getName().equals("Solar")) {
												SolarAttack(p2, p1);
											}
											if(p2.getName().equals("Mack")) {
												MackAttack(p2, p1);
											}
											if(p2.getName().equals("Finley")) {
												FinleyAttack(p2, p1);
											}
											if(p2.getName().equals("Burt")) {
												BurtAttack(p2, p1);
											}
											if(p2.getName().equals("Bolo")) {
												BoloAttack(p2, p1);
											}
											if(p2.getName().equals("Zero")) {
												ZeroAttack(p2, p1, p3, p5);
											}
											if(p2.getName().equals("Dylan")) {
												DylanAttack(p2, p1);
											}
											if(p2.getName().equals("Eli")) {
												EliAttack(p2, p1);
											}
											if(p2.getName().equals("Via")) {
												ViaAttack(p2, p1, p3 ,p5);
											}
											if(p2.getName().equals("Louis")) {
												LouisAttack(p2, p1);
											}
											if(p2.getName().equals("Alex")) {
												AlexAttack(p2, p1);
											}
											if(p2.getName().equals("Orion")) {
												OrionAttack(p2, p1);
											}
											if(p2.getName().equals("Kailani")) {
												KailaniAttack(p2, p1);
											}
											if(p2.getName().equals("Ashley")) {
												AshleyAttack(p2, p1);
											}
											if(p2.getName().equals("Rocco")) {
												RoccoAttack(p2, p1, p3, p5);
											}
											if(p2.getName().equals("Sammi")) {
												SammiAttack(p2, p1);
											}
											if(p2.getName().equals("Clara")) {
												ClaraAttack(p2, p1);
											}
											if(p2.getName().equals("Thunder")) {
												ThunderAttack(p2, p1, p3, p5);
											}
											if(p2.getName().equals("Aidan")) {
												AidanAttack(p2, p1);
											}
											if(p2.getName().equals("Liam")) {
												LiamAttack(p2, p1, p3, p5);
											}
											if(p2.getName().equals("Axol")) {
												AxolAttack(p2, p1);
											}
											if(p2.getName().equals("Katrina")) {
												KatrinaAttack(p2, p1);
											}
											if(p2.getName().equals("Midnite")) {
												MidniteAttack(p2, p1);
											}
											if(p2.getName().equals("Xara")) {
												XaraAttack(p2, p1);
											}
											if(p2.getName().equals("Kithara")) {
												KitharaAttack(p2, p1);
											}
											if(p2.getName().equals("Anjelika")) {
												AnjelikaAttack(p2, p1);
											}
											if(p2.getName().equals("Archer")) {
												ArcherAttack(p2, p1);
											}
											if(p2.getName().equals("Tom")) {
												TomAttack(p2, p1);
											}
											if(p2.getName().equals("Dimentio")) {
												DimentioAttack(p2, p1);
											}
											if(p2.getName().equals("Grizz")) {
												GrizzAttack(p2, p1, p3, p5);
											}
											if(p2.getName().equals("Evil")) {
												EvilAttack(p2, p1, p3, p5);
											}
											if(p2.getName().equals("Mason")) {
												MasonAttack(p2, p1);
											}
											if(p2.getName().equals("Airic")) {
												AiricAttack(p2, p1);
											}
											if(p2.getName().equals("Julian")) {
												JulianAttack(p2, p1, p3, p5);
											}
											if(p2.getName().equals("Gash")) {
												GashAttack(p2, p1);
											}
											if(p2.getName().equals("Mayhem")) {
												MayhemAttack(p2, p1);
											}
											if(p2.getName().equals("Gates")) {
												Location l = SetCursor(p2, p1, p3, p5, p4, p6);
												GatesAttack(p2, p1, l);
											}
											if(p2.getName().equals("Ayson")) {
												AysonAttack(p2, p1);
											}
											if(p2.getName().equals("Chloe")) {
												ChloeAttack(p2, p1, p4, p6);
											}
											if(p2.getName().equals("Hopper")) {
												HopperAttack(p2, p1);
											}
											if(p2.getName().equals("Redgar")) {
												RedgarAttack(p2, p1);
											}
											if(p2.getName().equals("Radar")) {
												RadarAttack(p2, p1);
											}
											if(p2.getName().equals("Oona")) {
												OonaAttack(p2, p1);
											}
											if(p2.getName().equals("Augie")) {
												AugieAttack(p2, p1);
											}
											if(p2.getName().equals("Ruby")) {
												RubyAttack(p2, p1);
											}
											if(p2.getName().equals("Norman")) {
												NormanAttack(p2, p1, p4, p6);
											}
											if(p2.getName().equals("Chief")) {
												ChiefAttack(p2, p1);
											}
										}
									}
									if(attackResponse.equals("2")) {
										if(!p2.inRange(p3)) {
											System.out.println();
											System.out.println("Target is out of range!");
											System.out.println();
										}else {
											if(p2.getName().equals("Lunar")) {
												LunarAttack(p2, p3, p1, p5);
											}
											if(p2.getName().equals("Solar")) {
												SolarAttack(p2, p3);
											}
											if(p2.getName().equals("Mack")) {
												MackAttack(p2, p3);
											}
											if(p2.getName().equals("Finley")) {
												FinleyAttack(p2, p3);
											}
											if(p2.getName().equals("Burt")) {
												BurtAttack(p2, p3);
											}
											if(p2.getName().equals("Bolo")) {
												BoloAttack(p2, p3);
											}
											if(p2.getName().equals("Zero")) {
												ZeroAttack(p2, p3, p1, p5);
											}
											if(p2.getName().equals("Dylan")) {
												DylanAttack(p2, p3);
											}
											if(p2.getName().equals("Eli")) {
												EliAttack(p2, p3);
											}
											if(p2.getName().equals("Via")) {
												ViaAttack(p2, p3, p1 ,p5);
											}
											if(p2.getName().equals("Louis")) {
												LouisAttack(p2, p3);
											}
											if(p2.getName().equals("Alex")) {
												AlexAttack(p2, p3);
											}
											if(p2.getName().equals("Orion")) {
												OrionAttack(p2, p3);
											}
											if(p2.getName().equals("Kailani")) {
												KailaniAttack(p2, p3);
											}
											if(p2.getName().equals("Ashley")) {
												AshleyAttack(p2, p3);
											}
											if(p2.getName().equals("Rocco")) {
												RoccoAttack(p2, p3, p1, p5);
											}
											if(p2.getName().equals("Sammi")) {
												SammiAttack(p2, p3);
											}
											if(p2.getName().equals("Clara")) {
												ClaraAttack(p2, p3);
											}
											if(p2.getName().equals("Thunder")) {
												ThunderAttack(p2, p3, p1, p5);
											}
											if(p2.getName().equals("Aidan")) {
												AidanAttack(p2, p3);
											}
											if(p2.getName().equals("Liam")) {
												LiamAttack(p2, p3, p1, p5);
											}
											if(p2.getName().equals("Axol")) {
												AxolAttack(p2, p3);
											}
											if(p2.getName().equals("Katrina")) {
												KatrinaAttack(p2, p3);
											}
											if(p2.getName().equals("Midnite")) {
												MidniteAttack(p2, p3);
											}
											if(p2.getName().equals("Xara")) {
												XaraAttack(p2, p3);
											}
											if(p2.getName().equals("Kithara")) {
												KitharaAttack(p2, p3);
											}
											if(p2.getName().equals("Anjelika")) {
												AnjelikaAttack(p2, p3);
											}
											if(p2.getName().equals("Archer")) {
												ArcherAttack(p2, p3);
											}
											if(p2.getName().equals("Tom")) {
												TomAttack(p2, p3);
											}
											if(p2.getName().equals("Dimentio")) {
												DimentioAttack(p2, p3);
											}
											if(p2.getName().equals("Grizz")) {
												GrizzAttack(p2, p3, p1, p5);
											}
											if(p2.getName().equals("Evil")) {
												EvilAttack(p2, p3, p1, p5);
											}
											if(p2.getName().equals("Mason")) {
												MasonAttack(p2, p3);
											}
											if(p2.getName().equals("Airic")) {
												AiricAttack(p2, p3);
											}
											if(p2.getName().equals("Julian")) {
												JulianAttack(p2, p3, p1, p5);
											}
											if(p2.getName().equals("Gash")) {
												GashAttack(p2, p3);
											}
											if(p2.getName().equals("Mayhem")) {
												MayhemAttack(p2, p3);
											}
											if(p2.getName().equals("Gates")) {
												Location l = SetCursor(p2, p1, p3, p5, p4, p6);
												GatesAttack(p2, p3, l);
											}
											if(p2.getName().equals("Ayson")) {
												AysonAttack(p2, p3);
											}
											if(p2.getName().equals("Chloe")) {
												ChloeAttack(p2, p3, p4, p6);
											}
											if(p2.getName().equals("Hopper")) {
												HopperAttack(p2, p3);
											}
											if(p2.getName().equals("Redgar")) {
												RedgarAttack(p2, p3);
											}
											if(p2.getName().equals("Radar")) {
												RadarAttack(p2, p3);
											}
											if(p2.getName().equals("Oona")) {
												OonaAttack(p2, p3);
											}
											if(p2.getName().equals("Augie")) {
												AugieAttack(p2, p3);
											}
											if(p2.getName().equals("Ruby")) {
												RubyAttack(p2, p3);
											}
											if(p2.getName().equals("Norman")) {
												NormanAttack(p2, p3, p4, p6);
											}
											if(p2.getName().equals("Chief")) {
												ChiefAttack(p2, p3);
											}
										}
									}
									if(attackResponse.equals("3")) {
										if(!p2.inRange(p5)) {
											System.out.println();
											System.out.println("Target is out of range!");
											System.out.println();
										}else {
											if(p2.getName().equals("Lunar")) {
												LunarAttack(p2, p5, p3, p1);
											}
											if(p2.getName().equals("Solar")) {
												SolarAttack(p2, p5);
											}
											if(p2.getName().equals("Mack")) {
												MackAttack(p2, p5);
											}
											if(p2.getName().equals("Finley")) {
												FinleyAttack(p2, p5);
											}
											if(p2.getName().equals("Burt")) {
												BurtAttack(p2, p5);
											}
											if(p2.getName().equals("Bolo")) {
												BoloAttack(p2, p5);
											}
											if(p2.getName().equals("Zero")) {
												ZeroAttack(p2, p5, p3, p1);
											}
											if(p2.getName().equals("Dylan")) {
												DylanAttack(p2, p5);
											}
											if(p2.getName().equals("Eli")) {
												EliAttack(p2, p5);
											}
											if(p2.getName().equals("Via")) {
												ViaAttack(p2, p5, p3 ,p1);
											}
											if(p2.getName().equals("Louis")) {
												LouisAttack(p2, p5);
											}
											if(p2.getName().equals("Alex")) {
												AlexAttack(p2, p5);
											}
											if(p2.getName().equals("Orion")) {
												OrionAttack(p2, p5);
											}
											if(p2.getName().equals("Kailani")) {
												KailaniAttack(p2, p5);
											}
											if(p2.getName().equals("Ashley")) {
												AshleyAttack(p2, p5);
											}
											if(p2.getName().equals("Rocco")) {
												RoccoAttack(p2, p5, p3, p1);
											}
											if(p2.getName().equals("Sammi")) {
												SammiAttack(p2, p5);
											}
											if(p2.getName().equals("Clara")) {
												ClaraAttack(p2, p5);
											}
											if(p2.getName().equals("Thunder")) {
												ThunderAttack(p2, p5, p3, p1);
											}
											if(p2.getName().equals("Aidan")) {
												AidanAttack(p2, p5);
											}
											if(p2.getName().equals("Liam")) {
												LiamAttack(p2, p5, p3, p1);
											}
											if(p2.getName().equals("Axol")) {
												AxolAttack(p2, p5);
											}
											if(p2.getName().equals("Katrina")) {
												KatrinaAttack(p2, p5);
											}
											if(p2.getName().equals("Midnite")) {
												MidniteAttack(p2, p5);
											}
											if(p2.getName().equals("Xara")) {
												XaraAttack(p2, p5);
											}
											if(p2.getName().equals("Kithara")) {
												KitharaAttack(p2, p5);
											}
											if(p2.getName().equals("Anjelika")) {
												AnjelikaAttack(p2, p5);
											}
											if(p2.getName().equals("Archer")) {
												ArcherAttack(p2, p5);
											}
											if(p2.getName().equals("Tom")) {
												TomAttack(p2, p5);
											}
											if(p2.getName().equals("Dimentio")) {
												DimentioAttack(p2, p5);
											}
											if(p2.getName().equals("Grizz")) {
												GrizzAttack(p2, p5, p3, p1);
											}
											if(p2.getName().equals("Evil")) {
												EvilAttack(p2, p5, p3, p1);
											}
											if(p2.getName().equals("Mason")) {
												MasonAttack(p2, p5);
											}
											if(p2.getName().equals("Airic")) {
												AiricAttack(p2, p5);
											}
											if(p2.getName().equals("Julian")) {
												JulianAttack(p2, p5, p3, p1);
											}
											if(p2.getName().equals("Gash")) {
												GashAttack(p2, p5);
											}
											if(p2.getName().equals("Mayhem")) {
												MayhemAttack(p2, p5);
											}
											if(p2.getName().equals("Gates")) {
												Location l = SetCursor(p2, p1, p3, p5, p4, p6);
												GatesAttack(p2, p5, l);
											}
											if(p2.getName().equals("Ayson")) {
												AysonAttack(p2, p5);
											}
											if(p2.getName().equals("Chloe")) {
												ChloeAttack(p2, p5, p4, p6);
											}
											if(p2.getName().equals("Hopper")) {
												HopperAttack(p2, p5);
											}
											if(p2.getName().equals("Redgar")) {
												RedgarAttack(p2, p5);
											}
											if(p2.getName().equals("Radar")) {
												RadarAttack(p2, p5);
											}
											if(p2.getName().equals("Oona")) {
												OonaAttack(p2, p5);
											}
											if(p2.getName().equals("Augie")) {
												AugieAttack(p2, p5);
											}
											if(p2.getName().equals("Ruby")) {
												RubyAttack(p2, p5);
											}
											if(p2.getName().equals("Norman")) {
												NormanAttack(p2, p5, p4, p6);
											}
											if(p2.getName().equals("Chief")) {
												ChiefAttack(p2, p5);
											}
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
					System.out.println("Turn: " + tD);
					System.out.println("Team B's Turn (Go " + p4.getSkin() + "!)");
					System.out.println(p4);
					System.out.print("What would " + p4.getName() + " like to do: ");
					String response = input.next();
					System.out.println();
					if(response.equals("b")){
						b.printField(p1, p2, p3, p4, p5, p6, orbs, cover, p4, p1, p3, p5);
					}
					if(response.equals("s")) {
						party2.showRoster();
						System.out.print("Who do you want to switch to: ");
						String switchResponse = input.next();
						if(switchResponse.equals("1")) {
							p4.passTurn(p2);
							System.out.println();
						}
						if(switchResponse.equals("2")) {
							p4.passTurn(p4);
							System.out.println();
						}
						if(switchResponse.equals("3")) {
							p4.passTurn(p6);
							System.out.println();
						}
					}
					if(response.equals("p")) {
						System.out.print("Are you sure you want to pass to the enemy's turn: ");
						String switchResponse = input.next();
						if(switchResponse.equals("p")) {
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
					if(response.equals("i")) {
						CheckProfile(p4, party1);
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
					if(response.equals("d")) {
						Dash(p4, p1, p3, p5);
					}
					if(response.equals("j")) {
						Location l = SetCursor(p4, p1, p3, p5, p2, p6);
						Jump(p4, p2, p6, l);
					}
					if(response.equals("u")) {
						if(!p4.ultReady() || p4.ultActive()) {
							System.out.println("My ultimate cannot be used!");
							System.out.println();
						}else {
							if(p4.getName().equals("Chief")) {
								ChiefUltimate(p4, p1, p3, p5, p2, p6);
							}
							if(p4.getName().equals("Jesse")) {
								JesseUltimate(p4);
							}
							if(p4.getName().equals("Norman")) {
								NormanUltimate(p4, p2, p6);
							}
							if(p4.getName().equals("Ruby")) {
								RubyUltimate(p4, p2, p6);
							}
							if(p4.getName().equals("Augie")) {
								AugieUltimate(p4, p1, p3 ,p5);
							}
							if(p4.getName().equals("Oona")) {
								OonaUltimate(p4, p2, p6);
							}
							if(p4.getName().equals("Radar")) {
								RadarUltimate(p4, p1, p3, p5, p2, p6);
							}
							if(p4.getName().equals("Redgar")) {
								RedgarUltimate(p4);
							}
							if(p4.getName().equals("Hopper")) {
								HopperUltimate(p4, p2, p6);
							}
							if(p4.getName().equals("Chloe")) {
								ChloeUltimate(p4, p2, p6);
							}
							if(p4.getName().equals("Ayson")) {
								AysonUltimate(p4, p1, p3 ,p5);
							}
							if(p4.getName().equals("Audrey")) {
								AudreyUltimate(p4, p2, p6);
							}
							if(p4.getName().equals("Mayhem")) {
								MayhemUltimate(p4);
							}
							if(p4.getName().equals("Gash")) {
								GashUltimate(p4, p1, p3, p5);
							}
							if(p4.getName().equals("Julian")) {
								JulianUltimate(p4, p1, p3, p5, p2, p6);
							}
							if(p4.getName().equals("Airic")) {
								AiricUltimate(p4, p1, p3, p5);
							}
							if(p4.getName().equals("Mason")) {
								MasonUltimate(p4, p1, p3, p5, p2, p6);
							}
							if(p4.getName().equals("Evil")) {
								EvilUltimate(p4, p1, p3, p5);
							}
							if(p4.getName().equals("Grizz")) {
								GrizzUltimate(p4);
							}
							if(p4.getName().equals("Dimentio")) {
								DimentioUltimate(p4);
							}
							if(p4.getName().equals("Tom")) {
								Location l = SetCursor(p4, p1, p3, p5, p2, p6);
								TomUltimate(p4, p1, p3, p5, l);
							}
							if(p4.getName().equals("Archer")) {
								ArcherUltimate(p4, p1, p3, p5);
							}
							if(p4.getName().equals("Anjelika")) {
								AnjelikaUltimate(p4);
							}
							if(p4.getName().equals("Kithara")) {
								KitharaUltimate(p4, p1, p3, p5);
							}
							if(p4.getName().equals("Xara")) {
								XaraUltimate(p4, p1, p3, p5);
							}
							if(p4.getName().equals("Midnite")) {
								MidniteUltimate(p4);
							}
							if(p4.getName().equals("Katrina")) {
								KatrinaUltimate(p4, p2, p6);
							}
							if(p4.getName().equals("Axol")) {
								AxolUltimate(p4, p2, p6);
							}
							if(p4.getName().equals("Liam")) {
								LiamUltimate(p4, p1, p3, p5, p2, p6);
							}
							if(p4.getName().equals("Aidan")) {
								AidanUltimate(p4, p1, p3, p5, p2, p6);
							}
							if(p4.getName().equals("Thunder")) {
								ThunderUltimate(p4, p1, p3, p5);
							}
							if(p4.getName().equals("Clara")) {
								ClaraUltimate(p4);
							}
							if(p4.getName().equals("Sammi")) {
								SammiUltimate(p4);
							}
							if(p4.getName().equals("Rocco")) {
								RoccoUltimate(p4, p1, p3, p5);
							}
							if(p4.getName().equals("Bedrock")) {
								BedrockUltimate(p4);
							}
							if(p4.getName().equals("Ashley")) {
								AshleyUltimate(p4, p1, p3, p5, p2, p6);
							}
							if(p4.getName().equals("Kailani")) {
								KailaniUltimate(p4, p1, p3, p5);
							}
							if(p4.getName().equals("Orion")) {
								OrionUltimate(p4, p2, p6);
							}
							if(p4.getName().equals("Alex")) {
								AlexUltimate(p4);
							}
							if(p4.getName().equals("Louis")) {
								Location l = SetCursor(p4, p1, p3, p5, p2, p6);
								LouisUltimate(p4, p1, p3, p5, l);
							}
							if(p4.getName().equals("Via")) {
								ViaUltimate(p4, p1, p3 ,p5);
							}
							if(p4.getName().equals("Max")) {
								MaxUltimate(p4, p1, p3 ,p5);
							}
							if(p4.getName().equals("Cherry")) {
								CherryUltimate(p4);
							}
							if(p4.getName().equals("Bolo")) {
								BoloUltimate(p4, p1, p3 ,p5);
							}
							if(p4.getName().equals("Mack")) {
								MackUltimate(p4);
							}
							if(p4.getName().equals("Finley")) {
								FinleyUltimate(p4, p1, p3, p5);
							}
							if(p4.getName().equals("Zero")) {
								ZeroUltimate(p4);
							}
							if(p4.getName().equals("Burt")) {
								BurtUltimate(p4, p1, p3, p5);
							}
							if(p4.getName().equals("Eli")) {
								EliUltimate(p4, p2, p6);
							}
							if(p4.getName().equals("Solar")) {
								SolarUltimate(p4, p1, p3, p5);
							}
							if(p4.getName().equals("Dylan")) {
								DylanUltimate(p4, p1, p3, p5, p2, p6);
							}
							if(p4.getName().equals("Gates")) {
								GatesUltimate(p4);
							}
							if(p4.getName().equals("Lunar")) {
								LunarUltimate(p4, p1, p3, p5);
							}
						}
					}
					if(response.equals("a") && !p4.onCooldown()) {
						if(p4.isDazed()) {
							System.out.println("Cannot use ability when dazed!");
							System.out.println();
						}else {
							if(p4.getName().equals("Lunar")) {
								LunarAbility(p4);
							}
							if(p4.getName().equals("Solar")) {
								SolarAbility(p4);
							}
							if(p4.getName().equals("Mack")) {
								Location l = SetCursor(p4, p1, p3, p5, p2, p6);
								MackAbility(p4, p1, p3, p5, l);
							}
							if(p4.getName().equals("Cherry")) {
								CherryAbility(p4, p1, p3, p5);
							}
							if(p4.getName().equals("Finley")) {
								FinleyAbility(p4, p1, p3, p5);
							}
							if(p4.getName().equals("Burt")) {
								BurtAbility(p4);
							}
							if(p4.getName().equals("Bolo")) {
								BoloAbility(p4);
							}
							if(p4.getName().equals("Dylan")) {
								DylanAbility(p4, p1, p3 ,p5);
							}
							if(p4.getName().equals("Zero")) {
								ZeroAbility(p4, p2, p6);
							}
							if(p4.getName().equals("Max")) {
								MaxAbility(p4, p2, p6);
							}
							if(p4.getName().equals("Eli")) {
								EliAbility(p4, p2, p6);
							}
							if(p4.getName().equals("Louis")) {
								LouisAbility(p4, p1, p3 ,p5);
							}
							if(p4.getName().equals("Alex")) {
								AlexAbility(p4, p2, p6);
							}
							if(p4.getName().equals("Orion")) {
								OrionAbility(p4, p2, p6);
							}
							if(p4.getName().equals("Via")) {
								Location l = SetCursor(p4, p1, p3, p5, p2, p6);
								ViaAbility(p4, p1, p3, p5, l);
							}
							if(p4.getName().equals("Kailani")) {
								Location l = SetCursor(p2, p1, p3, p5, p4, p6);
								KailaniAbility(p4, p1, p3, p5, l);
							}
							if(p4.getName().equals("Ashley")) {
								AshleyAbility(p4, p1, p3, p5, p2, p6);
							}
							if(p4.getName().equals("Bedrock")) {
								BedrockAbility(p4);
							}
							if(p4.getName().equals("Rocco")) {
								RoccoAbility(p4, p1, p3 ,p5);
							}
							if(p4.getName().equals("Sammi")) {
								SammiAbility(p4);
							}
							if(p4.getName().equals("Clara")) {
								ClaraAbility(p4);
							}
							if(p4.getName().equals("Thunder")) {
								ThunderAbility(p4);
							}
							if(p4.getName().equals("Aidan")) {
								AidanAbility(p4, p2, p6);
							}
							if(p4.getName().equals("Liam")) {
								LiamAbility(p4, p2, p6);
							}
							if(p4.getName().equals("Axol")) {
								AxolAbility(p4, p2, p6);
							}
							if(p4.getName().equals("Katrina")) {
								KatrinaAbility(p4, p2, p6);
							}
							if(p4.getName().equals("Midnite")) {
								MidniteAbility(p4, p1, p3, p5, p2, p6);
							}
							if(p4.getName().equals("Xara")) {
								XaraAbility(p4, p2, p6);
							}
							if(p4.getName().equals("Kithara")) {
								KitharaAbility(p4, p2, p6);
							}
							if(p4.getName().equals("Anjelika")) {
								AnjelikaAbility(p4, p1, p3 ,p5);
							}
							if(p4.getName().equals("Archer")) {
								ArcherAbility(p4, p2, p6);
							}
							if(p4.getName().equals("Tom")) {
								TomAbility(p4);
							}
							if(p4.getName().equals("Dimentio")) {
								DimentioAbility(p4, p1, p3 ,p5);
							}
							if(p4.getName().equals("Grizz")) {
								GrizzAbility(p4, p2, p6);
							}
							if(p4.getName().equals("Evil")) {
								EvilAbility(p4);
							}
							if(p4.getName().equals("Mason")) {
								Location l = SetCursor(p2, p1, p3, p5, p4, p6);
								MasonAbility(p4, p1, p3, p5, l);
							}
							if(p4.getName().equals("Airic")) {
								Location l = SetCursor(p4, p1, p3, p5, p2, p6);
								AiricAbility(p4, p2, p6, l);
							}
							if(p4.getName().equals("Julian")) {
								JulianAbility(p4);
							}
							if(p4.getName().equals("Gash")) {
								GashAbility(p4, p2, p6);
							}
							if(p4.getName().equals("Mayhem")) {
								MayhemAbility(p4, p1, p3 ,p5);
							}
							if(p4.getName().equals("Gates")) {
								GatesAbility(p4, p2, p6);
							}
							if(p4.getName().equals("Audrey")) {
								AudreyAbility(p4, p1, p3 ,p5);
							}
							if(p4.getName().equals("Ayson")) {
								AysonAbility(p4, p1, p3, p5, p2, p6);
							}
							if(p4.getName().equals("Chloe")) {
								ChloeAbility(p4, p2, p6);
							}
							if(p4.getName().equals("Hopper")) {
								HopperAbility(p4, p2, p6);
							}
							if(p4.getName().equals("Redgar")) {
								RedgarAbility(p4, p2, p6);
							}
							if(p4.getName().equals("Radar")) {
								RadarAbility(p4, p1, p3 ,p5);
							}
							if(p4.getName().equals("Oona")) {
								OonaAbility(p4, p1, p3 ,p5);
							}
							if(p4.getName().equals("Augie")) {
								AugieAbility(p4, p2, p6);
							}
							if(p4.getName().equals("Ruby")) {
								Location l = SetCursor(p4, p1, p3, p5, p2, p6);
								RubyAbility(p4, l);
							}
							if(p4.getName().equals("Norman")) {
								NormanAbility(p4, p1, p3 ,p5);
							}
							if(p4.getName().equals("Jesse")) {
								JesseAbility(p4);
							}
							if(p4.getName().equals("Chief")) {
								ChiefAbility(p4, p2, p6);
							}
						}
					}
					if(response.equals("w")) {
						if(p4.isFreezed()) {
							System.out.println("Cannot use weapon while freezed!");
							System.out.println();
						}else {
							if(p4.hasAttacked()) {
								System.out.println(p4.getName() + " has already attacked this turn!");
								System.out.println();
							}else {
								if(p4.getName().equals("Jesse")) {
									JesseAttack(p4, p1, p3, p5);
								}else if(p4.getName().equals("Audrey")) {
									AudreyAttack(p4, p1, p3, p5);
								}else if(p4.getName().equals("Bedrock")) {
									BedrockAttack(p4, p1, p3 ,p5);
								}else if(p4.getName().equals("Max")) {
									MaxAttack(p4, p1, p3 ,p5);
								}else if(p4.getName().equals("Cherry")) {
									CherryAttack(p4, p1, p3, p5, p2, p6);
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
											if(p4.getName().equals("Lunar")) {
												LunarAttack(p4, p1, p3, p5);
											}
											if(p4.getName().equals("Solar")) {
												SolarAttack(p4, p1);
											}
											if(p4.getName().equals("Mack")) {
												MackAttack(p4, p1);
											}
											if(p4.getName().equals("Finley")) {
												FinleyAttack(p4, p1);
											}
											if(p4.getName().equals("Burt")) {
												BurtAttack(p4, p1);
											}
											if(p4.getName().equals("Bolo")) {
												BoloAttack(p4, p1);
											}
											if(p4.getName().equals("Zero")) {
												ZeroAttack(p4, p1, p3, p5);
											}
											if(p4.getName().equals("Dylan")) {
												DylanAttack(p4, p1);
											}
											if(p4.getName().equals("Eli")) {
												EliAttack(p4, p1);
											}
											if(p4.getName().equals("Via")) {
												ViaAttack(p4, p1, p3 ,p5);
											}
											if(p4.getName().equals("Louis")) {
												LouisAttack(p4, p1);
											}
											if(p4.getName().equals("Alex")) {
												AlexAttack(p4, p1);
											}
											if(p4.getName().equals("Orion")) {
												OrionAttack(p4, p1);
											}
											if(p4.getName().equals("Kailani")) {
												KailaniAttack(p4, p1);
											}
											if(p4.getName().equals("Ashley")) {
												AshleyAttack(p4, p1);
											}
											if(p4.getName().equals("Rocco")) {
												RoccoAttack(p4, p1, p3, p5);
											}
											if(p4.getName().equals("Sammi")) {
												SammiAttack(p4, p1);
											}
											if(p4.getName().equals("Clara")) {
												ClaraAttack(p4, p1);
											}
											if(p4.getName().equals("Thunder")) {
												ThunderAttack(p4, p1, p3, p5);
											}
											if(p4.getName().equals("Aidan")) {
												AidanAttack(p4, p1);
											}
											if(p4.getName().equals("Liam")) {
												LiamAttack(p4, p1, p3, p5);
											}
											if(p4.getName().equals("Axol")) {
												AxolAttack(p4, p1);
											}
											if(p4.getName().equals("Katrina")) {
												KatrinaAttack(p4, p1);
											}
											if(p4.getName().equals("Midnite")) {
												MidniteAttack(p4, p1);
											}
											if(p4.getName().equals("Xara")) {
												XaraAttack(p4, p1);
											}
											if(p4.getName().equals("Kithara")) {
												KitharaAttack(p4, p1);
											}
											if(p4.getName().equals("Anjelika")) {
												AnjelikaAttack(p4, p1);
											}
											if(p4.getName().equals("Archer")) {
												ArcherAttack(p4, p1);
											}
											if(p4.getName().equals("Tom")) {
												TomAttack(p4, p1);
											}
											if(p4.getName().equals("Dimentio")) {
												DimentioAttack(p4, p1);
											}
											if(p4.getName().equals("Grizz")) {
												GrizzAttack(p4, p1, p3, p5);
											}
											if(p4.getName().equals("Evil")) {
												EvilAttack(p4, p1, p3, p5);
											}
											if(p4.getName().equals("Mason")) {
												MasonAttack(p4, p1);
											}
											if(p4.getName().equals("Airic")) {
												AiricAttack(p4, p1);
											}
											if(p4.getName().equals("Julian")) {
												JulianAttack(p4, p1, p3, p5);
											}
											if(p4.getName().equals("Gash")) {
												GashAttack(p4, p1);
											}
											if(p4.getName().equals("Mayhem")) {
												MayhemAttack(p4, p1);
											}
											if(p4.getName().equals("Gates")) {
												Location l = SetCursor(p4, p1, p3, p5, p2, p6);
												GatesAttack(p4, p1, l);
											}
											if(p4.getName().equals("Ayson")) {
												AysonAttack(p4, p1);
											}
											if(p4.getName().equals("Chloe")) {
												ChloeAttack(p4, p1, p2, p6);
											}
											if(p4.getName().equals("Hopper")) {
												HopperAttack(p4, p1);
											}
											if(p4.getName().equals("Redgar")) {
												RedgarAttack(p4, p1);
											}
											if(p4.getName().equals("Radar")) {
												RadarAttack(p4, p1);
											}
											if(p4.getName().equals("Oona")) {
												OonaAttack(p4, p1);
											}
											if(p4.getName().equals("Augie")) {
												AugieAttack(p4, p1);
											}
											if(p4.getName().equals("Ruby")) {
												RubyAttack(p4, p1);
											}
											if(p4.getName().equals("Norman")) {
												NormanAttack(p4, p1, p2, p6);
											}
											if(p4.getName().equals("Chief")) {
												ChiefAttack(p4, p1);
											}
										}
									}
									if(attackResponse.equals("2")) {
										if(!p4.inRange(p3)) {
											System.out.println();
											System.out.println("Target is out of range!");
											System.out.println();
										}else {
											if(p4.getName().equals("Lunar")) {
												LunarAttack(p4, p3, p1, p5);
											}
											if(p4.getName().equals("Solar")) {
												SolarAttack(p4, p3);
											}
											if(p4.getName().equals("Mack")) {
												MackAttack(p4, p3);
											}
											if(p4.getName().equals("Finley")) {
												FinleyAttack(p4, p3);
											}
											if(p4.getName().equals("Burt")) {
												BurtAttack(p4, p3);
											}
											if(p4.getName().equals("Bolo")) {
												BoloAttack(p4, p3);
											}
											if(p4.getName().equals("Zero")) {
												ZeroAttack(p4, p3, p1, p5);
											}
											if(p4.getName().equals("Dylan")) {
												DylanAttack(p4, p3);
											}
											if(p4.getName().equals("Eli")) {
												EliAttack(p4, p3);
											}
											if(p4.getName().equals("Via")) {
												ViaAttack(p4, p3, p1 ,p5);
											}
											if(p4.getName().equals("Louis")) {
												LouisAttack(p4, p3);
											}
											if(p4.getName().equals("Alex")) {
												AlexAttack(p4, p3);
											}
											if(p4.getName().equals("Orion")) {
												OrionAttack(p4, p3);
											}
											if(p4.getName().equals("Kailani")) {
												KailaniAttack(p4, p3);
											}
											if(p4.getName().equals("Ashley")) {
												AshleyAttack(p4, p3);
											}
											if(p4.getName().equals("Rocco")) {
												RoccoAttack(p4, p3, p1, p5);
											}
											if(p4.getName().equals("Sammi")) {
												SammiAttack(p4, p3);
											}
											if(p4.getName().equals("Clara")) {
												ClaraAttack(p4, p3);
											}
											if(p4.getName().equals("Thunder")) {
												ThunderAttack(p4, p3, p1, p5);
											}
											if(p4.getName().equals("Aidan")) {
												AidanAttack(p4, p3);
											}
											if(p4.getName().equals("Liam")) {
												LiamAttack(p4, p3, p1, p5);
											}
											if(p4.getName().equals("Axol")) {
												AxolAttack(p4, p3);
											}
											if(p4.getName().equals("Katrina")) {
												KatrinaAttack(p4, p3);
											}
											if(p4.getName().equals("Midnite")) {
												MidniteAttack(p4, p3);
											}
											if(p4.getName().equals("Xara")) {
												XaraAttack(p4, p3);
											}
											if(p4.getName().equals("Kithara")) {
												KitharaAttack(p4, p3);
											}
											if(p4.getName().equals("Anjelika")) {
												AnjelikaAttack(p4, p3);
											}
											if(p4.getName().equals("Archer")) {
												ArcherAttack(p4, p3);
											}
											if(p4.getName().equals("Tom")) {
												TomAttack(p4, p3);
											}
											if(p4.getName().equals("Dimentio")) {
												DimentioAttack(p4, p3);
											}
											if(p4.getName().equals("Grizz")) {
												GrizzAttack(p4, p3, p1, p5);
											}
											if(p4.getName().equals("Evil")) {
												EvilAttack(p4, p3, p1, p5);
											}
											if(p4.getName().equals("Mason")) {
												MasonAttack(p4, p3);
											}
											if(p4.getName().equals("Airic")) {
												AiricAttack(p4, p3);
											}
											if(p4.getName().equals("Julian")) {
												JulianAttack(p4, p3, p1, p5);
											}
											if(p4.getName().equals("Gash")) {
												GashAttack(p4, p3);
											}
											if(p4.getName().equals("Mayhem")) {
												MayhemAttack(p4, p3);
											}
											if(p4.getName().equals("Gates")) {
												Location l = SetCursor(p4, p1, p3, p5, p2, p6);
												GatesAttack(p4, p3, l);
											}
											if(p4.getName().equals("Ayson")) {
												AysonAttack(p4, p3);
											}
											if(p4.getName().equals("Chloe")) {
												ChloeAttack(p4, p3, p2, p6);
											}
											if(p4.getName().equals("Hopper")) {
												HopperAttack(p4, p3);
											}
											if(p4.getName().equals("Redgar")) {
												RedgarAttack(p4, p3);
											}
											if(p4.getName().equals("Radar")) {
												RadarAttack(p4, p3);
											}
											if(p4.getName().equals("Oona")) {
												OonaAttack(p4, p3);
											}
											if(p4.getName().equals("Augie")) {
												AugieAttack(p4, p3);
											}
											if(p4.getName().equals("Ruby")) {
												RubyAttack(p4, p3);
											}
											if(p4.getName().equals("Norman")) {
												NormanAttack(p4, p3, p2, p6);
											}
											if(p4.getName().equals("Chief")) {
												ChiefAttack(p4, p3);
											}
										}
									}
									if(attackResponse.equals("3")) {
										if(!p4.inRange(p5)) {
											System.out.println();
											System.out.println("Target is out of range!");
											System.out.println();
										}else {
											if(p4.getName().equals("Lunar")) {
												LunarAttack(p4, p5, p3, p1);
											}
											if(p4.getName().equals("Solar")) {
												SolarAttack(p4, p5);
											}
											if(p4.getName().equals("Mack")) {
												MackAttack(p4, p5);
											}
											if(p4.getName().equals("Finley")) {
												FinleyAttack(p4, p5);
											}
											if(p4.getName().equals("Burt")) {
												BurtAttack(p4, p5);
											}
											if(p4.getName().equals("Bolo")) {
												BoloAttack(p4, p5);
											}
											if(p4.getName().equals("Zero")) {
												ZeroAttack(p4, p5, p3, p1);
											}
											if(p4.getName().equals("Dylan")) {
												DylanAttack(p4, p5);
											}
											if(p4.getName().equals("Eli")) {
												EliAttack(p4, p5);
											}
											if(p4.getName().equals("Via")) {
												ViaAttack(p4, p5, p3 ,p1);
											}
											if(p4.getName().equals("Louis")) {
												LouisAttack(p4, p5);
											}
											if(p4.getName().equals("Alex")) {
												AlexAttack(p4, p5);
											}
											if(p4.getName().equals("Orion")) {
												OrionAttack(p4, p5);
											}
											if(p4.getName().equals("Kailani")) {
												KailaniAttack(p4, p5);
											}
											if(p4.getName().equals("Ashley")) {
												AshleyAttack(p4, p5);
											}
											if(p4.getName().equals("Rocco")) {
												RoccoAttack(p4, p5, p3, p1);
											}
											if(p4.getName().equals("Sammi")) {
												SammiAttack(p4, p5);
											}
											if(p4.getName().equals("Clara")) {
												ClaraAttack(p4, p5);
											}
											if(p4.getName().equals("Thunder")) {
												ThunderAttack(p4, p5, p3, p1);
											}
											if(p4.getName().equals("Aidan")) {
												AidanAttack(p4, p5);
											}
											if(p4.getName().equals("Liam")) {
												LiamAttack(p4, p5, p3, p1);
											}
											if(p4.getName().equals("Axol")) {
												AxolAttack(p4, p5);
											}
											if(p4.getName().equals("Katrina")) {
												KatrinaAttack(p4, p5);
											}
											if(p4.getName().equals("Midnite")) {
												MidniteAttack(p4, p5);
											}
											if(p4.getName().equals("Xara")) {
												XaraAttack(p4, p5);
											}
											if(p4.getName().equals("Kithara")) {
												KitharaAttack(p4, p5);
											}
											if(p4.getName().equals("Anjelika")) {
												AnjelikaAttack(p4, p5);
											}
											if(p4.getName().equals("Archer")) {
												ArcherAttack(p4, p5);
											}
											if(p4.getName().equals("Tom")) {
												TomAttack(p4, p5);
											}
											if(p4.getName().equals("Dimentio")) {
												DimentioAttack(p4, p5);
											}
											if(p4.getName().equals("Grizz")) {
												GrizzAttack(p4, p5, p3, p1);
											}
											if(p4.getName().equals("Evil")) {
												EvilAttack(p4, p5, p3, p1);
											}
											if(p4.getName().equals("Mason")) {
												MasonAttack(p4, p5);
											}
											if(p4.getName().equals("Airic")) {
												AiricAttack(p4, p5);
											}
											if(p4.getName().equals("Julian")) {
												JulianAttack(p4, p5, p3, p1);
											}
											if(p4.getName().equals("Gash")) {
												GashAttack(p4, p5);
											}
											if(p4.getName().equals("Mayhem")) {
												MayhemAttack(p4, p5);
											}
											if(p4.getName().equals("Gates")) {
												Location l = SetCursor(p4, p1, p3, p5, p2, p6);
												GatesAttack(p4, p5, l);
											}
											if(p4.getName().equals("Ayson")) {
												AysonAttack(p4, p5);
											}
											if(p4.getName().equals("Chloe")) {
												ChloeAttack(p4, p5, p2, p6);
											}
											if(p4.getName().equals("Hopper")) {
												HopperAttack(p4, p5);
											}
											if(p4.getName().equals("Redgar")) {
												RedgarAttack(p4, p5);
											}
											if(p4.getName().equals("Radar")) {
												RadarAttack(p4, p5);
											}
											if(p4.getName().equals("Oona")) {
												OonaAttack(p4, p5);
											}
											if(p4.getName().equals("Augie")) {
												AugieAttack(p4, p5);
											}
											if(p4.getName().equals("Ruby")) {
												RubyAttack(p4, p5);
											}
											if(p4.getName().equals("Norman")) {
												NormanAttack(p4, p5, p2, p6);
											}
											if(p4.getName().equals("Chief")) {
												ChiefAttack(p4, p5);
											}
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
					System.out.println("Turn: " + tD);
					System.out.println("Team B's Turn (Go " + p6.getSkin() + "!)");
					System.out.println(p6);
					System.out.print("What would " + p6.getName() + " like to do: ");
					String response = input.next();
					System.out.println();
					if(response.equals("b")){
						b.printField(p1, p2, p3, p4, p5, p6, orbs, cover, p6, p1, p3, p5);
					}
					if(response.equals("s")) {
						party2.showRoster();
						System.out.print("Who do you want to switch to: ");
						String switchResponse = input.next();
						if(switchResponse.equals("1")) {
							p6.passTurn(p2);
							System.out.println();
						}
						if(switchResponse.equals("2")) {
							p6.passTurn(p4);
							System.out.println();
						}
						if(switchResponse.equals("3")) {
							p6.passTurn(p6);
							System.out.println();
						}
					}
					if(response.equals("p")) {
						System.out.print("Are you sure you want to pass to the enemy's turn: ");
						String switchResponse = input.next();
						if(switchResponse.equals("p")) {
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
					if(response.equals("i")) {
						CheckProfile(p6, party1);
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
					if(response.equals("d")) {
						Dash(p6, p1, p3, p5);
					}
					if(response.equals("j")) {
						Location l = SetCursor(p6, p1, p3, p5, p2, p4);
						Jump(p6, p4, p2, l);
					}
					if(response.equals("u")) {
						if(!p6.ultReady() || p6.ultActive()) {
							System.out.println("My ultimate cannot be used!");
							System.out.println();
						}else {
							if(p6.getName().equals("Chief")) {
								ChiefUltimate(p6, p1, p3, p5, p4, p2);
							}
							if(p6.getName().equals("Jesse")) {
								JesseUltimate(p6);
							}
							if(p6.getName().equals("Norman")) {
								NormanUltimate(p6, p4, p2);
							}
							if(p6.getName().equals("Ruby")) {
								RubyUltimate(p6, p4, p2);
							}
							if(p6.getName().equals("Augie")) {
								AugieUltimate(p6, p1, p3 ,p5);
							}
							if(p6.getName().equals("Oona")) {
								OonaUltimate(p6, p4, p2);
							}
							if(p6.getName().equals("Radar")) {
								RadarUltimate(p6, p1, p3, p5, p4, p2);
							}
							if(p6.getName().equals("Redgar")) {
								RedgarUltimate(p6);
							}
							if(p6.getName().equals("Hopper")) {
								HopperUltimate(p6, p4, p2);
							}
							if(p6.getName().equals("Chloe")) {
								ChloeUltimate(p6, p4, p2);
							}
							if(p6.getName().equals("Ayson")) {
								AysonUltimate(p6, p1, p3 ,p5);
							}
							if(p6.getName().equals("Audrey")) {
								AudreyUltimate(p6, p4, p2);
							}
							if(p6.getName().equals("Mayhem")) {
								MayhemUltimate(p6);
							}
							if(p6.getName().equals("Gash")) {
								GashUltimate(p6, p1, p3, p5);
							}
							if(p6.getName().equals("Julian")) {
								JulianUltimate(p6, p1, p3, p5, p2, p4);
							}
							if(p6.getName().equals("Airic")) {
								AiricUltimate(p6, p1, p3, p5);
							}
							if(p6.getName().equals("Mason")) {
								MasonUltimate(p6, p1, p3, p5, p4, p2);
							}
							if(p6.getName().equals("Evil")) {
								EvilUltimate(p6, p1, p3, p5);
							}
							if(p6.getName().equals("Grizz")) {
								GrizzUltimate(p6);
							}
							if(p6.getName().equals("Dimentio")) {
								DimentioUltimate(p6);
							}
							if(p6.getName().equals("Tom")) {
								Location l = SetCursor(p6, p1, p3, p5, p4, p2);
								TomUltimate(p6, p1, p3, p5, l);
							}
							if(p6.getName().equals("Archer")) {
								ArcherUltimate(p6, p1, p3, p5);
							}
							if(p6.getName().equals("Anjelika")) {
								AnjelikaUltimate(p6);
							}
							if(p6.getName().equals("Kithara")) {
								KitharaUltimate(p6, p1, p3, p5);
							}
							if(p6.getName().equals("Xara")) {
								XaraUltimate(p6, p1, p3, p5);
							}
							if(p6.getName().equals("Midnite")) {
								MidniteUltimate(p6);
							}
							if(p6.getName().equals("Katrina")) {
								KatrinaUltimate(p6, p4, p2);
							}
							if(p6.getName().equals("Axol")) {
								AxolUltimate(p6, p4, p2);
							}
							if(p6.getName().equals("Liam")) {
								LiamUltimate(p6, p1, p3, p5, p4, p2);
							}
							if(p6.getName().equals("Aidan")) {
								AidanUltimate(p6, p1, p3, p5, p4, p2);
							}
							if(p6.getName().equals("Thunder")) {
								ThunderUltimate(p6, p1, p3, p5);
							}
							if(p6.getName().equals("Clara")) {
								ClaraUltimate(p6);
							}
							if(p6.getName().equals("Sammi")) {
								SammiUltimate(p6);
							}
							if(p6.getName().equals("Rocco")) {
								RoccoUltimate(p6, p1, p3, p5);
							}
							if(p6.getName().equals("Bedrock")) {
								BedrockUltimate(p6);
							}
							if(p6.getName().equals("Ashley")) {
								AshleyUltimate(p6, p1, p3, p5, p4, p2);
							}
							if(p6.getName().equals("Kailani")) {
								KailaniUltimate(p6, p1, p3, p5);
							}
							if(p6.getName().equals("Orion")) {
								OrionUltimate(p6, p4, p2);
							}
							if(p6.getName().equals("Alex")) {
								AlexUltimate(p6);
							}
							if(p6.getName().equals("Louis")) {
								Location l = SetCursor(p6, p1, p3, p5, p4, p2);
								LouisUltimate(p6, p1, p3, p5, l);
							}
							if(p6.getName().equals("Via")) {
								ViaUltimate(p6, p1, p3 ,p5);
							}
							if(p6.getName().equals("Max")) {
								MaxUltimate(p6, p1, p3 ,p5);
							}
							if(p6.getName().equals("Cherry")) {
								CherryUltimate(p6);
							}
							if(p6.getName().equals("Bolo")) {
								BoloUltimate(p6, p1, p3 ,p5);
							}
							if(p6.getName().equals("Mack")) {
								MackUltimate(p6);
							}
							if(p6.getName().equals("Finley")) {
								FinleyUltimate(p6, p1, p3, p5);
							}
							if(p6.getName().equals("Zero")) {
								ZeroUltimate(p6);
							}
							if(p6.getName().equals("Burt")) {
								BurtUltimate(p6, p1, p3, p5);
							}
							if(p6.getName().equals("Eli")) {
								EliUltimate(p6, p4, p2);
							}
							if(p6.getName().equals("Solar")) {
								SolarUltimate(p6, p1, p3, p5);
							}
							if(p6.getName().equals("Dylan")) {
								DylanUltimate(p6, p1, p3, p5, p4, p2);
							}
							if(p6.getName().equals("Gates")) {
								GatesUltimate(p6);
							}
							if(p6.getName().equals("Lunar")) {
								LunarUltimate(p6, p1, p3, p5);
							}
						}
					}
					if(response.equals("a") && !p6.onCooldown()) {
						if(p6.isDazed()) {
							System.out.println("Cannot use ability when dazed!");
							System.out.println();
						}else {
							if(p6.getName().equals("Lunar")) {
								LunarAbility(p6);
							}
							if(p6.getName().equals("Solar")) {
								SolarAbility(p6);
							}
							if(p6.getName().equals("Mack")) {
								Location l = SetCursor(p6, p1, p3, p5, p4, p2);
								MackAbility(p6, p1, p3, p5, l);
							}
							if(p6.getName().equals("Cherry")) {
								CherryAbility(p6, p1, p3, p5);
							}
							if(p6.getName().equals("Finley")) {
								FinleyAbility(p6, p1, p3, p5);
							}
							if(p6.getName().equals("Burt")) {
								BurtAbility(p6);
							}
							if(p6.getName().equals("Bolo")) {
								BoloAbility(p6);
							}
							if(p6.getName().equals("Dylan")) {
								DylanAbility(p6, p1, p3 ,p5);
							}
							if(p6.getName().equals("Zero")) {
								ZeroAbility(p6, p4, p2);
							}
							if(p6.getName().equals("Max")) {
								MaxAbility(p6, p2, p4);
							}
							if(p6.getName().equals("Eli")) {
								EliAbility(p6, p4, p2);
							}
							if(p6.getName().equals("Via")) {
								Location l = SetCursor(p6, p1, p3, p5, p4, p2);
								ViaAbility(p6, p1, p3, p5, l);
							}
							if(p6.getName().equals("Louis")) {
								LouisAbility(p6, p1, p3 ,p5);
							}
							if(p6.getName().equals("Alex")) {
								AlexAbility(p6, p4, p2);
							}
							if(p6.getName().equals("Orion")) {
								OrionAbility(p6, p4, p2);
							}
							if(p6.getName().equals("Kailani")) {
								Location l = SetCursor(p2, p1, p3, p5, p4, p6);
								KailaniAbility(p6, p1, p3, p5, l);
							}
							if(p6.getName().equals("Ashley")) {
								AshleyAbility(p6, p1, p3, p5, p2, p4);
							}
							if(p6.getName().equals("Bedrock")) {
								BedrockAbility(p6);
							}
							if(p6.getName().equals("Rocco")) {
								RoccoAbility(p6, p1, p3 ,p5);
							}
							if(p6.getName().equals("Sammi")) {
								SammiAbility(p6);
							}
							if(p6.getName().equals("Clara")) {
								ClaraAbility(p6);
							}
							if(p6.getName().equals("Thunder")) {
								ThunderAbility(p6);
							}
							if(p6.getName().equals("Aidan")) {
								AidanAbility(p6, p4, p2);
							}
							if(p6.getName().equals("Liam")) {
								LiamAbility(p6, p4, p2);
							}
							if(p6.getName().equals("Axol")) {
								AxolAbility(p6, p4, p2);
							}
							if(p6.getName().equals("Katrina")) {
								KatrinaAbility(p6, p4, p2);
							}
							if(p6.getName().equals("Midnite")) {
								MidniteAbility(p6, p1, p3, p5, p4, p2);
							}
							if(p6.getName().equals("Xara")) {
								XaraAbility(p6, p4, p2);
							}
							if(p6.getName().equals("Kithara")) {
								KitharaAbility(p6, p4, p2);
							}
							if(p6.getName().equals("Anjelika")) {
								AnjelikaAbility(p6, p1, p3 ,p5);
							}
							if(p6.getName().equals("Archer")) {
								ArcherAbility(p6, p4, p2);
							}
							if(p6.getName().equals("Tom")) {
								TomAbility(p6);
							}
							if(p6.getName().equals("Dimentio")) {
								DimentioAbility(p6, p1, p3 ,p5);
							}
							if(p6.getName().equals("Grizz")) {
								GrizzAbility(p6, p4, p2);
							}
							if(p6.getName().equals("Evil")) {
								EvilAbility(p6);
							}
							if(p6.getName().equals("Mason")) {
								Location l = SetCursor(p2, p1, p3, p5, p4, p6);
								MasonAbility(p6, p1, p3, p5, l);
							}
							if(p6.getName().equals("Airic")) {
								Location l = SetCursor(p6, p1, p3, p5, p4, p2);
								AiricAbility(p6, p4, p2, l);
							}
							if(p6.getName().equals("Julian")) {
								JulianAbility(p6);
							}
							if(p6.getName().equals("Gash")) {
								GashAbility(p6, p4, p2);
							}
							if(p6.getName().equals("Mayhem")) {
								MayhemAbility(p6, p1, p3 ,p5);
							}
							if(p6.getName().equals("Gates")) {
								GatesAbility(p6, p4, p2);
							}
							if(p6.getName().equals("Audrey")) {
								AudreyAbility(p6, p1, p3 ,p5);
							}
							if(p6.getName().equals("Ayson")) {
								AysonAbility(p6, p1, p3, p5, p4, p2);
							}
							if(p6.getName().equals("Chloe")) {
								ChloeAbility(p6, p4, p2);
							}
							if(p6.getName().equals("Hopper")) {
								HopperAbility(p6, p4, p2);
							}
							if(p6.getName().equals("Redgar")) {
								RedgarAbility(p6, p4, p2);
							}
							if(p6.getName().equals("Radar")) {
								RadarAbility(p6, p1, p3 ,p5);
							}
							if(p6.getName().equals("Oona")) {
								OonaAbility(p6, p1, p3 ,p5);
							}
							if(p6.getName().equals("Augie")) {
								AugieAbility(p6, p4, p2);
							}
							if(p6.getName().equals("Ruby")) {
								Location l = SetCursor(p6, p1, p3, p5, p4, p2);
								RubyAbility(p6, l);
							}
							if(p6.getName().equals("Norman")) {
								NormanAbility(p6, p1, p3 ,p5);
							}
							if(p6.getName().equals("Jesse")) {
								JesseAbility(p6);
							}
							if(p6.getName().equals("Chief")) {
								ChiefAbility(p6, p4, p2);
							}
						}
					}
					if(response.equals("w")) {
						if(p6.isFreezed()) {
							System.out.println("Cannot use weapon while freezed!");
							System.out.println();
						}else {
							if(p6.hasAttacked()) {
								System.out.println(p6.getName() + " has already attacked this turn!");
								System.out.println();
							}else {
								if(p6.getName().equals("Jesse")) {
									JesseAttack(p6, p1, p3, p5);
								}else if(p6.getName().equals("Audrey")) {
									AudreyAttack(p6, p1, p3, p5);
								}else if(p6.getName().equals("Bedrock")) {
									BedrockAttack(p6, p1, p3 ,p5);
								}else if(p6.getName().equals("Max")) {
									MaxAttack(p6, p1, p3 ,p5);
								}else if(p6.getName().equals("Cherry")) {
									CherryAttack(p6, p1, p3, p5, p2, p4);
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
											if(p6.getName().equals("Lunar")) {
												LunarAttack(p6, p1, p3, p5);
											}
											if(p6.getName().equals("Solar")) {
												SolarAttack(p6, p1);
											}
											if(p6.getName().equals("Mack")) {
												MackAttack(p6, p1);
											}
											if(p6.getName().equals("Finley")) {
												FinleyAttack(p6, p1);
											}
											if(p6.getName().equals("Burt")) {
												BurtAttack(p6, p1);
											}
											if(p6.getName().equals("Bolo")) {
												BoloAttack(p6, p1);
											}
											if(p6.getName().equals("Zero")) {
												ZeroAttack(p6, p1, p3, p5);
											}
											if(p6.getName().equals("Dylan")) {
												DylanAttack(p6, p1);
											}
											if(p6.getName().equals("Eli")) {
												EliAttack(p6, p1);
											}
											if(p6.getName().equals("Via")) {
												ViaAttack(p6, p1, p3 ,p5);
											}
											if(p6.getName().equals("Louis")) {
												LouisAttack(p6, p1);
											}
											if(p6.getName().equals("Alex")) {
												AlexAttack(p6, p1);
											}
											if(p6.getName().equals("Orion")) {
												OrionAttack(p6, p1);
											}
											if(p6.getName().equals("Kailani")) {
												KailaniAttack(p6, p1);
											}
											if(p6.getName().equals("Ashley")) {
												AshleyAttack(p6, p1);
											}
											if(p6.getName().equals("Rocco")) {
												RoccoAttack(p6, p1, p3, p5);
											}
											if(p6.getName().equals("Sammi")) {
												SammiAttack(p6, p1);
											}
											if(p6.getName().equals("Clara")) {
												ClaraAttack(p6, p1);
											}
											if(p6.getName().equals("Thunder")) {
												ThunderAttack(p6, p1, p3, p5);
											}
											if(p6.getName().equals("Aidan")) {
												AidanAttack(p6, p1);
											}
											if(p6.getName().equals("Liam")) {
												LiamAttack(p6, p1, p3, p5);
											}
											if(p6.getName().equals("Axol")) {
												AxolAttack(p6, p1);
											}
											if(p6.getName().equals("Katrina")) {
												KatrinaAttack(p6, p1);
											}
											if(p6.getName().equals("Midnite")) {
												MidniteAttack(p6, p1);
											}
											if(p6.getName().equals("Xara")) {
												XaraAttack(p6, p1);
											}
											if(p6.getName().equals("Kithara")) {
												KitharaAttack(p6, p1);
											}
											if(p6.getName().equals("Anjelika")) {
												AnjelikaAttack(p6, p1);
											}
											if(p6.getName().equals("Archer")) {
												ArcherAttack(p6, p1);
											}
											if(p6.getName().equals("Tom")) {
												TomAttack(p6, p1);
											}
											if(p6.getName().equals("Dimentio")) {
												DimentioAttack(p6, p1);
											}
											if(p6.getName().equals("Grizz")) {
												GrizzAttack(p6, p1, p3, p5);
											}
											if(p6.getName().equals("Evil")) {
												EvilAttack(p6, p1, p3, p5);
											}
											if(p6.getName().equals("Mason")) {
												MasonAttack(p6, p1);
											}
											if(p6.getName().equals("Airic")) {
												AiricAttack(p6, p1);
											}
											if(p6.getName().equals("Julian")) {
												JulianAttack(p6, p1, p3, p5);
											}
											if(p6.getName().equals("Gash")) {
												GashAttack(p6, p1);
											}
											if(p6.getName().equals("Mayhem")) {
												MayhemAttack(p6, p1);
											}
											if(p6.getName().equals("Gates")) {
												Location l = SetCursor(p6, p1, p3, p5, p2, p4);
												GatesAttack(p6, p1, l);
											}
											if(p6.getName().equals("Ayson")) {
												AysonAttack(p6, p1);
											}
											if(p6.getName().equals("Chloe")) {
												ChloeAttack(p6, p1, p2, p4);
											}
											if(p6.getName().equals("Hopper")) {
												HopperAttack(p6, p1);
											}
											if(p6.getName().equals("Redgar")) {
												RedgarAttack(p6, p1);
											}
											if(p6.getName().equals("Radar")) {
												RadarAttack(p6, p1);
											}
											if(p6.getName().equals("Oona")) {
												OonaAttack(p6, p1);
											}
											if(p6.getName().equals("Augie")) {
												AugieAttack(p6, p1);
											}
											if(p6.getName().equals("Ruby")) {
												RubyAttack(p6, p1);
											}
											if(p6.getName().equals("Norman")) {
												NormanAttack(p6, p1, p2, p4);
											}
											if(p6.getName().equals("Chief")) {
												ChiefAttack(p6, p1);
											}
										}
									}
									if(attackResponse.equals("2")) {
										if(!p6.inRange(p3)) {
											System.out.println();
											System.out.println("Target is out of range!");
											System.out.println();
										}else {
											if(p6.getName().equals("Lunar")) {
												LunarAttack(p6, p3, p1, p5);
											}
											if(p6.getName().equals("Solar")) {
												SolarAttack(p6, p3);
											}
											if(p6.getName().equals("Mack")) {
												MackAttack(p6, p3);
											}
											if(p6.getName().equals("Finley")) {
												FinleyAttack(p6, p3);
											}
											if(p6.getName().equals("Burt")) {
												BurtAttack(p6, p3);
											}
											if(p6.getName().equals("Bolo")) {
												BoloAttack(p6, p3);
											}
											if(p6.getName().equals("Zero")) {
												ZeroAttack(p6, p3, p1, p5);
											}
											if(p6.getName().equals("Dylan")) {
												DylanAttack(p6, p3);
											}
											if(p6.getName().equals("Eli")) {
												EliAttack(p6, p3);
											}
											if(p6.getName().equals("Via")) {
												ViaAttack(p6, p3, p1 ,p5);
											}
											if(p6.getName().equals("Louis")) {
												LouisAttack(p6, p3);
											}
											if(p6.getName().equals("Alex")) {
												AlexAttack(p6, p3);
											}
											if(p6.getName().equals("Orion")) {
												OrionAttack(p6, p3);
											}
											if(p6.getName().equals("Kailani")) {
												KailaniAttack(p6, p3);
											}
											if(p6.getName().equals("Ashley")) {
												AshleyAttack(p6, p3);
											}
											if(p6.getName().equals("Rocco")) {
												RoccoAttack(p6, p3, p1, p5);
											}
											if(p6.getName().equals("Sammi")) {
												SammiAttack(p6, p3);
											}
											if(p6.getName().equals("Clara")) {
												ClaraAttack(p6, p3);
											}
											if(p6.getName().equals("Thunder")) {
												ThunderAttack(p6, p3, p1, p5);
											}
											if(p6.getName().equals("Aidan")) {
												AidanAttack(p6, p3);
											}
											if(p6.getName().equals("Liam")) {
												LiamAttack(p6, p3, p1, p5);
											}
											if(p6.getName().equals("Axol")) {
												AxolAttack(p6, p3);
											}
											if(p6.getName().equals("Katrina")) {
												KatrinaAttack(p6, p3);
											}
											if(p6.getName().equals("Midnite")) {
												MidniteAttack(p6, p3);
											}
											if(p6.getName().equals("Xara")) {
												XaraAttack(p6, p3);
											}
											if(p6.getName().equals("Kithara")) {
												KitharaAttack(p6, p3);
											}
											if(p6.getName().equals("Anjelika")) {
												AnjelikaAttack(p6, p3);
											}
											if(p6.getName().equals("Archer")) {
												ArcherAttack(p6, p3);
											}
											if(p6.getName().equals("Tom")) {
												TomAttack(p6, p3);
											}
											if(p6.getName().equals("Dimentio")) {
												DimentioAttack(p6, p3);
											}
											if(p6.getName().equals("Grizz")) {
												GrizzAttack(p6, p3, p1, p5);
											}
											if(p6.getName().equals("Evil")) {
												EvilAttack(p6, p3, p1, p5);
											}
											if(p6.getName().equals("Mason")) {
												MasonAttack(p6, p3);
											}
											if(p6.getName().equals("Airic")) {
												AiricAttack(p6, p3);
											}
											if(p6.getName().equals("Julian")) {
												JulianAttack(p6, p3, p1, p5);
											}
											if(p6.getName().equals("Gash")) {
												GashAttack(p6, p3);
											}
											if(p6.getName().equals("Mayhem")) {
												MayhemAttack(p6, p3);
											}
											if(p6.getName().equals("Gates")) {
												Location l = SetCursor(p6, p1, p3, p5, p2, p4);
												GatesAttack(p6, p3, l);
											}
											if(p6.getName().equals("Ayson")) {
												AysonAttack(p6, p3);
											}
											if(p6.getName().equals("Chloe")) {
												ChloeAttack(p6, p3, p2, p4);
											}
											if(p6.getName().equals("Hopper")) {
												HopperAttack(p6, p3);
											}
											if(p6.getName().equals("Redgar")) {
												RedgarAttack(p6, p3);
											}
											if(p6.getName().equals("Radar")) {
												RadarAttack(p6, p3);
											}
											if(p6.getName().equals("Oona")) {
												OonaAttack(p6, p3);
											}
											if(p6.getName().equals("Augie")) {
												AugieAttack(p6, p3);
											}
											if(p6.getName().equals("Ruby")) {
												RubyAttack(p6, p3);
											}
											if(p6.getName().equals("Norman")) {
												NormanAttack(p6, p1, p2, p4);
											}
											if(p6.getName().equals("Chief")) {
												ChiefAttack(p6, p3);
											}
										}
									}
									if(attackResponse.equals("3")) {
										if(!p6.inRange(p5)) {
											System.out.println();
											System.out.println("Target is out of range!");
											System.out.println();
										}else {
											if(p6.getName().equals("Lunar")) {
												LunarAttack(p6, p5, p3, p1);
											}
											if(p6.getName().equals("Solar")) {
												SolarAttack(p6, p5);
											}
											if(p6.getName().equals("Mack")) {
												MackAttack(p6, p5);
											}
											if(p6.getName().equals("Finley")) {
												FinleyAttack(p6, p5);
											}
											if(p6.getName().equals("Burt")) {
												BurtAttack(p6, p5);
											}
											if(p6.getName().equals("Bolo")) {
												BoloAttack(p6, p5);
											}
											if(p6.getName().equals("Zero")) {
												ZeroAttack(p6, p5, p3, p1);
											}
											if(p6.getName().equals("Dylan")) {
												DylanAttack(p6, p5);
											}
											if(p6.getName().equals("Eli")) {
												EliAttack(p6, p5);
											}
											if(p6.getName().equals("Via")) {
												ViaAttack(p6, p5, p3 ,p1);
											}
											if(p6.getName().equals("Louis")) {
												LouisAttack(p6, p5);
											}
											if(p6.getName().equals("Alex")) {
												AlexAttack(p6, p5);
											}
											if(p6.getName().equals("Orion")) {
												OrionAttack(p6, p5);
											}
											if(p6.getName().equals("Kailani")) {
												KailaniAttack(p6, p5);
											}
											if(p6.getName().equals("Ashley")) {
												AshleyAttack(p6, p5);
											}
											if(p6.getName().equals("Rocco")) {
												RoccoAttack(p6, p5, p3, p1);
											}
											if(p6.getName().equals("Sammi")) {
												SammiAttack(p6, p5);
											}
											if(p6.getName().equals("Clara")) {
												ClaraAttack(p6, p5);
											}
											if(p6.getName().equals("Thunder")) {
												ThunderAttack(p6, p5, p3, p1);
											}
											if(p6.getName().equals("Aidan")) {
												AidanAttack(p6, p5);
											}
											if(p6.getName().equals("Liam")) {
												LiamAttack(p6, p5, p3, p1);
											}
											if(p6.getName().equals("Axol")) {
												AxolAttack(p6, p5);
											}
											if(p6.getName().equals("Katrina")) {
												KatrinaAttack(p6, p5);
											}
											if(p6.getName().equals("Midnite")) {
												MidniteAttack(p6, p5);
											}
											if(p6.getName().equals("Xara")) {
												XaraAttack(p6, p5);
											}
											if(p6.getName().equals("Kithara")) {
												KitharaAttack(p6, p5);
											}
											if(p6.getName().equals("Anjelika")) {
												AnjelikaAttack(p6, p5);
											}
											if(p6.getName().equals("Archer")) {
												ArcherAttack(p6, p5);
											}
											if(p6.getName().equals("Tom")) {
												TomAttack(p6, p5);
											}
											if(p6.getName().equals("Dimentio")) {
												DimentioAttack(p6, p5);
											}
											if(p6.getName().equals("Grizz")) {
												GrizzAttack(p6, p5, p3, p1);
											}
											if(p6.getName().equals("Evil")) {
												EvilAttack(p6, p5, p3, p1);
											}
											if(p6.getName().equals("Mason")) {
												MasonAttack(p6, p5);
											}
											if(p6.getName().equals("Airic")) {
												AiricAttack(p6, p5);
											}
											if(p6.getName().equals("Julian")) {
												JulianAttack(p6, p5, p3, p1);
											}
											if(p6.getName().equals("Gash")) {
												GashAttack(p6, p5);
											}
											if(p6.getName().equals("Mayhem")) {
												MayhemAttack(p6, p5);
											}
											if(p6.getName().equals("Gates")) {
												Location l = SetCursor(p6, p1, p3, p5, p2, p4);
												GatesAttack(p6, p5, l);
											}
											if(p6.getName().equals("Ayson")) {
												AysonAttack(p6, p5);
											}
											if(p6.getName().equals("Chloe")) {
												ChloeAttack(p6, p5, p2, p4);
											}
											if(p6.getName().equals("Hopper")) {
												HopperAttack(p6, p5);
											}
											if(p6.getName().equals("Redgar")) {
												RedgarAttack(p6, p5);
											}
											if(p6.getName().equals("Radar")) {
												RadarAttack(p6, p5);
											}
											if(p6.getName().equals("Oona")) {
												OonaAttack(p6, p5);
											}
											if(p6.getName().equals("Augie")) {
												AugieAttack(p6, p5);
											}
											if(p6.getName().equals("Ruby")) {
												RubyAttack(p6, p5);
											}
											if(p6.getName().equals("Norman")) {
												NormanAttack(p6, p1, p2, p4);
											}
											if(p6.getName().equals("Chief")) {
												ChiefAttack(p6, p5);
											}
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
			String audio = "victoryedit.wav";
			Music victoryPlayer = new Music(audio, false); 
			victoryPlayer.play();
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
		while(true) {
		}
		
	}
	
	
	public static Player CharacterSelection(Player p, String name, boolean start, int x, int y) {
		//p.setImage("backupdancer.png");
		switch (name) {
		  case "Lunar":
			  p = new Player(2750, 250, start, name, x, y, 10, 10, 5);
			  name = bold + color + 147 + "m" + "Lun" + color + 87 + "m" + "ar" + reset;
			  p.skin(name);
			  p.setC(147);
			  //p.setImage("lunar.png");
		    break;
		  case "Finley":
			  p = new Player(3325, 275, start, name, x, y, 9, 9, 6);
			  name = bold + color + 223 + "m" + "Fin" + color + 196 + "m" + "ley" + reset;
			  p.skin(name);
			  p.setC(223);
		    break;
		  case "Mack":
			  p = new Player(2900, 250, start, name, x, y, 11, 10, 6);
			  name = bold + color + 200 + "m" + "Mack" + reset;
			  p.skin(name);
			  p.setC(200);
		    break;
		  case "Solar":
			  p = new Player(2800, 175, start, name, x, y, 10, 10, 6);
			  name = bold + color + 220 + "m" + "S" + color + 221 + "m" + "o"  + color + 222 + "m" + "l" + color + 212 + "m" + "a" + color + 213 + "m" + "r" + reset;
			  p.skin(name);
			  p.setC(220);
		    break;
		  case "Cherry":
			  p = new Player(2700, 100, start, name, x, y, 12, 9, 5);
			  name = bold + color + 196 + "m" + "C" + color + 197 + "m" + "h"  + color + 198 + "m" + "e" + color + 199 + "m" + "r" + color + 200 + "m" + "r" + color + 201 + "m" + "y" + reset;
			  p.skin(name);
			  p.setC(196);
		    break;
		  case "Dylan":
			  p = new Player(2700, 200, start, name, x, y, 10, 10, 5);
			  name = bold + color + 41 + "m" + "D" + color + 42 + "m" + "y"  + color + 43 + "m" + "l" + color + 44 + "m" + "a" + color + 45 + "m" + "n" + reset;
			  p.skin(name);
			  p.setC(41);
		    break;
		  case "Zero":
			  p = new Player(2625, 225, start, name, x, y, 10, 10, 6);
			  name = bold + color + 196 + "m" + "Z" + color + 243 + "m" + "er" + color + 196 + "m" + "o" + reset;
			  p.skin(name);
			  p.setC(196);
		    break;
		  case "Kailani":
			  p = new Player(2650, 225, start, name, x, y, 30, 10, 6);
			  name = bold + color + 187 + "m" + "Kai" + color + 87 + "m" + "l" + color + 69 + "m" + "ani" + reset;
			  p.skin(name);
			  p.setC(187);
		    break;
		  case "Max":
			  p = new Player(3000, 50, start, name, x, y, 9, 9, 6);
			  name = bold + color + 180 + "m" + "Max" + reset;
			  p.skin(name);
			  p.setC(180);
		    break;
		  case "Aidan":
			  p = new Player(2700, 175, start, name, x, y, 11, 9, 6);
			  name = bold + color + 209 + "m" + "A" + color + 75 + "m" + "ida" + color + 209 + "m" + "n" + reset;
			  p.skin(name);
			  p.setC(209);
		    break;
		  case "Via":
			  p = new Player(2900, 325, start, name, x, y, 10, 10, 6);
			  name = bold + color + 124 + "m" + "Via" + reset;
			  p.skin(name);
			  p.setC(124);
		    break;
		  case "Alex":
			  p = new Player(2750, 250, start, name, x, y, 12, 10, 6);
			  name = bold + color + 196 + "m" + "A" + color + 226 + "m" + "le" + color + 196 + "m" + "x" + reset;
			  p.skin(name);
			  p.setC(196);
		    break;
		  case "Louis":
			  p = new Player(2750, 225, start, name, x, y, 10, 10, 6);
			  name = bold + color + 226 + "m" + "Lo" + color + 141 + "m" + "uis" + reset;
			  p.skin(name);
			  p.setC(226);
		    break;
		  case "Eli":
			  p = new Player(2525, 100, start, name, x, y, 10, 10, 7);
			  name = bold + color + 39 + "m" + "Eli" + reset;
			  p.skin(name);
			  p.setC(39);
		    break;
		  case "Ashley":
			  p = new Player(2550, 200, start, name, x, y, 10, 10, 6);
			  name = bold + color + 80 + "m" + "A" + color + 81 + "m" + "s"  + color + 87 + "m" + "h" + color + 183 + "m" + "l" + color + 218 + "m" + "e" + color + 225 + "m" + "y" + reset;
			  p.skin(name);
			  p.setC(80);
		    break;
		  case "Orion":
			  p = new Player(4325, 225, start, name, x, y, 10, 10, 6);
			  name = bold + color + 101 + "m" + "O" + color + 102 + "m" + "r"  + color + 103 + "m" + "i" + color + 104 + "m" + "o" + color + 105 + "m" + "n" + reset;
			  p.skin(name);
			  p.setC(101);
		    break;
		  case "Bedrock":
			  p = new Player(4350, 400, start, name, x, y, 7, 7, 6);
			  name = bold + color + 240 + "m" + "B" + color + 241 + "m" + "e"  + color + 242 + "m" + "d" + color + 243 + "m" + "r" + color + 245 + "m" + "o" + color + 246 + "m" + "c" + color + 247 + "m" + "k" + reset;
			  p.skin(name);
			  p.setC(240);
		    break;
		  case "Rocco":
			  p = new Player(2725, 200, start, name, x, y, 10, 10, 6);
			  name = bold + color + 65 + "m" + "R" + color + 66 + "m" + "o"  + color + 67 + "m" + "c" + color + 68 + "m" + "c" + color + 69 + "m" + "o" + reset;
			  p.skin(name);
			  p.setC(65);
		    break;
		  case "Sammi":
			  p = new Player(2750, 275, start, name, x, y, 10, 10, 6);
			  name = bold + color + 160 + "m" + "S" + color + 196 + "m" + "a"  + color + 202 + "m" + "m" + color + 166 + "m" + "m" + color + 208 + "m" + "i" + reset;
			  p.skin(name);
			  p.setC(160);
		    break;
		  case "Clara":
			  p = new Player(2850, 300, start, name, x, y, 10, 10, 6);
			  name = bold + color + 219 + "m" + "C" + color + 218 + "m" + "l"  + color + 217 + "m" + "a" + color + 216 + "m" + "r" + color + 215 + "m" + "a" + reset;
			  p.skin(name);
			  p.setC(219);
		    break;
		  case "Thunder":
			  p = new Player(4250, 300, start, name, x, y, 8, 8, 6);
			  name = bold + color + 226 + "m" + "Thu" + color + 244 + "m" + "nder" + reset;
			  p.skin(name);
			  p.setC(226);
		    break;
		  case "Liam":
			  p = new Player(2825, 225, start, name, x, y, 10, 10, 6);
			  name = bold + color + 196 + "m" + "Liam" + reset;
			  p.skin(name);
			  p.setC(196);
		    break;
		  case "Axol":
			  p = new Player(2650, 175, start, name, x, y, 10, 10, 6);
			  name = bold + color + 222 + "m" + "A" + color + 223 + "m" + "x"  + color + 224 + "m" + "o" + color + 225 + "m" + "l" + reset;
			  p.skin(name);
			  p.setC(222);
		    break;
		  case "Katrina":
			  p = new Player(2650, 175, start, name, x, y, 10, 10, 7);
			  name = bold + color + 32 + "m" + "K" + color + 38 + "m" + "a"  + color + 211 + "m" + "t" + color + 211 + "m" + "r" + color + 211 + "m" + "i" + color + 38 + "m" + "n" + color + 32 + "m" + "a" + reset;
			  p.skin(name);
			  p.setC(32);
		    break;
		  case "Midnite":
			  p = new Player(2900, 275, start, name, x, y, 12, 10, 7);
			  name = bold + color + 21 + "m" + "M" + color + 20 + "m" + "i"  + color + 39 + "m" + "d" + color + 75 + "m" + "n" + color + 111 + "m" + "i" + color + 183 + "m" + "t" + color + 247 + "m" + "e" + reset;
			  p.skin(name);
			  p.setC(21);
		    break;
		  case "Xara":
			  p = new Player(3550, 200, start, name, x, y, 10, 10, 6);
			  name = bold + color + 129 + "m" + "X" + color + 128 + "m" + "a" + color + 209 + "m" + "r" + color + 208 + "m" + "a" + reset;
			  p.skin(name);
			  p.setC(129);
		    break;
		  case "Kithara":
			  p = new Player(3850, 225, start, name, x, y, 10, 10, 7);
			  name = bold + color + 93 + "m" + "K" + color + 92 + "m" + "i"  + color + 99 + "m" + "t" + color + 98 + "m" + "h" + color + 105 + "m" + "a" + color + 104 + "m" + "r" + color + 103 + "m" + "a" + reset;
			  p.skin(name);
			  p.setC(93);
		    break;
		  case "Archer":
			  p = new Player(2700, 175, start, name, x, y, 15, 8, 6);
			  name = bold + color + 76 + "m" + "A" + color + 77 + "m" + "r"  + color + 70 + "m" + "c" + color + 71 + "m" + "h" + color + 58 + "m" + "e" + color + 59 + "m" + "r" + reset;
			  p.skin(name);
			  p.setC(76);
		    break;
		  case "Tom":
			  p = new Player(4225, 250, start, name, x, y, 10, 10, 6);
			  name = bold + color + 196 + "m" + "T" + color + 160 + "m" + "o"  + color + 161 + "m" + "m" + reset + " " + bold + color + 88 + "m" + "P" + color + 89 + "m" + "h" + color + 52 + "m" + "a" + color + 53 + "m" + "n" + reset;
			  p.skin(name);
			  p.setC(196);
		    break;
		  case "Dimentio":
			  p = new Player(3700, 250, start, name, x, y, 10, 10, 8);
			  name = bold + color + 202 + "m" + "D" + color + 203 + "m" + "i"  + color + 196 + "m" + "m" + color + 197 + "m" + "e" + color + 118 + "m" + "n" + color + 119 + "m" + "t" + color + 76 + "m" + "i" + color + 77 + "m" + "o" + reset;
			  p.skin(name);
			  p.setC(202);
		    break;
		  case "Grizz":
			  p = new Player(4200, 200, start, name, x, y, 10, 10, 8);
			  name = bold + color + 88 + "m" + "M" + color + 58 + "m" + "r"  + color + 59 + "m" + "." + reset  + bold + color + 53 + "m" + "G" + color + 54 + "m" + "r" + color + 55 + "m" + "i" + color + 91 + "m" + "z" + color + 91 + "m" + "z" + reset;
			  p.skin(name);
			  p.setC(88);
		    break;
		  case "Evil":
			  p = new Player(2700, 250, start, name, x, y, 11, 8, 7);
			  name = bold + color + 196 + "m" + "E" + color + 197 + "m" + "v"  + color + 198 + "m" + "i" + color + 199 + "m" + "l" + reset + " " + bold + color + 199 + "m" + "L" + color + 147 + "m" + "un" + color + 87 + "m" + "ar" + reset;
			  p.skin(name);
			  p.setC(196);
		    break;
		  case "Mason":
			  p = new Player(2850, 275, start, name, x, y, 11, 10, 6);
			  name = bold + color + 129 + "m" + "M" + color + 128 + "m" + "a"  + color + 93 + "m" + "s" + color + 92 + "m" + "o" + color + 91 + "m" + "n" + reset;
			  p.skin(name);
			  p.setC(129);
		    break;
		  case "Burt":
			  p = new Player(2800, 250, start, name, x, y, 10, 11, 6);
			  name = bold + color + 21 + "m" + "Burt" + reset;
			  p.skin(name);
			  p.setC(21);
		    break;
		  case "Airic":
			  p = new Player(2750, 250, start, name, x, y, 10, 10, 6);
			  name = bold + color + 19 + "m" + "Air" + color + 250 + "m" + "ic" + reset;
			  p.skin(name);
			  p.setC(19);
		    break;
		  case "Julian":
			  p = new Player(2800, 200, start, name, x, y, 10, 8, 7);
			  name = bold + color + 214 + "m" + "J" + color + 159 + "m" + "u"  + color + 214 + "m" + "l" + color + 159 + "m" + "i" + color + 214 + "m" + "a" + color + 159 + "m" + "n" + reset;
			  p.skin(name);
			  p.setC(214);
		    break;
		  case "Gash":
			  p = new Player(3950, 200, start, name, x, y, 10, 10, 5);
			  name = bold + color + 130 + "m" + "G" + color + 131 + "m" + "a" + color + 166 + "m" + "s" + color + 167 + "m" + "h" + reset;
			  p.skin(name);
			  p.setC(130);
		    break;
		  case "Mayhem":
			  p = new Player(3600, 200, start, name, x, y, 10, 10, 8);
			  name = bold + color + 171 + "m" + "M" + color + 170 + "m" + "a"  + color + 169 + "m" + "y" + color + 135 + "m" + "h" + color + 134 + "m" + "e" + color + 133 + "m" + "m" + reset;
			  p.skin(name);
			  p.setC(171);
		    break;
		  case "Gates":
			  p = new Player(3500, 200, start, name, x, y, 10, 10, 6);
			  name = bold + color + 44 + "m" + "D" + color + 45 + "m" + "r"  + color + 81 + "m" + "." + reset  + bold + color + 117 + "m" + "G" + color + 116 + "m" + "a" + color + 111 + "m" + "t" + color + 110 + "m" + "e" + color + 109 + "m" + "s" + reset;
			  p.skin(name);
			  p.setC(44);
		    break;
		  case "Audrey":
			  p = new Player(2550, 150, start, name, x, y, 10, 10, 8);
			  name = bold + color + 111 + "m" + "A" + color + 147 + "m" + "u"  + color + 123 + "m" + "d" + color + 122 + "m" + "r" + color + 221 + "m" + "e" + color + 220 + "m" + "y" + reset;
			  p.skin(name);
			  p.setC(111);
		    break;
		  case "Ayson":
			  p = new Player(3150, 150, start, name, x, y, 10, 10, 6);
			  name = bold + color + 45 + "m" + "A" + color + 54 + "m" + "y"  + color + 45 + "m" + "s" + color + 54 + "m" + "o" + color + 45 + "m" + "n" + reset + " "  + bold + color + 54 + "m" + "B" + color + 45 + "m" + "r" + color + 54 + "m" + "o" + color + 45 + "m" + "t" + color + 54 + "m" + "h" + color + 45 + "m" + "e" + color + 54 + "m" + "r"+ color + 45 + "m" + "s" + reset;
			  p.skin(name);
			  p.setC(45);
		    break;
		  case "Chloe":
			  p = new Player(2650, 200, start, name, x, y, 10, 10, 5);
			  name = bold + color + 136 + "m" + "C" + color + 137 + "m" + "h"  + color + 138 + "m" + "l" + color + 139 + "m" + "o" + color + 140 + "m" + "e" + reset;
			  p.skin(name);
			  p.setC(136);
		    break;
		  case "Hopper":
			  p = new Player(4200, 175, start, name, x, y, 10, 10, 7);
			  name = bold + color + 40 + "m" + "H" + color + 34 + "m" + "o"  + color +35 + "m" + "p" + color + 22 + "m" + "p" + color + 238 + "m" + "e"+ color + 239 + "m" + "r" + reset;
			  p.skin(name);
			  p.setC(40);
		    break;
		  case "Redgar":
			  p = new Player(2500, 150, start, name, x, y, 10, 10, 6);
			  name = bold + color + 33 + "m" + "R" + color + 32 + "m" + "e"  + color +27 + "m" + "d" + color + 26 + "m" + "g" + color + 21 + "m" + "a"+ color + 20 + "m" + "r" + reset;
			  p.skin(name);
			  p.setC(33);
		    break;
		  case "Radar":
			  p = new Player(2800, 200, start, name, x, y, 10, 10, 3);
			  name = bold + color + 147 + "m" + "R" + color + 146 + "m" + "a"  + color +145 + "m" + "d" + color + 144 + "m" + "a" + color + 143 + "m" + "r" + reset;
			  p.skin(name);
			  p.setC(147);
		    break;
		  case "Oona":
			  p = new Player(2600, 175, start, name, x, y, 10, 10, 8);
			  name = bold + color + 202 + "m" + "O" + color + 203 + "m" + "o"  + color +204 + "m" + "n" + color + 205 + "m" + "a" + reset;
			  p.skin(name);
			  p.setC(202);
		    break;
		  case "Ruby":
			  p = new Player(2750, 200, start, name, x, y, 10, 10, 5);
			  name = bold + color + 161 + "m" + "R" + color + 162 + "m" + "u"  + color +163 + "m" + "b" + color + 164 + "m" + "y" + reset;
			  p.skin(name);
			  p.setC(161);
		    break;
		  case "Augie":
			  p = new Player(4050, 150, start, name, x, y, 10, 10, 7);
			  name = bold + color + 34 + "m" + "C" + color + 70 + "m" + "a"  + color + 71 + "m" + "p" + color + 72 + "m" + "t" + color + 73 + "m" + "a" + color + 74 + "m" + "i" + color + 75 + "m" + "n" + reset + " "  + bold + color + 220 + "m" + "A" + color + 221 + "m" + "u" + color + 196 + "m" + "g" + color + 197 + "m" + "i" + color + 198 + "m" + "e" + reset;
			  p.skin(name);
			  p.setC(34);
		    break;
		  case "Norman":
			  p = new Player(2550, 125, start, name, x, y, 10, 10, 7);
			  name = bold + color + 46 + "m" + "N" + color + 47 + "m" + "o"  + color + 48 + "m" + "r" + color + 49 + "m" + "m" + color + 50 + "m" + "a" + color + 51 + "m" + "n" + reset;
			  p.skin(name);
			  p.setC(46);
		    break;
		  case "Jesse":
			  p = new Player(2700, 75, start, name, x, y, 10, 10, 7);
			  name = bold + color + 160 + "m" + "J" + color + 161 + "m" + "e"  + color +197 + "m" + "s" + color + 214 + "m" + "s" + color + 215 + "m" + "e" + reset;
			  p.skin(name);
			  p.setC(160);
		    break;
		  case "Chief":
			  p = new Player(4400, 175, start, name, x, y, 8, 8, 6);
			  name = bold + color + 69 + "m" + "C" + color + 68 + "m" + "h"  + color + 63 + "m" + "i" + color + 62 + "m" + "e" + color + 61 + "m" + "f" + reset;
			  p.skin(name);
			  p.setC(69);
		    break;
		  case "Bolo":
			  p = new Player(3050, 225, start, name, x, y, 13, 9, 6);
			  name = bold + color + 47 + "m" + "Bolo" + reset;
			  p.skin(name);
			  p.setC(47);
		    break;
		  case "Anjelika":
			  p = new Player(3900, 250, start, name, x, y, 10, 10, 6);
			  name = bold + color + 214 + "m" + "A" + color + 214 + "m" + "n"  + color + 215 + "m" + "j" + color + 215 + "m" + "e" + color + 216 + "m" + "l" + color + 216 + "m" + "i" + color + 217 + "m" + "k"+ color + 217 + "m" + "a" + reset;
			  p.skin(name);
			  p.setC(214);
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
	
	public static void Jump(Player p, Player a, Player b, Location l) {
		if(!p.canJump()) {
			System.out.println("No more jumps left!");
			System.out.println();
			return;
		}
		if(!p.getLoc().eqLoc(a.getLoc()) && !p.getLoc().eqLoc(b.getLoc())) {
			System.out.println("No allies in range to use a jump with!");
			System.out.println();
			return;
		}
		Scanner input = new Scanner(System.in);

		if(!p.inReach(l, 6)) {
			System.out.println("Can't jump there!");
			System.out.println();
			return;
		}
		if(GameSim.b.hasTrench(l.getX(), l.getY())) {
			System.out.println("Can't jump there!");
			System.out.println();
			return;
		}
		if(p.getLoc().eqLoc(a.getLoc()) && p.canJump()) {
			if(!a.isAlive()) {
				System.out.println("Ally is downed! Cannot perform a jump.");
				System.out.println();
				return;
			}else {
				p.getLoc().set(l.getX(), l.getY());
				p.useJump();
			}
		}else if(p.getLoc().eqLoc(b.getLoc()) && p.canJump()) {
			if(!b.isAlive()) {
				System.out.println("Ally is downed! Cannot perform a jump.");
				System.out.println();
				return;
			}else {
				p.getLoc().set(l.getX(), l.getY());
				p.useJump();
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
		for(int i = 0; i < orbs.size(); i++) {
			if(p.getLoc().eqLoc(orbs.get(i).getLoc()) && !p.ultReady()) {
				p.getOrb();
				orbs.remove(i);
				System.out.println(p.getName() + " has gotten an orb.");
			}
		}
		try {
			String audio = "jump.wav";
			Music victoryPlayer = new Music(audio, false); 
			victoryPlayer.play();
		}catch (Exception e) {
			System.out.println(e);
		}
		System.out.println("Jumped to " + p.getLoc() + ".");
		System.out.println();
	}
	
	public static void Dash(Player p, Player a, Player b, Player c) {
		if(!p.canDash()) {
			System.out.println("No more dashes left!");
			System.out.println();
			return;
		}
		double damage = 100;
		if(p.getName().equals("Clara") && p.usedAbility()) {
			damage = 175;
		}
		if (p.getName().equals("Clara") && p.ultActive()) {
			damage = 250;
		}
		if(p.getLoc().eqLoc(a.getLoc()) && p.canDash() && a.isAlive()) {
			a.takeDamage(damage);
			p.useDash();
			try {
				String audio = "dash.wav";
				Music victoryPlayer = new Music(audio, false); 
				victoryPlayer.play();
			}catch (Exception e) {
				System.out.println(e);
			}
		}
		if(p.getLoc().eqLoc(b.getLoc()) && p.canDash() && b.isAlive()) {
			b.takeDamage(damage);
			p.useDash();
			try {
				String audio = "dash.wav";
				Music victoryPlayer = new Music(audio, false); 
				victoryPlayer.play();
			}catch (Exception e) {
				System.out.println(e);
			}
		}
		if(p.getLoc().eqLoc(c.getLoc()) && p.canDash() && c.isAlive()) {
			c.takeDamage(damage);
			p.useDash();
			try {
				String audio = "dash.wav";
				Music victoryPlayer = new Music(audio, false); 
				victoryPlayer.play();
			}catch (Exception e) {
				System.out.println(e);
			}
		}
		System.out.println();
	}
	
	public static void Movement(Player p, Player a, Player z, Player c, Player d, Player e) {
		if(p.isParalyzed()) {
			System.out.println("Cannot move while paralyzed!");
			System.out.println();
			return;
		}
		String s = "";
		boolean move = true;
		boolean hasMoved = false;
		ArrayList<Effect> e1 = new ArrayList<Effect>();
		Effect ZeroIgnite = new Effect("ignite", 0, 1);
		Effect ZeroDaze = new Effect("daze", 0, 1);
		e1.add(ZeroDaze);
		e1.add(ZeroIgnite);
		double rand = Math.random();
		Scanner input = new Scanner(System.in);
		if(p.getMovement() == 0) {
			System.out.println("No more movement left!");
			System.out.println();
		}else {
			b.printField(p, a, z, c, d, e, orbs, cover, p, a, z, c);
			while(move) {
				System.out.print("Which way do you want to move: ");
				String moveResponse = input.next();
				switch (moveResponse) {
				  case "a":
					  if(b.hasTrench(p.getLoc().getX() - 1, p.getLoc().getY())) {
						  System.out.println("Cannot move onto a Trench tile!");
						  b.printField(p, a, z, c, d, e, orbs, cover, p, a, z, c);
						  break;
					  }
					  p.move(p.getLoc().getX() - 1, p.getLoc().getY());
					  hasMoved = true;
					  b.printField(p, a, z, c, d, e, orbs, cover, p, a, z, c);
					  System.out.println("Relocated to " + p.getLoc() + ". " + p.getMovement() + " movement left.");
				    break;
				  case "d":
					  if(b.hasTrench(p.getLoc().getX() + 1, p.getLoc().getY())) {
						  System.out.println("Cannot move onto a Trench tile!");
						  b.printField(p, a, z, c, d, e, orbs, cover, p, a, z, c);
						  break;
					  }
					  p.move(p.getLoc().getX() + 1, p.getLoc().getY());
					  hasMoved = true;
					  b.printField(p, a, z, c, d, e, orbs, cover, p, a, z, c);
					  System.out.println("Relocated to " + p.getLoc() + ". " + p.getMovement() + " movement left.");
				    break;
				  case "w":
					  if(b.hasTrench(p.getLoc().getX(), p.getLoc().getY() - 1)) {
						  System.out.println("Cannot move onto a Trench tile!");
						  b.printField(p, a, z, c, d, e, orbs, cover, p, a, z, c);
						  break;
					  }
					  p.move(p.getLoc().getX(), p.getLoc().getY() - 1);
					  hasMoved = true;
					  b.printField(p, a, z, c, d, e, orbs, cover, p, a, z, c);
					  System.out.println("Relocated to " + p.getLoc() + ". " + p.getMovement() + " movement left.");
				    break;
				  case "s":
					  if(b.hasTrench(p.getLoc().getX(), p.getLoc().getY() + 1)) {
						  System.out.println("Cannot move onto a Trench tile!");
						  b.printField(p, a, z, c, d, e, orbs, cover, p, a, z, c);
						  break;
					  }
					  p.move(p.getLoc().getX(), p.getLoc().getY() + 1);
					  hasMoved = true;
					  b.printField(p, a, z, c, d, e, orbs, cover, p, a, z, c);
					  System.out.println("Relocated to " + p.getLoc() + ". " + p.getMovement() + " movement left.");
				    break;
				  case "r":
					  Dash(p, a, z, c);
				    break;
				  case "j":
					  Location l = SetCursor(p, a, z, c, d, e);
					  Jump(p, d, e, l);
					  b.printField(p, a, z, c, d, e, orbs, cover, p, a, z, c);
				    break;
				  case "p":
					  p.increaseMovement(1);
				    break;
				  case "c":
					  System.out.println("Movement completed.");
					  hasMoved = false;
					  move = false;
				    break;
				}
				if(hasMoved) {
					if(a.hasSights() && a.inRange(p) && !a.isStunned()) {
						p.takeDamage(a.getDamage() * 0.9);
						a.useSight();
						if(a.getName().equals("Zero") && a.inRange(p, 4) && rand <= 0.1) {
							p.addEffects(e1);
							p.applyEffects();
						}
					}
					if(z.hasSights() && z.inRange(p) && !z.isStunned()) {
						p.takeDamage(z.getDamage() * 0.9);
						z.useSight();
						if(z.getName().equals("Zero") && z.inRange(p, 4) && rand <= 0.1) {
							p.addEffects(e1);
							p.applyEffects();
						}
					}
					if(c.hasSights() && c.inRange(p) && !c.isStunned()) {
						p.takeDamage(c.getDamage() * 0.9);
						c.useSight();
						if(c.getName().equals("Zero") && c.inRange(p, 4) && rand <= 0.1) {
							p.addEffects(e1);
							p.applyEffects();
						}
					}
				}
				for(int i = 0; i < orbs.size(); i++) {
					if(p.getLoc().eqLoc(orbs.get(i).getLoc()) && !p.ultReady()) {
						p.getOrb();
						orbs.remove(i);
						System.out.println(p.getName() + " has gotten an orb.");
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
				System.out.println();
				if(p.getMovement() == 0) {
					System.out.println("Out of movement.");
					move = false;
					System.out.println();
				}
				if(!p.isAlive()) {
					move = false;
				}
			}
		}
	}
	
	public static Location SetCursor(Player p, Player a, Player z, Player c, Player d, Player e) {
		Scanner input = new Scanner(System.in);
		boolean move = true;
		Location l = new Location(p.getLoc().getX(), p.getLoc().getY());
		b.setCursor(l);
		while(move) {
			b.printField(p, a, z, c, d, e, orbs, cover, p, a, z, c);
			System.out.print("Select a point on the battlefield with the cursor: ");
			String moveResponse = input.next();
			switch (moveResponse) {
			  case "a":
				  l.adjust(-1, 0);
				  b.moveCursor(l);
				  b.printField(p, a, z, c, d, e, orbs, cover, p, a, z, c);
				  System.out.println("Moved cursor to " + l + ".");
			    break;
			  case "d":
				  l.adjust(1, 0);
				  b.moveCursor(l);
				  b.printField(p, a, z, c, d, e, orbs, cover, p, a, z, c);
				  System.out.println("Moved cursor to " + l + ".");
			    break;
			  case "w":
				  l.adjust(0, -1);
				  b.moveCursor(l);
				  b.printField(p, a, z, c, d, e, orbs, cover, p, a, z, c);
				  System.out.println("Moved cursor to " + l + ".");
			    break;
			  case "s":
				  l.adjust(0, 1);
				  b.moveCursor(l);
				  b.printField(p, a, z, c, d, e, orbs, cover, p, a, z, c);
				  System.out.println("Moved cursor to " + l + ".");
			    break;
			  case "c":
				  System.out.println("Cursor set to " + l + ".");
				  move = false;
			    break;
			}
			System.out.println();
		}
		b.removeCursor();
		return l;
	}
	
	public static void SpawnOrbs() {
		int d = 2;
		int e = 0;
		if(turns2 >= 8) {
			e = 2;
		}
		if(turns2 >= 15) {
			e = 6;
			d = 0;
		}
		if(turns2 >= 3) {
			orbs.clear();
			for(int i = 0; i < d + e; i++) {
				int randomX = (int)(Math.random() * (25 - 16 + 1)) + 16;
				int randomY = (int)(Math.random() * (25 - 16 + 1)) + 16;
				Location l = new Location(randomX, randomY);
				Orb o = new Orb(l);
				orbs.add(o);
			}
			for(int i = 0; i < d; i++) {
				int randomX = (int)(Math.random() * (4 - 0 + 1)) + 0;
				int randomY = (int)(Math.random() * (41 - 0 + 1)) + 0;
				double rand = Math.random();
				if(rand <= 0.5) {
					randomX = randomX + 37;
				}
				Location l = new Location(randomX, randomY);
				Orb o = new Orb(l);
				orbs.add(o);
			}
			System.out.println("New orbs have spawned!");
		}
	}
	
	public static void SpawnCover() {
		int d1 = 2;
		int d2 = 4;
		if(turns2 >= 8) {
			d1 = 1;
			d2 = 2;
		}
		if(turns2 >= 15) {
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
		p.attack(a);
		if(a.inRange(b, 5)) {
			p.attack(b);
		}
		if(a.inRange(c, 5)) {
			p.attack(c);
		}
		System.out.println();
	}
	
	public static void LunarAbility(Player p) {
		ArrayList<Effect> e = new ArrayList<Effect>();
		Effect LunarPower = new Effect("power", 0.3, 2);
		p.heal(0.2);
		e.add(LunarPower);
		p.addEffects(e);
		p.applyEffects();
		p.setCooldown(3);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void LunarUltimate(Player p, Player a, Player b, Player c) {
		p.cleanse();
		Scanner input = new Scanner(System.in);
		System.out.println("1: " + a.getName() +". Health: " +  a.getHealth() + "/" + a.getMaxHP());
		System.out.println("2: " + b.getName() +". Health: " +  b.getHealth() + "/" + b.getMaxHP());
		System.out.println("3: " + c.getName() +". Health: " +  c.getHealth() + "/" + c.getMaxHP());
		System.out.print("Who do you want to make a copy of: ");
		String targetResponse = input.next();
		if(targetResponse.equals("1")) {
			p.setUlt();
			p.setName(a.getName());
			p.resetCooldown();
			p.resetAttack();
			p.resetUlt();
		}
		if(targetResponse.equals("2")) {
			p.setUlt();
			p.setName(b.getName());
			p.resetCooldown();
			p.resetAttack();
			p.resetUlt();
		}
		if(targetResponse.equals("3")) {
			p.setUlt();
			p.setName(c.getName());
			p.resetCooldown();
			p.resetAttack();
			p.resetUlt();
		}
		System.out.println();
		System.out.println("\"Time to meet your match!\"");
		System.out.println();
	}
	
	public static void SolarAttack(Player p, Player a) {
		p.attack(a);
		ArrayList<Effect> e = new ArrayList<Effect>();
		Effect SolarIgnite = new Effect("ignite", 0, 2);
		e.add(SolarIgnite);
		a.addEffects(e);
		System.out.println();
	}
	
	public static void SolarAbility(Player p) {
		ArrayList<Effect> e = new ArrayList<Effect>();
		Effect SolarProtect = new Effect("protect", 0.5, 3);
		e.add(SolarProtect);
		p.addEffects(e);
		p.applyEffects();
		p.setCooldown(3);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void SolarUltimate(Player p, Player a, Player b, Player c) {
		Scanner input = new Scanner(System.in);
		ArrayList<Effect> e = new ArrayList<Effect>();
		Effect SolarIgnite = new Effect("ignite", 0, 2);
		ArrayList<Effect> e2 = new ArrayList<Effect>();
		Effect SolarIgnite2 = new Effect("ignite", 0, 2);
		ArrayList<Effect> e3 = new ArrayList<Effect>();
		Effect SolarIgnite3 = new Effect("ignite", 0, 2);
		e.add(SolarIgnite);
		e2.add(SolarIgnite2);
		e3.add(SolarIgnite3);
		System.out.println("1: " + a.getName() +a.showHP() +  a.getHealth() + "/" + a.getMaxHP());
		System.out.println("2: " + b.getName() +b.showHP() +  b.getHealth() + "/" + b.getMaxHP());
		System.out.println("3: " + c.getName() +c.showHP() +  c.getHealth() + "/" + c.getMaxHP());
		System.out.print("Who do you want to see the Sunrise: ");
		String targetResponse = input.next();
		if(targetResponse.equals("1")) {
			a.takeDamage(700);
			a.addEffects(e);
			a.applyEffects();
			if(b.inRange(a, 10)) {
				b.addEffects(e2);
				b.applyEffects();
			}
			if(c.inRange(a, 10)) {
				c.addEffects(e3);
				c.applyEffects();
			}
			p.heal(0.2);
			p.resetUlt();
		}
		if(targetResponse.equals("2")) {
			b.takeDamage(700);
			b.addEffects(e);
			b.applyEffects();
			if(a.inRange(b, 10)) {
				a.addEffects(e2);
				a.applyEffects();
			}
			if(c.inRange(b, 10)) {
				c.addEffects(e3);
				c.applyEffects();
			}
			p.heal(0.2);
			p.resetUlt();
		}
		if(targetResponse.equals("3")) {
			c.takeDamage(700);
			c.addEffects(e);
			c.applyEffects();
			if(a.inRange(c, 10)) {
				a.addEffects(e2);
				a.applyEffects();
			}
			if(b.inRange(c, 10)) {
				b.addEffects(e3);
				b.applyEffects();
			}
			p.heal(0.2);
			p.resetUlt();
		}
		System.out.println();
		System.out.println("\"You're all Toast now!\"");
		System.out.println();
	}
	
	public static void MackAttack(Player p, Player a) {
		p.attack(a);
		double rand = Math.random();
		if(rand <= 0.1) {
			ArrayList<Effect> e = new ArrayList<Effect>();
			Effect MackDaze = new Effect("daze", 0, 1);
			e.add(MackDaze);
			a.addEffects(e);
			a.applyEffects();
		}
		System.out.println();
	}
	
	public static void MackAbility(Player p, Player a, Player b, Player c, Location l) {
		if(GameSim.b.hasTrench(l.getX(), l.getY())) {
			System.out.println("Can't go there!");
			System.out.println();
			return;
		}
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
				System.out.println(p.getName() + " has gotten an orb.");
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
				ArrayList<Effect> e = new ArrayList<Effect>();
				Effect MackDaze = new Effect("daze", 0, 1);
				e.add(MackDaze);
				a.addEffects(e);
				a.applyEffects();
			}
		}
		if(p.inRange(b)) {
			p.attack(b);
			p.resetAttack();
			double rand = Math.random();
			if(rand <= 0.1) {
				ArrayList<Effect> e = new ArrayList<Effect>();
				Effect MackDaze = new Effect("daze", 0, 1);
				e.add(MackDaze);
				b.addEffects(e);
				b.applyEffects();
			}
		}
		if(p.inRange(c)) {
			p.attack(c);
			p.resetAttack();
			double rand = Math.random();
			if(rand <= 0.1) {
				ArrayList<Effect> e = new ArrayList<Effect>();
				Effect MackDaze = new Effect("daze", 0, 1);
				e.add(MackDaze);
				c.addEffects(e);
				c.applyEffects();
			}
		}
		p.setCooldown(3);
		System.out.println();
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void MackUltimate(Player p) {
		ArrayList<Effect> e = new ArrayList<Effect>();
		Effect MackProtect = new Effect("protect", 0.65, 4);
		Effect MackPower = new Effect("power", 0.1, 3);
		p.setSights(4);
		e.add(MackPower);
		e.add(MackProtect);
		p.addEffects(e);
		p.applyEffects();
		p.resetUlt();
		System.out.println("\"Dalton! Come rip them apart for me!\"");
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
			System.out.println("1: " + a.getName() +a.showHP() +  a.getHealth() + "/" + a.getMaxHP() + ". Cover: " + a.getCover());
			System.out.println("2: " + b.getName() +b.showHP() +  b.getHealth() + "/" + b.getMaxHP() + ". Cover: " + b.getCover());
			System.out.println("3: " + c.getName() +c.showHP() +  c.getHealth() + "/" + c.getMaxHP() + ". Cover: " + c.getCover());
			System.out.println("4: " + d.getName() +d.showHP() +  d.getHealth() + "/" + d.getMaxHP() + ". Cover: " + d.getCover());
			System.out.println("5: " + e.getName() +e.showHP() +  e.getHealth() + "/" + e.getMaxHP() + ". Cover: " + e.getCover());
			for(int i = 0; i < 2; i++) {
				System.out.print("Who do you want to send a Cherry Missile to: ");
				String targetResponse = input.next();
				if(targetResponse.equals("1")) {
					p.attack(a);
					a.takeDamage(100);
				}
				if(targetResponse.equals("2")) {
					p.attack(b);
					b.takeDamage(100);
				}
				if(targetResponse.equals("3")) {
					p.attack(c);
					c.takeDamage(100);
				}
				if(targetResponse.equals("4")) {
					d.heal(0.2);
				}
				if(targetResponse.equals("5")) {
					e.heal(0.2);
				}
			}
		}else {
			System.out.println("1: " + a.getName() +a.showHP() +  a.getHealth() + "/" + a.getMaxHP() + ". In Range: " + range + ". Cover: " + a.getCover());
			System.out.println("2: " + b.getName() +b.showHP() +  b.getHealth() + "/" + b.getMaxHP() + ". In Range: " + range2 + ". Cover: " + b.getCover());
			System.out.println("3: " + c.getName() +c.showHP() +  c.getHealth() + "/" + c.getMaxHP() + ". In Range: " + range3 + ". Cover: " + c.getCover());
			System.out.println("4: " + d.getName() +d.showHP() +  d.getHealth() + "/" + d.getMaxHP() + ". In Range: " + range4 + ". Cover: " + d.getCover());
			System.out.println("5: " + e.getName() +e.showHP() +  e.getHealth() + "/" + e.getMaxHP() + ". In Range: " + range5 + ". Cover: " + e.getCover());
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
						d.heal(0.1);
					}else {
						i--;
						System.out.println();
						System.out.println("Target is out of range!");
						System.out.println();
					}
				}
				if(targetResponse.equals("5")) {
					if(p.inRange(e)) {
						e.heal(0.1);
					}else {
						i--;
						System.out.println();
						System.out.println("Target is out of range!");
						System.out.println();
					}
				}
			}
		}
		System.out.println();
	}
	
	public static void CherryAbility(Player p, Player a, Player b, Player c) {
		if(p.ultActive()) {
			ArrayList<Effect> e = new ArrayList<Effect>();
			ArrayList<Effect> e2 = new ArrayList<Effect>();
			ArrayList<Effect> e3 = new ArrayList<Effect>();
			Effect CherryWeaken = new Effect("weak", 0.4, 1);
			Effect CherryWeaken2 = new Effect("weak", 0.4, 1);
			Effect CherryWeaken3 = new Effect("weak", 0.4, 1);
			Effect CherryDaze = new Effect("daze", 0, 1);
			Effect CherryDaze2 = new Effect("daze", 0, 1);
			Effect CherryDaze3 = new Effect("daze", 0, 1);
			e.add(CherryWeaken);
			e.add(CherryDaze);
			e2.add(CherryWeaken2);
			e2.add(CherryDaze2);
			e3.add(CherryWeaken3);
			e3.add(CherryDaze3);
			a.addEffects(e);
			a.applyEffects();
			b.addEffects(e2);
			b.applyEffects();
			c.addEffects(e3);
			c.applyEffects();
			p.setCooldown(3);
			System.out.println(p.voiceline());
			System.out.println();
		}else {
			ArrayList<Effect> e = new ArrayList<Effect>();
			ArrayList<Effect> e2 = new ArrayList<Effect>();
			ArrayList<Effect> e3 = new ArrayList<Effect>();
			Effect CherryWeaken = new Effect("weak", 0.3, 1);
			Effect CherryWeaken2 = new Effect("weak", 0.3, 1);
			Effect CherryWeaken3 = new Effect("weak", 0.3, 1);
			e.add(CherryWeaken);
			e2.add(CherryWeaken2);
			e3.add(CherryWeaken3);
			if(!a.inRange(p, 15) && !b.inRange(p, 15) && !c.inRange(p, 15)) {
				System.out.println("No targets in range!");
				System.out.println();
				return;
			}
			if(a.inRange(p, 15)) {
				a.addEffects(e);
				a.applyEffects();
			}
			if(b.inRange(p, 15)) {
				b.addEffects(e2);
				b.applyEffects();
			}
			if(c.inRange(p, 15)) {
				c.addEffects(e3);
				c.applyEffects();
			}
			p.setCooldown(3);
			System.out.println(p.voiceline());
			System.out.println();
		}
	}
	
	public static void CherryUltimate(Player p) {
		p.setUlt();
		p.setMaxHP(3700);
		System.out.println("\"My engineering mechanics will put you all in a panic!\"");
		System.out.println();
	}
	
	public static void FinleyAttack(Player p, Player a) {
		ArrayList<Effect> e = new ArrayList<Effect>();
		Effect FinleyStun = new Effect("stun", 0, 1);
		e.add(FinleyStun);
		if(a.inRange(p, 2)) {
			p.attack(a);
			p.resetAttack();
			p.attack(a);
			a.addEffects(e);
			a.applyEffects();
		}else {
			double rand = Math.random();
			if(rand <= 0.1) {
				a.addEffects(e);
				a.applyEffects();
			}
			p.attack(a);
		}
		System.out.println();
	}
	
	public static void FinleyAbility(Player p, Player a, Player b, Player c) {
		Scanner input = new Scanner(System.in);
		ArrayList<Effect> e = new ArrayList<Effect>();
		ArrayList<Effect> e2 = new ArrayList<Effect>();
		ArrayList<Effect> e3 = new ArrayList<Effect>();
		Effect FinleyVulnerable = new Effect("vulnerable", 0.1, 2);
		Effect FinleyVulnerable2 = new Effect("vulnerable", 0.1, 2);
		Effect FinleyVulnerable3 = new Effect("vulnerable", 0.1, 2);
		e.add(FinleyVulnerable);
		e2.add(FinleyVulnerable2);
		e3.add(FinleyVulnerable3);
		if(!p.inRange(a, 20) && !p.inRange(b, 20) && !p.inRange(c, 20)) {
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
		System.out.println("1: " + a.getSkin() +". Health: " +  a.getHealth() + "/" + a.getMaxHP() + ". In Range: " + range + ".");
		System.out.println("2: " + b.getSkin() +". Health: " +  b.getHealth() + "/" + b.getMaxHP() + ". In Range: " + range2 + ".");
		System.out.println("3: " + c.getSkin() +". Health: " +  c.getHealth() + "/" + c.getMaxHP() + ". In Range: " + range3 + ".");
		System.out.println("Where do you want to send the Skateboard Surprise: ");
		String targetResponse = input.next();
		if(targetResponse.equals("1")) {
			if(p.inRange(a, 20)) {
				a.takeDamage(250);
				a.addEffects(e);
				a.applyEffects();
				if(b.inRange(a, 3)) {
					b.takeDamage(250);
					b.addEffects(e2);
					b.applyEffects();
				}
				if(c.inRange(a, 3)) {
					c.takeDamage(250);
					c.addEffects(e3);
					c.applyEffects();
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
				b.takeDamage(250);
				b.addEffects(e);
				b.applyEffects();
				if(a.inRange(b, 3)) {
					a.takeDamage(250);
					a.addEffects(e2);
					a.applyEffects();
				}
				if(c.inRange(b, 3)) {
					c.takeDamage(250);
					c.addEffects(e3);
					c.applyEffects();
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
				c.takeDamage(250);
				c.addEffects(e2);
				c.applyEffects();
				if(a.inRange(c, 3)) {
					a.takeDamage(250);
					a.addEffects(e);
					a.applyEffects();
				}
				if(b.inRange(c, 3)) {
					b.takeDamage(250);
					b.addEffects(e3);
					b.applyEffects();
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
			b.knockbacked();
			c.knockbacked();
			p.resetUlt();
		}
		if(targetResponse.equals("2")) {
			b.takeDamage(800);
			a.knockbacked();
			c.knockbacked();
			p.resetUlt();
		}
		if(targetResponse.equals("3")) {
			c.takeDamage(800);
			b.knockbacked();
			a.knockbacked();
			p.resetUlt();
		}
		System.out.println();
		System.out.println("\"I'm a meteor the way you're going to be extinct soon.\"");
		System.out.println();
	}
	
	public static void BurtAttack(Player p, Player a) {
		p.attack(a);
		double rand = Math.random();
		if(rand <= 0.15) {
			ArrayList<Effect> e = new ArrayList<Effect>();
			Effect BurtParalyze = new Effect("paralyze", 0, 1);
			e.add(BurtParalyze);
			a.addEffects(e);
			a.applyEffects();
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
			b.takeDamage(140);
			c.takeDamage(140);
			p.resetUlt();
		}
		if(targetResponse.equals("2")) {
			b.takeDamage(700);
			a.takeDamage(140);
			c.takeDamage(140);
			p.resetUlt();
		}
		if(targetResponse.equals("3")) {
			c.takeDamage(700);
			b.takeDamage(140);
			a.takeDamage(140);
			p.resetUlt();
		}
		System.out.println();
		System.out.println("\"We'll see who's better at combat!\"");
		System.out.println();
	}
	
	public static void BoloAttack(Player p, Player a) {
		p.attack(a);
		double rand = Math.random();
		if(rand <= 0.2) {
			ArrayList<Effect> e = new ArrayList<Effect>();
			Effect BoloParalyze = new Effect("paralyze", 0, 1);
			Effect BoloIgnite = new Effect("ignite", 0, 1);
			e.add(BoloParalyze);
			e.add(BoloIgnite);
			a.addEffects(e);
			a.applyEffects();
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
						b.takeDamage(b.getMaxHP() * 0.35);
					}else {
						c.takeDamage(c.getMaxHP() * 0.35);
					}
				}else {
					a.takeDamage(a.getMaxHP() * 0.35);
				}
			}
			if(randomY == 2) {
				if(!b.isAlive()) {
					if(c.isAlive()) {
						c.takeDamage(c.getMaxHP() * 0.35);
					}else {
						a.takeDamage(a.getMaxHP() * 0.35);
					}
				}else {
					b.takeDamage(b.getMaxHP() * 0.35);
				}
			}
			if(randomY == 3) {
				if(!c.isAlive()) {
					if(a.isAlive()) {
						a.takeDamage(a.getMaxHP() * 0.35);
					}else {
						b.takeDamage(b.getMaxHP() * 0.35);
					}
				}else {
					c.takeDamage(c.getMaxHP() * 0.35);
				}
			}
		}
		if(randomX == 2) {
			if(randomY == 1) {
				if(!a.isAlive() && !b.isAlive()) {
					c.takeDamage(c.getMaxHP() * 0.35);
				}else {
					a.takeDamage(a.getMaxHP() * 0.35);
					b.takeDamage(b.getMaxHP() * 0.35);
				}
			}
			if(randomY == 2) {
				if(!b.isAlive() && !c.isAlive()) {
					a.takeDamage(a.getMaxHP() * 0.35);
				}else {
					b.takeDamage(b.getMaxHP() * 0.35);
					c.takeDamage(c.getMaxHP() * 0.35);
				}
			}
			if(randomY == 3) {
				if(!a.isAlive() && !c.isAlive()) {
					b.takeDamage(b.getMaxHP() * 0.35);
				}else {
					a.takeDamage(a.getMaxHP() * 0.35);
					c.takeDamage(c.getMaxHP() * 0.35);
				}
			}
		}
		if(randomX == 3) {
			a.takeDamage(a.getMaxHP() * 0.35);
			b.takeDamage(b.getMaxHP() * 0.35);
			c.takeDamage(c.getMaxHP() * 0.35);
		}
		p.resetUlt();
		System.out.println("\"You'll truely want to hope I miss this time!\"");
		System.out.println();
	}
	
	public static void DylanAttack(Player p, Player a) {
		System.out.println();
		Scanner input = new Scanner(System.in);
		ArrayList<Effect> e = new ArrayList<Effect>();
		Effect DylanIgnite = new Effect("ignite", 0, 1);
		Effect DylanFreeze = new Effect("freeze", 0, 1);
		System.out.print("Would you like to use fire or ice: ");
		String attackResponse = input.next();
		if(attackResponse.equals("f")) {
			p.attack(a);
			e.add(DylanIgnite);
			a.addEffects(e);
			a.applyEffects();
		}else {
			p.attack(a);
			e.add(DylanFreeze);
			a.addEffects(e);
			a.applyEffects();
		}
		System.out.println();
	}
	
	public static void DylanAbility(Player p, Player a, Player b, Player c) {
		Scanner input = new Scanner(System.in);
		ArrayList<Effect> e = new ArrayList<Effect>();
		Effect DylanVulnerable = new Effect("vulnerable", 0.1, 1);
		e.add(DylanVulnerable);
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
			if(p.inRange(a, 15)) {
				a.takeDamage(250);
				a.addEffects(e);
				a.applyEffects();
				p.setCooldown(3);
			}else {
				System.out.println();
				System.out.println("Target is out of range!");
				System.out.println();
			}
		}
		if(targetResponse.equals("2")) {
			if(p.inRange(b, 15)) {
				b.takeDamage(250);
				b.addEffects(e);
				b.applyEffects();
				p.setCooldown(3);
			}else {
				System.out.println();
				System.out.println("Target is out of range!");
				System.out.println();
			}
		}
		if(targetResponse.equals("3")) {
			if(p.inRange(c, 15)) {
				c.takeDamage(250);
				c.addEffects(e);
				c.applyEffects();
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
		ArrayList<Effect> e1 = new ArrayList<Effect>();
		ArrayList<Effect> e2 = new ArrayList<Effect>();
		ArrayList<Effect> e3 = new ArrayList<Effect>();
		Effect DylanPower = new Effect("power", 0.25, 1);
		Effect DylanPower2 = new Effect("power", 0.25, 1);
		Effect DylanPower3 = new Effect("power", 0.25, 1);
		e1.add(DylanPower);
		e2.add(DylanPower2);
		e3.add(DylanPower3);
		p.addEffects(e1);
		p.applyEffects();
		d.addEffects(e2);
		d.applyEffects();
		e.addEffects(e3);
		e.applyEffects();
		ArrayList<Effect> e12 = new ArrayList<Effect>();
		ArrayList<Effect> e22 = new ArrayList<Effect>();
		ArrayList<Effect> e32 = new ArrayList<Effect>();
		Effect DylanIgnite = new Effect("ignite", 0, 2);
		Effect DylanIgnite2 = new Effect("ignite", 0, 2);
		Effect DylanIgnite3 = new Effect("ignite", 0, 2);
		Effect DylanFreeeze = new Effect("freeze", 0, 1);
		Effect DylanFreeeze2 = new Effect("freeze", 0, 1);
		Effect DylanFreeeze3 = new Effect("freeze", 0, 1);
		e12.add(DylanIgnite);
		e12.add(DylanFreeeze);
		e22.add(DylanIgnite2);
		e22.add(DylanFreeeze2);
		e32.add(DylanIgnite3);
		e32.add(DylanFreeeze3);
		a.addEffects(e12);
		a.applyEffects();
		b.addEffects(e22);
		b.applyEffects();
		c.addEffects(e32);
		c.applyEffects();
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
			p.heal(0.03);
		}
		System.out.println();
	}
	
	public static void ZeroAbility(Player p, Player a, Player b) {
		ArrayList<Effect> e = new ArrayList<Effect>();
		ArrayList<Effect> e2 = new ArrayList<Effect>();
		ArrayList<Effect> e3 = new ArrayList<Effect>();
		Effect ZeroPower = new Effect("power", 0.1, 1);
		Effect ZeroPower2 = new Effect("power", 0.1, 1);
		Effect ZeroPower3 = new Effect("power", 0.1, 1);
		e.add(ZeroPower);
		e2.add(ZeroPower2);
		e3.add(ZeroPower3);
		p.cleanse();
		a.cleanse();
		b.cleanse();
		p.heal(0.15);
		a.heal(0.15);
		b.heal(0.15);
		p.addEffects(e);
		p.applyEffects();
		a.addEffects(e2);
		a.applyEffects();
		b.addEffects(e3);
		b.applyEffects();
		p.setCooldown(3);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void ZeroUltimate(Player p) {
		p.resetUlt();
		p.setSights(6);
		System.out.println("\"Walk near me, and I'll /smite you.\"");
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
		System.out.println("1: " + a.getName() +". Health: " +  a.getHealth() + "/" + a.getMaxHP() + ". In Range: " + range + ". Cover: " + a.getCover());
		System.out.println("2: " + b.getName() +". Health: " +  b.getHealth() + "/" + b.getMaxHP() + ". In Range: " + range2 + ". Cover: " + b.getCover());
		System.out.println("3: " + c.getName() +". Health: " +  c.getHealth() + "/" + c.getMaxHP() + ". In Range: " + range3 + ". Cover: " + c.getCover());
		if(!p.inRange(a) && !p.inRange(b) && !p.inRange(c)) {
			System.out.println();
			System.out.println("No targets in range!");
			System.out.println();
			return;
		}
		for(int i = 0; i < 4; i++) {
			int randomNum = (int)(Math.random() * (75 - 50 + 1)) + 50;
			System.out.print("Who do you want to open a rift over: ");
			String targetResponse = input.next();
			if(targetResponse.equals("1")) {
				if(p.inRange(a)) {
					p.attack(a);
					a.takeDamage(randomNum);
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
				}else {
					i--;
					System.out.println();
					System.out.println("Target is out of range!");
					System.out.println();
				}
			}
		}
		System.out.println();
	}
	
	public static void MaxAbility(Player p, Player a, Player b) {
		Scanner input = new Scanner(System.in);
		ArrayList<Effect> e = new ArrayList<Effect>();
		Effect MaxPower = new Effect("power", 0.3, 1);
		Effect MaxProtect = new Effect("protect", 0.2, 2);
		e.add(MaxPower);
		e.add(MaxProtect);
		System.out.println("1: " + a.getSkin() +a.showHP() +  a.getHealth() + "/" + a.getMaxHP() + ".");
		System.out.println("2: " + b.getSkin() +b.showHP() +  b.getHealth() + "/" + b.getMaxHP() + ".");
		System.out.println("Who do you want to give guidance to: ");
		String targetResponse = input.next();
		if(targetResponse.equals("1")) {
			a.addEffects(e);
			a.applyEffects();
			p.setCooldown(3);
		}
		if(targetResponse.equals("2")) {
			b.addEffects(e);
			b.applyEffects();
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
			p.resetUlt();
		}
		if(targetResponse.equals("2")) {
			b.takeDamage(750);
			p.resetUlt();
		}
		if(targetResponse.equals("3")) {
			c.takeDamage(750);
			p.resetUlt();
		}
		ArrayList<Effect> e = new ArrayList<Effect>();
		ArrayList<Effect> e2 = new ArrayList<Effect>();
		ArrayList<Effect> e3 = new ArrayList<Effect>();
		Effect MaxParalyze = new Effect("paralyze", 0, 2);
		Effect MaxParalyze2 = new Effect("paralyze", 0, 2);
		Effect MaxParalyze3 = new Effect("paralyze", 0, 2);
		e.add(MaxParalyze);
		e2.add(MaxParalyze2);
		e3.add(MaxParalyze3);
		a.addEffects(e);
		a.applyEffects();
		b.addEffects(e2);
		b.applyEffects();
		c.addEffects(e3);
		c.applyEffects();
		System.out.println();
		System.out.println("\"My latest creation was a failure, just like you.\"");
		System.out.println();
	}
	
	public static void EliAttack(Player p, Player a) {
		System.out.println();
		Scanner input = new Scanner(System.in);
		ArrayList<Effect> e = new ArrayList<Effect>();
		Effect EliFreeze = new Effect("freeze", 0, 1);
		Effect EliParalyze = new Effect("paralyze", 0, 1);
		Effect EliVulnerable = new Effect("vulnerable", 0.25, 2);
		Effect EliBlind = new Effect("blind", 0.3, 2);
		System.out.println("1: Little Buddy");
		System.out.println("2: Medium Buddy");
		System.out.println("3: Big Buddy");
		System.out.print("Which buddy do you want to summon: ");
		String targetResponse = input.next();
		if(targetResponse.equals("1")) {
			p.attack(a);
			a.takeDamage(75);
			double rand = Math.random();
			if(rand <= 0.2) {
				e.add(EliParalyze);
				e.add(EliFreeze);
				a.addEffects(e);
				a.applyEffects();
			}
		}
		if(targetResponse.equals("2")) {
			p.attack(a);
			double rand = Math.random();
			if(rand <= 0.2) {
				e.add(EliVulnerable);
				a.addEffects(e);
				a.applyEffects();
			}
		}
		if(targetResponse.equals("3")) {
			p.attack(a);
			a.takeDamage(75);
			double rand = Math.random();
			if(rand <= 0.2) {
				e.add(EliBlind);
				a.addEffects(e);
				a.applyEffects();
			}
		}
		System.out.println();
	}
	
	public static void EliAbility(Player p, Player a, Player b) {
		ArrayList<Effect> e = new ArrayList<Effect>();
		Effect EliRegen = new Effect("heal", 0.15, 2);
		ArrayList<Effect> e2 = new ArrayList<Effect>();
		Effect EliRegen2 = new Effect("heal", 0.15, 2);
		ArrayList<Effect> e3 = new ArrayList<Effect>();
		Effect EliRegen3 = new Effect("heal", 0.15, 2);
		e.add(EliRegen);
		e2.add(EliRegen2);
		e3.add(EliRegen3);
		p.addEffects(e);
		a.addEffects(e2);
		b.addEffects(e3);
		p.setCooldown(4);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void EliUltimate(Player p, Player a, Player b) {
		ArrayList<Effect> e = new ArrayList<Effect>();
		ArrayList<Effect> e2 = new ArrayList<Effect>();
		ArrayList<Effect> e3 = new ArrayList<Effect>();
		Effect EliRegen = new Effect("heal", 0.4, 2);
		Effect EliPower = new Effect("power", 0.35, 1);
		Effect EliRegen2 = new Effect("heal", 0.4, 2);
		Effect EliPower2 = new Effect("power", 0.35, 1);
		Effect EliRegen3 = new Effect("heal", 0.4, 2);
		Effect EliPower3 = new Effect("power", 0.35, 1);
		e.add(EliRegen);
		e.add(EliPower);
		e2.add(EliRegen2);
		e2.add(EliPower2);
		e3.add(EliRegen3);
		e3.add(EliPower3);
		p.cleanse();
		a.cleanse();
		b.cleanse();
		p.addEffects(e);
		p.applyEffects();
		a.addEffects(e2);
		a.applyEffects();
		b.addEffects(e3);
		b.applyEffects();
		p.resetUlt();
		System.out.println("\"Time to make a splash!\"");
		System.out.println();
	}
	
	public static void ViaAttack(Player p, Player a, Player b, Player c) {
		p.attack(a);
		double rand = Math.random();
		if(rand <= 0.1) {
			ArrayList<Effect> e = new ArrayList<Effect>();
			Effect ViaParalyze = new Effect("paralyze", 0, 2);
			e.add(ViaParalyze);
			a.addEffects(e);
			a.applyEffects();
		}
		Location l = a.getLoc();
		Location ln = new Location(l.getX() + 3, l.getY());
		Location ln2 = new Location(l.getX(), l.getY() + 3);
		Location ln3 = new Location(l.getX() - 3, l.getY());
		Location ln4 = new Location(l.getX(), l.getY() - 3);
		if(b.getLoc().eqLoc(ln) || b.getLoc().eqLoc(ln2) || b.getLoc().eqLoc(ln3) || b.getLoc().eqLoc(ln4)) {
			b.takeDamage(200);
		}
		if(c.getLoc().eqLoc(ln) || c.getLoc().eqLoc(ln2) || c.getLoc().eqLoc(ln3) || c.getLoc().eqLoc(ln4)) {
			c.takeDamage(200);
		}
		System.out.println();
	}
	
	public static void ViaAbility(Player p, Player a, Player b, Player c, Location l) {
		if(GameSim.b.hasTrench(l.getX(), l.getY())) {
			System.out.println("Can't send Arro there!");
			System.out.println();
			return;
		}
		if(a.inRange(l, 10) && a.isAlive()) {
			a.getLoc().set(l.getX(), l.getY());
			ArrayList<Effect> e = new ArrayList<Effect>();
			Effect ViaVulnerable = new Effect("vulnerable", 0.1, 1);
			e.add(ViaVulnerable);
			a.addEffects(e);
			a.applyEffects();
			a.resetCover();
		}
		if(b.inRange(l, 10) && b.isAlive()) {
			b.getLoc().set(l.getX(), l.getY());
			ArrayList<Effect> e = new ArrayList<Effect>();
			Effect ViaVulnerable = new Effect("vulnerable", 0.1, 1);
			e.add(ViaVulnerable);
			b.addEffects(e);
			b.applyEffects();
			b.resetCover();
		}
		if(c.inRange(l, 10) && c.isAlive()) {
			c.getLoc().set(l.getX(), l.getY());
			ArrayList<Effect> e = new ArrayList<Effect>();
			Effect ViaVulnerable = new Effect("vulnerable", 0.1, 1);
			e.add(ViaVulnerable);
			c.addEffects(e);
			c.applyEffects();
			c.resetCover();
		}
		p.setCooldown(3);
		System.out.println();
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void ViaUltimate(Player p, Player a, Player b, Player c) {
		ArrayList<Effect> d = new ArrayList<Effect>();
		Effect e4 = new Effect("power", 0.5, 2);
		d.add(e4);
		ArrayList<Effect> e = new ArrayList<Effect>();
		ArrayList<Effect> e2 = new ArrayList<Effect>();
		ArrayList<Effect> e3 = new ArrayList<Effect>();
		Effect ViaParalyze = new Effect("paralyze", 0, 2);
		Effect ViaParalyze2 = new Effect("paralyze", 0, 2);
		Effect ViaParalyze3 = new Effect("paralyze", 0, 2);
		Effect ViaDaze = new Effect("daze", 0, 2);
		Effect ViaDaze2 = new Effect("daze", 0, 2);
		Effect ViaDaze3 = new Effect("daze", 0, 2);
		e.add(ViaParalyze);
		e2.add(ViaParalyze2);
		e3.add(ViaParalyze3);
		e.add(ViaDaze);
		e2.add(ViaDaze2);
		e3.add(ViaDaze3);
		a.addEffects(e);
		a.applyEffects();
		b.addEffects(e2);
		b.applyEffects();
		c.addEffects(e3);
		c.applyEffects();
		p.addEffects(d);
		p.applyEffects();
		p.resetUlt();
		System.out.println("\"Killer Wail incoming!\"");
		System.out.println();
	}
	
	public static void LouisAttack(Player p, Player a) {
		p.attack(a);
		if(turns2 >= 4) {
			p.increaseDPS(0.02);
		}
		System.out.println();
	}
	
	public static void LouisAbility(Player p, Player a, Player b, Player c) {
		Location l = p.getLoc();
		ArrayList<Effect> e = new ArrayList<Effect>();
		ArrayList<Effect> e2 = new ArrayList<Effect>();
		ArrayList<Effect> e3 = new ArrayList<Effect>();
		Effect LouisVulnerable = new Effect("vulnerable", 0.2, 2);
		Effect LouisVulnerable2 = new Effect("vulnerable", 0.2, 2);
		Effect LouisVulnerable3 = new Effect("vulnerable", 0.2, 2);
		Effect LouisFreeze = new Effect("freeze", 0, 2);
		Effect LouisFreeze2 = new Effect("freeze", 0, 2);
		Effect LouisFreeze3 = new Effect("freeze", 0, 2);
		e.add(LouisFreeze);
		e2.add(LouisFreeze2);
		e3.add(LouisFreeze3);
		e.add(LouisVulnerable);
		e2.add(LouisVulnerable2);
		e3.add(LouisVulnerable3);
		if(!a.inRange(p, 12) && !b.inRange(p, 12) && !c.inRange(p, 12)) {
			System.out.println("No targets in range!");
			System.out.println();
			return;
		}
		if(a.inRange(l, 12)) {
			a.addEffects(e);
			a.applyEffects();
			a.knockbacked();
		}
		if(b.inRange(l, 12)) {
			b.addEffects(e2);
			b.applyEffects();
			b.knockbacked();
		}
		if(c.inRange(l, 12)) {
			c.addEffects(e3);
			c.applyEffects();
			c.knockbacked();
		}
		p.setCooldown(3);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void LouisUltimate(Player p, Player a, Player b, Player c, Location l) {
		if(a.inRange(l, 10)) {
			a.takeDamage(600);
			a.knockbacked();
		}
		if(b.inRange(l, 10)) {
			b.takeDamage(600);
			b.knockbacked();
		}
		if(c.inRange(l, 10)) {
			c.takeDamage(600);
			c.knockbacked();
		}
		p.setSights(3);
		p.resetUlt();
		System.out.println("\"Ohhh Guardiannnn!\"");
		System.out.println();
	}
	
	public static void AlexAttack(Player p, Player a) {
		a.getLoc().set(p.getLoc().getX(), p.getLoc().getY());
		p.attack(a);
		a.knockbacked();
		p.increaseDPS(0.01);
		System.out.println();
	}
	
	public static void AlexAbility(Player p, Player a, Player b) {
		ArrayList<Effect> e = new ArrayList<Effect>();
		ArrayList<Effect> e2 = new ArrayList<Effect>();
		ArrayList<Effect> e3 = new ArrayList<Effect>();
		ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i=1; i<6; i++) list.add(i);
        Collections.shuffle(list);
        for (int i=0; i<3; i++) {
        	if(list.get(i) == 1) {
        		p.heal(0.2);
        		a.heal(0.2);
        		b.heal(0.2);
        	}
        	if(list.get(i) == 2) {
        		p.cleanse();
        		a.cleanse();
        		b.cleanse();
        	}
        	if(list.get(i) == 3) {
        		Effect AlexRefine = new Effect("refine", 0, 2);
        		Effect AlexRefine2 = new Effect("refine", 0, 2);
        		Effect AlexRefine3 = new Effect("refine", 0, 2);
        		e.add(AlexRefine);
        		e2.add(AlexRefine2);
        		e3.add(AlexRefine3);
        	}
        	if(list.get(i) == 4) {
        		Effect AlexRefine = new Effect("power", 0.15, 2);
        		Effect AlexRefine2 = new Effect("power", 0.15, 2);
        		Effect AlexRefine3 = new Effect("power", 0.15, 2);
        		e.add(AlexRefine);
        		e2.add(AlexRefine2);
        		e3.add(AlexRefine3);
        	}
        	if(list.get(i) == 5) {
        		Effect AlexRefine = new Effect("protect", 0.3, 3);
        		Effect AlexRefine2 = new Effect("protect", 0.3, 3);
        		Effect AlexRefine3 = new Effect("protect", 0.3, 3);
        		e.add(AlexRefine);
        		e2.add(AlexRefine2);
        		e3.add(AlexRefine3);
        	}
        }
        p.addEffects(e);
		p.applyEffects();
		a.addEffects(e2);
		a.applyEffects();
		b.addEffects(e3);
		b.applyEffects();
        p.setCooldown(4);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void AlexUltimate(Player p) {
		p.setUlt();
		ArrayList<Effect> e = new ArrayList<Effect>();
		Effect AlexProtect = new Effect("protect", 0.75, 30);
		e.add(AlexProtect);
		p.addEffects(e);
		p.applyEffects();
		System.out.println("\"I've grinded for days for this stuff. Watch out.\"");
		System.out.println();
	}
	
	public static void OrionAttack(Player p, Player a) {
		p.attack(a);
		p.getLoc().set(a.getLoc().getX(), a.getLoc().getY());
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
		a.knockbacked();
		double rand = Math.random();
		if(rand <= 0.15) {
			ArrayList<Effect> e = new ArrayList<Effect>();
			Effect OrionDaze = new Effect("daze", 0, 2);
			e.add(OrionDaze);
			a.addEffects(e);
			a.applyEffects();
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
		p.setCooldown(3);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void OrionUltimate(Player p, Player a, Player b) {
		p.increaseMovement(8);
		a.increaseMovement(8);
		b.increaseMovement(8);
		p.heal(0.15);
		a.heal(0.15);
		b.heal(0.15);
		ArrayList<Effect> e = new ArrayList<Effect>();
		ArrayList<Effect> e2 = new ArrayList<Effect>();
		ArrayList<Effect> e3 = new ArrayList<Effect>();
		Effect OrionProtect = new Effect("protect", 0.6, 2);
		Effect OrionProtect2 = new Effect("protect", 0.6, 2);
		Effect OrionProtect3 = new Effect("protect", 0.6, 2);
		e.add(OrionProtect);
		e2.add(OrionProtect2);
		e3.add(OrionProtect3);
		p.addEffects(e);
		p.applyEffects();
		a.addEffects(e2);
		a.applyEffects();
		b.addEffects(e3);
		b.applyEffects();
		p.resetUlt();
		System.out.println("\"Show them that we aren't backing down!\"");
		System.out.println();
	}
	
	public static void KailaniAttack(Player p, Player a) {
		p.attack(a);
		double rand = Math.random();
		if(rand <= 0.1) {
			ArrayList<Effect> e = new ArrayList<Effect>();
			Effect MackDaze = new Effect("daze", 0, 2);
			e.add(MackDaze);
			a.addEffects(e);
			a.applyEffects();
		}
		System.out.println();
	}
	
	public static void KailaniAbility(Player p, Player a, Player b, Player c, Location l) {
		if(!p.inReach(l, 15)) {
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
			a.takeDamage(a.getMaxHP() * 0.1);
			a.knockbacked();
		}
		if(p.inRange(b, 5)) {
			b.takeDamage(b.getMaxHP() * 0.1);
			b.knockbacked();
		}
		if(p.inRange(c, 5)) {
			c.takeDamage(c.getMaxHP() * 0.1);
			c.knockbacked();
		}
		p.getLoc().set(l.getX(), l.getY());
		for(int i = 0; i < orbs.size(); i++) {
			if(p.getLoc().eqLoc(orbs.get(i).getLoc()) && !p.ultReady()) {
				p.getOrb();
				orbs.remove(i);
				System.out.println(p.getName() + " has gotten an orb.");
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
			a.takeDamage(a.getMaxHP() * 0.1);
			a.knockbacked();
		}
		if(p.inRange(b, 5)) {
			b.takeDamage(b.getMaxHP() * 0.1);
			b.knockbacked();
		}
		if(p.inRange(c, 5)) {
			c.takeDamage(c.getMaxHP() * 0.1);
			c.knockbacked();
		}
		p.setCooldown(3);
		System.out.println();
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void KailaniUltimate(Player p, Player a, Player b, Player c) {
		a.takeDamage(650);
		a.knockbacked();
		b.takeDamage(650);
		b.knockbacked();
		c.takeDamage(650);
		c.knockbacked();
		ArrayList<Effect> e = new ArrayList<Effect>();
		Effect KailaniProtect = new Effect("protect", 0.55, 3);
		e.add(KailaniProtect);
		p.addEffects(e);
		p.applyEffects();
		p.resetUlt();
		System.out.println("\"Flood their victory out of here!\"");
		System.out.println();
	}
	
	public static void AshleyAttack(Player p, Player a) {
		p.attack(a);
		double rand = Math.random();
		if(rand <= 0.1) {
			ArrayList<Effect> e = new ArrayList<Effect>();
			Effect AshleyPoison = new Effect("poison", 0.2, 2);
			e.add(AshleyPoison);
			a.addEffects(e);
			a.applyEffects();
		}
		System.out.println();
	}
	
	public static void AshleyAbility(Player p, Player a, Player b, Player c, Player d, Player e) {
		p.cleanse();
		d.cleanse();
		e.cleanse();
		p.heal(0.2);
		d.heal(0.2);
		e.heal(0.2);
		ArrayList<Effect> f = new ArrayList<Effect>();
		ArrayList<Effect> e2 = new ArrayList<Effect>();
		ArrayList<Effect> e3 = new ArrayList<Effect>();
		Effect CherryWeaken = new Effect("weak", 0.2, 1);
		Effect CherryWeaken2 = new Effect("weak", 0.2, 1);
		Effect CherryWeaken3 = new Effect("weak", 0.2, 1);
		f.add(CherryWeaken);
		e2.add(CherryWeaken2);
		e3.add(CherryWeaken3);
		a.addEffects(f);
		a.applyEffects();
		b.addEffects(e2);
		b.applyEffects();
		c.addEffects(e3);
		c.applyEffects();
		p.setCooldown(3);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void AshleyUltimate(Player p, Player a, Player b, Player c, Player d, Player e) {
		ArrayList<Effect> e1 = new ArrayList<Effect>();
		ArrayList<Effect> e2 = new ArrayList<Effect>();
		ArrayList<Effect> e3 = new ArrayList<Effect>();
		ArrayList<Effect> e4 = new ArrayList<Effect>();
		ArrayList<Effect> e5 = new ArrayList<Effect>();
		ArrayList<Effect> e6 = new ArrayList<Effect>();
		Effect EliPower = new Effect("power", 0.15, 2);
		Effect EliPower2 = new Effect("power", 0.15, 2);
		Effect EliPower3 = new Effect("power", 0.15, 2);
		Effect AshleyProtect = new Effect("protect", 0.1, 3);
		Effect AshleyProtect2 = new Effect("protect", 0.1, 3);
		Effect AshleyProtect3 = new Effect("protect", 0.1, 3);
		Effect AshleyPoison = new Effect("poison", 0.25, 2);
		Effect AshleyPoison2 = new Effect("poison", 0.25, 2);
		Effect AshleyPoison3 = new Effect("poison", 0.25, 2);
		Effect AshleyBlind = new Effect("blind", 0.1, 2);
		Effect AshleyBlind2 = new Effect("blind", 0.1, 2);
		Effect AshleyBlind3 = new Effect("blind", 0.1, 2);
		p.heal(0.35);
		d.heal(0.35);
		e.heal(0.35);
		e1.add(EliPower);
		e1.add(AshleyProtect);
		e5.add(EliPower2);
		e5.add(AshleyProtect2);
		e6.add(EliPower3);
		e6.add(AshleyProtect3);
		e2.add(AshleyPoison);
		e2.add(AshleyBlind);
		e3.add(AshleyPoison2);
		e3.add(AshleyBlind2);
		e4.add(AshleyPoison3);
		e4.add(AshleyBlind3);
		p.addEffects(e1);
		p.applyEffects();
		a.addEffects(e2);
		a.applyEffects();
		b.addEffects(e3);
		b.applyEffects();
		c.addEffects(e4);
		c.applyEffects();
		d.addEffects(e5);
		d.applyEffects();
		e.addEffects(e6);
		e.applyEffects();
		p.resetUlt();
		System.out.println("\"The tree is up. Now is our chance!\"");
		System.out.println();
	}
	
	public static void BedrockAttack(Player p, Player a, Player b, Player c) {
		ArrayList<Effect> e = new ArrayList<Effect>();
		ArrayList<Effect> e2 = new ArrayList<Effect>();
		ArrayList<Effect> e3 = new ArrayList<Effect>();
		Effect BedrockStun = new Effect("stun", 0, 1);
		Effect BedrockStun2 = new Effect("stun", 0, 1);
		Effect BedrockStun3 = new Effect("stun", 0, 1);
		e.add(BedrockStun);
		e2.add(BedrockStun2);
		e3.add(BedrockStun3);
		if(!p.inRange(a) && !p.inRange(b) && !p.inRange(c)) {
			System.out.println("No targets in range!");
			System.out.println();
			return;
		}
		if(a.inRange(p, 1)) {
			p.attack(a);
			p.resetAttack();
			a.knockbacked();
			double rand = Math.random();
			if(rand <= 0.1) {
				a.addEffects(e);
				a.applyEffects();
			}
		}else if(p.inRange(a)){
			a.takeDamage(175);
			a.knockbacked();
			double rand = Math.random();
			if(rand <= 0.1) {
				a.addEffects(e);
				a.applyEffects();
			}
		}
		if(b.inRange(p, 1)) {
			p.attack(b);
			p.resetAttack();
			b.knockbacked();
			double rand = Math.random();
			if(rand <= 0.1) {
				b.addEffects(e2);
				b.applyEffects();
			}
		}else if(p.inRange(b)) {
			b.takeDamage(175);
			b.knockbacked();
			double rand = Math.random();
			if(rand <= 0.1) {
				b.addEffects(e2);
				b.applyEffects();
			}
		}
		if(c.inRange(p, 1)) {
			p.attack(c);
			c.knockbacked();
			double rand = Math.random();
			if(rand <= 0.1) {
				c.addEffects(e3);
				c.applyEffects();
			}
		}else if(p.inRange(c)) {
			c.takeDamage(175);
			c.knockbacked();
			double rand = Math.random();
			if(rand <= 0.1) {
				c.addEffects(e3);
				c.applyEffects();
			}
		}
		p.setAttacked();
		System.out.println();
	}
	
	public static void BedrockAbility(Player p) {
		p.heal(0.15);
		ArrayList<Effect> e = new ArrayList<Effect>();
		Effect BedrockProtect = new Effect("protect", 0.85, 3);
		e.add(BedrockProtect);
		p.addEffects(e);
		p.applyEffects();
		p.setCooldown(5);
		System.out.println("\"...\"");
		System.out.println();
	}
	
	public static void BedrockUltimate(Player p) {
		p.setUlt();
		System.out.println("\"... ... ...!\"");
		System.out.println();
	}
	
	public static void RoccoAttack(Player p, Player a, Player b, Player c) {
		ArrayList<Effect> e = new ArrayList<Effect>();
		ArrayList<Effect> e2 = new ArrayList<Effect>();
		ArrayList<Effect> e3 = new ArrayList<Effect>();
		Effect RoccoBlind = new Effect("blind", 0.25, 2);
		Effect RoccoBlind2 = new Effect("blind", 0.25, 2);
		Effect RoccoBlind3 = new Effect("blind", 0.25, 2);
		Effect RoccoIgnite = new Effect("ignite", 0, 1);
		Effect RoccoIgnite2 = new Effect("ignite", 0, 1);
		Effect RoccoIgnite3 = new Effect("ignite", 0, 1);
		p.attack(a);
		e.add(RoccoBlind);
		e.add(RoccoIgnite);
		e2.add(RoccoBlind2);
		e2.add(RoccoIgnite2);
		e3.add(RoccoBlind3);
		e3.add(RoccoIgnite3);
		a.addEffects(e);
		a.applyEffects();
		if(a.inRange(b, 5)) {
			double rand = Math.random();
			if(rand <= 0.1) {
				b.addEffects(e2);
				b.applyEffects();
			}
		}
		if(a.inRange(c, 5)) {
			double rand = Math.random();
			if(rand <= 0.1) {
				c.addEffects(e3);
				c.applyEffects();
			}
		}
		System.out.println();
	}
	
	public static void RoccoAbility(Player p, Player a, Player b, Player c) {
		ArrayList<Effect> e = new ArrayList<Effect>();
		ArrayList<Effect> e2 = new ArrayList<Effect>();
		ArrayList<Effect> e3 = new ArrayList<Effect>();
		Effect LouisVulnerable = new Effect("vulnerable", 0.1, 1);
		Effect LouisVulnerable2 = new Effect("vulnerable", 0.1, 1);
		Effect LouisVulnerable3 = new Effect("vulnerable", 0.1, 1);
		Effect LouisFreeze = new Effect("weak", 0.1, 1);
		Effect LouisFreeze2 = new Effect("weak", 0.1, 1);
		Effect LouisFreeze3 = new Effect("weak", 0.1, 1);
		Effect RoccoParalyze = new Effect("paralyze", 0, 1);
		Effect RoccoParalyze2 = new Effect("paralyze", 0, 1);
		Effect RoccoParalyze3 = new Effect("paralyze", 0, 1);
		e.add(LouisFreeze);
		e2.add(LouisFreeze2);
		e3.add(LouisFreeze3);
		e.add(LouisVulnerable);
		e2.add(LouisVulnerable2);
		e3.add(LouisVulnerable3);
		e.add(RoccoParalyze);
		e2.add(RoccoParalyze2);
		e3.add(RoccoParalyze3);
		a.addEffects(e);
		a.applyEffects();
		b.addEffects(e2);
		b.applyEffects();
		c.addEffects(e3);
		c.applyEffects();
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
			p.resetUlt();
		}
		if(targetResponse.equals("1")) {
			if(result.equals("draw")) {
				ArrayList<Effect> e = new ArrayList<Effect>();
				Effect RoccoIgnite = new Effect("ignite", 0, 3);
				e.add(RoccoIgnite);
				a.addEffects(e);
				a.applyEffects();
				System.out.println("You and Rocco tied.");
				System.out.println("\"The fire trap is a classic. Good Choice!\"");
				System.out.println();
				p.resetUlt();
			}
			if(result.equals("lose")) {
				a.takeDamage(a.getMaxHP() * 0.35);
				System.out.println("Rocco beat you.");
				System.out.println("\"BOOM sucker! Better luck next time!\"");
				System.out.println();
				p.resetUlt();
			}
		}
		if(targetResponse.equals("2")) {
			if(result.equals("draw")) {
				ArrayList<Effect> e = new ArrayList<Effect>();
				Effect RoccoIgnite = new Effect("ignite", 0, 3);
				e.add(RoccoIgnite);
				b.addEffects(e);
				b.applyEffects();
				System.out.println("You and Rocco tied.");
				System.out.println("\"The fire trap is a classic. Good Choice!\"");
				System.out.println();
				p.resetUlt();
			}
			if(result.equals("lose")) {
				b.takeDamage(a.getMaxHP() * 0.35);
				System.out.println("Rocco beat you.");
				System.out.println("\"BOOM sucker! Better luck next time!\"");
				System.out.println();
				p.resetUlt();
			}
			
		}
		if(targetResponse.equals("3")) {
			if(result.equals("draw")) {
				ArrayList<Effect> e = new ArrayList<Effect>();
				Effect RoccoIgnite = new Effect("ignite", 0, 3);
				e.add(RoccoIgnite);
				c.addEffects(e);
				c.applyEffects();
				System.out.println("\"The fire trap is a classic. Good Choice!\"");
				System.out.println();
				p.resetUlt();
			}
			if(result.equals("lose")) {
				c.takeDamage(a.getMaxHP() * 0.35);
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
			ArrayList<Effect> e = new ArrayList<Effect>();
			Effect SammiIgnite = new Effect("ignite", 0, 1);
			Effect SammiPoison = new Effect("poison", 0.3, 1);
			e.add(SammiIgnite);
			e.add(SammiPoison);
			a.addEffects(e);
			a.applyEffects();
		}
		System.out.println();
	}
	
	public static void SammiAbility(Player p) {
		p.setRange(200);
		p.setCooldown(3);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void SammiUltimate(Player p) {
		p.cleanse();
		ArrayList<Effect> e = new ArrayList<Effect>();
		Effect SammiPower = new Effect("power", 0.3, 2);
		Effect SammiProtect = new Effect("protect", 0.3, 2);
		Effect SammiRegen = new Effect("heal", 0.2, 2);
		e.add(SammiRegen);
		e.add(SammiProtect);
		e.add(SammiPower);
		p.addEffects(e);
		p.applyEffects();
		p.resetUlt();
		System.out.println("\"Locking in. It's over for them now.\"");
		System.out.println();
	}
	
	public static void ClaraAttack(Player p, Player a) {
		p.attack(a);
		double rand = Math.random();
		if(rand <= 0.35) {
			ArrayList<Effect> e = new ArrayList<Effect>();
			Effect BurtParalyze = new Effect("paralyze", 0, 1);
			Effect ClaraIgnite = new Effect("ignite", 0, 1);
			e.add(BurtParalyze);
			e.add(ClaraIgnite);
			a.addEffects(e);
			a.applyEffects();
		}
		System.out.println();
	}
	
	public static void ClaraAbility(Player p) {
		p.increaseMovement(7);
		p.addDashes(1);
		p.addJumps(2);
		p.setCooldown(2);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void ClaraUltimate(Player p) {
		ArrayList<Effect> e = new ArrayList<Effect>();
		Effect ClaraProtect = new Effect("power", 0.45, 1);
		e.add(ClaraProtect);
		p.addEffects(e);
		p.applyEffects();
		p.increaseMovement(14);
		p.addDashes(2);
		p.addJumps(4);
		p.setUlt();
		p.resetUlt();
		System.out.println("\"Hold on, let me cook.\"");
		System.out.println();
	}
	
	public static void ThunderAttack(Player p, Player a, Player b, Player c) {
		p.attack(a);
		a.resetCover();
		if(a.inRange(b, 4)) {
			b.takeDamage(200);
		}
		if(a.inRange(c, 4)) {
			c.takeDamage(200);
		}
		System.out.println();
	}
	
	public static void ThunderAbility(Player p) {
		ArrayList<Effect> e = new ArrayList<Effect>();
		Effect ThunderCounter = new Effect("counter", 0, 2);
		e.add(ThunderCounter);
		p.addEffects(e);
		p.applyEffects();
		p.setCooldown(4);
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
			a.takeDamage(a.getMaxHP() * 0.2);
			p.increaseDPSNum(a.getDamage() * 0.5);
			p.resetUlt();
		}
		if(targetResponse.equals("2")) {
			b.takeDamage(b.getMaxHP() * 0.2);
			p.increaseDPSNum(b.getDamage() * 0.5);
			p.resetUlt();
		}
		if(targetResponse.equals("3")) {
			c.takeDamage(c.getMaxHP() * 0.2);
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
		ArrayList<Effect> e = new ArrayList<Effect>();
		Effect AidanParalyze = new Effect("paralyze", 0, 1);
		Effect AidanDaze = new Effect("daze", 0, 1);
		System.out.println("1: Striker Assault Rifle");
		System.out.println("2: Frenzy Auto Shotgun");
		System.out.println("3: Reaper Sniper Rifle");
		System.out.print("Which gun do you want to use: ");
		String targetResponse = input.next();
		if(targetResponse.equals("1")) {
			p.attack(a);
			double rand = Math.random();
			if(rand <= 0.2) {
				e.add(AidanDaze);
				a.addEffects(e);
				a.applyEffects();
			}
		}
		if(targetResponse.equals("2")) {
			p.attack(a);
			if(p.inRange(a, 4)) {
				a.takeDamage(175);
			}
		}
		if(targetResponse.equals("3")) {
			if(p.overRange(a, 10)) {
				p.attack(a);
				a.takeDamage(175);
				double rand = Math.random();
				if(rand <= 0.2) {
					e.add(AidanParalyze);
					a.addEffects(e);
					a.applyEffects();
				}
			}else {
				p.attack(a);
				double rand = Math.random();
				if(rand <= 0.2) {
					e.add(AidanParalyze);
					a.addEffects(e);
					a.applyEffects();
				}
			}
		}
		System.out.println();
	}
	
	public static void AidanAbility(Player p, Player a, Player b) {
		p.heal(0.15);
		a.heal(0.15);
		b.heal(0.15);
		Cover c = new Cover("Full", p.getLoc());
		Cover c2 = new Cover("Full", a.getLoc());
		Cover c3 = new Cover("Full", b.getLoc());
		p.setCover("Full");
		a.setCover("Full");
		b.setCover("Full");
		cover.add(c);
		cover.add(c2);
		cover.add(c3);
		p.setCooldown(3);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void AidanUltimate(Player p, Player a, Player b, Player c, Player d, Player e) {
		ArrayList<Effect> e1 = new ArrayList<Effect>();
		ArrayList<Effect> e2 = new ArrayList<Effect>();
		ArrayList<Effect> e3 = new ArrayList<Effect>();
		ArrayList<Effect> e4 = new ArrayList<Effect>();
		ArrayList<Effect> e5 = new ArrayList<Effect>();
		ArrayList<Effect> e6 = new ArrayList<Effect>();
		Effect AidanRefine = new Effect("refine", 0, 2);
		Effect AidanRefine2 = new Effect("refine", 0, 2);
		Effect AidanRefine3 = new Effect("refine", 0, 2);
		Effect AidanVulnerable = new Effect("vulnerable", 0.1, 2);
		Effect AidanVulnerable2 = new Effect("vulnerable", 0.1, 2);
		Effect AidanVulnerable3 = new Effect("vulnerable", 0.1, 2);
		Effect AidanWeak = new Effect("weak", 0.3, 2);
		Effect AidanWeak2 = new Effect("weak", 0.3, 2);
		Effect AidanWeak3 = new Effect("weak", 0.3, 2);
		p.heal(0.10);
		d.heal(0.10);
		e.heal(0.10);
		a.takeDamage(250);
		b.takeDamage(250);
		c.takeDamage(250);
		e1.add(AidanRefine);
		e5.add(AidanRefine2);
		e6.add(AidanRefine3);
		e2.add(AidanVulnerable);
		e2.add(AidanWeak);
		e3.add(AidanVulnerable2);
		e3.add(AidanWeak2);
		e4.add(AidanVulnerable3);
		e4.add(AidanWeak3);
		p.addEffects(e1);
		p.applyEffects();
		a.addEffects(e2);
		a.applyEffects();
		b.addEffects(e3);
		b.applyEffects();
		c.addEffects(e4);
		c.applyEffects();
		d.addEffects(e5);
		d.applyEffects();
		e.addEffects(e6);
		e.applyEffects();
		p.resetUlt();
		System.out.println("\"Just because we're in the storm, doesn't mean the game's over.\"");
		System.out.println();
	}
	
	public static void LiamAttack(Player p, Player a, Player b, Player c) {
		p.attack(a);
		double rand = Math.random();
		if(rand <= 0.1) {
			ArrayList<Effect> e = new ArrayList<Effect>();
			Effect LiamIgnite = new Effect("ignite", 0, 1);
			e.add(LiamIgnite);
			a.addEffects(e);
			a.applyEffects();
			a.knockbacked();
		}
		if(a.inRange(b, 3)) {
			b.takeDamage(200);
			double rand2 = Math.random();
			if(rand2 <= 0.1) {
				ArrayList<Effect> e = new ArrayList<Effect>();
				Effect LiamIgnite = new Effect("ignite", 0, 1);
				e.add(LiamIgnite);
				a.addEffects(e);
				a.applyEffects();
				a.knockbacked();
			}
		}
		if(a.inRange(c, 3)) {
			c.takeDamage(200);
			double rand3 = Math.random();
			if(rand3 <= 0.1) {
				ArrayList<Effect> e = new ArrayList<Effect>();
				Effect LiamIgnite = new Effect("ignite", 0, 1);
				e.add(LiamIgnite);
				a.addEffects(e);
				a.applyEffects();
				a.knockbacked();
			}
		}
		System.out.println();
	}
	
	public static void LiamAbility(Player p, Player a, Player b) {
		ArrayList<Effect> e = new ArrayList<Effect>();
		ArrayList<Effect> e2 = new ArrayList<Effect>();
		ArrayList<Effect> e3 = new ArrayList<Effect>();
		Effect LiamProtect = new Effect("protect", 0.4, 2);
		Effect LiamProtect2 = new Effect("protect", 0.4, 2);
		Effect LiamProtect3 = new Effect("protect", 0.4, 2);
		e.add(LiamProtect);
		e2.add(LiamProtect2);
		e3.add(LiamProtect3);
		p.cleanse();
		a.cleanse();
		b.cleanse();
		p.addEffects(e);
		p.applyEffects();
		a.addEffects(e2);
		a.applyEffects();
		b.addEffects(e3);
		b.applyEffects();
		p.setCooldown(3);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void LiamUltimate(Player p, Player a, Player b, Player c, Player d, Player e) {
		Scanner input = new Scanner(System.in);
		double randomX = (int)(Math.random() * (1500 - 0 + 1)) + 0;
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
					a.takeDamage(250);
					System.out.println("\"Guilty as charged!\"");
				}else {
					System.out.println("\"The evidence says otherwise!\"");
					a.takeDamage(650);
				}
			}
			if(response.equals("n")) {
				if(randomX > a.getTotalDPS()) {
					p.heal(0.15);
					d.heal(0.15);
					e.heal(0.15);
					a.takeDamage(250);
					System.out.println("\"Innocence proven!\"");
				}else {
					System.out.println("\"The evidence says otherwise!\"");
					a.takeDamage(650);
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
					b.takeDamage(250);
					System.out.println("\"Guilty as charged!\"");
				}else {
					System.out.println("\"The evidence says otherwise!\"");
					b.takeDamage(650);
				}
			}
			if(response.equals("n")) {
				if(randomX > b.getTotalDPS()) {
					p.heal(0.15);
					d.heal(0.15);
					e.heal(0.15);
					b.takeDamage(250);
					System.out.println("\"Innocence proven!\"");
				}else {
					System.out.println("\"The evidence says otherwise!\"");
					b.takeDamage(650);
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
					c.takeDamage(250);
					System.out.println("\"Guilty as charged!\"");
				}else {
					System.out.println("\"The evidence says otherwise!\"");
					c.takeDamage(650);
				}
			}
			if(response.equals("n")) {
				if(randomX > c.getTotalDPS()) {
					p.heal(0.15);
					d.heal(0.15);
					e.heal(0.15);
					c.takeDamage(250);
					System.out.println("\"Innocence proven!\"");
				}else {
					System.out.println("\"The evidence says otherwise!\"");
					c.takeDamage(650);
				}
			}
			p.resetUlt();
			System.out.println();
		}
	}
	
	public static void AxolAttack(Player p, Player a) {
		p.attack(a);
		ArrayList<Effect> e = new ArrayList<Effect>();
		Effect AshleyPoison = new Effect("poison", 0.1, 1);
		e.add(AshleyPoison);
		a.addEffects(e);
		a.applyEffects();
		System.out.println();
	}
	
	public static void AxolAbility(Player p, Player a, Player b) {
		ArrayList<Effect> e = new ArrayList<Effect>();
		ArrayList<Effect> e2 = new ArrayList<Effect>();
		ArrayList<Effect> e3 = new ArrayList<Effect>();
		Effect AxolMend = new Effect("mend", 0.7, 2);
		Effect AxolMend2 = new Effect("mend", 0.7, 2);
		Effect AxolMend3 = new Effect("mend", 0.7, 2);
		e.add(AxolMend);
		e2.add(AxolMend2);
		e3.add(AxolMend3);
		p.addEffects(e);
		p.applyEffects();
		a.addEffects(e2);
		a.applyEffects();
		b.addEffects(e3);
		b.applyEffects();
		p.setCooldown(3);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void AxolUltimate(Player p, Player a, Player b) {
		p.increaseMaxHP(p.getMaxHP() * 0.25);
		a.increaseMaxHP(a.getMaxHP() * 0.25);
		b.increaseMaxHP(b.getMaxHP() * 0.25);
		p.resetUlt();
		System.out.println("\"Oh yeah, we vibing with this upgrade.\"");
		System.out.println();
	}
	
	public static void KatrinaAttack(Player p, Player a) {
		p.attack(a);
		ArrayList<Effect> e = new ArrayList<Effect>();
		Effect KatrinaWeaken = new Effect("weak", 0.15, 1);
		e.add(KatrinaWeaken);
		a.addEffects(e);
		a.applyEffects();
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
			p.getLoc().set(a.getLoc().getX(), a.getLoc().getY());
			a.heal(0.25);
			a.cleanse();
			p.setCooldown(3);
		}
		if(targetResponse.equals("2")) {
			p.getLoc().set(b.getLoc().getX(), b.getLoc().getY());
			b.heal(0.25);
			b.cleanse();
			p.setCooldown(3);
		}
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void KatrinaUltimate(Player p, Player a, Player b) {
		ArrayList<Effect> e = new ArrayList<Effect>();
		ArrayList<Effect> e2 = new ArrayList<Effect>();
		ArrayList<Effect> e3 = new ArrayList<Effect>();
		Effect KatrinaPower = new Effect("power", 0.35, 1);
		Effect KatrinaPower2 = new Effect("power", 0.35, 1);
		Effect KatrinaPower3 = new Effect("power", 0.35, 1);
		Effect KatrinaRefine = new Effect("refine", 0, 2);
		Effect KatrinaRefine2 = new Effect("refine", 0, 2);
		Effect KatrinaRefine3 = new Effect("refine", 0, 2);
		e.add(KatrinaPower);
		e.add(KatrinaRefine);
		e2.add(KatrinaPower2);
		e2.add(KatrinaRefine2);
		e3.add(KatrinaPower3);
		e3.add(KatrinaRefine3);
		p.addEffects(e);
		p.applyEffects();
		a.addEffects(e2);
		a.applyEffects();
		b.addEffects(e3);
		b.applyEffects();
		p.heal(0.5);
		a.heal(0.5);
		b.heal(0.5);
		p.resetUlt();
		System.out.println("\"100,000 bells on this? Let's make it count.\"");
		System.out.println();
	}
	
	public static void MidniteAttack(Player p, Player a) {
		p.attack(a);
		a.knockbacked();
		double rand = Math.random();
		if(rand <= 0.1) {
			ArrayList<Effect> e = new ArrayList<Effect>();
			Effect MackDaze = new Effect("daze", 0, 1);
			e.add(MackDaze);
			a.addEffects(e);
			a.applyEffects();
		}
		System.out.println();
	}
	
	public static void MidniteAbility(Player p, Player a, Player b, Player c, Player d, Player e) {
		ArrayList<Effect> e1 = new ArrayList<Effect>();
		ArrayList<Effect> e2 = new ArrayList<Effect>();
		ArrayList<Effect> e3 = new ArrayList<Effect>();
		Effect MidnitePower = new Effect("power", 0.15, 1);
		Effect MidnitePower2 = new Effect("power", 0.15, 1);
		Effect MidnitePower3 = new Effect("power", 0.15, 1);
		e1.add(MidnitePower);
		e2.add(MidnitePower2);
		e3.add(MidnitePower3);
		p.addEffects(e1);
		d.addEffects(e2);
		e.addEffects(e3);
		p.applyEffects();
		d.applyEffects();
		e.applyEffects();
		a.knockbacked();
		a.knockbacked();
		b.knockbacked();
		b.knockbacked();
		c.knockbacked();
		c.knockbacked();
		p.setCooldown(3);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void MidniteUltimate(Player p) {
		ArrayList<Effect> e = new ArrayList<Effect>();
		p.cleanse();
		Effect MidniteProtect = new Effect("protect", 1, 3);
		Effect MidniteRefine = new Effect("refine", 0, 3);
		Effect MidniteFortify = new Effect("fortify", 0, 3);
		Effect MidnitePower = new Effect("power", 1, 2);
		e.add(MidniteProtect);
		e.add(MidniteRefine);
		e.add(MidniteFortify);
		e.add(MidnitePower);
		p.addEffects(e);
		p.applyEffects();
		p.resetUlt();
		System.out.println("\"This will be your last haunt!\"");
		System.out.println();
	}
	
	public static void XaraAttack(Player p, Player a) {
		p.attack(a);
		ArrayList<Effect> e = new ArrayList<Effect>();
		Effect XaraVulnerable = new Effect("vulnerable", 0.10, 1);
		e.add(XaraVulnerable);
		a.addEffects(e);
		a.applyEffects();
		System.out.println();
	}
	
	public static void XaraAbility(Player p, Player a, Player b) {
		p.setSights(1);
		ArrayList<Effect> e = new ArrayList<Effect>();
		ArrayList<Effect> e2 = new ArrayList<Effect>();
		ArrayList<Effect> e3 = new ArrayList<Effect>();
		Effect LiamProtect = new Effect("protect", 0.2, 3);
		Effect LiamProtect2 = new Effect("protect", 0.2, 3);
		Effect LiamProtect3 = new Effect("protect", 0.2, 3);
		e.add(LiamProtect);
		e2.add(LiamProtect2);
		e3.add(LiamProtect3);
		p.addEffects(e);
		p.applyEffects();
		a.addEffects(e2);
		a.applyEffects();
		b.addEffects(e3);
		b.applyEffects();
		p.increaseMaxHP(p.getMaxHP() * 0.05);
		p.setCooldown(4);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void XaraUltimate(Player p, Player a, Player b, Player c) {
		a.takeDamage(300);
		b.takeDamage(300);
		c.takeDamage(300);
		ArrayList<Effect> e = new ArrayList<Effect>();
		ArrayList<Effect> e2 = new ArrayList<Effect>();
		ArrayList<Effect> e3 = new ArrayList<Effect>();
		Effect BedrockStun = new Effect("stun", 0, 1);
		Effect BedrockStun2 = new Effect("stun", 0, 1);
		Effect BedrockStun3 = new Effect("stun", 0, 1);
		e.add(BedrockStun);
		e2.add(BedrockStun2);
		e3.add(BedrockStun3);
		a.addEffects(e);
		a.applyEffects();
		b.addEffects(e2);
		b.applyEffects();
		c.addEffects(e3);
		c.applyEffects();
		p.resetUlt();
		System.out.println("\"Enemy team is down for maintenance.\"");
		System.out.println();
	}
	
	public static void KitharaAttack(Player p, Player a) {
		p.attack(a);
		double rand = Math.random();
		if(rand <= 0.03) {
			a.takeDamage(a.getMaxHP() * 0.10);
		}
		System.out.println();
	}
	
	public static void KitharaAbility(Player p, Player a, Player b) {
		ArrayList<Effect> e = new ArrayList<Effect>();
		ArrayList<Effect> e2 = new ArrayList<Effect>();
		ArrayList<Effect> e3 = new ArrayList<Effect>();
		Effect KitharaProtect = new Effect("protect", 0.8, 2);
		Effect KitharaProtect2 = new Effect("protect", 1, 2);
		Effect KitharaProtect3 = new Effect("protect", 1, 2);
		e.add(KitharaProtect);
		e2.add(KitharaProtect2);
		e3.add(KitharaProtect3);
		p.addEffects(e);
		p.applyEffects();
		a.addEffects(e2);
		a.applyEffects();
		b.addEffects(e3);
		b.applyEffects();
		p.setCooldown(5);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void KitharaUltimate(Player p, Player a, Player b, Player c) {
		ArrayList<Effect> e = new ArrayList<Effect>();
		ArrayList<Effect> e2 = new ArrayList<Effect>();
		ArrayList<Effect> e3 = new ArrayList<Effect>();
		Effect KitharaWeak = new Effect("weak", 0.35, 2);
		Effect KitharaWeak2 = new Effect("weak", 0.35, 2);
		Effect KitharaWeak3 = new Effect("weak", 0.35, 2);
		Effect KitharaIgnite = new Effect("ignite", 0, 2);
		Effect KitharaIgnite2 = new Effect("ignite", 0, 2);
		Effect KitharaIgnite3 = new Effect("ignite", 0, 2);
		e.add(KitharaWeak);
		e.add(KitharaIgnite);
		e2.add(KitharaWeak2);
		e2.add(KitharaIgnite2);
		e3.add(KitharaWeak3);
		e3.add(KitharaIgnite3);
		a.addEffects(e);
		a.applyEffects();
		b.addEffects(e2);
		b.applyEffects();
		c.addEffects(e3);
		c.applyEffects();
		a.knockbacked();
		b.knockbacked();
		c.knockbacked();
		p.resetUlt();
		System.out.println("\"Thank you all for coming. SORRY FOR THE WAIT!\"");
		System.out.println();
	}
	
	public static void AnjelikaAttack(Player p, Player a) {
		p.attack(a);
		p.getLoc().set(a.getLoc().getX(), a.getLoc().getY());
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
		a.knockbacked();
		double rand = Math.random();
		if(rand <= 0.15) {
			ArrayList<Effect> e = new ArrayList<Effect>();
			Effect AnjelikaBlind = new Effect("blind", 0.3, 1);
			e.add(AnjelikaBlind);
			a.addEffects(e);
			a.applyEffects();
		}
		System.out.println();
	}
	
	public static void AnjelikaAbility(Player p, Player a, Player b, Player c) {
		ArrayList<Effect> e = new ArrayList<Effect>();
		ArrayList<Effect> e2 = new ArrayList<Effect>();
		ArrayList<Effect> e3 = new ArrayList<Effect>();
		Effect CherryWeaken = new Effect("ignite", 0, 1);
		Effect CherryWeaken2 = new Effect("ignite", 0, 1);
		Effect CherryWeaken3 = new Effect("ignite", 0, 1);
		e.add(CherryWeaken);
		e2.add(CherryWeaken2);
		e3.add(CherryWeaken3);
		if(!a.inRange(p, 10) && !b.inRange(p, 10) && !c.inRange(p, 10)) {
			System.out.println("No targets in range!");
			System.out.println();
			return;
		}
		if(a.inRange(p, 10)) {
			a.addEffects(e);
			a.applyEffects();
			a.takeDamage(a.getMaxHP() * 0.1);
		}
		if(b.inRange(p, 10)) {
			b.addEffects(e2);
			b.applyEffects();
			b.takeDamage(b.getMaxHP() * 0.1);
		}
		if(c.inRange(p, 10)) {
			c.addEffects(e3);
			c.applyEffects();
			c.takeDamage(c.getMaxHP() * 0.1);
		}
		p.setCooldown(3);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void AnjelikaUltimate(Player p) {
		p.cleanse();
		ArrayList<Effect> e = new ArrayList<Effect>();
		Effect AnjelikaProtect = new Effect("protect", 0.3, 3);
		Effect AnjelikaRefine = new Effect("refine", 0, 3);
		Effect AnjelikaPower = new Effect("power", 0.15, 2);
		Effect AnjelikaHeal = new Effect("heal", 0.1, 2);
		e.add(AnjelikaProtect);
		e.add(AnjelikaHeal);
		e.add(AnjelikaPower);
		e.add(AnjelikaRefine);
		p.addEffects(e);
		p.applyEffects();
		p.resetUlt();
		System.out.println("\"See you in heaven!\"");
		System.out.println();
	}
	
	public static void ArcherAttack(Player p, Player a) {
		ArrayList<Effect> e = new ArrayList<Effect>();
		Effect AidanParalyze = new Effect("paralyze", 0, 1);
		if(p.overRange(a, 14)) {
			p.attack(a);
			a.takeDamage(225);
			double rand = Math.random();
			if(rand <= 0.2) {
				e.add(AidanParalyze);
				a.addEffects(e);
				a.applyEffects();
			}
		}else {
			p.attack(a);
			double rand = Math.random();
			if(rand <= 0.1) {
				e.add(AidanParalyze);
				a.addEffects(e);
				a.applyEffects();
			}
		}
		System.out.println();
	}
	
	public static void ArcherAbility(Player p, Player a, Player b) {
		ArrayList<Effect> e1 = new ArrayList<Effect>();
		ArrayList<Effect> e2 = new ArrayList<Effect>();
		ArrayList<Effect> e3 = new ArrayList<Effect>();
		Effect MidnitePower = new Effect("power", 0.05, 1);
		Effect MidnitePower2 = new Effect("power", 0.05, 1);
		Effect MidnitePower3 = new Effect("power", 0.05, 1);
		Effect ArcherSight = new Effect("sight", 0.25, 1);
		Effect ArcherSight2 = new Effect("sight", 0.25, 1);
		Effect ArcherSight3 = new Effect("sight", 0.25, 1);
		e1.add(MidnitePower);
		e2.add(MidnitePower2);
		e3.add(MidnitePower3);
		e1.add(ArcherSight);
		e2.add(ArcherSight2);
		e3.add(ArcherSight3);
		p.addEffects(e1);
		a.addEffects(e2);
		b.addEffects(e3);
		p.applyEffects();
		a.applyEffects();
		b.applyEffects();
		p.setCooldown(3);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void ArcherUltimate(Player p, Player a, Player b, Player c) {
		ArrayList<Effect> e = new ArrayList<Effect>();
		ArrayList<Effect> e2 = new ArrayList<Effect>();
		ArrayList<Effect> e3 = new ArrayList<Effect>();
		Effect ArcherWeak = new Effect("weak", 0.15, 1);
		Effect ArcherWeak2 = new Effect("weak", 0.15, 1);
		Effect ArcherWeak3 = new Effect("weak", 0.15, 1);
		Effect ArcherVulnerable = new Effect("vulnerable", 0.1, 1);
		Effect ArcherVulnerable2 = new Effect("vulnerable", 0.1, 1);
		Effect ArcherVulnerable3 = new Effect("vulnerable", 0.1, 1);
		Effect ArcherIgnite = new Effect("ignite", 0, 1);
		Effect ArcherIgnite2 = new Effect("ignite", 0, 1);
		Effect ArcherIgnite3 = new Effect("ignite", 0, 1);
		Effect ArcherPoison = new Effect("poison", 0.15, 1);
		Effect ArcherPoison2 = new Effect("poison", 0.15, 1);
		Effect ArcherPoison3 = new Effect("poison", 0.15, 1);
		e.add(ArcherWeak);
		e.add(ArcherVulnerable);
		e.add(ArcherIgnite);
		e.add(ArcherPoison);
		e2.add(ArcherWeak2);
		e2.add(ArcherVulnerable2);
		e2.add(ArcherIgnite2);
		e2.add(ArcherPoison2);
		e3.add(ArcherWeak3);
		e3.add(ArcherVulnerable3);
		e3.add(ArcherIgnite3);
		e3.add(ArcherPoison3);
		a.addEffects(e);
		b.addEffects(e2);
		c.addEffects(e3);
		a.applyEffects();
		b.applyEffects();
		c.applyEffects();
		a.takeDamage(225);
		b.takeDamage(225);
		c.takeDamage(225);
		p.resetUlt();
		System.out.println("\"I'll always be one step arrowhead.\"");
		System.out.println();
	}
	
	public static void TomAttack(Player p, Player a) {
		p.attack(a);
		double rand = Math.random();
		if(rand <= 0.10) {
			ArrayList<Effect> e = new ArrayList<Effect>();
			Effect BurtParalyze = new Effect("paralyze", 0, 1);
			Effect TomVulnerable = new Effect("vulnerable", 0.1, 1);
			e.add(BurtParalyze);
			e.add(TomVulnerable);
			a.addEffects(e);
			a.applyEffects();
		}
		System.out.println();
	}
	
	public static void TomAbility(Player p) {
		ArrayList<Effect> e = new ArrayList<Effect>();
		Effect TomMend = new Effect("mend", 1, 2);
		Effect TomProtect = new Effect("protect", 0.55, 3);
		e.add(TomMend);
		e.add(TomProtect);
		p.addEffects(e);
		p.applyEffects();
		p.setCooldown(4);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void TomUltimate(Player p, Player a, Player b, Player c, Location l) {
		if(a.inRange(l, 7)) {
			a.takeDamage(a.getMaxHP() * 0.3);
		}
		if(b.inRange(l, 7)) {
			b.takeDamage(b.getMaxHP() * 0.3);
		}
		if(c.inRange(l, 7)) {
			c.takeDamage(c.getMaxHP() * 0.3);
		}
		p.resetUlt();
		System.out.println("\"I will take EVERYTHING from you!\"");
		System.out.println();
	}
	
	public static void DimentioAttack(Player p, Player a) {
		if(a.getDamage() > p.getDamage() && p.inRange(a, 4)) {
			p.attack(a);
			double d = a.getDamage() - p.getDamage();
			a.takeDamage(d);
		}else {
			p.attack(a);
		}
		double rand = Math.random();
		if(rand <= 0.10) {
			ArrayList<Effect> e = new ArrayList<Effect>();
			Effect BurtParalyze = new Effect("freeze", 0, 1);
			e.add(BurtParalyze);
			a.addEffects(e);
			a.applyEffects();
		}
		System.out.println();
	}
	
	public static void DimentioAbility(Player p, Player a, Player b, Player c) {
		ArrayList<Effect> e = new ArrayList<Effect>();
		Effect DimentioStun = new Effect("stun", 0, 2);
		e.add(DimentioStun);
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
			a.addEffects(e);
			a.applyEffects();
			p.setCooldown(5);
			System.out.println(p.voiceline());
			System.out.println();
		}
		if(targetResponse.equals("2")) {
			if(!b.isAlive()) {
				System.out.println("Target is downed.");
				System.out.println();
				return;
			}
			b.addEffects(e);
			b.applyEffects();
			p.setCooldown(5);
			System.out.println(p.voiceline());
			System.out.println();
		}
		if(targetResponse.equals("3")) {
			if(!c.isAlive()) {
				System.out.println("Target is downed.");
				System.out.println();
				return;
			}
			c.addEffects(e);
			c.applyEffects();
			p.setCooldown(5);
			System.out.println(p.voiceline());
			System.out.println();
		}
	}
	
	public static void DimentioUltimate(Player p) {
		p.setUlt();
		ArrayList<Effect> e = new ArrayList<Effect>();
		Effect DimentioProtect = new Effect("protect", 0.1, 100);
		Effect DimentioHeal = new Effect("heal", 0.1, 100);
		e.add(DimentioProtect);
		e.add(DimentioHeal);
		p.addEffects(e);
		p.applyEffects();
		System.out.println("\"I'm now the most powerful virus in the digital world!\"");
		System.out.println();
	}
	
	public static void GrizzAttack(Player p, Player a, Player b, Player c) {
		p.attack(a);
		if(a.inRange(b, 3)) {
			b.takeDamage(p.getDamage() / 2);
		}
		if(a.inRange(c, 3)) {
			c.takeDamage(p.getDamage() / 2);
		}
		System.out.println();
	}
	
	public static void GrizzAbility(Player p, Player a, Player b) {
		p.increaseMovement(5);
		a.increaseMovement(5);
		b.increaseMovement(5);
		ArrayList<Effect> e1 = new ArrayList<Effect>();
		ArrayList<Effect> e2 = new ArrayList<Effect>();
		ArrayList<Effect> e3 = new ArrayList<Effect>();
		Effect MidnitePower = new Effect("power", 0.2, 2);
		Effect MidnitePower2 = new Effect("power", 0.2, 2);
		Effect MidnitePower3 = new Effect("power", 0.2, 2);
		e1.add(MidnitePower);
		e2.add(MidnitePower2);
		e3.add(MidnitePower3);
		p.addEffects(e1);
		a.addEffects(e2);
		b.addEffects(e3);
		p.applyEffects();
		a.applyEffects();
		b.applyEffects();
		if(p.getHealth() < (p.getMaxHP() * 0.5)) {
			p.heal(0.2);
		}
		if(a.getHealth() < (a.getMaxHP() * 0.5)) {
			a.heal(0.2);
		}
		if(b.getHealth() < (b.getMaxHP() * 0.5)) {
			b.heal(0.2);
		}
		p.setCooldown(4);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void GrizzUltimate(Player p) {
		p.setUlt();
		p.increaseMaxHP(p.getMaxHP() * 0.35);
		ArrayList<Effect> e = new ArrayList<Effect>();
		Effect DimentioProtect = new Effect("power", 0.3, 100);
		e.add(DimentioProtect);
		p.addEffects(e);
		p.applyEffects();
		System.out.println("\"This is a growth industry!\"");
		System.out.println();
	}
	
	public static void EvilAttack(Player p, Player a, Player b, Player c) {
		p.attack(a);
		double rand = Math.random();
		if(rand <= 0.1) {
			ArrayList<Effect> e = new ArrayList<Effect>();
			Effect BurtParalyze = new Effect("paralyze", 0, 1);
			Effect EvilIgnite = new Effect("ignite", 0, 1);
			e.add(BurtParalyze);
			e.add(EvilIgnite);
			a.addEffects(e);
			a.applyEffects();
		}
		if(a.inRange(b, 3)) {
			b.knockbacked();
		}
		if(a.inRange(c, 3)) {
			c.knockbacked();
		}
		System.out.println();
	}
	
	public static void EvilAbility(Player p) {
		ArrayList<Effect> e = new ArrayList<Effect>();
		Effect DimentioProtect = new Effect("protect", 0.6, 2);
		e.add(DimentioProtect);
		p.addEffects(e);
		p.applyEffects();
		p.setSights(3);
		p.setCooldown(4);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void EvilUltimate(Player p, Player a, Player b, Player c) {
		a.takeDamage(a.getMaxHP() * 0.15);
		b.takeDamage(a.getMaxHP() * 0.15);
		c.takeDamage(a.getMaxHP() * 0.15);
		ArrayList<Effect> e = new ArrayList<Effect>();
		Effect SolarIgnite = new Effect("ignite", 0, 2);
		e.add(SolarIgnite);
		ArrayList<Effect> e2 = new ArrayList<Effect>();
		Effect SolarIgnite2 = new Effect("ignite", 0, 2);
		e2.add(SolarIgnite2);
		ArrayList<Effect> e3 = new ArrayList<Effect>();
		Effect SolarIgnite3 = new Effect("ignite", 0, 2);
		e3.add(SolarIgnite3);
		a.addEffects(e);
		b.addEffects(e2);
		c.addEffects(e3);
		a.applyEffects();
		b.applyEffects();
		c.applyEffects();
		p.resetUlt();
		System.out.println("\"The Roche Limit approaches, and so will your fates!\"");
		System.out.println();
	}
	
	public static void MasonAttack(Player p, Player a) {
		p.attack(a);
		if(p.inRange(a, 4)) {
			p.heal(0.15);
		}
		System.out.println();
	}
	
	public static void MasonAbility(Player p, Player a, Player b, Player c, Location l) {
		ArrayList<Effect> e = new ArrayList<Effect>();
		ArrayList<Effect> e2 = new ArrayList<Effect>();
		ArrayList<Effect> e3 = new ArrayList<Effect>();
		Effect CherryWeaken = new Effect("daze", 0, 2);
		Effect CherryWeaken2 = new Effect("daze", 0, 2);
		Effect CherryWeaken3 = new Effect("daze", 0, 2);
		e.add(CherryWeaken);
		e2.add(CherryWeaken2);
		e3.add(CherryWeaken3);

		if(a.inRange(l, 8)) {
			a.takeDamage(350);
			a.addEffects(e);
			a.applyEffects();
		}
		if(b.inRange(l, 8)) {
			b.takeDamage(350);
			b.addEffects(e2);
			b.applyEffects();
		}
		if(c.inRange(l, 8)) {
			c.takeDamage(350);
			c.addEffects(e3);
			c.applyEffects();
		}
		p.setCooldown(3);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void MasonUltimate(Player p, Player a, Player b, Player c, Player d, Player e) {
		if(a.inRange(p, 15)) {
			a.takeDamage(a.getMaxHP() * 0.15);
			a.knockbacked();
		}
		if(b.inRange(p, 15)) {
			b.takeDamage(b.getMaxHP() * 0.15);
			b.knockbacked();
		}
		if(c.inRange(p, 15)) {
			c.takeDamage(c.getMaxHP() * 0.15);
			c.knockbacked();
		}
		if(d.inRange(p, 15)) {
			ArrayList<Effect> e1 = new ArrayList<Effect>();
			Effect SolarProtect = new Effect("protect", 0.1, 3);
			e1.add(SolarProtect);
			d.addEffects(e1);
			d.applyEffects();
		}
		if(e.inRange(p, 15)) {
			ArrayList<Effect> e1 = new ArrayList<Effect>();
			Effect SolarProtect = new Effect("protect", 0.1, 3);
			e1.add(SolarProtect);
			e.addEffects(e1);
			e.applyEffects();
		}
		p.resetUlt();
		System.out.println("\"Tech Terror in the house!\"");
		System.out.println();
	}
	
	public static void AiricAttack(Player p, Player a) {
		p.attack(a);
		double rand = Math.random();
		if(rand <= 0.08) {
			ArrayList<Effect> e = new ArrayList<Effect>();
			Effect BurtParalyze = new Effect("paralyze", 0, 1);
			Effect AiricDaze = new Effect("daze", 0, 1);
			e.add(BurtParalyze);
			e.add(AiricDaze);
			a.addEffects(e);
			a.applyEffects();
		}
		System.out.println();
	}
	
	public static void AiricAbility(Player p, Player a, Player b, Location l) {
		Scanner input = new Scanner(System.in);
		ArrayList<Effect> e = new ArrayList<Effect>();
		Effect MaxPower = new Effect("power", 0.25, 1);
		e.add(MaxPower);
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
					System.out.println(a.getName() + " has gotten an orb.");
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
			a.addEffects(e);
			a.applyEffects();
			p.setCooldown(4);
		}
		if(targetResponse.equals("2")) {
			b.getLoc().set(l.getX(), l.getY());
			for(int i = 0; i < orbs.size(); i++) {
				if(b.getLoc().eqLoc(orbs.get(i).getLoc()) && !b.ultReady()) {
					b.getOrb();
					orbs.remove(i);
					System.out.println(b.getName() + " has gotten an orb.");
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
			b.addEffects(e);
			b.applyEffects();
			p.setCooldown(4);
		}
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void AiricUltimate(Player p, Player a, Player b, Player c) {
		ArrayList<Effect> e = new ArrayList<Effect>();
		ArrayList<Effect> e2 = new ArrayList<Effect>();
		Effect AiricProtect = new Effect("protect", 0.5, 2);
		Effect AiricVulnerable = new Effect("vulnerable", 0.1, 1);
		e.add(AiricProtect);
		e2.add(AiricVulnerable);
		Scanner input = new Scanner(System.in);
		System.out.println("1: " + a.getName() +a.showHP() +  a.getHealth() + "/" + a.getMaxHP());
		System.out.println("2: " + b.getName() +b.showHP() +  b.getHealth() + "/" + b.getMaxHP());
		System.out.println("3: " + c.getName() +c.showHP() +  c.getHealth() + "/" + c.getMaxHP());
		System.out.print("Who do you want to turn the tables on: ");
		String targetResponse = input.next();
		if(targetResponse.equals("1")) {
			p.heal(0.2);
			Location l1 = new Location(p.getLoc().getX(), p.getLoc().getY());
			Location l2 = new Location(a.getLoc().getX(), a.getLoc().getY());
			p.getLoc().set(l2.getX(), l2.getY());
			a.getLoc().set(l1.getX(), l1.getY());
			p.addEffects(e);
			p.applyEffects();
			a.addEffects(e2);
			a.applyEffects();
			p.resetUlt();
		}
		if(targetResponse.equals("2")) {
			p.heal(0.2);
			Location l1 = new Location(p.getLoc().getX(), p.getLoc().getY());
			Location l2 = new Location(b.getLoc().getX(), b.getLoc().getY());
			p.getLoc().set(l2.getX(), l2.getY());
			b.getLoc().set(l1.getX(), l1.getY());
			p.addEffects(e);
			p.applyEffects();
			b.addEffects(e2);
			b.applyEffects();
			p.resetUlt();
		}
		if(targetResponse.equals("3")) {
			p.heal(0.2);
			Location l1 = new Location(p.getLoc().getX(), p.getLoc().getY());
			Location l2 = new Location(c.getLoc().getX(), c.getLoc().getY());
			p.getLoc().set(l2.getX(), l2.getY());
			c.getLoc().set(l1.getX(), l1.getY());
			p.addEffects(e);
			p.applyEffects();
			c.addEffects(e2);
			c.applyEffects();
			p.resetUlt();
		}
		System.out.println();
		System.out.println("\"Get over here!\"");
		System.out.println();
	}
	
	public static void JulianAttack(Player p, Player a, Player b, Player c) {
		p.attack(a);
		double rand = Math.random();
		if(rand <= 0.1) {
			ArrayList<Effect> e = new ArrayList<Effect>();
			Effect EvilIgnite = new Effect("ignite", 0, 1);
			e.add(EvilIgnite);
			a.addEffects(e);
			a.applyEffects();
		}
		if(a.inRange(b, 3)) {
			ArrayList<Effect> e = new ArrayList<Effect>();
			Effect EvilIgnite = new Effect("ignite", 0, 1);
			e.add(EvilIgnite);
			b.addEffects(e);
			b.applyEffects();
		}
		if(a.inRange(c, 3)) {
			ArrayList<Effect> e = new ArrayList<Effect>();
			Effect EvilIgnite = new Effect("ignite", 0, 1);
			e.add(EvilIgnite);
			c.addEffects(e);
			c.applyEffects();
		}
		System.out.println();
	}
	
	public static void JulianAbility(Player p) {
		p.increaseMovement(7);
		ArrayList<Effect> e = new ArrayList<Effect>();
		Effect SolarProtect = new Effect("protect", 0.8, 2);
		e.add(SolarProtect);
		p.addEffects(e);
		p.applyEffects();
		p.setCooldown(3);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void JulianUltimate(Player p, Player a, Player b, Player c, Player d, Player e) {
		try {
			String audio = "karma.wav";
			Music victoryPlayer = new Music(audio, false); 
			victoryPlayer.play();
		}catch (Exception e1) {
			System.out.println(e1);
		}
		p.heal(0.25);
		d.heal(0.25);
		e.heal(0.25);
		a.takeDamage(450);
		a.knockbacked();
		b.takeDamage(450);
		b.knockbacked();
		c.takeDamage(450);
		c.knockbacked();
		p.resetUlt();
		System.out.println("\033[3m🎵 I hope you get everything you deserve, Karma's a bitch, I heard! 🎵\033[0m");
		System.out.println();
	}
	
	public static void GashAttack(Player p, Player a) {
		p.attack(a);
		ArrayList<Effect> e = new ArrayList<Effect>();
		ArrayList<Effect> e2 = new ArrayList<Effect>();
		Effect GashVulnerable = new Effect("vulnerable", 0.05, 1);
		Effect GashPower = new Effect("power", 0.05, 2);
		e.add(GashPower);
		e2.add(GashVulnerable);
		p.addEffects(e);
		p.applyEffects();
		a.addEffects(e2);
		a.applyEffects();
		System.out.println();
	}
	
	public static void GashAbility(Player p, Player a, Player b) {
		if(p.inRange(a, 3)) {
			ArrayList<Effect> e = new ArrayList<Effect>();
			Effect GashProtect = new Effect("protect", 0.7, 3);
			e.add(GashProtect);
			a.addEffects(e);
			a.applyEffects();	
		}else {
			ArrayList<Effect> e = new ArrayList<Effect>();
			Effect GashProtect = new Effect("protect", 0.35, 3);
			e.add(GashProtect);
			a.addEffects(e);
			a.applyEffects();	
		}
		if(p.inRange(b, 3)) {
			ArrayList<Effect> e = new ArrayList<Effect>();
			Effect GashProtect = new Effect("protect", 0.7, 3);
			e.add(GashProtect);
			b.addEffects(e);
			b.applyEffects();	
		}else {
			ArrayList<Effect> e = new ArrayList<Effect>();
			Effect GashProtect = new Effect("protect", 0.35, 3);
			e.add(GashProtect);
			b.addEffects(e);
			b.applyEffects();	
		}
		ArrayList<Effect> e = new ArrayList<Effect>();
		Effect GashProtect = new Effect("protect", 0.35, 3);
		e.add(GashProtect);
		p.addEffects(e);
		p.applyEffects();
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
				a.takeDamage(1500);
			}else {
				a.takeDamage(750);
			}
			p.resetUlt();
		}
		if(targetResponse.equals("2")) {
			if(b.getHealth() < (b.getMaxHP() * 0.5)) {
				b.takeDamage(1500);
			}else {
				b.takeDamage(750);
			}
			p.resetUlt();
		}
		if(targetResponse.equals("3")) {
			if(c.getHealth() < (c.getMaxHP() * 0.5)) {
				c.takeDamage(1500);
			}else {
				c.takeDamage(750);
			}
			p.resetUlt();
		}
		System.out.println("\"I will put a Gash in you!\"");
		System.out.println();
	}
	
	public static void MayhemAttack(Player p, Player a) {
		p.attack(a);
		a.knockbacked();
		double rand = Math.random();
		if(rand <= 0.1) {
			ArrayList<Effect> e = new ArrayList<Effect>();
			Effect MackDaze = new Effect("daze", 0, 1);
			e.add(MackDaze);
			a.addEffects(e);
			a.applyEffects();
		}
		System.out.println();
	}
	
	public static void MayhemAbility(Player p, Player a, Player b, Player c) {
		ArrayList<Effect> e = new ArrayList<Effect>();
		Effect CherryWeaken = new Effect("weak", 1, 1);
		Effect MayhemVulnerable = new Effect("vulnerable", 0.3, 1);
		e.add(CherryWeaken);
		e.add(MayhemVulnerable);
		if(!a.inRange(p, 12) && !b.inRange(p, 12) && !c.inRange(p, 12)) {
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
			if(p.inRange(a, 12)) {
				a.addEffects(e);
				a.applyEffects();
				p.setCooldown(3);
				System.out.println(p.voiceline());
				System.out.println();
			}else {
				System.out.println(a.getSkin() + " is not in range!");
				System.out.println();
			}
		}
		if(targetResponse.equals("2")) {
			if(p.inRange(b, 12)) {
				b.addEffects(e);
				b.applyEffects();
				p.setCooldown(3);
				System.out.println(p.voiceline());
				System.out.println();
			}else {
				System.out.println(b.getSkin() + " is not in range!");
				System.out.println();
			}
		}
		if(targetResponse.equals("3")) {
			if(p.inRange(c, 12)) {
				c.addEffects(e);
				c.applyEffects();
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
		ArrayList<Effect> e = new ArrayList<Effect>();
		Effect DimentioProtect = new Effect("protect", 0.2, 100);
		Effect MayhemFortify = new Effect("fortify", 0, 100);
		e.add(DimentioProtect);
		e.add(MayhemFortify);
		p.addEffects(e);
		p.applyEffects();
		System.out.println("\"It's a good day to cause some Mayhem!\"");
		System.out.println();
	}
	
	public static void GatesAttack(Player p, Player a, Location l) {
		if(!a.inRange(l, 4)) {
			System.out.println("Too far to pull the target!");
			System.out.println();
			return;
		}
		a.getLoc().set(l.getX(), l.getY());
		a.resetCover();
		p.attack(a);
		System.out.println();
	}
	
	public static void GatesAbility(Player p, Player a, Player b) {
		Scanner input = new Scanner(System.in);
		ArrayList<Effect> e = new ArrayList<Effect>();
		Effect MaxProtect = new Effect("fortify", 0, 2);
		e.add(MaxProtect);
		System.out.println("1: " + a.getSkin() +a.showHP() +  a.getHealth() + "/" + a.getMaxHP() + ".");
		System.out.println("2: " + b.getSkin() +b.showHP() +  b.getHealth() + "/" + b.getMaxHP() + ".");
		System.out.println("Who do you want to give the Aegis Array to: ");
		String targetResponse = input.next();
		if(targetResponse.equals("1")) {
			a.setShield();
			a.addEffects(e);
			a.applyEffects();
			p.setCooldown(3);
		}
		if(targetResponse.equals("2")) {
			b.setShield();
			b.addEffects(e);
			b.applyEffects();
			p.setCooldown(3);
		}
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void GatesUltimate(Player p) {
		ArrayList<Effect> e = new ArrayList<Effect>();
		Effect GatesFreeze = new Effect("freeze", 0, 1);
		Effect GatesDaze = new Effect("daze", 0, 1);
		e.add(GatesFreeze);
		e.add(GatesDaze);
		p.addEffects(e);
		p.applyEffects();
		String[] chars = {"Lunar", "Aidan", "Finley", "Katrina", "Sammi", "Mack", "Axol", "Via", "Kailani", "Zero", "Mason", "Max", "Evil",
				"Airic", "Julian", "Solar", "Eli", "Dylan", "Orion", "Clara", "Liam", "Midnite", "Ashley", "Rocco", "Xara", "Thunder",
				"Archer", "Tom", "Gash", "Louis", "Kithara", "Audrey", "Ayson", "Chloe", "Hopper", "Radar", "Oona", "Augie", "Ruby", "Norman"};
		//System.out.println(chars.length);
		int randomX = (int)(Math.random() * (39 - 0 + 1)) + 0;
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
		if(rand <= 0.01) {
			a.knockbacked();
		}
		double rand2 = Math.random();
		if(rand2 <= 0.01) {
			b.knockbacked();
		}
		double rand3 = Math.random();
		if(rand3 <= 0.01) {
			c.knockbacked();
		}
		System.out.println();
	}
	
	public static void AudreyAbility(Player p, Player a, Player b, Player c) {
		ArrayList<Effect> e = new ArrayList<Effect>();
		ArrayList<Effect> e2 = new ArrayList<Effect>();
		ArrayList<Effect> e3 = new ArrayList<Effect>();
		Effect CherryWeaken = new Effect("weak", 0.5, 1);
		Effect CherryWeaken2 = new Effect("weak", 0.5, 1);
		Effect CherryWeaken3 = new Effect("weak", 0.5, 1);
		Effect AudreyVulnerable = new Effect("vulnerable", 0.2, 1);
		Effect AudreyVulnerable2 = new Effect("vulnerable", 0.2, 1);
		Effect AudreyVulnerable3 = new Effect("vulnerable", 0.2, 1);
		e.add(CherryWeaken);
		e2.add(CherryWeaken2);
		e3.add(CherryWeaken3);
		e.add(AudreyVulnerable);
		e2.add(AudreyVulnerable2);
		e3.add(AudreyVulnerable3);
		if(!a.inRange(p, 10) && !b.inRange(p, 10) && !c.inRange(p, 10)) {
			System.out.println("No targets in range!");
			System.out.println();
			return;
		}
		if(a.inRange(p, 10)) {
			a.addEffects(e);
			a.applyEffects();
		}
		if(b.inRange(p, 10)) {
			b.addEffects(e2);
			b.applyEffects();
		}
		if(c.inRange(p, 10)) {
			c.addEffects(e3);
			c.applyEffects();
		}
		p.setCooldown(4);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void AudreyUltimate(Player p, Player a, Player b) {
		Scanner input = new Scanner(System.in);
		ArrayList<Effect> e = new ArrayList<Effect>();
		Effect MaxPower = new Effect("power", 0.5, 1);
		Effect MaxProtect = new Effect("protect", 0.5, 2);
		e.add(MaxPower);
		e.add(MaxProtect);
		System.out.println("1: " + a.getSkin() +a.showHP() +  a.getHealth() + "/" + a.getMaxHP() + ".");
		System.out.println("2: " + b.getSkin() +b.showHP() +  b.getHealth() + "/" + b.getMaxHP() + ".");
		System.out.println("3: " + p.getSkin() +b.showHP() +  p.getHealth() + "/" + p.getMaxHP() + ".");
		System.out.print("Who do you want to use the Wonder Flower on: ");
		String targetResponse = input.next();
		if(targetResponse.equals("1")) {
			if(a.revive()) {
				p.resetUlt();
				System.out.println();
				System.out.println("\"You've still got the fighting spirit!\"");
			}
		}
		if(targetResponse.equals("2")) {
			if(b.revive()) {
				p.resetUlt();
				System.out.println();
				System.out.println("\"You've still got the fighting spirit!\"");
			}
		}
		if(targetResponse.equals("3")) {
			p.heal(0.5);
			p.addEffects(e);
			p.applyEffects();
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
        double rand = Math.random();
        if(score > 7) {
        	if(rand <= 0.4) {
    			ArrayList<Effect> e = new ArrayList<Effect>();
    			Effect BurtParalyze = new Effect("freeze", 0, 1);
    			Effect AiricDaze = new Effect("daze", 0, 1);
    			e.add(BurtParalyze);
    			e.add(AiricDaze);
    			a.addEffects(e);
    			a.applyEffects();
    		}
        }else {
        	if(rand <= 0.1) {
    			ArrayList<Effect> e = new ArrayList<Effect>();
    			Effect BurtParalyze = new Effect("freeze", 0, 1);
    			Effect AiricDaze = new Effect("daze", 0, 1);
    			e.add(BurtParalyze);
    			e.add(AiricDaze);
    			a.addEffects(e);
    			a.applyEffects();
    		}
        }
		System.out.println();
	}
	
	public static void AysonAbility(Player p, Player a, Player b, Player c, Player d, Player e) {
		p.increaseMovement(5);
		d.increaseMovement(5);
		e.increaseMovement(5);
		ArrayList<Effect> e1 = new ArrayList<Effect>();
		ArrayList<Effect> e2 = new ArrayList<Effect>();
		ArrayList<Effect> e3 = new ArrayList<Effect>();
		ArrayList<Effect> e4 = new ArrayList<Effect>();
		ArrayList<Effect> e5 = new ArrayList<Effect>();
		ArrayList<Effect> e6 = new ArrayList<Effect>();
		Effect EliPower = new Effect("power", 0.1, 1);
		Effect EliPower2 = new Effect("power", 0.1, 1);
		Effect EliPower3 = new Effect("power", 0.1, 1);
		Effect AshleyPoison = new Effect("weary", 0, 1);
		Effect AshleyPoison2 = new Effect("weary", 0, 1);
		Effect AshleyPoison3 = new Effect("weary", 0, 1);
		Effect AshleyBlind = new Effect("weak", 0.1, 1);
		Effect AshleyBlind2 = new Effect("weak", 0.1, 1);
		Effect AshleyBlind3 = new Effect("weak", 0.1, 1);
		e1.add(EliPower);
		e5.add(EliPower2);
		e6.add(EliPower3);
		e2.add(AshleyPoison);
		e2.add(AshleyBlind);
		e3.add(AshleyPoison2);
		e3.add(AshleyBlind2);
		e4.add(AshleyPoison3);
		e4.add(AshleyBlind3);
		p.addEffects(e1);
		p.applyEffects();
		a.addEffects(e2);
		a.applyEffects();
		b.addEffects(e3);
		b.applyEffects();
		c.addEffects(e4);
		c.applyEffects();
		d.addEffects(e5);
		d.applyEffects();
		e.addEffects(e6);
		e.applyEffects();
		p.setCooldown(3);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void AysonUltimate(Player p, Player a, Player b, Player c) {
		Scanner input = new Scanner(System.in);
		ArrayList<Effect> e = new ArrayList<Effect>();
		Effect AysonStun = new Effect("stun", 0, 1);
		e.add(AysonStun);
		System.out.println("1: " + a.getName() +a.showHP() +  a.getHealth() + "/" + a.getMaxHP());
		System.out.println("2: " + b.getName() +b.showHP() +  b.getHealth() + "/" + b.getMaxHP());
		System.out.println("3: " + c.getName() +c.showHP() +  c.getHealth() + "/" + c.getMaxHP());
		System.out.print("Who do you want to tag team on: ");
		String targetResponse = input.next();
		if(targetResponse.equals("1")) {
			a.takeDamage(900);
			a.addEffects(e);
			a.applyEffects();
			p.resetUlt();
		}
		if(targetResponse.equals("2")) {
			b.takeDamage(900);
			b.addEffects(e);
			b.applyEffects();
			p.resetUlt();
		}
		if(targetResponse.equals("3")) {
			c.takeDamage(900);
			c.addEffects(e);
			c.applyEffects();
			p.resetUlt();
		}
		System.out.println();
		System.out.println("\"Prepare for trouble... \"" +" "+ "\"and make it double!\"");
		System.out.println();
	}
	
	public static void ChloeAttack(Player p, Player a, Player b, Player c) {
		p.attack(a);
		ArrayList<Effect> e = new ArrayList<Effect>();
		ArrayList<Effect> e2 = new ArrayList<Effect>();
		Effect ChloePower = new Effect("power", 0.03, 1);
		Effect ChloePower2 = new Effect("power", 0.03, 1);
		e.add(ChloePower);
		e2.add(ChloePower2);
		b.addEffects(e);
		b.applyEffects();
		c.addEffects(e2);
		c.applyEffects();
		System.out.println();
	}
	
	public static void ChloeAbility(Player p, Player a, Player b) {
		p.heal(0.2);
		a.heal(0.2);
		b.heal(0.2);
		ArrayList<Effect> e1 = new ArrayList<Effect>();
		ArrayList<Effect> e2 = new ArrayList<Effect>();
		ArrayList<Effect> e3 = new ArrayList<Effect>();
		Effect ChloeMend = new Effect("mend", 0.25, 1);
		Effect ChloeMend2 = new Effect("mend", 0.25, 1);
		Effect ChloeMend3 = new Effect("mend", 0.25, 1);
		Effect ChloeFortify = new Effect("fortify", 0, 2);
		Effect ChloeFortify2 = new Effect("fortify", 0, 2);
		Effect ChloeFortify3 = new Effect("fortify", 0, 2);
		e1.add(ChloeMend);
		e1.add(ChloeFortify);
		e2.add(ChloeMend2);
		e2.add(ChloeFortify2);
		e3.add(ChloeMend3);
		e3.add(ChloeFortify3);
		p.addEffects(e1);
		p.applyEffects();
		a.addEffects(e2);
		a.applyEffects();
		b.addEffects(e3);
		b.applyEffects();
		p.setCooldown(3);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void ChloeUltimate(Player p, Player a, Player b) {
		ArrayList<Effect> e = new ArrayList<Effect>();
		ArrayList<Effect> e2 = new ArrayList<Effect>();
		ArrayList<Effect> e3 = new ArrayList<Effect>();
		Effect ChloeProtect = new Effect("protect", 0.6, 3);
		Effect ChloeProtect2 = new Effect("protect", 0.6, 3);
		Effect ChloeProtect3 = new Effect("protect", 0.6, 3);
		Effect ChloePower = new Effect("power", 0.4, 2);
		Effect ChloePower2 = new Effect("power", 0.4, 2);
		Effect ChloePower3 = new Effect("power", 0.4, 2);
		e.add(ChloeProtect);
		e.add(ChloePower2);
		e2.add(ChloeProtect2);
		e2.add(ChloePower);
		e3.add(ChloeProtect3);
		e3.add(ChloePower3);
		p.addEffects(e);
		p.applyEffects();
		a.addEffects(e2);
		a.applyEffects();
		b.addEffects(e3);
		b.applyEffects();
		p.resetUlt();
		System.out.println("\"If you need something done right, do it yourself!\"");
		System.out.println();
	}
	
	public static void HopperAttack(Player p, Player a) {
		p.attack(a);
		ArrayList<Effect> e = new ArrayList<Effect>();
		Effect AshleyPoison = new Effect("poison", 0.3, 1);
		e.add(AshleyPoison);
		a.addEffects(e);
		a.applyEffects();
		System.out.println();
	}
	
	public static void HopperAbility(Player p, Player a, Player b) {
		p.resetAttack();
		a.resetAttack();
		b.resetAttack();
		p.setCooldown(4);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void HopperUltimate(Player p, Player a, Player b) {
		ArrayList<Effect> e1 = new ArrayList<Effect>();
		ArrayList<Effect> e2 = new ArrayList<Effect>();
		ArrayList<Effect> e3 = new ArrayList<Effect>();
		Effect HopperReflect = new Effect("reflection", 0, 3);
		Effect HopperProtect = new Effect("protect", 0.5, 3);
		Effect HopperHeal = new Effect("heal", 0.1, 3);
		Effect HopperHeal2 = new Effect("heal", 0.1, 3);
		Effect HopperHeal3 = new Effect("heal", 0.1, 3);
		e1.add(HopperReflect);
		e1.add(HopperProtect);
		e1.add(HopperHeal);
		e2.add(HopperHeal2);
		e3.add(HopperHeal3);
		p.addEffects(e1);
		p.applyEffects();
		a.addEffects(e2);
		a.applyEffects();
		b.addEffects(e3);
		b.applyEffects();
		p.resetUlt();
		System.out.println("\"For the Crusaders!\"");
		System.out.println();
	}
	
	public static void RedgarAttack(Player p, Player a) {
		p.attack(a);
		double rand = Math.random();
		if(rand <= 0.1) {
			ArrayList<Effect> e = new ArrayList<Effect>();
			Effect BurtParalyze = new Effect("paralyze", 0, 1);
			Effect EvilIgnite = new Effect("blind", 0.2, 1);
			e.add(BurtParalyze);
			e.add(EvilIgnite);
			a.addEffects(e);
			a.applyEffects();
		}
		if(p.ultActive()) {
			p.increaseDPS(0.05);
			p.increaseMaxHP(p.getMaxHP() * 0.03);
		}
		System.out.println();
	}
	
	public static void RedgarAbility(Player p, Player a, Player b) {
		p.cleanse();
		a.cleanse();
		b.cleanse();
		ArrayList<Effect> e1 = new ArrayList<Effect>();
		ArrayList<Effect> e2 = new ArrayList<Effect>();
		ArrayList<Effect> e3 = new ArrayList<Effect>();
		Effect RedgarRefine = new Effect("refine", 0, 3);
		Effect RedgarRefine2 = new Effect("refine", 0, 3);
		Effect RedgarRefine3 = new Effect("refine", 0, 3);
		e1.add(RedgarRefine);
		e2.add(RedgarRefine2);
		e3.add(RedgarRefine3);
		p.addEffects(e1);
		p.applyEffects();
		a.addEffects(e2);
		a.applyEffects();
		b.addEffects(e3);
		b.applyEffects();
		p.setCooldown(5);
		System.out.println(p.voiceline());
		System.out.println();
		if(p.ultActive()) {
			p.increaseDPS(0.05);
			p.increaseMaxHP(p.getMaxHP() * 0.03);
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
		ArrayList<Effect> e = new ArrayList<Effect>();
		ArrayList<Effect> e2 = new ArrayList<Effect>();
		ArrayList<Effect> e3 = new ArrayList<Effect>();
		Effect CherryWeaken = new Effect("daze", 0, 1);
		Effect CherryWeaken2 = new Effect("daze", 0, 1);
		Effect CherryWeaken3 = new Effect("daze", 0, 1);
		Effect AudreyVulnerable = new Effect("weary", 0, 1);
		Effect AudreyVulnerable2 = new Effect("weary", 0, 1);
		Effect AudreyVulnerable3 = new Effect("weary", 0, 1);
		e.add(CherryWeaken);
		e2.add(CherryWeaken2);
		e3.add(CherryWeaken3);
		e.add(AudreyVulnerable);
		e2.add(AudreyVulnerable2);
		e3.add(AudreyVulnerable3);
		a.addEffects(e);
		a.applyEffects();
		b.addEffects(e2);
		b.applyEffects();
		c.addEffects(e3);
		c.applyEffects();
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
					d.increaseMovement(10);
				}else {
					d.getOrb();
				}
			}
			if(targetResponse.equals("2") && e.isAlive()) {
				if(e.ultReady()) {
					e.increaseMovement(10);
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
		a.knockbacked();
		System.out.println();
	}
	
	public static void OonaAbility(Player p, Player a, Player b, Player c) {
		ArrayList<Effect> e = new ArrayList<Effect>();
		Effect CherryWeaken = new Effect("blind", 0.75, 1);
		Effect MayhemVulnerable = new Effect("daze", 0, 1);
		e.add(CherryWeaken);
		e.add(MayhemVulnerable);
		Scanner input = new Scanner(System.in);
		System.out.println("1: " + a.getSkin() +a.showHP() +  a.getHealth() + "/" + a.getMaxHP());
		System.out.println("2: " + b.getSkin() +b.showHP() +  b.getHealth() + "/" + b.getMaxHP());
		System.out.println("3: " + c.getSkin() +c.showHP() +  c.getHealth() + "/" + c.getMaxHP());
		System.out.print("Who do you want to throw the VR Goggles on: ");
		String targetResponse = input.next();
		if(targetResponse.equals("1")) {
			a.addEffects(e);
			a.applyEffects();
			p.setCooldown(3);
			System.out.println(p.voiceline());
			System.out.println();
		}
		if(targetResponse.equals("2")) {
			b.addEffects(e);
			b.applyEffects();
			p.setCooldown(3);
			System.out.println(p.voiceline());
			System.out.println();
		}
		if(targetResponse.equals("3")) {
			c.addEffects(e);
			c.applyEffects();
			p.setCooldown(3);
			System.out.println(p.voiceline());
			System.out.println();
		}
	}
	
	public static void OonaUltimate(Player p, Player a, Player b) {
		p.setUlt();
		ArrayList<Effect> e = new ArrayList<Effect>();
		ArrayList<Effect> e2 = new ArrayList<Effect>();
		ArrayList<Effect> e3 = new ArrayList<Effect>();
		Effect ChloeProtect = new Effect("protect", 0.1, 100);
		Effect ChloeProtect2 = new Effect("protect", 0.1, 100);
		Effect ChloeProtect3 = new Effect("protect", 0.1, 100);
		Effect ChloePower = new Effect("power", 0.15, 100);
		Effect ChloePower2 = new Effect("power", 0.15, 100);
		Effect ChloePower3 = new Effect("power", 0.15, 100);
		Effect OonaHeal = new Effect("heal", 0.05, 100);
		Effect OonaHeal2 = new Effect("heal", 0.05, 100);
		Effect OonaHeal3 = new Effect("heal", 0.05, 100);
		e.add(ChloeProtect);
		e.add(ChloePower2);
		e.add(OonaHeal);
		e2.add(ChloeProtect2);
		e2.add(ChloePower);
		e2.add(OonaHeal2);
		e3.add(ChloeProtect3);
		e3.add(ChloePower3);
		e3.add(OonaHeal3);
		p.addEffects(e);
		p.applyEffects();
		a.addEffects(e2);
		a.applyEffects();
		b.addEffects(e3);
		b.applyEffects();
		System.out.println("\"Welcome to my World!\"");
		System.out.println();
	}
	
	public static void AugieAttack(Player p, Player a) {
		p.attack(a);
		p.setShield();
		System.out.println();
	}
	
	public static void AugieAbility(Player p, Player a, Player b) {
		ArrayList<Effect> e = new ArrayList<Effect>();
		ArrayList<Effect> e2 = new ArrayList<Effect>();
		ArrayList<Effect> e3 = new ArrayList<Effect>();
		Effect LiamProtect = new Effect("sight", 0.75, 1);
		Effect LiamProtect2 = new Effect("sight", 0.75, 1);
		Effect LiamProtect3 = new Effect("sight", 0.75, 1);
		e.add(LiamProtect);
		e2.add(LiamProtect2);
		e3.add(LiamProtect3);
		p.addEffects(e);
		p.applyEffects();
		a.addEffects(e2);
		a.applyEffects();
		b.addEffects(e3);
		b.applyEffects();
		p.setCooldown(3);
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
				a.takeDamage(350);
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
				a.getLoc().set(x, y);
				a.knockbacked();
			}
			if(targetResponse.equals("2")) {
				b.takeDamage(350);
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
				b.getLoc().set(x, y);
				b.knockbacked();
			}
			if(targetResponse.equals("3")) {
				c.takeDamage(350);
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
				c.getLoc().set(x, y);
				c.knockbacked();
			}
		}
		p.resetUlt();
		System.out.println("\"See you later!\"");
		System.out.println();
	}
	
	public static void RubyAttack(Player p, Player a) {
		p.attack(a);
		double rand = Math.random();
		if(rand <= 0.35) {
			ArrayList<Effect> e = new ArrayList<Effect>();
			Effect RubyBlind = new Effect("blind", 0.05, 1);
			Effect RubyVulnerable = new Effect("vulnerable", 0.05, 1);
			Effect RubyWeak = new Effect("weak", 0.05, 1);
			e.add(RubyWeak);
			e.add(RubyVulnerable);
			e.add(RubyBlind);
			a.addEffects(e);
			a.applyEffects();
		}
		System.out.println();
	}
	
	public static void RubyAbility(Player p, Location l) {
		Scanner input = new Scanner(System.in);
		System.out.print("What tile do you want to make: ");
		String targetResponse = input.next();
		b.addTile(targetResponse, l);
		p.setCooldown(3);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void RubyUltimate(Player p, Player a, Player b) {
		ArrayList<Effect> e = new ArrayList<Effect>();
		ArrayList<Effect> e2 = new ArrayList<Effect>();
		ArrayList<Effect> e3 = new ArrayList<Effect>();
		Effect ChloeProtect = new Effect("protect", 1, 3);
		Effect ChloeProtect2 = new Effect("protect", 1, 3);
		Effect ChloeProtect3 = new Effect("protect", 1, 3);
		e.add(ChloeProtect);
		e2.add(ChloeProtect2);
		e3.add(ChloeProtect3);
		p.addEffects(e);
		p.applyEffects();
		a.addEffects(e2);
		a.applyEffects();
		b.addEffects(e3);
		b.applyEffects();
		p.setReduce();
		a.setReduce();
		b.setReduce();
		p.resetUlt();
		System.out.println("\"Gamemode Creative activated!\"");
		System.out.println();
	}
	
	public static void NormanAttack(Player p, Player a, Player b, Player c) {
		p.attack(a);
		p.heal(0.05);
		b.heal(0.05);
		c.heal(0.05);
		System.out.println();
	}
	
	public static void NormanAbility(Player p, Player a, Player b, Player c) {
		ArrayList<Effect> e = new ArrayList<Effect>();
		ArrayList<Effect> e2 = new ArrayList<Effect>();
		ArrayList<Effect> e3 = new ArrayList<Effect>();
		Effect LouisVulnerable = new Effect("poison", 1, 1);
		Effect LouisVulnerable2 = new Effect("poison", 1, 1);
		Effect LouisVulnerable3 = new Effect("poison", 1, 1);
		Effect LouisFreeze = new Effect("blind", 0.5, 1);
		Effect LouisFreeze2 = new Effect("blind", 0.5, 1);
		Effect LouisFreeze3 = new Effect("blind", 0.5, 1);
		e.add(LouisFreeze);
		e2.add(LouisFreeze2);
		e3.add(LouisFreeze3);
		e.add(LouisVulnerable);
		e2.add(LouisVulnerable2);
		e3.add(LouisVulnerable3);
		a.addEffects(e);
		a.applyEffects();
		b.addEffects(e2);
		b.applyEffects();
		c.addEffects(e3);
		c.applyEffects();
		p.setCooldown(3);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void NormanUltimate(Player p, Player a, Player b) {
		Scanner input = new Scanner(System.in);
		ArrayList<Effect> e = new ArrayList<Effect>();
		Effect MaxPower = new Effect("power", 2.5, 1);
		e.add(MaxPower);
		System.out.println("1: " + a.getSkin() +a.showHP() +  a.getHealth() + "/" + a.getMaxHP() + ".");
		System.out.println("2: " + b.getSkin() +b.showHP() +  b.getHealth() + "/" + b.getMaxHP() + ".");
		System.out.println("3: " + p.getSkin() +b.showHP() +  p.getHealth() + "/" + p.getMaxHP() + ".");
		System.out.print("Who do you want to be able to one punch: ");
		String targetResponse = input.next();
		if(targetResponse.equals("1")) {
			a.addEffects(e);
			a.applyEffects();
			p.resetUlt();
			System.out.println();
			System.out.println("\"Knock them out!\"");
		}
		if(targetResponse.equals("2")) {
			b.addEffects(e);
			b.applyEffects();
			p.resetUlt();
			System.out.println();
			System.out.println("\"Knock them out!\"");
		}
		if(targetResponse.equals("3")) {
			p.addEffects(e);
			p.applyEffects();
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
		ArrayList<Effect> e = new ArrayList<Effect>();
		ArrayList<Effect> e2 = new ArrayList<Effect>();
		ArrayList<Effect> e3 = new ArrayList<Effect>();
		Effect LouisFreeze = new Effect("ignite", 0, 1);
		Effect LouisFreeze2 = new Effect("ignite", 0, 1);
		Effect LouisFreeze3 = new Effect("ignite", 0, 1);
		e.add(LouisFreeze);
		e2.add(LouisFreeze2);
		e3.add(LouisFreeze3);
		a.addEffects(e);
		a.applyEffects();
		b.addEffects(e2);
		b.applyEffects();
		c.addEffects(e3);
		c.applyEffects();
		if(p.ultActive()) {
			a.knockbacked();
			b.knockbacked();
			c.knockbacked();
		}
		System.out.println();
	}
	
	public static void JesseAbility(Player p) {
		ArrayList<Effect> e = new ArrayList<Effect>();
		Effect MaxPower = new Effect("power", 2, 2);
		Effect MaxProtect = new Effect("protect", 0.7, 3);
		e.add(MaxPower);
		e.add(MaxProtect);
		p.addEffects(e);
		p.applyEffects();
		p.setCooldown(5);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void JesseUltimate(Player p) {
		ArrayList<Effect> e = new ArrayList<Effect>();
		Effect MaxPower = new Effect("power", 3, 1);
		Effect MaxProtect = new Effect("protect", 0.9, 2);
		e.add(MaxPower);
		e.add(MaxProtect);
		p.addEffects(e);
		p.applyEffects();
		p.setUlt();
		p.resetUlt();
		System.out.println("\"Rise from the depths of hell Ignis!\"");
		System.out.println();
	}
	
	public static void ChiefAttack(Player p, Player a) {
		p.attack(a);
		p.getLoc().set(a.getLoc().getX(), a.getLoc().getY());
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
		a.knockbacked();
		double rand = Math.random();
		if(rand <= 0.025) {
			ArrayList<Effect> e = new ArrayList<Effect>();
			Effect OrionDaze = new Effect("daze", 0, 1);
			e.add(OrionDaze);
			a.addEffects(e);
			a.applyEffects();
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
		p.increaseMaxHP(p.getMaxHP() * 0.05);
		a.increaseMaxHP(p.getMaxHP() * 0.05);
		b.increaseMaxHP(p.getMaxHP() * 0.05);
		p.setCooldown(3);
		System.out.println(p.voiceline());
		System.out.println();
	}
	
	public static void ChiefUltimate(Player p, Player a, Player b, Player c, Player d, Player e) {
		ArrayList<Effect> e1 = new ArrayList<Effect>();
		ArrayList<Effect> e2 = new ArrayList<Effect>();
		ArrayList<Effect> e3 = new ArrayList<Effect>();
		Effect BedrockStun = new Effect("stun", 0, 1);
		Effect BedrockStun2 = new Effect("stun", 0, 1);
		Effect BedrockStun3 = new Effect("stun", 0, 1);
		e1.add(BedrockStun);
		e2.add(BedrockStun2);
		e3.add(BedrockStun3);
		if(a.inRange(p, 3)) {
			a.addEffects(e1);
			a.applyEffects();
		}else if(p.inRange(a)){
			double rand = Math.random();
			if(rand <= 0.35) {
				a.addEffects(e1);
				a.applyEffects();
			}
		}
		if(b.inRange(p, 3)) {
			b.addEffects(e2);
			b.applyEffects();
		}else if(p.inRange(b)) {
			double rand = Math.random();
			if(rand <= 0.35) {
				b.addEffects(e2);
				b.applyEffects();
			}
		}
		if(c.inRange(p, 3)) {
			c.addEffects(e3);
			c.applyEffects();
		}else if(p.inRange(c)) {
			double rand = Math.random();
			if(rand <= 0.35) {
				c.addEffects(e3);
				c.applyEffects();
			}
		}
		if(p.getHealth() < (p.getMaxHP() * 0.25)) {
			p.heal(0.5);
		}
		if(d.getHealth() < (d.getMaxHP() * 0.25)) {
			d.heal(0.5);
		}
		if(e.getHealth() < (e.getMaxHP() * 0.25)) {
			e.heal(0.5);
		}
		p.resetUlt();
		System.out.println("\"Hammer down!\"");
		System.out.println();
	}

}
