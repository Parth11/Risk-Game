package ca.concordia.app.view;

import java.awt.EventQueue;
import java.util.List;
import java.util.Map.Entry;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import ca.concordia.app.model.TournamentConfiguration;
import ca.concordia.app.model.TournamentResult;

public class TournamentResultView extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3016941960892662513L;
	public JTable table;

	

	/**
	 * Create the application.
	 */
	public TournamentResultView() {
		
		TournamentConfiguration cfg = TournamentConfiguration.getInstance();
		TournamentResult tr = TournamentResult.getInstance();
		
		setBounds(100, 100, 713, 483);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		getContentPane().setLayout(null);
		
		
		table = new JTable();
		table.setBounds(40, 46, 620, 338);
		Object[] columnNames = new Object[cfg.getNum_games()+1];
		columnNames[0] = "Maps";
		for(int i=1;i<=cfg.getNum_games();i++){
			columnNames[i] = "Game "+i;
		}
		Object[][] data = new Object[tr.results.keySet().size()][cfg.getNum_games()+1];
		int i = 0;
		for(Entry<String, List<String>> e : tr.results.entrySet()){
			data[i][0] = e.getKey();
			int j = 1;
			for(String s : e.getValue()){
				data[i][j] = s;
				j++;
			}
			i++;
		}
		TableModel dataModel =  new DefaultTableModel(data , columnNames );
		table.setModel(dataModel );
		getContentPane().add(table);
		
		setVisible(true);
		setModal(true);
	}
}
