 /**
 * 
 */
package ca.concordia.app.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import ca.concordia.app.model.Country;
import ca.concordia.app.model.GameMap;
import ca.concordia.app.model.Player;
import ca.concordia.app.service.MapService;
import ca.concordia.app.service.GamePlayService;
import ca.concordia.app.service.ConsoleLoggerService;
import ca.concordia.app.util.MapValidationException;
import ca.concordia.app.view.NewGameSelectorView;

/**
 * @author harvi
 *
 */
public class NewGameSelectorController implements ActionListener,MouseListener {
	
	NewGameSelectorView new_game_selector;
	NewGamePhaseController new_game_phase_selector;
	
	public NewGameSelectorController() {
		new_game_selector = new NewGameSelectorView();
		new_game_selector.setActionListener(this);
		new_game_selector.setMouseListener(this);
		new_game_selector.setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	 public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(new_game_selector.browse_map)){
			int retVal = new_game_selector.choose_map.showOpenDialog(new_game_selector);
			
			if(retVal == JFileChooser.APPROVE_OPTION){
				File mapFile = new_game_selector.choose_map.getSelectedFile();

				try {
					MapService.getInstance().loadMap(mapFile);
				} catch (MapValidationException e1) {
					JOptionPane.showMessageDialog(new_game_selector, e1.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
					return;
				}
				JOptionPane.showMessageDialog(new_game_selector, "Map Loaded Successfully! Click Next to Play!","Map Loaded",JOptionPane.INFORMATION_MESSAGE);
			}
		}
		else if(e.getSource().equals(new_game_selector.next_button)){
			
			Integer numPlayers = (Integer) new_game_selector.num_players.getSelectedItem();
			
			GamePlayService.getInstance().doStartupPhase(numPlayers);
			
			new_game_selector.dispose();
			
			new_game_phase_selector = new NewGamePhaseController();
		}
		else if(e.getSource().equals(new_game_selector.cancel_button)){
			new_game_selector.dispose();
			new MainController();
		}
	}
	
	
	

}
