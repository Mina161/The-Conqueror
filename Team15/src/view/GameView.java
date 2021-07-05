package view;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.sound.sampled.*;
import javax.swing.*;
import engine.*;
import exceptions.FriendlyFireException;
import units.*;

@SuppressWarnings("serial")
public class GameView extends JFrame implements Views, ActionListener, KeyListener{
	private Game currGame;
	private Player currPlayer;
	private String startCity;
	private Color translucent = new Color(0f,0f,0f,0.7f);
	private Clip gameMusic;
	
	public GameView(String playerName, String cityName) throws IOException {
		super();
		addWindowFocusListener(this);
		currGame = new Game(playerName, cityName);
		currPlayer = currGame.getPlayer();
		startCity = cityName;
		setSize(1600,900);
		setExtendedState(JFrame.MAXIMIZED_BOTH); //Full Screen
		setLocationRelativeTo(null); //Centered to screen
		setResizable(false); //Final size
		setUndecorated(true);
		setVisible(true);
		setLayout(new BorderLayout());
		initialize(); //Show the GUI
		playMusic();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.addKeyListener(this);
		setFocusable(true);
		this.validate();
	}

	public void playMusic() { //Playing music tutorial found on StackOverFlow using Java's API
		try {
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File("Assets//"+startCity+" Theme.wav")); // All music credits go to Geoff Knorr
	        gameMusic = AudioSystem.getClip();
	        gameMusic.open(audioIn);
		} catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
	        e.printStackTrace();
	    }
		gameMusic.start();
		gameMusic.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void stopMusic() {
		gameMusic.stop();
	}
	
	public boolean isPlayingMusic() {
		return gameMusic.isActive();
	}

	@Override
	public void initialize() {
		String playerName = currPlayer.getName();
		
		setTitle("Emperor "+playerName);
		ImageIcon img = new ImageIcon("Assets//"+startCity+".jpg");
		setIconImage(img.getImage());
		
		JPanel HUD = null;
		try {
			HUD =  new BackgroundPanel("Assets//hudBG.png");
		} catch (IOException e) {
			
		}
		HUD.setLayout(new GridLayout(1,5));
		HUD.setPreferredSize(new Dimension(this.getWidth(),this.getHeight()/15));
		
		JPanel menuPanel = new JPanel(new FlowLayout());
		menuPanel.setOpaque(false);
		ClickButton menu = new ClickButton( new ImageIcon("Assets//Menu.png"));
		menu.setActionCommand("ViewMenu");
		menu.setContentAreaFilled(false);
		menu.setBorderPainted(false);
		menu.addActionListener(this);
		menuPanel.add(menu);
		
		JPanel turnPanel = new JPanel(new FlowLayout());
		turnPanel.setOpaque(false);
		GameLabel turn = new GameLabel("Turn: "+currGame.getCurrentTurnCount()); //show text
		turn.setToolTipText((50-currGame.getCurrentTurnCount())+" turns left");
		turn.resize(20f);
		turn.setOpaque(false); //show what is behind
		turn.setAlignmentY(CENTER_ALIGNMENT);
		turnPanel.add(turn);
		
		JPanel namePanel = new JPanel(new FlowLayout());
		namePanel.setBackground(new Color(194,158,16,150)); 
		namePanel.setBorder(BorderFactory.createLineBorder(new Color(112,41,99), 3, true));
		GameLabel name = new GameLabel(currPlayer.getName()+"'s Empire");
		name.resize(25f);
		name.setOpaque(false);
		namePanel.add(name);
		
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
		
		HUD.add(menuPanel);
		HUD.add(turnPanel);
		HUD.add(namePanel);
		HUD.add(moneyPanel);
		HUD.add(foodPanel);
		HUD.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		getContentPane().add(HUD,BorderLayout.NORTH);
		
		JPanel worldMap = null;
		try {
			worldMap =  new BackgroundPanel("Assets//MapBG.jpg");
		} catch (IOException e) {
			
		}
		setupMap(worldMap);
		worldMap.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		getContentPane().add(worldMap,BorderLayout.CENTER);
		
		JPanel armyView = null;
		try {
			armyView =  new BackgroundPanel("Assets//ArmyPanelBG.jpg");
		} catch (IOException e) {
			
		}
		
		armyView.setLayout(new BorderLayout());
		armyView.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		
		JPanel armyPanel = new JPanel(new GridLayout(3,1));
		armyPanel.setOpaque(false);
		setupArmies(armyPanel);
		armyView.add(armyPanel, BorderLayout.CENTER);
		
		armyView.setPreferredSize(new Dimension(this.getWidth()/6,this.getHeight()));
		armyView.setMaximumSize(new Dimension(this.getWidth()/6,this.getHeight()));
		getContentPane().add(armyView,BorderLayout.WEST);
	}
	
	private void setupArmies(JPanel armyView) {
		Display displayAction;
		
		JPanel marchingPanel = new JPanel(new BorderLayout());
		marchingPanel.setOpaque(false);
		JPanel titlePanel = new JPanel(new FlowLayout());
		titlePanel.setOpaque(false);
		GameLabel march = new GameLabel("Marching Armies");
		march.resize(23f);
		march.setBackground(translucent);
		march.setForeground(Color.WHITE);
		march.setOpaque(true);
		titlePanel.add(march);
		marchingPanel.add(titlePanel,BorderLayout.NORTH);
		JPanel marchingUnits = new JPanel(new GridLayout(currPlayer.getControlledArmies().size()/2,2));
		marchingUnits.setOpaque(false);
		for(int i = 0; i<currPlayer.getControlledArmies().size(); i++) {
			Army currArmy = currPlayer.getControlledArmies().get(i);
			if(currArmy.getCurrentStatus()==Status.MARCHING) {
				displayAction = new Display(currArmy, currPlayer, currGame);
				ClickButton temp = new ClickButton(new ImageIcon("Assets//MarchingArmies.png"));
				Hover h = new Hover(temp, "MarchingArmies");
				temp.addMouseListener(h);
				temp.setContentAreaFilled(false);
				temp.setBorderPainted(false);
				temp.setText("Going to "+currArmy.getTarget());
				temp.setVerticalTextPosition(SwingConstants.BOTTOM);
				temp.setHorizontalTextPosition(SwingConstants.CENTER);
				temp.setToolTipText("Army reaching "+currArmy.getTarget()+" in "+currArmy.getDistancetoTarget()+" turn(s)");
				temp.addActionListener(displayAction);
				marchingUnits.add(temp);
			}
		}
		marchingPanel.add(marchingUnits, BorderLayout.CENTER);
		
		JPanel besiegingPanel = new JPanel(new BorderLayout());
		besiegingPanel.setOpaque(false);
		JPanel titlePanel1 = new JPanel(new FlowLayout());
		titlePanel1.setOpaque(false);
		GameLabel besiege = new GameLabel("Besieging Armies");
		besiege.resize(23f);
		besiege.setBackground(translucent);
		besiege.setForeground(Color.WHITE);
		besiege.setOpaque(true);
		titlePanel1.add(besiege);
		besiegingPanel.add(titlePanel1,BorderLayout.NORTH);
		JPanel besiegingUnits = new JPanel(new GridLayout(currPlayer.getControlledArmies().size()/2,2));
		besiegingUnits.setOpaque(false);
		for(int i = 0; i<currPlayer.getControlledArmies().size(); i++) {
			Army currArmy = currPlayer.getControlledArmies().get(i);
			if(currArmy.getCurrentStatus()==Status.BESIEGING) {
				int underSiege = getCity(currArmy.getCurrentLocation()).getTurnsUnderSiege();
				displayAction = new Display(currArmy, currPlayer, currGame);
				ClickButton temp = new ClickButton(new ImageIcon("Assets//SiegingArmies.png"));
				Hover h = new Hover(temp, "SiegingArmies");
				temp.addMouseListener(h);
				temp.setContentAreaFilled(false);
				temp.setBorderPainted(false);
				temp.setText("Sieging "+currArmy.getCurrentLocation());
				temp.setVerticalTextPosition(SwingConstants.BOTTOM);
				temp.setHorizontalTextPosition(SwingConstants.CENTER);
				temp.addActionListener(displayAction);
				temp.setToolTipText("Army besieging "+currArmy.getCurrentLocation()+" for "+underSiege+" turn(s)");
				besiegingUnits.add(temp);
			}
		}
		besiegingPanel.add(besiegingUnits, BorderLayout.CENTER);
		
		JPanel idlePanel = new JPanel(new BorderLayout());
		idlePanel.setOpaque(false);
		JPanel titlePanel2 = new JPanel(new FlowLayout());
		titlePanel2.setOpaque(false);
		GameLabel idle = new GameLabel("Idle Armies");
		idle.resize(23f);
		idle.setBackground(translucent);
		idle.setOpaque(true);
		idle.setForeground(Color.WHITE);
		titlePanel2.add(idle);
		idlePanel.add(titlePanel2,BorderLayout.NORTH);
		JPanel idleUnits = new JPanel(new GridLayout(currPlayer.getControlledArmies().size()/2,2));
		idleUnits.setOpaque(false);
		for(int i = 0; i<currPlayer.getControlledArmies().size(); i++) {
			Army currArmy = currPlayer.getControlledArmies().get(i);
			if(currArmy.getCurrentStatus()==Status.IDLE) {
				displayAction = new Display(currArmy, currPlayer, currGame);
				ClickButton temp = new ClickButton(new ImageIcon("Assets//IdleArmies.png"));
				Hover h = new Hover(temp, "IdleArmies");
				temp.addMouseListener(h);
				temp.setContentAreaFilled(false);
				temp.setBorderPainted(false);
				temp.setText("At "+currArmy.getCurrentLocation());
				temp.setVerticalTextPosition(SwingConstants.BOTTOM);
				temp.setHorizontalTextPosition(SwingConstants.CENTER);
				temp.setToolTipText("Army idle at "+currArmy.getCurrentLocation());
				temp.addActionListener(displayAction);
				idleUnits.add(temp);
			}
		}
		idlePanel.add(idleUnits, BorderLayout.CENTER);
		
		armyView.add(marchingPanel);
		armyView.add(besiegingPanel);
		armyView.add(idlePanel);
	}

	private void setupMap(JPanel worldMap) {
		JPanel mapPanel = new JPanel(new GridLayout(5,5,5,5));
		mapPanel.setOpaque(false);
		worldMap.add(mapPanel, BorderLayout.CENTER);
		
		JPanel[][] panelsGrid = new JPanel[5][5];
		for(int i = 0; i<5; i++) {
			for(int j = 0; j<5; j++) {
				JPanel temp = new JPanel(new FlowLayout());
				temp.setOpaque(false);
				panelsGrid[i][j] = temp;
				mapPanel.add(temp);
			}
		}
		
		ClickButton cairo = new ClickButton( new ImageIcon("Assets//CairoIcon.png"));
		Hover hCairo = new Hover(cairo, "CairoIcon");
		cairo.addMouseListener(hCairo);
		cairo.setText("Cairo");
		cairo.setActionCommand("Cairo");
		cairo.setVerticalTextPosition(SwingConstants.BOTTOM);
		cairo.setContentAreaFilled(false);
		cairo.setBorderPainted(false);
		panelsGrid[4][2].add(cairo); 
		cairo.setEnabled(isControlled("Cairo"));
		cairo.addActionListener(this);
		cairo.setOpaque(false);
		cairo.setForeground(Color.BLACK);
		if(isControlled("Cairo")) {
			cairo.setToolTipText("Press here to go to Cairo");
		} else {
			cairo.setToolTipText("Cannot go there yet, we have to conquer them first");
		}
		
		ClickButton sparta = new ClickButton( new ImageIcon("Assets//SpartaIcon.png"));
		Hover hSparta = new Hover(sparta, "SpartaIcon");
		sparta.addMouseListener(hSparta);
		sparta.setText("Sparta");
		sparta.setActionCommand("Sparta");
		sparta.setVerticalTextPosition(SwingConstants.BOTTOM);
		sparta.setContentAreaFilled(false);
		sparta.setBorderPainted(false);
		panelsGrid[3][4].add(sparta); 
		sparta.setEnabled(isControlled("Sparta"));
		sparta.addActionListener(this);
		if(isControlled("Sparta")) {
			sparta.setToolTipText("Press here to go to Sparta");
		} else {
			sparta.setToolTipText("Cannot go there yet, we have to conquer them first");
		}
		
		ClickButton rome = new ClickButton( new ImageIcon("Assets//RomeIcon.png"));
		Hover hRome = new Hover(rome, "RomeIcon");
		rome.addMouseListener(hRome);
		rome.setText("Rome");
		rome.setActionCommand("Rome");
		rome.setVerticalTextPosition(SwingConstants.BOTTOM);
		rome.setContentAreaFilled(false);
		rome.setBorderPainted(false);
		panelsGrid[0][0].add(rome); 
		rome.setEnabled(isControlled("Rome"));
		rome.addActionListener(this);
		if(isControlled("Rome")) {
			rome.setToolTipText("Press here to go to Rome");
		} else {
			rome.setToolTipText("Cannot go there yet, we have to conquer them first");
		}
		
		ClickButton endTurn = new ClickButton( new ImageIcon("Assets//EndTurn.png"));
		Hover hEnd = new Hover(endTurn, "EndTurn");
		endTurn.addMouseListener(hEnd);
		endTurn.addActionListener(this);
		endTurn.setActionCommand("End Turn");
		endTurn.setContentAreaFilled(false);
		endTurn.setBorderPainted(false);
		endTurn.setToolTipText("Press to end this turn");
		panelsGrid[0][4].add(endTurn); 
	}

	public void update() {
		getContentPane().removeAll();
		initialize();
		validate();
		if(currGame.isGameOver()) {
			if(currPlayer.getControlledCities().size() == currGame.getAvailableCities().size()) {
				EndScreen end = new EndScreen("Victory",startCity, currPlayer.getName());
			} else {
				EndScreen end = new EndScreen("Defeat",startCity, currPlayer.getName());
			}
			stopMusic();
			dispose();
		}
	}
	
	public boolean isControlled(String name) {
		for(int i = 0; i<currPlayer.getControlledCities().size(); i++) {
			if(currPlayer.getControlledCities().get(i).getName().equals(name)) return true;
		}
		return false;
	}
	
	public City getCity(String name) {
		for(int i = 0; i<currGame.getAvailableCities().size(); i++) {
			if (currGame.getAvailableCities().get(i).getName().equals(name)) return currGame.getAvailableCities().get(i);
		}
		return null;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Cairo") || e.getActionCommand().equals("Sparta") || e.getActionCommand().equals("Rome")) {
			CityView city = new CityView(currPlayer,e.getActionCommand(),currGame);
		} else if(e.getActionCommand().equals("ViewMenu")) {
			MenuView menu = new MenuView(this);
		} else if(e.getActionCommand().equals("End Turn")) {
			currGame.endTurn();
			for(int i = 0; i<currGame.getAvailableCities().size(); i++) {
				City currCity = currGame.getAvailableCities().get(i);
				if(currGame.getAvailableCities().get(i).getTurnsUnderSiege() == 3) {
					UIManager.put("OptionPane.cancelButtonText", "Auto Resolve");
					UIManager.put("OptionPane.okButtonText", "Attack");
					int reply = JOptionPane.showConfirmDialog(this, "Your army is tired of waiting. It is time to attack "+currGame.getAvailableCities().get(i).getName(), "Get ready for war", JOptionPane.WARNING_MESSAGE);
					Army attacker = null;
					for(int j = 0; j<currPlayer.getControlledArmies().size(); j++) {
						Army currArmy = currPlayer.getControlledArmies().get(j);
						if(currArmy.getCurrentLocation().equals(currGame.getAvailableCities().get(i).getName())) {
							attacker = currArmy;
							break;
						}
					}
					UIManager.put("OptionPane.okButtonText", "OK");	
					if(reply == JOptionPane.YES_OPTION) {
						BattleView battle = new BattleView(attacker, currCity, currGame);
						battle.requestFocus();
					}
					else if (reply == JOptionPane.CANCEL_OPTION) {
						try {
							currGame.autoResolve(attacker, getCity(attacker.getCurrentLocation()).getDefendingArmy());
							String message = getCity(attacker.getCurrentLocation()).getDefendingArmy() == attacker ? "won":"lost";
							JOptionPane.showMessageDialog(this, "Emperor you have "+message+" the war", "War result", JOptionPane.INFORMATION_MESSAGE,  new ImageIcon("Assets//"+message+"War.jpg"));
						} catch (FriendlyFireException e1) {
							ErrorView error = new ErrorView(e1.getMessage());
						} break;
					}
				}
			}
		}
		update();
	}

	@Override
	public void windowGainedFocus(WindowEvent e) {
		setFocusable(true);
		update();
	}

	@Override
	public void windowLostFocus(WindowEvent e) {
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == 27) {
			MenuView menu = new MenuView(this);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
