import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestAzuquero {
    private Azucarero azucarero;

    @Before
    public void preparar() {
        azucarero = new Azucarero(10);
    }

    @Test
    public void deberiaDevolverVerdaderoSiHaySuficienteAzucarEnElAzuquero() {
        assertTrue(azucarero.hasAzucar(10));
    }

    @Test
    public void deberiaDevolverFalsoPorqueNoHaySuficienteAzucarEnElAzuquero() {
        assertFalse(azucarero.hasAzucar(11));
    }

    @Test
    public void deberiaRestarAzucarAlAzuquero() {
        azucarero.giveAzucar(4);
        assertEquals(6, azucarero.getCantidadDeAzucar());
    }
}
