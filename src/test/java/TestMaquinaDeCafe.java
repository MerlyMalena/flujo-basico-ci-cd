import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestMaquinaDeCafe {
    private MaquinaDeCafe maquina;
    private Vaso pequeno;
    private Vaso mediano;
    private Vaso grande;
    private Cafetera cafetera;
    private Azucarero azucarero;

    @Before
    public void preparar() {
        pequeno = new Vaso(10, 3);
        mediano = new Vaso(10, 5);
        grande = new Vaso(10, 7);
        cafetera = new Cafetera(100);
        azucarero = new Azucarero(20);
        maquina = new MaquinaDeCafe();
        maquina.setVasosPequeno(pequeno);
        maquina.setVasosMediano(mediano);
        maquina.setVasosGrande(grande);
        maquina.setCafetera(cafetera);
        maquina.setAzucarero(azucarero);
    }

    @Test
    public void deberiaDevolverUnVasoPequeno() {
        assertSame(pequeno, maquina.getTipoDeVaso("pequeno"));
        assertEquals(3, maquina.getTipoDeVaso("pequeno").getContenido());
    }

    @Test
    public void deberiaDevolverUnVasoMediano() {
        assertSame(mediano, maquina.getTipoDeVaso("mediano"));
        assertEquals(5, maquina.getTipoDeVaso("mediano").getContenido());
    }

    @Test
    public void deberiaDevolverUnVasoGrande() {
        assertSame(grande, maquina.getTipoDeVaso("grande"));
        assertEquals(7, maquina.getTipoDeVaso("grande").getContenido());
    }

    @Test
    public void deberiaDevolverNoHayVasos() {
        pequeno.setCantidadVasos(0);
        assertEquals("No hay Vasos", maquina.getVasoDeCafe(pequeno, 1, 2));
        comprobarInventario(0, 100, 20);
    }

    @Test
    public void deberiaDevolverNoHayCafe() {
        cafetera.setCantidadCafe(2);
        assertEquals("No hay Cafe", maquina.getVasoDeCafe(pequeno, 1, 2));
        comprobarInventario(10, 2, 20);
    }

    @Test
    public void deberiaDevolverNoHayAzucar() {
        azucarero.setCantidadDeAzucar(1);
        assertEquals("No hay Azucar", maquina.getVasoDeCafe(pequeno, 1, 2));
        comprobarInventario(10, 100, 1);
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
        assertEquals("Felicitaciones", maquina.getVasoDeCafe(pequeno, 1, 2));
    }

    @Test
    public void deberiaServirVariosVasosConAzucarTotalDelPedido() {
        assertEquals("Felicitaciones", maquina.getVasoDeCafe(mediano, 3, 4));
        assertEquals(7, mediano.getCantidadVasos());
        assertEquals(85, cafetera.getCantidadCafe());
        assertEquals(16, azucarero.getCantidadDeAzucar());
        assertEquals(10, pequeno.getCantidadVasos());
        assertEquals(10, grande.getCantidadVasos());
    }

    @Test
    public void deberiaValidarCafeParaTodosLosVasos() {
        cafetera.setCantidadCafe(5);
        assertEquals("No hay Cafe", maquina.getVasoDeCafe(pequeno, 2, 2));
        comprobarInventario(10, 5, 20);
    }

    @Test
    public void deberiaPermitirCafeSinAzucar() {
        azucarero.setCantidadDeAzucar(0);
        assertEquals("Felicitaciones", maquina.getVasoDeCafe(pequeno, 1, 0));
        comprobarInventario(9, 97, 0);
    }

    @Test
    public void deberiaConsumirExistenciasExactas() {
        pequeno.setCantidadVasos(2);
        cafetera.setCantidadCafe(6);
        azucarero.setCantidadDeAzucar(4);
        assertEquals("Felicitaciones", maquina.getVasoDeCafe(pequeno, 2, 4));
        comprobarInventario(0, 0, 0);
    }

    @Test
    public void deberiaPriorizarVasosLuegoCafeLuegoAzucar() {
        pequeno.setCantidadVasos(0);
        cafetera.setCantidadCafe(0);
        azucarero.setCantidadDeAzucar(0);
        assertEquals("No hay Vasos", maquina.getVasoDeCafe(pequeno, 1, 1));
        pequeno.setCantidadVasos(1);
        assertEquals("No hay Cafe", maquina.getVasoDeCafe(pequeno, 1, 1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void deberiaRechazarTipoDesconocido() {
        maquina.getTipoDeVaso("enorme");
    }

    @Test
    public void deberiaRechazarPedidoSinVasosSinAlterarInventario() {
        assertThrows(IllegalArgumentException.class, () -> maquina.getVasoDeCafe(pequeno, 0, 2));
        comprobarInventario(10, 100, 20);
    }

    @Test
    public void deberiaRechazarAzucarNegativaSinAlterarInventario() {
        assertThrows(IllegalArgumentException.class, () -> maquina.getVasoDeCafe(pequeno, 1, -1));
        comprobarInventario(10, 100, 20);
    }

    private void comprobarInventario(int vasos, int cafe, int azucar) {
        assertEquals(vasos, pequeno.getCantidadVasos());
        assertEquals(cafe, cafetera.getCantidadCafe());
        assertEquals(azucar, azucarero.getCantidadDeAzucar());
    }
}
