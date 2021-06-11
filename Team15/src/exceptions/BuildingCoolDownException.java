package exceptions;

@SuppressWarnings("serial")
public class BuildingCoolDownException extends BuildingException {
	public BuildingCoolDownException() {
		
	}
	public BuildingCoolDownException(String s) {
		super(s);
	}
}
