package game;

import java.util.Random;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Exit;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

/**
 * Behaviour class to return a SowingAction with a probability of 33% if the ground is dirt
 * @author Nicholas
 *
 */
public class SowingBehaviour implements Behaviour{
	private Random random = new Random();
	
	@Override
	public Action getAction(Actor actor, GameMap map) {
		for (Exit exit : map.locationOf(actor).getExits()) {
            Location destination = exit.getDestination();
            if (destination.getDisplayChar() == '.') {
            	if(random.nextDouble() < 0.33) {
            		return new SowingAction(destination);
            	}
            	break;
            }
        }
		
		return null;
	}
	

}
