package game;

/**
 * Left Ghost class that creates the ghost enemy that moves from right to left.
 * @author Wesley Tu
 */
public class Left_Ghost extends Sprite {
	
	/**
	 * Variable that keeps track of the rightmost edge of the window.
	 */
	private int INITIAL_X = 568;
	
	/**
	 * Constructor for the Left Ghost entity.
	 * @param x
	 * @param y
	 */
	public Left_Ghost(int x, int y) {
		super(x, y);
		
		loadImage("left_ghost.png");
		getImageDimensions();
	}
	
	/**
	 * Moves the Left Ghost entity from right to left, moving the entity back to
	 * the right side of the screen once it touches the left side.
	 */
	public void move() {
		if (x < 0)
			x = INITIAL_X;
		x -= 2;
	}
}
