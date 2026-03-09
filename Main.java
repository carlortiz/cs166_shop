import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:36238/corti001_shop";
        String user = "corti001";
        String password = "Cc030503091101033017///";

        try(
            Connection conn = DriverManager.getConnection(url,user,password);
            Scanner scanner = new Scanner(System.in);
        ){
            System.out.println("Connected to database!");
            boolean running = true;

            while(running){
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

                switch(choice){
                    case 1:
                        Add_functions.addCustomer(conn,scanner);
                        break;
                    case 2:
                        Add_functions.addMechanic(conn,scanner);
                        break;
                    case 3:
                        Add_functions.addCar(conn,scanner);
                        break;
                    case 4:
                        Add_functions.initiateServiceRequest(conn,scanner);
                        break;
                    case 5:
                        Add_functions.closeServiceRequest(conn,scanner);
                        break;
                    case 6:
                        System.out.println("\nClosed requests with final bill less than 100:");
                        Query_functions.closedRequests(conn);

                        System.out.println("\nCustomers who paid less than 100:");
                        Query_functions.customersLessThan100(conn);

                        System.out.println("\nCustomers with more than 20 cars:");
                        Query_functions.customersMoreThan20Cars(conn);

                        System.out.println("\nCars built before 1995 with less than 50000 miles:");
                        Query_functions.carsBefore1995(conn);

                        System.out.print("\nEnter k for top cars with most open service requests: ");
                        int k = scanner.nextInt();
                        scanner.nextLine();
                        Query_functions.topKCars(conn,k);

                        System.out.println("\nCustomers ordered by total bill:");
                        Query_functions.customersTotalBill(conn);
                        break;
                    case 0:
                        running = false;
                        System.out.println("Exiting program.");
                        break;
                    default:
                        System.out.println("Invalid option.");
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}