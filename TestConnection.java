import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestConnection {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:36238/corti001_shop";
        String user = "corti001";
        String password = "";

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected successfully!");
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}