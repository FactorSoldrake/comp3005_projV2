
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



public class adminFunc {

    // Add Equipment under Equipment_Track
    public static void addEquip_Track(String name, Timestamp maintain_last, Timestamp maintain_next, String Condition) {
        // creates varaibles to utilize database elements
        Connection conn = databaseAccess.establishAccess();
        PreparedStatement prepar = null;
        
        try{


            if (maintain_last != null && maintain_next != null && !maintain_next.after(maintain_last)) {
                System.out.println("last maintainence date is before next maintainence date!");
                return;
            }

    

            // SQL instruction issued and preserved and then sent off
            String Instr = "INSERT INTO Equipment_Track (name, maintain_last, maintain_next, Condition) VALUES (?, ?, ?, ?)";

            prepar = conn.prepareStatement(Instr, Statement.RETURN_GENERATED_KEYS);

            prepar.setString(1, name);
            prepar.setTimestamp(2, maintain_last);
            prepar.setTimestamp(3, maintain_next);
            prepar.setString(4, Condition);
            


            
            // Data gets added in the required format 
            int specRow = prepar.executeUpdate();

            if (specRow > 0) {

                try (ResultSet tuple = prepar.getGeneratedKeys()) {

                    if(tuple.next()) {
                        // information on function that has been issued
                        int ID_request = tuple.getInt(1); 
                        System.out.println("Your new equipment ID is: "+ ID_request);

                    } 
                    
                }


            } else {
                System.out.println("No rows affected, please check again!");
            }        
            
        } catch (SQLException e){
            System.out.println("Your equipment addition is unsuccessful " + e.getMessage());
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
                System.out.println("Your equipment addition is unsuccessful at resource level " + e.getMessage());
                e.printStackTrace();
            }

        }

    }

    // lists all equipment under Equipment_Track 
    public static void getAllEquipment() {
        // creates variables to utilize database elements
        Connection conn = null;
        ResultSet result = null;
        Statement statement = null;
        
            // establish connection, statement creation and SQL instructions isseed
        try{
            conn = databaseAccess.establishAccess();
            statement = conn.createStatement();
            result = statement.executeQuery("SELECT * FROM Equipment_Track");


            // List out of all elements in loop
            while(result.next()) {

                int equipment_id = result.getInt("equipment_ID");
                String name = result.getString("name");
                String maintain_last = result.getString("maintain_last");
                String maintain_next = result.getString("maintain_next");
                String condition = result.getString("condition");


                System.out.println("ID: " + equipment_id + ", name: " + name + ", Last Maintenance: " + maintain_last + ", Next Maintenance: " + maintain_next + ", Condition: " + condition);

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
    
    // update maintenance of equipment 
    public static void updateMaintenance(int equipment_ID,Timestamp newLast, Timestamp newNext) {

        // creates varaibles to utilize database elements
        Connection conn = null;
        PreparedStatement prepar = null;
        
        try{
            
            conn = databaseAccess.establishAccess();
            String Instr = "UPDATE Equipment_Track SET maintain_last = ?, maintain_next = ? WHERE equipment_ID = ?";

            prepar = conn.prepareStatement(Instr);

            
            prepar.setTimestamp(1, newLast);
            prepar.setTimestamp(2, newNext);
            prepar.setInt(3, equipment_ID);



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

    // delete equipment
    public static void deleteEquipment(int equipment_ID) {

        // establish connections
        Connection conn = databaseAccess.establishAccess();
        PreparedStatement prepar = null;
        
        try{
            // SQL instructions issued, held and then sent off
            String Instr = "DELETE FROM Equipment_Track WHERE equipment_ID = ?";

            prepar = conn.prepareStatement(Instr);

            prepar.setInt(1, equipment_ID);
            
            // there has been a deletion
            int specRow = prepar.executeUpdate();

            if (specRow > 0) {
                System.out.println("The following row has been deleted: " + specRow);

            } else {
                System.out.println("The following row has not been deleted, ID: " + equipment_ID);

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

    // Add room
    public static void addRoom(String name, int capacity, String status, Date Last, Date Next) {
        // creates varaibles to utilize database elements
        Connection conn = databaseAccess.establishAccess();
        PreparedStatement prepar = null;
        
        try{

            // input check
            if (Last != null && Next != null && !Next.after(Last)) {
                System.out.println("last maintainence date is before next maintainence date!");
                return;
            }

    

            // SQL instruction issued and preserved and then sent off
            String Instr = "INSERT INTO Room_Track (name, capacity, room_status, last_maintain, next_maintain) VALUES (?, ?, ?, ?, ?)";

            prepar = conn.prepareStatement(Instr, Statement.RETURN_GENERATED_KEYS);

            prepar.setString(1, name);
            prepar.setInt(2, capacity);
            prepar.setString(3, status);
            prepar.setDate(4, Last);
            prepar.setDate(5, Next);
            
            


            
            // Data gets added in the required format 
            int specRow = prepar.executeUpdate();

            if (specRow > 0) {

                try (ResultSet tuple = prepar.getGeneratedKeys()) {

                    if(tuple.next()) {
                        // information on function that has been issued
                        int ID_request = tuple.getInt(1); 
                        System.out.println("Your new room ID is: "+ ID_request);

                    } 
                    
                }


            } else {
                System.out.println("No rows affected, please check again!");
            }        
            
        } catch (SQLException e){
            System.out.println("Your room addition is unsuccessful " + e.getMessage());
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
                System.out.println("Your room addition is unsuccessful at resource level " + e.getMessage());
                e.printStackTrace();
            }

        }

    }

    // get all rooms
    public static void getAllRooms() {
        // creates variables to utilize database elements
        Connection conn = null;
        ResultSet result = null;
        Statement statement = null;
        
            // establish connection, statement creation and SQL instructions isseed
        try{
            conn = databaseAccess.establishAccess();
            statement = conn.createStatement();
            result = statement.executeQuery("SELECT * FROM Room_Track");


            // List out of all elements in loop
            while(result.next()) {

                int room_id = result.getInt("room_ID");
                String name = result.getString("name");
                int capacity = result.getInt("capacity");
                String status = result.getString("status");
                String lastMaintain = result.getString("last_maintain");
                String nextMaintain = result.getString("next_maintain");


                System.out.println("ID: " + room_id + ", Name: " + name + ", Capacity: " + capacity + ", Status: " 
                + status + ", Last Maintenance: " + lastMaintain + ", Next Maintenance: " + nextMaintain);

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

    // modify room information
    public static void updateRoom(int room_ID, String newName, int newCapacity, String newStatus, Date newLast, Date newNext) {

        // creates varaibles to utilize database elements
        Connection conn = null;
        PreparedStatement prepar = null;
        
        try{

            // input check
            if (newLast != null && newNext != null && !newNext.after(newLast)) {
                System.out.println("last room maintainence date is before next room maintainence date!");
                return;
            }
            
            conn = databaseAccess.establishAccess();
            String Instr = "UPDATE Room_Track SET name = ?, capacity = ?, room_status = ?, last_maintain = ?, next_maintain = ? WHERE room_ID = ?";

            prepar = conn.prepareStatement(Instr);

            prepar.setString(1, newName);
            prepar.setInt(2, newCapacity);
            prepar.setString(3, newStatus);
            prepar.setDate(4, newLast);
            prepar.setDate(5, newNext);
            prepar.setInt(6, room_ID);



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

    // delete room 
    public static void deleteRoom(int room_ID) {

        // establish connections
        Connection conn = databaseAccess.establishAccess();
        PreparedStatement prepar = null;
        
        try{
            // SQL instructions issued, held and then sent off
            String Instr = "DELETE FROM Room_Track WHERE room_ID = ?";

            prepar = conn.prepareStatement(Instr);

            prepar.setInt(1, room_ID);
            
            // there has been a deletion
            int specRow = prepar.executeUpdate();

            if (specRow > 0) {
                System.out.println("The following row has been deleted: " + specRow);

            } else {
                System.out.println("The following row has not been deleted, ID: " + room_ID);

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

    // list of bills
    public static void getAllBills() {
        // creates variables to utilize database elements
        Connection conn = null;
        ResultSet result = null;
        Statement statement = null;
        
            // establish connection, statement creation and SQL instructions isseed
        try{
            conn = databaseAccess.establishAccess();
            statement = conn.createStatement();
            result = statement.executeQuery("SELECT * FROM Billing");


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

    // update bill payment and comment status
    public static void updateBill(int bill_ID, String newStatus, String newComment) {

        // creates varaibles to utilize database elements
        Connection conn = null;
        PreparedStatement prepar = null;
        
        try{
            
            conn = databaseAccess.establishAccess();
            String Instr = "UPDATE Billing SET payment_status = ?, product_descript = ? WHERE billing_ID = ?";

            prepar = conn.prepareStatement(Instr);

            prepar.setString(1, newStatus);
            prepar.setString(2, newComment);
            prepar.setInt(3, bill_ID);



            // Data gets added in the required format 
            int specRow = prepar.executeUpdate();
            if (specRow > 0) {
                System.out.println("The following bill has been updated: " + bill_ID);
            } else {
                System.out.println("The following row failed updating: " + bill_ID);

            }
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

    // update private class schedule
    public static void updatePrivateClass(int PrivateClass_ID, Timestamp newStartTime,  Timestamp newEndTime, String newComments) {

        // creates varaibles to utilize database elements
        Connection conn = null;
        PreparedStatement prepar = null;
        
        try{
            
            conn = databaseAccess.establishAccess();
            String Instr = "UPDATE Private_Class SET start_time = ?, end_time = ?, comments = ? WHERE privClass_ID = ?";

            prepar = conn.prepareStatement(Instr);

            prepar.setTimestamp(1, newStartTime);
            prepar.setTimestamp(2, newEndTime);
            prepar.setString(3, newComments);
            prepar.setInt(4, PrivateClass_ID);



            // Data gets added in the required format 
            int specRow = prepar.executeUpdate();
            if (specRow > 0) {
                System.out.println("The following schedule has been updated: " + PrivateClass_ID);
            } else {
                System.out.println("The following schedule failed updating: " + PrivateClass_ID);

            }
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

    // update group class schedule 
    public static void updateGroupClass(int groupClass_ID, int newRoom_ID, Timestamp newStartTime,  Timestamp newEndTime, String newComments, int newCapacityRemain) {

        // creates varaibles to utilize database elements
        Connection conn = null;
        PreparedStatement prepar = null;
        
        try{
            
            conn = databaseAccess.establishAccess();
            String Instr = "UPDATE Group_Class SET room_ID = ?, start_time = ?, end_time = ?, comments = ?, capacity_remain = ? WHERE groupClass_ID = ?";

            prepar = conn.prepareStatement(Instr);

            prepar.setInt(1, newRoom_ID);
            prepar.setTimestamp(2, newStartTime);
            prepar.setTimestamp(3, newEndTime);
            prepar.setString(4, newComments);
            prepar.setInt(5, newCapacityRemain);
            prepar.setInt(6, groupClass_ID);



            // Data gets added in the required format 
            int specRow = prepar.executeUpdate();
            if (specRow > 0) {
                System.out.println("The following schedule has been updated: " + groupClass_ID);
            } else {
                System.out.println("The following schedule failed updating: " + groupClass_ID);

            }
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
