module com.example.averagereparingtime {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.averagereparingtime to javafx.fxml;
    exports com.example.averagereparingtime;
}