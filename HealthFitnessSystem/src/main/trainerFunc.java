
package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Time;



public class trainerFunc {

    // group class booking method and uto billing
    public static void groupClassBook(int trainerId, int roomId, Timestamp startTime, Timestamp endTime, double cost, String comments, int totalCapacity) {
        // creates varaibles to utilize database elements
        Connection conn = databaseAccess.establishAccess();
        PreparedStatement prepar = null;
        
        try{
            // SQL instruction issued and preserved and then sent off
            String Instr = "INSERT INTO Group_Class (trainer_ID, room_ID, start_time, end_time, cost, comments, total_capacity, capacity_remain) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            prepar = conn.prepareStatement(Instr, Statement.RETURN_GENERATED_KEYS);

            prepar.setInt(1, trainerId);
            prepar.setInt(2, roomId);
            prepar.setTimestamp(3, startTime);
            prepar.setTimestamp(4, endTime);
            prepar.setDouble(5, cost);
            prepar.setString(6, comments);
            prepar.setInt(7, totalCapacity);
            prepar.setInt(8, totalCapacity);


            
            // Data gets added in the required format 
            int specRow = prepar.executeUpdate();

            if (specRow > 0) {

                try (ResultSet tuple = prepar.getGeneratedKeys()) {

                    if(tuple.next()) {
                        // information on function that has been issued
                        int ID_request = tuple.getInt(1); 
                        System.out.println("Your group class booking is: "+ ID_request);

                    } 
                    
                }


            } else {
                System.out.println("No rows affected, please check again!");
            }        
            
        } catch (SQLException e){
            System.out.println("Your group class booking is unsuccessful " + e.getMessage());
            e.printStackTrace();

        }
        finally {

            try {
                // close down components
    
                if (prepar != null) {
                    prepar.close();
                }

                if (conn != null) {
                    conn.close();
                }

            
            } catch (SQLException e) {
                System.out.println("Your group class booking is unsuccessful at resource level " + e.getMessage());
                e.printStackTrace();
            }

        }

    }

    // private class booking and bill issuance
    public static void privateClassBook(int trainerId, int roomId, int memberId, Timestamp startTime, Timestamp endTime, double cost, String comments) {
        // creates varaibles to utilize database elements
        Connection conn = databaseAccess.establishAccess();
        PreparedStatement billPrepar = null;
        PreparedStatement prepar = null;
        
        try{

            // input check
            if(startTime != null && endTime != null && startTime.after(endTime)) {

                System.out.println("Please place start time before end time!");
                return;
            }


            // SQL instruction issued and preserved and then sent off
            String Instr = "INSERT INTO Private_Class (trainer_ID, room_ID, member_ID, start_time, end_time, cost, comments) VALUES (?, ?, ?, ?, ?, ?, ?)";
            prepar = conn.prepareStatement(Instr, Statement.RETURN_GENERATED_KEYS);

            prepar.setInt(1, trainerId);
            prepar.setInt(2, roomId);
            prepar.setInt(3, memberId);
            prepar.setTimestamp(4, startTime);
            prepar.setTimestamp(5, endTime);
            prepar.setDouble(6, cost);
            prepar.setString(7, comments);
            


            
            // Data gets added in the required format 
            int specRow = prepar.executeUpdate();

            if (specRow > 0) {

                ResultSet tuple = prepar.getGeneratedKeys();

                if(tuple.next()) {
                    // information on function that has been issued
                    int ID_request = tuple.getInt(1); 
                    System.out.println("Your private class booking is: "+ ID_request);

                    String billIssue = "INSERT INTO Billing (fees, issue_date, due_date, payment_status, product_descript, member_ID) " + "VALUES (?, CURRENT_DATE, CURRENT_DATE + INTERVAL '14 days', 'Issued', ?, ?)";
                    billPrepar = conn.prepareStatement(billIssue);
                    billPrepar.setDouble(1, cost);
                    billPrepar.setString(2, "Private");
                    billPrepar.setInt(3, memberId);


                    int order = billPrepar.executeUpdate();

                    if(order > 0) {
                        System.out.println("Billing record created successfully");

                    } else {
                        System.out.println("Billing record creation failed");

                    }

                
                } 

            } else {
                System.out.println("No rows affected, please check again!");
            }        
            
        } catch (SQLException e){
            System.out.println("Your private class booking is unsuccessful " + e.getMessage());
            e.printStackTrace();

        } finally {

            try {
                // close down components
    
                if (prepar != null) {
                    prepar.close();
                }

                if (conn != null) {
                    conn.close();
                }

            
            } catch (SQLException e) {
                System.out.println("Your private class booking is unsuccessful at resource level " + e.getMessage());
                e.printStackTrace();
            }

        }

    }

    // lists all members and their IDs available in the database 
    public static void getAllMembers() {
        // creates variables to utilize database elements
        Connection conn = null;
        ResultSet result = null;
        Statement statement = null;
        
            // establish connection, statement creation and SQL instructions isseed
        try{
            conn = databaseAccess.establishAccess();
            statement = conn.createStatement();
            result = statement.executeQuery("SELECT member_ID, name FROM Member");


            // List out of all elements in loop
            while(result.next()) {

                int member_id = result.getInt("member_ID");
                String name = result.getString("name");

                System.out.println("ID: " + member_id + ", Full name: " + name);

            }

        }
        catch (SQLException e){
            // problem catcher
            e.printStackTrace();

        }
        finally {

            // close and sign out all components that were active
            try {


                if (statement != null) {
                    statement.close();
                }
    
                if (result != null) {
                    result.close();
                }

                if (conn != null) {
                    databaseAccess.stopAccess(conn);
                }

            } catch (SQLException e) {
                // further failure investigation
                e.printStackTrace();
            }

        }

    }

    
    // lists all member's health metrics
    public static void getHealthMetric(int memberID) {
        // creates variables to utilize database elements
        Connection conn = null;
        ResultSet result = null;
        PreparedStatement statement = null;
        
            // establish connection, statement creation and SQL instructions isseed
        try{
            conn = databaseAccess.establishAccess();
            String sqlQuery = "SELECT height, weight, age, updated_info FROM Health_Metrics WHERE member_ID = ?";
            statement = conn.prepareStatement(sqlQuery);
            statement.setInt(1, memberID);
            result = statement.executeQuery();

            // List out of all elements in loop
            if(result.next()) {

                int height = result.getInt("height");
                int weight = result.getInt("weight");
                int age = result.getInt("age");
                java.sql.Date updatedInfo = result.getDate("updated_info");

                System.out.println("Requested member_ID health metric: " + memberID);
                System.out.println("Height: " + height + " cm");
                System.out.println("Weight: " + weight + " kg");
                System.out.println("Age: " + age + " years old");
                System.out.println("Last update: " + updatedInfo);

            } else {
                System.out.println("No data found for member_ID: "+ memberID);
            }

        }
        catch (SQLException e){
            // problem catcher
            e.printStackTrace();

        }
        finally {

            // close and sign out all components that were active
            try {


                if (statement != null) {
                    statement.close();
                }
    
                if (result != null) {
                    result.close();
                }

                if (conn != null) {
                    databaseAccess.stopAccess(conn);
                }

            } catch (SQLException e) {
                // further failure investigation
                e.printStackTrace();
            }

        }

    }

    // update availibility
    public static void updateAvailability(int trainer_ID,Time avail_from, Time avail_to) {

        // creates varaibles to utilize database elements
        Connection conn = null;
        PreparedStatement prepar = null;
        
        try{
            
            conn = databaseAccess.establishAccess();
            String Instr = "UPDATE Trainer_Availability SET avail_from = ?, avail_to = ? WHERE trainer_ID = ?";

            prepar = conn.prepareStatement(Instr);

            
            prepar.setTime(1, avail_from);
            prepar.setTime(2, avail_to);
            prepar.setInt(3, trainer_ID);



            // Data gets added in the required format 
            int specRow = prepar.executeUpdate();
            
            System.out.println("The following row has been updated: " + specRow);
        }
        catch (SQLException e){

            e.printStackTrace();

        }
        finally {

            try {
                
                // components are shut down
                if (prepar != null) {
                    prepar.close();
                }

                if (conn != null) {

                    databaseAccess.stopAccess(conn);
                }

            

            } catch (SQLException e) {

                e.printStackTrace();
            }

        }

    }


}
