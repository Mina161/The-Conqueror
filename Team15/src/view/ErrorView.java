package view;

import java.awt.event.*;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.awt.*;
import javax.swing.*;

public class ErrorView extends JFrame implements Views, ActionListener {

	private String errorMessage;
	
	public ErrorView(String errorMessage) {
		super();
		this.errorMessage = errorMessage;
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
		setLayout(new GridLayout(3,1));
		JPanel placeHolder = new JPanel();
		placeHolder.setOpaque(false);
		add(placeHolder);
		initialize();
		addWindowFocusListener(this);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	@Override
	public void initialize() {
		JPanel titlePanel = new JPanel(new BorderLayout());
		titlePanel.setOpaque(false);
		String[] message = errorMessage.split(",");
		if(message.length > 0) {
			JPanel panel1 = new JPanel(new FlowLayout());
			panel1.setOpaque(false);
			GameLabel error1 = new GameLabel(message[0]);
			error1.resize(28f);
			panel1.add(error1);
			titlePanel.add(panel1, BorderLayout.NORTH);
		}
		if(message.length > 1) {
			JPanel panel2 = new JPanel(new FlowLayout());
			panel2.setOpaque(false);
			GameLabel error2 = new GameLabel(message[1]);
			error2.resize(28f);
			titlePanel.add(error2);
			panel2.add(error2);
			titlePanel.add(panel2, BorderLayout.CENTER);
		} else {
			JPanel panel1 = new JPanel(new FlowLayout());
			panel1.setOpaque(false);
			GameLabel error1 = new GameLabel(errorMessage);
			error1.resize(28f);
			titlePanel.add(error1);
			panel1.add(error1);
			titlePanel.add(panel1, BorderLayout.NORTH);
		}
		getContentPane().add(titlePanel);
		
		JPanel buttonPanel = new JPanel(new FlowLayout());
		buttonPanel.setOpaque(false);
		ClickButton ok = new ClickButton("Understood");
		ok.addActionListener(this);
		buttonPanel.add(ok);
		getContentPane().add(buttonPanel);
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
		dispose();
	}

}
