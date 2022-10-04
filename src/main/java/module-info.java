module com.taxi.javataxi {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.sql.rowset;


    opens com.taxi.javataxi to javafx.fxml;
    exports com.taxi.javataxi;
    exports com.taxi.javataxi.Controllers;
    opens com.taxi.javataxi.Controllers to javafx.fxml;
}