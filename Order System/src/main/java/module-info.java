module template.try_demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens template.try_demo to javafx.fxml;
    exports template.try_demo;
}