package units;

import exceptions.FriendlyFireException;

public class Infantry extends Unit {

	public Infantry(int level, int maxSoldierConunt, double idleUpkeep, double marchingUpkeep, double siegeUpkeep) {
		super(level, maxSoldierConunt, idleUpkeep, marchingUpkeep, siegeUpkeep);
	}

	@Override
	public void attack(Unit target) throws FriendlyFireException {
		super.attack(target);
		
		int damage = getDamage(target);
		target.setCurrentSoldierCount(target.getCurrentSoldierCount()-damage);
		
		target.getParentArmy().handleAttackedUnit(target);
	}
	
	public int getDamage(Unit target) {
		double factor=0;
		if(target instanceof Archer)
		{
			if(getLevel()==1)
				factor=0.3;
			else if(getLevel()==2)
				factor=0.4;
			else 
				factor=0.5;
		}
		else if(target instanceof Infantry)
		{
			if(getLevel()==1)
				factor=0.1;
			else if(getLevel()==2)
				factor=0.2;
			else
				factor=0.3;
		}
		else if(target instanceof Cavalry)
		{
			if(getLevel()==1 )
				factor=0.1;
			else if(getLevel()==2)
				factor=0.2;
			else
				factor=0.25;
		}
		
		return (int)(factor*getCurrentSoldierCount());
	}
	
	public String toString() {
		return "Infantry Level "+getLevel();
	}

}
