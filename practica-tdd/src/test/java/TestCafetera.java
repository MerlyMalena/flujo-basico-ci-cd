import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import org.junit.Before;

public class TestCafetera {
    @Test
    public void deberiaDevolverVerdaderoSiExisteCafe() {

        assertTrue(cafetera.hasCafe(10));
    }

    @Test
    public void deberiaDevolverFalsoSiNoExisteCafe() {

        assertFalse(cafetera.hasCafe(11));
    }

    private Cafetera cafetera;

    @Before
    public void preparar() {
        cafetera = new Cafetera(10);
    }
    
    @Test
    public void deberiaRestarcafeAlaCafetera() {
        cafetera.giveCafe(7);

        assertEquals(3, cafetera.getCantidadCafe());
    }
}