package engine;
import java.util.*;

public class Game {
	private Player player ;
	private ArrayList<City> availableCities;
	private ArrayList<Distance> distances;
	private final int maxTurnCount = 30 ;
	private int currentTurnCount=1; // wait if you want to delete this 1 until we construct the constructor 
	
	// the constructor will be constructed Wednesday second slot 
	
	public Player getPlayer ()
	{
		return player;
	}
	public void setPlayer(Player player)
	{
		this.player=player;
	}
	public ArrayList<City> getAvailableCities()
	{
		return availableCities;
	}
	public ArrayList<Distance> getDistances ()
	{
		return distances;
	}
	public int getMaxTurnCount()
	{
		return maxTurnCount;
	}
	public int getCurrentTurnCount()
	{
		return currentTurnCount;
	}
	public void setCurrentTurnCount(int currentTurnCount)
	{
		this.currentTurnCount = currentTurnCount;
	}

}
