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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import model.Order;

public class EmployeeController implements Initializable {

	@FXML
	private Pane employeeViewPane, EmployeeOperationsPane;
	@FXML
	private TableView orderLogsTableView;
	private Stage primaryStage, dialogStage, updateStage;
	private ObservableList<ObservableList> data;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		employeeViewPane.setVisible(true);
		EmployeeOperationsPane.setVisible(false);
	}

	@FXML
	void viewOrderLogsOnAction() {
		employeeViewPane.setVisible(false);
		EmployeeOperationsPane.setVisible(true);

		if (!(orderLogsTableView.getItems() == null) && !(data == null)) {
			orderLogsTableView.getItems().clear();
			orderLogsTableView.getColumns().clear();
			data.clear();
		}

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
	void updateInformation() {
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

	@FXML
	void backOnAction() {
		employeeViewPane.setVisible(true);
		EmployeeOperationsPane.setVisible(false);
	}

	void logoutButtonOnAction() {
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

	public void setDialogStage(Stage primaryStage) {
		this.dialogStage = primaryStage;
	}

}