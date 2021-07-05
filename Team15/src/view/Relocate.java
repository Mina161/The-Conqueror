package view;

import java.awt.event.ActionEvent;
import units.*;
import javax.swing.*;

import exceptions.MaxCapacityException;

public class Relocate extends AbstractAction {
	
	private Army army;
	private Unit unit;
	
	public Relocate(Army army, Unit unit) {
		this.army = army;
		this.unit = unit;
	}
	
	public void actionPerformed(ActionEvent e) {
		try {
			army.relocateUnit(unit);
		} catch (MaxCapacityException e1) {
			ErrorView error = new ErrorView(e1.getMessage());
		}
	}

	

}
