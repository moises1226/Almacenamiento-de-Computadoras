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


    @FXML
    private Spinner<Integer> nro_carrito;
    @FXML
    private Spinner<Integer> nro_compu;
    @FXML
    private TextField curso;

    @FXML
    public void Spinner() {

        SpinnerValueFactory<Integer> valores1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100000, 0);
        SpinnerValueFactory<Integer> valores2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100000, 0);
        nro_carrito.setValueFactory(valores1);
        nro_compu.setValueFactory(valores2);

    }

    @FXML
    private TextField nombre_Respo;
    @FXML
    private TextField apellido_Respo;
    @FXML
    private TextArea campo_Descripcion;

    @FXML
    private Pane panelError;

    @FXML
    private void btn_Enviar(){

        String nombre = nombre_Respo.getText();
        String apellido = apellido_Respo.getText();
        String curso = this.curso.getText();
        long nro_Carrito = this.nro_carrito.getValue();
        int nro_compu = this.nro_compu.getValue();
        String descripcion = campo_Descripcion.getText();

       if(nombre.isEmpty() || apellido.isEmpty() || curso.isEmpty() || nro_compu == 0 || nro_Carrito == 0){

           panelError.setVisible(true);

       }else{

           GuardadoDeDatos(nombre, apellido, curso, nro_compu, nro_Carrito, descripcion);

           panelError.setVisible(false);

        }
    }

    public void GuardadoDeDatos(String nombre, String apellido, String curso, int nro_compu, long nro_Carrito, String descripcion) {

        ConectorBaseDatos conexion = new ConectorBaseDatos();


        try {
            Connection connection = conexion.getConexion();
            String insertDatos = "INSERT INTO Usuarios(nombre, apellido, curso , nro_Compu, descripcion, nro_Carrito) VALUES(? , ?, ?, ?, ?, ?)";
            PreparedStatement ins_dt = connection.prepareStatement(insertDatos);
            ins_dt.setString(1, nombre);
            ins_dt.setString(2, apellido);
            ins_dt.setString(3, curso);
            ins_dt.setInt(4, nro_compu);
            ins_dt.setString(5, descripcion);
            ins_dt.setLong(6, nro_Carrito);

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
    @FXML
    private TableColumn<Usuarios, String> col4;
    @FXML
    private TableColumn<Usuarios, Long> col5;
    @FXML
    private TableColumn<Usuarios, String> col6;
    @FXML
    private TableColumn<Usuarios, String> col7;

    public void initialize() {
        Spinner();
        incializarColumnas();
        CargarRegistro_tabla();
    }

    public void incializarColumnas(){
        col1.setCellValueFactory(new PropertyValueFactory<>("id"));
        col2.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        col3.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        col4.setCellValueFactory(new PropertyValueFactory<>("curso"));
        col5.setCellValueFactory(new PropertyValueFactory<>("nro_compu"));
        col6.setCellValueFactory(new PropertyValueFactory<>("nro_Carrito"));
        col7.setCellValueFactory(new PropertyValueFactory<>("descripcion"));





    }

    private void CargarRegistro_tabla(){

        ConectorBaseDatos conexion = new ConectorBaseDatos();
        int id = 0;

        try {

            Connection connection = conexion.getConexion();
            String consult = "SELECT nombre, apellido, curso , nro_Compu, descripcion, nro_Carrito FROM Usuarios";
            PreparedStatement consulta = connection.prepareStatement(consult);
            ResultSet muestraResultado = consulta.executeQuery(consult);

            ObservableList<Usuarios> datos = FXCollections.observableArrayList();

            while(muestraResultado.next()){
                id++;
                String nombre = muestraResultado.getString("nombre");
                String apellido = muestraResultado.getString("apellido");
                String curso = muestraResultado.getString("curso");
                int nro_compu = muestraResultado.getInt("nro_Compu");
                String descripcion = muestraResultado.getString("descripcion");
                long nro_Carrito = muestraResultado.getLong("nro_Carrito");

                datos.add(new Usuarios(id,nombre, apellido, curso, nro_compu, descripcion, nro_Carrito));


            }

            tabla.setItems(datos);




        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Error al cargar datos");
        }



    }


    @FXML
    public void GenerarNuevaColumna(){


        TableColumn<Usuarios, String> nuevaCol = new TableColumn<>("Nueva Columna");
        col2.getColumns().add(nuevaCol);



    }
















}