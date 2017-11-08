package ca.concordia.app.view;

import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;

public class CardExchangeView extends JDialog implements IView {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2050079622333794747L;
	public JButton btn_skip;
	public JButton btn_exchange;
	public JComboBox<Integer> num_artillery;
	public JComboBox<Integer> num_cavalry;
	public JComboBox<Integer> num_infantry;

	
	public CardExchangeView() {
		setBounds(100, 100, 324, 300);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JLabel lblArtillery = new JLabel("Artillery");
		lblArtillery.setBounds(27, 31, 117, 25);
		getContentPane().add(lblArtillery);
		
		JLabel lblCavarly = new JLabel("Cavarly");
		lblCavarly.setBounds(27, 76, 117, 25);
		getContentPane().add(lblCavarly);
		
		JLabel lblNewLabel = new JLabel("Infantry");
		lblNewLabel.setBounds(27, 117, 117, 32);
		getContentPane().add(lblNewLabel);
		
		num_artillery = new JComboBox<>();
		num_artillery.setBounds(159, 30, 106, 26);
		getContentPane().add(num_artillery);
		
		num_cavalry = new JComboBox<>();
		num_cavalry.setBounds(159, 75, 106, 26);
		getContentPane().add(num_cavalry);
		
		num_infantry = new JComboBox<>();
		num_infantry.setBounds(159, 120, 106, 26);
		getContentPane().add(num_infantry);
		
		btn_exchange = new JButton("Exchange");
		btn_exchange.setBounds(15, 182, 115, 29);
		getContentPane().add(btn_exchange);
		
		btn_skip = new JButton("Skip");
		btn_skip.setBounds(150, 182, 115, 29);
		getContentPane().add(btn_skip);
		setVisible(true);
	}

	@Override
	public void setActionListener(ActionListener actionListener) {
		btn_exchange.addActionListener(actionListener);
		btn_skip.addActionListener(actionListener);
	}

	@Override
	public void setMouseListener(MouseListener mouseListener) {

	}
}
