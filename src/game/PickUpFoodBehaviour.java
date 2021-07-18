package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;

/**
 * Behaviour class that returns a PickUpItemAction
 * @author Nicholas
 *
 */
public class PickUpFoodBehaviour implements Behaviour{

	
	@Override
	public Action getAction(Actor actor, GameMap map) {
		// TODO Auto-generated method stub
		for(Item item : map.locationOf(actor).getItems()) {
			if(item.getClass() == Food.class) {
				return item.getPickUpAction();
			}
		}
		return null;
	}

}
