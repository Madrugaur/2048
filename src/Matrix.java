import java.io.File;
import java.util.Random;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

/**
 * 
 * @author Braden
 * 
 */
public class Matrix {
	private int[][] matrix = new int[4][4];
	public static final int HEIGHT = 4;
	public static final int WIDTH = 4;
	public boolean playing = true;
	/**
	 * Matrix Constructor
	 */
	public Matrix() {
		init();
	}
	/*
	 * Initiates the matrix by setting all of its elements to 0
	 */
	private void init() {
		for (int r = 0; r < HEIGHT; r++) {
			for (int c = 0; c < WIDTH; c++) {
				matrix[r][c] = 0;
			}
		}
	}
	/*
	 * Adds a random value somewhere in the matrix
	 */
	public void addRandom() {
		if (!isFull()) {
			Random rnd = new Random();
			int x = 0;
			int y = 0;
			boolean works = false;
			while (!works) {
				x = rnd.nextInt(4);
				y = rnd.nextInt(4);
				works = checkAdd(x, y);
			}
			//Figure out how to randomize values randomly added
			int val = getRandomVal();
			matrix[y][x] = val;
		}
	}
	/*
	 * Returns 4 50% of the time and 2 the other 50%
	 * Future Improvement: As the game progresses, increase the value of the random number accordingly
	 */
	private int getRandomVal() {
		double rnd = Math.random();
		if (rnd >= .50) {
			return 4;
		}else {
			return 2;
		}
	}
	/**
	 * Checks to see if a value can be added to the r and c position
	 */
	private boolean checkAdd(int c, int r) {
		if (matrix[r][c] > 0) {
			return false;
		}
		return true;
	}
	/**
	 * Checks to see if the matrix is full
	 * @return if the matrix is "full" i.e. there are no zeros present
	 */
	private boolean isFull() {
		for (int r = 0; r < HEIGHT; r++) {
			for (int c = 0; c < WIDTH; c++) {
				if (matrix[r][c] <= 0) {
					System.out.println("isFull() = false");
					return false;
				}
			}
		}
		System.out.println("isFull() = true");
		return true;
	}
	/**
	 * Returns the value at row r and column c
	 * @param r
	 * @param c
	 * @return value
	 */
	public int get(int r, int c) {
		return matrix[r][c];
	}
	/**
	 * Pulls all of the values "down", if vertically adjacent values are the same they are merged together
	 * Zero values are filled in by non-zero values above them
	 */
	public void pullDown() {
		boolean canAddRandom = false;
		for (int c = 0; c < WIDTH; c++) {
			//get column c
			int[] col = (getCol(c));
			
			if (!allZero(col) && goodTurn(col)) {
				if (!canAddRandom) canAddRandom = !allZero(col) && goodTurn(col);
				//first shift;
				col = shift(col);
				//merge
				col = merge(col);
				//second shift, fill in gaps from merge
				col = shift(col);
				//set column c
				setCol(c, col);
			}			
		}
		if (canAddRandom) {
			addRandom();			
		}
		playing = !endGame();
	}
	/**
	 * Pulls all of the values "up", if vertically adjacent values are the same they are merged together
	 * Zero values are filled in by non-zero values below them
	 */
	public void pullUp() {
		boolean canAddRandom = false;
		for (int c = 0; c < WIDTH; c++) {
			//get column c in reverse
			int[] col = reverse(getCol(c));
			
			if (!allZero(col) && goodTurn(col)) {
				if (!canAddRandom) canAddRandom = goodTurn(col);
				//first shift;
				col = shift(col);
				//merge
				col = merge(col);
				//second shift, fill in gaps from merge
				col = shift(col);
				//set column c, in reverse
				setCol(c, reverse(col));
			}			
		}
		if (canAddRandom) {
			addRandom();			
		}
		playing = !endGame();
	}
	/**
	 * Pulls all of the values "left", if horizontally adjacent values are the same they are merged together
	 * Zero values are filled in by non-zero values next to them
	 */
	public void pullLeft() {
		boolean canAddRandom = false;
		for (int r = 0; r < HEIGHT; r++) {
			//get column c in reverse
			int[] row = reverse(getRow(r));
			
			if (!allZero(row) && goodTurn(row)) {
				if (!canAddRandom) canAddRandom = goodTurn(row);
				//first shift;
				row = shift(row);
				//merge
				row = merge(row);
				//second shift, fill in gaps from merge
				row = shift(row);
				//set column c, in reverse
				setRow(r, reverse(row));
			}			
		}
		if (canAddRandom) {
			addRandom();			
		}
		playing = !endGame();
	}
	/**
	 * Pulls all of the values "Right", if horizontally adjacent values are the same they are merged together
	 * Zero values are filled in by non-zero values next to them
	 */
	public void pullRight() {
		boolean canAddRandom = false;
		for (int r = 0; r < HEIGHT; r++) {
			//get column c in reverse
			int[] row = getRow(r);
			
			if (!allZero(row) && goodTurn(row)) {
				if (!canAddRandom) canAddRandom = goodTurn(row);
				//first shift;
				row = shift(row);
				//merge
				row = merge(row);
				//second shift, fill in gaps from merge
				row = shift(row);
				//set column c, in reverse
				setRow(r, row);
			}			
		}
		if (canAddRandom) {
			addRandom();			
		}
		playing = !endGame();
	}
	/**
	 * Shifts all values in the array to the left, zero values are over-written by non-zero values
	 * @param arr
	 * @return the left shifted arr
	 */
	private int[] shift(int[] arr) {
		for (int curr = arr.length - 2; curr >= 0; curr--) {
			int temp_curr = curr;
			if (arr[curr] != 0) {
				for (int next = curr + 1; next < arr.length; next++) {
					if (arr[next] == 0) {
						arr[next] = arr[temp_curr];
						arr[temp_curr] = 0;
						temp_curr++;
					}else if (arr[next] > 0){
						next = arr.length;
					}	
				}
			}
		}
		
		return arr;
	}
	/**
	 * Adjacent values that are the same are merged together
	 * @param arr
	 * @return a merged arr
	 */
	private int[] merge(int[] arr) {
		for (int curr = arr.length - 2; curr >= 0; curr--) {
			int next = curr + 1;
			if (arr[curr] == arr[next]) {
				arr[next] *= 2;
				arr[curr] = 0;
				playMergeNoise();
			}
		}
		return arr;
	}
	/**
	 * Loads and plays the merge noise
	 */
	private void playMergeNoise() {
		try {
			File f = new File("button-16.wav");
		    AudioInputStream stream;
		    AudioFormat format;
		    DataLine.Info info;
		    Clip clip;

		    stream = AudioSystem.getAudioInputStream(f);
		    format = stream.getFormat();
		    info = new DataLine.Info(Clip.class, format);
		    clip = (Clip) AudioSystem.getLine(info);
		    clip.open(stream);
		    clip.start();
		}
		catch (Exception e) {
		    //whatevers
		}
	}
	/**
	 * Returns a specified row in the matrix
	 * @param tar
	 * @return
	 */
	private int[] getRow(int tar) {
		int[] arr = new int[4];
		for (int c = 0; c < WIDTH; c++) {
			arr[c] = matrix[tar][c];	
		}
		return arr;
	}
	/**
	 * Returns a specified column in the matrix
	 * @param tar
	 * @return
	 */
	private int[] getCol(int tar) {
		int[] arr = new int[4];
		for (int r = 0; r < HEIGHT; r++) {
			arr[r] = matrix[r][tar];	
		}
		return arr;
	}
	/**
	 * Replace all values in a specified row with new ones
	 * @param tar
	 * @param arr
	 */
	private void setRow(int tar, int[] arr) {
		for (int c = 0; c < WIDTH; c++) {
			matrix[tar][c] = arr[c];
		}
	}
	/**
	 * Replace all values in a specified column with new ones
	 * @param tar
	 * @param arr
	 */
	private void setCol(int tar, int[] arr) {
		for (int r = 0; r < HEIGHT; r++) {
			matrix[r][tar] = arr[r];
		}
	}
	/**
	 * Reverses a given int[]
	 * @param arr
	 * @return
	 */
	private int[] reverse(int[] arr) {
		for (int i = 0; i < arr.length / 2; i++) {
			int temp = arr[i];
			arr[i] = arr[arr.length - i - 1];
			arr[arr.length - i - 1] = temp;
		}
		return arr;
	}
	/**
	 * Checks to see if an array contains only zeros
	 * @param arr
	 * @return
	 */
	private boolean allZero(int[] arr) {
		boolean all = true;
		for (int i : arr) {
			if (i != 0) all = false;
		}
		return all;
	}
	/**
	 * Checks to see if a move can be made to a given arr
	 * @param arr
	 * @return
	 */
	private boolean goodTurn(int[] arr) {
		return canMerge(arr) || canShift(arr);
	}
	/**
	 * Checks for duplicate, adjacent values that can be merged
	 * @param arr
	 * @return
	 */
	private boolean canMerge(int[] arr) {
		for (int curr = arr.length - 2; curr >= 0; curr--) {
			int curr_val = arr[curr];
			int next = arr[curr + 1];
			if (curr_val == next && curr_val != 0) return true;
		}
		return false;
	}
	/**
	 * Checks for zero values adjacent to non-zero values
	 * @param arr
	 * @return
	 */
	private boolean canShift(int[] arr) {
		for (int curr = 0; curr < arr.length - 1; curr++) {
			if (arr[curr] > 0) {
				if (arr[curr + 1] == 0) return true;
			}
		}
		return false;
	}
	/**
	 * Checks all conditions for ending the game,
	 * all columns and rows must not be able to
	 * merge or shift for this to be true.
	 * @return
	 */
	public boolean endGame() {
		for (int up = 0; up < WIDTH; up++) {
			int[] col = reverse(getCol(up));
			if (goodTurn(col)) return false;
		}
		for (int down = 0; down < WIDTH; down++) {
			int[] col = getCol(down);
			if (goodTurn(col)) return false;
		}
		for (int left = 0; left < HEIGHT; left++) {
			int[] row = reverse(getRow(left));
			if (goodTurn(row)) return false;
		}
		for (int right = 0; right < HEIGHT; right++) {
			int[] row = getRow(right);
			if (goodTurn(row)) return false;
		}
		return true;
	}
	/**
	 * Converts the matrix into a string.
	 */
	public String toString() {
		String str = "";
		for (int[] arr : matrix) {
			for (int i : arr) {
				str += String.valueOf(i) + " ";
			}
			str += "\n";
		}
		return str;
	}
}
