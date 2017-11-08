package ca.concordia.app.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicIconFactory;

import ca.concordia.app.model.Card;
import ca.concordia.app.model.Country;
import ca.concordia.app.model.DiceRoller;
import ca.concordia.app.model.GamePlayEvent;
import ca.concordia.app.model.GamePlayEvent.EventType;
import ca.concordia.app.model.Player;
import ca.concordia.app.service.GamePlayService;
import ca.concordia.app.util.GamePhase;
import ca.concordia.app.view.AttackInputView;
import ca.concordia.app.view.FortificationInputView;
import ca.concordia.app.view.GameLoggerView;
import ca.concordia.app.view.GamePlayView;
import ca.concordia.app.view.ReinforcementInputView;


/**
 * The Class NewGamePhaseController manages the starting of the game.
 * 
 * @author harvi
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
	
	AttackInputView attack_view;
	
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
		current_player = game_play_service.getCurrentTurnPlayer();
				
		prepareToReinforce();
	}

	private void prepareToReinforce(){
		reinforcement_armies = game_play_service.getReinforcementArmyForPlayer(current_player);
		current_player.setCurrentPhase(GamePhase.REINFORCEMENT);
		reinforcePlayer();
	}

	private void reinforcePlayer() {

		current_player.setTotalArmies(reinforcement_armies);
		
		HashMap<String, Object> eventPayload = new HashMap<>();
		eventPayload.put("reinforcementArmies", reinforcement_armies);
		GamePlayEvent gpe = new GamePlayEvent(EventType.REINFORCE_ARMY_ALLOCATION, eventPayload);
		current_player.publishGamePlayEvent(gpe);

		reinforcement_view = new ReinforcementInputView(game_play_service.getCountriesConqueredBy(current_player),
				reinforcement_armies);
		reinforcement_view.setActionListener(this);
	}
	
	private void initiateAttack(){
		current_player.setCurrentPhase(GamePhase.ATTACK);
		attack_view = new AttackInputView(current_player);
		attack_view.setActionListener(this);
		attack_view.setVisible(true);
	}
	
	private void fortifyPlayer(){
		current_player.setCurrentPhase(GamePhase.FORTIFICATION);
		fortification_view = new FortificationInputView(current_player);
		fortification_view.setActionListener(this);
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
			reinforcement_armies -= armiesToReinforce;
			current_player.doReinforcement(c,armiesToReinforce);
			JOptionPane.showMessageDialog(reinforcement_view, "Reinforcement Successful");
			reinforcement_view.dispose();
			if(reinforcement_armies!=0){
				reinforcePlayer();
			}
			else{
				initiateAttack();
			}
		}
		else if(e.getSource().equals(attack_view.btn_battle)){
			if(game_play_service.canWar((Country)attack_view.attack_country.getSelectedItem(), 
												(Country)attack_view.defence_country.getSelectedItem())){
				
				DiceRoller attackRoller = new DiceRoller((Integer)attack_view.num_attacks.getSelectedItem());
				DiceRoller defenceRoller = new DiceRoller((Integer)attack_view.num_defences.getSelectedItem());
			
				List<Integer> attacks = attackRoller.rollAll();
				List<Integer> defences = defenceRoller.rollAll();
				
				switch(attacks.size()){
				case 1:
					attack_view.attack_dice1.setText(String.valueOf(attacks.get(0)));
					break;
				case 2:
					attack_view.attack_dice1.setText(String.valueOf(attacks.get(0)));
					attack_view.attack_dice2.setText(String.valueOf(attacks.get(1)));
					break;
				case 3:
					attack_view.attack_dice1.setText(String.valueOf(attacks.get(0)));
					attack_view.attack_dice2.setText(String.valueOf(attacks.get(1)));
					attack_view.attack_dice3.setText(String.valueOf(attacks.get(2)));
					break;
				}
				
				switch(defences.size()){
				case 1:
					attack_view.defence_dice1.setText(String.valueOf(defences.get(0)));
					break;
				case 2:
					attack_view.defence_dice1.setText(String.valueOf(defences.get(0)));
					attack_view.defence_dice2.setText(String.valueOf(defences.get(1)));
					break;
				}
				
				
				
				current_player.doAttack((Country)attack_view.attack_country.getSelectedItem(),
										(Country)attack_view.defence_country.getSelectedItem(), attacks, defences);
			
				JOptionPane.showMessageDialog(attack_view, "Attack Done");
				
				if(current_player.country_captured){
					List<Integer> attackerArmies = new ArrayList<Integer>();

					for (int i = 0; i < ((Country)attack_view.attack_country.getSelectedItem()).getNoOfArmy()-attacks.size(); i++) {
						attackerArmies.add(i+attacks.size());
					}
					Integer armies = (Integer) JOptionPane.showInputDialog(attack_view, "Number of Armies to Move",
							"Input", JOptionPane.NO_OPTION, BasicIconFactory.getMenuArrowIcon(), attackerArmies.toArray(new Integer[attackerArmies.size()]),
							attackerArmies.get(0));

					game_play_service.moveArmyFromTo(((Country)attack_view.attack_country.getSelectedItem()).getRuler(), ((Country)attack_view.attack_country.getSelectedItem()),
							(Country)attack_view.defence_country.getSelectedItem(), armies);
					
					HashMap<String, Object> eventPayload = new HashMap<>();
					eventPayload.put("attackCountry", (Country)attack_view.attack_country.getSelectedItem());
					eventPayload.put("capturedCountry", (Country)attack_view.defence_country.getSelectedItem());
					eventPayload.put("armies", armies);
					GamePlayEvent gpe = new GamePlayEvent(EventType.ATTACK_CAPTURE, eventPayload);
					current_player.publishGamePlayEvent(gpe);
					
					current_player.country_captured = false;
					
				}
				
				attack_view.renderAttackViewForPlayer(current_player);
			}
			else{
				JOptionPane.showMessageDialog(attack_view, "These Countries Cannot Go To War", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}	
		else if(e.getSource().equals(attack_view.btn_submit)){
			attack_view.dispose();
			fortifyPlayer();
		}
		else if(e.getSource().equals(fortification_view.btn_submit)){
			Country from = (Country) fortification_view.from_country.getSelectedItem();
			Country to = (Country) fortification_view.to_country.getSelectedItem();
			Integer armies = (Integer) fortification_view.armies.getSelectedItem();
			fortification_view.dispose();
			current_player.doFortification(from, to, armies);
			JOptionPane.showMessageDialog(game_play_view, "Fortification Successful");
			triggerNextPlayer();
		}
		else if(e.getSource().equals(fortification_view.btn_skip)){
			fortification_view.dispose();
			triggerNextPlayer();
		}
	}

	private void triggerNextPlayer() {
		current_player.captureCards();
		current_player = game_play_service.changeTurnToNextPlayer();
		game_play_view.changeCurrentPlayer();
		prepareToReinforce();
	}

}
