import java.sql.*;
import java.util.Scanner;

import com.mysql.jdbc.PreparedStatement;

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
		
		ResultSet result = null;
		int rows = 0;
		boolean flag = false;
		String command = null;
		PreparedStatement ps = null;
		do{
			System.out.println("\n\tPlease Enter the SQL Query :");
			stringscan = new Scanner(System.in);
			command = stringscan.nextLine();
			ps = (PreparedStatement) connection.prepareStatement(command);
			command = command.toUpperCase();
		try {
			if (command.contains("SELECT"))
				ps.executeQuery();
			else
				rows = ps.executeUpdate();
			flag = true;
		} 
		catch (SQLException e) {
			System.out.println(e.getMessage());
			flag = false;
		}} while (flag == false);
		
		result = ps.getResultSet();
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
			if (result.next())
			{
				while (result.next())
				{
					for (int i=1; i<=metadata.getColumnCount(); i++)
						System.out.printf("%-" + metadata.getColumnDisplaySize(i) + "s", result.getString(i));
					System.out.println();
				}
			}
			else
			{
				System.out.println("NO RECORDS FOUND");
			}
			System.out.printf(String.format("%" + width + "s \n","").replace(" ", "="));
		}
		else
		{
			System.out.println();
			System.out.println(rows + " rows updated..!");
		}
		
	}

}
