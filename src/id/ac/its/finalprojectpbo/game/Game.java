package id.ac.its.finalprojectpbo.game;

import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import id.ac.its.finalprojectpbo.gui.GuiScreen;

public class Game extends JPanel implements MouseListener, MouseMotionListener {
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
