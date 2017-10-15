/**
 * 
 */
package ca.concordia.app.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
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
	
	Image image;
	/**
	 * @throws IOException 
	 * 
	 */
	public MapEditorPanel() throws IOException {
		// TODO Auto-generated constructor stub
		setLayout(new BorderLayout());
		
		setBounds(12, 13, 1446, 975);
		
		mapArea=new DrawingBase();
		add(mapArea);
		
		mapArea.setBounds(12, 24, mapArea.getParent().getWidth(), mapArea.getParent().getHeight());
		
		setOpaque(false);
		mapArea.setOpaque(false);
		
		BufferedImage imageOriginal = ImageIO.read(getClass().getResource("/images/grid_background.png"));
		image = imageOriginal.getScaledInstance(mapArea.getParent().getWidth(), mapArea.getParent().getHeight(), Image.SCALE_SMOOTH);
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
