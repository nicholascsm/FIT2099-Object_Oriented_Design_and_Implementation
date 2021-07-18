package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.DoNothingAction;
import edu.monash.fit2099.engine.GameMap;

/**
 * A Farmer
 * @author Nicholas
 *
 */
public class Farmer extends Human{
	private Behaviour[] behaviours = {
			new SowingBehaviour(),
			new HarvestBehaviour(),
			new FertilizeBehaviour(),
			new WanderBehaviour()
			
	};

	/**
	 * Constructor
	 * @param name name of the Farmer
	 * @param displayChar displayChar of the Farmer
	 */
	public Farmer(String name, char displayChar) {
		super(name, 'F');
	}

	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		for (Behaviour behaviour : behaviours) {
			Action action = behaviour.getAction(this, map);
			if (action != null)
				return action;
		}
		return new DoNothingAction();
	}
}
