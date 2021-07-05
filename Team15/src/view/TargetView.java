package view;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import engine.*;
import units.*;
import javax.swing.*;

public class TargetView extends JFrame implements ActionListener, Views {

	private Game game;
	private Army attacker;
	
	public TargetView(Game game, Army attacker) {
		super();
		this.game = game;
		this.attacker = attacker;
		setSize(900,500);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
		requestFocus();
		setTitle("Target City");
		setLayout(new BorderLayout());
		ImageIcon img = new ImageIcon("Assets//Target.jpg");
		setIconImage(img.getImage());
		
		try {
			BackgroundPanel bg = new BackgroundPanel("Assets//TargetCity.jpg");
			setContentPane(bg);
		} catch (IOException e) {
			
		}
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		initialize();
		addWindowFocusListener(this);
		this.validate();
	}
	
	public void initialize() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setOpaque(false);
		JPanel titlePanel = new JPanel(new FlowLayout());
		titlePanel.setOpaque(false);
		GameLabel title = new GameLabel("Select a target city");
		title.resize(24f);
		title.setOpaque(true);
		title.setBackground(new Color(0f,0f,0f,0.5f));
		title.setForeground(Color.WHITE);
		titlePanel.add(title);
		mainPanel.add(titlePanel,BorderLayout.NORTH);
		JPanel citiesPanel = new JPanel(new FlowLayout());
		citiesPanel.setOpaque(false);
		Player p = game.getPlayer();
		for(int i = 0; i<game.getAvailableCities().size(); i++) {
			City currCity = game.getAvailableCities().get(i);
			if(!p.getControlledCities().contains(currCity)) {
				ClickButton temp = new ClickButton(new ImageIcon("Assets//"+currCity.getName()+"Icon.png"));
				Hover h = new Hover(temp, currCity.getName()+"Icon");
				temp.addMouseListener(h);
				temp.setText(currCity.getName());
				temp.setToolTipText("Distance from current location: "+game.getDistanceBetween(attacker.getCurrentLocation(), currCity.getName()));
				temp.setForeground(Color.WHITE);
				temp.setActionCommand(currCity.getName());
				temp.addActionListener(this);
				temp.setPreferredSize(new Dimension(300,200));
				temp.setContentAreaFilled(false);
				temp.setBorderPainted(false);
				citiesPanel.add(temp);
			}
		}
		mainPanel.add(citiesPanel,BorderLayout.CENTER);
		
		getContentPane().add(mainPanel,BorderLayout.CENTER);
		
	}
	
	public void update() {
		getContentPane().removeAll();
		initialize();
		validate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		game.targetCity(attacker, e.getActionCommand());
		dispose();
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
