package game;

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

/**
 * Sprite class to inherit all the attributes of a sprite on the screen.
 * @author Wesley Tu
 */
public class Sprite {
	
	/**
	 * Variables the keep track of the position of the sprite, the dimensions of the
	 * sprite, whether it should be visible on screen, and the actual image of the sprite.
	 */
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected boolean visible;
	protected Image image;
	
	/**
	 * Constructor to create a sprite with its position given.
	 * @param x
	 * @param y
	 */
	public Sprite(int x, int y) {
		this.x = x;
		this.y = y;
		visible = true;
	}
	
	/**
	 * Load the image of the sprite.
	 * @param imageName
	 */
	protected void loadImage(String imageName) {
		image = new ImageIcon(imageName).getImage();
	}
	
	/**
	 * Set the dimensions of the sprite to be the dimensions of the image.
	 */
	protected void getImageDimensions() {
		width = image.getWidth(null);
		height = image.getHeight(null);
	}
	
	/**
	 * Returns the image that the sprite uses.
	 * @return Image of the sprite.
	 */
	public Image getImage() {
		return image;
	}
	
	/**
	 * Returns the X position of the sprite.
	 * @return X position of the sprite.
	 */
	public int getX() {
		return x;
	}
	
	
	/**
	 * Returns the Y position of the sprite.
	 * @return Y position of the sprite.
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Returns the visible variable to see if the sprite should be visible or not.
	 * @return The visibility boolean variable.
	 */
	public boolean isVisible() {
		return visible;
	}
	
	/**
	 * Set the visibility of the sprite.
	 * @param visible
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	/**
	 * Get the bounds of the sprite.
	 * @return A rectangle object that lies on the bounds of the sprite image.
	 */
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}
}
