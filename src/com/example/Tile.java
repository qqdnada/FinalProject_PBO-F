package com.example;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.image.BufferedImage;

public class Tile {
	public static final int WIDTH = 80;
	public static final int HEIGHT = 80;
	
	//utk speed per update waktu gerak 
//	public static final SLIDE_SPEED = 20;
	public static final int ARC_WIDTH = 80;
	public static final int ARC_HEIGHT = 80;
		
	private int value;
	private BufferedImage gambarKotak;
	private Color background;
	private Color text;
	private Font font;
	private int x;
	private int y;
		
	public Tile (int value, int x, int y) {
		this.value = value;
		this.x = x;
		this.y = y;
		
		//ARGB spy bs gambar transparent image 
		gambarKotak = new BufferedImage (WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);		
		
		//untuk gambar / kasi angka di tile nya 
		drawImage();
	}
		
	private void drawImage() {
		//kayak pensilnya 
		Graphics2D g = (Graphics2D)gambarKotak.getGraphics();
				
		//kayak kertasnya, jd pensilnya dipake di gambar di kertas 
				
		//untuk value 2
		if(value == 2) {
			background = new Color(0x9e9e9);
			text = new Color(0x000000);
		}
		//untuk value 4
		else if (value == 4) {
			background = new Color(0xe6daab);
			text = new Color(0x000000);
		}
		//untuk value 8
		else if (value == 8) {
			background = new Color(0xf79d3d);
			text = new Color(0xffffff);
		}
		//untuk value 
		else if (value == 16) {
			background = new Color(0xf28007);
			text = new Color(0xffffff);
		}
		//untuk value 32
		else if (value == 32) {
			background = new Color(0xf55e32);
			text = new Color(0xffffff);
		}
		//untuk value 64
		else if (value == 64) {
			background = new Color(0xff0000);
			text = new Color(0xffffff);
		}
		//untuk value 128
		else if (value == 128) {
			background = new Color(0xe9de84);
			text = new Color(0xffffff);
		}
		//untuk value 256
		else if (value == 256) {
			background = new Color(0xf6e873);
			text = new Color(0xffffff);
		}
		//untuk value 512
		else if (value == 512) {
			background = new Color(0xf5e455);
			text = new Color(0xffffff);
		}
		//untuk value 1024
		else if (value == 1024) {
			background = new Color(0xf7e12c);
			text = new Color(0xffffff);
		}
		//untuk value 2048
		else if (value == 2048) {
			background = new Color(0xffe400);
			text = new Color(0x000000);
		}
		//kalo g ditaruh gambar
		else {
			background = Color.black;
			text = Color.white;
		}
				
		g.setColor(new Color(0, 0, 0, 0));
		g.fillRect(0, 0, WIDTH, HEIGHT);
				
		g.setColor(background);
		g.fillRoundRect(0, 0, WIDTH, HEIGHT, ARC_WIDTH, ARC_HEIGHT);
				
		g.setColor(text);
				
		//two digit number
		if(value <= 64) {
			font = Game.main.deriveFont(36f);
		}
		else {
			font = Game.main;
		}
		g.setFont(font);
		
		//setengah dari lebar  
		int drawX = WIDTH / 2 - DrawUtils.getMessageWidth("" + value, font, g) /2;
		int drawY = HEIGHT / 2 + DrawUtils.getMessageHeight("" + value, font, g)
	} 
}