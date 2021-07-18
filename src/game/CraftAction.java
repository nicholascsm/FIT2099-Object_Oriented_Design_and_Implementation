package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.WeaponItem;

/**
 * An Action that allows an Actor to craft an item.
 * 
 * @author Bryan Ho
 *
 */
public class CraftAction extends Action {
	private CraftableItem item;
	
	/**
	 * Constructor.
	 * 
	 * @param item The item to be crafted into something else.
	 */
	public CraftAction(CraftableItem item) {
		if (item == null)
			throw new NullPointerException("Item to craft must not be null.");
		this.item = item;
	}
	
	/**
	 * Removes the item from the Actor's inventory and adds
	 * the end product of the item into their inventory.
	 * 
	 * @param actor	The Actor performing the action.
	 * @param map The map the Actor is on.
	 * @return A description of the Action suitable for feedback in the UI.
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		WeaponItem endProduct = item.getEndProduct();
		
		actor.removeItemFromInventory(item);
		actor.addItemToInventory(endProduct);
		
		return actor + " crafted the " + endProduct;
	}

	@Override
	public String menuDescription(Actor actor) {
		return actor + " crafts a " + item.getEndProduct() + " from " + item;
	}

}
