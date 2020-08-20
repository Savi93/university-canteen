package EmployeesAPP;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class ExternalTransaction 
{
	private String date;
	private int employeenr;
	private String name;
	private String surname;
	private String menutype;
	private double amount;
	private boolean isfinanced = false;
	
	private static ArrayList<ExternalTransaction> transactionlist = new ArrayList<ExternalTransaction>();
	
	public ExternalTransaction(String date, String name, String surname, int employeenr, String menutype, double amount)
	{
		this.date = date;
		this.employeenr = employeenr;
		this.name = name;
		this.surname = surname;
		this.menutype = menutype;
		this.amount = Math.round(amount * 100.0 ) / 100.0;
		
		transactionlist.add(this);
	}

	public static void retrieveAllExternalTransactions(String from, String to)
	{
		ResultSet rs = null;
		getTransactionlist().clear();
		
		DBConnector.obtainConnection();
		
		try
		{
			rs = DBConnector.executeQuery("select timedate, External_person.name, External_person.surname, id_employee, mealtypename, price from meal, meal_type, external_person WHERE id_externalperson = external_person.id AND meal_type.name = mealtypename AND timedate BETWEEN '" + from + "' AND '" + to + "' ORDER BY timedate desc");
			while(rs.next() != false)
				new ExternalTransaction(String.valueOf(rs.getTimestamp("timedate")), rs.getString("name"),  rs.getString("surname"), rs.getInt("id_employee"), rs.getString("mealtypename"), rs.getDouble("price"));	
		}
		
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(null, "SQLException!", "Error!", JOptionPane.ERROR_MESSAGE);
		}
		
		DBConnector.closeConnection();
	}

	public String getDate() 
	{
		return date;
	}

	public void setDate(String date) 
	{
		this.date = date;
	}

	public int getEmployeenr() 
	{
		return employeenr;
	}

	public void setEmployeenr(int employeenr) 
	{
		this.employeenr = employeenr;
	}

	public String getName() 
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	public String getSurname() 
	{
		return surname;
	}

	public void setSurname(String surname) 
	{
		this.surname = surname;
	}

	public String getMenutype() 
	{
		return menutype;
	}

	public void setMenutype(String menutype) 
	{
		this.menutype = menutype;
	}

	public double getAmount() 
	{
		return amount;
	}

	public void setAmount(double amount) 
	{
		this.amount = amount;
	}

	public boolean isIsfinanced() 
	{
		return isfinanced;
	}

	public void setIsfinanced(boolean isfinanced) 
	{
		this.isfinanced = isfinanced;
	}

	public static ArrayList<ExternalTransaction> getTransactionlist() 
	{
		return transactionlist;
	}
}
