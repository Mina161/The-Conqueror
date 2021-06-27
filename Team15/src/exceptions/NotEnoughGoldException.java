package exceptions;

@SuppressWarnings("serial")
public class NotEnoughGoldException extends BuildingException {

	public NotEnoughGoldException() {
		
	}

	public NotEnoughGoldException(String s) {
		super(s);
		
	}

}
