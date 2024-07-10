package sm.app;

public class Usuarios {

    private int id;
    private String nombre;
    private String curso;
    private int nro_compu;
    private String descripcion;
    private long nro_Carrito;
    private String entrega;

    public Usuarios(int id, String nombre, String curso, int nro_compu, String descripcion, long nro_Carrito, String entrega) {
        this.id = id;
        this.nombre = nombre;
        this.curso = curso;
        this.nro_compu = nro_compu;
        this.descripcion = descripcion;
        this.nro_Carrito = nro_Carrito;
        this.entrega = entrega;
    }

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

    public int getNro_compu() {
        return nro_compu;
    }

    public void setNro_compu(int nro_compu) {
        this.nro_compu = nro_compu;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public long getNro_Carrito() {
        return nro_Carrito;
    }

    public void setNro_Carrito(long nro_Carrito) {
        this.nro_Carrito = nro_Carrito;
    }

    public String getEntrega() {
        return entrega;
    }

    public void setEntrega(String entrega) {
        this.entrega = entrega;
    }
}
