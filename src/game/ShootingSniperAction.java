package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

/**
 * This class is the class that will implement the damage done by the sniper.
 * It will consume ammo every time the sniper is fired and it uses AttackAction to implement the damage to the target
 * @author Nicholas
 *
 */
public class ShootingSniperAction extends Action{
	private Player player;
	private SniperAmmunition ammo;
	
	/**
	 * Constructor
	 * @param player the player
	 * @param ammo the ammo stack of the sniper that is currently consuming from
	 */
	public ShootingSniperAction(Player player, SniperAmmunition ammo) {
		this.player = player;
		this.ammo = ammo;
	}
	
	@Override
	public String execute(Actor actor, GameMap map) {
		ammo.shotOnce();
		return shootsTarget(map);
	}

	@Override
	public String menuDescription(Actor actor) {
		return actor +" shoots "+player.getTarget();
	}
	
	/**
	 * The method to implement the damage to the target by using AttackAction
	 * @param map the GameMap
	 * @return a string of the result from running to AttackAction class
	 */
	private String shootsTarget(GameMap map) {
		Action action = new AttackAction(player.getTarget());
		String result = System.lineSeparator() + action.execute(player, map);
		player.setTarget(null);
		player.setDefaultAimCount();
		player.setDefaulttrackAimCount();
		return result;
	}
	

}
