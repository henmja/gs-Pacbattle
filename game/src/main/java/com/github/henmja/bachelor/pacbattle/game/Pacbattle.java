package com.github.henmja.bachelor.pacbattle.game;

import com.github.henmja.bachelor.pacbattle.Application;
import com.github.henmja.bachelor.pacbattle.network.GameSession;
import com.github.henmja.bachelor.pacbattle.network.GameSession.Player;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Transform;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Timer;

public class Pacbattle extends BasicGame implements ActionListener {
	private boolean inc, inc2, set, set2, set3, set4;
	private long gameTimeStart, gameTimeEnd, min;
	private int i = 0;
	private float j1 = 1;
	private float j2 = 1;
	private float j3 = 1;
	private float j4 = 1;
	private float h = 1;
	private float h2 = 1;
	private float h3 = 1;
	private float h4 = 1;
	private Circle p1, p2, p3, p4;
	private Circle food1, food2, food3, food4;
	private String pl1Rot = "right";
	private String pl2Rot = "down";
	private String pl3Rot = "left";
	private String pl4Rot = "up";
	private int numDef;
	private int newScore1, newScore2, newScore3, newScore4, oldScore1,
			oldScore2, oldScore3, oldScore4;
	private int n, c;
	private int size1 = 3;
	private int size2 = 3;
	private int size3 = 3;
	private int size4 = 3;
	private int score1, score2, score3, score4;
	private int[] spawnLocations;
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	private static final Color WHITE = new Color(238, 239, 239);
	private GameSession gameSession;
	private Polygon p1Mouth, p2Mouth, p3Mouth, p4Mouth;
	private Timer clk, gameOverClk;
	private int mvInt = 5;
	private int player1Health = 10;
	private int player2Health = 10;
	private boolean gameover;
	private Font winFont;
	private TrueTypeFont winTTF;
	private double oldRadiusP1, oldRadiusP2, oldRadiusP3, oldRadiusP4,
			newRadiusP1, newRadiusP2, newRadiusP3, newRadiusP4;
	private String p1Result, p2Result, p3Result, p4Result;
	private boolean foodSet, locationsSet;
	private Timer foodClk;
	private float p1X, p1Y, p2X, p3X, p4X, p2Y, p3Y, p4Y;
	private int calc, num;
	private String res1, res2, res3, res4;
	private int newSize2, newSize1, oldSize1, oldSize2;
	private boolean dec1, dec2, dec3, dec4;
	private int oldSize3, newSize3, oldSize4, newSize4;
	private boolean msgSet;
	private URL powerUp = this.getClass().getClassLoader()
			.getResource("sounds/Powerup2.wav");
	private URL hitHurt = this.getClass().getClassLoader()
			.getResource("sounds/Hit_Hurt.wav");
	private URL playerDead = this.getClass().getClassLoader()
			.getResource("sounds/Explosion18.wav");
	private URL gameOverCountDown = this.getClass().getClassLoader()
			.getResource("sounds/Blip_Select.wav");
	private URL gameMusic = this.getClass().getClassLoader()
			.getResource("sounds/PacBattle.wav");
	private URL timedGameOver = this.getClass().getClassLoader()
			.getResource("sounds/timedGameOver.wav");
	private Clip musicClip;
	private boolean p1Dead, p2Dead, p3Dead, p4Dead, draw4, end;

	public Pacbattle(String title) {
		super(title);
	}

	public int getPlayer1Health() {
		return player1Health;
	}

	public void setPlayer1Health(int player1Health) {
		this.player1Health = player1Health;
	}

	public int getPlayer2Health() {
		return player2Health;
	}

	public void setPlayer2Health(int player2Health) {
		this.player2Health = player2Health;
	}

	private void movePlayerRight(Circle p12, Polygon pMouth, float position) {
		if (p12.getCenterX() + p12.getRadius() + position >= WIDTH) {
			if (position != 0 && gameover != true) {
				try {
					AudioInputStream audioIn = AudioSystem
							.getAudioInputStream(hitHurt);
					Clip clip = AudioSystem.getClip();
					clip.open(audioIn);
					clip.start();
				} catch (UnsupportedAudioFileException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (LineUnavailableException e1) {
					e1.printStackTrace();
				}
			}
			position = 0;
		}

		if (p12 == p1) {
			p1X = p12.getCenterX() + position;
		} else if (p12 == p2) {
			p2X = p12.getCenterX() + position;
		} else if (p12 == p3) {
			p3X = p12.getCenterX() + position;
		} else if (p12 == p4) {
			p4X = p12.getCenterX() + position;
		}
		if (position < 0 || position > 1) {
			return;
		}
		p12.setX(p12.getX() + position * 3);
		pMouth.setX(pMouth.getX() + position * 3);
	}

	private void movePlayerLeft(Circle p12, Polygon pMouth, float position) {
		if (p12.getCenterX() - p12.getRadius() - position <= 0) {
			if (position != 0 && gameover != true) {
				try {
					AudioInputStream audioIn = AudioSystem
							.getAudioInputStream(hitHurt);
					Clip clip = AudioSystem.getClip();
					clip.open(audioIn);
					clip.start();
				} catch (UnsupportedAudioFileException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (LineUnavailableException e1) {
					e1.printStackTrace();
				}
			}
			position = 0;
		}
		if (p12 == p1) {
			p1X = p12.getCenterX() - position;
		} else if (p12 == p2) {
			p2X = p12.getCenterX() - position;
		} else if (p12 == p3) {
			p3X = p12.getCenterX() - position;
		} else if (p12 == p4) {
			p4X = p12.getCenterX() - position;
		}

		if (position < 0 || position > 1) {
			return;
		}
		p12.setX(p12.getX() - position * 3);
		pMouth.setX(pMouth.getX() - position * 3);
	}

	private void movePlayerUp(Circle p12, Polygon pMouth, float position) {
		if (p12.getCenterY() - p12.getRadius() - position <= 0) {
			if (position != 0 && gameover != true) {
				try {
					AudioInputStream audioIn = AudioSystem
							.getAudioInputStream(hitHurt);
					Clip clip = AudioSystem.getClip();
					clip.open(audioIn);
					clip.start();
				} catch (UnsupportedAudioFileException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (LineUnavailableException e1) {
					e1.printStackTrace();
				}
			}
			position = 0;
		}
		if (p12 == p1) {
			p1Y = p12.getCenterY() - position;
		} else if (p12 == p2) {
			p2Y = p12.getCenterY() - position;
		} else if (p12 == p3) {
			p3Y = p12.getCenterY() - position;
		} else if (p12 == p4) {
			p4Y = p12.getCenterY() - position;
		}

		if (position < 0 || position > 1) {
			return;
		}
		p12.setY(p12.getY() - position * 3);
		pMouth.setY(pMouth.getY() - position * 3);
	}

	private void movePlayer(Circle player, Polygon pMouth, float position) {
		if (player.getCenterY() + player.getRadius() + position >= HEIGHT) {
			if (position != 0 && gameover != true) {
				try {
					AudioInputStream audioIn = AudioSystem
							.getAudioInputStream(hitHurt);
					Clip clip = AudioSystem.getClip();
					clip.open(audioIn);
					clip.start();
				} catch (UnsupportedAudioFileException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (LineUnavailableException e1) {
					e1.printStackTrace();
				}
			}
			position = 0;
		}
		if (player == p1) {
			p1Y = player.getCenterY() + position;
		} else if (player == p2) {
			p2Y = player.getCenterY() + position;
		} else if (player == p3) {
			p3Y = player.getCenterY() + position;
		} else if (player == p4) {
			p4Y = player.getCenterY() + position;
		}
		if (position < 0 || position > 1) {
			return;
		}
		player.setY(player.getY() + position * 3);
		pMouth.setY(pMouth.getY() + position * 3);
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		gameSession = Application.getGameSession();
		winFont = new Font(Font.MONOSPACED, Font.BOLD, 24);
		winTTF = new TrueTypeFont(winFont, true);
		gameTimeStart = System.currentTimeMillis();
		min = 60;

		try {
			AudioInputStream audioIn = AudioSystem
					.getAudioInputStream(gameMusic);
			musicClip = AudioSystem.getClip();
			musicClip.open(audioIn);
			musicClip.start();
		} catch (UnsupportedAudioFileException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (LineUnavailableException e1) {
			e1.printStackTrace();
		}

		if (locationsSet == false) {
			spawnLocations = new int[6];
			for (int i = 0; i < 3; i++) {
				spawnLocations[i] = (i + 1) * WIDTH / 4;
			}
			for (int i = 3; i < 6; i++) {
				spawnLocations[i] = (i - 2) * HEIGHT / 4;
			}
			locationsSet = true;
		}

		if (foodSet == false && locationsSet == true) {
			food1 = new Circle(spawnLocations[0], spawnLocations[3], 10);
			food2 = new Circle(spawnLocations[2], spawnLocations[3], 10);
			food3 = new Circle(spawnLocations[0], spawnLocations[5], 10);
			food4 = new Circle(spawnLocations[2], spawnLocations[5], 10);
			foodSet = true;
		}

		for (Player player : gameSession.getPlayersSet()) {

			if (i == 0 && !set) {
				p1 = new Circle(player.getX(), player.getY(), 40);
				p1Mouth = new Polygon();
				p1Mouth.addPoint(player.getX() + 40, player.getY() - 40);
				p1Mouth.addPoint(player.getX(), player.getY());
				p1Mouth.addPoint(player.getX() + 40, player.getY() + 40);
				set = true;
			} else if (i == 1 && !set2 && gameSession.isTwoP()) {
				p2 = new Circle(player.getX(), player.getY(), 40);
				p2Mouth = new Polygon();
				p2Mouth.addPoint(player.getX() + 40, player.getY() + 40);
				p2Mouth.addPoint(player.getX(), player.getY());
				p2Mouth.addPoint(player.getX() - 40, player.getY() + 40);
				set2 = true;
			} else if (i == 2 && !set3 && gameSession.isThreeP()) {
				p3 = new Circle(player.getX(), player.getY(), 40);
				p3Mouth = new Polygon();
				p3Mouth.addPoint(player.getX() - 40, player.getY() - 40);
				p3Mouth.addPoint(player.getX(), player.getY());
				p3Mouth.addPoint(player.getX() - 40, player.getY() + 40);
				set3 = true;
			} else if (i == 3 && !set4 && gameSession.isFourP()) {
				p4 = new Circle(player.getX(), player.getY(), 40);
				p4Mouth = new Polygon();
				p4Mouth.addPoint(player.getX() + 40, player.getY() - 40);
				p4Mouth.addPoint(player.getX(), player.getY());
				p4Mouth.addPoint(player.getX() - 40, player.getY() - 40);
				set4 = true;
			}

			i++;
		}
		p1X = 80;
		p1Y = 360;
		p2X = 640;
		p2Y = 80;
		p3X = 1200;
		p3Y = 360;
		p4X = 640;
		p4Y = 640;

		clk = new Timer(mvInt, this);
		clk.start();

		gameOverClk = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if ((min * 2 - (gameTimeEnd - gameTimeStart) / 1000) <= 10
						&& (min * 2 - (gameTimeEnd - gameTimeStart) / 1000) > 1
						&& gameover != true) {
					try {
						AudioInputStream audioIn = AudioSystem
								.getAudioInputStream(gameOverCountDown);
						Clip clip = AudioSystem.getClip();
						clip.open(audioIn);
						clip.start();
					} catch (UnsupportedAudioFileException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (LineUnavailableException e1) {
						e1.printStackTrace();
					}
				}
			}

		});
		gameOverClk.start();

		foodClk = new Timer(3000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (locationsSet && foodSet) {
					respawnFood();
				}
			}
		});
		foodClk.start();
	}

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		Input input = container.getInput();
		if (input.isKeyPressed(Input.KEY_Q)) {
			container.exit();
		}
	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		container.setShowFPS(false);
		if (gameover == false) {
			if (p1!=null) {
			eatFood(p1, p1X, p1Y, p1Mouth);
			}
			if (p2!=null) {
				eatFood(p2, p2X, p2Y, p2Mouth);
			}
			if (set3&&p3!=null) {
				eatFood(p3, p3X, p3Y, p3Mouth);
			}
			if (set4&&p4!=null) {
				eatFood(p4, p4X, p4Y, p4Mouth);
			}
		}
		if (!gameover) {
			if (set && foodSet) {
				g.setColor(Color.yellow);
				if (food1 != null) {
					g.fill(food1);
				}
				if (food2 != null) {
					g.fill(food2);
				}
				if (food3 != null) {
					g.fill(food3);
				}
				if (food4 != null) {
					g.fill(food4);
				}
			}
			if (set && size1 != 0) {

				g.setColor(Color.yellow);
				g.fill(p1);
				g.setColor(Color.black);
				g.fill(p1Mouth);

			}
			if (set2 && size2 != 0) {
				g.setColor(Color.yellow);
				g.fill(p2);
				g.setColor(Color.black);
				g.fill(p2Mouth);
			}
			if (set3 && size3 != 0) {
				g.setColor(Color.yellow);
				g.fill(p3);
				g.setColor(Color.black);
				g.fill(p3Mouth);
			}
			if (set4 && size4 != 0) {
				g.setColor(Color.yellow);
				g.fill(p4);
				g.setColor(Color.black);
				g.fill(p4Mouth);
			}

			gameTimeEnd = System.currentTimeMillis();
			if (gameTimeEnd - gameTimeStart >= 60000 * 2) {
				if (gameTimeEnd - gameTimeStart >= 60000 * 2 - 1) {
					try {
						AudioInputStream audioIn = AudioSystem
								.getAudioInputStream(timedGameOver);
						Clip clip = AudioSystem.getClip();
						clip.open(audioIn);
						clip.start();
					} catch (UnsupportedAudioFileException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (LineUnavailableException e1) {
						e1.printStackTrace();
					}
				}
				gameover = true;
			}
			if (set && size1 != 0) {
				g.setColor(Color.white);
				g.fillRect(p1.getCenterX() - 40,
						p1.getCenterY() - p1.getRadius() - 30, 80, 30);
				g.setColor(Color.red);
				g.fillRect(p1.getCenterX() - 40,
						p1.getCenterY() - p1.getRadius() - 10, size1 * 16, 10);
				g.setColor(Color.black);
				g.drawString("PLAYER 1", p1.getCenterX() - 40, p1.getCenterY()
						- p1.getRadius() - 30);
			}
			if (set2 && size2 != 0) {
				g.setColor(Color.white);
				g.fillRect(p2.getCenterX() - 40,
						p2.getCenterY() - p2.getRadius() - 30, 80, 30);
				g.setColor(Color.red);
				g.fillRect(p2.getCenterX() - 40,
						p2.getCenterY() - p2.getRadius() - 10, size2 * 16, 10);
				g.setColor(Color.black);
				g.drawString("PLAYER 2", p2.getCenterX() - 40, p2.getCenterY()
						- p2.getRadius() - 30);
			}
			if (set3 && size3 != 0) {
				g.setColor(Color.white);
				g.fillRect(p3.getCenterX() - 40,
						p3.getCenterY() - p3.getRadius() - 30, 80, 30);
				g.setColor(Color.red);
				g.fillRect(p3.getCenterX() - 40,
						p3.getCenterY() - p3.getRadius() - 10, size3 * 16, 10);
				g.setColor(Color.black);
				g.drawString("PLAYER 3", p3.getCenterX() - 40, p3.getCenterY()
						- p3.getRadius() - 30);
			}
			if (set4 && size4 != 0) {
				g.setColor(Color.white);
				g.fillRect(p4.getCenterX() - 40,
						p4.getCenterY() - p4.getRadius() - 30, 80, 30);
				g.setColor(Color.red);
				g.fillRect(p4.getCenterX() - 40,
						p4.getCenterY() - p4.getRadius() - 10, size4 * 16, 10);
				g.setColor(Color.black);
				g.drawString("PLAYER 4", p4.getCenterX() - 40, p4.getCenterY()
						- p4.getRadius() - 30);
			}

			g.setColor(Color.white);
			g.drawString("p1 score: " + score1, 10, 20);
			if (set2) {
				g.drawString("p2 score: " + score2, 1050, 20);
			}
			if (set4) {
				g.drawString("p3 score: " + score3, 1050, 680);
			}
			if (set3) {
				g.drawString("p4 score: " + score4, 10, 680);
			}

			g.setColor(Color.white);
			if ((min * 2 - (gameTimeEnd - gameTimeStart) / 1000) <= 10) {
				g.setColor(Color.red);
			}
			g.drawString("Seconds Remaining "
					+ (min * 2 - (gameTimeEnd - gameTimeStart) / 1000), 525, 20);
		} else {
			musicClip.stop();

			String message = "";

			if (msgSet) {
				message = "Minimum two players, and maximum four players!";
			}

			if (min - (gameTimeEnd - gameTimeStart) / 1000 <= 0) {
				res1 = "player 1: " + score1;
				res2 = " player 2: " + score2;
				res3 = " player 3: " + score3;
				res4 = " player 4: " + score4;

				if (!set) {
					res1 = "";
				}
				if (!set2) {
					res2 = "";
				}
				if (!set3) {
					res3 = "";
				}
				if (!set4) {
					res4 = "";
				}

				if ((score1 > score2 && score1 > score3 && score1 > score4)
						|| (set && !set2 && !set3 && !set4 && score1 == 0
								&& score2 == 0 && score3 == 0 && score4 == 0)) {
					message = "player 1 wins!" + " score: " + res1 + res2
							+ res3 + res4;
				} else if (score2 > score1 && score2 > score3
						&& score2 > score4) {
					message = "player 2 wins!" + " score: " + res1 + res2
							+ res3 + res4;
				} else if (score3 > score1 && score3 > score2
						&& score3 > score4) {
					message = "player 3 wins!" + " score: " + res1 + res2
							+ res3 + res4;
				} else if (score4 > score1 && score4 > score3
						&& score4 > score2) {
					message = "player 4 wins!" + " score: " + res1 + res2
							+ res3 + res4;
				} else if ((score1 == score2 && score2 == score3
						&& score3 == score4 && set && set2 && set3 && set4)
						|| (set && set2 && set3 && !set4 && score1 == score2 && score2 == score3)
						|| (set && set2 && !set3 && set4 && score1 == score2 && score2 == score4)
						|| (set && !set2 && set3 && set4 && score1 == score3 && score3 == score4)
						|| (!set && set2 && set3 && set4 && score3 == score2 && score2 == score4)
						|| (set
								&& set2
								&& !set3
								&& !set4
								&& ((score1 == score2) || (score1 == score3) || score1 == score4)
								|| (score2 == score3) || (score2 == score4) || (score3 == score4))) {
					message = "Draw, everybody wins!" + " score: " + res1
							+ res2 + res3 + res4;
				} else if (set
						&& set2
						&& set3
						&& set4
						&& ((score1 == score2 && score2 == score3)
								|| (score1 == score3 && score3 == score4)
								|| (score1 == score3 && score3 == score4) || (score2 == score4 && score4 == score3))) {
					message = "Draw, three winners!" + " score: " + res1 + res2
							+ res3 + res4;
				} else if ((set && set2 && set3 && !set4 && (score1 == score2)
						|| (score1 == score3) || (score2 == score3))
						|| (set && set2 && set3 && set4)
						&& ((score1 == score2) || (score1 == score3)
								|| (score1 == score4) || (score2 == score3)
								|| (score2 == score4) || (score3 == score4))) {
					message = "Draw, two winners!" + " score: " + res1 + res2
							+ res3 + res4;
				}
				end = true;
				if (msgSet) {
					message = "Minimum two players, and maximum four players!";
				}
			} else {
				if (set && set2 && set3 && set4) {
					if (p1Result == "") {
						p1Result = "First! ";
					} else if (p2Result == "") {
						p2Result = "First! ";
					} else if (p3Result == "") {
						p3Result = "First! ";
					} else if (p4Result == "") {
						p4Result = "First! ";
					}
					message = "Player 1: " + p1Result + " " + "Player 2: "
							+ p2Result + " " + "Player 3: " + p3Result + " "
							+ "Player 4: " + p4Result;
				} else if (set && set2 && set3 && !set4) {
					if (p1Result == "") {
						p1Result = "First! ";
					} else if (p2Result == "") {
						p2Result = "First! ";
					} else if (p3Result == "") {
						p3Result = "First! ";
					}
					message = "Player 1: " + p1Result + " " + "Player 2: "
							+ p2Result + " " + "Player 3: " + p3Result;
				} else if (set && set2 && !set3 && !set4) {
					if (p1Result == "") {
						p1Result = "First! ";
					} else if (p2Result == "") {
						p2Result = "First! ";
					}
					message = "Player 1: " + p1Result + " " + "Player 2: "
							+ p2Result;
				} else {
					message = "";
				}
				if (msgSet) {
					message = "Minimum two players, and maximum four players!";
				}

			}
			int messageWidth = winTTF.getWidth(message);
			int messageHeight = winTTF.getHeight(message);

			g.setFont(winTTF);
			g.setColor(WHITE);
			g.drawString(message, (WIDTH - messageWidth) / 2,
					(HEIGHT - messageHeight) / 2);

		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (set && !set2 && !set3 && !set4) {
			gameover = true;
			msgSet = true;
		}
		if (p1.getRadius() != newRadiusP1) {
			oldRadiusP1 = p1.getRadius();
		}
		if (p2.getRadius() != newRadiusP2) {
			oldRadiusP2 = p2.getRadius();
		}

		if (size1 != 0 && size2 != 0) {
			n = 1;
			adjustMouthColl(p1, p2, pl1Rot, pl2Rot, size1, size2, score1, score2);
			collisionDetection(p1, p2, p1Mouth, p2Mouth, pl1Rot, pl2Rot, size1,
					size2);
			newRadiusP1 = p1.getRadius();
			newRadiusP2 = p2.getRadius();
		}

		oldSize1 = size1;
		oldSize2 = size2;
		if (newRadiusP1 < oldRadiusP1 && inc == true) {
			if (size1 >= 1) {
				size1 -= 1;
			}
			if (score1 > 0) {
				score1 -= 1;
			}

			inc = false;
		} else if (newRadiusP1 > oldRadiusP1 && inc == true) {
			if (size1 <= 4) {
				size1 += 1;
				if (gameover != true) {
					try {
						AudioInputStream audioIn = AudioSystem
								.getAudioInputStream(powerUp);
						Clip clip = AudioSystem.getClip();
						clip.open(audioIn);
						clip.start();
					} catch (UnsupportedAudioFileException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (LineUnavailableException e1) {
						e1.printStackTrace();
					}
				}
			}
			inc = false;
		}
		if (newRadiusP2 < oldRadiusP2 && inc2 == true) {
			if (size2 >= 1) {
				size2 -= 1;
			}
			if (score2 > 0) {
				score2 -= 1;
			}

			inc2 = false;
		} else if (newRadiusP2 > oldRadiusP2 && inc2 == true) {
			if (size2 <= 4) {
				size2 += 1;
				if (gameover != true) {
					try {
						AudioInputStream audioIn = AudioSystem
								.getAudioInputStream(powerUp);
						Clip clip = AudioSystem.getClip();
						clip.open(audioIn);
						clip.start();
					} catch (UnsupportedAudioFileException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (LineUnavailableException e1) {
						e1.printStackTrace();
					}
				}
			}
			inc2 = false;
		}

		newSize1 = size1;
		newSize2 = size2;
		if (newSize1 != oldSize1 || newSize2 != oldSize2) {
			inc = false;
			inc2 = false;
		}
		if (set3) {
			if (p1.getRadius() != newRadiusP1) {
				oldRadiusP1 = p1.getRadius();
			}
			if (p3.getRadius() != newRadiusP3) {
				oldRadiusP3 = p3.getRadius();
			}
			if (size1 != 0 && size3 != 0) {
				n = 2;
				oldSize1 = size1;
				oldSize3 = size3;
				adjustMouthColl(p1, p3, pl1Rot, pl3Rot, size1, size3, score1,
						score3);
				collisionDetection(p1, p3, p1Mouth, p3Mouth, pl1Rot, pl3Rot,
						size1, size3);
				newRadiusP1 = p1.getRadius();
				newRadiusP3 = p3.getRadius();
			}
			if (newRadiusP1 < oldRadiusP1 && inc == true) {
				if (size1 >= 1) {
					size1 -= 1;
					if (score1 > 0) {
						score1 -= 1;
					}

				}
				inc = false;
			} else if (newRadiusP1 > oldRadiusP1 && inc == true) {
				if (size1 <= 4) {
					size1 += 1;
					if (gameover != true) {
						try {
							AudioInputStream audioIn = AudioSystem
									.getAudioInputStream(powerUp);
							Clip clip = AudioSystem.getClip();
							clip.open(audioIn);
							clip.start();
						} catch (UnsupportedAudioFileException e1) {
							e1.printStackTrace();
						} catch (IOException e1) {
							e1.printStackTrace();
						} catch (LineUnavailableException e1) {
							e1.printStackTrace();
						}
					}
				}
				inc = false;
			}
			if (newRadiusP3 < oldRadiusP3 && inc2 == true) {
				if (size3 >= 1) {
					size3 -= 1;
				}
				if (score3 > 0) {
					score3 -= 1;
				}

				inc2 = false;
			} else if (newRadiusP3 > oldRadiusP3 && inc2 == true) {
				if (size3 <= 4) {
					size3 += 1;
					if (gameover != true) {
						try {
							AudioInputStream audioIn = AudioSystem
									.getAudioInputStream(powerUp);
							Clip clip = AudioSystem.getClip();
							clip.open(audioIn);
							clip.start();
						} catch (UnsupportedAudioFileException e1) {
							e1.printStackTrace();
						} catch (IOException e1) {
							e1.printStackTrace();
						} catch (LineUnavailableException e1) {
							e1.printStackTrace();
						}
					}
				}
				inc2 = false;
			}
			if (newSize1 != oldSize1 || newSize3 != oldSize3) {
				inc = false;
				inc2 = false;
			}
			newSize1 = size1;
			newSize3 = size3;

			if (p2.getRadius() != newRadiusP2) {
				oldRadiusP2 = p2.getRadius();
			}
			if (p3.getRadius() != newRadiusP3) {
				oldRadiusP3 = p3.getRadius();
			}
			if (size2 != 0 && size3 != 0) {
				oldSize2 = size2;
				oldSize3 = size3;
				n = 3;
				adjustMouthColl(p2, p3, pl2Rot, pl3Rot, size2, size3, score2,
						score3);
				collisionDetection(p2, p3, p2Mouth, p3Mouth, pl2Rot, pl3Rot,
						size2, size3);
				newRadiusP3 = p3.getRadius();
				newRadiusP2 = p2.getRadius();
			}
		}
		if (newRadiusP2 < oldRadiusP2 && inc == true) {
			if (size2 >= 1) {
				size2 -= 1;
			}
			if (score2 > 0) {
				score2 -= 1;
			}

			inc = false;
		} else if (newRadiusP2 > oldRadiusP2 && inc == true) {
			if (size2 <= 4) {
				size2 += 1;
				if (gameover != true) {
					try {
						AudioInputStream audioIn = AudioSystem
								.getAudioInputStream(powerUp);
						Clip clip = AudioSystem.getClip();
						clip.open(audioIn);
						clip.start();
					} catch (UnsupportedAudioFileException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (LineUnavailableException e1) {
						e1.printStackTrace();
					}
				}
			}
			inc = false;
		}
		if ((newRadiusP3 < oldRadiusP3 && inc2 == true)) {
			if (size3 >= 1) {
				size3 -= 1;
			}
			if (score3 > 0) {
				score3 -= 1;
			}

			inc2 = false;
		} else if (newRadiusP3 > oldRadiusP3 && inc2 == true) {
			if (size3 <= 4) {
				size3 += 1;
				if (gameover != true) {
					try {
						AudioInputStream audioIn = AudioSystem
								.getAudioInputStream(powerUp);
						Clip clip = AudioSystem.getClip();
						clip.open(audioIn);
						clip.start();
					} catch (UnsupportedAudioFileException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (LineUnavailableException e1) {
						e1.printStackTrace();
					}
				}
			}
			inc2 = false;
		}
		newSize2 = size2;
		newSize3 = size3;
		if (newSize2 != oldSize2 || newSize3 != oldSize3) {
			inc = false;
			inc2 = false;
		}

		if (set3 && set4) {
			if (p3.getRadius() != newRadiusP3) {
				oldRadiusP3 = p3.getRadius();
			}
			if (p4.getRadius() != newRadiusP4) {
				oldRadiusP4 = p4.getRadius();
			}
			if (size3 != 0 && size4 != 0) {
				n = 4;
				oldSize3 = size3;
				oldSize4 = size4;
				adjustMouthColl(p3, p4, pl3Rot, pl4Rot, size3, size4, score3,
						score4);
				collisionDetection(p3, p4, p3Mouth, p4Mouth, pl3Rot, pl4Rot,
						size3, size4);
				newRadiusP3 = p3.getRadius();
				newRadiusP4 = p4.getRadius();
			}
			if (newRadiusP3 < oldRadiusP3 && inc == true) {
				if (size3 >= 1) {
					size3 -= 1;
				}
				if (score3 > 0) {
					score3 -= 1;
				}

				inc = false;
			} else if (newRadiusP3 > oldRadiusP3 && inc == true) {
				if (size3 <= 4) {
					size3 += 1;
					if (gameover != true) {
						try {
							AudioInputStream audioIn = AudioSystem
									.getAudioInputStream(powerUp);
							Clip clip = AudioSystem.getClip();
							clip.open(audioIn);
							clip.start();
						} catch (UnsupportedAudioFileException e1) {
							e1.printStackTrace();
						} catch (IOException e1) {
							e1.printStackTrace();
						} catch (LineUnavailableException e1) {
							e1.printStackTrace();
						}
					}
				}
				inc = false;
			}
			if (newRadiusP4 < oldRadiusP4 && inc2 == true) {
				if (size4 >= 1) {
					size4 -= 1;
				}
				if (score4 > 0) {
					score4 -= 1;
				}

				inc2 = false;
			} else if (newRadiusP4 > oldRadiusP4 && inc2 == true) {
				if (size4 <= 4) {
					size4 += 1;
					if (gameover != true) {
						try {
							AudioInputStream audioIn = AudioSystem
									.getAudioInputStream(powerUp);
							Clip clip = AudioSystem.getClip();
							clip.open(audioIn);
							clip.start();
						} catch (UnsupportedAudioFileException e1) {
							e1.printStackTrace();
						} catch (IOException e1) {
							e1.printStackTrace();
						} catch (LineUnavailableException e1) {
							e1.printStackTrace();
						}
					}
				}
				inc2 = false;
			}
		}
		newSize3 = size3;
		newSize4 = size4;
		if (newSize3 != oldSize3 || newSize4 != oldSize4) {
			inc = false;
			inc2 = false;
		}
		if (set4) {
			if (p2.getRadius() != newRadiusP2) {
				oldRadiusP2 = p2.getRadius();
			}
			if (p4.getRadius() != newRadiusP4) {
				oldRadiusP4 = p4.getRadius();
			}
			if (size2 != 0 && size4 != 0) {
				n = 5;
			}
			oldSize2 = size2;
			oldSize4 = size4;
			adjustMouthColl(p2, p4, pl2Rot, pl4Rot, size2, size4, score2, score4);
			collisionDetection(p2, p4, p2Mouth, p4Mouth, pl2Rot, pl4Rot, size2,
					size4);
			newRadiusP2 = p2.getRadius();
			newRadiusP4 = p4.getRadius();
			if (newRadiusP2 < oldRadiusP2 && inc == true) {
				if (size2 >= 1) {
					size2 -= 1;
				}
				if (score2 > 0) {
					score2 -= 1;
				}

				inc = false;
			} else if (newRadiusP2 > oldRadiusP2 && inc == true) {
				if (size2 <= 4) {
					size2 += 1;
					if (gameover != true) {
						try {
							AudioInputStream audioIn = AudioSystem
									.getAudioInputStream(powerUp);
							Clip clip = AudioSystem.getClip();
							clip.open(audioIn);
							clip.start();
						} catch (UnsupportedAudioFileException e1) {
							e1.printStackTrace();
						} catch (IOException e1) {
							e1.printStackTrace();
						} catch (LineUnavailableException e1) {
							e1.printStackTrace();
						}
					}
				}
				inc = false;
			}
			if (newRadiusP4 < oldRadiusP4 && inc2 == true) {
				if (size4 >= 1) {
					size4 -= 1;
				}
				if (score4 > 0) {
					score4 -= 1;
				}

				inc2 = false;
			} else if (newRadiusP4 > oldRadiusP4 && inc2 == true) {
				if (size4 <= 4) {
					size4 += 1;
					if (gameover != true) {
						try {
							AudioInputStream audioIn = AudioSystem
									.getAudioInputStream(powerUp);
							Clip clip = AudioSystem.getClip();
							clip.open(audioIn);
							clip.start();
						} catch (UnsupportedAudioFileException e1) {
							e1.printStackTrace();
						} catch (IOException e1) {
							e1.printStackTrace();
						} catch (LineUnavailableException e1) {
							e1.printStackTrace();
						}
					}
				}
				inc2 = false;
			}
			newSize2 = size2;
			newSize4 = size4;
			if (newSize2 != oldSize2 || newSize4 != oldSize4) {
				inc = false;
				inc2 = false;
			}

			if (p1.getRadius() != newRadiusP1) {
				oldRadiusP1 = p1.getRadius();
			}
			if (p4.getRadius() != newRadiusP4) {
				oldRadiusP4 = p4.getRadius();
			}

			if (size1 != 0 && size4 != 0) {
				n = 6;
				oldSize1 = size1;
				oldSize4 = size4;
				adjustMouthColl(p1, p4, pl1Rot, pl4Rot, size1, size4, score1,
						score4);
				collisionDetection(p1, p4, p1Mouth, p4Mouth, pl1Rot, pl4Rot,
						size1, size4);
				newRadiusP1 = p1.getRadius();
				newRadiusP4 = p4.getRadius();
			}
			if (newRadiusP1 < oldRadiusP1 && inc == true) {
				if (size1 >= 1) {
					size1 -= 1;
				}
				if (score1 > 0) {
					score1 -= 1;
				}

				inc = false;
			} else if (newRadiusP1 > oldRadiusP1 && inc == true) {
				if (size1 <= 4) {
					size1 += 1;
					if (gameover != true) {
						try {
							AudioInputStream audioIn = AudioSystem
									.getAudioInputStream(powerUp);
							Clip clip = AudioSystem.getClip();
							clip.open(audioIn);
							clip.start();
						} catch (UnsupportedAudioFileException e1) {
							e1.printStackTrace();
						} catch (IOException e1) {
							e1.printStackTrace();
						} catch (LineUnavailableException e1) {
							e1.printStackTrace();
						}
					}
				}
				inc = false;
			}
			if (newRadiusP4 < oldRadiusP4 && inc2 == true) {
				if (size4 >= 1) {
					size4 -= 1;
				}
				if (score4 > 0) {
					score4 -= 1;
				}

				inc2 = false;
			} else if (newRadiusP4 > oldRadiusP4 && inc2 == true) {
				if (size4 <= 4) {
					size4 += 1;
					if (gameover != true) {
						try {
							AudioInputStream audioIn = AudioSystem
									.getAudioInputStream(powerUp);
							Clip clip = AudioSystem.getClip();
							clip.open(audioIn);
							clip.start();
						} catch (UnsupportedAudioFileException e1) {
							e1.printStackTrace();
						} catch (IOException e1) {
							e1.printStackTrace();
						} catch (LineUnavailableException e1) {
							e1.printStackTrace();
						}
					}
				}
				inc2 = false;
			}
			newSize1 = size1;
			newSize4 = size4;
			if (newSize1 != oldSize1 || newSize4 != oldSize4) {
				inc = false;
				inc2 = false;
			}
		}

		if (size1 == 3) {
			j1 = 1;
			h = 1;
		}
		if (size2 == 3) {
			j2 = 1;
			h2 = 1;
		}
		if (size3 == 3) {
			j3 = 1;
			h3 = 1;
		}
		if (size4 == 3) {
			j4 = 1;
			h4 = 1;
		}
		if (size1 == 2) {
			j1 = (float) 0.66;
		} else if (size1 == 4) {
			j1 = (float) 1;
		} else if (size1 == 5) {
			j1 = (float) 1.5;
		} else if (size1 == 1) {
			j1 = (float) 0.6;
		} else {
			j1 = 1;
		}

		if (size2 == 2) {
			j2 = (float) 0.66;
		} else if (size2 == 4) {
			j2 = (float) 1;
		} else if (size2 == 5) {
			j2 = (float) 1.5;
		} else if (size2 == 1) {
			j2 = (float) 0.6;
		} else {
			j2 = 1;
		}

		if (size3 == 2) {
			j3 = (float) 0.66;
		} else if (size3 == 4) {
			j3 = (float) 1;
		} else if (size3 == 5) {
			j3 = (float) 1.5;
		} else if (size3 == 1) {
			j3 = (float) 0.6;
		} else {
			j3 = 1;
		}

		if (size4 == 2) {
			j4 = (float) 0.66;
		} else if (size4 == 4) {
			j4 = (float) 1;
		} else if (size4 == 5) {
			j4 = (float) 1.5;
		} else if (size4 == 1) {
			j4 = (float) 0.6;
		} else {
			j4 = 1;
		}

		if (size1 == 1) {
			h = (float) 0.66;
		} else if (size1 == 5 || size1 == 4) {
			h = (float) 1.5;
		} else {
			h = 1;
		}

		if (size2 == 1) {
			h2 = (float) 0.66;
		} else if (size2 == 5 || size2 == 4) {
			h2 = (float) 1.5;
		} else {
			h2 = 1;
		}
		if (size3 == 1) {
			h3 = (float) 0.66;
		} else if (size3 == 5 || size3 == 4) {
			h3 = (float) 1.5;
		} else {
			h3 = 1;
		}
		if (size4 == 1) {
			h4 = (float) 0.66;
		} else if (size4 == 5 || size4 == 4) {
			h4 = (float) 1.5;
		} else {
			h4 = 1;
		}
		if (size1 == 0 && p1Dead == false) {
			p1 = null;
			p1Mouth = null;
			if (gameover != true) {
				try {
					AudioInputStream audioIn = AudioSystem
							.getAudioInputStream(playerDead);
					Clip clip = AudioSystem.getClip();
					clip.open(audioIn);
					clip.start();
				} catch (UnsupportedAudioFileException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (LineUnavailableException e1) {
					e1.printStackTrace();
				}
			}
			p1Dead = true;
		} else if (size2 == 0 && p2Dead == false) {
			p2 = null;
			p2Mouth = null;
			if (gameover != true) {
				try {
					AudioInputStream audioIn = AudioSystem
							.getAudioInputStream(playerDead);
					Clip clip = AudioSystem.getClip();
					clip.open(audioIn);
					clip.start();
				} catch (UnsupportedAudioFileException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (LineUnavailableException e1) {
					e1.printStackTrace();
				}
			}
			p2Dead = true;
		} else if (size3 == 0 && p3Dead == false) {
			p3 = null;
			p3Mouth = null;
			if (gameover != true) {
				try {
					AudioInputStream audioIn = AudioSystem
							.getAudioInputStream(playerDead);
					Clip clip = AudioSystem.getClip();
					clip.open(audioIn);
					clip.start();
				} catch (UnsupportedAudioFileException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (LineUnavailableException e1) {
					e1.printStackTrace();
				}
			}
			p3Dead = true;
		} else if (size4 == 0 && p4Dead == false) {
			p4 = null;
			p4Mouth = null;
			if (gameover != true) {
				try {
					AudioInputStream audioIn = AudioSystem
							.getAudioInputStream(playerDead);
					Clip clip = AudioSystem.getClip();
					clip.open(audioIn);
					clip.start();
				} catch (UnsupportedAudioFileException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (LineUnavailableException e1) {
					e1.printStackTrace();
				}
			}
			p4Dead = true;
		}

		c = 1;
		calcResults(size1);
		c = 2;
		calcResults(size2);
		c = 3;
		calcResults(size3);
		c = 4;
		calcResults(size4);

		if (numDef == 3 && set && set2 && set3 && set4) {
			gameover = true;
		} else if (numDef == 2 && set && set2 && set3 && !set4) {
			gameover = true;
		} else if (numDef == 1 && set && set2 && !set3 && !set4) {
			gameover = true;
		}

		newScore1 = score1;
		newScore2 = score2;
		newScore3 = score3;
		newScore4 = score4;

	}

	public void setMouthRot(Circle p) {
		if (p == p1) {
			if (p.getRadius() > 50 && p.getRadius() < 70) {
				j1 = (float) 1.5;
				h = 1;
			} else if (p.getRadius() > 69 && p.getRadius() < 100) {
				j1 = (float) 1.5;
				h = (float) 1.5;
			} else if (p.getRadius() > 33 && p.getRadius() < 51) {
				j1 = 1;
				h = 1;
			} else if (p.getRadius() < 34 && p.getRadius() > 20) {
				j1 = (float) 0.66;
				h = 1;
			} else if (p.getRadius() > 0 && p.getRadius() < 21) {
				j1 = (float) 0.66;
				h = (float) 0.66;
			}
		} else if (p == p2) {
			if (p.getRadius() > 50 && p.getRadius() < 70) {
				j2 = (float) 1.5;
				h2 = 1;
			} else if (p.getRadius() > 69 && p.getRadius() < 100) {
				j2 = (float) 1.5;
				h2 = (float) 1.5;
			} else if (p.getRadius() > 33 && p.getRadius() < 51) {
				j2 = 1;
				h2 = 1;
			} else if (p.getRadius() < 34 && p.getRadius() > 20) {
				j2 = (float) 0.66;
				h2 = 1;
			} else if (p.getRadius() > 0 && p.getRadius() < 21) {
				j2 = (float) 0.66;
				h2 = (float) 0.66;
			}
		} else if (p == p3) {
			if (p.getRadius() > 50 && p.getRadius() < 70) {
				j3 = (float) 1.5;
				h3 = 1;
			} else if (p.getRadius() > 69 && p.getRadius() < 100) {
				j3 = (float) 1.5;
				h3 = (float) 1.5;
			} else if (p.getRadius() > 33 && p.getRadius() < 51) {
				j3 = 1;
				h3 = 1;
			} else if (p.getRadius() < 34 && p.getRadius() > 20) {
				j3 = (float) 0.66;
				h3 = 1;
			} else if (p.getRadius() > 0 && p.getRadius() < 21) {
				j3 = (float) 0.66;
				h3 = (float) 0.66;
			}
		} else if (p == p4) {
			if (p.getRadius() > 50 && p.getRadius() < 70) {
				j4 = (float) 1.5;
				h4 = 1;
			} else if (p.getRadius() > 69 && p.getRadius() < 100) {
				j4 = (float) 1.5;
				h4 = (float) 1.5;
			} else if (p.getRadius() > 33 && p.getRadius() < 51) {
				j4 = 1;
				h4 = 1;
			} else if (p.getRadius() < 34 && p.getRadius() > 20) {
				j4 = (float) 0.66;
				h4 = 1;
			} else if (p.getRadius() > 0 && p.getRadius() < 21) {
				j4 = (float) 0.66;
				h4 = (float) 0.66;
			}
		}
	}

	public void growMouth(Circle p, Polygon pMouth, String pRot, int size) {
		if (pRot == "right") {
			if (size == 2) {
				pMouth.addPoint((float) (p.getCenterX() + 29 + 8),
						(float) (p.getCenterY() - 29 + 8));
				pMouth.addPoint((float) (p.getCenterX()) + 8 - 4,
						(float) (p.getCenterY() + 8));
				pMouth.addPoint((float) (p.getCenterX() + 29 + 8),
						(float) (p.getCenterY() + 29 + 8));
			} else if (size == 3) {
				pMouth.addPoint(p.getCenterX() + 45 + 15,
						p.getCenterY() - 45 + 15);
				pMouth.addPoint(p.getCenterX() + 15 - 5, p.getCenterY() + 15);
				pMouth.addPoint(p.getCenterX() + 45 + 15,
						p.getCenterY() + 45 + 15);
			}
			if (size == 4) {
				pMouth.addPoint((float) (p.getCenterX() + 20 + 40 * 1.5),
						(float) (p.getCenterY() + 20 - 40 * 1.5));
				pMouth.addPoint(p.getCenterX() + 20, p.getCenterY() + 20);
				pMouth.addPoint((float) (p.getCenterX() + 20 + 40 * 1.5),
						(float) (p.getCenterY() + 20 + 40 * 1.5));
			} else if (size == 5) {
				pMouth.addPoint(
						(float) (p.getCenterX() + 1.5 * (20 + 40 * 1.5)),
						(float) (p.getCenterY() - 10 + 20 * 2 - 40 * 1.5 * 1.5));
				pMouth.addPoint(p.getCenterX() + 30, p.getCenterY() + 30);
				pMouth.addPoint(
						(float) (p.getCenterX() + 1.5 * (20 + 40 * 1.5)),
						(float) (p.getCenterY() - 10 + 2 * 20 + 40 * 1.5 * 1.5));
			}
		} else if (pRot == "left") {
			if (size == 2) {
				pMouth.addPoint((float) (p.getCenterX() - 29 + 8),
						(float) (p.getCenterY() - 29 + 8));
				pMouth.addPoint((float) (p.getCenterX() + 8 + 4),
						(float) (p.getCenterY() + 8));
				pMouth.addPoint((float) (p.getCenterX() - 29 + 8),
						(float) (p.getCenterY() + 29 + 8));
			} else if (size == 3) {
				pMouth.addPoint(p.getCenterX() - 45 + 15,
						p.getCenterY() - 45 + 15);
				pMouth.addPoint(p.getCenterX() + 15 + 5, p.getCenterY() + 15);
				pMouth.addPoint(p.getCenterX() - 45 + 15,
						p.getCenterY() + 45 + 15);
			}
			if (size == 4) {
				pMouth.addPoint((float) (p.getCenterX() + 20 - 40 * 1.5),
						(float) (p.getCenterY() + 20 - 40 * 1.5));
				pMouth.addPoint(p.getCenterX() + 20, p.getCenterY() + 20);
				pMouth.addPoint((float) (p.getCenterX() + 20 - 40 * 1.5),
						(float) (p.getCenterY() + 20 + 40 * 1.5));
			} else if (size == 5) {
				pMouth.addPoint(
						(float) (p.getCenterX() + 1.5 * (20 - 40 * 1.5)),
						(float) (p.getCenterY() - 10 + 20 * 2 - 40 * 1.5 * 1.5));
				pMouth.addPoint(p.getCenterX() + 30, p.getCenterY() + 30);
				pMouth.addPoint(
						(float) (p.getCenterX() + 1.5 * (20 - 40 * 1.5)),
						(float) (p.getCenterY() - 10 + 2 * 20 + 40 * 1.5 * 1.5));
			}
		} else if (pRot == "down") {
			if (size == 2) {
				pMouth.addPoint((float) (p.getCenterX() + 29 + 8),
						(float) (p.getCenterY() + 29 + 8));
				pMouth.addPoint(p.getCenterX() + 8, p.getCenterY() + 8 - 4);
				pMouth.addPoint((float) (p.getCenterX() - 29 + 8),
						(float) (p.getCenterY() + 29 + 8));
			} else if (size == 3) {
				pMouth.addPoint(p.getCenterX() + 45 + 15,
						p.getCenterY() + 45 + 15);
				pMouth.addPoint(p.getCenterX() + 15, p.getCenterY() + 15 - 5);
				pMouth.addPoint(p.getCenterX() - 45 + 15,
						p.getCenterY() + 45 + 15);
			}
			if (size == 4) {
				pMouth.addPoint((float) (p.getCenterX() + 20 + 40 * 1.5),
						(float) (p.getCenterY() + 22 + 40 * 1.5));
				pMouth.addPoint(p.getCenterX() + 20, p.getCenterY() + 20);
				pMouth.addPoint((float) (p.getCenterX() + 20 - 40 * 1.5),
						(float) (p.getCenterY() + 22 + 40 * 1.5));
			} else if (size == 5) {
				pMouth.addPoint(
						(float) (p.getCenterX() + 1.5 * (20 + 40 * 1.5)),
						(float) (p.getCenterY() - 10 + 20 * 2 + 40 * 1.5 * 1.5));
				pMouth.addPoint(p.getCenterX() + 30, p.getCenterY() + 30);
				pMouth.addPoint(
						(float) (p.getCenterX() + 1.5 * (20 - 40 * 1.5)),
						(float) (p.getCenterY() - 10 + 2 * 20 + 40 * 1.5 * 1.5));

			}
		} else if (pRot == "up") {
			if (size == 2) {
				pMouth.addPoint((float) (p.getCenterX() + 29 + 8),
						(float) (p.getCenterY() - 29 + 8));
				pMouth.addPoint(p.getCenterX() + 8, p.getCenterY() + 8 + 4);
				pMouth.addPoint((float) (p.getCenterX() - 29 + 8),
						(float) (p.getCenterY() - 29 + 8));
			} else if (size == 3) {
				pMouth.addPoint(p.getCenterX() + 45 + 15,
						p.getCenterY() - 45 + 15);
				pMouth.addPoint(p.getCenterX() + 15, p.getCenterY() + 15 + 5);
				pMouth.addPoint(p.getCenterX() - 45 + 15,
						p.getCenterY() - 45 + 15);
			} else if (size == 4) {
				pMouth.addPoint((float) (p.getCenterX() + 20 + 40 * 1.5),
						(float) (p.getCenterY() - (8 * 8 / 9) * 2 - 40 / 1.5));
				pMouth.addPoint(p.getCenterX() + 20, p.getCenterY() + 20);
				pMouth.addPoint((float) (p.getCenterX() + 20 - 40 * 1.5),
						(float) (p.getCenterY() - (8 * 8 / 9) * 2 - 40 / 1.5));
			} else if (size == 5) {
				pMouth.addPoint(
						(float) (p.getCenterX() + 1.5 * (20 + 40 * 1.5)),
						(float) (p.getCenterY() + 30 - 40 * 1.5 * 1.5));
				pMouth.addPoint(p.getCenterX() + 30, p.getCenterY() + 30);
				pMouth.addPoint(
						(float) (p.getCenterX() + 1.5 * (20 - 40 * 1.5)),
						(float) (p.getCenterY() + 30 - 40 * 1.5 * 1.5));

			}
		}
	}

	public void eatFood(Circle p, float px, float py, Polygon p1Mouth2) {
		int size = 0;
		if (p.getRadius() < 21 && p.getRadius() > 0) {
			size = 1;
		} else if (p.getRadius() < 31 && p.getRadius() > 20) {
			size = 2;
		} else if (p.getRadius() > 30 && p.getRadius() < 50) {
			size = 3;
		} else if (p.getRadius() > 49 && p.getRadius() < 80) {
			size = 4;
		} else if (p.getRadius() > 79 && p.getRadius() < 100) {
			size = 5;
		}

		for (int i = (int) (px - p.getRadius()); i <= px + p.getRadius(); i++) {
			for (int j = (int) (py - p.getRadius()); j <= py + p.getRadius(); j++) {
				if (food1 != null) {
					if (i <= food1.getX() + 10 && i >= food1.getX() - 10
							&& j <= food1.getY() + 10 && j >= food1.getY() - 10) {
						food1 = null;
						if (size <= 4) {
							size += 1;
							if (p == p1) {
								p1Mouth = new Polygon();
								growMouth(p1, p1Mouth, pl1Rot, size);
							} else if (p == p2) {
								p2Mouth = new Polygon();
								growMouth(p2, p2Mouth, pl2Rot, size);
							} else if (p == p3) {
								p3Mouth = new Polygon();
								growMouth(p3, p3Mouth, pl3Rot, size);
							} else if (p == p4) {
								p4Mouth = new Polygon();
								growMouth(p4, p4Mouth, pl4Rot, size);
							}
							p.setRadius((float) (p.getRadius() * 1.5));
							if (gameover != true) {
								try {
									AudioInputStream audioIn = AudioSystem
											.getAudioInputStream(powerUp);
									Clip clip = AudioSystem.getClip();
									clip.open(audioIn);
									clip.start();
								} catch (UnsupportedAudioFileException e) {
									e.printStackTrace();
								} catch (IOException e) {
									e.printStackTrace();
								} catch (LineUnavailableException e) {
									e.printStackTrace();
								}
							}

							float diffX = p.getX() - p1Mouth2.getX();
							if (p.getX() < 0) {
								p.setX(20);
								p1Mouth2.setX(p.getX() - diffX);
							}

							float diffX2 = p.getX() - p1Mouth2.getX();
							if (p.getX() > WIDTH - 2 * p.getRadius()) {
								p.setX(WIDTH - 2 * p.getRadius());
								p1Mouth2.setX(p.getX() - diffX2);
							}

							float diffY = p.getY() - p1Mouth2.getY();
							if (p.getY() < 0) {
								p.setY(20);
								p1Mouth2.setY(p.getY() - diffY);
							}

							float diffY2 = p.getY() - p1Mouth2.getY();
							if (p.getY() > HEIGHT - 2 * p.getRadius()) {
								p.setY(HEIGHT - 2 * p.getRadius());
								p1Mouth2.setY(p.getY() - diffY2);
							}

						}
						if (p == p1) {
							score1 += 1;
						} else if (p == p2) {
							score2 += 1;
						} else if (p == p3) {
							score3 += 1;
						} else if (p == p4) {
							score4 += 1;
						}
					}
				}
				if (food2 != null) {
					if (i <= food2.getX() + 10 && i >= food2.getX() - 10
							&& j <= food2.getY() + 10 && j >= food2.getY() - 10) {
						food2 = null;
						if (size <= 4) {
							size += 1;
							if (p == p1) {
								p1Mouth = new Polygon();
								growMouth(p1, p1Mouth, pl1Rot, size);
							} else if (p == p2) {
								p2Mouth = new Polygon();
								growMouth(p2, p2Mouth, pl2Rot, size);
							} else if (p == p3) {
								p3Mouth = new Polygon();
								growMouth(p3, p3Mouth, pl3Rot, size);
							} else if (p == p4) {
								p4Mouth = new Polygon();
								growMouth(p4, p4Mouth, pl4Rot, size);
							}
							p.setRadius((float) (p.getRadius() * 1.5));
							if (gameover != true) {
								try {
									AudioInputStream audioIn = AudioSystem
											.getAudioInputStream(powerUp);
									Clip clip = AudioSystem.getClip();
									clip.open(audioIn);
									clip.start();
								} catch (UnsupportedAudioFileException e) {
									e.printStackTrace();
								} catch (IOException e) {
									e.printStackTrace();
								} catch (LineUnavailableException e) {
									e.printStackTrace();
								}
							}

							float diffX = p.getX() - p1Mouth2.getX();
							if (p.getX() < 0) {
								p.setX(20);
								p1Mouth2.setX(p.getX() - diffX);
							}

							float diffX2 = p.getX() - p1Mouth2.getX();
							if (p.getX() > WIDTH - 2 * p.getRadius()) {
								p.setX(WIDTH - 2 * p.getRadius());
								p1Mouth2.setX(p.getX() - diffX2);
							}

							float diffY = p.getY() - p1Mouth2.getY();
							if (p.getY() < 0) {
								p.setY(20);
								p1Mouth2.setY(p.getY() - diffY);
							}

							float diffY2 = p.getY() - p1Mouth2.getY();
							if (p.getY() > HEIGHT - 2 * p.getRadius()) {
								p.setY(HEIGHT - 2 * p.getRadius());
								p1Mouth2.setY(p.getY() - diffY2);
							}
						}
						if (p == p1) {
							score1 += 1;
						} else if (p == p2) {
							score2 += 1;
						} else if (p == p3) {
							score3 += 1;
						} else if (p == p4) {
							score4 += 1;
						}
					}
				}
				if (food3 != null) {
					if (i <= food3.getX() + 10 && i >= food3.getX() - 10
							&& j <= food3.getY() + 10 && j >= food3.getY() - 10) {
						food3 = null;
						if (size <= 4) {
							size += 1;
							if (p == p1) {
								p1Mouth = new Polygon();
								growMouth(p1, p1Mouth, pl1Rot, size);
							} else if (p == p2) {
								p2Mouth = new Polygon();
								growMouth(p2, p2Mouth, pl2Rot, size);
							} else if (p == p3) {
								p3Mouth = new Polygon();
								growMouth(p3, p3Mouth, pl3Rot, size);
							} else if (p == p4) {
								p4Mouth = new Polygon();
								growMouth(p4, p4Mouth, pl4Rot, size);
							}
							p.setRadius((float) (p.getRadius() * 1.5));
							if (gameover != true) {
								try {
									AudioInputStream audioIn = AudioSystem
											.getAudioInputStream(powerUp);
									Clip clip = AudioSystem.getClip();
									clip.open(audioIn);
									clip.start();
								} catch (UnsupportedAudioFileException e) {
									e.printStackTrace();
								} catch (IOException e) {
									e.printStackTrace();
								} catch (LineUnavailableException e) {
									e.printStackTrace();
								}
							}

							float diffX = p.getX() - p1Mouth2.getX();
							if (p.getX() < 0) {
								p.setX(20);
								p1Mouth2.setX(p.getX() - diffX);
							}

							float diffX2 = p.getX() - p1Mouth2.getX();
							if (p.getX() > WIDTH - 2 * p.getRadius()) {
								p.setX(WIDTH - 2 * p.getRadius());
								p1Mouth2.setX(p.getX() - diffX2);
							}

							float diffY = p.getY() - p1Mouth2.getY();
							if (p.getY() < 0) {
								p.setY(20);
								p1Mouth2.setY(p.getY() - diffY);
							}

							float diffY2 = p.getY() - p1Mouth2.getY();
							if (p.getY() > HEIGHT - 2 * p.getRadius()) {
								p.setY(HEIGHT - 2 * p.getRadius());
								p1Mouth2.setY(p.getY() - diffY2);
							}
						}
						if (p == p1) {
							score1 += 1;
						} else if (p == p2) {
							score2 += 1;
						} else if (p == p3) {
							score3 += 1;
						} else if (p == p4) {
							score4 += 1;
						}
					}
				}
				if (food4 != null) {
					if (i <= food4.getX() + 10 && i >= food4.getX() - 10
							&& j <= food4.getY() + 10 && j >= food4.getY() - 10) {
						food4 = null;
						if (size <= 4) {
							size += 1;
							if (p == p1) {
								p1Mouth = new Polygon();
								growMouth(p1, p1Mouth, pl1Rot, size);
							} else if (p == p2) {
								p2Mouth = new Polygon();
								growMouth(p2, p2Mouth, pl2Rot, size);
							} else if (p == p3) {
								p3Mouth = new Polygon();
								growMouth(p3, p3Mouth, pl3Rot, size);
							} else if (p == p4) {
								p4Mouth = new Polygon();
								growMouth(p4, p4Mouth, pl4Rot, size);
							}
							p.setRadius((float) (p.getRadius() * 1.5));
							if (gameover != true) {
								try {
									AudioInputStream audioIn = AudioSystem
											.getAudioInputStream(powerUp);
									Clip clip = AudioSystem.getClip();
									clip.open(audioIn);
									clip.start();
								} catch (UnsupportedAudioFileException e) {
									e.printStackTrace();
								} catch (IOException e) {
									e.printStackTrace();
								} catch (LineUnavailableException e) {
									e.printStackTrace();
								}
							}

							float diffX = p.getX() - p1Mouth2.getX();
							if (p.getX() < 0) {
								p.setX(20);
								p1Mouth2.setX(p.getX() - diffX);
							}

							float diffX2 = p.getX() - p1Mouth2.getX();
							if (p.getX() > WIDTH - 2 * p.getRadius()) {
								p.setX(WIDTH - 2 * p.getRadius());
								p1Mouth2.setX(p.getX() - diffX2);
							}

							float diffY = p.getY() - p1Mouth2.getY();
							if (p.getY() < 0) {
								p.setY(20);
								p1Mouth2.setY(p.getY() - diffY);
							}

							float diffY2 = p.getY() - p1Mouth2.getY();
							if (p.getY() > HEIGHT - 2 * p.getRadius()) {
								p.setY(HEIGHT - 2 * p.getRadius());
								p1Mouth2.setY(p.getY() - diffY2);
							}
						}
						if (p == p1) {
							score1 += 1;
						} else if (p == p2) {
							score2 += 1;
						} else if (p == p3) {
							score3 += 1;
						} else if (p == p4) {
							score4 += 1;
						}
					}
				}
			}
		}
		if (p == p1) {
			size1 = size;
		} else if (p == p2) {
			size2 = size;
		} else if (p == p3) {
			size3 = size;
		} else if (p == p4) {
			size4 = size;
		}
	}

	public void adjustMouthColl(Circle pl1, Circle pl2, String pl1Rot,
			String pl2Rot, int sizeOne, int sizeTwo, int scoreOne, int scoreTwo) {
		Polygon player1Mouth;
		Polygon player2Mouth;
		player1Mouth = null;
		player2Mouth = null;

		if (pl1.intersects(pl2) && gameover != true) {
			try {
				AudioInputStream audioIn = AudioSystem
						.getAudioInputStream(hitHurt);
				Clip clip = AudioSystem.getClip();
				clip.open(audioIn);
				clip.start();
			} catch (UnsupportedAudioFileException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (LineUnavailableException e1) {
				e1.printStackTrace();
			}
		}

		if (pl1.intersects(pl2) && pl1Rot == "right" && pl1.getX() < pl2.getX()
				&& pl1.getRadius() >= pl2.getRadius() - pl2.getRadius() / 10
				&& (pl2Rot == "down" || pl2Rot == "right" || pl2Rot == "up")) {
			if (!(pl2.getX() > pl1.getX() && pl2.getY() > pl1.getY()
					&& pl2Rot == "up" && pl1Rot == "right")
					&& !(pl1.getX() < pl2.getX() && pl1.getY() > pl2.getY()
							&& pl1Rot == "right" && pl2Rot == "down")
					&& !(pl1.getX() < pl2.getX() && pl1.getY() < pl2.getY()
							&& pl1Rot == "right" && pl2Rot == "up")) {
				inc = true;
				inc2 = true;
				scoreOne += 1;
				player1Mouth = new Polygon();
				if (sizeOne == 1) {
					player1Mouth
							.addPoint(
									(float) (pl1.getCenterX() + 1 - (8 * 8 / 9)
											* 2 + 40 / 1.5),
									(float) (pl1.getCenterY() - (8 * 8 / 9) * 2 + 40 / 1.5));
					player1Mouth.addPoint(
							(float) (pl1.getCenterX() + 1 - (8 * 8 / 9) * 2),
							(float) (pl1.getCenterY() - (8 * 8 / 9) * 2));
					player1Mouth
							.addPoint(
									(float) (pl1.getCenterX() + 1 - (8 * 8 / 9)
											* 2 + 40 / 1.5),
									(float) (pl1.getCenterY() - (8 * 8 / 9) * 2 - 40 / 1.5));
				} else if (sizeOne == 2) {
					player1Mouth.addPoint(pl1.getCenterX() + 40,
							pl1.getCenterY() - 40);
					player1Mouth.addPoint(pl1.getCenterX(), pl1.getCenterY());
					player1Mouth.addPoint(pl1.getCenterX() + 40,
							pl1.getCenterY() + 40);
				}
				if (sizeOne == 3) {
					player1Mouth.addPoint(
							(float) (pl1.getCenterX() + 20 + 40 * 1.5),
							(float) (pl1.getCenterY() + 20 - 40 * 1.5));
					player1Mouth.addPoint(pl1.getCenterX() + 20,
							pl1.getCenterY() + 20);
					player1Mouth.addPoint(
							(float) (pl1.getCenterX() + 20 + 40 * 1.5),
							(float) (pl1.getCenterY() + 20 + 40 * 1.5));
				} else if (sizeOne == 4) {
					player1Mouth
							.addPoint(
									(float) (pl1.getCenterX() + 1.5 * (20 + 40 * 1.5)),
									(float) (pl1.getCenterY() - 10 + 20 * 2 - 40 * 1.5 * 1.5));
					player1Mouth.addPoint(pl1.getCenterX() + 20,
							pl1.getCenterY() + 30);
					player1Mouth
							.addPoint(
									(float) (pl1.getCenterX() + 1.5 * (20 + 40 * 1.5)),
									(float) (pl1.getCenterY() - 10 + 2 * 20 + 40 * 1.5 * 1.5));
				} else if (sizeOne == 5) {
					player1Mouth
							.addPoint(
									(float) (pl1.getCenterX() - 20 + 1.5 * (20 + 40 * 1.5)),
									(float) (pl1.getCenterY() - 40 + 20 * 2 - 40 * 1.5 * 1.5));
					player1Mouth.addPoint(pl1.getCenterX(), pl1.getCenterY());
					player1Mouth
							.addPoint(
									(float) (pl1.getCenterX() - 20 + 1.5 * (20 + 40 * 1.5)),
									(float) (pl1.getCenterY() - 40 + 2 * 20 + 40 * 1.5 * 1.5));
				}

				if (sizeTwo == 1 || sizeTwo == 2) {
					player2Mouth = new Polygon();
					if (pl2Rot == "down") {
						player2Mouth.addPoint(
								(float) (pl2.getCenterX() + 20 - 9),
								(float) (pl2.getCenterY() + 20 - 9));
						player2Mouth.addPoint((float) (pl2.getCenterX() - 9),
								(float) (pl2.getCenterY() - 13));
						player2Mouth.addPoint(
								(float) (pl2.getCenterX() - 20 - 9),
								(float) (pl2.getCenterY() + 20 - 9));
					} else if (pl2Rot == "right") {
						player2Mouth.addPoint(
								(float) (pl2.getCenterX() + 20 - 9),
								(float) (pl2.getCenterY() - 20 - 9));
						player2Mouth.addPoint((float) (pl2.getCenterX() - 13),
								(float) (pl2.getCenterY() - 9));
						player2Mouth.addPoint(
								(float) (pl2.getCenterX() + 20 - 9),
								(float) (pl2.getCenterY() + 20 - 9));
					} else if (pl2Rot == "up") {
						player2Mouth.addPoint(
								(float) (pl2.getCenterX() + 20 - 9),
								(float) (pl2.getCenterY() - 20 - 9));
						player2Mouth.addPoint((float) (pl2.getCenterX() - 9),
								(float) (pl2.getCenterY() - 5));
						player2Mouth.addPoint(
								(float) (pl2.getCenterX() - 20 - 9),
								(float) (pl2.getCenterY() - 20 - 9));
					}
				} else if (sizeTwo == 3) {
					player2Mouth = new Polygon();
					if (pl2Rot == "down") {
						player2Mouth.addPoint(
								(float) (pl2.getCenterX() + 29 - 14),
								(float) (pl2.getCenterY() + 29 - 14));
						player2Mouth.addPoint((float) (pl2.getCenterX() - 14),
								(float) (pl2.getCenterY() - 22));
						player2Mouth.addPoint(
								(float) (pl2.getCenterX() - 29 - 14),
								(float) (pl2.getCenterY() + 29 - 14));
					} else if (pl2Rot == "right") {
						player2Mouth.addPoint(
								(float) (pl2.getCenterX() + 29 - 14),
								(float) (pl2.getCenterY() - 29 - 14));
						player2Mouth.addPoint((float) (pl2.getCenterX() - 22),
								(float) (pl2.getCenterY() - 14));
						player2Mouth.addPoint(
								(float) (pl2.getCenterX() + 29 - 14),
								(float) (pl2.getCenterY() + 29 - 14));
					} else if (pl2Rot == "up") {
						player2Mouth.addPoint(
								(float) (pl2.getCenterX() + 29 - 14),
								(float) (pl2.getCenterY() - 29 - 14));
						player2Mouth.addPoint((float) (pl2.getCenterX() - 14),
								(float) (pl2.getCenterY() - 6));
						player2Mouth.addPoint(
								(float) (pl2.getCenterX() - 29 - 14),
								(float) (pl2.getCenterY() - 29 - 14));
					}
				} else if (sizeTwo == 4) {
					if (pl2Rot == "right") {
						player2Mouth = new Polygon();
						player2Mouth.addPoint(pl2.getCenterX() + 20,
								pl2.getCenterY() - 60);
						player2Mouth.addPoint(pl2.getCenterX() - 20,
								pl2.getCenterY() - 20);
						player2Mouth.addPoint(pl2.getCenterX() + 20,
								pl2.getCenterY() + 20);
					} else if (pl2Rot == "up") {
						player2Mouth = new Polygon();
						player2Mouth.addPoint(pl2.getCenterX() + 20,
								pl2.getCenterY() - 60);
						player2Mouth.addPoint(pl2.getCenterX() - 20,
								pl2.getCenterY() - 20);
						player2Mouth.addPoint(pl2.getCenterX() - 60,
								pl2.getCenterY() - 60);
					} else if (pl2Rot == "down") {
						player2Mouth = new Polygon();
						player2Mouth.addPoint(pl2.getCenterX() + 20,
								pl2.getCenterY() + 20);
						player2Mouth.addPoint(pl2.getCenterX() - 20,
								pl2.getCenterY() - 20);
						player2Mouth.addPoint(pl2.getCenterX() - 60,
								pl2.getCenterY() + 20);
					}

				} else if (sizeTwo == 5) {
					if (pl2 == p1) {
						dec1 = true;
					} else if (pl2 == p2) {
						dec2 = true;
					} else if (pl2 == p3) {
						dec3 = true;
					} else if (pl2 == p4) {
						dec4 = true;
					}
					player2Mouth = new Polygon();
					if (pl2Rot == "right") {
						player2Mouth.addPoint(
								(float) (pl2.getCenterX() - 30 + 40 * 1.5),
								(float) (pl2.getCenterY() - 30 - 40 * 1.5));
						player2Mouth.addPoint(pl2.getCenterX() - 30,
								pl2.getCenterY() - 30);
						player2Mouth.addPoint(
								(float) (pl2.getCenterX() - 30 + 40 * 1.5),
								(float) (pl2.getCenterY() - 30 + 40 * 1.5));
					} else if (pl2Rot == "up") {
						player2Mouth.addPoint(
								(float) (pl2.getCenterX() - 30 + 40 * 1.5),
								(float) (pl2.getCenterY() - 50 - (8 * 8 / 9)
										* 2 - 40 / 1.5));
						player2Mouth.addPoint(pl2.getCenterX() - 30,
								pl2.getCenterY() - 30);
						player2Mouth.addPoint(
								(float) (pl2.getCenterX() - 30 - 40 * 1.5),
								(float) (pl2.getCenterY() - 50 - (8 * 8 / 9)
										* 2 - 40 / 1.5));
					} else if (pl2Rot == "down") {
						player2Mouth.addPoint(
								(float) (pl2.getCenterX() - 30 + 40 * 1.5),
								(float) (pl2.getCenterY() - 30 + 40 * 1.5));
						player2Mouth.addPoint(pl2.getCenterX() - 30,
								pl2.getCenterY() - 30);
						player2Mouth.addPoint(
								(float) (pl2.getCenterX() - 30 - 40 * 1.5),
								(float) (pl2.getCenterY() - 30 + 40 * 1.5));
					}
				}
			}
		} else if (pl1.intersects(pl2) && pl2.getX() > pl1.getX()
				&& pl2Rot == "left"
				&& pl2.getRadius() >= pl1.getRadius() - pl1.getRadius() / 10
				&& (pl1Rot == "down" || pl1Rot == "left" || pl1Rot == "up")) {
			if (!(pl1.getX() < pl2.getX() && pl1.getY() > pl2.getY()
					&& pl1Rot == "up" && pl2Rot == "left")
					&& !(pl1.getX() < pl2.getX() && pl1.getY() < pl2.getY()
							&& pl1Rot == "down" && pl2Rot == "left")) {
				inc = true;
				inc2 = true;
				scoreTwo += 1;
				if (sizeOne == 1 || sizeOne == 2) {
					player1Mouth = new Polygon();
					if (pl1Rot == "down") {
						player1Mouth.addPoint(
								(float) (pl1.getCenterX() + 20 - 9),
								(float) (pl1.getCenterY() + 20 - 9));
						player1Mouth.addPoint((float) (pl1.getCenterX() - 9),
								(float) (pl1.getCenterY() - 13));
						player1Mouth.addPoint(
								(float) (pl1.getCenterX() - 20 - 9),
								(float) (pl1.getCenterY() + 20 - 9));
					} else if (pl1Rot == "left") {
						player1Mouth.addPoint(
								(float) (pl1.getCenterX() - 20 - 9),
								(float) (pl1.getCenterY() - 20 - 9));
						player1Mouth.addPoint((float) (pl1.getCenterX() - 5),
								(float) (pl1.getCenterY() - 9));
						player1Mouth.addPoint(
								(float) (pl1.getCenterX() - 20 - 9),
								(float) (pl1.getCenterY() + 20 - 9));
					} else if (pl1Rot == "up") {
						player1Mouth.addPoint(
								(float) (pl1.getCenterX() + 20 - 9),
								(float) (pl1.getCenterY() - 20 - 9));
						player1Mouth.addPoint((float) (pl1.getCenterX() - 9),
								(float) (pl1.getCenterY() - 5));
						player1Mouth.addPoint(
								(float) (pl1.getCenterX() - 20 - 9),
								(float) (pl1.getCenterY() - 20 - 9));
					}
				} else if (sizeOne == 3) {
					if (pl1Rot == "up") {
						player1Mouth = new Polygon();
						player1Mouth.addPoint(
								(float) (pl1.getCenterX() + 29 - 14),
								(float) (pl1.getCenterY() - 29 - 14));
						player1Mouth.addPoint(pl1.getCenterX() - 14,
								pl1.getCenterY() - 6);
						player1Mouth.addPoint(
								(float) (pl1.getCenterX() - 29 - 14),
								(float) (pl1.getCenterY() - 29 - 14));
					} else if (pl1Rot == "left") {
						player1Mouth = new Polygon();
						player1Mouth.addPoint(
								(float) (pl1.getCenterX() - 29 - 14),
								(float) (pl1.getCenterY() - 29 - 14));
						player1Mouth.addPoint(pl1.getCenterX() - 6,
								pl1.getCenterY() - 14);
						player1Mouth.addPoint(
								(float) (pl1.getCenterX() - 29 - 14),
								(float) (pl1.getCenterY() + 29 - 14));
					} else if (pl1Rot == "down") {
						player1Mouth = new Polygon();
						player1Mouth.addPoint(
								(float) (pl1.getCenterX() + 29 - 14),
								(float) (pl1.getCenterY() + 29 - 14));
						player1Mouth.addPoint(pl1.getCenterX() - 14,
								pl1.getCenterY() - 22);
						player1Mouth.addPoint(
								(float) (pl1.getCenterX() - 29 - 14),
								(float) (pl1.getCenterY() + 29 - 14));
					}
				} else if (sizeOne == 4) {
					if (pl1Rot == "down") {
						player1Mouth = new Polygon();
						player1Mouth.addPoint(pl1.getCenterX() - 60,
								pl1.getCenterY() + 20);
						player1Mouth.addPoint(pl1.getCenterX() - 20,
								pl1.getCenterY() - 20);
						player1Mouth.addPoint(pl1.getCenterX() + 20,
								pl1.getCenterY() + 20);
					} else if (pl1Rot == "up") {
						player1Mouth = new Polygon();
						player1Mouth.addPoint(pl1.getCenterX() + 20,
								pl1.getCenterY() - 60);
						player1Mouth.addPoint(pl1.getCenterX() - 20,
								pl1.getCenterY() - 20);
						player1Mouth.addPoint(pl1.getCenterX() - 60,
								pl1.getCenterY() - 60);
					} else if (pl1Rot == "left") {
						player1Mouth = new Polygon();
						player1Mouth.addPoint(pl1.getCenterX() - 60,
								pl1.getCenterY() - 60);
						player1Mouth.addPoint(pl1.getCenterX() - 20,
								pl1.getCenterY() - 20);
						player1Mouth.addPoint(pl1.getCenterX() - 60,
								pl1.getCenterY() + 20);
					}

				} else if (sizeOne == 5) {
					if (pl1 == p1) {
						dec1 = true;
					} else if (pl1 == p2) {
						dec2 = true;
					} else if (pl1 == p3) {
						dec3 = true;
					} else if (pl1 == p4) {
						dec4 = true;
					}
					player1Mouth = new Polygon();
					if (pl1Rot == "down") {
						player1Mouth.addPoint(
								(float) (pl1.getCenterX() - 30 + 40 * 1.5),
								(float) (pl1.getCenterY() - 30 + 40 * 1.5));
						player1Mouth.addPoint(pl1.getCenterX() - 30,
								pl1.getCenterY() - 30);
						player1Mouth.addPoint(
								(float) (pl1.getCenterX() - 30 - 40 * 1.5),
								(float) (pl1.getCenterY() - 30 + 40 * 1.5));
					} else if (pl1Rot == "up") {
						player1Mouth.addPoint(
								(float) (pl1.getCenterX() - 30 + 40 * 1.5),
								(float) (pl1.getCenterY() - 50 - (8 * 8 / 9)
										* 2 - 40 / 1.5));
						player1Mouth.addPoint(pl1.getCenterX() - 30,
								pl1.getCenterY() - 30);
						player1Mouth.addPoint(
								(float) (pl1.getCenterX() - 30 - 40 * 1.5),
								(float) (pl1.getCenterY() - 50 - (8 * 8 / 9)
										* 2 - 40 / 1.5));
					} else if (pl1Rot == "left") {
						player1Mouth.addPoint(
								(float) (pl1.getCenterX() - 30 - 40 * 1.5),
								(float) (pl1.getCenterY() - 30 - 40 * 1.5));
						player1Mouth.addPoint(pl1.getCenterX() - 30,
								pl1.getCenterY() - 30);
						player1Mouth.addPoint(
								(float) (pl1.getCenterX() - 30 - 40 * 1.5),
								(float) (pl1.getCenterY() - 30 + 40 * 1.5));
					}
				}
				if (sizeTwo == 1) {
					player2Mouth = new Polygon();
					player2Mouth.addPoint(
							(float) (pl2.getCenterX() - 40 / 1.5),
							(float) (pl2.getCenterY() - 40 / 1.5));
					player2Mouth.addPoint(pl2.getCenterX(), pl2.getCenterY());
					player2Mouth.addPoint(
							(float) (pl2.getCenterX() - 40 / 1.5),
							(float) (pl2.getCenterY() + 40 / 1.5));
				} else if (sizeTwo == 2) {
					player2Mouth = new Polygon();
					player2Mouth.addPoint(pl2.getCenterX() - 40,
							pl2.getCenterY() - 40);
					player2Mouth.addPoint(pl2.getCenterX(), pl2.getCenterY());
					player2Mouth.addPoint(pl2.getCenterX() - 40,
							pl2.getCenterY() + 40);
				}
				if (sizeTwo == 3) {
					player2Mouth = new Polygon();
					player2Mouth.addPoint(
							(float) (pl2.getCenterX() + 20 - 40 * 1.5),
							(float) (pl2.getCenterY() + 20 - 40 * 1.5));
					player2Mouth.addPoint(pl2.getCenterX() + 20,
							pl2.getCenterY() + 20);
					player2Mouth.addPoint(
							(float) (pl2.getCenterX() + 20 - 40 * 1.5),
							(float) (pl2.getCenterY() + 20 + 40 * 1.5));
				} else if (sizeTwo == 4) {
					player2Mouth = new Polygon();
					player2Mouth
							.addPoint(
									(float) (pl2.getCenterX() + 1.5 * (20 - 40 * 1.5)),
									(float) (pl2.getCenterY() - 10 + 20 * 2 - 40 * 1.5 * 1.5));
					player2Mouth.addPoint(pl2.getCenterX() + 30,
							pl2.getCenterY() + 30);
					player2Mouth
							.addPoint(
									(float) (pl2.getCenterX() + 1.5 * (20 - 40 * 1.5)),
									(float) (pl2.getCenterY() - 10 + 2 * 20 + 40 * 1.5 * 1.5));
				} else if (sizeTwo == 5) {
					player2Mouth = new Polygon();
					player2Mouth.addPoint(
							(float) (pl2.getCenterX() + 1.5 * (-40 * 1.5)),
							(float) (pl2.getCenterY() - 40 * 1.5 * 1.5));
					player2Mouth.addPoint(pl2.getCenterX(), pl2.getCenterY());
					player2Mouth.addPoint(
							(float) (pl2.getCenterX() + 1.5 * (-40 * 1.5)),
							(float) (pl2.getCenterY() + 40 * 1.5 * 1.5));
				}
			}
		} else if (pl1.intersects(pl2) && pl1.getX() > pl2.getX()
				&& pl1Rot == "left"
				&& pl1.getRadius() >= pl2.getRadius() - pl2.getRadius() / 10
				&& (pl2Rot == "down" || pl2Rot == "left" || pl2Rot == "up")) {
			if (!(pl2.getX() < pl1.getX() && pl2.getY() > pl1.getY()
					&& pl2Rot == "up" && pl1Rot == "left")
					&& !(pl2.getX() < pl1.getX() && pl2.getY() < pl1.getY()
							&& pl2Rot == "down" && pl1Rot == "left")) {
				inc = true;
				inc2 = true;
				scoreOne += 1;
				if (sizeOne <= 4) {
					pl1.setRadius((float) (pl1.getRadius() * 1.5));
				}
				if (sizeTwo >= 2) {
					pl2.setRadius((float) (pl2.getRadius() / 1.5));
				}
				pl2.setX(pl2.getX() - 60);
				if (pl2.getX() < 0) {
					pl2.setX(20);
				}
				pl1.setX((pl1.getX() + 60));
				if (pl1.getX() > 1200) {
					pl1.setX(1180);
				}
				if (sizeOne == 1) {
					player1Mouth = new Polygon();
					player1Mouth
							.addPoint(
									(float) (pl1.getCenterX() + 1 - (8 * 8 / 9)
											* 2 - 40 / 1.5),
									(float) (pl1.getCenterY() - (8 * 8 / 9) * 2 - 40 / 1.5));
					player1Mouth.addPoint(pl1.getCenterX() + 1 - (8 * 8 / 9)
							* 2, pl1.getCenterY() - (8 * 8 / 9) * 2);
					player1Mouth
							.addPoint(
									(float) (pl1.getCenterX() + 1 - (8 * 8 / 9)
											* 2 - 40 / 1.5),
									(float) (pl1.getCenterY() - (8 * 8 / 9) * 2 + 40 / 1.5));
				} else if (sizeOne == 2) {
					player1Mouth = new Polygon();
					player1Mouth.addPoint(pl1.getCenterX() - 40,
							pl1.getCenterY() - 40);
					player1Mouth.addPoint(pl1.getCenterX(), pl1.getCenterY());
					player1Mouth.addPoint(pl1.getCenterX() - 40,
							pl1.getCenterY() + 40);
				} else if (sizeOne == 3) {
					player1Mouth = new Polygon();
					player1Mouth.addPoint(
							(float) (pl1.getCenterX() - 40 * 1.5),
							(float) (pl1.getCenterY() - 40 * 1.5));
					player1Mouth.addPoint(pl1.getCenterX(), pl1.getCenterY());
					player1Mouth.addPoint(
							(float) (pl1.getCenterX() - 40 * 1.5),
							(float) (pl1.getCenterY() + 40 * 1.5));
				} else if (sizeOne == 4) {
					player1Mouth = new Polygon();
					player1Mouth
							.addPoint(
									(float) (pl1.getCenterX() - 30 + 1.5 * (20 - 40 * 1.5)),
									(float) (pl1.getCenterY() - 40 + 20 * 2 - 40 * 1.5 * 1.5));
					player1Mouth.addPoint(pl1.getCenterX(), pl1.getCenterY());
					player1Mouth
							.addPoint(
									(float) (pl1.getCenterX() - 30 + 1.5 * (20 - 40 * 1.5)),
									(float) (pl1.getCenterY() - 40 + 2 * 20 + 40 * 1.5 * 1.5));
				} else if (sizeOne == 5) {
					player1Mouth = new Polygon();
					player1Mouth
							.addPoint(
									(float) (pl1.getCenterX() - 30 + 1.5 * (20 - 40 * 1.5)),
									(float) (pl1.getCenterY() - 40 + 20 * 2 - 40 * 1.5 * 1.5));
					player1Mouth.addPoint(pl1.getCenterX(), pl1.getCenterY());
					player1Mouth
							.addPoint(
									(float) (pl1.getCenterX() - 30 + 1.5 * (20 - 40 * 1.5)),
									(float) (pl1.getCenterY() - 40 + 2 * 20 + 40 * 1.5 * 1.5));
				}
				player2Mouth = new Polygon();
				if (sizeTwo == 1 || sizeTwo == 2) {
					player2Mouth = new Polygon();
					if (pl2Rot == "down") {
						player2Mouth.addPoint((float) (pl2.getCenterX() + 20),
								(float) (pl2.getCenterY() + 20));
						player2Mouth.addPoint((float) (pl2.getCenterX()),
								(float) (pl2.getCenterY() - 4));
						player2Mouth.addPoint((float) (pl2.getCenterX() - 20),
								(float) (pl2.getCenterY() + 20));
					} else if (pl2Rot == "left") {
						player2Mouth.addPoint((float) (pl2.getCenterX() - 20),
								(float) (pl2.getCenterY() + 20));
						player2Mouth.addPoint((float) (pl2.getCenterX() + 4),
								(float) (pl2.getCenterY()));
						player2Mouth.addPoint((float) (pl2.getCenterX() - 20),
								(float) (pl2.getCenterY() - 20));
					} else if (pl2Rot == "up") {
						player2Mouth.addPoint((float) (pl2.getCenterX() - 20),
								(float) (pl2.getCenterY() - 20));
						player2Mouth.addPoint((float) (pl2.getCenterX()),
								(float) (pl2.getCenterY() + 4));
						player2Mouth.addPoint((float) (pl2.getCenterX() + 20),
								(float) (pl2.getCenterY() - 20));
					}
				} else if (sizeTwo == 3) {
					if (pl2Rot == "down") {
						player2Mouth.addPoint((float) (pl2.getCenterX() + 29),
								(float) (pl2.getCenterY() + 29));
						player2Mouth.addPoint(pl2.getCenterX(),
								pl2.getCenterY() - 8);
						player2Mouth.addPoint((float) (pl2.getCenterX() - 29),
								(float) (pl2.getCenterY() + 29));
					} else if (pl2Rot == "left") {
						player2Mouth.addPoint((float) (pl2.getCenterX() - 29),
								(float) (pl2.getCenterY() - 29));
						player2Mouth.addPoint(pl2.getCenterX() + 8,
								pl2.getCenterY());
						player2Mouth.addPoint((float) (pl2.getCenterX() - 29),
								(float) (pl2.getCenterY() + 29));
					} else if (pl2Rot == "up") {
						player2Mouth.addPoint((float) (pl2.getCenterX() + 29),
								(float) (pl2.getCenterY() - 29));
						player2Mouth.addPoint(pl2.getCenterX(),
								pl2.getCenterY() + 8);
						player2Mouth.addPoint((float) (pl2.getCenterX() - 29),
								(float) (pl2.getCenterY() - 29));
					}
				} else if (sizeTwo == 4) {
					if (pl2Rot == "down") {
						player2Mouth = new Polygon();
						player2Mouth.addPoint(pl2.getCenterX() - 40,
								pl2.getCenterY() + 40);
						player2Mouth.addPoint(pl2.getCenterX(),
								pl2.getCenterY());
						player2Mouth.addPoint(pl2.getCenterX() + 40,
								pl2.getCenterY() + 40);
					} else if (pl2Rot == "up") {
						player2Mouth = new Polygon();
						player2Mouth.addPoint(pl2.getCenterX() + 40,
								pl2.getCenterY() - 40);
						player2Mouth.addPoint(pl2.getCenterX(),
								pl2.getCenterY());
						player2Mouth.addPoint(pl2.getCenterX() - 40,
								pl2.getCenterY() - 40);
					} else if (pl2Rot == "left") {
						player2Mouth = new Polygon();
						player2Mouth.addPoint(pl2.getCenterX() - 40,
								pl2.getCenterY() - 40);
						player2Mouth.addPoint(pl2.getCenterX(),
								pl2.getCenterY());
						player2Mouth.addPoint(pl2.getCenterX() - 40,
								pl2.getCenterY() + 40);
					}

				} else if (sizeTwo == 5) {
					if (pl2 == p1) {
						dec1 = true;
					} else if (pl2 == p2) {
						dec2 = true;
					} else if (pl2 == p3) {
						dec3 = true;
					} else if (pl2 == p4) {
						dec4 = true;
					}
					player2Mouth = new Polygon();
					if (pl2Rot == "down") {
						player2Mouth.addPoint(
								(float) (pl2.getCenterX() + 40 * 1.5),
								(float) (pl2.getCenterY() + 40 * 1.5));
						player2Mouth.addPoint(pl2.getCenterX(),
								pl2.getCenterY());
						player2Mouth.addPoint(
								(float) (pl2.getCenterX() - 40 * 1.5),
								(float) (pl2.getCenterY() + 40 * 1.5));
					} else if (pl2Rot == "up") {
						player2Mouth.addPoint(
								(float) (pl2.getCenterX() + 40 * 1.5),
								(float) (pl2.getCenterY() - 20 - (8 * 8 / 9)
										* 2 - 40 / 1.5));
						player2Mouth.addPoint(pl2.getCenterX(),
								pl2.getCenterY());
						player2Mouth.addPoint(
								(float) (pl2.getCenterX() - 40 * 1.5),
								(float) (pl2.getCenterY() - 20 - (8 * 8 / 9)
										* 2 - 40 / 1.5));
					} else if (pl2Rot == "left") {
						player2Mouth.addPoint(
								(float) (pl2.getCenterX() - 40 * 1.5),
								(float) (pl2.getCenterY() - 40 * 1.5));
						player2Mouth.addPoint(pl2.getCenterX(),
								pl2.getCenterY());
						player2Mouth.addPoint(
								(float) (pl2.getCenterX() - 40 * 1.5),
								(float) (pl2.getCenterY() + 40 * 1.5));
					}
				}
			}

		} else if (pl1.intersects(pl2) && pl2Rot == "right"
				&& pl2.getX() < pl1.getX()
				&& pl2.getRadius() >= pl1.getRadius() - pl1.getRadius() / 10
				&& (pl1Rot == "down" || pl1Rot == "right" || pl1Rot == "up")) {
			if (!(pl1.getX() > pl2.getX() && pl1.getY() > pl2.getY()
					&& pl1Rot == "up" && pl2Rot == "right")
					&& !(pl2.getX() < pl1.getX() && pl1.getY() < pl2.getY()
							&& pl1Rot == "down" && pl2Rot == "right")) {
				inc = true;
				inc2 = true;
				scoreTwo += 1;
				player1Mouth = new Polygon();
				if (sizeOne == 1 || sizeOne == 2) {
					if (pl1Rot == "down") {
						player1Mouth.addPoint(
								(float) (pl1.getCenterX() + 20 - 9),
								(float) (pl1.getCenterY() + 20 - 9));
						player1Mouth.addPoint((float) (pl1.getCenterX() - 9),
								(float) (pl1.getCenterY() - 13));
						player1Mouth.addPoint(
								(float) (pl1.getCenterX() - 20 - 9),
								(float) (pl1.getCenterY() + 20 - 9));
					} else if (pl1Rot == "right") {
						player1Mouth.addPoint(
								(float) (pl1.getCenterX() + 20 - 9),
								(float) (pl1.getCenterY() + 20 - 9));
						player1Mouth.addPoint((float) (pl1.getCenterX() - 13),
								(float) (pl1.getCenterY() - 9));
						player1Mouth.addPoint(
								(float) (pl1.getCenterX() + 20 - 9),
								(float) (pl1.getCenterY() - 20 - 9));
					} else if (pl1Rot == "up") {
						player1Mouth.addPoint(
								(float) (pl1.getCenterX() - 20 - 9),
								(float) (pl1.getCenterY() - 20 - 9));
						player1Mouth.addPoint((float) (pl1.getCenterX() - 9),
								(float) (pl1.getCenterY() - 5));
						player1Mouth.addPoint(
								(float) (pl1.getCenterX() + 20 - 9),
								(float) (pl1.getCenterY() - 20 - 9));
					}
				} else if (sizeOne == 3) {
					if (pl1Rot == "down") {
						player1Mouth.addPoint(
								(float) (pl1.getCenterX() + 29 - 14),
								(float) (pl1.getCenterY() + 29 - 14));
						player1Mouth.addPoint((float) (pl1.getCenterX() - 14),
								(float) (pl1.getCenterY() - 22));
						player1Mouth.addPoint(
								(float) (pl1.getCenterX() - 29 - 14),
								(float) (pl1.getCenterY() + 29 - 14));
					} else if (pl1Rot == "right") {
						player1Mouth.addPoint(
								(float) (pl1.getCenterX() + 29 - 14),
								(float) (pl1.getCenterY() + 29 - 14));
						player1Mouth.addPoint((float) (pl1.getCenterX() - 22),
								(float) (pl1.getCenterY() - 14));
						player1Mouth.addPoint(
								(float) (pl1.getCenterX() + 29 - 14),
								(float) (pl1.getCenterY() - 29 - 14));
					} else if (pl1Rot == "up") {
						player1Mouth.addPoint(
								(float) (pl1.getCenterX() + 29 - 14),
								(float) (pl1.getCenterY() - 29 - 14));
						player1Mouth.addPoint((float) (pl1.getCenterX() - 14),
								(float) (pl1.getCenterY() - 6));
						player1Mouth.addPoint(
								(float) (pl1.getCenterX() - 29 - 14),
								(float) (pl1.getCenterY() - 29 - 14));
					}
				} else if (sizeOne == 4) {
					if (pl1Rot == "down") {
						player1Mouth.addPoint(pl1.getCenterX() - 60,
								pl1.getCenterY() + 20);
						player1Mouth.addPoint(pl1.getCenterX() - 20,
								pl1.getCenterY() - 20);
						player1Mouth.addPoint(pl1.getCenterX() + 20,
								pl1.getCenterY() + 20);
					} else if (pl1Rot == "right") {
						player1Mouth.addPoint(pl1.getCenterX() + 20,
								pl1.getCenterY() - 60);
						player1Mouth.addPoint(pl1.getCenterX() - 20,
								pl1.getCenterY() - 20);
						player1Mouth.addPoint(pl1.getCenterX() + 20,
								pl1.getCenterY() + 20);
					} else if (pl1Rot == "up") {
						player1Mouth.addPoint(pl1.getCenterX() - 60,
								pl1.getCenterY() - 60);
						player1Mouth.addPoint(pl1.getCenterX() - 20,
								pl1.getCenterY() - 20);
						player1Mouth.addPoint(pl1.getCenterX() + 20,
								pl1.getCenterY() - 60);
					}
				} else if (sizeOne == 5) {
					if (pl1 == p1) {
						dec1 = true;
					} else if (pl1 == p2) {
						dec2 = true;
					} else if (pl1 == p3) {
						dec3 = true;
					} else if (pl1 == p4) {
						dec4 = true;
					}
					if (pl1Rot == "down") {
						player1Mouth.addPoint(
								(float) (pl1.getCenterX() - 30 + 40 * 1.5),
								(float) (pl1.getCenterY() - 30 + 40 * 1.5));
						player1Mouth.addPoint(pl1.getCenterX() - 30,
								pl1.getCenterY() - 30);
						player1Mouth.addPoint(
								(float) (pl1.getCenterX() - 30 - 40 * 1.5),
								(float) (pl1.getCenterY() - 30 + 40 * 1.5));
					} else if (pl1Rot == "right") {
						player1Mouth.addPoint(
								(float) (pl1.getCenterX() - 30 + 40 * 1.5),
								(float) (pl1.getCenterY() - 30 - 40 * 1.5));
						player1Mouth.addPoint(pl1.getCenterX() - 30,
								pl1.getCenterY() - 30);
						player1Mouth.addPoint(
								(float) (pl1.getCenterX() - 30 + 40 * 1.5),
								(float) (pl1.getCenterY() - 30 + 40 * 1.5));
					} else if (pl1Rot == "up") {
						player1Mouth.addPoint(
								(float) (pl1.getCenterX() - 30 + 40 * 1.5),
								(float) (pl1.getCenterY() - 50 - (8 * 8 / 9)
										* 2 - 40 / 1.5));
						player1Mouth.addPoint(pl1.getCenterX() - 30,
								pl1.getCenterY() - 30);
						player1Mouth.addPoint(
								(float) (pl1.getCenterX() - 30 - 40 * 1.5),
								(float) (pl1.getCenterY() - 50 - (8 * 8 / 9)
										* 2 - 40 / 1.5));
					}
				}
				if (sizeTwo == 1) {
					player2Mouth = new Polygon();
					player2Mouth
							.addPoint((float) (pl2.getCenterX() + 1
									- (8 * 8 / 9) * 2 + 40 / 1.5),
									(float) (pl2.getCenterY() + 1 - (8 * 8 / 9)
											* 2 - 40 / 1.5));
					player2Mouth.addPoint(pl2.getCenterX() + 1 - (8 * 8 / 9)
							* 2, pl2.getCenterY() - (8 * 8 / 9) * 2);
					player2Mouth
							.addPoint((float) (pl2.getCenterX() + 1
									- (8 * 8 / 9) * 2 + 40 / 1.5),
									(float) (pl2.getCenterY() + 1 - (8 * 8 / 9)
											* 2 + 40 / 1.5));
				} else if (sizeTwo == 2) {
					player2Mouth = new Polygon();
					player2Mouth.addPoint(pl2.getCenterX() + 40,
							pl2.getCenterY() - 40);
					player2Mouth.addPoint(pl2.getCenterX(), pl2.getCenterY());
					player2Mouth.addPoint(pl2.getCenterX() + 40,
							pl2.getCenterY() + 40);

				} else if (sizeTwo == 3) {
					player2Mouth = new Polygon();
					player2Mouth.addPoint(
							(float) (pl2.getCenterX() + 20 + 40 * 1.5),
							(float) (pl2.getCenterY() + 20 - 40 * 1.5));
					player2Mouth.addPoint(pl2.getCenterX() + 20,
							pl2.getCenterY() + 20);
					player2Mouth.addPoint(
							(float) (pl2.getCenterX() + 20 + 40 * 1.5),
							(float) (pl2.getCenterY() + 20 + 40 * 1.5));
				} else if (sizeTwo == 4) {
					player2Mouth = new Polygon();
					player2Mouth
							.addPoint(
									(float) (pl2.getCenterX() + 1.5 * (20 + 40 * 1.5)),
									(float) (pl2.getCenterY() - 10 + 20 * 2 - 40 * 1.5 * 1.5));
					player2Mouth.addPoint(pl2.getCenterX() + 20,
							pl2.getCenterY() + 30);
					player2Mouth
							.addPoint(
									(float) (pl2.getCenterX() + 1.5 * (20 + 40 * 1.5)),
									(float) (pl2.getCenterY() - 10 + 2 * 20 + 40 * 1.5 * 1.5));

				} else if (sizeTwo == 5) {
					player2Mouth = new Polygon();
					player2Mouth
							.addPoint(
									(float) (pl2.getCenterX() - 20 + 1.5 * (20 + 40 * 1.5)),
									(float) (pl2.getCenterY() - 40 * 1.5 * 1.5));
					player2Mouth.addPoint(pl2.getCenterX(), pl2.getCenterY());
					player2Mouth
							.addPoint(
									(float) (pl2.getCenterX() - 20 + 1.5 * (20 + 40 * 1.5)),
									(float) (pl2.getCenterY() + 40 * 1.5 * 1.5));

				}
			}
		} else if (pl1.intersects(pl2) && pl1.getY() < pl2.getY()
				&& pl1Rot == "down"
				&& pl1.getRadius() >= pl2.getRadius() - pl2.getRadius() / 10
				&& (pl2Rot == "down" || pl2Rot == "left" || pl2Rot == "right")) {
			if (!(pl1.getX() < pl2.getX() && pl1.getY() < pl2.getY()
					&& pl1Rot == "down" && pl2Rot == "left")
					&& !(pl2.getX() < pl1.getX() && pl1.getY() < pl2.getY()
							&& pl1Rot == "down" && pl2Rot == "right")) {
				inc = true;
				inc2 = true;
				scoreOne += 1;
				if (sizeOne == 1) {
					player1Mouth = new Polygon();
					player1Mouth
							.addPoint((float) (pl1.getCenterX() + 1
									- (8 * 8 / 9) * 2 + 40 / 1.5),
									(float) (pl1.getCenterY() + 1 - (8 * 8 / 9)
											* 2 + 40 / 1.5));
					player1Mouth.addPoint(pl1.getCenterX() + 1 - (8 * 8 / 9)
							* 2, pl1.getCenterY() + 1 - (8 * 8 / 9) * 2);
					player1Mouth
							.addPoint((float) (pl1.getCenterX() + 1
									- (8 * 8 / 9) * 2 - 40 / 1.5),
									(float) (pl1.getCenterY() + 1 - (8 * 8 / 9)
											* 2 + 40 / 1.5));
				} else if (sizeOne == 2) {
					player1Mouth = new Polygon();
					player1Mouth.addPoint(pl1.getCenterX() + 40,
							pl1.getCenterY() + 40);
					player1Mouth.addPoint(pl1.getCenterX(), pl1.getCenterY());
					player1Mouth.addPoint(pl1.getCenterX() - 40,
							pl1.getCenterY() + 40);
				}
				if (sizeOne == 3) {
					player1Mouth = new Polygon();
					player1Mouth.addPoint(
							(float) (pl1.getCenterX() + 20 + 40 * 1.5),
							(float) (pl1.getCenterY() + 22 + 40 * 1.5));
					player1Mouth.addPoint(pl1.getCenterX() + 20,
							pl1.getCenterY() + 20);
					player1Mouth.addPoint(
							(float) (pl1.getCenterX() + 20 - 40 * 1.5),
							(float) (pl1.getCenterY() + 22 + 40 * 1.5));
				} else if (sizeOne == 4) {
					player1Mouth = new Polygon();
					player1Mouth
							.addPoint(
									(float) (pl1.getCenterX() + 1.5 * (20 + 40 * 1.5)),
									(float) (pl1.getCenterY() - 10 + 20 * 2 + 40 * 1.5 * 1.5));
					player1Mouth.addPoint(pl1.getCenterX() + 30,
							pl1.getCenterY() + 30);
					player1Mouth
							.addPoint(
									(float) (pl1.getCenterX() + 1.5 * (20 - 40 * 1.5)),
									(float) (pl1.getCenterY() - 10 + 2 * 20 + 40 * 1.5 * 1.5));

				} else if (sizeOne == 5) {
					player1Mouth = new Polygon();
					player1Mouth.addPoint(
							(float) (pl1.getCenterX() + 1.5 * (+40 * 1.5)),
							(float) (pl1.getCenterY() + 40 * 1.5 * 1.5));
					player1Mouth.addPoint(pl1.getCenterX(), pl1.getCenterY());
					player1Mouth.addPoint(
							(float) (pl1.getCenterX() + 1.5 * (-40 * 1.5)),
							(float) (pl1.getCenterY() + 40 * 1.5 * 1.5));

				}
				player2Mouth = new Polygon();
				if (sizeTwo == 1 || sizeTwo == 2) {
					if (pl2Rot == "down") {
						player2Mouth.addPoint(
								(float) (pl2.getCenterX() + 20 - 9),
								(float) (pl2.getCenterY() + 20 - 9));
						player2Mouth.addPoint((float) (pl2.getCenterX() - 9),
								(float) (pl2.getCenterY() - 13));
						player2Mouth.addPoint(
								(float) (pl2.getCenterX() - 20 - 9),
								(float) (pl2.getCenterY() + 20 - 9));
					} else if (pl2Rot == "left") {
						player2Mouth.addPoint(
								(float) (pl2.getCenterX() - 20 - 9),
								(float) (pl2.getCenterY() - 20 - 9));
						player2Mouth.addPoint((float) (pl2.getCenterX() - 5),
								(float) (pl2.getCenterY() - 9));
						player2Mouth.addPoint(
								(float) (pl2.getCenterX() - 20 - 9),
								(float) (pl2.getCenterY() + 20 - 9));
					} else if (pl2Rot == "right") {
						player2Mouth.addPoint(
								(float) (pl2.getCenterX() + 20 - 9),
								(float) (pl2.getCenterY() - 20 - 9));
						player2Mouth.addPoint((float) (pl2.getCenterX() - 13),
								(float) (pl2.getCenterY() - 9));
						player2Mouth.addPoint(
								(float) (pl2.getCenterX() + 20 - 9),
								(float) (pl2.getCenterY() + 20 - 9));
					}
				} else if (sizeTwo == 3) {
					if (pl2Rot == "down") {
						player2Mouth.addPoint(
								(float) (pl2.getCenterX() + 29 - 14),
								(float) (pl2.getCenterY() + 29 - 14));
						player2Mouth.addPoint(pl2.getCenterX() - 14,
								pl2.getCenterY() - 22);
						player2Mouth.addPoint(
								(float) (pl2.getCenterX() - 29 - 14),
								(float) (pl2.getCenterY() + 29 - 14));
					} else if (pl2Rot == "left") {
						player2Mouth.addPoint(
								(float) (pl2.getCenterX() - 29 - 14),
								(float) (pl2.getCenterY() + 29 - 14));
						player2Mouth.addPoint(pl2.getCenterX() - 6,
								pl2.getCenterY() - 14);
						player2Mouth.addPoint(
								(float) (pl2.getCenterX() - 29 - 14),
								(float) (pl2.getCenterY() - 29 - 14));
					} else if (pl2Rot == "right") {
						player2Mouth.addPoint(
								(float) (pl2.getCenterX() + 29 - 14),
								(float) (pl2.getCenterY() + 29 - 14));
						player2Mouth.addPoint(pl2.getCenterX() - 22,
								pl2.getCenterY() - 14);
						player2Mouth.addPoint(
								(float) (pl2.getCenterX() + 29 - 14),
								(float) (pl2.getCenterY() - 29 - 14));
					}
				} else if (sizeTwo == 4) {
					if (pl2Rot == "down") {
						player2Mouth = new Polygon();
						player2Mouth.addPoint(pl2.getCenterX() - 60,
								pl2.getCenterY() + 20);
						player2Mouth.addPoint(pl2.getCenterX() - 20,
								pl2.getCenterY() - 20);
						player2Mouth.addPoint(pl2.getCenterX() + 20,
								pl2.getCenterY() + 20);
					} else if (pl2Rot == "right") {
						player2Mouth = new Polygon();
						player2Mouth.addPoint(pl2.getCenterX() + 20,
								pl2.getCenterY() - 60);
						player2Mouth.addPoint(pl2.getCenterX() - 20,
								pl2.getCenterY() - 20);
						player2Mouth.addPoint(pl2.getCenterX() + 20,
								pl2.getCenterY() + 20);
					} else if (pl2Rot == "left") {
						player2Mouth = new Polygon();
						player2Mouth.addPoint(pl2.getCenterX() - 60,
								pl2.getCenterY() - 60);
						player2Mouth.addPoint(pl2.getCenterX() - 20,
								pl2.getCenterY() - 20);
						player2Mouth.addPoint(pl2.getCenterX() - 60,
								pl2.getCenterY() + 20);
					}
				} else if (sizeTwo == 5) {
					if (pl2 == p1) {
						dec1 = true;
					} else if (pl2 == p2) {
						dec2 = true;
					} else if (pl2 == p3) {
						dec3 = true;
					} else if (pl2 == p4) {
						dec4 = true;
					}
					player2Mouth = new Polygon();
					if (pl2Rot == "down") {
						player2Mouth.addPoint(
								(float) (pl2.getCenterX() - 30 + 40 * 1.5),
								(float) (pl2.getCenterY() - 30 + 40 * 1.5));
						player2Mouth.addPoint(pl2.getCenterX() - 30,
								pl2.getCenterY() - 30);
						player2Mouth.addPoint(
								(float) (pl2.getCenterX() - 30 - 40 * 1.5),
								(float) (pl2.getCenterY() - 30 + 40 * 1.5));
					} else if (pl2Rot == "right") {
						player2Mouth.addPoint(
								(float) (pl2.getCenterX() - 30 + 40 * 1.5),
								(float) (pl2.getCenterY() - 30 - 40 * 1.5));
						player2Mouth.addPoint(pl2.getCenterX() - 30,
								pl2.getCenterY() - 30);
						player2Mouth.addPoint(
								(float) (pl2.getCenterX() - 30 + 40 * 1.5),
								(float) (pl2.getCenterY() - 30 + 40 * 1.5));
					} else if (pl2Rot == "left") {
						player2Mouth.addPoint(
								(float) (pl2.getCenterX() - 30 - 40 * 1.5),
								(float) (pl2.getCenterY() - 30 - 40 * 1.5));
						player2Mouth.addPoint(pl2.getCenterX() - 30,
								pl2.getCenterY() - 30);
						player2Mouth.addPoint(
								(float) (pl2.getCenterX() - 30 - 40 * 1.5),
								(float) (pl2.getCenterY() - 30 + 40 * 1.5));
					}

				}
			}
		} else if (pl1.intersects(pl2) && pl1.getY() < pl2.getY()
				&& pl2Rot == "up"
				&& pl2.getRadius() >= pl1.getRadius() - pl1.getRadius() / 10
				&& (pl1Rot == "left" || pl1Rot == "right" || pl1Rot == "up")) {
			if (!(pl2.getX() < pl1.getX() && pl2.getY() > pl1.getY()
					&& pl2Rot == "up" && pl1Rot == "left")
					&& !(pl2.getX() > pl1.getX() && pl2.getY() > pl1.getY()
							&& pl2Rot == "up" && pl1Rot == "right")
					&& !(pl1.getX() < pl2.getX() && pl1.getY() < pl2.getY()
							&& pl1Rot == "right" && pl2Rot == "up")) {
				inc = true;
				inc2 = true;
				scoreTwo += 1;
				if (sizeOne == 1 || sizeOne == 2) {
					player1Mouth = new Polygon();
					if (pl1Rot == "left") {
						player1Mouth.addPoint(
								(float) (pl1.getCenterX() - 20 - 9),
								(float) (pl1.getCenterY() - 20 - 9));
						player1Mouth.addPoint((float) (pl1.getCenterX() - 5),
								(float) (pl1.getCenterY() - 9));
						player1Mouth.addPoint(
								(float) (pl1.getCenterX() - 20 - 9),
								(float) (pl1.getCenterY() + 20 - 9));
					} else if (pl1Rot == "right") {
						player1Mouth.addPoint(
								(float) (pl1.getCenterX() + 20 - 9),
								(float) (pl1.getCenterY() + 20 - 9));
						player1Mouth.addPoint((float) (pl1.getCenterX() - 13),
								(float) (pl1.getCenterY() - 9));
						player1Mouth.addPoint(
								(float) (pl1.getCenterX() + 20 - 9),
								(float) (pl1.getCenterY() - 20 - 9));
					} else if (pl1Rot == "up") {
						player1Mouth.addPoint(
								(float) (pl1.getCenterX() + 20 - 9),
								(float) (pl1.getCenterY() - 20 - 9));
						player1Mouth.addPoint((float) (pl1.getCenterX() - 9),
								(float) (pl1.getCenterY() - 5));
						player1Mouth.addPoint(
								(float) (pl1.getCenterX() - 20 - 9),
								(float) (pl1.getCenterY() - 20 - 9));
					}
				} else if (sizeOne == 3) {
					if (pl1Rot == "up") {
						player1Mouth = new Polygon();
						player1Mouth.addPoint(
								(float) (pl1.getCenterX() + 29 - 14),
								(float) (pl1.getCenterY() - 29 - 14));
						player1Mouth.addPoint(pl1.getCenterX() - 14,
								pl1.getCenterY() - 6);
						player1Mouth.addPoint(
								(float) (pl1.getCenterX() - 29 - 14),
								(float) (pl1.getCenterY() - 29 - 14));
					} else if (pl1Rot == "left") {
						player1Mouth = new Polygon();
						player1Mouth.addPoint(
								(float) (pl1.getCenterX() - 29 - 14),
								(float) (pl1.getCenterY() + 29 - 14));
						player1Mouth.addPoint(pl1.getCenterX() - 6,
								pl1.getCenterY() - 14);
						player1Mouth.addPoint(
								(float) (pl1.getCenterX() - 29 - 14),
								(float) (pl1.getCenterY() - 29 - 14));
					} else if (pl1Rot == "right") {
						player1Mouth = new Polygon();
						player1Mouth.addPoint(
								(float) (pl1.getCenterX() + 29 - 14),
								(float) (pl1.getCenterY() - 29 - 14));
						player1Mouth.addPoint(pl1.getCenterX() - 22,
								pl1.getCenterY() - 14);
						player1Mouth.addPoint(
								(float) (pl1.getCenterX() + 29 - 14),
								(float) (pl1.getCenterY() + 29 - 14));
					}
				} else if (sizeOne == 4) {
					player1Mouth = new Polygon();
					if (pl1Rot == "left") {
						player1Mouth.addPoint(pl1.getCenterX() - 60,
								pl1.getCenterY() + 20);
						player1Mouth.addPoint(pl1.getCenterX() - 20,
								pl1.getCenterY() - 20);
						player1Mouth.addPoint(pl1.getCenterX() - 60,
								pl1.getCenterY() - 60);
					} else if (pl1Rot == "right") {
						player1Mouth.addPoint(pl1.getCenterX() + 20,
								pl1.getCenterY() - 60);
						player1Mouth.addPoint(pl1.getCenterX() - 20,
								pl1.getCenterY() - 20);
						player1Mouth.addPoint(pl1.getCenterX() + 20,
								pl1.getCenterY() + 20);
					} else if (pl1Rot == "up") {
						player1Mouth.addPoint(pl1.getCenterX() - 60,
								pl1.getCenterY() - 60);
						player1Mouth.addPoint(pl1.getCenterX() - 20,
								pl1.getCenterY() - 20);
						player1Mouth.addPoint(pl1.getCenterX() + 20,
								pl1.getCenterY() - 60);
					}
				} else if (sizeOne == 5) {
					if (pl1 == p1) {
						dec1 = true;
					} else if (pl1 == p2) {
						dec2 = true;
					} else if (pl1 == p3) {
						dec3 = true;
					} else if (pl1 == p4) {
						dec4 = true;
					}
					player1Mouth = new Polygon();
					if (pl1Rot == "left") {
						player1Mouth.addPoint(
								(float) (pl1.getCenterX() - 30 - 40 * 1.5),
								(float) (pl1.getCenterY() - 30 - 40 * 1.5));
						player1Mouth.addPoint(pl1.getCenterX() - 30,
								pl1.getCenterY() - 30);
						player1Mouth.addPoint(
								(float) (pl1.getCenterX() - 30 - 40 * 1.5),
								(float) (pl1.getCenterY() - 30 + 40 * 1.5));
					} else if (pl1Rot == "right") {
						player1Mouth.addPoint(
								(float) (pl1.getCenterX() - 30 + 40 * 1.5),
								(float) (pl1.getCenterY() - 30 - 40 * 1.5));
						player1Mouth.addPoint(pl1.getCenterX() - 30,
								pl1.getCenterY() - 30);
						player1Mouth.addPoint(
								(float) (pl1.getCenterX() - 30 + 40 * 1.5),
								(float) (pl1.getCenterY() - 30 + 40 * 1.5));
					} else if (pl1Rot == "up") {
						player1Mouth.addPoint(
								(float) (pl1.getCenterX() - 30 + 40 * 1.5),
								(float) (pl1.getCenterY() - 50 - (8 * 8 / 9)
										* 2 - 40 / 1.5));
						player1Mouth.addPoint(pl1.getCenterX() - 30,
								pl1.getCenterY() - 30);
						player1Mouth.addPoint(
								(float) (pl1.getCenterX() - 30 - 40 * 1.5),
								(float) (pl1.getCenterY() - 50 - (8 * 8 / 9)
										* 2 - 40 / 1.5));
					}
				}
				if (sizeTwo == 1) {
					player2Mouth = new Polygon();
					player2Mouth
							.addPoint(
									(float) (pl2.getCenterX() + 1 - (8 * 8 / 9)
											* 2 + 40 / 1.5),
									(float) (pl2.getCenterY() - (8 * 8 / 9) * 2 - 40 / 1.5));
					player2Mouth.addPoint(pl2.getCenterX() + 1 - (8 * 8 / 9)
							* 2, pl2.getCenterY() - (8 * 8 / 9) * 2);
					player2Mouth
							.addPoint(
									(float) (pl2.getCenterX() + 1 - (8 * 8 / 9)
											* 2 - 40 / 1.5),
									(float) (pl2.getCenterY() - (8 * 8 / 9) * 2 - 40 / 1.5));
				} else if (sizeTwo == 2) {
					player2Mouth = new Polygon();
					player2Mouth.addPoint(pl2.getCenterX() - 40,
							pl2.getCenterY() - 40);
					player2Mouth.addPoint(pl2.getCenterX(), pl2.getCenterY());
					player2Mouth.addPoint(pl2.getCenterX() + 40,
							pl2.getCenterY() - 40);

				} else if (sizeTwo == 3) {
					player2Mouth = new Polygon();
					player2Mouth
							.addPoint(
									(float) (pl2.getCenterX() + 20 + 40 * 1.5),
									(float) (pl2.getCenterY() - (8 * 8 / 9) * 2 - 40 / 1.5));
					player2Mouth.addPoint(pl2.getCenterX() + 20,
							pl2.getCenterY() + 20);
					player2Mouth
							.addPoint(
									(float) (pl2.getCenterX() + 20 - 40 * 1.5),
									(float) (pl2.getCenterY() - (8 * 8 / 9) * 2 - 40 / 1.5));
				} else if (sizeTwo == 4) {
					player2Mouth = new Polygon();
					player2Mouth
							.addPoint(
									(float) (pl2.getCenterX() + 1.5 * (20 + 40 * 1.5)),
									(float) (pl2.getCenterY() - 14 + 20 * 2 - 40 * 1.5 * 1.5));
					player2Mouth.addPoint(pl2.getCenterX() + 26,
							pl2.getCenterY() + 26);
					player2Mouth
							.addPoint(
									(float) (pl2.getCenterX() + 1.5 * (20 - 40 * 1.5)),
									(float) (pl2.getCenterY() - 14 + 2 * 20 - 40 * 1.5 * 1.5));

				} else if (sizeTwo == 5) {
					player2Mouth = new Polygon();
					player2Mouth
							.addPoint(
									(float) (pl2.getCenterX() - 26 + 1.5 * (20 + 40 * 1.5)),
									(float) (pl2.getCenterY() - 1 - 40 * 1.5 * 1.5));
					player2Mouth.addPoint(pl2.getCenterX() - 1,
							pl2.getCenterY());
					player2Mouth
							.addPoint(
									(float) (pl2.getCenterX() - 26 + 1.5 * (20 - 40 * 1.5)),
									(float) (pl2.getCenterY() - 1 - 40 * 1.5 * 1.5));

				}
			}
		} else if (pl1.intersects(pl2) && pl2.getY() < pl1.getY()
				&& pl2Rot == "down"
				&& pl2.getRadius() >= pl1.getRadius() - pl1.getRadius() / 10
				&& (pl1Rot == "down" || pl1Rot == "left" || pl1Rot == "right")) {
			if (!(pl1.getX() < pl2.getX() && pl1.getY() > pl2.getY()
					&& pl1Rot == "right" && pl2Rot == "down")
					&& !(pl2.getX() < pl1.getX() && pl2.getY() < pl1.getY()
							&& pl2Rot == "down" && pl1Rot == "left")) {
				inc = true;
				inc2 = true;
				scoreTwo += 1;
				if (sizeTwo == 1) {
					player2Mouth = new Polygon();
					player2Mouth
							.addPoint((float) (pl2.getCenterX() + 1
									- (8 * 8 / 9) * 2 + 40 / 1.5),
									(float) (pl2.getCenterY() + 1 - (8 * 8 / 9)
											* 2 + 40 / 1.5));
					player2Mouth.addPoint(pl2.getCenterX() + 1 - (8 * 8 / 9)
							* 2, pl2.getCenterY() - (8 * 8 / 9) * 2);
					player2Mouth
							.addPoint((float) (pl2.getCenterX() + 1
									- (8 * 8 / 9) * 2 - 40 / 1.5),
									(float) (pl2.getCenterY() + 1 - (8 * 8 / 9)
											* 2 + 40 / 1.5));

				} else if (sizeTwo == 2) {
					player2Mouth = new Polygon();
					player2Mouth.addPoint(pl2.getCenterX() - 40,
							pl2.getCenterY() + 40);
					player2Mouth.addPoint(pl2.getCenterX(), pl2.getCenterY());
					player2Mouth.addPoint(pl2.getCenterX() + 40,
							pl2.getCenterY() + 40);

				} else if (sizeTwo == 3) {
					player2Mouth = new Polygon();
					player2Mouth.addPoint(
							(float) (pl2.getCenterX() + 20 + 40 * 1.5),
							(float) (pl2.getCenterY() + 20 + 40 * 1.5));
					player2Mouth.addPoint(pl2.getCenterX() + 20,
							pl2.getCenterY() + 20);
					player2Mouth.addPoint(
							(float) (pl2.getCenterX() + 20 - 40 * 1.5),
							(float) (pl2.getCenterY() + 20 + 40 * 1.5));
				} else if (sizeTwo == 4) {
					player2Mouth = new Polygon();
					player2Mouth
							.addPoint(
									(float) (pl2.getCenterX() + 1.5 * (20 + 40 * 1.5)),
									(float) (pl2.getCenterY() - 8 + 20 * 2 + 40 * 1.5 * 1.5));
					player2Mouth.addPoint(pl2.getCenterX() + 25,
							pl2.getCenterY() + 25);
					player2Mouth
							.addPoint(
									(float) (pl2.getCenterX() + 1.5 * (20 - 40 * 1.5)),
									(float) (pl2.getCenterY() - 8 + 2 * 20 + 40 * 1.5 * 1.5));

				} else if (sizeTwo == 5) {
					player2Mouth = new Polygon();
					player2Mouth
							.addPoint(
									(float) (pl2.getCenterX() - 25 + 1.5 * (20 + 40 * 1.5)),
									(float) (pl2.getCenterY() - 25 - 8 + 20 * 2 + 40 * 1.5 * 1.5));
					player2Mouth.addPoint(pl2.getCenterX(), pl2.getCenterY());
					player2Mouth
							.addPoint(
									(float) (pl2.getCenterX() - 25 + 1.5 * (20 - 40 * 1.5)),
									(float) (pl2.getCenterY() - 25 - 8 + 2 * 20 + 40 * 1.5 * 1.5));

				}
				if (sizeOne == 1 || sizeOne == 2) {
					player1Mouth = new Polygon();
					if (pl1Rot == "left") {
						player1Mouth.addPoint(
								(float) (pl1.getCenterX() - 20 - 9),
								(float) (pl1.getCenterY() + 20 - 9));
						player1Mouth.addPoint((float) (pl1.getCenterX() - 5),
								(float) (pl1.getCenterY() - 9));
						player1Mouth.addPoint(
								(float) (pl1.getCenterX() - 20 - 9),
								(float) (pl1.getCenterY() - 20 - 9));
					} else if (pl1Rot == "right") {
						player1Mouth.addPoint(
								(float) (pl1.getCenterX() + 20 - 9),
								(float) (pl1.getCenterY() + 20 - 9));
						player1Mouth.addPoint((float) (pl1.getCenterX() - 13),
								(float) (pl1.getCenterY() - 9));
						player1Mouth.addPoint(
								(float) (pl1.getCenterX() + 20 - 9),
								(float) (pl1.getCenterY() - 20 - 9));
					} else if (pl1Rot == "down") {
						player1Mouth.addPoint(
								(float) (pl1.getCenterX() - 20 - 9),
								(float) (pl1.getCenterY() + 20 - 9));
						player1Mouth.addPoint((float) (pl1.getCenterX() - 9),
								(float) (pl1.getCenterY() - 13));
						player1Mouth.addPoint(
								(float) (pl1.getCenterX() + 20 - 9),
								(float) (pl1.getCenterY() + 20 - 9));
					}
				} else if (sizeOne == 3) {
					player1Mouth = new Polygon();
					if (pl1Rot == "down") {
						player1Mouth.addPoint(
								(float) (pl1.getCenterX() + 29 - 14),
								(float) (pl1.getCenterY() + 29 - 14));
						player1Mouth.addPoint(pl1.getCenterX() - 14,
								pl1.getCenterY() - 22);
						player1Mouth.addPoint(
								(float) (pl1.getCenterX() - 29 - 14),
								(float) (pl1.getCenterY() + 29 - 14));
					} else if (pl1Rot == "left") {
						player1Mouth.addPoint(
								(float) (pl1.getCenterX() - 29 - 14),
								(float) (pl1.getCenterY() - 29 - 14));
						player1Mouth.addPoint(pl1.getCenterX() - 6,
								pl1.getCenterY() - 14);
						player1Mouth.addPoint(
								(float) (pl1.getCenterX() - 29 - 14),
								(float) (pl1.getCenterY() + 29 - 14));
					} else if (pl1Rot == "right") {
						player1Mouth.addPoint(
								(float) (pl1.getCenterX() + 29 - 14),
								(float) (pl1.getCenterY() - 29 - 14));
						player1Mouth.addPoint(pl1.getCenterX() - 22,
								pl1.getCenterY() - 14);
						player1Mouth.addPoint(
								(float) (pl1.getCenterX() + 29 - 14),
								(float) (pl1.getCenterY() + 29 - 14));
					}
				} else if (sizeOne == 4) {
					player1Mouth = new Polygon();
					if (pl1Rot == "left") {
						player1Mouth.addPoint(pl1.getCenterX() - 60,
								pl1.getCenterY() + 20);
						player1Mouth.addPoint(pl1.getCenterX() - 20,
								pl1.getCenterY() - 20);
						player1Mouth.addPoint(pl1.getCenterX() - 60,
								pl1.getCenterY() - 60);
					} else if (pl1Rot == "right") {
						player1Mouth.addPoint(pl1.getCenterX() + 20,
								pl1.getCenterY() - 60);
						player1Mouth.addPoint(pl1.getCenterX() - 20,
								pl1.getCenterY() - 20);
						player1Mouth.addPoint(pl1.getCenterX() + 20,
								pl1.getCenterY() + 20);
					} else if (pl1Rot == "down") {
						player1Mouth.addPoint(pl1.getCenterX() - 60,
								pl1.getCenterY() + 20);
						player1Mouth.addPoint(pl1.getCenterX() - 20,
								pl1.getCenterY() - 20);
						player1Mouth.addPoint(pl1.getCenterX() + 20,
								pl1.getCenterY() + 20);
					}
				} else if (sizeOne == 5) {
					if (pl1 == p1) {
						dec1 = true;
					} else if (pl1 == p2) {
						dec2 = true;
					} else if (pl1 == p3) {
						dec3 = true;
					} else if (pl1 == p4) {
						dec4 = true;
					}
					player1Mouth = new Polygon();
					if (pl1Rot == "left") {
						player1Mouth.addPoint(
								(float) (pl1.getCenterX() - 30 - 40 * 1.5),
								(float) (pl1.getCenterY() - 30 - 40 * 1.5));
						player1Mouth.addPoint(pl1.getCenterX() - 30,
								pl1.getCenterY() - 30);
						player1Mouth.addPoint(
								(float) (pl1.getCenterX() - 30 - 40 * 1.5),
								(float) (pl1.getCenterY() - 30 + 40 * 1.5));
					} else if (pl1Rot == "right") {
						player1Mouth.addPoint(
								(float) (pl1.getCenterX() - 30 + 40 * 1.5),
								(float) (pl1.getCenterY() - 30 - 40 * 1.5));
						player1Mouth.addPoint(pl1.getCenterX() - 30,
								pl1.getCenterY() - 30);
						player1Mouth.addPoint(
								(float) (pl1.getCenterX() - 30 + 40 * 1.5),
								(float) (pl1.getCenterY() - 30 + 40 * 1.5));
					} else if (pl1Rot == "down") {
						player1Mouth.addPoint(
								(float) (pl1.getCenterX() - 30 + 40 * 1.5),
								(float) (pl1.getCenterY() - 30 + 40 * 1.5));
						player1Mouth.addPoint(pl1.getCenterX() - 30,
								pl1.getCenterY() - 30);
						player1Mouth.addPoint(
								(float) (pl1.getCenterX() - 30 - 40 * 1.5),
								(float) (pl1.getCenterY() - 30 + 40 * 1.5));
					}
				}
			}
		} else if (pl1.intersects(pl2) && pl2.getY() < pl1.getY()
				&& pl1Rot == "up"
				&& pl1.getRadius() >= pl2.getRadius() - pl2.getRadius() / 10
				&& (pl2Rot == "left" || pl2Rot == "right" || pl2Rot == "up")) {
			if (!(pl1.getX() < pl2.getX() && pl1.getY() > pl2.getY()
					&& pl1Rot == "up" && pl2Rot == "left")
					&& !(pl1.getX() > pl2.getX() && pl1.getY() > pl2.getY()
							&& pl1Rot == "up" && pl2Rot == "right")
					&& !(pl1.getX() < pl2.getX() && pl1.getY() > pl2.getY()
							&& pl1Rot == "up" && pl2Rot == "left")) {
				inc = true;
				inc2 = true;
				scoreOne += 1;
				player2Mouth = new Polygon();
				if (sizeTwo == 1 || sizeTwo == 2) {
					if (pl2Rot == "up") {
						player2Mouth.addPoint(
								(float) (pl2.getCenterX() + 20 - 9),
								(float) (pl2.getCenterY() - 20 - 9));
						player2Mouth.addPoint((float) (pl2.getCenterX() - 9),
								(float) (pl2.getCenterY() - 5));
						player2Mouth.addPoint(
								(float) (pl2.getCenterX() - 20 - 9),
								(float) (pl2.getCenterY() - 20 - 9));
					} else if (pl2Rot == "left") {
						player2Mouth.addPoint(
								(float) (pl2.getCenterX() - 20 - 9),
								(float) (pl2.getCenterY() - 20 - 9));
						player2Mouth.addPoint((float) (pl2.getCenterX() - 5),
								(float) (pl2.getCenterY() - 9));
						player2Mouth.addPoint(
								(float) (pl2.getCenterX() - 20 - 9),
								(float) (pl2.getCenterY() + 20 - 9));
					} else if (pl2Rot == "right") {
						player2Mouth.addPoint(
								(float) (pl2.getCenterX() + 20 - 9),
								(float) (pl2.getCenterY() + 20 - 9));
						player2Mouth.addPoint((float) (pl2.getCenterX() - 13),
								(float) (pl2.getCenterY() - 9));
						player2Mouth.addPoint(
								(float) (pl2.getCenterX() + 20 - 9),
								(float) (pl2.getCenterY() - 20 - 9));
					}
				} else if (sizeTwo == 3) {
					if (pl2Rot == "up") {
						player2Mouth = new Polygon();
						player2Mouth.addPoint(
								(float) (pl2.getCenterX() + 29 - 14),
								(float) (pl2.getCenterY() - 29 - 14));
						player2Mouth.addPoint(pl2.getCenterX() - 14,
								pl2.getCenterY() - 6);
						player2Mouth.addPoint(
								(float) (pl2.getCenterX() - 29 - 14),
								(float) (pl2.getCenterY() - 29 - 14));
					} else if (pl2Rot == "left") {
						player2Mouth = new Polygon();
						player2Mouth.addPoint(
								(float) (pl2.getCenterX() - 29 - 14),
								(float) (pl2.getCenterY() - 29 - 14));
						player2Mouth.addPoint(pl2.getCenterX() - 6,
								pl2.getCenterY() - 14);
						player2Mouth.addPoint(
								(float) (pl2.getCenterX() - 29 - 14),
								(float) (pl2.getCenterY() + 29 - 14));
					} else if (pl2Rot == "right") {
						player2Mouth = new Polygon();
						player2Mouth.addPoint(
								(float) (pl2.getCenterX() + 29 - 14),
								(float) (pl2.getCenterY() - 29 - 14));
						player2Mouth.addPoint(pl2.getCenterX() - 22,
								pl2.getCenterY() - 14);
						player2Mouth.addPoint(
								(float) (pl2.getCenterX() + 29 - 14),
								(float) (pl2.getCenterY() + 29 - 14));
					}
				} else if (sizeTwo == 4) {
					if (pl2Rot == "up") {
						player2Mouth = new Polygon();
						player2Mouth.addPoint(pl2.getCenterX() - 60,
								pl2.getCenterY() - 60);
						player2Mouth.addPoint(pl2.getCenterX() - 20,
								pl2.getCenterY() - 20);
						player2Mouth.addPoint(pl2.getCenterX() + 20,
								pl2.getCenterY() - 60);
					} else if (pl2Rot == "right") {
						player2Mouth = new Polygon();
						player2Mouth.addPoint(pl2.getCenterX() + 20,
								pl2.getCenterY() - 60);
						player2Mouth.addPoint(pl2.getCenterX() - 20,
								pl2.getCenterY() - 20);
						player2Mouth.addPoint(pl2.getCenterX() + 20,
								pl2.getCenterY() + 20);
					} else if (pl2Rot == "left") {
						player2Mouth = new Polygon();
						player2Mouth.addPoint(pl2.getCenterX() - 60,
								pl2.getCenterY() - 60);
						player2Mouth.addPoint(pl2.getCenterX() - 20,
								pl2.getCenterY() - 20);
						player2Mouth.addPoint(pl2.getCenterX() - 60,
								pl2.getCenterY() + 20);
					}
				} else if (sizeTwo == 5) {
					if (pl2 == p1) {
						dec1 = true;
					} else if (pl2 == p2) {
						dec2 = true;
					} else if (pl2 == p3) {
						dec3 = true;
					} else if (pl2 == p4) {
						dec4 = true;
					}
					player2Mouth = new Polygon();
					if (pl2Rot == "up") {
						player2Mouth.addPoint(
								(float) (pl2.getCenterX() - 30 + 40 * 1.5),
								(float) (pl2.getCenterY() - 50 - (8 * 8 / 9)
										* 2 - 40 / 1.5));
						player2Mouth.addPoint(pl2.getCenterX() - 30,
								pl2.getCenterY() - 30);
						player2Mouth.addPoint(
								(float) (pl2.getCenterX() - 30 - 40 * 1.5),
								(float) (pl2.getCenterY() - 50 - (8 * 8 / 9)
										* 2 - 40 / 1.5));
					} else if (pl2Rot == "right") {
						player2Mouth.addPoint(
								(float) (pl2.getCenterX() - 30 + 40 * 1.5),
								(float) (pl2.getCenterY() - 30 - 40 * 1.5));
						player2Mouth.addPoint(pl2.getCenterX() - 30,
								pl2.getCenterY() - 30);
						player2Mouth.addPoint(
								(float) (pl2.getCenterX() - 30 + 40 * 1.5),
								(float) (pl2.getCenterY() - 30 + 40 * 1.5));
					} else if (pl2Rot == "left") {
						player2Mouth.addPoint(
								(float) (pl2.getCenterX() - 30 - 40 * 1.5),
								(float) (pl2.getCenterY() - 30 - 40 * 1.5));
						player2Mouth.addPoint(pl2.getCenterX() - 30,
								pl2.getCenterY() - 30);
						player2Mouth.addPoint(
								(float) (pl2.getCenterX() - 30 - 40 * 1.5),
								(float) (pl2.getCenterY() - 30 + 40 * 1.5));
					}

				}
				if (sizeOne == 1) {
					player1Mouth = new Polygon();
					player1Mouth
							.addPoint(
									(float) (pl1.getCenterX() + 1 - (8 * 8 / 9)
											* 2 + 40 / 1.5),
									(float) (pl1.getCenterY() - (8 * 8 / 9) * 2 - 40 / 1.5));
					player1Mouth.addPoint(pl1.getCenterX() + 1 - (8 * 8 / 9)
							* 2, pl1.getCenterY() - (8 * 8 / 9) * 2);
					player1Mouth
							.addPoint(
									(float) (pl1.getCenterX() + 1 - (8 * 8 / 9)
											* 2 - 40 / 1.5),
									(float) (pl1.getCenterY() - (8 * 8 / 9) * 2 - 40 / 1.5));
				} else if (sizeOne == 2) {
					player1Mouth = new Polygon();
					player1Mouth.addPoint(pl1.getCenterX() + 40,
							pl1.getCenterY() - 40);
					player1Mouth.addPoint(pl1.getCenterX(), pl1.getCenterY());
					player1Mouth.addPoint(pl1.getCenterX() - 40,
							pl1.getCenterY() - 40);
				}
				if (sizeOne == 3) {
					player1Mouth = new Polygon();
					player1Mouth
							.addPoint(
									(float) (pl1.getCenterX() + 20 + 40 * 1.5),
									(float) (pl1.getCenterY() - (8 * 8 / 9) * 2 - 40 / 1.5));
					player1Mouth.addPoint(pl1.getCenterX() + 20,
							pl1.getCenterY() + 20);
					player1Mouth
							.addPoint(
									(float) (pl1.getCenterX() + 20 - 40 * 1.5),
									(float) (pl1.getCenterY() - (8 * 8 / 9) * 2 - 40 / 1.5));
				} else if (sizeOne == 4) {
					player1Mouth = new Polygon();
					player1Mouth.addPoint(
							(float) (pl1.getCenterX() + 1.5 * (20 + 40 * 1.5)),
							(float) (pl1.getCenterY() + 30 - 40 * 1.5 * 1.5));
					player1Mouth.addPoint(pl1.getCenterX() + 30,
							pl1.getCenterY() + 30);
					player1Mouth.addPoint(
							(float) (pl1.getCenterX() + 1.5 * (20 - 40 * 1.5)),
							(float) (pl1.getCenterY() + 30 - 40 * 1.5 * 1.5));

				} else if (sizeOne == 5) {
					player1Mouth = new Polygon();
					player1Mouth.addPoint(
							(float) (pl1.getCenterX() + 1.5 * (+40 * 1.5)),
							(float) (pl1.getCenterY() - 40 * 1.5 * 1.5));
					player1Mouth.addPoint(pl1.getCenterX(), pl1.getCenterY());
					player1Mouth.addPoint(
							(float) (pl1.getCenterX() + 1.5 * (-40 * 1.5)),
							(float) (pl1.getCenterY() - 40 * 1.5 * 1.5));

				}
			}
		}

		if (player1Mouth != null && player2Mouth != null) {
			if (n == 1) {
				p1Mouth = player1Mouth;
				p2Mouth = player2Mouth;
			} else if (n == 2) {
				p1Mouth = player1Mouth;
				p3Mouth = player2Mouth;
			} else if (n == 3) {
				p2Mouth = player1Mouth;
				p3Mouth = player2Mouth;
			} else if (n == 4) {
				p3Mouth = player1Mouth;
				p4Mouth = player2Mouth;
			} else if (n == 5) {
				p2Mouth = player1Mouth;
				p4Mouth = player2Mouth;
			} else if (n == 6) {
				p1Mouth = player1Mouth;
				p4Mouth = player2Mouth;
			}
		}

	}

	public void collisionDetection(Circle p1, Circle p2, Polygon pl1Mouth,
			Polygon pl2Mouth, String pl1Rot, String pl2Rot, int sizeOne,
			int sizeTwo) {
		float a = (float) 1.5;
		if (p1.intersects(p2)) {
			if (p1.getX() < p2.getX()) {
				if (pl1Rot == "right"
						&& p1.getRadius() >= p2.getRadius() - p2.getRadius()
								/ 10
						&& (pl2Rot == "down" || pl2Rot == "right" || pl2Rot == "up")) {
					if (!(p2.getX() > p1.getX() && p2.getY() > p1.getY()
							&& pl2Rot == "up" && pl1Rot == "right")
							&& !(p1.getX() < p2.getX() && p1.getY() > p2.getY()
									&& pl1Rot == "right" && pl2Rot == "down")
							&& !(p1.getX() < p2.getX() && p1.getY() < p2.getY()
									&& pl1Rot == "right" && pl2Rot == "up")) {
						if (sizeOne <= 4) {
							p1.setRadius((float) (p1.getRadius() * a));
						}
						if (sizeTwo >= 2) {
							p2.setRadius((float) (p2.getRadius() / a));
						}
					}
				} else if (pl2Rot == "left"
						&& p2.getRadius() >= p1.getRadius() - p1.getRadius()
								/ 10
						&& (pl1Rot == "down" || pl1Rot == "left" || pl1Rot == "up")) {
					if (!(p1.getX() < p2.getX() && p1.getY() > p2.getY()
							&& pl1Rot == "up" && pl2Rot == "left")
							&& !(p1.getX() < p2.getX() && p1.getY() > p2.getY()
									&& pl1Rot == "up" && pl2Rot == "left")
							&& !(p1.getX() < p2.getX() && p1.getY() < p2.getY()
									&& pl1Rot == "down" && pl2Rot == "left")) {
						if (sizeTwo <= 4) {
							p2.setRadius((float) (p2.getRadius() * a));
						}
						if (sizeOne >= 2) {
							p1.setRadius((float) (p1.getRadius() / a));
						}
					}
				}
				p1.setX(p1.getX() - 60);
				pl1Mouth.setX(pl1Mouth.getX() - 60);
				float diffX = p1.getX() - pl1Mouth.getX();
				if (p1.getX() < 0) {
					p1.setX(20);
					pl1Mouth.setX(p1.getX() - diffX);
					if (gameover != true) {
						try {
							AudioInputStream audioIn = AudioSystem
									.getAudioInputStream(hitHurt);
							Clip clip = AudioSystem.getClip();
							clip.open(audioIn);
							clip.start();
						} catch (UnsupportedAudioFileException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						} catch (LineUnavailableException e) {
							e.printStackTrace();
						}
					}
				}

				p2.setX(p2.getX() + 60);
				pl2Mouth.setX(pl2Mouth.getX() + 60);
				float diffX2 = p2.getX() - pl2Mouth.getX();
				if (p2.getX() > WIDTH - 2 * p2.getRadius()) {
					p2.setX(WIDTH - 2 * p2.getRadius());
					pl2Mouth.setX(p2.getX() - diffX2);
					if (gameover != true) {
						try {
							AudioInputStream audioIn = AudioSystem
									.getAudioInputStream(hitHurt);
							Clip clip = AudioSystem.getClip();
							clip.open(audioIn);
							clip.start();
						} catch (UnsupportedAudioFileException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						} catch (LineUnavailableException e) {
							e.printStackTrace();
						}
					}
				}
			} else if (p2.getX() < p1.getX()) {
				if (pl1Rot == "left"
						&& p2.getY() <= p1.getY()
						&& p1.getRadius() >= p2.getRadius() - p2.getRadius()
								/ 10
						&& (pl2Rot == "down" || pl2Rot == "left" || pl2Rot == "up")) {
					if (!(p2.getX() < p1.getX() && p2.getY() > p1.getY()
							&& pl2Rot == "up" && pl1Rot == "left")
							&& !(p2.getX() < p1.getX() && p2.getY() < p1.getY()
									&& pl2Rot == "down" && pl1Rot == "left")) {
						if (sizeOne <= 4) {
							p1.setRadius((float) (p1.getRadius() * 1.5));
						}
						if (sizeTwo >= 2) {
							p2.setRadius((float) (p2.getRadius() / 1.5));
						}
					}
				} else if (pl2Rot == "right"
						&& p2.getRadius() >= p1.getRadius() - p1.getRadius()
								/ 10
						&& (pl1Rot == "down" || pl1Rot == "right" || pl1Rot == "up")) {
					if (!(p1.getX() > p2.getX() && p1.getY() > p2.getY()
							&& pl1Rot == "up" && pl2Rot == "right")
							&& !(p2.getX() < p1.getX() && p1.getY() < p2.getY()
									&& pl1Rot == "down" && pl2Rot == "right")) {
						if (sizeTwo <= 4) {
							p2.setRadius((float) (p2.getRadius() * 1.5));
						}
						if (sizeOne >= 2) {
							p1.setRadius((float) (p1.getRadius() / 1.5));
						}
					}
				}
				p1.setX(p1.getX() + 60);
				pl1Mouth.setX(pl1Mouth.getX() + 60);
				float diffX3 = p1.getX() - pl1Mouth.getX();
				if (p1.getX() > WIDTH - 2 * p1.getRadius()) {
					p1.setX(WIDTH - 2 * p1.getRadius());
					pl1Mouth.setX(p1.getX() - diffX3);
					if (gameover != true) {
						try {
							AudioInputStream audioIn = AudioSystem
									.getAudioInputStream(hitHurt);
							Clip clip = AudioSystem.getClip();
							clip.open(audioIn);
							clip.start();
						} catch (UnsupportedAudioFileException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						} catch (LineUnavailableException e) {
							e.printStackTrace();
						}
					}

				}

				p2.setX(p2.getX() - 60);
				pl2Mouth.setX(pl2Mouth.getX() - 60);
				float diffX4 = p2.getX() - pl2Mouth.getX();
				if (p2.getX() < 0) {
					p2.setX(20);
					pl2Mouth.setX(p2.getX() - diffX4);
					if (gameover != true) {
						try {
							AudioInputStream audioIn = AudioSystem
									.getAudioInputStream(hitHurt);
							Clip clip = AudioSystem.getClip();
							clip.open(audioIn);
							clip.start();
						} catch (UnsupportedAudioFileException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						} catch (LineUnavailableException e) {
							e.printStackTrace();
						}
					}
				}

			}
			if (p1.getY() < p2.getY()) {
				if (pl1Rot == "down"
						&& p1.getRadius() >= p2.getRadius() - p2.getRadius()
								/ 10
						&& (pl2Rot == "down" || pl2Rot == "left" || pl2Rot == "right")) {
					if (!(p1.getX() < p2.getX() && p1.getY() < p2.getY()
							&& pl1Rot == "down" && pl2Rot == "left")
							&& !(p2.getX() < p1.getX() && p1.getY() < p2.getY()
									&& pl1Rot == "down" && pl2Rot == "right")) {
						if (sizeOne <= 4) {
							p1.setRadius((float) (p1.getRadius() * 1.5));
						}
						if (sizeTwo >= 2) {
							p2.setRadius((float) (p2.getRadius() / 1.5));
						}
					}
				} else if (pl2Rot == "up"
						&& p2.getRadius() >= p1.getRadius() - p1.getRadius()
								/ 10
						&& (pl1Rot == "left" || pl1Rot == "right" || pl1Rot == "up")) {
					if (!(p2.getX() < p1.getX() && p2.getY() > p1.getY()
							&& pl2Rot == "up" && pl1Rot == "left")
							&& !(p2.getX() > p1.getX() && p2.getY() > p1.getY()
									&& pl2Rot == "up" && pl1Rot == "right")
							&& !(p1.getX() < p2.getX() && p1.getY() < p2.getY()
									&& pl1Rot == "right" && pl2Rot == "up")) {
						if (sizeTwo <= 4) {
							p2.setRadius((float) (p2.getRadius() * 1.5));
						}
						if (sizeOne >= 2) {
							p1.setRadius((float) (p1.getRadius() / 1.5));
						}
					}

				}
				p1.setY(p1.getY() - 60);
				pl1Mouth.setY(pl1Mouth.getY() - 60);
				float diffY = p1.getY() - pl1Mouth.getY();
				if (p1.getY() < 0) {
					p1.setY(20);
					pl1Mouth.setY(p1.getY() - diffY);
					if (gameover != true) {
						try {
							AudioInputStream audioIn = AudioSystem
									.getAudioInputStream(hitHurt);
							Clip clip = AudioSystem.getClip();
							clip.open(audioIn);
							clip.start();
						} catch (UnsupportedAudioFileException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						} catch (LineUnavailableException e) {
							e.printStackTrace();
						}
					}
				}

				p2.setY(p2.getY() + 60);
				pl2Mouth.setY(pl2Mouth.getY() + 60);
				float diffY2 = p2.getY() - pl2Mouth.getY();
				if (p2.getY() > HEIGHT - 2 * p2.getRadius()) {
					p2.setY(HEIGHT - 2 * p2.getRadius());
					pl2Mouth.setY(p2.getY() - diffY2);
					if (gameover != true) {
						try {
							AudioInputStream audioIn = AudioSystem
									.getAudioInputStream(hitHurt);
							Clip clip = AudioSystem.getClip();
							clip.open(audioIn);
							clip.start();
						} catch (UnsupportedAudioFileException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						} catch (LineUnavailableException e) {
							e.printStackTrace();
						}
					}
				}
			} else if (p2.getY() < p1.getY()) {
				if (pl2Rot == "down"
						&& p2.getRadius() >= p1.getRadius() - p1.getRadius()
								/ 10
						&& (pl1Rot == "down" || pl1Rot == "left" || pl1Rot == "right")) {
					if (!(p1.getX() < p2.getX() && p1.getY() > p2.getY()
							&& pl1Rot == "right" && pl2Rot == "down")
							&& !(p2.getX() < p1.getX() && p2.getY() < p1.getY()
									&& pl2Rot == "down" && pl1Rot == "left")) {
						if (sizeOne >= 2) {
							p1.setRadius((float) (p1.getRadius() / 1.5));
						}
						if (sizeTwo <= 4) {
							p2.setRadius((float) (p2.getRadius() * 1.5));
						}
					}
				} else if (pl1Rot == "up"
						&& p1.getRadius() >= p2.getRadius() - p2.getRadius()
								/ 10
						&& (pl2Rot == "left" || pl2Rot == "right" || pl2Rot == "up")) {
					if (!(p1.getX() < p2.getX() && p1.getY() > p2.getY()
							&& pl1Rot == "up" && pl2Rot == "left")
							&& !(p1.getX() > p2.getX() && p1.getY() > p2.getY()
									&& pl1Rot == "up" && pl2Rot == "right")) {
						if (sizeTwo >= 2) {
							p2.setRadius((float) (p2.getRadius() / 1.5));
						}
						if (sizeOne <= 4) {
							p1.setRadius((float) (p1.getRadius() * 1.5));
						}
					}
				}
				p1.setY(p1.getY() + 60);
				pl1Mouth.setY(pl1Mouth.getY() + 60);
				float diffY3 = p1.getY() - pl1Mouth.getY();
				if (p1.getY() > HEIGHT - 2 * p1.getRadius()) {
					p1.setY(HEIGHT - 2 * p1.getRadius());
					pl1Mouth.setY(p1.getY() - diffY3);
					if (gameover != true) {
						try {
							AudioInputStream audioIn = AudioSystem
									.getAudioInputStream(hitHurt);
							Clip clip = AudioSystem.getClip();
							clip.open(audioIn);
							clip.start();
						} catch (UnsupportedAudioFileException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						} catch (LineUnavailableException e) {
							e.printStackTrace();
						}
					}
				}

				p2.setY(p2.getY() - 60);
				pl2Mouth.setY(pl2Mouth.getY() - 60);
				float diffY4 = p2.getY() - pl2Mouth.getY();
				if (p2.getY() < 0) {
					p2.setY(20);
					pl2Mouth.setY(p2.getY() - diffY4);
					if (gameover != true) {
						try {
							AudioInputStream audioIn = AudioSystem
									.getAudioInputStream(hitHurt);
							Clip clip = AudioSystem.getClip();
							clip.open(audioIn);
							clip.start();
						} catch (UnsupportedAudioFileException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						} catch (LineUnavailableException e) {
							e.printStackTrace();
						}
					}

				}

			}

		}

	}

	public void respawnFood() {
		int randX1, randX2, randX3, randX4, randY1, randY2, randY3, randY4;
		Random rand = new Random();
		randX1 = rand.nextInt(2 - 0 + 1) + 0;
		randY1 = rand.nextInt(5 - 3 + 1) + 3;

		randX2 = rand.nextInt(2 - 0 + 1) + 0;
		randY2 = rand.nextInt(5 - 3 + 1) + 3;
		while (randX2 == randX1 && randY2 == randY1) {
			randX2 = rand.nextInt(2 - 0 + 1) + 0;
			randY2 = rand.nextInt(5 - 3 + 1) + 3;
		}

		randX3 = rand.nextInt(2 - 0 + 1) + 0;
		randY3 = rand.nextInt(5 - 3 + 1) + 3;
		while (randX3 == randX1 && randY3 == randY1 || randX3 == randX2
				&& randY3 == randY2) {
			randX3 = rand.nextInt(2 - 0 + 1) + 0;
			randY3 = rand.nextInt(5 - 3 + 1) + 3;
		}

		randX4 = rand.nextInt(2 - 0 + 1) + 0;
		randY4 = rand.nextInt(5 - 3 + 1) + 3;
		while (randX4 == randX1 && randY4 == randY1 || randX4 == randX2
				&& randY4 == randY2 || randX4 == randX3 && randY4 == randY3) {
			randX4 = rand.nextInt(2 - 0 + 1) + 0;
			randY4 = rand.nextInt(5 - 3 + 1) + 3;
		}

		food1 = new Circle(spawnLocations[randX1], spawnLocations[randY1], 10);
		food2 = new Circle(spawnLocations[randX2], spawnLocations[randY2], 10);
		food3 = new Circle(spawnLocations[randX3], spawnLocations[randY3], 10);
		food4 = new Circle(spawnLocations[randX4], spawnLocations[randY4], 10);
	}

	public void calcResults(int size) {
		String pResult = "";
		if (set && set2 && set3 && set4) {
			if (size == 0 && numDef == 0) {
				pResult = "Fourth! ";
				numDef = 1;
			} else if (numDef == 1 && pResult == "" && size == 0) {
				pResult = "Third! ";
				numDef = 2;
			} else if (numDef == 2 && pResult == "" && size == 0) {
				pResult = "Second! ";
				numDef = 3;
			}
		} else if (set && set2 && set3 && !set4) {
			if (size == 0 && numDef == 0) {
				pResult = "Third! ";
				numDef = 1;
			} else if (numDef == 1 && pResult == "" && size == 0) {
				pResult = "Second! ";
				numDef = 2;
			}
		} else if (set && set2 && !set3 && !set4) {
			if (size == 0 && numDef == 0) {
				pResult = "Second! ";
				numDef = 1;
			}
		}
		if (c == 1) {
			p1Result = pResult;
		} else if (c == 2) {
			p2Result = pResult;
		} else if (c == 3) {
			p3Result = pResult;
		} else if (c == 4) {
			p4Result = pResult;
		}
	}

	public void moveP1(float position) {
		movePlayer(p1, p1Mouth, position);
		if (pl1Rot == "up") {
			p1Mouth.setCenterY(p1Mouth.getCenterY() + 55 * j1 * h);
			p1Mouth = (Polygon) p1Mouth.transform(Transform
					.createRotateTransform((float) Math.toRadians(180),
							p1Mouth.getCenterX(), p1Mouth.getCenterY()));

			pl1Rot = "down";
		} else if (pl1Rot == "left") {
			p1Mouth.setCenterX((float) (p1Mouth.getCenterX() + 27.5 * j1 * h));
			p1Mouth.setCenterY((float) (p1Mouth.getCenterY() + 27.5 * j1 * h));
			p1Mouth = (Polygon) p1Mouth.transform(Transform
					.createRotateTransform((float) Math.toRadians(-90),
							p1Mouth.getCenterX(), p1Mouth.getCenterY()));

			pl1Rot = "down";
		} else if (pl1Rot == "right") {
			p1Mouth.setCenterX((float) (p1Mouth.getCenterX() - 27.5 * j1 * h));
			p1Mouth.setCenterY((float) (p1Mouth.getCenterY() + 27.5 * j1 * h));
			p1Mouth = (Polygon) p1Mouth.transform(Transform
					.createRotateTransform((float) Math.toRadians(90),
							p1Mouth.getCenterX(), p1Mouth.getCenterY()));

			pl1Rot = "down";
		}
	}

	public void moveP1Up(float position) {
		movePlayerUp(p1, p1Mouth, position);

		if (pl1Rot == "down") {
			p1Mouth.setCenterY(p1Mouth.getCenterY() - 55 * j1 * h);
			p1Mouth = (Polygon) p1Mouth.transform(Transform
					.createRotateTransform((float) Math.toRadians(180),
							p1Mouth.getCenterX(), p1Mouth.getCenterY()));
			pl1Rot = "up";
		} else if (pl1Rot == "left") {
			p1Mouth.setCenterX((float) (p1Mouth.getCenterX() + 27.5 * j1 * h));
			p1Mouth.setCenterY((float) (p1Mouth.getCenterY() - 27.5 * j1 * h));
			p1Mouth = (Polygon) p1Mouth.transform(Transform
					.createRotateTransform((float) Math.toRadians(90),
							p1Mouth.getCenterX(), p1Mouth.getCenterY()));
			pl1Rot = "up";
		} else if (pl1Rot == "right") {
			p1Mouth.setCenterX((float) (p1Mouth.getCenterX() - 27.5 * j1 * h));
			p1Mouth.setCenterY((float) (p1Mouth.getCenterY() - 27.5 * j1 * h));
			p1Mouth = (Polygon) p1Mouth.transform(Transform
					.createRotateTransform((float) Math.toRadians(-90),
							p1Mouth.getCenterX(), p1Mouth.getCenterY()));
			pl1Rot = "up";
		}
	}

	public void moveP1Left(float position) {
		movePlayerLeft(p1, p1Mouth, position);

		if (pl1Rot == "down") {
			p1Mouth.setCenterX((float) (p1Mouth.getCenterX() - 27.5 * j1 * h));
			p1Mouth.setCenterY((float) (p1Mouth.getCenterY() - 27.5 * j1 * h));
			p1Mouth = (Polygon) p1Mouth.transform(Transform
					.createRotateTransform((float) Math.toRadians(90),
							p1Mouth.getCenterX(), p1Mouth.getCenterY()));
			pl1Rot = "left";
		} else if (pl1Rot == "up") {
			p1Mouth.setCenterX((float) (p1Mouth.getCenterX() - 27.5 * j1 * h));
			p1Mouth.setCenterY((float) (p1Mouth.getCenterY() + 27.5 * j1 * h));
			p1Mouth = (Polygon) p1Mouth.transform(Transform
					.createRotateTransform((float) Math.toRadians(-90),
							p1Mouth.getCenterX(), p1Mouth.getCenterY()));
			pl1Rot = "left";
		} else if (pl1Rot == "right") {
			p1Mouth.setCenterX(p1Mouth.getCenterX() - 55 * j1 * h);
			p1Mouth = (Polygon) p1Mouth.transform(Transform
					.createRotateTransform((float) Math.toRadians(180),
							p1Mouth.getCenterX(), p1Mouth.getCenterY()));
			pl1Rot = "left";
		}
	}

	public void moveP1Right(float position) {
		movePlayerRight(p1, p1Mouth, position);

		if (pl1Rot == "up") {
			p1Mouth.setCenterX((float) (p1Mouth.getCenterX() + 27.5 * j1 * h));
			p1Mouth.setCenterY((float) (p1Mouth.getCenterY() + 27.5 * j1 * h));
			p1Mouth = (Polygon) p1Mouth.transform(Transform
					.createRotateTransform((float) Math.toRadians(90),
							p1Mouth.getCenterX(), p1Mouth.getCenterY()));
			pl1Rot = "right";
		} else if (pl1Rot == "left") {
			p1Mouth.setCenterX(p1Mouth.getCenterX() + 55 * j1 * h);
			p1Mouth = (Polygon) p1Mouth.transform(Transform
					.createRotateTransform((float) Math.toRadians(180),
							p1Mouth.getCenterX(), p1Mouth.getCenterY()));
			pl1Rot = "right";
		} else if (pl1Rot == "down") {
			p1Mouth.setCenterX((float) (p1Mouth.getCenterX() + 27.5 * j1 * h));
			p1Mouth.setCenterY((float) (p1Mouth.getCenterY() - 27.5 * j1 * h));
			p1Mouth = (Polygon) p1Mouth.transform(Transform
					.createRotateTransform((float) Math.toRadians(-90),
							p1Mouth.getCenterX(), p1Mouth.getCenterY()));

			pl1Rot = "right";
		}
	}

	public void moveP2(float position) {
		movePlayer(p2, p2Mouth, position);
		if (pl2Rot == "up") {
			p2Mouth.setCenterY(p2Mouth.getCenterY() + 55 * j2 * h2);
			p2Mouth = (Polygon) p2Mouth.transform(Transform
					.createRotateTransform((float) Math.toRadians(180),
							p2Mouth.getCenterX(), p2Mouth.getCenterY()));
			pl2Rot = "down";
		} else if (pl2Rot == "left") {
			p2Mouth.setCenterX((float) (p2Mouth.getCenterX() + 27.5 * j2 * h2));
			p2Mouth.setCenterY((float) (p2Mouth.getCenterY() + 27.5 * j2 * h2));
			p2Mouth = (Polygon) p2Mouth.transform(Transform
					.createRotateTransform((float) Math.toRadians(-90),
							p2Mouth.getCenterX(), p2Mouth.getCenterY()));
			pl2Rot = "down";
		} else if (pl2Rot == "right") {
			p2Mouth.setCenterX((float) (p2Mouth.getCenterX() - 27.5 * j2 * h2));
			p2Mouth.setCenterY((float) (p2Mouth.getCenterY() + 27.5 * j2 * h2));
			p2Mouth = (Polygon) p2Mouth.transform(Transform
					.createRotateTransform((float) Math.toRadians(90),
							p2Mouth.getCenterX(), p2Mouth.getCenterY()));
			pl2Rot = "down";
		}

	}

	public void moveP2Up(float position) {
		movePlayerUp(p2, p2Mouth, position);
		if (pl2Rot == "down") {
			p2Mouth.setCenterY((p2Mouth.getCenterY() - 55 * j2 * h2));
			p2Mouth = (Polygon) p2Mouth.transform(Transform
					.createRotateTransform((float) Math.toRadians(180),
							p2Mouth.getCenterX(), p2Mouth.getCenterY()));
			pl2Rot = "up";
		} else if (pl2Rot == "left") {
			p2Mouth.setCenterX((float) (p2Mouth.getCenterX() + 27.5 * j2 * h2));
			p2Mouth.setCenterY((float) (p2Mouth.getCenterY() - 27.5 * j2 * h2));
			p2Mouth = (Polygon) p2Mouth.transform(Transform
					.createRotateTransform((float) Math.toRadians(90),
							p2Mouth.getCenterX(), p2Mouth.getCenterY()));
			pl2Rot = "up";
		} else if (pl2Rot == "right") {
			p2Mouth.setCenterX((float) (p2Mouth.getCenterX() - 27.5 * j2 * h2));
			p2Mouth.setCenterY((float) (p2Mouth.getCenterY() - 27.5 * j2 * h2));
			p2Mouth = (Polygon) p2Mouth.transform(Transform
					.createRotateTransform((float) Math.toRadians(-90),
							p2Mouth.getCenterX(), p2Mouth.getCenterY()));
			pl2Rot = "up";
		}

	}

	public void moveP2Left(float position) {
		movePlayerLeft(p2, p2Mouth, position);
		if (pl2Rot == "up") {
			p2Mouth.setCenterX((float) (p2Mouth.getCenterX() - 27.5 * j2 * h2));
			p2Mouth.setCenterY((float) (p2Mouth.getCenterY() + 27.5 * j2 * h2));
			p2Mouth = (Polygon) p2Mouth.transform(Transform
					.createRotateTransform((float) Math.toRadians(-90),
							p2Mouth.getCenterX(), p2Mouth.getCenterY()));
			pl2Rot = "left";
		} else if (pl2Rot == "down") {
			p2Mouth.setCenterX((float) (p2Mouth.getCenterX() - 27.5 * j2 * h2));
			p2Mouth.setCenterY((float) (p2Mouth.getCenterY() - 27.5 * j2 * h2));
			p2Mouth = (Polygon) p2Mouth.transform(Transform
					.createRotateTransform((float) Math.toRadians(90),
							p2Mouth.getCenterX(), p2Mouth.getCenterY()));
			pl2Rot = "left";
		} else if (pl2Rot == "right") {
			p2Mouth.setCenterX((float) (p2Mouth.getCenterX() - 55 * j2 * h2));
			p2Mouth = (Polygon) p2Mouth.transform(Transform
					.createRotateTransform((float) Math.toRadians(180),
							p2Mouth.getCenterX(), p2Mouth.getCenterY()));
			pl2Rot = "left";
		}

	}

	public void moveP2Right(float position) {
		movePlayerRight(p2, p2Mouth, position);
		if (pl2Rot == "up") {
			p2Mouth.setCenterX((float) (p2Mouth.getCenterX() + 27.5 * j2 * h2));
			p2Mouth.setCenterY((float) (p2Mouth.getCenterY() + 27.5 * j2 * h2));
			p2Mouth = (Polygon) p2Mouth.transform(Transform
					.createRotateTransform((float) Math.toRadians(90),
							p2Mouth.getCenterX(), p2Mouth.getCenterY()));
			pl2Rot = "right";
		} else if (pl2Rot == "left") {
			p2Mouth.setCenterX((p2Mouth.getCenterX() + 55 * j2 * h2));
			p2Mouth = (Polygon) p2Mouth.transform(Transform
					.createRotateTransform((float) Math.toRadians(180),
							p2Mouth.getCenterX(), p2Mouth.getCenterY()));
			pl2Rot = "right";
		} else if (pl2Rot == "down") {
			p2Mouth.setCenterX((float) (p2Mouth.getCenterX() + 27.5 * j2 * h2));
			p2Mouth.setCenterY((float) (p2Mouth.getCenterY() - 27.5 * j2 * h2));
			p2Mouth = (Polygon) p2Mouth.transform(Transform
					.createRotateTransform((float) Math.toRadians(-90),
							p2Mouth.getCenterX(), p2Mouth.getCenterY()));
			pl2Rot = "right";
		}
	}

	public void moveP3(float position) {
		movePlayer(p3, p3Mouth, position);
		if (pl3Rot == "up") {
			p3Mouth.setCenterY((p3Mouth.getCenterY() + 55 * j3 * h3));
			p3Mouth = (Polygon) p3Mouth.transform(Transform
					.createRotateTransform((float) Math.toRadians(180),
							p3Mouth.getCenterX(), p3Mouth.getCenterY()));
			pl3Rot = "down";
		} else if (pl3Rot == "left") {
			p3Mouth.setCenterX((float) (p3Mouth.getCenterX() + 27.5 * j3 * h3));
			p3Mouth.setCenterY((float) (p3Mouth.getCenterY() + 27.5 * j3 * h3));
			p3Mouth = (Polygon) p3Mouth.transform(Transform
					.createRotateTransform((float) Math.toRadians(-90),
							p3Mouth.getCenterX(), p3Mouth.getCenterY()));
			pl3Rot = "down";
		} else if (pl3Rot == "right") {
			p3Mouth.setCenterX((float) (p3Mouth.getCenterX() - 27.5 * j3 * h3));
			p3Mouth.setCenterY((float) (p3Mouth.getCenterY() + 27.5 * j3 * h3));
			p3Mouth = (Polygon) p3Mouth.transform(Transform
					.createRotateTransform((float) Math.toRadians(90),
							p3Mouth.getCenterX(), p3Mouth.getCenterY()));
			pl3Rot = "down";
		}

	}

	public void moveP3Up(float position) {
		movePlayerUp(p3, p3Mouth, position);
		if (pl3Rot == "down") {
			p3Mouth.setCenterY((p3Mouth.getCenterY() - 55 * j3 * h3));
			p3Mouth = (Polygon) p3Mouth.transform(Transform
					.createRotateTransform((float) Math.toRadians(180),
							p3Mouth.getCenterX(), p3Mouth.getCenterY()));
			pl3Rot = "up";
		} else if (pl3Rot == "left") {
			p3Mouth.setCenterX((float) (p3Mouth.getCenterX() + 27.5 * j3 * h3));
			p3Mouth.setCenterY((float) (p3Mouth.getCenterY() - 27.5 * j3 * h3));
			p3Mouth = (Polygon) p3Mouth.transform(Transform
					.createRotateTransform((float) Math.toRadians(90),
							p3Mouth.getCenterX(), p3Mouth.getCenterY()));
			pl3Rot = "up";
		} else if (pl3Rot == "right") {
			p3Mouth.setCenterX((float) (p3Mouth.getCenterX() - 27.5 * j3 * h3));
			p3Mouth.setCenterY((float) (p3Mouth.getCenterY() - 27.5 * j3 * h3));
			p3Mouth = (Polygon) p3Mouth.transform(Transform
					.createRotateTransform((float) Math.toRadians(-90),
							p3Mouth.getCenterX(), p3Mouth.getCenterY()));
			pl3Rot = "up";
		}

	}

	public void moveP3Left(float position) {
		movePlayerLeft(p3, p3Mouth, position);
		if (pl3Rot == "up") {
			p3Mouth.setCenterX((float) (p3Mouth.getCenterX() - 27.5 * j3 * h3));
			p3Mouth.setCenterY((float) (p3Mouth.getCenterY() + 27.5 * j3 * h3));
			p3Mouth = (Polygon) p3Mouth.transform(Transform
					.createRotateTransform((float) Math.toRadians(-90),
							p3Mouth.getCenterX(), p3Mouth.getCenterY()));
			pl3Rot = "left";
		} else if (pl3Rot == "down") {
			p3Mouth.setCenterX((float) (p3Mouth.getCenterX() - 27.5 * j3 * h3));
			p3Mouth.setCenterX((float) (p3Mouth.getCenterY() - 27.5 * j3 * h3));
			p3Mouth = (Polygon) p3Mouth.transform(Transform
					.createRotateTransform((float) Math.toRadians(90),
							p3Mouth.getCenterX(), p3Mouth.getCenterY()));
			pl3Rot = "left";
		} else if (pl3Rot == "right") {
			p3Mouth.setCenterX((float) (p3Mouth.getCenterX() - 55 * j3 * h3));
			p3Mouth = (Polygon) p3Mouth.transform(Transform
					.createRotateTransform((float) Math.toRadians(180),
							p3Mouth.getCenterX(), p3Mouth.getCenterY()));
			pl3Rot = "left";
		}

	}

	public void moveP3Right(float position) {
		movePlayerRight(p3, p3Mouth, position);
		if (pl3Rot == "up") {
			p3Mouth.setCenterX((float) (p3Mouth.getCenterX() + 27.5 * j3 * h3));
			p3Mouth.setCenterY((float) (p3Mouth.getCenterY() + 27.5 * j3 * h3));
			p3Mouth = (Polygon) p3Mouth.transform(Transform
					.createRotateTransform((float) Math.toRadians(90),
							p3Mouth.getCenterX(), p3Mouth.getCenterY()));
			pl3Rot = "right";
		} else if (pl3Rot == "left") {
			p3Mouth.setCenterX((float) (p3Mouth.getCenterX() + 55 * j3 * h3));
			p3Mouth = (Polygon) p3Mouth.transform(Transform
					.createRotateTransform((float) Math.toRadians(180),
							p3Mouth.getCenterX(), p3Mouth.getCenterY()));
			pl3Rot = "right";
		} else if (pl3Rot == "down") {
			p3Mouth.setCenterX((float) (p3Mouth.getCenterX() + 27.5 * j3 * h3));
			p3Mouth.setCenterY((float) (p3Mouth.getCenterY() - 27.5 * j3 * h3));
			p3Mouth = (Polygon) p3Mouth.transform(Transform
					.createRotateTransform((float) Math.toRadians(-90),
							p3Mouth.getCenterX(), p3Mouth.getCenterY()));
			pl3Rot = "right";
		}
	}

	public void moveP4(float position) {
		movePlayer(p4, p4Mouth, position);
		if (pl4Rot == "up") {
			p4Mouth.setCenterX((float) (p4Mouth.getCenterY() + 55 * j4 * h4));
			p4Mouth = (Polygon) p4Mouth.transform(Transform
					.createRotateTransform((float) Math.toRadians(180),
							p4Mouth.getCenterX(), p4Mouth.getCenterY()));
			pl4Rot = "down";
		} else if (pl4Rot == "left") {
			p4Mouth.setCenterX((float) (p4Mouth.getCenterX() + 27.5 * j4 * h4));
			p4Mouth.setCenterY((float) (p4Mouth.getCenterY() + 27.5 * j4 * h4));
			p4Mouth = (Polygon) p4Mouth.transform(Transform
					.createRotateTransform((float) Math.toRadians(-90),
							p4Mouth.getCenterX(), p4Mouth.getCenterY()));
			pl4Rot = "down";
		} else if (pl4Rot == "right") {
			p4Mouth.setCenterX((float) (p4Mouth.getCenterX() - 27.5 * j4 * h4));
			p4Mouth.setCenterY((float) (p4Mouth.getCenterY() + 27.5 * j4 * h4));
			p4Mouth = (Polygon) p4Mouth.transform(Transform
					.createRotateTransform((float) Math.toRadians(90),
							p4Mouth.getCenterX(), p4Mouth.getCenterY()));
			pl4Rot = "down";
		}
	}

	public void moveP4Up(float position) {
		movePlayerUp(p4, p4Mouth, position);
		if (pl4Rot == "down") {
			p4Mouth.setCenterY((float) (p4Mouth.getCenterY() - 55 * j4 * h4));
			p4Mouth = (Polygon) p4Mouth.transform(Transform
					.createRotateTransform((float) Math.toRadians(180),
							p4Mouth.getCenterX(), p4Mouth.getCenterY()));
			pl4Rot = "up";
		} else if (pl4Rot == "left") {
			p4Mouth.setCenterX((float) (p4Mouth.getCenterX() + 27.5 * j4 * h4));
			p4Mouth.setCenterY((float) (p4Mouth.getCenterY() - 27.5 * j4 * h4));
			p4Mouth = (Polygon) p4Mouth.transform(Transform
					.createRotateTransform((float) Math.toRadians(90),
							p4Mouth.getCenterX(), p4Mouth.getCenterY()));
			pl4Rot = "up";
		} else if (pl4Rot == "right") {
			p4Mouth.setCenterX((float) (p4Mouth.getCenterX() - 27.5 * j4 * h4));
			p4Mouth.setCenterY((float) (p4Mouth.getCenterY() - 27.5 * j4 * h4));
			p4Mouth = (Polygon) p4Mouth.transform(Transform
					.createRotateTransform((float) Math.toRadians(-90),
							p4Mouth.getCenterX(), p4Mouth.getCenterY()));
			pl4Rot = "up";
		}
	}

	public void moveP4Left(float position) {
		movePlayerLeft(p4, p4Mouth, position);
		if (pl4Rot == "up") {
			p4Mouth.setCenterX((float) (p4Mouth.getCenterX() - 27.5 * j4 * h4));
			p4Mouth.setCenterY((float) (p4Mouth.getCenterY() + 27.5 * j4 * h4));
			p4Mouth = (Polygon) p4Mouth.transform(Transform
					.createRotateTransform((float) Math.toRadians(-90),
							p4Mouth.getCenterX(), p4Mouth.getCenterY()));
			pl4Rot = "left";
		} else if (pl4Rot == "down") {
			p4Mouth.setCenterX((float) (p4Mouth.getCenterX() - 27.5 * j4 * h4));
			p4Mouth.setCenterY((float) (p4Mouth.getCenterY() - 27.5 * j4 * h4));
			p4Mouth = (Polygon) p4Mouth.transform(Transform
					.createRotateTransform((float) Math.toRadians(90),
							p4Mouth.getCenterX(), p4Mouth.getCenterY()));
			pl4Rot = "left";
		} else if (pl4Rot == "right") {
			p4Mouth.setCenterX((float) (p4Mouth.getCenterX() - 55 * j4 * h4));
			p4Mouth = (Polygon) p4Mouth.transform(Transform
					.createRotateTransform((float) Math.toRadians(180),
							p4Mouth.getCenterX(), p4Mouth.getCenterY()));
			pl4Rot = "left";
		}
	}

	public void moveP4Right(float position) {
		movePlayerRight(p4, p4Mouth, position);
		if (pl4Rot == "up") {
			p4Mouth.setCenterX((float) (p4Mouth.getCenterX() + 27.5 * j4 * h4));
			p4Mouth.setCenterY((float) (p4Mouth.getCenterY() + 27.5 * j4 * h4));
			p4Mouth = (Polygon) p4Mouth.transform(Transform
					.createRotateTransform((float) Math.toRadians(90),
							p4Mouth.getCenterX(), p4Mouth.getCenterY()));
			pl4Rot = "right";
		} else if (pl4Rot == "left") {
			p4Mouth.setCenterX((float) (p4Mouth.getCenterX() + 55 * j4 * h4));
			p4Mouth = (Polygon) p4Mouth.transform(Transform
					.createRotateTransform((float) Math.toRadians(180),
							p4Mouth.getCenterX(), p4Mouth.getCenterY()));
			pl4Rot = "right";
		} else if (pl4Rot == "down") {
			p4Mouth.setCenterX((float) (p4Mouth.getCenterX() + 27.5 * j4 * h4));
			p4Mouth.setCenterY((float) (p4Mouth.getCenterY() - 27.5 * j4 * h4));
			p4Mouth = (Polygon) p4Mouth.transform(Transform
					.createRotateTransform((float) Math.toRadians(-90),
							p4Mouth.getCenterX(), p4Mouth.getCenterY()));
			pl4Rot = "right";
		}
	}
}
