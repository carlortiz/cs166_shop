import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:36238/corti001_shop";
        String user = "corti001";
        String password = "";

        try (
            Connection conn = DriverManager.getConnection(url, user, password);
            Scanner scanner = new Scanner(System.in);
        ) {
            System.out.println("Connected to database!");
            boolean running = true;

            while (running) {
                System.out.println("\n===== Mechanics Shop Menu =====");
                System.out.println("1. Add Customer");
                System.out.println("2. Add Mechanic");
                System.out.println("3. Add Car");
                System.out.println("4. Initiate Service Request");
                System.out.println("5. Close Service Request");
                System.out.println("6. Lists");
                System.out.println("0. Exit");
                System.out.print("Choose option: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // consume newline

                switch (choice) {
                    case 1:
                        System.out.println("Add Customer selected.");
                        break;
                    case 2:
                        System.out.println("Add Mechanic selected.");
                        break;
                    case 3:
                        System.out.println("Add Car selected.");
                        break;
                    case 4:
                        System.out.println("Initiate Service Request selected.");
                        break;
                    case 5:
                        System.out.println("Close Service Request selected.");
                        break;
                    case 6:
                        System.out.println("Reports selected.");
                        break;
                    case 0:
                        running = false;
                        System.out.println("Exiting program.");
                        break;
                    default:
                        System.out.println("Invalid option.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

// initiate a service request
public static void initiateServiceRequest(Connection conn, Scanner scanner)
{
    System.out.print("Enter last name: ");
    String lastName = scanner.nextLine();

    String findCustomersByLastName = "SELECT * FROM customer where lastName = ?";
    PreparedStatement stmt = conn.prepareStatement(sql);
    stmt.setString(1, lastName);
    ResultSet rs = stmt.executeQuery();

    // 3. Handle multiple matches
    if ()

    // 4. Handle no matches
    // 5. Select or add car
    // 6. Insert service_request
    
}

// close a service request
public static void closeServiceRequest(Connection conn, Scanner scanner)
{
    // TODO:
    // 1. Ask for service_request_id
    // 2. Ask for employee_id
    // 3. Validate request exists
    // 4. Validate mechanic exists
    // 5. Validate dates
    // 6. Update service_request as closed
    
}
