package view;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class Hover implements MouseListener{
	
	private JButton button;
	private Color prev;
	private String img;
	
	public Hover(JButton button, String path) {
		super();
		this.button = button;
		img = path;
	}
	
	public void setIcon(String path) {
		button.setIcon(new ImageIcon("Assets//"+path+".png"));
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
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		setIcon(img+"Bright");
		if(img.equals("Door")) {
			playClip("Open");
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		setIcon(img);
	}

}
