/**
 * 
 */
package ca.concordia.app.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ca.concordia.app.view.IView;

/**
 * @author hardikfumakiya
 *
 */
public class MapEditorPanel extends JPanel implements IView{

	public DrawingBase mapArea;
	
	BufferedImage image;
	
	/**
	 * Instantiates a new map editor panel.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public MapEditorPanel() throws IOException {
		// TODO Auto-generated constructor stub
		setLayout(new BorderLayout());
		image = ImageIO.read(getClass().getResource("/images/grid_background.png"));
        int w = image.getWidth();
        int h = image.getHeight();
		
		mapArea=new DrawingBase();
		mapArea.setBounds(12, 24, 821, 666);
		
		setOpaque(false);
		mapArea.setOpaque(false);
		add(mapArea);
		
	}
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image,0,0,this);
   }
	//@Override
//	public void actionPerformed(ActionEvent e) {
//		// TODO Auto-generated method stub
//		if(e.getSource().equals(mapArea)) {
//			System.out.println("clicked mapArea"+mapArea.getX()+":"+mapArea.getY());
//		}
//	}
//	
	@Override
	public void setActionListener(ActionListener actionListener) {
		//
	}
	@Override
	public void setMouseListener(MouseListener mouseListener) {
		mapArea.addMouseListener(mouseListener);
	}
	

}
