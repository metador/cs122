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
		ResultSet result = null;
		try {
			result = query.executeQuery(command);
		} catch (SQLException e) {
			System.out.println("Illegal SQL statement...!!");
			return;
		}
		
		int columns = result.getMetaData().getColumnCount();
		int width = 0;
		if (command.contains("SELECT"))
		{
			ResultSetMetaData metadata = result.getMetaData();
			for (int i=1 ; i<=metadata.getColumnCount(); i++)
				width += metadata.getColumnDisplaySize(i);
			System.out.printf(String.format("\n\n\n%" + width + "s \n","").replace(" ", "="));
			for (int i=1 ; i<=metadata.getColumnCount(); i++)
				System.out.printf("%-" + metadata.getColumnDisplaySize(i) + "s", metadata.getColumnLabel(i).toUpperCase());
			System.out.printf(String.format("\n%" + width + "s \n","").replace(" ", "="));
			while (result.next())
			{
				for (int i=1; i<=columns; i++)
					System.out.printf("%-" + metadata.getColumnDisplaySize(i) + "s", result.getString(i));
				System.out.println();
			}
			System.out.printf(String.format("%" + width + "s \n","").replace(" ", "="));
		}
		else
		{
			while (result.next())
			{
				System.out.println();
				for (int i=1; i<=columns; i++)
					System.out.printf("%15s", result.getString(i));
			}
		}
		
	}

}
