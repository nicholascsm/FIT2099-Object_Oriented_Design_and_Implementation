package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.DoNothingAction;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.IntrinsicWeapon;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.MoveActorAction;
import edu.monash.fit2099.engine.PickUpItemAction;
import edu.monash.fit2099.engine.Weapon;

/**
 * A Zombie.
 * 
 * Zombies have 2 intrinsic attacks -- bite and punch. A bite attack allows them
 * to recover their health but it is harder to land. They also have limbs that can be
 * dropped onto the map if they are attacked, which will also weaken them in the
 * process by restricting them from doing certain actions that they usually could.
 * 
 * @author Bryan Ho
 *
 */
public class Zombie extends ZombieActor {
	private Behaviour[] behaviours = {
			new SpeakBehaviour("Braaaaaaains", 10),
			new PickUpWeaponBehaviour(),
			new AttackBehaviour(ZombieCapability.ALIVE),
//			new HuntBehaviour(Human.class, 10),
//			new WanderBehaviour()
	};
	
	private ArrayList<Limb> limbs = new ArrayList<>(Arrays.asList(
			new Limb("left arm", 'a', new ZombieClub()),
			new Limb("right arm", 'a', new ZombieClub()),
			new Limb("left leg", 'l', new ZombieMace()),
			new Limb("right leg", 'l', new ZombieMace())
	));
	
	private Random rand = new Random();
	
	private int numberOfArms = 2;
	private int numberOfLegs = 2;
	private int biteChance = 50;
	private int turn = 0;
	private Actions additionalEffects = new Actions();
	
	/**
	 * Constructor.
	 * 
	 * @param name The name of the Zombie.
	 */
	public Zombie(String name) {
		super(name, 'Z', 100, ZombieCapability.UNDEAD);
	}
	
	/**
	 * The Zombie has a default probability of 50% to either bite or punch.
	 * The probability of biting will increase every time the Zombie loses
	 * an arm.
	 * 
	 * @return An IntrinsicWeapon of a bite attack or punch attack.
	 */
	@Override
	public IntrinsicWeapon getIntrinsicWeapon() {
		if (rand.nextInt(100) < biteChance)
			return new IntrinsicWeapon(15, "bites");
		else
			return new IntrinsicWeapon(10, "punches");
	}
	
	/**
	 * The Zombie has a 10% chance to say "Braaaaaaains".<br>
	 * If not, it will pick up a weapon at its location.<br>
	 * If not, it will attack an adjacent Human if there is one.<br>
	 * If not, it will chase any Human within 10 spaces.<br>
	 * If no humans are close enough, it will wander randomly.<br><br>
	 * 
	 * Zombies can only hold one weapon at a time. They must have at least an
	 * arm to be able to hold a weapon. If a Zombie does not have a leg,
	 * it cannot move every second turn. If a Zombie has no legs, they cannot
	 * move at all.
	 * 
	 * @param actions List of possible Actions.
	 * @param lastAction Previous Action, if it was a multiturn action.
	 * @param map The map where the current Zombie is.
	 * @param display The Display where the Zombie's utterances will be displayed.
	 * @return An Action from one of its behaviours.
	 */
	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		turn++;
		
		for (Behaviour behaviour : behaviours) {
			Action action = behaviour.getAction(this, map);
			if (action != null) {
				if (canMove(action) && canPickUp(action))
					return action;
			}
		}
		return new DoNothingAction();
	}
	
	/**
	 * If a Zombie bites, their chance of missing that attack is a lot
	 * higher than any other attack they use.
	 * 
	 * @param weapon The weapon that the Zombie is holding.
	 * @return True if they miss, False otherwise.
	 */
	@Override
	public boolean attackMissed(Weapon weapon) {
		if (weapon.verb().equals("bites"))
			return rand.nextInt(100) < 80;
		else
			return rand.nextInt(100) < 50;
	}
	
	/**
	 * If a Zombie bites, they recover 5 HP.
	 * If not, the Zombie will drop any limbs if they are attacked.
	 * 
	 * @param weapon The weapon that the Zombie is holding.
	 * @param map The map to apply the effect on if applicable.
	 * @return A description that the Zombie recovers 5 HP after a successful bite attack.
	 */
	@Override
	public String bonusEffect(Weapon weapon, GameMap map) {
		if (weapon.verb().equals("bites")) {
			heal(5);
			return System.lineSeparator() + name + " recovers 5 HP.";
		}
		else {
			String message = "";
			
			for (Action action : additionalEffects)
				message += System.lineSeparator() + action.execute(this, map);
			
			additionalEffects.clear();
			return message;
		}
	}
	
	/**
	 * After inflicting damage to the Zombie, there is a 80% chance for the
	 * Zombie to lose a random limb. If they do lose a limb, the Zombie is
	 * weakened according to the type of limb they lost.
	 * 
	 * @param points The amount of damage inflicted on the Zombie.
	 */
	@Override
	public void hurt(int points) {
		hitPoints -= points;
		
		if (rand.nextInt(100) < 100 && limbs.size() > 0) {
			Item limb = limbs.remove(rand.nextInt(limbs.size()));
			additionalEffects.add(limb.getDropAction());
			weakenZombie(limb.getDisplayChar());
		}
	}
	
	/**
	 * Weaken a Zombie based on the type of limb they lost.
	 * 
	 * If they lose an arm, the probability of them attempting to bite instead
	 * of punch increases and there is a 50% chance of them to drop
	 * a weapon it is holding. If both arms are lost, they will definitely
	 * drop any weapons they are holding.
	 * 
	 * If they lose a leg, it will affect the Zombie's movement every time
	 * their turn starts.
	 * 
	 * @param displayChar The character that represents the limb.
	 */
	private void weakenZombie(char displayChar) {
		if (displayChar == 'a') {
			numberOfArms--;
			biteChance = Math.min(biteChance + 25, 100);
			
			if (rand.nextInt(100) < 50 || numberOfArms == 0) {
				for (Item item : getInventory())
					additionalEffects.add(item.getDropAction());
			}
		}
		else if (displayChar == 'l')
			numberOfLegs--;
		else
			throw new IllegalArgumentException("Invalid character called.");
	}
	
	/**
	 * Checks if a Zombie can move. If they lost a leg, they cannot move
	 * every second turn. If they lost both legs, they cannot move at all.
	 * 
	 * @param action The Action to check if the Zombie can perform.
	 * @return True if they can perform, False otherwise.
	 */
	private boolean canMove(Action action) {
		if (action instanceof MoveActorAction) {
			if ((numberOfLegs < 2 && turn % 2 == 1) || (numberOfLegs < 1))
				return false;
		}
		return true;
	}
	
	/**
	 * Checks if a Zombie can pick up a weapon. They can only pick up
	 * one weapon at any given time. If they have no arms, they cannot
	 * pick up any weapons at all.
	 * 
	 * @param action The Action to check if the Zombie can perform.
	 * @return True if they can perform, False otherwise.
	 */
	private boolean canPickUp(Action action) {
		if (action instanceof PickUpItemAction) {
			if (getInventory().size() >= 1 || numberOfArms < 1)
				return false;
		}
		return true;
	}
}
