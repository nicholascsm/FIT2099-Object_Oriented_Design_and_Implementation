package game;

import edu.monash.fit2099.engine.WeaponItem;

/**
 * A zombie mace that is crafted from a zombie's leg.
 * 
 * @author Bryan Ho
 *
 */
public class ZombieMace extends WeaponItem {
	
	/**
	 * Constructor.
	 */
	public ZombieMace() {
		super("zombie mace", '!', 50, "bludgeons");
	}
}
