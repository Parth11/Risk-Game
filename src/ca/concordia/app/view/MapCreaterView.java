package ca.concordia.app.view;

import java.awt.Component;
import java.awt.Container;
import java.awt.HeadlessException;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MapCreaterView extends JFrame implements IView{

	private String title= "Create Map";
	
	public MapCreaterView()  {
		this.initWindow();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void initWindow() {
		// TODO Auto-generated method stub
		
		this.setTitle(title);
		
		JPanel mainPanel =new JPanel();
		JButton jbtn= new JButton("Hi creator");
		jbtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		mainPanel.add(jbtn);
		Container contentPane= getContentPane();
		contentPane.add(mainPanel);
		
		this.pack();
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		
		
	}

	@Override
	public void setActionListener(ActionListener actionListener) {
		// TODO Auto-generated method stub
		
	}
	

}
