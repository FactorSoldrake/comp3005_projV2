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


public class memberFunc {

    
    // lists member's health metrics
    
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
    

    //list all private classes
    public static void getPrivClass(int memberID) {
        // creates variables to utilize database elements
        Connection conn = null;
        ResultSet result = null;
        PreparedStatement statement = null;
        
            // establish connection, statement creation and SQL instructions isseed
        try{
            conn = databaseAccess.establishAccess();
            String sqlQuery = "SELECT privClass_ID, trainer_ID, room_ID, start_time, end_time, cost, comments FROM Private_Class WHERE member_ID = ?";
            statement = conn.prepareStatement(sqlQuery);
            statement.setInt(1, memberID);
            result = statement.executeQuery();

            // List out of all elements in loop
            if(result.next()) {

                int privClass_ID = result.getInt("privClass_ID");
                int trainer_ID = result.getInt("trainer_ID");
                int room_ID = result.getInt("room_ID");
                Timestamp timeStart = result.getTimestamp("start_time");
                Timestamp timeEnd = result.getTimestamp("end_time");
                double cost = result.getDouble("cost");
                String comments = result.getString("comments");

                System.out.println("Private Class_ID " + privClass_ID);
                System.out.println("Trainer ID: " + trainer_ID);
                System.out.println("Room ID: " + room_ID);
                System.out.println("Start time: " + timeStart);
                System.out.println("End time: " + timeEnd);
                System.out.println("Cost: " + cost);
                System.out.println("Comments: " + comments);

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
    

    //join group class and issue a bill (capacity check may be needed)
    public static void groupClassRegister(int member_ID, int class_ID, double cost, String comments) {
        // creates varaibles to utilize database elements
        Connection conn = databaseAccess.establishAccess();
        PreparedStatement prepar = null;
        PreparedStatement billPrepar = null;
        
        try{
            // SQL instruction issued and preserved and then sent off
            String Instr = "UPDATE Group_Class SET capacity_remain = capacity_remain - 1 WHERE groupClass_ID = ? AND capacity_remain > 0";

            prepar = conn.prepareStatement(Instr);

            prepar.setInt(1, class_ID);

            
            // Data gets added in the required format 
            int specRow = prepar.executeUpdate();

            if (specRow > 0) {


                    // information on function that has been issued
                    String billIssue = "INSERT INTO Billing (fees, issue_date, due_date, payment_status, product_descript, member_ID) " + "VALUES (?, CURRENT_DATE, CURRENT_DATE + INTERVAL '14 days', 'Issued', ?, ?)";
                    billPrepar = conn.prepareStatement(billIssue);
                    billPrepar.setDouble(1, 20);
                    billPrepar.setString(2, "Group");
                    billPrepar.setInt(3, member_ID);


                    int order = billPrepar.executeUpdate();

                    if(order > 0) {
                        System.out.println("Billing record created successfully");

                    } else {
                        System.out.println("Billing record creation failed");

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

                if (billPrepar != null) {
                    billPrepar.close();
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

    // update health metrics
    public static void updateHealthMetric(int member_ID,int newHeight, int newWeight, int newAge, Date updatedInfo) {

        // creates varaibles to utilize database elements
        Connection conn = null;
        PreparedStatement prepar = null;
        
        try{
            
            conn = databaseAccess.establishAccess();
            String Instr = "UPDATE Health_Metrics SET height = ?, weight = ?, age = ?, updated_info = CURRENT_DATE WHERE member_ID = ?";

            prepar = conn.prepareStatement(Instr);

            
            prepar.setInt(1, newHeight);
            prepar.setInt(2, newWeight);
            prepar.setInt(3, newAge);
            prepar.setInt(4, member_ID);


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


    // Add fitness goals
    public static void addFitnessGoal(int member_ID, String description) {
        // creates varaibles to utilize database elements
        Connection conn = databaseAccess.establishAccess();
        PreparedStatement prepar = null;
        
        try{

            

            // SQL instruction issued and preserved and then sent off
            String Instr = "INSERT INTO Fitness_Goal (member_ID, describe, goal_comp) VALUES (?, ?, FALSE)";

            prepar = conn.prepareStatement(Instr);

            prepar.setInt(1, member_ID);
            prepar.setString(2, description);
            
            
            // Data gets added in the required format 
            int specRow = prepar.executeUpdate();

            System.out.println("Your goal addition is successful. The row affected is: " + specRow);
            
        } catch (SQLException e){
            System.out.println("Your goal addition is unsuccessful " + e.getMessage());
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
                System.out.println("Your goal addition is unsuccessful at resource level " + e.getMessage());
                e.printStackTrace();
            }

        }

    }

    // Update fitness goals
    public static void updateGoals(int goal_ID) {

        // creates varaibles to utilize database elements
        Connection conn = null;
        PreparedStatement prepar = null;
        
        try{
            
            conn = databaseAccess.establishAccess();
            String Instr = "UPDATE Fitness_Goal SET goal_comp = TRUE WHERE goal_ID = ?";

            prepar = conn.prepareStatement(Instr);

            prepar.setInt(1, goal_ID);


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

    // get all your fitness goals specific member
    public static void getGoals(int memberID) {
        // creates variables to utilize database elements
        Connection conn = null;
        ResultSet result = null;
        PreparedStatement statement = null;
        
            // establish connection, statement creation and SQL instructions isseed
        try{
            conn = databaseAccess.establishAccess();
            String sqlQuery = "SELECT goal_ID, describe, goal_comp FROM Fitness_Goal WHERE member_ID = ?";
            statement = conn.prepareStatement(sqlQuery);

            statement.setInt(1, memberID);
            result = statement.executeQuery();

            if(!result.isBeforeFirst()) {
                System.out.println("No goals found for member_ID: " + memberID);
            }


            // List out of all elements in loop
            while(result.next()) {

                int goalID = result.getInt("goal_ID");
                String description = result.getString("describe");
                boolean goals = result.getBoolean("goal_comp");

                System.out.println("Requested member_ID's goal ID: " + goalID);
                System.out.println("Description: " + description);
                System.out.println("Completed ?: " + goals);
                

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

    // Add fitness Achievements 
    public static void addFitnessAchieve(int member_ID, String description, Date Achieved, int goal_ID) {

        // creates varaibles to utilize database elements
        Connection conn = databaseAccess.establishAccess();
        PreparedStatement prepar = null;
        
        try{

            

            // SQL instruction issued and preserved and then sent off
            String Instr = "INSERT INTO Fitness_Achievement (member_ID, achievement_description, date_achieved, goal_ID) VALUES (?, ?, ?, ?)";

            prepar = conn.prepareStatement(Instr);

            prepar.setInt(1, member_ID);
            prepar.setString(2, description);
            prepar.setDate(3, new java.sql.Date(Achieved.getTime()));
            prepar.setInt(4, goal_ID);
            
            // Data gets added in the required format 
            int specRow = prepar.executeUpdate();

            System.out.println("Your achievement addition is successful. The row affected is: " + specRow);
            
        } catch (SQLException e){
            System.out.println("Your achievement addition is unsuccessful " + e.getMessage());
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
                System.out.println("Your achievement addition is unsuccessful at resource level " + e.getMessage());
                e.printStackTrace();
            }

        }

    }

    // Register new member
    public static void registerMember(String name, String pass) {
        // creates varaibles to utilize database elements
        Connection conn = databaseAccess.establishAccess();
        PreparedStatement prepar = null;
        PreparedStatement regisIssue = null;
        
        try{

            

            // SQL instruction issued and preserved and then sent off
            String Instr = "INSERT INTO Member (name, pass) VALUES (?, ?)";

            prepar = conn.prepareStatement(Instr, Statement.RETURN_GENERATED_KEYS);

            prepar.setString(1, name);
            prepar.setString(2, pass);
            
            
            // Data gets added in the required format 
            int specRow = prepar.executeUpdate();

            System.out.println("Your registraion is successful. The row affected is: " + specRow);


            // Display new member information
            regisIssue = prepar.getGeneratedKeys();

            if(regisIssue.next()) {
                int member_ID = regisIssue.getInt(1);
                System.out.println("Member ID: " + member_ID + ", Name: " + name + ", Password: " +pass);
            }
            
        } catch (SQLException e){
            System.out.println("Your registraion is unsuccessful " + e.getMessage());
            e.printStackTrace();

        }
        finally {

            try {
                // close down components
    
                if (prepar != null) {
                    prepar.close();
                }

                if (regisIssue != null) {
                    regisIssue.close();
                }

                if (conn != null) {
                    conn.close();
                }
            
            } catch (SQLException e) {
                System.out.println("Your registraion is unsuccessful at resource level " + e.getMessage());
                e.printStackTrace();
            }

        }

    }

    // Register to health_metric
    public static void addHealthMetric(int member_ID, int height, int weight, int age) {
        // creates varaibles to utilize database elements
        Connection conn = databaseAccess.establishAccess();
        PreparedStatement prepar = null;
        try{

            

            // SQL instruction issued and preserved and then sent off
            String Instr = "INSERT INTO Health_Metrics (member_ID, height, weight, age, updated_info) VALUES (?, ?, ?, ?, CURRENT_DATE)";

            prepar = conn.prepareStatement(Instr);

            prepar.setInt(1, member_ID);
            prepar.setInt(2, height);
            prepar.setInt(3, weight);
            prepar.setInt(4, age);


        
            // Data gets added in the required format 
            int specRow = prepar.executeUpdate();

            System.out.println("Your metric addition is successful. The row affected is: " + specRow);
            
        } catch (SQLException e){
            System.out.println("Your metric addition is unsuccessful " + e.getMessage());
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
                System.out.println("Your metric addition is unsuccessful at resource level " + e.getMessage());
                e.printStackTrace();
            }

        }

    }

    // Get your bills
    public static void getYourBills(int member_ID) {
        // creates variables to utilize database elements
        Connection conn = null;
        ResultSet result = null;
        PreparedStatement statement = null;
        
            // establish connection, statement creation and SQL instructions isseed
        try{
            conn = databaseAccess.establishAccess();
            statement = conn.prepareStatement("SELECT * FROM Billing WHERE member_ID = ?");
            statement.setInt(1, member_ID);
            result = statement.executeQuery();


            if (!result.isBeforeFirst()) {

                System.out.println("All dues are paid member ID: " + member_ID);

            } else {
                System.out.println("List of dues for member ID: " + member_ID);

                // List out of all elements in loop
                while(result.next()) {

                    int billing_id = result.getInt("billing_ID");
                    double fees = result.getDouble("fees");
                    Date issue = result.getDate("issue_date");
                    Date due = result.getDate("due_date");
                    String status = result.getString("payment_status");
                    String comment = result.getString("product_descript");
                    int member_id = result.getInt("member_ID");



                    System.out.println("Bill ID: " + billing_id + ", fees: " + fees + ", Issue Date: " + issue 
                    + ", Due Date: " + due + ", Payment Status: " + status + ", Comments: " + comment 
                    + ", Member ID: " + member_id);

                }

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

    // cancel private classes
    public static void deletePrivClass(int class_ID) {

        // establish connections
        Connection conn = databaseAccess.establishAccess();
        PreparedStatement prepar = null;
        
        try{
            // SQL instructions issued, held and then sent off
            String Instr = "DELETE FROM Private_Class WHERE privClass_ID = ?";

            prepar = conn.prepareStatement(Instr);

            prepar.setInt(1, class_ID);
            
            // there has been a deletion
            int specRow = prepar.executeUpdate();

            if (specRow > 0) {
                System.out.println("The following row has been deleted: " + specRow);

            } else {
                System.out.println("The following row was never found, ID: " + class_ID);

            }
            
            
        }
        catch (SQLException e){
            System.out.println("Error when deleting the row: " + e.getMessage());
            e.printStackTrace();

        }
        finally {

            try {
                // shut down databse compnenets and connections
    
                if (prepar != null) {
                    prepar.close();
                }

                if (conn != null) {

                    databaseAccess.stopAccess(conn);
                }

            

            } catch (SQLException e) {
                System.out.println("Error when deleting the row at resources: " + e.getMessage());
                e.printStackTrace();
            }

            

        }



    }

    // get all trainer avilibility
    public static void getAllTrainers() {
        // creates variables to utilize database elements
        Connection conn = null;
        ResultSet result = null;
        Statement statement = null;
        
            // establish connection, statement creation and SQL instructions isseed
        try{
            conn = databaseAccess.establishAccess();
            statement = conn.createStatement();
            result = statement.executeQuery("SELECT trainer_ID, name FROM Trainer");


            // List out of all elements in loop
            while(result.next()) {

                int trainer_id = result.getInt("trainer_ID");
                String name = result.getString("name");

                System.out.println("ID: " + trainer_id + ", Full name: " + name);

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

    // get all trainer avilibility
    public static void getAllTrainerAvail() {
        // creates variables to utilize database elements
        Connection conn = null;
        ResultSet result = null;
        Statement statement = null;
        
            // establish connection, statement creation and SQL instructions isseed
        try{
            conn = databaseAccess.establishAccess();
            statement = conn.createStatement();
            result = statement.executeQuery("SELECT trainer_ID, avail_from, avail_to FROM Trainer_Availability");

            System.out.println("Trainers available: ");

            // List out of all elements in loop
            while(result.next()) {

                int trainer_id = result.getInt("trainer_ID");
                Time avail_from = result.getTime("avail_from");
                Time avail_to = result.getTime("avail_to");

                System.out.println("ID: " + trainer_id + ", Time start: " + avail_from + ", Time end: " + avail_to);

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

    // Add excercise routine
    public static void addRoutine(int member_ID, String routine_Name, String description) {
        // creates varaibles to utilize database elements
        Connection conn = databaseAccess.establishAccess();
        PreparedStatement prepar = null;
        
        try{

            

            // SQL instruction issued and preserved and then sent off
            String Instr = "INSERT INTO Member_Routine (member_ID, routine_name, description) VALUES (?, ?, ?)";

            prepar = conn.prepareStatement(Instr);

            prepar.setInt(1, member_ID);
            prepar.setString(2, routine_Name);
            prepar.setString(3, description);
            
            
            // Data gets added in the required format 
            int specRow = prepar.executeUpdate();

            System.out.println("Your input is successful. The row affected is: " + specRow);


            
        } catch (SQLException e){
            System.out.println("Your input is unsuccessful " + e.getMessage());
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
                System.out.println("Your input is unsuccessful at resource level " + e.getMessage());
                e.printStackTrace();
            }

        }

    }


    // View excercise routine
    public static void getAllRoutines() {
        // creates variables to utilize database elements
        Connection conn = null;
        ResultSet result = null;
        Statement statement = null;
        
            // establish connection, statement creation and SQL instructions isseed
        try{
            conn = databaseAccess.establishAccess();
            statement = conn.createStatement();
            result = statement.executeQuery("SELECT exercise_ID, member_ID, routine_name, description FROM Member_Routine");


            // List out of all elements in loop
            while(result.next()) {

                int exercise_id = result.getInt("exercise_ID");
                int member_id = result.getInt("member_ID");
                String routine = result.getString("routine_name");
                String description = result.getString("description");

                System.out.println("Exercise ID: " + exercise_id + ", Member ID: " + member_id + ", Routine name: " + routine + ", Description: " + description);

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



}
