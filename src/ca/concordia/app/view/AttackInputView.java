package ca.concordia.app.view;

import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ca.concordia.app.model.Country;

public class AttackInputView extends JDialog implements IView{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1856165156719508723L;
	public JComboBox<Country> attack_country;
	public JComboBox<Country> defence_country;
	public JTextField attack_dice1;
	public JTextField attack_dice2;
	public JTextField attack_dice3;
	public JTextField defence_dice1;
	public JTextField defence_dice2;
	public JButton btn_submit;
	public JButton btn_battle;
	public JComboBox<Integer> num_defences;
	public JComboBox<Integer> num_attacks;

	public AttackInputView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setBounds(410, 600, 450, 465);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		getContentPane().setLayout(null);
		
		attack_country = new JComboBox<>();
		attack_country.setBounds(179, 50, 211, 26);
		getContentPane().add(attack_country);
		
		JLabel lblAttackCountry = new JLabel("Attack Country");
		lblAttackCountry.setBounds(15, 50, 128, 26);
		getContentPane().add(lblAttackCountry);
		
		defence_country = new JComboBox<>();
		defence_country.setBounds(179, 92, 211, 26);
		getContentPane().add(defence_country);
		
		JLabel lblDefenceCountry = new JLabel("Defence Country");
		lblDefenceCountry.setBounds(15, 98, 128, 20);
		getContentPane().add(lblDefenceCountry);
		
		btn_battle = new JButton("Battle");
		btn_battle.setBounds(15, 354, 115, 29);
		getContentPane().add(btn_battle);
		
		num_attacks = new JComboBox<>();
		num_attacks.setBounds(179, 134, 211, 26);
		getContentPane().add(num_attacks);
		
		JLabel lblAttackMoves = new JLabel("Attack Moves");
		lblAttackMoves.setBounds(15, 137, 128, 20);
		getContentPane().add(lblAttackMoves);
		
		num_defences = new JComboBox<>();
		num_defences.setBounds(179, 247, 211, 26);
		getContentPane().add(num_defences);
		
		JLabel lblDefenceMoves = new JLabel("Defence Moves");
		lblDefenceMoves.setBounds(15, 250, 128, 20);
		getContentPane().add(lblDefenceMoves);
		
		JPanel panel = new JPanel();
		panel.setBounds(15, 173, 375, 61);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		attack_dice1 = new JTextField();
		attack_dice1.setEditable(false);
		attack_dice1.setBounds(38, 16, 60, 45);
		panel.add(attack_dice1);
		attack_dice1.setColumns(10);
		
		attack_dice2 = new JTextField();
		attack_dice2.setEditable(false);
		attack_dice2.setBounds(153, 16, 60, 45);
		panel.add(attack_dice2);
		attack_dice2.setColumns(10);
		
		attack_dice3 = new JTextField();
		attack_dice3.setEditable(false);
		attack_dice3.setBounds(271, 16, 60, 45);
		panel.add(attack_dice3);
		attack_dice3.setColumns(10);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(15, 291, 375, 47);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		defence_dice1 = new JTextField();
		defence_dice1.setEditable(false);
		defence_dice1.setColumns(10);
		defence_dice1.setBounds(37, 0, 60, 45);
		panel_1.add(defence_dice1);
		
		defence_dice2 = new JTextField();
		defence_dice2.setEditable(false);
		defence_dice2.setColumns(10);
		defence_dice2.setBounds(150, 0, 60, 45);
		panel_1.add(defence_dice2);
		
		btn_submit = new JButton("Submit");
		btn_submit.setBounds(179, 354, 115, 29);
		getContentPane().add(btn_submit);
	}

	@Override
	public void setActionListener(ActionListener actionListener) {
		btn_battle.addActionListener(actionListener);
		btn_submit.addActionListener(actionListener);
	}

	@Override
	public void setMouseListener(MouseListener mouseListener) {
		
	}
}
