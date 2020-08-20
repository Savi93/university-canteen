package EmployeesAPP;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

public class Frame
{
	private static JFrame frame;
	private static LoginPanel loginpanel;
	private static MainPanel mainpanel;
	private static JMenuItem close;
	private static JMenu logoutmenu;
	private static JMenu governmentmenu;
	private static JMenu deletemenu;
	private static int selectedItemStatistic;
	private static int selectedStatisticPanel;
	private static String fromdate = "";
	private static String todate = "";
	
	public static void main(String[] args) 
	{
		try
		{
			for(LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
			{
				if("Nimbus".equals(info.getName()))
				{
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		}
		
		catch(Exception e){}
		
		frame = new JFrame("EmployeesAPP");
		loginpanel = new LoginPanel();
		
		JMenuBar bar = new JMenuBar();
		JMenu aboutmenu = new JMenu("About");
		logoutmenu = new JMenu("Logout");
		logoutmenu.setVisible(false);
		governmentmenu = new JMenu("Government");
		governmentmenu.setVisible(false);
		deletemenu = new JMenu("Delete");
		deletemenu.setVisible(false);
		JMenuItem deletebydate = new JMenuItem("Delete older");
		JMenu closemenu = new JMenu("Close");
		JMenuItem about = new JMenuItem("About");
		JMenuItem logout = new JMenuItem("Logout");
		JMenuItem government_ok = new JMenuItem("Sum paid!");
		JMenuItem government_topay = new JMenuItem("Amount");
		close = new JMenuItem("Close");
		
		close.addActionListener(new close_click());
		logout.addActionListener(new logout_click());
		about.addActionListener(new about_click());
		government_topay.addActionListener(new government_topay_click());
		government_ok.addActionListener(new government_ok_click());
		deletebydate.addActionListener(new delete_by_date_click());
		
		frame.add(loginpanel);
		
		logoutmenu.add(logout);
		aboutmenu.add(about);
		closemenu.add(close);
		governmentmenu.add(government_topay);
		governmentmenu.add(government_ok);
		deletemenu.add(deletebydate);
		
		bar.add(aboutmenu);
		bar.add(closemenu);
		bar.add(logoutmenu);
		bar.add(governmentmenu);
		bar.add(deletemenu);
		
		frame.setVisible(true);
		frame.setSize(800, 670);
		frame.setJMenuBar(bar);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(2);
	}
	
	public static void openMainPanel()
	{
		mainpanel = new MainPanel();
		frame.getContentPane().removeAll();
		frame.add(mainpanel);
		frame.revalidate();
	}
	
	public static void refreshMainPanel()
	{
		setSelectedStatisticPanel();
		frame.getContentPane().removeAll();
		frame.add(new MainPanel());
		frame.revalidate();
	}
	
	public static void setVisibleLogout()
	{
		logoutmenu.setVisible(true);
	}
	
	public static void setVisibleGovernment()
	{
		governmentmenu.setVisible(true);
	}
	
	public static void setVisibleDelete()
	{
		deletemenu.setVisible(true);
	}
	
	public static String getFromDate()
	{
		return fromdate;
	}
	
	public static String getToDate()
	{
		return todate;
	}
	
	public static void setFromDate(String from)
	{
		fromdate = from;
	}
	
	public static void setToDate(String to)
	{
		todate = to;
	}
	
	public static void setSelectedItemStatistic(int index)
	{
		selectedItemStatistic = index;
	}
	
	public static int getSelectedItemStatistic()
	{
		return selectedItemStatistic;
	}
	
	public static int getSelectedStatisticPanel()
	{
		return selectedStatisticPanel;
	}
	
	public static void setSelectedStatisticPanel()
	{
		selectedStatisticPanel = 1;
	}
	
	public static void setSelectedSellChargePanel()
	{
		selectedStatisticPanel = 0;
	}
	
	public static class close_click implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			frame.dispose();
		}
	}
	
	public static class logout_click implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			Frame.setSelectedSellChargePanel();
			Frame.setFromDate("");
			Frame.setToDate("");
			Frame.setSelectedItemStatistic(0);
			frame.dispose();
			main(new String[0]);
		}
	}
	
	public static class about_click implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			JOptionPane.showMessageDialog(null, "Developed by SAVEV DAVID\nAll rights reserved.", "Information", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	public static class government_ok_click implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			DBConnector.obtainConnection();
			DBConnector.updateQuery("UPDATE Government_fund SET topay = 0");
			JOptionPane.showMessageDialog(null, "The sum was successfully paid by the government!", "Government amount paid", JOptionPane.INFORMATION_MESSAGE);
			DBConnector.closeConnection();
		}
	}
	
	public static class government_topay_click implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			ResultSet rs = null;
			double topay = 0;
			
			DBConnector.obtainConnection();
			rs = DBConnector.executeQuery("SELECT * FROM Government_fund");
			
			try 
			{
				while(rs.next() != false)
					topay = rs.getFloat("topay");
				
				topay = Math.round(topay * 100.0 ) / 100.0;
				JOptionPane.showMessageDialog(null, "The sum to be paid by the government is: " + topay + " €", "Amount to pay by government", JOptionPane.INFORMATION_MESSAGE);
				
			} 
			
			catch (SQLException e) 
			{
				JOptionPane.showMessageDialog(null, "SQLException!", "Error!", JOptionPane.ERROR_MESSAGE);
			}
			
			DBConnector.closeConnection();
		}
	}
	
	public static class delete_by_date_click implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			try
			{
				int days = Integer.valueOf(JOptionPane.showInputDialog(null, "Delete transactions & external people older than this amount of days: ", "Delete older transactions and external people", JOptionPane.QUESTION_MESSAGE));
				
				if(days <= 0)
					throw new NumberFormatException();
				
				else
				{
					ResultSet rs = null;
					
					DBConnector.obtainConnection();
					
					int deletedmeal = DBConnector.updateQuery("DELETE FROM Meal WHERE timedate < (NOW() - INTERVAL '" + days + "days')");
					int deletedexternal = DBConnector.updateQuery("DELETE FROM External_person WHERE (Select COUNT(*) FROM meal WHERE id_externalperson = External_person.id) = 0");
					int deletedcharge = DBConnector.updateQuery("DELETE FROM Charge_card WHERE timedate < (NOW() - INTERVAL '" + days + "days')");
					
					DBConnector.closeConnection();
					
					JOptionPane.showMessageDialog(null, "Deleted bought meals: " + deletedmeal + "\nDeleted charge operations: " + deletedcharge + "\nDeleted external person: " + deletedexternal, "Rows affected", JOptionPane.INFORMATION_MESSAGE);
				}
			}
			
			catch(NumberFormatException e)
			{
				JOptionPane.showMessageDialog(null, "Invalid timeframe!", "Error!", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
