package com.heshanthenura.libro.controllers;

import com.heshanthenura.libro.services.database.Database;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DBSettingController implements Initializable {

    @FXML
    private TextField dbNameInp;

    @FXML
    private TextField hostInp;

    @FXML
    private TextField portInp;

    @FXML
    private PasswordField pwdInp;

    @FXML
    private Label testUrlLbl;

    @FXML
    private TextField usrInp;

    @FXML
    private Text dbMsg;

    @FXML
    void connectDb(MouseEvent event) {
        String host = hostInp.getText();
        String port = portInp.getText();
        String dbName = dbNameInp.getText();
        String username = usrInp.getText();
        String password = pwdInp.getText();

        Database.setHOST(host);
        Database.setPORT(port);
        Database.setDBNAME(dbName);
        Database.setUSERNAME(username);
        Database.setPASSWORD(password);

        String url = "jdbc:mysql://" + host + ":" + port + "/" + dbName;
        testUrlLbl.setText(url);

        String resultMessage = Database.testConnection();
        dbMsg.setText(resultMessage);

        if (resultMessage.equals("Connection was successful")) {
            dbMsg.setStyle("-fx-fill: green;");
            try {
                Database.saveConfiguration();
                dbMsg.setText("Save successfully");
            } catch (IOException e) {
                dbMsg.setText("Error saving configuration: " + e.getMessage());
                dbMsg.setStyle("-fx-fill: red;");
            }
        } else {
            dbMsg.setStyle("-fx-fill: red;");
        }
    }

    @FXML
    void testDB(MouseEvent event) {
        String host = hostInp.getText();
        String port = portInp.getText();
        String dbName = dbNameInp.getText();
        String username = usrInp.getText();
        String password = pwdInp.getText();

        Database.setHOST(host);
        Database.setPORT(port);
        Database.setDBNAME(dbName);
        Database.setUSERNAME(username);
        Database.setPASSWORD(password);

        String url = "jdbc:mysql://" + host + ":" + port + "/" + dbName;
        testUrlLbl.setText(url);

        String resultMessage = Database.testConnection();
        dbMsg.setText(resultMessage);

        if (resultMessage.equals("Connection was successful")) {
            dbMsg.setStyle("-fx-fill: green;");
        } else {
            dbMsg.setStyle("-fx-fill: red;");
        }
    }

    private void populateFieldsFromConfiguration() {
        dbNameInp.setText(Database.getDBNAME());
        hostInp.setText(Database.getHOST());
        portInp.setText(Database.getPORT());
        usrInp.setText(Database.getUSERNAME());
        pwdInp.setText(Database.getPASSWORD());
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Database.loadConfiguration();
            populateFieldsFromConfiguration();
        } catch (IOException e) {

        }
    }
}
