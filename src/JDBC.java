import java.sql.*;
import java.util.*;

public class JDBC
{
	public static Connection connection = null;

	public static String Username = "root", Password = "muk5086";
	public static Scanner stringscan = null;
	public static Scanner intscan = null;
	public static int choice = 0;
	public static Metadata metadata = null;
	public static Query cmdline = null;
	public static Statement statement = null;
	public static String exiting = null;
	static Operat functions;
	
	public static void main (String arg[]) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
	{
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		connection = DriverManager.getConnection("jdbc:mysql:///moviedb", Username, Password);
		functions = new Operat(connection);
		do
		{
			//while(login());
			while(Menu());
			System.out.println("\n\tDo you want to Exit the program? ['Yes' or 'No'] : ");
			stringscan = new Scanner(System.in);
			exiting = stringscan.nextLine().toLowerCase();
			
		} while (!(exiting.equals("yes") || exiting.equals("y") ));
	}
	
	public static boolean login() throws SQLException
	{
		stringscan = new Scanner(System.in);
		System.out.println("\n-----------------LOGIN---------------------");
		System.out.println("\n\tUsername = ");
		Username = stringscan.nextLine();
		System.out.println("\n\tPassword = ");
		Password = stringscan.nextLine();
		statement = connection.createStatement();
		String command = "Select * from users where username = '" + Username + "' and password = '" + Password + "'";
		ResultSet result = statement.executeQuery(command);
		if (result.next())
			return false;
		else
			return true;
	}
	
	public static boolean Menu () throws SQLException
	{

		System.out.println("\n\n-----------------MENU---------------------");
		System.out.println("\n\t1. Print All movies of your favourite star");
		System.out.println("\n\t2. Enter Star details in the Database");
		System.out.println("\n\t3. Add a new Customer");
		System.out.println("\n\t4. Delete existing customer");
		System.out.println("\n\t5. Display the MetaData of Database");
		System.out.println("\n\t6. Query the Database");
		System.out.println("\n\t7. Exit the Menu");
		System.out.println("\n\t8. Exit the program");
		System.out.println("\n\n\t\tEnter your choice :");
		intscan = new Scanner(System.in);
		boolean flag = false;
		do
		{
			try{
			choice = intscan.nextInt();
			}
			catch(InputMismatchException i)
			{
				flag = false;
				break;
			}
			flag = true;
		} while (flag == false);
		
		switch(choice)
		{ 
			case 1:
				functions.movieStars();
				break;
			case 2:
				functions.addStars();
				break;
			case 3:
				functions.addCustomers();
				break;
			case 4:
				functions.deleteCustomers();
				break;
			case 5: 
				metadata = new Metadata(connection);
				Metadata.print();
				break;
			case 6: 
				cmdline = new Query(connection);
				cmdline.display();
				break;
			case 7:
				return false;
			case 8:
				return false;
			default:
				break;
		}
		return true;
		
	}
}