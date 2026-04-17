package Registro;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ValidadorPaciente - Validaciones de datos del paciente")
class ValidadorPacienteTest {

    // ───────────────────────────────────────────────
    // esIDValido
    // ───────────────────────────────────────────────

    @Test
    void testEsIDValido_limiteInferiorExacto() {
        assertTrue(ValidadorPaciente.esIDValido("180000"));
    }

    @Test
    void testEsIDValido_limiteSuperiorExacto() {
        assertTrue(ValidadorPaciente.esIDValido("999999"));
    }

    @Test
    void testEsIDValido_valorIntermedio() {
        assertTrue(ValidadorPaciente.esIDValido("500000"));
    }

    @Test
    void testEsIDValido_unDebajoDelLimiteInferior() {
        assertFalse(ValidadorPaciente.esIDValido("179999"));
    }

    @Test
    void testEsIDValido_unArribaDelLimiteSuperior() {
        assertFalse(ValidadorPaciente.esIDValido("1000000"));
    }

    @Test
    void testEsIDValido_cero() {
        assertFalse(ValidadorPaciente.esIDValido("0"));
    }

    @Test
    void testEsIDValido_negativo() {
        assertFalse(ValidadorPaciente.esIDValido("-1"));
    }

    @Test
    void testEsIDValido_textoNoNumerico() {
        assertFalse(ValidadorPaciente.esIDValido("abc"));
    }

    @Test
    void testEsIDValido_cadenaVacia() {
        assertFalse(ValidadorPaciente.esIDValido(""));
    }

    @Test
    void testEsIDValido_conEspacios() {
        assertFalse(ValidadorPaciente.esIDValido("180 000"));
    }

    @Test
    void testEsIDValido_conDecimales() {
        assertFalse(ValidadorPaciente.esIDValido("180000.5"));
    }

    // ───────────────────────────────────────────────
    // esTextoAlfabetico
    // ───────────────────────────────────────────────

    @Test
    void testEsTextoAlfabetico_nombreSimple() {
        assertTrue(ValidadorPaciente.esTextoAlfabetico("Roberto"));
    }

    @Test
    void testEsTextoAlfabetico_nombreConEspacios() {
        assertTrue(ValidadorPaciente.esTextoAlfabetico("María del Carmen López"));
    }

    @Test
    void testEsTextoAlfabetico_caracteresEspanoles() {
        assertTrue(ValidadorPaciente.esTextoAlfabetico("José Ángel Muñoz Ibáñez"));
    }

    @Test
    void testEsTextoAlfabetico_todasLasVocalesConAcento() {
        assertTrue(ValidadorPaciente.esTextoAlfabetico("áéíóú ÁÉÍÓÚ"));
    }

    @Test
    void testEsTextoAlfabetico_enie() {
        assertTrue(ValidadorPaciente.esTextoAlfabetico("Ñoño ñandú"));
    }

    @Test
    void testEsTextoAlfabetico_conNumeros() {
        assertFalse(ValidadorPaciente.esTextoAlfabetico("Juan123"));
    }

    @Test
    void testEsTextoAlfabetico_conSignos() {
        assertFalse(ValidadorPaciente.esTextoAlfabetico("Juan!"));
    }

    @Test
    void testEsTextoAlfabetico_cadenaVacia() {
        assertFalse(ValidadorPaciente.esTextoAlfabetico(""));
    }

    @Test
    void testEsTextoAlfabetico_soloEspacios() {
        // regex [a-zA-Z...\\s]+ acepta solo espacios
        assertTrue(ValidadorPaciente.esTextoAlfabetico("   "));
    }

    // ───────────────────────────────────────────────
    // esCorreoValido
    // ───────────────────────────────────────────────

    @Test
    void testEsCorreoValido_formatoNormal() {
        assertTrue(ValidadorPaciente.esCorreoValido("usuario@dominio.com"));
    }

    @Test
    void testEsCorreoValido_conPuntoEnUsuario() {
        assertTrue(ValidadorPaciente.esCorreoValido("nombre.apellido@correo.mx"));
    }

    @Test
    void testEsCorreoValido_conGuionEnUsuario() {
        assertTrue(ValidadorPaciente.esCorreoValido("nombre-apellido@correo.com"));
    }

    @Test
    void testEsCorreoValido_conSubdominio() {
        assertTrue(ValidadorPaciente.esCorreoValido("user@mail.empresa.com"));
    }

    @Test
    void testEsCorreoValido_sinArroba() {
        assertFalse(ValidadorPaciente.esCorreoValido("usuariodominio.com"));
    }

    @Test
    void testEsCorreoValido_sinDominio() {
        assertFalse(ValidadorPaciente.esCorreoValido("usuario@"));
    }

    @Test
    void testEsCorreoValido_sinUsuario() {
        assertFalse(ValidadorPaciente.esCorreoValido("@dominio.com"));
    }

    @Test
    void testEsCorreoValido_dominioSinExtension() {
        assertFalse(ValidadorPaciente.esCorreoValido("usuario@dominio"));
    }

    @Test
    void testEsCorreoValido_extensionUnCaracter() {
        assertFalse(ValidadorPaciente.esCorreoValido("usuario@dominio.c"));
    }

    @Test
    void testEsCorreoValido_extensionDosCaracteres() {
        assertTrue(ValidadorPaciente.esCorreoValido("usuario@dominio.mx"));
    }

    @Test
    void testEsCorreoValido_cadenaVacia() {
        assertFalse(ValidadorPaciente.esCorreoValido(""));
    }

    // ───────────────────────────────────────────────
    // esNumerico
    // ───────────────────────────────────────────────

    @Test
    void testEsNumerico_entero() {
        assertTrue(ValidadorPaciente.esNumerico("12345"));
    }

    @Test
    void testEsNumerico_decimal() {
        assertTrue(ValidadorPaciente.esNumerico("123.45"));
    }

    @Test
    void testEsNumerico_cero() {
        assertTrue(ValidadorPaciente.esNumerico("0"));
    }

    @Test
    void testEsNumerico_ceroDecimal() {
        assertTrue(ValidadorPaciente.esNumerico("0.0"));
    }

    @Test
    void testEsNumerico_soloLetras() {
        assertFalse(ValidadorPaciente.esNumerico("abc"));
    }

    @Test
    void testEsNumerico_mezcla() {
        assertFalse(ValidadorPaciente.esNumerico("12abc"));
    }

    @Test
    void testEsNumerico_negativo() {
        assertFalse(ValidadorPaciente.esNumerico("-5"));
    }

    @Test
    void testEsNumerico_cadenaVacia() {
        assertFalse(ValidadorPaciente.esNumerico(""));
    }

    @Test
    void testEsNumerico_soloDecimal() {
        assertFalse(ValidadorPaciente.esNumerico(".5"));
    }

    @Test
    void testEsNumerico_dosDecimales() {
        assertFalse(ValidadorPaciente.esNumerico("1.2.3"));
    }

    @Test
    void testEsNumerico_conEspacios() {
        assertFalse(ValidadorPaciente.esNumerico("12 34"));
    }

    // ───────────────────────────────────────────────
    // esAlfanumerico
    // ───────────────────────────────────────────────

    @Test
    void testEsAlfanumerico_textoSimple() {
        assertTrue(ValidadorPaciente.esAlfanumerico("Hola mundo"));
    }

    @Test
    void testEsAlfanumerico_conNumeros() {
        assertTrue(ValidadorPaciente.esAlfanumerico("Calle 123"));
    }

    @Test
    void testEsAlfanumerico_conPuntuacion() {
        assertTrue(ValidadorPaciente.esAlfanumerico("Hola, mundo; esto es: una prueba!"));
    }

    @Test
    void testEsAlfanumerico_conInterrogacion() {
        assertTrue(ValidadorPaciente.esAlfanumerico("¿Cómo estás?"));
    }

    @Test
    void testEsAlfanumerico_conParentesis() {
        assertTrue(ValidadorPaciente.esAlfanumerico("Nota (importante)"));
    }

    @Test
    void testEsAlfanumerico_conGuion() {
        assertTrue(ValidadorPaciente.esAlfanumerico("Auto-evaluación"));
    }

    @Test
    void testEsAlfanumerico_caracteresEspanoles() {
        assertTrue(ValidadorPaciente.esAlfanumerico("Señor Ñoño áéíóú"));
    }

    @Test
    void testEsAlfanumerico_cadenaVacia() {
        // regex con * permite cadena vacía
        assertTrue(ValidadorPaciente.esAlfanumerico(""));
    }

    @Test
    void testEsAlfanumerico_simbolosNoPermitidos() {
        assertFalse(ValidadorPaciente.esAlfanumerico("user@mail.com"));
    }

    @Test
    void testEsAlfanumerico_conArroba() {
        assertFalse(ValidadorPaciente.esAlfanumerico("hola@mundo"));
    }

    @Test
    void testEsAlfanumerico_conAlmohadilla() {
        assertFalse(ValidadorPaciente.esAlfanumerico("#hashtag"));
    }

    // ───────────────────────────────────────────────
    // estanTodosLlenos
    // ───────────────────────────────────────────────

    @Test
    void testEstanTodosLlenos_todosConValor() {
        assertTrue(ValidadorPaciente.estanTodosLlenos("a", "b", "c"));
    }

    @Test
    void testEstanTodosLlenos_unCampo() {
        assertTrue(ValidadorPaciente.estanTodosLlenos("único"));
    }

    @Test
    void testEstanTodosLlenos_unoVacio() {
        assertFalse(ValidadorPaciente.estanTodosLlenos("a", "", "c"));
    }

    @Test
    void testEstanTodosLlenos_unoNull() {
        assertFalse(ValidadorPaciente.estanTodosLlenos("a", null, "c"));
    }

    @Test
    void testEstanTodosLlenos_todosVacios() {
        assertFalse(ValidadorPaciente.estanTodosLlenos("", "", ""));
    }

    @Test
    void testEstanTodosLlenos_soloEspacios() {
        // trim().isEmpty() devuelve true para espacios en blanco
        assertFalse(ValidadorPaciente.estanTodosLlenos("   "));
    }

    @Test
    void testEstanTodosLlenos_sinArgumentos() {
        // varargs vacío: el ciclo no itera, devuelve true
        assertTrue(ValidadorPaciente.estanTodosLlenos());
    }

    @Test
    void testEstanTodosLlenos_primerCampoNull() {
        assertFalse(ValidadorPaciente.estanTodosLlenos(null, "b"));
    }

    @Test
    void testEstanTodosLlenos_ultimoCampoVacio() {
        assertFalse(ValidadorPaciente.estanTodosLlenos("a", "b", ""));
    }
}
