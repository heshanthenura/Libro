package com.heshanthenura.libro.controllers;

import com.heshanthenura.libro.MainApplication;
import com.heshanthenura.libro.User;
import com.heshanthenura.libro.services.Authentication;
import com.heshanthenura.libro.services.database.DatabaseServices;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private Button loginBtn;

    @FXML
    private TextField pwdInp;

    @FXML
    private TextField usrInp;

    @FXML
    private Text errorMsg;

    private DatabaseServices databaseServices;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        databaseServices = new DatabaseServices(); // Initialize your database services here
        errorMsg.setVisible(false); // Hide the error message on initialization
    }

    @FXML
    void loginFunc(MouseEvent event) {
        boolean valid = true;

        if (usrInp.getText().isEmpty()) {
            usrInp.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            valid = false;
        } else {
            usrInp.setStyle(null);
        }

        if (pwdInp.getText().isEmpty()) {
            pwdInp.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            valid = false;
        } else {
            pwdInp.setStyle(null);
        }

        if (valid) {
            User user = databaseServices.getUserByUsername(usrInp.getText());
            if (user == null) {
                // Handle user not found case
                usrInp.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                errorMsg.setText("User not found");
                errorMsg.setStyle("-fx-fill: red;");
                errorMsg.setVisible(true); // Show the error message
            } else {
                if (Authentication.validateUser(user, pwdInp.getText())) {
                    // Handle successful authentication
                    System.out.println("Login successful");
                    errorMsg.setText("Login successful");
                    errorMsg.setStyle("-fx-fill: green;");
                    errorMsg.setVisible(true); // Show the success message

                    // Delay closing the current window and opening the main window
                    PauseTransition delay = new PauseTransition(Duration.seconds(0.5));
                    delay.setOnFinished(e -> {
                        // Close the current window
                        Stage stage = (Stage) loginBtn.getScene().getWindow();
                        stage.close();

                        // Load and display the main.fxml file
                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main.fxml"));
                            Parent root = fxmlLoader.load();
                            Stage mainStage = new Stage();
                            mainStage.setScene(new Scene(root));
                            mainStage.show();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    });
                    delay.play();

                } else {
                    // Handle failed authentication
                    pwdInp.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                    errorMsg.setText("Invalid username or password");
                    errorMsg.setStyle("-fx-fill: red;");
                    errorMsg.setVisible(true); // Show the error message
                }
            }
        }
    }


    @FXML
    void openDBSettings(MouseEvent event) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("dbsettings.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setMaximized(true);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
