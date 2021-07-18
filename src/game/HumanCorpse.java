package game;

import edu.monash.fit2099.engine.*;

/**
 * Class to represent a human corpse that is still a PortableItem.
 * After 5 turns, the HumanCorpse will turn into a Zombie
 * @author Nicholas
 *
 */
public class HumanCorpse extends PortableItem{
	private int turns = 0;
	private boolean added = true;
	private Zombie zombie = new Zombie(name);
	
	public HumanCorpse(String name) {
		super(name, '&');
	}
	
	@Override
	public void tick(Location currentLocation){
		if (!added) {
			added = addCorpseZombie(currentLocation, zombie, false);
		}
		
		if(turns == 5) {
			added = addCorpseZombie(currentLocation, zombie, false);
		}
		turns++;
		
	}
	
	@Override
	public void tick(Location currentLocation, Actor actor) {
		if(!added) {
			added = addCorpseZombie(currentLocation, zombie,true);
			actor.removeItemFromInventory(this);
		}
		
		if(turns == 5) {
			added = addCorpseZombie(currentLocation, zombie,true);
			if(added) {
				actor.removeItemFromInventory(this);
			}
			
		}
		turns++;
	}
	
	/**
	 * Adds a new Zombie at a Location
	 * @param itemLocation the item's location
	 * @param newZombie a new Zombie actor
	 * @param picked a boolean to check if the HumanCorpse PortableItem is picked up or not
	 * @return returns a true if a new Zombie is added and false if its not
	 */
	private boolean addCorpseZombie(Location itemLocation, Zombie newZombie, Boolean picked) {
		if(!picked) {
			Location aLocation = checkLocation(itemLocation, newZombie);
			if(aLocation != null) {
				aLocation.addActor(newZombie);
			}
			
			for(int i = 0; i< itemLocation.getItems().size(); i++) {
				if(itemLocation.getItems().get(i).getClass() == HumanCorpse.class) {
					itemLocation.removeItem(itemLocation.getItems().get(i));
				}
			}
			return true;
			
		}else if(picked) {
			Location aLocation = checkLocation(itemLocation, newZombie);
			
			if(aLocation != null) {
				aLocation.addActor(newZombie);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Method to check if an actor be added to the location, else return an adjacent location
	 * @param itemLocation the item's location
	 * @param zombie the new Zombie
	 * @return returns a new location if the itemLocation is occupied, else return null. If the itemLocation is empty, just return the itemLocation
	 */
	private Location checkLocation(Location itemLocation, Zombie zombie) {
		if(itemLocation.containsAnActor() == true) {
			for (Exit exit : itemLocation.getExits()) {
	            Location destination = exit.getDestination();
	            if(destination.canActorEnter(zombie) == true) {
	            	return destination;
	            }
	            else {
	            	return null; 
	            }
			}
		}
		return itemLocation;
		
	}

}
