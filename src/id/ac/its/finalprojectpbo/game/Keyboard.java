package id.ac.its.finalprojectpbo.game;

import java.awt.event.KeyEvent;

public class Keyboard {
	
	public static boolean[]pressed = new boolean [256];
	public static boolean[]prev = new boolean [256];	
	
	//karna static class 
	private Keyboard() { }

	//untuk meng set apapun yang di arrah sebelumnya ke  "pressed" array
	public static void update() {
		//up, down, left, right aja keyboardnya, jd looping sampe 4 kali 
		for (int i = 0; i < 4; i++) {
			if(i==0) prev[KeyEvent.VK_LEFT] = pressed[KeyEvent.VK_LEFT];
			if(i==1) prev[KeyEvent.VK_RIGHT] = pressed[KeyEvent.VK_RIGHT];
			if(i==2) prev[KeyEvent.VK_UP] = pressed[KeyEvent.VK_UP];
			if(i==3) prev[KeyEvent.VK_DOWN] = pressed[KeyEvent.VK_DOWN];
			
		}
	}      
	
	//pas keyboard dipencet, di set true
	public static void keyPressed(KeyEvent e) {
		pressed[e.getKeyCode()] = true;
	}
	
	//pas keyboard di lepas, set false
	public static void keyReleased(KeyEvent e) {
		pressed[e.getKeyCode()] = false;
	}
	
	public static boolean typed(int keyEvent) {
		return !pressed[keyEvent] && prev[keyEvent];
	}
}

//pakai dua array karena untuk membedakan antara "typed" dan "press"
//kalo typed terjadi waktu tombol dilepaskan, tp kalo press terjadi kalao tombol terus ditekan