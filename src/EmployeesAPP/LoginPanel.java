package EmployeesAPP;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginPanel extends JPanel
{
	private JLabel logoname;
	private JLabel logo;
	private JTextField userenter;
	private JTextField pswenter;
	private JLabel userlabel;
	private JLabel pswlabel;
	private JLabel enterbutton;
	private JPanel container1;
	private JPanel container2;
	private JPanel container3;
	private JPanel container4;
	private JPanel container5;
	
	public LoginPanel()
	{
		Font myFont = new Font("MyFont", 25, 25);
		logoname = new JLabel(new ImageIcon("employee_title.png"));
		logo = new JLabel(new ImageIcon("meal2.png"));
		userenter = new JTextField();
		userenter.setFont(myFont);
		pswenter = new JPasswordField();
		pswenter.setFont(myFont);
		userlabel = new JLabel("ID: ");
		userlabel.setFont(myFont);
		userlabel.setForeground(Color.red);
		pswlabel = new JLabel("Password: ");
		pswlabel.setFont(myFont);
		pswlabel.setForeground(Color.red);
		enterbutton = new JLabel(new ImageIcon("login.png"));
		enterbutton.addMouseListener(new enter_click());
		
		setLayout(new GridLayout(5,1));
		
		container1 = new JPanel(new GridLayout());
		container1.add(logoname);
		container1.setBackground(Color.LIGHT_GRAY);
		container2 = new JPanel(new GridLayout());
		container2.add(logo);
		container2.setBackground(Color.LIGHT_GRAY);
		container3 = new JPanel(new GridLayout());
		container3.setBackground(Color.LIGHT_GRAY);
		container4 = new JPanel(new GridLayout(3,2));
		container4.add(userlabel);
		container4.add(userenter);
		container4.add(pswlabel);
		container4.add(pswenter);
		JPanel filler = new JPanel();
		filler.setBackground(Color.lightGray);
		container4.add(filler);
		container4.add(filler);
		container4.setBackground(Color.LIGHT_GRAY);
		container5 = new JPanel(new GridLayout(1,1));
		container5.add(enterbutton);
		container5.setBackground(Color.LIGHT_GRAY);
		
		add(container1);
		add(container2);
		add(container3);
		add(container4);
		add(container5);
	}
	
	private class enter_click implements MouseListener
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
			try
			{
				Integer.valueOf(userenter.getText());
				
				if(Employee.checkExistence(Integer.valueOf(userenter.getText()), pswenter.getText()))
					Frame.openMainPanel();
				else
				{
					JOptionPane.showMessageDialog(null, "Invalid ID or Password!", "Error!", JOptionPane.ERROR_MESSAGE);
					userenter.setText("");
					pswenter.setText("");
				}	
			}
			
			catch(NumberFormatException e)
			{
				JOptionPane.showMessageDialog(null, "Invalid ID or Password!", "Error!", JOptionPane.ERROR_MESSAGE);
				userenter.setText("");
				pswenter.setText("");
			}
		}
		@Override
		public void mouseReleased(MouseEvent arg0) {}
	}
}
