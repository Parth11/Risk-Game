/**
 * 
 */
package ca.concordia.app.util;

import java.awt.Component;
import java.lang.Thread.UncaughtExceptionHandler;

import javax.swing.JOptionPane;

/**
 * @author harvi
 *
 */
public class RiskExceptionHandler implements UncaughtExceptionHandler {

	private Component parent_component;
	
	@Override
	public void uncaughtException(Thread t, Throwable e) {
		e.printStackTrace();
		JOptionPane.showMessageDialog(parent_component, "Something Went Wrong","Error",JOptionPane.ERROR_MESSAGE);
	}
	
	public RiskExceptionHandler(Component parentComponent) {
		this.parent_component = parentComponent;
	}

}
