package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;

/**
 * Action for actors to eat a Food item to heal
 * @author Nicholas
 *
 */
public class EatAction extends Action{
	private Food food;
	
	/**
	 * Constructor for EatAction
	 * @param food the food to be eaten
	 */
	public EatAction(Food food) {
		if(food == null) {
			throw new NullPointerException("Food must not be null to be eaten.");
		}
		this.food = food;
	}
	
	@Override
	public String execute(Actor actor, GameMap map) {
		actor.heal(food.getHealValue());
		actor.removeItemFromInventory(food);
		
		String result = actor + " ate the " + food;
		result += System.lineSeparator() + actor + " recovered " + food.getHealValue() + " HP";
		return result;
	}

	@Override
	public String menuDescription(Actor actor) {
		return actor + " eats the "+ food;
	}

	
}
