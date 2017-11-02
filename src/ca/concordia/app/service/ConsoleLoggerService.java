package ca.concordia.app.service;

import javax.swing.JTextArea;

/**
 * The Class ConsoleLoggerService.
 * @author Hardik
 */
public class ConsoleLoggerService{
	
	/** The logger. */
	private static ConsoleLoggerService logger = null;
	
	/** The console. */
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
		if(jt!=null)
			logger.console = jt;
		return logger;
	}

	/**
	 * Write.
	 *
	 * @param s the s
	 */
	public void write(String s) {
		if(console!=null){
			console.append(s + "\n");
			console.repaint();
		}
	}

}
