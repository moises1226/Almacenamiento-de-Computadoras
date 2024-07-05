package sm.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class ConectorBaseDatos {

    private static final String url = "jdbc:mysql://localhost:3306/GuardadoDeBarras";
    private static final String usuario = "root";
    private static final String password = "123456";


    public Connection getConexion(){

        try {
            return DriverManager.getConnection(url, usuario, password);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("No se pudo conectar a la base de datos", e);
        }

    }
}


