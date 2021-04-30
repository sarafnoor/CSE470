package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {

    public static Connection connectDataBase() {
        // Creating Connection object.
        Connection connection = null;
        try {
            //DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            
        } catch (SQLException e) {
            System.out.println("Unable to load  jdbc driver class" + e);
        }
		
        final String url = "jdbc:mysql://localhost:3306/coffeedb";
        final String userName = "root";
        final String password = "alpha";

        try {
            connection = DriverManager.getConnection(url, userName, password);
        } catch (SQLException err) {
            System.out.println("Unable to establish connection with database " + err);
        }

        return connection;
    }
    public static void main(String[] args) {
        Connector con=new Connector();
        
         Connection connection =Connector.connectDataBase();
                
    }

}
