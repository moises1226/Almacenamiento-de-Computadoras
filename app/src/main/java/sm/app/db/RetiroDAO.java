package sm.app.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class RetiroDAO {


        private Connection connection;

        // Constructor que recibe la conexión a la base de datos
        public RetiroDAO(Connection connection) {
            this.connection = connection;
        }

        // Método para actualizar la fecha de entrega en la tabla 'retiro'

        public void insertarFechaEntrega(int idRetiro, Timestamp fechaEntrega) {
            String sql = "UPDATE retiro SET FechaEntrega = ? WHERE IdRetiro = ?";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setTimestamp(1, fechaEntrega); // Establece la fecha de entrega
                statement.setInt(2, idRetiro); // Establece el ID del retiro
                statement.executeUpdate(); // Ejecuta la actualización
            } catch (SQLException e) {
                e.printStackTrace(); // Manejo de excepciones
            }
        }
    }



