package ca.concordia.app.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

/**
 * The Class GamePlayView.
 */
public class GamePlayView extends JFrame implements IView{

	private static final long serialVersionUID = -4197727817255615951L;
	
	/** The number of players. */
	int current_player,num_players;
	
	/** The players. */
	List<JLabel> players = new ArrayList<JLabel>();

	/**
	 * Instantiates a new game play view.
	 *
	 * @param numberOfPlayers the number of players
	 */
	public GamePlayView(Integer numberOfPlayers) {
		this.num_players = numberOfPlayers;
		this.setBounds(100, 600, 320, 390);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		for(int i = 1;i<=numberOfPlayers;i++){
			JLabel lblPlayer = new JLabel("Player "+i);
			lblPlayer.setBounds(15, 15+(i-1)*50, 265, 50);
			if(i==1){
				lblPlayer.setBorder(new LineBorder(Color.BLACK, 2));
			}
			getContentPane().add(lblPlayer);
			players.add(lblPlayer);
		}
		
		this.setVisible(true);
		setAlwaysOnTop(true);
	}

	/* (non-Javadoc)
	 * @see ca.concordia.app.view.IView#setActionListener(java.awt.event.ActionListener)
	 */
	@Override
	public void setActionListener(ActionListener actionListener) {
		
	}

	/* (non-Javadoc)
	 * @see ca.concordia.app.view.IView#setMouseListener(java.awt.event.MouseListener)
	 */
	@Override
	public void setMouseListener(MouseListener mouseListener) {
		
	}
	
	/**
	 * Change current player.
	 */
	public void changeCurrentPlayer(){
		players.get(current_player).setBorder(null);
		if(++current_player==num_players){
			current_player = 0;
		}
		players.get(current_player).setBorder(new LineBorder(Color.BLACK, 2));
	}
}
