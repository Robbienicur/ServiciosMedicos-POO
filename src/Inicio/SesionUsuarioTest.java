package Inicio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SesionUsuarioTest {

    @BeforeEach
    void resetSesion() {
        SesionUsuario.cerrarSesion();
    }

    @Test
    void iniciarSesionPacienteSetsIdAndNotMedico() {
        SesionUsuario.iniciarSesionPaciente(190000);
        assertEquals(190000, SesionUsuario.getPacienteActual());
        assertFalse(SesionUsuario.isMedico());
    }

    @Test
    void iniciarSesionSetsIdPaciente() {
        SesionUsuario.iniciarSesion(185000);
        assertEquals(185000, SesionUsuario.getPacienteActual());
    }

    @Test
    void iniciarSesionMedicoSetsAllMedicoFields() {
        SesionUsuario.iniciarSesionMedico(2500, "Dr. Garcia");
        assertEquals(2500, SesionUsuario.getMedicoId());
        assertEquals("Dr. Garcia", SesionUsuario.getMedicoActual());
        assertTrue(SesionUsuario.isMedico());
    }

    @Test
    void isMedicoReturnsFalseByDefault() {
        assertFalse(SesionUsuario.isMedico());
    }

    @Test
    void isMedicoReturnsTrueAfterMedicoLogin() {
        SesionUsuario.iniciarSesionMedico(2500, "Dr. Garcia");
        assertTrue(SesionUsuario.isMedico());
    }

    @Test
    void iniciarSesionPacienteOverridesMedicoFlag() {
        SesionUsuario.iniciarSesionMedico(2500, "Dr. Garcia");
        assertTrue(SesionUsuario.isMedico());

        SesionUsuario.iniciarSesionPaciente(190000);
        assertFalse(SesionUsuario.isMedico());
    }

    @Test
    void cerrarSesionResetsIdPaciente() {
        SesionUsuario.iniciarSesionPaciente(190000);
        SesionUsuario.cerrarSesion();
        assertEquals(0, SesionUsuario.getPacienteActual());
    }

    @Test
    void cerrarSesionResetsIdMedico() {
        SesionUsuario.iniciarSesionMedico(2500, "Dr. Garcia");
        SesionUsuario.cerrarSesion();
        assertEquals(0, SesionUsuario.getMedicoId());
    }

    @Test
    void cerrarSesionResetsNombreMedico() {
        SesionUsuario.iniciarSesionMedico(2500, "Dr. Garcia");
        SesionUsuario.cerrarSesion();
        assertNull(SesionUsuario.getMedicoActual());
    }

    @Test
    void cerrarSesionResetsEsMedico() {
        SesionUsuario.iniciarSesionMedico(2500, "Dr. Garcia");
        SesionUsuario.cerrarSesion();
        assertFalse(SesionUsuario.isMedico());
    }

    @Test
    void cerrarSesionResetsEverything() {
        SesionUsuario.iniciarSesionMedico(2500, "Dr. Garcia");
        SesionUsuario.iniciarSesionPaciente(190000);
        SesionUsuario.cerrarSesion();

        assertEquals(0, SesionUsuario.getPacienteActual());
        assertEquals(0, SesionUsuario.getMedicoId());
        assertNull(SesionUsuario.getMedicoActual());
        assertFalse(SesionUsuario.isMedico());
    }

    @Test
    void getPacienteActualReturnsZeroBeforeLogin() {
        assertEquals(0, SesionUsuario.getPacienteActual());
    }

    @Test
    void getMedicoIdReturnsZeroBeforeLogin() {
        assertEquals(0, SesionUsuario.getMedicoId());
    }

    @Test
    void getMedicoActualReturnsNullBeforeLogin() {
        assertNull(SesionUsuario.getMedicoActual());
    }

    @Test
    void iniciarSesionDoesNotAffectMedicoFlag() {
        SesionUsuario.iniciarSesionMedico(2500, "Dr. Garcia");
        SesionUsuario.iniciarSesion(190000);
        // iniciarSesion only sets idPaciente, does NOT change esMedico
        assertTrue(SesionUsuario.isMedico());
        assertEquals(190000, SesionUsuario.getPacienteActual());
    }
}
