package ca.concordia.app.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import ca.concordia.app.service.GamePlayService;
import ca.concordia.app.view.NewGamePlayView;

public class NewGamePhaseController implements ActionListener, MouseListener {

	NewGamePlayView game_play_view;

	public NewGamePhaseController() {
		game_play_view = new NewGamePlayView();
		game_play_view.setActionListener(this);
		game_play_view.setMouseListener(this);
		game_play_view.setVisible(true);
		init();

	}

	private void init() {
		GamePlayService.getInstance().doPlayGame(game_play_view);
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

	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}

}
