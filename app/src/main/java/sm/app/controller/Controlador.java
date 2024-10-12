package sm.app.controller;

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
import sm.app.db.ConectorBaseDatos;
import sm.app.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Controlador {

    @FXML
    private TableView<Usuario> tabla;

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
    private TextField Curso;
    @FXML
    private TextField Dni;
    @FXML
    private TextField nombre_Respo;
    @FXML
    private TextArea CampoDescripcion;
    @FXML
    private TextField codigoBarras;
    @FXML
    private Pane panelError;



    @FXML
    private void btnRegistrar(){

        String dni = Dni.getText();
        String n = nombre_Respo.getText();
        String c = Curso.getText();
        // String CB = codigoBarras.getText();
        

       if(n.isEmpty()  || c.isEmpty() || dni.isEmpty() ){

           panelError.setVisible(true);

       }else{

           int d = Integer.parseInt(dni); 

           AgregarUsuario(n, d, c );
           panelError.setVisible(false);

        }
    }



    private void AgregarUsuario(String nRespo , int dniRespo , String cursoRespo ){


        ConectorBaseDatos conexion = new ConectorBaseDatos();

        
        try {
            

            Connection connection = conexion.getConexion();
            
            String query = "INSERT INTO usuario (Nombre ,  DNI , Curso ) VALUES ( ? , ? , ? ) "; 
            PreparedStatement pQuery = connection.prepareStatement(query);

            pQuery.setString(1 , nRespo);
            pQuery.setInt(2,  dniRespo);
            pQuery.setString(3, cursoRespo);
            

            pQuery.executeUpdate();
            mostrarMensajeExito();


        } catch (Exception e) {
            e.printStackTrace();
        }


    }








   @FXML
   private String VerificacionCodigoBarras(String codigoB) {
    ConectorBaseDatos conexion = new ConectorBaseDatos();
    Set<String> codigosBarras = new HashSet<>(); // Cambiar a HashSet

    HashMap<Integer , String > numeroAndcodigo = new HashMap<>();
    
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = conexion.getConexion();
            

            String query = "SELECT computadora.NroCompu, computadora.CodigoBarras, carrito.NroCarrito " + 
            "FROM carrito " + 
            "INNER JOIN computadora ON carrito.IdCompu = computadora.IdCompu";

            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int nroCompu = resultSet.getInt("NroCompu");
                String codigBarras = resultSet.getString("CodigoBarras");
                int nroCarrito = resultSet.getInt("NroCarrito");
                
                numeroAndcodigo.put(nroCompu, codigBarras);
            }

            // Verificación
            
            for(int n : numeroAndcodigo.keySet()){
                
            }



        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("El codigo de barras que ingeso no existe" + e.getMessage());
        } finally {
            // Asegúrate de cerrar los recursos
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return ; // O podrías retornar un valor booleano

        }
     

   
}



    @FXML
    private Label TextoRegistrado;


    @FXML
    private void mostrarMensajeExito() {
        TextoRegistrado.setVisible(true);
        Timeline tempoAparecerDesaparecer = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> TextoRegistrado.setVisible(false)));
        tempoAparecerDesaparecer.play();
    }


    @FXML
    private TableColumn<Usuario, Integer> col1;
    @FXML
    private TableColumn<Usuario, String> col2;
    @FXML
    private TableColumn<Usuario, String> col3;
    @FXML
    private TableColumn<Usuario, String> col4;
    @FXML
    private TableColumn<Usuario, Long> col5;
    @FXML
    private TableColumn<Usuario, String> col6;
    @FXML
    private TableColumn<Usuario, String> col7;




    // public void initialize() {
    //     incializarColumnas();
    //     CargarRegistro_tabla();



    // }


    public void incializarColumnas(){
        col1.setCellValueFactory(new PropertyValueFactory<>("id"));
        col2.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        col3.setCellValueFactory(new PropertyValueFactory<>("curso"));
        col4.setCellValueFactory(new PropertyValueFactory<>("nro_compu"));
        col5.setCellValueFactory(new PropertyValueFactory<>("nro_Carrito"));
        col6.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        col7.setCellValueFactory(new PropertyValueFactory<>("entrega"));

    }

    // private void CargarRegistro_tabla(){

    //     ConectorBaseDatos conexion = new ConectorBaseDatos();
    //     int id = 0;

    //     try {

    //         Connection connection = conexion.getConexion();
    //         String consult = "SELECT nombre, curso , nro_Compu, descripcion, nro_Carrito, entrega FROM Usuarios";
    //         PreparedStatement consulta = connection.prepareStatement(consult);
    //         ResultSet muestraResultado = consulta.executeQuery(consult);

    //         ObservableList<Usuario> datos = FXCollections.observableArrayList();

    //         while(muestraResultado.next()){
    //             id++;
    //             String nombre = muestraResultado.getString("nombre");
    //             String curso = muestraResultado.getString("curso");
    //             int nro_compu = muestraResultado.getInt("nro_Compu");
    //             String descripcion = muestraResultado.getString("descripcion");
    //             long nro_Carrito = muestraResultado.getLong("nro_Carrito");
    //             String entrega = muestraResultado.getString("entrega");

    //             datos.add(new Usuario(id,nombre, curso, nro_compu, descripcion, nro_Carrito, entrega));


    //         }

    //         tabla.setItems(datos);


    //     }catch (SQLException e){
    //         e.printStackTrace();
    //         System.out.println("Error al mostrar datos");
    //     }

    // }


    @FXML
    private Label fechaHora;

    @FXML
    private void GeneradorFechaHora(){

        LocalDateTime newFH = LocalDateTime.now();

        DateTimeFormatter formateoHF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String FH = newFH.format(formateoHF);

        fechaHora.setText(FH);



    }





    











}