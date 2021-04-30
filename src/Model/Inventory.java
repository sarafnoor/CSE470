package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Inventory {
    Connector connector = new Connector();
    Connection connection = null;
    Statement stmt = null;
    private String itemId;
    private String itemName;
    private int itemQuantity;
    
    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public ResultSet getInventoryData() {
        ResultSet rs = null;
        try {
            connection = connector.connectDataBase();
            stmt = connection.createStatement();
            String query = "SELECT * from inventory_cd";
            System.out.println(query);
            rs = stmt.executeQuery(query);

        } catch (SQLException e) {
            System.out.println("\nFailed to fetch data from Database  " + e);
        }

        return rs;
    }

    public Boolean addInventoryData(Inventory inventoryParams) {
        Boolean bValue = false;
        int rs = 0;
        try {
            connection = connector.connectDataBase();
            stmt = connection.createStatement();
            String query = "INSERT INTO inventory_cd(itemId,itemName,itemQuantity)"
                    + "VALUES ('" + inventoryParams.getItemId() + "','"
                    + inventoryParams.getItemName() + "','"
                    + inventoryParams.getItemQuantity() + "')";
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

    public Boolean updateInventory(Inventory inventoryParams) {
        Boolean bValue = false;
        int rs = 0;
        try {
            connection = connector.connectDataBase();
            stmt = connection.createStatement();
            String query = "update inventory_cd set itemQuantity = '" + inventoryParams.getItemQuantity() + "'  where itemId='" + inventoryParams.getItemId() + "' ";
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
    
    public Boolean deleteInventoryData(int invId) {
        Boolean bValue = false;
        int rs = 0;
        try {
            connection = connector.connectDataBase();
            stmt = connection.createStatement();
            String query = "Delete from inventory_cd where itemId='" + invId + "' ";
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
}
