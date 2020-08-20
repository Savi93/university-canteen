package UsersAPP;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	private static JMenuItem close;
	private static JMenu logoutmenu;
	private static TransactionsPanel transactionspanel;
	
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
		
		frame = new JFrame("UsersAPP");
		loginpanel = new LoginPanel();
		
		JMenuBar bar = new JMenuBar();
		JMenu aboutmenu = new JMenu("About");
		logoutmenu = new JMenu("Logout");
		logoutmenu.setVisible(false);
		JMenu closemenu = new JMenu("Close");
		JMenuItem about = new JMenuItem("About");
		JMenuItem logout = new JMenuItem("Logout");
		close = new JMenuItem("Close");
		
		close.addActionListener(new close_click());
		logout.addActionListener(new logout_click());
		about.addActionListener(new about_click());
		
		frame.add(loginpanel);
		
		logoutmenu.add(logout);
		aboutmenu.add(about);
		closemenu.add(close);
		
		bar.add(aboutmenu);
		bar.add(closemenu);
		bar.add(logoutmenu);
		
		frame.setVisible(true);
		frame.setSize(800, 670);
		frame.setJMenuBar(bar);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(2);
	}
	
	public static void openTransactionsPanel()
	{
		transactionspanel = new TransactionsPanel();
		frame.getContentPane().removeAll();
		frame.add(transactionspanel);
		frame.revalidate();
	}
	
	public static void refreshTransactionPanel()
	{
		transactionspanel = new TransactionsPanel();
		frame.getContentPane().removeAll();
		frame.add(transactionspanel);
		frame.revalidate();
	}
	
	public static void setVisibleLogout()
	{
		logoutmenu.setVisible(true);
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
			frame.dispose();
			Frame.main(new String[0]);
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
		
}
