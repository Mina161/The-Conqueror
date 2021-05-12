package units;

public class Farmer extends PassiveUnit {
	private double foodProduction;
	private int cooldown;
		
	public Farmer(int level, double foodConsumption, double foodProduction, int cooldown) {
		super(level, foodConsumption);
		this.foodProduction = foodProduction;
		this.cooldown = cooldown;
	}

	public double getFoodProduction() {
		return foodProduction;
	}

	public void setFoodProduction(double foodProduction) {
		this.foodProduction = foodProduction;
	}

	public int getCooldown() {
		return cooldown;
	}

	public void setCooldown(int cooldown) {
		this.cooldown = cooldown;
	}
}
