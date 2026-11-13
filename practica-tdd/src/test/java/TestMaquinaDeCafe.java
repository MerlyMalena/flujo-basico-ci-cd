import org.junit.Test;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertEquals;
import org.junit.Before;

public class TestMaquinaDeCafe {

    private MaquinaDeCafe maquina;
    private Vaso pequeno;
    private Vaso mediano;
    private Vaso grande;
    private Cafetera cafetera;
private Azucarero azucarero;

    @Test
    public void deberiaDevolverUnVasoPequeno() {
        Vaso seleccionado = maquina.getTipoDeVaso("pequeno");

        assertSame(pequeno, seleccionado);
        assertEquals(3, seleccionado.getContenido());
    }

    @Test
    public void deberiaDevolverUnVasoMediano() {
        Vaso seleccionado = maquina.getTipoDeVaso("mediano");

        assertSame(mediano, seleccionado);
        assertEquals(5, seleccionado.getContenido());
    }

    @Before
    public void preparar() {
        pequeno = new Vaso(10, 3);
        mediano = new Vaso(10, 5);

        maquina = new MaquinaDeCafe();
        maquina.setVasosPequeno(pequeno);
        maquina.setVasosMediano(mediano);
        grande = new Vaso(10, 7);
        maquina.setVasosGrande(grande);
        cafetera = new Cafetera(100);
        azucarero = new Azucarero(20);

        maquina.setCafetera(cafetera);
        maquina.setAzucarero(azucarero);
    }

    @Test
    public void deberiaDevolverUnVasoGrande() {
        Vaso seleccionado = maquina.getTipoDeVaso("grande");

        assertSame(grande, seleccionado);
        assertEquals(7, seleccionado.getContenido());
    }

    @Test
    public void deberiaDevolverNoHayVasos() {
        pequeno = new Vaso(0, 3);
        maquina.setVasosPequeno(pequeno);

        assertEquals(
                "No hay Vasos",
                maquina.getVasoDeCafe(pequeno, 1, 2));
    }

    @Test
    public void deberiaDevolverNoHayCafe() {
        Cafetera cafetera = new Cafetera(2);
        maquina.setCafetera(cafetera);

        assertEquals(
                "No hay Cafe",
                maquina.getVasoDeCafe(pequeno, 1, 2));
    }

    @Test
    public void deberiaDevolverNoHayAzucar() {
        maquina.setCafetera(new Cafetera(100));
        maquina.setAzucarero(new Azucarero(1));

        assertEquals(
                "No hay Azucar",
                maquina.getVasoDeCafe(pequeno, 1, 2));
    }

    @Test
public void deberiaRestarCafe() {
    maquina.getVasoDeCafe(pequeno, 1, 2);

    assertEquals(97, cafetera.getCantidadCafe());
}

@Test
public void deberiaRestarVaso() {
    maquina.getVasoDeCafe(pequeno, 1, 2);

    assertEquals(9, pequeno.getCantidadVasos());
}

@Test
public void deberiaRestarAzucar() {
    maquina.getVasoDeCafe(pequeno, 1, 2);

    assertEquals(18, azucarero.getCantidadDeAzucar());
}

@Test
public void deberiaDevolverFelicitaciones() {
    assertEquals(
        "Felicitaciones",
        maquina.getVasoDeCafe(pequeno, 1, 2)
    );
}

}