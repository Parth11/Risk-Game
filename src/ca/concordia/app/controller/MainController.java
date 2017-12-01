/**
 * 
 */
package ca.concordia.app.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ca.concordia.app.model.SavedGame;
import ca.concordia.app.model.GamePlayEvent.GameMode;
import ca.concordia.app.service.GamePlayService;
import ca.concordia.app.strategies.PlayerStrategy;
import ca.concordia.app.util.GsonUtil;
import ca.concordia.app.util.RiskExceptionHandler;
import ca.concordia.app.util.StrategyAdapter;
import ca.concordia.app.view.MainView;

/**
 * The Class MainController is the initial class to start the game.
 *
 * @author harvi
 */
public class MainController implements ActionListener, MouseListener {

	/** 
	 * Create an object of the MainView class.
	 */
	public MainView main_view;

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		new MainController();
	}

	/**
	 * Instantiates a new main controller.
	 */
	public MainController() {

		main_view = new MainView();
		main_view.setActionListener(this);
		main_view.setMouseListener(this);
		main_view.setVisible(true);

		Thread.setDefaultUncaughtExceptionHandler(new RiskExceptionHandler(main_view));
	}

	/**
	 * Mouse click event
	 * @param e
	 */
	@Override
	public void mouseClicked(MouseEvent e) {

	}

	/**
	 * Mouse pressed event 
	 * @param e
	 */
	@Override
	public void mousePressed(MouseEvent e) {

	}

	/**
	 * Mouse released event 
	 * @param e
	 */
	@Override
	public void mouseReleased(MouseEvent e) {

	}

	/**
	 * Mouse Entered event
	 * @param e
	 */
	@Override
	public void mouseEntered(MouseEvent e) {

	}

	/**
	 * Mouse Exited event
	 * @param e
	 */
	@Override
	public void mouseExited(MouseEvent e) {

	}


	
	/**
	 * Calls the actionPerformed method of java.
	 * @param e
	 * 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource().equals(main_view.create_map)) {
			new MapEditorController(false);
			main_view.dispose();
		} else if (e.getSource().equals(main_view.edit_map)) {
			new MapEditorController(true);
			main_view.dispose();
		} else if (e.getSource().equals(main_view.new_game)) {
			GameMode[] gameMode = { GameMode.SINGLE_GAME, GameMode.TOURNAMENT };

			JFrame frame = new JFrame("Select Game Mode");
			GameMode selectedMode = null;
			while(selectedMode==null) 
			{
				selectedMode = (GameMode) JOptionPane.showInputDialog(frame, "In Which mode you like to play game?",
						"Game Mode", JOptionPane.QUESTION_MESSAGE, null, gameMode, gameMode[0]);
				
				if(selectedMode!=null) 
				{
					
					
					if(selectedMode==GameMode.SINGLE_GAME) {
						GamePlayService.getInstance().setGameMode(GameMode.SINGLE_GAME);
						new NewGameSelectorController(1);
						
					}
					else {
						JFrame maps = new JFrame("Select Number of Maps");
						String[] mapsArray= new String[] {"1","2","3","4","5"};
						int nMaps=0;
						do
						{
							String noOfMaps = (String) JOptionPane.showInputDialog(frame, "How many maps you want in Game?",
									"Tournament Mode", JOptionPane.QUESTION_MESSAGE, null,mapsArray, mapsArray[0]);
							nMaps=Integer.parseInt(noOfMaps);
							
							GamePlayService.getInstance().setGameMode(GameMode.TOURNAMENT);
							
							new NewGameSelectorController(nMaps);
							
						}while(nMaps<1);
						
					}
					
					
					
					break;
				}
			}
			
			main_view.dispose();
		}
		else if(e.getSource().equals(main_view.load_game)){
			JFileChooser file = new JFileChooser();
			int retVal = file.showOpenDialog(main_view);
			
			if(retVal == JFileChooser.APPROVE_OPTION){
				File f = file.getSelectedFile();
				FileReader fr = null;
				try {
					fr = new FileReader(f);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				Gson gson = GsonUtil.getGSONInstance();
				SavedGame savedGame = gson.fromJson(fr, SavedGame.class);
				
				new NewGamePhaseController(savedGame);
				main_view.dispose();
			}
			
		}
	}

}
