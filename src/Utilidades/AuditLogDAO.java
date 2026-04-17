package Utilidades;

import BaseDeDatos.ConexionSQLite;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuditLogDAO {

    /**
     * Registra una entrada en la bitacora de auditoria.
     * Fire-and-forget: nunca lanza excepciones para no interrumpir el flujo principal.
     */
    public static void registrar(int userId, String accion, String detalle) {
        String sql = "INSERT INTO AuditLog (userId, accion, detalle) VALUES (?, ?, ?)";
        try (Connection conn = ConexionSQLite.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setString(2, accion);
            ps.setString(3, detalle);
            ps.executeUpdate();
        } catch (Exception e) {
            System.err.println("Error al registrar audit log: " + e.getMessage());
        }
    }

    /**
     * Obtiene los ultimos registros de auditoria ordenados por fecha descendente.
     *
     * @param limite cantidad maxima de registros a devolver
     * @return lista de String[] con {id, userId, accion, detalle, fecha}
     */
    public static List<String[]> obtenerLogs(int limite) {
        List<String[]> logs = new ArrayList<>();
        String sql = "SELECT id, userId, accion, detalle, fecha FROM AuditLog ORDER BY fecha DESC LIMIT ?";
        try (Connection conn = ConexionSQLite.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, limite);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    logs.add(new String[]{
                            String.valueOf(rs.getInt("id")),
                            String.valueOf(rs.getInt("userId")),
                            rs.getString("accion"),
                            rs.getString("detalle"),
                            rs.getString("fecha")
                    });
                }
            }
        } catch (Exception e) {
            System.err.println("Error al obtener audit logs: " + e.getMessage());
        }
        return logs;
    }

    /**
     * Obtiene los ultimos registros de auditoria de un usuario especifico.
     *
     * @param userId ID del usuario a filtrar
     * @param limite cantidad maxima de registros a devolver
     * @return lista de String[] con {id, userId, accion, detalle, fecha}
     */
    public static List<String[]> obtenerLogsPorUsuario(int userId, int limite) {
        List<String[]> logs = new ArrayList<>();
        String sql = "SELECT id, userId, accion, detalle, fecha FROM AuditLog WHERE userId = ? ORDER BY fecha DESC LIMIT ?";
        try (Connection conn = ConexionSQLite.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, limite);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    logs.add(new String[]{
                            String.valueOf(rs.getInt("id")),
                            String.valueOf(rs.getInt("userId")),
                            rs.getString("accion"),
                            rs.getString("detalle"),
                            rs.getString("fecha")
                    });
                }
            }
        } catch (Exception e) {
            System.err.println("Error al obtener audit logs por usuario: " + e.getMessage());
        }
        return logs;
    }
}
