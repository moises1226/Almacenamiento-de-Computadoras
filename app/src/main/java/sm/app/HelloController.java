package sm.app;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HelloController {


    @FXML
    private Button btn_Table;
    @FXML
    private TableView tabla;

    @FXML
    public void Mostrar_tabla(){
        if(tabla.isVisible()){
            tabla.setVisible(false);
        }
        else{
            tabla.setVisible(true);
            panel_Registro.setVisible(false);
        }
    }
    @FXML
    private AnchorPane panel_Registro;
    @FXML
    public void registrar_Compu(){
        if(panel_Registro.isVisible()){
            panel_Registro.setVisible(false);
        }
        else{
            panel_Registro.setVisible(true);
            tabla.setVisible(false);
        }
    }


//    @FXML
//    private Spinner<Integer> nro_carrito;
//
//
//    @FXML
//    public void initialize() {
//        // Configura el Spinner con un rango de valores
//        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100000, 0);
//        nro_carrito.setValueFactory(valueFactory);
//
//
//    }

    @FXML
    private TextField nombre_Respo;
    @FXML
    private TextField apellido_Respo;
    @FXML
    private TextField curso;
    @FXML
    private Spinner<Integer> nro_compu;

    @FXML
    private void btn_Enviar(){

        String nombre = nombre_Respo.getText();
        String apellido = apellido_Respo.getText();

//        String curso = this.curso.getText();
//        int nro_Carrito = this.nro_carrito.getValue();
//        int nro_compu = this.nro_compu.getValue();

        GuardadoDeDatos(nombre, apellido);


    }

    public void GuardadoDeDatos(String nombre, String apellido) {

        ConectorBaseDatos conexion = new ConectorBaseDatos();

        try {

            Connection connection = conexion.getConexion();
            String insertDatos = "INSERT INTO Usuarios(nombre, apellido) VALUES(? , ?)";
            PreparedStatement ins_dt = connection.prepareStatement(insertDatos);
            ins_dt.setString(1, nombre);
            ins_dt.setString(2, apellido);

            ins_dt.executeUpdate();
            mostrarMensajeExito();
            limpiarDatos();




        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Error al guardar datos");
        }

    }

    @FXML
    private Label TextoEnviado;

    private void mostrarMensajeExito() {
        TextoEnviado.setVisible(true);
        Timeline tempoAparecerDesaparecer = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> TextoEnviado.setVisible(false)));
        tempoAparecerDesaparecer.play();
    }

    private void limpiarDatos(){

        nombre_Respo.setText("");
        apellido_Respo.setText("");

    }


    private void mostrarRegistro(){







    }








}