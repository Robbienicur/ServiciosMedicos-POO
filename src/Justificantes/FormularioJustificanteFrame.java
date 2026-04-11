package Justificantes;

import Utilidades.ColoresUDLAP;
import Utilidades.ui.BotonUDLAP;

import javax.swing.*;
import javax.swing.border.Border;

import BaseDeDatos.ConexionSQLite;
import Inicio.SesionUsuario;
import Utilidades.PanelManager;

import java.awt.*;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class FormularioJustificanteFrame extends JPanel {

    private final PanelManager panelManager;
    private JTextField idField, nombreField, motivoField;
    private JComboBox<String> diaInicio, mesInicio, anioInicio;
    private JComboBox<String> diaFin, mesFin, anioFin;
    private File archivoPDF = null;
    private JLabel mensajeLabel;

    public FormularioJustificanteFrame(PanelManager panelManager) {
        this.panelManager = panelManager;

        setLayout(new GridBagLayout());
        setBackground(ColoresUDLAP.FONDO_GENERAL);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 12);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);

        // Título
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel titulo = new JLabel("Solicitud de Justificante Médico", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titulo.setForeground(ColoresUDLAP.TEXTO_PRINCIPAL);
        add(titulo, gbc);

        gbc.gridwidth = 1;

        // ID
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel lblId = new JLabel("ID:");
        lblId.setFont(labelFont);
        lblId.setForeground(ColoresUDLAP.TEXTO_SECUNDARIO);
        add(lblId, gbc);
        gbc.gridx = 1;
        idField = new JTextField(20);
        idField.setFont(fieldFont);
        idField.setBorder(getCampoBorde());
        add(idField, gbc);

        // Nombre
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(labelFont);
        lblNombre.setForeground(ColoresUDLAP.TEXTO_SECUNDARIO);
        add(lblNombre, gbc);
        gbc.gridx = 1;
        nombreField = new JTextField(20);
        nombreField.setFont(fieldFont);
        nombreField.setBorder(getCampoBorde());
        add(nombreField, gbc);

        // Motivo
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel lblMotivo = new JLabel("Motivo:");
        lblMotivo.setFont(labelFont);
        lblMotivo.setForeground(ColoresUDLAP.TEXTO_SECUNDARIO);
        add(lblMotivo, gbc);
        gbc.gridx = 1;
        motivoField = new JTextField(20);
        motivoField.setFont(fieldFont);
        motivoField.setBorder(getCampoBorde());
        add(motivoField, gbc);

        // Fecha de inicio
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel lblInicio = new JLabel("Inicio de Reposo:");
        lblInicio.setFont(labelFont);
        lblInicio.setForeground(ColoresUDLAP.TEXTO_SECUNDARIO);
        add(lblInicio, gbc);
        gbc.gridx = 1;
        JPanel panelInicio = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panelInicio.setBackground(ColoresUDLAP.FONDO_GENERAL);
        diaInicio = new JComboBox<>(generarDias());
        mesInicio = new JComboBox<>(generarMeses());
        anioInicio = new JComboBox<>(generarAnios());
        diaInicio.setFont(fieldFont);
        mesInicio.setFont(fieldFont);
        anioInicio.setFont(fieldFont);
        panelInicio.add(diaInicio);
        panelInicio.add(mesInicio);
        panelInicio.add(anioInicio);
        add(panelInicio, gbc);

        // Fecha de fin
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel lblFin = new JLabel("Fin de Reposo:");
        lblFin.setFont(labelFont);
        lblFin.setForeground(ColoresUDLAP.TEXTO_SECUNDARIO);
        add(lblFin, gbc);
        gbc.gridx = 1;
        JPanel panelFin = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panelFin.setBackground(ColoresUDLAP.FONDO_GENERAL);
        diaFin = new JComboBox<>(generarDias());
        mesFin = new JComboBox<>(generarMeses());
        anioFin = new JComboBox<>(generarAnios());
        diaFin.setFont(fieldFont);
        mesFin.setFont(fieldFont);
        anioFin.setFont(fieldFont);
        panelFin.add(diaFin);
        panelFin.add(mesFin);
        panelFin.add(anioFin);
        add(panelFin, gbc);

        // Label de estado
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        mensajeLabel = new JLabel("", SwingConstants.CENTER);
        mensajeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        mensajeLabel.setForeground(ColoresUDLAP.ROJO_ERROR);
        add(mensajeLabel, gbc);
        gbc.gridwidth = 1;

        // Botones
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        panelBotones.setBackground(ColoresUDLAP.FONDO_GENERAL);

        JButton subirPDF = BotonUDLAP.secundario("Subir Receta");
        JButton btnGuardar = BotonUDLAP.primario("Guardar");
        JButton btnRegresar = BotonUDLAP.neutro("Regresar");
        btnRegresar.addActionListener(e -> regresarAMenuJustificantes());

        subirPDF.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                archivoPDF = chooser.getSelectedFile();
                mensajeLabel.setForeground(ColoresUDLAP.VERDE_PRIMARIO);
                mensajeLabel.setText("Archivo cargado: " + archivoPDF.getName());
            }
        });

        btnGuardar.addActionListener(e -> guardarJustificante());

        panelBotones.add(btnRegresar);
        panelBotones.add(subirPDF);
        panelBotones.add(btnGuardar);
        add(panelBotones, gbc);
    }

    private void guardarJustificante() {
        String id = idField.getText().trim();
        String nombre = nombreField.getText().trim();
        String motivo = motivoField.getText().trim();

        if (id.isEmpty() || nombre.isEmpty() || motivo.isEmpty()) {
            mensajeLabel.setForeground(ColoresUDLAP.ROJO_ERROR);
            mensajeLabel.setText("Completa todos los campos obligatorios.");
            return;
        }

        LocalDate inicio = construirFecha(diaInicio, mesInicio, anioInicio);
        LocalDate fin = construirFecha(diaFin, mesFin, anioFin);

        if (!inicio.isAfter(LocalDate.now())) {
            mensajeLabel.setForeground(ColoresUDLAP.ROJO_ERROR);
            mensajeLabel.setText("La fecha de inicio debe ser posterior a hoy.");
            return;
        }

        if (inicio.isAfter(fin)) {
            mensajeLabel.setForeground(ColoresUDLAP.ROJO_ERROR);
            mensajeLabel.setText("La fecha de inicio no puede ser posterior a la de fin.");
            return;
        }

        Justificante nuevo = new Justificante(id, nombre, motivo, inicio, fin, "", archivoPDF);
        boolean exito = JustificanteDAO.guardarJustificante(nuevo);

        if (exito) {
            mensajeLabel.setForeground(ColoresUDLAP.VERDE_PRIMARIO);
            mensajeLabel.setText("Justificante guardado correctamente.");
            limpiarFormulario();

            int folioNuevo = obtenerUltimoFolioInsertado();

            if (folioNuevo > 0) {
                CorreosProfesoresPanel panelCorreos = new CorreosProfesoresPanel(folioNuevo, panelManager);
                panelManager.mostrarPanelPersonalizado(panelCorreos);

            } else {
                mensajeLabel.setForeground(ColoresUDLAP.ROJO_ERROR);
                mensajeLabel.setText("No se pudo obtener el folio para agregar correos.");
            }

        } else {
            mensajeLabel.setForeground(ColoresUDLAP.ROJO_ERROR);
            mensajeLabel.setText("Error al guardar justificante.");
        }
    }

    private String[] generarDias() {
        String[] d = new String[31];
        for (int i = 1; i <= 31; i++)
            d[i - 1] = String.valueOf(i);
        return d;
    }

    private String[] generarMeses() {
        return new String[] {
                "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
                "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
        };
    }

    private String[] generarAnios() {
        int base = LocalDate.now().getYear();
        String[] a = new String[6];
        for (int i = 0; i < 6; i++)
            a[i] = String.valueOf(base + i);
        return a;
    }

    private LocalDate construirFecha(JComboBox<String> dia, JComboBox<String> mes, JComboBox<String> anio) {
        try {
            int d = Integer.parseInt((String) dia.getSelectedItem());
            int m = mes.getSelectedIndex() + 1;
            int y = Integer.parseInt((String) anio.getSelectedItem());
            return LocalDate.of(y, m, d);
        } catch (Exception e) {
            mensajeLabel.setForeground(ColoresUDLAP.ROJO_ERROR);
            mensajeLabel.setText("Fecha inválida. Verifique el día, mes y año seleccionados.");
            return null;
        }
    }

    private Border getCampoBorde() {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ColoresUDLAP.BORDE),
                BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    public void setValoresDesdeSesion() {
        int idActual = SesionUsuario.getPacienteActual();
        idField.setText(String.valueOf(idActual));
        idField.setEditable(false);

        try (Connection conn = ConexionSQLite.conectar();
                PreparedStatement ps = conn.prepareStatement(
                        "SELECT Nombre, ApellidoPaterno, ApellidoMaterno FROM InformacionAlumno WHERE ID = ?")) {

            ps.setInt(1, idActual);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String nombreCompleto = rs.getString("Nombre") + " " +
                        rs.getString("ApellidoPaterno") + " " +
                        rs.getString("ApellidoMaterno");
                nombreField.setText(nombreCompleto);
                nombreField.setEditable(false);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            nombreField.setText("Error al cargar nombre");
        }
    }

    private void regresarAMenuJustificantes() {
        panelManager.showPanel("justificantesPaciente");
    }

    private int obtenerUltimoFolioInsertado() {
        try (Connection conn = ConexionSQLite.conectar();
                PreparedStatement pst = conn.prepareStatement("SELECT MAX(folio) FROM JustificantePaciente")) {
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private void limpiarFormulario() {
        motivoField.setText("");
        archivoPDF = null;
        mensajeLabel.setText("");
    }
}
