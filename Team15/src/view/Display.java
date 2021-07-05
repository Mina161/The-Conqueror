package view;

import java.awt.event.ActionEvent;
import units.*;
import engine.*;
import javax.swing.*;

public class Display extends AbstractAction{
	
	private Army army;
	private Player player;
	private Game game;
	private CityView c;
	
	public Display(Army army, Player player, Game game) {
		this.army = army;
		this.player = player;
		this.game = game;
	}

	public Display(Army currArmy, Player currPlayer, Game currGame, CityView cityView) {
		this.army = currArmy;
		this.player = currPlayer;
		this.game = currGame;
		c = cityView;
	}

	public void actionPerformed(ActionEvent e) {
		ArmyDisplay disp = new ArmyDisplay(army, player, game, c);
	}

}
