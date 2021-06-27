package units;

import exceptions.FriendlyFireException;

public abstract class Unit {
	private int level;
	private int maxSoldierCount;
	private int currentSoldierCount;
	private double idleUpkeep;
	private double marchingUpkeep;
	private double siegeUpkeep;
	private Army parentArmy;

	public Unit(int level,int maxSoldierConunt,double idleUpkeep, double marchingUpkeep,double siegeUpkeep) {
		this.level=level;
		this.maxSoldierCount=maxSoldierConunt;
		this.currentSoldierCount=maxSoldierConunt;
		this.idleUpkeep=idleUpkeep;
		this.marchingUpkeep=marchingUpkeep;
		this.siegeUpkeep=siegeUpkeep;
		
	}
	public  void attack(Unit target) throws FriendlyFireException {
		if(getParentArmy()==target.getParentArmy())
			throw new FriendlyFireException("Cannot attack a friendly unit");
	}
	public int getCurrentSoldierCount() {
		return currentSoldierCount;
	}
	public void setCurrentSoldierCount(int currentSoldierCount) {
		this.currentSoldierCount = currentSoldierCount;
		if(this.currentSoldierCount <= 0)
			this.currentSoldierCount =  0;
	}
	public int getLevel() {
		return level;
	}
	public int getMaxSoldierCount() {
		return maxSoldierCount;
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
	public void setParentArmy(Army army) {
		this.parentArmy=army;
		
	}
	public abstract int getDamage(Unit target);
}
