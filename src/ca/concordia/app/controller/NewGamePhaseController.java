package ca.concordia.app.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import ca.concordia.app.service.CreateMapService;
import ca.concordia.app.view.NewGameFrame;
import ca.concordia.app.view.NewGameSelectorView;

public class NewGamePhaseController implements ActionListener,MouseListener{
	
	NewGameFrame new_game_phase_selector;
	
	public NewGamePhaseController() {
		// TODO Auto-generated constructor stub
		new_game_phase_selector = new NewGameFrame();
		
		new_game_phase_selector.setActionListener(this);
		new_game_phase_selector.setMouseListener(this);
		new_game_phase_selector.setVisible(true);
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getSource().equals(new_game_phase_selector.btnSubmit)){
			
		}
		else if(e.getSource().equals(new_game_phase_selector.btnFortify)){
			
		}
		
	}

}
