package SEClass;

import java.sql.*;
import java.util.Scanner;

public class SampleJDBC {
    public static void main(String[] args) {
        // Database connection details
        String url = "jdbc:mysql://localhost:3306/school";
        String user = "root";
        String password = "password";
        String driverClass = "com.mysql.cj.jdbc.Driver"; 

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        Scanner scanner = new Scanner(System.in);

        try {
            Class.forName(driverClass);
            connection = DriverManager.getConnection(url, user, password);

            while(true) { // Added a loop so the program doesn't exit after one action
                System.out.println("\n--- Main Menu ---");
                System.out.println("1. View Patients");
                System.out.println("2. View Procedures"); 
                System.out.println("3. View Patients"); 
                System.out.println("4. Add New Patient"); 
                System.out.println("5. Add Patient Event (Procedure)");
                System.out.println("6. Exit");
                System.out.print("Enter choice: ");
                
                int choice = 0;
                try {
                    choice = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid number.");
                    continue;
                }

                String tableName = "";

                switch (choice) {
                    case 1: 
                        viewTable(connection, "patients");
                        break;
                    case 2: 
                        viewTable(connection, "procedures");
                        break;  
                    case 3: 
                        viewTable(connection, "events");
                        break; 
                    case 4:
                        addNewPatient(connection, scanner);
                        break;
                    // *** NEW CASE ***
                    case 5:
                        addPatientEvent(connection, scanner);
                        break;
                    case 6:
                        System.out.println("Exiting...");
                        return; // Exits the main method
                    default: 
                        System.out.println("Invalid selection.");
                }
            }

        } catch (ClassNotFoundException e) {
            System.err.println("Error loading the JDBC driver: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
                if (scanner != null) scanner.close();
                System.out.println("\nConnection closed.");
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
    }
    
    public static void addPatientEvent(Connection conn, Scanner scanner) throws SQLException {
        System.out.println("\n--- Record a Patient Event ---");

        // Validate MRN (Checking 'MRN' column in patients table)
        String mrn = "";
        boolean validMRN = false;
        while (!validMRN) {
            System.out.print("Enter Patient MRN: ");
            mrn = scanner.nextLine();
            
            // Querying the `patients` table for MRN existence
            String checkPatientSql = "SELECT MRN FROM patients WHERE MRN = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkPatientSql)) {
                checkStmt.setString(1, mrn);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next()) {
                    validMRN = true;
                    System.out.println("Patient found.");
                } else {
                    System.out.println("Error: MRN '" + mrn + "' not found in patients table. Please try again.");
                }
            }
        }

        // Validate Procedure ID (Checking `procedures` table)
        String procID = "";
        boolean validProc = false;
        while (!validProc) {
            System.out.print("Enter Procedure ID: ");
            procID = scanner.nextLine();

            String checkProcSql = "SELECT `Procedure ID` FROM procedures WHERE `Procedure ID` = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkProcSql)) {
                checkStmt.setString(1, procID);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next()) {
                    validProc = true;
                    System.out.println("Procedure found.");
                } else {
                    System.out.println("Error: Procedure ID '" + procID + "' not found. Please try again.");
                }
            }
        }

        // Gather remaining data
        System.out.print("Enter Date (DD/MM/YYYY): ");
        String date = scanner.nextLine();

        System.out.print("Enter Time (HH:MM): ");
        String time = scanner.nextLine();

        System.out.print("Enter Doctor's Last Name: ");
        String docName = scanner.nextLine();
        if (!docName.startsWith("Dr.")) {
            docName = "Dr. " + docName;
        }

        System.out.print("Is this paid? (Yes/No): ");
        String paidInput = scanner.nextLine();
        String paidStatus = (paidInput.toLowerCase().startsWith("y")) ? "Yes" : "No";

        String insertSql = "INSERT INTO events (`Patient`, `Procedure`, `Date`, `Time`, `Doctor`, `Paid`) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
            pstmt.setString(1, mrn);      // Value for `Patient` column
            pstmt.setString(2, procID);   // Value for `Procedure` column
            pstmt.setString(3, date);
            pstmt.setString(4, time);
            pstmt.setString(5, docName);
            pstmt.setString(6, paidStatus);

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Event recorded successfully!");
            }
        }
    }

    public static void addNewPatient(Connection conn, Scanner scanner) throws SQLException {
        System.out.println("\n--- Adding New Patient ---");
        
        System.out.print("First Name: ");
        String fName = scanner.nextLine();
        System.out.print("Last Name: ");
        String lName = scanner.nextLine();
        System.out.print("Pronouns (e.g. He/Him): ");
        String pronouns = scanner.nextLine();
        System.out.print("Date of Birth (DD/MM/YYYY): ");
        String dob = scanner.nextLine();
        System.out.print("Address: ");
        String address = scanner.nextLine();
        System.out.print("City: ");
        String city = scanner.nextLine();
        System.out.print("State: ");
        String state = scanner.nextLine();
        System.out.print("Zip: ");
        String zip = scanner.nextLine();
        System.out.print("Insurance Provider: ");
        String insProvider = scanner.nextLine();
        System.out.print("Insurance ID: ");
        String insID = scanner.nextLine();
        System.out.print("Sex: ");
        String sex = scanner.nextLine();
        System.out.print("Gender: ");
        String gender = scanner.nextLine();
        System.out.print("Doctor Name: ");
        String doctor = scanner.nextLine();
        System.out.print("MRN (Medical Record Number): ");
        String mrn = scanner.nextLine();

        String sql = "INSERT INTO patients (`Last Name`, `First Name`, `Pronoun`, `DOB`, `Address`, `City`, `State`, `Zip`, `Insurance provider`, `Insurance ID`, `Sex`, `Gender`, `Doctor`, `MRN`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, lName);
            pstmt.setString(2, fName);
            pstmt.setString(3, pronouns);
            pstmt.setString(4, dob);
            pstmt.setString(5, address);
            pstmt.setString(6, city);
            pstmt.setString(7, state);
            pstmt.setString(8, zip);
            pstmt.setString(9, insProvider);
            pstmt.setString(10, insID);
            pstmt.setString(11, sex);
            pstmt.setString(12, gender);
            pstmt.setString(13, doctor);
            pstmt.setString(14, mrn);
            
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new patient was inserted successfully!");
            }
        }
    }

    public static void viewTable(Connection connection, String tableName) throws SQLException {
        String query = "SELECT * FROM " + tableName;
        System.out.println("Querying table: " + tableName + "\n");

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        
        for (int i = 1; i <= columnCount; i++) {
            System.out.print(metaData.getColumnName(i) + "\t");
        }
        System.out.println();
        
        while (resultSet.next()) {
            String[] patient_info = sql_entry_to_string_array(resultSet);
            format_and_print_entry(tableName, patient_info);
            System.out.println();
        }
    }
    
    public static String[] sql_entry_to_string_array(ResultSet sql_entries) {
        try {
            ResultSetMetaData metaData = sql_entries.getMetaData();
            int columnCount = metaData.getColumnCount();
            String[] entries = new String[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                entries[i-1]= sql_entries.getString(i);
            }
            return entries;
        }
        catch(SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            return null;
        }
    }
    
    public static void format_and_print_entry(String table_name, String[] entries) {
	    	if (table_name == "patients") {
	        	//Last Name	First Name	Pronoun	DOB	Address	City	State	Zip	
	        	//Insurance provider	Insurance ID	Sex	Gender	Doctor	MRN
	    		String patient_name_and_pronouns = entries[1] + " " + entries[0] + " (" + entries[2] + ")";
	    		System.out.println(patient_name_and_pronouns);
	    		System.out.println("Born: " + entries[3]);
	    		System.out.println("Address: " + entries[4] + ", " + entries[5] + " " + entries[6]);
	    		System.out.println("Insurance Provider: " + entries[7] + "\n\tID Number: " + entries[8]);
	    		System.out.println("Overseen by " + entries[11]);
	    	}
	    	if (table_name == "procedures") {
	    		//Name	Procedure ID	Base Cost	Category
	    		System.out.println(entries[0] + "\n\tCost: " + entries[2] + "\n\t(" + entries[3] + ")");
	    	}
	    	if (table_name == "events") {
	    		//MRN	Proc. ID	Date	Time	Doctor	Paid?
	    		System.out.println("Date: " + entries[2] + "\t" + entries[3] + 
	    				"\n\tMRN: " + entries[0] + "\n\tProcedure ID: " + entries[1] + "\n\tDone by: " + entries[4]
	    				+ "\n\tPaid? " + entries[5]
	    				);
	    	}
	}
}