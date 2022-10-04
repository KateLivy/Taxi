package com.taxi.javataxi.Controllers;

import com.taxi.javataxi.Classes.Booking;
import com.taxi.javataxi.Classes.Driver;
import com.taxi.javataxi.MainTaxi;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.Date;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

/** Класс для работы с добавлением заказов*/
public class AddOrderController {
    @FXML
    public TextField hours;
    @FXML
    public TextField minutes;
    @FXML
    private DatePicker dtpOrderDate;
    @FXML
    private TextField tfAddressOtbytiya;
    @FXML
    private TextField tfAddressPribytia;
    @FXML
    private ComboBox cbClient;
    @FXML
    private TextField tfPrice;
    @FXML
    private ComboBox cbStatus;
    @FXML
    private TextField tfCountSeats;
    @FXML
    private TextField tfGosNumber;
    @FXML
    private ComboBox cbDriver;
    @FXML
    private ImageView imageView;

    private ObservableList<Driver> drivers = FXCollections.observableArrayList();
    private ObservableList<Integer> clients = FXCollections.observableArrayList();
    private ObservableList<String> driversList = FXCollections.observableArrayList();
    private ObservableList<Booking> book = FXCollections.observableArrayList();

    String firstName;
    String lastName;
    String patronymic;
    String isWork;
    String carNumber;
    String driverLicenseNumber;
    boolean isOwner;
    int driverId;
    Date dateLicense;
    int photoId;
    byte[] photo;
    Blob blobPhoto;
    Stage dialogStage;
    int lastCount = 5;
    boolean flag = true;

    /** Метод initialize заполняет поля на форме данными, необходимыми для добавления заказа. */

    @FXML
    public void initialize(){

        Statement statement = null;
        Statement statement1 = null;
        try {
            statement = MainTaxi.getConnection().createStatement();
            statement1 = MainTaxi.getConnection().createStatement();
        } catch (SQLException e) {
            return;
        }

        try {
            ResultSet resultSet = statement1.executeQuery("SELECT * FROM driver where St_num in(SELECT ST_NUM FROM DRIVER WHERE IS_WORK = 'На линии') and ST_NUM IN (SELECT ST_NUM FROM (SELECT ST_NUM FROM CAR WHERE NUM_OF_SEATS > '0' AND CAR_CONDITION = 'Рабочее'))");
//SELECT * FROM driver where St_num in(SELECT ST_NUM FROM DRIVER WHERE IS_WORK = 'На линии') and ST_NUM IN (SELECT ST_NUM FROM (SELECT ST_NUM FROM CAR WHERE NUM_OF_SEATS > '0' AND CAR_CONDITION = 'Рабочее'))
            while (resultSet.next()) {
                flag = false;
                break;
            }
        }catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Ошибка поиска водителей.", ButtonType.OK);
            alert.showAndWait();
        }
        if (flag){
            Alert alert = new Alert(Alert.AlertType.WARNING, "Свободных водителей на линии нет. Делать заказ бессмысленно.", ButtonType.OK);
            alert.showAndWait();
        }
        else {
            try {
                ResultSet resultSet = statement1.executeQuery("SELECT * FROM Client");
                while (resultSet.next()) {
                    clients.add(clients.size(), Integer.parseInt(resultSet.getString("NUM_CL")));
                }
                cbClient.setItems(clients);
            } catch (SQLException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Ошибка поиска клиентов.", ButtonType.OK);
                alert.showAndWait();
            }

            TextFormatter priceFormatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change ->
                    Pattern.compile("\\d{0,5}").matcher(change.getControlNewText()).matches() ? change : null);

            tfPrice.setTextFormatter(priceFormatter);

            TextFormatter seatsFormatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change ->
                    Pattern.compile("[1-4]").matcher(change.getControlNewText()).matches() ? change : null);

            tfCountSeats.setTextFormatter(seatsFormatter);

            cbStatus.setItems(FXCollections.observableArrayList("Не выполнен", "Выполнен", "Выполняется"));

            hours.textProperty().addListener(
                    (observable, oldValue, newValue) -> {
                        if (newValue.matches("^[0-9]+$")) {
                            int number = Integer.parseInt(newValue);
                            if (number < 24 && newValue.length()<3) {
                                return;
                            }
                            hours.setText(oldValue);
                        }
                        else hours.setText("");
                    });

            minutes.textProperty().addListener(
                    (observable, oldValue, newValue) -> {
                        if (newValue.matches("^[0-9]+$")) {
                            int number = Integer.parseInt(newValue);
                            if (number < 60 && newValue.length()<3) {
                                return;
                            }
                            minutes.setText(oldValue);
                        }
                        else minutes.setText("");
                    });
        }

        //register FontController in  Context Class
        Context.getInstance().setAddOrderController(this);
    }

    public void setAddStage(Stage addStage) {
        this.dialogStage = addStage;
    }

/**Метод onAddAddressOtb открывает окно для ввода и вводит адрес отбытия (адрес клиента)*/
    public void onAddAddressOtb(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(MainTaxi.class.getResource("addAddress-view.fxml"));
        Parent page = null;
        try {
            page = loader.load();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Ошибка открытия формы", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        Stage addStage = new Stage();
        addStage.setTitle("Адрес отбытия");
        addStage.initModality(Modality.APPLICATION_MODAL);
        addStage.initOwner(MainTaxi.getPrimaryStage());
        Scene scene = new Scene(page);
        addStage.setScene(scene);
        AddAddress controller = loader.getController();
        //getting Controller variables and methods through Context class
        AddAddress cont = Context.getInstance().getAddAddress();
        controller.setAddStage(addStage);

        addStage.showAndWait();

        addStage.close();
        //tfAddressOtbytiya.setText(cont.getTfRegion().getText() + " " + cont.getTfTown().getText() + " " + cont.getTfStreet().getText() + " " +cont.getTfHouse().getText());
    }

    public TextField getTfAddressOtbytiya() {
        return tfAddressOtbytiya;
    }

    public void setTfAddressOtbytiya(TextField tfAddressOtbytiya) {
        this.tfAddressOtbytiya = tfAddressOtbytiya;
    }
    /**Метод onAddAddressPrib открывает окно для ввода и вводит адрес прибытия*/

    public void onAddAddressPrib(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(MainTaxi.class.getResource("addAddress-view.fxml"));
        Parent page = null;
        try {
            page = loader.load();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Ошибка открытия формы", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        Stage addStage = new Stage();
        addStage.setTitle("Адрес прибытия");
        addStage.initModality(Modality.APPLICATION_MODAL);
        addStage.initOwner(MainTaxi.getPrimaryStage());
        Scene scene = new Scene(page);
        addStage.setScene(scene);
        AddAddress controller = loader.getController();
        //getting Controller variables and methods through Context class
        AddAddress cont = Context.getInstance().getAddAddress();

        controller.setAddStage(addStage);

        addStage.showAndWait();
    }

    public TextField getTfAddressPribytia() {
        return tfAddressPribytia;
    }

    public void setTfAddressPribytia(TextField tfAddressPribytia) {
        this.tfAddressPribytia = tfAddressPribytia;
    }
/** Метод onAddClient открывает окно для добавления нового клиента и следит за изменением клиентов в комбобоксе*/
    public void onAddClient(ActionEvent actionEvent) {
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

        Statement statement = null;
        try {
            statement = MainTaxi.getConnection().createStatement();
        } catch (SQLException e) {
            return;
        }
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Client");
            clients.clear();
            while (resultSet.next()) {
                clients.add(clients.size(), Integer.parseInt(resultSet.getString("NUM_CL")));
            }
            cbClient.setItems(clients);
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Ошибка поиска клиентов.", ButtonType.OK);
            alert.showAndWait();
        }
    }
/**Метод onDone проверяет введённые данные и вносит заказ в бд*/
    public void onDone(ActionEvent actionEvent) {
        boolean congrats = false;
        if(dtpOrderDate.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Вы не выбрали дату", ButtonType.OK);
            alert.showAndWait();
        }
        else if(hours.getText() == "" || minutes.getText() ==""){
            Alert alert = new Alert(Alert.AlertType.WARNING, "Вы не ввели время", ButtonType.OK);
            alert.showAndWait();
        }
        else if(tfAddressOtbytiya.getText()==""|| tfAddressPribytia.getText()==""){
            Alert alert = new Alert(Alert.AlertType.WARNING, "Вы не ввели адрес", ButtonType.OK);
            alert.showAndWait();
        }
        else if(cbClient.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.WARNING, "Вы не выбрали клиента", ButtonType.OK);
            alert.showAndWait();
        }
        else if (tfPrice.getText() == ""){
            Alert alert = new Alert(Alert.AlertType.WARNING, "Вы не ввели цену поездки", ButtonType.OK);
            alert.showAndWait();
        }
        else if (cbStatus.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.WARNING, "Вы не выбрали статус поездки", ButtonType.OK);
            alert.showAndWait();
        }
        else if(tfCountSeats.getText().isEmpty()){
            cbDriver.setDisable(true);
            Alert alert = new Alert(Alert.AlertType.WARNING, "Заполните количество мест.", ButtonType.OK);
            alert.showAndWait();}
        else
            if (lastCount < Integer.parseInt(tfCountSeats.getText())){
                onShowDrivers();
                tfGosNumber.setText("");
                imageView.setImage(null);
                imageView.setVisible(false);
                Alert alert = new Alert(Alert.AlertType.WARNING, "Вы изменили количество необходимых мест на наибольшее. Возможно в этой машине не так свободно. Попробуйте ещё раз.", ButtonType.OK);
                alert.showAndWait();
            }
            else
                if (cbDriver.getSelectionModel().getSelectedItem() == null){
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Выберите водителя.", ButtonType.OK);
                    alert.showAndWait();
                }
                else if (tfGosNumber.getText() ==""){
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Вы даже не посмотрите номер и фото машины?", ButtonType.OK);
                    alert.showAndWait();
                    onShowPhoto();
                    congrats = true;
                }
                else {
                    Statement statement = null;
                    try {
                        statement = MainTaxi.getConnection().createStatement();
                    } catch (SQLException e) {
                        return;
                    }
                    try {
                        String dt = dtpOrderDate.getValue()+ " " + hours.getText()+ ":"+minutes.getText();
                        String[] ca = new String[5];
                        ca = tfAddressOtbytiya.getText().split(" ");
                        String[] ar = new String[5];
                        ar = tfAddressPribytia.getText().split(" ");
                        String[] fio = new String[3];
                        fio = cbDriver.getSelectionModel().getSelectedItem().toString().split(" ");

                        int info = statement.executeUpdate("INSERT INTO ORDERS (DATETIME, CL_REGION, CL_TOWN, " +
                                "CL_STR, CL_HOME, ARR_REGION, ARR_TOWN, " +
                                "ARR_STR, ARR_HOME, D_SURNAME, D_NAME," +
                                " D_PATRONYM, CAR_NUM, NUM_CL, PAY, COMPL_MARK) " +
                                "VALUES('"+dt+"', '"+ ca[0]+" "+ ca[1]+"', '"+ca[2]+"', '"+
                        ca[3]+"', '"+ ca[4]+"', '"+ ar[0]+" "+ar[1]+"', '"+ ar[2]+"', '"+
                        ar[3]+"', '"+ ar[4]+"', '"+ fio[0]+"', '"+ fio[1]+"', '"+
                        fio[2]+"', '"+ tfGosNumber.getText()+"', '"+ cbClient.getSelectionModel().getSelectedItem()+"', '"+ tfPrice.getText()+"', '"+ cbStatus.getSelectionModel().getSelectedItem()+"'); ");
                            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Заказ добавлен.", ButtonType.OK);
                            alert.showAndWait();
                        Stage stage = (Stage) tfPrice.getScene().getWindow();
                        stage.close();
                    }catch (SQLException ex) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Ошибка добавления заказа", ButtonType.OK);
                        alert.showAndWait();
                    }
                }
    }
    @FXML
/** Метод onShowNum выводит номер автомобиля, которым управляет выбранный водитель*/
    public void onShowNum(){
        if(!tfCountSeats.getText().isEmpty()){
        if (lastCount<Integer.parseInt(tfCountSeats.getText())){
            onShowDrivers();
            tfGosNumber.setText("");
            imageView.setImage(null);
            imageView.setVisible(false);
            Alert alert = new Alert(Alert.AlertType.WARNING, "Вы изменили количество необходимых мест на наибольшее. Возможно в этой машине не так свободно. Попробуйте ещё раз.", ButtonType.OK);
            alert.showAndWait();
        }
        else{
            if (cbDriver.getSelectionModel().getSelectedItem() != null){
                //индекс выбранного водителя, совпадающий с индексом в drivers
                int num = cbDriver.getSelectionModel().getSelectedIndex();
                tfGosNumber.setText(drivers.get(num).getCarNumber());
            }
            else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Выберите водителя.", ButtonType.OK);
                alert.showAndWait();
            }
        }}
        else{
            cbDriver.setDisable(true);
            Alert alert = new Alert(Alert.AlertType.WARNING, "Заполните количество мест.", ButtonType.OK);
            alert.showAndWait();
        }
    }
/** Метод onShowPhoto отображает номер машины и фото*/
    public void onShowPhoto() {
        if (!tfCountSeats.getText().isEmpty()&& cbDriver.getSelectionModel().getSelectedItem() != null){
        onShowNum();
        Statement statement = null;
        Path tempFile = null;
        try {
            statement = MainTaxi.getConnection().createStatement();
        } catch (SQLException e) {
            return;
        }
        try {
            ResultSet resultSet = statement.executeQuery("SELECT PHOTO FROM CAR WHERE ST_NUM = '" + tfGosNumber.getText() + "'");
            while (resultSet.next()) {
                blobPhoto = resultSet.getBlob("Photo");
                try {
                    tempFile = Files.createTempFile("temp-", ".tmp");
                    Files.write(tempFile, photo = blobPhoto.getBytes(1, (int) blobPhoto.length()));
                    InputStream inputStream = Files.newInputStream(tempFile);
                    Image image = new Image(inputStream);
                    imageView.setImage(image);
                    imageView.setVisible(true);
                } catch (IOException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Не удалось загрузить фотографию, возможно, она отсутствует для данного автомобиля", ButtonType.OK);
                    alert.showAndWait();
                }
            }
        }
            catch(SQLException ex){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Не удалось загрузить фотографию, возможно, она отсутствует для данного автомобиля", ButtonType.OK);
                alert.showAndWait();
            }
            /*
            ResultSet resultSet = statement.executeQuery("SELECT * FROM DRIVERS");
            while (resultSet.next()) {
                blobPhoto = resultSet.getBlob("DRIVERS");
                try {
                    tempFile = Files.createTempFile("temp-", ".tmp");
                    Files.write(tempFile, photo = blobPhoto.getBytes(1, (int)blobPhoto.length()));
                    InputStream inputStream = Files.newInputStream(tempFile);
                    Image image = new Image(inputStream);
                    imageView.setImage(image);
                } catch (IOException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Не удалось загрузить фотографию", ButtonType.OK);
                    alert.showAndWait();
                }
            }
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Не удалось вывести данные из базы данных", ButtonType.OK);
            alert.showAndWait();
        }*/

        }
        else{
            cbDriver.setDisable(true);
            Alert alert = new Alert(Alert.AlertType.WARNING, "Заполните количество мест и выберите водителя.", ButtonType.OK);
            alert.showAndWait();
        }
    }
/**Метод onCancel закрывает форму*/
    public void onCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) tfGosNumber.getScene().getWindow();
        stage.close();
    }
/** Метод onShowDrivers заполняет комбобокс с водителями*/
    public void onShowDrivers() {
        if (!tfCountSeats.getText().isEmpty()){
        cbDriver.getItems().clear();
        tfGosNumber.clear();
        imageView.setImage(null);
        lastCount = Integer.parseInt(tfCountSeats.getText());
        cbDriver.setDisable(false);
        Statement statement = null;
        try {
            statement = MainTaxi.getConnection().createStatement();
        } catch (SQLException e) {
            return;
        }
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM driver where St_num in(SELECT ST_NUM FROM DRIVER WHERE IS_WORK = 'На линии') and ST_NUM IN (SELECT ST_NUM FROM (SELECT ST_NUM FROM CAR WHERE NUM_OF_SEATS >= '"+lastCount+"' AND CAR_CONDITION = 'Рабочее'))");
            while (resultSet.next()) {
                driverId = resultSet.getInt("ID_DRIVER");
                lastName= resultSet.getString("D_SURNAME");
                firstName= resultSet.getString("D_NAME");
                patronymic= resultSet.getString("D_PATRONYM");
                drivers.add(new Driver(driverId, lastName, firstName, patronymic,
                        resultSet.getString("IS_WORK"), resultSet.getString("ST_NUM"), resultSet.getString("IS_OWNER"), resultSet.getString("DL_NUM"), resultSet.getDate("DATE_LICENSE")));
                driversList.add(lastName + " " + firstName + " " + patronymic);
            }
            cbDriver.setItems(driversList);
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Ошибка поиска водителей.", ButtonType.OK);
            alert.showAndWait();
        }}
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING, "Заполните количество мест.", ButtonType.OK);
            alert.showAndWait();
        }

    }
}
/*[Display(Name = "Машина, выполняющая заказ")]
        [RegularExpression(@"[АВЕКМНОРСТУХ]{1}[0-9]{3}[АВЕКМНОРСТУХ]{2}", ErrorMessage = "Введён неверный номер")]

        [Display(Name = "Номер свидетельства о регистрации транспортного средства (2 цифры, 2 буквы, 6 цифр)")]
                [RegularExpression(@"[0-9]{2}[АВЕКМНОРСТУХ]{2}[0-9]{6}", ErrorMessage = "Введён неверный номер")]

        [Display(Name = "Цвет")]
        [Display(Name = "Марка")]
        [RegularExpression(@"[А-Я][а-я,ё]+", ErrorMessage = "Это поле должно содержать только буквы")]

        [Display(Name = "Номер водительского удостоверения (сост. из 10 чисел)")]
        [RegularExpression(@"[0-9]{10}", ErrorMessage = "Введён неверный номер")]
 */