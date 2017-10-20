package ca.concordia.app.service;

import javax.swing.JTextArea;

import ca.concordia.app.util.GameLogger;

public class MyLogger extends GameLogger {
	
	private static MyLogger logger = null;
	
	private JTextArea jt = null;

	public static MyLogger getInstance(JTextArea jt) {
		if(logger==null)
			logger = new MyLogger();
		if(jt!=null)
			logger.jt = jt;
		return logger;
	}

	@Override
	public void write(String s) {
		if(jt!=null)
			jt.append(s + "\n");
	}

}
