package ca.concordia.app.view;


import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;

import ca.concordia.app.model.Country;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JButton;

public class ReinforcementInputView extends JDialog implements IView{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3027080226694856782L;
	public JComboBox<Country> country_list;
	public JComboBox<Integer> armies_list;
	public JButton btn_submit;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */
	public ReinforcementInputView(List<Country> countries, Integer armies) {
		setTitle("Reinforcement Input View");
		this.setBounds(410,600, 450, 221);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setAlwaysOnTop(true);
		getContentPane().setLayout(null);
		
		JLabel lblCountry = new JLabel("Country");
		lblCountry.setBounds(30, 31, 127, 20);
		getContentPane().add(lblCountry);
		
		country_list = new JComboBox<Country>(countries.toArray(new Country[countries.size()]));
		lblCountry.setLabelFor(country_list);
		country_list.setBounds(172, 28, 189, 26);
		getContentPane().add(country_list);
		
		JLabel lblArmies = new JLabel("Armies");
		lblArmies.setBounds(30, 67, 127, 29);
		getContentPane().add(lblArmies);
		
		
		Integer[] armiesList = new Integer[armies];
		for(int i=0;i<armies;i++){
			armiesList[i] = i+1;
		}
		
		armies_list = new JComboBox<Integer>(armiesList);
		armies_list.setBounds(172, 70, 189, 26);
		getContentPane().add(armies_list);
		
		btn_submit = new JButton("Submit");
		btn_submit.setBounds(30, 112, 115, 29);
		getContentPane().add(btn_submit);
	
		setVisible(true);
	}

	@Override
	public void setActionListener(ActionListener actionListener) {
		btn_submit.addActionListener(actionListener);
	}

	@Override
	public void setMouseListener(MouseListener mouseListener) {
		
	}

}
