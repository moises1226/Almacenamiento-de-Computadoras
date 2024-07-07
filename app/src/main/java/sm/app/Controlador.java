package sm.app;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Controlador {


    @FXML
    private Button btn_Table;
    @FXML
    private TableView<Usuarios> tabla;


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
    private Pane panelError;

    @FXML
    private void btn_Enviar(){

        String nombre = nombre_Respo.getText();
        String apellido = apellido_Respo.getText();

//        String curso = this.curso.getText();
//        int nro_Carrito = this.nro_carrito.getValue();
//        int nro_compu = this.nro_compu.getValue();

       if(nombre.isEmpty() || apellido.isEmpty()){

           panelError.setVisible(true);

       }else{

           GuardadoDeDatos(nombre, apellido);
           panelError.setVisible(false);

        }
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
            CargarRegistro_tabla();





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

    @FXML
    private TableColumn<Usuarios, Integer> col1;
    @FXML
    private TableColumn<Usuarios, String> col2;
    @FXML
    private TableColumn<Usuarios, String> col3;

    public void initialize() {
        incializarColumnas();
        CargarRegistro_tabla();
    }

    public void incializarColumnas(){
        col1.setCellValueFactory(new PropertyValueFactory<>("id"));
        col2.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        col3.setCellValueFactory(new PropertyValueFactory<>("apellido"));


    }

    private void CargarRegistro_tabla(){

        ConectorBaseDatos conexion = new ConectorBaseDatos();
        int id = 0;

        try {

            Connection connection = conexion.getConexion();
            String consult = "SELECT nombre, apellido FROM Usuarios";
            PreparedStatement consulta = connection.prepareStatement(consult);
            ResultSet muestraResultado = consulta.executeQuery(consult);

            ObservableList<Usuarios> datos = FXCollections.observableArrayList();

            while(muestraResultado.next()){
                id++;
                String nombre = muestraResultado.getString("nombre");
                String apellido = muestraResultado.getString("apellido");
                datos.add(new Usuarios(id,nombre, apellido));

            }

            tabla.setItems(datos);




        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Error al cargar datos");
        }



    }
















}