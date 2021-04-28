package engine;

import java.util.*;
import java.io.*;
import units.*;

public class Game {
	private Player player ;
	private ArrayList<City> availableCities;
	private ArrayList<Distance> distances;
	private final int maxTurnCount = 30 ;
	private int currentTurnCount = 1; // wait if you want to delete this 1 until we construct the constructor 
	
	public Game(String playerName,String playerCity) throws IOException{
		player = new Player(playerName);
		player.setStartCity(playerCity);
		availableCities = new ArrayList<City>();
		distances = new ArrayList<Distance>();
		loadCitiesAndDistances();
		for(int i = 0; i<availableCities.size(); i++) {
			City curCity = availableCities.get(i);
			if(!curCity.getName().equals(playerCity))
				loadArmy(curCity.getName(),curCity.getName()+".csv");
		}
	}
	
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
	
	public static String[] readFile(String path) throws IOException{
		String currentLine = "";
		String allLines = "";
		FileReader fileReader= new FileReader(path);
		BufferedReader br = new BufferedReader(fileReader);
		while ((currentLine = br.readLine()) != null) {
			allLines = allLines +","+ currentLine;
		}
		br.close();
		return allLines.split(",");
	}
	
	public void loadArmy(String cityName,String path) throws IOException{
		String[] unitsCsv = readFile(path);
		Army defendingArmy = new Army(cityName);
		ArrayList<Unit> units = new ArrayList<Unit>();
		for(int i = 1; i<unitsCsv.length; i+=2) {
			String unitName = unitsCsv[i];
			int level = Integer.parseInt(unitsCsv[i+1]);
			switch(unitName) {
			case "Archer": 
				if(level==1 || level==2) units.add(new Archer(level,60,0.4,0.5,0.6));
				else units.add(new Archer(level,70,0.5,0.6,0.7));break;
			case "Infantry":
				if(level==1 || level==2) units.add(new Infantry(level,50,0.5,0.6,0.7));
				else units.add(new Infantry(level,60,0.6,0.7,0.8));break;
			case "Cavalry":
				if(level==1 || level==2) units.add(new Cavalry(level,40,0.6,0.7,0.75));
				else units.add(new Cavalry(level,60,0.7,0.8,0.9));break;
			}
		}
		defendingArmy.setUnits(units);
		for(int i = 0; i<availableCities.size(); i++) {
			if(availableCities.get(i).getName().equals(cityName))
				availableCities.get(i).setDefendingArmy(defendingArmy);
		}
	}
	
	private void loadCitiesAndDistances() throws IOException {
		String[] dist = readFile("distances.csv");
		ArrayList<String> unique = new ArrayList<String>();
		for(int i = 1; i<dist.length; i+=3) {
			String from = dist[i];
			String to = dist[i+1];
			int distance = Integer.parseInt(dist[i+2]);
			distances.add(new Distance(from,to,distance));
			if(!unique.contains(from)) {
				unique.add(from);
				availableCities.add(new City(from));
			}
			if(!unique.contains(to)) {
				unique.add(to);
				availableCities.add(new City(to));
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		String[] r = readFile("distances.csv");
		for(int i = 0; i<r.length; i++) {
			System.out.print(r[i]+" , ");
		}
	}

}
