package ca.concordia.app.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;

import ca.concordia.app.model.Country;
import ca.concordia.app.model.GamePlayEvent;
import ca.concordia.app.model.Player;
import ca.concordia.app.model.GamePlayEvent.EventType;
import ca.concordia.app.service.GamePlayService;
import ca.concordia.app.util.GamePhase;
import ca.concordia.app.view.FortificationInputView;
import ca.concordia.app.view.GameLoggerView;
import ca.concordia.app.view.GamePlayView;
import ca.concordia.app.view.ReinforcementInputView;


/**
 * The Class NewGamePhaseController manages the starting of the game.
 * 
 * @author Parth
 */
public class NewGamePhaseController implements ActionListener, MouseListener {

	GameLoggerView game_logger_view;

	GamePlayView game_play_view;
	
	GamePlayService game_play_service;

	Player current_player;
	
	ReinforcementInputView reinforcement_view;
	
	Integer reinforcement_armies;
	
	FortificationInputView fortification_view;
	
	Country from_country;
	
	Country to_country;
	
	
	
	
	public NewGamePhaseController(Integer numPlayers) {
		game_logger_view = new GameLoggerView();
		game_play_view = new GamePlayView(numPlayers);
		game_play_service = GamePlayService.getInstance();
		init(numPlayers);

	}
	
	/**
	 * Initializes the start
	 */

	private void init(Integer numPlayers) {
		game_play_service.doStartupPhase(numPlayers,game_logger_view);
		current_player = game_play_service.getPlayers().get(0);
		reinforcement_armies = game_play_service.getReinforcementArmyForPlayer(current_player);
		
		
		HashMap<String, Object> eventPayload = new HashMap<>();
		eventPayload.put("reinforcementArmies", reinforcement_armies);
		GamePlayEvent gpe = new GamePlayEvent(EventType.REINFORCE_ARMY_ALLOCATION, eventPayload);

		current_player.publishGamePlayEvent(gpe);

		
		reinforcePlayer();
		//GamePlayService.getInstance().doPlayGame(game_logger_view);
	}


	private void reinforcePlayer(){
		current_player.setCurrentPhase(GamePhase.REINFORCEMENT);
		current_player.setTotalArmies(reinforcement_armies);
		reinforcement_view = new ReinforcementInputView(game_play_service.getCountriesConqueredBy(current_player),reinforcement_armies);
		reinforcement_view.setActionListener(this);
	}
	
	private void initiateAttack(){
		current_player.doAttack();
	}
	
	private void fortifyPlayer(){
		
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

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(reinforcement_view.btn_submit)){
			Country c = (Country) reinforcement_view.country_list.getSelectedItem();
			int armiesToReinforce = (int) reinforcement_view.armies_list.getSelectedItem();
			reinforcement_view.dispose();
			reinforcement_armies -= armiesToReinforce;
			current_player.doReinforcement(c,armiesToReinforce);
			if(reinforcement_armies!=0){
				reinforcePlayer();
			}
			else{
				fortifyPlayer();
			}
		}
	}

}
