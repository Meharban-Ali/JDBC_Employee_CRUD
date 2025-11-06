import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

public class JDBCRUDApplication {
    //Declare global variable
    static String url;
    static String username;
    static String password;
     //Take the input from the user
       private static final Scanner sc = new Scanner(System.in);
    public static void main(String[] args) throws IOException, ClassNotFoundException {
       
        //Propeties class use for Load dbconfigproperties file
        Properties props = new Properties();
        //FileInputStream class use for read properties file data
        FileInputStream fls = new FileInputStream("dbconfig.properties");
        props.load(fls);

        //Database Configuration
        url = props.getProperty("db.url");
        username = props.getProperty("db.username");
        password = props.getProperty("db.password");
        
        //Loading Driver
        Class.forName("com.mysql.cj.jdbc.Driver");
        System.out.println("JDBC Driver not found! Make sure mysql-connector jar is in classpath.");

        while (true) {
            showMenu();
            int choice = readInt("Choose option ! ");
            switch (choice) {
                case 1: insertEmployee(); 
                case 2: updateEmployee();
                case 3: deleteEmployeeById();
                case 4: findEmployeeById();
                case 5:findAllEmployee();
                case 6:{
                    System.out.println("Existing... GoodBye");
                    sc.close();
                    return;
                }    
                default: System.out.println("Invalid Option, Try again..");       
            }
            
        }
    }  
    //CRUD Operatin menu............
    private static void showMenu(){
        System.out.println("\n===== Student Perform CRUD Operation");
       System.out.println("1. Insert new student");
        System.out.println("2. Update existing student by ID");
        System.out.println("3. Delete student by ID");
        System.out.println("4. View student by ID");
        System.out.println("5. View all students");
        System.out.println("6. Exit");
        System.out.println("================================");
    }

    //Insert employee data in database 
    private static void insertEmployee(){
        //take the input from the user
       String name =  readString("Enter Name: ");
        String email =readString("Enter Email: ");
        String mobileNo = readString("Enter Mobile: ");
        String role = readString("Enter Role: ");
        String address = readString("Enter Address");

        //Write the insert query for create employee details and store database
        String query = "insert into employee_details(name, email, mobileNo, role, address) VALUES(?, ?, ?, ?, ?)";
        //Perfrm Exception Hnadling....................
        try {
            //Create Connection with databases (use are both class DriverManager and Conn)
            Connection con = DriverManager.getConnection(url, username, password);
            //Execute insert query using Prepared Statement
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, name);
            st.setString(2, email);
            st.setString(3, mobileNo);
            st.setString(4, role);
            st.setString(5, address);
            int inserted = st.executeUpdate();
            //this condition check empl_details store in db or not
            if (inserted>0) {
                System.out.println("Employee details registered successfully..");
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Database erorr during ");
        }
        
    }
    // create updating feature for update employee details ...
    private static void updateEmployee(){
    //Take input id from uer for update indivisual details...
        int id = readInt("Enter Employee id for update: ");
        if (!existById(id)) {
            System.out.println("No Employee found with this id: "+id);
            return;
        }
        // Take input from the user for update employee details(new details)
        String newName = readString("Enter new name: ");
        String newMail = readString("Enter new mail id: ");
        String newMobNo = readString("Enter new mobile number: ");
        String newRole = readString("Enter new role: ");
        String newAdderess = readString("Enter new address: ");
        //Write the mysql query for update employee details for specific id
        String updateQuery = "UPDATE employee_details SET name = ?, email = ?, mobileNo = ?, role = ?, address = ? where id = ?";
        try {
            // same on top cooment
            Connection conn = DriverManager.getConnection(url, username, password);
            PreparedStatement st = conn.prepareStatement(updateQuery);

            st.setString(1, newName);
            st.setString(2, newMail);
            st.setString(3, newMobNo);
            st.setString(4, newRole);
            st.setString(5, newAdderess);
            st.setInt(6, id);

            int updateDetails = st.executeUpdate();
            if(updateDetails>0){
                System.out.println("Employee Details updated successfully..");
            }else{
                System.out.println("Something went wrong please try again");
            }
            conn.close();

        } catch (Exception e) {
            System.out.println("Databae error during update details");
            System.out.println(e.getMessage());
        }
        
    }
    //Delete employee details by id
    private static void deleteEmployeeById(){
        int empId = readInt("Enter employee id: ");
        if (!existById(empId)) {
            System.out.println("Employee is not found in this id! ");
            return;
        }
        String deleteQuery = "DELETE FROM employee_details WHERE ID = ? ";
        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            PreparedStatement st = conn.prepareStatement(deleteQuery);
            st.setInt(1, empId);
            int deleteEmployee  = st.executeUpdate();
            if (deleteEmployee > 0) {
                System.out.println("Employee has been deleted successfully...");
            }else{
                System.out.println("Employee not found or Do not delete...");
            }
            conn.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    //Get the All employee details by id
    private static void findEmployeeById(){
        int id = readInt("Enter employee id: ");
        if (!existById(id)) {
            System.out.println("Employee not found in thid id: "+id);
            return;
        }

        String deleteQuery = "SELECT * FROM employee_details WHERE ID = ?";
        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            PreparedStatement st = conn.prepareStatement(deleteQuery);
            st.setInt(1, id);
            ResultSet rs= st.executeQuery();
            System.out.println("-------------------------------------------");
            if (rs.next()) {
                System.out.println("Id: "+rs.getInt("id"));
                System.out.println("Name: "+rs.getString("name"));
                System.out.println("Email: "+rs.getString("email"));
                System.out.println("MobileNo: "+rs.getString("mobileNo"));
                System.out.println("Role: "+rs.getString("role"));
                System.out.println("Address: "+rs.getString("address"));
                System.out.println("--------------------------------------");
            }else{
                System.out.println("Employee not found by given id: "+id);
            }
            conn.close();
        } catch (Exception e) {
           System.out.println(e.getMessage());
        }
    }

    //Get All employee details
    private static void findAllEmployee(){
        //Write the query for find the all employee in a single query...
        String getAllEmpQuery = "SELECT * FROM employee_details";
        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            PreparedStatement ps = conn.prepareStatement(getAllEmpQuery);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println("-------------------------------------");
                System.out.println("Id: "+rs.getInt("id"));
                System.out.println("Name: "+rs.getString("name"));
                System.out.println("Email: "+rs.getString("email"));
                System.out.println("MobileNo: "+rs.getString("mobileNo"));
                System.out.println("Role: "+rs.getString("role"));
                System.out.println("Address: "+rs.getString("address"));
            }
            conn.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Database error during find employee details...");
        }
        System.out.println("----------------------------------------");
    }
    // helper: check Employee Existence by id
    private static boolean existById(int id){
        String query = "SELECT 1 FROM employee_details WHERE ID = ?";
        try 
            (Connection conn = DriverManager.getConnection(url, username, password);
            PreparedStatement st = conn.prepareStatement(query)){
            st.setInt(1, id);
            try(ResultSet rs = st.executeQuery()){
                rs.next();  
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    } 

    //This logic are using for safely take input and given the correct output and not crash 
    //your program if employee take wrong number as a input.
    private static int readInt(String message){
        while (true) {
            System.out.println(message); 
            try {
                // two login working together(take input from the user and convert string to interger value)
                int value = Integer.parseInt(sc.nextLine().trim());
                return value;
            } catch (Exception e) {
                System.out.println("Invalid number, try again..");
            }
        }
       
    }

    //This logic are using for safely take input and given the correct output and not crash 
    //your program if employee take wrong number as a input.
    private static String readString(String message){
        System.out.println(message);
        return sc.nextLine().trim();

    }
}
