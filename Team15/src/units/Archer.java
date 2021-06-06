package units;
import exceptions.FriendlyFireException;

public class Archer extends Unit {

	public Archer(int level, int maxSoldierCount, double idleUpkeep, double marchingUpkeep, double siegeUpkeep) {
		super(level, maxSoldierCount, idleUpkeep, marchingUpkeep, siegeUpkeep);
	}
	
	public void attack(Unit target) throws FriendlyFireException{
		super.attack(target);
		double factor = 0.0;
		if(getLevel() == 1 && target instanceof Archer)
			factor = 0.3;
		
		if(getLevel() == 2 && target instanceof Archer)
			factor = 0.4;
		
		if(getLevel() == 3 && target instanceof Archer)
			factor = 0.5;
		
		if(getLevel() == 1 && target instanceof Cavalry)
			factor = 0.1;
		
		if(getLevel() == 2 && target instanceof Cavalry)
			factor = 0.1;
		
		if(getLevel() == 3 && target instanceof Cavalry)
			factor = 0.2;
		
		if(getLevel() == 1 && target instanceof Infantry)
			factor = 0.2;
		
		if(getLevel() == 2 && target instanceof Infantry)
			factor = 0.3;
		
		if(getLevel() == 3 && target instanceof Infantry)
			factor = 0.4;
		
		if((int) (target.getCurrentSoldierCount()-getCurrentSoldierCount()*factor)>0)
			target.setCurrentSoldierCount((int) (target.getCurrentSoldierCount()-getCurrentSoldierCount()*factor));
		else
			target.setCurrentSoldierCount(0);
		
		target.getParentArmy().handleAttackedUnit(target);
	}


}
