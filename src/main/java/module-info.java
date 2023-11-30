module com.example.csc311capstoneproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.csc311capstoneproject to javafx.fxml;
    exports com.example.csc311capstoneproject;
}