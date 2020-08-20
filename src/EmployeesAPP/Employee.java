package EmployeesAPP;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import UsersAPP.DBConnector;

public class Employee 
{
	private static Employee currentEmployee = new Employee();
	private int id;
	private String name;
	private String surname;
	private String password;
	
	public Employee(int id, String name, String surname, String password)
	{
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.password = password;
		
		currentEmployee.id = id;
		currentEmployee.name = name;
		currentEmployee.surname = surname;
		currentEmployee.password = password;
	}
	
	public Employee(){}
	
	public static boolean checkExistence(int id, String password)
	{
		boolean found = false;;
		DBConnector.obtainConnection();
		ResultSet rs = DBConnector.getTuples("SELECT id, name, surname, password FROM Employee;");
		
		try 
		{
			while(rs.next() != false)	
				if(id == rs.getInt("id") && password.equals(rs.getString("password")))
				{
					found = true;
					Employee employee = new Employee(id, rs.getString("name"), rs.getString("surname"), password);
					break;
				}	
		} 
		
		catch (SQLException e) 
		{
			JOptionPane.showMessageDialog(null, "SQLException!", "Error!", JOptionPane.ERROR_MESSAGE);
		}
		
		return found;
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

	public String getPassword() 
	{
		return password;
	}
	
	public static Employee getCurrentEmployee()
	{
		return currentEmployee;
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

	public void setPassword(String password) 
	{
		this.password = password;
	}
}
