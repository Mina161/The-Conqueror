package units;

public abstract class PassiveUnit {
	private int level;
	private double foodConsumption;
	
	public PassiveUnit(int level, double foodConsumption) {
		this.level = level;
		this.foodConsumption = foodConsumption;
	}
	
	public int getLevel() {
		return level;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	public double getFoodConsumption() {
		return foodConsumption;
	}
	
	public void setFoodConsumption(double foodConsumption) {
		this.foodConsumption = foodConsumption;
	}
}
