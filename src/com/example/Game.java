package com.example;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;


public class Game extends JPanel implements KeyListener, MouseListener, MouseMotionListener, Runnable {
	
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 330;
	public static final int HEIGHT = 450;
	public static final Font main = new Font("Arial", Font.PLAIN, 28);
	
	private Thread game;
	private boolean running;
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private GameBoard board;
	
	private long startTime;
	private long elapsed;
	private boolean set;
	
//	private GuiScreen screen;
	
	public Game() {
		setFocusable(true);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		
		board = new GameBoard(WIDTH / 2 - GameBoard.BOARD_WIDTH / 2, HEIGHT - GameBoard.BOARD_HEIGHT - 10);
//		screen = GuiScreen.getScreen();
//		screen.add("Menu", new MainMenuPanel());
//		screen.add("Play", new PlayPanel());
//		screen.setCurrentPanel("Menu");
	}
	
	//akan dipanggil 60x/seconds
	private void update() {
    	board.update();
//		screen.update();
		Keyboard.update();
	}
	
	//untuk membangun gambaran / model screen nya 
	private void render() {
		//how you draw to an image 
		//"image" adalah virtual image yang kita simpan di memori, keeps track everything that we drawing on the screen
		Graphics2D g = (Graphics2D) image.getGraphics();
		
		//clear screen dengan warna putih
		g.setColor(Color.white);
		//screen bentuk rectangle 
		g.fillRect(0, 0, WIDTH, HEIGHT);

		//render board
		board.render(g);
		
//		screen.render(g);
		
		//wajib setelah memanggil Graphics / Graphics2D, untuk mengatur
		g.dispose();
		
		//actual JPanel Graphics yang akan dimasukkan image / object nya 
		Graphics2D g2d = (Graphics2D) getGraphics();
		g2d.drawImage(image, 0, 0, null);
		g2d.dispose();
	}
	
	//thread nya   
	@Override 
	public void run() {

		//update untuk print brp kali kita update in frame per second
		//supyaa tau game kita baik" aja 
		int fps = 0, updates = 0;
		long fpsTimer = System.currentTimeMillis();
		double nsPerUpdate = 1000000000.0 / 60;
		
		//last update time in nanosecond
		double then = System.nanoTime();
		//unprocessed untuk keeps track berapa updates yang harus kita lakukan 
		double unprocessed = 0;
		
		while(running) {
			boolean shouldRender = false; 
			double now = System.nanoTime();
			
			//count how many updates we have to do 
			unprocessed += (now - then) / nsPerUpdate;
			then = now;
			
			//update queue
			while (unprocessed >= 1) {
				updates++;
				update();
				unprocessed--;
				shouldRender = true;
			}
			
			//render
			if(shouldRender) {
				fps++;
				render();
				shouldRender = false;
			}
			//kalo render ga kepake
			else {
				try {
					Thread.sleep(1);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
			
		//FPS TIMER 
		if(System.currentTimeMillis() - fpsTimer > 1000) {
			System.out.printf("%d fps %d updates", fps, updates);
			System.out.println();
			fps = 0;
			updates = 0;
			fpsTimer += 1000;
		}
	}
		
	public synchronized void start() {
		if(running) return;
		running = true;
		game = new Thread (this, "game");
		game.start();
	}
		
	public synchronized void stop() {
		if(!running) return;
		running = false;
		System.exit(0);
	}
	
	@Override 
	public void keyPressed(KeyEvent e) {
		Keyboard.keyPressed(e);
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		Keyboard.keyReleased(e);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {

		
	}

	@Override
	public void mouseEntered(MouseEvent e) {

		
	}

	@Override
	public void mouseExited(MouseEvent e) {

		
	}

	@Override
	public void mousePressed(MouseEvent e) {
//		screen.mousePressed(e);
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
//		screen.mouseReleased(e);
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
//		screen.mouseDragged(e);
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
//		screen.mouseMoved(e);
		
	}
	
}
