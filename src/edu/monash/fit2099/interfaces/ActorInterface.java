package edu.monash.fit2099.interfaces;

import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Weapon;

/**
 * This interface provides the ability to add methods to Actor, without modifying code in the engine,
 * or downcasting references in the game.   
 */

public interface ActorInterface {
	/**
	 * Checks if an Actor missed an attack or not. It takes in a weapon so that
	 * different chances of missing an attack can be implemented based on the
	 * equipped weapon. (e.g. Kicking might have a higher chance of missing compared
	 * to punching)
	 * 
	 * By default, an Actor never misses their attack.
	 * 
	 * @param weapon The weapon that the Actor is holding.
	 * @return True if they miss, False otherwise.
	 */
	default boolean attackMissed(Weapon weapon) {
		return false;
	}

	/**
	 * Adds an additional bonus effect for the Actor. The effect can be applied to Actors
	 * in cases such as every time their turn starts, whenever they successfully attack
	 * someone, or even when they are hurt. Different effects can be set based on the weapon used
	 * as well. (e.g. Restores the Actor's HP after attacking an enemy with a special weapon)
	 * 
	 * By default, an Actor has no bonus effect, so an empty string is returned.
	 * 
	 * @param weapon The weapon the Actor is holding.
	 * @param map The GameMap containing the Actor.
	 * @return The description of the effect that is applied to the Actor.
	 */
	default String bonusEffect(Weapon weapon, GameMap map) {
		return "";
	}
}
