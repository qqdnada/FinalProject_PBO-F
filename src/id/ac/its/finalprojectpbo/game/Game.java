package id.ac.its.finalprojectpbo.game;

import java.awt.Font;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import com.example.GameBoard;

import id.ac.its.finalprojectpbo.gui.GuiScreen;
import id.ac.its.finalprojectpbo.gui.MainMenuPanel;

public class Game extends JPanel implements MouseListener, MouseMotionListener {
	
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 400;
	public static final int HEIGHT = 630;
	public static final Font main = new Font("Bebas", Font.PLAIN, 28);
	
	private Thread game;
	private boolean running;
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private GameBoard board;
	
	private long startTime;
	private long elapsed;
	private boolean set;
	private GuiScreen screen;
	
	public Game() {
		addMouseListener(this);
		addMouseMotionListener(this);
		
		screen = GuiScreen.getScreen();
		screen.add("Menu", new MainMenuPanel());
		screen.setCurrentPanel("Menu");
	}

	private void update() {
		screen.update();
	}
	
	private void render() {
//		screen.render(g);
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
		screen.mousePressed(e);
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		screen.mouseReleased(e);
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		screen.mouseDragged(e);
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		screen.mouseMoved(e);
		
	}
	
}
