package view;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.*;

public class ClickButton extends JButton implements MouseListener {
	
	private boolean sound = true;
	private Click clickListen = new Click(ButtonType.CLICK);
	
	public ClickButton() {
		super();
		super.addActionListener(clickListen);
	}

	public ClickButton(String string) {
		super(string);
		super.setBackground(new Color(209,168,87));
		Font font = null;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new BufferedInputStream(new FileInputStream("GrandFont.ttf")));
		} catch (FontFormatException | IOException e1) {
			e1.printStackTrace();
		}
		super.setFont(font.deriveFont(15f));
		super.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 2), BorderFactory.createLineBorder(new Color(209,168,87), 8)));
		super.addActionListener(clickListen);
		super.addMouseListener(this);
	}
	
	public ClickButton(String string, boolean sound) {
		super(string);
		this.sound = sound;
		super.setBackground(new Color(209,168,87));
		Font font = null;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new BufferedInputStream(new FileInputStream("GrandFont.ttf")));
		} catch (FontFormatException | IOException e1) {
			e1.printStackTrace();
		}
		super.setFont(font.deriveFont(15f));
		super.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 2), BorderFactory.createLineBorder(new Color(209,168,87), 8)));
		super.addMouseListener(this);
	}

	public ClickButton(ImageIcon imageIcon) {
		super(imageIcon);
		Font font = null;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new BufferedInputStream(new FileInputStream("GrandFont.ttf")));
		} catch (FontFormatException | IOException e1) {
			e1.printStackTrace();
		}
		super.setFont(font.deriveFont(15f));
	}
	
	public void addActionListener(ActionListener I) {
		if(sound) {
			super.removeActionListener(clickListen);
		}
		super.addActionListener(I);
		if(sound) {
			super.addActionListener(clickListen);
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		super.setBackground(new Color(209,168,87));
	}

	@Override
	public void mousePressed(MouseEvent e) {
		UIManager.put( "Button.select", new Color(209,168,87) );
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		super.setBackground(new Color(209,168,87));
		super.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.WHITE, 2), BorderFactory.createLineBorder(new Color(209,168,87), 8)));
	}

	@Override
	public void mouseExited(MouseEvent e) {
		super.setBackground(new Color(209,168,87));
		super.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 2), BorderFactory.createLineBorder(new Color(209,168,87), 8)));
	}
}
