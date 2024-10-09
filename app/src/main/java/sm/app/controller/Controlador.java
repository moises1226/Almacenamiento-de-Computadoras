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
import javafx.scene.text.Text;
import javafx.util.Duration;
import sm.app.db.ConectorBaseDatos;
import sm.app.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
    private TextField curso;
    @FXML
    private TextField Dni;
    @FXML
    private TextField nombre_Respo;
    @FXML
    private TextArea CampoDescripcion;
    @FXML
    private Pane panelError;
    @FXML
    private TextField
     codigoBarras;
    @FXML
    private Pane barrasError; 

     @FXML
     private void btn_Registrar() {
         String dni = Dni.getText();
         String n = nombre_Respo.getText();
         String c = curso.getText();
         String CB = codigoBarras.getText();
         
         // Verificar si los campos están vacíos
         if (n.isEmpty() || c.isEmpty() || dni.isEmpty() || CB.isEmpty()) {
             panelError.setVisible(true);
             return; // Salir del método si hay campos vacíos
         }
     
         // Verificar el código de barras
         // Verificar el código de barras
        String verificacion = VerificacionCodigoBarras(CB);
        if (verificacion.isEmpty() || dni.length() > 8) {
        barrasError.setVisible(true);
        return; // Salir del método
        }

        // Si el código de barras es válido, mostrar los resultados
        ResultadoPanel(verificacion, dni);

         // Convertir DNI a entero y agregar usuario si todo es válido
         AgregarUsuario(n, dni, c, verificacion); // Usar el código de barras verificado
         panelError.setVisible(false);
     }
     



    private void AgregarUsuario(String nRespo , String dniRespo , String cursoRespo , String codigoBarras){


        ConectorBaseDatos conexion = new ConectorBaseDatos();

        
        try {
            

            Connection connection = conexion.getConexion();
            
            String query = "INSERT INTO usuario (Nombre ,  DNI , Curso ) VALUES ( ? , ? , ? ) "; 
            PreparedStatement pQuery = connection.prepareStatement(query);

            pQuery.setString(1 , nRespo);
            pQuery.setString(2,  dniRespo);
            pQuery.setString(3, cursoRespo);


            pQuery.executeUpdate();
            mostrarMensajeExito();


        } catch (Exception e) {
            // TODO: handle exception
        }


    }



  
private String VerificacionCodigoBarras(String codigoB) {
    ConectorBaseDatos conexion = new ConectorBaseDatos();
    Set<String> codigosBarras = new HashSet<>();
    String dato = "";

    if (codigoB.length() == 16) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = conexion.getConexion();
            String query = "SELECT CodigoBarras FROM computadora";
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String codBarras = resultSet.getString("CodigoBarras");
                codigosBarras.add(codBarras);
            }

            // Verificación
            if (codigosBarras.contains(codigoB)) {
                dato = codigoB; // Retornar el código de barras si es válido
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al verificar el código de barras: " + e.getMessage());
        } finally {
            // Asegúrate de cerrar los recursos
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    } else {
        System.out.println("El código excede el tamaño requerido.");
    }

    return dato; // Retorna el código de barras si es válido o una cadena vacía si no lo es
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




    public void initialize() {
        incializarColumnas();
        CargarRegistro_tabla();



    }


    public void incializarColumnas(){
        col1.setCellValueFactory(new PropertyValueFactory<>("id"));
        col2.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        col3.setCellValueFactory(new PropertyValueFactory<>("curso"));
        col4.setCellValueFactory(new PropertyValueFactory<>("nro_compu"));
        col5.setCellValueFactory(new PropertyValueFactory<>("nro_Carrito"));
        col6.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        col7.setCellValueFactory(new PropertyValueFactory<>("entrega"));

    }

    private void CargarRegistro_tabla(){

        ConectorBaseDatos conexion = new ConectorBaseDatos();
        int id = 0;

        try {

            Connection connection = conexion.getConexion();
            String consult = "SELECT nombre, curso , nro_Compu, descripcion, nro_Carrito, entrega FROM Usuarios";
            PreparedStatement consulta = connection.prepareStatement(consult);
            ResultSet muestraResultado = consulta.executeQuery(consult);

            ObservableList<Usuario> datos = FXCollections.observableArrayList();

            while(muestraResultado.next()){
                id++;
                String nombre = muestraResultado.getString("nombre");
                String curso = muestraResultado.getString("curso");
                int nro_compu = muestraResultado.getInt("nro_Compu");
                String descripcion = muestraResultado.getString("descripcion");
                long nro_Carrito = muestraResultado.getLong("nro_Carrito");
                String entrega = muestraResultado.getString("entrega");

                datos.add(new Usuario(id,nombre, curso, nro_compu, descripcion, nro_Carrito, entrega));


            }

            tabla.setItems(datos);


        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Error al mostrar datos");
        }

    }


    @FXML
    private Label fechaHora;

    @FXML
    private void GeneradorFechaHora(){

        LocalDateTime newFH = LocalDateTime.now();

        DateTimeFormatter formateoHF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String FH = newFH.format(formateoHF);

        fechaHora.setText(FH);



    }




    @FXML
    private Text textResponsable;

    @FXML
    private Text textCurso;
    @FXML
    private Text textCarrito;
    @FXML
    private Text textComputadora;




    private void ResultadoPanel(String codigoB, String dni) {
        ConectorBaseDatos conexion = new ConectorBaseDatos();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
    
        try {
            connection = conexion.getConexion();
            // Consulta para obtener los datos del usuario y el carrito
            String query = "SELECT u.Nombre, u.DNI, u.Curso, c.NroCompu, ca.NroCarrito " +
                           "FROM usuario u " +
                           "JOIN carrito ca ON ca.IdCompu = (SELECT IdCompu FROM computadora WHERE CodigoBarras = ?) " +
                           "WHERE u.DNI = ?";
    
            statement = connection.prepareStatement(query);
            statement.setString(1, codigoB);
            statement.setString(2, dni); // Usamos el DNI directamente del TextField
    
            resultSet = statement.executeQuery();
    
            if (resultSet.next()) {
                // Obtener los datos y mostrarlos en la interfaz
                String nombre = resultSet.getString("Nombre");
                String curso = resultSet.getString("Curso");
                int numeroComputadora = resultSet.getInt("NroCompu");
                int numeroCarrito = resultSet.getInt("NroCarrito");
                
                //conversion
                String nCompu = Integer.toString(numeroComputadora);
                String nCarrito = Integer.toString(numeroCarrito);
                // Aquí debes tener etiquetas o campos en tu interfaz para mostrar estos datos
                textResponsable.setText(nombre);
                textCurso.setText(curso);
                textCarrito.setText(nCarrito);
                textComputadora.setText(nCompu);
            } else {
                // Manejo del caso cuando no se encuentra el usuario
                System.out.println("error");
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al obtener resultados: " + e.getMessage());
        } finally {
            // Asegúrate de cerrar los recursos
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    








}