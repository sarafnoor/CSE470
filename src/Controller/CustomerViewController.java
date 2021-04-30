
package controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Admin;
import model.Customer;
import model.User;
import model.Order;
import model.Menu;
import model.OrderParams;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;

import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

public class CustomerViewController implements Initializable {
	@FXML
	private Pane orderViewPane, customerViewPane, confrimationPane;
	@FXML
	private Label welcomeLabel, orderCartLabel, totalLabel, errorLabel, checkTotalError, cartItemsLabel,
			totalItemsFinalLabel, nameLabel, addressLabel, giftCard;
	@FXML
	private HBox menuListHBox;
	@FXML
	private Button editProfileButton, logoutButton, addToCartButton, checkTotalButton, placeOrderButton;
	@FXML
	private TextField itemIdTextFeild;
	private Stage dialogStage, primaryStage, updateStage;
	private Customer customerView;
	private ObservableList<ObservableList> data;
	@FXML
	private TableView tableView;
	private Properties prop = new Properties();
	private InputStream input = null;
	private String user;
	private String address, empId;
	private Menu menuItemDetails;
	private Order insertOrder;
	private HashMap<String, String> hmap;
	private ArrayList menuitemsPrice;
	private ArrayList menuitemsNames;
	private double total = 0;


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		customerViewPane.setVisible(true);
		orderViewPane.setVisible(false);
		confrimationPane.setVisible(false);

		loadItemsData();
		hmap = new HashMap<String, String>();
		menuitemsPrice = new ArrayList<>();
		menuitemsNames = new ArrayList<>();
	}

	public CustomerViewController() {
	}

	@FXML
	void editProfileButtonOnAction() {
		updateStage = new Stage();

		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/updatePofile.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			updateStage.setTitle("Online Coffee Shop Management System");
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

	@FXML
	void deleteProfileButtonOnAction() {

		Admin adminEmployeeOperations = new Admin();
		adminEmployeeOperations.deleteParentOrders(Integer.parseInt(empId));
		adminEmployeeOperations.deleteParent(Integer.parseInt(empId));
		adminEmployeeOperations.deleteEmployee(Integer.parseInt(empId));

		try {
			primaryStage = new Stage();
			dialogStage.fireEvent(new WindowEvent(dialogStage, WindowEvent.WINDOW_CLOSE_REQUEST));

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/deleteCustomer.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			primaryStage.setTitle("Saraf Coffee Shop Management System");
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			DeleteCustomerProfile controller = loader.getController();
			controller.setDialogStage(primaryStage);
			primaryStage.show();
		} catch (Exception e) {
			System.out.println("Error occured while inflating view: " + e);
		}
	}

	@FXML
	void addToCartButtonOnAction() {
		try {
			errorLabel.setText("");
			menuItemDetails = new Menu();
			ResultSet rs = menuItemDetails.getMenuItemDetails(itemIdTextFeild.getText().toString());
			if (rs.next()) {

				hmap.put(rs.getString(3), rs.getString(4));
				System.out.println();
				menuitemsPrice.add(rs.getString(4));
				menuitemsNames.add(rs.getString(3));

				itemIdTextFeild.setText("");

			} else {
				errorLabel.setText("Enter valid Menu Item ID");
			}
			orderCartLabel.setText(menuitemsNames.toString());

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@FXML
	void clearCartButtonOnAction() {
		clearOrder();

	}

	private void clearOrder() {
		hmap.clear();
		menuitemsPrice.clear();
		menuitemsNames.clear();
		orderCartLabel.setText("");

		totalLabel.setText("");
		checkTotalError.setText("");
		total = 0;
	}

	@FXML
	void checkTotalOnAction() {
		totalLabel.setText("");
		checkTotalError.setText("");
		if (!menuitemsPrice.isEmpty()) {

			Iterator<String> itr = menuitemsPrice.iterator();
			while (itr.hasNext()) {
				double element = Double.parseDouble(itr.next());
				total = total + element;
			}

			totalLabel.setText("$" + total);
		} else {
			checkTotalError.setText("Enter Menu Item to Cart");
		}

	}

	@FXML
	void placeOrderOnAction() {
		if(total == 0){
		Iterator<String> itr = menuitemsPrice.iterator();
		while (itr.hasNext()) {
			double element = Double.parseDouble(itr.next());
			total = total + element;
		}}
		confrimationPane.setVisible(false);
		checkTotalError.setText("");

		if (!menuitemsPrice.isEmpty()) {
			customerViewPane.setVisible(false);
			orderViewPane.setVisible(true);

			cartItemsLabel.setText(menuitemsNames.toString());
			totalItemsFinalLabel.setText("$" + total);

			nameLabel.setText(user);
			addressLabel.setText(address);

		} else {
			checkTotalError.setText("Enter Menu Item to Cart");

		}
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;

	}

	public void loadItemsData() {
		try {

			input = new FileInputStream("config.properties");

			prop.load(input);

			System.out.println(prop.getProperty("user"));
			user = prop.getProperty("user");
			address = prop.getProperty("address");
			empId = prop.getProperty("userEMPId");
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		welcomeLabel.setText("Welcome " + user + ".");
		customerView = new Customer();
		ResultSet rs = customerView.getMenuItem();
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

				tableView.getColumns().addAll(col);
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

			tableView.setItems(data);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void cancelOrderOnAction() {
		customerViewPane.setVisible(true);
		orderViewPane.setVisible(false);
		confrimationPane.setVisible(false);
		clearOrder();
	}

	@FXML
	void confirmOrderOnAction() {

		if (checkOrderAmount()) {
			giftCard.setVisible(true);

		}
		confrimationPane.setVisible(true);
		orderViewPane.setVisible(false);
		customerViewPane.setVisible(false);
		User getUserIdDao = new User();
		insertOrder = new Order();
		OrderParams orderParams = new OrderParams();
		orderParams.setUserid(getUserIdDao.getMenuItem(empId));
		orderParams.setOrderPrice(total);
		orderParams.setOrderItems(menuitemsNames.toString());

		insertOrder.insertsOrder(orderParams);

	}

	@FXML
	void placeNewOrderOnAction() {
		customerViewPane.setVisible(true);
		orderViewPane.setVisible(false);
		confrimationPane.setVisible(false);

		total = 0;
		clearOrder();
	}

	public Boolean checkOrderAmount() {
		boolean check = false;
		if (total > 50) {
			check = true;
		}
		return check;
	}

}
