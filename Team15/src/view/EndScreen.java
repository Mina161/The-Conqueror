package view;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.Border;

import engine.*;
import units.*;

public class EndScreen extends JFrame implements Views, ActionListener{
	
	private String cityGod;
	private Font endFont;
	private Clip music;
			
	public EndScreen(String outcome, String cityName, String playerName) {
		super();
		setSize(1600,900);
		setExtendedState(JFrame.MAXIMIZED_BOTH); //Full Screen
		setLocationRelativeTo(null); //Centered to screen
		setResizable(false); //Final size
		setUndecorated(true);
		setVisible(true);
		switch(cityName) {
		case "Cairo": this.cityGod = "Ra";break;
		case "Rome": this.cityGod = "Jupiter";break;
		case "Sparta": this.cityGod = "Zeus";break;
		}
		
		JPanel topPanel = new JPanel(new GridLayout(2,1));
		topPanel.setOpaque(false);
		JPanel bottomPanel = new JPanel(new GridLayout(2,1));
		bottomPanel.setOpaque(false);
		
		if(outcome.equals("Victory")) {
			playClip("Victory");
			try {
				BackgroundPanel bg = new BackgroundPanel("Assets//Victory.jpg");
				setContentPane(bg);
			} catch (IOException e) {
				
			}
			JPanel resultPanel = new JPanel();
			resultPanel.setOpaque(false);
			GameLabel result = new GameLabel(cityGod+" smiles upon you, Mighty "+playerName);
			result.setOpaque(true);
			result.setBackground(new Color(4, 18, 89, 200));
			result.setForeground(new Color(194, 158, 16));
			result.resize(42f);
			resultPanel.add(result);
			topPanel.add(resultPanel);
			
			JPanel messages = new JPanel(new GridLayout(2,1));
			messages.setBorder(BorderFactory.createLineBorder(new Color(194, 158, 16), 5, true));
			messages.setBackground(new Color(4, 18, 89, 200));
			
			JPanel message1Panel = new JPanel(new FlowLayout());
			message1Panel.setOpaque(false);
			GameLabel message1 = new GameLabel("You are victorious, you rule the world with strength and courage");
			message1.resize(24f);
			message1.setForeground(new Color(194, 158, 16));
			message1Panel.add(message1);
			
			JPanel message2Panel = new JPanel(new FlowLayout());
			message2Panel.setOpaque(false);
			GameLabel message2 = new GameLabel("Do you want to try again with a new city, or leave to celebrate?");
			message2.resize(24f);
			message2.setForeground(new Color(194, 158, 16));
			message2Panel.add(message2);
			
			messages.add(message1Panel);
			messages.add(message2Panel);
			bottomPanel.add(messages);
		} else {
			playClip("Defeat");
			try {
				BackgroundPanel bg = new BackgroundPanel("Assets//Defeat.jpg");
				setContentPane(bg);
			} catch (IOException e) {
				
			}
			JPanel resultPanel = new JPanel();
			resultPanel.setOpaque(false);
			GameLabel result = new GameLabel(cityGod+" frowns upon you, "+playerName+" the Frail");
			result.setOpaque(true);
			result.setBackground(new Color(0, 0, 0, 200));
			result.setForeground(Color.WHITE);
			result.resize(42f);
			resultPanel.add(result);
			topPanel.add(resultPanel);
			
			JPanel messages = new JPanel(new GridLayout(2,1));
			messages.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 5, true));
			messages.setBackground(new Color(0, 0, 0, 200));
			
			JPanel message1Panel = new JPanel(new FlowLayout());
			message1Panel.setOpaque(false);
			GameLabel message1 = new GameLabel("Your dreams of conquering the world haven't been fulfilled");
			message1.resize(24f);
			message1.setForeground(Color.WHITE);
			message1Panel.add(message1);
			
			JPanel message2Panel = new JPanel(new FlowLayout());
			message2Panel.setOpaque(false);
			GameLabel message2 = new GameLabel("Do you want to start over, or let the world forget your name?");
			message2.resize(24f);
			message2.setForeground(Color.WHITE);
			message2Panel.add(message2);
			
			messages.add(message1Panel);
			messages.add(message2Panel);
			bottomPanel.add(messages);
		}
		setLayout(new BorderLayout());
		JPanel buttonPanel = new JPanel(new FlowLayout());
		buttonPanel.setOpaque(false);
		ClickButton endGame = new ClickButton("Exit the game");
		endGame.addActionListener(this);
		buttonPanel.add(endGame);
		ClickButton restartGame = new ClickButton("Try again with a different city");
		restartGame.addActionListener(this);
		buttonPanel.add(restartGame);
		bottomPanel.add(buttonPanel);
		
		getContentPane().add(topPanel,BorderLayout.NORTH);
		getContentPane().add(bottomPanel,BorderLayout.SOUTH);
		this.validate();
	}
	
	public void playClip(String name) {
		try {
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File("Assets//"+name+".wav"));
	        music = AudioSystem.getClip();
	        music.open(audioIn);
	        music.start();
		} catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
	        e.printStackTrace();
		}
	}
	
	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
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
		music.stop();
		if(e.getActionCommand().equals("Exit the game")) {
			System.exit(0);
		} else {
			Launcher newGame = new Launcher();
			dispose();
		}
	}


	
}
