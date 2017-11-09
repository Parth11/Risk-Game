package ca.concordia.app.view;

import java.awt.Color;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

import ca.concordia.app.service.ConsoleLoggerService;

/**
 * The Class NewGamePlayView handles the view of new game.
 *
 * @author Abhinav
 */
public class GameLoggerView extends JFrame {

	private static final long serialVersionUID = -9072014196882993806L;
	
	public JScrollPane scroll_pane;
	
	public JTextArea console;
	
	public ConsoleLoggerService console_logger_service;
	
	/**
	 * Instantiates a new new game play view.
	 */

	public GameLoggerView() {

		getContentPane().setLayout(null);
		setTitle("Game Logger View");
		scroll_pane = new JScrollPane();
		scroll_pane.setBounds(15, 16, 773, 485);
		getContentPane().add(scroll_pane);

		console = new JTextArea();
		scroll_pane.setViewportView(console);
		console.setForeground(Color.WHITE);
		console.setBackground(Color.BLACK);
		DefaultCaret caret = (DefaultCaret) console.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		console.setCaret(caret);
		console_logger_service = ConsoleLoggerService.getInstance(console);
		initialize();
	}

	/**
	 * Initialize window.
	 */

	private void initialize() {
		this.setVisible(true);
		setBounds(0, 0, 823, 575);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
