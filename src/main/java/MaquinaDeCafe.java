import java.util.Objects;

public class MaquinaDeCafe {
    private Cafetera cafetera = new Cafetera(0);
    private Vaso vasosPequeno = new Vaso(0, 3);
    private Vaso vasosMediano = new Vaso(0, 5);
    private Vaso vasosGrande = new Vaso(0, 7);
    private Azucarero azucarero = new Azucarero(0);

    public Vaso getTipoDeVaso(String tipoDeVaso) {
        if (tipoDeVaso == null) {
            throw new IllegalArgumentException("Debe indicar un tipo de vaso");
        }
        switch (tipoDeVaso) {
            case "pequeno":
                return vasosPequeno;
            case "mediano":
                return vasosMediano;
            case "grande":
                return vasosGrande;
            default:
                throw new IllegalArgumentException("Tipo de vaso desconocido: " + tipoDeVaso);
        }
    }

    /** cantidadAzucar representa el total del pedido, no la cantidad por vaso. */
    public String getVasoDeCafe(Vaso vaso, int cantidadVasos, int cantidadAzucar) {
        if (vaso == null || cantidadVasos <= 0 || cantidadAzucar < 0) {
            throw new IllegalArgumentException("Indique un vaso, una cantidad positiva y azucar no negativa");
        }
        if (!vaso.hasVasos(cantidadVasos)) {
            return "No hay Vasos";
        }

        long cafeNecesario = (long) vaso.getContenido() * cantidadVasos;
        if (cafeNecesario > Integer.MAX_VALUE || !cafetera.hasCafe((int) cafeNecesario)) {
            return "No hay Cafe";
        }
        if (!azucarero.hasAzucar(cantidadAzucar)) {
            return "No hay Azucar";
        }

        // Descontar solamente despues de validar todos los insumos.
        vaso.giveVasos(cantidadVasos);
        cafetera.giveCafe((int) cafeNecesario);
        azucarero.giveAzucar(cantidadAzucar);
        return "Felicitaciones";
    }

    public Cafetera getCafetera() {
        return cafetera;
    }

    public void setCafetera(Cafetera cafetera) {
        this.cafetera = Objects.requireNonNull(cafetera, "cafetera");
    }

    public Vaso getVasosPequeno() {
        return vasosPequeno;
    }

    public void setVasosPequeno(Vaso vasosPequeno) {
        this.vasosPequeno = Objects.requireNonNull(vasosPequeno, "vasosPequeno");
    }

    public Vaso getVasosMediano() {
        return vasosMediano;
    }

    public void setVasosMediano(Vaso vasosMediano) {
        this.vasosMediano = Objects.requireNonNull(vasosMediano, "vasosMediano");
    }

    public Vaso getVasosGrande() {
        return vasosGrande;
    }

    public void setVasosGrande(Vaso vasosGrande) {
        this.vasosGrande = Objects.requireNonNull(vasosGrande, "vasosGrande");
    }

    public Azucarero getAzucarero() {
        return azucarero;
    }

    public void setAzucarero(Azucarero azucarero) {
        this.azucarero = Objects.requireNonNull(azucarero, "azucarero");
    }
}
