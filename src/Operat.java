import java.sql.*;
import java.util.Scanner;

import com.mysql.jdbc.PreparedStatement;

public class Operat
{ 	
	private static Connection connection;
	private static Statement query = null;
	private static Scanner name;
	
	
	public Operat( Connection connection) 
	{
		this.connection = connection;
	//	query = query_main;
	}

	
	public static void movieStars() throws SQLException
	{
		System.out.println("Which star do you need information about: ");
		query= connection.createStatement();
		name = new Scanner(System.in);
	System.out.println(" Enter First name ");
	String first_name= name.next();
	System.out.println(" Enter Last name ");
	String last_name= name.next();
 //===============================================================================//
	// handel cases //
	
//=======================================================================================//
	
	PreparedStatement getmovies= (PreparedStatement) connection.prepareStatement("select movies.title from movies join stars_in_movies on movies.id=stars_in_movies.movie_id  where stars_in_movies.star_id in (select stars.id from stars where stars.first_name=? or stars.last_name=?) ");
	getmovies.setString(1, first_name);
	getmovies.setString(2, last_name);
	ResultSet result=getmovies.executeQuery();
	
	while(result.next()){
		System.out.println("Movie " + result.getString("title"));
	}
	
	/*	ResultSet result = query.executeQuery("Select * from star where first_name=?");
		
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
		}*/
	}
}
