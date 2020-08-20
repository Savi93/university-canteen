package EmployeesAPP;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class User 
{
	private static User selectedUser = new User();
	private static ArrayList<UserTransaction> transactionlist = new ArrayList<UserTransaction>();
	
	private int id;
	private String name;
	private String surname;
	private double balance;
	private boolean isprofessor;
	
	public User(int id, String name, String surname, double balance, boolean isprofessor)
	{
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.balance = Math.round(balance * 100.0 ) / 100.0;;
		this.isprofessor = isprofessor;
		
		selectedUser.id = id;
		selectedUser.name = name;
		selectedUser.surname = surname;
		selectedUser.balance = this.balance;
		selectedUser.isprofessor = isprofessor;
	}
	
	public User() {}
	
	public static boolean checkExistence(int id)
	{
		DBConnector.obtainConnection();

		boolean found = false;
		
		try 
		{	
			ResultSet rs;
			
			rs = DBConnector.executeQuery("SELECT id, name, surname, balance, isprofessor FROM Account;");
			
			while(rs.next() != false)	
				if(id == rs.getInt("id"))
				{
					found = true;
					break;
				}
			
			DBConnector.closeConnection();
		}
			
		catch (SQLException e) 
		{
			JOptionPane.showMessageDialog(null, "SQLException!", "Error!", JOptionPane.ERROR_MESSAGE);
		}
		
		return found;
	}
	
	public static void setSelectedUser(int id)
	{
		DBConnector.obtainConnection();
		ResultSet rs = DBConnector.executeQuery("SELECT id, name, surname, balance, isprofessor, password FROM Account;");
		
		try 
		{
			while(rs.next() != false)	
				if(id == rs.getInt("id"))
				{
					User newuser = new User(id, rs.getString("name"), rs.getString("surname"), rs.getFloat("balance"), rs.getBoolean("isprofessor"));
					break;
				}	
		} 
		
		catch (SQLException e) 
		{
			JOptionPane.showMessageDialog(null, "SQLException!", "Error!", JOptionPane.ERROR_MESSAGE);
		}
		
		DBConnector.closeConnection();
	}
	
	public static boolean canUseFund(int id)
	{
		boolean can = false;
		
		DBConnector.obtainConnection();
		ResultSet rs = null;

		rs = DBConnector.executeQuery("SELECT COUNT(*) FROM Meal WHERE id_account = " + id + " AND timedate >= current_date and timedate < current_date + interval '1 day'");
		
		try 
		{
			while(rs.next() != false)
				if(rs.getInt("count") < 3)
				{
					can = true;
					break;
				}
		} 
		
		catch (SQLException e) 
		{
			JOptionPane.showMessageDialog(null, "SQLException!", "Error!", JOptionPane.ERROR_MESSAGE);
		}
		
		DBConnector.closeConnection();
		
		return can;
	}
	
	public static boolean hasEnoughMoney(int id, String menutype)
	{
		
		DBConnector.obtainConnection();
		
		ResultSet rs = null;
		float price = 0;
		float balance = 0;
		boolean has = false;
		
		rs = DBConnector.executeQuery("SELECT price, balance FROM meal_type, Account WHERE meal_type.name = '" + menutype + "' AND id = " + id);
		
		try 
		{
			while(rs.next() != false)
			{
				price = rs.getFloat("price");
				balance = rs.getFloat("balance");
				break;
			}
		} 
		catch (SQLException e) 
		{
			JOptionPane.showMessageDialog(null, "SQLException!", "Error!", JOptionPane.ERROR_MESSAGE);
		}
		
		if(canUseFund(id))
			has = balance > (price / 2);
		else
			has = balance > price;
		
		DBConnector.closeConnection();
		
		return has;
	}
	
	public static ArrayList<Integer> getAllUsersID()
	{
		ArrayList<Integer> userid = new ArrayList<Integer>();
		
		DBConnector.obtainConnection();
		ResultSet rs = null;

		rs = DBConnector.executeQuery("SELECT id FROM Account");
		
		try 
		{
			while(rs.next() != false)
				userid.add(rs.getInt("id"));
		} 
		
		catch (SQLException e) 
		{
			JOptionPane.showMessageDialog(null, "SQLException!", "Error!", JOptionPane.ERROR_MESSAGE);
		}
		
		DBConnector.closeConnection();
		
		
		return userid;
	}
	
	public static User getSelectedUser()
	{
		return selectedUser;
	}
	
	public static ArrayList<UserTransaction> getUserTransactionList()
	{
		return transactionlist;
	}

	public boolean isIsprofessor() 
	{
		return isprofessor;
	}

	public int getID() 
	{
		return id;
	}

	public String getName() 
	{
		return name;
	}

	public String getSurname() 
	{
		return surname;
	}

	public double getBalance() 
	{
		return balance;
	}

	public void setID(int id) 
	{
		this.id = id;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	public void setSurname(String surname) 
	{
		this.surname = surname;
	}

	public void setBalance(float balance) 
	{
		this.balance = balance;
	}

	public void setIsprofessor(boolean isprofessor) 
	{
		this.isprofessor = isprofessor;
	}
}

