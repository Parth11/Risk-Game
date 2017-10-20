/**
 * 
 */
package ca.concordia.app.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import ca.concordia.app.util.RiskExceptionHandler;
import ca.concordia.app.view.MainView;

/**
 * The Class MainController.
 *
 * @author harvi
 */
public class MainController implements ActionListener, MouseListener {

	/** The main view. */
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent e) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent e) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent e) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent e) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent e) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
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
			new NewGameSelectorController();
			main_view.dispose();
		}
	}

}
