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

public class SellChargePanel extends JPanel
{
	private JLabel welcometext;
	private JLabel chargelabel;
	private JLabel fullmenulabel;
	private JLabel lightmenulabel;
	private JLabel extralightmenulabel;
	private JPanel container1;
	
	public SellChargePanel()
	{
		Font font = new Font("Arial", Font.BOLD, 25);
		
		setBackground(Color.lightGray);
		setLayout(new GridLayout(1,1));
		
		container1 = new JPanel();
		container1.setLayout(new GridLayout(6,1));
		container1.setBackground(Color.lightGray);
		
		welcometext = new JLabel("  Welcome employee " + Employee.getCurrentEmployee().getName() + " " + Employee.getCurrentEmployee().getSurname() + " !");
		welcometext.setForeground(Color.red);
		welcometext.setFont(font);
		
		chargelabel = new JLabel(new ImageIcon("Immagine1.1.png"));
		chargelabel.addMouseListener(new charge_click());
		fullmenulabel = new JLabel(new ImageIcon("Immagine1.2.png"));
		fullmenulabel.addMouseListener(new full_click());
		lightmenulabel = new JLabel(new ImageIcon("Immagine1.3.png"));
		lightmenulabel.addMouseListener(new light_click());
		extralightmenulabel = new JLabel(new ImageIcon("Immagine1.4.png"));
		extralightmenulabel.addMouseListener(new extra_click());
		container1.add(welcometext);
		container1.add(chargelabel);
		container1.add(fullmenulabel);
		container1.add(lightmenulabel);
		container1.add(extralightmenulabel);
		
		add(container1);
	}
	
	public void sellMenu(String menutype)
	{
		try
		{
			int question = JOptionPane.showConfirmDialog(null, "Is that an user?", "Sell menu", JOptionPane.YES_NO_OPTION);
			
			if(question == JOptionPane.YES_OPTION)
			{
				int id = Integer.valueOf(JOptionPane.showInputDialog(null, "Insert the Card ID: ", "Sell menu", JOptionPane.QUESTION_MESSAGE));
				
				if(User.checkExistence(id))
				{
					User.setSelectedUser(id);
					UserTransaction.sellMenuUser(id, menutype);
				}
					
				else
					throw new NumberFormatException();
			}
			
			else if(question == JOptionPane.NO_OPTION)
			{
				try
				{
					String name = "";
					String surname = "";
					name = JOptionPane.showInputDialog(null, "Insert the name: ", "Sell menu", JOptionPane.QUESTION_MESSAGE);
					surname = JOptionPane.showInputDialog(null, "Insert the surname: ", "Sell menu", JOptionPane.QUESTION_MESSAGE);
						
					if(!name.equals("") && !surname.equals(""))
						UserTransaction.sellMenuExternal(name, surname, menutype);
						
					else
						throw new NullPointerException();
				}
				
				catch(NullPointerException e)
				{
					JOptionPane.showMessageDialog(null, "Invalid name or surname!", "Error!", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		}
		
		catch(NumberFormatException e)
		{
			JOptionPane.showMessageDialog(null, "Invalid ID!", "Error!", JOptionPane.ERROR_MESSAGE);
		}	
	}
	
	public class charge_click implements MouseListener
	{
		@Override
		public void mouseClicked(MouseEvent arg0) {}
		@Override
		public void mouseEntered(MouseEvent arg0) {}
		@Override
		public void mouseExited(MouseEvent arg0) {}
		@Override
		public void mouseReleased(MouseEvent arg0) {}
		@Override
		public void mousePressed(MouseEvent arg0) 
		{
			try
			{
				int id = Integer.valueOf(JOptionPane.showInputDialog(null, "Insert the Card ID: ", "Card charge", JOptionPane.QUESTION_MESSAGE));
				
				if(User.checkExistence(id))
				{
					try
					{
						int amount = Integer.valueOf(JOptionPane.showInputDialog(null, "Insert the amount of Euros you want to charge: ", "Card charge", JOptionPane.QUESTION_MESSAGE));
						UserTransaction.chargeCard(amount, id);
					}
					
					catch(NumberFormatException e)
					{
						JOptionPane.showMessageDialog(null, "Invalid amount!", "Error!", JOptionPane.ERROR_MESSAGE);
					}
				}
				
				else
					throw new NumberFormatException();
			}
			
			catch(NumberFormatException e)
			{
				JOptionPane.showMessageDialog(null, "Invalid ID!", "Error!", JOptionPane.ERROR_MESSAGE);
			} 
		}
	}
	
	public class full_click implements MouseListener
	{
		@Override
		public void mouseClicked(MouseEvent arg0) {}
		@Override
		public void mouseEntered(MouseEvent arg0) {}
		@Override
		public void mouseExited(MouseEvent arg0) {}
		@Override
		public void mouseReleased(MouseEvent arg0) {}
		@Override
		public void mousePressed(MouseEvent arg0) 
		{
			final String menutype = "Full menu";
			sellMenu(menutype);
		}
	}
	
	public class light_click implements MouseListener
	{
		@Override
		public void mouseClicked(MouseEvent arg0) {}
		@Override
		public void mouseEntered(MouseEvent arg0) {}
		@Override
		public void mouseExited(MouseEvent arg0) {}
		@Override
		public void mouseReleased(MouseEvent arg0) {}
		@Override
		public void mousePressed(MouseEvent arg0) 
		{
			final String menutype = "Light menu";
			sellMenu(menutype);
		}
	}
	
	public class extra_click implements MouseListener
	{
		@Override
		public void mouseClicked(MouseEvent arg0) {}
		@Override
		public void mouseEntered(MouseEvent arg0) {}
		@Override
		public void mouseExited(MouseEvent arg0) {}
		@Override
		public void mouseReleased(MouseEvent arg0) {}
		@Override
		public void mousePressed(MouseEvent arg0) 
		{
			final String menutype = "Extra light menu";
			sellMenu(menutype);
		}
	}
}

