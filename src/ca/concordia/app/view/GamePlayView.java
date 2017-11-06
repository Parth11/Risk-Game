package ca.concordia.app.view;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

public class GamePlayView extends JFrame implements IView{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4197727817255615951L;

	public static void main(String[] args){
		GamePlayView gpv = new GamePlayView(6);
	}
	
	public GamePlayView(Integer numberOfPlayers) {
		
		this.setBounds(700, 400, 320, 390);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		for(int i = 1;i<=numberOfPlayers;i++){
			JLabel lblPlayer = new JLabel("Player "+i);
			lblPlayer.setBounds(15, 15+(i-1)*50, 265, 50);
			if(i==1){
				lblPlayer.setBorder(new LineBorder(Color.BLACK, 2));
			}
			getContentPane().add(lblPlayer);
		}
		
		this.setVisible(true);
		setAlwaysOnTop(true);
	}

	@Override
	public void setActionListener(ActionListener actionListener) {
		
	}

	@Override
	public void setMouseListener(MouseListener mouseListener) {
		
	}
}
