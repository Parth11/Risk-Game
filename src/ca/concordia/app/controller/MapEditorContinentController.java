/**
 * 
 */
package ca.concordia.app.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import ca.concordia.app.view.MapEditorContinentView;

/**
 * @author harvi
 *
 */
public class MapEditorContinentController implements ActionListener,MouseListener {
	
	MapEditorContinentView map_continent_view;
	
	public MapEditorContinentController() {

		map_continent_view = new MapEditorContinentView();
		map_continent_view.setActionListener(this);
		map_continent_view.setMouseListener(this);
		map_continent_view.setVisible(true);
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
		// TODO Auto-generated method stub
		
	}

}
