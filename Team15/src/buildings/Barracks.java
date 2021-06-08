package buildings;

import exceptions.BuildingInCoolDownException;
import exceptions.MaxLevelException;
import exceptions.MaxRecruitedException;
import units.Infantry;
import units.Unit;

public class Barracks extends MilitaryBuilding {

	public Barracks() {
		super(2000, 1000, 500);
	}
	
	public void upgrade() throws BuildingInCoolDownException, MaxLevelException{
		super.upgrade();
		
		if(getLevel() == 1) {
			setLevel(2);
			setUpgradeCost(1500);
			setRecruitmentCost(550);
		} else {
			setLevel(3);
			setRecruitmentCost(600);
		}
		
	}
	
	public Unit recruit() throws BuildingInCoolDownException, MaxRecruitedException {
		if(isCoolDown()) throw new BuildingInCoolDownException();
		if(getCurrentRecruit() == getMaxRecruit()) throw new MaxRecruitedException();
		
		setCurrentRecruit(getCurrentRecruit()+1);
		
		if(getLevel()==1) {
			return new Infantry(1,50,0.5,0.6,0.7);
		}
		if(getLevel()==2) {
			return new Infantry(2,50,0.5,0.6,0.7);
		}
		else {
			return new Infantry(3,60,0.6,0.7,0.8);
		}
	}
}
