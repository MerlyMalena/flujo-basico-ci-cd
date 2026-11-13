import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class TestAzuquero {
    @Test
    public void deberiaDevolverVerdaderoSiHaySuficienteAzucarEnElAzuquero() {
        

        assertTrue(azucarero.hasAzucar(10));
    }

    @Test
    public void deberiaDevolverFalsoPorqueNoHaySuficienteAzucarEnElAzuquero() {
       

        assertFalse(azucarero.hasAzucar(11));
    }

    private Azucarero azucarero;

    @Before
    public void preparar() {
        azucarero = new Azucarero(10);
    }

    @Test
    public void deberiaRestarAzucarAlAzuquero() {
        azucarero.giveAzucar(4);

        assertEquals(6, azucarero.getCantidadDeAzucar());
    }
}
