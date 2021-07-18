package game;

import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;

/**
 * This class represents a Crop.
 * 
 * @author Nicholas
 *
 */
public class Crop extends Ground{
	private int age = 0;
	
	/**
	 * Default constructor for a Crop
	 */
	public Crop() {
		super('c');
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void tick(Location location) {
		super.tick(location);
		
		age++;
		
		if (age == 20) {
			displayChar = 'C';
		}
	}
	
	/**
	 * Method to fertilize the Crop to increase it's age by 10 turns
	 */
	public void fertilize() {
		age = age + 10;
	}

}
