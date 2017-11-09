package ca.concordia.app.service;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

import ca.concordia.app.model.GamePlayEvent;
import ca.concordia.app.model.Player;

/**
 * The Class ConsoleLoggerService. Logs the events of the Game
 * @author Hardik
 */
public class ConsoleLoggerService implements Observer{
	
	private static ConsoleLoggerService logger = null;
	
	private JTextArea console = null;

	/**
	 * Gets the single instance of ConsoleLoggerService.
	 *
	 * @param jt the jt
	 * @return single instance of ConsoleLoggerService
	 */
	public static ConsoleLoggerService getInstance(JTextArea jt) {
		if(logger==null)
			logger = new ConsoleLoggerService();
		if(jt!=null){
			logger.console = jt;
			DefaultCaret caret = (DefaultCaret) logger.console.getCaret();
			caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
			logger.console.setCaret(caret);
		}
		return logger;
	}

	/**
	 * Writes on the JTestArea
	 *
	 * @param s the s
	 */
	public void write(String s) {
		if(console!=null){
			console.append(s + "\n");
			console.repaint();
		}
	}
	/**
	 * in-build observer method of the Observer class. Update based on the event changed
	 */
	@Override
	public void update(Observable o, Object arg) {
		Player p =  (Player) o;
		GamePlayEvent e = p.event_log.get(p.event_log.size()-1);
		
		switch(e.getEvent_type()){
		case ATTACK_COUNTRY:
			break;
		case FORTIFY_COUNTRY:
			break;
		case REFINFORCE_COUNTRY:
			break;
		case REINFORCE_ARMY_ALLOCATION:
			break;
		case START_ARMY:
			break;
		case START_ARMY_ALLOCATION:
			write(p.getName()+" -> Receives -> "+e.getEvent_payload().get("initialArmies")+"\n");
			break;
		case START_COUNTRY:
			write(p.getName()+" -> controls the country -> "+e.getEvent_payload().get("countryName")+"\n");
			break;
		default:
			break;
		
		}
		
	}

}
