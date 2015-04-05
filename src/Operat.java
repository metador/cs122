import java.sql.*;

public class Operat
{
	private static Statement query = null;
	
	public Operat(Statement query_main) 
	{
		query = query_main;
	}

	
	public static void Select() throws SQLException
	{
		ResultSet result = query.executeQuery("Select * from stars");
		
		System.out.println("The results of the query: ");
		ResultSetMetaData metadata = result.getMetaData();
		System.out.println("There are " + metadata.getColumnCount() + " columns");
		
		for (int i=1 ; i<=metadata.getColumnCount(); i++)
			System.out.println("Type of column " + i + " is " + metadata.getColumnTypeName(i));
		
		while (result.next())
		{
			System.out.println("ID = " + result.getInt(1));
			System.out.println("Name = " + result.getString(2) + result.getString(3));
			System.out.println("DOB = " + result.getString(4));
			System.out.println("Photo URL = " + result.getString(5));
			System.out.println();
		}
	}
}
