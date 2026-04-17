package Inicio;

public class SesionUsuario {
    private static int idPaciente;
    private static int idMedico;
    private static String nombreMedico;
    private static boolean esMedico = false;

    // --- PACIENTE ---
    public static void iniciarSesionPaciente(int id) {
        idPaciente = id;
        esMedico = false;
    }

    public static void iniciarSesion(int id) {
        idPaciente = id;
    }

    public static int getPacienteActual() {
        return idPaciente;
    }

    // --- MÉDICO ---
    public static void iniciarSesionMedico(int id, String nombre) {
        idMedico = id;
        nombreMedico = nombre;
        esMedico = true;
    }

    public static int getMedicoId() {
        return idMedico;
    }

    public static String getMedicoActual() {
        return nombreMedico;
    }

    public static boolean isMedico() {
        return esMedico;
    }

    public static void cerrarSesion() {
        idPaciente = 0;
        idMedico = 0;
        nombreMedico = null;
        esMedico = false;
    }
}
