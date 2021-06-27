package exceptions;

@SuppressWarnings("serial")
public class TargetNotReachedException extends ArmyException {

	public TargetNotReachedException() {
		
	}

	public TargetNotReachedException(String s) {
		super(s);
	}

}
