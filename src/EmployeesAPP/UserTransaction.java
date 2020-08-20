package EmployeesAPP;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class UserTransaction
{
	private String date;
	private int employeenr;
	private int id_account;
	private String menutype;
	private double amount;
	private double before;
	private double after;
	private boolean isfinanced = false;
	
	public UserTransaction(String date, int employeenr, int id_account, String menutype, double amount, double after, boolean isfinanced)
	{
		this.date = date;
		this.employeenr = employeenr;
		this.id_account = id_account;
		this.menutype = menutype;
		this.amount = Math.round(amount * 100.0 ) / 100.0;
		this.isfinanced = isfinanced;
		
		if(User.getUserTransactionList().isEmpty())
			this.after = Math.round(after * 100.0 ) / 100.0;
		else
			this.after = User.getUserTransactionList().get(User.getUserTransactionList().size() - 1).before;
			
		if(menutype.equals("NULL"))
			before = Math.round((this.after - this.amount) * 100.0 ) / 100.0;
		else if(!isfinanced)
			before = Math.round((this.after + this.amount) * 100.0 ) / 100.0;
		else if(isfinanced)
		{
			this.amount = Math.round(amount * 50.0 ) / 100.0;
			before = Math.round((this.after + this.amount) * 100.0 ) / 100.0;
		}
			
		User.getUserTransactionList().add(this);
	}
	
	public static void chargeCard(int amount, int id_account)
	{	
		DBConnector.obtainConnection();
		DBConnector.updateQuery("INSERT INTO Charge_card(id_account, id_employee, amount) VALUES (" + id_account + ", " + Employee.getCurrentEmployee().getID() + ", " + amount + ")");
		DBConnector.updateQuery("UPDATE Account SET balance = balance +" + amount + " WHERE id = " + id_account);
		DBConnector.closeConnection();
		JOptionPane.showMessageDialog(null, "Charging operation successfully done!", "Charge card", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void sellMenuUser(int id_account, String menutype)
	{
		if(!(User.getSelectedUser().isIsprofessor()) && User.canUseFund(id_account) && User.hasEnoughMoney(id_account, menutype))
		{
			DBConnector.obtainConnection();
			DBConnector.updateQuery("UPDATE Government_fund SET topay = topay + ((SELECT price FROM Meal_type WHERE name = '" + menutype + "') ::float /2)");
			DBConnector.updateQuery("UPDATE Account SET balance = balance - ((SELECT price FROM Meal_type WHERE name = '" + menutype + "')::float/2) WHERE id = " + id_account);
			DBConnector.updateQuery("INSERT INTO Meal(id_account, id_employee, mealtypename, isfinanced) VALUES (" + id_account + ", " + Employee.getCurrentEmployee().getID() + ", '" + menutype + "', true)");
			DBConnector.closeConnection();
			JOptionPane.showMessageDialog(null, "Selling operation successfully done!", "Sell menu", JOptionPane.INFORMATION_MESSAGE);
		}
		 
		else if(User.hasEnoughMoney(id_account, menutype))
		{
			DBConnector.obtainConnection();
			DBConnector.updateQuery("UPDATE Account SET balance = balance - (SELECT price FROM Meal_type WHERE name = '" + menutype + "') WHERE id = " + id_account);
			DBConnector.updateQuery("INSERT INTO Meal(id_account, id_employee, mealtypename) VALUES (" + id_account + ", " + Employee.getCurrentEmployee().getID() + ", '" + menutype + "')");
			DBConnector.closeConnection();
			JOptionPane.showMessageDialog(null, "Selling operation successfully done!", "Sell menu", JOptionPane.INFORMATION_MESSAGE);
		}
		
		else if(!User.hasEnoughMoney(id_account, menutype))
			JOptionPane.showMessageDialog(null, "Not enough money!", "Sell menu", JOptionPane.ERROR_MESSAGE);
	}
	
	public static void sellMenuExternal(String name, String surname, String menutype)
	{
		DBConnector.obtainConnection();
		DBConnector.updateQuery("INSERT INTO External_person(name, surname) VALUES(" + "'" + name + "'" + ", " + "'" + surname + "'" + ")");
		DBConnector.updateQuery("INSERT INTO Meal(id_externalperson, id_employee, mealtypename) VALUES ((SELECT MAX(id) FROM external_person WHERE name =" + "'" + name + "'" + "AND surname =" + "'" + surname + "'), " + Employee.getCurrentEmployee().getID() + ", '" + menutype + "')");
		DBConnector.closeConnection();
		JOptionPane.showMessageDialog(null, "Selling operation successfully done!", "Sell menu", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void retrieveOneUserTransactions(int id, String from, String to)
	{
		User.getUserTransactionList().clear();
		
		ResultSet rs = null;
		DBConnector.obtainConnection();
		
		try
		{
			rs = DBConnector.executeQuery("(select timedate, id_employee, 'NULL' mealtypename, amount, balance, false isfinanced from charge_card, Account where id_account =" + id + " AND Account.id = " + id + " AND timedate BETWEEN '" + from + "' AND '" + to + "') union all (select timedate, id_employee, mealtypename, price, balance, isfinanced from Meal, Meal_type, Account where id_account = " + id + " AND Account.id = " + id + " AND Meal.mealtypename = Meal_type.name AND timedate BETWEEN '" + from + "' AND '" + to + "') ORDER BY timedate DESC");
			while(rs.next() != false)
				new UserTransaction(String.valueOf(rs.getTimestamp("timedate")), id, rs.getInt("id_employee"), rs.getString("mealtypename"), rs.getDouble("amount"), rs.getDouble("balance"), rs.getBoolean("isfinanced"));	
		}
		
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(null, "SQLException!", "Error!", JOptionPane.ERROR_MESSAGE);
		}
		
		DBConnector.closeConnection();
	}
	
	public static void retrieveAllUsersTransactions(String from, String to)
	{
		User.getUserTransactionList().clear();
		
		ResultSet rs = null;
		DBConnector.obtainConnection();
		
		try
		{
			rs = DBConnector.executeQuery("(select timedate, id_account, id_employee, mealtypename, price amount, balance, isfinanced from meal, meal_type, Account where id_account is not null AND id_account = Account.id AND meal_type.name = meal.mealtypename AND timedate BETWEEN '" + from + "' AND '" + to + "')union all(select timedate, id_account, id_employee,'NULL', amount, balance, false from charge_card, Account WHERE id_account = Account.id AND timedate BETWEEN '" + from + "' AND '" + to + "') ORDER BY timedate desc");
			while(rs.next() != false)
				new UserTransaction(String.valueOf(rs.getTimestamp("timedate")), rs.getInt("id_employee"), rs.getInt("id_account"), rs.getString("mealtypename"), rs.getDouble("amount"), rs.getDouble("balance"), rs.getBoolean("isfinanced"));	
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

	public int getId_account() 
	{
		return id_account;
	}

	public void setId_account(int id_account) 
	{
		this.id_account = id_account;
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

	public double getBefore() 
	{
		return before;
	}

	public void setBefore(double before) 
	{
		this.before = before;
	}

	public double getAfter() 
	{
		return after;
	}

	public void setAfter(double after) 
	{
		this.after = after;
	}

	public boolean isIsfinanced() 
	{
		return isfinanced;
	}
}
