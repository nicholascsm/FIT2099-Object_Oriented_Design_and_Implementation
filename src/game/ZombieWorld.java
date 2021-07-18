package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.World;

/**
 * The Zombie World of the game. It has a few additional criterias to end the game:<br>
 * 
 * - If Player is dead<br>
 * 
 * - If all Humans are dead<br>
 * 
 * - If all Zombies including Mambo Marie are dead<br>
 * 
 * The victory condition for the Player is to kill all Zombies as well as Mambo Marie.
 * The losing condition is when the Player is killed or all Humans are dead.
 * 
 * @author Bryan Ho
 *
 */
public class ZombieWorld extends World {
	private boolean allHumansDead = false;
	private boolean allZombiesDead = false;
	
	/**
	 * Constructor.
	 * 
	 * @param display The display that will display this World.
	 */
	public ZombieWorld(Display display) {
		super(display);
	}
	
	/**
	 * Returns True if the game is still running.
	 * 
	 * The game is considered to still be running if the Player is alive, all Humans are alive,
	 * and all Zombies including Mambo Marie are alive.
	 * 
	 * @return True if the all Humans including the Player and all Zombies are alive, False otherwise.
	 */
	@Override
	protected boolean stillRunning() {
		return playerAlive() && humansAlive() && zombiesAlive();
	}
	
	/**
	 * Returns True if the Player is still alive.
	 * 
	 * @return True if the Player is alive, False otherwise.
	 */
	protected boolean playerAlive() {
		return actorLocations.contains(player);
	}
	
	/**
	 * Returns True if all Humans in the game are still alive.
	 * 
	 * @return True if all Humans are alive, False otherwise.
	 */
	protected boolean humansAlive() {
		for (Actor actor : actorLocations) {
			if (actor.hasCapability(ZombieCapability.ALIVE) && !actor.equals(player))
				return true;
		}
		
		allHumansDead = true;
		return false;
	}
	
	/**
	 * Returns True if all Zombies in the game are still alive.
	 * 
	 * @return True if all Zombies are alive, False otherwise.
	 */
	protected boolean zombiesAlive() {
		for (Actor actor : actorLocations) {
			if (actor.hasCapability(ZombieCapability.UNDEAD))
				return true;
		}
		
		allZombiesDead = true;
		return false;
	}
	
	/**
	 * Returns a string that can be displayed when the game ends.
	 * 
	 * Additional messages are returned when the victory condition or lose
	 * condition is achieved.
	 * 
	 * @return A string when the game ends.
	 */
	@Override
	protected String endGameMessage() {
		String message = "";
		
		if (allHumansDead)
			message += "All humans are dead" + System.lineSeparator();
		
		if (allZombiesDead) {
			message += "All zombies are dead" + System.lineSeparator();
			message += "Congratulations! You have won the game!" + System.lineSeparator();
		}
		
		return message += "Game Over";
	}

}
