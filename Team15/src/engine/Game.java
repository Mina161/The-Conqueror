package engine;

import java.util.*;
import buildings.*;
import exceptions.FriendlyFireException;
import java.io.*;
import units.*;

public class Game {
	private Player player ;
	private ArrayList<City> availableCities;
	private ArrayList<Distance> distances;
	private final int maxTurnCount = 30 ;
	private int currentTurnCount = 1;
	
	public Game(String playerName,String playerCity) throws IOException{
		player = new Player(playerName);
		availableCities = new ArrayList<City>();
		distances = new ArrayList<Distance>();
		loadCitiesAndDistances();
		for(int i = 0; i<availableCities.size(); i++) {
			City curCity = availableCities.get(i);
			if(!curCity.getName().equals(playerCity))
				loadArmy(curCity.getName(),curCity.getName().toLowerCase()+"_army.csv");
			else {
				player.getControlledCities().add(curCity);
			}
		}
	}
	
	public Player getPlayer ()
	{
		return player;
	}
	public void setPlayer(Player player)
	{
		this.player=player;
	}
	public ArrayList<City> getAvailableCities()
	{
		return availableCities;
	}
	public ArrayList<Distance> getDistances ()
	{
		return distances;
	}
	public int getMaxTurnCount()
	{
		return maxTurnCount;
	}
	public int getCurrentTurnCount()
	{
		return currentTurnCount;
	}
	public void setCurrentTurnCount(int currentTurnCount)
	{
		this.currentTurnCount = currentTurnCount;
	}
	
	/*
	 * readFile method taken from the document provided with additional edits done by us to help in our implementation
	 */
	public static String[] readFile(String path) throws IOException{
		String currentLine = "";
		String allLines = "";
		FileReader fileReader= new FileReader(path);
		BufferedReader br = new BufferedReader(fileReader);
		while ((currentLine = br.readLine()) != null) {
			allLines = allLines +","+ currentLine;
		}
		br.close();
		return allLines.split(",");
	}
	
	public void loadArmy(String cityName,String path) throws IOException{
		String[] unitsCsv = readFile(path);
		Army defendingArmy = new Army(cityName);
		ArrayList<Unit> units = new ArrayList<Unit>();
		for(int i = 1; i<unitsCsv.length; i+=2) {
			String unitType = unitsCsv[i];
			int level = Integer.parseInt(unitsCsv[i+1]);
			switch(unitType) {
			case "Archer": 
				if(level==1 || level==2) units.add(new Archer(level,60,0.4,0.5,0.6));
				else units.add(new Archer(level,70,0.5,0.6,0.7));break;
			case "Infantry":
				if(level==1 || level==2) units.add(new Infantry(level,50,0.5,0.6,0.7));
				else units.add(new Infantry(level,60,0.6,0.7,0.8));break;
			case "Cavalry":
				if(level==1 || level==2) units.add(new Cavalry(level,40,0.6,0.7,0.75));
				else units.add(new Cavalry(level,60,0.7,0.8,0.9));break;
			}
		}
		defendingArmy.setUnits(units);
		units.forEach((u) -> u.setParentArmy(defendingArmy)); //forEach() implementation and examples found on GeeksForGeeks
		
		for(int i = 0; i<availableCities.size(); i++) {
			if(availableCities.get(i).getName().equals(cityName)) 
				availableCities.get(i).setDefendingArmy(defendingArmy);
		}
	}
	
	private void loadCitiesAndDistances() throws IOException {
		String[] dist = readFile("distances.csv");
		ArrayList<String> unique = new ArrayList<String>();
		for(int i = 1; i<dist.length; i+=3) {
			String from = dist[i];
			String to = dist[i+1];
			int distance = Integer.parseInt(dist[i+2]);
			distances.add(new Distance(from,to,distance));
			if(!unique.contains(from)) {
				unique.add(from);
				availableCities.add(new City(from));
			}
			if(!unique.contains(to)) {
				unique.add(to);
				availableCities.add(new City(to));
			}
		}
	}
	
	public void targetCity(Army army, String targetName) {
		if(army.getCurrentStatus() == Status.MARCHING || army.getCurrentLocation().equals("onRoad") || !army.getTarget().equals("")) return;
		
		int distance = 0;
		for(int i = 0; i<distances.size(); i++) {
			Distance currDistance = distances.get(i);
			if((currDistance.getFrom().equals(army.getCurrentLocation()) && currDistance.getTo().equals(targetName))||
					(currDistance.getFrom().equals(targetName) && currDistance.getTo().equals(army.getCurrentLocation()))) {
				distance = currDistance.getDistance();
				break;
			}
		}
		army.setCurrentLocation("onRoad");
		army.setCurrentStatus(Status.MARCHING);
		army.setDistancetoTarget(distance);
		army.setTarget(targetName);
	}
	
	public void endTurn() {
		currentTurnCount++;
		 
		//Loop on all Controlled Cities
		for(int i = 0; i<player.getControlledCities().size(); i++) {
			City currCity = player.getControlledCities().get(i);
			
			//Loop on Economy
			for(int j = 0; j<currCity.getEconomicalBuildings().size(); j++) {
				EconomicBuilding currEco = currCity.getEconomicalBuildings().get(j);
				if(currEco instanceof Farm) {
					player.setFood(player.getFood()+currEco.harvest());
				} else if(currEco instanceof Market) {
					player.setTreasury(player.getTreasury()+currEco.harvest());
				}
				currEco.setCoolDown(false);
			}
			
			//Loop on Military
			for(int j = 0; j<currCity.getMilitaryBuildings().size(); j++) {
				MilitaryBuilding currMil = currCity.getMilitaryBuildings().get(j);
				currMil.setCoolDown(false);
				currMil.setCurrentRecruit(0);
			}
		}
		
		//Loop on all Armies
		double foodNeeded = 0.0;
		for(int i = 0; i<player.getControlledArmies().size(); i++) {
			Army currArmy = player.getControlledArmies().get(i);
			foodNeeded += currArmy.foodNeeded();
			if(!currArmy.getTarget().equals("") && currArmy.getCurrentStatus() == Status.MARCHING && currArmy.getDistancetoTarget() > 0) {
				currArmy.setDistancetoTarget(currArmy.getDistancetoTarget()-1);
				if(currArmy.getDistancetoTarget() == 0) {
					currArmy.setCurrentLocation(currArmy.getTarget());
					currArmy.setTarget("");
					currArmy.setCurrentStatus(Status.IDLE);
				}
			}
		}
		
		int newFood = (player.getFood()-foodNeeded)<0 ? 0:(int)(player.getFood()-foodNeeded);
		player.setFood(newFood);
		
		//Loop on Units of each Army when starving
		if(newFood == 0) {
			for(int i = 0; i<player.getControlledArmies().size(); i++) {
				Army currArmy = player.getControlledArmies().get(i);
				currArmy.getUnits().forEach( (u) -> u.setCurrentSoldierCount( (int) (u.getCurrentSoldierCount()*0.9) ) );
			}
		}
		
		//Loop on all Available Cities to count turns under siege and decrement
		for(int i = 0; i<availableCities.size(); i++) {
			City currCity = availableCities.get(i);
			if(currCity.isUnderSiege()) currCity.setTurnsUnderSiege(currCity.getTurnsUnderSiege()+1);
			Army currArmy = currCity.getDefendingArmy();
			currArmy.getUnits().forEach( (u) -> u.setCurrentSoldierCount( (int) (u.getCurrentSoldierCount()*0.9) ) );
		}
		
	}
	
	public void occupy(Army a,String cityName) {
		City occupied = null;
		for(int i = 0; i<availableCities.size(); i++) {
			if(availableCities.get(i).getName().equals(cityName)) {
				occupied = availableCities.get(i);
				break;
			}
		}
		
		player.getControlledCities().add(occupied);
		occupied.setDefendingArmy(a);
		occupied.setTurnsUnderSiege(-1);
		occupied.setUnderSiege(false);
	}
	
	public void autoResolve(Army attacker, Army defender) throws FriendlyFireException{
		if(player.getControlledArmies().contains(defender) && player.getControlledArmies().contains(attacker)) throw new FriendlyFireException();
		
		for(int attack = 0; ; attack++) {
			Random rand = new Random();
			Unit randomAttacker = attacker.getUnits().get(rand.nextInt(attacker.getUnits().size()));
			Unit randomDefender = defender.getUnits().get(rand.nextInt(defender.getUnits().size())); // get 2 random units each loop
			
			if(attack%2 == 0) { //Even and odd to alternate attacker and defender
				randomAttacker.attack(randomDefender);
			} else {
				randomDefender.attack(randomAttacker);
			}
			
			if(attacker.getUnits().size() == 0) {
				player.getControlledArmies().remove(attacker);
				break;
			}
			else if(defender.getUnits().size() == 0) {
				occupy(attacker, defender.getCurrentLocation());
				break;
			}
		}
	}
	
	public boolean isGameOver() {
		return (player.getControlledCities().containsAll(availableCities)) || (currentTurnCount > maxTurnCount);
	}
}
