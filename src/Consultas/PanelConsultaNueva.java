package Consultas;

import Utilidades.PanelProvider;
import Utilidades.ColoresUDLAP;
import Utilidades.FormularioMedicoBase;
import Utilidades.ui.BotonUDLAP;
import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PanelConsultaNueva extends FormularioMedicoBase implements PanelProvider {

    private final JButton btnGuardar;
    private final JButton btnBuscar;
    private final JButton btnLimpiar;

    public PanelConsultaNueva(int idMedico, String nombreMedico) {
        super("Consulta Médica - Dr. " + nombreMedico, new String[] {
                "ID Paciente:", "Nombre", "Edad", "Correo",
                "Síntomas", "Medicamentos", "Diagnóstico",
                "Fecha Consulta", "Última Consulta", "Inicio de Síntomas",
                "Receta Médica"
        });

        // Inicializar fecha por defecto
        JTextField fecha = (JTextField) campos[7];
        fecha.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));

        // Botones
        btnGuardar = BotonUDLAP.primario("Guardar");
        btnBuscar = BotonUDLAP.secundario("Buscar");
        btnLimpiar = BotonUDLAP.neutro("Limpiar");

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        botones.setOpaque(false);

        botones.add(btnGuardar);
        botones.add(btnBuscar);
        botones.add(btnLimpiar);
        add(botones, BorderLayout.SOUTH);

        // Referencias reales
        JTextField[] camposConsulta = getCamposTexto(0, 1, 2, 3, 7, 8, 9);
        JTextArea receta = (JTextArea) campos[10];

        // Listener: ID Paciente (autollenado)
        camposConsulta[0].addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                String id = camposConsulta[0].getText().trim();
                if (!id.matches("\\d+"))
                    return;

                try (Connection conn = BaseDeDatos.ConexionSQLite.conectar()) {
                    String query = "SELECT Nombre, Correo, Edad FROM InformacionAlumno JOIN Registro USING(ID) WHERE ID = ?";
                    PreparedStatement ps = conn.prepareStatement(query);
                    ps.setInt(1, Integer.parseInt(id));
                    ResultSet rs = ps.executeQuery();

                    if (rs.next()) {
                        camposConsulta[1].setText(rs.getString("Nombre"));
                        camposConsulta[2].setText(rs.getString("Edad"));
                        camposConsulta[3].setText(rs.getString("Correo"));
                    } else {
                        JOptionPane.showMessageDialog(null, "No se encontró un paciente con ese ID.",
                                "Paciente no encontrado", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al buscar paciente:\n" + ex.getMessage(),
                            "Error de BD", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Acciones de botones
        btnGuardar.addActionListener(new GuardarConsulta(camposConsulta, receta));

        btnBuscar.addActionListener(new BuscarPaciente(new JTextField[] {
                (JTextField) campos[0], (JTextField) campos[1],
                (JTextField) campos[2], (JTextField) campos[3]
        }));

        btnLimpiar.addActionListener(e -> limpiarCampos());
    }

    @Override
    protected boolean isTextArea(int index) {
        return index >= 4 && index <= 6 || index == 10;
    }

    @Override
    public JPanel getPanel() {
        return this;
    }

    @Override
    public String getPanelName() {
        return "consultaNueva";
    }
}
