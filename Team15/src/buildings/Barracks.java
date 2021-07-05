package buildings;


import exceptions.BuildingInCoolDownException;
import exceptions.MaxLevelException;
import exceptions.MaxRecruitedException;
import units.Infantry;
import units.Unit;

public class Barracks extends MilitaryBuilding {

	public Barracks() {
		super(2000, 1000,500);
		
	}

	@Override
	public Unit recruit() throws BuildingInCoolDownException, MaxRecruitedException {
		if(isCoolDown())
			throw new BuildingInCoolDownException("Building is in cool down,Wait for the next turn");
		if(getCurrentRecruit()==getMaxRecruit())
			throw new MaxRecruitedException("Cannot Recruit more than,3 units per turn");
		setCurrentRecruit(getCurrentRecruit()+1);
		if(getLevel()==1)
			return new Infantry(1, 50, 0.5, 0.6, 0.7);
		
	else if(getLevel()==2)
		return new Infantry(2,50,0.5,0.6,0.7);
	else
		return new Infantry(3,60,0.6,0.7,0.8);
		
	}

	@Override
	public void upgrade() throws BuildingInCoolDownException, MaxLevelException {
		super.upgrade();
		if(getLevel()==1)
		{
			setLevel(2);
			setUpgradeCost(1500);
			setRecruitmentCost(550);
		}
		else if(getLevel()==2)
		{
		setLevel(3);
		setRecruitmentCost(600);
		}
		
	}


}
