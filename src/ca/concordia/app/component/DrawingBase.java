package ca.concordia.app.component;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

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
}
