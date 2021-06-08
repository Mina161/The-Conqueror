package buildings;

import exceptions.BuildingInCoolDownException;
import exceptions.MaxLevelException;

public class Farm extends EconomicBuilding {

	public Farm() {
		super(1000, 500);
	}
	
	public void upgrade() throws BuildingInCoolDownException, MaxLevelException{
		super.upgrade();
		
		if(getLevel() == 1) {
			setLevel(2);
			setUpgradeCost(700);
		} else {
			setLevel(3);
		}
	}
	
	public int harvest() {
		if(getLevel()==1)
			return 500;
		if(getLevel()==2)
			return 700;
		else
			return 1000;
	}

}
