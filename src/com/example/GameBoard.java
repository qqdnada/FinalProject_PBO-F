package com.example;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.fatalcubez.game.Tile;

public class GameBoard {
	
	public static final int ROWS = 4;
	public static final int COLS = 4;
	
	private final int startingTiles = 2;
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
	
	private void start() {
		for(int i = 0; i < startingTiles; i++) {
			spawnRandom();
		}
	}
	
	private void spawnRandom() {
		//random number generator untuk angka di tilesnya 
		Random random = new Random();
		boolean notValid = true;
	
		//menetukan valid location
		while (notValid) {
			int location = random.nextInt(ROWS * COLS);
			int row = location / ROWS;
			int col = location % COLS;
			Tile current = board[row][col];
			if (current == null) {
				int value = random.nextInt(10) < 9 ? 2 : 4;
				Tile tile = new Tile(value, getTileX(col), getTileY(row));
				board[row][col] = tile;
				notValid = false;
			}
		}
	}
	
	public int getTileX(int col) {
		return SPACING + col * Tile.WIDTH + col * SPACING;
	}
	
	public int getTileY(int row) {
		return SPACING + row * Tile.HEIGHT + row * SPACING;
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
	 
	private void moveTiles(Direction ar) {
		boolean canMove = false;
		int horizontalDirection = 0;
		int verticalDirection = 0;
		
		if(ar == Direction.LEFT) {
			horizontalDirection = -1;
			for (int row = 0; row < ROWS; row++) {
				//dari kiri ke kanan, jd 0 sampe 3, tinggi ttp dr 0 ke 3
				for (int col = 0; col < COLS; col++) {
					if(!canMove) {
						canMove = move(row, col, horizontalDirection, verticalDirection, ar);
					}
					else  
						move(row, col, horizontalDirection, verticalDirection, ar);
				}
			}
		}
		if(ar == Direction.RIGHT) {
			horizontalDirection = +1;
			for (int row = 0; row < ROWS; row++) {
				//dari kanan ke kiri, jadi 3 ke 0 (kolom yg berubah bukan baris), tinggi ttp dr 0 ke 3
				for (int col = COLS-1; col >=0; col--) {
					if(!canMove) {
						canMove = move(row, col, horizontalDirection, verticalDirection, ar);
					}
					else  
						move(row, col, horizontalDirection, verticalDirection, ar);
				}
			}
		}
		
		//ke arah kanan
		// 2 2 4 8 ----> 0 2 4 8
		//dalam satu kali pencet kanan, lgsng jadi 16, ga 4 4 8, baru 8 8 baru 16
		//2 2 4 8 -----> 0 0 0 16
		
		if(ar == Direction.UP) {
			//dari bawah ke atas (baris yg berubah), ttp di kiri dari 0
			verticalDirection = -1;
			for (int row = 0; row < ROWS; row++) {
				for (int col = 0; col < COLS; col++) {
					if(!canMove) {
						canMove = move(row, col, horizontalDirection, verticalDirection, ar);
					}
					else  
						move(row, col, horizontalDirection, verticalDirection, ar);
				}
			}
		}
		
		if(ar == Direction.DOWN) {
			//dari  atas ke bawah (baris yg berubah), ttp di kiri dari 0
			verticalDirection = +1;
			for (int row = ROWS -1; row >= 0; row--) {
				for (int col = 0; col < COLS; col++) {
					if(!canMove) {
						canMove = move(row, col, horizontalDirection, verticalDirection, ar);
					}
					else  
						move(row, col, horizontalDirection, verticalDirection, ar);
				}
			}
		}
		
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				Tile current = board[row][col];
				if(current == null) continue;
				current.setCanCombine(true);
			}
		}
		
		if(canMove) {
			spawnRandom();
			//checkdead
		}
	}
	
	//can mvove = memprediksi posisi dari tiles, klo bisa di gerakin, function if diatas jadi true dan tidak akan bisa dirubah lagi jadi false
	private void checkKeys() {
		if(Keyboard.typed(KeyEvent.VK_LEFT)) {
			//pindah kiri tilesnya
			moveTiles(Direction.LEFT);
		}
		if(Keyboard.typed(KeyEvent.VK_RIGHT)) {
			//pindah kanan tilesnya
			moveTiles(Direction.RIGHT);
		}
		if(Keyboard.typed(KeyEvent.VK_UP)) {
			//pindah atas tilesnya
			moveTiles(Direction.UP);
		}
		if(Keyboard.typed(KeyEvent.VK_DOWN)) {
			//pindah bawah tilesnya
			moveTiles(Direction.DOWN);
		}
		
		if(hasStarted) hasStarted = true;
	}
}
