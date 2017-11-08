package ca.concordia.app.view;

import java.awt.Color;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import ca.concordia.app.service.ConsoleLoggerService;

/**
 * The Class NewGamePlayView handles the view of new game.
 *
 * @author Abhinav
 */
public class GameLoggerView extends JDialog {

	private static final long serialVersionUID = -9072014196882993806L;
	
	/** The scroll pane. */
	public JScrollPane scroll_pane;
	
	/** The console. */
	public JTextArea console;
	
	/** The console logger service. */
	public ConsoleLoggerService console_logger_service;
	
	/**
	 * Instantiates a new new game play view.
	 */

	public GameLoggerView() {

		getContentPane().setLayout(null);

		scroll_pane = new JScrollPane();
		scroll_pane.setBounds(15, 16, 773, 485);
		getContentPane().add(scroll_pane);

		console = new JTextArea();
		scroll_pane.setViewportView(console);
		console.setForeground(Color.WHITE);
		console.setBackground(Color.BLACK);
		console_logger_service = ConsoleLoggerService.getInstance(console);
		initialize();
	}

	/**
	 * Initialize.
	 */

	private void initialize() {
		this.setVisible(true);
		setBounds(0, 0, 823, 575);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}
}
