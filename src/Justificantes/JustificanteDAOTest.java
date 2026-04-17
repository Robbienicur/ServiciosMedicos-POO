package Justificantes;

import BaseDeDatos.ConexionSQLite;
import Utilidades.TestDBHelper;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class JustificanteDAOTest {

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

    private Justificante crearJustificante(String idPaciente, String nombre, String motivo) {
        return new Justificante(
                idPaciente, nombre, motivo,
                LocalDate.of(2026, 4, 10), LocalDate.of(2026, 4, 15),
                "Gripe comun", null
        );
    }

    @Test
    void guardarYObtenerPorFolio() {
        Justificante j = crearJustificante("180000", "Juan Perez", "Enfermedad");
        boolean guardado = JustificanteDAO.guardarJustificante(j);
        assertTrue(guardado);

        Optional<Justificante> resultado = JustificanteDAO.obtenerPorFolio(1);
        assertTrue(resultado.isPresent());

        Justificante recuperado = resultado.get();
        assertEquals("180000", recuperado.getIdPaciente());
        assertEquals("Juan Perez", recuperado.getNombrePaciente());
        assertEquals("Enfermedad", recuperado.getMotivo());
        assertEquals(LocalDate.of(2026, 4, 10), recuperado.getFechaInicio());
        assertEquals(LocalDate.of(2026, 4, 15), recuperado.getFechaFin());
        assertEquals("Gripe comun", recuperado.getDiagnostico());
        assertEquals("Pendiente", recuperado.getEstado());
    }

    @Test
    void obtenerTodosOrdenadosPorFolioDesc() {
        JustificanteDAO.guardarJustificante(
                crearJustificante("180000", "Juan Perez", "Motivo A"));
        JustificanteDAO.guardarJustificante(
                crearJustificante("180001", "Maria Garcia", "Motivo B"));
        JustificanteDAO.guardarJustificante(
                crearJustificante("180000", "Juan Perez", "Motivo C"));

        List<Justificante> todos = JustificanteDAO.obtenerTodos();
        assertEquals(3, todos.size());
        // ORDER BY folio DESC: el ultimo insertado tiene folio mayor
        assertTrue(todos.get(0).getFolio() > todos.get(1).getFolio());
        assertTrue(todos.get(1).getFolio() > todos.get(2).getFolio());
        assertEquals("Motivo C", todos.get(0).getMotivo());
        assertEquals("Motivo A", todos.get(2).getMotivo());
    }

    @Test
    void aprobarJustificanteCambiaEstado() {
        JustificanteDAO.guardarJustificante(
                crearJustificante("180000", "Juan Perez", "Consulta"));

        boolean aprobado = JustificanteDAO.aprobarJustificante(
                1, "Consulta medica", "Diagnostico actualizado",
                "Dr. Carlos Ramirez",
                LocalDate.of(2026, 4, 10), LocalDate.of(2026, 4, 17));
        assertTrue(aprobado);

        Optional<Justificante> resultado = JustificanteDAO.obtenerPorFolio(1);
        assertTrue(resultado.isPresent());
        assertEquals("Aprobado", resultado.get().getEstado());
        assertEquals("Dr. Carlos Ramirez", resultado.get().getResueltoPor());
        assertNotNull(resultado.get().getFechaResolucion());
    }

    @Test
    void rechazarJustificanteCambiaEstado() {
        JustificanteDAO.guardarJustificante(
                crearJustificante("180001", "Maria Garcia", "Ausencia"));

        boolean rechazado = JustificanteDAO.rechazarJustificante(1, "Dr. Carlos Ramirez");
        assertTrue(rechazado);

        Optional<Justificante> resultado = JustificanteDAO.obtenerPorFolio(1);
        assertTrue(resultado.isPresent());
        assertEquals("Rechazado", resultado.get().getEstado());
        assertEquals("Dr. Carlos Ramirez", resultado.get().getResueltoPor());
        assertNotNull(resultado.get().getFechaResolucion());
    }

    @Test
    void eliminarPorFolioRemueve() {
        JustificanteDAO.guardarJustificante(
                crearJustificante("180000", "Juan Perez", "Motivo"));

        assertTrue(JustificanteDAO.obtenerPorFolio(1).isPresent());

        boolean eliminado = JustificanteDAO.eliminarPorFolio(1);
        assertTrue(eliminado);

        assertFalse(JustificanteDAO.obtenerPorFolio(1).isPresent());
    }

    @Test
    void eliminarFolioInexistenteRetornaFalse() {
        boolean eliminado = JustificanteDAO.eliminarPorFolio(999);
        assertFalse(eliminado);
    }

    @Test
    void obtenerPorFolioInexistenteRetornaEmpty() {
        Optional<Justificante> resultado = JustificanteDAO.obtenerPorFolio(999);
        assertFalse(resultado.isPresent());
    }

    @Test
    void guardarConArchivoReceta() {
        File archivoFalso = new File("/tmp/receta_test.pdf");
        Justificante j = new Justificante(
                "180000", "Juan Perez", "Enfermedad",
                LocalDate.of(2026, 4, 1), LocalDate.of(2026, 4, 5),
                "Diagnostico", archivoFalso
        );

        assertTrue(JustificanteDAO.guardarJustificante(j));

        Optional<Justificante> resultado = JustificanteDAO.obtenerPorFolio(1);
        assertTrue(resultado.isPresent());
        assertNotNull(resultado.get().getArchivoReceta());
        assertTrue(resultado.get().getArchivoReceta().getAbsolutePath().contains("receta_test.pdf"));
    }
}
