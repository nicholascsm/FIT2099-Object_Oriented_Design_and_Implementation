package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

/**
 * An Action that summons Zombies onto the game map.
 * 
 * @author Bryan Ho
 *
 */
public class SummonZombieAction extends Action {
	private int numberOfZombies;
	private String zombieName;
	private String verb;
	
	/**
	 * Constructor.
	 * 
	 * @param numberOfZombies The number of Zombies to summon.
	 * @param zombieName The names for all the Zombies to be summoned.
	 * @param verb The verb that causes the summoning of the Zombies.
	 */
	public SummonZombieAction(int numberOfZombies, String zombieName, String verb) {
		if (numberOfZombies < 0)
			throw new IllegalArgumentException("Number of zombies to summon must be equal or greater than zero");
		if (zombieName == null || verb == null)
			throw new IllegalArgumentException("Name of zombie minions and verb cannot be null");
		
		this.numberOfZombies = numberOfZombies;
		this.zombieName = zombieName;
		this.verb = verb;
	}
	
	/**
	 * Randomly spawns Zombies on the map.
	 * 
	 * @param actor The Actor that summons the Zombies.
	 * @param map The map to summon Zombies on.
	 * @return A string description indicating that actor has summoned Zombies.
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		int x, y;
		for (int i = 0; i < numberOfZombies; i++) {
			do {
				x = (int) Math.floor(Math.random() * map.getXRange().max());
				y = (int) Math.floor(Math.random() * map.getYRange().max());
			}
			while (map.at(x, y).containsAnActor());
			map.at(x, y).addActor(new Zombie(zombieName));
		}
		
		return String.format("%s %s and summons %d zombies!", actor, verb, numberOfZombies);

	}

	@Override
	public String menuDescription(Actor actor) {
		return String.format("%s %s to summon %d zombies", actor, verb, numberOfZombies);
	}

}
