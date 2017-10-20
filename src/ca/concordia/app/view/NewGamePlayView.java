package ca.concordia.app.view;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import ca.concordia.app.service.ConsoleLoggerService;
import ca.concordia.app.service.GamePlayService;

/**
 * The Class NewGamePlayView handles the view of new game.
 *
 * @author Abhinav
 */
public class NewGamePlayView extends JFrame implements IView {

	private static final long serialVersionUID = -9072014196882993806L;
	
	/** The scroll pane. */
	public JScrollPane scroll_pane;
	
	/** The console. */
	public JTextArea console;
	
	/** The game play service. */
	public GamePlayService game_play_service;
	
	/** The console logger service. */
	public ConsoleLoggerService console_logger_service;
	
	/**
	 * Instantiates a new new game play view.
	 */

	public NewGamePlayView() {

		getContentPane().setLayout(null);

		scroll_pane = new JScrollPane();
		scroll_pane.setBounds(15, 16, 1248, 812);
		getContentPane().add(scroll_pane);

		console = new JTextArea();
		scroll_pane.setViewportView(console);
		console.setForeground(Color.WHITE);
		console.setBackground(Color.BLACK);
		console_logger_service = ConsoleLoggerService.getInstance(console);
		setBounds(0, 0, 1300, 900);
		initialize();
	}

	/**
	 * Initialize.
	 */

	private void initialize() {
		this.setVisible(true);
		setBounds(100, 100, 1300, 900);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}


	/* (non-Javadoc)
	 * @see ca.concordia.app.view.IView#setActionListener(java.awt.event.ActionListener)
	 */

	@Override
	public void setActionListener(ActionListener actionListener) {

	}


	/* (non-Javadoc)
	 * @see ca.concordia.app.view.IView#setMouseListener(java.awt.event.MouseListener)
	 */
	@Override
	public void setMouseListener(MouseListener mouseListener) {

	}
}
