package game;

import java.util.List;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;

/**
 * Returns an EatAction if the actor has a Food item in the actor's inventory
 * @author Nicholas
 *
 */
public class EatBehaviour implements Behaviour{
	private List<Item> inventory;
	
	@Override
	public Action getAction(Actor actor, GameMap map) {
		inventory = actor.getInventory();
		for(int i=0; i< inventory.size();i++) {
			if(inventory.get(i).getClass() == Food.class) {
				return new EatAction((Food)inventory.get(i));
			}
		}
		
		return null;
	}

}
