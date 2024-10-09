package sm.app.model;
import java.util.Date;

public class Retiro {
    private int idRetiro;
    private int idUser;
    private int idCarrito;
    private Date fecha;
    private String descripcion;

    public Retiro(int idRetiro, int idUser, int idCarrito, Date fecha, String descripcion) {
        this.idRetiro = idRetiro;
        this.idUser = idUser;
        this.idCarrito = idCarrito;
        this.fecha = fecha;
        this.descripcion = descripcion;
    }

    // Getters y Setters
    public int getIdRetiro() {
        return idRetiro;
    }

    public void setIdRetiro(int idRetiro) {
        this.idRetiro = idRetiro;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdCarrito() {
        return idCarrito;
    }

    public void setIdCarrito(int idCarrito) {
        this.idCarrito = idCarrito;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
