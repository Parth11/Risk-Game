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
import ca.concordia.app.service.CreateMapService;
import ca.concordia.app.service.Game;
import ca.concordia.app.service.MyLogger;
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
					CreateMapService.getInstance().loadMap(mapFile);
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
			
			Game gameApi = Game.getInstance();
			
			//get logger
			MyLogger logger = MyLogger.getInstance(null);
			
			//using DB converter
//			DbConverter.convert(GameMap.getInstance(), lib.model.GameMap.getInstance());
//			DbConverter.print();
			
			//Loading converted Log File
			print(logger);
			logger.write("\n\nMAP FILE CONVERTED SUCCESSFULLY\n");
			
			
			init(numPlayers,gameApi, logger);
			
			new_game_selector.dispose();
			
			new_game_phase_selector = new NewGamePhaseController(numPlayers,logger);
		}
		else if(e.getSource().equals(new_game_selector.cancel_button)){
			new_game_selector.dispose();
			new MainController();
		}
	}
	
	private void init(int numberOfPlayers, Game gameApi, MyLogger logger) {
		
		
		logger.write("\nStartup Phase Started:-\n----------------------\n");
		gameApi.setPlayers(numberOfPlayers);
		
		// get players
		List<Player> players = gameApi.getPlayers();
		for(Player p : players) {
			String s = p.name + " - [ ";
			
			// get countries counquered by each player + no. of armies assigned
			List<Country> countries = gameApi.getCountriesConqueredBy(p);
			for(Country c : countries)
				s += "" + c.getCountryName() + "(" + c.getNoOfArmy() + "), ";
			
			s += "]\n";
			
			// write each info to logger files
			logger.write(s);
		}
		logger.write("\\nStartup Phase Ended:-\\n----------------------\\n");
		
	}
	
	private void print(MyLogger logger) {
		GameMap gameMap = GameMap.getInstance();
		for(Country c : gameMap.getCountries()){
			logger.write(c.getCountryName()+"( belongs to '"+c.getContinentName()+"') : [");
			
			if(gameMap.getTerritories().get(c)!=null)
				for(String s : gameMap.getTerritories().get(c)) 
					if(s!=null)
						logger.write(s+", ");
			
			logger.write("]\n");
		}
	}
	private void reinforcementPhase(int numberOfPlayers, Game gameApi, MyLogger logger) {
		
		for(int i=0; i<numberOfPlayers; i++) {
		
		Player player = gameApi.getCurrentTurnPlayer(); // whose turn to play ?

		logger.write("Current Player : \n" + player.getName());
		int putArmy = gameApi.getReinforcementArmyForPlayer(player);

		logger.write("Put Army : " + putArmy);
		// .. put all the armies wherever you want
		// for now we are putting armies in round robin
		
		//gameApi.addArmies(player, playerCountries, putArmy);

		player = gameApi.changeTurnToNextPlayer();
		
		logger.write("Next Player Turn :- " + player.getName());
	}
}

}
