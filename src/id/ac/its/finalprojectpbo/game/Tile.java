package id.ac.its.finalprojectpbo.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.Font;
import java.awt.image.BufferedImage;

public class Tile {
	public static final int WIDTH = 80;
	public static final int HEIGHT = 80;
	
	//utk speed per update waktu gerak 
	public static final int ARC_WIDTH = 15;
	public static final int ARC_HEIGHT = 15;
	public static final int SLIDE_SPEED =20;
		
	private int value;
	private BufferedImage gambarKotak;
	private Color background;
	private Color text;
	private Font font;
	private Point slideTo;
	private int x;
	private int y;
	
	//untuk animasi
	private boolean beginningAnimation = true;
	private double scaleFirst = 0.1;
	private BufferedImage beginningImage;
	private boolean combineAnimation = false;
	private double scaleCombine = 1.3;
	private BufferedImage combineImage;
	
	private boolean canCombine = true;
	
	//waktu kita slide tilesnya, pasti ada tempat akhir nya tile
	//jadi harus ada variable yg nyimpien selama dia di posisi yg salah( temporary posisition)
		
	//setter getter
	public boolean canCombine() {
		return canCombine;
	}

	public void setCanCombine(boolean canCombine) {
		this.canCombine = canCombine;
	}

	public Tile (int value, int x, int y) {
		this.value = value;
		this.x = x;
		this.y = y;
		//posisi tile yg mau di datangin
		slideTo = new Point(x,y);
		 
		//ARGB spy bs gambar transparent image 
		gambarKotak = new BufferedImage (WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);		
		
		beginningImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		combineImage = new BufferedImage(WIDTH * 2, HEIGHT * 2, BufferedImage.TYPE_INT_ARGB);
		
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
		//untuk value 16
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
		//kalo g ditaruh angka apa"
		else {
			background = Color.black;
			text = Color.white;
		}
				
		g.setColor(new Color(0, 0, 0, 0));
		g.fillRect(0, 0, WIDTH, HEIGHT);
				
		g.setColor(background);
		g.fillRoundRect(0, 0, WIDTH, HEIGHT, ARC_WIDTH, ARC_HEIGHT);
				
		g.setColor(text);
				
		//dua digit angka
		if(value <= 64) {
			font = Game.main.deriveFont(36f);
		}
		else {
			font = Game.main;
		}
		g.setFont(font);
		
		//supaya tulisan angkanya berada di tengah tiles  
		int drawX = WIDTH / 2 - DrawUtils.getMessageWidth("" + value, font, g) /2;
		int drawY = HEIGHT / 2 + DrawUtils.getMessageHeight("" + value, font, g) /2;
		g.drawString("" + value, drawX, drawY);
		g.dispose();
	}
	
	public void update() {
		if(beginningAnimation) {
			AffineTransform transform = new AffineTransform();
			transform.translate(WIDTH / 2 - scaleFirst * WIDTH / 2, HEIGHT / 2 - scaleFirst * HEIGHT / 2);
			transform.scale(scaleFirst, scaleFirst);
			
			Graphics2D g2d = (Graphics2D) beginningImage.getGraphics();
			g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
			g2d.setColor(new Color(0,0,0,0));
			g2d.fillRect( 0,0, WIDTH, HEIGHT );
			g2d.drawImage(gambarKotak, transform, null);
			scaleFirst += 0.09;
			g2d.dispose();
			
			//klo uda ada animasinya, ga perlu lagi nambah
			if(scaleFirst >= 1) beginningAnimation = false;
		}
		
		else if (combineAnimation) {
			AffineTransform transform = new AffineTransform();
			transform.translate(WIDTH / 2 - scaleCombine * WIDTH / 2, HEIGHT / 2 - scaleCombine * HEIGHT / 2);
			transform.scale(scaleCombine, scaleCombine);
			
			Graphics2D g2d = (Graphics2D) combineImage.getGraphics();
			g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
			g2d.setColor(new Color(0,0,0,0));
			g2d.fillRect( 0,0, WIDTH, HEIGHT );
			g2d.drawImage(gambarKotak, transform, null);
			scaleCombine -= 0.05;
			g2d.dispose();
			
			//klo uda ada animasinya, ga perlu lagi nambah
			if(scaleCombine <= 1) combineAnimation = false;
		}
	}
	
	public void render(Graphics2D g) {
		if(beginningAnimation) {
			g.drawImage(beginningImage, x, y, null);
		}
		
		else if (combineAnimation) {
			g.drawImage(combineImage, (int) (x + WIDTH / 2 - scaleCombine * WIDTH /2), (int) (y + HEIGHT / 2 - scaleCombine * HEIGHT /2), null)	;
		}
		
		else {
			g.drawImage(gambarKotak, x, y, null);
		}
		
		g.drawImage(gambarKotak, x, y, null);
	}
	
	//getter setter
	public int getValue() {
		return value;
	}
	
	public void setValue(int value) {
		this.value = value;
		//setelah set value (pertama dan pengulangan) perlu digambar lagi 
		drawImage();
	}

	public Point getSlideTo() {
		return slideTo;
	}

	public void setSlideTo(Point slideTo) {
		this.slideTo = slideTo;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isCombineAnimation() {
		return combineAnimation;
	}

	public void setCombineAnimation(boolean combineAnimation) {
		this.combineAnimation = combineAnimation;
		if(combineAnimation) scaleCombine = 1.3;
	} 
}