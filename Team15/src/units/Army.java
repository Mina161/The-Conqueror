package units;

import java.util.ArrayList;

import exceptions.MaxCapacityException;


public class Army{
	private Status currentStatus;
	private ArrayList<Unit> units;
	private int distancetoTarget;
	private String target;
	private String currentLocation;
	private final int maxToHold=10;

	public Army(String currentLocation) {
		this.currentLocation=currentLocation;
		currentStatus=Status.IDLE;
		units=new ArrayList<Unit>();
		distancetoTarget=-1;
		target="";
		
	}
	public void relocateUnit(Unit unit) throws MaxCapacityException
	{
		if(units.size()==maxToHold)
			throw new MaxCapacityException("Maximum capacity reached");
		units.add(unit);
		unit.getParentArmy().units.remove(unit);
		unit.setParentArmy(this);
	}
	
	

	public Status getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(Status currentStatus) {
		this.currentStatus = currentStatus;
	}

	public ArrayList<Unit> getUnits() {
		return units;
	}

	public void setUnits(ArrayList<Unit> units) {
		this.units = units;
	}

	public int getDistancetoTarget() {
		return distancetoTarget;
	}

	public void setDistancetoTarget(int distancetoTarget) {
		this.distancetoTarget = distancetoTarget;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getCurrentLocation() {
		return currentLocation;
	}
	public void setCurrentLocation(String currentLocation) {
		this.currentLocation = currentLocation;
	}
	
	public int getMaxToHold() {
		return maxToHold;
	}
	public double foodNeeded()
	{
		double sum=0;
		for(Unit u: units)
		{
			if(currentStatus==Status.IDLE)
				sum+=(u.getIdleUpkeep()*u.getCurrentSoldierCount());
			else if(currentStatus==Status.MARCHING)
				sum+=(u.getMarchingUpkeep()*u.getCurrentSoldierCount());
			else 
				sum+=(u.getSiegeUpkeep()*u.getCurrentSoldierCount());
			
		}
		return sum;
		
	}
	public void handleAttackedUnit(Unit u) {
		if(u.getCurrentSoldierCount()<=0)
		{
			u.setCurrentSoldierCount(0);
		units.remove(u);
		}
		
	}
	

}
