package ca.concordia.app.component;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import ca.concordia.app.view.IView;

public class DrawingBase extends JPanel{
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

}
