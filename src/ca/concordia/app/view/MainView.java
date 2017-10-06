/**
 * 
 */
package ca.concordia.app.view;

import java.awt.Container;
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

	private static final String MAIN_WINDOW_TITLE = "Risk";
	
	public JMenuBar gameMenu;
	
	public JMenu fileMenu,optionsMenu,aboutMenu;
	
	public JMenuItem newGame,createMap,loadMap;
	
	public MainView() {
		
		this.setTitle(MAIN_WINDOW_TITLE);
		
		
		this.setLocation(100, 100);
		this.setSize(500, 400);
		
		gameMenu = new JMenuBar();
		
		fileMenu= new JMenu("File");
		newGame = new JMenuItem("New Game");
		
		fileMenu.add(newGame);
		
		optionsMenu = new JMenu("Options");
		createMap = new JMenuItem("Create Map");
		optionsMenu.add(createMap);
		loadMap = new JMenuItem("Load Map");
		optionsMenu.add(loadMap);

		
		aboutMenu = new JMenu("About");

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
