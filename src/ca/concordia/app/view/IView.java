
package ca.concordia.app.view;

import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

/**
 * The Interface IView.
 *
 * @author harvi
 */
public interface IView {
	
	/**
	 * Sets the action listener.
	 *
	 * @param actionListener the new action listener
	 */
	public void setActionListener(ActionListener actionListener);

	/**
	 * Sets the mouse listener.
	 *
	 * @param mouseListener the new mouse listener
	 */
	public void setMouseListener(MouseListener mouseListener);
}
