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
import model.Admin;
import model.EmployeeParams;

public class AdminViewController implements Initializable {

	@FXML
	private Pane adminOperationsPane, adminViewPane;
	@FXML
	private TableView empoyeeLogsTableView;
	@FXML
	private TextField deleteEmpTextFeild, addEmpID, addName, addContactNo, addEmailID,
			addAddress, addUserID, addRole;
	@FXML
	private Label errorLabel, errorAddEmployee, errorAddEmployee1;
	private Stage dialogStage, primaryStage, updateStage;
	private ObservableList<ObservableList> data;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		adminViewPane.setVisible(true);
		adminOperationsPane.setVisible(false);

	}

	@FXML
	public void addDeleteEmpOnAction() {
		adminViewPane.setVisible(false);
		adminOperationsPane.setVisible(true);

		if (!(empoyeeLogsTableView.getItems() == null) && !(data == null)) {
			empoyeeLogsTableView.getItems().clear();
			empoyeeLogsTableView.getColumns().clear();
			data.clear();
		}

		data = FXCollections.observableArrayList();
		Admin adminEmployeeOperations = new Admin();
		ResultSet rs = adminEmployeeOperations.getEmployeeLog();
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

				empoyeeLogsTableView.getColumns().addAll(col);
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
			empoyeeLogsTableView.setItems(data);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@FXML
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

	@FXML
	public void deleteEmpOnAction() {
		errorLabel.setText("");
		Admin adminEmployeeOperations = new Admin();
		if (!(deleteEmpTextFeild.getText().isEmpty())) {
			adminEmployeeOperations.deleteParentOrders(Integer.parseInt(deleteEmpTextFeild.getText()));
			adminEmployeeOperations.deleteParent(Integer.parseInt(deleteEmpTextFeild.getText()));
			adminEmployeeOperations.deleteEmployee(Integer.parseInt(deleteEmpTextFeild.getText()));

		} else {
			errorLabel.setText("Enter Employee Id");
		}
		addDeleteEmpOnAction();
		deleteEmpTextFeild.setText("");
		addDeleteEmpOnAction();

	}

	@FXML
	public void addEmployeeOnAction() {
		Boolean add = false;
		errorAddEmployee.setText("");
		errorAddEmployee1.setText("");

		EmployeeParams employeeParams = new EmployeeParams();
		Admin adminEmployeeOperations = new Admin();
		if (!(addEmpID.getText().toString()).isEmpty() && !(addName.getText().toString()).isEmpty()
				&& !(addContactNo.getText().toString()).isEmpty()
				&& !(addEmailID.getText().toString()).isEmpty()
				&& !(addAddress.getText().toString()).isEmpty() && !(addUserID.getText().toString()).isEmpty()
				&& !(addRole.getText().toString()).isEmpty()) {
			add = true;
			employeeParams.setEmpId(addEmpID.getText());
			employeeParams.setName(addName.getText());
			if (((addContactNo.getText()).length() == 10)) {
				employeeParams.setPhoneNumber(Long.parseLong(addContactNo.getText()));
			} else {
				add = false;
				errorAddEmployee.setText("Enter valid Phone number");

			}

			if (addEmailID.getText().contains("@") && addEmailID.getText().contains(".")) {
				employeeParams.setEmail(addEmailID.getText());

			} else {
				errorAddEmployee.setText("Enter Valid Employee Email ID");
				add = false;
			}
			employeeParams.setAddress(addAddress.getText());
			employeeParams.setUserId(addUserID.getText());
			employeeParams.setRole(addRole.getText());

		} else {
			errorAddEmployee.setText("Enter employee info. in all fields");
			add = false;
		}
		if (addEmailID.getText().contains("@") && addEmailID.getText().contains(".") && add.equals(true)) {
			employeeParams.setEmail(addEmailID.getText());

		} else if (add.equals(true)) {
			errorAddEmployee.setText("Enter Valid Employee Email ID");
			add = false;
		}

		if (add == true) {
			add = adminEmployeeOperations.addEmp(employeeParams);
		}

		if (add == true) {
			errorAddEmployee.setText("Employee added");
			errorAddEmployee1
					.setText("Temporary Login ID: " + addName.getText().toLowerCase() + " Password : 123");
			addEmpID.clear();
			addUserID.clear();
			addName.clear();
			addContactNo.clear();
			addRole.clear();
			addEmailID.clear();
			addAddress.clear();
		}

		addDeleteEmpOnAction();

	}

	@FXML
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
	public void backOnAction() {
		adminViewPane.setVisible(true);
		adminOperationsPane.setVisible(false);

	}

	public void setDialogStage(Stage primaryStage) {
		this.dialogStage = primaryStage;

	}

}
