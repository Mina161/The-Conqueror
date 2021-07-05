package view;

import java.awt.*;
import java.io.*;
import javax.swing.*;

public class GameLabel extends JLabel {
	
	public GameLabel(String s){
		super(s);
		init();
	}
	
	public GameLabel(ImageIcon i){
		super(i);
		init();
	}
	
	public void init(){
		try {
			Font font = Font.createFont(Font.TRUETYPE_FONT, new BufferedInputStream(new FileInputStream("GrandFont.ttf")));
			setFont(font.deriveFont(12f));
		} catch (FontFormatException | IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public void resize(float f) {
		setFont(getFont().deriveFont(f));
	}
}
