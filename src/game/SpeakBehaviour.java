package game;

import java.util.Random;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

/**
 * A class that generates a SpeakAction where the Actor will say
 * something at a probability chance.
 * 
 * @author Bryan Ho
 *
 */
public class SpeakBehaviour implements Behaviour {
	private Random rand = new Random();
	private String sentence;
	private int speakChance = 100;
	
	/**
	 * 1st constructor.
	 * 
	 * Sets the sentence that the Actor will be speaking and the
	 * probability that they will speak it (0% - 100%).
	 * 
	 * @param sentence The sentence that the Actor will speak.
	 * @param speakChance The probability that the Actor will speak the sentence. (0% - 100%)
	 */
	public SpeakBehaviour(String sentence, int speakChance) {
		if (speakChance < 0 || speakChance > 100)
			throw new IllegalArgumentException("Speak chance must be within 0 to 100.");
		if (sentence == null)
			throw new NullPointerException("Sentence must not be null.");
		this.sentence = sentence;
		this.speakChance = speakChance;
	}
	
	/**
	 * 2nd constructor.
	 * 
	 * Sets the sentence that the Actor will be speaking.
	 * The probability that they will speak it is defaulted to 100%.
	 * 
	 * @param sentence The sentence that the Actor will speak.
	 */
	public SpeakBehaviour(String sentence) {
		if (sentence == null)
			throw new NullPointerException("Sentence must not be null.");
		this.sentence = sentence;
	}
	
	@Override
	public Action getAction(Actor actor, GameMap map) {
		if (rand.nextInt(100) < speakChance)
			return new SpeakAction(sentence);
		else
			return null;
	}

}
