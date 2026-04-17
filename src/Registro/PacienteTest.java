package Registro;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PacienteTest {

    @Test
    void constructorWithValidId() {
        Paciente p = new Paciente(190000, "Juan Perez");
        assertNotNull(p);
    }

    @Test
    void constructorWithLowerBoundId() {
        Paciente p = new Paciente(180000, "Limite Inferior");
        assertEquals(180000, p.getId());
    }

    @Test
    void constructorWithUpperBoundId() {
        Paciente p = new Paciente(200000, "Limite Superior");
        assertEquals(200000, p.getId());
    }

    @Test
    void constructorThrowsWhenIdBelowRange() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            new Paciente(179999, "Fuera de rango");
        });
        assertEquals("ID de Paciente fuera de rango", ex.getMessage());
    }

    @Test
    void constructorThrowsWhenIdAboveRange() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            new Paciente(200001, "Fuera de rango");
        });
        assertEquals("ID de Paciente fuera de rango", ex.getMessage());
    }

    @Test
    void getIdReturnsCorrectValue() {
        Paciente p = new Paciente(185000, "Maria Lopez");
        assertEquals(185000, p.getId());
    }

    @Test
    void getNombreReturnsCorrectValue() {
        Paciente p = new Paciente(185000, "Maria Lopez");
        assertEquals("Maria Lopez", p.getNombre());
    }

    @Test
    void setAndGetEnfermedades() {
        Paciente p = new Paciente(190000, "Test");
        p.setEnfermedades("Diabetes");
        // No getter for enfermedades directly, but obtenerDatos includes it
        assertTrue(p.obtenerDatos().contains("Diabetes"));
    }

    @Test
    void setAndGetMedicamentos() {
        Paciente p = new Paciente(190000, "Test");
        p.setMedicamentos("Insulina");
        assertTrue(p.obtenerDatos().contains("Insulina"));
    }

    @Test
    void setAndGetAlergias() {
        Paciente p = new Paciente(190000, "Test");
        p.setAlergias("Penicilina");
        assertTrue(p.obtenerDatos().contains("Penicilina"));
    }

    @Test
    void setAndGetAltura() {
        Paciente p = new Paciente(190000, "Test");
        p.setAltura(1.75);
        assertTrue(p.obtenerDatos().contains("1.75"));
    }

    @Test
    void setAndGetPeso() {
        Paciente p = new Paciente(190000, "Test");
        p.setPeso(70.5);
        assertTrue(p.obtenerDatos().contains("70.5"));
    }

    @Test
    void obtenerDatosContainsAllFields() {
        Paciente p = new Paciente(190000, "Carlos Ruiz");
        p.setEnfermedades("Hipertension");
        p.setMedicamentos("Losartan");
        p.setAlergias("Ninguna");
        p.setAltura(1.80);
        p.setPeso(85.0);

        String datos = p.obtenerDatos();
        assertTrue(datos.contains("ID: 190000"));
        assertTrue(datos.contains("Nombre: Carlos Ruiz"));
        assertTrue(datos.contains("Enfermedades: Hipertension"));
        assertTrue(datos.contains("Medicamentos: Losartan"));
        assertTrue(datos.contains("Alergias: Ninguna"));
        assertTrue(datos.contains("Altura: 1.8"));
        assertTrue(datos.contains("Peso: 85.0"));
    }

    @Test
    void obtenerDatosFormat() {
        Paciente p = new Paciente(190000, "Ana Torres");
        p.setEnfermedades("Asma");
        p.setMedicamentos("Salbutamol");
        p.setAlergias("Polen");
        p.setAltura(1.65);
        p.setPeso(60.0);

        String expected = "ID: 190000\nNombre: Ana Torres\nEnfermedades: Asma\nMedicamentos: Salbutamol\nAlergias: Polen\nAltura: 1.65\nPeso: 60.0";
        assertEquals(expected, p.obtenerDatos());
    }

    @Test
    void obtenerDatosWithNullFields() {
        Paciente p = new Paciente(190000, "Sin Datos");
        String datos = p.obtenerDatos();
        assertTrue(datos.contains("Enfermedades: null"));
        assertTrue(datos.contains("Medicamentos: null"));
        assertTrue(datos.contains("Alergias: null"));
    }
}
