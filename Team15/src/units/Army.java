package units;
import java.util.ArrayList;

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