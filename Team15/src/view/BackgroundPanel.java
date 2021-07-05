package view;

import java.awt.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

public class BackgroundPanel extends JPanel {
	private Image backgroundImage;
	
	public BackgroundPanel(String fileName) throws IOException {
	    backgroundImage = ImageIO.read(new File(fileName));
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backgroundImage, 0, 0, this);
	}
}
