package game;

import java.util.Random;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Weapon;

/**
 * Special Action for attacking other Actors.
 */
public class AttackAction extends Action {

	private Player player;
	/**
	 * The Actor that is to be attacked
	 */
	protected Actor target;
	/**
	 * Random number generator
	 */
	protected Random rand = new Random();

	/**
	 * Constructor.
	 * 
	 * @param target the Actor to attack
	 */
	public AttackAction(Actor target) {
		this.target = target;
	}
	
	@Override
	public String execute(Actor actor, GameMap map) {
		int damage = 0;
		String result = "";
		Weapon weapon = actor.getWeapon();

		if (actor.attackMissed(weapon)) {
			return actor + " misses " + target;
		}
		
		if(actor.getClass() == Player.class) {
			player = (Player)actor;
		}
		
		if(weapon.getClass().equals(Sniper.class)) {
			if(player.getAimCount() == 0) {
				damage = weapon.damage();
				target.hurt(damage);
				result = actor + " " + weapon.verb() + " " + target + " for " + damage + " damage";
			} else if(player.getAimCount() == 1) {
				damage = weapon.damage()*2;
				target.hurt(damage);
				result = actor + " " + weapon.verb() + " " + target + " for " + damage + " damage";
			} else if(player.getAimCount() >= 2) {
				damage = weapon.damage()*10;
				target.hurt(damage);
				result = actor + " " + weapon.verb() + " " + target + " for an instakill";
			}
		}else {
			damage = weapon.damage();
			target.hurt(damage);
			result = actor + " " + weapon.verb() + " " + target + " for " + damage + " damage";
		}
		
		result += actor.bonusEffect(weapon, map);
		result += target.bonusEffect(weapon, map);
		
		if (!target.isConscious()) {
			if(target.hasCapability(ZombieCapability.ALIVE) && target.getClass() != Player.class) {
				HumanCorpse humanCorpse = new HumanCorpse(target.toString());
				map.locationOf(target).addItem(humanCorpse);
			}else if(target.hasCapability(ZombieCapability.UNDEAD)){
				Item corpse = new PortableItem("dead " + target, '%');
				map.locationOf(target).addItem(corpse);
			}
			
			
			Actions dropActions = new Actions();
			for (Item item : target.getInventory())
				dropActions.add(item.getDropAction());
			for (Action drop : dropActions)		
				drop.execute(target, map);
			map.removeActor(target);
			
			result += System.lineSeparator() + target + " is killed";
		}

		return result;
	}

	@Override
	public String menuDescription(Actor actor) {
		return actor + " attacks " + target;
	}
	
	
}
