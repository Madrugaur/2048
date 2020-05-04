import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
/**
 * 
 * @author Bradon
 *
 */
public class Board extends JPanel {
	private static final int CARD_WIDTH = 120;
	private static final int CARD_HEIGHT = 120;
	public static final int UP = 1, DOWN = 2, LEFT = 3, RIGHT = 4;
	private Matrix matrix;
	/**
	 * Board Constructor
	 * Sets up the basics of the JPanel and starts the game.
	 */
	public Board() {
		this.setPreferredSize(new Dimension(500, 500));
		this.setBackground(Color.lightGray);
		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		this.setVisible(true);
		matrix = new Matrix();
		matrix.addRandom();
		matrix.addRandom();
		renderMatrix();
	}
	/**
	 * Runs through all elements of the Matrix and renders a Card based on its attributes.
	 */
	private void renderMatrix() {
		this.removeAll();
		for (int r = 0; r < Matrix.HEIGHT; r++) {
			for (int c = 0; c < Matrix.WIDTH; c++) {
				addCard(matrix.get(r, c), r, c);
			}
		}
		this.updateUI();
	}
	/**
	 * Creates a new instance of the Card class and adds it to the control panel
	 * @param val
	 * @param r
	 * @param c
	 */
	private void addCard(int val, int r, int c) {
		Card card = new Card(val);
		card.setLocation(r * CARD_HEIGHT, c * CARD_WIDTH);
		this.add(card);
	}
	/**
	 * Handles all of the movement for the game
	 * @param i
	 */
	public void handleKeyEvent(int i) {
		if (matrix.playing) {
			switch(i) {
			case UP:
				matrix.pullUp();
				break;
			case DOWN:
				matrix.pullDown();
				break;
			case LEFT:
				matrix.pullLeft();
				break;
			case RIGHT:
				matrix.pullRight();
				break;
			}
			renderMatrix();			
		} else {
			System.out.println("GAME OVER");
		}
	}
	
}
