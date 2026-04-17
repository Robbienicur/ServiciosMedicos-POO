package Inicio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import BaseDeDatos.ConexionSQLite;
import BaseDeDatos.InicializadorBD;
import GestionCitas.RecordatorioCitasService;
import Utilidades.AuditLogDAO;
import Utilidades.ColoresUDLAP;
import Utilidades.ui.TemaUDLAP;
import Utilidades.ui.BotonUDLAP;
import Utilidades.ui.CampoTextoUDLAP;
import Utilidades.ui.CardPanel;

public class InterfazLogin extends JFrame {

    private JPanel panelID;
    private JPanel panelPassword;

    public InterfazLogin() {
        super("Servicios Médicos UDLAP");
        setSize(500, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        // Fondo general
        JPanel fondo = new JPanel(new GridBagLayout());
        fondo.setBackground(ColoresUDLAP.FONDO_GENERAL);
        setContentPane(fondo);

        // Card central
        CardPanel card = new CardPanel(false);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        // Logo UDLAP
        JLabel lblLogo = new JLabel();
        lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
        try {
            java.net.URL logoUrl = getClass().getResource("/icons/udlap_logo.png");
            if (logoUrl != null) {
                ImageIcon iconoOriginal = new ImageIcon(logoUrl);
                Image imgEscalada = iconoOriginal.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
                lblLogo.setIcon(new ImageIcon(imgEscalada));
            }
        } catch (Exception e) {
            // Si no se encuentra el logo, se omite
        }
        card.add(lblLogo);
        card.add(Box.createVerticalStrut(12));

        // Titulo
        JLabel lblTitulo = new JLabel("Servicios Médicos");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(ColoresUDLAP.VERDE_PRIMARIO);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(lblTitulo);
        card.add(Box.createVerticalStrut(4));

        // Subtitulo
        JLabel lblSubtitulo = new JLabel("Iniciar Sesión");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubtitulo.setForeground(ColoresUDLAP.TEXTO_SECUNDARIO);
        lblSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(lblSubtitulo);
        card.add(Box.createVerticalStrut(15));

        // Campo ID
        panelID = CampoTextoUDLAP.crear("ID");
        panelID.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelID.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        card.add(panelID);
        card.add(Box.createVerticalStrut(10));

        // Campo Contraseña
        panelPassword = CampoTextoUDLAP.crearPassword("Contraseña");
        panelPassword.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPassword.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        card.add(panelPassword);
        card.add(Box.createVerticalStrut(15));

        // Boton Iniciar Sesion
        BotonUDLAP btnIniciar = BotonUDLAP.primario("Iniciar Sesión");
        btnIniciar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnIniciar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        card.add(btnIniciar);
        card.add(Box.createVerticalStrut(12));

        // ENTER dispara btnIniciar
        getRootPane().setDefaultButton(btnIniciar);

        // Recuperar contrasena
        JLabel lblRecuperar = new JLabel("Recuperar contraseña");
        lblRecuperar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblRecuperar.setForeground(ColoresUDLAP.NARANJA_ACENTO);
        lblRecuperar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblRecuperar.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(lblRecuperar);
        card.add(Box.createVerticalStrut(8));

        // Entrar como invitado
        JLabel lblInvitado = new JLabel("Entrar como invitado");
        lblInvitado.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblInvitado.setForeground(ColoresUDLAP.TEXTO_SECUNDARIO);
        lblInvitado.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblInvitado.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(lblInvitado);

        // Agregar card al fondo centrado
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 20, 20, 20);
        fondo.add(card, gbc);

        // ===== LOGICA DE AUTENTICACION =====

        btnIniciar.addActionListener(e -> {
            JTextField txtID = CampoTextoUDLAP.getTextField(panelID);
            JPasswordField txtPass = CampoTextoUDLAP.getPasswordField(panelPassword);

            String idText = txtID.getText().trim();
            String password = new String(txtPass.getPassword());
            if (idText.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Por favor, ingrese sus credenciales.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int id;
            try {
                id = Integer.parseInt(idText);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "ID inválido.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try (Connection con = ConexionSQLite.conectar()) {
                // Medicos
                String sqlMed = "SELECT Nombre, ApellidoPaterno FROM InformacionMedico WHERE ID = ? AND Contraseña = ?";
                try (PreparedStatement ps = con.prepareStatement(sqlMed)) {
                    ps.setInt(1, id);
                    ps.setString(2, password);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            String nombreCompleto = rs.getString("Nombre") + " " + rs.getString("ApellidoPaterno");
                            SesionUsuario.iniciarSesionMedico(id, nombreCompleto);
                            AuditLogDAO.registrar(id, "LOGIN", "Inicio de sesión médico: " + nombreCompleto);
                            new InterfazMedica(true, id).setVisible(true);
                            dispose();
                            return;
                        }
                    }
                }

                // Pacientes
                String sqlPac = "SELECT ID FROM InformacionAlumno WHERE ID = ? AND Contraseña = ?";
                try (PreparedStatement ps = con.prepareStatement(sqlPac)) {
                    ps.setInt(1, id);
                    ps.setString(2, password);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            SesionUsuario.iniciarSesion(id);
                            AuditLogDAO.registrar(id, "LOGIN", "Inicio de sesión paciente");
                            new InterfazMedica(false, id).setVisible(true);
                            dispose();
                            return;
                        }
                    }
                }
                // Fallo
                JOptionPane.showMessageDialog(this,
                        "ID o contraseña incorrectos.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this,
                        "Error en la base de datos:\n" + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        lblRecuperar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new RecuperarContrasenaFrame().setVisible(true);
            }
        });

        lblInvitado.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new InterfazMedica(false, -1).setVisible(true);
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        InicializadorBD.inicializar();
        RecordatorioCitasService.iniciar();
        SwingUtilities.invokeLater(() -> {
            TemaUDLAP.inicializar();
            new InterfazLogin().setVisible(true);
        });
    }
}
