package ca.concordia.app.view;

import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.JDialog;
import javax.swing.JLabel;

import ca.concordia.app.model.Card;
import ca.concordia.app.model.Player;
import ca.concordia.app.util.GameConstants;

import javax.swing.JComboBox;
import javax.swing.JButton;

/**
 * The Class CardExchangeView contains detail of the card exchange view.
 */
public class CardExchangeView extends JDialog implements IView {


	private static final long serialVersionUID = -2050079622333794747L;
	
	/** The btn skip. */
	public JButton btn_skip;
	
	/** The btn exchange. */
	public JButton btn_exchange;
	
	/** The num artillery. */
	public JComboBox<Integer> num_artillery;
	
	/** The num cavalry. */
	public JComboBox<Integer> num_cavalry;
	
	/** The num infantry. */
	public JComboBox<Integer> num_infantry;

	
	/**
	 * Instantiates a new card exchange view.
	 *
	 * @param player the player
	 */
	public CardExchangeView(Player player) {
		
		int a=0,i=0,c=0;
		boolean isEnabled = player.getCards().size()>=5?false:true;
		for(Card card : player.getCards()){
			switch(card.getCardType()){
			case GameConstants.ARTILLERY:
				a++;
				break;
			case GameConstants.CAVALRY:
				c++;
				break;
			case GameConstants.INFANTRY:
				i++;
				break;
			}
		}
		
		
		Integer[] artillery = new Integer[a+1];
		Integer[] cavalry = new Integer[c+1];
		Integer[] infantry = new Integer[i+1];
		
		for(int j=0;j<=a;j++){
			artillery[j] = j; 
		}
		for(int j=0;j<=i;j++){
			infantry[j] = j; 
		}
		for(int j=0;j<=c;j++){
			cavalry[j] = j; 
		}
		
		
		setBounds(410, 600, 324, 300);
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
		
		num_artillery = new JComboBox<>(artillery);
		num_artillery.setBounds(159, 30, 106, 26);
		getContentPane().add(num_artillery);
		
		num_cavalry = new JComboBox<>(cavalry);
		num_cavalry.setBounds(159, 75, 106, 26);
		getContentPane().add(num_cavalry);
		
		num_infantry = new JComboBox<>(infantry);
		num_infantry.setBounds(159, 120, 106, 26);
		getContentPane().add(num_infantry);
		
		btn_exchange = new JButton("Exchange");
		btn_exchange.setBounds(15, 182, 115, 29);
		getContentPane().add(btn_exchange);
		
		btn_skip = new JButton("Skip");
		btn_skip.setBounds(150, 182, 115, 29);
		btn_skip.setEnabled(isEnabled);
		getContentPane().add(btn_skip);
		setTitle("Card Exchange View");
		setVisible(true);
	}

	/**
	 * Adds the action listener.
	 * @param actionListener the Action listener
	 */
	@Override
	public void setActionListener(ActionListener actionListener) {
		btn_exchange.addActionListener(actionListener);
		btn_skip.addActionListener(actionListener);
	}
	
	/**
	 * sets Mouse listener.
	 * @param mouseListener the mouse listener
	 */
	@Override
	public void setMouseListener(MouseListener mouseListener) {

	}
}
