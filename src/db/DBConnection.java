package db;

import java.sql.*;

public class DBConnection {
    // Change the parameters accordingly.
    //private static String dbUrl = "jdbc:mysql://127.0.0.1:3306/dblp_data?useUnicode=true&characterEncoding=utf-8";
    private static String dbUrl1000 = "jdbc:mysql://127.0.0.1:3306/dblp_data1000?useUnicode=true&characterEncoding=utf-8";
    private static String user = "root";
    private static String password = "123456";

    public static Connection getConn() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection(dbUrl1000, user, password);
        } catch (Exception e) {
            System.out.println("Error while opening a conneciton to database server: "
                    + e.getMessage());
            return null;
        }
    }
}
