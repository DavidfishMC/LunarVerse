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
	static int turns2 = 0;

	public static void main(String[] args) {
		
		try {
			String audio = "battleedit.wav";
			audioPlayer = new Music(audio); 
			audioPlayer.play();
			audioPlayer.pause();
		}catch (Exception e) {
			System.out.println(e);
		}
		int turns = 0;
		//HP, Damage, Turn, Name, X, Y, Range, Movement, Ult
		Player p1 = new Player(2650, 175, true, "Max", 0, 0, 30, 100, 0);
		Player p3 = new Player(2900, 325, false, "Cherry", 0, 3, 10, 100, 0);
		Player p5 = new Player(4850, 575, false, "Rocco", 3, 0, 6, 500, 0);
		
		Player p2 = new Player(10000, 225, false, "Finley", 40, 40, 9, 100, 0);
		Player p4 = new Player(10000, 200, false, "Louis", 40, 37, 10, 100, 0);
		Player p6 = new Player(10000, 200, false, "Solar", 37, 40, 10, 100, 0);
		
		Party party1 = new Party(true, p1, p3, p5);
		Party party2 = new Party(false, p2, p4, p6);
		
		Battlefield b = new Battlefield("Galactical Laboratories", p1, p3, p5, p2, p4, p6);
		boolean game = false;
		Scanner input = new Scanner(System.in);
		System.out.println("\033[3mLunarVerse\033[0m");
		System.out.println("Created by Davidfish. Inspired by the Mario and Rabbids games and the V.C., R.C., and D.C. trilogies.");
		System.out.println("Music taken from the Mario and Rabbids games.");
		System.out.print("Enter any key to start: ");
		String temp = input.next();
		System.out.println();
		audioPlayer.play();
		game = true;
		while(game) {
			if(turns % 2 == 0) {
				turns2++;
			}
			while(party1.getTurn()) {
				if((p1.isStunned() || !p1.isAlive()) && (p3.isStunned() || !p3.isAlive()) && (p5.isStunned() || !p5.isAlive())) {
					party1.passTurn(party2);
					party1.reduceTeamEffects();
					party1.reduceTeamCooldowns();
					turns++;
					SpawnOrbs();
					SpawnCover();
				}
				while(p1.getTurn() && !p1.isStunned()) {
					System.out.println("Turn: " + turns2);
					System.out.println("Team 1's Turn (Go " + p1.getName() + "!)");
					System.out.println(p1);
					System.out.print("What would " + p1.getName() + " like to do: ");
					String response = input.next();
					System.out.println();
					if(response.equals("b")){
						b.printField(p1, p2, p3, p4, p5, p6, orbs, cover);
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
							System.out.println();
						}
						if(switchResponse.equals("3")) {
							p1.passTurn(p5);
							System.out.println();
						}
					}
					if(response.equals("p")) {
						System.out.print("Are you sure you want to pass to the enemy's turn: ");
						String switchResponse = input.next();
						if(switchResponse.equals("p")) {
							if(party2.teamDown()) {
								System.out.println();
								System.out.println("Team 1 Wins!");
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
					if(response.equals("m")) {
						Movement(p1, p2, p4, p6);
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
						Jump(p1, p3, p5);
					}
					if(response.equals("u")) {
						if(!p1.ultReady() || p1.ultActive()) {
							System.out.println("My ultimate cannot be used!");
							System.out.println();
						}else {
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
								System.out.print("Where do you want to send the Guardian to: ");
								String moveResponse = input.next();
								String[] arr = moveResponse.split(",", 0);
								int x = Integer.parseInt(arr[0]);
								int y = Integer.parseInt(arr[1]);
								LouisUltimate(p1, p2, p4, p6, x, y);
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
								System.out.print("Where do you want to move to: ");
								String moveResponse = input.next();
								String[] arr = moveResponse.split(",", 0);
								int x = Integer.parseInt(arr[0]);
								int y = Integer.parseInt(arr[1]);
								MackAbility(p1, p2, p4, p6, x, y);
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
								System.out.print("Where do you want to send Arro: ");
								String moveResponse = input.next();
								String[] arr = moveResponse.split(",", 0);
								int x = Integer.parseInt(arr[0]);
								int y = Integer.parseInt(arr[1]);
								ViaAbility(p1, p2, p4, p6, x, y);
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
								System.out.print("Where do you want to riptide to: ");
								String moveResponse = input.next();
								String[] arr = moveResponse.split(",", 0);
								int x = Integer.parseInt(arr[0]);
								int y = Integer.parseInt(arr[1]);
								KailaniAbility(p1, p2, p4, p6, x, y);
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
								if(p1.getName().equals("Bedrock")) {
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
										}
									}
								}
							}
						}
					}
				}
				
				while(p3.getTurn()) {
					System.out.println("Turn: " + turns2);
					System.out.println("Team 1's Turn (Go " + p3.getName() + "!)");
					System.out.println(p3);
					System.out.print("What would " + p3.getName() + " like to do: ");
					String response = input.next();
					System.out.println();
					if(response.equals("b")){
						b.printField(p1, p2, p3, p4, p5, p6, orbs, cover);
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
								System.out.println("Team 1 Wins!");
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
						Jump(p3, p1, p5);
					}
					if(response.equals("u")) {
						if(!p3.ultReady() || p3.ultActive()) {
							System.out.println("My ultimate cannot be used!");
							System.out.println();
						}else {
							if(p3.getName().equals("Sammi")) {
								SammiUltimate(p3);
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
								System.out.print("Where do you want to send the Guardian to: ");
								String moveResponse = input.next();
								String[] arr = moveResponse.split(",", 0);
								int x = Integer.parseInt(arr[0]);
								int y = Integer.parseInt(arr[1]);
								LouisUltimate(p3, p2, p4, p6, x, y);
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
							if(p1.getName().equals("Burt")) {
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
							if(p3.getName().equals("Lunar")) {
								LunarUltimate(p3, p2, p4, p6);
							}
						}
					}
					if(response.equals("m")) {
						Movement(p3, p2, p4, p6);
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
								System.out.print("Where do you want to move to: ");
								String moveResponse = input.next();
								String[] arr = moveResponse.split(",", 0);
								int x = Integer.parseInt(arr[0]);
								int y = Integer.parseInt(arr[1]);
								MackAbility(p3, p2, p4, p6, x, y);
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
								System.out.print("Where do you want to send Arro: ");
								String moveResponse = input.next();
								String[] arr = moveResponse.split(",", 0);
								int x = Integer.parseInt(arr[0]);
								int y = Integer.parseInt(arr[1]);
								ViaAbility(p3, p2, p4, p6, x, y);
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
								System.out.print("Where do you want to riptide to: ");
								String moveResponse = input.next();
								String[] arr = moveResponse.split(",", 0);
								int x = Integer.parseInt(arr[0]);
								int y = Integer.parseInt(arr[1]);
								KailaniAbility(p3, p2, p4, p6, x, y);
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
								if(p3.getName().equals("Bedrock")) {
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
										}
									}
								}
							}
						}
					}
				}
				
				while(p5.getTurn()) {
					System.out.println("Turn: " + turns2);
					System.out.println("Team 1's Turn (Go " + p5.getName() + "!)");
					System.out.println(p5);
					System.out.print("What would " + p5.getName() + " like to do: ");
					String response = input.next();
					System.out.println();
					if(response.equals("b")){
						b.printField(p1, p2, p3, p4, p5, p6, orbs, cover);
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
								System.out.println("Team 1 Wins!");
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
					if(response.equals("m")) {
						Movement(p5, p2, p4, p6);
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
						Jump(p5, p3, p1);
					}
					if(response.equals("u")) {
						if(!p5.ultReady() || p5.ultActive()) {
							System.out.println("My ultimate cannot be used!");
							System.out.println();
						}else {
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
								System.out.print("Where do you want to send the Guardian to: ");
								String moveResponse = input.next();
								String[] arr = moveResponse.split(",", 0);
								int x = Integer.parseInt(arr[0]);
								int y = Integer.parseInt(arr[1]);
								LouisUltimate(p5, p2, p4, p6, x, y);
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
								System.out.print("Where do you want to move to: ");
								String moveResponse = input.next();
								String[] arr = moveResponse.split(",", 0);
								int x = Integer.parseInt(arr[0]);
								int y = Integer.parseInt(arr[1]);
								MackAbility(p5, p2, p4, p6, x, y);
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
								System.out.print("Where do you want to send Arro: ");
								String moveResponse = input.next();
								String[] arr = moveResponse.split(",", 0);
								int x = Integer.parseInt(arr[0]);
								int y = Integer.parseInt(arr[1]);
								ViaAbility(p5, p2, p4, p6, x, y);
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
								System.out.print("Where do you want to riptide to: ");
								String moveResponse = input.next();
								String[] arr = moveResponse.split(",", 0);
								int x = Integer.parseInt(arr[0]);
								int y = Integer.parseInt(arr[1]);
								KailaniAbility(p5, p2, p4, p6, x, y);
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
								if(p5.getName().equals("Bedrock")) {
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
										}
									}
								}
							}
						}
					}
				}
				
			}
			
			while(party2.getTurn()) {
				if((p2.isStunned() || !p2.isAlive()) && (p4.isStunned() || !p4.isAlive()) && (p6.isStunned() || !p6.isAlive())) {
					party2.passTurn(party1);
					party2.reduceTeamEffects();
					party2.reduceTeamCooldowns();
					turns++;
					SpawnOrbs();
					SpawnCover();
				}
				while(p2.getTurn()) {
					System.out.println("Turn: " + turns2);
					System.out.println("Team 2's Turn (Go " + p2.getName() + "!)");
					System.out.println(p2);
					System.out.print("What would " + p2.getName() + " like to do: ");
					String response = input.next();
					System.out.println();
					if(response.equals("b")){
						b.printField(p1, p2, p3, p4, p5, p6, orbs, cover);
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
								System.out.println("Team 2 Wins!");
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
					if(response.equals("m")) {
						Movement(p2, p1, p3, p5);
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
						Jump(p2, p4, p6);
					}
					if(response.equals("u")) {
						if(!p2.ultReady() || p2.ultActive()) {
							System.out.println("My ultimate cannot be used!");
							System.out.println();
						}else {
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
								System.out.print("Where do you want to send the Guardian to: ");
								String moveResponse = input.next();
								String[] arr = moveResponse.split(",", 0);
								int x = Integer.parseInt(arr[0]);
								int y = Integer.parseInt(arr[1]);
								LouisUltimate(p2, p1, p3, p5, x, y);
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
								System.out.print("Where do you want to move to: ");
								String moveResponse = input.next();
								String[] arr = moveResponse.split(",", 0);
								int x = Integer.parseInt(arr[0]);
								int y = Integer.parseInt(arr[1]);
								MackAbility(p2, p1, p3, p5, x, y);
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
								System.out.print("Where do you want to send Arro: ");
								String moveResponse = input.next();
								String[] arr = moveResponse.split(",", 0);
								int x = Integer.parseInt(arr[0]);
								int y = Integer.parseInt(arr[1]);
								ViaAbility(p2, p1, p3, p5, x, y);
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
								System.out.print("Where do you want to riptide to: ");
								String moveResponse = input.next();
								String[] arr = moveResponse.split(",", 0);
								int x = Integer.parseInt(arr[0]);
								int y = Integer.parseInt(arr[1]);
								KailaniAbility(p2, p1, p3, p5, x, y);
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
								if(p2.getName().equals("Bedrock")) {
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
										}
									}
								}
							}
						}
					}
				}
				
				while(p4.getTurn()) {
					System.out.println("Turn: " + turns2);
					System.out.println("Team 2's Turn (Go " + p4.getName() + "!)");
					System.out.println(p4);
					System.out.print("What would " + p4.getName() + " like to do: ");
					String response = input.next();
					System.out.println();
					if(response.equals("b")){
						b.printField(p1, p2, p3, p4, p5, p6, orbs, cover);
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
								System.out.println("Team 2 Wins!");
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
					if(response.equals("m")) {
						Movement(p4, p1, p3, p5);
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
						Jump(p4, p2, p6);
					}
					if(response.equals("u")) {
						if(!p4.ultReady() || p4.ultActive()) {
							System.out.println("My ultimate cannot be used!");
							System.out.println();
						}else {
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
								System.out.print("Where do you want to send the Guardian to: ");
								String moveResponse = input.next();
								String[] arr = moveResponse.split(",", 0);
								int x = Integer.parseInt(arr[0]);
								int y = Integer.parseInt(arr[1]);
								LouisUltimate(p4, p1, p3, p5, x, y);
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
								System.out.print("Where do you want to move to: ");
								String moveResponse = input.next();
								String[] arr = moveResponse.split(",", 0);
								int x = Integer.parseInt(arr[0]);
								int y = Integer.parseInt(arr[1]);
								MackAbility(p4, p1, p3, p5, x, y);
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
								System.out.print("Where do you want to send Arro: ");
								String moveResponse = input.next();
								String[] arr = moveResponse.split(",", 0);
								int x = Integer.parseInt(arr[0]);
								int y = Integer.parseInt(arr[1]);
								ViaAbility(p4, p1, p3, p5, x, y);
							}
							if(p4.getName().equals("Kailani")) {
								System.out.print("Where do you want to riptide to: ");
								String moveResponse = input.next();
								String[] arr = moveResponse.split(",", 0);
								int x = Integer.parseInt(arr[0]);
								int y = Integer.parseInt(arr[1]);
								KailaniAbility(p4, p1, p3, p5, x, y);
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
								if(p4.getName().equals("Bedrock")) {
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
										}
									}
								}
							}
						}
					}
				}
				
				while(p6.getTurn()) {
					System.out.println("Turn: " + turns2);
					System.out.println("Team 2's Turn (Go " + p6.getName() + "!)");
					System.out.println(p6);
					System.out.print("What would " + p6.getName() + " like to do: ");
					String response = input.next();
					System.out.println();
					if(response.equals("b")){
						b.printField(p1, p2, p3, p4, p5, p6, orbs, cover);
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
								System.out.println("Team 2 Wins!");
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
					if(response.equals("m")) {
						Movement(p6, p1, p3, p5);
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
						Jump(p6, p4, p2);
					}
					if(response.equals("u")) {
						if(!p6.ultReady() || p6.ultActive()) {
							System.out.println("My ultimate cannot be used!");
							System.out.println();
						}else {
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
								System.out.print("Where do you want to send the Guardian to: ");
								String moveResponse = input.next();
								String[] arr = moveResponse.split(",", 0);
								int x = Integer.parseInt(arr[0]);
								int y = Integer.parseInt(arr[1]);
								LouisUltimate(p6, p1, p3, p5, x, y);
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
								System.out.print("Where do you want to move to: ");
								String moveResponse = input.next();
								String[] arr = moveResponse.split(",", 0);
								int x = Integer.parseInt(arr[0]);
								int y = Integer.parseInt(arr[1]);
								MackAbility(p6, p1, p3, p5, x, y);
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
								System.out.print("Where do you want to send Arro: ");
								String moveResponse = input.next();
								String[] arr = moveResponse.split(",", 0);
								int x = Integer.parseInt(arr[0]);
								int y = Integer.parseInt(arr[1]);
								ViaAbility(p6, p1, p3, p5, x, y);
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
								System.out.print("Where do you want to riptide to: ");
								String moveResponse = input.next();
								String[] arr = moveResponse.split(",", 0);
								int x = Integer.parseInt(arr[0]);
								int y = Integer.parseInt(arr[1]);
								KailaniAbility(p6, p1, p3, p5, x, y);
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
								if(p6.getName().equals("Bedrock")) {
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
										}
									}
								}
							}
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
		
	}
	
	public static void Jump(Player p, Player a, Player b) {
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
		System.out.print("Where do you want to jump to: ");
		String moveResponse = input.next();
		String[] arr = moveResponse.split(",", 0);
		int x = Integer.parseInt(arr[0]);
		int y = Integer.parseInt(arr[1]);
		Location l = new Location(x, y);
		if(!p.inReach(l, 5)) {
			System.out.println("Can't jump there!");
			System.out.println();
			return;
		}
		if(p.getLoc().eqLoc(a.getLoc()) && p.canJump()) {
			p.getLoc().set(x, y);
			p.useJump();
		}else if(p.getLoc().eqLoc(b.getLoc()) && p.canJump()) {
			p.getLoc().set(x, y);
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
		System.out.println("Jumped to " + p.getLoc() + ".");
		System.out.println();
	}
	
	public static void Dash(Player p, Player a, Player b, Player c) {
		if(!p.canDash()) {
			System.out.println("No more dashes left!");
			System.out.println();
			return;
		}
		if(p.getLoc().eqLoc(a.getLoc()) && p.canDash()) {
			a.takeDamage(75);
			p.useDash();
		}
		if(p.getLoc().eqLoc(b.getLoc()) && p.canDash()) {
			b.takeDamage(75);
			p.useDash();
		}
		if(p.getLoc().eqLoc(c.getLoc()) && p.canDash()) {
			c.takeDamage(75);
			p.useDash();
		}
		System.out.println();
	}
	
	public static void Movement(Player p, Player a, Player b, Player c) {
		if(p.isParalyzed()) {
			System.out.println("Cannot move while paralyzed!");
			System.out.println();
			return;
		}
		ArrayList<Effect> e = new ArrayList<Effect>();
		Effect ZeroIgnite = new Effect("ignite", 0, 1);
		Effect ZeroDaze = new Effect("daze", 0, 1);
		e.add(ZeroDaze);
		e.add(ZeroIgnite);
		double rand = Math.random();
		Scanner input = new Scanner(System.in);
		if(p.getMovement() == 0) {
			System.out.println("No more movement left!");
			System.out.println();
		}else {
			System.out.print("Where do you want to move to: ");
			String moveResponse = input.next();
			String[] arr = moveResponse.split(",", 0);
			int x = 0;
			int y = 0;
			try {
				x = Integer.parseInt(arr[0]);
				y = Integer.parseInt(arr[1]);
			}catch (Exception ex){
				System.out.println("Enter a new location to move to with the format 'x,y'.");
				System.out.println();
				return;
			}
			if(a.hasSights() && a.inRange(p)) {
				p.takeDamage(a.getDamage() * 0.9);
				a.useSight();
				if(a.getName().equals("Zero") && a.inRange(p, 4) && rand <= 0.1) {
					p.addEffects(e);
					p.applyEffects();
				}
			}
			if(b.hasSights() && b.inRange(p)) {
				p.takeDamage(b.getDamage() * 0.9);
				b.useSight();
				if(b.getName().equals("Zero") && b.inRange(p, 4) && rand <= 0.1) {
					p.addEffects(e);
					p.applyEffects();
				}
			}
			if(c.hasSights() && c.inRange(p)) {
				p.takeDamage(c.getDamage() * 0.9);
				c.useSight();
				if(c.getName().equals("Zero") && c.inRange(p, 4) && rand <= 0.1) {
					p.addEffects(e);
					p.applyEffects();
				}
			}
			p.move(x, y);
			if(a.hasSights() && a.inRange(p)) {
				p.takeDamage(a.getDamage() * 0.9);
				a.useSight();
				if(a.getName().equals("Zero") && a.inRange(p, 4) && rand <= 0.1) {
					p.addEffects(e);
					p.applyEffects();
				}
			}
			if(b.hasSights() && b.inRange(p)) {
				p.takeDamage(b.getDamage() * 0.9);
				b.useSight();
				if(b.getName().equals("Zero") && b.inRange(p, 4) && rand <= 0.1) {
					p.addEffects(e);
					p.applyEffects();
				}
			}
			if(c.hasSights() && c.inRange(p)) {
				p.takeDamage(c.getDamage() * 0.9);
				c.useSight();
				if(c.getName().equals("Zero") && c.inRange(p, 4) && rand <= 0.1) {
					p.addEffects(e);
					p.applyEffects();
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
			System.out.println("Relocated to " + p.getLoc() + ".");
			System.out.println();
		}
	}
	
	public static void SpawnOrbs() {
		int d = 3;
		if(turns2 >= 10) {
			d = 6;
		}
		if(turns2 >= 3) {
			orbs.clear();
			for(int i = 0; i < d; i++) {
				int randomX = (int)(Math.random() * (25 - 15 + 1)) + 15;
				int randomY = (int)(Math.random() * (25 - 15 + 1)) + 15;
				Location l = new Location(randomX, randomY);
				Orb o = new Orb(l);
				orbs.add(o);
			}
			System.out.println("New orbs have spawned!");
		}
	}
	
	public static void SpawnCover() {
		int d1 = 2;
		int d2 = 5;
		if(turns2 >= 10) {
			d1 = 1;
			d2 = 3;
		}
		cover.clear();
		for(int i = 0; i < d1; i++) {
			int randomX = (int)(Math.random() * (30 - 10 + 1)) + 10;
			int randomY = (int)(Math.random() * (30 - 10 + 1)) + 10;
			Location l = new Location(randomX, randomY);
			Cover c = new Cover("Full", l);
			cover.add(c);
		}
		for(int i = 0; i < d2; i++) {
			int randomX = (int)(Math.random() * (30 - 10 + 1)) + 10;
			int randomY = (int)(Math.random() * (30 - 10 + 1)) + 10;
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
		Scanner input = new Scanner(System.in);
		System.out.println("1: " + a.getName() +". Health: " +  a.getHealth() + "/" + a.getMaxHP());
		System.out.println("2: " + b.getName() +". Health: " +  b.getHealth() + "/" + b.getMaxHP());
		System.out.println("3: " + c.getName() +". Health: " +  c.getHealth() + "/" + c.getMaxHP());
		System.out.print("Who do you want to make a copy of: ");
		String targetResponse = input.next();
		if(targetResponse.equals("1")) {
			p.setName(a.getName());
			p.resetCooldown();
			p.resetAttack();
			p.resetUlt();
		}
		if(targetResponse.equals("2")) {
			p.setName(b.getName());
			p.resetCooldown();
			p.resetAttack();
			p.resetUlt();
		}
		if(targetResponse.equals("3")) {
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
		Effect SolarProtect = new Effect("protect", 0.5, 2);
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
		System.out.println("1: " + a.getName() +". Health: " +  a.getHealth() + "/" + a.getMaxHP());
		System.out.println("2: " + b.getName() +". Health: " +  b.getHealth() + "/" + b.getMaxHP());
		System.out.println("3: " + c.getName() +". Health: " +  c.getHealth() + "/" + c.getMaxHP());
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
	
	public static void MackAbility(Player p, Player a, Player b, Player c, int x, int y) {
		p.getLoc().set(x, y);
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
			System.out.println("1: " + a.getName() +". Health: " +  a.getHealth() + "/" + a.getMaxHP() + ". Cover: " + a.getCover());
			System.out.println("2: " + b.getName() +". Health: " +  b.getHealth() + "/" + b.getMaxHP() + ". Cover: " + b.getCover());
			System.out.println("3: " + c.getName() +". Health: " +  c.getHealth() + "/" + c.getMaxHP() + ". Cover: " + c.getCover());
			System.out.println("4: " + d.getName() +". Health: " +  d.getHealth() + "/" + d.getMaxHP() + ". Cover: " + d.getCover());
			System.out.println("5: " + e.getName() +". Health: " +  e.getHealth() + "/" + e.getMaxHP() + ". Cover: " + e.getCover());
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
			System.out.println("1: " + a.getName() +". Health: " +  a.getHealth() + "/" + a.getMaxHP() + ". In Range: " + range + ". Cover: " + a.getCover());
			System.out.println("2: " + b.getName() +". Health: " +  b.getHealth() + "/" + b.getMaxHP() + ". In Range: " + range2 + ". Cover: " + b.getCover());
			System.out.println("3: " + c.getName() +". Health: " +  c.getHealth() + "/" + c.getMaxHP() + ". In Range: " + range3 + ". Cover: " + c.getCover());
			System.out.println("4: " + d.getName() +". Health: " +  d.getHealth() + "/" + d.getMaxHP() + ". In Range: " + range4 + ". Cover: " + d.getCover());
			System.out.println("5: " + e.getName() +". Health: " +  e.getHealth() + "/" + e.getMaxHP() + ". In Range: " + range5 + ". Cover: " + e.getCover());
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
		p.setMaxHP(3600);
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
		System.out.println("1: " + a.getName() +". Health: " +  a.getHealth() + "/" + a.getMaxHP() + ". In Range: " + range + ".");
		System.out.println("2: " + b.getName() +". Health: " +  b.getHealth() + "/" + b.getMaxHP() + ". In Range: " + range2 + ".");
		System.out.println("3: " + c.getName() +". Health: " +  c.getHealth() + "/" + c.getMaxHP() + ". In Range: " + range3 + ".");
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
		System.out.println("1: " + a.getName() +". Health: " +  a.getHealth() + "/" + a.getMaxHP());
		System.out.println("2: " + b.getName() +". Health: " +  b.getHealth() + "/" + b.getMaxHP());
		System.out.println("3: " + c.getName() +". Health: " +  c.getHealth() + "/" + c.getMaxHP());
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
		System.out.println("1: " + a.getName() +". Health: " +  a.getHealth() + "/" + a.getMaxHP());
		System.out.println("2: " + b.getName() +". Health: " +  b.getHealth() + "/" + b.getMaxHP());
		System.out.println("3: " + c.getName() +". Health: " +  c.getHealth() + "/" + c.getMaxHP());
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
				a.takeDamage(a.getMaxHP() * 0.5);
			}
			if(randomY == 2) {
				b.takeDamage(b.getMaxHP() * 0.5);
			}
			if(randomY == 3) {
				c.takeDamage(c.getMaxHP() * 0.5);
			}
		}
		if(randomX == 2) {
			if(randomY == 1) {
				a.takeDamage(a.getMaxHP() * 0.5);
				b.takeDamage(b.getMaxHP() * 0.5);
			}
			if(randomY == 2) {
				b.takeDamage(b.getMaxHP() * 0.5);
				c.takeDamage(c.getMaxHP() * 0.5);
			}
			if(randomY == 3) {
				c.takeDamage(c.getMaxHP() * 0.5);
				a.takeDamage(a.getMaxHP() * 0.5);
			}
		}
		if(randomX == 3) {
			a.takeDamage(a.getMaxHP() * 0.5);
			b.takeDamage(b.getMaxHP() * 0.5);
			c.takeDamage(c.getMaxHP() * 0.5);
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
		System.out.println("1: " + a.getName() +". Health: " +  a.getHealth() + "/" + a.getMaxHP() + ". In Range: " + range + ".");
		System.out.println("2: " + b.getName() +". Health: " +  b.getHealth() + "/" + b.getMaxHP() + ". In Range: " + range2 + ".");
		System.out.println("3: " + c.getName() +". Health: " +  c.getHealth() + "/" + c.getMaxHP() + ". In Range: " + range3 + ".");
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
			p.heal(p.getDamage(), 0.05);
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
		p.heal(0.1);
		a.heal(0.1);
		b.heal(0.1);
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
		p.setSights(10);
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
			input.close();
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
		Effect MaxProtect = new Effect("protect", 0.2, 1);
		e.add(MaxPower);
		e.add(MaxProtect);
		System.out.println("1: " + a.getName() +". Health: " +  a.getHealth() + "/" + a.getMaxHP() + ".");
		System.out.println("2: " + b.getName() +". Health: " +  b.getHealth() + "/" + b.getMaxHP() + ".");
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
		System.out.println("1: " + a.getName() +". Health: " +  a.getHealth() + "/" + a.getMaxHP());
		System.out.println("2: " + b.getName() +". Health: " +  b.getHealth() + "/" + b.getMaxHP());
		System.out.println("3: " + c.getName() +". Health: " +  c.getHealth() + "/" + c.getMaxHP());
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
	
	public static void ViaAbility(Player p, Player a, Player b, Player c, int x, int y) {
		Location l = new Location(x, y);
		if(a.inRange(l, 10) && a.isAlive()) {
			a.getLoc().set(x, y);
			ArrayList<Effect> e = new ArrayList<Effect>();
			Effect ViaVulnerable = new Effect("vulnerable", 0.1, 1);
			e.add(ViaVulnerable);
			a.addEffects(e);
			a.applyEffects();
			a.resetCover();
		}
		if(b.inRange(l, 10) && b.isAlive()) {
			b.getLoc().set(x, y);
			ArrayList<Effect> e = new ArrayList<Effect>();
			Effect ViaVulnerable = new Effect("vulnerable", 0.1, 1);
			e.add(ViaVulnerable);
			b.addEffects(e);
			b.applyEffects();
			b.resetCover();
		}
		if(c.inRange(l, 10) && c.isAlive()) {
			c.getLoc().set(x, y);
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
		Effect ViaParalyze = new Effect("paralyze", 0, 3);
		Effect ViaParalyze2 = new Effect("paralyze", 0, 3);
		Effect ViaParalyze3 = new Effect("paralyze", 0, 3);
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
	
	public static void LouisUltimate(Player p, Player a, Player b, Player c, int x, int y) {
		Location l = new Location(x, y);
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
		p.setSights(4);
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
		a.getOrb();
		b.getOrb();
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
	
	public static void KailaniAbility(Player p, Player a, Player b, Player c, int x, int y) {
		Location l = new Location(x, y);
		if(!p.inReach(l, 15)) {
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
		p.getLoc().set(x, y);
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
			a.takeDamage(225);
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
			b.takeDamage(225);
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
			c.takeDamage(225);
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
		System.out.println("1: " + a.getName() +". Health: " +  a.getHealth() + "/" + a.getMaxHP());
		System.out.println("2: " + b.getName() +". Health: " +  b.getHealth() + "/" + b.getMaxHP());
		System.out.println("3: " + c.getName() +". Health: " +  c.getHealth() + "/" + c.getMaxHP());
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
	
}
