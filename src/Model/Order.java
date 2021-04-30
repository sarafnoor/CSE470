package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Order {
    Connector connector = new Connector();
    Connection connection = null;
    Statement stmt = null;

    public ResultSet getOrderLog() {
        ResultSet rs = null;
        try {
            connection = connector.connectDataBase();
            stmt = connection.createStatement();
            String query = "SELECT * from orderss_cd";
            System.out.println(query);
            rs = stmt.executeQuery(query);

        } catch (SQLException e) {
            System.out.println("\nFailed to fetch data from Database  " + e);
        }

        return rs;
    }

    public void insertsOrder(OrderParams orderParams) {
        try {
            connection = connector.connectDataBase();
            stmt = connection.createStatement();
            String query = null;
            query = "INSERT INTO orderss_cd(userid,orderItems,orderPrice)"
                    + "VALUES ('" + orderParams.getUserid() + "','"
                    + orderParams.getOrderItems() + "','"
                    + orderParams.getOrderPrice() + "')";
            System.out.println(" SQL Query --> " + query);
            stmt.executeUpdate(query);

            stmt.close();
        } catch (SQLException e) {
            System.out.println("\nFailed to insert records in the table " + e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}
