package GestionCitas;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@DisplayName("ValidacionesCita - Validaciones de datos para citas médicas")
class ValidacionesCitaTest {

    // ───────────────────────────────────────────────
    // esNombreValido
    // ───────────────────────────────────────────────

    @Test
    void testEsNombreValido_nombreSimple() {
        assertTrue(ValidacionesCita.esNombreValido("Juan"));
    }

    @Test
    void testEsNombreValido_nombreCompuesto() {
        assertTrue(ValidacionesCita.esNombreValido("María del Carmen"));
    }

    @Test
    void testEsNombreValido_caracteresEspanoles() {
        assertTrue(ValidacionesCita.esNombreValido("José Ángel Muñoz"));
    }

    @Test
    void testEsNombreValido_todasLasVocalesAcentuadas() {
        assertTrue(ValidacionesCita.esNombreValido("áéíóú ÁÉÍÓÚ"));
    }

    @Test
    void testEsNombreValido_enie() {
        assertTrue(ValidacionesCita.esNombreValido("Ñoño"));
    }

    @Test
    void testEsNombreValido_null() {
        assertFalse(ValidacionesCita.esNombreValido(null));
    }

    @Test
    void testEsNombreValido_cadenaVacia() {
        assertFalse(ValidacionesCita.esNombreValido(""));
    }

    @Test
    void testEsNombreValido_conNumeros() {
        assertFalse(ValidacionesCita.esNombreValido("Juan123"));
    }

    @Test
    void testEsNombreValido_conSignos() {
        assertFalse(ValidacionesCita.esNombreValido("Juan!"));
    }

    @Test
    void testEsNombreValido_conArroba() {
        assertFalse(ValidacionesCita.esNombreValido("Juan@Pedro"));
    }

    @Test
    void testEsNombreValido_conGuion() {
        // El regex ^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$ no incluye guión
        assertFalse(ValidacionesCita.esNombreValido("Jean-Pierre"));
    }

    // ───────────────────────────────────────────────
    // esIdValido
    // ───────────────────────────────────────────────

    @Test
    void testEsIdValido_limiteExacto() {
        assertTrue(ValidacionesCita.esIdValido("100000"));
    }

    @Test
    void testEsIdValido_valorMayor() {
        assertTrue(ValidacionesCita.esIdValido("500000"));
    }

    @Test
    void testEsIdValido_unDebajoDelLimite() {
        assertFalse(ValidacionesCita.esIdValido("99999"));
    }

    @Test
    void testEsIdValido_cero() {
        assertFalse(ValidacionesCita.esIdValido("0"));
    }

    @Test
    void testEsIdValido_null() {
        assertFalse(ValidacionesCita.esIdValido(null));
    }

    @Test
    void testEsIdValido_cadenaVacia() {
        assertFalse(ValidacionesCita.esIdValido(""));
    }

    @Test
    void testEsIdValido_textoNoNumerico() {
        assertFalse(ValidacionesCita.esIdValido("abc"));
    }

    @Test
    void testEsIdValido_conDecimales() {
        assertFalse(ValidacionesCita.esIdValido("100000.5"));
    }

    @Test
    void testEsIdValido_conEspacios() {
        assertFalse(ValidacionesCita.esIdValido("100 000"));
    }

    @Test
    void testEsIdValido_negativo() {
        assertFalse(ValidacionesCita.esIdValido("-100000"));
    }

    // ───────────────────────────────────────────────
    // esFechaValida
    // ───────────────────────────────────────────────

    @Test
    void testEsFechaValida_fechaFutura() {
        LocalDate maniana = LocalDate.now().plusDays(1);
        assertTrue(ValidacionesCita.esFechaValida(
                maniana.getDayOfMonth(), maniana.getMonthValue(), maniana.getYear()));
    }

    @Test
    void testEsFechaValida_unAnioEnElFuturo() {
        LocalDate futuro = LocalDate.now().plusYears(1);
        assertTrue(ValidacionesCita.esFechaValida(
                futuro.getDayOfMonth(), futuro.getMonthValue(), futuro.getYear()));
    }

    @Test
    void testEsFechaValida_hoyNoEsValida() {
        // isAfter(now()) excluye el día de hoy
        LocalDate hoy = LocalDate.now();
        assertFalse(ValidacionesCita.esFechaValida(
                hoy.getDayOfMonth(), hoy.getMonthValue(), hoy.getYear()));
    }

    @Test
    void testEsFechaValida_fechaPasada() {
        assertFalse(ValidacionesCita.esFechaValida(1, 1, 2020));
    }

    @Test
    void testEsFechaValida_ayer() {
        LocalDate ayer = LocalDate.now().minusDays(1);
        assertFalse(ValidacionesCita.esFechaValida(
                ayer.getDayOfMonth(), ayer.getMonthValue(), ayer.getYear()));
    }

    @Test
    void testEsFechaValida_febrero30() {
        // Fecha inválida: 30 de febrero no existe
        assertFalse(ValidacionesCita.esFechaValida(30, 2, 2027));
    }

    @Test
    void testEsFechaValida_febrero29Bisiesto() {
        // 2028 es bisiesto
        assertTrue(ValidacionesCita.esFechaValida(29, 2, 2028));
    }

    @Test
    void testEsFechaValida_febrero29NoBisiesto() {
        // 2027 no es bisiesto
        assertFalse(ValidacionesCita.esFechaValida(29, 2, 2027));
    }

    @Test
    void testEsFechaValida_dia0() {
        assertFalse(ValidacionesCita.esFechaValida(0, 6, 2027));
    }

    @Test
    void testEsFechaValida_mes13() {
        assertFalse(ValidacionesCita.esFechaValida(1, 13, 2027));
    }

    @Test
    void testEsFechaValida_mes0() {
        assertFalse(ValidacionesCita.esFechaValida(1, 0, 2027));
    }

    @Test
    void testEsFechaValida_dia32() {
        assertFalse(ValidacionesCita.esFechaValida(32, 1, 2027));
    }

    @Test
    void testEsFechaValida_abril31() {
        // Abril tiene 30 días
        assertFalse(ValidacionesCita.esFechaValida(31, 4, 2027));
    }

    // ───────────────────────────────────────────────
    // esFechaHoraValida
    // ───────────────────────────────────────────────

    @Test
    void testEsFechaHoraValida_manianaMediodia() {
        LocalDate maniana = LocalDate.now().plusDays(1);
        assertTrue(ValidacionesCita.esFechaHoraValida(
                maniana.getDayOfMonth(), maniana.getMonthValue(), maniana.getYear(), 12, 0));
    }

    @Test
    void testEsFechaHoraValida_fechaFuturaMedianoche() {
        LocalDate futuro = LocalDate.now().plusDays(2);
        assertTrue(ValidacionesCita.esFechaHoraValida(
                futuro.getDayOfMonth(), futuro.getMonthValue(), futuro.getYear(), 0, 0));
    }

    @Test
    void testEsFechaHoraValida_fechaPasada() {
        assertFalse(ValidacionesCita.esFechaHoraValida(1, 1, 2020, 10, 30));
    }

    @Test
    void testEsFechaHoraValida_horaInvalida24() {
        LocalDate maniana = LocalDate.now().plusDays(1);
        assertFalse(ValidacionesCita.esFechaHoraValida(
                maniana.getDayOfMonth(), maniana.getMonthValue(), maniana.getYear(), 24, 0));
    }

    @Test
    void testEsFechaHoraValida_minutoInvalido60() {
        LocalDate maniana = LocalDate.now().plusDays(1);
        assertFalse(ValidacionesCita.esFechaHoraValida(
                maniana.getDayOfMonth(), maniana.getMonthValue(), maniana.getYear(), 10, 60));
    }

    @Test
    void testEsFechaHoraValida_horaNegativa() {
        LocalDate maniana = LocalDate.now().plusDays(1);
        assertFalse(ValidacionesCita.esFechaHoraValida(
                maniana.getDayOfMonth(), maniana.getMonthValue(), maniana.getYear(), -1, 0));
    }

    @Test
    void testEsFechaHoraValida_febrero30() {
        assertFalse(ValidacionesCita.esFechaHoraValida(30, 2, 2027, 10, 0));
    }

    @Test
    void testEsFechaHoraValida_hora23Minuto59Futuro() {
        LocalDate futuro = LocalDate.now().plusDays(2);
        assertTrue(ValidacionesCita.esFechaHoraValida(
                futuro.getDayOfMonth(), futuro.getMonthValue(), futuro.getYear(), 23, 59));
    }

    @Test
    void testEsFechaHoraValida_fechaInvalidaMes0() {
        assertFalse(ValidacionesCita.esFechaHoraValida(15, 0, 2027, 10, 0));
    }
}
