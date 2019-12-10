package com.example;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class GameBoard {
	
	public static final int ROWS = 4;
	public static final int COLS = 4;
	
	private final int startigTiles = 2;
	private Tile[][] board;
	private boolean dead;
	private boolean win;
	private boolean hasStarted;
	
	//background dr gameBoard
	private BufferedImage gameBoard;
	//background+tiles
	private BufferedImage finalBoard;
	
	//render di screennya
	private int x;
	private int y;
	
	private static int SPACING = 10;
	public static int BOARD_WIDTH = (COLS + 1)*SPACING + COLS * Tile.WIDTH;
	public static int BOARD_HEIGHT = (ROWS + 1)*SPACING + ROWS * Tile.HEIGHT;
	
	public GameBoard(int x, int y) {
		this.x = x;
		this.y = y;
		board = new Tile[ROWS][COLS];
		gameBoard = new BufferedImage(BOARD_WIDTH, BOARD_HEIGHT, BufferedImage.TYPE_INT_RGB);
		finalBoard = new BufferedImage(BOARD_WIDTH, BOARD_HEIGHT, BufferedImage.TYPE_INT_RGB);
		
		createBoardImage();
	}
	
	//background dari gameBoard
	private void createBoardImage() {
		Graphics2D g = (Graphics2D) gameBoard.getGraphics();
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);
		g.setColor(Color.LIGHT_GRAY);
		
		for (int row = 0; row < ROWS; row++) {
			for(int col = 0; col < COLS; col++) {
				int x = SPACING + SPACING * col + Tile.WIDTH * col;
				int y = SPACING + SPACING * row + Tile.WIDTH * row;
				g.fillRect(x, y, Tile.ARC_WIDTH, Tile.ARC_HEIGHT);
			}
		}
		g.dispose();
	}
	
	public void render(Graphics2D g) {
		Graphics2D g2d = (Graphics2D)finalBoard.getGraphics();
		g2d.drawImage(gameBoard, 0, 0, null);
		
		//gambar tiles
		
		g.drawImage(finalBoard, x, y, null);
		g2d.dispose();
	}
	
	public void update() {
		checkKeys();
	}
	 
	private void checkKeys() {
		if(Keyboard.typed(KeyEvent.VK_LEFT)) {
			//pindah kiri tilesnya
		}
		if(Keyboard.typed(KeyEvent.VK_RIGHT)) {
			//pindah kanan tilesnya
		}
		if(Keyboard.typed(KeyEvent.VK_UP)) {
			//pindah atas tilesnya
		}
		if(Keyboard.typed(KeyEvent.VK_DOWN)) {
			//pindah bawah tilesnya
		}
		
		if(hasStarted) hasStarted = true;
	}
}
