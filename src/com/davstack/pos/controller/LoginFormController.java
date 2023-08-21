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
import javafx.stage.Window;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFormController {
    public AnchorPane context;
    public TextField txtEmail;
    public TextField txtPassword;

    public void btnLoginOnAction(ActionEvent actionEvent) {
        System.out.println("salman");

        //login

        try {
            Connection connection = DBConnection.getDbConnection().getConnection();
            String sql="SELECT * FROM user";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                if(PasswordManager.checkPssword(txtPassword.getText(),resultSet.getString(2))){
                    System.out.println("Completed login");
                }else {
                    new Alert(Alert.AlertType.WARNING,"Password Wrong !").show();
                }

            }else {
                new Alert(Alert.AlertType.WARNING,"Customer Not Found!").show();
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public void btnCreateNewAnAccountOnAction(ActionEvent actionEvent) throws IOException {
        setUi("SignUpForm");


    }

    public void setUi(String url) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+url+".fxml"))));
        stage.show();
        stage.centerOnScreen();
    }
}
