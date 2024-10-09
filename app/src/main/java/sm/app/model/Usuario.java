package sm.app.model;
public class Usuario {
    private int idUsuario;
    private String nombre;
    private String dni;
    private String curso;

    public Usuario(int idUsuario, String nombre, String dni, String curso) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.dni = dni;
        this.curso = curso;
    }

    // Getters y Setters
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }
}
