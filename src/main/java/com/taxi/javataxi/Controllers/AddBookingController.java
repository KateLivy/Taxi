package com.taxi.javataxi.Controllers;

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
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

/** Класс для работы с добавлением бронирований. В проекте не используется.*/
public class AddBookingController {
    public DatePicker dtpOrderDate;
    public TextField hours;
    public TextField minutes;
    public TextField tfAddressOtbytiya;
    public TextField tfAddressPribytia;
    public ComboBox cbClient;
    public TextField tfCountSeats;
    public TextField tfPrice;

    private ObservableList<Driver> drivers = FXCollections.observableArrayList();
    private ObservableList<Integer> clients = FXCollections.observableArrayList();
    private ObservableList<String> driversList = FXCollections.observableArrayList();

    Stage dialogStage;
    boolean flag = true;

    /** Метод initialize заполняет поля на форме данными, необходимыми для добавления бронирования. */
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


        //register FontController in  Context Class
        Context1.getInstance().setAddBookingController(this);

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
        addStage.setTitle("Адрес отбытия бронирования");
        addStage.initModality(Modality.APPLICATION_MODAL);
        addStage.initOwner(MainTaxi.getPrimaryStage());
        Scene scene = new Scene(page);
        addStage.setScene(scene);
        AddAddress controller = loader.getController();
        //getting Controller variables and methods through Context class
        AddAddress cont1 = Context.getInstance().getAddAddress();
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
        addStage.setTitle("Адрес прибытия бронирования");
        addStage.initModality(Modality.APPLICATION_MODAL);
        addStage.initOwner(MainTaxi.getPrimaryStage());
        Scene scene = new Scene(page);
        addStage.setScene(scene);
        AddAddress controller = loader.getController();
        //getting Controller variables and methods through Context class
        AddAddress cont1 = Context.getInstance().getAddAddress();

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
    /**Метод onDone проверяет введённые данные и вносит бронь в бд*/

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

                int info = statement.executeUpdate("INSERT INTO BOOKING (DATETIME, CL_REG, CL_TOWN, CL_STR, CL_HOME," +
                        " ARR_REG, ARR_TOWN, ARR_STR, ARR_HOME, " +
                        "NUM_OF_SEATS, PAY, CL_NUM) VALUES ('"+dt+"', '"+ ca[0]+" "+ ca[1]+"', '"+ca[2]+"', '"+
                        ca[3]+"', '"+ ca[4]+"', '"+ ar[0]+" "+ar[1]+"', '"+ ar[2]+"', '"+
                        ar[3]+"', '"+ ar[4]+"', '"+tfCountSeats.getText()+"', '"+ tfPrice.getText()+"', '"+ cbClient.getSelectionModel().getSelectedItem()+"'); ");
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Бронь добавлена.", ButtonType.OK);
                alert.showAndWait();
                Stage stage = (Stage) tfPrice.getScene().getWindow();
                stage.close();
            }catch (SQLException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Ошибка добавления бронирования", ButtonType.OK);
                alert.showAndWait();
            }
        }
    }
    /**Метод onCancel закрывает форму*/
    public void onCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) tfPrice.getScene().getWindow();
        stage.close();
    }
}

