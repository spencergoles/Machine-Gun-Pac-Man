package game;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
/**
 * Pacman class to keep track of the player character.
 * 
 * @author Wesley Tu
 */
public class Pacman extends Sprite{
	
	/**
	 * Variables to keep track of which direction Pacman is going and where
	 * the boundaries of the board is.
	 */
	private int xVelocity = 0;
	private int yVelocity = 0;
	private int BOARD_WIDTH = 568;
	private final int BOARD_HEIGHT = 360;
	
	/**
	 * Keeps track of all the bullets that Pacman has fired.
	 */
	private List<Bullet> bullets;
	
	/**
	 * 
	 */
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 3239385422608431256L;
	
	/**
	 * Pacman constructor to place the Pacman entity.
	 * @param x
	 * @param y
	 */
	public Pacman(int x, int y) {
		super(x, y);
		
		bullets = new ArrayList<>();
		loadImage("pacman.png");
		getImageDimensions();
	}
	
	/**
	 * Moves the pacman based on its current velocity that is determined by player
	 * input. Also prevents Pacman from moving beyond the window's edge.
	 */
	public void move() {
        x += xVelocity;
        if (x < 0)
        	x = 0;
        else if (x + width > BOARD_WIDTH)
        	x = BOARD_WIDTH - width;
        y += yVelocity;
        if (y < 0)
        	y = 0;
        else if (y + height > BOARD_HEIGHT)
        	y = BOARD_HEIGHT - height;
    }
    
	/**
	 * A method that returns the bullet objects created by Pacman.
	 * @return A list containing all the bullets fired by Pacman.
	 */
    public List<Bullet> getBullets() {
    	return bullets;
    }
    /**
     * Whenever Pacman fires a bullet, a new bullet object is created and
     * is added to Pacman's bullet list.
     */
    public void fire() {
		bullets.add(new Bullet(x + width, y + height/2));
	}
	
    /**
     * Key listeners that listen for what key the player has pressed and acts 
     * accordingly. Arrow keys move the Pacman, while the spacebar fires a bullet.
     * @param e
     */
	public void keyPressed(KeyEvent e) {
		
		if (e.getKeyCode() == KeyEvent.VK_SPACE)
		{
			fire();
		}
		
		if (e.getKeyCode() == KeyEvent.VK_D) {
			 // change direction
			xVelocity = 2;
		}
		
		
		if (e.getKeyCode() == KeyEvent.VK_A) {
			xVelocity = -2; // change direction
		}
		
		if (e.getKeyCode() == KeyEvent.VK_S) {
			 // change direction
			yVelocity = 2;
		}
		
		
		if (e.getKeyCode() == KeyEvent.VK_W) {
			yVelocity = -2; // change direction
		}
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_D) {
			 // change direction
			xVelocity = 0;
		}
		
		
		if (e.getKeyCode() == KeyEvent.VK_A) {
			xVelocity = 0; // change direction
		}
		
		if (e.getKeyCode() == KeyEvent.VK_S) {
			 // change direction
			yVelocity = 0;
		}
		
		
		if (e.getKeyCode() == KeyEvent.VK_W) {
			yVelocity = 0; // change direction
		}
	}
}
