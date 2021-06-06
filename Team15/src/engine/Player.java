package engine;
import units.Army;
import units.Status;
import units.Unit;

import java.util.ArrayList;

import buildings.ArcheryRange;
import buildings.Barracks;
import buildings.Building;
import buildings.EconomicBuilding;
import buildings.Farm;
import buildings.Market;
import buildings.MilitaryBuilding;
import buildings.Stable;
import exceptions.BuildingInCoolDownException;
import exceptions.FriendlyCityException;
import exceptions.MaxLevelException;
import exceptions.MaxRecruitedException;
import exceptions.NotEnoughGoldException;
import exceptions.TargetNotReachedException;

public class Player {
	private String name;
	private ArrayList<City> controlledCities;
	private ArrayList<Army> controlledArmies;
	private double treasury;
	private double food;
	
	public Player(String name) {
		this.name=name;
		controlledCities = new ArrayList<City>();
		controlledArmies = new ArrayList<Army>();
	}
	
	public void recruitUnit(String type,String cityName) throws BuildingInCoolDownException, MaxRecruitedException, NotEnoughGoldException{
		City currCity = null;
		for(int i = 0; i<controlledCities.size(); i++) {
			currCity = controlledCities.get(i);
			if(currCity.getName().equals(cityName)) break;
		}
		if(currCity == null) return;
		
		ArrayList<MilitaryBuilding> buildings = currCity.getMilitaryBuildings();
		MilitaryBuilding targetBuilding = null;	
		for(int i = 0; i<buildings.size(); i++) {
			switch(type) {
			case "Archer": 
				if(buildings.get(i) instanceof ArcheryRange) targetBuilding = buildings.get(i);break;
			case "Cavalry":
				if(buildings.get(i) instanceof Stable) targetBuilding = buildings.get(i);break;
			case "Infantry":
				if(buildings.get(i) instanceof Barracks) targetBuilding = buildings.get(i);break;
			}
		}
		if(targetBuilding == null) return;
		
		if(targetBuilding.isCoolDown()) throw new BuildingInCoolDownException();
		if(targetBuilding.getCurrentRecruit() == targetBuilding.getMaxRecruit()) throw new MaxRecruitedException();
		if(targetBuilding.getRecruitmentCost() > treasury) throw new NotEnoughGoldException();
		
		
		treasury -= targetBuilding.getRecruitmentCost();
		Unit recruited = targetBuilding.recruit();
		currCity.getDefendingArmy().getUnits().add(recruited);
		recruited.setParentArmy(currCity.getDefendingArmy());
	}
	
	public void build(String type,String cityName) throws NotEnoughGoldException{
		City currCity = null;
		for(int i = 0; i<controlledCities.size(); i++) {
			currCity = controlledCities.get(i);
			if(currCity.getName().equals(cityName)) break;
		}
		if(currCity == null) return;
		
		String parentBuildingType = "";
		Building toBuild = null;
		switch(type) {
		case "ArcheryRange": toBuild = new ArcheryRange() ; parentBuildingType = "Military";break;
		case "Stable": toBuild = new Stable() ; parentBuildingType = "Military";break;
		case "Barracks": toBuild = new Barracks() ; parentBuildingType = "Military";break;
		case "Farm": toBuild = new Farm() ; parentBuildingType = "Economic";break;
		case "Market": toBuild = new Market() ; parentBuildingType = "Economic";break;
		}
		if(toBuild == null) return;
		
		if(toBuild.getCost() > treasury) throw new NotEnoughGoldException();
		boolean flag = true;
		
		switch(parentBuildingType) {
		case "Economic": 
			for(int i = 0; i<currCity.getEconomicalBuildings().size(); i++) {
				EconomicBuilding currBuilding = currCity.getEconomicalBuildings().get(i);
				if(currBuilding.getClass().equals(toBuild.getClass())) {
					flag = false;
					break;
				}
			}
			if(flag) {
				currCity.getEconomicalBuildings().add((EconomicBuilding) toBuild);
				treasury -= toBuild.getCost();
			}
			break;
		case "Military": 
			for(int i = 0; i<currCity.getMilitaryBuildings().size(); i++) {
				MilitaryBuilding currBuilding = currCity.getMilitaryBuildings().get(i);
				if(currBuilding.getClass().equals(toBuild.getClass())) {
					flag = false;
					break;
				}
			}
			if(flag) {
				currCity.getMilitaryBuildings().add((MilitaryBuilding) toBuild);
				treasury -= toBuild.getCost();
			}
			break;
		}
		toBuild.setCoolDown(true);
	}
	
	public void upgradeBuilding(Building b) throws NotEnoughGoldException, BuildingInCoolDownException, MaxLevelException{
		int cost = b.getUpgradeCost();
		if(cost>treasury) throw new NotEnoughGoldException();
		b.upgrade();
		treasury -= cost;
	}
	
	public void initiateArmy(City city,Unit unit) {
		Army newArmy = new Army(city.getName());
		newArmy.getUnits().add(unit);
		unit.setParentArmy(newArmy);
		city.getDefendingArmy().getUnits().remove(unit);
		controlledArmies.add(newArmy);
	}
	
	public void laySiege(Army army,City city) throws TargetNotReachedException, FriendlyCityException{
		if(!army.getCurrentLocation().equals(city.getName()) || army.getDistancetoTarget() > 0) throw new TargetNotReachedException();
		if(controlledCities.contains(city)) throw new FriendlyCityException();
		
		army.setCurrentStatus(Status.BESIEGING);
		city.setUnderSiege(true);
		city.setTurnsUnderSiege(0);
	}
	
	public String getName() {
		return name;
	}
	
	public ArrayList<City> getControlledCities(){
		return controlledCities;
	}
	
	public ArrayList<Army> getControlledArmies(){
		return controlledArmies;
	}
	
	public double getTreasury(){
		return treasury ;
	}
	
	public void setTreasury(double treasury){
		this.treasury=treasury ;
	}
	
	public double getFood(){
		return food ;
	}
	
	public void setFood(double food){
		this.food=food ;
	}
	
}
