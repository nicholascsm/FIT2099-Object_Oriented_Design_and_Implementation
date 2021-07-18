package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Menu;

/**
 * This action class will display a submenu of options of which target to shoot at.
 * @author Nicholas
 *
 */
public class ChooseTargetMenu extends Action{
	private Actions actions = new Actions();
	private Display display;
	private Menu menu = new Menu();
	private SniperAmmunition ammo;
	private Player player;
	
	/**
	 * Constructor
	 * @param player the player
	 * @param display the display
	 * @param ammo the ammo stack to consume from
	 */
	public ChooseTargetMenu(Player player, Display display, SniperAmmunition ammo) {
		this.player = player;
		this.display = display;
		this.ammo = ammo;
	}
	
	/**
	 * Get all the zombie and mambo marie actors on the map to allow the player to choose an actor to shoot at
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		for(int i = 0; i< 80 ;i++) {
			for(int j = 0; j< 25; j++) {
				if(map.at(i, j).containsAnActor()) {
					if(map.at(i, j).getActor().getClass().equals(Zombie.class) || map.at(i, j).getActor().getClass().equals(MamboMarie.class)) {
						actions.add(new ChoosingTargetAction(map.at(i, j).getActor(),display, ammo, player));
					}
				}
			}
		}
		return menu.showMenu(actor, actions, display).execute(actor, map);
	}

	@Override
	public String menuDescription(Actor actor) {
		return actor + " chooses a target to snipe";
	}

}
