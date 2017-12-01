package ca.concordia.app.view;

import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import ca.concordia.app.model.Country;
import ca.concordia.app.model.Player;
import ca.concordia.app.service.ConsoleLoggerService;
import ca.concordia.app.service.GamePlayService;


/**
 * The Class FortificationInputView , describes UI for the Fortification.
 * @author Harvi
 */
public class FortificationInputView extends JDialog implements IView {


	private static final long serialVersionUID = 2912847319751663392L;
	
	public JComboBox<Country> from_country;

	public JComboBox<Country> to_country;
	
	public JComboBox<Integer> armies;

	public JButton btn_submit;
	
	public JButton btn_skip;

	/** The current player object of Player  */
	Player current_player;
	
	/**
	 * Instantiates a new fortification input view.
	 *
	 * @param p the p
	 */
	public FortificationInputView(Player p) {
		
		current_player = p;
		setTitle("Fortification Input View");
		setBounds(410, 600, 450, 300);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setAlwaysOnTop(true);
		getContentPane().setLayout(null);
		
		JLabel lblFromCountry = new JLabel("From Country");
		lblFromCountry.setBounds(31, 33, 118, 20);
		getContentPane().add(lblFromCountry);
		
		List<Country> countrySelection = GamePlayService.getInstance().getCountriesConqueredBy(current_player);
		
		List<Country> countrySelectionFiltered = new ArrayList<>();
		for(Country c : countrySelection){
			if(c.getNoOfArmy()>1){
				countrySelectionFiltered.add(c);
			}
		}
		
		from_country = new JComboBox<>(countrySelectionFiltered.toArray(new Country[countrySelectionFiltered.size()]));
		lblFromCountry.setLabelFor(from_country);
		from_country.setBounds(164, 30, 189, 26);
		from_country.addItemListener(new CountryListToggle());
		getContentPane().add(from_country);
		
		JLabel lblToCountry = new JLabel("To Country");
		lblToCountry.setBounds(31, 90, 118, 20);
		getContentPane().add(lblToCountry);
		
		
		Country c = (Country) from_country.getItemAt(0);
		
		to_country = new JComboBox<>();
		lblToCountry.setLabelFor(to_country);
		to_country.setBounds(164, 87, 189, 26);
		if (c!=null) {
			List<Country> countries = GamePlayService.getInstance().getCountriesConqueredBy(c.getRuler());
			int index = countries.indexOf(c);
			for (int i = 0; i < countries.size(); i++) {
				if (i != index && GamePlayService.getInstance().isConnected(c, countries.get(i), c.getRuler())) {
					to_country.addItem(countries.get(i));
				}
			} 
		}
		getContentPane().add(to_country);
		
		JLabel lblArmies = new JLabel("Armies");
		lblArmies.setBounds(31, 135, 118, 20);
		getContentPane().add(lblArmies);
		
		armies = new JComboBox<>();
		lblArmies.setLabelFor(armies);
		armies.setBounds(164, 129, 189, 26);
		if (c!=null) {
			for (int i = 1; i < c.getNoOfArmy(); i++) {
				armies.addItem(i);
			} 
		}
		getContentPane().add(armies);
		
		btn_submit = new JButton("Submit");
		btn_submit.setBounds(34, 183, 118, 29);
		getContentPane().add(btn_submit);
		
		btn_skip = new JButton("End Fortification");
		btn_skip.setBounds(160,183,118,29);
		getContentPane().add(btn_skip);
		
		setVisible(true);
		
		if(countrySelectionFiltered.isEmpty()){
			ConsoleLoggerService.getInstance(null).write(current_player.getName()+
					" -> does not have eligible countries to fortify ->\n");
			JOptionPane.showMessageDialog(this, "Cannot Fortify. Skip to continue", "Error", JOptionPane.ERROR_MESSAGE);
			btn_submit.setEnabled(false);
		}
		
	}

	/* (non-Javadoc)
	 * @see ca.concordia.app.view.IView#setActionListener(java.awt.event.ActionListener)
	 */
	/**
	 * sets the action Listener
	 * @param actionListener 
	 */
	@Override
	public void setActionListener(ActionListener actionListener) {
		btn_submit.addActionListener(actionListener);
		btn_skip.addActionListener(actionListener);
	}

	/* (non-Javadoc)
	 * @see ca.concordia.app.view.IView#setMouseListener(java.awt.event.MouseListener)
	 */
	/**
	 * sets Mouse listener
	 * @param mouseListener
	 */
	@Override
	public void setMouseListener(MouseListener mouseListener) {
		
	}
	
	/**
	 * The Class CountryListToggle.
	 */
	class CountryListToggle implements ItemListener {

	/**
	 * Sets the UI for the state change events.
	 * @param ItemEvent e
	 */
		@Override
		public void itemStateChanged(ItemEvent e) {

			if(e.getSource().equals(from_country) && e.getStateChange() == ItemEvent.SELECTED){
				
				to_country.removeAllItems();
				armies.removeAllItems();
				
				Country c = (Country) from_country.getSelectedItem();
				
				if(c.getNoOfArmy()<2){
					JOptionPane.showMessageDialog(new JFrame() , c.getCountryName()+" does not have enough armies to give away",
							"Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				for(int i=1;i<c.getNoOfArmy();i++){
					armies.addItem(i);
				}
				
				
				List<Country> countries = GamePlayService.getInstance().getCountriesConqueredBy(c.getRuler());
				
				int index = countries.indexOf(c);
				for(int i=0;i<countries.size();i++){
					if(i!=index &&
							GamePlayService.getInstance().isConnected(c, countries.get(i), c.getRuler())){
						to_country.addItem(countries.get(i));
					}
				}
				
			}
		
		}
		
	}
}
