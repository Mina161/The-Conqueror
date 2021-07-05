package view;

import java.awt.event.ActionEvent;
import javax.swing.*;

import engine.City;
import engine.Player;
import units.Unit;

public class Initiate extends AbstractAction {
	
	private Player player;
	private City city;
	private Unit unit;
	
	public Initiate(Player currPlayer, City currCity, Unit currUnit) {
		player = currPlayer;
		city = currCity;
		unit = currUnit;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		player.initiateArmy(city, unit);
	}

}
