package Inicio;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.Random;
import BaseDeDatos.ConexionSQLite;

public class RecuperarContrasenaFrame extends JFrame {
    private JTextField emailField;
    private JButton enviarBtn;

    public RecuperarContrasenaFrame() {
        setTitle("Recuperar Contraseña");
        setSize(420, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 5, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        formPanel.add(new JLabel("Correo Electrónico:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        emailField = new JTextField();
        formPanel.add(emailField, gbc);

        add(formPanel, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        enviarBtn = new JButton("Recuperar Contraseña");
        enviarBtn.setBackground(new Color(255, 102, 0));
        enviarBtn.setForeground(Color.WHITE);
        enviarBtn.setFocusPainted(false);
        btnPanel.add(enviarBtn);
        add(btnPanel, BorderLayout.SOUTH);

        getRootPane().setDefaultButton(enviarBtn);

        enviarBtn.addActionListener(e -> recuperarContrasena());
    }

    private void recuperarContrasena() {
        String email = emailField.getText().trim();

        if (email.isEmpty() || !email.contains("@") || !email.contains(".")) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, ingrese un correo electrónico válido.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = ConexionSQLite.conectar()) {
            // Buscar en InformacionAlumno
            String sqlAlumno = "SELECT ID FROM InformacionAlumno WHERE Correo = ?";
            try (PreparedStatement ps = conn.prepareStatement(sqlAlumno)) {
                ps.setString(1, email);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        int id = rs.getInt("ID");
                        String nuevaPass = generarContrasena();
                        actualizarContrasena(conn, "InformacionAlumno", id, nuevaPass);
                        mostrarNuevaContrasena(nuevaPass, "Paciente");
                        return;
                    }
                }
            }

            // Buscar en InformacionMedico
            String sqlMedico = "SELECT ID FROM InformacionMedico WHERE Correo = ?";
            try (PreparedStatement ps = conn.prepareStatement(sqlMedico)) {
                ps.setString(1, email);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        int id = rs.getInt("ID");
                        String nuevaPass = generarContrasena();
                        actualizarContrasena(conn, "InformacionMedico", id, nuevaPass);
                        mostrarNuevaContrasena(nuevaPass, "Médico");
                        return;
                    }
                }
            }

            JOptionPane.showMessageDialog(this,
                    "No se encontró una cuenta asociada a ese correo electrónico.",
                    "Correo no encontrado", JOptionPane.WARNING_MESSAGE);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al acceder a la base de datos:\n" + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String generarContrasena() {
        String chars = "ABCDEFGHJKLMNPQRSTUVWXYZabcdefghjkmnpqrstuvwxyz23456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(8);
        for (int i = 0; i < 8; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    private void actualizarContrasena(Connection conn, String tabla, int id, String nuevaPass) throws SQLException {
        String sql = "UPDATE " + tabla + " SET Contraseña = ? WHERE ID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nuevaPass);
            ps.setInt(2, id);
            ps.executeUpdate();
        }
    }

    private void mostrarNuevaContrasena(String nuevaPass, String tipoUsuario) {
        JOptionPane.showMessageDialog(this,
                "Se ha generado una nueva contraseña para la cuenta de " + tipoUsuario + ".\n\n"
                + "Nueva contraseña: " + nuevaPass + "\n\n"
                + "Por favor, anótela y úsela para iniciar sesión.",
                "Contraseña Recuperada", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RecuperarContrasenaFrame::new);
    }
}
