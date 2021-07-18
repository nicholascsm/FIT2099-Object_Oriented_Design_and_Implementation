package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

/**
 * An Action class where the Player is removed from the game,
 * thus ending the game.
 * 
 * @author Bryan Ho
 *
 */
public class QuitGameAction extends Action {

	@Override
	public String execute(Actor actor, GameMap map) {
		map.removeActor(actor);
		return menuDescription(actor);
	}

	@Override
	public String menuDescription(Actor actor) {
		return actor + " quits the game";
	}

}
