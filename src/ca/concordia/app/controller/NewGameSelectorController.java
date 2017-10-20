
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
 * The Class NewGameSelectorController.
 *
 * @author harvi
 */
public class NewGameSelectorController implements ActionListener,MouseListener {
	
	/** creates the object of the NewGameSelectorView. */
	NewGameSelectorView new_game_selector;
	
	/** create the object NewGamePhaseController */
	NewGamePhaseController new_game_phase_selector;
	
	/**
	 * Instantiates a new new game selector controller.
	 */
	public NewGameSelectorController() {
		new_game_selector = new NewGameSelectorView();
		new_game_selector.setActionListener(this);
		new_game_selector.setMouseListener(this);
		new_game_selector.setVisible(true);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	/**
	 * Calls the actionPerformed method of java during game start phase.
	 * @param e
	 */
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
				}
				JOptionPane.showMessageDialog(new_game_selector, "Map Loaded Successfully! Click Next to Play!","Map Loaded",JOptionPane.INFORMATION_MESSAGE);
			}
		}
		else if(e.getSource().equals(new_game_selector.next_button)){
			
			int numPlayers = Integer.parseInt(new_game_selector.num_players.getText());
			
			if(numPlayers<2 || numPlayers>6){
				JOptionPane.showMessageDialog(new_game_selector, "Number of players must be between 2 and 6","Invalid",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			
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
