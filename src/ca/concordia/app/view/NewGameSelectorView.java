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

import ca.concordia.app.model.GamePlayEvent.GameMode;
import ca.concordia.app.service.GamePlayService;
import ca.concordia.app.strategies.PlayerStrategy;
import ca.concordia.app.strategies.StrategyFactory;
import ca.concordia.app.strategies.StrategyFactory.Strategy;

import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * The Class NewGameSelectorView
 *
 * @author Abhinav
 */

public class NewGameSelectorView extends JFrame implements IView {

	private static final long serialVersionUID = 1L;

	int no_of_maps = 0;

	/** The browse map. */
	public JButton browse_map1, browse_map2, browse_map3, browse_map4, browse_map5;
	
	public JTextField no_of_max_turn;
	public JLabel max_turns;

	public JTextField no_of_games;
	public JLabel no_games;
	
	/** The next button. */
	public JButton next_button;

	/** The cancel button. */
	public JButton cancel_button;

	/** The choose map. */
	public JComboBox<Integer> num_players;

	private JComboBox<Strategy> strategy_1, strategy_2, strategy_3, strategy_4, strategy_5, strategy_6;

	Strategy[] single_strategies = { Strategy.HUMAN, Strategy.AGGRESSIVE, Strategy.BENEVOLENT, Strategy.RANDOM,
			Strategy.CHEATER };
	
	Strategy[] tournament_strategies = { Strategy.AGGRESSIVE, Strategy.BENEVOLENT, Strategy.RANDOM,
			Strategy.CHEATER };

	public JFileChooser choose_map;

	/** The label map file. */
	public JLabel label_players, label_map_file1, label_map_file2, label_map_file3, label_map_file4, label_map_file5;

	/** The final players. */
	public int final_players;

	/**
	 * Create the view for selecting New Game
	 * @param noOfMaps the number of maps
	 */
	public NewGameSelectorView(int noOfMaps) {
		setTitle("Start Game");
		getContentPane().setLayout(null);

		this.no_of_maps = noOfMaps;

		label_players = new JLabel("How many Players?");
		label_players.setBounds(53, 150, 160, 27);
		getContentPane().add(label_players);

		Integer[] items = { 2, 3, 4, 5, 6 };
		num_players = new JComboBox<Integer>(items);
		num_players.setBounds(225, 150, 116, 22);
		getContentPane().add(num_players);

		label_map_file1 = new JLabel("Select map File 1");
		label_map_file1.setBounds(50, 40, 150, 25);
		getContentPane().add(label_map_file1);
		browse_map1 = new JButton("Browse");
		browse_map1.setBounds(160, 40, 100, 25);
		getContentPane().add(browse_map1);

		label_map_file2 = new JLabel("Select map File 2");
		label_map_file2.setBounds(275, 40, 150, 25);
		getContentPane().add(label_map_file2);
		browse_map2 = new JButton("Browse");
		browse_map2.setBounds(385, 40, 100, 25);
		getContentPane().add(browse_map2);

		label_map_file3 = new JLabel("Select map File 3");
		label_map_file3.setBounds(50, 90, 150, 25);
		getContentPane().add(label_map_file3);
		browse_map3 = new JButton("Browse");
		browse_map3.setBounds(160, 90, 100, 25);
		getContentPane().add(browse_map3);

		label_map_file4 = new JLabel("Select map File 4");
		label_map_file4.setBounds(275, 90, 150, 25);
		getContentPane().add(label_map_file4);
		browse_map4 = new JButton("Browse");
		browse_map4.setBounds(385, 90, 100, 25);
		getContentPane().add(browse_map4);

		label_map_file5 = new JLabel("Select map File 5");
		label_map_file5.setBounds(500, 65, 150, 25);
		getContentPane().add(label_map_file5);
		browse_map5 = new JButton("Browse");
		browse_map5.setBounds(610, 65, 100, 25);
		getContentPane().add(browse_map5);

		max_turns = new JLabel("Maximum number of Turns?");
		max_turns.setBounds(720, 65, 180, 25);
		getContentPane().add(max_turns);
		
		no_of_max_turn = new JTextField();
		no_of_max_turn.setColumns(10);
		no_of_max_turn.setBounds(910, 65, 60, 25);
		no_of_max_turn.setText("10");
		getContentPane().add(no_of_max_turn);
		
		no_games = new JLabel("Number of Games");
		no_games.setBounds(720, 105, 180, 25);
		getContentPane().add(no_games);
		
		no_of_games = new JTextField();
		no_of_games.setColumns(10);
		no_of_games.setBounds(910, 105, 60, 25);
		no_of_games.setText("1");
		getContentPane().add(no_of_games);
		
		Strategy[] strategies;
		if(GamePlayService.getInstance().getGameMode()==GameMode.TOURNAMENT) 
		{
			strategies=tournament_strategies;
			max_turns.setVisible(true);
			no_of_max_turn.setVisible(true);
			no_of_games.setVisible(true);
			no_games.setVisible(true);
		}
		else 
		{
			strategies=single_strategies;
			max_turns.setVisible(false);
			no_of_max_turn.setVisible(false);
			no_of_games.setVisible(false);
			no_games.setVisible(false);
		}
		
		if (no_of_maps == 1) {
			label_map_file2.setVisible(false);
			browse_map2.setVisible(false);

			label_map_file3.setVisible(false);
			browse_map3.setVisible(false);

			label_map_file4.setVisible(false);
			browse_map4.setVisible(false);

			label_map_file5.setVisible(false);
			browse_map5.setVisible(false);
			
			
		} else {
			
			
			switch (no_of_maps) 
			{
			case 2:
				label_map_file3.setVisible(false);
				browse_map3.setVisible(false);

				label_map_file4.setVisible(false);
				browse_map4.setVisible(false);

				label_map_file5.setVisible(false);
				browse_map5.setVisible(false);
				
				break;
			case 3:
				label_map_file4.setVisible(false);
				browse_map4.setVisible(false);

				label_map_file5.setVisible(false);
				browse_map5.setVisible(false);
				break;
			case 4:

				label_map_file5.setVisible(false);
				browse_map5.setVisible(false);
				break;
			case 5:

				break;
			}
		}

		next_button = new JButton("Next");
		next_button.setBounds(227, 530, 116, 25);
		getContentPane().add(next_button);

		cancel_button = new JButton("Cancel");
		cancel_button.setBounds(53, 530, 97, 25);
		getContentPane().add(cancel_button);

		JPanel panel = new JPanel();
		panel.setBounds(15, 162, 481, 392);
		getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblP = new JLabel("P1");
		lblP.setBounds(80, 25, 69, 20);
		panel.add(lblP);

		JLabel lblP_1 = new JLabel("P2");
		lblP_1.setBounds(80, 75, 69, 20);
		panel.add(lblP_1);

		JLabel lblP_2 = new JLabel("P3");
		lblP_2.setBounds(80, 125, 69, 20);
		panel.add(lblP_2);

		JLabel lblP_3 = new JLabel("P4");
		lblP_3.setBounds(80, 175, 69, 20);
		panel.add(lblP_3);

		JLabel lblP_4 = new JLabel("P5");
		lblP_4.setBounds(80, 225, 69, 20);
		panel.add(lblP_4);

		JLabel lblP_5 = new JLabel("P6");
		lblP_5.setBounds(80, 275, 69, 20);
		panel.add(lblP_5);

		strategy_1 = new JComboBox<Strategy>(strategies);
		strategy_1.setBounds(207, 25, 135, 26);
		strategy_1.setSelectedIndex(2);
		panel.add(strategy_1);

		strategy_2 = new JComboBox<Strategy>(strategies);
		strategy_2.setBounds(207, 75, 135, 26);
		strategy_2.setSelectedIndex(2);
		panel.add(strategy_2);

		strategy_3 = new JComboBox<Strategy>(strategies);
		strategy_3.setBounds(207, 125, 135, 26);
		strategy_3.setSelectedIndex(2);
		panel.add(strategy_3);

		strategy_4 = new JComboBox<Strategy>(strategies);
		strategy_4.setBounds(207, 175, 135, 26);
		strategy_4.setSelectedIndex(2);
		panel.add(strategy_4);

		strategy_5 = new JComboBox<Strategy>(strategies);
		strategy_5.setBounds(207, 225, 135, 26);
		strategy_5.setSelectedIndex(2);
		panel.add(strategy_5);

		strategy_6 = new JComboBox<Strategy>(strategies);
		strategy_6.setBounds(207, 275, 135, 26);
		strategy_6.setSelectedIndex(2);
		panel.add(strategy_6);

		choose_map = new JFileChooser();

		initialize();
	}
	
	/**
	 * initialize close operation & set bounds
	 */
	private void initialize() {
		setBounds(100, 100, 1147, 798);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * calls the setActionlistener
	 * 
	 * @param actionListener the action listener
	 */
	@Override
	public void setActionListener(ActionListener actionListener) {
		if (no_of_maps == 1) {
			browse_map1.addActionListener(actionListener);
		} else {
			browse_map1.addActionListener(actionListener);
			browse_map2.addActionListener(actionListener);
			browse_map3.addActionListener(actionListener);
			browse_map4.addActionListener(actionListener);
			browse_map5.addActionListener(actionListener);
		}
		next_button.addActionListener(actionListener);
		cancel_button.addActionListener(actionListener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ca.concordia.app.view.IView#setMouseListener(java.awt.event.MouseListener)
	 */
	/**
	 * sets mouse listener
	 * @param mouseListener
	 */
	@Override
	public void setMouseListener(MouseListener mouseListener) {

	}
	/**
	 * This method gets the strategies 
	 * @return strategies
	 */
	public List<PlayerStrategy> getStrategies() {
		List<PlayerStrategy> strategies = new ArrayList<>();
		switch (Integer.parseInt(num_players.getSelectedItem().toString())) {
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
