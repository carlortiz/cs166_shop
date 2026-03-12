import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        String url = "jdbc:postgresql://localhost:36238/corti001_shop";
        String user = "corti001";
        String password = "Cc030503091101033017///";

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
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        Add_functions.addCustomer(conn, scanner);
                        break;
                    case 2:
                        Add_functions.addMechanic(conn, scanner);
                        break;
                   case 3:
                        Add_functions.addCar(conn, scanner);
                        break;
                    case 4:
                        Add_functions.initiateServiceRequest(conn, scanner);
                        break;
                    case 5:
                        Add_functions.closeServiceRequest(conn, scanner);
                        break;
                    case 6:
                        boolean listRunning = true;
                        while (listRunning) {

                            System.out.println("\n===== Lists =====");
                            System.out.println("1. List date, comment, and bill for all closed requests with bill lower than 100");
                            System.out.println("2. List first and last name of customers having more than 20 different cars");
                            System.out.println("3. List Make, Model, and Year of all cars build before 1995 having less than 50000 miles");
                            System.out.println("4. List the make, model and number of service requests for the first k cars with the highest number of service orders.");
                            System.out.println("5. List the first name, last name and total bill of customers in descending order of their total bill for all cars brought to the mechanic.");
                            System.out.println("0. To return to main menu");
                            System.out.print("Choose option: ");

                            int listChoice = scanner.nextInt();
                            scanner.nextLine();

                            switch (listChoice) {
                                case 1:
                                    System.out.println("Closed requests with bill lower than 100:");
                                    Query_functions.closedRequests(conn);
                                    break;
                                case 2:
                                    System.out.println("Customers with more than 20 cars:");
                                    Query_functions.customersMoreThan20Cars(conn);
                                    break;
                                case 3:
                                    System.out.println("Cars built before 1995 with less than 50000 miles:");
                                    Query_functions.carsBefore1995(conn);
                                    break;
                                case 4:
                                    System.out.print("Enter k: ");
                                    int k = scanner.nextInt();
                                    scanner.nextLine();

                                    System.out.println("First k cars with the highest number of service orders:");
                                    Query_functions.topKCars(conn, k);
                                    break;
                                case 5:
                                    System.out.println("Customers in descending order of total bill:");
                                    Query_functions.customersTotalBill(conn);
                                    break;
                                case 0:
                                    listRunning = false;
                                    System.out.println("Exiting Lists.");
                                    break;

                                default:
                                    System.out.println("Invalid option.");
                            }
                        }
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