import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Scanner;

import com.mysql.jdbc.CallableStatement;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.StatementImpl;

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
		ResultSet result=null;
		query= connection.createStatement();
		PreparedStatement getmovies;
		int id =0;
		String first_name="";
		name = new Scanner(System.in);
	System.out.println(" Enter First name or Star id: ");
	boolean flag_id=true;
	try{
	first_name = name.nextLine();
	id = Integer.parseInt(first_name);
	}catch(NumberFormatException i){
		flag_id=false;
	}
	
	if(flag_id){
		getmovies= (PreparedStatement) connection.prepareStatement("select distinct * from movies join stars_in_movies on movies.id=stars_in_movies.movie_id  where stars_in_movies.star_id in (select stars.id from stars where stars.id=? ) ");
		getmovies.setInt(1, id);
		result=getmovies.executeQuery();
	}else{
	//first_name= name.nextLine();

	System.out.println(" Enter Last name ");
	String last_name= name.nextLine();
	
	getmovies= (PreparedStatement) connection.prepareStatement("select distinct * from movies join stars_in_movies on movies.id=stars_in_movies.movie_id  where stars_in_movies.star_id in (select stars.id from stars where stars.first_name=? and stars.last_name=?) ");
	getmovies.setString(1, first_name);
	getmovies.setString(2, last_name);
//	getmovies.setString(3, first_name);
//	getmovies.setString(4, last_name);
    result=getmovies.executeQuery();
    
    if(!result.next()){
    	getmovies= (PreparedStatement) connection.prepareStatement("select distinct * from movies join stars_in_movies on movies.id=stars_in_movies.movie_id  where stars_in_movies.star_id in (select stars.id from stars where stars.first_name=? or stars.last_name=?) ");
    	getmovies.setString(1, first_name);
    	getmovies.setString(2, last_name);
    	result=getmovies.executeQuery();
     }
	}
	

	
	System.out.printf(String.format("%508s \n","").replace(" ", "="));
	System.out.printf(String.format("%-50s%-8s%-50s%-200s%-200s\n","Title","Year","Director","Banner Url","Trailer Url"));
	System.out.printf(String.format("%508s \n\n","").replace(" ", "="));
if(result.next()){
	result.beforeFirst();
	while(result.next()){

		String banner=result.getString("banner_url");
		String space="%20";
		banner=banner.replaceAll(space," ");
	
		String trailer=result.getString("trailer_url");
		trailer=trailer.replaceAll(space," ");
		System.out.printf(String.format("%-50s%-8s%-50s%-200s%-200s\n", result.getString("title"),result.getString("year"),result.getString("director"),banner,trailer) );

	}}
    else{
	System.out.println("NO RECORDS FOUND");
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
			 System.out.println();
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
	
	
	public void addMovies() throws SQLException{
	
			String statement = new String();
			
			statement= "call movie_adder(";
			 String star_firstname =""; 
			 String star_lastname ="";
			 String password ="";
			 String director_name ="";
			 String movie_name  ="";
			 String  genre_name="";
			 boolean bankcheckflag;
		
			 while(movie_name.equals("")){
			System.out.println(" Please movie name ");
			scan = new Scanner(System.in);
			movie_name=scan.nextLine();
			movie_name.replaceAll("'","''");
			 }
			 while(director_name.equals("")){
			System.out.println(" Please directors name ");
			scan = new Scanner(System.in);
			director_name=scan.nextLine();
			director_name.replaceAll("'","''");
			 }
			 int movie_year = 0;
			 while(movie_year <1900 || movie_year>2015){
					System.out.println(" Please enter correct date of release ");
					scan = new Scanner(System.in);
					movie_year=scan.nextInt();
					
					 }
			
			 query = connection.createStatement();
		    String moviec="select * from moviedb.movies where title ='"+movie_name+"' and director='"+director_name+"' and year='"+movie_year+"'";

		
			ResultSet moviecheck = query.executeQuery(moviec);
			
			if (moviecheck.next()){
				System.out.println("================================= The Movie "+movie_name+" already exists in database===========================================");
				return;
			}
			
			String final_comments = "================================= New movie title added ================================= \n";
			while(genre_name.equals("")){
				System.out.println(" Please Enter genre of movie");
				scan = new Scanner(System.in);
				genre_name=scan.nextLine();
				genre_name.replaceAll("'","''");
				}
			String genrec = "{call getGenreId (?,?)}";;
			int genre_check=197;
			CallableStatement call_query= (CallableStatement) connection.prepareCall(genrec);
			call_query.setString(1, genre_name);
			call_query.registerOutParameter(2,java.sql.Types.INTEGER);
			
			try{
				call_query.execute();
			genre_check = call_query.getInt(2);
			//System.out.print("genre check asd "+genre_check);
			}
			
			catch (SQLException e){
				System.out.print("genre check exception"+e);
			}
			
			
			if(genre_check == 0){
				System.out.println(" Genre not found in database");
				final_comments = final_comments+"================================= New Genre added ================================= \n ";
			}
			int star_id =  validateStars();
			
			String moviequery = "{call movie_adders (?,?,?,?,?)}";;
			//int genre_check=197;
			 call_query= (CallableStatement) connection.prepareCall(moviequery);
			 call_query.setString(1, movie_name);
			 call_query.setString(2, director_name);
			 call_query.setInt(3, movie_year);
			 call_query.setString(4, genre_name);
			 call_query.setInt(5, star_id);
			 call_query.executeUpdate();
			if(call_query.executeUpdate()==1){
	
			final_comments+="================================= gerne_in_movies table updated ================================= \n ";
			final_comments+="================================= stars_in_movies table updated ================================= \n ";
			
			}
			System.out.print(final_comments);
		}
	
	
	public int validateStars() throws SQLException{

		String statement = new String();
		String checkstatement = new String();
		checkstatement="select * from stars where first_name=? and last_name = ?";
		statement= "insert into stars (first_name,last_name,dob) values (?,?,?)";
		boolean flag1=true;
	   String last_name ="";String first_name  ="";;
		int star_id=0;
		while(flag1){
			flag1=false;
		System.out.println(" Please Enter Star's First name ");
		scan = new Scanner(System.in);
		first_name=scan.nextLine();
		first_name.replaceAll("[^a-zA-Z0-9]","");
		System.out.println(" Please Enter  Star's Last name ");
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
		 PreparedStatement pscheck = (PreparedStatement) connection.prepareStatement(checkstatement);
		 pscheck.setString(1, first_name);
		 pscheck.setString(2, last_name);
		 ResultSet rs = pscheck.executeQuery();
		 if(!rs.next()){
		java.sql.Date sqlDate;
		boolean flag= false;
		do{
			System.out.println(" Enter date of birth in YYYY/MM/DD format ");
			scan = new Scanner(System.in);
			String date =scan.next();
			DateFormat format = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
			java.util.Date d;
			flag= false;
			System.out.println(" star not found in databse ");
			try {
				d = format.parse(date);
			
			    sqlDate = new java.sql.Date(d.getTime());
			    
			    flag= true;
			    PreparedStatement ps = (PreparedStatement) connection.prepareStatement(statement);
				ps.setString(1, first_name);
				ps.setString(2, last_name);
				ps.setDate(3, sqlDate);
				ps.executeUpdate();
				rs =pscheck.executeQuery();
				rs.next();
				star_id=rs.getInt(1);
				System.out.println(String.format("============================= %s %s Successfully Added As a Star ===================================== ", first_name,last_name));
			return star_id;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				System.out.println(" Date in the wrong format ");
				//e.printStackTrace();
			}
		} while(!flag);
		
		 }else{
			 rs.beforeFirst();
			 int count=0;
			 System.out.println(" Star found in database");
			 while(rs.next()){
				 count++;
					System.out.println(count+" is "+ rs.getString("first_name")+" "+rs.getString("last_name"));
			 }
			 
			 if(count>1){
				 System.out.println("Please enter correct star no");
				 int pick =  scan.nextInt();
				 rs.beforeFirst();
				 while(rs.next()){
					 if(count==pick)
						 star_id=rs.getInt("id");
					 return star_id;
				 }
			 }else{
				 rs.last();
				 star_id=rs.getInt("id");
				 return star_id;
			 }
	     }
    return star_id;
	}

}

