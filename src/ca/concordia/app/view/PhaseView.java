package ca.concordia.app.view;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JPanel;

public class PhaseView extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JTextField player_name;
	public JTextField player_phase;
	public JTextField player_armies;
	public JTextField num_infantry;
	public JTextField num_cavalry;
	public JTextField num_artillery;
	public JTable conquest_table;
	public JTextField reinforced_country;
	public JTextField reinforce_armies;
	public JTextField attacking_country;
	public JTextField attacked_country;
	public JTextField attack_throws;
	public JTextField defence_throws;
	public JTextField win_loss;
	public JTextField fortifying_country;
	public JTextField fortified_country;
	public JTextField armies_moved;


	/**
	 * Create the application.
	 */
	public PhaseView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		this.setBounds(870, 0, 984, 713);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.getContentPane().setLayout(null);
		
		JLabel lblCurrentPlayer = new JLabel("Current Player");
		lblCurrentPlayer.setBounds(30, 29, 149, 20);
		this.getContentPane().add(lblCurrentPlayer);
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(30, 75, 96, 20);
		this.getContentPane().add(lblName);
		
		player_name = new JTextField();
		player_name.setEditable(false);
		lblName.setLabelFor(player_name);
		player_name.setBounds(167, 72, 146, 26);
		this.getContentPane().add(player_name);
		player_name.setColumns(10);
		
		JLabel lblPhase = new JLabel("Phase");
		lblPhase.setBounds(30, 123, 69, 20);
		this.getContentPane().add(lblPhase);
		
		player_phase = new JTextField();
		player_phase.setEditable(false);
		lblPhase.setLabelFor(player_phase);
		player_phase.setBounds(167, 120, 146, 26);
		this.getContentPane().add(player_phase);
		player_phase.setColumns(10);
		
		JLabel lblArmies = new JLabel("Armies");
		lblArmies.setBounds(30, 176, 69, 20);
		this.getContentPane().add(lblArmies);
		
		player_armies = new JTextField();
		lblArmies.setLabelFor(player_armies);
		player_armies.setEditable(false);
		player_armies.setBounds(167, 173, 146, 26);
		this.getContentPane().add(player_armies);
		player_armies.setColumns(10);
		
		JLabel lblInfantry = new JLabel("INFANTRY");
		lblInfantry.setBounds(359, 75, 106, 20);
		this.getContentPane().add(lblInfantry);
		
		num_infantry = new JTextField();
		lblInfantry.setLabelFor(num_infantry);
		num_infantry.setEditable(false);
		num_infantry.setBounds(484, 72, 52, 26);
		this.getContentPane().add(num_infantry);
		num_infantry.setColumns(10);
		
		JLabel lblCavalry = new JLabel("CAVALRY");
		lblCavalry.setBounds(358, 123, 107, 20);
		this.getContentPane().add(lblCavalry);
		
		num_cavalry = new JTextField();
		lblCavalry.setLabelFor(num_cavalry);
		num_cavalry.setEditable(false);
		num_cavalry.setBounds(484, 120, 52, 26);
		this.getContentPane().add(num_cavalry);
		num_cavalry.setColumns(10);
		
		JLabel lblArtillery = new JLabel("ARTILLERY");
		lblArtillery.setBounds(359, 176, 106, 20);
		this.getContentPane().add(lblArtillery);
		
		num_artillery = new JTextField();
		lblArtillery.setLabelFor(num_artillery);
		num_artillery.setEditable(false);
		num_artillery.setBounds(484, 173, 52, 26);
		this.getContentPane().add(num_artillery);
		num_artillery.setColumns(10);
		
		JLabel lblCards = new JLabel("Cards");
		lblCards.setBounds(359, 29, 69, 20);
		this.getContentPane().add(lblCards);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(30, 274, 506, 367);
		this.getContentPane().add(scrollPane);
		
		conquest_table = new JTable();
		conquest_table.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		scrollPane.setViewportView(conquest_table);
		
		JLabel lblConquests = new JLabel("Conquests");
		lblConquests.setBounds(30, 238, 106, 20);
		this.getContentPane().add(lblConquests);
		
		JPanel reinforce_panel = new JPanel();
		reinforce_panel.setBounds(574, 29, 366, 107);
		this.getContentPane().add(reinforce_panel);
		reinforce_panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Reinforced Country");
		lblNewLabel.setBounds(15, 16, 165, 20);
		reinforce_panel.add(lblNewLabel);
		
		reinforced_country = new JTextField();
		lblNewLabel.setLabelFor(reinforced_country);
		reinforced_country.setEditable(false);
		reinforced_country.setBounds(201, 13, 146, 26);
		reinforce_panel.add(reinforced_country);
		reinforced_country.setColumns(10);
		
		JLabel lblReinforcementArmies = new JLabel("Reinforcement Armies");
		lblReinforcementArmies.setBounds(15, 70, 165, 20);
		reinforce_panel.add(lblReinforcementArmies);
		
		reinforce_armies = new JTextField();
		lblReinforcementArmies.setLabelFor(reinforce_armies);
		reinforce_armies.setEditable(false);
		reinforce_armies.setBounds(201, 67, 146, 26);
		reinforce_panel.add(reinforce_armies);
		reinforce_armies.setColumns(10);
		
		JPanel attack_panel = new JPanel();
		attack_panel.setBounds(584, 152, 366, 242);
		this.getContentPane().add(attack_panel);
		attack_panel.setLayout(null);
		
		JLabel lblAttackingCountry = new JLabel("Attacking Country");
		lblAttackingCountry.setBounds(15, 16, 167, 26);
		attack_panel.add(lblAttackingCountry);
		
		attacking_country = new JTextField();
		lblAttackingCountry.setLabelFor(attacking_country);
		attacking_country.setEditable(false);
		attacking_country.setBounds(197, 13, 146, 26);
		attack_panel.add(attacking_country);
		attacking_country.setColumns(10);
		
		JLabel lblAttackedCountry = new JLabel("Attacked Country");
		lblAttackedCountry.setBounds(15, 52, 138, 29);
		attack_panel.add(lblAttackedCountry);
		
		attacked_country = new JTextField();
		attacked_country.setEditable(false);
		attacked_country.setBounds(197, 55, 146, 26);
		attack_panel.add(attacked_country);
		attacked_country.setColumns(10);
		
		attack_throws = new JTextField();
		attack_throws.setEditable(false);
		attack_throws.setBounds(197, 105, 146, 26);
		attack_panel.add(attack_throws);
		attack_throws.setColumns(10);
		
		JLabel lblAttackDiceThrows = new JLabel("Attack Throws");
		lblAttackDiceThrows.setLabelFor(attack_throws);
		lblAttackDiceThrows.setBounds(15, 107, 154, 26);
		attack_panel.add(lblAttackDiceThrows);
		
		defence_throws = new JTextField();
		defence_throws.setEditable(false);
		defence_throws.setBounds(197, 159, 146, 26);
		attack_panel.add(defence_throws);
		defence_throws.setColumns(10);
		
		JLabel lblDefenceThrows = new JLabel("Defence Throws");
		lblDefenceThrows.setLabelFor(defence_throws);
		lblDefenceThrows.setBounds(15, 162, 146, 20);
		attack_panel.add(lblDefenceThrows);
		
		win_loss = new JTextField();
		win_loss.setEditable(false);
		win_loss.setBounds(197, 203, 146, 26);
		attack_panel.add(win_loss);
		win_loss.setColumns(10);
		
		JLabel lblWinloss = new JLabel("Win/Loss");
		lblWinloss.setLabelFor(win_loss);
		lblWinloss.setBounds(15, 209, 167, 20);
		attack_panel.add(lblWinloss);
		
		JPanel fortify_panel = new JPanel();
		fortify_panel.setBounds(584, 436, 366, 205);
		this.getContentPane().add(fortify_panel);
		fortify_panel.setLayout(null);
		
		fortifying_country = new JTextField();
		fortifying_country.setEditable(false);
		fortifying_country.setBounds(206, 16, 146, 26);
		fortify_panel.add(fortifying_country);
		fortifying_country.setColumns(10);
		
		JLabel lblFortifyingCountry = new JLabel("Fortifying Country");
		lblFortifyingCountry.setLabelFor(fortifying_country);
		lblFortifyingCountry.setBounds(15, 19, 161, 20);
		fortify_panel.add(lblFortifyingCountry);
		
		fortified_country = new JTextField();
		fortified_country.setEditable(false);
		fortified_country.setBounds(206, 58, 146, 26);
		fortify_panel.add(fortified_country);
		fortified_country.setColumns(10);
		
		JLabel lblFortifiedCountry = new JLabel("Fortified Country");
		lblFortifiedCountry.setLabelFor(fortified_country);
		lblFortifiedCountry.setBounds(15, 61, 151, 20);
		fortify_panel.add(lblFortifiedCountry);
		
		armies_moved = new JTextField();
		armies_moved.setEditable(false);
		armies_moved.setBounds(206, 111, 146, 26);
		fortify_panel.add(armies_moved);
		armies_moved.setColumns(10);
		
		JLabel lblArmiesMoved = new JLabel("Armies Moved");
		lblArmiesMoved.setLabelFor(armies_moved);
		lblArmiesMoved.setBounds(15, 114, 151, 20);
		fortify_panel.add(lblArmiesMoved);
	}
	
	public void clearFields(){
		
		player_name.setText("");
		
		player_phase.setText("");
		
		player_armies.setText("");
		
		num_infantry.setText("");
		
		num_cavalry.setText("");
		
		num_artillery.setText("");
		
		reinforced_country.setText("");
		
		reinforce_armies.setText("");
		
		attacking_country.setText("");
		
		attacked_country.setText("");
		
		attack_throws.setText("");
		
		defence_throws.setText("");
		
		win_loss.setText("");
		
		fortifying_country.setText("");
		
		fortified_country.setText("");
		
		armies_moved.setText("");
		
	}
	
}
