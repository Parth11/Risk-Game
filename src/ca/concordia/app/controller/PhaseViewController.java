/**
 * 
 */
package ca.concordia.app.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.table.DefaultTableModel;

import ca.concordia.app.model.Card;
import ca.concordia.app.model.GameMap;
import ca.concordia.app.model.GamePlayEvent;
import ca.concordia.app.model.Player;
import ca.concordia.app.model.SavedGame;
import ca.concordia.app.service.GamePlayService;
import ca.concordia.app.util.GameConstants;
import ca.concordia.app.view.PhaseView;

/**
 * Shows a log of which phase is being played by which player.
 * @author harvi
 *
 */
public class PhaseViewController implements Observer,ActionListener{

	private static PhaseViewController instance;
	
	private String[] column_names = {"Country","Armies","Player"};
	
	PhaseView phase_view;
	
	private PhaseViewController() {

		phase_view = new PhaseView();
		phase_view.setActionListener(this);
		phase_view.setVisible(true);
		
	}
	
	/**
	 * Used for applying Singleton pattern
	 * @return instance
	 */
	public static PhaseViewController getInstance(){
		if(instance==null){
			instance = new PhaseViewController();
		}
		return instance;
	}
	
	/**
	 * An in-build java method of Observer class which publishes event whenever the update is notified
	 * @param o
	 * @param arg 
	 */
	@Override
	public void update(Observable o, Object arg) {

		Player currentPlayer = (Player) o;
		
		phase_view.clearFields();
		
		phase_view.repaint();
		
		phase_view.player_name.setText(currentPlayer.getName());
		phase_view.player_phase.setText(currentPlayer.game_phase.name());
		
		Object[][] gamePlayState = GamePlayService.getInstance().getGamePlayState();
		
		DefaultTableModel dataModel = new DefaultTableModel(gamePlayState, column_names);

		phase_view.conquest_table.setModel(dataModel);
		
		phase_view.plotDominationView(gamePlayState);
		
		GamePlayEvent publishedEvent = currentPlayer.event_log.get(currentPlayer.event_log.size()-1);
	
		Map<String,Object> eventPayload = publishedEvent.getEvent_payload();
		int a=0,c=0,i = 0;
		for(Card card : currentPlayer.getCards()){
			switch(card.getCardType()){
			case GameConstants.ARTILLERY:
				a++;
				break;
			case GameConstants.CAVALRY:
				c++;
				break;
			case GameConstants.INFANTRY:
				i++;
				break;
			}
		}
		phase_view.num_artillery.setText(String.valueOf(a));
		phase_view.num_cavalry.setText(String.valueOf(c));
		phase_view.num_infantry.setText(String.valueOf(i));
		
		switch(publishedEvent.getEvent_type()){
		case ATTACK_COUNTRY:
			phase_view.attacking_country.setText(eventPayload.get("attackingCountry").toString());
			phase_view.attacked_country.setText(eventPayload.get("defendingCountry").toString());
			phase_view.attack_throws.setText(eventPayload.get("attackThrows").toString());
			phase_view.defence_throws.setText(eventPayload.get("defenceThrows").toString());
			phase_view.win_loss.setText(eventPayload.get("attackWin").toString());
			break;
		case ATTACK_CAPTURE:
			break;
		case FORTIFY_COUNTRY:
			phase_view.fortifying_country.setText(eventPayload.get("fromCountry").toString());
			phase_view.fortified_country.setText(eventPayload.get("toCountry").toString());
			phase_view.armies_moved.setText(eventPayload.get("armies").toString());
			break;
		case REFINFORCE_COUNTRY:
			phase_view.reinforced_country.setText(eventPayload.get("reinforcedCountry").toString());
			phase_view.reinforce_armies.setText(eventPayload.get("reinforceArmy").toString());
			phase_view.player_armies.setText(String.valueOf(currentPlayer.getTotalArmies()));
			break;
		case REINFORCE_ARMY_ALLOCATION:
			phase_view.player_armies.setText(String.valueOf(currentPlayer.getTotalArmies()));
			break;
		case START_ARMY_COUNTRY:
			break;
		case START_ARMY_ALLOCATION:
			break;
		case START_COUNTRY:
			break;
		case CARD_WIN:
			break;
		case CARD_EXCHANGE:
			phase_view.armies_exchanged.setText(String.valueOf(eventPayload.get("armies").toString()));
			break;
		default:
			break;
		
		}
		phase_view.revalidate();
		phase_view.repaint();
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(phase_view.btn_save_game)){
			SavedGame savedGame = new SavedGame();
			savedGame.saveThisGame();
		}
		
	}

}
