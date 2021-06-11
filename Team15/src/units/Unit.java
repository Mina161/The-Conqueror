package units;
import exceptions.FriendlyFireException;

public abstract class Unit {
	private int level; //READ ONLY
	private int maxSoldierCount; //READ ONLY
	private int currentSoldierCount;
	private double idleUpkeep; //READ ONLY
	private double marchingUpkeep; //READ ONLY
	private double siegeUpkeep; //READ ONLY
	private Army parentArmy;
	
	public Unit(int level, int maxSoldierCount, double idleUpkeep, double marchingUpkeep, double siegeUpkeep) {
		this.level = level;
		this.maxSoldierCount = maxSoldierCount;
		this.idleUpkeep = idleUpkeep;
		this.marchingUpkeep = marchingUpkeep;
		this.siegeUpkeep = siegeUpkeep;
		currentSoldierCount = maxSoldierCount;
	}
	
	public int getLevel() {
		return level;
	}
	
	public int getMaxSoldierCount() {
		return maxSoldierCount;
	}
	
	public int getCurrentSoldierCount() {
		return currentSoldierCount;
	}
	
	public void setCurrentSoldierCount(int currentSoldierCount) {
		this.currentSoldierCount = currentSoldierCount;
	}

	public double getIdleUpkeep() {
		return idleUpkeep;
	}

	public double getMarchingUpkeep() {
		return marchingUpkeep;
	}

	public double getSiegeUpkeep() {
		return siegeUpkeep;
	}
	
	
	public Army getParentArmy() {
		return parentArmy;
	}

	public void setParentArmy(Army parentArmy) {
		this.parentArmy = parentArmy;
	}

	public void attack(Unit target) throws FriendlyFireException{
		if(parentArmy.equals(target.parentArmy)) throw new FriendlyFireException();
	}
}