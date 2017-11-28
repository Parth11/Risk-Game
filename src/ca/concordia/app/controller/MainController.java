/**
 * 
 */
package ca.concordia.app.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import ca.concordia.app.model.GamePlayEvent.GameMode;
import ca.concordia.app.service.GamePlayService;
import ca.concordia.app.util.RiskExceptionHandler;
import ca.concordia.app.view.MainView;

/**
 * The Class MainController is the initial class to start the game.
 *
 * @author harvi
 */
public class MainController implements ActionListener, MouseListener {

	/** 
	 * Create an object of the MainView class.
	 */
	public MainView main_view;

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		new MainController();
	}

	/**
	 * Instantiates a new main controller.
	 */
	public MainController() {

		main_view = new MainView();
		main_view.setActionListener(this);
		main_view.setMouseListener(this);
		main_view.setVisible(true);

		Thread.setDefaultUncaughtExceptionHandler(new RiskExceptionHandler(main_view));
	}

	/**
	 * Mouse click event
	 * @param e
	 */
	@Override
	public void mouseClicked(MouseEvent e) {

	}

	/**
	 * Mouse pressed event 
	 * @param e
	 */
	@Override
	public void mousePressed(MouseEvent e) {

	}

	/**
	 * Mouse released event 
	 * @param e
	 */
	@Override
	public void mouseReleased(MouseEvent e) {

	}

	/**
	 * Mouse Entered event
	 * @param e
	 */
	@Override
	public void mouseEntered(MouseEvent e) {

	}

	/**
	 * Mouse Exited event
	 * @param e
	 */
	@Override
	public void mouseExited(MouseEvent e) {

	}


	
	/**
	 * Calls the actionPerformed method of java.
	 * @param e
	 * 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource().equals(main_view.create_map)) {
			new MapEditorController(false);
			main_view.dispose();
		} else if (e.getSource().equals(main_view.edit_map)) {
			new MapEditorController(true);
			main_view.dispose();
		} else if (e.getSource().equals(main_view.new_game)) {
			GameMode[] gameMode = { GameMode.SINGLE_GAME, GameMode.TOURNAMENT };

			JFrame frame = new JFrame("Select Game Mode");
			GameMode selectedMode = null;
			while(selectedMode==null) 
			{
				selectedMode = (GameMode) JOptionPane.showInputDialog(frame, "In Which mode you like to play game?",
						"Game Mode", JOptionPane.QUESTION_MESSAGE, null, gameMode, gameMode[0]);
				
				if(selectedMode!=null) 
				{
					GamePlayService.getInstance().setGameMode(selectedMode);
					
					new NewGameSelectorController();
					
					
					
					break;
				}
			}
			
			
			
			
			main_view.dispose();
		}
	}

}
