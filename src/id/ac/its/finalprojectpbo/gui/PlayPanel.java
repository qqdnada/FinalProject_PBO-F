package id.ac.its.finalprojectpbo.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import id.ac.its.finalprojectpbo.game.DrawUtils;
import id.ac.its.finalprojectpbo.game.Game;
import id.ac.its.finalprojectpbo.game.GameBoard;

public class PlayPanel extends GuiPanel {
	
	private GameBoard board;
	private BufferedImage info;
	private ScoreManager score;
	private Font scoreFont;
	
	// Game over
	private GuiButton tryAgain;
	private GuiButton mainMenu;
	private GuiButton screenShot;
	private int smallButtonWidth = 160;
	private int spacing = 20;
	private int largeButtonWidth = smallButtonWidth * 2 + spacing;
	private int buttonHeight = 50;
	
	private boolean added;
	private int alpha;
	private Font gameOverFont;
	private boolean screenshot;
	
	public PlayPanel() {
		scoreFont = Game.main.deriveFont(24f);
		gameOverFont = Game.main.deriveFont(70f);
		board = new GameBoard(Game.WIDTH /2 - GameBoard.BOARD_WIDTH / 2, Game.HEIGHT - GameBoard.BOARD_HEIGHT - 20);
//		score = board.getScores();
		info = new BufferedImage(Game.WIDTH, 200, BufferedImage.TYPE_INT_RGB);
		
		mainMenu = new GuiButton(Game.WIDTH / 2 - largeButtonWidth / 2, 450, largeButtonWidth, buttonHeight);
		tryAgain = new GuiButton(mainMenu.getX(), mainMenu.getY()- spacing - buttonHeight, smallButtonWidth, buttonHeight);
		screenShot = new GuiButton(tryAgain.getX() + tryAgain.getWidth() + spacing, tryAgain.getY(), smallButtonWidth, buttonHeight);
		
		tryAgain.setText("Try Again");
		screenShot.setText("Screenshot");
		mainMenu.setText("Back to Main Menu");
		
		tryAgain.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
//				board.getScores().reset();
				board.reset();
				alpha = 0;
				
				remove(tryAgain);
//				remove(screenshot);
				remove(mainMenu);
				
				added = false;
			}
		});
		
		mainMenu.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				GuiScreen.getScreen().setCurrentPanel("Menu");
				
			}
		});
	}
	
	private void drawGui(Graphics2D g) {
		
		Graphics2D g2d = (Graphics2D)info.getGraphics();
		g2d.setColor(Color.white);
		g2d.fillRect(0, 0, info.getWidth(), info.getHeight());
		g2d.setColor(Color.lightGray);
//		g2d.setFont(scoreFont);
//		g2d.drawString("" + score.getCurrentScore(), 30, 40);
//		g2d.setColor(Color.red);
		g2d.dispose();
		g.drawImage(info, 0, 0, null);
	}
	
	public void drawGameOver(Graphics2D g) {
		
		g.setColor(new Color(222, 222, 222, alpha));
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		g.setColor(Color.red);
		g.setFont(gameOverFont);
		g.drawString("Game Over", Game.WIDTH / 2 - DrawUtils.getMessageWidth("Game Over", gameOverFont, g) /2 , 250);
	}
	
	@Override
	public void update() {
		board.update();
		if(board.isDead()) {
			alpha++;
			if(alpha > 170) alpha = 170;
		}
	}
	
	@Override
	public void render(Graphics2D g) {
		drawGui(g);
		board.render(g);
//		if(screenshot) {}
		if(board.isDead()) {
			if(!added) {
				added = true;
				add(mainMenu);
				add(tryAgain);
//				add(screenShot);
			}
			drawGameOver(g);
		}
		super.render(g);
	}
}
