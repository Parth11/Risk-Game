/**
 * 
 */
package ca.concordia.app.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import ca.concordia.app.view.MapCreaterView;

/**
 * @author hardikfumakiya
 *
 */
public class MapCreatorController implements MouseListener {
	private MapCreaterView mapCreaterView;

	public MapCreatorController() {
		this.mapCreaterView = new MapCreaterView();
		this.mapCreaterView.setMouseListener(this);
		this.mapCreaterView.setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource().equals(mapCreaterView.mapEditor.mapArea)) {
			System.out.println(e.getX()+" "+e.getY());
		}
		else {
			System.out.println("you clicked me:"+e.getSource());
		}
		
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

//	@Override
//	public void actionPerformed(ActionEvent e) {
//		// TODO Auto-generated method stub
//		System.out.println("asdasdas");
//		if(e.getSource().equals(mapCreaterView.mapEditor.mapArea)) {
//			System.out.println("at (x,y):"+mapCreaterView.mapEditor.mapArea.toString());
//		}
//		else {
//			System.out.println("you clicked me:"+e.getSource());
//		}
//	}

}
