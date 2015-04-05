import java.sql.*;
import java.util.*;

public class JDBC
{
	public static Connection connection = null;
	public static String Username = "root", Password = "decodder";
	public static Scanner stringscan = null;
	
	public static void main (String arg[]) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
	{
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		
		connection = DriverManager.getConnection("jdbc:mysql:///moviedb", Username, Password);
		//while(login());
		Menu();
		
		Statement select = connection.createStatement();
		
		Operat operation = new Operat(select);
		
		operation.Select();
		
		
	}
	
	public static boolean login()
	{
		stringscan = new Scanner(System.in);
		System.out.println("-----------------LOGIN---------------------");
		System.out.println("\n\tUsername = ");
		Username = stringscan.nextLine();
		System.out.println("\n\tPassword = ");
		Password = stringscan.nextLine();
		return false;
	}
	
	public static void Menu ()
	{
		System.out.println("-----------------MENU---------------------");
		System.out.println("\n\t1. Print All movies of your favourite star");
		System.out.println("\n\t2. Search for a Movie Star");
		System.out.println("\n\t3. Search for Movies acted by a Star");
		System.out.println("\n\t4. Insert a Star");
		System.out.println("\n\t5. Add a new Customer");
		System.out.println("\n\t6. Delete existing customer");
		System.out.println("\n\t7. Exit the Menu");
		System.out.println("\n\t8. Exit the program");
		System.out.println("\n\n\t\t :");
	}
}