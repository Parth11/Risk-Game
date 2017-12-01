package ca.concordia.app.component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.List;

import javax.swing.JPanel;

import ca.concordia.app.model.Country;
import ca.concordia.app.model.GameMap;

/**
 * The Class DrawingBase is a base panel for the UI of the map.
 * @author AbhinavSingh
 */
public class DrawingBase extends JPanel {

	
	private static final long serialVersionUID = 1L;

	private String display_text = "";
	
	private int x = 0;

	private int y = 0;

	/**
	 * Sets the values on the UI
	 *
	 * @param text
	 *            the text
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 */
	public void setValues(String text, int x, int y) {
		display_text = text;
		this.x = x;
		this.y = y;
		repaint();
	}

	/**
	 * This method gets the dimensions
	 * @return Dimension
	 */
	public Dimension getPreferredSize() {
		return (new Dimension(500, 400));
	}

	/**
	 * For painting the component.
	 * @param g  the graphics
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawString(display_text, x, y);
	}

	/**
	 * This method gets the X coordinates
	 * @return x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Sets the x.
	 *
	 * @param x the new x
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * This method gets the Y coordinate
	 * @return y
	 */
	public int getY() {
		return y;
	}

	/**
	 * Sets the y.
	 *
	 * @param y the new y
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * gives in string format
	 * @return in String format 
	 */
	public String toString() {
		return "(" + getX() + "," + getY() + ")";

	}

	/**
	 * Draw country on the UI as a dot.
	 *@param countryName the country name
	 * @param x the x
	 * @param y the y
	 * @param color the color
	 */

	public void drawCountry(String countryName, int x, int y, Color color) {
		Graphics g = this.getGraphics();
		if (color == null) {
			color = Color.BLACK;
		}
		g.setColor(color);
		g.fillOval(x - 10, y - 10, 20, 20);
		g.drawString(countryName, x + 12, y);
	}

	/**
	 * Connect neighbors on the map as per user input.
	 * @param name the name	
	 *  
	 */
	public void connectNeighbours(String name) {
		GameMap gameMap = GameMap.getInstance();

		Country c = gameMap.getCountryByName(name);

		List<String> neighbours = gameMap.getTerritories().get(c);

		for (String s : neighbours) {
			Country d = gameMap.getCountryByName(s);
			this.getGraphics().drawLine(c.getLocX(), c.getLocY(), d.getLocX(), d.getLocY());
		}

	}

}
