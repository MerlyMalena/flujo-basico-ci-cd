public class Cafetera {
    private int cantidadCafe;

    public Cafetera(int cantidadCafe) {
        this.cantidadCafe = cantidadCafe;
    }

    public boolean hasCafe(int cantidad) {
        return cantidadCafe >= cantidad;
    }
    
   public void giveCafe(int cantidad) {
    cantidadCafe -= cantidad;
}

    public int getCantidadCafe() {
        return cantidadCafe;
    }
}