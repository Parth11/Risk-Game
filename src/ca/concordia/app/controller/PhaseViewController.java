/**
 * 
 */
package ca.concordia.app.controller;

import java.util.Observable;
import java.util.Observer;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import ca.concordia.app.model.Player;
import ca.concordia.app.service.GamePlayService;
import ca.concordia.app.view.PhaseView;

/**
 * @author harvi
 *
 */
public class PhaseViewController implements Observer{

	private static PhaseViewController instance;
	
	private String[] column_names = {"Country","Armies","Player"};
	
	PhaseView phase_view;
	
	private PhaseViewController() {

		phase_view = new PhaseView();
		phase_view.setVisible(true);
		
	}
	
	public static PhaseViewController getInstance(){
		if(instance==null){
			instance = new PhaseViewController();
		}
		return instance;
	}
	
	@Override
	public void update(Observable o, Object arg) {

		Player currentPlayer = (Player) o;
		
		phase_view.player_name.setText(currentPlayer.getName());
		phase_view.player_phase.setText(currentPlayer.game_phase.name());
		phase_view.player_armies.setText(String.valueOf(currentPlayer.getTotalArmies()));
		
		Object[][] gamePlayState = GamePlayService.getInstance().getGamePlayState();
		
		DefaultTableModel dataModel = new DefaultTableModel(gamePlayState, column_names);

		phase_view.conquest_table.setModel(dataModel);
	
	}

}
