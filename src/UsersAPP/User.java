package UsersAPP;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class User 
{
	private static User currentUser = new User();
	private static ArrayList<DBTransaction> mylist = new ArrayList<DBTransaction>();
	
	private int id;
	private String name;
	private String surname;
	private float balance;
	private boolean isprofessor;
	private String password;
	
	public User(int id, String name, String surname, float balance, boolean isprofessor, String password)
	{
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.balance = balance;
		this.isprofessor = isprofessor;
		this.password = password;
		
		currentUser.id = id;
		currentUser.name = name;
		currentUser.surname = surname;
		currentUser.balance = balance;
		currentUser.isprofessor = isprofessor;
		currentUser.password = password;
	}
	
	public User() {}
	
	public static boolean checkExistence(int id, String password)
	{
		boolean found = false;
		
		DBConnector.obtainConnection();
		ResultSet rs = DBConnector.getTuples("SELECT id, name, surname, balance, isprofessor, password FROM Account;");
		
		try 
		{
			while(rs.next() != false)	
				if(id == rs.getInt("id") && password.equals(rs.getString("password")))
				{
					found = true;
					User newuser = new User(id, rs.getString("name"), rs.getString("surname"), rs.getFloat("balance"), rs.getBoolean("isprofessor"), password);
					break;
				}	
		} 
		
		catch (SQLException e) 
		{
			JOptionPane.showMessageDialog(null, "SQLException!", "Error!", JOptionPane.ERROR_MESSAGE);
		}
		
		DBConnector.closeConnection();
		
		return found;
	}
	
	public ArrayList<DBTransaction> getMyList()
	{
		return mylist;
	}
	
	public static User getCurrentUser()
	{
		return currentUser;
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

	public float getBalance() 
	{
		return balance;
	}

	public boolean isIsprofessor() 
	{
		return isprofessor;
	}

	public String getPassword() 
	{
		return password;
	}
}
