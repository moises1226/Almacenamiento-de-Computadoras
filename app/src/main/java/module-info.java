module sm.app {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    requires java.sql;
    requires mysql.connector.j;
    requires jdk.jdi;

    opens sm.app to javafx.fxml;
    exports sm.app;
    exports sm.app.model;
    opens sm.app.model to javafx.fxml;
    exports sm.app.db;
    opens sm.app.db to javafx.fxml;
    exports sm.app.controller;
    opens sm.app.controller to javafx.fxml;
}