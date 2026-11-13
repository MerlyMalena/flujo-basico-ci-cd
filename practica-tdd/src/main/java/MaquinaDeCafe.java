public class MaquinaDeCafe {
    private Vaso vasosPequeno;
    private Vaso vasosMediano;
    private Vaso vasosGrande;
    private Cafetera cafetera;
    private Azucarero azucarero;

    public void setVasosPequeno(Vaso vaso) {
        vasosPequeno = vaso;
    }

    public Vaso getTipoDeVaso(String tipo) {
    if ("mediano".equals(tipo)) {
        return vasosMediano;
    }

    if ("grande".equals(tipo)) {
        return vasosGrande;
    }

    return vasosPequeno;
}

    public void setVasosMediano(Vaso vaso) {
    vasosMediano = vaso;
    }

   public void setVasosGrande(Vaso vaso) {
    vasosGrande = vaso;
}

public String getVasoDeCafe(
        Vaso vaso, int cantidadVasos, int cantidadAzucar) {

    if (!vaso.hasVasos(cantidadVasos)) {
        return "No hay Vasos";
    }

    int cafeNecesario = vaso.getContenido() * cantidadVasos;

    if (!cafetera.hasCafe(cafeNecesario)) {
        return "No hay Cafe";
    }
    if (!azucarero.hasAzucar(cantidadAzucar)) {
        return "No hay Azucar";
    }
    vaso.giveVasos(cantidadVasos);
    cafetera.giveCafe(cafeNecesario);
    azucarero.giveAzucar(cantidadAzucar);

    return "Felicitaciones";
}

public void setCafetera(Cafetera cafetera) {
    this.cafetera = cafetera;
}

public void setAzucarero(Azucarero azucarero) {
    this.azucarero = azucarero;
}
}

