package BaseDeDatos;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class InicializadorBD {

    public static void inicializar() {
        try (Connection conn = ConexionSQLite.conectar();
             Statement stmt = conn.createStatement()) {

            // Tabla Enfermedades (usada por EnfermedadesDB)
            stmt.execute(
                "CREATE TABLE IF NOT EXISTS Enfermedades (" +
                "    idPaciente INTEGER PRIMARY KEY," +
                "    enfermedades TEXT," +
                "    alergias TEXT," +
                "    medicacion TEXT," +
                "    FOREIGN KEY(idPaciente) REFERENCES InformacionAlumno(ID)" +
                ")"
            );

            // Tabla SolicitudesJustificantes (usada por PanelSolicitarJustificante)
            stmt.execute(
                "CREATE TABLE IF NOT EXISTS SolicitudesJustificantes (" +
                "    id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "    IDAlumno INTEGER NOT NULL," +
                "    Fecha TEXT NOT NULL," +
                "    Profesor TEXT NOT NULL," +
                "    Motivo TEXT NOT NULL," +
                "    Estado TEXT DEFAULT 'Pendiente'," +
                "    FOREIGN KEY(IDAlumno) REFERENCES InformacionAlumno(ID)" +
                ")"
            );

            // Tabla SignosVitales (nueva funcionalidad)
            stmt.execute(
                "CREATE TABLE IF NOT EXISTS SignosVitales (" +
                "    id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "    idPaciente INTEGER NOT NULL," +
                "    fecha DATETIME DEFAULT CURRENT_TIMESTAMP," +
                "    presionArterial TEXT," +
                "    ritmoCardiaco INTEGER," +
                "    ritmoRespiratorio INTEGER," +
                "    temperatura REAL," +
                "    oximetria INTEGER," +
                "    FOREIGN KEY(idPaciente) REFERENCES InformacionAlumno(ID)" +
                ")"
            );

            // Tabla AuditLog (log de auditoría)
            stmt.execute(
                "CREATE TABLE IF NOT EXISTS AuditLog (" +
                "    id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "    userId INTEGER NOT NULL," +
                "    accion TEXT NOT NULL," +
                "    detalle TEXT," +
                "    fecha DATETIME DEFAULT CURRENT_TIMESTAMP" +
                ")"
            );

            // Agregar columna TipoSangre a Registro si no existe
            try {
                stmt.execute("ALTER TABLE Registro ADD COLUMN TipoSangre TEXT");
            } catch (SQLException e) {
                // La columna ya existe, ignorar
            }

        } catch (SQLException e) {
            System.err.println("Error al inicializar la base de datos: " + e.getMessage());
        }
    }
}
