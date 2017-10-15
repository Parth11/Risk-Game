/**
 * 
 */
package ca.concordia.app.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import ca.concordia.app.view.MainView;

/**
 * @author harvi
 *
 */
public class MainController implements ActionListener, MouseListener {
	
	
	public MainView main_view;
	
	
	public static void main(String[] args){
		new MainController();
	}
	
	public MainController() {
		
		main_view = new MainView();
		main_view.setActionListener(this);
		main_view.setMouseListener(this);
		main_view.setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource().equals(main_view.create_map)){
			new MapEditorController(false);
			main_view.dispose();
		}
		else if(e.getSource().equals(main_view.edit_map)){
			new MapEditorController(true);
			main_view.dispose();
		}
	}

}
