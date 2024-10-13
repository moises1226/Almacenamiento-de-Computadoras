package sm.app.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import sm.app.APP;
import sm.app.db.ConectorBaseDatos;
import sm.app.model.Usuario;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Controlador {


    public void initialize() {
        panelError.setVisible(false);
        // incializarColumnas();
        // CargarRegistro_tabla()
    }

    @FXML
    private TableView<Usuario> tabla;

    


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
    private Text infoError;
    @FXML
    private Label tituloError;
    @FXML
    private Text textResponsable;
    @FXML 
    private Text textCurso;
    @FXML
    private Text textCarrito;
    @FXML
    private Text textComputadora;

    @FXML
    private void btnRegistrar() {
        String dni = Dni.getText();
        String n = nombre_Respo.getText();
        String c = Curso.getText();
        String CB = codigoBarras.getText();
    
        if (n.isEmpty() || c.isEmpty() || dni.isEmpty() || CB.isEmpty()) {
            tituloError.setText("!ERROR!");
            infoError.setText("Los datos no fueron registrados correctamente por falta de datos en campos vacíos.");
            panelError.setVisible(true);
        } else {
            if (CB.length() != 16) {
                tituloError.setText("¡ERROR!");
                infoError.setText("El código de barras debe tener 16 caracteres.");
                panelError.setVisible(true);
            } else {
                boolean verificacion = VerificacionCodigoBarras(CB);
    
                if (verificacion == true) {
                    int d = Integer.parseInt(dni);
                    AgregarUsuario(n, d, c);
                    MuestraDatos(n, c, CB);
                    panelError.setVisible(false);
        

                } else {
                    tituloError.setText("!ERROR!");
                    infoError.setText("El código de barras que ingresó no está registrado.");
                    panelError.setVisible(true);
                }
            }
        }
    }
    
    
    private void MuestraDatos(String nombre, String curso, String codBarras) {
        ConectorBaseDatos conexion = new ConectorBaseDatos();
        String codigoBarrasResult = null;
        String nroCarritoResult = null;
        String nroComputadoraResult = null;
        Connection connection = null;
        PreparedStatement pQuery = null;
        ResultSet resultSet = null;
    
        try {
            connection = conexion.getConexion();
    
            String query = "SELECT computadora.CodigoBarras, computadora.NroCompu, carrito.NroCarrito " + 
                           "FROM carrito " + 
                           "INNER JOIN computadora ON carrito.IdCompu = computadora.IdCompu " +
                           "WHERE CodigoBarras = ?";
    
            pQuery = connection.prepareStatement(query);
            pQuery.setString(1, codBarras);
            resultSet = pQuery.executeQuery();
    
            if (resultSet.next()) {
                codigoBarrasResult = resultSet.getString("CodigoBarras");
                nroComputadoraResult = resultSet.getString("NroCompu");
                nroCarritoResult = resultSet.getString("NroCarrito");
    
                textComputadora.setText(nroComputadoraResult);
                textCarrito.setText(nroCarritoResult);
                textResponsable.setText(nombre);
                textCurso.setText(curso);
                nombre_Respo.clear();
                Curso.clear();
                Dni.clear();
                codigoBarras.clear();
            }
    
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (pQuery != null) {
                    pQuery.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    



    private void AgregarUsuario(String nRespo, int dniRespo, String cursoRespo) {
        ConectorBaseDatos conexion = new ConectorBaseDatos();
    
        try {
            Connection connection = conexion.getConexion();
            String query = "INSERT INTO usuario (Nombre, DNI, Curso) VALUES (?, ?, ?)";
            PreparedStatement pQuery = connection.prepareStatement(query);
    
            pQuery.setString(1, nRespo);
            pQuery.setInt(2, dniRespo);
            pQuery.setString(3, cursoRespo);
            
            pQuery.executeUpdate();
            mostrarMensajeExito();
    
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    



    @FXML
    private Boolean VerificacionCodigoBarras(String codigoB) {
        ConectorBaseDatos conexion = new ConectorBaseDatos();
        boolean bandera = false;    
        Connection connection = null;
        PreparedStatement ejecucion = null;
        ResultSet resultado = null;
    
        panelError.setVisible(false);
    
        try {
            connection = conexion.getConexion();
            String query = "SELECT computadora.NroCompu, computadora.CodigoBarras, carrito.NroCarrito " + 
                           "FROM carrito " + 
                           "INNER JOIN computadora ON carrito.IdCompu = computadora.IdCompu";
    
            ejecucion = connection.prepareStatement(query);
            resultado = ejecucion.executeQuery();
    
            while (resultado.next()) {
                String codigBarras = resultado.getString("CodigoBarras");
                if (codigoB.equals(codigBarras)) {
                    bandera = true; // Se encontró el código de barras
                    break;
                }
            }
    
        } catch (SQLException e) {
            tituloError.setText("¡ERROR EN BD!");
            infoError.setText("Error en la conexión o recopilación de datos de la base de datos.");
            panelError.setVisible(true);
            e.printStackTrace();
        } finally {
            try {
                if (resultado != null) resultado.close();
                if (ejecucion != null) ejecucion.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    
        return bandera;
    }
    

    @FXML
    private void AgregarDatosRetiro(){




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


    @FXML
    private Stage ventana ;

    @FXML
    private void MostrarTabla(ActionEvent event) {
        try {

        
            if (ventana == null || !ventana.isShowing()) {
            // Cargar el nuevo archivo FXML
            FXMLLoader loader = new FXMLLoader(APP.class.getResource("TablaVisual.fxml"));
            Parent interfazTabla = loader.load();
            
            // Crear un nuevo Stage
            ventana = new Stage();
            ventana.setTitle("Tabla de usuarios");
            
            // Crear una nueva escena
            Scene Esena = new Scene(interfazTabla , 1182 , 680);
            ventana.setScene(Esena);
            ventana.setResizable(false);
            


            // Mostrar ventana
            ventana.show();
        
            
        
        }else {
           
            ventana.toFront();
        }


        } catch (IOException e) {
            e.printStackTrace();
        }
    
    
    
    
    }
    
    




    











}