package Emergencias;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import java.time.LocalDate;

@DisplayName("ValidadorAccidente - Validación de campos de reporte de accidente")
class ValidadorAccidenteTest {

    // Valores válidos por defecto para todos los campos
    private String idEmergencia;
    private String idPaciente;
    private String nombrePaciente;
    private String correoPaciente;
    private String telefonoPaciente;
    private String presionArterial;
    private String ritmoCardiaco;
    private String ritmoRespiratorio;
    private ButtonGroup conscienciaGroup;
    private String descripcion;
    private String correoContacto;
    private String observaciones;
    private int dia;
    private int mes;
    private int anio;
    private String idContacto;
    private String nombreContacto;
    private String telefonoContacto;

    /**
     * Crea un ButtonGroup con un radio button seleccionado,
     * simulando la selección del nivel de consciencia.
     */
    private ButtonGroup crearButtonGroupConSeleccion() {
        ButtonGroup group = new ButtonGroup();
        JRadioButton radio = new JRadioButton("Consciente");
        radio.setSelected(true);
        group.add(radio);
        return group;
    }

    /**
     * Crea un ButtonGroup sin ninguna selección.
     */
    private ButtonGroup crearButtonGroupSinSeleccion() {
        ButtonGroup group = new ButtonGroup();
        JRadioButton radio = new JRadioButton("Consciente");
        // No se selecciona
        group.add(radio);
        return group;
    }

    @BeforeEach
    void setUp() {
        idEmergencia = "1001";
        idPaciente = "200000";
        nombrePaciente = "Juan Pérez";
        correoPaciente = "juan@correo.com";
        telefonoPaciente = "5512345678";
        presionArterial = "120/80";
        ritmoCardiaco = "72";
        ritmoRespiratorio = "18";
        conscienciaGroup = crearButtonGroupConSeleccion();
        descripcion = "Caída en escaleras";
        correoContacto = "contacto@correo.com";
        observaciones = "Paciente estable";

        // Fecha futura (hoy o después)
        LocalDate hoy = LocalDate.now();
        dia = hoy.getDayOfMonth();
        mes = hoy.getMonthValue();
        anio = hoy.getYear();

        idContacto = "3001";
        nombreContacto = "María López";
        telefonoContacto = "5598765432";
    }

    private String validar() {
        return ValidadorAccidente.validarCampos(
                idEmergencia, idPaciente, nombrePaciente, correoPaciente,
                telefonoPaciente, presionArterial, ritmoCardiaco, ritmoRespiratorio,
                conscienciaGroup, descripcion, correoContacto, observaciones,
                dia, mes, anio, idContacto, nombreContacto, telefonoContacto);
    }

    // ───────────────────────────────────────────────
    // Caso exitoso
    // ───────────────────────────────────────────────

    @Test
    void testTodosCamposValidos_retornaNull() {
        assertNull(validar());
    }

    // ───────────────────────────────────────────────
    // ID Emergencia
    // ───────────────────────────────────────────────

    @Test
    void testIdEmergenciaNoNumerico() {
        idEmergencia = "abc";
        assertEquals("ID Emergencia debe ser numérico", validar());
    }

    @Test
    void testIdEmergenciaVacio() {
        idEmergencia = "";
        assertEquals("ID Emergencia debe ser numérico", validar());
    }

    @Test
    void testIdEmergenciaConEspacios() {
        idEmergencia = "10 01";
        assertEquals("ID Emergencia debe ser numérico", validar());
    }

    // ───────────────────────────────────────────────
    // ID Paciente
    // ───────────────────────────────────────────────

    @Test
    void testIdPacienteNoNumerico() {
        idPaciente = "xyz";
        assertEquals("ID Paciente debe ser numérico", validar());
    }

    @Test
    void testIdPacienteVacio() {
        idPaciente = "";
        assertEquals("ID Paciente debe ser numérico", validar());
    }

    // ───────────────────────────────────────────────
    // Nombre paciente
    // ───────────────────────────────────────────────

    @Test
    void testNombrePacienteVacio() {
        nombrePaciente = "   ";
        assertEquals("Nombre del paciente es obligatorio", validar());
    }

    @Test
    void testNombrePacienteSoloEspacios() {
        nombrePaciente = "     ";
        assertEquals("Nombre del paciente es obligatorio", validar());
    }

    // ───────────────────────────────────────────────
    // Correo paciente
    // ───────────────────────────────────────────────

    @Test
    void testCorreoPacienteSinArroba() {
        correoPaciente = "juancorreo.com";
        assertEquals("El correo del paciente debe contener '@'", validar());
    }

    @Test
    void testCorreoPacienteVacio() {
        correoPaciente = "";
        assertEquals("El correo del paciente debe contener '@'", validar());
    }

    // ───────────────────────────────────────────────
    // Teléfono paciente
    // ───────────────────────────────────────────────

    @Test
    void testTelefonoPacienteMuyCorto() {
        telefonoPaciente = "123456789"; // 9 dígitos
        assertEquals("El teléfono del paciente debe tener entre 10 y 15 dígitos", validar());
    }

    @Test
    void testTelefonoPacienteMuyLargo() {
        telefonoPaciente = "1234567890123456"; // 16 dígitos
        assertEquals("El teléfono del paciente debe tener entre 10 y 15 dígitos", validar());
    }

    @Test
    void testTelefonoPacienteConLetras() {
        telefonoPaciente = "55abcd1234";
        assertEquals("El teléfono del paciente debe tener entre 10 y 15 dígitos", validar());
    }

    @Test
    void testTelefonoPaciente10Digitos() {
        telefonoPaciente = "5512345678"; // exactamente 10
        // No debe fallar en teléfono (puede pasar o fallar más adelante)
        String resultado = validar();
        assertNotEquals("El teléfono del paciente debe tener entre 10 y 15 dígitos", resultado);
    }

    @Test
    void testTelefonoPaciente15Digitos() {
        telefonoPaciente = "123456789012345"; // exactamente 15
        String resultado = validar();
        assertNotEquals("El teléfono del paciente debe tener entre 10 y 15 dígitos", resultado);
    }

    // ───────────────────────────────────────────────
    // Fecha
    // ───────────────────────────────────────────────

    @Test
    void testFechaPasada() {
        dia = 1;
        mes = 1;
        anio = 2020;
        assertEquals("La fecha del accidente no puede ser anterior a hoy", validar());
    }

    @Test
    void testFechaInvalida_febrero30() {
        dia = 30;
        mes = 2;
        anio = 2027;
        assertEquals("La fecha ingresada no es válida", validar());
    }

    @Test
    void testFechaInvalida_mes13() {
        dia = 1;
        mes = 13;
        anio = 2027;
        assertEquals("La fecha ingresada no es válida", validar());
    }

    @Test
    void testFechaInvalida_dia0() {
        dia = 0;
        mes = 6;
        anio = 2027;
        assertEquals("La fecha ingresada no es válida", validar());
    }

    // ───────────────────────────────────────────────
    // ID Contacto
    // ───────────────────────────────────────────────

    @Test
    void testIdContactoNoNumerico() {
        idContacto = "abc";
        assertEquals("ID del contacto debe ser numérico", validar());
    }

    @Test
    void testIdContactoVacio() {
        idContacto = "";
        assertEquals("ID del contacto debe ser numérico", validar());
    }

    // ───────────────────────────────────────────────
    // Nombre contacto
    // ───────────────────────────────────────────────

    @Test
    void testNombreContactoVacio() {
        nombreContacto = "   ";
        assertEquals("El nombre del contacto es obligatorio", validar());
    }

    // ───────────────────────────────────────────────
    // Correo contacto
    // ───────────────────────────────────────────────

    @Test
    void testCorreoContactoSinArroba() {
        correoContacto = "contactocorreo.com";
        assertEquals("El correo del contacto debe contener '@'", validar());
    }

    // ───────────────────────────────────────────────
    // Teléfono contacto
    // ───────────────────────────────────────────────

    @Test
    void testTelefonoContactoMuyCorto() {
        telefonoContacto = "12345"; // 5 dígitos
        assertEquals("El teléfono del contacto debe tener entre 10 y 15 dígitos", validar());
    }

    @Test
    void testTelefonoContactoConLetras() {
        telefonoContacto = "abcdefghij";
        assertEquals("El teléfono del contacto debe tener entre 10 y 15 dígitos", validar());
    }

    // ───────────────────────────────────────────────
    // Presión arterial
    // ───────────────────────────────────────────────

    @Test
    void testPresionArterialFormatoIncorrecto() {
        presionArterial = "120-80";
        assertEquals("Presión arterial debe estar en formato ##/##", validar());
    }

    @Test
    void testPresionArterialSoloNumero() {
        presionArterial = "120";
        assertEquals("Presión arterial debe estar en formato ##/##", validar());
    }

    @Test
    void testPresionArterialVacia() {
        presionArterial = "";
        assertEquals("Presión arterial debe estar en formato ##/##", validar());
    }

    @Test
    void testPresionArterialFormatoCorrecto() {
        presionArterial = "120/80";
        // No debe fallar en presión
        String resultado = validar();
        assertNotEquals("Presión arterial debe estar en formato ##/##", resultado);
    }

    @Test
    void testPresionArterialTresDigitos() {
        presionArterial = "130/90";
        String resultado = validar();
        assertNotEquals("Presión arterial debe estar en formato ##/##", resultado);
    }

    // ───────────────────────────────────────────────
    // Ritmo cardíaco
    // ───────────────────────────────────────────────

    @Test
    void testRitmoCardiacoNoNumerico() {
        ritmoCardiaco = "alto";
        assertEquals("Ritmo cardíaco debe ser numérico", validar());
    }

    @Test
    void testRitmoCardiacoVacio() {
        ritmoCardiaco = "";
        assertEquals("Ritmo cardíaco debe ser numérico", validar());
    }

    // ───────────────────────────────────────────────
    // Ritmo respiratorio
    // ───────────────────────────────────────────────

    @Test
    void testRitmoRespiratorioNoNumerico() {
        ritmoRespiratorio = "normal";
        assertEquals("Ritmo respiratorio debe ser numérico", validar());
    }

    @Test
    void testRitmoRespiratorioVacio() {
        ritmoRespiratorio = "";
        assertEquals("Ritmo respiratorio debe ser numérico", validar());
    }

    // ───────────────────────────────────────────────
    // Consciencia (ButtonGroup)
    // ───────────────────────────────────────────────

    @Test
    void testConscienciaSinSeleccion() {
        conscienciaGroup = crearButtonGroupSinSeleccion();
        assertEquals("Seleccione nivel de consciencia", validar());
    }

    // ───────────────────────────────────────────────
    // Descripción
    // ───────────────────────────────────────────────

    @Test
    void testDescripcionVacia() {
        descripcion = "   ";
        assertEquals("La descripción del accidente es obligatoria", validar());
    }

    @Test
    void testDescripcionSoloEspacios() {
        descripcion = "     ";
        assertEquals("La descripción del accidente es obligatoria", validar());
    }

    // ───────────────────────────────────────────────
    // Observaciones
    // ───────────────────────────────────────────────

    @Test
    void testObservacionesVacias() {
        observaciones = "   ";
        assertEquals("Las observaciones no pueden estar vacías", validar());
    }

    @Test
    void testObservacionesSoloEspacios() {
        observaciones = "      ";
        assertEquals("Las observaciones no pueden estar vacías", validar());
    }

    // ───────────────────────────────────────────────
    // Prioridad de errores: primer campo inválido gana
    // ───────────────────────────────────────────────

    @Test
    void testMultiplesCamposInvalidos_retornaPrimerError() {
        idEmergencia = "abc";
        idPaciente = "xyz";
        nombrePaciente = "";
        // Debe retornar el primer error en orden de validación
        assertEquals("ID Emergencia debe ser numérico", validar());
    }
}
