package ca.concordia.app.view;

import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ca.concordia.app.model.Country;
import ca.concordia.app.model.Player;
import ca.concordia.app.service.GamePlayService;

import javax.swing.JComboBox;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;

public class FortificationInputView extends JDialog implements IView {


	/**
	 * 
	 */
	private static final long serialVersionUID = 2912847319751663392L;
	public JComboBox<Country> from_country;
	public JComboBox<Country> to_country;
	public JComboBox<Integer> armies;
	public JButton btn_submit;

	Player current_player;
	
	public FortificationInputView(Player p) {
		
		current_player = p;
		
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setAlwaysOnTop(true);
		getContentPane().setLayout(null);
		
		JLabel lblFromCountry = new JLabel("From Country");
		lblFromCountry.setBounds(31, 33, 118, 20);
		getContentPane().add(lblFromCountry);
		
		List<Country> countrySelection = GamePlayService.getInstance().getCountriesConqueredBy(current_player);
		
		from_country = new JComboBox<>(countrySelection.toArray(new Country[countrySelection.size()]));
		lblFromCountry.setLabelFor(from_country);
		from_country.setBounds(164, 30, 189, 26);
		from_country.addItemListener(new CountryListToggle());
		getContentPane().add(from_country);
		
		JLabel lblToCountry = new JLabel("To Country");
		lblToCountry.setBounds(31, 90, 118, 20);
		getContentPane().add(lblToCountry);
		
		to_country = new JComboBox<>();
		lblToCountry.setLabelFor(to_country);
		to_country.setBounds(164, 87, 189, 26);
		getContentPane().add(to_country);
		
		JLabel lblArmies = new JLabel("Armies");
		lblArmies.setBounds(31, 135, 118, 20);
		getContentPane().add(lblArmies);
		
		armies = new JComboBox<>();
		lblArmies.setLabelFor(armies);
		armies.setBounds(164, 129, 189, 26);
		getContentPane().add(armies);
		
		btn_submit = new JButton("Submit");
		btn_submit.setBounds(34, 183, 118, 29);
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
	
	class CountryListToggle implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent e) {

			if(e.getSource().equals(from_country) && e.getStateChange() == ItemEvent.SELECTED){
				Country c = (Country) from_country.getSelectedItem();
				
				if(c.getNoOfArmy()<2){
					JOptionPane.showMessageDialog(new JFrame() , c.getCountryName()+" does not have enough armies to give away",
							"Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				for(int i=0;i< c.getNoOfArmy()-1;i++){
					armies.addItem(i);
				}
				
				
				List<Country> countries = GamePlayService.getInstance().getCountriesConqueredBy(c.getRuler());
				
				int index = countries.indexOf(c);
				for(int i=0;i<countries.size();i++){
					if(i!=index){
						to_country.addItem(countries.get(i));
					}
				}
				
			}
		
			
		}
		
	}
}
