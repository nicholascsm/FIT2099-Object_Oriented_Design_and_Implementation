package game;

import edu.monash.fit2099.engine.WeaponItem;

/**
 * An Actor's limb. It can be used to attack someone else,
 * but it can also be crafted into a more powerful weapon.
 * 
 * @author Bryan Ho
 *
 */
public class Limb extends CraftableItem {
	
	/**
	 * Constructor.
	 * 
	 * @param name Name to call the limb in the UI.
	 * @param displayChar Character to represent the limb in the UI.
	 * @param endProduct The item that the limb can be crafted into.
	 */
	public Limb(String name, char displayChar, WeaponItem endProduct) {
		super(name, displayChar, 15, "whacks", endProduct);
	}

}
