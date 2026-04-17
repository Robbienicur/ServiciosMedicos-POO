package GestionEnfermedades;

import BaseDeDatos.ConexionSQLite;
import Utilidades.TestDBHelper;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class EnfermedadesDBTest {

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

    @Test
    void guardarYBuscarPorID() {
        boolean resultado = EnfermedadesDB.guardarEnfermedades(
                "180000", "Diabetes", "Penicilina", "Metformina");
        assertTrue(resultado);

        EnfermedadPaciente ep = EnfermedadesDB.buscarPorID("180000");
        assertNotNull(ep);
        assertEquals("Diabetes", ep.enfermedades());
        assertEquals("Penicilina", ep.alergias());
        assertEquals("Metformina", ep.medicacion());
    }

    @Test
    void replaceIntoActualizaRegistroExistente() {
        EnfermedadesDB.guardarEnfermedades(
                "180000", "Diabetes", "Penicilina", "Metformina");

        // Guardar de nuevo con mismos ID pero datos diferentes (REPLACE INTO)
        boolean actualizado = EnfermedadesDB.guardarEnfermedades(
                "180000", "Hipertension", "Sulfas", "Losartan");
        assertTrue(actualizado);

        EnfermedadPaciente ep = EnfermedadesDB.buscarPorID("180000");
        assertNotNull(ep);
        assertEquals("Hipertension", ep.enfermedades());
        assertEquals("Sulfas", ep.alergias());
        assertEquals("Losartan", ep.medicacion());
    }

    @Test
    void buscarPorIDInexistenteRetornaNull() {
        EnfermedadPaciente ep = EnfermedadesDB.buscarPorID("999999");
        assertNull(ep);
    }

    @Test
    void guardarConCamposVacios() {
        boolean resultado = EnfermedadesDB.guardarEnfermedades(
                "180001", "", "", "");
        assertTrue(resultado);

        EnfermedadPaciente ep = EnfermedadesDB.buscarPorID("180001");
        assertNotNull(ep);
        assertEquals("", ep.enfermedades());
        assertEquals("", ep.alergias());
        assertEquals("", ep.medicacion());
    }

    @Test
    void guardarMultiplesPacientes() {
        EnfermedadesDB.guardarEnfermedades(
                "180000", "Asma", "Polvo", "Salbutamol");
        EnfermedadesDB.guardarEnfermedades(
                "180001", "Migrania", "Ninguna", "Ibuprofeno");

        EnfermedadPaciente ep1 = EnfermedadesDB.buscarPorID("180000");
        EnfermedadPaciente ep2 = EnfermedadesDB.buscarPorID("180001");

        assertNotNull(ep1);
        assertNotNull(ep2);
        assertEquals("Asma", ep1.enfermedades());
        assertEquals("Migrania", ep2.enfermedades());
    }
}
