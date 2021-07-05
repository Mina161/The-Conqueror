package buildings;

import exceptions.BuildingInCoolDownException;
import exceptions.MaxLevelException;
import exceptions.MaxRecruitedException;
import units.Cavalry;
import units.Unit;

public class Stable extends MilitaryBuilding {

	public Stable() {
		super(2500, 1500, 600);

	}

	@Override
	public Unit recruit() throws BuildingInCoolDownException, MaxRecruitedException {
		if(isCoolDown())
			throw new BuildingInCoolDownException("Building is in cool down,Wait for the next turn");
		if(getCurrentRecruit()==getMaxRecruit())
			throw new MaxRecruitedException("Cannot Recruit more than,3 units per turn");
		setCurrentRecruit(getCurrentRecruit() + 1);
		if (getLevel() == 1)
			return new Cavalry(1, 40, 0.6, 0.7, 0.75);

		else if (getLevel() == 2)
			return new Cavalry(2, 40, 0.6, 0.7, 0.75);
		else
			return new Cavalry(3, 60, 0.7, 0.8, 0.9);

	}

	@Override
	public void upgrade() throws BuildingInCoolDownException, MaxLevelException {
		super.upgrade();
		if (getLevel() == 1) {
			setLevel(2);
			setUpgradeCost(2000);
			setRecruitmentCost(650);
		} else if (getLevel() == 2) {
			setLevel(3);
			setRecruitmentCost(700);
		}

	}

}
