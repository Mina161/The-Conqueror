package engine;
import units.Army;
import java.util.ArrayList;

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
	
	public void setStartCity(String cityName) {
		controlledCities.add(new City(cityName));
	}
	
}
