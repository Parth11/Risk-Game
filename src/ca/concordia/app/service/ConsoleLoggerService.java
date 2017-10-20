package ca.concordia.app.service;

import javax.swing.JTextArea;


public class ConsoleLoggerService{
	
	private static ConsoleLoggerService logger = null;
	
	private JTextArea console = null;

	public static ConsoleLoggerService getInstance(JTextArea jt) {
		if(logger==null)
			logger = new ConsoleLoggerService();
		if(jt!=null)
			logger.console = jt;
		return logger;
	}

	public void write(String s) {
		if(console!=null)
			console.append(s + "\n");
	}

}
