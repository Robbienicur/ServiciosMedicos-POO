package GestionCitas;

import BaseDeDatos.ConexionSQLite;
import Modelo.Espera;
import Utilidades.TestDBHelper;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ListaEsperaDAOTest {

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
    void registrarEnEsperaInsertaRegistro() throws SQLException {
        ListaEsperaDAO.registrarEnEspera("180000", "2026-04-20", "10:00", "Consulta General");

        List<Espera> solicitudes = ListaEsperaDAO.obtenerSolicitudesPara(
                "2026-04-20", "10:00", "Consulta General");
        assertEquals(1, solicitudes.size());
        assertEquals("180000", solicitudes.get(0).getIdPaciente());
    }

    @Test
    void registrarDuplicadoNoInsertaSegundaVez() throws SQLException {
        ListaEsperaDAO.registrarEnEspera("180000", "2026-04-20", "10:00", "Consulta General");
        // Segundo intento con los mismos datos -- no debe insertar
        ListaEsperaDAO.registrarEnEspera("180000", "2026-04-20", "10:00", "Consulta General");

        List<Espera> solicitudes = ListaEsperaDAO.obtenerSolicitudesPara(
                "2026-04-20", "10:00", "Consulta General");
        assertEquals(1, solicitudes.size());
    }

    @Test
    void obtenerSolicitudesFiltraCorrectamente() throws SQLException {
        ListaEsperaDAO.registrarEnEspera("180000", "2026-04-20", "10:00", "Consulta General");
        ListaEsperaDAO.registrarEnEspera("180001", "2026-04-20", "10:00", "Consulta General");
        ListaEsperaDAO.registrarEnEspera("180000", "2026-04-21", "10:00", "Consulta General");
        ListaEsperaDAO.registrarEnEspera("180000", "2026-04-20", "11:00", "Consulta General");
        ListaEsperaDAO.registrarEnEspera("180000", "2026-04-20", "10:00", "Odontologia");

        // Solo los que coinciden en fecha, hora Y servicio
        List<Espera> resultado = ListaEsperaDAO.obtenerSolicitudesPara(
                "2026-04-20", "10:00", "Consulta General");
        assertEquals(2, resultado.size());
    }

    @Test
    void marcarNotificadaCambiaEstado() throws SQLException {
        ListaEsperaDAO.registrarEnEspera("180000", "2026-04-20", "10:00", "Consulta General");

        // Antes de marcar: debe estar pendiente
        List<Espera> antes = ListaEsperaDAO.obtenerSolicitudesPara(
                "2026-04-20", "10:00", "Consulta General");
        assertEquals(1, antes.size());

        // Marcar como notificada
        ListaEsperaDAO.marcarNotificada("180000", "2026-04-20", "10:00", "Consulta General");

        // Despues de marcar: obtenerSolicitudesPara filtra por estado='pendiente',
        // asi que ya no debe aparecer
        List<Espera> despues = ListaEsperaDAO.obtenerSolicitudesPara(
                "2026-04-20", "10:00", "Consulta General");
        assertEquals(0, despues.size());
    }

    @Test
    void obtenerSolicitudesParaVacioSinRegistros() throws SQLException {
        List<Espera> resultado = ListaEsperaDAO.obtenerSolicitudesPara(
                "2026-04-20", "10:00", "Consulta General");
        assertTrue(resultado.isEmpty());
    }

    @Test
    void registrarDiferenteServicioNoDuplica() throws SQLException {
        ListaEsperaDAO.registrarEnEspera("180000", "2026-04-20", "10:00", "Consulta General");
        ListaEsperaDAO.registrarEnEspera("180000", "2026-04-20", "10:00", "Odontologia");

        List<Espera> general = ListaEsperaDAO.obtenerSolicitudesPara(
                "2026-04-20", "10:00", "Consulta General");
        List<Espera> odonto = ListaEsperaDAO.obtenerSolicitudesPara(
                "2026-04-20", "10:00", "Odontologia");

        assertEquals(1, general.size());
        assertEquals(1, odonto.size());
    }
}
