package com.taxi.javataxi.Controllers;

import com.taxi.javataxi.Classes.Client;
import com.taxi.javataxi.Classes.Driver;
import com.taxi.javataxi.Classes.Orders;
import com.taxi.javataxi.MainTaxi;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Date;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

/** Класс для работы с заказами водителя. Не используется.*/
public class OrdersOfDriverController {
    @FXML
    private ComboBox cbDriver;
    @FXML
    private ComboBox cbStatus;
    @FXML
    private TextField tfPrice;
    @FXML
    private TextField tfNewAddress;
    @FXML
    private TextField tfOrdersCount;
    @FXML
    private TextField tfFinalPrice;
    @FXML
    private TableView<Orders> ordersTable;
    @FXML
    private TableColumn<Orders, String> lastNameColumn;
    @FXML
    private TableColumn<Orders, String>  firstNameColumn;
    @FXML
    private TableColumn<Orders, String>  patronymicColumn;
    @FXML
    private TableColumn<Orders, String> gosNumberColumn;
    @FXML
    private TableColumn<Orders, Integer>  clientIdColumn;
    @FXML
    private TableColumn<Orders, String> statusColumn;

    String firstName;
    String lastName;
    String patronymic;
    String isWork;
    String carNumber;
    String driverLicenseNumber;
    String isOwner;
    int driverId;
    Date dateLicense;
    int orderId = 0;
    Date dtOrd;
    String clReg ="";
    String clTown = "";
    String clStr = "";
    String clHome = "";
    String arReg = "";
    String arTown = "";
    String arStr = "";
    String arHome = "";
    String drSurname = "";
    String drName = "";
    String drPatronym = "";
    String stNumb ="";
    Integer price = 0;
    String mark ="";
    String numCl = "";
    String address;
    private ObservableList<Driver> drivers = FXCollections.observableArrayList();
    private ObservableList<Orders> orders = FXCollections.observableArrayList();

    @FXML
    public void initialize(){

        Statement statement = null;
        try {
            statement = MainTaxi.getConnection().createStatement();
        } catch (SQLException e) {
            return;
        }
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Driver");
            while (resultSet.next()) {
                driverId = resultSet.getInt("ID_DRIVER");
                lastName= resultSet.getString("D_SURNAME");
                firstName= resultSet.getString("D_NAME");
                patronymic= resultSet.getString("D_PATRONYM");
                drivers.add(new Driver(driverId, lastName, firstName, patronymic, isWork, carNumber, isOwner, driverLicenseNumber, dateLicense));
            }
            cbDriver.setItems(drivers);
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Ошибка поиска водителей.", ButtonType.OK);
            alert.showAndWait();
        }

        TextFormatter priceFormatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change ->
                Pattern.compile("\\d{5}").matcher(change.getControlNewText()).matches() ? change : null);

        tfPrice.setTextFormatter(priceFormatter);

        //Параметры таблицы Заказы
        lastNameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getDriverSurname()));
        firstNameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getDriverName()));
        patronymicColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getDriverPatronymic()));

        cbStatus.setItems(FXCollections.observableArrayList("Не выполнен", "Выполнен", "Выполняется"));

    }

    @FXML
    private void onDoneStatus(ActionEvent actionEvent) {
        if (ordersTable.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.WARNING, "Заказ не выбран", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        Statement statement = null;
        try {
            statement = MainTaxi.getConnection().createStatement();
        } catch (SQLException e) {
            return;
        }
        try {
            int info = statement.executeUpdate("UPDATE Orders SET COMPL_MARK = '" +
                    cbStatus.getValue() + "' Where ID_ORDER = " + orderId);


        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Ошибка добавления автомобиля.", ButtonType.OK);
            alert.showAndWait();
        }
    }

    @FXML
    private void onDonePrice(ActionEvent actionEvent) {
        if (ordersTable.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.WARNING, "Заказ не выбран", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        Statement statement = null;
        try {
            statement = MainTaxi.getConnection().createStatement();
        } catch (SQLException e) {
            return;
        }
        try {
            int info = statement.executeUpdate("UPDATE Orders SET Pay = '" + tfPrice.getText() + "' Where ID_ORDER = " + orderId);



        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Ошибка добавления автомобиля.", ButtonType.OK);
            alert.showAndWait();
        }
    }

    @FXML
    private void onAddNewAddress(ActionEvent actionEvent) {
        if (ordersTable.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.WARNING, "Заказ не выбран", ButtonType.OK);
            alert.showAndWait();
            return;
        }


    }

    public void getNewAdd(String address){
        this.address = address;
    }

    @FXML
    private void onDoneNewAddress(ActionEvent actionEvent) {
        if (ordersTable.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.WARNING, "Заказ не выбран", ButtonType.OK);
            alert.showAndWait();
            return;
        }


        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(MainTaxi.class.getResource("addAddress-view.fxml"));
        Parent page = null;
        try {
            page = loader.load();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Ошибка открытия формы", ButtonType.OK);
            alert.showAndWait();
        }
        Stage addStage = new Stage();
        addStage.setTitle("Добавление нового адреса");
        addStage.initModality(Modality.APPLICATION_MODAL);
        addStage.initOwner(MainTaxi.getPrimaryStage());
        assert page != null;
        Scene scene = new Scene(page);
        addStage.setScene(scene);
        AddAddress controller = loader.getController();
        controller.setAddStage(addStage);
        addStage.showAndWait();

        Statement statement = null;
        try {
            statement = MainTaxi.getConnection().createStatement();
        } catch (SQLException e) {
            return;
        }
        /*try {


            //UPDATE


        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Ошибка добавления автомобиля.", ButtonType.OK);
            alert.showAndWait();
        }*/
    }

    @FXML
    private void onSelectDriver(MouseEvent mouseEvent) {
        if(cbDriver.getValue() == null){
            return;
        }
        Statement statement = null;
        try {
            statement = MainTaxi.getConnection().createStatement();
        } catch (SQLException e) {
            return;
        }
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Orders Where ID_DRIVER = " + drivers.get(cbDriver.getSelectionModel().getSelectedIndex()).getDriverId());
            while (resultSet.next()) {
                orderId= resultSet.getInt("ID_ORDER");
                dtOrd = resultSet.getDate("DATETIME");
                clReg = resultSet.getString("CL_REGION");
                clTown = resultSet.getString("CL_TOWN");
                clStr = resultSet.getString("CL_STR");
                clHome = resultSet.getString("CL_HOME");
                arReg = resultSet.getString("ARR_REGION");
                arTown = resultSet.getString("ARR_TOWN");
                arStr = resultSet.getString("ARR_STR");
                arHome = resultSet.getString("ARR_HOME");
                drSurname = resultSet.getString("D_SURNAME");
                drName = resultSet.getString("D_NAME");
                drPatronym = resultSet.getString("D_PATRONYM");
                stNumb = resultSet.getString("CAR_NUM");
                numCl = resultSet.getString("NUM_CL");
                price = resultSet.getInt("PAY");
                mark = resultSet.getString("COMPL_MARK");

                orders.add(new Orders(orderId, dtOrd, clReg, clTown, clStr, clHome, arReg, arTown, arStr, arHome, drSurname, drName, drPatronym, stNumb, numCl, price, mark));
            }
            ordersTable.setItems(orders);
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Ошибка добавления автомобиля.", ButtonType.OK);
            alert.showAndWait();
        }


    }
}
