public class Vaso {
    private int cantidadVasos;
    private int contenido;

    public Vaso(int cantidadVasos, int contenido) {
        setCantidadVasos(cantidadVasos);
        setContenido(contenido);
    }

    public boolean hasVasos(int cantidadVasos) {
        return cantidadVasos >= 0 && this.cantidadVasos >= cantidadVasos;
    }

    public void giveVasos(int cantidadVasos) {
        if (!hasVasos(cantidadVasos)) {
            throw new IllegalArgumentException("Cantidad de vasos invalida o insuficiente");
        }
        this.cantidadVasos -= cantidadVasos;
    }

    public int getCantidadVasos() {
        return cantidadVasos;
    }

    public void setCantidadVasos(int cantidadVasos) {
        if (cantidadVasos < 0) {
            throw new IllegalArgumentException("La cantidad de vasos no puede ser negativa");
        }
        this.cantidadVasos = cantidadVasos;
    }

    public int getContenido() {
        return contenido;
    }

    public void setContenido(int contenido) {
        if (contenido <= 0) {
            throw new IllegalArgumentException("El contenido debe ser mayor que cero");
        }
        this.contenido = contenido;
    }
}
