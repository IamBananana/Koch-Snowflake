module com.example.kochsnowflake {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.kochsnowflake to javafx.fxml;
    exports com.example.kochsnowflake;
}