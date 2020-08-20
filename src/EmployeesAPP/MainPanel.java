package EmployeesAPP;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class MainPanel extends JPanel
{
	private JTabbedPane window;
	
	public MainPanel()
	{
		Frame.setVisibleLogout();
		Frame.setVisibleGovernment();
		Frame.setVisibleDelete();
		window = new JTabbedPane();
		window.setBackground(Color.lightGray);
		SellChargePanel sellchargepanel = new SellChargePanel();
		StatisticPanel statisticpanel = new StatisticPanel();
		setLayout(new GridLayout());
		window.add("Sell/Charge", sellchargepanel);
		window.add("Statistics", statisticpanel);
		window.setSelectedIndex(Frame.getSelectedStatisticPanel());
		
		add(window);
	}
}
