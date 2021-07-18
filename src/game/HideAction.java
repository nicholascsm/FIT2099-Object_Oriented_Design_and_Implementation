package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

/**
 * A simple Action class that returns a string where an Actor has disappeared.
 * 
 * @author Bryan Ho
 *
 */
public class HideAction extends Action {

	@Override
	public String execute(Actor actor, GameMap map) {
		return menuDescription(actor);
	}
	
	@Override
	public String menuDescription(Actor actor) {
		return actor + " is nowhere to be found...";
	}
	
}
