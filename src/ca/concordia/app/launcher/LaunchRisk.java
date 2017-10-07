/**
 * 
 */
package ca.concordia.app.launcher;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ca.concordia.app.controller.MapCreatorController;
import ca.concordia.app.view.MainView;
import ca.concordia.app.view.MapCreaterView;

/**
 * @author harvi
 *
 */
public class LaunchRisk implements ActionListener {

	private MainView mainView;

	public LaunchRisk() {

		mainView = new MainView();
		
		mainView.setActionListener(this);
	
		mainView.setVisible(true);
	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource().equals(mainView.createMap)){
			
			
			showCreateMapView();
			
			
		}
		
	}

	private void showCreateMapView(){
		new MapCreatorController();
		mainView.dispose();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		LaunchRisk launcher = new LaunchRisk();
		
		//mychange
	}

}
