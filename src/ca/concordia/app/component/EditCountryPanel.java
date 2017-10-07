/**
 * 
 */
package ca.concordia.app.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import ca.concordia.app.view.IView;

/**
 * @author hardikfumakiya
 *
 */
public class EditCountryPanel extends JPanel implements IView{


	/**
	 * @throws IOException 
	 * 
	 */
	public EditCountryPanel() throws IOException {
		// TODO Auto-generated constructor stub
		setLayout(new GridLayout(5,2));
		JLabel countryTitle= new JLabel("Country Name");
		add(countryTitle);
		JTextField countryName= new JTextField();
		countryName.setPreferredSize(new Dimension(100, 50));
		add(countryName);
		JLabel neighbourTitle= new JLabel("Select Neighbour");
		add(neighbourTitle);
		JList<String> neighbourCountries= new JList(new String[]{"C1","C2"});
		neighbourCountries.setPreferredSize((new Dimension(100, 200)));
		neighbourCountries.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		neighbourCountries.setVisibleRowCount(6);
		add(new JScrollPane(neighbourCountries));
		
		JPanel buttonPanel =new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		JButton cancelBtn= new JButton("Cancel");
		buttonPanel.add(cancelBtn);
		JButton saveBtn= new JButton("Save Country");
		buttonPanel.add(saveBtn);
		JButton nextBtn= new JButton("Next >");
		buttonPanel.add(nextBtn);
		add(buttonPanel);
		
	}

	@Override
	public void setActionListener(ActionListener actionListener) {
		// TODO Auto-generated method stub
		
	}

	

}
