import java.sql.*;
import java.util.Scanner;
import java.util.ArrayList;

public class Add_functions {

    public static void addCustomer(Connection conn, Scanner scanner){
        try{

            System.out.println("Customer ID:");
            int id = scanner.nextInt();
            scanner.nextLine();

            if(id <= 0){
                System.out.println("Error: ID must be positive");
                return;
            }

            // check duplicate ID
            String check = "SELECT id FROM customer WHERE id = ?";
            PreparedStatement stmtCheck = conn.prepareStatement(check);
            stmtCheck.setInt(1,id);

            ResultSet rs = stmtCheck.executeQuery();

            if(rs.next()){
                System.out.println("Error: Customer ID already exists");
                return;
            }

            System.out.println("First Name:");
            String fname = scanner.nextLine();
            if(fname.isEmpty()){
                System.out.println("Error: Invalid first name");
                return;
            }

            System.out.println("Last Name:");
            String lname = scanner.nextLine();
            if(lname.isEmpty()){
                System.out.println("Error: Invalid last name");
                return;
            }

            System.out.println("Phone:");
            String phone = scanner.nextLine();
            if(phone.length() < 10){
                System.out.println("Error: Invalid phone number");
                return;
            }

            System.out.println("Address:");
            String address = scanner.nextLine();
            if(address.isEmpty()){
                System.out.println("Error: Invalid address");
                return;
            }

            String sql = "INSERT INTO customer VALUES(?,?,?,?,?)";

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
        }
    }

    public static void addMechanic(Connection conn, Scanner scanner){
        try{
            System.out.println("Employee ID:");
            int id = scanner.nextInt();
            scanner.nextLine();

            if(id <= 0){
                System.out.println("Error: ID must be positive");
                return;
            }

            String check = "SELECT employee_id FROM mechanic WHERE employee_id = ?";
            PreparedStatement stmtCheck = conn.prepareStatement(check);
            stmtCheck.setInt(1,id);

            ResultSet rs = stmtCheck.executeQuery();

            if(rs.next()){
                System.out.println("Error: Employee ID already exists");
                return;
            }

            System.out.println("First Name:");
            String fname = scanner.nextLine();
            if(fname.isEmpty()){
                System.out.println("Error: Invalid first name");
                return;
            }

            System.out.println("Last Name:");
            String lname = scanner.nextLine();
            if(lname.isEmpty()){
                System.out.println("Error: Invalid last name");
                return;
            }

            System.out.println("Years Experience:");
            int exp = scanner.nextInt();
            scanner.nextLine();

            if(exp < 0){
                System.out.println("Error: Experience cannot be negative");
                return;
            }

            String sql = "INSERT INTO mechanic(employee_id,fname,lname,years_of_experience,vin) VALUES(?,?,?,?,?)";

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1,id);
            stmt.setString(2,fname);
            stmt.setString(3,lname);
            stmt.setInt(4,exp);
            stmt.setNull(5, java.sql.Types.VARCHAR);

            stmt.executeUpdate();

            System.out.println("Mechanic Added");

        }catch(Exception e){
            System.out.println("Error adding mechanic");
        }
    }

    public static void addCar(Connection conn, Scanner scanner){

        try{

            System.out.println("VIN:");
            String vin = scanner.nextLine();

            if(vin.isEmpty()){
                System.out.println("Error: Invalid VIN");
                return;
            }

            String check = "SELECT vin FROM car WHERE vin = ?";
            PreparedStatement stmtCheck = conn.prepareStatement(check);
            stmtCheck.setString(1,vin);

            ResultSet rs = stmtCheck.executeQuery();

            if(rs.next()){
                System.out.println("Error: VIN already exists");
                return;
            }

            System.out.println("Make:");
            String make = scanner.nextLine();
            if(make.isEmpty()){
                System.out.println("Error: Invalid make");
                return;
            }

            System.out.println("Model:");
            String model = scanner.nextLine();
            if(model.isEmpty()){
                System.out.println("Error: Invalid model");
                return;
            }

            System.out.println("Year:");
            int year = scanner.nextInt();
            scanner.nextLine();

            if(year < 0 || year > 2026){
                System.out.println("Error: Invalid year");
                return;
            }

            System.out.println("Customer ID:");
            int customer_id = scanner.nextInt();
            scanner.nextLine();

            String sqlCheck = "SELECT * FROM customer WHERE id = ?";
            PreparedStatement stmtCustomer = conn.prepareStatement(sqlCheck);
            stmtCustomer.setInt(1, customer_id);

            ResultSet rsCustomer = stmtCustomer.executeQuery();

            if(!rsCustomer.next()){
                System.out.println("Error: Customer does not exist");
                return;
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
        }
    }

    public static void initiateServiceRequest(Connection conn, Scanner scanner){
        try{
            System.out.print("enter last name: ");
            String lastName = scanner.nextLine();

            String query = "SELECT * FROM customer WHERE lname = ?";
            PreparedStatement statement = conn.prepareStatement(query); // prepares a query to search for customers with the entered last name
            statement.setString(1,lastName); // inserts the last name into the query
            ResultSet result = statement.executeQuery(); // runs the query and returns matching customers

            ArrayList<Integer> ids = new ArrayList<Integer>(); // stores customer ids so the user can choose one later
            int count = 0;

            while(result.next()){ // loops through all matching customers
                int id = result.getInt("id"); // gets the customer id from the current row
                String lname = result.getString("lname"); // gets the last name

                count++;
                ids.add(id); // saves the id into the array list

                System.out.println(count + ". " + lname + " (id " + id + ")");
            }

            int customerId;

            if(count == 0){
                System.out.println("no customers found.");
                System.out.print("add new customer? (y/n): ");
                String answer = scanner.nextLine();

                if(answer.equalsIgnoreCase("y")){
                    addCustomer(conn,scanner); // calls the helper function to add a new customer

                    System.out.print("enter id of new customer: ");
                    customerId = scanner.nextInt();
                    scanner.nextLine();
                }else{
                    System.out.println("cancelled.");
                    return; // exits if the user chooses not to add a customer
                }
            }else if(count == 1){
                customerId = ids.get(0); // automatically chooses the only matching customer
            }else{
                System.out.print("choose customer number: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                if(choice < 1 || choice > count){
                    System.out.println("invalid customer choice.");
                    return;
                }

                customerId = ids.get(choice - 1); // converts the displayed number into the correct index
            }

            System.out.println("selected customer id: " + customerId);

            String carQuery = "SELECT * FROM car WHERE customer_id = ?";
            PreparedStatement carStatement = conn.prepareStatement(carQuery); // prepares a query to find all cars owned by the selected customer
            carStatement.setInt(1,customerId); // inserts the selected customer id
            ResultSet carResult = carStatement.executeQuery(); // runs the query and returns the customer's cars

            ArrayList<String> vins = new ArrayList<String>(); // stores vins so the user can choose a car
            int carCount = 0;

            while(carResult.next()){ // loops through all cars owned by the customer
                String vin = carResult.getString("vin"); // gets the vin
                String make = carResult.getString("make"); // gets the make
                String model = carResult.getString("model"); // gets the model
                int year = carResult.getInt("year"); // gets the year

                carCount++;
                vins.add(vin); // saves the vin for later selection

                System.out.println(carCount + ". " + year + " " + make + " " + model + " (vin " + vin + ")");
            }

            String selectedVin;

            if(carCount == 0){
                System.out.println("customer has no cars.");
                System.out.print("add new car? (y/n): ");
                String answer = scanner.nextLine();

                if(answer.equalsIgnoreCase("y")){
                    addCar(conn,scanner); // calls the helper function to add a new car

                    System.out.print("enter vin of new car: ");
                    selectedVin = scanner.nextLine();
                }else{
                    System.out.println("cancelled.");
                    return; // exits if the user chooses not to add a car
                }
            }else if(carCount == 1){
                selectedVin = vins.get(0); // automatically chooses the only car
            }else{
                System.out.print("choose car number: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                if(choice < 1 || choice > carCount){
                    System.out.println("invalid car choice.");
                    return;
                }

                selectedVin = vins.get(choice - 1); // converts the displayed number into the correct vin
            }

            System.out.print("enter mechanic employee id: ");
            int employeeId = scanner.nextInt();
            scanner.nextLine();

            String mechanicCheck = "SELECT vin FROM mechanic WHERE employee_id = ?";
            PreparedStatement mechanicStmt = conn.prepareStatement(mechanicCheck); // prepares a query to check if the mechanic exists and to get their current assigned vin
            mechanicStmt.setInt(1,employeeId); // inserts the mechanic id into the query
            ResultSet mechanicRs = mechanicStmt.executeQuery(); // runs the query

            if(!mechanicRs.next()){
                System.out.println("error: mechanic does not exist");
                return; // stops if the mechanic id is not found
            }

            String mechanicCurrentVin = mechanicRs.getString("vin"); // gets the vin currently assigned to the mechanic

            if(mechanicCurrentVin != null && !mechanicCurrentVin.equals(selectedVin)){
                System.out.println("error: mechanic is already assigned to another car");
                return; // prevents a mechanic from being assigned to more than one different car at a time
            }

            System.out.print("enter service request id: ");
            int requestId = scanner.nextInt();
            scanner.nextLine();

            String checkRequest = "SELECT service_request_id FROM service_request WHERE service_request_id = ?";
            PreparedStatement checkRequestStatement = conn.prepareStatement(checkRequest); // prepares a query to make sure the request id does not already exist
            checkRequestStatement.setInt(1,requestId); // inserts the request id
            ResultSet checkRequestResult = checkRequestStatement.executeQuery(); // runs the query

            if(checkRequestResult.next()){
                System.out.println("service request id already exists.");
                return; // stops if the request id is already being used
            }

            System.out.print("enter service date (yyyy-mm-dd): ");
            String serviceDate = scanner.nextLine();

            System.out.print("enter problem description: ");
            String problem = scanner.nextLine();

            System.out.print("enter odometer reading: ");
            int odometer = scanner.nextInt();
            scanner.nextLine();

            if(odometer < 0){
                System.out.println("odometer reading cannot be negative.");
                return;
            }

            String insert =
            "INSERT INTO service_request (service_request_id, service_date, problem, odometer_reading, is_closed, vin, employee_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement insertStatement = conn.prepareStatement(insert); // prepares the insert statement for the new service request
            insertStatement.setInt(1,requestId); // sets the request id
            insertStatement.setDate(2,java.sql.Date.valueOf(serviceDate)); // converts the entered date string into an sql date
            insertStatement.setString(3,problem); // sets the problem description
            insertStatement.setInt(4,odometer); // sets the odometer reading
            insertStatement.setBoolean(5,false); // marks the request as open when first created
            insertStatement.setString(6,selectedVin); // links the request to the selected car
            insertStatement.setInt(7,employeeId); // links the request to the selected mechanic
            insertStatement.executeUpdate(); // inserts the new service request into the table

            String updateMechanic = "UPDATE mechanic SET vin = ? WHERE employee_id = ?";
            PreparedStatement updateStmt = conn.prepareStatement(updateMechanic); // prepares an update so the mechanic is now marked as working on this car
            updateStmt.setString(1,selectedVin); // assigns the selected car vin to the mechanic
            updateStmt.setInt(2,employeeId); // chooses which mechanic to update
            updateStmt.executeUpdate(); // runs the mechanic update

            System.out.println("service request created.");
        }catch(IllegalArgumentException e){
            System.out.println("invalid date format. please use yyyy-mm-dd.");
        }catch(Exception e){
            System.out.println("error: " + e.getMessage());
        }
    }


    public static void closeServiceRequest(Connection conn, Scanner scanner){
        try{
            System.out.print("enter service request id: ");
            int serviceRequestId = scanner.nextInt();
            scanner.nextLine();

            System.out.print("enter closing date (yyyy-mm-dd): ");
            String closedDateInput = scanner.nextLine();

            System.out.print("enter closing comments: ");
            String comments = scanner.nextLine();

            System.out.print("enter final bill amount: ");
            double finalBill = scanner.nextDouble();
            scanner.nextLine();

            if(finalBill < 0){
                System.out.println("final bill cannot be negative.");
                return;
            }

            java.sql.Date closedDate = java.sql.Date.valueOf(closedDateInput); // converts the entered closing date string into an sql date

            String findRequestSql =
            "SELECT service_date, is_closed, employee_id FROM service_request WHERE service_request_id = ?";
            PreparedStatement findRequestStmt = conn.prepareStatement(findRequestSql); // prepares a query to find the service request and retrieve its service date, closed status, and assigned mechanic
            findRequestStmt.setInt(1,serviceRequestId); // inserts the entered service request id into the query
            ResultSet requestRs = findRequestStmt.executeQuery(); // runs the query and returns the matching service request

            if(!requestRs.next()){
                System.out.println("service request not found.");
                return; // stops if the request id does not exist
            }

            java.sql.Date serviceDate = requestRs.getDate("service_date"); // gets the original service date from the database
            boolean isClosed = requestRs.getBoolean("is_closed"); // checks whether the request is already closed
            int employeeId = requestRs.getInt("employee_id"); // retrieves the mechanic assigned to this request

            if(isClosed){
                System.out.println("this service request is already closed.");
                return; // stops if the request was already closed earlier
            }

            if(closedDate.before(serviceDate)){
                System.out.println("closing date cannot be before the service date.");
                return; // prevents invalid closing dates that occur before the service request date
            }

            String updateRequestSql =
            "UPDATE service_request " +
            "SET is_closed = ?, closed_date = ?, comments = ?, final_bill = ? " +
            "WHERE service_request_id = ?";

            PreparedStatement updateStatement = conn.prepareStatement(updateRequestSql); // prepares the update statement that marks the request as closed
            updateStatement.setBoolean(1,true); // sets the request status to closed
            updateStatement.setDate(2,closedDate); // stores the closing date
            updateStatement.setString(3,comments); // stores the closing comments
            updateStatement.setDouble(4,finalBill); // stores the final bill amount
            updateStatement.setInt(5,serviceRequestId); // identifies which service request should be updated

            int rowsUpdated = updateStatement.executeUpdate(); // runs the update statement to close the request

            if(rowsUpdated <= 0){
                System.out.println("could not close service request.");
                return; // stops if the update failed and no rows were modified
            }

            String clearMechanicSql = "UPDATE mechanic SET vin = NULL WHERE employee_id = ?";
            PreparedStatement clearStmt = conn.prepareStatement(clearMechanicSql); // prepares an update to remove the mechanic's current car assignment
            clearStmt.setInt(1,employeeId); // uses the mechanic retrieved from the service request
            clearStmt.executeUpdate(); // clears the mechanic's vin so they are no longer assigned to a car

            System.out.println("service request closed successfully.");

        }catch(SQLException e){
            System.out.println("sql error: " + e.getMessage());
        }catch(IllegalArgumentException e){
            System.out.println("invalid date format. please use yyyy-mm-dd.");
        }
    }
}