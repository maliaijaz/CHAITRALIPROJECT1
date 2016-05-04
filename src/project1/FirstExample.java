package project1;

//JDBC Example - printing a database's metadata
//Coded by Chen Li/Kirill Petrov Winter, 2005
//Slightly revised for ICS185 Spring 2005, by Norman Jacobson


import java.sql.*;                              // Enable SQL processing
import static java.lang.System.out; 
import java.util.*;


public class FirstExample
{
	
	
	public static void welcomeMenu() {
		// Intro to program
		System.out.println("Welcome to the Java JBDC Program. We will prompt you with a few questions regarding your favorite movie star and"
				+ " then print out information regarding that movie.");
	}
	
	public static String userInterface() {
		System.out.println("\n-------Please enter what you want to do. Here is a list of possible instructions--------\n"
				+ "Here is the list of exact queries you must enter (it is not case sensitive):\n \nINSERT CUSTOMER \nINSERT STAR \nSEARCH (this looks up a movie star), "
				+ "\nDELETE CUSTOMER \nPRINT METADATA \nCUSTOM QUERY \nQUIT \nLOGOUT" + "\n-------Please enter one of the above and enter LOGOUT if you want to change the database\n------ +"
						+ " Type in QUIT if you want to exit the entire program: \n");
		Scanner instructionScanner = new Scanner(System.in);
		String instruction = instructionScanner.nextLine();
		return instruction;
	}
	
	public static String getDatabase() {
		String database = "";
        while (!database.equals("root")){
        	System.out.println("Please enter the name of the database (root)");
            Scanner databaseName = new Scanner(System.in);
            database = databaseName.nextLine();
        }
        return database;
	}
	
	public static String getPassword(String database) {
		 String password = "";
         while (!password.equals("poop")){
         	System.out.println("Please enter the password (poop) for + " + database + " : ");
         Scanner passwordScanner = new Scanner(System.in);
         password = passwordScanner.nextLine();
         }
         return password;
	}
	
	// Prompt user to enter either a name or an ID
	public static String getNameOrId() {
		
		Scanner idOrNameScanner = new Scanner(System.in); // this will be used to read a user's input to see if they want to search by name or id
        
        System.out.println("Would you like to search a movie by your favorite star's name or ID? Please enter \"name\" or \"id\"");
        
        // Name or ID?
        String nameOrId = idOrNameScanner.nextLine();
        
        System.out.println(nameOrId + " accepted as input.");
        
        return nameOrId;
		
	}
	
	// START---Helper Methods used for searching for a star---
	public static int getNumberOfSpaces(String name) {
		int spaceCount = 0;
    	for (char c : name.toCharArray()) {
    	    if (c == ' ') {
    	         spaceCount++;
    	    }
    	}
    	return spaceCount;
	}
	
	public static String getStarName(){
		String nameOfStar = "";
		System.out.println("Enter a name...");
    	Scanner starNameScanner = new Scanner(System.in);         	
    	nameOfStar = starNameScanner.nextLine();     	
    	System.out.println("Name of star: " + nameOfStar);
    	return nameOfStar;
	}
	
	public static String getID(){
		String starId = "";
		System.out.println("Enter an id..."); 	
    	Scanner starIDScanner = new Scanner(System.in);
    	starId = starIDScanner.nextLine();
    	System.out.println("ID of star: " + starId);
    	return starId; 
	}
	
	// ---END HELPER METHODS THAT ARE USED TO SEARCH FOR A STAR---
	
	
	// --START HELPER METHODS THAT ARE USED TO ENTER A CUSTOMER--
	
	
	// This method is universal to all methods that insert
	public static String getId() {
		System.out.println("Enter your id: ");
		Scanner idScanner = new Scanner(System.in);
		String id = idScanner.nextLine();
		return id;
	}
	
	public static String getCustomerName() {
		System.out.println("Please enter a name: ");
		Scanner nameScanner = new Scanner(System.in);
		String name = nameScanner.nextLine();
		return name;
	}
	
	public static String getCcId() {
		System.out.println("Please enter a cc id: ");
		Scanner ccIdScanner = new Scanner(System.in);
		String ccId = ccIdScanner.nextLine();
		return ccId;
	}
	
	public static String getAddress() {
		System.out.println("Please enter an address: ");
		Scanner addressScaner = new Scanner(System.in);
		String address = addressScaner.nextLine();
		return address;
	}
	
	public static String getEmail() {
		System.out.println("Please enter an email: ");
		Scanner emailS = new Scanner(System.in);
		String email = emailS.nextLine();
		return email;
	}
	
	public static String getPassword() {
		System.out.println("Please enter a password: ");
		Scanner passwordS = new Scanner(System.in);
		String password = passwordS.nextLine();
		return password;
	}
	
	public static String lookUpAStar() {
		
		// Name or ID?
        String nameOrId = getNameOrId();
        
        // Name of star 
        String nameOfStar = "";
        
        // Initialize a query String
        String queryString = "";

		// This query searches for either by JUST the first name OR just the last name.
		if (nameOrId.equals("name")){

			nameOfStar = getStarName();
    	
    	
			// get number of spaces
			int spaceCount = getNumberOfSpaces(nameOfStar);
    	
			// if name does not contain any spaces, execute the following query
    		if (!nameOfStar.contains(" ")){
    			// if name does not contain any spaces, execute the following query
    			
    			queryString = (" select movies.* from stars st inner join stars_in_movies s on st.id = s.star_id inner join movies on "
						+ "s.movie_id = movies.id where st.first_name = \"" + nameOfStar + "\""
						+ " or st.last_name = \"" + nameOfStar + "\"");
    		
    		}
        
        // This query searches by first name AND last name
    		else if (spaceCount == 1){ // if this string contains ONLY one space
        	
        	// we split the name by space into first and last name strings
        	
    			String first_name =  "";
    			String last_name = "";
    			String[] names = nameOfStar.split(" ");
    			first_name = names[0];
    			last_name = names[1];
        	
        	
        	
    			queryString = (" select movies.* from stars st inner join stars_in_movies s on st.id = s.star_id inner join movies on "
					+ "s.movie_id = movies.id where st.first_name = \"" + first_name + "\""
					+ " and st.last_name = \"" + last_name + "\"");
        	      
        }
    		
    	
        
        
        // This query searches by first and middle name combined into first name
    		
    		else if (spaceCount == 2) {
    			String firstMiddle =  "";
        		String lastName = "";
        		
        		String firstName = "";
        		String middleLast = "";
        		
        		// first case of firstMiddle
            	String[] names = nameOfStar.split(" ");
            	firstMiddle = names[0] + " " + names[1];
            	lastName = names[2];
            	
            	firstName = names[0];
            	middleLast = names[1] + " " + names[2];
            	
            	queryString = (" select movies.* from stars st inner join stars_in_movies s on st.id = s.star_id inner join movies on "
    					+ "s.movie_id = movies.id where st.first_name = \"" + firstMiddle + "\""
    					+ " and st.last_name = \"" + lastName + "\"" + " or st.first_name = \"" + firstName + "\"" + " and st.last_name = \"" + middleLast + "\"");

    		}
        
        
        // This query searches by middle and last name combined into last name
  
    }

    // if searching by id, do the following
		else if (nameOrId.equals ("id")){

			nameOfStar = getID();
			//    	Statement select = connection.createStatement();
			queryString = " select movies.* from stars st inner join stars_in_movies s on st.id = s.star_id inner join movies on s.movie_id = movies.id where st.id = \"" + nameOfStar + "\"";	
		}
		System.out.println("\nHere are the movies that the star, " + nameOfStar + ", appears in: ");
		return queryString;

	}
	
	public static void printMovieData(ResultSet result) throws SQLException {
		
		ResultSetMetaData metadata = result.getMetaData();
		
		// column count
				System.out.println("There are " + metadata.getColumnCount() + " columns");
		   
				System.out.println("The name of the table is: " + metadata.getTableName(1));
		   

//		    // Print type of each attribute
				for (int i = 1; i <= metadata.getColumnCount(); i++){
		            System.out.println("Type of column "+ i + " is " + metadata.getColumnTypeName(i));
		    		System.out.println("\nThe names of the columns are: " + metadata.getColumnName(i));
		    		System.out.println("The name of the table is: " + metadata.getTableName(i)); // keeps printing out m...
				}

		    // print table's contents, field by field
		    
		    // counter
				int i = 1;
				while (result.next())
				{
		    		System.out.println("\n-----Movie number: " + i + "------ \n");
		            System.out.println("Id = " + result.getInt(1));
		            System.out.println("Title = " + result.getString(2));
		            System.out.println("Year = " + result.getString(3));
		            System.out.println("Director = " + result.getString(4));
		            System.out.println("bannerURL = " + result.getString(5));
		            System.out.println("trailerURL = " + result.getString(6));
		            i++;
				}
	}

	
	public static String insertCustomer(){
		
		// NOTE TO TA: WE ARE ASSUMING THAT THE CUSTOMER NAME GIVEN IS ONLY 2 WORDS. NOT 3 WORDS.
		
		System.out.println("You are now entering a customer's information into the database.\n");
		String id = "";
		String firstName = "";
		String lastName = "";
		String cc_id = "";
		String address = "";
		String email = "";
		String password = "";
		
		String queryString = "";
		
		
		id = getId();
		
		// Check for empty spaace, then split into first and last name
		String name = getCustomerName();
		
		cc_id = getCcId();
		
		address = getAddress();
		
		email = getEmail();
		
		password = getPassword();
				
		if (!name.contains(" ")){
			lastName = name;
			// firstName remains an empty string
			
        	System.out.println("Constructing query for just a last name");
			// execute this query in the main
			
        	queryString = "INSERT INTO customers(first_name, last_name, cc_id, address, email, password) VALUES ('" + firstName + "', '" + lastName + "', '" + cc_id + "', '" + address + "', '" + email + "', '" + password + "');";

		}
		
		else if (name.contains(" ")){
        	String[] names = name.split(" ");
        	firstName = names[0];
        	lastName = names[1];
        	
        	// execute this query in the main
        	
        	System.out.println("Constructing query for a full name");
        	queryString = "INSERT INTO customers(first_name, last_name, cc_id, address, email, password) VALUES ('" + firstName + "', '" + lastName + "', '" + cc_id + "', '" + address + "', '" + email + "', '" + password + "');";

		}

		return queryString;
	}
	
	
	// HELPER METHODS FOR INSERT STAR
	
	public static String getDob() {
		System.out.println("Please enter a DOB: ");
		Scanner dobScanner = new Scanner(System.in);
		String dob = dobScanner.nextLine();
		return dob;
	}
	
	public static String getPhotoUrl() {
		System.out.println("Please enter a photo URL: ");
		Scanner urlScanner = new Scanner(System.in);
		String url = urlScanner.nextLine();
		return url;
	}
	
	public static String insertStar(){
		
		// NOTE TO TA: WE ARE ASSUMING THAT THE CUSTOMER NAME GIVEN IS ONLY 2 WORDS. NOT 3 WORDS.
		
		System.out.println("You are now entering a customer's information into the database.\n");
		String id = "";
		String firstName = "";
		String lastName = "";
		String dob = "";
		String photo_url = "";
		
		
		String queryString = "";
		
		
		id = getId();
		
		// Check for empty spaace, then split into first and last name
		String name = getCustomerName();
		
		dob = getDob();
		
		photo_url = getPhotoUrl();
				
		if (!name.contains(" ")){
			lastName = name;
			// firstName remains an empty string
			
        	System.out.println("Constructing query for just a last name");
			// execute this query in the main
			
        	queryString = "INSERT INTO stars(id, first_name, last_name, dob, photo_url) VALUES ('" + id + "', '" + firstName + "', '" + lastName + "', '" + dob + "', '" + photo_url + "', '" + "');";

		}
		
		else if (name.contains(" ")){
        	String[] names = name.split(" ");
        	firstName = names[0];
        	lastName = names[1];
        	
        	// execute this query in the main
        	
        	System.out.println("Constructing query for a full name");
        	queryString = "INSERT INTO stars(id, first_name, last_name, dob, photo_url) VALUES ('" + id + "', '" + firstName + "', '" + lastName + "', '" + dob + "', '" + photo_url + "', '" + "');";

		}

		return queryString;
	}
	
	// Delete customer
	public static String deleteCustomer() {
		
		String firstName = "";
		String lastName = "";
		String ccId = "";
		
		String queryString = "";
		
		System.out.println("Please enter the first name of customer to delete from the database: ");
		
		String name = getCustomerName();
		
		if (!name.contains(" ")){
			lastName = name;
			// firstName remains an empty string
			
        	System.out.println("Constructing query for just a last name");
			// execute this query in the main
			
        	queryString = "DELETE FROM customers WHERE first_name = '" + firstName + "' AND last_name = '" + lastName + "';";

		}
		
		else if (name.contains(" ")){
			String[] names = name.split(" ");
        	firstName = names[0];
        	lastName = names[1];
        	
        	// execute this query in the main
        	
        	System.out.println("Constructing query for a full name");
        	queryString = "DELETE FROM customers WHERE first_name = '" + firstName + "' AND last_name = '" + lastName + "';";

		}

		return queryString;
	}
	
    public static void main(String[] arg) throws Exception
    {
   
    		welcomeMenu();

            // Incorporate mySQL driver
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            
            

            // For use later on
            ResultSet result = null;
            
            
            // everything below will be in a function for the first bullet point
            
            String nameOfStar = "";
            
            // prompt user for instructions
            
            
    		String instruction = "";
    		
    	while (!instruction.equals("quit")){
    		
    		// Ask user for database and password information
            String database = getDatabase();
            String password = getPassword(database);

            Connection connection = DriverManager.getConnection("jdbc:mysql:///moviedb", database, password); 
    
        	Statement select = connection.createStatement();

    		instruction = userInterface().toLowerCase();
    		
    		
    		// CHECK IF INSTRUCTION IS TO INSERT A CUSTOMER
    		if (instruction.equals("insert customer")) {
    			try{
	    			System.out.println("Execute the customer insertion query");
	    			String queryString = insertCustomer();
	    			System.out.println("Query begiinning execution.");
	    			int temp = select.executeUpdate(queryString);
	
	    			System.out.println("Success");
    			}
    			catch (SQLException e) {
        			System.out.println("Your insertion that was inputted is incorrect. Please try again, you'll be redirected to the start menu.");
        		}
    			continue;
    		}
    		
    		else if (instruction.equals("logout")){
    			select.close();
    			continue;
    		}
    		
    		// CHECK IF INSTRUCTION IS TO INSERT A STAR

    		else if (instruction.equals("insert star")){
    			try{
	    			System.out.println("Execute the star insertion query");
	    			String queryString = insertStar();
	    			System.out.println("Query beginning execution.");
	    			int temp = select.executeUpdate(queryString);
	
	    			System.out.println("Success");
    			}
    			catch (SQLException e) {
        			System.out.println("Your insertion failed. Please try again, you'll be redirected to the start menu.");
        		}
    			continue;
    		}
    		
    		else if (instruction.equals("delete customer")){
    			
    			try{ 
	    			System.out.println("Execute the delete customer query");
	    			String queryString = deleteCustomer();
	    			System.out.println("Query beginning execution.");
	    			int temp = select.executeUpdate(queryString);
	
	    			System.out.println("Success");
    			}
    			catch (SQLException e) {
        			System.out.println("Your deletion failed. Please try again, you'll be redirected to the start menu.");
        		}
    			continue;
    		}
    		
            
    		else if (instruction.equals("search")){
    			
    			try{
	    			System.out.println("Execute the look up a movie query");
	    			String queryString = lookUpAStar();
	    			System.out.println("Query beginning execution.");
	    			result = select.executeQuery(queryString);
	    			printMovieData(result);
	    			System.out.println("Success");
    				}
    			catch (SQLException e) {
        			System.out.println("Your search failed. Please try again, you'll be redirected to the start menu.");
        		}
    			continue;
    			
    		}
    		
    		else if (instruction.equals("print metadata")){

    		try{
    			ArrayList<String> tableNames = new ArrayList<String>();
    			DatabaseMetaData tableMetadata = connection.getMetaData();
    			ResultSet tableResults = tableMetadata.getTables(null, null, "%", null);
    			
    			while (tableResults.next()) {
    			  tableNames.add(tableResults.getString(3));
    			}
    	
    			for(String tName : tableNames ){
    				String query = "SELECT * FROM " + tName;
    				result = select.executeQuery(query);
    				System.out.println("\nTable name: " + tName + "\n\n");
    				ResultSetMetaData metadata = result.getMetaData();
    	
    				for (int i = 1; i <= metadata.getColumnCount(); i++) {
    		        	System.out.println(metadata.getColumnName(i) + " : " + metadata.getColumnTypeName(i));
    				}
    				}
    			}
    		catch (SQLException e) {
    			System.out.println("Your query that was inputted is incorrect. Please try again, you'll be redirected to the start menu.");
    		}
    			continue;
    			
    		}
    		
    		else if (instruction.equals("custom query")){
    			
    		try{
    			System.out.println("Please Enter a valid SELECT/UPDATE/INSERT/DELETE SQL command and you will see its execution...: ");
    			
    			Scanner customScanner = new Scanner(System.in);
    			String customQuery = customScanner.nextLine();
    			
    			// Store it in a boolean value to see if the query works or not
    			boolean successOrNah = select.execute(customQuery);

    			if (successOrNah) {
    				System.out.println("\n--------Your SQL command has executed successfully---------\n\n");
    				result = select.getResultSet();

    				ResultSetMetaData metadata = result.getMetaData();
    			    int cols = metadata.getColumnCount();

    			    for (int i = 1; i <= cols; i++) {
    		            if (i > 1) {
    		            	System.out.print(", \t\t"); // used to separate the data values

    		            }
    		            System.out.print(metadata.getColumnName(i));
    		        }
    			    System.out.println("");
    			    while (result.next()) {
    			        for (int i = 1; i <= cols; i++) {
    			            if (i > 1) {
    			            	System.out.print(", \t");
    			            }
    			            String columnValue = result.getString(i);
    			            System.out.print(columnValue);
    			        }
    			        System.out.println("");
    			    }
    			}
    			else {
    				System.out.println("\n-------Your SQL command has executed successfully------\nThere are a total of " + select.getUpdateCount() + " rows that are updated!");
    				result = select.getResultSet();
    			}
    			}
    		
    		catch (SQLException e) {
    			System.out.println("Your custom query failed. Please try again, you'll be redirected to the start menu.");
    		}
    			
    			continue;
    			
    		}
	
 
    	}
    }
}
