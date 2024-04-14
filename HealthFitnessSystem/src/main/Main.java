package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Scanner;
import java.sql.Date;



public class main {

    public static Scanner scanner = new Scanner(System.in);



    public static boolean userFetcher(String user, String pass, String userType) {

        Connection conn = null; 
        PreparedStatement prepar = null;
        ResultSet result = null;
        boolean allowed = false;
        
        try{
            conn = databaseAccess.establishAccess();
            prepar = conn.prepareStatement("SELECT * FROM " + userType + " WHERE name = ? AND pass = ?");

            prepar.setString(1, user);
            prepar.setString(2, pass);

            result = prepar.executeQuery();
            allowed = result.next();

            if(allowed) {
                System.out.println("Welcome back: " + user);

            } else {
                System.out.println("My database does not seem to welcome you: " + user);
            }
        } catch (SQLException e){
            // problem catcher
            e.printStackTrace();

        }
        finally {

            // close and sign out all components that were active
            try {


                if (prepar != null) {
                    prepar.close();
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

    public static void dashTrainer() {

        boolean keepWorking = true;
        
        while (keepWorking) {
            System.out.println("Welcome to Trainer Dashboard");
            System.out.println("1. Update your schedule");
            System.out.println("2. List all members of the gym");
            System.out.println("3. Read a member's health profile");
            System.out.println("4. Book a private class");
            System.out.println("5. Book a group class");
            System.out.println("6. End your login session");


            System.out.println("Enter your request: ");

            String select = scanner.nextLine();

            switch (select) {
                case "1":
                    // update your schedule
                    System.out.println("trainer ID: ");
                    int trainer_ID = scanner.nextInt();
                    scanner.nextLine();

                    System.out.println("Start time: ");
                    Time avail_from = Time.valueOf(scanner.nextLine());

                    System.out.println("End time: ");
                    Time avail_to = Time.valueOf(scanner.nextLine());

                    trainerFunc.updateAvailability(trainer_ID, avail_from, avail_to);
                    
                    break;
                case "2":
                    // list members
                    trainerFunc.getAllMembers(); 

                    break;
                case "3":
                    // health metric 
                    System.out.println("Enter member_ID: ");
                    int memberID = scanner.nextInt();
                    scanner.nextLine();
                    trainerFunc.getHealthMetric(memberID);
                    break;
                case "4":
                    // private class book
                    System.out.println("trainer ID: ");
                    int trainerID = scanner.nextInt();
                    scanner.nextLine();

                    System.out.println("room ID: ");
                    int roomID = scanner.nextInt();
                    scanner.nextLine();

                    System.out.println("member ID: ");
                    int member_ID = scanner.nextInt();
                    scanner.nextLine();

                    System.out.println("Start time (YYYY-MM-DD hh:mm:ss): ");
                    Timestamp start_from = Timestamp.valueOf(scanner.nextLine());

                    System.out.println("End time (YYYY-MM-DD hh:mm:ss): ");
                    Timestamp end_to = Timestamp.valueOf(scanner.nextLine());

                    System.out.println("cost: ");
                    double privCost = scanner.nextDouble();
                    scanner.nextLine();

                    ystem.out.println("comments: ");
                    String privComment = scanner.nextLine();

                    trainerFunc.privateClassBook(trainerID, roomID, member_ID, start_from, end_to, privCost, privComment);

                    break;
                case "5":
                    // group class book
                    System.out.println("trainer ID: ");
                    int gtrainerID = scanner.nextInt();
                    scanner.nextLine();

                    System.out.println("room ID: ");
                    int groomID = scanner.nextInt();
                    scanner.nextLine();

                    System.out.println("Start time (YYYY-MM-DD hh:mm:ss): ");
                    Timestamp gstart_from = Timestamp.valueOf(scanner.nextLine());

                    System.out.println("End time (YYYY-MM-DD hh:mm:ss): ");
                    Timestamp gend_to = Timestamp.valueOf(scanner.nextLine());

                    System.out.println("cost: ");
                    double gCost = scanner.nextDouble();
                    scanner.nextLine();

                    System.out.println("comments: ");
                    String gComment = scanner.nextLine();

                    System.out.println("Enter room capacity: ");
                    int gCapacity = scanner.nextInt();
                    scanner.nextLine();


                    trainerFunc.groupClassBook(gtrainerID, groomID, gstart_from, gend_to, gCost, gComment, gCapacity);


                    break;
                case "6":
                    // menu
                    System.out.println("System quitting----");
                    keepWorking = false;

                    break;
                default:
                    System.out.println("Invalid option, try again");
                    break;
            }        

        }
    

    }


    public static void dashAdmin() {

        boolean keepWorking = true;
        
        while (keepWorking) {
            System.out.println("Welcome to Admin Dashboard");
            System.out.println("1. Add Equipment");
            System.out.println("2. List all equipment of the gym");
            System.out.println("3. update maintenance");
            System.out.println("4. Delete equipment information");
            System.out.println("5. Add a room");
            System.out.println("6. List all rooms");
            System.out.println("7. Update a room");
            System.out.println("8. Delete a room");
            System.out.println("9. List all bills");
            System.out.println("10. Update a bill status");
            System.out.println("11. Update a private class");
            System.out.println("12. Update a group class");
            System.out.println("13. Quit session");
            System.out.println("Enter your request: ");

            String select = scanner.nextLine();

            switch (select) {
                case "1":
                    // Add equipment

                    System.out.println("Equipment name: ");
                    String eqiupName = scanner.nextLine();

                    System.out.println("Last maintenance (YYYY-MM-DD hh:mm:ss): ");
                    Timestamp pstart_last = Timestamp.valueOf(scanner.nextLine());

                    System.out.println("Next maintenance (YYYY-MM-DD hh:mm:ss): ");
                    Timestamp pstart_next = Timestamp.valueOf(scanner.nextLine());
                    
                    System.out.println("Condition: ");
                    String cond = scanner.nextLine();


                    adminFunc.addEquip_Track(eqiupName, pstart_last, pstart_next, cond);
                    break;
                case "2":
                    // List equipment
                    adminFunc.getAllEquipment();

                    break;
                case "3":
                    // equipmenty update
                    System.out.println("Equipment ID: ");
                    int equipment = scanner.nextInt();
                    scanner.nextLine();

                    System.out.println("Start time (YYYY-MM-DD hh:mm:ss): ");
                    Timestamp estart_from = Timestamp.valueOf(scanner.nextLine());

                    System.out.println("End time (YYYY-MM-DD hh:mm:ss): ");
                    Timestamp eend_to = Timestamp.valueOf(scanner.nextLine());

                    adminFunc.updateMaintenance(equipment, estart_from, eend_to);
                    
                    break;
                case "4":
                    // delete equipment
                    System.out.println("Enter equipment ID to delete: ");
                    int dAdminEq = scanner.nextInt();
                    scanner.nextLine();

                    adminFunc.deleteEquipment(dAdminEq);
                    break;
                case "5":
                    // Add a room
                    System.out.println("Room name: ");
                    String room = scanner.nextLine();

                    System.out.println("Enter room capacity: ");
                    int roomCap = scanner.nextInt();
                    scanner.nextLine();

                    System.out.println("Room status: ");
                    String roomStat = scanner.nextLine();

                    System.out.println("Last maintenance (YYYY-MM-DD): ");
                    Date dateStart = Date.valueOf(LocalDate.parse(scanner.nextLine()));

                    System.out.println("Next maintenance: ");
                    Date dateEnd = Date.valueOf(LocalDate.parse(scanner.nextLine()));

                    adminFunc.addRoom(room, roomCap, roomStat, dateStart, dateEnd);
                    

                    break;
                case "6":
                    // list rooms
                    adminFunc.getAllRooms();

                    break;
                case "7":

                    System.out.println("Enter room ID: ");
                    int NewroomID = scanner.nextInt();
                    scanner.nextLine();

                    System.out.println("Room name: ");
                    String Newroom = scanner.nextLine();

                    System.out.println("Enter room capacity: ");
                    int NewroomCap = scanner.nextInt();
                    scanner.nextLine();

                    System.out.println("Room status: ");
                    String NewroomStat = scanner.nextLine();

                    System.out.println("Last maintenance (YYYY-MM-DD): ");
                    Date NewdateStart = Date.valueOf(LocalDate.parse(scanner.nextLine()));

                    System.out.println("Next maintenance: ");
                    Date NewdateEnd = Date.valueOf(LocalDate.parse(scanner.nextLine()));


                    adminFunc.updateRoom(NewroomID, Newroom, NewroomCap, NewroomStat, NewdateStart, NewdateEnd);
                    break;
                case "8":
                    // delete a room
                    System.out.println("Enter room ID to delete: ");
                    int dAdminRoom = scanner.nextInt();
                    scanner.nextLine();
                    adminFunc.deleteRoom(dAdminRoom);
                    break;
                case "9":
                    // list bills
                    adminFunc.getAllBills();
                    
                    break;
                case "10":
                    // update a bill
                    System.out.println("bill ID: ");
                    int aBill = scanner.nextInt();
                    scanner.nextLine();

                    System.out.println("Status: ");
                    String pStatus = scanner.nextLine();

                    System.out.println("comments: ");
                    String pBill = scanner.nextLine();

                    adminFunc.updateBill(aBill, pStatus, pBill);
                    
                    break;
                case "11":
                    // update private class
                    System.out.println("private class ID: ");
                    int pClassID = scanner.nextInt();
                    scanner.nextLine();

                    System.out.println("Start time (YYYY-MM-DD hh:mm:ss): ");
                    Timestamp pstart_from = Timestamp.valueOf(scanner.nextLine());

                    System.out.println("End time (YYYY-MM-DD hh:mm:ss): ");
                    Timestamp pend_to = Timestamp.valueOf(scanner.nextLine());

                    System.out.println("comments: ");
                    String pComment = scanner.nextLine();

                    adminFunc.updatePrivateClass(pClassID, pstart_from, pend_to, pComment);
                    

                    break;
                case "12":
                    // update group class
                    System.out.println("group class ID: ");
                    int gClassID = scanner.nextInt();
                    scanner.nextLine();

                    System.out.println("room ID: ");
                    int NewgroomID = scanner.nextInt();
                    scanner.nextLine();

                    System.out.println("Start time (YYYY-MM-DD hh:mm:ss): ");
                    Timestamp Newgstart_from = Timestamp.valueOf(scanner.nextLine());

                    System.out.println("End time (YYYY-MM-DD hh:mm:ss): ");
                    Timestamp Newgend_to = Timestamp.valueOf(scanner.nextLine());


                    System.out.println("comments: ");
                    String NewgComment = scanner.nextLine();

                    System.out.println("Enter room capacity: ");
                    int NewgCapacity = scanner.nextInt();
                    scanner.nextLine();
                    adminFunc.updateGroupClass(gClassID, NewgroomID, Newgstart_from, Newgend_to, NewgComment, NewgCapacity);

                    break;
                case "13":
                    // menu
                    System.out.println("System quitting----");
                    keepWorking = false;

                    break;
                default:
                    System.out.println("Invalid option, try again");
                    break;
            }        

        }
    

    }

    // incomplete
    public static void dashMember() {
        boolean keepWorking = true;
        
        while (keepWorking) {
            System.out.println("Welcome to Member Dashboard");
            System.out.println("1. Get your health metrics");
            System.out.println("2. Get your private classes");
            System.out.println("3. Register for group classes");
            System.out.println("4. Update health metrics");
            System.out.println("5. Add a fitness goal");
            System.out.println("6. Mark your fitness goal complete");
            System.out.println("7. get your fitness goals");
            System.out.println("8. Add your achievement");
            System.out.println("9. Get a list of your biils");
            System.out.println("10. Cancel private classes");
            System.out.println("11. List of trainers");
            System.out.println("12. List of trainers work hours");
            System.out.println("13. Add a routine");
            System.out.println("14. View public forum of routines");
            System.out.println("15. Add your health metrics");

            System.out.println("Enter your request: ");

            String select = scanner.nextLine();

            switch (select) {
                case "1":

                    
                    break;
                case "2":
                    

                    break;
                case "3":
                    
                    break;
                case "4":
                    
                    break;
                case "5":
                    

                    break;
                case "6":
                    
                    
                    break;
                case "7":
                    

                    break;
                case "8":
                    
                    break;
                case "9":
                    
                    break;
                case "10":
                    

                    break;
                case "11":
                    // trainers list
                    memberFunc.getAllTrainers();
                    

                    break;
                case "12":
                    // trainers schedule
                    memberFunc.getAllTrainerAvail();
                    
                    break;
                case "13":
                    
                    break;
                case "14":
                    // Public forum of routines
                    memberFunc.getAllRoutines();


                    break;
                case "15":
                    

                    break;
                case "16":
                    // menu
                    System.out.println("System quitting----");
                    keepWorking = false;

                    break;
                default:
                    System.out.println("Invalid option, try again");
                    break;
            }        

        }


    }


    public static void main(String[] args) {
        System.out.println("Welcome to the Health and Fitness Club Management System");
        System.out.println("Please specify, if you are an old user type 'Y' if you are a new user type 'N'.");

        char inputType = scanner.next().charAt(0);


        switch (inputType) {

            case 'Y':
                System.out.println("Welcome old user, now please specify your name");
                String oldUser = scanner.nextLine();

                System.out.println("Now please specify your password");
                String oldPasser = scanner.nextLine();

                System.out.println("Now please specify your userType: 'Member', 'Trainer' or 'Admin' exactly in that lettercase");
                String oldType = scanner.nextLine();

                boolean allowed = userFetcher(oldUser, oldPasser, oldType);

                if (!allowed) {
                    System.out.println("Access denied try again!");
                    return;
                }

                switch (oldType) {
                    case "Member":
                        // menu 
                        dashMember();
                        
                        break;
                    case "Trainer":
                        dashTrainer();

                        break;
                    case "Admin":
                        // menu
                        dashAdmin();
                        break;
                    default:
                        System.out.println("How did you even end up here??");
                        break;
                }

                
                break;
            case 'N':
                System.out.println("Registration request recieved");
                System.out.println("Enter your name: ");
                String add_name = scan.nextLine();
                System.out.println("Enter your password: ");
                String add_pass = scan.nextLine();

                memberFunc.registerMember(add_name, add_pass);
                
                // go to member menu now
                dashMember();
                break;
            default:
                System.out.println("Invalid input");
        }

    }
    
}
