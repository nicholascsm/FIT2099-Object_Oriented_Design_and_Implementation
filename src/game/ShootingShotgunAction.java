package game;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;

/**
 * The class to fire the shotgun with an area of damage.
 * The area of 
 * @author Nicholas
 *
 */
public class ShootingShotgunAction extends Action{
	private String direction;
	private HashMap<String, List<Pair>> ranges = new HashMap<String, List<Pair>>() {
		private static final long serialVersionUID = 1L;

		{
			put("North", Arrays.asList(
					new Pair(0, -1), new Pair(-1, -2), new Pair(0, -2), new Pair(1, -2),
					new Pair(-2, -3), new Pair(-1, -3), new Pair(0, -3), new Pair(1, -3), new Pair(2, -3)));
			put("South", Arrays.asList(
					new Pair(0, 1), new Pair(-1, 2), new Pair(0, 2), new Pair(1, 2),
					new Pair(-2, 3), new Pair(-1, 3), new Pair(0, 3), new Pair(1, 3), new Pair(2, 3)));
			put("East", Arrays.asList(
					new Pair(1, 0), new Pair(2, -1), new Pair(2, 0), new Pair(2, 1),
					new Pair(3, -2), new Pair(3, -1), new Pair(3, 0), new Pair(3, 1), new Pair(3, 2)));
			put("West", Arrays.asList(
					new Pair(-1, 0), new Pair(-2, -1), new Pair(-2, 0), new Pair(-2, 1),
					new Pair(-3, -2), new Pair(-3, -1), new Pair(-3, 0), new Pair(-3, 1), new Pair(-3, 2)));
			put("North-East", Arrays.asList(
					new Pair(1, -1), new Pair(2, -1), new Pair(3, -1), new Pair(4, -1), new Pair(1, -2),
					new Pair(2, -2), new Pair(3, -2), new Pair(1, -3), new Pair(4, -2), new Pair(2, -3), new Pair(3, -3),
					new Pair(1, -4), new Pair(2, -4)));
			put("North-West", Arrays.asList(
					new Pair(-1, -1), new Pair(-2, -1), new Pair(-3, -1), new Pair(-4, -1), new Pair(-1, -2),
					new Pair(-2, -2), new Pair(-3, -2), new Pair(-4, -2),new Pair(-1, -3), new Pair(-2, -3), new Pair(-3, -3),
					new Pair(-1, -4), new Pair(-2, -4)));
			put("South-East", Arrays.asList(
					new Pair(1, 1), new Pair(2, 1), new Pair(3, 1), new Pair(4, 1),new Pair(1, 2),
					new Pair(2, 2), new Pair(3, 2), new Pair(4, 2), new Pair(1, 3), new Pair(2, 3), new Pair(3, 3),
					new Pair(1, 4), new Pair(2, 4)));
			put("South-West", Arrays.asList(
					new Pair(-1, 1), new Pair(-2, 1), new Pair(-3, 1), new Pair(-4, 1), new Pair(-1, 2),
					new Pair(-2, 2), new Pair(-3, 2), new Pair(-4, 2),new Pair(-1, 3), new Pair(-2, 3), new Pair(-3, 3),
					new Pair(-1, 4), new Pair(-2, 4)));
		}
	};
	
	/**
	 * Constructor
	 * @param direction the direction to fire the shotgun
	 */
	public ShootingShotgunAction(String direction) {
		if (direction == null)
			throw new IllegalArgumentException("Direction must not be null");
		this.direction = direction;
	}

	/**
	 * From the location of the actor, we will first get Array in the HashMap represented as ranges
	 * to obtain the coordinates of the area that the Shotgun will deal damage. After getting the 
	 * x and y values, we will first check if the location is within the map or not. If it is then 
	 * only we call an AttackAction to attack the actor at the location specified by the x,y values.
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		Location actorLocation = map.locationOf(actor);
		String result = "";
		boolean hasTarget = false;
		
		for (Pair pair : ranges.get(direction)) {
			int x = actorLocation.x() + pair.getX();
			int y = actorLocation.y() + pair.getY();
			
			if ((x < 0) || (y < 0) || (x > map.getXRange().max()) || (y > map.getYRange().max()))
				continue;
			
			Actor target = map.getActorAt(map.at(x, y));
			if (target != null) {
				if (target.hasCapability(ZombieCapability.UNDEAD)) {
					hasTarget = true;
					Action action = new AttackAction(target);
					result += System.lineSeparator() + action.execute(actor, map);
				}
			}
		}
		
		if (!hasTarget)
			result += System.lineSeparator() + actor + " shot at nothing...";
		
		shotAmmo(actor);
		return result;
	}

	@Override
	public String menuDescription(Actor actor) {
		return actor + " shoots " + direction + " with the shotgun";
	}
	
	/**
	 * To decrease the amount of ammo after each shot
	 * @param actor the actor to check for a ShotgunAmmunition class in their inventory
	 */
	private void shotAmmo(Actor actor) {
		for(Item item : actor.getInventory()) {
			if(item.getClass().equals(ShotgunAmmunition.class)) {
				ShotgunAmmunition ammo = (ShotgunAmmunition) item;
				ammo.shotOnce();
				break;
			}
		}
	}

}
