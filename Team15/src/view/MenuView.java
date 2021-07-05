package view;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

public class MenuView extends JFrame implements Views, ActionListener{

	private GameView mainWindow;
	private Font mainFont = new Font("Serif",Font.PLAIN,24);
	
	public MenuView(GameView mainWindow) {
		super();
		this.mainWindow = mainWindow;
		setSize(1600,900);
		setUndecorated(true);
		setLocationRelativeTo(null); 
		setResizable(false); 
		setVisible(true);
		try {
			BackgroundPanel bg = new BackgroundPanel("Assets//ErrorBg.png");
			bg.setOpaque(false);
			setContentPane(bg);
			setBackground(new Color(0f,0f,0f,0.1f));
		} catch (IOException e) {
			
		}
		requestFocus();
		setLayout(new GridLayout(3,1));
		initialize();
		addWindowFocusListener(this);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		addWindowFocusListener(this);
		this.validate();
	}
	
	@Override
	public void initialize() {
		JPanel placeHolder = new JPanel();
		placeHolder.setOpaque(false);
		add(placeHolder);
		
		JPanel titlePanel = new JPanel(new FlowLayout());
		titlePanel.setOpaque(false);
		GameLabel title = new GameLabel("Game Paused");
		title.resize(40f);
		titlePanel.add(title);
		getContentPane().add(titlePanel, BorderLayout.NORTH);
		
		JPanel buttonPanel = new JPanel(new FlowLayout());
		buttonPanel.setOpaque(false);
		ClickButton resume = new ClickButton("Resume");
		resume.addActionListener(this);
		buttonPanel.add(resume);
		
		ClickButton gameRules = new ClickButton("Game Rules");
		gameRules.addActionListener(this);
		buttonPanel.add(gameRules);
		
	    ClickButton exit = new ClickButton("Exit Game"); 
	    exit.setToolTipText("Exit the game completely");
	    exit.setActionCommand("Exit Game");
	    exit.addActionListener(this);
	    buttonPanel.add(exit);
		
		getContentPane().add(buttonPanel, BorderLayout.CENTER);
	}
	
	public void update() {
		getContentPane().removeAll();
		initialize();
		validate();
	}
	
	@Override
	public void windowGainedFocus(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowLostFocus(WindowEvent e) {
		requestFocus();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Resume")) {
			dispose();
		} else if(e.getActionCommand().equals("Game Rules")) {
			GameRules g = new GameRules();
			dispose();
		} else if(e.getActionCommand().equals("Exit Game")) {
			System.exit(0);
		}
		update();
	}

}
