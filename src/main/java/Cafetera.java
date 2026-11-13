public class Cafetera {
    private int cantidadCafe;

    public Cafetera(int cantidadCafe) {
        setCantidadCafe(cantidadCafe);
    }

    public boolean hasCafe(int cantidadCafe) {
        return cantidadCafe >= 0 && this.cantidadCafe >= cantidadCafe;
    }

    public void giveCafe(int cantidadCafe) {
        if (!hasCafe(cantidadCafe)) {
            throw new IllegalArgumentException("Cantidad de cafe invalida o insuficiente");
        }
        this.cantidadCafe -= cantidadCafe;
    }

    public int getCantidadCafe() {
        return cantidadCafe;
    }

    public void setCantidadCafe(int cantidadCafe) {
        if (cantidadCafe < 0) {
            throw new IllegalArgumentException("La cantidad de cafe no puede ser negativa");
        }
        this.cantidadCafe = cantidadCafe;
    }
}
