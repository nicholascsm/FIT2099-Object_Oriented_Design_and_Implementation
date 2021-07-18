package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

/**
 * This class represents the action of aiming at a target
 * @author Nicholas
 *
 */
public class AimAction extends Action{
	private Actor target;
	private Player player;
	
	/**
	 * Constructor
	 * @param player the player
	 */
	public AimAction(Player player) {
		if(player.getTarget() == null) {
			throw new NullPointerException("Must have a target to aim at, target cant be null.");
		}
		this.target = player.getTarget();
		this.player = player;
	}
	
	/**
	 * Increase the aimOnce() of the player to indicate that the player is aiming at a target and 
	 * set the hitpointcheck to keep track of the player's hitpoints in the event that the player 
	 * might be attacked while he is aiming.
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		player.aimOnce();
		player.setHitpointCheck();
		return player+" aims at "+target;
	}

	@Override
	public String menuDescription(Actor actor) {
		return actor +" aims at "+target+" again";
	}

}
