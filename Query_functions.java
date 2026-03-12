import java.sql.*;

public class Query_functions {

    public static void closedRequests(Connection conn){
        try{
            String sql =
            "SELECT closed_date, comments, final_bill FROM service_request " +
            "WHERE is_closed = true AND final_bill < 100";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                System.out.println(
                    rs.getDate("closed_date") + " | " +
                    rs.getString("comments") + " | " +
                    rs.getDouble("final_bill")
                );
            }

        }catch(SQLException e){
            System.out.println("Error retrieving requests");
            e.printStackTrace();
        }
    }

    public static void customersMoreThan20Cars(Connection conn){

        try{

            String sql =
            "SELECT c.fname, c.lname, COUNT(*) AS num_cars " +
            "FROM customer c " +
            "JOIN car ca ON c.id = ca.customer_id " +
            "GROUP BY c.id, c.fname, c.lname " +
            "HAVING COUNT(*) > 20";

            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                System.out.println(
                    rs.getString("fname") + " " +
                    rs.getString("lname") +
                    " Cars: " +
                    rs.getInt("num_cars")
                );
            }

        }catch(SQLException e){
            System.out.println("Error retrieving customers");
            e.printStackTrace();
        }
    }

    public static void carsBefore1995(Connection conn){
        try{
            String sql =
            "SELECT car.make, car.model, car.year " +
            "FROM car " +
            "JOIN service_request ON car.vin = service_request.vin " +
            "WHERE car.year < 1995 AND service_request.odometer_reading < 50000";
            
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
           
            while(result.next()){
                System.out.println(
                    result.getString("make") + " " +
                    result.getString("model") + " " +
                    result.getInt("year")
                );
            }
        }catch(SQLException e){
            System.out.println("Error retrieving cars");
            e.printStackTrace();
        }
    }
    
    public static void topKCars(Connection conn,int k){
        if(k <= 0){
            System.out.println("k must be greater than 0");
            return;
        }

        try{
            String sql =
            "SELECT car.make, car.model, COUNT(*) AS number_of_requests " +
            "FROM car " +
            "JOIN service_request ON car.vin = service_request.vin " +
            "WHERE service_request.is_closed = FALSE " +
            "GROUP BY car.vin, car.make, car.model " +
            "ORDER BY number_of_requests DESC " +
            "LIMIT ?";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1,k);
            ResultSet result = statement.executeQuery();

            while(result.next()){
                System.out.println(
                    result.getString("make") + " " +
                    result.getString("model") + " Requests: " +
                    result.getInt("number_of_requests")
                );
            }
        }catch(SQLException e){
            System.out.println("Error retrieving cars");
            e.printStackTrace();
        }
    }

    public static void customersTotalBill(Connection conn){
        try{
            String sql =
                "SELECT customer.fname, customer.lname, SUM(service_request.final_bill) AS total_bill " +
                "FROM customer " +
                "JOIN car ON customer.id = car.customer_id " +
                "JOIN service_request ON car.vin = service_request.vin " +
                "WHERE service_request.final_bill IS NOT NULL " +
                "GROUP BY customer.id, customer.fname, customer.lname " +
                "ORDER BY total_bill DESC";
                
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            while(result.next()){
                System.out.println(
                    result.getString("fname") + " " +
                    result.getString("lname") + " Total Bill: " +
                    result.getDouble("total_bill")
                );
            }
        }catch(SQLException e){
            System.out.println("Error retrieving customers");
            e.printStackTrace();
        }
    }
}
