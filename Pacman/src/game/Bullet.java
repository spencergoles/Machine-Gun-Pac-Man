package game;

/**
 * Bullet class to create the sprite of a bullet being fired by Pacman.
 * @author Wesley Tu
 */
public class Bullet extends Sprite{
	
	/**
	 * Variables that contain the width of the window plus the bullet's speed.
	 */
	private int BOARD_WIDTH = 568;
	private int BULLET_SPEED = 2;
	
	/**
	 * Constructor for the Bullet class.
	 * @param x
	 * @param y
	 */
	public Bullet(int x, int y) {
		super(x,y);
		
		loadImage("pellet.png");
		getImageDimensions();
	}
	
	/**
	 * Moves the bullet to the right, until the bullet is offscreen, in which the 
	 * bullet then disappears.
	 */
	public void move() {
		x += BULLET_SPEED;
		
		if (x > BOARD_WIDTH) {
			visible = false;
		}
	}
}
