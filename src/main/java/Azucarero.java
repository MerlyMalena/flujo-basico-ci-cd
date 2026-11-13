public class Azucarero {
    private int cantidadDeAzucar;

    public Azucarero(int cantidadDeAzucar) {
        setCantidadDeAzucar(cantidadDeAzucar);
    }

    public boolean hasAzucar(int cantidadDeAzucar) {
        return cantidadDeAzucar >= 0 && this.cantidadDeAzucar >= cantidadDeAzucar;
    }

    public void giveAzucar(int cantidadDeAzucar) {
        if (!hasAzucar(cantidadDeAzucar)) {
            throw new IllegalArgumentException("Cantidad de azucar invalida o insuficiente");
        }
        this.cantidadDeAzucar -= cantidadDeAzucar;
    }

    public int getCantidadDeAzucar() {
        return cantidadDeAzucar;
    }

    public void setCantidadDeAzucar(int cantidadDeAzucar) {
        if (cantidadDeAzucar < 0) {
            throw new IllegalArgumentException("La cantidad de azucar no puede ser negativa");
        }
        this.cantidadDeAzucar = cantidadDeAzucar;
    }
}
