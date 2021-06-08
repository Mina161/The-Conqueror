package buildings;

import exceptions.BuildingInCoolDownException;
import exceptions.MaxLevelException;

public class Market extends EconomicBuilding {

	public Market() {
		super(1500, 700);
	}
	
	public void upgrade() throws BuildingInCoolDownException, MaxLevelException{
		super.upgrade();
		
		if(getLevel() == 1) {
			setLevel(2);
			setUpgradeCost(1000);
		} else {
			setLevel(3);
		}
	}
	
	public int harvest() {
		if(getLevel()==1)
			return 1000;
		if(getLevel()==2)
			return 1500;
		else
			return 2000;
	}

}
