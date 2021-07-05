package view;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

@SuppressWarnings("serial")
public class Launcher extends JFrame implements ActionListener{
	
	private JTextField userName;
	private JComboBox<String> city;
	private ClickButton submit;
	
	public Launcher() {
		super();
		setSize(900,500);
		setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setLayout(new BorderLayout());
		setVisible(true);
		setTitle("Game Launcher");
		ImageIcon img = new ImageIcon("Assets//LauncherIcon.jpg");
		setIconImage(img.getImage());
		initialize();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.validate();
	}
	
	public void initialize() {
		JPanel worldMap = null;
		try {
			worldMap =  new BackgroundPanel("Assets//Launcher.jpg");
		} catch (IOException e) {
			
		}
		getContentPane().add(worldMap,BorderLayout.CENTER);
		
		
		String[] c = {"Select a city","Cairo","Rome","Sparta"};
		userName = new JTextField("Player Name");
		userName.setToolTipText("Write your name here, your highness");
		userName.addFocusListener(new FocusListener() { //This snippet is to make the JTextField have a placeholder
		    @Override
		    public void focusGained(FocusEvent e) {
		        if (userName.getText().equals("Player Name")) {
		        	userName.setText("");
		        	userName.setForeground(Color.BLACK);
		        }
		    }
		    @Override
		    public void focusLost(FocusEvent e) {
		        if (userName.getText().isEmpty()) {
		        	userName.setForeground(Color.GRAY);
		        	userName.setText("Player Name");
		        }
		    }
		    });
		city = new JComboBox<String>(c);
		city.setToolTipText("Select your starting city, your highness");
		submit = new ClickButton(new ImageIcon("Assets//StartButton.png"));
		Hover h1 = new Hover(submit,"StartButton");
		submit.addMouseListener(h1);
		submit.setContentAreaFilled(false);
		submit.setBorderPainted(false);
		submit.setActionCommand("Start Game");
		submit.addActionListener(this);
		GameLabel title = new GameLabel("Welcome to Empire Building");
		title.setOpaque(true);
		title.setBackground(new Color(255,255,255,150));
		title.resize(42f);
		
		JPanel TP = new JPanel(new FlowLayout());
		TP.setOpaque(false);
		worldMap.add(TP, BorderLayout.CENTER);
		
		TP.add(title); 
	
		JPanel sq = new JPanel(new GridLayout(5,5,5,5));
		sq.setOpaque(false);
		worldMap.add(sq, BorderLayout.CENTER);
		JPanel[][] panelsGrid = new JPanel[5][5];
		for(int i = 0; i<5; i++) {
			for(int j = 0; j<5; j++) {
				JPanel temp = new JPanel(new FlowLayout());
				temp.setOpaque(false);
				panelsGrid[i][j] = temp;
				sq.add(temp);
			}
		}
		
		ClickButton rules = new ClickButton(new ImageIcon("Assets//Info.png"));
		Hover h2 = new Hover(rules,"Info");
		rules.addMouseListener(h2);
		rules.setContentAreaFilled(false);
		rules.setBorderPainted(false);
		rules.addActionListener(this);
		
		panelsGrid[1][0].add(userName); 
		panelsGrid[2][0].add(city); 
		panelsGrid[3][0].add(submit);
		panelsGrid[4][0].add(rules);
		
		getContentPane().add(worldMap,BorderLayout.CENTER);
	}
	


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Start Game")) {
			try {
				if(userName.getText().equals("Player Name")) {
					JOptionPane.showMessageDialog(this, "Please enter your name","Name Required", JOptionPane.WARNING_MESSAGE);
				} else if(city.getSelectedItem().equals("Select a city")) {
					JOptionPane.showMessageDialog(this, "Please choose a starting city","City Required", JOptionPane.WARNING_MESSAGE);
				} else {
					GameView newView = new GameView((String)userName.getText(), (String)city.getSelectedItem());
					dispose();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} else {
			GameRules g = new GameRules();
		}
	}
	
	
}
