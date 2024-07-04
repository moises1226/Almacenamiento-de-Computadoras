package sm.app;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    private Button btn_Table;
    @FXML
    private TableView tabla;

    @FXML
    public void Mostrar_tabla(){

        if(tabla.isVisible()){

            tabla.setVisible(false);
        }else{

            tabla.setVisible(true);

        }


    }


    @FXML
    private Spinner<Integer> nro_carrito;


    @FXML
    public void initialize() {
        // Configura el Spinner con un rango de valores
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100000, 0);
        nro_carrito.setValueFactory(valueFactory);


    }

    @FXML
    private TextField nombre_Respo;
    private TextField curso;

    @FXML
    private Spinner<Integer> nro_compu;

    @FXML
    private void btn_Enviar(){

        String nombre = nombre_Respo.getText();
        String curso = this.curso.getText();
        int nro_Carrito = this.nro_carrito.getValue();
        int nro_compu = this.nro_compu.getValue();

        GuardadoDeDatos(nombre, curso, nro_Carrito, nro_compu);


    }


    @FXML
    public void GuardadoDeDatos(String nombre, String curso, int nro_Carrito, int nro_compu ) {

        ConectorBaseDatos conexion = new ConectorBaseDatos();

        try {





        }catch (Exception e){}

    }







}