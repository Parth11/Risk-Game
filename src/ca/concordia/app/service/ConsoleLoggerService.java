package ca.concordia.app.service;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

import ca.concordia.app.model.Country;
import ca.concordia.app.model.GamePlayEvent;
import ca.concordia.app.model.Player;

/**
 * The Class ConsoleLoggerService. Logs the events of the Game
 * 
 * @author Hardik
 */
public class ConsoleLoggerService implements Observer {

	private static ConsoleLoggerService logger = null;

	private JTextArea console = null;

	/**
	 * Gets the single instance of ConsoleLoggerService.
	 *
	 * @param jt
	 *            the jt
	 * @return single instance of ConsoleLoggerService
	 */
	public static ConsoleLoggerService getInstance(JTextArea jt) {
		if (logger == null)
			logger = new ConsoleLoggerService();
		if (jt != null) {
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
	 * @param s
	 *            the s
	 */
	public void write(String s) {
		if (console != null) {
			console.append(s + "\n");
			console.repaint();
		}
	}

	/**
	 * in-build observer method of the Observer class. Update based on the event
	 * changed
	 */
	@Override
	public void update(Observable o, Object arg) {
		Player p = (Player) o;
		GamePlayEvent e = p.event_log.get(p.event_log.size() - 1);

		switch (e.getEvent_type()) {
		case START_ARMY_ALLOCATION:
			write(p.getName() + " -> Receives -> " + e.getEvent_payload().get("initialArmies") + "\n");
			break;
		case START_COUNTRY:
			write(p.getName() + " -> controls the country -> " + e.getEvent_payload().get("countryName") + "\n");
			break;
		case ATTACK_CAPTURE:
			write(p.getName() + " -> captured the country -> "
					+ ((Country) e.getEvent_payload().get("capturedCountry")).getCountryName() + "\n");
			break;
		case ATTACK_COUNTRY:
			write(p.getName()+" -> attacked the country -> "+e.getEvent_payload().get("defendingCountry")+
					" -> using the country -> "+e.getEvent_payload().get("attackingCountry")+
					" -> with the attack moves -> "+e.getEvent_payload().get("attackThrows")+
					" -> against defence moves -> "+e.getEvent_payload().get("defenceThrows")+
					" -> with the outcomes -> "+e.getEvent_payload().get("attackWin")+"\n");
			break;
		case CARD_EXCHANGE:
			write(p.getName() + " -> exchanged the cards [Artillery,Infantry,Cavalry] -> "
					+ e.getEvent_payload().get("cards") + " -> for armies -> " + e.getEvent_payload().get("armies")
					+ "\n");
			break;
		case CARD_WIN:
			write(p.getName() + " -> won a card of type -> " + e.getEvent_payload().get("card") + "\n");
			break;
		case FORTIFY_COUNTRY:
			write(p.getName() + " -> fortified the country -> " + e.getEvent_payload().get("toCountry")
					+ " -> by moving armies -> " + e.getEvent_payload().get("armies") + " -> from country -> "
					+ e.getEvent_payload().get("fromCountry") + "\n");
			break;
		case REFINFORCE_COUNTRY:
			write(p.getName() + " -> reinforced the country -> " + e.getEvent_payload().get("reinforcedCountry")
					+ " -> with armies ->" + e.getEvent_payload().get("reinforceArmy") + "\n");
			break;
		case REINFORCE_ARMY_ALLOCATION:
			write(p.getName() + " -> allocated armies for reinforcement -> "
					+ e.getEvent_payload().get("reinforcementArmies") + "\n");
			break;
		case START_ARMY_COUNTRY:
			write(p.getName() + " -> placed an army in -> " + e.getEvent_payload().get("countryName") + "\n");
			break;
		case PLAYER_DEAD:
			write(p.getName() + " -> your rule has ended \n");
			break;
		case THE_END:
			write(p.getName() + " -> has won the game" + "\n");
			break;
		default:
			break;

		}

	}

}
