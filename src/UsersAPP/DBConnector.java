package UsersAPP;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class DBConnector 
{
	private static Statement st;
	private static Connection conn;
	
	public static void obtainConnection()
	{
		try
		{
			try 
			{
				Class.forName("org.postgresql.Driver");
			} 
			
			catch (ClassNotFoundException e) 
			{
				JOptionPane.showMessageDialog(null, "ClassNotFoundException!", "Error!", JOptionPane.ERROR_MESSAGE);
			}
			
			conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/canteendb","postgres","password");
			st = conn.createStatement();
		}
		
		catch(SQLException d)
		{
			JOptionPane.showMessageDialog(null, "SQLException!", "Error!", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static void closeConnection()
	{
		try 
		{
			conn.close();
		} 
		
		catch (SQLException e) 
		{
			JOptionPane.showMessageDialog(null, "SQLException!", "Error!", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static ResultSet getTuples(String query)
	{
		ResultSet result = null;
		
		try 
		{
			result = st.executeQuery(query);
		} 
		catch (SQLException e) 
		{
			JOptionPane.showMessageDialog(null, "SQLException!", "Error!", JOptionPane.ERROR_MESSAGE);
		}
		
		return result;
	}
}
