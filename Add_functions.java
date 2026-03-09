import java.sql.*;
import java.util.Scanner;
import java.util.ArrayList;

public class Add_functions {

    public static void addCustomer(Connection conn, Scanner scanner){
        try{
            int id = -1;
            System.out.println("Customer ID:");

            while(true){
                if(scanner.hasNextInt()){
                    id = scanner.nextInt();
                    scanner.nextLine();

                    if(id <= 0){
                        System.out.println("ID must be positive:");
                        continue;
                    }
                    try{
                        String check = "SELECT id FROM customer WHERE id = ?";
                        PreparedStatement stmt = conn.prepareStatement(check);
                        stmt.setInt(1,id);
                        ResultSet rs = stmt.executeQuery();

                        if(rs.next()){
                            System.out.println("Customer ID already exists");
                            continue;
                        }
                        break;
                    }catch(SQLException e){
                        System.out.println("Database error");
                        return;
                    }
                }else{
                    System.out.println("Enter a valid number:");
                    scanner.nextLine();
                }
            }

            System.out.println("First Name:");
            String fname = scanner.nextLine();
            if(fname.isEmpty()){
                System.out.println("Invalid first name");
                return;
            }

            System.out.println("Last Name:");
            String lname = scanner.nextLine();
            if(lname.isEmpty()){
                System.out.println("Invalid last name");
                return;
            }

            System.out.println("Phone:");
            String phone = scanner.nextLine();
            if(phone.length() < 10){
                System.out.println("Invalid phone number");
                return;
            }

            System.out.println("Address:");
            String address = scanner.nextLine();
            if(address.isEmpty()){
                System.out.println("Invalid address");
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
            e.printStackTrace();
        }
    }

    public static void addMechanic(Connection conn, Scanner scanner){
        try{
            int id = -1;
            System.out.println("Employee ID:");

            while(true){
                if(scanner.hasNextInt()){
                    id = scanner.nextInt();
                    scanner.nextLine();

                    if(id <= 0){
                        System.out.println("ID must be positive:");
                        continue;
                    }
                    try{
                        String check = "SELECT employee_id FROM mechanic WHERE employee_id = ?";
                        PreparedStatement stmt = conn.prepareStatement(check);
                        stmt.setInt(1,id);
                        ResultSet rs = stmt.executeQuery();

                        if(rs.next()){
                            System.out.println("Employee ID already exists");
                            continue;
                        }
                        break;
                    }catch(SQLException e){
                        System.out.println("Database error");
                        return;
                    }
                }else{
                    System.out.println("Enter a valid number:");
                    scanner.nextLine();
                }
            }

            System.out.println("First Name:");
            String fname = scanner.nextLine();
            if(fname.isEmpty()){
                System.out.println("Invalid first name");
                return;
            }

            System.out.println("Last Name:");
            String lname = scanner.nextLine();
            if(lname.isEmpty()){
                System.out.println("Invalid last name");
                return;
            }

            System.out.println("Years Experience:");
            int exp = scanner.nextInt();
            scanner.nextLine();
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

    public static void addCar(Connection conn, Scanner scanner){
        try{
            String vin;
            while(true){
                System.out.println("VIN:");
                vin = scanner.nextLine();

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
                }catch(SQLException e){
                    System.out.println("Database error");
                    return;
                }
            }

            System.out.println("Make:");
            String make = scanner.nextLine();
            if(make.isEmpty()){
                System.out.println("Invalid make");
                return;
            }

            System.out.println("Model:");
            String model = scanner.nextLine();
            if(model.isEmpty()){
                System.out.println("Invalid model");
                return;
            }

            System.out.println("Year:");
            int year = scanner.nextInt();
            scanner.nextLine();
            if(year < 0 || year > 2026){
                System.out.println("Year must be between 0 and 2026");
                return;
            }

            int customer_id = -1;
            while(true){
                System.out.println("Customer ID:");
                customer_id = scanner.nextInt();
                scanner.nextLine();

                try{
                    String sql = "SELECT * FROM customer WHERE id = ?";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setInt(1,customer_id);
                    ResultSet rs = stmt.executeQuery();

                    if(rs.next()){
                        break;
                    }
                    System.out.println("Customer not found");
                }catch(SQLException e){
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

    public static void initiateServiceRequest(Connection conn, Scanner scanner){
        try{
            System.out.print("Enter last name: ");
            String lastName = scanner.nextLine();

            String query = "SELECT * FROM customer WHERE lname = ?";
            PreparedStatement statement = conn.prepareStatement(query); // prepares sql query with a placeholder (?) so we can safely insert user input
            statement.setString(1,lastName); // replaces the ? in the sql query with the last name entered by the user
            ResultSet result = statement.executeQuery(); // executes the query and returns the matching rows from the database

            ArrayList<Integer> ids = new ArrayList<Integer>(); // stores the customer ids we find so we can let the user choose one later
            int count = 0;

            while(result.next()){ // loops through each row returned from the query
                int id = result.getInt("id"); // gets the id column from the current row
                String lname = result.getString("lname"); // gets the lname column from the current row

                count++;
                ids.add(id); // saves the id into the list so we can retrieve it by index later

                System.out.println(count + ". " + lname + " (id " + id + ")");
            }

            int customerId;
            if(count == 0){
                System.out.println("No customers found.");
                System.out.print("Add new customer? (y/n): ");
                String answer = scanner.nextLine();

                if(answer.equalsIgnoreCase("y")){ // checks user input ignoring uppercase/lowercase
                    addCustomer(conn,scanner); // calls helper function that inserts a new customer into the database

                    System.out.print("Enter id of new customer: ");
                    customerId = scanner.nextInt();
                    scanner.nextLine();
                }else{
                    System.out.println("Cancelled.");
                    return; // exits the function early if the user cancels
                }
            }else if(count == 1){
                customerId = ids.get(0); // automatically selects the only customer found
            }else{
                System.out.print("Choose customer number: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                customerId = ids.get(choice - 1); // converts the displayed number into the correct array index
            }

            System.out.println("Selected customer id: " + customerId);

            String carQuery = "SELECT * FROM car WHERE customer_id = ?";
            PreparedStatement carStatement = conn.prepareStatement(carQuery); // prepares query to find cars owned by the selected customer
            carStatement.setInt(1,customerId); // inserts the selected customer id into the query
            ResultSet carResult = carStatement.executeQuery(); // executes query and returns all cars belonging to that customer

            ArrayList<String> vins = new ArrayList<String>(); // stores car vins so user can choose one
            int carCount = 0;

            while(carResult.next()){ // loops through each car found for the customer
                String vin = carResult.getString("vin"); // retrieves vin from row
                String make = carResult.getString("make"); // retrieves make
                String model = carResult.getString("model"); // retrieves model
                int year = carResult.getInt("year"); // retrieves year

                carCount++;
                vins.add(vin); // saves vin so we can reference it later when user chooses a car

                System.out.println(carCount + ". " + year + " " + make + " " + model + " (vin " + vin + ")");
            }

            String selectedVin;
            if(carCount == 0){
                System.out.println("Customer has no cars.");
                System.out.print("Add new car? (y/n): ");
                String answer = scanner.nextLine();

                if(answer.equalsIgnoreCase("y")){
                    addCar(conn,scanner); // calls helper function that inserts a new car into the database

                    System.out.print("Enter vin of new car: ");
                    selectedVin = scanner.nextLine();
                }else{
                    System.out.println("Cancelled.");
                    return;
                }
            }else if(carCount == 1){
                selectedVin = vins.get(0); // automatically selects the only car
            }else{
                System.out.print("Choose car number: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                selectedVin = vins.get(choice - 1); // converts displayed number into correct array index
            }

            System.out.print("Enter service request id: ");
            int requestId = scanner.nextInt();
            scanner.nextLine();

            String checkRequest = "SELECT service_request_id FROM service_request WHERE service_request_id = ?";
            PreparedStatement checkRequestStatement = conn.prepareStatement(checkRequest); // prepares query to check if request id already exists
            checkRequestStatement.setInt(1,requestId); // inserts entered request id into query
            ResultSet checkRequestResult = checkRequestStatement.executeQuery(); // runs query to check for duplicates

            if(checkRequestResult.next()){ // if a row exists then the id already exists
                System.out.println("Service request id already exists.");
                return;
            }

            System.out.print("Enter service date (YYYY-MM-DD): ");
            String serviceDate = scanner.nextLine();

            System.out.print("Enter problem description: ");
            String problem = scanner.nextLine();

            System.out.print("Enter odometer reading: ");
            int odometer = scanner.nextInt();
            scanner.nextLine();

            if(odometer < 0){
                System.out.println("Odometer reading cannot be negative.");
                return;
            }

            String insert =
            "INSERT INTO service_request (service_request_id, service_date, problem, odometer_reading, is_closed, vin) VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement insertStatement = conn.prepareStatement(insert); // prepares insert statement for new service request
            insertStatement.setInt(1,requestId); // sets request id
            insertStatement.setDate(2,java.sql.Date.valueOf(serviceDate)); // converts string date into sql date format
            insertStatement.setString(3,problem); // sets problem description
            insertStatement.setInt(4,odometer); // sets odometer value
            insertStatement.setBoolean(5,false); // new requests start as not closed
            insertStatement.setString(6,selectedVin); // links request to selected car vin
            insertStatement.executeUpdate(); // executes the insert query

            System.out.println("Service request created.");
        }catch(Exception e){
            System.out.println("Error: " + e.getMessage()); // prints the error message if something goes wrong
        }
    }

    public static void closeServiceRequest(Connection conn, Scanner scanner){
        try{
            System.out.print("Enter service request id: ");
            int serviceRequestId = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Enter mechanic employee id: ");
            int mechanicId = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Enter closing date (YYYY-MM-DD): ");
            String closedDateInput = scanner.nextLine();

            System.out.print("Enter closing comments: ");
            String comments = scanner.nextLine();

            System.out.print("Enter final bill amount: ");
            double finalBill = scanner.nextDouble();
            scanner.nextLine();

            if(finalBill < 0){
                System.out.println("Final bill cannot be negative.");
                return; // stops the method if the bill is invalid
            }

            java.sql.Date closedDate = java.sql.Date.valueOf(closedDateInput); // converts the user-entered string date into a sql date object

            String findRequestSql = "SELECT service_date, is_closed FROM service_request WHERE service_request_id = ?";
            PreparedStatement findRequestStmt = conn.prepareStatement(findRequestSql); // prepares query to find the service request
            findRequestStmt.setInt(1,serviceRequestId); // inserts the entered request id into the query
            ResultSet requestRs = findRequestStmt.executeQuery(); // executes query and returns the matching row

            if(!requestRs.next()){ // if no row exists then the request id was not found
                System.out.println("Service request not found.");
                return;
            }

            java.sql.Date serviceDate = requestRs.getDate("service_date"); // gets the original service date from the database
            boolean isClosed = requestRs.getBoolean("is_closed"); // checks whether the request is already closed

            if(isClosed){
                System.out.println("This service request is already closed.");
                return;
            }

            if(closedDate.before(serviceDate)){ // ensures closing date is not before the original service date
                System.out.println("Closing date cannot be before the service date.");
                return;
            }

            String findMechanicSql = "SELECT * FROM mechanic WHERE employee_id = ?";
            PreparedStatement findMechanicStmt = conn.prepareStatement(findMechanicSql); // prepares query to verify the mechanic exists
            findMechanicStmt.setInt(1,mechanicId); // inserts mechanic id into the query
            ResultSet mechanicRs = findMechanicStmt.executeQuery(); // runs query to check for a matching mechanic

            if(!mechanicRs.next()){ // if no row returned then mechanic id does not exist
                System.out.println("Mechanic not found.");
                return;
            }

            String updateRequestSql =
            "UPDATE service_request " +
            "SET is_closed = ?, closed_date = ?, comments = ?, final_bill = ?, employee_id = ? " +
            "WHERE service_request_id = ?"; // sql update statement that closes the service request

            PreparedStatement updateStatement = conn.prepareStatement(updateRequestSql); // prepares update query
            updateStatement.setBoolean(1,true); // sets is_closed to true
            updateStatement.setDate(2,closedDate); // sets closing date
            updateStatement.setString(3,comments); // saves mechanic comments
            updateStatement.setDouble(4,finalBill); // records final bill amount
            updateStatement.setInt(5,mechanicId); // records which mechanic closed the request
            updateStatement.setInt(6,serviceRequestId); // specifies which service request to update

            int rowsUpdated = updateStatement.executeUpdate(); // executes update and returns number of rows changed

            if(rowsUpdated > 0){ // if at least one row changed then update succeeded
                System.out.println("Service request closed successfully.");
            }else{
                System.out.println("Could not close service request.");
            }
        }catch(SQLException e){
            System.out.println("SQL Error: " + e.getMessage()); // catches database related errors
        }catch(IllegalArgumentException e){
            System.out.println("Invalid date format. Please use YYYY-MM-DD."); // catches errors if date format cannot be converted
        }
    }
}