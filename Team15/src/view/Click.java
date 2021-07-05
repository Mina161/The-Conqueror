package view;

import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

public class Click implements ActionListener{
	
	private ButtonType buttonType;
	
	public Click(ButtonType buttonType) {
		this.buttonType = buttonType;
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
	public void actionPerformed(ActionEvent e) {
		playClip("Click");
	}

}
