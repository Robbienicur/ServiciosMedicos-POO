package Registro;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class DoctorTest {

    @Test
    void constructorWithValidId() {
        Doctor d = new Doctor(2500, "Dr. Garcia");
        assertNotNull(d);
    }

    @Test
    void constructorWithLowerBoundId() {
        Doctor d = new Doctor(2000, "Dr. Limite Inferior");
        assertEquals(2000, d.getId());
    }

    @Test
    void constructorWithUpperBoundId() {
        Doctor d = new Doctor(3000, "Dr. Limite Superior");
        assertEquals(3000, d.getId());
    }

    @Test
    void constructorThrowsWhenIdBelowRange() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            new Doctor(1999, "Dr. Fuera");
        });
        assertEquals("ID de Doctor fuera de rango", ex.getMessage());
    }

    @Test
    void constructorThrowsWhenIdAboveRange() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            new Doctor(3001, "Dr. Fuera");
        });
        assertEquals("ID de Doctor fuera de rango", ex.getMessage());
    }

    @Test
    void getIdReturnsCorrectValue() {
        Doctor d = new Doctor(2500, "Dr. Martinez");
        assertEquals(2500, d.getId());
    }

    @Test
    void getNombreReturnsCorrectValue() {
        Doctor d = new Doctor(2500, "Dr. Martinez");
        assertEquals("Dr. Martinez", d.getNombre());
    }

    @Test
    void revisarFormularioPrintsPatientInfo() {
        Doctor d = new Doctor(2500, "Dr. Martinez");
        Paciente p = new Paciente(190000, "Juan Perez");
        p.setEnfermedades("Gripe");
        p.setMedicamentos("Paracetamol");
        p.setAlergias("Ninguna");
        p.setAltura(1.70);
        p.setPeso(75.0);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        try {
            d.revisarFormulario(p);
        } finally {
            System.setOut(originalOut);
        }

        String output = outputStream.toString();
        assertTrue(output.contains("Revisando formulario de Juan Perez"));
        assertTrue(output.contains("ID: 190000"));
        assertTrue(output.contains("Enfermedades: Gripe"));
    }
}
