package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.Exit;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;
import edu.monash.fit2099.engine.Menu;

/**
 * This action class displays an options submenu for the player to choose which available direction the player
 * could shoot the shotgun at.
 * @author Nicholas
 *
 */
public class ShootingShotgunMenu extends Action {
	private Menu menu = new Menu();
	private Display display;
	private Location playerLocation;
	private Actions actions = new Actions();
	
	/**
	 * Constructor
	 * @param playerLocation the player's location
	 * @param display the display
	 */
	public ShootingShotgunMenu(Location playerLocation, Display display) {
		this.playerLocation = playerLocation;
		this.display = display;
	}
	
	/**
	 * Get the available directions where it has a space between the player and the end of the map
	 * for edge detection of the map. And display the choices of shooting the shotgun in that 
	 * direction
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		for (Exit exit : playerLocation.getExits()) {
			actions.add(new ShootingShotgunAction(exit.getName()));
		}
		
		return menu.showMenu(actor, actions, display).execute(actor, map);
	}

	@Override
	public String menuDescription(Actor actor) {
		return actor + " shoots using a "+actor.getWeapon();
	}

}
