package com.taxi.javataxi;

import com.taxi.javataxi.Classes.*;
import com.taxi.javataxi.Classes.Driver;
import com.taxi.javataxi.Controllers.AddClient;
import javafx.application.Application;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Properties;

public class MainTaxi extends Application {
    private static Stage primaryStage;
    static String strDatabasePath = "D:/My papka/Java_Taxi/TAXI.fdb";

    public static Stage getPrimaryStage() {
        return primaryStage;
    }
    private static Connection connection;
    private static final String DB_URL = "jdbc:firebirdsql://localhost:3050/"+strDatabasePath;
    private static final String LOGIN = "SYSDBA";
    private static final String PASSWORD = "masterkey";

    public static Connection getConnection() {
        return connection;
    }

    @Override
    public void stop() throws Exception {
        connection.close();
        super.stop();
    }

    @Override
    public void start(Stage stage) throws IOException {

        this.primaryStage = primaryStage;
        try{
            Properties props = new Properties();
            props.setProperty("user", "SYSDBA");
            props.setProperty("password", "masterkey");
            props.setProperty("encoding", "win1251");

            connection = DriverManager.getConnection("jdbc:firebirdsql://localhost:3050/"+strDatabasePath, props);

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Не удалось соединиться с БД", ButtonType.OK);
            alert.showAndWait();
        }

        FXMLLoader fxmlLoader = new FXMLLoader(MainTaxi.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1100, 600);
        stage.setTitle("Такси");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }}
