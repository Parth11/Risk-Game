
package ca.concordia.app.util;

import java.awt.Component;
import java.lang.Thread.UncaughtExceptionHandler;

import javax.swing.JOptionPane;

/**
 * The Class RiskExceptionHandler.
 *
 * @author harvi
 */
public class RiskExceptionHandler implements UncaughtExceptionHandler {

	/** The parent component. */
	private Component parent_component;
	
	/**
	 * calls the uncaughtException java method
	 * @param t, e
	 */
	@Override
	public void uncaughtException(Thread t, Throwable e) {
		e.printStackTrace();
		JOptionPane.showMessageDialog(parent_component, "Something Went Wrong","Error",JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * Instantiates a new risk exception handler.
	 *
	 * @param parentComponent the parent component
	 */
	public RiskExceptionHandler(Component parentComponent) {
		this.parent_component = parentComponent;
	}

}
