package ca.concordia.app.view;

import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * @author Abhinav
 * 
 */


public class NewGameSelectorView extends JFrame implements IView{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JButton browse_map;
	public JButton next_button;
	public JButton cancel_button;
	public JTextField num_players;
	public JFileChooser choose_map;
	public JLabel lblHowManyPlayers, lblSelectMapFile;
	public int finalPlayers;
	
	/**
	 * Create the application.
	 */
	public NewGameSelectorView() {
		getContentPane().setLayout(null);
		
		lblHowManyPlayers = new JLabel("How many Players?");
		lblHowManyPlayers.setBounds(53, 47, 311, 27);
		getContentPane().add(lblHowManyPlayers);
		
		num_players = new JTextField("2");
		num_players.setBounds(202, 49, 116, 22);
		getContentPane().add(num_players);
		num_players.setColumns(10);
		
		lblSelectMapFile = new JLabel("Select map File");
		lblSelectMapFile.setBounds(53, 133, 157, 27);
		getContentPane().add(lblSelectMapFile);
		
		browse_map = new JButton("Browse");
		browse_map.setBounds(202, 134, 116, 27);
		getContentPane().add(browse_map);
		
		next_button = new JButton("Next");
//		next_button.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				finalPlayers = Integer.parseInt(num_players.getText());
//				NewGameFrame newgameframe = new NewGameFrame(finalPlayers);
//			}
//		});
		next_button.setBounds(202, 237, 116, 25);
		getContentPane().add(next_button);
		
		cancel_button = new JButton("Cancel");
		cancel_button.setBounds(53, 237, 97, 25);
		getContentPane().add(cancel_button);
		

		
		choose_map = new JFileChooser();
		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setBounds(100, 100, 881, 536);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void setActionListener(ActionListener actionListener) {
		browse_map.addActionListener(actionListener);
		next_button.addActionListener(actionListener);
		cancel_button.addActionListener(actionListener);
	}

	@Override
	public void setMouseListener(MouseListener mouseListener) {
		// TODO Auto-generated method stub
		
	}
}
