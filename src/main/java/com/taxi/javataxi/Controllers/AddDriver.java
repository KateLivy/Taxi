package com.taxi.javataxi.Controllers;

import com.taxi.javataxi.Classes.Driver;
import com.taxi.javataxi.MainTaxi;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

/** Класс для работы с добавлением водителей*/
public class AddDriver {
    @FXML
    private TextField tfLastName;
    @FXML
    private TextField tfFirstName;
    @FXML
    private TextField tfPatronymic;
    @FXML
    private ComboBox tfGosNumber;
    @FXML
    private CheckBox chbIsOwner;
    @FXML
    private TextField tfDriverPass;
    @FXML
    private DatePicker dtpPassDate;
    @FXML
    private ComboBox<String> cbDriverStat;

    int driverId;
    String firstName;
    String lastName;
    String patronymic;
    private Stage dialogStage;
/** Метод initialize заполняет все поля заданными значениями*/
    @FXML
    public void initialize(){

        /*tfLastName.textProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue.matches("\\D{1,15}+$")) {
                        int number = Integer.parseInt(newValue);
                        if (number < 60 && newValue.length()<3) {
                            return;
                        }
                        tfLastName.setText(oldValue);
                    }
                    else tfLastName.setText("");
                });*/


        addTextLimiter(tfLastName,15);
        Pattern pattern = Pattern.compile("[А-Яа-я\s]*");
        UnaryOperator<TextFormatter.Change> filter = c -> {
            if (pattern.matcher(c.getControlNewText()).matches()) {
                return c ;
            } else {
                return null ;
            }
        };
        TextFormatter<String> formatter = new TextFormatter<>(filter);
        tfLastName.setTextFormatter(formatter);

        //TextFormatter formatLast = new TextFormatter((UnaryOperator<TextFormatter.Change>) change ->
        //              Pattern.compile("\\D{1,15}").matcher(change.getControlNewText()).matches() ? change : null);
        TextFormatter formatI = new TextFormatter((UnaryOperator<TextFormatter.Change>) change ->
                Pattern.compile("\\D{1,15}").matcher(change.getControlNewText()).matches() ? change : null);
        TextFormatter formatO = new TextFormatter((UnaryOperator<TextFormatter.Change>) change ->
                Pattern.compile("\\D{1,15}").matcher(change.getControlNewText()).matches() ? change : null);
        //TextFormatter  gosNumberFormatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change ->
          //      Pattern.compile("\\D\\d{3}\\D{2}").matcher(change.getControlNewText()).matches() ? change : null);
        TextFormatter  driverPassNumberFormatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change ->
                Pattern.compile("\\d{0,10}").matcher(change.getControlNewText()).matches() ? change : null);

        //tfLastName.setTextFormatter(formatLast);
        tfFirstName.setTextFormatter(formatI);
        tfPatronymic.setTextFormatter(formatO);
        //tfGosNumber.setTextFormatter(gosNumberFormatter);
        tfDriverPass.setTextFormatter(driverPassNumberFormatter);

        cbDriverStat.setItems(FXCollections.observableArrayList("Рабочее", "На линии", "На больничном", "В отпуске", "Уволен"));

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
            tfGosNumber.setItems(mb);
        }
        catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Не удалось вывести данные из базы данных", ButtonType.OK);
            alert.showAndWait();
        }
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
    public void setAddStage(Stage addStage) {
        this.dialogStage = addStage;
    }
/** Метод onDone вносит запись о водителе в бд, проверяя ввод всех данных*/
    public void onDone() {
        if (tfLastName.getText() != "" && tfFirstName.getText() != "" &&
                tfPatronymic.getText() != "" && cbDriverStat.getValue() != null &&
                tfGosNumber.getValue() != null && tfDriverPass.getText() !="" &&
                dtpPassDate.getValue() != null){

            Statement statement = null;
            try {
                statement = MainTaxi.getConnection().createStatement();
            } catch (SQLException e) {
                return;
            }
            try {
                //Добавить водителя " " + hours.getText()+ ":"+minutes.getText()
                String dt = dtpPassDate.getValue().toString();
                String isOwner = "";
                if (chbIsOwner.isSelected()){
                    isOwner = "Да";
                }
                else isOwner = "Нет";
                String ln = tfLastName.getText();
                String fn = tfFirstName.getText();
                String  p=tfPatronymic.getText();
                String  ds = cbDriverStat.getValue().toString();
                String  gn = tfGosNumber.getValue().toString();
                String  dp = tfDriverPass.getText();
                int info = statement.executeUpdate("INSERT INTO DRIVER (D_SURNAME, D_NAME, D_PATRONYM, IS_WORK, " +
                        "ST_NUM, IS_OWNER, DL_NUM, " +
                        "DATE_LICENSE) VALUES('"
                        + ln + "', '" + fn + "', '" + p + "', '" + ds +"', '" +
                        gn + "', '" + isOwner + "', '" + dp +
                        "', '" + dt+"')");
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Водитель успешно добавлен.", ButtonType.OK);
                alert.showAndWait();

            } catch (SQLException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Ошибка добавления водителя.", ButtonType.OK);
                alert.showAndWait();
            }
            Stage stage = (Stage) tfLastName.getScene().getWindow();
            stage.close();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING, "Введите все необходимые данные.", ButtonType.OK);
            alert.showAndWait();
        }
    }

    /**Метод onCancel закрывает форму*/
    public void onCancel() {
        Stage stage = (Stage) tfDriverPass.getScene().getWindow();
        stage.close();
    }
}
