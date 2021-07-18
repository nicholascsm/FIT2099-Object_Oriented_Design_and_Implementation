package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

/**
 * An Action that allows an Actor to say something.
 * 
 * @author Bryan Ho
 */
public class SpeakAction extends Action {
	private String sentence;
	
	/**
	 * Constructor.
	 * 
	 * @param sentence The sentence to say.
	 */
	public SpeakAction(String sentence) {
		if (sentence == null)
			throw new NullPointerException("Sentence must not be null.");
		this.sentence = sentence;
	}
	
	@Override
	public String execute(Actor actor, GameMap map) {
		return menuDescription(actor);
	}

	@Override
	public String menuDescription(Actor actor) {
		return actor + " says " + sentence;
	}

}
