package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Menu;

/**
 * This class displays the option menu to aim at the target or shoot straight away after first selecting the target
 * @author Nicholas
 *
 */
public class ChoosingTargetAction extends Action{
	private Actor target;
	private Menu menu = new Menu();
	private Display display;
	private Actions actions = new Actions();
	private SniperAmmunition ammo;
	private Player player;
	
	/**
	 * Constructor
	 * @param target the target the player chose
	 * @param display the display
	 * @param ammo the ammo stack to consume from
	 * @param player the player
	 */
	public ChoosingTargetAction(Actor target, Display display, SniperAmmunition ammo, Player player) {
		this.target = target;
		this.display = display;
		this.ammo = ammo;
		this.player = player;
	}
	
	/**
	 * This execute method will check is the player has a previous or not first, if its not null,
	 * then set it to null and display that the player lost concentration on the previous target.
	 * If there's no target, then set the new target and display the submenu for options to aim 
	 * at the target again to shoot straight away.
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		if(player.getTarget() != null) {
			display.println("Concentration on previous target lost! ");
			player.setDefaultAimCount();
			player.setDefaulttrackAimCount();
		}
		player.setTarget(target);
		actions.add(new AimAction((Player)actor));
		actions.add(new ShootingSniperAction((Player)actor, ammo));
		return menu.showMenu(actor, actions, display).execute(actor, map);
	}

	@Override
	public String menuDescription(Actor actor) {
		return actor +" chooses to snipe "+target;
	}

}
