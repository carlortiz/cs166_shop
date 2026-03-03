import java.sql.*;

public class Query_functions {

    public static void closedRequests(Connection conn){
        try{
            String sql =
            "SELECT closed_date, comments, final_bill FROM service_request " +
            "WHERE is_closed = 1 AND final_bill < 100";
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


    public static void customersLessThan100(Connection conn){
        try{
            String sql =
            "SELECT DISTINCT c.fname, c.lname " +
            "FROM customer c " +
            "JOIN car ca ON c.id = ca.customer_id " +
            "JOIN service_request sr ON ca.vin = sr.vin " +
            "WHERE sr.final_bill < 100";

            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                System.out.println(
                    rs.getString("fname") + " " +
                    rs.getString("lname")
                );
            }

        }catch(SQLException e){
            System.out.println("Error retrieving customers");
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
}
