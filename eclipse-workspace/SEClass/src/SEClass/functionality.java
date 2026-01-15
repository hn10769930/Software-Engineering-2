package SEClass;

import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class functionality {
	private static String url = "jdbc:mysql://localhost:3306/school";
	private static String user = "root";
	private static String passwor = "password";
	private static String driverClass = "com.mysql.cj.jdbc.Driver"; // For Connector/J 8.0 and newer
	public static void main(String[] args) {
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		System.out.println("System online.");
		System.out.println("Before you start asking, please enter the user password:");
		//passwor = scan.nextLine();
		passwor = "password";
		System.out.println("Great. Now what is the request?");
		int select;
		System.out.println("-Record a patient event");
		System.out.println("-Leave");
		while (true) {
			select = scan.nextInt();
			switch (select) {
			case 0: System.out.println("List starts from 1");break;
			case 1: addPatientEvent();break;	//must be tested
			case 2: System.out.println("Goodbye then.");System.exit(2);
			default: System.out.println("I don't know how to do that.");
			}
		}
	}
	// This method is the one to be implemented in project code
	public static void addPatientEvent() {
		String query = null;
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		try {
			// 1. Register the Driver (Class.forName is still supported, but not required for JDBC 4.0+)
			Class.forName(driverClass);
			// 2. Establish the connection
			connection = DriverManager.getConnection(url, user, passwor);
			System.out.println("------Connected to the database------");
			// 3. Create a statement
			statement = connection.createStatement();
			System.out.println("Enter patient MRN");
			boolean validMRN = true;
			String mrn = "";
			do {
				mrn = scan.nextLine();		//should make sure this corresponds to a patient in the data table
				resultSet = statement.executeQuery("select * from patients where MRN = '"+mrn+"'");
				if (!resultSet.next()) {
					validMRN = false;
					System.out.println("MRM does not correspond to patient in database. Please re-enter patient MRN");
				}
				else
					validMRN = true;
			} while (!validMRN);
			System.out.println("Enter procedure name"); // I tried to have the code validate the procedure id
			boolean validProc = true;
			String proc = "";
			do {
				proc = scan.nextLine();
				resultSet = statement.executeQuery("select * from procedures where Procedure ID = '"+proc+"'"); //This is an illegal query
				if (!resultSet.next()) {										// 'procedure' is a keyword in SQL
					validProc = false;
					System.out.println("Procedure name does not exist in database. Please re-enter procedure name");
				}
				else
					validProc = true;
			} while (!validProc);
			System.out.println("Enter procedure name");
			String proc1 = scan.nextLine();
			System.out.println("Enter the date and time (dd/mm/yyyy) (hh:mm)");
			String date = scan.next();
			String time = scan.nextLine();
			System.out.println("Enter the last name of the doctor (without the Dr.)");
			String doc = "Dr. "+scan.nextLine();
			query = "insert into new_events (Patient, Procedure, Date, Time, Doctor, Paid?) values ("+mrn+","+proc+","+date+","+time+","+doc+")";
			// 4. Execute the query
			resultSet = statement.executeQuery(query); // Causes error :(
		} catch (ClassNotFoundException e) {
			System.err.println("Error loading the JDBC driver: " + e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			System.err.println("Database error: " + e.getMessage());
			e.printStackTrace();
		} finally {
			// 6. Close the connection, statement, and result set
			try {	
				if (resultSet != null) resultSet.close();
				if (statement != null) statement.close();
				if (connection != null) connection.close();
				System.out.println("------Connection closed------");
			} catch (SQLException e) {
				System.err.println("Error closing resources: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
}