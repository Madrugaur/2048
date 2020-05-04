import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
/**
 * 
 * @author Braden
 *
 */
public class Card extends JPanel {
	int number;
	private static final int HEIGHT = 120;
	private static final int WIDHT = 120;
	/**
	 * Card Constructor
	 * Sets up the backing JPanel
	 */
	public Card() {
		number = 0;
		this.setBackground(Color.lightGray);
		this.setPreferredSize(new Dimension(120, 120));
	}
	/**
	 * Card Constructor
	 * Sets up the backing JPanel and sets the cards number
	 * @param number
	 */
	public Card(int number) {
		this.number = number;
		this.setBackground(Color.lightGray);
		this.setPreferredSize(new Dimension(120, 120));
	}
	/**
	 * Add the card's number to its graphics context
	 * @param g
	 */
	protected void paintComponent(Graphics g) {
        super.paintComponent(g); 
        g.setColor(Color.gray);
        g.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 30, 30);  
        if (number > 0) {
        	
        	g.setColor(Color.white);
        	g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 46));
        	String str = String.valueOf(number);
        	FontMetrics metrics = g.getFontMetrics();
        	int height = metrics.getHeight();
        	int width = metrics.stringWidth(str);
        	int x = ((this.getWidth() - width) / 2);
        	int y = ((this.getHeight() - height) / 2) + metrics.getAscent();
        	g.drawString(str, x, y);
        }
      
    }
	
}
