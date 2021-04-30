package controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Customer;
import model.User;
import model.Login;
import model.EmployeeParams;
import model.LoginParams;

public class LoginController implements Initializable {
	@FXML
	private Button signUpButton;
	@FXML
	private GridPane loginGridPane1, buttonsGridPane2, loginDetailsGridPane;
	@FXML
	private TextField loginIdTextField, name, userName, password, phoneNumber, email, address;
	@FXML
	private PasswordField passwordIdTextField;
	@FXML
	private Label errorMsg, custErr, loginViewLabel;
	@FXML
	private Pane custRegPane;
	private LoginParams loginParams;
	private Login loginDaoModel;
	private String roleName;
	private Stage primaryStage, dialogStage;
	private User user;
	private Properties prop;


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		loginDetailsGridPane.setVisible(false);
		loginGridPane1.setVisible(true);
		buttonsGridPane2.setVisible(true);
		custRegPane.setVisible(false);
	}

	void loginPanel() {
		loginGridPane1.setVisible(false);
		buttonsGridPane2.setVisible(false);
		loginDetailsGridPane.setVisible(true);
		signUpButton.setVisible(false);
	}

	@FXML
	void loginButtonOnAction() {
		try {
			loginParams = new LoginParams();
			loginDaoModel = new Login();
			Boolean isValidUser = false;

			if (!(loginIdTextField.getText().toString()).isEmpty()
					&& !(passwordIdTextField.getText().toString()).isEmpty()) {
				loginParams.setUsername(loginIdTextField.getText().toString());
				loginParams.setPassword(passwordIdTextField.getText().toString());
				ResultSet resultset = loginDaoModel.getResultSet(loginParams);
				int i = 3;

				if (resultset.next() == true && loginIdTextField.getText().equals(resultset.getString(3))
						&& passwordIdTextField.getText().equals(resultset.getString(4))) {

					if (!roleName.equals(resultset.getString(5))) {
						errorMsg.setText("");
						errorMsg.setText("Please enter " + roleName + " login details correctly...");

					} else {
						errorMsg.setText("");
						errorMsg.setText("Login Successful");
						System.out.println("Login Successful");
						isValidUser = true;

						try {
							String empId = resultset.getString(2);
							String name = null;
							String address = null;

							user = new User();
							ResultSet rs = user.getUserDetails(empId);

							if (rs.next()) {
								name = rs.getString(2);
								address = rs.getString(7);
							}
							OutputStream output = null;
							output = new FileOutputStream("config.properties");

							prop = new Properties();
							prop.setProperty("LoginID", loginIdTextField.getText());
							prop.setProperty("userEMPId", empId);
							prop.setProperty("user", name);
							prop.setProperty("address", address);

							prop.store(output, null);
							output.close();
						} catch (IOException e) {
							e.printStackTrace();
						}

					}

					i = 0;
				} else {
					errorMsg.setText("");
					errorMsg.setText("Please enter login details correctly...");
				}

				if (isValidUser && roleName.equals("customer")) {
					loadCustomerView();
				} else if (isValidUser && roleName.equals("admin")) {
					loadAdminView();
				} else if (isValidUser && roleName.equals("manager")) {
					loadCashierView();
				}

			} else {
				errorMsg.setText("");
				errorMsg.setText("Please enter login details correctly...");
			}
			loginIdTextField.clear();
			passwordIdTextField.clear();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void loadCashierView() {
		primaryStage = new Stage();
		dialogStage.fireEvent(new WindowEvent(dialogStage, WindowEvent.WINDOW_CLOSE_REQUEST));
		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/managerView.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			primaryStage.setTitle("Saraf Coffee Shop Management System");
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			CashierViewController controller = loader.getController();
			controller.setDialogStage(primaryStage);
			primaryStage.show();
		} catch (Exception e) {
			System.out.println("Error occured while inflating view: " + e.getMessage());

		}

	}

	private void loadAdminView() {
		primaryStage = new Stage();
		dialogStage.fireEvent(new WindowEvent(dialogStage, WindowEvent.WINDOW_CLOSE_REQUEST));
		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/adminView.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			primaryStage.setTitle("Saraf Coffee Shop Management System");
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			AdminViewController controller = loader.getController();
			controller.setDialogStage(primaryStage);
			primaryStage.show();
		} catch (Exception e) {
			System.out.println("Error occured while inflating view: " + e.getMessage());
		}
	}

	private void loadCustomerView() {
		primaryStage = new Stage();
		dialogStage.fireEvent(new WindowEvent(dialogStage, WindowEvent.WINDOW_CLOSE_REQUEST));
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/customerView.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			primaryStage.setTitle("Saraf Coffee Shop Management System");
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			CustomerViewController controller = loader.getController();
			controller.setDialogStage(primaryStage);
			primaryStage.show();
		} catch (Exception e) {
			System.out.println("Error occured while inflating view: " + e.getMessage());
		}

	}

	@FXML
	void backButtonOnAction() {
		loginIdTextField.clear();
		passwordIdTextField.clear();
		loginDetailsGridPane.setVisible(false);
		loginGridPane1.setVisible(true);
		buttonsGridPane2.setVisible(true);
		errorMsg.setText("");

	}

	@FXML
	void customerLoginOnAction() {
		loginViewLabel.setText("Customer Login");

		roleName = "customer";
		loginPanel();
		System.out.println("customer login");
		signUpButton.setVisible(true);

	}

	@FXML
	void cashierLoginOnAction() {
		loginViewLabel.setText("Cashier Login");
		roleName = "cashier";
		signUpButton.setVisible(false);

		loginPanel();
		System.out.println("cashier login");
	}

	@FXML
	void adminLoginOnAction() {
		loginViewLabel.setText("Admin Login");

		roleName = "admin";
		signUpButton.setVisible(false);

		loginPanel();
		System.out.println("admin login");
	}

	@FXML
	void signUpButtonOnAction() {
		loginGridPane1.setVisible(false);
		buttonsGridPane2.setVisible(false);
		loginDetailsGridPane.setVisible(false);
		custRegPane.setVisible(true);

	}

	@FXML
	void custDataSubmitOnAction() {
		boolean add = false;
		custErr.setText("");
		EmployeeParams employeeParams = new EmployeeParams();
		Customer customer = new Customer();
		ResultSet rs = customer.getEmployeeId();
		int test = 0;
		try {
			if (rs.next()) {
				String temp = null;
				test = Integer.parseInt(rs.getString(1));
				test = test + 1;
				employeeParams.setEmpId(Integer.toString(test));
			}
			if (!(test == 0)) {
				test = test + 10;
				employeeParams.setUserId(Integer.toString(test));
			}

			if (!(name.getText().toString()).isEmpty() && !(userName.getText().toString()).isEmpty() && !(password.getText().toString()).isEmpty()
					&& !(phoneNumber.getText().toString()).isEmpty() && !(email.getText().toString()).isEmpty() && !(address.getText().toString()).isEmpty()) {
				add = true;
				employeeParams.setRole("customer");
				employeeParams.setName(name.getText());
				employeeParams.setUserName(userName.getText());
				employeeParams.setPassword(password.getText());
				if (((phoneNumber.getText()).length() == 11)) {
					employeeParams.setPhoneNumber((int) Long.parseLong(phoneNumber.getText()));
				} else {
					add = false;
					custErr.setText("Enter valid Contact No.");
				}
				if (email.getText().contains("@") && email.getText().contains(".")) {
					employeeParams.setEmail(email.getText());

				} else {
					custErr.setText("Enter Valid Employee Email ID");
					add = false;
				}
				employeeParams.setAddress(address.getText());

			} else {
				custErr.setText("Enter info. in all fields");
				add = false;
			}

			if (add == true) {
				add = customer.addNewCustomer(employeeParams);
			}
			if (add == true) {
				custErr.setText("Customer Registered");
				name.setText("");
				userName.setText("");
				password.setText("");
				phoneNumber.setText("");
				email.setText("");
				address.setText("");

			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void custBackOnAction() {
		custRegPane.setVisible(false);
		loginGridPane1.setVisible(false);
		buttonsGridPane2.setVisible(false);
		loginDetailsGridPane.setVisible(true);
		signUpButton.setVisible(true);
	}


	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

}
