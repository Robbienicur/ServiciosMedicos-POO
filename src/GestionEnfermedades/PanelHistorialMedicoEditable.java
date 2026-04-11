package GestionEnfermedades;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import BaseDeDatos.ConexionSQLite;
import Utilidades.ColoresUDLAP;
import Utilidades.ui.BotonUDLAP;

public class PanelHistorialMedicoEditable extends JPanel {
    private JTextField campoID;
    private JPanel panelDatos;
    private JPanel panelHistorial;

    public PanelHistorialMedicoEditable() {
        setLayout(new BorderLayout());
        setBackground(ColoresUDLAP.FONDO_GENERAL);

        // Título
        JLabel titulo = new JLabel("Historial Médico por Paciente", SwingConstants.LEFT);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titulo.setForeground(ColoresUDLAP.TEXTO_PRINCIPAL);
        titulo.setBorder(BorderFactory.createCompoundBorder(
                new MatteBorder(0, 0, 2, 0, ColoresUDLAP.VERDE_PRIMARIO),
                BorderFactory.createEmptyBorder(16, 24, 16, 24)));

        // Parte superior con campo para ID
        JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 8));
        panelTop.setBackground(ColoresUDLAP.FONDO_GENERAL);

        JLabel label = new JLabel("ID del Paciente:");
        label.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        label.setForeground(ColoresUDLAP.TEXTO_SECUNDARIO);

        campoID = new JTextField(10);
        campoID.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JButton botonBuscar = BotonUDLAP.secundario("Consultar");

        panelTop.add(label);
        panelTop.add(campoID);
        panelTop.add(botonBuscar);

        JPanel encabezado = new JPanel(new BorderLayout());
        encabezado.setOpaque(false);
        encabezado.add(titulo, BorderLayout.NORTH);
        encabezado.add(panelTop, BorderLayout.CENTER);
        add(encabezado, BorderLayout.NORTH);

        // Panel donde irán los resultados
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
        panelCentral.setBackground(ColoresUDLAP.FONDO_GENERAL);
        panelCentral.setBorder(BorderFactory.createEmptyBorder(8, 24, 16, 24));

        panelDatos = new JPanel();
        panelDatos.setBackground(ColoresUDLAP.FONDO_CARD);
        panelDatos.setLayout(new BoxLayout(panelDatos, BoxLayout.Y_AXIS));

        panelHistorial = new JPanel();
        panelHistorial.setBackground(ColoresUDLAP.FONDO_GENERAL);
        panelHistorial.setLayout(new BoxLayout(panelHistorial, BoxLayout.Y_AXIS));

        panelCentral.add(panelDatos);
        panelCentral.add(Box.createVerticalStrut(10));
        panelCentral.add(panelHistorial);

        JScrollPane scroll = new JScrollPane(panelCentral);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(ColoresUDLAP.FONDO_GENERAL);
        add(scroll, BorderLayout.CENTER);

        // Acción del botón
        botonBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idTexto = campoID.getText().trim();
                if (!idTexto.matches("\\d+")) {
                    mostrarMensaje("Ingrese un ID numérico válido.");
                    return;
                }
                int idPaciente = Integer.parseInt(idTexto);
                mostrarExpediente(idPaciente);
            }
        });
    }

    private void mostrarExpediente(int id) {
        panelDatos.removeAll();
        panelHistorial.removeAll();

        try (Connection conn = ConexionSQLite.conectar()) {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT Nombre, ApellidoPaterno, ApellidoMaterno, Correo FROM InformacionAlumno WHERE ID = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                panelDatos.add(crearLabelDato("ID:", String.valueOf(id)));
                panelDatos.add(crearLabelDato("Nombre:", rs.getString("Nombre") + " " + rs.getString("ApellidoPaterno")
                        + " " + rs.getString("ApellidoMaterno")));
                panelDatos.add(crearLabelDato("Correo:", rs.getString("Correo")));
            } else {
                mostrarMensaje("No se encontró un alumno con ID: " + id);
                revalidate();
                repaint();
                return;
            }

            // Datos clínicos del paciente desde la tabla Registro
            PreparedStatement psRegistro = conn.prepareStatement(
                    "SELECT Edad, Altura, Peso, EnfermedadesPreexistentes, Medicacion, Alergias FROM Registro WHERE ID = ?");
            psRegistro.setInt(1, id);
            ResultSet rsRegistro = psRegistro.executeQuery();

            if (rsRegistro.next()) {
                panelDatos.add(crearLabelDato("Edad:", String.valueOf(rsRegistro.getInt("Edad"))));
                panelDatos.add(crearLabelDato("Altura:", rsRegistro.getDouble("Altura") + " cm"));
                panelDatos.add(crearLabelDato("Peso:", rsRegistro.getDouble("Peso") + " kg"));
                panelDatos.add(crearLabelDato("Enfermedades Preexistentes:", rsRegistro.getString("EnfermedadesPreexistentes")));
                panelDatos.add(crearLabelDato("Medicación:", rsRegistro.getString("Medicacion")));
                panelDatos.add(crearLabelDato("Alergias:", rsRegistro.getString("Alergias")));
            } else {
                panelDatos.add(crearLabelSimple("No hay datos clínicos registrados para este paciente."));
            }

            // Consultar historial
            PreparedStatement ps2 = conn.prepareStatement(
                    "SELECT FechaConsulta, Diagnostico, Sintomas, Medicamentos, Receta FROM Consultas WHERE IDPaciente = ? ORDER BY FechaConsulta DESC");
            ps2.setInt(1, id);
            ResultSet rs2 = ps2.executeQuery();

            boolean tieneConsultas = false;
            while (rs2.next()) {
                tieneConsultas = true;
                JTextArea area = new JTextArea();
                area.setEditable(false);
                area.setFont(new Font("Segoe UI", Font.PLAIN, 13));
                area.setForeground(ColoresUDLAP.TEXTO_PRINCIPAL);
                area.setBackground(ColoresUDLAP.FONDO_CARD);
                area.setText(
                        "Fecha: " + rs2.getString("FechaConsulta") +
                                "\nDiagnóstico: " + rs2.getString("Diagnostico") +
                                "\nSíntomas: " + rs2.getString("Sintomas") +
                                "\nMedicamentos: " + rs2.getString("Medicamentos") +
                                "\nReceta: " + rs2.getString("Receta"));
                area.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(ColoresUDLAP.BORDE),
                        BorderFactory.createEmptyBorder(8, 10, 8, 10)));
                panelHistorial.add(area);
                panelHistorial.add(Box.createVerticalStrut(10));
            }

            if (!tieneConsultas) {
                panelHistorial.add(crearLabelSimple("Este paciente no tiene historial médico registrado."));
            }

        } catch (SQLException ex) {
            mostrarMensaje("Error al obtener el expediente:\n" + ex.getMessage());
        }

        revalidate();
        repaint();
    }

    private JLabel crearLabelDato(String etiqueta, String valor) {
        JLabel lbl = new JLabel(etiqueta + "  " + (valor != null ? valor : "-"));
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lbl.setForeground(ColoresUDLAP.TEXTO_PRINCIPAL);
        lbl.setBorder(BorderFactory.createEmptyBorder(3, 8, 3, 8));
        return lbl;
    }

    private JLabel crearLabelSimple(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        lbl.setForeground(ColoresUDLAP.TEXTO_SECUNDARIO);
        lbl.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        return lbl;
    }

    private void mostrarMensaje(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Historial Médico", JOptionPane.INFORMATION_MESSAGE);
    }
}
