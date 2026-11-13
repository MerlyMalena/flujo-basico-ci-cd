public class Vaso {
    private int cantidadVasos;

    public Vaso(int cantidadVasos, int contenido) {
        this.cantidadVasos = cantidadVasos;
        this.contenido = contenido;
    }

    public boolean hasVasos(int cantidad) {
        return cantidadVasos >= cantidad;
    }

    public void giveVasos(int cantidad) {
        cantidadVasos -= cantidad;
    }

    public int getCantidadVasos() {
        return cantidadVasos;
    }

    public int getContenido() {
        return contenido;
    }

    private int contenido;

}