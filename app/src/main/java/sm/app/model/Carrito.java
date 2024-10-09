package sm.app.model;

public class Carrito {
    private int idCarrito;
    private int nroCarrito;
    private int idCompu;

    public Carrito(int idCarrito, int nroCarrito, int idCompu) {
        this.idCarrito = idCarrito;
        this.nroCarrito = nroCarrito;
        this.idCompu = idCompu;
    }

    // Getters y Setters
    public int getIdCarrito() {
        return idCarrito;
    }

    public void setIdCarrito(int idCarrito) {
        this.idCarrito = idCarrito;
    }

    public int getNroCarrito() {
        return nroCarrito;
    }

    public void setNroCarrito(int nroCarrito) {
        this.nroCarrito = nroCarrito;
    }

    public int getIdCompu() {
        return idCompu;
    }

    public void setIdCompu(int idCompu) {
        this.idCompu = idCompu;
    }
}
