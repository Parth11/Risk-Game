/**
 * 
 */
package ca.concordia.app.component;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import ca.concordia.app.view.IView;

/**
 * @author hardikfumakiya
 *
 */
public class MapEditorPanel extends JPanel implements IView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DrawingBase mapArea;

	Image image;

	/**
	 * Instantiates a new map editor panel.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public MapEditorPanel() throws IOException {
		// TODO Auto-generated constructor stub
		setLayout(new BorderLayout());

		setBounds(12, 13, 1378, 768);

		mapArea = new DrawingBase();
		add(mapArea);

		mapArea.setBounds(12, 24, mapArea.getParent().getWidth(), mapArea.getParent().getHeight());

		setOpaque(false);
		mapArea.setOpaque(false);

		BufferedImage imageOriginal = ImageIO.read(getClass().getResource("/images/grid_background.png"));
		image = imageOriginal.getScaledInstance(mapArea.getParent().getWidth(), mapArea.getParent().getHeight(),
				Image.SCALE_SMOOTH);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, this);
	}

	@Override
	public void setActionListener(ActionListener actionListener) {

	}

	@Override
	public void setMouseListener(MouseListener mouseListener) {
		mapArea.addMouseListener(mouseListener);
	}

}
