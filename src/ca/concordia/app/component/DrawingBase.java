package ca.concordia.app.component;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JPanel;

import ca.concordia.app.model.Country;
import ca.concordia.app.model.GameMap;
import ca.concordia.app.view.IView;

public class DrawingBase extends JPanel {
	private String clickedAt = "";
    private int x = 0;
    private int y = 0;

    public void setValues(String text, int x, int y)
    {
        clickedAt = text;
        this.x = x;
        this.y = y;
        repaint();
    }

    public Dimension getPreferredSize()
    {
        return (new Dimension(500, 400));
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawString(clickedAt, x, y);
    }

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public String toString()
	{
		return "("+getX() +","+getY()+")";
		
	}
	
	public void drawCountry(int x, int y){
		this.getGraphics().drawOval(x-10, y-10, 20, 20);
	}

	public void connectNeighbours(String name) {
		GameMap gameMap = GameMap.getInstance();
		
		Country c = gameMap.getCountryByName(name);
		
		List<String> neighbours = gameMap.getTerritories().get(c);
		
		for(String s :  neighbours){
			Country d = gameMap.getCountryByName(s);
			this.getGraphics().drawLine(c.getLocX(), c.getLocy(), d.getLocX(), d.getLocy());
		}
		
	}

}
