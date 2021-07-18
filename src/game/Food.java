package game;

import edu.monash.fit2099.engine.Item;

/**
 * A Food item after harvesting a Crop
 * @author Nicholas
 *
 */
public class Food extends Item{
	private int healValue;
	
	/**
	 * Constructor
	 * @param name name of the crop
	 * @param healValue amount of health to heal the actor
	 */
	public Food(String name, int healValue) {
		super(name, 'f', true);
		this.healValue = healValue;
	}
	
	/**
	 * The get the heal value of the Food item
	 * @return the healValue
	 */
	public int getHealValue() {
		return healValue;
	}
}
