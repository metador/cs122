import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Scanner;

import com.mysql.jdbc.PreparedStatement;

public class Operat
{ 	
	private static Connection connection;
	private static Statement query = null;
	private static Scanner name;
	private static Scanner scan;
	
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
/*	String firstLetter = first_name.substring(0,1);
	String rem = first_name.substring(1,first_name.length());
	firstLetter.toUpperCase();
	rem.toLowerCase();
	first_name=firstLetter.concat(rem); */
	System.out.println(" Enter Last name ");
	String last_name= name.next();
 //===============================================================================//
	// handel cases //
	
//=======================================================================================//
	
	PreparedStatement getmovies= (PreparedStatement) connection.prepareStatement("select * from movies join stars_in_movies on movies.id=stars_in_movies.movie_id  where stars_in_movies.star_id in (select stars.id from stars where stars.first_name=? or stars.last_name=?) ");
	getmovies.setString(1, first_name);
	getmovies.setString(2, last_name);
	ResultSet result=getmovies.executeQuery();
	System.out.printf(String.format("%508s \n","").replace(" ", "="));
	System.out.printf(String.format("%-50s%-8s%-50s%-200s%-200s\n","Title","Year","Director","Banner Url","Trailer Url"));
	System.out.printf(String.format("%508s \n\n","").replace(" ", "="));
	
	while(result.next()){
		String banner=result.getString("banner_url");
		//banner.replaceAll("[%20]","");
	//	System.out.println(banner);
		String trailer=result.getString("trailer_url");
		//trailer.replaceAll("%20","");
		System.out.printf(String.format("%-50s%-8s%-50s%-200s%-200s\n", result.getString("title"),result.getString("year"),result.getString("director"),banner,trailer) );
	}
	System.out.printf(String.format("\n%508s \n\n","").replace(" ", "="));

	}
	
	public void insertStars() throws SQLException{
		String statement = new String();
		statement= "insert into stars (first_name,last_name,dob) values (?,?,?)";
		boolean flag1=true;
	   String last_name ="";String first_name  ="";;
		
		while(flag1){
			flag1=false;
		System.out.println(" Please Enter First name ");
		scan = new Scanner(System.in);
		first_name=scan.nextLine();
		first_name.replaceAll("[^a-zA-Z0-9]","");
		System.out.println(" Please Enter Last name ");
		scan = new Scanner(System.in);
		last_name=scan.nextLine();
		last_name.replaceAll("[^a-zA-Z0-9]","");
		
		if(first_name.equals("") && last_name.equals(""))  {
			System.out.println("Please Enter at least one of the first name or Last Name");
			flag1=true;
		}
		}
		if(last_name.equals("")){
			last_name=first_name;
			first_name="";
		}
		java.sql.Date sqlDate;
		boolean flag= false;
		do{
			System.out.println(" Enter date of birth in YYYY/MM/DD format ");
			scan = new Scanner(System.in);
			String date =scan.next();
			DateFormat format = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
			java.util.Date d;
			flag= false;
			try {
				d = format.parse(date);
			
			    sqlDate = new java.sql.Date(d.getTime());
			    
			    flag= true;
			    PreparedStatement ps = (PreparedStatement) connection.prepareStatement(statement);
				ps.setString(1, first_name);
				ps.setString(2, last_name);
				ps.setDate(3, sqlDate);
				ps.executeUpdate();
				System.out.println("=============================Operation Successfull===================================== ");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				System.out.println(" Date in the wrong format ");
				//e.printStackTrace();
			}
		} while(!flag);
		
		
	}
	public void deleteStars() throws SQLException{
		String statement = new String();
		statement= "delete from stars (first_name,last_name,dob) values (?,?,?)";
		boolean flag1=true;
	   String last_name ="";String first_name  ="";;
		
		while(flag1){
			flag1=false;
		System.out.println(" Please Enter First name ");
		scan = new Scanner(System.in);
		first_name=scan.nextLine();
		first_name.replaceAll("[^a-zA-Z0-9]","");
		System.out.println(" Please Enter Last name ");
		scan = new Scanner(System.in);
		last_name=scan.nextLine();
		last_name.replaceAll("[^a-zA-Z0-9]","");
		
		if(first_name.equals("") && last_name.equals(""))  {
			System.out.println("Please Enter at least one of the first name or Last Name");
			flag1=true;
		}
		}
		if(last_name.equals("")){
			last_name=first_name;
			first_name="";
		}
		java.sql.Date sqlDate;
		boolean flag= false;
		do{
			System.out.println(" Enter date of birth in YYYY/MM/DD format ");
			scan = new Scanner(System.in);
			String date =scan.next();
			DateFormat format = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
			java.util.Date d;
			flag= false;
			try {
				d = format.parse(date);
			
			    sqlDate = new java.sql.Date(d.getTime());
			    
			    flag= true;
			    PreparedStatement ps = (PreparedStatement) connection.prepareStatement(statement);
				ps.setString(1, first_name);
				ps.setString(2, last_name);
				ps.setDate(3, sqlDate);
				ps.executeUpdate();
				System.out.println("=============================Operation Successfull===================================== ");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				System.out.println(" Date in the wrong format ");
				//e.printStackTrace();
			}
		} while(!flag);
		
		
	}
}
