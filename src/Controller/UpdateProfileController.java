package controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.User;

public class UpdateProfileController implements Initializable {

    @FXML
    private Pane updatePane, individualUpdatePane;
    @FXML
    private Label currentValue, updateMsgLabel, errorLabel;
    @FXML
    private String userId;
    @FXML
    private TextField valueTextField;
    private Properties prop = new Properties();
    private InputStream input = null;
    private User getUsername;
    private User updateUser;
    private String columnName;

    public void setDialogStage(Stage updateStage) {

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        updatePane.setVisible(true);
        individualUpdatePane.setVisible(false);
        setUserId();

    }

    private void setUserId() {
        try {

            input = new FileInputStream("config.properties");

            prop.load(input);

            userId = prop.getProperty("userEMPId");

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

    }

    @FXML
    void submitUpdateOnAction() {
        errorLabel.setText("");
        Boolean bValue;

        if (columnName.equals("Address") && !valueTextField.getText().isEmpty()) {
            bValue = false;
            bValue = updateUser.updateAdress(userId, valueTextField.getText());

            if (bValue = true) {
                errorLabel.setText(columnName + " updated successfully");
            } else {
                errorLabel.setText(columnName + " failed to update");

            }

        } else if (columnName.equals("Email ID") && !valueTextField.getText().isEmpty()) {
            bValue = false;
            if (valueTextField.getText().contains("@") && valueTextField.getText().contains(".")) {
                bValue = updateUser.updateEmailID(userId, valueTextField.getText());

                if (bValue = true) {
                    errorLabel.setText(columnName + " updated successfully");
                } else {
                    errorLabel.setText(columnName + " failed to update");

                }
            } else {
                errorLabel.setText("Enter valid Email ID ");
            }
        } else if (columnName.equals("Password") && !valueTextField.getText().isEmpty()) {
            bValue = false;
            bValue = updateUser.updatePassword(userId, valueTextField.getText());

            if (bValue = true) {
                errorLabel.setText(columnName + " updated successfully");
            } else {
                errorLabel.setText(columnName + " failed to update");

            }
        } else if (columnName.equals("Login ID") && !valueTextField.getText().isEmpty()) {
            bValue = false;
            bValue = updateUser.updateLoginId(userId, valueTextField.getText());

            if (bValue = true) {
                errorLabel.setText(columnName + " updated successfully");
            } else {
                errorLabel.setText(columnName + " failed to update");

            }
        } else {
            errorLabel.setText("Enter " + columnName + " to be updated");
        }
        valueTextField.clear();
    }

    private void updateValue(String value) {

        try {
            clearLabels();
            getUsername = new User();
            updateUser = new User();
            ResultSet rs = getUsername.getUserDetails(userId);
            if (rs.next()) {
                if (value.equals("Address")) {
                    currentValue.setText("Your present " + value + " : " + rs.getString(7));
                    updateMsgLabel.setText("Enter new " + value + " below:");

                } else if (value.equals("Contact No")) {
                    currentValue.setText("Your present " + value + " : " + rs.getString(4));
                    updateMsgLabel.setText("Enter new " + value + " below:");
                } else if (value.equals("Email ID")) {
                    currentValue.setText("Your present " + value + " : " + rs.getString(6));
                    updateMsgLabel.setText("Enter new " + value + " below:");

                }
            }
            ResultSet res = getUsername.getUsersloginDetails(userId);
            if (res.next()) {
                if (value.equals("Login ID")) {
                    currentValue.setText("Your present " + value + " : " + res.getString(3));
                    updateMsgLabel.setText("Enter new " + value + " below:");
                } else if (value.equals("Password")) {
                    currentValue.setText("Your present " + value + " : " + res.getString(4));
                    updateMsgLabel.setText("Enter new " + value + " below:");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void updateAddressOnAction() {
        clearLabels();
        updatePane.setVisible(false);
        individualUpdatePane.setVisible(true);
        updateValue("Address");
        columnName = "Address";
    }

    private void clearLabels() {
        valueTextField.clear();
        currentValue.setText("");
        updateMsgLabel.setText("");
        errorLabel.setText("");
    }

    @FXML
    void updateSSNOnAction() {
        clearLabels();
        updatePane.setVisible(false);
        individualUpdatePane.setVisible(true);
        updateValue("SSN");
        columnName = "SSN";

    }

    @FXML
    void updateContactNoOnAction() {
        clearLabels();
        updatePane.setVisible(false);
        individualUpdatePane.setVisible(true);
        updateValue("Contact No");
        columnName = "Contact No";

    }

    @FXML
    void updateEmailOnAction() {
        clearLabels();
        updatePane.setVisible(false);
        individualUpdatePane.setVisible(true);
        updateValue("Email ID");
        columnName = "Email ID";

    }

    @FXML
    void updateLoginIdOnAction() {
        clearLabels();
        updatePane.setVisible(false);
        individualUpdatePane.setVisible(true);
        updateValue("Login ID");
        columnName = "Login ID";

    }

    @FXML
    void updatePasswordOnAction() {
        clearLabels();
        updatePane.setVisible(false);
        individualUpdatePane.setVisible(true);
        updateValue("Password");
        columnName = "Password";

    }

    @FXML
    void backOnAction() {
        clearLabels();
        updatePane.setVisible(true);
        individualUpdatePane.setVisible(false);
    }
}
