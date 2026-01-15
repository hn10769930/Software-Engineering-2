package SEClass;

import java.sql.*;



public class SEClass {

    public static void main(String[] args) {

        // Database connection details

        String url = "jdbc:mysql://localhost:3306/school";

        String user = "root";

        String password = "521Annadale!";

        ; // For Connector/J 8.0 and newer

        String query = "SELECT Name FROM procedures";



        Connection connection = null;

        Statement statement = null;

        ResultSet resultSet = null;



        try {
        		String driverClass="com.mysql.cj.jdbc.Driver"

            // 1. Register the Driver (Class.forName is still supported, but not required for JDBC 4.0+)

            // For Connector/J 9.4.0, this is still fine, but you might see it omitted in newer code

            Class.forName(driverClass);



            // 2. Establish the connection

            connection = DriverManager.getConnection(url, user, password);

            System.out.println("Connected to the database.");



            // 3. Create a statement

            statement = connection.createStatement();



            // 4. Execute the query

            resultSet = statement.executeQuery(query);



            // 5. Process the results

            while (resultSet.next()) {

            	 System.out.println(resultSet.getString("Name"));

            }



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

                System.out.println("Connection closed.");

            } catch (SQLException e) {

                System.err.println("Error closing resources: " + e.getMessage());

                e.printStackTrace();

            }

        }

    }
    
    public static Connection connectToDatabase(String url, String user, String password)  {
        try {
        
            String driverClass="com.mysql.cj.jdbc.Driver"
            Class.forName(driverClass);



            // 2. Establish the connection

            connection = DriverManager.getConnection(url, user, password);

            System.out.println("Connected to the database.");
            return connection;
        
        
        
        
        } catch (ClassNotFoundException e) {

            System.err.println("Error loading the JDBC driver: " + e.getMessage());

            e.printStackTrace();

        } catch (SQLException e) {

            System.err.println("Database error: " + e.getMessage());

            e.printStackTrace();

        }
    }
    public static boolean closeEverything(ResultSet resultSet, Statement statement, Connection connection) {
    
                // Close the connection, statement, and result set

            try {

                if (resultSet != null) resultSet.close();

                if (statement != null) statement.close();

                if (connection != null) connection.close();

                System.out.println("Connection closed.");
                return true;

            } catch (SQLException e) {

                System.err.println("Error closing resources: " + e.getMessage());

                e.printStackTrace();
                return false;

            }
    }
}







