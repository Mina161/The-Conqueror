package engine;

import java.util.ArrayList;

import buildings.ArcheryRange;
import buildings.Barracks;
import buildings.Building;
import buildings.EconomicBuilding;
import buildings.Farm;
import buildings.Market;
import buildings.MilitaryBuilding;
import buildings.Stable;
import exceptions.BuildingInCoolDownException;
import exceptions.FriendlyCityException;
import exceptions.MaxLevelException;
import exceptions.MaxRecruitedException;
import exceptions.NotEnoughGoldException;
import exceptions.TargetNotReachedException;
import units.Army;
import units.Status;
import units.Unit;

public class Player {
	private String name;
	private ArrayList<City> controlledCities;
	private ArrayList<Army> controlledArmies;
	private double treasury;
	private double food;

	public Player(String name) {
		this.name = name;
		this.controlledCities = new ArrayList<City>();
		this.controlledArmies = new ArrayList<Army>();
	}

	public void recruitUnit(String type, String cityName)
			throws BuildingInCoolDownException, MaxRecruitedException, NotEnoughGoldException {
		for (City c : controlledCities) {
			if (c.getName().equals(cityName)) {
				for (MilitaryBuilding b : c.getMilitaryBuildings()) {
					if ((type.toLowerCase().equals("archer") && b instanceof ArcheryRange)
							|| (type.toLowerCase().equals("cavalry") && b instanceof Stable)
							|| (type.toLowerCase().equals("infantry") && b instanceof Barracks)) {

						Unit u = b.recruit();
						if (treasury < b.getRecruitmentCost())
							throw new NotEnoughGoldException("Not enough gold");
						treasury -= b.getRecruitmentCost();
						u.setParentArmy(c.getDefendingArmy());
						c.getDefendingArmy().getUnits().add(u);
					}
				}
			}
		}

	}

	public void build(String type, String cityName) throws NotEnoughGoldException {
		for (City c : controlledCities) {
			if (c.getName().equals(cityName)) {
				Building b = null;
				switch (type.toLowerCase()) {
				case "archeryrange":
					b = new ArcheryRange();
					break;
				case "barracks":
					b = new Barracks();
					break;
				case "stable":
					b = new Stable();
					break;
				case "farm":
					b = new Farm();
					break;
				case "market":
					b = new Market();
				}
				if (type.equals("Farm") || type.equals("Market")) {
					for (EconomicBuilding e : c.getEconomicalBuildings()) {
						if (e instanceof Farm && type.equals("Farm") || e instanceof Market && type.equals("Market"))
							return;
					}
				} else {
					{
						for (MilitaryBuilding e : c.getMilitaryBuildings()) {
							if (e instanceof ArcheryRange && type.equals("ArcheryRange")
									|| e instanceof Barracks && type.equals("Barracks")
									|| e instanceof Stable && type.equals("Stable"))
								return;
						}
					}
				}
				if (treasury < b.getCost())
					throw new NotEnoughGoldException("Not enough gold");
				treasury -= b.getCost();
				if (type.toLowerCase().equals("farm") || type.toLowerCase().equals("market"))
					c.getEconomicalBuildings().add((EconomicBuilding) b);
				else {
					c.getMilitaryBuildings().add((MilitaryBuilding) b);
				}

			}
		}
	}

	public void upgradeBuilding(Building b)
			throws NotEnoughGoldException, BuildingInCoolDownException, MaxLevelException {
		if (treasury < b.getUpgradeCost())
			throw new NotEnoughGoldException("Not enough gold");
		int originalCost = b.getUpgradeCost();
		b.upgrade();
		treasury -= originalCost;
	}

	public void initiateArmy(City city, Unit unit) {
		Army army = new Army(city.getName());
		army.getUnits().add(unit);
		city.getDefendingArmy().getUnits().remove(unit);
		unit.setParentArmy(army);
		controlledArmies.add(army);
	}

	public void laySiege(Army army, City city) throws TargetNotReachedException, FriendlyCityException {
		if (controlledCities.contains(city))
			throw new FriendlyCityException("You can't attack a friendly city");
		if (!army.getCurrentLocation().equals(city.getName()))
			throw new TargetNotReachedException("Target not reached");
		army.setCurrentStatus(Status.BESIEGING);
		city.setUnderSiege(true);
		city.setTurnsUnderSiege(0);
	}

	public double getTreasury() {
		return treasury;
	}

	public void setTreasury(double treasury) {
		this.treasury = treasury;
	}

	public double getFood() {
		return food;
	}

	public void setFood(double food) {
		this.food = food;
	}

	public String getName() {
		return name;
	}

	public ArrayList<City> getControlledCities() {
		return controlledCities;
	}

	public ArrayList<Army> getControlledArmies() {
		return controlledArmies;
	}

}
