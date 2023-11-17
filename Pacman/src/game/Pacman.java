package game;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Pacman extends Sprite{
	
	private int xVelocity = 0;
	private int yVelocity = 0;
	private int BOARD_WIDTH = 568;
	private final int BOARD_HEIGHT = 360;
	private List<Bullet> bullets;
	
	/**
	 * 
	 */
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 3239385422608431256L;
	
	public Pacman(int x, int y) {
		super(x, y);
		
		bullets = new ArrayList<>();
		loadImage("pacman.png");
		getImageDimensions();
	}
	
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
    
    public List<Bullet> getBullets() {
    	return bullets;
    }
	
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
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
	
	public void fire() {
		bullets.add(new Bullet(x + width, y + height/2));
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
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
