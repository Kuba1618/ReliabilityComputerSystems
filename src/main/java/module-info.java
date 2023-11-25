module com.example.averagereparingtime {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.averagereparingtime to javafx.fxml;
    exports com.example.averagereparingtime;
}