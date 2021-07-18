package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

/**
 * Action class to sow a new Crop
 * @author Nicholas
 *
 */
public class SowingAction extends Action{
	private Location location;
	private Crop crop = new Crop();
	
	/**
	 * Constructor
	 * @param location the location to sow a Crop
	 */
	public SowingAction(Location location) {
		if(location == null) {
			throw new NullPointerException("Location to sow a crop must not be null.");
		}
		this.location = location;
	}
	
	@Override
	public String execute(Actor actor, GameMap map) {
        location.setGround(crop);
		return menuDescription(actor);
	}

	@Override
	public String menuDescription(Actor actor) {
		// TODO Auto-generated method stub
		return actor +" sows a new crop";
	}

}
