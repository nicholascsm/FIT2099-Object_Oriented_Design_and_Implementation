package game;

import edu.monash.fit2099.engine.WeaponItem;

/**
 * Class for items that can be crafted into something else.
 * It inherits PortableItem because craftable items can be held.
 * 
 * @author Bryan Ho
 *
 */
public class CraftableItem extends WeaponItem {
	private WeaponItem endProduct;
	
	/**
	 * Constructor.
	 * 
	 * @param name Name to call the item in the UI.
	 * @param displayChar Character to represent the item in the UI.
	 * @param damage The damage of the weapon in hitpoints.
	 * @param verb The verb to use for this weapon. (e.g. whacks, smashes)
	 * @param endProduct The end product that the item is crafted into.
	 */
	public CraftableItem(String name, char displayChar, int damage, String verb, WeaponItem endProduct) {
		super(name, displayChar, damage, verb);
		
		if (endProduct == null)
			throw new NullPointerException("End product must not be null.");
		
		this.endProduct = endProduct;
	}
	
	/**
	 * Getter method to get the end product of the CraftableItem.
	 * 
	 * @return The end product of the CraftableItem.
	 */
	public WeaponItem getEndProduct() {
		return endProduct;
	}

}
