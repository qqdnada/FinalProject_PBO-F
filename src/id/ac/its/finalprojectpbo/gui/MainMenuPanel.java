package id.ac.its.finalprojectpbo.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.example.DrawUtils;
import com.example.Game;

import id.ac.its.finalprojectpbo.gui.GuiButton;
import id.ac.its.finalprojectpbo.gui.GuiScreen;

public class MainMenuPanel extends GuiPanel {
	
	private Font titleFont = Game.main.deriveFont(100f);
	private String title = "2048";
	private int buttonWidth = 220;
	private int buttonHeight = 60;
	private int spacing = 90;

	public MainMenuPanel() {
		GuiButton playButton = new GuiButton(Game.WIDTH / 2 - buttonWidth / 2 , 220, buttonWidth, buttonHeight);
		GuiButton quitButton = new GuiButton(Game.WIDTH / 2 - buttonWidth / 2 , playButton.getY() + spacing, buttonWidth, buttonHeight);
		
		playButton.setText("Play");
		quitButton.setText("Quit");
		
		playButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				GuiScreen.getScreen().setCurrentPanel("Play");
				
			}
		});
		
		quitButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
				
			}
		});
		
		add(playButton);
		add(quitButton);
	}

	@Override
	public void render(Graphics2D g) {
		super.render(g);
		g.setFont(titleFont);
		g.setColor(Color.black);
		g.drawString(title, Game.WIDTH / 2 - DrawUtils.getMessageWidth(title, titleFont, g) / 2, 150);
	}
	
	
}
