package sm.app.model;

public class Computadora {
    private int idCompu;
    private int nroCompu;
    private String codigoBarras;

    public Computadora(int idCompu, int nroCompu, String codigoBarras) {
        this.idCompu = idCompu;
        this.nroCompu = nroCompu;
        this.codigoBarras = codigoBarras;
    }

    // Getters y Setters
    public int getIdCompu() {
        return idCompu;
    }

    public void setIdCompu(int idCompu) {
        this.idCompu = idCompu;
    }

    public int getNroCompu() {
        return nroCompu;
    }

    public void setNroCompu(int nroCompu) {
        this.nroCompu = nroCompu;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }
}
