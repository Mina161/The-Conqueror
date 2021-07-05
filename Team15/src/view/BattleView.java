package view;

import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.dnd.DnDConstants;
import java.awt.event.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import units.*;
import engine.*;
import exceptions.FriendlyFireException;

public class BattleView extends JFrame implements Views, ActionListener{
	private Army myArmy;
	private Army defendingArmy;
	private City defendingCity;
	private Game game;
	private ArrayList<String> log;
	
    public BattleView(Army myArmy,City defendingCity, Game game) {
    	this.myArmy = myArmy;
    	this.defendingCity = defendingCity;
    	defendingCity.setUnderSiege(false);
    	defendingCity.setTurnsUnderSiege(-1);
    	this.defendingArmy = defendingCity.getDefendingArmy();
    	this.game = game;
		setSize(1600,900);
		try {
			BackgroundPanel bg = new BackgroundPanel("Assets//"+defendingCity.getName()+"BG.png");
			bg.setOpaque(false);
			setContentPane(bg);
		} catch (IOException e) {
			
		}
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setLocationRelativeTo(null);
		setResizable(false);
		setUndecorated(true);
		setVisible(true);
		setLayout(new BorderLayout());
		addWindowFocusListener(this); 
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		log = new ArrayList<String>();
		log.add("Battle log:");
		initialize();
		this.validate();
    }
    
    @Override
	public void initialize() {
    	JPanel battle = new JPanel(new GridLayout(1,2));
    	battle.setOpaque(false);
    	
    	JButton actionButton = new JButton();
    	actionButton.addActionListener(this);
    	
    	JPanel myArmyPanel = new JPanel();
    	myArmyPanel.setLayout(new GridLayout(5, 2));
    	myArmyPanel.setSize(700, 750);
    	myArmyPanel.setOpaque(false);
		for(int i = 0; i<myArmy.getUnits().size(); i++) {
			Unit currUnit = myArmy.getUnits().get(i);
			double health = ((double)currUnit.getCurrentSoldierCount()/(double)currUnit.getMaxSoldierCount())*100.0;
			JButton temp = new JButton(new ImageIcon("Assets//"+getType(currUnit)+".gif"));
			temp.setToolTipText(currUnit.toString()+" Health "+health+"%");
			
			temp.addMouseMotionListener(new MouseAdapter() {
                 public void mouseDragged(MouseEvent e) {
                     JButton button = (JButton) e.getSource();
                     TransferHandler handle = button.getTransferHandler();
                     handle.exportAsDrag(button, e, TransferHandler.COPY);
                 }
            });
			
			temp.setContentAreaFilled(false);
			temp.setBorderPainted(false);
			temp.setTransferHandler(new AttackHandler(i+""));
			JButton healthBar = new JButton(currUnit.getLevel()+"");
			healthBar.setSize(new Dimension(80,10));
			if(health > 80.0) {
				healthBar.setBackground(Color.GREEN);
			} else if (health > 50) {
				healthBar.setBackground(Color.YELLOW);
			} else {
				healthBar.setBackground(Color.RED);
			}
			JPanel flow = new JPanel(new FlowLayout());
			flow.setOpaque(false);
			flow.add(temp);
			flow.add(healthBar);
			myArmyPanel.add(flow);
		}
		battle.add(myArmyPanel);
		
		JPanel defendingPanel = new JPanel();
		defendingPanel.setLayout(new GridLayout(5,5));
		defendingPanel.setOpaque(false);
		for(int i = 0; i<defendingArmy.getUnits().size(); i++) {
			Unit currUnit = defendingArmy.getUnits().get(i);
			double health = ((double)currUnit.getCurrentSoldierCount()/(double)currUnit.getMaxSoldierCount())*100.0;
			JLabel temp = new JLabel(new ImageIcon("Assets//Enemy"+getType(currUnit)+".gif"));
			temp.setToolTipText(currUnit.toString()+" Health: "+health);
			temp.setAlignmentX(CENTER_ALIGNMENT);
			temp.setOpaque(false);
			temp.setTransferHandler(new DefendHandler(i+"", myArmy, defendingArmy, log, actionButton));
			JButton healthBar = new JButton(currUnit.getLevel()+"");
			healthBar.setSize(new Dimension(10,80));
			if(health > 80.0) {
				healthBar.setBackground(Color.GREEN);
			} else if (health > 50) {
				healthBar.setBackground(Color.YELLOW);
			} else {
				healthBar.setBackground(Color.RED);
			}
			JPanel flow = new JPanel(new FlowLayout());
			flow.setOpaque(false);
			flow.add(temp);
			flow.add(healthBar);
			defendingPanel.add(flow);
		}
		battle.add(defendingPanel);
		
		JTextArea logText = new JTextArea(log.size(),1);
		for(int i = 0; i<log.size(); i++) {
			logText.setText(logText.getText()+"\n"+log.get(i));
		}
		Font logFont = null;
		try {
			logFont = Font.createFont(Font.TRUETYPE_FONT, new BufferedInputStream(new FileInputStream("LogFont.ttf")));
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		} 
		logText.setFont(logFont.deriveFont(15f));
		logText.setEditable(false);
		JScrollPane logPanel = new JScrollPane(logText);
		logText.setBackground(Color.BLACK);
		logText.setForeground(Color.WHITE);
		logPanel.setPreferredSize(new Dimension(900,150));
		
		getContentPane().add(battle, BorderLayout.CENTER);
		getContentPane().add(logPanel, BorderLayout.SOUTH);
	}
    
    public void update() {
    	getContentPane().removeAll();
		initialize();
		validate();
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
    
	public String getType(Unit u) {
		if(u instanceof Archer) {
			return "Archer";
		} else if(u instanceof Infantry) {
			return "Infantry";
		} else if(u instanceof Cavalry) {
			return "Cavalry";
		}
		return "";
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
		if(defendingArmy.getUnits().isEmpty()) {
			game.occupy(myArmy, defendingCity.getName());
			JOptionPane.showMessageDialog(this, "Emperor you have won this war!", "War result", JOptionPane.INFORMATION_MESSAGE,  new ImageIcon("Assets//wonWar.jpg"));
			dispose();
		} else if(myArmy.getUnits().isEmpty()) {
			game.getPlayer().getControlledArmies().remove(myArmy);
			JOptionPane.showMessageDialog(this, "Emperor you have lost this war!", "War result", JOptionPane.INFORMATION_MESSAGE,  new ImageIcon("Assets//lostWar.jpg"));
			dispose();
		} else {
			playClip("Attack");
			Unit unit1 = myArmy.getUnits().get((int) (Math.random() * myArmy.getUnits().size()));
			Unit unit2 = defendingArmy.getUnits().get((int) (Math.random() * defendingArmy.getUnits().size()));
			log.add("Enemy "+unit2.toString()+" attacked your "+unit1.toString()+" and dealt "+unit2.getDamage(unit1)+" damage");
			try {
				unit2.attack(unit1);
			} catch (FriendlyFireException e1) {
				ErrorView error = new ErrorView(e1.getMessage());
			}
			if(myArmy.getUnits().isEmpty()) {
				game.getPlayer().getControlledArmies().remove(myArmy);
				JOptionPane.showMessageDialog(this, "Emperor you have lost this war!", "War result", JOptionPane.INFORMATION_MESSAGE,  new ImageIcon("Assets//lostWar.jpg"));
				dispose();
			}
		}
		update();
	}
}

/*
 * Drag and drop handlers found on StackOverflow, we took part of the code and adapted it to our use
 * We made sure we understand the code to be able to implement correctly.
 */

class AttackHandler extends TransferHandler { 

    public static final DataFlavor SUPPORTED_DATE_FLAVOR = DataFlavor.stringFlavor;
    private String attackerIdx;

    public AttackHandler(String attackerIdx) {
        this.attackerIdx = attackerIdx;
    }

    public String getValue() {
        return attackerIdx;
    }

    @Override
    public int getSourceActions(JComponent c) {
        return DnDConstants.ACTION_COPY_OR_MOVE;
    }

    @Override
    protected Transferable createTransferable(JComponent c) { //To transfer the attacker unit's index to the import handler
        Transferable t = new StringSelection(getValue());
        return t;
    }

    @Override
    protected void exportDone(JComponent source, Transferable data, int action) {
        super.exportDone(source, data, action);
    }

}

class DefendHandler extends TransferHandler { //Import handler

    public static final DataFlavor SUPPORTED_DATE_FLAVOR = DataFlavor.stringFlavor;
    private String defenderIdx;
    private Army attackers;
    private Army defenders; 
    private ArrayList<String> log;
    private JButton actionButton;
    
    public DefendHandler(String string, Army attackers, Army defenders, ArrayList<String> log, JButton actionButton) {
    	defenderIdx = string;
    	this.attackers = attackers;
    	this.defenders = defenders;
    	this.log = log;
    	this.actionButton = actionButton;
    }

    @Override
    public boolean canImport(TransferHandler.TransferSupport support) {
        return support.isDataFlavorSupported(SUPPORTED_DATE_FLAVOR);
    }

    @Override
    public boolean importData(TransferHandler.TransferSupport support) {
        boolean accept = false;
        if (canImport(support)) {
            try {
                Transferable t = support.getTransferable();
                Object value = t.getTransferData(SUPPORTED_DATE_FLAVOR);
                if (value instanceof String) {
                    Component component = support.getComponent();
                    if (component instanceof JLabel) {
                    	Unit attacker = attackers.getUnits().get(Integer.parseInt((String) value));
                    	Unit defender = defenders.getUnits().get(Integer.parseInt(defenderIdx));
                    	log.add("Your "+attacker.toString()+" attacked the enemy "+defender.toString()+" and dealt "+attacker.getDamage(defender)+" damage");
                        attacker.attack(defender);
                        actionButton.doClick(); //To refresh the battle view and initiate enemy defense;
                        accept = true;
                    }
                }
            } catch (Exception exp) {
                exp.printStackTrace();
            }
        }
        return accept;
    }
}