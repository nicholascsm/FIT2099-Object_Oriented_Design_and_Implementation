package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;

/**
 * The class to represent a Shotgun ammunition stack
 * @author Nicholas
 *
 */
public class ShotgunAmmunition extends Item{
	protected int shells;
	
	/**
	 * Constructor
	 */
	public ShotgunAmmunition() {
		super("shotgun ammo",'o',true);
		shells = 10;
	}
	
	/**
	 * To track after every turn the amount of shotgun shells left,
	 * if its zero, then remove the item from the actor's inventory.
	 */
	@Override
	public void tick(Location currentLocation, Actor actor) {
		if(shells == 0) {
			actor.removeItemFromInventory(this);
		}
	}
	
	/**
	 * To decrease the number of shells/ammunition left by 1
	 */
	public void shotOnce() {
		shells = shells-1;
	}
	
	
}
