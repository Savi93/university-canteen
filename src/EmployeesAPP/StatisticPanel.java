package EmployeesAPP;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

public class StatisticPanel extends JPanel
{
	Date current;
	private JLabel fromlabel;
	private JDateChooser fromdate;
	private JTextFieldDateEditor editor1;
	private JLabel tolabel;
	private JDateChooser todate;
	private JTextFieldDateEditor editor2;
	private JLabel incomeslabel;
	private double balance;
	private JLabel balancelabel;
	private double fisicalincomes;
	private double virtualincomes;
	private JLabel govern_contributionlabel;
	private double contribution;
	private JLabel selledlabel;
	private int selled;
	private JLabel boughtlabel;
	private JLabel chargedlabel;
	private JLabel searchlabel;
	private JComboBox searchbyid;
	private JTable table;
	private JScrollPane scroller;
	private JLabel reload;
	private JPanel container1;
	private JPanel container2;
	private JPanel container3;
	
	public StatisticPanel()
	{
		Frame.setVisibleLogout();
		
		Font font = new Font("Arial", Font.BOLD, 16);
		Font font2 = new Font("Arial", Font.BOLD, 13);

		setLayout(new GridLayout(3,1));
		
		current = new Date();
		current = Calendar.getInstance().getTime();
		
		fromdate = new JDateChooser();
		fromlabel = new JLabel("FROM: ");
		fromdate.setDateFormatString("yyyy-MM-dd");
	
		todate = new JDateChooser();
		tolabel = new JLabel("TO: ");
		todate.setDateFormatString("yyyy-MM-dd");
		
		editor1 = (JTextFieldDateEditor) fromdate.getDateEditor();
		editor1.setEditable(false);
		editor1.setBackground(Color.white);
		
		editor2 = (JTextFieldDateEditor) todate.getDateEditor();
		editor2.setEditable(false);
		editor2.setBackground(Color.white);
		
		balancelabel = new JLabel();
		balancelabel.setVisible(false);
		
		if(Frame.getFromDate().equals("") && Frame.getToDate().equals(""))
		{
			fromdate.setDate(current);
			todate.setDate(current);
		}
		
		else
		{
			editor1.setText(Frame.getFromDate());
			editor2.setText(Frame.getToDate());
		}
		
		searchlabel = new JLabel("SELECT HERE: ");
		searchbyid = new JComboBox();
		searchbyid.addItem("ALL external persons");
		searchbyid.addItem("ALL users");
			for(int id : User.getAllUsersID())
				searchbyid.addItem((int)id);
		searchbyid.setSelectedIndex(Frame.getSelectedItemStatistic());
		searchbyid.addItemListener(new item_click());
		
		container1 = new JPanel();
		container1.setBackground(Color.LIGHT_GRAY);
		container1 = new JPanel(new GridLayout(8,2));
		
		container2 = new JPanel();
		container2.setBackground(Color.LIGHT_GRAY);
		container2 = new JPanel(new GridLayout(1,1));
		
		container3 = new JPanel();
		container3.setBackground(Color.LIGHT_GRAY);
		container3 = new JPanel(new GridLayout(1,1));
		
		boughtlabel = new JLabel("*BLACK COLOR: Buying operations");
		chargedlabel = new JLabel("*BLUE COLOR: Charging operations");
		chargedlabel.setForeground(Color.blue);
		
	    DefaultTableModel modeltable = new DefaultTableModel();
		
	    if(String.valueOf(searchbyid.getSelectedIndex()).equals("0"))
	    {
	    	modeltable.addColumn("Date");
	    	modeltable.addColumn("Name");
	    	modeltable.addColumn("Surname");
	    	modeltable.addColumn("Emp. nr.");
	    	modeltable.addColumn("Menu type");
	    	modeltable.addColumn("Price €");
	    	
	    	for(ExternalTransaction tr : ExternalTransaction.getTransactionlist())
			{
				modeltable.addRow(new Object[] {tr.getDate(), tr.getName(), tr.getSurname(), tr.getEmployeenr(), tr.getMenutype(), tr.getAmount()});
				fisicalincomes += tr.getAmount();
				selled++;
			}
	    }
	    
	    else if(String.valueOf(searchbyid.getSelectedIndex()).equals("1"))
	    {
	    	modeltable.addColumn("Date");
	    	modeltable.addColumn("User nr.");
	    	modeltable.addColumn("Emp. nr.");
	    	modeltable.addColumn("Menu type");
	    	modeltable.addColumn("Amount €");
	    	
	    	for(UserTransaction tr : User.getUserTransactionList())
			{
				modeltable.addRow(new Object[] {tr.getDate(), tr.getId_account(), tr.getEmployeenr(), tr.getMenutype(), tr.getAmount()});
	    	
				
				
				if(!tr.getMenutype().equals("NULL"))
				{
					virtualincomes += tr.getAmount();
					selled++;
				}
				
				else
					fisicalincomes += tr.getAmount();
				
				if(tr.isIsfinanced())
					contribution += tr.getAmount();
			}
	    	
	    	virtualincomes = Math.round(virtualincomes * 100.0 ) / 100.0;
	    	contribution = Math.round(contribution * 100.0 ) / 100.0;
	    }  
	   
	    else
	    {
	    	balance = User.getSelectedUser().getBalance();
	    	balancelabel.setText("Actual balance: " + balance + " €");
	    	balancelabel.setFont(font);
	    	balancelabel.setForeground(Color.red);
	    	balancelabel.setVisible(true);
	    	
	    	modeltable.addColumn("Date");
	    	modeltable.addColumn("User nr.");
	    	modeltable.addColumn("Emp. nr.");
	    	modeltable.addColumn("Menu type");
	    	modeltable.addColumn("Amount €");
	    	modeltable.addColumn("Before €");
	    	modeltable.addColumn("After €");
	    	
			for(UserTransaction tr : User.getUserTransactionList())
			{
				modeltable.addRow(new Object[] {tr.getDate(), tr.getId_account(), tr.getEmployeenr(), tr.getMenutype(), tr.getAmount(), tr.getBefore(), tr.getAfter()});
	    	
				
				
				if(!tr.getMenutype().equals("NULL"))
				{
					virtualincomes += tr.getAmount();
					selled++;
				}
				
				else
					fisicalincomes += tr.getAmount();
				
				if(tr.isIsfinanced())
					contribution += tr.getAmount();
			}
			
			virtualincomes = Math.round(virtualincomes * 100.0 ) / 100.0;
			contribution = Math.round(contribution * 100.0 ) / 100.0;
	    }
	    
	    fisicalincomes = Math.round(fisicalincomes * 100.0 ) / 100.0;
	    
	    table = new JTable(modeltable);
	    
	    table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
		table.getTableHeader().setForeground(Color.red);
		table.getColumnModel().getColumn(0).setPreferredWidth(170);
		table.setEnabled(false);
		table.setFont(new Font("Serif", Font.ITALIC, 15));
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		table.setBackground(Color.lightGray);
		
		for(int j = 0; j < table.getRowCount(); j++)
		{
			if(table.getValueAt(j, 3).equals("NULL"))
			{
				table.addRowSelectionInterval(j, j);
				table.setSelectionForeground(Color.blue);
				table.setSelectionBackground(Color.LIGHT_GRAY);
			}
		}
	    
		incomeslabel = new JLabel("Cash IN: " + fisicalincomes + " € Virtual IN: " + virtualincomes + " €");
		incomeslabel.setFont(font2);
		govern_contributionlabel = new JLabel("Govern. contr.: " + contribution + " €");
		govern_contributionlabel.setFont(font2);
		selledlabel = new JLabel("Bought menus: " + selled);
		selledlabel.setFont(font2);
		
		scroller = new JScrollPane(table);
		scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		reload = new JLabel(new ImageIcon("refresh.png"));
		reload.addMouseListener(new reload_click());
		
		container1.add(fromlabel);
		container1.add(new JPanel());
		container1.add(fromdate);
		container1.add(tolabel);
		container1.add(new JPanel());
		container1.add(todate);
		container1.add(searchlabel);
		container1.add(new JPanel());
		container1.add(searchbyid);
		container1.add(new JPanel());
		container1.add(new JPanel());
		container1.add(new JPanel());
		container1.add(incomeslabel);
		container1.add(selledlabel);
		container1.add(govern_contributionlabel);
		container1.add(balancelabel);
		container1.add(new JPanel());
		container1.add(new JPanel());
		container1.add(new JPanel());
		container1.add(new JPanel());
		container1.add(new JPanel());
		container1.add(chargedlabel);
		container1.add(new JPanel());
		container1.add(boughtlabel);
		container2.add(scroller);
		container3.add(reload);

		add(container1);
		add(container2);
		add(container3);
	}
	
	private class reload_click implements MouseListener
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
			User.getUserTransactionList().clear();
			ExternalTransaction.getTransactionlist().clear();
			
			if(searchbyid.getSelectedIndex() == 0)
				ExternalTransaction.retrieveAllExternalTransactions(editor1.getText() + " 00:00:00", editor2.getText() + " 23:59:59");
			else if(searchbyid.getSelectedIndex() == 1)
				UserTransaction.retrieveAllUsersTransactions(editor1.getText() + " 00:00:00", editor2.getText() + " 23:59:59");
			else
			{
				UserTransaction.retrieveOneUserTransactions((int)searchbyid.getSelectedItem(), editor1.getText() + " 00:00:00", editor2.getText() + " 23:59:59");
				User.setSelectedUser((int)searchbyid.getSelectedItem());
			}			
			Frame.setFromDate(editor1.getText());
			Frame.setToDate(editor2.getText());
			Frame.refreshMainPanel();
		}
		@Override
		public void mouseReleased(MouseEvent arg0) {}
	}
	
	private class item_click implements ItemListener
	{
		@Override
		public void itemStateChanged(ItemEvent arg0) 
		{
			User.getUserTransactionList().clear();
			User.getSelectedUser().setBalance(0);
			ExternalTransaction.getTransactionlist().clear();
			
			if(searchbyid.getSelectedIndex() == 0)
				Frame.setSelectedItemStatistic(0);
			else if(searchbyid.getSelectedIndex() == 1)
				Frame.setSelectedItemStatistic(1);
			else
				Frame.setSelectedItemStatistic(searchbyid.getSelectedIndex());
			
			Frame.setFromDate(editor1.getText());
			Frame.setToDate(editor2.getText());
			Frame.refreshMainPanel();
		}
	}
}

