package game;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * The PacmanWindow class initializes the window in which the Pacman Shooter
 * application will be played in.
 * 
 * @author Wesley Tu
 */
public class PacmanWindow extends JFrame {
	private static final long serialVersionUID = -2251845675309884097L;
	
	/**
	 * Defines the panel in which the elements of the game will be played in,
	 * plus the restart button to restart the game.
	 */
	private StartMenu menu;
	private JButton restart;
	
	/**
	 * Default width and height of the window, plus the title of the game.
	 */
	public static final int DEFAULT_WIDTH = 500;
	public static final int DEFAULT_HEIGHT = 500;
	public static final String DEFAULT_TITLE = "Pacman Shooter";
	
	/**
	 * Default constructor
	 */
	public PacmanWindow() {
		this(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_TITLE);
	}
	
	/**
	 * Constructor to create the window for the Pacman Shooter, plus add the restart button.
	 * @param width
	 * @param height
	 * @param title
	 */
	public PacmanWindow(int width, int height, String title)
	{
		
		super(title);
		super.setSize(width, height);
		
		this.menu = new StartMenu();
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(menu, BorderLayout.CENTER);
		this.restart = new JButton("RESTART");
		this.restart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				start();
			}
		});
		this.restart.setFocusable(false);
		this.add(restart, BorderLayout.SOUTH);
		this.pack();
		this.setLocationRelativeTo(null); // center
		this.setResizable(false);
		this.setVisible(true);	
	}
	
	/**
	 * Method that restarts the game if the restart button is pressed.
	 */
	public void start() {
		menu.removeAll();
		this.remove(menu);
		this.menu = new StartMenu();
		this.add(menu, BorderLayout.CENTER);
		menu.revalidate();
		menu.repaint();
		menu.requestFocus();
	}
}
