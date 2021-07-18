package game;

import java.util.Random;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.DoNothingAction;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Weapon;

/**
 * Class representing an ordinary human.
 * 
 * 
 * @author ram
 *
 */
public class Human extends ZombieActor {
	private Random rand = new Random();
	private Behaviour[] behaviours = {
			new PickUpFoodBehaviour(),
			new EatBehaviour(),
			new WanderBehaviour()
	};

	/**
	 * The default constructor creates default Humans
	 * 
	 * @param name the human's display name
	 */
	public Human(String name) {
		super(name, 'H', 50, ZombieCapability.ALIVE);
	}
	
	/**
	 * Constructor for Humans with different roles
	 * @param name the human's display name
	 * @param displayChar character that will represent the Human in the map
	 */
	protected Human(String name, char displayChar) {
		super(name, displayChar, 50, ZombieCapability.ALIVE);
	}
	
	/**
	 * The protected constructor can be used to create subtypes
	 * of Human, such as the Player
	 * 
	 * @param name the human's display name
	 * @param displayChar character that will represent the Human in the map 
	 * @param hitPoints amount of damage that the Human can take before it dies
	 */
	protected Human(String name, char displayChar, int hitPoints) {
		super(name, displayChar, hitPoints, ZombieCapability.ALIVE);
	}

	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		// FIXME humans are pretty dumb, maybe they should at least run away from zombies?
		for (Behaviour behaviour : behaviours) {
			Action action = behaviour.getAction(this, map);
			if (action != null)
				return action;
		}
		return new DoNothingAction();
		
	}
	
	/**
	 * Humans can fight better than Zombies, so their chances of missing
	 * their attacks are lower.
	 * 
	 * @param weapon The weapon the Human is holding.
	 * @return True if their attack miss, False otherwise.
	 */
	@Override
	public boolean attackMissed(Weapon weapon) {
		if(weapon.getClass().equals(Shotgun.class)) {
			return rand.nextDouble() < 0.75;
		}
		return rand.nextInt(100) < 20;
	}
}
