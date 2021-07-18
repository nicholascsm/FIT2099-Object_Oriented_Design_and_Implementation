package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Exit;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;
//import edu.monash.fit2099.engine.PickUpItemAction;

/**
 * Returns a HarvestAction if Ground has a ripe Crop
 * @author Nicholas
 *
 */
public class HarvestBehaviour implements Behaviour{

	@Override
	public Action getAction(Actor actor, GameMap map) {
		if(actor.getDisplayChar() == 'F') {
			for (Exit exit : map.locationOf(actor).getExits()) {
				Location destination = exit.getDestination();
				if (destination.getGround().getDisplayChar() == 'C') {
					return new HarvestAction(destination);
				}
			}
		}
		return null;
	}

}
