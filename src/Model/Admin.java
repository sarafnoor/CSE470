package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Admin {
    Connector connector = new Connector();
    Connection connection = null;
    Statement stmt = null;

    public ResultSet getEmployeeLog() {
        ResultSet rs = null;
        try {
            connection = connector.connectDataBase();
            stmt = connection.createStatement();
            String query = "SELECT * from employee_cd";
            System.out.println(query);
            rs = stmt.executeQuery(query);

        } catch (SQLException e) {
            System.out.println("\nFailed to fetch data from Database  " + e);
        }

        return rs;
    }

    public Boolean deleteEmployee(int empId) {
        Boolean bValue = false;
        int rs = 0;
        try {
            connection = connector.connectDataBase();
            stmt = connection.createStatement();
            String query = "Delete from employee_cd where empId='" + empId + "' ";
            System.out.println(query);
            rs = stmt.executeUpdate(query);

            if (rs == 1) {
                bValue = true;
            }

        } catch (SQLException e) {
            System.out.println("\nFailed to fetch data from Database  " + e);
        }
        return bValue;
    }

    public void deleteParentOrders(int empId) {
        User user = new User();
        int rs = 0;
        try {
            connection = connector.connectDataBase();
            stmt = connection.createStatement();
            String query = "Delete from orderss_cd where userid='" + user.getMenuItem(String.valueOf(empId)) + "' ";
            System.out.println(query);
            rs = stmt.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("\nFailed to fetch data from Database  " + e);
        }
    }

    public void deleteParent(int empId) {
        int rs = 0;
        try {
            connection = connector.connectDataBase();
            stmt = connection.createStatement();
            String query = "Delete from users_cd where empId='" + empId + "' ";
            System.out.println(query);
            rs = stmt.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("\nFailed to fetch data from Database  " + e);
        }
    }

    public Boolean addEmp(EmployeeParams employeeParams) {
        Boolean bValue = false;
        int rs = 0;
        int ns = 0;
        try {
            connection = connector.connectDataBase();
            stmt = connection.createStatement();
            String query = "INSERT INTO employee_cd(empId,name,phoneNo,email, address)" + "VALUES ('"
                    + employeeParams.getEmpId() + "','" + employeeParams.getName() + "','"
                    + employeeParams.getPhoneNumber()+ "','" + employeeParams.getEmail() + "','" + employeeParams.getAddress() + "')";
            System.out.println(query);
            rs = stmt.executeUpdate(query);

            if (rs == 1) {
                stmt = connection.createStatement();
                String query2 = "INSERT INTO users_cd(userId,empId,username,password,role)" + "VALUES ('"
                        + employeeParams.getUserId() + "','" + employeeParams.getEmpId() + "','"
                        + employeeParams.getName().toLowerCase() + "','" + "123" + "','" + employeeParams.getRole() + "')";
                System.out.println(query2);
                ns = stmt.executeUpdate(query2);
                if (ns == 1) {
                    bValue = true;
                }

            }

        } catch (SQLException e) {
            System.out.println("\nFailed to fetch data from Database  " + e);
        }

        return bValue;
    }
}
