package sm.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class APP extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(APP.class.getResource("PanelPrincipal.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1075, 687);
        stage.setTitle("Guardado de Computadoras");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }


}