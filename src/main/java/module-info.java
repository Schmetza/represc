module com.example.represc {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.prefs;


    exports com.example.represc.gui;
    opens com.example.represc.gui to javafx.fxml;
    exports com.example.represc.utils;
    opens com.example.represc.utils to javafx.fxml;
}