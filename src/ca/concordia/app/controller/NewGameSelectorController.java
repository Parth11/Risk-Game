/**
 * 
 */
package ca.concordia.app.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import ca.concordia.app.model.GameMap;
import ca.concordia.app.service.CreateMapService;
import ca.concordia.app.util.MapValidationException;
import ca.concordia.app.view.NewGameSelectorView;

/**
 * @author harvi
 *
 */
public class NewGameSelectorController implements ActionListener,MouseListener {
	
	NewGameSelectorView new_game_selector;
	
	public NewGameSelectorController() {
		new_game_selector = new NewGameSelectorView();
		
		new_game_selector.setActionListener(this);
		new_game_selector.setMouseListener(this);
		new_game_selector.setVisible(true);
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
		if(e.getSource().equals(new_game_selector.browse_map)){
			int retVal = new_game_selector.choose_map.showOpenDialog(new_game_selector);
			
			if(retVal == JFileChooser.APPROVE_OPTION){
				File mapFile = new_game_selector.choose_map.getSelectedFile();
				try {
					CreateMapService.loadMap(mapFile);
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
			
			GameMap gameMap = GameMap.getInstance();
			new_game_selector.dispose();
			new MainController();
		}
		else if(e.getSource().equals(new_game_selector.cancel_button)){
			GameMap gameMap = GameMap.getInstance();
			new_game_selector.dispose();
			new MainController();
		}
	}

}
