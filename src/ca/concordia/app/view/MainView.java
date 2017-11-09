package ca.concordia.app.view;

import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * The Class MainView.
 * 
 * @author Hardik
 */
public class MainView extends JFrame implements IView{


	private static final long serialVersionUID = 1L;
	
	/** The edit map. */
	public JButton new_game,create_map,edit_map;

	/**
	 * Launch the application.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		MainView window = new MainView();
		window.setVisible(true);
	}

	/**
	 * Create the application.
	 */
	public MainView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		this.setBounds(100, 100, 1280, 763);
		setTitle("Risk");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);
		
		new_game = new JButton("New Game");
		new_game.setBounds(164, 290, 213, 109);
		this.getContentPane().add(new_game);
		
		create_map = new JButton("Create Map");
		create_map.setBounds(521, 290, 199, 109);
		this.getContentPane().add(create_map);
		
		edit_map = new JButton("Edit Map");
		edit_map.setBounds(886, 290, 213, 109);
		this.getContentPane().add(edit_map);
	}

	/**
	 * calls setActionlistener java method
	 */
	@Override
	public void setActionListener(ActionListener actionListener) {
		new_game.addActionListener(actionListener);
		create_map.addActionListener(actionListener);
		edit_map.addActionListener(actionListener);
	}

	/* (non-Javadoc)
	 * @see ca.concordia.app.view.IView#setMouseListener(java.awt.event.MouseListener)
	 */
	@Override
	public void setMouseListener(MouseListener mouseListener) {
		
	}

}
