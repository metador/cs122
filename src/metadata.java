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
		
		int i = 1, j = 1;
		while (!tab.isEmpty())
		{
			System.out.println("Table " + i + " = " + tab.peek().toUpperCase());
			ResultSet iter_table = query.executeQuery("desc " + tab.pop());
			j = 1;
			while(iter_table.next())
			{
				System.out.print("Name of column " + j + " is " + iter_table.getString(1).toUpperCase());
				System.out.println("\t\t\t\tType of column " + j + " is " + iter_table.getString(2).toUpperCase());
				j++;
			}
			System.out.println();
			i++;
		}
		
	}

}
