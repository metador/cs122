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
	
	PreparedStatement getmovies= (PreparedStatement) connection.prepareStatement("select distinct * from movies join stars_in_movies on movies.id=stars_in_movies.movie_id  where stars_in_movies.star_id in (select stars.id from stars where stars.first_name=? or stars.last_name=?) ");
	getmovies.setString(1, first_name);
	getmovies.setString(2, last_name);
//	getmovies.setString(3, first_name);
//	getmovies.setString(4, last_name);
	ResultSet result=getmovies.executeQuery();
	System.out.printf(String.format("%508s \n","").replace(" ", "="));
	System.out.printf(String.format("%-50s%-8s%-50s%-200s%-200s\n","Title","Year","Director","Banner Url","Trailer Url"));
	System.out.printf(String.format("%508s \n\n","").replace(" ", "="));
	
	while(result.next()){

		String banner=result.getString("banner_url");
		String space="%20";
		banner=banner.replaceAll(space," ");
	
		String trailer=result.getString("trailer_url");
		trailer=trailer.replaceAll(space," ");
		System.out.printf(String.format("%-50s%-8s%-50s%-200s%-200s\n", result.getString("title"),result.getString("year"),result.getString("director"),banner,trailer) );

	}
	System.out.printf(String.format("\n%508s \n\n","").replace(" ", "="));

	}
	
	public void addStars() throws SQLException{
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
				System.out.println(String.format("============================= %s %s Successfully Added As a Star ===================================== ", first_name,last_name));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				System.out.println(" Date in the wrong format ");
				//e.printStackTrace();
			}
		} while(!flag);
		
		
	}
	public void addCustomers() throws SQLException{
		String statement = new String();
		
		statement= "insert into customers (first_name,last_name,cc_id, address, email,password) values (?,?,?,?,?,?)";
		 String address =""; 
		 String email ="";
		 String password ="";
		 String last_name ="";
		 String first_name  ="";
		 String  cc_id="";
		 boolean bankcheckflag;
		 do{
		boolean flag1=true;
	   bankcheckflag=false;
	   last_name ="";
	   first_name  ="";
	   cc_id="";
	   
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
		
		while(cc_id.equals("")){
		System.out.println(" Please Enter credit card no");
		scan = new Scanner(System.in);
		cc_id=scan.nextLine();
		}
		
		while(address.equals("")){
		System.out.println(" Please address ");
		scan = new Scanner(System.in);
		address=scan.nextLine();
		}
		while(email.equals("")){
		System.out.println(" Please email");
		scan = new Scanner(System.in);
		email=scan.nextLine();
		}
		while(password.equals("")){
		System.out.println(" Please password ");
		scan = new Scanner(System.in);
		password=scan.nextLine();
		}
		
		String bankCheckStatement = "select * from creditcards where first_name=? and last_name =? and id=?";
		 PreparedStatement bankcheck = (PreparedStatement) connection.prepareStatement(bankCheckStatement);
		 bankcheck.setString(1, first_name);
		 bankcheck.setString(2, last_name);
		 bankcheck.setString(3, cc_id);
		 ResultSet res= bankcheck.executeQuery();
		 if(res.next()){
		 
		 
			    PreparedStatement ps = (PreparedStatement) connection.prepareStatement(statement);
				ps.setString(1, first_name);
				ps.setString(2, last_name);
				ps.setString(3, cc_id);
				ps.setString(4, address);
				ps.setString(5, email);
				ps.setString(6, password);
				ps.executeUpdate();
		 }else{
			 System.out.println("");
			 System.out.println("========================No records found with the information you provided========================");
			 System.out.println("============================Please Enter correct credit card information======================== ");
			 bankcheckflag=true;
		   }
		 }while(bankcheckflag);
	
				System.out.println(String.format("============================= %s %s Successfully Added As a Customer===================================== ", first_name,last_name));
		
		
		
	}
	public void deleteCustomers() throws SQLException{
		String statement = new String();
		statement= "delete from customers where first_name=? or last_name=? order by id desc limit 1";
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
		
			    PreparedStatement ps = (PreparedStatement) connection.prepareStatement(statement);
				ps.setString(1, first_name);
				ps.setString(2, last_name);
				ps.executeUpdate();
				System.out.println(String.format("============================= Successfully Deleted %s %s From Database ===================================== ", first_name, last_name));
			
	}
}
