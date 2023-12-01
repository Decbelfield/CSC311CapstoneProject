module com.example.csc311capstoneproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.prefs;


    opens com.example.csc311capstoneproject to javafx.fxml;
    exports com.example.csc311capstoneproject;
    exports db;
    opens db to javafx.fxml;
}