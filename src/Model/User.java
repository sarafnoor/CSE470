package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class User {

    Connector connector = new Connector();
    Connection connection = null;
    Statement stmt = null;

    public String getMenuItem(String empId) {
        String userId = null;
        ResultSet rs = null;
        try {
            connection = connector.connectDataBase();
            stmt = connection.createStatement();
            String query = "SELECT * from users_cd where empId ='" + empId + "' ";
            System.out.println(query);
            rs = stmt.executeQuery(query);
            if (rs.next()) {
                userId = rs.getString(1);
            }
        } catch (SQLException e) {
            System.out.println("\nData retrival form database  failed  " + e);
        }

        return userId;
    }

    public String getUserId(String empId) {
        String userId = null;
        ResultSet rs = null;
        try {
            connection = connector.connectDataBase();
            stmt = connection.createStatement();
            String query = "SELECT * from users_cd where empId ='" + empId + "' ";
            System.out.println(query);
            rs = stmt.executeQuery(query);
            if (rs.next()) {
                userId = rs.getString(1);
            }
        } catch (SQLException e) {
            System.out.println("\nData retrieval from database failed  " + e);
        }

        return userId;
    }

    public ResultSet getUserDetails(String id) {
        ResultSet rs = null;
        try {
            connection = connector.connectDataBase();
            stmt = connection.createStatement();
            String query = "SELECT * from employee_cd where empId='" + id + "' ";
            System.out.println(query);
            rs = stmt.executeQuery(query);

        } catch (SQLException e) {
            System.out.println("\nData retrival form database  failed  " + e);
        }

        return rs;
    }

    public ResultSet getUsersloginDetails(String id) {
        ResultSet rs = null;
        try {
            connection = connector.connectDataBase();
            stmt = connection.createStatement();
            String query = "SELECT * from users_cd where empId='" + id + "' ";
            System.out.println(query);
            rs = stmt.executeQuery(query);

        } catch (SQLException e) {
            System.out.println("\nData retrival form database  failed  " + e);
        }

        return rs;
    }

    public Boolean updateAdress(String id, String value) {
        Boolean bValue = false;
        int rs = 0;
        try {
            connection = connector.connectDataBase();
            stmt = connection.createStatement();
            String query = "update employee_cd set address = '" + value + "'  where empId='" + id + "' ";
            System.out.println(query);
            rs = stmt.executeUpdate(query);

            if (rs == 1) {
                bValue = true;
            }

        } catch (SQLException e) {
            System.out.println("\nData retrival form database  failed  " + e);
        }

        return bValue;
    }

    public Boolean updateEmailID(String id, String value) {
        Boolean bValue = false;
        int rs = 0;
        try {
            connection = connector.connectDataBase();
            stmt = connection.createStatement();
            String query = "update employee_cd set email = '" + value + "'  where empId='" + id + "' ";
            System.out.println(query);
            rs = stmt.executeUpdate(query);

            if (rs == 1) {
                bValue = true;
            }

        } catch (SQLException e) {
            System.out.println("\nData retrival form database  failed  " + e);
        }

        return bValue;
    }

    public Boolean updateLoginId(String id, String value) {
        Boolean bValue = false;
        int rs = 0;
        try {
            connection = connector.connectDataBase();
            stmt = connection.createStatement();
            String query = "update users_cd set username = '" + value + "'  where empId='" + id + "' ";
            System.out.println(query);
            rs = stmt.executeUpdate(query);

            if (rs == 1) {
                bValue = true;
            }

        } catch (SQLException e) {
            System.out.println("\nData retrival form database  failed  " + e);
        }

        return bValue;
    }

    public Boolean updatePassword(String id, String value) {
        Boolean bValue = false;
        int rs = 0;
        try {
            connection = connector.connectDataBase();
            stmt = connection.createStatement();
            String query = "update users_cd set password = '" + value + "'  where empId='" + id + "' ";
            System.out.println(query);
            rs = stmt.executeUpdate(query);

            if (rs == 1) {
                bValue = true;
            }

        } catch (SQLException e) {
            System.out.println("\nData retrival form database  failed  " + e);
        }

        return bValue;
    }
}
