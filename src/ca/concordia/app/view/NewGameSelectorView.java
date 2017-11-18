package ca.concordia.app.view;

import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;

import ca.concordia.app.strategies.PlayerStrategy;
import ca.concordia.app.strategies.StrategyFactory;

import javax.swing.JPanel;

/**
 * The Class NewGameSelectorView 
 *
 * @author Abhinav
 */


public class NewGameSelectorView extends JFrame implements IView{

	private static final long serialVersionUID = 1L;
	
	/** The browse map. */
	public JButton browse_map;
	
	/** The next button. */
	public JButton next_button;
	
	/** The cancel button. */
	public JButton cancel_button;
	
	/** The choose map. */
	public JComboBox<Integer> num_players;
	
	private JComboBox<String> strategy_1,strategy_2,strategy_3,strategy_4,strategy_5,strategy_6;

	public JFileChooser choose_map;
	
	/** The label map file. */
	public JLabel label_players, label_map_file;
	
	/** The final players. */
	public int final_players;
	
	/**
	 * Create the view for selecting New Game
	 */
	public NewGameSelectorView() {
		setTitle("Start Game");
		getContentPane().setLayout(null);
		
		label_players = new JLabel("How many Players?");
		label_players.setBounds(53, 99, 157, 27);
		getContentPane().add(label_players);
		
		Integer[] items = {2,3,4,5,6};
		num_players = new JComboBox<>(items);
		num_players.setBounds(247, 101, 116, 22);
		getContentPane().add(num_players);
		
		label_map_file = new JLabel("Select map File");
		label_map_file.setBounds(53, 39, 157, 27);
		getContentPane().add(label_map_file);
		
		browse_map = new JButton("Browse");
		browse_map.setBounds(247, 39, 116, 27);
		getContentPane().add(browse_map);
		
		next_button = new JButton("Next");
		next_button.setBounds(227, 587, 116, 25);
		getContentPane().add(next_button);
		
		cancel_button = new JButton("Cancel");
		cancel_button.setBounds(53, 587, 97, 25);
		getContentPane().add(cancel_button);
		
		JPanel panel = new JPanel();
		panel.setBounds(15, 162, 481, 392);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblP = new JLabel("P 1");
		lblP.setBounds(15, 27, 69, 20);
		panel.add(lblP);
		
		JLabel lblP_1 = new JLabel("P2");
		lblP_1.setBounds(15, 79, 69, 20);
		panel.add(lblP_1);
		
		JLabel lblP_2 = new JLabel("P3");
		lblP_2.setBounds(15, 128, 69, 20);
		panel.add(lblP_2);
		
		JLabel lblP_3 = new JLabel("P4");
		lblP_3.setBounds(15, 184, 69, 20);
		panel.add(lblP_3);
		
		JLabel lblP_4 = new JLabel("P5");
		lblP_4.setBounds(15, 244, 69, 20);
		panel.add(lblP_4);
		
		JLabel lblP_5 = new JLabel("P6");
		lblP_5.setBounds(15, 295, 69, 20);
		panel.add(lblP_5);
		
		String[] strategies = {"human","aggressive","benevolent","random","cheater"};
		
		strategy_1 = new JComboBox<>(strategies);
		strategy_1.setBounds(207, 24, 135, 26);
		panel.add(strategy_1);
		
		strategy_2 = new JComboBox<>(strategies);
		strategy_2.setBounds(207, 66, 135, 26);
		panel.add(strategy_2);
		
		strategy_3 = new JComboBox<>(strategies);
		strategy_3.setBounds(207, 125, 135, 26);
		panel.add(strategy_3);
		
		strategy_4 = new JComboBox<>(strategies);
		strategy_4.setBounds(207, 181, 135, 26);
		panel.add(strategy_4);
		
		strategy_5 = new JComboBox<>(strategies);
		strategy_5.setBounds(207, 241, 135, 26);
		panel.add(strategy_5);
		
		strategy_6 = new JComboBox<>(strategies);
		strategy_6.setBounds(207, 292, 135, 26);
		panel.add(strategy_6);
		

		
		choose_map = new JFileChooser();
		
		initialize();
	}


	private void initialize() {
		setBounds(100, 100, 1147, 798);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * calls the setActionlistener
	 * @param actionListener
	 */
	@Override
	public void setActionListener(ActionListener actionListener) {
		browse_map.addActionListener(actionListener);
		next_button.addActionListener(actionListener);
		cancel_button.addActionListener(actionListener);
	}

	/* (non-Javadoc)
	 * @see ca.concordia.app.view.IView#setMouseListener(java.awt.event.MouseListener)
	 */
	@Override
	public void setMouseListener(MouseListener mouseListener) {
		
	}


	public List<PlayerStrategy> getStrategies() {
		List<PlayerStrategy> strategies = new ArrayList<>();
		switch(Integer.parseInt(num_players.getSelectedItem().toString())){
		case 6:
			strategies.add(StrategyFactory.getStrategyByName(strategy_6.getSelectedItem().toString()));
		case 5:
			strategies.add(StrategyFactory.getStrategyByName(strategy_5.getSelectedItem().toString()));
		case 4:
			strategies.add(StrategyFactory.getStrategyByName(strategy_4.getSelectedItem().toString()));
		case 3:
			strategies.add(StrategyFactory.getStrategyByName(strategy_3.getSelectedItem().toString()));
		case 2:
			strategies.add(StrategyFactory.getStrategyByName(strategy_2.getSelectedItem().toString()));
			strategies.add(StrategyFactory.getStrategyByName(strategy_1.getSelectedItem().toString()));
		}
		Collections.reverse(strategies);
		return strategies;
	}
}
