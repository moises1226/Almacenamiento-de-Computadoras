package sm.app.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.util.Duration;

public class MetodosBotones {



    private void mostrarMensajeExito(TextField TextoEnviado ) {
        TextoEnviado.setVisible(true);
        Timeline tempoAparecerDesaparecer = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> TextoEnviado.setVisible(false)));
        tempoAparecerDesaparecer.play();
    }

    private void limpiarDatos(Spinner<Integer> nro_compu){
        nro_compu.getValueFactory().setValue(0);
    }


}
