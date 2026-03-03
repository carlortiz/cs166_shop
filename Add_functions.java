import java.sql.*;
import java.util.Scanner;

public class Add_functions {

    static Scanner sc = new Scanner(System.in);

    public static void addCustomer(Connection conn){

        try{

            int id = -1;

            System.out.println("Customer ID:");

            while(true){

                if(sc.hasNextInt()){

                    id = sc.nextInt();
                    sc.nextLine();

                    if(id <= 0){
                        System.out.println("ID must be positive:");
                        continue;
                    }
                    try{
                        // check if ID already exists
                        String check = "SELECT id FROM customer WHERE id = ?";
                        PreparedStatement stmt = conn.prepareStatement(check);
                        stmt.setInt(1,id);

                        ResultSet rs = stmt.executeQuery();

                        if(rs.next()){
                            System.out.println("Customer ID already exists");
                            continue;   
                        }
                        break;
                    }
                    catch(SQLException e){
                        System.out.println("Database error");
                        return;
                    }
                }
                else{
                    System.out.println("Enter a valid number:");
                    sc.nextLine();
                }
            }
            
            System.out.println("First Name:");
            String fname = sc.nextLine();
            if(fname.isEmpty()){
                System.out.println("Invalid first name");
                return;
            }

            System.out.println("Last Name:");
            String lname = sc.nextLine();
            if(lname.isEmpty()){
                System.out.println("Invalid last name");
                return;
            }

            System.out.println("Phone:");
            String phone = sc.nextLine();
            if(phone.length() < 10){
                System.out.println("Invalid phone number");
                return;
            }

            System.out.println("Address:");
            String address = sc.nextLine();
            if(address.isEmpty()){
                System.out.println("Invalid address");
                return;
            }


            String sql = "INSERT INTO customer VALUES(?,?,?, ?, ?)";

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1,id);
            stmt.setString(2,fname);
            stmt.setString(3,lname);
            stmt.setString(4,phone);
            stmt.setString(5,address);

            stmt.executeUpdate();

            System.out.println("Customer Added");

        }catch(Exception e){

            System.out.println("Error adding customer");
            e.printStackTrace();

        }

    }

        public static void addMechanic(Connection conn){

        try{
            int id = -1;
            System.out.println("Employee ID:");

            while(true){
                if(sc.hasNextInt()){
                    id = sc.nextInt();
                    sc.nextLine();

                    if(id <= 0){
                        System.out.println("ID must be positive:");
                        continue;
                    }
                    try{
                        // Check if ID already exists
                        String check = "SELECT employee_id FROM mechanic WHERE employee_id = ?";
                        PreparedStatement stmt = conn.prepareStatement(check);
                        stmt.setInt(1,id);
                        ResultSet rs = stmt.executeQuery();

                        if(rs.next()){
                            System.out.println("Employee ID already exists");
                            continue;
                        }
                        break; // ID is valid and does not exist, exit loop
                    }
                    catch(SQLException e){
                        System.out.println("Database error");
                        return;
                    }
                }
                else{
                    System.out.println("Enter a valid number:");
                    sc.nextLine();
                }
            }

            System.out.println("First Name:");
            String fname = sc.nextLine();
            if(fname.isEmpty()){
                System.out.println("Invalid first name");
                return;
            }

            System.out.println("Last Name:");
            String lname = sc.nextLine();
            if(lname.isEmpty()){
                System.out.println("Invalid last name");
                return;
            }

            System.out.println("Years Experience:");
            int exp = sc.nextInt();
            if(exp < 0){
                System.out.println("Experience cannot be negative");
                return;
            }

            String sql = "INSERT INTO mechanic(employee_id,fname,lname,years_of_experience) VALUES(?,?,?,?)";

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1,id);
            stmt.setString(2,fname);
            stmt.setString(3,lname);
            stmt.setInt(4,exp);

            stmt.executeUpdate();

            System.out.println("Mechanic Added");

        }catch(Exception e){

            System.out.println("Error adding mechanic");
            e.printStackTrace();

        }

    }
        public static void addCar(Connection conn){

        try{
            String vin;
            while(true){
                System.out.println("VIN:");
                vin = sc.nextLine();

                if(vin.isEmpty()){
                    System.out.println("Invalid VIN");
                    continue;
                }

                try{
                    String check = "SELECT vin FROM car WHERE vin = ?";
                    PreparedStatement stmtCheck = conn.prepareStatement(check);
                    stmtCheck.setString(1,vin);
                    ResultSet rs = stmtCheck.executeQuery();

                    if(rs.next()){
                        System.out.println("VIN already exists");
                        continue;
                    }

                    break;
                }
                catch(SQLException e){
                    System.out.println("Database error");
                    return;
                }
            }

            System.out.println("Make:");
            String make = sc.nextLine();
            if(make.isEmpty()){
                System.out.println("Invalid make");
                return;
            }

            System.out.println("Model:");
            String model = sc.nextLine();
            if(model.isEmpty()){
                System.out.println("Invalid model");
                return;
            }

            System.out.println("Year:");
            int year = sc.nextInt();
            if(year < 0 || year > 2026){
                System.out.println("Year must be between 0 and 2026");
                return;
            }

            int customer_id = -1;
            
            while(true){
                System.out.println("Customer ID:");
                customer_id = sc.nextInt();
                sc.nextLine();

                try{
                    String sql = "SELECT * FROM customer WHERE id = ?";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setInt(1, customer_id);
                    ResultSet rs = stmt.executeQuery();
                    if(rs.next()){
                        break;
                    }
                    System.out.println("Customer not found");
                }
                catch(SQLException e){

                    System.out.println("Database error");
                    return;
                }
            }

            String sql = "INSERT INTO car VALUES(?,?,?,?,?)";

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1,vin);
            stmt.setString(2,make);
            stmt.setString(3,model);
            stmt.setInt(4,customer_id);
            stmt.setInt(5,year);

            stmt.executeUpdate();

            System.out.println("Car Added");

        }catch(Exception e){
            System.out.println("Error adding car");
            e.printStackTrace();
        }
    }
}
