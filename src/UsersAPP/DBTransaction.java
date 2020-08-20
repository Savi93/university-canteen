package UsersAPP;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class DBTransaction 
{
	private String date;
	private int employeenr;
	private String menutype;
	private double amount;
	private double before;
	private double after;
	
	public DBTransaction(String date, int employeenr, String menutype, double amount, double after, boolean isfinanced)
	{
		this.date = date;
		this.employeenr = employeenr;
		this.menutype = menutype;
		this.amount = Math.round(amount * 100.0 ) / 100.0;
		
		if(User.getCurrentUser().getMyList().isEmpty())
			this.after = Math.round(after * 100.0 ) / 100.0;
		else
			this.after = User.getCurrentUser().getMyList().get(User.getCurrentUser().getMyList().size() - 1).before;
			
		if(menutype.equals("NULL"))
			before = Math.round((this.after - this.amount) * 100.0 ) / 100.0;
		else if(!isfinanced)
			before = Math.round((this.after + this.amount) * 100.0 ) / 100.0;
		else if(isfinanced)
		{
			this.amount = Math.round(amount * 50.0 ) / 100.0;
			before = Math.round((this.after + this.amount) * 100.0 ) / 100.0;
		}
			
		User.getCurrentUser().getMyList().add(this);
	}
	
	public static void retrieveTransactions()
	{
		ResultSet rs = null;
		User.getCurrentUser().getMyList().clear();
		
		DBConnector.obtainConnection();
		
		try
		{
			rs = DBConnector.getTuples("(select timedate, id_employee, 'NULL' mealtypename, amount, balance, false isfinanced from charge_card, Account where id_account =" + User.getCurrentUser().getID() + " AND Account.id = " + User.getCurrentUser().getID() + ") union all (select timedate, id_employee, mealtypename, price, balance, isfinanced from Meal, Meal_type, Account where id_account = " + User.getCurrentUser().getID() + " AND Account.id = " + User.getCurrentUser().getID() + " AND Meal.mealtypename = Meal_type.name) ORDER BY timedate DESC");
			while(rs.next() != false)
				new DBTransaction(String.valueOf(rs.getTimestamp("timedate")), rs.getInt("id_employee"), rs.getString("mealtypename"), rs.getDouble("amount"), rs.getDouble("balance"), rs.getBoolean("isfinanced"));	
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

	public int getEmployeenr() 
	{
		return employeenr;
	}

	public String getMenutype() 
	{
		return menutype;
	}

	public double getAmount() 
	{
		return amount;
	}

	public double getBefore() 
	{
		return before;
	}

	public double getAfter() 
	{
		return after;
	}
}
