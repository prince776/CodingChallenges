package dev.cmc.cmcGFX;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener{
	public boolean[] keys;
	
	public static boolean up, down, left, right, enter;
	
	public KeyManager() {
		keys = new boolean[512];
		resetKeys();
	}
	
	public void tick(){
		up    = keys[KeyEvent.VK_UP];
		down  = keys[KeyEvent.VK_DOWN];
		left  = keys[KeyEvent.VK_LEFT];
		right = keys[KeyEvent.VK_RIGHT];
		enter = keys[KeyEvent.VK_ENTER];
	}
	
	public static void resetKeys() {
		up = false;
		down = false;
		left = false;
		right = false;
		enter = false;
	}
	
	public void keyTyped(KeyEvent e) {
		
	}

	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}

	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}
}
