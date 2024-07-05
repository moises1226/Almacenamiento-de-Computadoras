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
}