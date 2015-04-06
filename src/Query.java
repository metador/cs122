import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class Query 
{
	private static Connection connection = null;
	private static Statement query = null;
	public static Scanner stringscan = null;
	
	public Query(Connection connection) 
	{
		this.connection = connection;
	}

	public void display() throws SQLException 
	{
		System.out.println("\n\tPlease Enter the SQL Query :");
		stringscan = new Scanner(System.in);
		String command = stringscan.nextLine();
		command = command.toUpperCase();
		Statement query = connection.createStatement();
		ResultSet result = query.executeQuery(command);
		
		int columns = result.getMetaData().getColumnCount();
		if (command.contains("SELECT"))
		{
			ResultSetMetaData metadata = result.getMetaData();
			for (int i=1 ; i<=metadata.getColumnCount(); i++)
				System.out.printf("%15s", metadata.getColumnName(i));
		}
		
		while (result.next())
		{
			System.out.println();
			for (int i=1; i<=columns; i++)
				System.out.printf("%15s", result.getString(i));
		}
		
	}

}
