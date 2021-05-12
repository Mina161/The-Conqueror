package units;

import java.util.ArrayList;

public class Assassin extends Unit {
	private ArrayList <String> cities;
	private ArrayList <Army> armies;
	
	public Assassin(int level, int maxSoldierCount, double idleUpkeep, double marchingUpkeep) {
		super(level, maxSoldierCount, idleUpkeep, marchingUpkeep, -1);
		cities = new ArrayList<String>();
		armies = new ArrayList<Army>();
	}
	
	public ArrayList<String> getCities() {
		return cities;
	}
	
	public void setCities(ArrayList<String> cities) {
		this.cities = cities;
	}
	
	public ArrayList<Army> getArmies() {
		return armies;
	}
	
	public void setArmies(ArrayList<Army> armies) {
		this.armies = armies;
	}

}
