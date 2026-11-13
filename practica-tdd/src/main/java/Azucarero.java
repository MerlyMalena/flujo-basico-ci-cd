public class Azucarero {
    private int cantidadDeAzucar;

    public Azucarero(int cantidadDeAzucar) {
        this.cantidadDeAzucar = cantidadDeAzucar;
    }

    public boolean hasAzucar(int cantidad) {
        return cantidadDeAzucar >= cantidad;
    }

    public void giveAzucar(int cantidad) {
        cantidadDeAzucar -= cantidad;
    }
    
    public int getCantidadDeAzucar() {
        return cantidadDeAzucar;
    }
}