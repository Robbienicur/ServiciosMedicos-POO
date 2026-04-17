package Inicio;

import Utilidades.*;
import Utilidades.ui.BotonUDLAP;
import Utilidades.ui.SidebarPanel;
import BaseDeDatos.ConexionSQLite;
import Consultas.PanelConsultaNueva;
import Emergencias.PanelLlamadaEmergencia;
import Emergencias.PanelReportarEmergencia;
import GestionCitas.NotificacionDAO;
import GestionCitas.PanelGestionCitas;
import GestionCitas.AgendaCitaFrame;
import GestionCitas.ModificarCitaFrame;
import GestionCitas.NotificacionCitasFrame;
import GestionEnfermedades.PanelHistorialMedico;
import GestionEnfermedades.PanelHistorialMedicoEditable;
import Justificantes.PanelJustificantesProvider;
import Justificantes.PanelMenuJustificantes;
import Justificantes.PanelJustificantesPacienteMenu;
import Registro.PanelRegistroPaciente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.sql.*;
import java.util.List;

public class InterfazMedica extends JFrame {
    private JPanel contentPanel;
    private final boolean esMedico;
    private final int userId;
    private String nombreUsuario;
    private PanelManager panelManager;
    private JLabel notificationIcon;
    private ImageIcon iconDefault, iconNew;
    private boolean hasNewNotification = false;
    private JLabel sectionTitle;
    private SidebarPanel sidebar;

    public InterfazMedica(boolean esMedico, int userId) {
        this.esMedico = esMedico;
        this.userId = userId;
        this.nombreUsuario = fetchNombreUsuario();
        loadNotificationIcons();
        initUI();
        checkNotifications();
    }

    private void loadNotificationIcons() {
        try {
            URL u1 = getClass().getResource("/icons/bell.png");
            URL u2 = getClass().getResource("/icons/bell_new.png");
            iconDefault = new ImageIcon(new ImageIcon(u1).getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
            iconNew = new ImageIcon(new ImageIcon(u2).getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
        } catch (Exception e) {
            iconDefault = new ImageIcon();
            iconNew = new ImageIcon();
        }
    }

    private void checkNotifications() {
        if (!esMedico) {
            hasNewNotification = NotificacionDAO.tieneNotificacionesNoLeidas(userId);
            System.out.println("¿Tiene notificaciones pendientes? " + hasNewNotification);
        } else if (esMedico) {
            // Doctors get notified about new justificante requests and emergencies
            try (Connection conn = ConexionSQLite.conectar()) {
                String sql = "SELECT COUNT(*) FROM Notificaciones WHERE idPaciente = ? AND estado = 'pendiente'";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setInt(1, userId);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) hasNewNotification = rs.getInt(1) > 0;
                    }
                }
            } catch (SQLException e) { /* ignore */ }
        }

        if (notificationIcon != null) {
            notificationIcon.setIcon(hasNewNotification ? iconNew : iconDefault);
        }
    }

    private void mostrarNotificaciones() {
        if (esMedico) {
            List<NotificacionDAO.Notificacion> lista = NotificacionDAO.obtenerNotificaciones(userId);
            if (lista.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "No hay nuevas notificaciones.",
                        "Notificaciones", JOptionPane.INFORMATION_MESSAGE);
            } else {
                StringBuilder sb = new StringBuilder("<html><b>Notificaciones:</b><br><br>");
                for (NotificacionDAO.Notificacion n : lista) {
                    sb.append("- ").append(n.servicio).append(" | ").append(n.fecha)
                      .append(" ").append(n.hora).append("<br>");
                    NotificacionDAO.marcarComoAtendida(n.idNotificacion);
                }
                sb.append("</html>");
                JOptionPane.showMessageDialog(this, sb.toString(),
                        "Notificaciones Médicas", JOptionPane.INFORMATION_MESSAGE);
            }
            hasNewNotification = false;
            if (notificationIcon != null) notificationIcon.setIcon(iconDefault);
            return;
        }

        List<NotificacionDAO.Notificacion> lista = NotificacionDAO.obtenerNotificaciones(userId);
        if (lista.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No hay nuevas notificaciones.",
                    "Notificaciones", JOptionPane.INFORMATION_MESSAGE);
            notificationIcon.setIcon(iconDefault);
        } else {
            for (NotificacionDAO.Notificacion n : lista) {
                new NotificacionCitasFrame(n.fecha, n.hora, n.servicio, String.valueOf(userId));
            }
            hasNewNotification = false;
            notificationIcon.setIcon(iconDefault);
        }
    }

    private void initUI() {
        setTitle("Servicios Médicos UDLAP");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // WEST: SidebarPanel
        String[] menuLabels = esMedico
                ? new String[]{"Inicio", "Registrar Paciente", "Consulta Nueva", "Historial Médico", "Justificantes", "Emergencias"}
                : new String[]{"Inicio", "Mis Citas", "Historial Médico", "Justificantes", "Reportar Emergencia"};

        String userRole = esMedico ? "Médico" : "Paciente";

        sidebar = new SidebarPanel(nombreUsuario, userRole, menuLabels, idx -> manejarClick(idx));
        add(sidebar, BorderLayout.WEST);

        // NORTH: Top bar
        add(crearTopPanel(), BorderLayout.NORTH);

        // CENTER: contentPanel with CardLayout
        contentPanel = new JPanel(new CardLayout());
        contentPanel.setBackground(ColoresUDLAP.FONDO_GENERAL);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));
        add(contentPanel, BorderLayout.CENTER);

        panelManager = new PanelManager(contentPanel);

        // Register panel0 (portada/home)
        panelManager.registerPanel(new PanelProvider() {
            public JPanel getPanel() { return new PanelPortada(); }
            public String getPanelName() { return "panel0"; }
        });

        registrarPaneles();
        panelManager.showPanel("panel0");
    }

    private JPanel crearTopPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, ColoresUDLAP.BORDE),
                BorderFactory.createEmptyBorder(0, 20, 0, 20)
        ));
        panel.setPreferredSize(new Dimension(0, 56));

        // Left: section title
        sectionTitle = new JLabel("Inicio");
        sectionTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        sectionTitle.setForeground(ColoresUDLAP.TEXTO_PRINCIPAL);

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        left.setOpaque(false);
        left.add(sectionTitle);

        // Right: notification bell + user name + logout button
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 8));
        right.setOpaque(false);

        notificationIcon = new JLabel(iconDefault);
        notificationIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        notificationIcon.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                mostrarNotificaciones();
            }
        });
        right.add(notificationIcon);

        JLabel lblNombre = new JLabel(nombreUsuario);
        lblNombre.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblNombre.setForeground(ColoresUDLAP.TEXTO_PRINCIPAL);
        right.add(lblNombre);

        BotonUDLAP btnCerrarSesion = BotonUDLAP.neutro("Cerrar Sesión");
        btnCerrarSesion.addActionListener(e -> {
            dispose();
            new InterfazLogin().setVisible(true);
        });
        right.add(btnCerrarSesion);

        panel.add(left, BorderLayout.WEST);
        panel.add(right, BorderLayout.EAST);
        return panel;
    }

    private void manejarClick(int idx) {
        if (idx == 0) {
            panelManager.showPanel("panel0");
            sectionTitle.setText("Inicio");
            return;
        }

        // Shift index by 1 since index 0 is "Inicio" (handled above)
        int shiftedIdx = idx - 1;

        String[] medicoKeys = {"formularioRegistro", "consultaNueva", "historialMedico", "justificantes",
                "llamadaEmergencia"};
        String[] pacienteKeys = {"panelGestionCitas", "historialMedico", "justificantesPaciente",
                "reportarEmergencia"};

        String[] medicoTitles = {"Registrar Paciente", "Consulta Nueva", "Historial Médico", "Justificantes", "Emergencias"};
        String[] pacienteTitles = {"Mis Citas", "Historial Médico", "Justificantes", "Reportar Emergencia"};

        if (esMedico) {
            if (shiftedIdx < medicoKeys.length) {
                panelManager.showPanel(medicoKeys[shiftedIdx]);
                sectionTitle.setText(medicoTitles[shiftedIdx]);
            }
        } else {
            if (shiftedIdx < pacienteKeys.length) {
                panelManager.showPanel(pacienteKeys[shiftedIdx]);
                sectionTitle.setText(pacienteTitles[shiftedIdx]);
            }
        }
    }

    private void registrarPaneles() {
        if (esMedico) {
            panelManager.registerPanel(new PanelRegistroPaciente());
            panelManager.registerPanel(new PanelConsultaNueva(userId, nombreUsuario));

            // Mostrar historial médico editable con campo ID fijo
            panelManager.registerPanel(new PanelProvider() {
                public JPanel getPanel() {
                    return new PanelHistorialMedicoEditable();
                }

                public String getPanelName() {
                    return "historialMedico";
                }
            });

            panelManager.registerPanel(new PanelProvider() {
                public JPanel getPanel() {
                    return new PanelMenuJustificantes(panelManager); // se pasa el PanelManager
                }
                public String getPanelName() {
                    return "justificantes";
                }
            });


            panelManager.registerPanel(new PanelLlamadaEmergencia(esMedico, userId));

           panelManager.registerPanel(new PanelProvider() {
    public JPanel getPanel() {
        return new Emergencias.FormularioAccidenteCompleto();
    }

    public String getPanelName() {
        return "reporteAccidente";
    }
});


        } else {
            // Paneles para el paciente (sin cambios)
            panelManager.registerPanel(new PanelHistorialMedico(userId));

            panelManager.registerPanel(new PanelProvider() {
                public JPanel getPanel() {
                    return new PanelGestionCitas(userId, panelManager);
                }

                public String getPanelName() {
                    return "panelGestionCitas";
                }
            });

            panelManager.registerPanel(new PanelProvider() {
            public JPanel getPanel() {
                return new PanelJustificantesPacienteMenu(panelManager);
            }

            public String getPanelName() {
                return "justificantesPaciente";
            }
        });


            panelManager.registerPanel(new PanelReportarEmergencia());

            panelManager.registerPanel(new PanelProvider() {
                public JPanel getPanel() {
                    return new AgendaCitaFrame(userId, panelManager);
                }

                public String getPanelName() {
                    return "agendarCita";
                }
            });

            panelManager.registerPanel(new PanelProvider() {
                public JPanel getPanel() {
                    return new ModificarCitaFrame(userId, panelManager);
                }

                public String getPanelName() {
                    return "modificarCita";
                }
            });
        }
    }

    private String fetchNombreUsuario() {
        String sql = esMedico
                ? "SELECT Nombre||' '||ApellidoPaterno FROM InformacionMedico WHERE ID=?"
                : "SELECT Nombre||' '||ApellidoPaterno FROM InformacionAlumno WHERE ID=?";
        try (Connection con = ConexionSQLite.conectar();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next())
                    return rs.getString(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return "Usuario";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InterfazMedica(true, 1).setVisible(true));
    }
}
