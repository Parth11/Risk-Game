/**
 * 
 */
package ca.concordia.app.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * @author harvi
 *
 */
public class MainView extends JFrame implements IView {

	private static final String MAIN_WINDOW_TITLE = "A Clash of Kings";
	
	public MainView() {
		
		this.setTitle(MAIN_WINDOW_TITLE);
		
		
		this.setLocation(100, 100);
		this.setSize(500, 400);
		
		JMenuBar gameMenu = new JMenuBar();
		
		JMenu fileMenu= new JMenu("File");
		JMenuItem newGame = new JMenuItem("New Game");
		
		newGame.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				e.getActionCommand();
				JMenuItem src = (JMenuItem) e.getSource();
				src.getParent();
			}
		});
		
		fileMenu.add(newGame);
		
		JMenu optionsMenu = new JMenu("Options");
		JMenuItem loadMap = new JMenuItem("Load Map");
		optionsMenu.add(loadMap);
		
		JMenu aboutMenu = new JMenu("About");
		
		gameMenu.add(fileMenu);
		gameMenu.add(optionsMenu);
		gameMenu.add(aboutMenu);
		
		this.setJMenuBar(gameMenu);
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	@Override
	public void setActionListener(ActionListener actionListener) {
		// TODO Auto-generated method stub
		
	}

}
