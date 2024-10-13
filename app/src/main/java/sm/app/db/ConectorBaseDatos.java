package sm.app.db;

import java.sql.Connection;
import java.sql.DriverManager;
import sm.app.controller.Controlador.*;



public final class ConectorBaseDatos {

    private static final String url = "jdbc:mysql://localhost:3306/GuardadoDeBarras";
    private static final String usuario = "root";
    private static final String password = "";


    public Connection getConexion(){

        try {
            return DriverManager.getConnection(url, usuario, password);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("No se pudo conectar a la base de datos", e);
            tituloError.setText("!ERROR!");
            infoError.setText("El código de barras que ingresó no está registrado.");
            panelError.setVisible(true);
        }

    }
}


