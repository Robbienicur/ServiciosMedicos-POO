package Emergencias;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AccidenteTest {

    private Accidente crearAccidenteCompleto() {
        return new Accidente(
            1, "2026-04-17", "Masculino", "120/80",
            72, 18, "Consciente",
            "Caida en escaleras", "Sin fracturas visibles",
            100, "Maria", "Lopez Gomez",
            "maria@correo.com", "5551234567", "Atendido"
        );
    }

    @Test
    void getIdEmergencia() {
        Accidente a = crearAccidenteCompleto();
        assertEquals(1, a.getIdEmergencia());
    }

    @Test
    void getFecha() {
        Accidente a = crearAccidenteCompleto();
        assertEquals("2026-04-17", a.getFecha());
    }

    @Test
    void getGenero() {
        Accidente a = crearAccidenteCompleto();
        assertEquals("Masculino", a.getGenero());
    }

    @Test
    void getPresionArterial() {
        Accidente a = crearAccidenteCompleto();
        assertEquals("120/80", a.getPresionArterial());
    }

    @Test
    void getRitmoCardiaco() {
        Accidente a = crearAccidenteCompleto();
        assertEquals(72, a.getRitmoCardiaco());
    }

    @Test
    void getRitmoRespiratorio() {
        Accidente a = crearAccidenteCompleto();
        assertEquals(18, a.getRitmoRespiratorio());
    }

    @Test
    void getConsciencia() {
        Accidente a = crearAccidenteCompleto();
        assertEquals("Consciente", a.getConsciencia());
    }

    @Test
    void getDescripcion() {
        Accidente a = crearAccidenteCompleto();
        assertEquals("Caida en escaleras", a.getDescripcion());
    }

    @Test
    void getObservaciones() {
        Accidente a = crearAccidenteCompleto();
        assertEquals("Sin fracturas visibles", a.getObservaciones());
    }

    @Test
    void getIdContacto() {
        Accidente a = crearAccidenteCompleto();
        assertEquals(Integer.valueOf(100), a.getIdContacto());
    }

    @Test
    void getNombreContacto() {
        Accidente a = crearAccidenteCompleto();
        assertEquals("Maria", a.getNombreContacto());
    }

    @Test
    void getApellidosContacto() {
        Accidente a = crearAccidenteCompleto();
        assertEquals("Lopez Gomez", a.getApellidosContacto());
    }

    @Test
    void getCorreoContacto() {
        Accidente a = crearAccidenteCompleto();
        assertEquals("maria@correo.com", a.getCorreoContacto());
    }

    @Test
    void getTelefonoContacto() {
        Accidente a = crearAccidenteCompleto();
        assertEquals("5551234567", a.getTelefonoContacto());
    }

    @Test
    void getEstado() {
        Accidente a = crearAccidenteCompleto();
        assertEquals("Atendido", a.getEstado());
    }

    @Test
    void idContactoCanBeNull() {
        Accidente a = new Accidente(
            2, "2026-04-17", "Femenino", "110/70",
            80, 20, "Inconsciente",
            "Desmayo", "Requiere observacion",
            null, null, null,
            null, null, "Pendiente"
        );
        assertNull(a.getIdContacto());
        assertNull(a.getNombreContacto());
        assertNull(a.getApellidosContacto());
        assertNull(a.getCorreoContacto());
        assertNull(a.getTelefonoContacto());
    }

    @Test
    void allFieldsStoredCorrectly() {
        Accidente a = new Accidente(
            99, "2025-12-31", "No binario", "130/90",
            65, 16, "Semiconsciente",
            "Accidente vehicular", "Heridas leves",
            50, "Pedro", "Ramirez",
            "pedro@mail.com", "5559876543", "En proceso"
        );
        assertEquals(99, a.getIdEmergencia());
        assertEquals("2025-12-31", a.getFecha());
        assertEquals("No binario", a.getGenero());
        assertEquals("130/90", a.getPresionArterial());
        assertEquals(65, a.getRitmoCardiaco());
        assertEquals(16, a.getRitmoRespiratorio());
        assertEquals("Semiconsciente", a.getConsciencia());
        assertEquals("Accidente vehicular", a.getDescripcion());
        assertEquals("Heridas leves", a.getObservaciones());
        assertEquals(Integer.valueOf(50), a.getIdContacto());
        assertEquals("Pedro", a.getNombreContacto());
        assertEquals("Ramirez", a.getApellidosContacto());
        assertEquals("pedro@mail.com", a.getCorreoContacto());
        assertEquals("5559876543", a.getTelefonoContacto());
        assertEquals("En proceso", a.getEstado());
    }
}
