package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Item;

/**
 * A vehicle that allows the Player to travel to another map.
 * 
 * @author Bryan Ho
 *
 */
public class Vehicle extends Item {
	
	/**
	 * Constructor.
	 */
	public Vehicle() {
		super("vehicle", '^', false);
	}
	
	/**
	 * Add an Action into the list of allowable actions for
	 * an Actor to perform on it.
	 * 
	 * @param action The action where an Actor can perform on the vehicle.
	 */
	public void addAction(Action action) {
		this.allowableActions.add(action);
	}
	
}
