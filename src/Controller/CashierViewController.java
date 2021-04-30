package controller;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import model.Order;
import model.Inventory;

public class CashierViewController implements Initializable {

    @FXML
    private Pane cashierViewPane, cashierOperationsPane, inventoryPane;
    @FXML
    private TableView orderLogsTableView, inventoryTableView;
    @FXML
    private TextField deleteInventoryTextFeild, addItemID, addItemName, addItemQuantity, updateItemID,
            updateItemQuantity;
    @FXML
    private Label errorLabel, errorAddInventory, errorUpdateInventory;

    private Stage dialogStage, primaryStage, updateStage;
    private ObservableList<ObservableList> data;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        cashierViewPane.setVisible(true);
        cashierOperationsPane.setVisible(false);
        inventoryPane.setVisible(false);
    }

    @FXML
    public void viewOrderLogsOnAction() {
        if (!(orderLogsTableView.getItems() == null) && !(data == null)) {
            orderLogsTableView.getItems().clear();
            orderLogsTableView.getColumns().clear();
            data.clear();
        }
        cashierViewPane.setVisible(false);
        cashierOperationsPane.setVisible(true);
        inventoryPane.setVisible(false);

        data = FXCollections.observableArrayList();
        Order getOrderLog = new Order();
        ResultSet rs = getOrderLog.getOrderLog();
        try {
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory(
                        new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                orderLogsTableView.getColumns().addAll(col);
                System.out.println("Column [" + i + "] ");
            }

            while (rs.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    row.add(rs.getString(i));
                }
                System.out.println("Row [1] added " + row);
                data.add(row);

            }
            orderLogsTableView.setItems(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void inventoryOnAction() {
        if (!(inventoryTableView.getItems() == null) && !(data == null)) {
            inventoryTableView.getItems().clear();
            inventoryTableView.getColumns().clear();
            data.clear();
        }
        cashierViewPane.setVisible(false);
        cashierOperationsPane.setVisible(false);
        inventoryPane.setVisible(true);

        Inventory getInventoryData = new Inventory();
        ResultSet rs = getInventoryData.getInventoryData();

        data = FXCollections.observableArrayList();

        try {
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory(
                        new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                inventoryTableView.getColumns().addAll(col);
                System.out.println("Column [" + i + "] ");
            }

            while (rs.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    row.add(rs.getString(i));
                }
                System.out.println("Row [1] added " + row);
                data.add(row);

            }
            inventoryTableView.setItems(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void backOnAction() {
        cashierViewPane.setVisible(true);
        cashierOperationsPane.setVisible(false);
        inventoryPane.setVisible(false);

    }

    public void updateInformation() {
        updateStage = new Stage();
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/updatePofile.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            updateStage.setTitle("Saraf Coffee Shop Management System");
            Scene scene = new Scene(root);
            updateStage.setScene(scene);
            UpdateProfileController controller = loader.getController();

            controller.setDialogStage(updateStage);
            updateStage.show();
        } catch (Exception e) {
            System.out.println("Error occured while inflating view: " + e.getMessage());
        }
    }

    public void logoutButtonOnAction() {
        try {
            primaryStage = new Stage();
            dialogStage.fireEvent(new WindowEvent(dialogStage, WindowEvent.WINDOW_CLOSE_REQUEST));

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/logOut.fxml"));
            AnchorPane root = (AnchorPane) loader.load();

            primaryStage.setTitle("Saraf Coffee Shop Management System");
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            LogoutController controller = loader.getController();
            controller.setDialogStage(primaryStage);
            primaryStage.show();
        } catch (Exception e) {
            System.out.println("Error occured while inflating view: " + e);
        }
    }

    @FXML
    public void deleteInvOnAction() {
        errorLabel.setText("");
        Inventory getInventoryData = new Inventory();
        if (!(deleteInventoryTextFeild.getText().isEmpty())) {
            getInventoryData.deleteInventoryData(Integer.parseInt(deleteInventoryTextFeild.getText()));
        } else {
            errorLabel.setText("Enter Inventory item Id");
        }
        inventoryOnAction();
        deleteInventoryTextFeild.setText("");
    }

    void addItemOnAction() {

        boolean check = false;
        errorAddInventory.setText("");
        Inventory inventoryParams = new Inventory();
        Inventory getInventoryData = new Inventory();
        if (!(addItemQuantity.getText().isEmpty()) && !(addItemName.getText().isEmpty())
                && !(addItemID.getText().isEmpty())) {
            inventoryParams.setItemId(addItemID.getText());
            inventoryParams.setItemName(addItemName.getText());
            inventoryParams.setItemQuantity(Integer.parseInt(addItemQuantity.getText()));
            getInventoryData.addInventoryData(inventoryParams);
        } else {
            check = true;
            errorAddInventory.setText("Enter all Inventory item details");
        }
        inventoryOnAction();
        addItemQuantity.setText("");
        addItemName.setText("");
        addItemID.setText("");
    }

    @FXML
    void updateItemOnAction() {
        errorUpdateInventory.setText("");
        Inventory inventoryParams = new Inventory();
        Inventory getInventoryData = new Inventory();
        if (!(updateItemID.getText().isEmpty()) && !(updateItemQuantity.getText().isEmpty())) {
            inventoryParams.setItemId(updateItemID.getText());
            inventoryParams.setItemQuantity(Integer.parseInt(updateItemQuantity.getText()));
            getInventoryData.updateInventory(inventoryParams);
        } else {
            errorUpdateInventory.setText("Enter Inventory item details to be updated");
        }
        inventoryOnAction();
        updateItemID.setText("");
        updateItemQuantity.setText("");

    }

    public void setDialogStage(Stage primaryStage) {
        this.dialogStage = primaryStage;
    }

}
