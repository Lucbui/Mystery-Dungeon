/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mysterydungeon;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Class that keeps track of keys pressed and released. This class is used as a medium
 * between key-based interrupts and key polling, used in other games.
 * 
 * This file is free to use and modify as it is for educational use.
 * brought to you by Game Programming Snippets (http://gpsnippets.blogspot.com/)
 * 
 * Revisions:
 * 1.1 Initial Revision 
 * 
 */
public class Controls implements KeyListener
{
    //array of key states made as integers for more possible states. 
	private int[] keys = new int[256];
	
	//one for each ascii character.
	private boolean[] key_state_up = new boolean[256];  //true if pressed
	private boolean[] key_state_down = new boolean[256]; //true if not pressed
	
	//variable that indicates when any key(s) are being pressed.
	private boolean keyPressed = false;
	
	//variable that indicates that some key was released this frame.
	private boolean keyReleased = false; //cleared every frame.
	
	//a string used as a buffer by widgets or other text input controls
	private String keyCache = "";
	
	//the only instantiated object
	private static Controls instance = new Controls();
	
	/**
	 * Empty Constructor: nothing really needed here.
	 */
	protected Controls() {
	}

	/**
	 * Singleton accessor the only means of getting the instantiated object.
	 * @return One and only InputManager object.
	 */
	public static Controls getInstance() {
		return instance;
	}
	
	/**
	 * This function is specified in the KeyListener interface. It sets the 
	 * state elements for whatever key was pressed.
	 * 
	 * @param e The KeyEvent fired by the awt Toolkit
	 */
        @Override
	public void keyPressed(KeyEvent e) {
		//System.out.println("InputManager: A key has been pressed code=" + e.getKeyCode());
		if( e.getKeyCode() >= 0 && e.getKeyCode() < 256 ) {
			keys[e.getKeyCode()] = (int) System.currentTimeMillis();
			key_state_down[e.getKeyCode()] = true;
			key_state_up[e.getKeyCode()] = false;
			keyPressed = true;
			keyReleased = false;
		}
	}

	/**
	 * This function is specified in the KeyListener interface. It sets the 
	 * state elements for whatever key was released.
	 * 
	 * @param e The KeyEvent fired by the awt Toolkit.
	 */
        @Override
	public void keyReleased(KeyEvent e) {
		//System.out.println("InputManager: A key has been released code=" + e.getKeyCode());
		if( e.getKeyCode() >= 0 && e.getKeyCode() < 256 ) {
			keys[e.getKeyCode()] = 0;
			key_state_up[e.getKeyCode()] = true;
			key_state_down[e.getKeyCode()] = false;
			keyPressed = false;
			keyReleased = true;
		}
	}

	/**
	 * This function is called if certain keys are pressed namely those used
	 * for text input.
	 * 
	 * @param e The KeyEvent fired by the awt Toolkit.
	 */
        @Override
	public void keyTyped(KeyEvent e) {	
		keyCache += e.getKeyChar();
		
	}
	
	/**
	 * Returns true if the key (0-256) is being pressed use the KeyEvent.VK_
	 * key variables to check specific keys.
	 * 
	 * @param key The ascii value of the keyboard key being checked
	 * @return true is that key is currently being pressed.
	 */
	public boolean isKeyDown( int key ) {
		return key_state_down[key];
	}
	
	/**
	 * Returns true if the key (0-256) is being pressed use the KeyEvent.VK_
	 * key variables to check specific keys.
	 * 
	 * @param key The ascii value of the keyboard key being checked
	 * @return true is that key is currently being pressed.
	 */
	public boolean isKeyUp( int key ) {
		return key_state_up[key];
	}

	/**
	 * In case you want to know if a user is pressing a key but don't care which
	 * one.
	 * 
	 * @return true if one or more keys are currently being pressed.
	 */
	public boolean isAnyKeyDown() {
		return keyPressed;
	}
	
	/**
	 * In case you want to know if a user released a key but don't care which
	 * one.
	 * 
	 * @return true if one or more keys have been released this frame. 
	 */
	public boolean isAnyKeyUp() {
		return keyReleased;
	}
	
	/**
	 * Only resets the key state up because you don't want keys to be showing
	 * as up forever which is what will happen unless the array is cleared.
	 */
	public void update() {
		//clear out the key up states
		key_state_up = new boolean[256];
                keyPressed = false;
		if( keyCache.length() > 1024 ) {
			keyCache = "";
		}
	}
}
