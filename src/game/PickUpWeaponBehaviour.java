package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;

/**
 * A class that generates a PickUpItemAction where the Actor will
 * pick up the first weapon it comes across at its location.
 * 
 * @author Bryan Ho
 *
 */
public class PickUpWeaponBehaviour implements Behaviour {
	
	@Override
	public Action getAction(Actor actor, GameMap map) {
		for (Item item : map.locationOf(actor).getItems()) {
			if (item.asWeapon() != null)
				return item.getPickUpAction();
		}
		return null;
	}

}
