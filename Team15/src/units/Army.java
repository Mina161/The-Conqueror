package units;
import java.util.ArrayList;
import exceptions.MaxCapacityException;

public class Army {
	private Status currentStatus = Status.IDLE;
	private ArrayList<Unit> units;
	private int distancetoTarget = -1;
	private String target = "";
	private String currentLocation;
	final private int maxToHold = 10; //READ ONLY
	
	public Army(String currentLocation) {
		this.currentLocation = currentLocation;
		units = new ArrayList<Unit>();
	}
	
	public void relocateUnit(Unit unit) throws MaxCapacityException
	{
		    if(units.size()==maxToHold) throw new MaxCapacityException();
		    else
		    {
			   units.add(unit);
			   unit.getParentArmy().getUnits().remove(unit);
			   unit.setParentArmy(this);
		    }
			  
	}
	
	public void handleAttackedUnit(Unit u) {
		if (u.getCurrentSoldierCount()==0)
			units.remove(u);
	}
	
	public double foodNeeded() {    // food needed for each unit in the army 
		double r = 0.0 ;
		double c = 0.0 ;
		for(int i =0 ; i<units.size() ; i++)
		{
			Unit currUnit = units.get(i);
			if(currentStatus==Status.IDLE)
			{
				c=currUnit.getIdleUpkeep()*currUnit.getCurrentSoldierCount();
			}
			else if(currentStatus==Status.BESIEGING)
			{
				c=currUnit.getSiegeUpkeep()*currUnit.getCurrentSoldierCount();
			}
			else if(currentStatus==Status.MARCHING)
			{
				c=currUnit.getMarchingUpkeep()*currUnit.getCurrentSoldierCount();
			}
			r+=c;
		}
		return r;
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
	
	
}