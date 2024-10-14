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
import sm.app.model.Retiro;


import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Controlador {


    @FXML
    private TextField Curso;
    @FXML
    private TextField Dni;
    @FXML
    private TextField nombre_Respo;
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
    private TextArea CampoDescripcion;

    @FXML
    private void btnRegistrar() {
        String dni = Dni.getText();
        String n = nombre_Respo.getText();
        String c = Curso.getText();
        String CB = codigoBarras.getText();
        String descripcion = CampoDescripcion.getText();
    
        if (n.isEmpty() || c.isEmpty() || CB.isEmpty() || dni.isEmpty()) {
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

                    if(dni.length() != 8){
                        
                        tituloError.setText("¡ERROR!");
                        infoError.setText("El dni debe tener 8 caracteres");
                        panelError.setVisible(true);
    
                    }else{

                        int d = Integer.parseInt(dni);
                   
                        AgregarUsuarioEinsertarRetiro(n, d, c, CB , descripcion);
                        MuestraDatos(n, c, CB);
                        panelError.setVisible(false);
                    }

                
        

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
    


    private void AgregarUsuarioEinsertarRetiro(String nRespo, int dniRespo, String cursoRespo, String codigoBarras, String descripcion) {
        ConectorBaseDatos conexion = new ConectorBaseDatos();
    
        try {
            Connection connection = conexion.getConexion();
            String query = "INSERT INTO usuario (Nombre, DNI, Curso) VALUES (?, ?, ?)";
            
            // Preparar el statement con la opción para obtener las claves generadas
            PreparedStatement pQuery = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pQuery.setString(1, nRespo);
            pQuery.setInt(2, dniRespo);
            pQuery.setString(3, cursoRespo);
            
            // Ejecutar la inserción
            pQuery.executeUpdate();
            
            // Obtener la ID generada
            ResultSet generatedKeys = pQuery.getGeneratedKeys();
            int idUsuarioGenerado = 0;
            if (generatedKeys.next()) {
                idUsuarioGenerado = generatedKeys.getInt(1); // Primera columna
            }
    
            // Traer ID del carrito usando el código de barras
            String query2 = "SELECT computadora.IdCompu, carrito.IdCarrito " +
                            "FROM computadora " +
                            "INNER JOIN carrito ON carrito.IdCompu = computadora.IdCompu " +
                            "WHERE computadora.CodigoBarras = ?";

            
            PreparedStatement pQuery2 = connection.prepareStatement(query2);
            pQuery2.setString(1, codigoBarras);
            
            ResultSet resultSetCarrito = pQuery2.executeQuery();
            int idCarrito = 0;
            if (resultSetCarrito.next()) {
                idCarrito = resultSetCarrito.getInt("IdCarrito");
            } else {
                System.out.println("No se encontró un carrito para el código de barras proporcionado.");
            }
    
            // Insertar en la tabla retiro
            if (idUsuarioGenerado > 0 && idCarrito > 0) {
                String queryRetiro = "INSERT INTO retiro (IdUser, IdCarrito, FechaRetiro, Descripcion) VALUES (?, ?, NOW(), ?)";
                PreparedStatement pQueryRetiro = connection.prepareStatement(queryRetiro);
                pQueryRetiro.setInt(1, idUsuarioGenerado);
                pQueryRetiro.setInt(2, idCarrito);
                pQueryRetiro.setString(3, descripcion);
                pQueryRetiro.executeUpdate();
        
            } else {
                System.out.println("No se puede registrar el retiro. Verifique las IDs.");
            }
    
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
    private Label TextoRegistrado;
    @FXML
    private void mostrarMensajeExito() {
        TextoRegistrado.setVisible(true);
        Timeline tempoAparecerDesaparecer = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> TextoRegistrado.setVisible(false)));
        tempoAparecerDesaparecer.play();
    }

  



    @FXML
    private TableView<Retiro> tabla;
    
    @FXML
    private TableColumn<Retiro, Integer> col1; // ID
    @FXML
    private TableColumn<Retiro, String> col2; // Nombre
    @FXML
    private TableColumn<Retiro , Integer> col3 ;
    @FXML
    private TableColumn<Retiro, String> col4; // Curso
    @FXML
    private TableColumn<Retiro, Integer> col5; // NroCompu
    @FXML
    private TableColumn<Retiro, Long> col6; // NroCarrito
    @FXML
    private TableColumn<Retiro, String> col7; // Descripcion
    @FXML
    private TableColumn<Retiro, Date> col8; // FechaRetiro
    

    public void incializarColumnas() {
        col1.setCellValueFactory(new PropertyValueFactory<>("id"));
        col2.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        col3.setCellValueFactory(new PropertyValueFactory<>("curso"));
        col4.setCellValueFactory(new PropertyValueFactory<>("nroCompu"));
        col5.setCellValueFactory(new PropertyValueFactory<>("nroCarrito"));
        col6.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        col7.setCellValueFactory(new PropertyValueFactory<>("fechaRetiro"));
    }
    
    private void CargarRegistro_tabla() {
        ConectorBaseDatos conexion = new ConectorBaseDatos();
        int id = 0;
    
        try {
            Connection connection = conexion.getConexion();
            String consult = "SELECT usuario.Nombre, usuario.Curso, usuario.DNI, computadora.NroCompu, carrito.NroCarrito, retiro.Descripcion, retiro.FechaRetiro " +
                             "FROM retiro " +
                             "INNER JOIN usuario ON retiro.IdUser = usuario.IdUsuario " +
                             "INNER JOIN carrito ON retiro.IdCarrito = carrito.IdCarrito " +
                             "INNER JOIN computadora ON carrito.IdCompu = computadora.IdCompu";
    
            PreparedStatement consulta = connection.prepareStatement(consult);
            ResultSet muestraResultado = consulta.executeQuery();
    
            ObservableList<Retiro> datos = FXCollections.observableArrayList();
    
            while (muestraResultado.next()) {
                id++;
                String nombre = muestraResultado.getString("Nombre");
                int dni = muestraResultado.getInt("DNI");
                String curso = muestraResultado.getString("Curso");
                int nroCompu = muestraResultado.getInt("NroCompu");
                String descripcion = muestraResultado.getString("Descripcion");
                long nroCarrito = muestraResultado.getLong("NroCarrito");
                Date fechaRetiro = muestraResultado.getDate("FechaRetiro");
    
                datos.add(new Retiro(id, nombre, dni, curso, nroCompu, descripcion, nroCarrito, fechaRetiro));
            }
    
            tabla.setItems(datos);
    
        } catch (SQLException e) {
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
    private Stage ventana ;

    @FXML
    private void MostrarTabla(ActionEvent event) {
        try {

        
            if (ventana == null || !ventana.isShowing()) {
            // Cargar el nuevo archivo FXML
            FXMLLoader loader = new FXMLLoader(APP.class.getResource("TablaVisual.fxml"));
            Parent interfazTabla = loader.load();
            Controlador controlador = loader.getController();
            
            controlador.incializarColumnas(); // Inicializar columnas
            controlador.CargarRegistro_tabla(); 
            
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