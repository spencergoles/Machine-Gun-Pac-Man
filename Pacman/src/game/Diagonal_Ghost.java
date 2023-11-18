package game;

/**
 * Diagonal Ghost class that creates a ghost enemy that bounces around the screen.
 * @author Wesley Tu
 */
public class Diagonal_Ghost extends Sprite{
	
	/**
	 * Variables that keep track of the window's width and height, plus the speed
	 * and health of the ghost.
	 */
	private int BOARD_WIDTH = 568;
	private int BOARD_HEIGHT = 360;
	private int GHOST_X_SPEED = 1;
	private int GHOST_Y_SPEED = 1;
	private int health = 2;
	
	/**
	 * Constructor for the Diagonal Ghost class.
	 * @param x
	 * @param y
	 */
	public Diagonal_Ghost(int x, int y) {
		super(x,y);
		
		loadImage("diagonal_ghost.png");
		getImageDimensions();
	}
	
	/**
	 * Returns the health of the Diagonal Ghost entity.
	 * @return Health of Diagonal Ghost entity
	 */
	public int getHealth() {
		return health;
	}
	
	/**
	 * Method to decrease the health of the Diagonal Ghost entity.
	 */
	public void decreaseHealth() {
		health--;
	}
	
	/**
	 * Move the entity diagonally, bouncing off the boundaries of the window.
	 */
	public void move() {
		x += GHOST_X_SPEED;
		y += GHOST_Y_SPEED;
		
		if (x < 0 || x > BOARD_WIDTH - width) {
			GHOST_X_SPEED = GHOST_X_SPEED * -1;
		}
		
		if (y < 0 || y > BOARD_HEIGHT - height) {
			GHOST_Y_SPEED = GHOST_Y_SPEED * -1;
		}
	}
}

