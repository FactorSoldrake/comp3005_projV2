package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


// Database acces code for resuable and easy access
public class databaseAccess {

    
    // responsible for establishing access with the database
    public static Connection establishAccess() {

        // Credentials for signing in 
        String url = "jdbc:postgresql://localhost:5432/SampleHealthFitness";
        String user = "postgres";
        String password = "postgres";
        Connection conn = null;

        try { // Load PostgreSQL JDBC Driver
            Class.forName("org.postgresql.Driver");
            // Connect to the database
            conn = DriverManager.getConnection(url, user, password);
            if (conn != null) {
                System.out.println("Connected to PostgreSQL successfully!");
            } else {
                System.out.println("Failed to establish connection.");
            } 

        } catch (ClassNotFoundException | SQLException e) {
        // catch problems
        e.printStackTrace();
        }

        // connection ON 
        return conn;


    }

    // stops database access
    public static void stopAccess (Connection conn) {

        if (conn != null) {

            try{
                conn.close();
                System.out.println("Connection has successfully closed.");
            }
            catch (SQLException e){
                e.printStackTrace();
            }

        }
       

    }

    
}
