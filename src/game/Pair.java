package game;

/**
 * Class to separate a pair into x and y values.
 * @author Nicholas
 *
 */
public class Pair {
	private int X;
	private int Y;
	
	/**
	 * Constructor
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public Pair(int x, int y) {
		this.X = x;
		this.Y = y;
	}
	
	/**
	 * To get the x-coordinate of the Pair
	 * @return X
	 */
	public int getX() {
		return X;
	}
	
	/**
	 * To get the y-coordinate of the Pair
	 * @return Y
	 */
	public int getY() {
		return Y;
	}
}
