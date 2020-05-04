import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
/**
 * 
 * @author Braden
 *
 */
public class Main extends JFrame {
	/**
	 * Main Construtor
	 * Create the board and sets up KeyListener
	 */
	public Main() {
		Board b = new Board();
		b.setLocation(10, 10);
		this.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void keyReleased(KeyEvent e) {
				System.out.println(e.getKeyChar());
				int key = e.getKeyCode();
				switch (key) {
					case KeyEvent.VK_UP:
						b.handleKeyEvent(Board.UP);
						break;
					case KeyEvent.VK_DOWN:
						b.handleKeyEvent(Board.DOWN);
						break;
					case KeyEvent.VK_RIGHT:
						b.handleKeyEvent(Board.RIGHT);
						break;
					case KeyEvent.VK_LEFT:
						b.handleKeyEvent(Board.LEFT);
						break;
					default:
						break;
				}
			}
			
		});
		this.add(b);
	}
	/*
	 * Program entry point
	 */
	public static void main (String[] args) {
		JFrame f = new Main();
		f.setSize(550, 560);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
}
