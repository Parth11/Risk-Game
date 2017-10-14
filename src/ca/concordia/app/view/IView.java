/**
 * 
 */
package ca.concordia.app.view;

import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

/**
 * @author harvi
 *
 */
public interface IView {
	
	public void setActionListener(ActionListener actionListener);

	public void setMouseListener(MouseListener mouseListener);
}
