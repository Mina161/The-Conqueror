package exceptions;

@SuppressWarnings("serial")
public abstract class BuildingException extends EmpireException {
	public BuildingException()
	{
		super();
	}
	public BuildingException(String s)
	{
		super(s);
	}

}
