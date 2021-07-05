package view;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;
import units.*;
import engine.*;
import exceptions.MaxCapacityException;

public class RelocateView  extends JFrame implements ActionListener, WindowFocusListener{

	private Army currArmy;
	private Player currPlayer;
	
	RelocateView(Army a, Player p){
		super();
		currArmy = a;
		currPlayer = p;
		setSize(1080,800);
		setLocationRelativeTo(null);
		setResizable(false);
		setUndecorated(true);
		setVisible(true);
		requestFocus();
		setLayout(new BorderLayout());
		JPanel background = null;
		try {
			background =  new BackgroundPanel("Assets//MenuBG.png");
		} catch (IOException e) {
			
		}
		setContentPane(background);
		initialize();
		addWindowFocusListener(this);
		this.validate();
	}
	
	private void initialize() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setOpaque(false);
		JPanel titlePanel = new JPanel(new FlowLayout());
		titlePanel.setOpaque(false);
		GameLabel title = new GameLabel("Available Units to relocate here");
		title.resize(24f);
		title.setOpaque(false);
		titlePanel.add(title);
		mainPanel.add(titlePanel,BorderLayout.NORTH);
		JPanel unitsPanel = new JPanel(new GridLayout(2,1));
		unitsPanel.setOpaque(false);
		
		JPanel panel1 = new JPanel(new BorderLayout());
		panel1.setOpaque(false);
		JPanel textPanel1 = new JPanel(new FlowLayout());
		textPanel1.setOpaque(false);
		GameLabel city = new GameLabel("From your Cities");
		city.resize(24f);
		city.setOpaque(false);
		city.setAlignmentX(CENTER_ALIGNMENT);
		textPanel1.add(city);
		panel1.add(textPanel1, BorderLayout.NORTH);
		JPanel defendingUnits = new JPanel(new FlowLayout());
		defendingUnits.setPreferredSize(new Dimension (this.getWidth(), this.getHeight()/3));
		for(int i = 0; i<currPlayer.getControlledCities().size(); i++) {
			Army tempArmy = currPlayer.getControlledCities().get(i).getDefendingArmy();
			for(int j = 0; j<tempArmy.getUnits().size(); j++) {
				Unit currUnit = tempArmy.getUnits().get(j);
				ClickButton temp = new ClickButton(getType(currUnit)+" level "+currUnit.getLevel()+", Current Soldiers: "+currUnit.getCurrentSoldierCount()+" of "+currUnit.getMaxSoldierCount());
				Relocate relocateAction = new Relocate(currArmy, currUnit);
				temp.addActionListener(this);
				temp.addActionListener(relocateAction);
				defendingUnits.add(temp);
			}
		}
		defendingUnits.setOpaque(false);
		panel1.add(defendingUnits, BorderLayout.CENTER);
		unitsPanel.add(panel1);
		
		JPanel panel2 = new JPanel(new BorderLayout());
		panel2.setOpaque(false);
		JPanel textPanel2 = new JPanel(new FlowLayout());
		textPanel2.setOpaque(false);
		GameLabel army = new GameLabel("From other Armies");
		army.resize(24f);
		army.setOpaque(false);
		textPanel2.add(army);
		panel2.add(textPanel2, BorderLayout.NORTH);
		JPanel armyUnits = new JPanel(new FlowLayout());
		armyUnits.setPreferredSize(new Dimension (this.getWidth(), this.getHeight()/3));
		for(int i = 0; i<currPlayer.getControlledArmies().size(); i++) {
			if(currPlayer.getControlledArmies().get(i) != currArmy) {
				Army tempArmy = currPlayer.getControlledArmies().get(i);
				for(int j = 0; j<tempArmy.getUnits().size(); j++) {
					Unit currUnit = tempArmy.getUnits().get(j);
					ClickButton temp = new ClickButton(getType(currUnit)+" level "+currUnit.getLevel()+", Current Soldiers: "+currUnit.getCurrentSoldierCount()+" of "+currUnit.getMaxSoldierCount());
					Relocate relocateAction = new Relocate(currArmy, currUnit);
					temp.addActionListener(this);
					temp.addActionListener(relocateAction);
					armyUnits.add(temp);
				}
			}
		}
		armyUnits.setOpaque(false);
		panel2.add(armyUnits, BorderLayout.CENTER);
		unitsPanel.add(panel2);
		
		mainPanel.add(unitsPanel, BorderLayout.CENTER);
		
		JPanel buttonPanel = new JPanel(new FlowLayout());
		buttonPanel.setOpaque(false);
		ClickButton done = new ClickButton("Done");
		done.addActionListener(this);
		buttonPanel.add(done);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		getContentPane().add(mainPanel);
	}
	
	public void update() {
		getContentPane().removeAll();
		getContentPane().repaint();
		initialize();
		validate();
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

	@Override
	public void actionPerformed(ActionEvent e) {
		update();
		if(e.getActionCommand().equals("Done")) {
			dispose();
		}
	}

	@Override
	public void windowGainedFocus(WindowEvent e) {
		update();
	}

	@Override
	public void windowLostFocus(WindowEvent e) {
		
	}
}
