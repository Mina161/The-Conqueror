package view;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.*;
import javax.sound.sampled.*;
import javax.swing.*;
import buildings.*;
import engine.*;
import exceptions.*;
import units.*;

public class CityView extends JFrame implements ActionListener, Views{
	
	private Player currPlayer;
	private City currCity;
	private Game currGame;
	private JPanel editPanel;
	
	public CityView(Player currPlayer, String city, Game currGame) {
		super();
		addWindowFocusListener(this);
		this.currGame = currGame;
		this.currPlayer = currPlayer;
		for(int i = 0; i<currPlayer.getControlledCities().size(); i++) {
			if(currPlayer.getControlledCities().get(i).getName().equals(city)) {
				currCity = currPlayer.getControlledCities().get(i);
				break;
			}
		}
		JPanel mainPanel = new JPanel();
		mainPanel.setBounds(0, 0, 1600, 900);
		setSize(1600,900);
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setLocationRelativeTo(null);
		setResizable(false);
		setUndecorated(true);
		setVisible(true);
		setTitle("Welcome to "+city);
		setLayout(new BorderLayout());
		initialize(); //Show the GUI
		addWindowFocusListener(this); 
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setFocusable(true);
	}
	
	public void initialize() {
		JPanel HUD = null;
		try {
			HUD =  new BackgroundPanel("Assets//hudBG.png");
		} catch (IOException e) {
			
		}
		HUD.setLayout(new GridLayout(1,3));
		HUD.setPreferredSize(new Dimension(this.getWidth(),this.getHeight()/15));
		HUD.setOpaque(false);
		JPanel leavePanel = new JPanel(new FlowLayout());
		leavePanel.setOpaque(false);
		ClickButton leave = new ClickButton(new ImageIcon("Assets//Door.png"));
		leave.setToolTipText("Leave "+currCity.getName());
		Hover hLeave = new Hover(leave,"Door");
		leave.addMouseListener(hLeave);
		leave.setContentAreaFilled(false);
		leave.setBorderPainted(false);
		leavePanel.add(leave);
		leave.setActionCommand("Exit");
		leave.addActionListener(this);
		JPanel moneyPanel = new JPanel(new FlowLayout());
		moneyPanel.setOpaque(false);
		ImageIcon gold = new ImageIcon("Assets//Treasury.png");
		GameLabel goldIcon = new GameLabel(gold);
		goldIcon.setToolTipText("The amout of gold in your Empire. You need this to purchase useful buildings and units");
		GameLabel money = new GameLabel(""+currPlayer.getTreasury());
		money.resize(20f);
		money.setOpaque(false);
		moneyPanel.add(goldIcon);
		moneyPanel.add(money);
		
		JPanel foodPanel = new JPanel(new FlowLayout());
		foodPanel.setOpaque(false);
		ImageIcon bread = new ImageIcon("Assets//Food.png");
		GameLabel breadIcon = new GameLabel(bread);
		breadIcon.setToolTipText("The amout of food in your Empire. You need this to feed your armies");
		GameLabel food = new GameLabel(""+currPlayer.getFood());
		food.resize(20f);
		food.setOpaque(false);
		foodPanel.add(breadIcon);
		foodPanel.add(food);
		
		HUD.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		HUD.add(moneyPanel);
		HUD.add(leavePanel);
		HUD.add(foodPanel);
		getContentPane().add(HUD,BorderLayout.NORTH);
		
		ClickButton farmBuilding = new ClickButton( new ImageIcon("Assets//Farm.png"));
		Hover h1 = null;
		if(getBuilding("Farm") == null) {
			farmBuilding.setIcon(new ImageIcon("Assets//Empty.png"));
			h1 = new Hover(farmBuilding, "Empty");
		} else { 
			farmBuilding.setToolTipText("Farm level:"+getBuilding("Farm").getLevel());
			h1 = new Hover(farmBuilding, "Farm");
		}
		farmBuilding.addMouseListener(h1);
		farmBuilding.setText("Farm");
		farmBuilding.setActionCommand("Farm");
		farmBuilding.setVerticalTextPosition(SwingConstants.BOTTOM);
		farmBuilding.setHorizontalTextPosition(SwingConstants.CENTER);
		farmBuilding.setContentAreaFilled(false);
		farmBuilding.setBorderPainted(false);
		farmBuilding.addActionListener(this);
		farmBuilding.setOpaque(false);
		farmBuilding.setForeground(Color.BLACK);
		
		ClickButton marketBuilding = new ClickButton( new ImageIcon("Assets//Market.png"));
		Hover h2 = null;
		if(getBuilding("Market") == null) {
			marketBuilding.setIcon(new ImageIcon("Assets//Empty.png"));
			h2 = new Hover(marketBuilding, "Empty");
		} else { 
			marketBuilding.setToolTipText("Market level:"+getBuilding("Market").getLevel());
			h2 = new Hover(marketBuilding, "Market");
		}
		marketBuilding.addMouseListener(h2);
		marketBuilding.setText("Market");
		marketBuilding.setActionCommand("Market");
		marketBuilding.setVerticalTextPosition(SwingConstants.BOTTOM);
		marketBuilding.setHorizontalTextPosition(SwingConstants.CENTER);
		marketBuilding.setContentAreaFilled(false);
		marketBuilding.setBorderPainted(false);
		marketBuilding.addActionListener(this);
		marketBuilding.setOpaque(false);
		marketBuilding.setForeground(Color.BLACK);
		
		ClickButton archeryBuilding = new ClickButton(new ImageIcon("Assets//Archery.png"));
		Hover h3 = null;
		if(getBuilding("ArcheryRange") == null) {
			archeryBuilding.setIcon(new ImageIcon("Assets//Empty.png"));
			h3 = new Hover(archeryBuilding, "Empty");
		} else { 
			archeryBuilding.setToolTipText("Archery Range level:"+getBuilding("ArcheryRange").getLevel());
			h3 = new Hover(archeryBuilding, "Archery");
		}
		archeryBuilding.addMouseListener(h3);
		archeryBuilding.setText("Archery Range");
		archeryBuilding.setActionCommand("Archery");
		archeryBuilding.setVerticalTextPosition(SwingConstants.BOTTOM);
		archeryBuilding.setHorizontalTextPosition(SwingConstants.CENTER);
		archeryBuilding.setContentAreaFilled(false);
		archeryBuilding.setBorderPainted(false);
		archeryBuilding.addActionListener(this);
		archeryBuilding.setOpaque(false);
		archeryBuilding.setForeground(Color.BLACK);	
		
		ClickButton barracksBuilding = new ClickButton(new ImageIcon("Assets//Barracks.png"));
		Hover h4 = null;
		if(getBuilding("Barracks") == null) {
			barracksBuilding.setIcon(new ImageIcon("Assets//Empty.png"));
			h4 = new Hover(barracksBuilding, "Empty");
		} else { 
			barracksBuilding.setToolTipText("Barracks level:"+getBuilding("Barracks").getLevel());
			h4 = new Hover(barracksBuilding, "Barracks");
		}
		barracksBuilding.addMouseListener(h4);
		barracksBuilding.setText("Barracks");
		barracksBuilding.setActionCommand("Barracks");
		barracksBuilding.setVerticalTextPosition(SwingConstants.BOTTOM);
		barracksBuilding.setHorizontalTextPosition(SwingConstants.CENTER);
		barracksBuilding.setContentAreaFilled(false);
		barracksBuilding.setBorderPainted(false);
		barracksBuilding.addActionListener(this);
		barracksBuilding.setOpaque(false);
		barracksBuilding.setForeground(Color.BLACK);	
		
		ClickButton stableBuilding = new ClickButton(new ImageIcon("Assets//Stable.png"));
		Hover h5 = null;
		if(getBuilding("Stable") == null) {
			stableBuilding.setIcon(new ImageIcon("Assets//Empty.png"));
			h5 = new Hover(stableBuilding, "Empty");
		} else { 
			stableBuilding.setToolTipText("Stable level:"+getBuilding("Stable").getLevel());
			h5 = new Hover(stableBuilding, "Stable");
		}
		stableBuilding.addMouseListener(h5);
		stableBuilding.setText("Stable");
		stableBuilding.setActionCommand("Stable");
		stableBuilding.setVerticalTextPosition(SwingConstants.BOTTOM);
		stableBuilding.setHorizontalTextPosition(SwingConstants.CENTER);
		stableBuilding.setContentAreaFilled(false);
		stableBuilding.setBorderPainted(false);
		stableBuilding.addActionListener(this);
		stableBuilding.setOpaque(false);
		stableBuilding.setForeground(Color.BLACK);
		
		ClickButton fortBuilding = new ClickButton(new ImageIcon("Assets//Fort.png"));
		Hover h6 = new Hover(fortBuilding, "Fort");
		fortBuilding.addMouseListener(h6);
		fortBuilding.setText("Fortress");
		fortBuilding.setToolTipText("All your units present at "+currCity.getName()+" are stationed here");
		fortBuilding.setActionCommand("Fort");
		fortBuilding.setVerticalTextPosition(SwingConstants.BOTTOM);
		fortBuilding.setHorizontalTextPosition(SwingConstants.CENTER);
		fortBuilding.setContentAreaFilled(false);
		fortBuilding.setBorderPainted(false);
		fortBuilding.addActionListener(this);
		fortBuilding.setOpaque(false);
		fortBuilding.setForeground(Color.BLACK);
		
		if(currCity.getName().equals("Cairo")) {
			JPanel photoPanel = null;
			try {
				photoPanel = new BackgroundPanel("Assets//CairoBG.png");
			} catch (IOException e) {
				
			}
			photoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
			photoPanel.setLayout(new GridLayout(3,8,5,5));
			getContentPane().add(photoPanel, BorderLayout.CENTER);
			
			JPanel[][] panelsGrid = new JPanel[3][8];
			for(int i = 0; i<3; i++) {
				for(int j = 0; j<8; j++) {
					JPanel temp = new JPanel(new FlowLayout());
					temp.setOpaque(false);
					panelsGrid[i][j] = temp;
					photoPanel.add(temp);
				}
			}
			

			panelsGrid[0][1].add(farmBuilding);
			panelsGrid[2][0].add(marketBuilding);
			panelsGrid[1][2].add(archeryBuilding); 
			panelsGrid[1][5].add(barracksBuilding); 
			panelsGrid[2][7].add(stableBuilding);
			panelsGrid[0][7].add(fortBuilding); 

		} 
		
		else if(currCity.getName().equals("Rome")) {
			JPanel photoPanel = null;
			try {
				photoPanel = new BackgroundPanel("Assets//RomeBG.png");
			} catch (IOException e) {
				
			}
			photoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
			photoPanel.setLayout(new GridLayout(3,8,5,5));
			getContentPane().add(photoPanel, BorderLayout.CENTER);
			
			JPanel[][] panelsGrid = new JPanel[3][8];
			for(int i = 0; i<3; i++) {
				for(int j = 0; j<8; j++) {
					JPanel temp = new JPanel(new FlowLayout());
					temp.setOpaque(false);
					panelsGrid[i][j] = temp;
					photoPanel.add(temp);
				}
			}
			panelsGrid[0][1].add(farmBuilding);
			panelsGrid[2][0].add(marketBuilding);
			panelsGrid[1][2].add(archeryBuilding);
			panelsGrid[1][5].add(barracksBuilding);
			panelsGrid[2][7].add(stableBuilding);
			panelsGrid[0][7].add(fortBuilding);
		} 
		
		else {
			JPanel photoPanel = null;
			try {
				photoPanel = new BackgroundPanel("Assets//SpartaBG.png");
			} catch (IOException e) {
				
			}
			photoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
			photoPanel.setLayout(new GridLayout(3,8,5,5));
			getContentPane().add(photoPanel, BorderLayout.CENTER);
			
			JPanel[][] panelsGrid = new JPanel[3][8];
			for(int i = 0; i<3; i++) {
				for(int j = 0; j<8; j++) {
					JPanel temp = new JPanel(new FlowLayout());
					temp.setOpaque(false);
					panelsGrid[i][j] = temp;
					photoPanel.add(temp);
				}
			}
			
			panelsGrid[1][3].add(farmBuilding);
			panelsGrid[2][7].add(marketBuilding);
			panelsGrid[0][1].add(archeryBuilding);
			panelsGrid[2][2].add(barracksBuilding);
			panelsGrid[2][0].add(stableBuilding);
			panelsGrid[0][7].add(fortBuilding);
		} 
		
		
		if(editPanel == null) {
			try {
				editPanel =  new BackgroundPanel("Assets//Panel.png");
			} catch (IOException e) {
				
			}
			editPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
			editPanel.setLayout(new FlowLayout());
			editPanel.setPreferredSize(new Dimension(this.getWidth(),this.getHeight()/5));
		}
		getContentPane().add(editPanel,BorderLayout.SOUTH);
		this.validate();
	}
	
	public void setupPanel(String typeOfBuilding) {
		if(typeOfBuilding.equals("Fort")) {
			editPanel.setLayout(new FlowLayout());
			
			JPanel armiesPanel = new JPanel(new GridLayout(1,2));
			JPanel unitsPanel = new JPanel(new BorderLayout());
			JPanel titlePanel = new JPanel(new FlowLayout());
			titlePanel.setBackground(new Color(255,255,255,200));
			GameLabel title = new GameLabel("Stationed Units");
			title.resize(26f);
			title.setOpaque(false);
			titlePanel.add(title);
			unitsPanel.add(titlePanel,BorderLayout.NORTH);
			JPanel defendingUnits = new JPanel(new FlowLayout());
			defendingUnits.setPreferredSize(new Dimension(this.getWidth()/2,editPanel.getHeight()-50));
			defendingUnits.setOpaque(false);
			for(int i = 0; i<currCity.getDefendingArmy().getUnits().size(); i++) {
				Unit currUnit = currCity.getDefendingArmy().getUnits().get(i);
				Initiate initiateAction = new Initiate(currPlayer, currCity, currUnit);
				ClickButton temp = new ClickButton(currUnit.toString());
				temp.setToolTipText("Initiate a new army with this unit. Current Soldiers: "+currUnit.getCurrentSoldierCount()+" of "+currUnit.getMaxSoldierCount());
				temp.addActionListener(this);
				temp.addActionListener(initiateAction);
				temp.setActionCommand("Initiate Army");
				defendingUnits.add(temp);
			}
			
			unitsPanel.add(defendingUnits, BorderLayout.CENTER);
			unitsPanel.setOpaque(false);
			armiesPanel.add(unitsPanel);
			
			JPanel idlePanel = new JPanel(new BorderLayout());
			JPanel titlePanel1 = new JPanel(new FlowLayout());
			titlePanel1.setBackground(new Color(255,255,255,200));
			GameLabel title1 = new GameLabel("Idle Armies");
			title1.resize(26f);
			title1.setOpaque(false);
			titlePanel1.add(title1);
			idlePanel.add(titlePanel1,BorderLayout.NORTH);
			JPanel idleArmies = new JPanel(new FlowLayout());
			idleArmies.setPreferredSize(new Dimension(this.getWidth()/2,editPanel.getHeight()-50));
			idleArmies.setOpaque(false);
			for(int i = 0; i<currPlayer.getControlledArmies().size(); i++) {
				Army currArmy = currPlayer.getControlledArmies().get(i);
				if(currArmy.getCurrentStatus()==Status.IDLE && currArmy.getCurrentLocation().equals(currCity.getName())) {
					Display displayAction = new Display(currArmy, currPlayer, currGame, this);
					ClickButton temp = new ClickButton("Army idle at "+currArmy.getCurrentLocation());
					temp.addActionListener(displayAction);
					temp.addActionListener(this);
					idleArmies.add(temp);
				}
			}
			idlePanel.add(idleArmies, BorderLayout.CENTER);
			idlePanel.setOpaque(false);
			armiesPanel.add(idlePanel);
			armiesPanel.setOpaque(false);
			
			editPanel.add(armiesPanel);
		} else {
			JPanel grid = new JPanel(new GridLayout(2,1));
			grid.setOpaque(false);
			JPanel infoPanel = new JPanel();
			infoPanel.setOpaque(false);
			GameLabel info = null;
			if(getBuilding(typeOfBuilding) != null) {
				if(typeOfBuilding.equals("ArcheryRange")) 
					info = new GameLabel("Archery Range Level:"+getBuilding(typeOfBuilding).getLevel()+" costs "+getBuilding(typeOfBuilding).getUpgradeCost()+" to upgrade");
				else
					info = new GameLabel(typeOfBuilding+" Level:"+getBuilding(typeOfBuilding).getLevel()+" costs "+getBuilding(typeOfBuilding).getUpgradeCost()+" to upgrade");
			} else {
				switch(typeOfBuilding) {
				case "ArcheryRange": info = new GameLabel("Archery Range costs 1500 to build");break;
				case "Barracks": info = new GameLabel("Barracks costs 2000 to build");break;
				case "Stable": info = new GameLabel("Stable costs 2500 to build");break;
				case "Farm": info = new GameLabel("Farm costs 1000 to build");break;
				case "Market": info = new GameLabel("Market costs 1500 to build");break;
				}
			}
			info.setOpaque(true);
			info.setBackground(new Color(255,255,255,200));
			info.resize(26f);
			infoPanel.add(info);
			grid.add(infoPanel);
			JPanel buttons = new JPanel(new FlowLayout());
			buttons.setOpaque(false);
			if(typeOfBuilding.equals("Farm")) {
				ClickButton farmBuild = new ClickButton("Build Farm",false);
				farmBuild.setActionCommand("BuildFarm");
				farmBuild.addActionListener(this);
				buttons.add(farmBuild);
				ClickButton farmUpgrade = new ClickButton("Upgrade Farm", false);
				farmUpgrade.setActionCommand("UpgradeFarm");
				farmUpgrade.addActionListener(this);
				buttons.add(farmUpgrade);
				if(getBuilding("Farm") == null) {
					farmUpgrade.setEnabled(false);
					farmBuild.setToolTipText("Costs 1000, you need this building to gain food");
				} else {
					farmUpgrade.setToolTipText("Current Level: "+getBuilding("Farm").getLevel());
					farmBuild.setEnabled(false);
				}
			} else if(typeOfBuilding.equals("Market")) {
				ClickButton marketBuild = new ClickButton("Build Market", false);
				marketBuild.setActionCommand("BuildMarket");
				marketBuild.addActionListener(this);
				buttons.add(marketBuild);
				ClickButton marketUpgrade = new ClickButton("Ugrade Market", false);
				marketUpgrade.setActionCommand("UpgradeMarket");
				marketUpgrade.addActionListener(this);
				buttons.add(marketUpgrade);
				if(getBuilding("Market") == null) {
					marketUpgrade.setEnabled(false);
					marketUpgrade.setToolTipText("");
					marketBuild.setToolTipText("Costs 1500, , you need this building to earn gold");
				} else {
					marketUpgrade.setToolTipText("Current Level: "+getBuilding("Market").getLevel());
					marketBuild.setEnabled(false);
				}
			} else if(typeOfBuilding.equals("Barracks")) {
				ClickButton barracksBuild = new ClickButton("Build Barracks",false);
				barracksBuild.setActionCommand("BuildBarracks");
				barracksBuild.addActionListener(this);
				buttons.add(barracksBuild);
				ClickButton barracksUpgrade = new ClickButton("Ugrade Barracks",false);
				barracksUpgrade.setActionCommand("UpgradeBarracks");
				barracksUpgrade.addActionListener(this);
				buttons.add(barracksUpgrade);
				ClickButton recruitInfantry = new ClickButton("Recruit Infantry",false);
				recruitInfantry.setActionCommand("RecruitInfantry");
				recruitInfantry.addActionListener(this);
				buttons.add(recruitInfantry);
				if(getBuilding("Barracks") == null) {
					recruitInfantry.setEnabled(false);
					barracksUpgrade.setEnabled(false);
					barracksBuild.setToolTipText("Barracks costs 2000, you need this building to recruit Infantry");
				} else {
					recruitInfantry.setText("Recruit Infantry");
					recruitInfantry.setToolTipText("Infantry level "+getBuilding("Barracks").getLevel()+" costs "+((MilitaryBuilding) getBuilding("Barracks")).getRecruitmentCost()+" gold");
					barracksUpgrade.setText("Upgrade Barracks");
					barracksBuild.setEnabled(false);
				}
			} else if(typeOfBuilding.equals("ArcheryRange")) {
				ClickButton archeryBuild = new ClickButton("Build Archery", false);
				archeryBuild.setActionCommand("BuildArchery");
				archeryBuild.addActionListener(this);
				buttons.add(archeryBuild);
				ClickButton archeryUpgrade = new ClickButton("Ugrade Archery", false);
				archeryUpgrade.setActionCommand("UpgradeArchery");
				archeryUpgrade.addActionListener(this);
				buttons.add(archeryUpgrade);
				ClickButton recruitArcher = new ClickButton("Recruit Archer", false);
				recruitArcher.setActionCommand("RecruitArcher");
				recruitArcher.addActionListener(this);
				buttons.add(recruitArcher);
				if(getBuilding("ArcheryRange") == null) {
					recruitArcher.setEnabled(false);
					archeryUpgrade.setEnabled(false);
					archeryBuild.setToolTipText("Archery Range costs 1500, you need this building to recruit Archers");
				} else {
					recruitArcher.setText("Recruit Archer");
					recruitArcher.setToolTipText("Archer level "+getBuilding("ArcheryRange").getLevel()+" costs "+((MilitaryBuilding) getBuilding("ArcheryRange")).getRecruitmentCost()+" gold");
					archeryUpgrade.setText("Upgrade Archery");
					archeryBuild.setEnabled(false);
				}
			} else if(typeOfBuilding.equals("Stable")) {
				ClickButton stableBuild = new ClickButton("Build Stable", false);
				stableBuild.setActionCommand("BuildStable");
				stableBuild.addActionListener(this);
				buttons.add(stableBuild);
				ClickButton stableUpgrade = new ClickButton("Ugrade Stable", false);
				stableUpgrade.setActionCommand("UpgradeStable");
				stableUpgrade.addActionListener(this);
				buttons.add(stableUpgrade);
				ClickButton recruitCavalry = new ClickButton("Recruit Cavalry", false);
				recruitCavalry.setActionCommand("RecruitCavalry");
				recruitCavalry.addActionListener(this);
				buttons.add(recruitCavalry);
				if(getBuilding("Stable") == null) {
					recruitCavalry.setEnabled(false);
					stableUpgrade.setEnabled(false);
					stableBuild.setToolTipText("Stable costs 2500, you need this building to recruit Cavalry");
				} else {
					stableBuild.setEnabled(false);
					recruitCavalry.setText("Recruit Cavalry");
					recruitCavalry.setToolTipText("Cavalry level "+getBuilding("Stable").getLevel()+" costs "+((MilitaryBuilding) getBuilding("Stable")).getRecruitmentCost()+" gold");
					stableUpgrade.setText("Upgrade Stable");
				}
			}
			grid.add(buttons);
			editPanel.setLayout(new BorderLayout());
			editPanel.add(grid, BorderLayout.CENTER);
		}
		this.validate();
	}
	
	
	
	public Building getBuilding(String type) {
		for(int i = 0; i< currCity.getEconomicalBuildings().size(); i++) {
			Building currBuilding = currCity.getEconomicalBuildings().get(i);
			switch(type) {
			case "Farm": if(currBuilding instanceof Farm) return currBuilding;break;
			case "Market": if(currBuilding instanceof Market) return currBuilding;break;
			}
		}
		
		for(int i = 0; i< currCity.getMilitaryBuildings().size(); i++) {
			Building currBuilding = currCity.getMilitaryBuildings().get(i);
			switch(type) {
			case "Barracks": if(currBuilding instanceof Barracks) return currBuilding;break;
			case "ArcheryRange": if(currBuilding instanceof ArcheryRange) return currBuilding;break;
			case "Stable": if(currBuilding instanceof Stable) return currBuilding;break;
			}
		}
		
		return null;
	}
	
	public void updatePanel(String type) {
		editPanel.removeAll();
		editPanel.repaint();
		setupPanel(type);
		editPanel.validate();
	}
	
	public void update() {
		getContentPane().removeAll();
		initialize();
		validate();
	}
	
	public void playClip(String name) {
		try {
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File("Assets//"+name+".wav"));
	        Clip clip = AudioSystem.getClip();
	        clip.open(audioIn);
	        clip.start();
		} catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
	        e.printStackTrace();
		}
	}
	
	@Override
	public void windowGainedFocus(WindowEvent e) {
		setFocusable(true);
		update();
	}
	@Override
	public void windowLostFocus(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Initiate Army")) {
			updatePanel("Fort");
		} else if(e.getActionCommand().equals("Exit")) {
			dispose();
		} else {
			if(e.getActionCommand().equals("Farm")) {
				updatePanel("Farm");
			} else if (e.getActionCommand().equals("Market")) {
				updatePanel("Market");
			} else if (e.getActionCommand().equals("Archery")) {
				updatePanel("ArcheryRange");
			} else if (e.getActionCommand().equals("Barracks")) {
				updatePanel("Barracks");
			} else if (e.getActionCommand().equals("Stable")) {
				updatePanel("Stable");
			} else if (e.getActionCommand().equals("Fort")) {
				updatePanel("Fort");
			} else {
				try {
					if(e.getActionCommand().equals("UpgradeStable")) {
						currPlayer.upgradeBuilding(getBuilding("Stable"));
						playClip("Buy");
						updatePanel("Stable");
					} else  if(e.getActionCommand().equals("UpgradeBarracks")) {
						currPlayer.upgradeBuilding(getBuilding("Barracks"));
						playClip("Buy");
						updatePanel("Barracks");
					} else if(e.getActionCommand().equals("UpgradeArchery")) {
						currPlayer.upgradeBuilding(getBuilding("ArcheryRange"));
						playClip("Buy");
						updatePanel("ArcheryRange");
					} else if(e.getActionCommand().equals("UpgradeFarm")) {
						currPlayer.upgradeBuilding(getBuilding("Farm"));
						playClip("Buy");
						updatePanel("Farm");
					} else if(e.getActionCommand().equals("UpgradeMarket")) {
						currPlayer.upgradeBuilding(getBuilding("Market"));
						playClip("Buy");
						updatePanel("Market");
					} else if(e.getActionCommand().equals("RecruitCavalry")) {
						currPlayer.recruitUnit("Cavalry", currCity.getName());
						playClip("Buy");
						updatePanel("Stable");
					} else  if(e.getActionCommand().equals("RecruitInfantry")) {
						currPlayer.recruitUnit("Infantry", currCity.getName());
						playClip("Buy");
						updatePanel("Barracks");
					} else if(e.getActionCommand().equals("RecruitArcher")) {
						currPlayer.recruitUnit("Archer", currCity.getName());
						playClip("Buy");
						updatePanel("ArcheryRange");
					} else if(e.getActionCommand().equals("BuildStable")) {
						currPlayer.build("Stable", currCity.getName());
						playClip("Build");
						updatePanel("Stable");
					} else  if(e.getActionCommand().equals("BuildBarracks")) {
						currPlayer.build("Barracks", currCity.getName());
						playClip("Build");
						updatePanel("Barracks");
					} else if(e.getActionCommand().equals("BuildArchery")) {
						currPlayer.build("ArcheryRange", currCity.getName());
						playClip("Build");
						updatePanel("ArcheryRange");
					} else if(e.getActionCommand().equals("BuildFarm")) {
						currPlayer.build("Farm", currCity.getName());
						playClip("Build");
						updatePanel("Farm");
					} else if(e.getActionCommand().equals("BuildMarket")) {
						currPlayer.build("Market", currCity.getName());
						playClip("Build");
						updatePanel("Market");
					}
				} catch(NotEnoughGoldException | MaxRecruitedException | BuildingInCoolDownException | MaxLevelException e1) {
					ErrorView error = new ErrorView(e1.getMessage());
				}
				update();
			}
		}
	}
}

