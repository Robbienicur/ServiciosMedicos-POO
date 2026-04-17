package Modelo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EsperaTest {

    @Test
    void constructorStoresAllFields() {
        Espera e = new Espera("190000", "2026-04-17", "10:30", "Consulta General");
        assertNotNull(e);
    }

    @Test
    void getIdPaciente() {
        Espera e = new Espera("190000", "2026-04-17", "10:30", "Consulta General");
        assertEquals("190000", e.getIdPaciente());
    }

    @Test
    void getFecha() {
        Espera e = new Espera("190000", "2026-04-17", "10:30", "Consulta General");
        assertEquals("2026-04-17", e.getFecha());
    }

    @Test
    void getHora() {
        Espera e = new Espera("190000", "2026-04-17", "10:30", "Consulta General");
        assertEquals("10:30", e.getHora());
    }

    @Test
    void getServicio() {
        Espera e = new Espera("190000", "2026-04-17", "10:30", "Consulta General");
        assertEquals("Consulta General", e.getServicio());
    }

    @Test
    void fieldsWithDifferentValues() {
        Espera e = new Espera("185555", "2025-01-01", "08:00", "Urgencias");
        assertEquals("185555", e.getIdPaciente());
        assertEquals("2025-01-01", e.getFecha());
        assertEquals("08:00", e.getHora());
        assertEquals("Urgencias", e.getServicio());
    }

    @Test
    void fieldsWithNullValues() {
        Espera e = new Espera(null, null, null, null);
        assertNull(e.getIdPaciente());
        assertNull(e.getFecha());
        assertNull(e.getHora());
        assertNull(e.getServicio());
    }

    @Test
    void fieldsWithEmptyStrings() {
        Espera e = new Espera("", "", "", "");
        assertEquals("", e.getIdPaciente());
        assertEquals("", e.getFecha());
        assertEquals("", e.getHora());
        assertEquals("", e.getServicio());
    }
}
