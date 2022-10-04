package com.taxi.javataxi.Controllers;


import com.taxi.javataxi.MainTaxi;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.sql.rowset.serial.SerialBlob;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

/** Класс для работы с добавлением авто*/
public class AddCar {
    @FXML
    private ImageView imageView;
    @FXML
    private TextField tfOrderNumber;
    @FXML
    private TextField tfBrand;
    @FXML
    private ComboBox<String> cbCarStatus;
    @FXML
    private TextField tfColor;
    @FXML
    private TextField tfGosNumber;
    @FXML
    private ComboBox<String> cbFreePlacesCount;
    @FXML
    private ComboBox<String> tfCreatedYear;
    @FXML
    private ComboBox<String> cbOwner;

    byte[] photo;
    private Stage dialogStage;

    private ObservableList<String> _years = FXCollections.observableArrayList();

    /** Метод initialize заполняет поля на форме данными, необходимыми для добавления автомобиля. */
    @FXML
    public void initialize(){
        TextFormatter textFormatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change ->
                Pattern.compile("\\D{0,15}").matcher(change.getControlNewText()).matches() ? change : null);
        TextFormatter textFormatter1 = new TextFormatter((UnaryOperator<TextFormatter.Change>) change ->
                Pattern.compile("[А-Яа-я\s]{0,15}").matcher(change.getControlNewText()).matches() ? change : null);

        tfGosNumber.setText("А000АА");

        TextFormatter gosNumberFormatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change ->
                Pattern.compile("[АВЕКМНОРСТУХ]{0,1}\\d{0,3}[АВЕКМНОРСТУХ]{0,2}").matcher(change.getControlNewText()).matches() ? change : null);
        /*TextFormatter gosNumberFormatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change ->
                Pattern.compile("/^[АВЕКМНОРСТУХ]{2}\\d{3}(?<!000)\\d{2,3}$/ui").matcher(change.getControlNewText()).matches() ? change : null);*/

        addTextLimiter(tfGosNumber,6);

        tfOrderNumber.setText("00АА000000");
        TextFormatter orderNumberFormatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change ->
                Pattern.compile("\\d{0,2}[АВЕКМНОРСТУХ]{0,2}\\d{0,6}").matcher(change.getControlNewText()).matches() ? change : null);
        addTextLimiter(tfOrderNumber,10);

        tfBrand.setTextFormatter(textFormatter);
        tfColor.setTextFormatter(textFormatter1);
        tfGosNumber.setTextFormatter(gosNumberFormatter);
        tfOrderNumber.setTextFormatter(orderNumberFormatter);

        ObservableList stst = FXCollections.observableArrayList("Рабочее", "Ремонт", "Списан");
        cbCarStatus.setItems(stst);
        cbFreePlacesCount.setItems(FXCollections.observableArrayList("1", "2", "3", "4"));
        cbOwner.setItems(FXCollections.observableArrayList("Служба", "Водитель") );

        SimpleDateFormat forma = new SimpleDateFormat("dd MM yyyy");
        Date date1 = new Date();
        String[] d = forma.format(date1).toString().split(" ");
        int year = Integer.parseInt(d[2]);
        for (int i = year - 12; i <= year; i++)
        {
            _years.add(Integer.toString(i));
        }
        tfCreatedYear.setItems(_years);
    }

    public void setAddStage(Stage addStage) {
        this.dialogStage = addStage;
    }

    /**
     * Функция устанавливает лимит на количество символов
     * @param tf TextArea
     * @param maxLength Максимальная длина
     */
    private void addTextLimiter(final TextField tf, final int maxLength) {
        tf.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (tf.getText().length() > maxLength) {
                    String s = tf.getText().substring(0, maxLength);
                    tf.setText(s);
                }
            }
        });
    }
    /**Метод onAddPhoto добавляет фото автомобиля на форму, чтобы его можно было добавить в бд*/
    public void onAddPhoto(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");
        FileChooser.ExtensionFilter filter1 =
                new FileChooser.ExtensionFilter("All image files", "*.png", "*.jpg");
        FileChooser.ExtensionFilter filter2 =
                new FileChooser.ExtensionFilter("JPEG files", "*.jpg");
        FileChooser.ExtensionFilter filter3 =
                new FileChooser.ExtensionFilter("PNG image Files","*.png");
        fileChooser.getExtensionFilters().addAll(filter1, filter2, filter3);

        File file = fileChooser.showOpenDialog(MainTaxi.getPrimaryStage());
        if (file != null) {
            try {
                photo = Files.readAllBytes(file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Image image = new Image(file.toURI().toString());
        imageView.setImage(image);
    }

    /**Метод onDelete удаляет выбранную фотографию автомобиля*/
    public void onDelete(ActionEvent actionEvent) {
        Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
        alert1.setTitle("Удаление");
        alert1.setHeaderText("Вы уверены, что хотите удалить выбранную фотографию?");

        ButtonType buttonTypeOne = new ButtonType("Да");
        ButtonType buttonTypeTwo = new ButtonType("Нет", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert1.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);

        Optional<ButtonType> result = alert1.showAndWait();
        if (result.get() == buttonTypeOne) {
            imageView.getImage().cancel();
            photo = null;
        }
    }

    /** Метод onDone получает данные из полей на форме и добавляет в базу данных запись о новом автомобиле. */
    //Подтверждение                 ДОДЕЛАТЬ
    public void onDone() {
        if (tfGosNumber.getText().length()==6 && tfColor.getText() != "" &&
                cbFreePlacesCount.getValue() != "" && tfOrderNumber.getText().length() ==10 &&cbCarStatus.getValue() != ""
                &&tfCreatedYear.getValue() != "" &&
                tfBrand.getText() != "" && cbOwner.getValue() != null){

            Statement statement = null;
            try {
                statement = MainTaxi.getConnection().createStatement();
            } catch (SQLException e) {
                return;
            }
            try {
                /*int info = statement.executeUpdate("INSERT INTO CAR (OWNER, NCRV, CAR_CONDITION, YEAR_, COLOR_, BRAND, ST_NUM, NUM_OF_SEATS, PHOTO) VALUES " +
                        "('" + cbOwner.getValue() + "','" + tfOrderNumber.getText() + "','" + cbCarStatus.getValue() + "','" + tfCreatedYear.getText() +
                        tfColor.getText() + "','" + tfBrand.getText() + "','" + tfGosNumber.getText() +
                        "','" + cbFreePlacesCount.getValue() + "','" + blobPhoto +"')");*/
                Blob blob = new SerialBlob(photo);
                String _year = tfCreatedYear.getValue() + "-01-01";
                int info = statement.executeUpdate("INSERT INTO CAR (OWNER, NCRV, CAR_CONDITION, YEAR_, COLOR_, BRAND, ST_NUM, NUM_OF_SEATS, PHOTO) VALUES " +
                        "('" + cbOwner.getValue() + "','" + tfOrderNumber.getText() + "','" + cbCarStatus.getValue() + "','" + _year +"','" +
                        tfColor.getText() + "','" + tfBrand.getText() + "','" + tfGosNumber.getText() +
                        "','" + cbFreePlacesCount.getValue() + "','"+ blob +"')");
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Машина успешно добавлена.", ButtonType.OK);
                alert.showAndWait();

                /*try{
                    ResultSet resultSet = statement.executeQuery("SELECT ID_CAR Where ST_NUM = '" +tfGosNumber.getText()+"' ");
                    while (resultSet.next()) {
                        id.add(id.size(), resultSet.getString("ID_CAR"));
                    }
                }catch (SQLException e1){
                    Alert alert1 = new Alert(Alert.AlertType.ERROR, "Ошибка получения id.", ButtonType.OK);
                    alert1.showAndWait();}
                //Фотография
                PreparedStatement statement1;
                try {
                    Blob blob = new SerialBlob(photo);
                    statement1 = MainTaxi.getConnection().prepareStatement("UPDATE CAR SET Photo = "+ blob +" WHERE ID_CAR = '"+id.get(0)+"'");
                    int info1 = statement1.executeUpdate();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Машина успешно добавлена.", ButtonType.OK);
                    alert.showAndWait();
                } catch (SQLException e) {
                    Alert alert1 = new Alert(Alert.AlertType.ERROR, "Ошибка добавления фотографии.", ButtonType.OK);
                    alert1.showAndWait();
                    return;
                }/*
                try {
                    Blob blob = new SerialBlob(photo);
                    statement1.setBlob(1, blob);*/
                    //statement1.setInt(2, carId);
                    /*int info1 = statement1.executeUpdate();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Машина успешно добавлена.", ButtonType.OK);
                    alert.showAndWait();
                } catch (SQLException ex) {
                    Alert alert1 = new Alert(Alert.AlertType.ERROR, "Ошибка добавления фотографии.", ButtonType.OK);
                    alert1.showAndWait();
                }*/
            } catch (SQLException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Ошибка добавления автомобиля.", ButtonType.OK);
                alert.showAndWait();
            }
            Stage stage = (Stage) tfBrand.getScene().getWindow();
            stage.close();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING, "Введите все необходимые данные.", ButtonType.OK);
            alert.showAndWait();
        }
    }
    /** Метод onCancel закрывает данное диалоговое окно. */
    public void onCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) tfOrderNumber.getScene().getWindow();
        stage.close();
    }
}
