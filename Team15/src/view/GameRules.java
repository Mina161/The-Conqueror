package view;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URL;
import javax.swing.*;

public class GameRules extends JFrame implements Views, ActionListener {
	
	private final String[] labels = {
			"Target:",
			"The game's target is to conquer the world",
			"World Map:",
			"At the top you can see all the game's information (turns, gold and food)",
			"On the left you can see all the armies you control",
			"On the map itself, you can see the 3 cities: Cairo, Rome and Sparta",
			"To enter a city you control, simply click on it",
			"Cities:",
			"Each city has 5 empty land plots to build on",
			"Farm: Generates food for your armies",
			"Market: Generates money for you to spend",
			"Archery Range: To recruit Archers",
			"Barracks: To recruit Infantry",
			"Stable: To recruit Cavalry",
			"Creating armies:",
			"You can initiate armies by visiting your fortress and selecting a unit",
			"You can also relocate units to your army by clicking \"Relocate units\"",
			"Attacking:",
			"Once you reach a city you've targeted you will have 3 options",
			"Besiege: Lay siege on the city to starve them for 3 turns",
			"Attack: Manually attack your enemy by dragging and dropping units",
			"Auto-resolve: Battle will be randomly resolved until one army loses",
			"Winning:",
			"You win when you conquer the 3 cities in under 50 turns",
			"Tips:",
			"Build markets early on to avoid bankruptcy",
			"When you have more armies than you can feed, they will lose health"
	};
	
	public GameRules() {
		super();
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
		setLayout(new BorderLayout());
		initialize();
		addWindowFocusListener(this);
		this.validate();
	}
	
	@Override
	public void initialize() {
		JPanel title = new JPanel(new FlowLayout());
		title.setOpaque(false);
		GameLabel titleLab = new GameLabel("Welcome to Empire Building");
		titleLab.resize(40f);
		title.add(titleLab);
		getContentPane().add(title, BorderLayout.NORTH);
		
		JPanel textPanel = new JPanel(new GridLayout(27,1));
		textPanel.setOpaque(false);
		for(int i = 0; i<27; i++) {
			JPanel temp = new JPanel(new FlowLayout());
			temp.setOpaque(false);
			GameLabel t = new GameLabel(labels[i]);
			t.resize(20f);
			if(i == 0 || i==2 || i == 7 || i == 14 || i == 17 || i == 22 || i == 24)
				t.resize(25f);
			temp.add(t);
			textPanel.add(temp);
		}
		getContentPane().add(textPanel, BorderLayout.CENTER);
		
		JPanel buttonPanel = new JPanel(new FlowLayout());
		buttonPanel.setOpaque(false);
		ClickButton done = new ClickButton("Done");
		done.addActionListener(this);
		buttonPanel.add(done);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
	}

	@Override
	public void windowGainedFocus(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowLostFocus(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		dispose();
	}
}
