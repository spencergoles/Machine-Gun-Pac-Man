package game;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.Timer;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class StartMenu extends JPanel implements ActionListener {
	
	private final int PANEL_WIDTH= 568;
	private final int PANEL_HEIGHT = 360;
	private final int PACMAN_STARTX = 50;
	private final int PACMAN_STARTY = 165;
	private final int max_lghosts = 10;
	private final int max_dghosts = 6;
	private int counter = 0;
	private boolean playing;
	private static final long serialVersionUID = -3070933422348067426L;
	private Image background;
	private Pacman pacman;
	private List<Left_Ghost> lghosts;
	private List<Diagonal_Ghost> dghosts;
	private Timer timer1;
	private int score = 0;
	private Image half_health = new ImageIcon("half_health.png").getImage();
	
	private final int[][] lgpos = {
		{PANEL_WIDTH, 15}, {PANEL_WIDTH, 75}, {PANEL_WIDTH, 135},
		{PANEL_WIDTH, 195}, {PANEL_WIDTH, 255}, {PANEL_WIDTH, 315}
	};
	
	private final int[][] twohpos = {
			{PANEL_WIDTH - 30, 15}, {PANEL_WIDTH - 30, 75}, {PANEL_WIDTH - 30, 135},
			{PANEL_WIDTH - 30, 195}, {PANEL_WIDTH - 30, 255}, {PANEL_WIDTH - 30, 315}
	};
	
	public StartMenu() {
		this.background = new ImageIcon("Background.jpg").getImage();
		this.pacman = new Pacman(PACMAN_STARTX, PACMAN_STARTY);
		this.setPreferredSize(new Dimension(this.PANEL_WIDTH, this.PANEL_HEIGHT));
		this.setBackground(Color.WHITE);
		addKeyListener(new TAdapter());
		setFocusable(true);
		this.timer1 = new Timer(1, this);
		this.timer1.start();
		playing = true;
		
		lghosts = new ArrayList<>();
		dghosts = new ArrayList<>();
		/*
		for (int[] p : lgpos) {
			lghosts.add(new Left_Ghost(p[0], p[1]));
		}
		
		for (int[] dp : twohpos) {
			dghosts.add(new Diagonal_Ghost(dp[0], dp[1]));
		} */
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d=(Graphics2D)g;
		
		RenderingHints rh = new RenderingHints(
				RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON
				);
		g2d.setRenderingHints(rh);
		g2d.drawImage(this.background, 0, 0, this);
		
		if (playing) {
			if (pacman.isVisible())
				g2d.drawImage(pacman.getImage(), pacman.getX(), pacman.getY(), this);
		
			List<Bullet> bullets = pacman.getBullets();
			for (Bullet b : bullets) {
				if (b.isVisible())
					g2d.drawImage(b.getImage(), b.getX(), b.getY(), this);
			}
			
			for (Left_Ghost lg : lghosts) {
				if (lg.isVisible())
					g2d.drawImage(lg.getImage(), lg.getX(), lg.getY(), this);
			}
			
			for (Diagonal_Ghost dg : dghosts) {
				if (dg.isVisible())
				{
					g2d.drawImage(dg.getImage(), dg.getX(), dg.getY(), this);
					if (dg.getHealth() == 1)
						g2d.drawImage(half_health, dg.getX(), dg.getY(), this);
				}
			}
			g2d.setColor(Color.WHITE);
			g2d.setFont(new Font("Helvetica", Font.BOLD, 15));
			String sScore = "Score: " + String.valueOf(score);
			g2d.drawString(sScore, 0, 15);
		}
		else {
			int temp_score = score;
			String game_over = "Game Over";
			String press_space = "Click RESTART to play again!";
			String final_score = "FINAL SCORE:  " + String.valueOf(temp_score);
			Font small = new Font("Helvetica", Font.BOLD, 14);
			
			DatabaseManager databaseManager = new DatabaseManager();
			
			
	        databaseManager.addScore(temp_score);
	        
			g2d.setColor(Color.WHITE);
			g2d.setFont(small);
			g2d.drawString(game_over, (PANEL_WIDTH - 90) / 2, PANEL_HEIGHT / 2);
			g2d.drawString(press_space, (PANEL_WIDTH - 210) / 2, (PANEL_HEIGHT + 30) / 2);
			g2d.drawString(final_score, (PANEL_WIDTH - 130) / 2, (PANEL_HEIGHT + 60) / 2);
			g2d.drawString("LEADERBOARD", (PANEL_WIDTH - 120) / 2, (PANEL_HEIGHT + 90) / 2);
			
			score = 0;
			// Example: Get and print the top three scores as a map
	        Map<Integer, Integer> topThreeScores = databaseManager.getTopThreeScores();
	        int height = 120;
	        for (Map.Entry<Integer, Integer> entry : topThreeScores.entrySet()) {
	            g2d.drawString("Rank: " + entry.getKey() + ", Score: " + entry.getValue(), (PANEL_WIDTH - 130) / 2, (PANEL_HEIGHT + height) / 2);
	            height += 30;
	        } 
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		
		checkCollisions();
		Playing();
        
        updatePacman();
        updateBullets();
        updateGhosts();
        addGhosts();
        
        counter = counter + 1 % 5000;
        
        this.repaint();
    }
	
	private void Playing() {
		if (!playing)
			timer1.stop();
	}
	
	private void addGhosts() {
		Random rand = new Random();
		int random_pos1 = rand.nextInt(6);
		int random_pos2 = rand.nextInt(6);
		if (counter % 50 == 0 && lghosts.size() < max_lghosts)
		{
			lghosts.add(new Left_Ghost(lgpos[random_pos1][0], lgpos[random_pos1][1]));
		}
		if (counter % 150 == 0 && dghosts.size() < max_dghosts)
		{
			dghosts.add(new Diagonal_Ghost(twohpos[random_pos2][0], twohpos[random_pos2][1]));
		}
	}
    
    private void updatePacman() {
    	if (pacman.isVisible())
    		pacman.move();         
    }
    
    private void updateBullets() {
    	List<Bullet> bullets = pacman.getBullets();
    	
    	for (int i = 0; i < bullets.size(); i++) {
    		Bullet b = bullets.get(i);
    		
    		if (b.isVisible())
    			b.move();
    		else
    			bullets.remove(i);
    	}
    }
    
    private void updateGhosts() {
    	for (int i = 0; i < lghosts.size(); i++)
    	{
    		Left_Ghost lg = lghosts.get(i);
    		if (lg.isVisible()) 
    			lg.move();
    		else
    			lghosts.remove(i);
    	}
    	for (int i = 0; i < dghosts.size(); i++)
    	{
    		Diagonal_Ghost dg = dghosts.get(i);
    		if (dg.isVisible())
    			dg.move();
    		else
    			dghosts.remove(i);
    	}
    }
    
    public void checkCollisions() {
    	Rectangle pacman_bounds = pacman.getBounds();
    	
    	for (Left_Ghost lg : lghosts) {
    		Rectangle lg_bounds = lg.getBounds();
    		
    		if (pacman_bounds.intersects(lg_bounds)) {
    			pacman.setVisible(false);
    			lg.setVisible(false);
    			playing = false;
    		}
    	}
    	for (Diagonal_Ghost dg : dghosts) {
    		Rectangle dg_bounds = dg.getBounds();
    		
    		if (pacman_bounds.intersects(dg_bounds)) {
    			pacman.setVisible(false);
    			dg.setVisible(false);
    			playing = false;
    		}
    	}
    	
    	List<Bullet> bullets = pacman.getBullets();
    	
    	for (Bullet b : bullets) {
    		Rectangle b_bounds = b.getBounds();
    		
    		for (Left_Ghost lg : lghosts) {
    			Rectangle lg_bounds = lg.getBounds();
    			
    			if (b_bounds.intersects(lg_bounds)) {
    				b.setVisible(false);
    				lg.setVisible(false);
    				score += 1;
    			}
    		}
    		for (Diagonal_Ghost dg : dghosts) {
    			Rectangle dg_bounds = dg.getBounds();
    			
    			if (b_bounds.intersects(dg_bounds)) {
    				if (dg.getHealth() == 1)
    				{
    					b.setVisible(false);
    					dg.setVisible(false);
    					score += 2;
    				}
    				else
    				{
    					b.setVisible(false);
    					dg.decreaseHealth();
    				}
    			}
    		}
    	}
    }
    
    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            pacman.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            pacman.keyPressed(e);
        }
    }
	
}
