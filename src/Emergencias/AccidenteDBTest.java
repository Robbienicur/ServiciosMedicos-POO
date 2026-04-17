package Emergencias;

import BaseDeDatos.ConexionSQLite;
import Utilidades.TestDBHelper;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class AccidenteDBTest {

    private Connection conn;

    @BeforeEach
    void setUp() throws Exception {
        conn = TestDBHelper.crearConexionTest();
        ConexionSQLite.setTestConnection(conn);
    }

    @AfterEach
    void tearDown() {
        ConexionSQLite.setTestConnection(null);
    }

    private Accidente crearAccidenteConContacto() {
        return new Accidente(
                1, "2026-04-17", "Masculino", "120/80",
                72, 18, "Consciente",
                "Caida en escaleras", "Sin fracturas visibles",
                100, "Maria", "Lopez Gomez",
                "maria@correo.com", "5551234567", "Completo"
        );
    }

    private Accidente crearAccidenteSinContacto() {
        return new Accidente(
                2, "2026-04-17", "Femenino", "110/70",
                80, 20, "Inconsciente",
                "Desmayo en salon", "Requiere observacion",
                null, null, null,
                null, null, "Completo"
        );
    }

    @Test
    void guardarYBuscarAccidente() {
        Accidente original = crearAccidenteConContacto();
        boolean guardado = AccidenteDB.guardarAccidenteCompleto(original);
        assertTrue(guardado);

        Accidente encontrado = AccidenteDB.buscarAccidente("1");
        assertNotNull(encontrado);
        assertEquals(1, encontrado.getIdEmergencia());
        assertEquals("2026-04-17", encontrado.getFecha());
        assertEquals("Masculino", encontrado.getGenero());
        assertEquals("120/80", encontrado.getPresionArterial());
        assertEquals(72, encontrado.getRitmoCardiaco());
        assertEquals(18, encontrado.getRitmoRespiratorio());
        assertEquals("Consciente", encontrado.getConsciencia());
        assertEquals("Caida en escaleras", encontrado.getDescripcion());
        assertEquals("Sin fracturas visibles", encontrado.getObservaciones());
        assertEquals(Integer.valueOf(100), encontrado.getIdContacto());
        assertEquals("Maria", encontrado.getNombreContacto());
        assertEquals("Lopez Gomez", encontrado.getApellidosContacto());
        assertEquals("maria@correo.com", encontrado.getCorreoContacto());
        assertEquals("5551234567", encontrado.getTelefonoContacto());
        assertEquals("Completo", encontrado.getEstado());
    }

    @Test
    void buscarAccidenteInexistenteRetornaNull() {
        Accidente resultado = AccidenteDB.buscarAccidente("999");
        assertNull(resultado);
    }

    @Test
    void guardarAccidenteConIdContactoNull() {
        Accidente accidente = crearAccidenteSinContacto();
        boolean guardado = AccidenteDB.guardarAccidenteCompleto(accidente);
        assertTrue(guardado);

        Accidente encontrado = AccidenteDB.buscarAccidente("2");
        assertNotNull(encontrado);
        assertEquals("Femenino", encontrado.getGenero());
        assertEquals("Desmayo en salon", encontrado.getDescripcion());
        assertNull(encontrado.getIdContacto());
        assertNull(encontrado.getNombreContacto());
        assertNull(encontrado.getApellidosContacto());
        assertNull(encontrado.getCorreoContacto());
        assertNull(encontrado.getTelefonoContacto());
    }

    @Test
    void guardarMultiplesAccidentes() {
        AccidenteDB.guardarAccidenteCompleto(crearAccidenteConContacto());
        AccidenteDB.guardarAccidenteCompleto(crearAccidenteSinContacto());

        Accidente a1 = AccidenteDB.buscarAccidente("1");
        Accidente a2 = AccidenteDB.buscarAccidente("2");

        assertNotNull(a1);
        assertNotNull(a2);
        assertEquals("Masculino", a1.getGenero());
        assertEquals("Femenino", a2.getGenero());
    }

    @Test
    void camposDeSignosVitalesSonCorrectos() {
        Accidente accidente = new Accidente(
                5, "2026-04-15", "Masculino", "140/95",
                110, 25, "Semiconsciente",
                "Accidente vehicular", "Heridas multiples",
                null, null, null,
                null, null, "Completo"
        );
        AccidenteDB.guardarAccidenteCompleto(accidente);

        Accidente encontrado = AccidenteDB.buscarAccidente("5");
        assertNotNull(encontrado);
        assertEquals("140/95", encontrado.getPresionArterial());
        assertEquals(110, encontrado.getRitmoCardiaco());
        assertEquals(25, encontrado.getRitmoRespiratorio());
        assertEquals("Semiconsciente", encontrado.getConsciencia());
    }
}
