package UsersAPP;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class TransactionsPanel extends JPanel
{
	private JLabel welcometext;
	private JLabel boughtlabel;
	private JLabel chargedlabel;
	private JLabel balancelabel;
	private JTable table;
	private JScrollPane scroller;
	private JLabel refresh;
	private JPanel container1;
	private JPanel container2;
	private JPanel container3;
	
	public TransactionsPanel()
	{
		Frame.setVisibleLogout();
		
		User.checkExistence(User.getCurrentUser().getID(), User.getCurrentUser().getPassword());
		
		Font font = new Font("Arial", Font.BOLD, 25);
		setLayout(new GridLayout(3,1));
		
		container1 = new JPanel();
		container1.setBackground(Color.LIGHT_GRAY);
		container1 = new JPanel(new GridLayout(8,2));
		
		container2 = new JPanel();
		container2.setBackground(Color.LIGHT_GRAY);
		container2 = new JPanel(new GridLayout(1,1));
		
		container3 = new JPanel();
		container3.setBackground(Color.LIGHT_GRAY);
		container3 = new JPanel(new GridLayout(1,1));
		
		welcometext = new JLabel("Welcome user " + User.getCurrentUser().getName() + " " + User.getCurrentUser().getSurname() + " !");
		welcometext.setFont(font);
		welcometext.setForeground(Color.red);
		boughtlabel = new JLabel("*BLACK COLOR: Buying operations");
		chargedlabel = new JLabel("*BLUE COLOR: Charging operations");
		chargedlabel.setForeground(Color.blue);
		balancelabel = new JLabel("Actual balance: " + User.getCurrentUser().getBalance() + " €");
		balancelabel.setFont(font);
		balancelabel.setForeground(Color.red);
		
		DBTransaction.retrieveTransactions();;
		
	    DefaultTableModel model = new DefaultTableModel();
	    model.addColumn("Date");
	    model.addColumn("Employee nr.");
	    model.addColumn("Menu type");
	    model.addColumn("Amount €");
	    model.addColumn("Before €");
	    model.addColumn("After €");

	    for(DBTransaction tr : User.getCurrentUser().getMyList())
	    	model.addRow(new Object[] {tr.getDate(), tr.getEmployeenr(), tr.getMenutype(), tr.getAmount(), tr.getBefore(), tr.getAfter()});
	    
		table = new JTable(model);
		table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
		table.getTableHeader().setForeground(Color.red);
		table.getColumnModel().getColumn(0).setPreferredWidth(150);
		table.setEnabled(false);
		table.setFont(new Font("Serif", Font.ITALIC, 15));
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		table.setBackground(Color.lightGray);
		
		for(int j = 0; j < table.getRowCount(); j++)
			if(table.getValueAt(j, 2).equals("NULL"))
			{
				table.addRowSelectionInterval(j, j);
				table.setSelectionForeground(Color.blue);
				table.setSelectionBackground(Color.LIGHT_GRAY);
			}	
			
		scroller = new JScrollPane(table);
		scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		refresh = new JLabel(new ImageIcon("refresh.png"));
		refresh.addMouseListener(new refresh_click());
		
		container1.add(welcometext);
		container1.add(new JPanel());
		container1.add(balancelabel);
		container1.add(new JPanel());
		container1.add(new JPanel());
		container1.add(new JPanel());
		container1.add(new JPanel());
		container1.add(new JPanel());
		container1.add(new JPanel());
		container1.add(new JPanel());
		container1.add(new JPanel());
		container1.add(new JPanel());
		container1.add(new JPanel());
		container1.add(new JPanel());
		container1.add(chargedlabel);
		container1.add(boughtlabel);
		
		container2.add(scroller);
		container3.add(refresh);
		add(container1);
		add(container2);
		add(container3);
	}
	
	private class refresh_click implements MouseListener
	{

		@Override
		public void mouseClicked(MouseEvent arg0) {}
		@Override
		public void mouseEntered(MouseEvent arg0) {}
		@Override
		public void mouseExited(MouseEvent arg0) {}
		@Override
		public void mousePressed(MouseEvent arg0)
		{
			Frame.refreshTransactionPanel();
		}
		@Override
		public void mouseReleased(MouseEvent arg0) {}
	}
}
