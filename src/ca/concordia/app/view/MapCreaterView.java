package ca.concordia.app.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ca.concordia.app.component.EditCountryPanel;
import ca.concordia.app.component.MapEditorPanel;

public class MapCreaterView extends MainView {

	private String title = "Create Map";
	public MapEditorPanel mapEditor;
	JPanel mainPanel ;
	public JButton button;
	
	public MapCreaterView() {
		this.initWindow();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void initWindow() {
		// TODO Auto-generated method stub

		this.setTitle(title);
		GridLayout layout= new GridLayout(0,1,10,10);
	
		setLayout(layout);
		
		setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		
	    mainPanel = new JPanel();
		
		
		EditCountryPanel countryPanel;
		try {
			mapEditor = new MapEditorPanel();
			
			mainPanel.add(mapEditor);
			
			countryPanel= new EditCountryPanel();
			mainPanel.add(countryPanel);
			Container contentPane = getContentPane();
			contentPane.add(mainPanel);
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		


		this.pack();
		this.setLocationRelativeTo(null);
		this.setResizable(false);

	}

	@Override
	public void setActionListener(ActionListener actionListener) {
		super.setActionListener(actionListener);
		mapEditor.setActionListener(actionListener);
	}

}
