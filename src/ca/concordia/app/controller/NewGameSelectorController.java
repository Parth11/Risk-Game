
package ca.concordia.app.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import ca.concordia.app.model.GamePlayEvent.GameMode;
import ca.concordia.app.model.TournamentConfiguration;
import ca.concordia.app.service.GamePlayService;
import ca.concordia.app.service.MapService;
import ca.concordia.app.strategies.PlayerStrategy;
import ca.concordia.app.util.MapValidationException;
import ca.concordia.app.view.NewGameSelectorView;

/**
 * This is a controller class for New Game Selection.
 *
 * @author harvi
 */
public class NewGameSelectorController implements ActionListener, MouseListener {

	/** creates the object of the NewGameSelectorView. */
	NewGameSelectorView new_game_selector;

	/** create the object NewGamePhaseController */
	NewGamePhaseController new_game_phase_selector;

	TournamentController new_tournament_selector;

	/**
	 * Instantiates a new new game selector controller.
	 * @param noOfMaps number of maps
	 */
	public NewGameSelectorController(int noOfMaps) {
		new_game_selector = new NewGameSelectorView(noOfMaps);
		new_game_selector.setActionListener(this);
		new_game_selector.setMouseListener(this);
		new_game_selector.setVisible(true);

	}

	/**
	 * mouse clicked event
	 * 
	 * @param e mouse event
	 */
	@Override
	public void mouseClicked(MouseEvent e) {

	}

	/**
	 * mouse clicked pressed
	 * 
	 * @param e mouse event
	 */
	@Override
	public void mousePressed(MouseEvent e) {

	}

	/**
	 * mouse clicked released
	 * 
	 * @param e mouse event
	 */
	@Override
	public void mouseReleased(MouseEvent e) {

	}

	/**
	 * mouse clicked entered
	 * 
	 * @param e mouse event
	 */
	@Override
	public void mouseEntered(MouseEvent e) {

	}

	/**
	 * mouse clicked exited
	 * @param e mouse event
	 */
	@Override
	public void mouseExited(MouseEvent e) {

	}

	/**
	 * Calls the actionPerformed method of java during game start phase. Selects the
	 * map on which the game will be played.
	 * 
	 * @param e action event
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(new_game_selector.browse_map1)) {
			int retVal = new_game_selector.choose_map.showOpenDialog(new_game_selector);

			if (retVal == JFileChooser.APPROVE_OPTION) {
				File mapFile = new_game_selector.choose_map.getSelectedFile();
				if (GamePlayService.getInstance().getGameMode() == GameMode.SINGLE_GAME) {
					try {
						MapService.getInstance().loadMap(mapFile);
					} catch (MapValidationException e1) {
						JOptionPane.showMessageDialog(new_game_selector, e1.getMessage(), "Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
					JOptionPane.showMessageDialog(new_game_selector, "Map Loaded Successfully! Click Next to Play!",
							"Map Loaded", JOptionPane.INFORMATION_MESSAGE);
				} else {
					loadTournamentMap(mapFile);

				}

			}
		} else if (e.getSource().equals(new_game_selector.next_button)) {

			Integer numPlayers = (Integer) new_game_selector.num_players.getSelectedItem();

			List<? extends PlayerStrategy> strategies = new_game_selector.getStrategies();

			TournamentConfiguration.getInstance().setNum_players(numPlayers);
			TournamentConfiguration.getInstance().setStrategies(strategies);
			
			if (GamePlayService.getInstance().getGameMode() == GameMode.TOURNAMENT) {
				Integer numMaxTurn = Integer.parseInt(new_game_selector.no_of_max_turn.getText());
				Integer numGames = Integer.parseInt(new_game_selector.no_of_games.getText());
				if (numGames > 0) {
					TournamentConfiguration.getInstance().setNum_games(numGames);
					if (numMaxTurn >= 10 && numMaxTurn <= 50) {
						TournamentConfiguration.getInstance().setMax_turns(numMaxTurn);
						new_game_selector.dispose();
						new_tournament_selector = new TournamentController();
					} else {
						new_game_selector.max_turns.setText("Max Turn range of 10 to 50");
					}
				} else {
					new_game_selector.no_of_games.setText("No of Games should be >0");
				}
			} else {
				new_game_selector.dispose();
				new_game_phase_selector = new NewGamePhaseController(numPlayers, strategies);
			}

		} else if (e.getSource().equals(new_game_selector.cancel_button)) {
			new_game_selector.dispose();
			new MainController();
		} else if (e.getSource().equals(new_game_selector.browse_map2)) {
			int retVal = new_game_selector.choose_map.showOpenDialog(new_game_selector);

			if (retVal == JFileChooser.APPROVE_OPTION) {
				File mapFile = new_game_selector.choose_map.getSelectedFile();
				loadTournamentMap(mapFile);

			}
		} else if (e.getSource().equals(new_game_selector.browse_map3)) {
			int retVal = new_game_selector.choose_map.showOpenDialog(new_game_selector);

			if (retVal == JFileChooser.APPROVE_OPTION) {
				File mapFile = new_game_selector.choose_map.getSelectedFile();
				loadTournamentMap(mapFile);

			}
		} else if (e.getSource().equals(new_game_selector.browse_map4)) {
			int retVal = new_game_selector.choose_map.showOpenDialog(new_game_selector);

			if (retVal == JFileChooser.APPROVE_OPTION) {
				File mapFile = new_game_selector.choose_map.getSelectedFile();
				loadTournamentMap(mapFile);

			}
		} else if (e.getSource().equals(new_game_selector.browse_map5)) {
			int retVal = new_game_selector.choose_map.showOpenDialog(new_game_selector);

			if (retVal == JFileChooser.APPROVE_OPTION) {
				File mapFile = new_game_selector.choose_map.getSelectedFile();
				loadTournamentMap(mapFile);

			}
		}
	}

	private void loadTournamentMap(File mapFile) {
		try {
			GamePlayService.getInstance().addTournamentMap(mapFile);
			JOptionPane.showMessageDialog(new_game_selector, "Map Loaded Successfully!", "Map Loaded",
					JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(new_game_selector, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

	}

}
