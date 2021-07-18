package game;

import java.util.ArrayList;
import java.util.Random;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.DoNothingAction;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Weapon;

/**
 * Mambo Marie, a Voodoo priestess and the source of the local zombie epidemic.
 * She will appear at the edge of a game map from time to time. If she is on the
 * map, she will wander around and every 10 turns she will summon 10 Zombies known
 * as her Mambooo minions. She will vanish after 30 turns.
 * 
 * @author Bryan Ho
 *
 */
public class MamboMarie extends ZombieActor {
	private static final int MIN_X = 72;
	private static final int MIN_Y = 20;
	
	private Behaviour[] behaviours = {
			new AttackBehaviour(ZombieCapability.ALIVE),
			new WanderBehaviour()
	};
	
	private ArrayList<GameMap> spawnableMaps;
	private GameMap hidingMap;
	private boolean appeared = false;
	private Random rand = new Random();
	private int turn = 0;
	
	/**
	 * Constructor.
	 * 
	 * @param spawnableMaps A list of GameMaps where Mambo Marie can appear.
	 * @param hidingMap The map for her to stay hidden from the actual game map.
	 */
	public MamboMarie(ArrayList<GameMap> spawnableMaps, GameMap hidingMap) {
		super("Mambo Marie", 'M', 200, ZombieCapability.UNDEAD);
		
		if (spawnableMaps == null || spawnableMaps.isEmpty())
			throw new IllegalArgumentException("Mambo Marie must have at least 1 map to spawn on");
		if (hidingMap == null)
			throw new IllegalArgumentException("Mambo Marie must have a map to hide in");
		
		this.spawnableMaps = spawnableMaps;
		this.hidingMap = hidingMap;
	}
	
	/**
	 * If Mambo Marie is currently not appeared on any game maps, there is a
	 * 5% chance for her to appear. If she appears, she will wander around the
	 * game map and can attack any nearby Humans. Every 10 turns however, she
	 * will chant and summon 5 Zombies known as her Mambooo minions. After 30 turns,
	 * she will disappear from the game map.
	 * 
	 * @param actions List of possible Actions.
	 * @param lastAction Previous Action, if it was a multiturn action.
	 * @param map The map where Mambo Marie is.
	 * @param display The Display where Mambo Marie's utterances will be displayed.
	 * @return An Action from one of its behaviours.
	 */
	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		if (!appeared && rand.nextInt(100) < 5)
			return appear();

		else if (appeared && turn <= 30) {
			turn++;
			
			if (turn % 10 == 0)
				return new SummonZombieAction(5, "Mambooo", "chants");
			
			for (Behaviour behaviour : behaviours) {
				Action action = behaviour.getAction(this, map);
				if (action != null)
					return action;
			}
			return new DoNothingAction();
		}
		else
			return vanish();
	}
	
	@Override
	public boolean attackMissed(Weapon weapon) {
		return rand.nextInt(100) < 50;
	}
	
	/**
	 * Make Mambo Marie appear on the bottom right area of a random game map.
	 * 
	 * @return An AppearAction that tells the Player that she is on the game map.
	 */
	private Action appear() {
		GameMap spawnMap = spawnableMaps.get(rand.nextInt(spawnableMaps.size()));
		
		int x, y;
		do {
			x = MIN_X + (int) Math.floor(Math.random() * (spawnMap.getXRange().max() - MIN_X + 1));
			y = MIN_Y + (int) Math.floor(Math.random() * (spawnMap.getYRange().max() - MIN_Y + 1));
		} 
		while (spawnMap.at(x, y).containsAnActor());
		
		spawnMap.moveActor(this, spawnMap.at(x, y));
		appeared = true;
		return new AppearAction();
	}
	
	/**
	 * Make Mambo Marie disappear from the game map.
	 * 
	 * @return A HideAction that tells the Player that she is not on the game map.
	 */
	private Action vanish() {
		if (!hidingMap.contains(this)) {
			int x = hidingMap.getXRange().max();
			int y = hidingMap.getYRange().max();
			hidingMap.moveActor(this, hidingMap.at(x, y));
		}
			
		turn = 0;
		appeared = false;
		return new HideAction();
	}

}
