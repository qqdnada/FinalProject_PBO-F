package id.ac.its.finalprojectpbo.game;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;

//untuk membuat tulisannya di tengah
public class DrawUtils {
	
	//ga pake constructur
	//hanya untuk static variable
	private DrawUtils() { }

	//getStringBounds = Returns the bounds of the specified array of characters in the specified Graphics context.
	
	//untuk mendapatkan lebar dari "message" nya
	public static int getMessageWidth(String message, Font font, Graphics2D g) {
		g.setFont(font);
		Rectangle2D bounds = g.getFontMetrics().getStringBounds(message,  g);
		return(int)bounds.getWidth();
	}
	
	//FontRenderContext = a container for the information needed to correctly measure text
	
	//untuk mendapatkan lebar dari "message" nya
	public static int getMessageHeight(String message, Font font, Graphics2D g) {
		g.setFont(font);
		//kalo g ada messagenya, return length nya 0
		if(message.length() == 0) 
			return 0;
		TextLayout tl = new TextLayout(message, font, g.getFontRenderContext());
		return(int)tl.getBounds().getHeight();
	}
}