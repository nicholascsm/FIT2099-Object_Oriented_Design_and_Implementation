package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

/**
 * Returns a FertilizeAction if the Crop is not ripe
 * @author Nicholas
 *
 */
public class FertilizeBehaviour implements Behaviour{

	@Override
	public Action getAction(Actor actor, GameMap map) {
		if(map.locationOf(actor).getGround().getDisplayChar() == 'c') {
			return new FertilizeAction((Crop)map.locationOf(actor).getGround());
		}
		return null;
	}

}
