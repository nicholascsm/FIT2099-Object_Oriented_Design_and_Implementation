package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Ground;

/**
 * A ground to place a Vehicle. It can only be entered by the Player.
 * 
 * @author Bryan Ho
 *
 */
public class PlayerGround extends Ground {
	
	/**
	 * Constructor.
	 */
	public PlayerGround() {
		super('*');
	}
	
	@Override
	public boolean canActorEnter(Actor actor) {
		return actor.hasCapability(ZombieCapability.PLAYER);
	}
	
	@Override
	public boolean blocksThrownObjects() {
		return true;
	}

}
