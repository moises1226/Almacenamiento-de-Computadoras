package sm.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


import java.io.IOException;

public class APP extends Application {
    @Override
    public void start(Stage stage) throws IOException {

  

        FXMLLoader fxmlLoader = new FXMLLoader(APP.class.getResource("PanelPrincipal.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1182, 680);
        Image icon = new Image(APP.class.getResourceAsStream("icono.png")); // Reemplaza "icono.png" por el nombre de tu archivo
        stage.getIcons().add(icon); // Agrega el ícono al Stage
        stage.setTitle("Guardado de Computadoras");
        stage.setScene(scene);
        stage.setResizable(false);


        stage.show();




}

    public static void main(String[] args) {
        Application.launch();
    }


}