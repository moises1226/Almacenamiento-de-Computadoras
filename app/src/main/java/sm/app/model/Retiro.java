package sm.app.model;

import java.util.Date;

public class Retiro {
    private int id;
    private String nombre;
    private String curso;
    private int nroCompu;
    private String descripcion;
    private long nroCarrito;
    private Date fechaRetiro;

    public Retiro(int id, String nombre, String curso, int nroCompu, String descripcion, long nroCarrito, Date fechaRetiro) {
        this.id = id;
        this.nombre = nombre;
        this.curso = curso;
        this.nroCompu = nroCompu;
        this.descripcion = descripcion;
        this.nroCarrito = nroCarrito;
        this.fechaRetiro = fechaRetiro;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public int getNroCompu() {
        return nroCompu;
    }

    public void setNroCompu(int nroCompu) {
        this.nroCompu = nroCompu;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public long getNroCarrito() {
        return nroCarrito;
    }

    public void setNroCarrito(long nroCarrito) {
        this.nroCarrito = nroCarrito;
    }

    public Date getFechaRetiro() {
        return fechaRetiro;
    }

    public void setFechaRetiro(Date fechaRetiro) {
        this.fechaRetiro = fechaRetiro;
    }
}
