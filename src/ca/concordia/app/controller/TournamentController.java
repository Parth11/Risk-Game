package ca.concordia.app.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;

import ca.concordia.app.model.Card;
import ca.concordia.app.model.Country;
import ca.concordia.app.model.GamePlayEvent;
import ca.concordia.app.model.Player;
import ca.concordia.app.model.GamePlayEvent.EventType;
import ca.concordia.app.service.ConsoleLoggerService;
import ca.concordia.app.service.GamePlayService;
import ca.concordia.app.strategies.PlayerStrategy;
import ca.concordia.app.util.GameConstants;
import ca.concordia.app.util.GamePhase;
import ca.concordia.app.view.AttackInputView;
import ca.concordia.app.view.CardExchangeView;
import ca.concordia.app.view.FortificationInputView;
import ca.concordia.app.view.GameLoggerView;
import ca.concordia.app.view.ReinforcementInputView;

public class TournamentController implements ActionListener, MouseListener {

	GameLoggerView game_logger_view;

	int tournament_game_turns = 0;

	ReinforcementInputView reinforcement_view;

	FortificationInputView fortification_view;

	AttackInputView attack_view;

	CardExchangeView card_exchange_view;

	GamePlayService game_play_service;

	Player current_player;

	Integer reinforcement_armies;

	Country from_country;

	Country to_country;

	List<Player> players = new ArrayList<>();

	public TournamentController(Integer numPlayers, List<? extends PlayerStrategy> strategies) {
		game_logger_view = new GameLoggerView();
		game_play_service = GamePlayService.getInstance();
		init(numPlayers, strategies);
	}

	private void init(Integer numPlayers, List<? extends PlayerStrategy> strategies) {
		// TODO Auto-generated method stub
		ConsoleLoggerService.getInstance(game_logger_view.console);
		game_play_service.doStartupPhase(numPlayers, strategies);
		current_player = game_play_service.getCurrentTurnPlayer();
		goToNextMove();
	}

	public void goToNextMove() {

		switch (current_player.game_phase) {
		case ATTACK:
			if (current_player.event_log.get(current_player.event_log.size() - 1).getEvent_type().equals(EventType.THE_END)) {
				JOptionPane.showMessageDialog(game_logger_view, "Game Ends");
				System.exit(0);
			}
			fortifyPlayer();
			break;
		case FORTIFICATION:
			prepareToReinforce();
			break;
		case REINFORCEMENT:
			initiateAttack();
			break;
		case STARTUP:
			prepareToReinforce();
			break;
		}
	}

	private void prepareToReinforce() {
		if (tournament_game_turns < game_play_service.getMaxTurns()) 
		{
			tournament_game_turns++;
			ConsoleLoggerService.getInstance(null).write("->->" + current_player.getName() + "******REINFORCEMENT PHASE BEGIN*******\n");
			current_player.setCurrentPhase(GamePhase.REINFORCEMENT);
			GamePlayEvent gpe = new GamePlayEvent(EventType.GENERIC_UPDATE, new HashMap<>());
			current_player.publishGamePlayEvent(gpe);
			if (game_play_service.showCardExchangeView(current_player)) 
			{
				int a = 0, i = 0, c = 0;
				for (Card cd : current_player.getCards()) {
					switch (cd.getCardType()) {
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
				if (a >= 3 && i < 3 && c < 3) {
					current_player.reimburseCards(3, 0, 0);
				} else if (i >= 3 && a < 3 && c < 3) {
					current_player.reimburseCards(0, 3, 0);
				} else if (c >= 3 && a < 3 && i < 3) {
					current_player.reimburseCards(0, 0, 3);
				} else if (a >= 1 && i >= 1 && c >= 1) {
					current_player.reimburseCards(1, 1, 1);
				}
			} 
			reinforcement_armies = game_play_service.getReinforcementArmyForPlayer(current_player);
			reinforcePlayer();

		} else {
			game_play_service.declareDraw();
		}

	}

	private void reinforcePlayer() {
		current_player.setTotalArmies(reinforcement_armies);
		HashMap<String, Object> eventPayload = new HashMap<>();
		eventPayload.put("reinforcementArmies", reinforcement_armies);
		GamePlayEvent gpe = new GamePlayEvent(EventType.REINFORCE_ARMY_ALLOCATION, eventPayload);
		current_player.publishGamePlayEvent(gpe);
		current_player.strategizeReinforcement();
		goToNextMove();
	}

	private void initiateAttack() {
		
		current_player.setCurrentPhase(GamePhase.ATTACK);
		current_player.strategizeAttack();
		goToNextMove();
		
	}

	private void fortifyPlayer() {
		current_player.setCurrentPhase(GamePhase.FORTIFICATION);
		current_player.strategizeFortification();
		triggerNextPlayer();
	}

	private void triggerNextPlayer() {

		ConsoleLoggerService.getInstance(null)
				.write("->->" + current_player.getName() + "******FORTIFICATION PHASE END*******\n");

		current_player.captureCards();
		current_player = game_play_service.changeTurnToNextPlayer();
		goToNextMove();
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

	}

}
