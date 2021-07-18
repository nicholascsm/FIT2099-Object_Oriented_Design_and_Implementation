package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;

/**
 * The sniper ammunition stack
 * @author Nicholas
 *
 */
public class SniperAmmunition extends Item{
	protected int bullets;
	
	/**
	 * Constructor
	 */
	public SniperAmmunition() {
		super("sniper ammo", 'O', true);
		bullets = 5;
	}
	
	/**
	 * To track after every turn the amount of sniper bullets left,
	 * if its zero, then remove the item from the actor's inventory.
	 */
	@Override
	public void tick(Location currentLocation, Actor actor) {
		if(bullets == 0) {
			actor.removeItemFromInventory(this);
		}
	}
	/**
	 * Method to decrease the number of bullet left in the ammo stack
	 */
	public void shotOnce() {
		bullets = bullets -1; 
	}

}
