package units;

import exceptions.FriendlyFireException;

public class Infantry extends Unit{

	public Infantry(int level, int maxSoldierCount, double idleUpkeep, double marchingUpkeep, double siegeUpkeep) {
		super(level, maxSoldierCount, idleUpkeep, marchingUpkeep, siegeUpkeep);
	}
	
	public void attack(Unit target) throws FriendlyFireException{
		super.attack(target);
		int targetCount = target.getCurrentSoldierCount();
		int attackerCount = getCurrentSoldierCount();
		double factor = 0.0;
		if(getLevel() == 1) {
			if(target instanceof Archer) 
				factor = 0.3;
			else if (target instanceof Cavalry)
				factor = 0.1;
			else if (target instanceof Infantry)
				factor = 0.1;
		} else if(getLevel() == 2) {
			if(target instanceof Archer) 
				factor = 0.4;
			else if (target instanceof Cavalry)
				factor = 0.2;
			else if (target instanceof Infantry)
				factor = 0.2;
		} else {
			if(target instanceof Archer) 
				factor = 0.5;
			else if (target instanceof Cavalry)
				factor = 0.25;
			else if (target instanceof Infantry)
				factor = 0.3;
		}
		int reduceBy = (int)(factor*attackerCount);
		targetCount = (targetCount-reduceBy)<0 ? 0:(targetCount-reduceBy);
		target.setCurrentSoldierCount(targetCount);
		target.getParentArmy().handleAttackedUnit(target);
	}

}
