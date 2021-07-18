package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.FancyGroundFactory;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.MoveActorAction;

/**
 * The main class for the zombie apocalypse game.
 * Added a new town map. The Player can walk over to a vehicle
 * to move between the main map and the town map.
 * 
 * @author Bryan Ho
 * 
 */
public class Application {

	public static void main(String[] args) {
		ZombieWorld world = new ZombieWorld(new Display());

		FancyGroundFactory groundFactory = new FancyGroundFactory(new Dirt(), new Fence(), new Tree());
		
		List<String> map = Arrays.asList(
		"................................................................................",
		"................................................................................",
		"....................................##########..................................",
		"..........................###########........#####..............................",
		"............++...........##......................########.......................",
		"..............++++.......#..............................##......................",
		".............+++...+++...#...............................#......................",
		".........................##..............................##.....................",
		"..........................#...............................#.....................",
		".........................##...............................##....................",
		".........................#...............................##.....................",
		".........................###..............................##....................",
		"...........................####......................######.....................",
		"..............................#########.........####............................",
		"............+++.......................#.........#...............................",
		".............+++++....................#.........#...............................",
		"...............++........................................+++++..................",
		".............+++....................................++++++++....................",
		"............+++.......................................+++.......................",
		"................................................................................",
		".........................................................................++.....",
		"........................................................................++.++...",
		".........................................................................++++...",
		"..........................................................................++....",
		"................................................................................");
		GameMap gameMap = new GameMap(groundFactory, map);
		world.addGameMap(gameMap);
		
		List<String> townMap = Arrays.asList(
		"................................................................................",
		"........+++++.......................++..........................................",
		"..........++........................##########........####....##########........",
		"...................................##........##.......##...............##.......",
		"...................................##.........#........#................##......",
		"....................++.............##.........#........##................###....",
		"...................................##........##.......###..................###..",
		"...................................#....######..........##................##....",
		"..........................................................##..........#####.....",
		"............................................................###...#####.........",
		"....++###..########..####.......................................................",
		"......#........#........#.......................................................",
		"...............#........#.....+++....++.........##########......................",
		"......#.................#.........+++...........##.......#......................",
		"......#........#........#......+++...+..........##.......#########..............",
		"......###....#####....###.......+++..++.........#................#..............",
		"......#........#........#.........+..............................#..............",
		"......#.................#...........+............................#..............",
		"......#........#................................##################..............",
		"......#........#........#................................++.....++....+++.......",
		"......############....###.........................................++++++........",
		".........++++............................+..........................++++........",
		"........++++++..........................+.........................+.............",
		".........++++.....................................................+.............",
		".........++.....................................................................");
		GameMap town = new GameMap(groundFactory, townMap);
		world.addGameMap(town);
		
		List<String> marieMap = Arrays.asList(".");
		GameMap hiddenMap = new GameMap(groundFactory, marieMap);
		world.addGameMap(hiddenMap);
		
		Actor player = new Player("Player", '@', 100);
//		world.addPlayer(player, gameMap.at(42, 15));
		world.addPlayer(player, gameMap.at(70, 19));
//		world.addPlayer(player, gameMap.at(33, 20));
		
	    // Place some random humans
		String[] humans = {"Carlton", "May", "Vicente", "Andrea", "Wendy",
				"Elina", "Winter", "Clem", "Jacob", "Jaquelyn"};
		
		//Place some Farmers
		String[] farmers = {"George", "Emily", "Jeff"};
		
		// Add some humans in the town
		String[] townHumans = {"Bob", "Negan", "Chris", "Michelle", "John",
				"Ashley", "Cynthia", "Dante", "Cleo", "Xander"};
		
		// Add vehicle in main map
		Vehicle mainVehicle = new Vehicle();
		mainVehicle.addAction(new MoveActorAction(town.at(3, 3), "to the town"));
		gameMap.at(3, 3).setGround(new PlayerGround());
        gameMap.at(3, 3).addItem(mainVehicle);
        
        // Add vehicle in town map
        Vehicle townVehicle = new Vehicle();
        townVehicle.addAction(new MoveActorAction(gameMap.at(3, 3), "to the main field"));
        town.at(3, 3).setGround(new PlayerGround());
        town.at(3, 3).addItem(townVehicle);
		
		int x, y;
		for (String name : humans) {
			do {
				x = (int) Math.floor(Math.random() * 20.0 + 30.0);
				y = (int) Math.floor(Math.random() * 7.0 + 5.0);
			} 
			while (gameMap.at(x, y).containsAnActor());
			gameMap.at(x, y).addActor(new Human(name));
		}
		
		for (String name : farmers) {
			do {
				x = (int) Math.floor(Math.random() * 20.0 + 30.0);
				y = (int) Math.floor(Math.random() * 7.0 + 5.0);
			} 
			while (gameMap.at(x, y).containsAnActor());
			gameMap.at(x, y).addActor(new Farmer(name,'F'));
		}
		
		for (String name : townHumans) {
			do {
				x = (int) Math.floor(Math.random() * 79.0);
				y = (int) Math.floor(Math.random() * 24.0);
			}
			while (town.at(x, y).containsAnActor());
			town.at(x, y).addActor(new Human(name));
		}
		
		// Place weapons around both maps
		gameMap.at(40, 5).addItem(new Plank());
		gameMap.at(72, 21).addItem(new Limb("right leg", 'l', new ZombieMace()));
		gameMap.at(72, 19).addItem(new Shotgun());
		gameMap.at(18, 22).addItem(new Shotgun());
		town.at(37, 3).addItem(new Shotgun());
		gameMap.at(79, 15).addItem(new Sniper());
		town.at(64, 17).addItem(new Sniper());

		
		// Add zombies to the main field
		gameMap.at(42, 16).addActor(new Zombie("Groan"));
		gameMap.at(30, 20).addActor(new Zombie("Raaogh"));
		gameMap.at(30,  19).addActor(new Zombie("Boo"));
		gameMap.at(10,  4).addActor(new Zombie("Uuuurgh"));
		gameMap.at(50, 18).addActor(new Zombie("Mortalis"));
		gameMap.at(1, 10).addActor(new Zombie("Gaaaah"));
		gameMap.at(62, 12).addActor(new Zombie("Aaargh"));
		
		// Add zombies to the town
		town.at(42, 15).addActor(new Zombie("Graowhh"));
		town.at(60, 20).addActor(new Zombie("Ooooghh"));
		town.at(30,  19).addActor(new Zombie("Rooooaah"));
		town.at(70,  22).addActor(new Zombie("Rooooaah"));
		town.at(50, 18).addActor(new Zombie("Despicable"));
		
		// Add Mambo Marie
		ArrayList<GameMap> spawnableMaps = new ArrayList<GameMap>(Arrays.asList(gameMap, town));
		hiddenMap.at(0, 0).addActor(new MamboMarie(spawnableMaps, hiddenMap));
		
		//Add shotgun ammo
		gameMap.at(71, 19).addItem(new ShotgunAmmunition());
		gameMap.at(73, 19).addItem(new ShotgunAmmunition());
		gameMap.at(3, 0).addItem(new ShotgunAmmunition());
		gameMap.at(4, 0).addItem(new ShotgunAmmunition());
		gameMap.at(40, 19).addItem(new ShotgunAmmunition());
		gameMap.at(20, 19).addItem(new ShotgunAmmunition());
		gameMap.at(50, 23).addItem(new ShotgunAmmunition());
		gameMap.at(51, 22).addItem(new ShotgunAmmunition());
		gameMap.at(75, 10).addItem(new ShotgunAmmunition());
		gameMap.at(19, 22).addItem(new ShotgunAmmunition());
		
		town.at(6, 8).addItem(new ShotgunAmmunition());
		town.at(7, 8).addItem(new ShotgunAmmunition());
		town.at(52, 20).addItem(new ShotgunAmmunition());
		town.at(78, 10).addItem(new ShotgunAmmunition());
		town.at(38, 3).addItem(new ShotgunAmmunition());
		
		
		//Add sniper ammo
		gameMap.at(78, 15).addItem(new SniperAmmunition());
		gameMap.at(71, 18).addItem(new SniperAmmunition());
		gameMap.at(39, 15).addItem(new SniperAmmunition());
		gameMap.at(70, 5).addItem(new SniperAmmunition());
		gameMap.at(0, 23).addItem(new SniperAmmunition());
		gameMap.at(1, 23).addItem(new SniperAmmunition());
		
		town.at(35, 10).addItem(new SniperAmmunition());
		town.at(75, 13).addItem(new SniperAmmunition());
		town.at(51, 19).addItem(new SniperAmmunition());
		town.at(63, 17).addItem(new SniperAmmunition());
		
		world.run();
	}
}
