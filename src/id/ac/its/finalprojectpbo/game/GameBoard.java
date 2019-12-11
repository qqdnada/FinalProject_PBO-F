package id.ac.its.finalprojectpbo.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.Random;

public class GameBoard {
	
	//bisa ubah rows cm cols, tp jgn lupa ubha ukuran JPanel di Game
	
	public static final int ROWS = 3;
	public static final int COLS = 3;
	
	private final int startingTiles = 2;
	private Tile[][] board;
	private boolean dead;
	private boolean win;
	private boolean hasStarted;
	
	//background dr gameBoard
	private BufferedImage gameBoard;
	//background+tiles
	private BufferedImage finalBoard;
	
	//untuk render di screennya
	private int x;
	private int y;
	
	//untuk score
	private int score = 0;
	private Font scoreFont;
	//untuk menyimpan data path ke foldernya
	private String saveDataPath;
	//bisa dibuka, diubah 
	private String fileName = "SaveData";
	
	private static int SPACING = 10;
	public static int BOARD_WIDTH = (COLS + 1)*SPACING + COLS * Tile.WIDTH;
	public static int BOARD_HEIGHT = (ROWS + 1)*SPACING + ROWS * Tile.HEIGHT;
	
	public GameBoard(int x, int y) {
		//untuk user yg bakal running jar file (dimana pun) 
		try {
//			fungsiny sama aja, dua cara ini
			saveDataPath = GameBoard.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
//			saveDataPath = System.getProperty("user.home") + "\\foldername";
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		scoreFont = Game.main.deriveFont(24f);
		this.x = x;
		this.y = y;
		board = new Tile[ROWS][COLS];
		gameBoard = new BufferedImage(BOARD_WIDTH, BOARD_HEIGHT, BufferedImage.TYPE_INT_RGB);
		finalBoard = new BufferedImage(BOARD_WIDTH, BOARD_HEIGHT, BufferedImage.TYPE_INT_RGB);
		
		loadScore();
		createBoardImage();
		start();
	}
	
	// 3 fungsi dibawah buat nyimpen data score kita
	private void createSaveData() {
		//untuk buat file baru dengan lokasi dari pathnya
		try {
			File file = new File (saveDataPath, fileName);
			FileWriter output = new  FileWriter(file);
			BufferedWriter writer = new BufferedWriter(output);
			
			//"" kosong supaya tau itu string, klo g dikasi jadinya karakter
			writer.write("" + 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void loadScore() {
		try {
			File f = new File(saveDataPath, fileName);
			//kalau file yg di input g ada, lgsng ke ke fungsi saveData buat bikin penyimpananny 
			if(!f.isFile()) createSaveData();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
			reader.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setScore() {
		FileWriter output = null;
		try {
			File f = new File(saveDataPath, fileName);
			output = new FileWriter(f);
			BufferedWriter writer = new BufferedWriter(output);
			writer.write("" + score);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

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
				g.fillRoundRect(x, y, Tile.WIDTH, Tile.HEIGHT, Tile.ARC_WIDTH, Tile.ARC_HEIGHT);
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
			//location random dan bisa dimana aja, jadi rows * cols
			int location = random.nextInt(ROWS * COLS);
			int row = location / ROWS;
			int col = location % COLS;
			Tile current = board[row][col];
			//kalo di tile itu g ada angkanya (null) di random angkanya 
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
//		BufferedImage finalBoard = new BufferedImage(BOARD_WIDTH, BOARD_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D)finalBoard.getGraphics();
		g2d.drawImage(gameBoard, 0, 0, null);
		
		//gambar tiles
		for (int row = 0; row < ROWS; row ++) {
			for (int col = 0; col < COLS; col ++) {
				Tile current = board[row][col];
				if(current == null) continue;
				current.render(g2d);
			}		
		}
		
		g.drawImage(finalBoard, x, y, null);
		g2d.dispose();
		
		g.setColor(Color.BLACK);
		g.setFont(scoreFont);
		g.drawString("Score : " + score, 30, 40);
	}
	
	public void update() {
		checkKeys();
		
//		if(score > highscore) {
//			highscore = score;
//		}
		
		for ( int row = 0; row < ROWS; row++) {
			for ( int col = 0; col < COLS; col++) {
				Tile current = board[row][col];
				
				if (current == null) continue;
				current.update();
				
				//reset posisi
				resetPosition(current, row, col);
				
				if(current.getValue() == 2048) {
					win = true;
				}
			}
		}
	}
	
	private void resetPosition(Tile current, int row, int col) {
		if(current == null) return;
		
		int x = getTileX(col);
		int y = getTileY(row);
		
		int distX = current.getX() - x;
		int distY = current.getY() - y;
		
		if(Math.abs(distX) < Tile.SLIDE_SPEED) {
			current.setX(current.getX() - distX);
		}
		
		if(Math.abs(distY) < Tile.SLIDE_SPEED) {
			current.setY(current.getY() - distY);
		}
		
		if(distX < 0) {
			current.setX(current.getX() + Tile.SLIDE_SPEED);
		}
		if(distY < 0) {
			current.setY(current.getY() + Tile.SLIDE_SPEED);
		}
		
		if(distX > 0) {
			current.setX(current.getX() - Tile.SLIDE_SPEED);
		}
		if(distY > 0) {
			current.setY(current.getY() - Tile.SLIDE_SPEED);
		}
		
	}
	
	private boolean move(int row, int col, int horizontalDirection, int verticalDirection, Direction ar) {
		boolean canMove = false;
		
		//ngambil tile nya dimanapun
		Tile current = board[row][col];
		//kalo kosong brarti g ada isinya jd ga gerak
		if (current == null) return false;
		
		boolean move = true;
		int newCol = col;
		int newRow = row;
		
		//looping selama tilenya msi bs di combine, bs di slide ke empty tile, dan belum nyampe di temboknya
		while (move) {
			newCol += horizontalDirection;
			newRow += verticalDirection;
			
			if(checkOutOfBounds(ar, newRow, newCol)) break;
			//kalo tilenya kosong
			if(board[newRow][newCol] == null) {
				//tiles yg akan dipergiin di set jadi current "sekarang"
				board[newRow][newCol] = current;
				//tiles yg lama -> yg mw ditinggalin di set kosong
				board[newRow - verticalDirection][newCol - horizontalDirection] = null;
				board[newRow][newCol].setSlideTo(new Point(newRow, newCol));
				//spaceny g ada isinya, jd bisa digerakin
				canMove = true;
			}
			//kalo tileny ga kosong dan bs di combine sm value lain
			else if (board[newRow][newCol].getValue() == current.getValue() && board[newRow][newCol].canCombine()) {
				//stelah di combine, g bs di combine lagi
				board[newRow][newCol].setCanCombine(false);
				//valuenya di kali 2 
				board[newRow][newCol].setValue(board[newRow][newCol].getValue() * 2);
				canMove = true;
				board[newRow - verticalDirection][newCol - horizontalDirection] = null;
				board[newRow][newCol].setSlideTo(new Point (newRow, newCol));
//				board[newRow][newCol].setCanCombine(true);
				board[newRow][newCol].setCombineAnimation(true);
				
				//scoring
				score += board[newRow][newCol].getValue();
			}
			else {
				move = false;
			}
		}
		return canMove;
	}
	
	//untuk periksa batasnya 
	private boolean checkOutOfBounds(Direction ar, int row, int col) {
		if(ar == Direction.LEFT) return col < 0;
		else if(ar == Direction.RIGHT) return col > COLS - 1;
		else if(ar == Direction.UP) return row < 0;
		else if(ar == Direction.DOWN) return row > ROWS - 1;
		return false;
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
					else move(row, col, horizontalDirection, verticalDirection, ar);
				}
			}
		}
		else if(ar == Direction.RIGHT) {
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
		
		else if(ar == Direction.UP) {
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
		
		else if(ar == Direction.DOWN) {
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
		else {
			System.out.println(ar + "bukan arah yg valid");
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
			//cek mati apa ga, klo mati tile ny abis
			checkDead();
		}
	}
	
	private void checkDead() {
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				//klo kosong, santuy jd bisa masuk 
				if(board[row][col] == null) return;
				//klo ga kosong, cek sekitar 
				if(checkSurroundingTiles(row, col, board[row][col])) {
					return;
				}
			}	
		}
		dead = true;
		
		//untuk score
		setScore();
	}
	
	private boolean checkSurroundingTiles(int row, int col, Tile Current) {
		if (row > 0) {
			Tile check = board[row-1][col];
			if(check == null) return true;
			if(Current.getValue() == check.getValue()) return true;
		}
		
		if(row < ROWS -1) {
			Tile check = board[row + 1][col];
			if(check == null) return true;
			if(Current.getValue() == check.getValue()) return true;
		
		}
		
		if (col > 0) {
			Tile check = board[row][col-1];
			if(check == null) return true;
			if(Current.getValue() == check.getValue()) return true;
		}
		
		if(col < COLS - 1) {
			Tile check = board[row][col+1];
			if(check == null) return true;
			if(Current.getValue() == check.getValue()) return true;
		}
		return false;
	}
	
	//can move = memprediksi posisi dari tiles, klo bisa di gerakin, function if diatas jadi true dan tidak akan bisa dirubah lagi jadi false
	private void checkKeys() {
		if(Keyboard.typed(KeyEvent.VK_LEFT)) {
			//pindah kiri tilesnya
			moveTiles(Direction.LEFT);
			if(!hasStarted) hasStarted = true;
		}
		if(Keyboard.typed(KeyEvent.VK_RIGHT)) {
			//pindah kanan tilesnya
			moveTiles(Direction.RIGHT);
			if(!hasStarted) hasStarted = true;
		}
		if(Keyboard.typed(KeyEvent.VK_UP)) {
			//pindah atas tilesnya
			moveTiles(Direction.UP);
			if(!hasStarted) hasStarted = true;
			
		}
		if(Keyboard.typed(KeyEvent.VK_DOWN)) {
			//pindah bawah tilesnya
			moveTiles(Direction.DOWN);
			if(!hasStarted) hasStarted = true;
			
		}
	}
}
