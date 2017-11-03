package ca.concordia.app.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import ca.concordia.app.service.GamePlayService;
import ca.concordia.app.view.NewGamePlayView;


/**
 * The Class NewGamePhaseController manages the starting of the game.
 * 
 * @author Parth
 */
public class NewGamePhaseController implements ActionListener, MouseListener {

	NewGamePlayView game_play_view;

	public NewGamePhaseController(Integer numPlayers) {
		game_play_view = new NewGamePlayView();
		game_play_view.setActionListener(this);
		game_play_view.setMouseListener(this);
		game_play_view.setVisible(true);
		init(numPlayers);

	}
	

	private void init(Integer numPlayers) {
		GamePlayService.getInstance().doStartupPhase(numPlayers,game_play_view);
		GamePlayService.getInstance().doPlayGame(game_play_view);
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

	}

}
