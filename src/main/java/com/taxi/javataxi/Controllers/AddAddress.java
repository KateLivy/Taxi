package com.taxi.javataxi.Controllers;

import com.taxi.javataxi.Classes.Address;
import com.taxi.javataxi.MainTaxi;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.sql.rowset.serial.SerialBlob;
import java.net.URL;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

/** Класс для работы с добавлением адреса*/
public class AddAddress {
    @FXML
    public TextField tfRegion;
    @FXML
    public TextField tfTown;
    @FXML
    public TextField tfStreet;
    @FXML
    public TextField tfHouse;

    private Stage dialogStage;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {

        //register Controller in  Context Class
        Context.getInstance().setAddAddress(this);

    }
    public void setAddStage(Stage addStage) {
        this.dialogStage = addStage;
    }

    @FXML
    public void onDone(ActionEvent actionEvent) {
        if (tfRegion.getText() != "" && tfTown.getText() != "" &&
                tfStreet.getText() != "" && tfHouse.getText() != ""){

            Stage stage = (Stage) tfHouse.getScene().getWindow();

            //getting FontController through Context Class
            AddOrderController addCont = Context.getInstance().getAddOrderController();
            //getting FontController through Context Class
            AddBookingController addCont1 = Context1.getInstance().getAddBookingController();
            if (stage.getTitle().toString() == "Адрес отбытия") {
                addCont.getTfAddressOtbytiya().setText(tfRegion.getText() + " " + tfTown.getText() + " " + tfStreet.getText() + " " + tfHouse.getText());
            }else if(stage.getTitle().toString() == "Адрес прибытия") {
                addCont.getTfAddressPribytia().setText(tfRegion.getText() + " " + tfTown.getText() + " " + tfStreet.getText() + " " + tfHouse.getText());
            }
            else if (stage.getTitle().toString() == "Адрес отбытия бронирования")
                addCont1.getTfAddressOtbytiya().setText(tfRegion.getText() + " " + tfTown.getText() + " " + tfStreet.getText() + " " + tfHouse.getText());
            else if (stage.getTitle().toString() == "Адрес прибытия бронирования")
                addCont1.getTfAddressPribytia().setText(tfRegion.getText() + " " + tfTown.getText() + " " + tfStreet.getText() + " " + tfHouse.getText());

            stage.close();

        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING, "Введите все необходимые данные.", ButtonType.OK);
            alert.showAndWait();
        }

    }
    public TextField getTfRegion() {
        return tfRegion;
    }

    public void setTfRegion(TextField tfRegion) {
        this.tfRegion = tfRegion;
    }
    public TextField getTfTown() {
        return tfTown;
    }

    public void setTfTown(TextField tfTown) {
        this.tfTown = tfTown;
    }
    public TextField getTfStreet() {
        return tfStreet;
    }

    public void setTfStreet(TextField tfStreet) {
        this.tfStreet = tfStreet;
    }
    public TextField getTfHouse() {
        return tfHouse;
    }

    public void setTfHouse(TextField tfHouse) {
        this.tfHouse = tfHouse;
    }
    @FXML
    private void onCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) tfHouse.getScene().getWindow();
        stage.close();
    }
}
