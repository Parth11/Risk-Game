/**
 * 
 */
package ca.concordia.app.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ca.concordia.app.view.MapCreaterView;

/**
 * @author hardikfumakiya
 *
 */
public class MapCreatorController implements ActionListener {
	private MapCreaterView mapCreaterView;

	public MapCreatorController() {
		this.mapCreaterView = new MapCreaterView();
		this.mapCreaterView.setActionListener(this);
		this.mapCreaterView.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(mapCreaterView.mapEditor.mapArea)) {
			System.out.println("at (x,y):"+mapCreaterView.mapEditor.mapArea.toString());
		}
		else {
			System.out.println("you clicked me:"+e.getSource());
		}
	}

}
