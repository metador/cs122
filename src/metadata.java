import java.sql.*;
import java.util.ArrayList;
import java.util.Stack;

public class Metadata 
{
	private static Connection connection = null;
	private static Statement query = null;
	
	public Metadata(Connection connection) 
	{
		this.connection = connection;
	}

	public static void print() throws SQLException
	{
		query = connection.createStatement();
		
		ResultSet tables = query.executeQuery("show tables");
		Stack<String> tab = new Stack<String>();
		
		while (tables.next())
			tab.push(tables.getString(1));
		
		int i = 1;
		while (!tab.isEmpty())
		{
			System.out.printf(String.format("%-50s %-50s \n","Table " + i, tab.peek().toUpperCase()));
			System.out.printf(String.format("%100s \n","").replace(" ", "="));
			System.out.printf(String.format("%-50s %-50s \n","Column Name","Column Type"));
			System.out.printf(String.format("%100s \n","").replace(" ", "="));
			ResultSet iter_table = query.executeQuery("desc " + tab.pop());
			while(iter_table.next())
				System.out.printf(String.format("%-50s %-50s \n",iter_table.getString(1).toUpperCase(),iter_table.getString(2).toUpperCase()));
			System.out.printf(String.format("%100s \n\n\n\n","").replace(" ", "="));
			i++;
		}
		
	}

}
