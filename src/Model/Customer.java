/*
 * This model contains all the database operations related to customer
 */
package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Saraf Noor Khaled
 */
public class Customer {
    Connector connector = new Connector();
    Connection connection = null;
    Statement stmt = null;
    
        public boolean addNewCustomer(EmployeeParams employeeParams) {
        Boolean bValue = false;
        int rs = 0;
        int ns = 0;
        try {
            connection = connector.connectDataBase();
            stmt = connection.createStatement();
            String query = "INSERT INTO employee_cd(empId,name,phoneNo,email,address)" + "VALUES ('"
                    + employeeParams.getEmpId() + "','" + employeeParams.getName() + "','"
                    + employeeParams.getPhoneNumber() + "','" + employeeParams.getEmail() + "','"
                    + employeeParams.getAddress() + "')";
            System.out.println(query);
            rs = stmt.executeUpdate(query);

            if (rs == 1) {
                stmt = connection.createStatement();
                String query2 = "INSERT INTO users_cd(userId,empId,username,password,role)" + "VALUES ('"
                        + employeeParams.getUserId() + "','" + employeeParams.getEmpId() + "','"
                        + employeeParams.getUserName() + "','" + employeeParams.getPassword() + "','"
                        + employeeParams.getRole() + "')";
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

    public ResultSet getEmployeeId() {
        ResultSet rs = null;
        try {
            connection = connector.connectDataBase();
            stmt = connection.createStatement();
            String query = "SELECT * from employee_cd order by empId desc limit 1";
            System.out.println(query);
            rs = stmt.executeQuery(query);

        } catch (SQLException e) {
            System.out.println("\nData retrival form database  failed  " + e);
        }

        return rs;
    }

    public ResultSet getMenuItem() {
        ResultSet rs = null;
        try {
            connection = connector.connectDataBase();
            stmt = connection.createStatement();
            String query = "SELECT * from menuItem_cd";
            System.out.println(query);
            rs = stmt.executeQuery(query);

        } catch (SQLException e) {
            System.out.println("\nFailed to fetch data from Database  " + e);
        }

        return rs;
    }

}
