package ca.concordia.app.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.concurrent.BrokenBarrierException;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;

import ca.concordia.app.model.Player;
import ca.concordia.app.service.GamePlayService;

import java.awt.GridLayout;

public class CardExchangeView extends JFrame implements IView {
	
	public CardExchangeView()
	{
		
		getContentPane().setLayout(new GridLayout(5, 5));
		//int temNoofCards=15;
		Player p = GamePlayService.getInstance().getCurrentTurnPlayer();
		
		
		int noOfCards= p.getCards().size();
		
		for(int i =0; i <noOfCards;i++)
		{
			JLabel label =  new JLabel(p.cards_list.get(i).getCard_type());
			label.setBorder(BorderFactory.createLineBorder(Color.BLUE));
			label.addMouseListener(null);
			
			getContentPane().add(label);
			
			
	
		}
		initialize();
		
	}
	
	private void initialize() {
		this.setVisible(true);
		setBounds(0, 0, 1000, 800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public static void main(String args[])
	{
		CardExchangeView c = new CardExchangeView();
	}
	

	@Override
	public void setActionListener(ActionListener actionListener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setMouseListener(MouseListener mouseListener) {
		// TODO Auto-generated method stub
		
	}

}
