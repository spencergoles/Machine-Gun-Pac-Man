package game;

public class Left_Ghost extends Sprite {
	private int INITIAL_X = 568;
	
	public Left_Ghost(int x, int y) {
		super(x, y);
		
		loadImage("left_ghost.png");
		getImageDimensions();
	}
	
	public void move() {
		if (x < 0)
			x = INITIAL_X;
		x -= 2;
	}
}
