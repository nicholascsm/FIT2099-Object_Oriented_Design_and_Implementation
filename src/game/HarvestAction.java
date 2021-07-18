package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Exit;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

/**
 * Action for player and Farmer to harvest ripe Crops
 * @author Nicholas
 *
 */
public class HarvestAction extends Action{
	private Location location;
	private Dirt dirt = new Dirt();
	private Food food = new Food("food",10);
	
	/**
	 * Constructor
	 * @param location location of the Crop
	 */
	public HarvestAction(Location location) {
		this.location = location;
	}
	
	@Override
	public String execute(Actor actor, GameMap map) {
		if(actor.getClass() == Player.class) {
			actor.addItemToInventory(food);
			location.setGround(dirt);
		}
		
		if(actor.getClass() != Player.class) {
			location.setGround(dirt);
			location.addItem(food);
		}
		
		return actor+" harvested a crop";
	}

	@Override
	public String menuDescription(Actor actor) {
		// TODO Auto-generated method stub
		return actor+" harvests the crop";
	}

}
