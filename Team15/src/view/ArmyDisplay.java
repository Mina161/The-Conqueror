package view;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;
import engine.*;
import exceptions.FriendlyCityException;
import exceptions.FriendlyFireException;
import exceptions.TargetNotReachedException;
import units.*;

public class ArmyDisplay extends JFrame implements ActionListener, Views {
	
	private Army currArmy;
	private Player currPlayer;
	private Game currGame;
	private CityView c;
	
	public ArmyDisplay(Army army, Player player, Game game) {
		super();
		currArmy = army;
		currPlayer = player;
		currGame = game;
		setSize(900,500);
		setLocationRelativeTo(null);
		setResizable(false);
		setUndecorated(true);
		setVisible(true);
		requestFocus();
		setLayout(new BorderLayout());
		ImageIcon img = new ImageIcon("Assets//Logo.jpg");
		setIconImage(img.getImage());
		try {
			BackgroundPanel bg = null;
			if(currArmy.getCurrentStatus() == Status.BESIEGING) {
				bg = new BackgroundPanel("Assets//SiegeBg.jpg");
			} else {
				bg = new BackgroundPanel("Assets//UnitsBg.jpg");
			}
			setContentPane(bg);
		} catch (IOException e) {
			
		}
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		initialize();
		addWindowFocusListener(this);
		this.validate();
	}
	
	public ArmyDisplay(Army army, Player player, Game game, CityView c) {
		super();
		currArmy = army;
		currPlayer = player;
		currGame = game;
		this.c = c;
		setSize(900,500);
		setLocationRelativeTo(null);
		setResizable(false);
		setUndecorated(true);
		setVisible(true);
		requestFocus();
		setLayout(new BorderLayout());
		ImageIcon img = new ImageIcon("Assets//Logo.jpg");
		setIconImage(img.getImage());
		try {
			BackgroundPanel bg = null;
			if(currArmy.getCurrentStatus() == Status.BESIEGING) {
				bg = new BackgroundPanel("Assets//SiegeBg.jpg");
			} else {
				bg = new BackgroundPanel("Assets//UnitsBg.jpg");
			}
			setContentPane(bg);
		} catch (IOException e) {
			
		}
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		initialize();
		addWindowFocusListener(this);
		this.validate();
	}

	public void initialize() {
		JPanel unitsPanel = new JPanel(new BorderLayout());
		JPanel titlePanel = new JPanel(new FlowLayout());
		GameLabel title = new GameLabel("Units in Army");
		title.resize(24f);
		title.setOpaque(true);
		title.setBackground(new Color(0f,0f,0f,0.5f));
		title.setForeground(Color.WHITE);
		titlePanel.setOpaque(false);
		titlePanel.add(title);
		unitsPanel.add(titlePanel,BorderLayout.NORTH);
		
		JPanel units = new JPanel(new GridLayout(2,5,5,5));
		for(int i = 0; i<currArmy.getUnits().size(); i++) {
			Unit currUnit = currArmy.getUnits().get(i);
			JTextArea temp = new JTextArea(getType(currUnit)+" level "+currUnit.getLevel()+"\nCurrent Soldiers: "+currUnit.getCurrentSoldierCount()+" of "+currUnit.getMaxSoldierCount());
			temp.setEditable(false);
			temp.setBackground(Color.LIGHT_GRAY);
			units.add(temp);
		}
		units.setOpaque(false);
		unitsPanel.add(units,BorderLayout.CENTER);
		
		JPanel buttonPanel = new JPanel(new FlowLayout());
		ClickButton relocateArmy = new ClickButton("Relocate units to this army");
		relocateArmy.addActionListener(this);
		buttonPanel.add(relocateArmy);
		buttonPanel.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()/4));
		
		if(currArmy.getCurrentStatus() == Status.IDLE) {
			if(!isAttacking(currArmy.getCurrentLocation())) {
				ClickButton targetCity = new ClickButton("Select a city to target");
				targetCity.addActionListener(this);
				buttonPanel.add(targetCity);
			} else {
				ClickButton besiegeCity = new ClickButton("Besiege "+currArmy.getCurrentLocation());
				besiegeCity.addActionListener(this);
				besiegeCity.setActionCommand("Besiege");
				besiegeCity.setToolTipText("Starve them, only for 3 turns");
				buttonPanel.add(besiegeCity);
				ClickButton attackCity = new ClickButton("Attack "+currArmy.getCurrentLocation());
				attackCity.addActionListener(this);
				attackCity.setActionCommand("Attack");
				attackCity.setToolTipText("Go to battle yourself");
				buttonPanel.add(attackCity);
				ClickButton autoResolve = new ClickButton("Attack "+currArmy.getCurrentLocation()+" (Auto reslove)");
				autoResolve.addActionListener(this);
				autoResolve.setActionCommand("Auto");
				autoResolve.setToolTipText("Let your generals do all the action");
				buttonPanel.add(autoResolve);
			}
		} else if(currArmy.getCurrentStatus() == Status.BESIEGING) {
			ClickButton attackCity = new ClickButton("Break siege and attack "+currArmy.getCurrentLocation());
			attackCity.addActionListener(this);
			attackCity.setActionCommand("Attack");
			buttonPanel.add(attackCity);
			ClickButton autoResolve = new ClickButton("Break siege and attack "+currArmy.getCurrentLocation()+" (Auto reslove)");
			autoResolve.addActionListener(this);
			autoResolve.setActionCommand("Auto");
			autoResolve.setToolTipText("Let your generals do all the action");
			buttonPanel.add(autoResolve);
		}
		
		ClickButton done = new ClickButton("Done");
		done.addActionListener(this);
		done.setToolTipText("Done with this army");
		buttonPanel.add(done);
		
		unitsPanel.setOpaque(false);
		buttonPanel.setOpaque(false);
		unitsPanel.add(buttonPanel, BorderLayout.SOUTH);
		getContentPane().add(unitsPanel);
	}
	
	public String getType(Unit u) {
		if(u instanceof Archer) {
			return "Archer";
		} else if(u instanceof Infantry) {
			return "Infantry";
		} else if(u instanceof Cavalry) {
			return "Cavalry";
		}
		return "";
	}
	
	public City getCity(String name) {
		for(int i = 0; i<currGame.getAvailableCities().size(); i++) {
			if (currGame.getAvailableCities().get(i).getName().equals(name)) return currGame.getAvailableCities().get(i);
		}
		return null;
	}
	
	public void update() {
		getContentPane().removeAll();
		initialize();
		validate();
	}
	
	public boolean isAttacking(String location) {
		for(int i = 0; i<currPlayer.getControlledCities().size(); i++) {
			if(currPlayer.getControlledCities().get(i).getName().equals(location)) return false;
		}
		return true;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		switch(e.getActionCommand()) {
		case "Relocate units to this army":
			RelocateView armysetup = new RelocateView(currArmy, currPlayer);break;
		case "Select a city to target":
			TargetView targetCity = new TargetView(currGame, currArmy);break;
		case "Attack":
			BattleView attack = new BattleView(currArmy,getCity(currArmy.getCurrentLocation()), currGame);
			dispose();
			break;
		case "Besiege":
			try {
				currPlayer.laySiege(currArmy, getCity(currArmy.getCurrentLocation()));
			} catch (TargetNotReachedException | FriendlyCityException e1) {
				ErrorView error = new ErrorView(e1.getMessage());
			} break;
		case "Auto":
			try {
				currGame.autoResolve(currArmy, getCity(currArmy.getCurrentLocation()).getDefendingArmy());
				String message = getCity(currArmy.getCurrentLocation()).getDefendingArmy() == currArmy ? "won":"lost";
				JOptionPane.showMessageDialog(this, "Emperor you have "+message+" the war", "War result", JOptionPane.INFORMATION_MESSAGE,  new ImageIcon("Assets//"+message+"War.jpg"));
				dispose();
			} catch (FriendlyFireException e1) {
				ErrorView error = new ErrorView(e1.getMessage());
			} break;
		case "Done":
			if(c != null) {
				c.updatePanel("Fort");
			}
			dispose();
			break;
		}
	}

	@Override
	public void windowGainedFocus(WindowEvent e) {
		update();
	}

	@Override
	public void windowLostFocus(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	
}
