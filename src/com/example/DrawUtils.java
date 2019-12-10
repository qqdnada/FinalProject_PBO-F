package com.example;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;

public class DrawUtils {
	
	//ga pake constructur
	//hanya untuk static variable
	private DrawUtils() { }
	
	//untuk membuat tulisannya di tengah
	//a container for the information needed to correctly measure text.
	public static int getMessageWidth(String message, Font font, Graphics2D g) {
		g.setFont(font);
		Rectangle2D bounds = g.getFontMetrics().getStringBounds(message,  g);
		return(int)bounds.getWidth();
	}
	
	public static int getMessageHeight(String message, Font font, Graphics2D g) {
		g.setFont(font);
		if(message.length() == 0) 
			return 0;
		TextLayout tl = new TextLayout(message, font, g.getFontRenderContext());
		return(int)tl.getBounds().getHeight();
	}
}