package ca.concordia.app.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import ca.concordia.app.view.NewGamePlayView;

public class NewGamePhaseController implements ActionListener,MouseListener{
	
	NewGamePlayView new_game_phase_selector;
	
	public NewGamePhaseController() {
		// TODO Auto-generated constructor stub
		new_game_phase_selector = new NewGamePlayView();
		
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
		
		if(e.getSource().equals(new_game_phase_selector.btnNewButton)){
			
		}
		else if(e.getSource().equals(new_game_phase_selector.btnFortify)){
			
		}
		
	}

}
