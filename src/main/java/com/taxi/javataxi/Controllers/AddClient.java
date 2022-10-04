package com.taxi.javataxi.Controllers;

import com.taxi.javataxi.Classes.Client;
import com.taxi.javataxi.MainTaxi;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
/** Класс для работы с добавлением клиентов*/
public class AddClient {
    @FXML
    private TextField numCl;
    @FXML
    private ListView numClList;

    private Stage dialogStage;
    Client client;
    int clientId;
    /** Список предлагаемых для добавления клиентов*/
    private ObservableList<String> mb = FXCollections.observableArrayList();
    /** Список клиентов*/
    private ObservableList<String> cls = FXCollections.observableArrayList();

    /** Метод initialize заполняет поля на форме данными, необходимыми для добавления клиента. */
    @FXML
    public void initialize(){
        TextFormatter numCltex = new TextFormatter((UnaryOperator<TextFormatter.Change>) change ->
               Pattern.compile("\\d{0,11}").matcher(change.getControlNewText()).matches() ? change : null);

        numCl.setTextFormatter(numCltex);

//заполнение листа
        Statement statement1 = null;
        try {
            statement1 = MainTaxi.getConnection().createStatement();
        } catch (SQLException e) {
            return;
        }
        try {
            ResultSet resultSet = statement1.executeQuery("SELECT * FROM CLIENT");
            int count = 0;
            while (resultSet.next()) {
                count++;
            }

            int[] clients = new int[count];
            resultSet = statement1.executeQuery("SELECT * FROM CLIENT");
            int last = 0;
            while (resultSet.next()) {
                clients[last] = Integer.parseInt(resultSet.getString("NUM_CL"));
                last++;
                cls.add(cls.size(), resultSet.getString("NUM_CL"));
            }
            Arrays.sort(clients);
            count = 0;
                for (int i = 1; i < 5000; i++)
                {
                    boolean flag = true;
                    for (int v: clients)
                    {
                        if(v == i)
                        {
                            flag = false;
                            break;
                        }
                    }
                    if (flag && (count != 5))
                    {
                        mb.add(Integer.toString(i));
                        count++;
                    }
                    if (count == 5){
                        numClList.setItems(mb);
                        break;
                    }
                }
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Ошибка поиска клиентов.", ButtonType.OK);
            alert.showAndWait();
        }
    }

    public void setAddStage(Stage addStage) {
        this.dialogStage = addStage;
    }

    /** Метод onOk получает данные из полей на форме и добавляет в базу данных запись о новом клиенте. */
    public void onOk(ActionEvent actionEvent) {
        //Проверка на ввод
        if ((numCl.getText() == "")&&(numClList.getSelectionModel().getSelectedItem() == null)){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Ошибка, проверьте введённые данные.", ButtonType.OK);
            alert.showAndWait();
        }
        else{
            //проверка на текстовое поле
        if (numCl.getText() != ""){
            Statement statement = null;
            try {
                statement = MainTaxi.getConnection().createStatement();
            } catch (SQLException e) {
                return;
            }
            //проверка на существующего клиента
            String curr = numCl.getText();
            if (cls.lastIndexOf(curr)!=-1){
                //exist
                Alert alert = new Alert(Alert.AlertType.ERROR, "Данный клиент существует.", ButtonType.OK);
                alert.showAndWait();
            }
            else{
                //добавление
            try{
                int info = statement.executeUpdate("INSERT INTO CLIENT (NUM_CL) VALUES " +
                            "(" + numCl.getText()+");");
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Клиент успешно добавлен.", ButtonType.OK);
                alert.showAndWait();
            } catch (SQLException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Ошибка, проверьте введённые данные.", ButtonType.OK);
                alert.showAndWait();
            }
            Stage stage = (Stage) numCl.getScene().getWindow();
            stage.close();
            }
        }
        else{
            //проверка на выбор предлагаемого клиента
            if (numClList.getSelectionModel().getSelectedItem() != null){
                Statement statement = null;
                try {
                    statement = MainTaxi.getConnection().createStatement();
                } catch (SQLException e) {
                    return;
                }
                try{
                    //добавление
                    int info = statement.executeUpdate("INSERT INTO CLIENT (NUM_CL) VALUES " +
                            "(" + numClList.getSelectionModel().getSelectedItem().toString()+");");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Клиент успешно добавлен.", ButtonType.OK);
                    alert.showAndWait();
                } catch (SQLException ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Ошибка, проверьте введённые данные.", ButtonType.OK);
                    alert.showAndWait();
                }
                Stage stage = (Stage) numCl.getScene().getWindow();
                stage.close();
            }
        }
        }
    }

    /** Метод onCancel закрывает данное диалоговое окно. */
    public void onCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) numCl.getScene().getWindow();
        stage.close();
    }
}
