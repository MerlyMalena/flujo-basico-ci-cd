import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import org.junit.Before;

public class TestVaso {
    @Test
    public void deberiaDevolverVerdaderoSiExistenVasos() {

        assertTrue(vaso.hasVasos(10));
    }

    @Test
    public void deberiaDevolverFalsoSiNoExistenvasos() {

        assertFalse(vaso.hasVasos(11));
    }

    private Vaso vaso;

    @Before
    public void preparar() {
        vaso = new Vaso(10, 3);
    }

    @Test
    public void deberiaRestarCantidadDeVaso() {
        vaso.giveVasos(3);

        assertEquals(7, vaso.getCantidadVasos());
    }
}