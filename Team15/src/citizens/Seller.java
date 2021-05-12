package citizens;

public class Seller extends Citizen {
	private final int products = 10;
	
	public Seller(String name, int age) {
		super(name,age);
	}
	
	public int getProducts() {
		return products;
	}
}
