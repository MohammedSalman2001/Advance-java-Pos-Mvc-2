package com.davstack.pos.controller;

import com.davstack.pos.db.DBConnection;
import com.davstack.pos.util.PasswordManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignUpFormController {
    public AnchorPane context;
    public TextField txtEmail;
    public TextField txtPassword;

    public void btnRegisterNowOnAction(ActionEvent actionEvent) {

        try {
            Connection connection = DBConnection.getDbConnection().getConnection();
            String sql="INSERT  INTO user VALUES (?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,txtEmail.getText());
            preparedStatement.setString(2, PasswordManager.enCryptPassword(txtPassword.getText()));
            boolean isSaved = preparedStatement.executeUpdate() > 0;
            if(isSaved){
                new Alert(Alert.AlertType.INFORMATION,"User Saved !").show();
                clerarFlied();
            }else {
                new Alert(Alert.AlertType.WARNING,"Try Again").show();
            }

        } catch (ClassNotFoundException | SQLException e) {
           e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }

    private void clerarFlied() {
        txtPassword.clear();
        txtEmail.clear();
    }

    public void btnAlreadyHaveAnAction(ActionEvent actionEvent) throws IOException {
        setUi("LoginForm");
    }

    public void setUi(String url) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+url+".fxml"))));
        stage.show();
        stage.centerOnScreen();
    }
}
