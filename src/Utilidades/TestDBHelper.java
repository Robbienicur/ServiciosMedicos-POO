package Utilidades;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TestDBHelper {

    public static Connection crearConexionTest() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlite::memory:");
        crearTablas(conn);
        insertarDatosTest(conn);
        return conn;
    }

    private static void crearTablas(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE InformacionAlumno (" +
                    "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "Nombre TEXT NOT NULL," +
                    "ApellidoPaterno TEXT NOT NULL," +
                    "ApellidoMaterno TEXT NOT NULL," +
                    "Correo TEXT NOT NULL," +
                    "Contraseña TEXT NOT NULL)");

            stmt.execute("CREATE TABLE InformacionMedico (" +
                    "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "Nombre TEXT NOT NULL," +
                    "ApellidoPaterno TEXT NOT NULL," +
                    "ApellidoMaterno TEXT NOT NULL," +
                    "Correo TEXT NOT NULL," +
                    "Contraseña TEXT NOT NULL)");

            stmt.execute("CREATE TABLE Registro (" +
                    "ID INTEGER PRIMARY KEY," +
                    "Edad INTEGER," +
                    "Altura REAL," +
                    "Peso REAL," +
                    "EnfermedadesPreexistentes TEXT," +
                    "Medicacion TEXT," +
                    "Alergias TEXT," +
                    "TipoSangre TEXT," +
                    "FOREIGN KEY(ID) REFERENCES InformacionAlumno(ID))");

            stmt.execute("CREATE TABLE Consultas (" +
                    "IDExpedienteMedico INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "IDPaciente INTEGER NOT NULL," +
                    "IDMedico INTEGER NOT NULL," +
                    "Sintomas TEXT NOT NULL," +
                    "Medicamentos TEXT NOT NULL," +
                    "Diagnostico TEXT NOT NULL," +
                    "FechaConsulta DATETIME NOT NULL," +
                    "FechaUltimaConsulta DATETIME NOT NULL," +
                    "FechaInicioSintomas DATETIME NOT NULL," +
                    "Receta TEXT NOT NULL)");

            stmt.execute("CREATE TABLE CitasMedicas (" +
                    "idCita INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "idPaciente TEXT NOT NULL," +
                    "fecha TEXT NOT NULL," +
                    "hora TEXT NOT NULL," +
                    "servicio TEXT NOT NULL)");

            stmt.execute("CREATE TABLE ListaEsperaCitas (" +
                    "idEspera INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "idPaciente INTEGER NOT NULL," +
                    "fechaDeseada TEXT NOT NULL," +
                    "horaDeseada TEXT NOT NULL," +
                    "servicio TEXT NOT NULL," +
                    "estado TEXT DEFAULT 'pendiente')");

            stmt.execute("CREATE TABLE Notificaciones (" +
                    "idNotificacion INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "idPaciente INTEGER NOT NULL," +
                    "mensaje TEXT NOT NULL," +
                    "estado TEXT NOT NULL CHECK(estado IN ('pendiente', 'atendida', 'rechazada'))," +
                    "fecha TEXT NOT NULL," +
                    "hora TEXT NOT NULL," +
                    "servicio TEXT NOT NULL)");

            stmt.execute("CREATE TABLE Enfermedades (" +
                    "idPaciente INTEGER PRIMARY KEY," +
                    "enfermedades TEXT," +
                    "alergias TEXT," +
                    "medicacion TEXT)");

            stmt.execute("CREATE TABLE JustificantePaciente (" +
                    "folio INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "idPaciente TEXT NOT NULL," +
                    "nombrePaciente TEXT NOT NULL," +
                    "motivo TEXT NOT NULL," +
                    "fechaInicio TEXT NOT NULL," +
                    "fechaFin TEXT NOT NULL," +
                    "diagnostico TEXT," +
                    "rutaArchivo TEXT," +
                    "estado TEXT DEFAULT 'Pendiente'," +
                    "resueltoPor TEXT," +
                    "fechaResolucion TEXT)");

            stmt.execute("CREATE TABLE Emergencias (" +
                    "IDEmergencia INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "IDPaciente INTEGER," +
                    "Ubicacion TEXT NOT NULL," +
                    "TipoDeEmergencia TEXT," +
                    "Gravedad TEXT CHECK(Gravedad IN ('Rojo','Naranja','Amarillo','Verde','Azul'))," +
                    "Descripcion TEXT," +
                    "FechaIncidente DATETIME NOT NULL," +
                    "FechaRegistro DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP," +
                    "Estado TEXT NOT NULL DEFAULT 'Pendiente'," +
                    "TelefonoContacto TEXT," +
                    "IDResponsable INTEGER)");

            stmt.execute("CREATE TABLE Accidentes (" +
                    "IDAccidente INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "IDEmergencia INTEGER NOT NULL," +
                    "FechaAccidente DATETIME NOT NULL," +
                    "GeneroPaciente TEXT NOT NULL," +
                    "PresionArterial TEXT NOT NULL," +
                    "RitmoCardiaco INTEGER NOT NULL," +
                    "RitmoRespiratorio INTEGER NOT NULL," +
                    "Consciencia TEXT NOT NULL," +
                    "DescripcionAccidente TEXT NOT NULL," +
                    "Observaciones TEXT," +
                    "IDContacto INTEGER," +
                    "NombreContacto TEXT," +
                    "ApellidosContacto TEXT," +
                    "CorreoContacto TEXT," +
                    "TelefonoContacto TEXT," +
                    "EstadoEmergencia TEXT NOT NULL DEFAULT 'Completo')");

            stmt.execute("CREATE TABLE SignosVitales (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "idPaciente INTEGER NOT NULL," +
                    "fecha DATETIME DEFAULT CURRENT_TIMESTAMP," +
                    "presionArterial TEXT," +
                    "ritmoCardiaco INTEGER," +
                    "ritmoRespiratorio INTEGER," +
                    "temperatura REAL," +
                    "oximetria INTEGER)");

            stmt.execute("CREATE TABLE AuditLog (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "userId INTEGER NOT NULL," +
                    "accion TEXT NOT NULL," +
                    "detalle TEXT," +
                    "fecha DATETIME DEFAULT CURRENT_TIMESTAMP)");
        }
    }

    private static void insertarDatosTest(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("INSERT INTO InformacionAlumno VALUES (180000, 'Juan', 'Pérez', 'López', 'juan@udlap.mx', 'pass180000')");
            stmt.execute("INSERT INTO InformacionAlumno VALUES (180001, 'María', 'García', 'Ruiz', 'maria@udlap.mx', 'pass180001')");
            stmt.execute("INSERT INTO InformacionMedico VALUES (5009, 'Carlos', 'Ramírez', 'Torres', 'carlos@udlap.mx', 'pass5009')");
            stmt.execute("INSERT INTO Registro VALUES (180000, 20, 1.75, 70.5, 'Ninguna', 'Ninguno', 'Ninguna', 'O+')");
        }
    }
}
