package buildings;

import exceptions.BuildingInCoolDownException;
import exceptions.MaxLevelException;
import exceptions.MaxRecruitedException;
import units.Cavalry;
import units.Unit;

public class Stable extends MilitaryBuilding{

	public Stable() {
		super(2500, 1500, 600);
	}
	
	public void upgrade() throws BuildingInCoolDownException, MaxLevelException{
		if(isCoolDown()) throw new BuildingInCoolDownException();
		if(getLevel() == 3) throw new MaxLevelException();
		
		if(getLevel() == 1) {
			setLevel(2);
			setUpgradeCost(2000);
			setRecruitmentCost(650);
		} else {
			setLevel(3);
			setRecruitmentCost(700);
		}
	}
	
	public Unit recruit() throws BuildingInCoolDownException, MaxRecruitedException {
		if(isCoolDown()) throw new BuildingInCoolDownException();
		if(getCurrentRecruit() == getMaxRecruit()) throw new MaxRecruitedException();
		
		setCurrentRecruit(getCurrentRecruit()+1);
		setCoolDown(true);
		
		if(getLevel()==1) {
			return new Cavalry(1,40,0.6,0.7,0.75);
		}
		if(getLevel()==2) {
			return new Cavalry(2,40,0.6,0.7,0.75);
		}
		else {
			return new Cavalry(3,60,0.7,0.8,0.9);
		}
	}

}
