package game;

public class Bullet extends Sprite{
	
	private int BOARD_WIDTH = 568;
	private int BULLET_SPEED = 2;
	
	public Bullet(int x, int y) {
		super(x,y);
		
		loadImage("pellet.png");
		getImageDimensions();
	}
	
	public void move() {
		x += BULLET_SPEED;
		
		if (x > BOARD_WIDTH) {
			visible = false;
		}
	}
}
