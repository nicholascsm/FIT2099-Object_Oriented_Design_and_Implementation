package game;

import java.util.Random;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;
import edu.monash.fit2099.engine.Menu;
import edu.monash.fit2099.engine.Weapon;

/**
 * Class representing the Player.
 */
public class Player extends Human {

	private Menu menu = new Menu();
	private int trackAimCount = -1;
	private int aimCount = 0;
	private Actor targetSnipe;
	private int currentHitPoints;
	private int hitpointCheck;
	private boolean printed = false;
	private Random rand = new Random();
	
	/**
	 * Constructor.
	 *
	 * @param name        Name to call the player in the UI
	 * @param displayChar Character to represent the player in the UI
	 * @param hitPoints   Player's starting number of hitpoints
	 */
	public Player(String name, char displayChar, int hitPoints) {
		super(name, displayChar, hitPoints);
		addCapability(ZombieCapability.PLAYER);
		currentHitPoints = hitPoints;
	}

	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		Location here = map.locationOf(this);
		
		for (Item item : getInventory()) {
			if (item instanceof CraftableItem)
				actions.add(new CraftAction((CraftableItem) item));
		}
		
		for(Item item: getInventory()) {
			if(item instanceof Food) {
				actions.add(new EatAction((Food)item));
			}
		}
		
		if(map.locationOf(this).getGround().getDisplayChar() == 'C') {
			actions.add(new HarvestAction(map.locationOf(this)));
		}
		
		if(this.getWeapon().getClass() == Shotgun.class) {
			boolean ammo = false;
			for(int i = 0; i< this.getInventory().size(); i++) {
				if(this.getInventory().get(i).getClass() == ShotgunAmmunition.class) {
					ammo = true;
					break;
				}
				ammo = false;
			}
			if(ammo == true) {
				actions.add(new ShootingShotgunMenu(here, display));
			}else {
				display.println("Shotgun has no ammo!");
			}
		}
		
		if(this.getWeapon().getClass() == Sniper.class) {
			boolean ammo = false;
			SniperAmmunition item = null;
			for(int i = 0; i< this.getInventory().size(); i++) {
				if(this.getInventory().get(i).getClass() == SniperAmmunition.class) {
					ammo = true;
					item = (SniperAmmunition)this.getInventory().get(i);
					break;
				}
				ammo = false;
			}
			if(ammo == true) {
				actions.add(new ChooseTargetMenu(this, display, item));
				if(aimCount != 0) {
					if(currentHitPoints < hitpointCheck && printed == false) {
						display.println("Player was attacked and lost concentration! Target lost.");
						printed = true;
						this.setTarget(null);
						trackAimCount = -2;
						aimCount = 0;
					}
					if(aimCount - trackAimCount == 3) {
						display.println("Player lost concentration! Target lost.");
						this.setTarget(null);
						trackAimCount = -1;
						aimCount = 1;
					}
					trackAimCount++;
					if(aimCount >= 1 && trackAimCount != aimCount) {
						actions.add(new ShootingSniperAction(this, item));
						actions.add(new AimAction(this));
					}
					if(trackAimCount == aimCount && printed == false) {
						display.println("Player lost concentration! Target lost.");
						this.setTarget(null);
						trackAimCount = -1;
						aimCount = 0;
					}
					
				}
				printed = false;
				
			}else {
				display.println("Sniper has no ammo!");
			}
		}
		
		actions.add(new QuitGameAction());
		
		if (lastAction.getNextAction() != null)
			return lastAction.getNextAction();
		
		return menu.showMenu(this, actions, display);
	}
	
	@Override
	public void hurt(int points) {
		super.hitPoints -= points;
		currentHitPoints -= points;
	}
	
	/**
	 * To set the currentHitPoints to the hitpointCheck to keep track if the player gets attacked when aiming at a target
	 */
	public void setHitpointCheck() {
		hitpointCheck = currentHitPoints; 
	}
	
	/**
	 * To increase the aimCount by 1 when the player aims at the target again
	 */
	public void aimOnce() {
		aimCount++;
	}
	
	/**
	 * A function to set aimCount back to zero
	 */
	public void setDefaultAimCount() {
		aimCount = 0;
	}
	
	/**
	 * To get the aimCount value
	 * @return the aimCount
	 */
	public int getAimCount() {
		return aimCount;
	}
	
	/**
	 * To set the tracker for the aimCount to its default value to track if the player's next move is aiming or not 
	 */
	public void setDefaulttrackAimCount() {
		trackAimCount = -1;
	}
	
	/**
	 * To get the target of the player is aiming at
	 * @return the actor that the player is aiming at
	 */
	public Actor getTarget() {
		return targetSnipe;
	}
	
	/**
	 * To set the target to another actor
	 * @param actor next target
	 */
	public void setTarget(Actor actor) {
		targetSnipe = actor;
	}
	
	@Override
	public boolean attackMissed(Weapon weapon) {
		if(weapon.getClass().equals(Shotgun.class)) {
			return rand.nextDouble() > 0.75;
		}
		
		if(weapon.getClass().equals(Sniper.class)) {
			if(aimCount == 0) {
				return rand.nextDouble() > 0.75;
			}else if(aimCount == 1) {
				return rand.nextDouble() > 0.90;
			}else if(aimCount >=2) {
				return false;
			}
		}
		return true;
	}
	
}
