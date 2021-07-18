package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

/**
 * Action for Farmers to fertilize Crop
 * @author Nicholas
 *
 */
public class FertilizeAction extends Action{
	private Crop crop;
	
	/**
	 * Constructor
	 * @param crop Crop to be fertilized
	 */
	public FertilizeAction(Crop crop) {
		if(crop == null) {
			throw new NullPointerException("Crop to be fertilized must not be null.");
		}
		this.crop = crop;
	}
	
	@Override
	public String execute(Actor actor, GameMap map) {
		// TODO Auto-generated method stub
		crop.fertilize();
		return menuDescription(actor);
	}

	@Override
	public String menuDescription(Actor actor) {
		// TODO Auto-generated method stub
		return actor+" fertilized a crop";
	}

}
