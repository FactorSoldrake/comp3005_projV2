COMP 3005 Project V2

Note: The project file has been submitted via BrightSpace

Preequisties:

- PostgreSQL database software: https://www.postgresql.org/download/
- Java Development kit (JDK) version: https://www.oracle.com/java/technologies/downloads/#java17
- PostgreSQL JDBC driver software: https://jdbc.postgresql.org/download/
- Visual Studio Code (Or any IDE should work): https://code.visualstudio.com/download

SQL setup:
- Find the DDL_comp3005_V2Proj.sql and the DML_comp3005_V2Proj.sql file in src->main folder.
- Launch pgAdmin.
- In the browser panel on the left, right-click on Databases and select Create > Database.
- Name the new database "SampleHealthFitness" and click Save.
- In pgAdmin, select the newly created database by clicking on it.
- Open the Query Tool by right-clicking on database and selecting Query Tool.
- Click on the Open File icon (looks like a folder) in the toolbar.
- Navigate to the location where you saved "DDL_comp3005_V2Proj.sql" and open it.
- The content of "DDL_comp3005_V2Proj.sql" should now be displayed in the query editor.
- Click on the Run button (a green triangle) to execute the SQL commands.
- Repeat the same with the "DML_comp3005_V2Proj.sql" file

- Credential Specifications: (Note: Your user and password may be 
				different those must be replaced)
  Credentials for signing in:
        String url = "jdbc:postgresql://localhost:5432/SampleHealthFitness";
        String user = "postgres";
        String password = "postgres";

IDE setup:
- Running the application: Open the IDE and locate the src folder with compile all 
 the files in the main.

Application run:
- Compile all the files properly first
- Run the main.java file using the run button in VS code IDE the terminal should launch
 with the menu options, you can start interacting with the program.

