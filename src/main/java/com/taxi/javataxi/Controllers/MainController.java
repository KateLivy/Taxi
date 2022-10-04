package com.taxi.javataxi.Controllers;

import com.taxi.javataxi.Classes.*;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/** Основной класс главной формы*/
public class MainController {@FXML
public TableColumn<Orders, String> dTOrdColumn;
    @FXML
    public TableColumn<Orders, String> clRegOColumn;
    @FXML
    public TableColumn<Orders, String> clTownOColumn;
    @FXML
    public TableColumn<Orders, String> clStrOColumn;
    @FXML
    public TableColumn<Orders, String> clHomeOColumn;
    @FXML
    public TableColumn<Orders, String> arRegOColumn;
    @FXML
    public TableColumn<Orders, String> arTownOColumn;
    @FXML
    public TableColumn<Orders, String> arStrOColumn;
    @FXML
    public TableColumn<Orders, String> arHomeOColumn;
    @FXML
    public TableColumn<Orders, String> dSurnameOColumn;
    @FXML
    public TableColumn<Orders, String> dNameOColumn;
    @FXML
    public TableColumn<Orders, String> dPatronymOColumn;
    @FXML
    public TableColumn<Orders, String> carNumOColumn;
    @FXML
    public TableColumn<Orders, String> numClOColumn;
    @FXML
    public TableColumn<Orders, Integer> priceOColumn;
    @FXML
    public TableColumn<Orders, String> complMarkColumn;
    @FXML
    public TableView<Orders> livingsTable;
    public Tab tabLivings;
    public TabPane tabPane;
    @FXML
    public ComboBox<String> isDone;
    public Tab tabBookings;
    @FXML
    public TableView<Booking> bookingsTable;
    @FXML
    public TableColumn<Booking, String> dtBookColumn;
    @FXML
    public TableColumn<Booking, String> clRegBColumn;
    @FXML
    public TableColumn<Booking, String> clTownBColumn;
    @FXML
    public TableColumn<Booking, String> clStrBColumn;
    @FXML
    public TableColumn<Booking, String> clHomeBColumn;
    @FXML
    public TableColumn<Booking, String> arRegBColumn;
    @FXML
    public TableColumn<Booking, String> arTownBColumn;
    @FXML
    public TableColumn<Booking, String> arStrBColumn;
    @FXML
    public TableColumn<Booking, String> arHomeBColumn;
    @FXML
    public TableColumn<Booking, String> seatsColumn;
    @FXML
    public TableColumn<Booking, Integer> priceBColumn;
    @FXML
    public TableColumn<Booking, Integer> numClBColumn;
    public Tab tabClients;
    @FXML
    public TableView<Client> clientsTable;
    @FXML
    public TableColumn<Client, String> clientsColumn;
    public Tab tabApartments;
    @FXML
    public TableView<Car> apartmentsTable;
    @FXML
    public TableColumn<Car, String> owner;
    @FXML
    public TableColumn<Car, String> ncrv;
    @FXML
    public TableColumn<Car, String> stateCar;
    @FXML
    public TableColumn<Car, Integer> yearCar;
    @FXML
    public TableColumn<Car, String> color;
    @FXML
    public TableColumn<Car, String> brand;
    @FXML
    public TableColumn<Car, String> stNum;
    @FXML
    public TableColumn<Car, Integer> numOfSeats;
    @FXML
    public TableView<Driver> apartmentsTable1;
    @FXML
    public TableColumn<Driver, String> dLastName;
    @FXML
    public TableColumn<Driver, String> dName;
    @FXML
    public TableColumn<Driver, String> dPatronym;
    @FXML
    public TableColumn<Driver, String> isWork;
    @FXML
    public TableColumn<Driver, String> stNumDriv;
    @FXML
    public TableColumn<Driver, String> isOwner;
    @FXML
    public TableColumn<Driver, String> numDriver;
    @FXML
    public TableColumn<Driver, String> dtDriver;
    @FXML
    public TableColumn<Client,Integer> clientIdCol;
    @FXML
    public TableColumn<Orders, Integer> orderIdCol;
    @FXML
    public TableColumn<Driver, Integer> driversId;
    @FXML
    public ComboBox statesOfCar;
    @FXML
    public ComboBox statesDriv;
    @FXML
    public ComboBox ownDriv;
    @FXML
    public Label sumOfOrders;
    @FXML
    public ComboBox cbStNumDriv;

    int orderId = 0;
    int bookingId = 0;
    int clientId = 0;
    int carId = 0;
    int driverId = 0;
    Date dtOrd;
    Date dtBook;
    Date dateLicense;
    Blob blobPhoto;


    private ObservableList<Orders> orders = FXCollections.observableArrayList();
    private ObservableList<Booking> bookings = FXCollections.observableArrayList();
    private ObservableList<Client> clients = FXCollections.observableArrayList();
    private ObservableList<Car> cars = FXCollections.observableArrayList();
    private ObservableList<Driver> drivers = FXCollections.observableArrayList();

    private static Stage stage;
    public static Stage getStage() {
        return stage;
    }


    @FXML
    void initialize() {
        //Параметры таблицы Клиенты
        clientsColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getClientNumber()));
        clientsTable.getSortOrder().add(clientsColumn);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        //Параметры таблицы Заказы

        dTOrdColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getOrderDateTime().format(dtf)));
        clRegOColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getClRegion()));
        clTownOColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getClTown()));
        clStrOColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getClStreet()));
        clHomeOColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getClHome()));
        arRegOColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getAdrRegion()));
        arTownOColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getAdrTown()));
        arStrOColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getAdrStreet()));
        arHomeOColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getAdrHome()));
        dSurnameOColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getDriverSurname()));
        dNameOColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getDriverName()));
        dPatronymOColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getDriverPatronymic()));
        carNumOColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getCarNumber()));
        numClOColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getClientNumber()));
        priceOColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getPrice()));
        complMarkColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getCompleteMark()));
        livingsTable.getSortOrder().add(dTOrdColumn);
/*
        //Параметры таблицы Бронирования
        dtBookColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getBookDateTime().format(dtf)));
        clRegBColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getClRegion()));
        clTownBColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getClTown()));
        clStrBColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getClStreet()));
        clHomeBColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getClHome()));
        arRegBColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getAdrRegion()));
        arTownBColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getAdrTown()));
        arStrBColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getAdrStreet()));
        arHomeBColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getAdrHome()));
        seatsColumn.setCellValueFactory(param -> new SimpleObjectProperty(param.getValue().getNumOfSeats()));
        priceBColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getPrice()));
        numClBColumn.setCellValueFactory(param -> new SimpleObjectProperty(param.getValue().getClientNumber()));
        bookingsTable.getSortOrder().add(dtBookColumn);*/

        //Параметры таблицы Водители
        dLastName.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getDriverSurname()));
        dName.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getDriverName()));
        dPatronym.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getDriverPatronymic()));
        isWork.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getIsWork()));
        stNumDriv.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getCarNumber()));
        isOwner.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().isOwner()));
        numDriver.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getDriverLicenseNum()));
        dtDriver.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getDateLicense().format(dtf)));
        apartmentsTable1.getSortOrder().add(dLastName);

        //Параметры таблицы Авто
        owner.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getOwner()));
        ncrv.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getRegistrTSNumber()));
        stateCar.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getCarCondition()));
        yearCar.setCellValueFactory(param -> new SimpleObjectProperty(param.getValue().getYearBornCar()));
        color.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getColor()));
        brand.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getBrand()));
        stNum.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getGosNumber()));
        numOfSeats.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getNumOfSeats()));
        apartmentsTable.getSortOrder().add(owner);
        //заполняем комбобоксы

        ObservableList<String> stst = FXCollections.observableArrayList("Рабочее", "На линии", "В отпуске", "На больничном", "Уволен");
        statesDriv.setItems(stst);

        stst = FXCollections.observableArrayList("Выполнен", "Выполняется", "Не выполнен");
        isDone.setItems(stst);

        stst = FXCollections.observableArrayList("Ремонт", "Рабочее", "Списан");
        statesOfCar.setItems(stst);

        stst = FXCollections.observableArrayList("Является собственником", "Не является собственником");
        ownDriv.setItems(stst);

        Statement statement = null; try {
            statement = MainTaxi.getConnection().createStatement();
        } catch (SQLException e) {
            return;
        }
        try {
            ResultSet resultSet = statement.executeQuery("select ST_NUM from CAR where ST_NUM not in (select ST_NUM from driver where is_work = 'На линии') and CAR_CONDITION = 'Рабочее'");
            ObservableList<String> mb = FXCollections.observableArrayList();
            while (resultSet.next()) {
                mb.add(mb.size(), resultSet.getString("ST_NUM"));
            }
            cbStNumDriv.setItems(mb);
        }
        catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Не удалось вывести данные из базы данных", ButtonType.OK);
            alert.showAndWait();
        }
        update();
    }
    String numCl = "";
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
    Integer seats = 0;
    String own ="";
    String numTS ="";
    String condition = "";
    String year = "";
    String coloring = "";
    String brand1 = "";
    byte[] photo;
    String is_work = "";
    String is_owner = "";
    String dlNum = "";
    Date dateLicen;

    /** Метод update обновляет данные во всех таблицах*/
    public void update(){
        drivers.clear();
        bookings.clear();
        clients.clear();
        orders.clear();
        cars.clear();
        apartmentsTable.getItems().clear();
        apartmentsTable1.getItems().clear();
        //bookingsTable.getItems().clear();
        clientsTable.getItems().clear();
        livingsTable.getItems().clear();

        Statement statement = null;
        Statement statement1 = null;
        try {
            statement = MainTaxi.getConnection().createStatement();
            statement1 = MainTaxi.getConnection().createStatement();
        } catch (SQLException e) {
            return;
        }
        try {
            //Получаем список Клиентов
            ResultSet resultSet = statement.executeQuery("SELECT * FROM CLIENT");
            while (resultSet.next()) {
                clientId = resultSet.getInt("ID_CLIENT");
                numCl = resultSet.getString("NUM_CL");

                clients.add(new Client(clientId, numCl));
            }
            clientsTable.setItems(clients);

            //Получаем список orders
            ResultSet result;
            resultSet = statement.executeQuery("SELECT * FROM ORDERS");
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
            livingsTable.setItems(orders);
/*
            //Получаем список Броней
            resultSet = statement.executeQuery("SELECT * FROM BOOKING");
            while (resultSet.next()) {
                bookingId = resultSet.getInt("ID_B");
                dtBook = resultSet.getDate("DATETIME");
                clReg = resultSet.getString("CL_REG");
                clTown = resultSet.getString("CL_TOWN");
                clStr = resultSet.getString("CL_STR");
                clHome = resultSet.getString("CL_HOME");
                arReg = resultSet.getString("ARR_REG");
                arTown = resultSet.getString("ARR_TOWN");
                arStr = resultSet.getString("ARR_STR");
                arHome = resultSet.getString("ARR_HOME");
                seats = resultSet.getInt("NUM_OF_SEATS");
                price = resultSet.getInt("PAY");
                numCl = resultSet.getString("CL_NUM");

                bookings.add(new Booking(bookingId, dtBook, clReg, clTown, clStr, clHome, arReg, arTown, arStr,arHome, numCl, price, seats));
            }
            bookingsTable.setItems(bookings);*/

            //Получаем список Cars
            resultSet = statement.executeQuery("SELECT * FROM CAR");
            while (resultSet.next()) {
                carId = resultSet.getInt("ID_CAR");
                own = resultSet.getString("OWNER");
                numTS = resultSet.getString("NCRV");
                condition = resultSet.getString("CAR_CONDITION");
                year = resultSet.getString("YEAR_");
                coloring = resultSet.getString("COLOR_");
                brand1 = resultSet.getString("BRAND");
                stNumb = resultSet.getString("ST_NUM");
                seats = resultSet.getInt("NUM_OF_SEATS");
                blobPhoto = resultSet.getBlob("Photo");
                cars.add(new Car(carId, own, numTS, condition, year, coloring, brand1, stNumb, seats, blobPhoto));
            }
            apartmentsTable.setItems(cars);

            //Получаем список driver
            resultSet = statement.executeQuery("SELECT * FROM DRIVER");
            while (resultSet.next()) {
                driverId = resultSet.getInt("ID_DRIVER");
                drSurname = resultSet.getString("D_SURNAME");
                drName = resultSet.getString("D_NAME");
                drPatronym = resultSet.getString("D_PATRONYM");
                is_work = resultSet.getString("IS_WORK");
                stNumb = resultSet.getString("ST_NUM");
                is_owner = resultSet.getString("IS_OWNER");
                dlNum = resultSet.getString("DL_NUM");
                dateLicen = resultSet.getDate("DATE_LICENSE");
                drivers.add(new Driver(driverId, drSurname, drName, drPatronym, is_work, stNumb, is_owner, dlNum, dateLicen));
            }
            apartmentsTable1.setItems(drivers);

        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Не удалось вывести данные из базы данных", ButtonType.OK);
            alert.showAndWait();
        }
    }
/** Метод onCreateOrder открывает окно добавления заказа*/
    public void onCreateOrder(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(MainTaxi.class.getResource("addOrder-view.fxml"));
        Parent page = null;
        try {
            page = loader.load();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Невозможно открыть форму.", ButtonType.OK);
            alert.showAndWait();
        }
        Stage addStage = new Stage();
        addStage.setTitle("Добавление нового заказа");
        addStage.initModality(Modality.APPLICATION_MODAL);
        addStage.initOwner(MainTaxi.getPrimaryStage());
        assert page != null;
        Scene scene = new Scene(page);
        addStage.setScene(scene);
        AddOrderController controller = loader.getController();
        controller.setAddStage(addStage);
        addStage.showAndWait();

        update();
    }
/** Метод onUpdate обновляет данные в таблицах, вызывается нажатием на кнопку*/
    public void onUpdate(ActionEvent actionEvent) {
        update();
    }
/** Метод onNowOrders отображает действующие заказы*/
    public void onNowOrders(ActionEvent actionEvent) {
        orders.clear();
        livingsTable.getItems().clear();
        boolean flag = false;
        Statement statement = null;
        try {
            statement = MainTaxi.getConnection().createStatement();
        } catch (SQLException e) {
            return;
        }
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM ORDERS WHERE COMPL_MARK = 'Выполняется'");
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
                flag = true;
            }
            if (flag){
                livingsTable.setItems(orders);
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Отсутствуют выполняющиеся заказы.", ButtonType.OK);
                alert.showAndWait();
                update();
            }
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Не удалось вывести данные из базы данных", ButtonType.OK);
            alert.showAndWait();
        }



    }
/** Метод onEditOrder меняет состояние заказа и кол-во свободных мест в автомобиле, если заказ выполнен (на 4) или выполняется (на 0).*/
    public void onEditOrder(ActionEvent actionEvent) {
        if ( livingsTable.getSelectionModel().getSelectedItem() != null){
            orderId = livingsTable.getSelectionModel().getSelectedItem().getOrderId();
            Statement statement = null;
            try {
                statement = MainTaxi.getConnection().createStatement();
            } catch (SQLException e) {
                return;
            }
            try {
                int info = statement.executeUpdate("UPDATE ORDERS SET COMPL_MARK = '" + isDone.getValue() + "' WHERE ID_ORDER = " + orderId);
                if (isDone.getValue() == "Выполняется"){
                    //машина занята
                    String carNum = livingsTable.getSelectionModel().getSelectedItem().getCarNumber();
                    info = statement.executeUpdate("UPDATE CAR SET NUM_OF_SEATS = 0 WHERE ST_NUM = '"+ carNum +"'");
                }
                else {
                    //машина свободна
                    String carNum = livingsTable.getSelectionModel().getSelectedItem().getCarNumber();
                    info = statement.executeUpdate("UPDATE CAR SET NUM_OF_SEATS = 4 WHERE ST_NUM = '"+ carNum+"'");
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Данные успешно заменены.", ButtonType.OK);
                alert.showAndWait();

            } catch (SQLException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Ошибка.", ButtonType.OK);
                alert.showAndWait();
            }
            update();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING, "Выберите запись.", ButtonType.OK);
            alert.showAndWait();
        }
    }

    /** Метод onCreateBook открывает окно добавления бронирования*//*
    public void onCreateBook(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(MainTaxi.class.getResource("addBooking-view.fxml"));
        Parent page = null;
        try {
            page = loader.load();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Ошибка открытия формы", ButtonType.OK);
            alert.showAndWait();
        }
        Stage addStage = new Stage();
        addStage.setTitle("Добавление нового бронирования");
        addStage.initModality(Modality.APPLICATION_MODAL);
        addStage.initOwner(MainTaxi.getPrimaryStage());
        assert page != null;
        Scene scene = new Scene(page);
        addStage.setScene(scene);
        AddBookingController controller = loader.getController();
        controller.setAddStage(addStage);
        addStage.showAndWait();

        update();
    }*/

/*    public void onUpgradeToOrder(ActionEvent actionEvent) {
        if (bookingsTable.getSelectionModel().getSelectedItem() != null){
            bookingId = bookingsTable.getSelectionModel().getSelectedItem().getBookId();

            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(MainTaxi.class.getResource("addOrderFromBook-view.fxml"));
            Parent page = null;
            try {
                page = loader.load();
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Невозможно открыть форму.", ButtonType.OK);
                alert.showAndWait();
            }
        Stage addStage = new Stage();
        String title = "Добавление нового заказа из брони";
        addStage.setTitle(title);
            addStage.initModality(Modality.APPLICATION_MODAL);
            addStage.initOwner(MainTaxi.getPrimaryStage());
            assert page != null;
            Scene scene = new Scene(page);
            addStage.setScene(scene);
            AddOrderFromBookController controller = loader.getController();
            controller.setAddStage(addStage);
            controller.setBooking(bookingsTable.getSelectionModel().getSelectedItem().getBookId());
            addStage.showAndWait();

            update();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Выберите запись.", ButtonType.OK);
            alert.showAndWait();
        }
    }*/
/** Метод onCreateCl открывает окно для добавления клиентов*/
    public void onCreateCl() {
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(MainTaxi.class.getResource("addClient-view.fxml"));
        Parent page = null;
        try {
            page = loader.load();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Ошибка открытия формы", ButtonType.OK);
            alert.showAndWait();
        }
        Stage addStage = new Stage();
        addStage.setTitle("Добавление нового клиента");
        addStage.initModality(Modality.APPLICATION_MODAL);
        addStage.initOwner(MainTaxi.getPrimaryStage());
        assert page != null;
        Scene scene = new Scene(page);
        addStage.setScene(scene);
        AddClient controller = loader.getController();
        controller.setAddStage(addStage);
        addStage.showAndWait();

        update();
    }

    /** Метод onCreateCar открывает окно для добавления автомобилей*/
    public void onCreateCar(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(MainTaxi.class.getResource("addCar-view.fxml"));
        Parent page = null;
        try {
            page = loader.load();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Ошибка открытия формы", ButtonType.OK);
            alert.showAndWait();
        }
        Stage addStage = new Stage();
        addStage.setTitle("Добавление новой машины");
        addStage.initModality(Modality.APPLICATION_MODAL);
        addStage.initOwner(MainTaxi.getPrimaryStage());
        assert page != null;
        Scene scene = new Scene(page);
        addStage.setScene(scene);
        AddCar controller = loader.getController();
        controller.setAddStage(addStage);
        addStage.showAndWait();
        update();
    }
/** Метод onStateCar изменяет состояние собственности автомобиля у выбранного водителя*/
    public void onStateCar(ActionEvent actionEvent) {
        if (statesOfCar.getSelectionModel().getSelectedItem() != null){
            if ( apartmentsTable.getSelectionModel().getSelectedItem() != null){
                carId = apartmentsTable.getSelectionModel().getSelectedItem().getCarId();
                Statement statement = null;
                try {
                   statement = MainTaxi.getConnection().createStatement();
               } catch (SQLException e) {
                   return;
               }
               try {
                   int info = statement.executeUpdate("UPDATE CAR SET CAR_CONDITION = '" + statesOfCar.getValue() + "' WHERE ID_CAR = " + carId);
                   if (statesOfCar.getValue() == "Рабочее"){
                       //машина свободна
                       String carNum = apartmentsTable.getSelectionModel().getSelectedItem().getGosNumber();
                       info = statement.executeUpdate("UPDATE CAR SET NUM_OF_SEATS = 4 WHERE ST_NUM = '"+ carNum +"'");
                   }
                   else {
                       //машина занята
                       String carNum = apartmentsTable.getSelectionModel().getSelectedItem().getGosNumber();
                       info = statement.executeUpdate("UPDATE CAR SET NUM_OF_SEATS = 0 WHERE ST_NUM = '"+ carNum+"'");
                   }
                   Alert alert = new Alert(Alert.AlertType.INFORMATION, "Данные успешно заменены.", ButtonType.OK);
                   alert.showAndWait();

               } catch (SQLException ex) {
                   Alert alert = new Alert(Alert.AlertType.ERROR, "Ошибка.", ButtonType.OK);
                   alert.showAndWait();
               }
               update();
           }
           else{
               Alert alert = new Alert(Alert.AlertType.WARNING, "Выберите запись.", ButtonType.OK);
               alert.showAndWait();
           }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Выберите состояние авто.", ButtonType.OK);
            alert.showAndWait();
        }
    }
/** Метод onCreateDriv открывает окно для добавления водителя*/
    public void onCreateDriv() {
        FXMLLoader loader = new FXMLLoader();

        Parent page = null;
        loader.setLocation(MainTaxi.class.getResource("addDriver-view.fxml"));
        try {
            page = loader.load();
        }
        catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Ошибка открытия формы", ButtonType.OK);
            alert.showAndWait();
        }
        Stage addStage = new Stage();

        addStage.setTitle("Добавление нового водителя");
        addStage.initModality(Modality.APPLICATION_MODAL);
        addStage.initOwner(MainTaxi.getPrimaryStage());
        assert page != null;
        Scene scene = new Scene(page);
        addStage.setScene(scene);
        AddDriver controller = loader.getController();
        controller.setAddStage(addStage);
        addStage.showAndWait();


        update();
    }
/** Метод onStateDriv меняет состояние выбранного водителя на выбранное из statesDriv*/
    public void onStateDriv(ActionEvent actionEvent) {
        if (statesDriv.getSelectionModel().getSelectedItem() != null){
            if ( apartmentsTable1.getSelectionModel().getSelectedItem() != null){
                driverId = apartmentsTable1.getSelectionModel().getSelectedItem().getDriverId();
                Statement statement = null;
                try {
                    statement = MainTaxi.getConnection().createStatement();
                } catch (SQLException e) {
                    return;
                }
                try {
                    int info = statement.executeUpdate("UPDATE DRIVER SET IS_WORK = '" + statesDriv.getValue() + "' WHERE ID_DRIVER = " + driverId);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Данные успешно заменены.", ButtonType.OK);
                    alert.showAndWait();

                } catch (SQLException ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Ошибка.", ButtonType.OK);
                    alert.showAndWait();
                }
                update();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.WARNING, "Выберите запись.", ButtonType.OK);
                alert.showAndWait();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Выберите состояние.", ButtonType.OK);
            alert.showAndWait();
        }
    }
/** Метод onDeleteClient удаляет выбранного клиента, если он не делал заказы и бронирования.*/
    public void onDeleteClient() {
        if (clientsTable.getSelectionModel().getSelectedItem() != null){
            numCl=clientsTable.getSelectionModel().getSelectedItem().getClientNumber().toString();
            Statement statement = null;
            try {
                statement = MainTaxi.getConnection().createStatement();
            } catch (SQLException e) {
                return;
            }
            try {
                ResultSet resultSet = statement.executeQuery("SELECT * FROM ORDERS WHERE NUM_CL = "+ numCl);
                if (resultSet.next()){
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Нельзя удалять клиента, который делал заказы.", ButtonType.OK);
                    alert.showAndWait();
                }
                else {
                    resultSet = statement.executeQuery("SELECT * FROM BOOKING WHERE CL_NUM = "+ numCl);
                    if (resultSet.next()){
                        Alert alert = new Alert(Alert.AlertType.WARNING, "Нельзя удалять клиента, который бронировал такси.", ButtonType.OK);
                        alert.showAndWait();
                    }
                    else{
                    int info = statement.executeUpdate("DELETE FROM CLIENT WHERE NUM_CL = " + numCl + ";");

                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Данные успешно заменены.", ButtonType.OK);
                    alert.showAndWait();}
                }

            } catch (SQLException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Ошибка.", ButtonType.OK);
                alert.showAndWait();
            }
            update();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING, "Выберите запись.", ButtonType.OK);
            alert.showAndWait();
        }
        update();
    }
    /** Метод onOwnDriv изменяет состояние владения автомобилем*/
    public void onOwnDriv(){
        if (ownDriv.getSelectionModel().getSelectedItem() != null){
            if ( apartmentsTable1.getSelectionModel().getSelectedItem() != null){
                driverId = apartmentsTable1.getSelectionModel().getSelectedItem().getDriverId();
                Statement statement = null;
                try {
                    statement = MainTaxi.getConnection().createStatement();
                } catch (SQLException e) {
                    return;
                }
                try {
                    String yn = null;
                    if (ownDriv.getValue() == "Является собственником")
                        yn = "Да";
                    else yn = "Нет";
                    //if (statement.executeQuery("select ST_NUM from CAR where ST_NUM in (select ST_NUM from driver where is_owner != '1') and owner != 'Служба'").getString("ST_NUM") == apartmentsTable1.getSelectionModel().getSelectedItem().getCarNumber())
                    //select ST_NUM from CAR where ST_NUM in (select ST_NUM from driver where is_owner != '1') and owner != 'Служба'
                    int info = statement.executeUpdate("UPDATE DRIVER SET IS_OWNER = '" + yn + "' WHERE ID_DRIVER = " + driverId);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Данные успешно заменены.", ButtonType.OK);
                    alert.showAndWait();
                } catch (SQLException ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Ошибка.", ButtonType.OK);
                    alert.showAndWait();
                }
                update();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.WARNING, "Выберите запись.", ButtonType.OK);
                alert.showAndWait();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Выберите состояние.", ButtonType.OK);
            alert.showAndWait();
        }

    }
    /** Метод onSumm отображает сумму оплаченных заказов водителя*/
    @FXML
    private void onSumm(){
        if ( apartmentsTable1.getSelectionModel().getSelectedItem() != null) {
            sumOfOrders.setText("");
            driverId = apartmentsTable1.getSelectionModel().getSelectedItem().getDriverId();
            Statement statement = null;
            try {
                statement = MainTaxi.getConnection().createStatement();
            } catch (SQLException e) {
                return;
            }
            try {
                String[] fio = new String[3];
                fio[0]=apartmentsTable1.getSelectionModel().getSelectedItem().getDriverSurname();
                fio[1]=apartmentsTable1.getSelectionModel().getSelectedItem().getDriverName();
                fio[2]=apartmentsTable1.getSelectionModel().getSelectedItem().getDriverPatronymic();

                int summa = 0;
                ResultSet resultSet = statement.executeQuery("SELECT PAY FROM ORDERS WHERE COMPL_MARK = 'Выполнен' AND D_SURNAME = '"+fio[0]+"' AND D_NAME = '"+fio[1]+"' AND D_PATRONYM = '"+fio[2]+"'");
                while (resultSet.next()){
                    summa += resultSet.getInt("PAY");
                    sumOfOrders.setText(Integer.toString(summa));
                }

            } catch (SQLException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Ошибка.", ButtonType.OK);
                alert.showAndWait();
            }
        }
    }
    /** Метод onOrders отображает все заказы выбранного водителя на вкладке "Заказы"*/
    @FXML
    private void onOrders(){
        if ( apartmentsTable1.getSelectionModel().getSelectedItem() != null) {
            driverId = apartmentsTable1.getSelectionModel().getSelectedItem().getDriverId();
            Statement statement = null;
            try {
                statement = MainTaxi.getConnection().createStatement();
            } catch (SQLException e) {
                return;
            }
            try {
                String[] fio = new String[3];
                fio[0]=apartmentsTable1.getSelectionModel().getSelectedItem().getDriverSurname();
                fio[1]=apartmentsTable1.getSelectionModel().getSelectedItem().getDriverName();
                fio[2]=apartmentsTable1.getSelectionModel().getSelectedItem().getDriverPatronymic();

                orders.clear();
                livingsTable.getItems().clear();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM ORDERS WHERE D_SURNAME = '"+fio[0]+"' AND D_NAME = '"+fio[1]+"' AND D_PATRONYM = '"+fio[2]+"'");
                while (resultSet.next()){
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
                    livingsTable.setItems(orders);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Успешно. Посмотрите результат на вкладке \"Заказы\"", ButtonType.OK);
                alert.showAndWait();

            } catch (SQLException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Ошибка.", ButtonType.OK);
                alert.showAndWait();
            }

        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING, "Выберите водителя.", ButtonType.OK);
            alert.showAndWait();
        }
    }
    /** Метод onEditStNum изменяет номер автомобиля для выбранного водителя*/
    @FXML
    private void onEditStNum(){
        if (cbStNumDriv.getSelectionModel().getSelectedItem() != null){
            if ( apartmentsTable1.getSelectionModel().getSelectedItem() != null){
                driverId = apartmentsTable1.getSelectionModel().getSelectedItem().getDriverId();
                Statement statement = null;
                try {
                    statement = MainTaxi.getConnection().createStatement();
                } catch (SQLException e) {
                    return;
                }
                try {
                    int info = statement.executeUpdate("UPDATE DRIVER SET ST_NUM = '" + cbStNumDriv.getValue() + "' WHERE ID_DRIVER = " + driverId);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Данные успешно заменены.", ButtonType.OK);
                    alert.showAndWait();

                } catch (SQLException ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Ошибка.", ButtonType.OK);
                    alert.showAndWait();
                }
                update();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.WARNING, "Выберите запись.", ButtonType.OK);
                alert.showAndWait();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Выберите номер.", ButtonType.OK);
            alert.showAndWait();
        }
    }
}