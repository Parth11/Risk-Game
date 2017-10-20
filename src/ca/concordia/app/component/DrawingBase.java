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

	/**
	 * The field is set to blank initially
	 *
	 */
	private String display_text = "";

	/** The x. coordinate */
	private int x = 0;

	/** The y. coordinate */
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#getPreferredSize()
	 */
	public Dimension getPreferredSize() {
		return (new Dimension(500, 400));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawString(display_text, x, y);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#getX()
	 */
	public int getX() {
		return x;
	}

	/**
	 * Sets the x.
	 *
	 * @param x
	 *            the new x
	 */
	public void setX(int x) {
		this.x = x;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#getY()
	 */
	public int getY() {
		return y;
	}

	/**
	 * Sets the y.
	 *
	 * @param y
	 *            the new y
	 */
	public void setY(int y) {
		this.y = y;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.Component#toString()
	 */
	public String toString() {
		return "(" + getX() + "," + getY() + ")";

	}

	/**
	 * Draw country on the UI as a dot.
	 *
	 * @param x
	 *            the x
	 * @param y
	 *            the y
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
	 * Connect neighbours on the map as per user input.
	 *
	 * @param name
	 *            the name
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
