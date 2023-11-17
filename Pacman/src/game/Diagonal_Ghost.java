package game;

public class Diagonal_Ghost extends Sprite{
	
	private int BOARD_WIDTH = 568;
	private int BOARD_HEIGHT = 360;
	private int GHOST_X_SPEED = 1;
	private int GHOST_Y_SPEED = 1;
	private int health = 2;
	
	public Diagonal_Ghost(int x, int y) {
		super(x,y);
		
		loadImage("diagonal_ghost.png");
		getImageDimensions();
	}
	
	public int getHealth() {
		return health;
	}
	
	public void decreaseHealth() {
		health--;
	}
	
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

