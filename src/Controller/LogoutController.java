package controller;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class LogoutController {

    private Stage dialogStage, loginStage;

    public void loginButtonOnAction() {
        try {
            dialogStage.fireEvent(new WindowEvent(dialogStage, WindowEvent.WINDOW_CLOSE_REQUEST));

            Main main = new Main();
            loginStage = new Stage();

            main.start(loginStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
}
