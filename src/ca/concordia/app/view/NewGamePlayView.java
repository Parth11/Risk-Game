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
 * 
 * @author Abhinav
 *
 */
public class NewGamePlayView extends JFrame implements IView {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9072014196882993806L;
	public JScrollPane scroll_pane;
	public JTextArea console;
	public GamePlayService game_play_service;
	public ConsoleLoggerService console_logger_service;
	
	public NewGamePlayView() {
		
		getContentPane().setLayout(null);
		
		scroll_pane = new JScrollPane();
		scroll_pane.setBounds(150, 50, 800, 700);
		getContentPane().add(scroll_pane);
		
		console = new JTextArea();
		scroll_pane.setViewportView(console);
		console.setForeground(Color.WHITE);
		console.setBackground(Color.BLACK);
		console_logger_service = ConsoleLoggerService.getInstance(console);
		
		initialize();
	}
	
	
	private void initialize() {
		this.setVisible(true);
		setBounds(100, 100, 1300, 900);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}


	@Override
	public void setActionListener(ActionListener actionListener) {

	}


	@Override
	public void setMouseListener(MouseListener mouseListener) {
		
	}
}
