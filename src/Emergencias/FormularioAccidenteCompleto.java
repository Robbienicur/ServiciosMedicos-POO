package Emergencias;

import Utilidades.ColoresUDLAP;
import Utilidades.ui.BotonUDLAP;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class FormularioAccidenteCompleto extends JPanel {

    private JTextField campoIDEmergencia, campoIDPaciente, campoNombrePaciente,
            campoCorreoPaciente, campoTelefonoPaciente,
            campoPresionArterial, campoRitmoCardiaco, campoRitmoRespiratorio,
            campoIDContacto, campoNombreContacto, campoCorreoContacto, campoTelefonoContacto;
    private JComboBox<String> comboGenero, comboDia, comboMes, comboAnio, comboHora, comboMinuto, comboEstado;
    private JTextArea areaDescripcion, areaObservaciones;
    private JRadioButton rbAlerta, rbConsciente, rbInconsciente;
    private ButtonGroup grupoConsciencia;
    private JLabel mensajeLabel;

    public FormularioAccidenteCompleto() {
        setLayout(new BorderLayout());
        setBackground(ColoresUDLAP.FONDO_GENERAL);

        JPanel contenido = new JPanel(new GridBagLayout());
        contenido.setBackground(ColoresUDLAP.FONDO_GENERAL);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font fontLabel = new Font("Segoe UI", Font.PLAIN, 12);
        Font fontField = new Font("Segoe UI", Font.PLAIN, 14);

        // Título
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel lblTitulo = new JLabel("Reporte de Accidente", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(ColoresUDLAP.TEXTO_PRINCIPAL);
        lblTitulo.setBorder(BorderFactory.createCompoundBorder(
                new MatteBorder(0, 0, 2, 0, ColoresUDLAP.VERDE_PRIMARIO),
                BorderFactory.createEmptyBorder(0, 0, 12, 0)));
        contenido.add(lblTitulo, gbc);
        gbc.gridwidth = 1;

        int row = 1;
        campoIDEmergencia = crearCampo(contenido, gbc, row++, "ID Emergencia:", fontLabel, fontField);
        campoIDPaciente = crearCampo(contenido, gbc, row++, "ID Paciente:", fontLabel, fontField);

        campoIDPaciente.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String textoID = campoIDPaciente.getText().trim();
                if (!textoID.matches("\\d+")) {
                    campoNombrePaciente.setText("ID inválido");
                    return;
                }

                int id = Integer.parseInt(textoID);
                String sql = "SELECT Nombre, ApellidoPaterno, ApellidoMaterno FROM InformacionAlumno WHERE ID = ?";

                try (Connection conn = BaseDeDatos.ConexionSQLite.conectar();
                        PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setInt(1, id);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            campoNombrePaciente.setText(
                                    rs.getString("Nombre") + " " +
                                            rs.getString("ApellidoPaterno") + " " +
                                            rs.getString("ApellidoMaterno"));
                        } else {
                            campoNombrePaciente.setText("No encontrado");
                        }
                    }
                } catch (SQLException ex) {
                    campoNombrePaciente.setText("Error BD");
                    ex.printStackTrace();
                }
            }
        });

        campoNombrePaciente = crearCampo(contenido, gbc, row++, "Nombre Paciente:", fontLabel, fontField);
        campoNombrePaciente.setEditable(false);

        campoCorreoPaciente = crearCampo(contenido, gbc, row++, "Correo Paciente:", fontLabel, fontField);
        campoTelefonoPaciente = crearCampo(contenido, gbc, row++, "Teléfono Paciente:", fontLabel, fontField);

        comboDia = new JComboBox<>();
        comboMes = new JComboBox<>(new String[] {
                "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
                "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" });
        comboAnio = new JComboBox<>();
        comboHora = new JComboBox<>();
        comboMinuto = new JComboBox<>();
        for (int i = 1; i <= 31; i++)
            comboDia.addItem(String.valueOf(i));
        for (int y = LocalDate.now().getYear(); y <= 2030; y++)
            comboAnio.addItem(String.valueOf(y));
        for (int h = 0; h < 24; h++)
            comboHora.addItem(String.format("%02d", h));
        for (int m = 0; m < 60; m++)
            comboMinuto.addItem(String.format("%02d", m));

        gbc.gridx = 0;
        gbc.gridy = row;
        JLabel lblFecha = new JLabel("Fecha del Accidente:");
        lblFecha.setFont(fontLabel);
        lblFecha.setForeground(ColoresUDLAP.TEXTO_SECUNDARIO);
        contenido.add(lblFecha, gbc);
        gbc.gridx = 1;
        JPanel panelFecha = new JPanel();
        panelFecha.setOpaque(false);
        panelFecha.add(comboDia);
        panelFecha.add(comboMes);
        panelFecha.add(comboAnio);
        panelFecha.add(comboHora);
        panelFecha.add(comboMinuto);
        contenido.add(panelFecha, gbc);
        row++;

        campoPresionArterial = crearCampo(contenido, gbc, row++, "Presión Arterial:", fontLabel, fontField);
        campoRitmoCardiaco = crearCampo(contenido, gbc, row++, "Ritmo Cardíaco:", fontLabel, fontField);
        campoRitmoRespiratorio = crearCampo(contenido, gbc, row++, "Ritmo Respiratorio:", fontLabel, fontField);

        gbc.gridx = 0;
        gbc.gridy = row;
        JLabel lblCons = new JLabel("Consciencia:");
        lblCons.setFont(fontLabel);
        lblCons.setForeground(ColoresUDLAP.TEXTO_SECUNDARIO);
        contenido.add(lblCons, gbc);
        gbc.gridx = 1;
        rbAlerta = new JRadioButton("Alerta");
        rbConsciente = new JRadioButton("Consciente");
        rbInconsciente = new JRadioButton("Inconsciente");
        grupoConsciencia = new ButtonGroup();
        grupoConsciencia.add(rbAlerta);
        grupoConsciencia.add(rbConsciente);
        grupoConsciencia.add(rbInconsciente);
        JPanel panelCons = new JPanel();
        panelCons.setOpaque(false);
        panelCons.add(rbAlerta);
        panelCons.add(rbConsciente);
        panelCons.add(rbInconsciente);
        contenido.add(panelCons, gbc);
        row++;

        comboGenero = new JComboBox<>(new String[] { "Masculino", "Femenino", "Otro" });
        gbc.gridx = 0;
        gbc.gridy = row;
        JLabel lblGenero = new JLabel("Género:");
        lblGenero.setFont(fontLabel);
        lblGenero.setForeground(ColoresUDLAP.TEXTO_SECUNDARIO);
        contenido.add(lblGenero, gbc);
        gbc.gridx = 1;
        contenido.add(comboGenero, gbc);
        row++;

        areaDescripcion = new JTextArea(3, 20);
        areaDescripcion.setFont(fontField);
        gbc.gridy = row;
        gbc.gridx = 0;
        JLabel lblDesc = new JLabel("Descripción:");
        lblDesc.setFont(fontLabel);
        lblDesc.setForeground(ColoresUDLAP.TEXTO_SECUNDARIO);
        contenido.add(lblDesc, gbc);
        gbc.gridx = 1;
        contenido.add(new JScrollPane(areaDescripcion), gbc);
        row++;

        areaObservaciones = new JTextArea(3, 20);
        areaObservaciones.setFont(fontField);
        gbc.gridy = row;
        gbc.gridx = 0;
        JLabel lblObs = new JLabel("Observaciones:");
        lblObs.setFont(fontLabel);
        lblObs.setForeground(ColoresUDLAP.TEXTO_SECUNDARIO);
        contenido.add(lblObs, gbc);
        gbc.gridx = 1;
        contenido.add(new JScrollPane(areaObservaciones), gbc);
        row++;

        campoIDContacto = crearCampo(contenido, gbc, row++, "ID Contacto:", fontLabel, fontField);

        campoIDContacto.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String textoID = campoIDContacto.getText().trim();

                if (!textoID.matches("\\d+")) {
                    campoNombreContacto.setText("ID inválido");

                    return;
                }

                int id = Integer.parseInt(textoID);
                String sql = "SELECT Nombre, ApellidoPaterno, ApellidoMaterno FROM InformacionAlumno WHERE ID = ?";

                try (Connection conn = BaseDeDatos.ConexionSQLite.conectar();
                        PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setInt(1, id);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            campoNombreContacto.setText(
                                    rs.getString("Nombre") + " " +
                                            rs.getString("ApellidoPaterno") + " " +
                                            rs.getString("ApellidoMaterno"));

                        } else {
                            campoNombreContacto.setText("No encontrado");

                        }
                    }
                } catch (SQLException ex) {
                    campoNombreContacto.setText("Error BD");
                    ex.printStackTrace();
                }
            }
        });

        campoNombreContacto = crearCampo(contenido, gbc, row++, "Nombre Contacto:", fontLabel, fontField);

        campoNombreContacto.setEditable(true);

        campoCorreoContacto = crearCampo(contenido, gbc, row++, "Correo Contacto:", fontLabel, fontField);
        campoTelefonoContacto = crearCampo(contenido, gbc, row++, "Teléfono Contacto:", fontLabel, fontField);

        comboEstado = new JComboBox<>(new String[] { "Pendiente", "Completo", "Transferido" });
        gbc.gridx = 0;
        gbc.gridy = row;
        JLabel lblEstado = new JLabel("Estado de Emergencia:");
        lblEstado.setFont(fontLabel);
        lblEstado.setForeground(ColoresUDLAP.TEXTO_SECUNDARIO);
        contenido.add(lblEstado, gbc);
        gbc.gridx = 1;
        contenido.add(comboEstado, gbc);
        row++;

        mensajeLabel = new JLabel("", SwingConstants.CENTER);
        mensajeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        mensajeLabel.setForeground(ColoresUDLAP.ROJO_ERROR);
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        contenido.add(mensajeLabel, gbc);

        JButton btnRegistrar = BotonUDLAP.primario("Registrar Accidente");
        btnRegistrar.addActionListener(e -> validarFormulario());
        gbc.gridy = row;
        contenido.add(btnRegistrar, gbc);

        JScrollPane scrollPane = new JScrollPane(contenido);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);
    }

    private JTextField crearCampo(JPanel panel, GridBagConstraints gbc, int row, String label, Font fontLabel, Font fontField) {
        gbc.gridx = 0;
        gbc.gridy = row;
        JLabel lbl = new JLabel(label);
        lbl.setFont(fontLabel);
        lbl.setForeground(ColoresUDLAP.TEXTO_SECUNDARIO);
        panel.add(lbl, gbc);
        gbc.gridx = 1;
        JTextField field = new JTextField(20);
        field.setFont(fontField);
        panel.add(field, gbc);
        return field;
    }

    private void validarFormulario() {
        try {
            if (comboDia.getSelectedItem() == null || comboMes.getSelectedItem() == null
                    || comboAnio.getSelectedItem() == null) {
                mostrarError("Seleccione una fecha válida.");
                return;
            }

            int dia, anio;
            try {
                dia = Integer.parseInt((String) comboDia.getSelectedItem());
                anio = Integer.parseInt((String) comboAnio.getSelectedItem());
            } catch (NumberFormatException ex) {
                mostrarError("Fecha inválida.");
                return;
            }

            String error = ValidadorAccidente.validarCampos(
                    campoIDEmergencia.getText(),
                    campoIDPaciente.getText(),
                    campoNombrePaciente.getText(),
                    campoCorreoPaciente.getText(),
                    campoTelefonoPaciente.getText(),
                    campoPresionArterial.getText(),
                    campoRitmoCardiaco.getText(),
                    campoRitmoRespiratorio.getText(),
                    grupoConsciencia,
                    areaDescripcion.getText(),
                    campoCorreoContacto.getText(),
                    areaObservaciones.getText(),
                    dia,
                    comboMes.getSelectedIndex() + 1,
                    anio,
                    campoIDContacto.getText(),
                    campoNombreContacto.getText(),
                    campoTelefonoContacto.getText());

            if (error != null) {
                mostrarError(error);
                return;
            }

            if (ValidadorAccidente.idEmergenciaExiste(campoIDEmergencia.getText())) {
                mostrarError("Ya se encuentra registrado ese ID de emergencia, utilice otro.");
                return;
            }

            guardarEnBaseDeDatos();

        } catch (Exception ex) {
            mostrarError("Error inesperado: " + ex.getMessage());
        }
    }

    private void mostrarError(String msg) {
        mensajeLabel.setForeground(ColoresUDLAP.ROJO_ERROR);
        mensajeLabel.setText(msg);
    }

    private void guardarEnBaseDeDatos() {
        try {
            int idEmergencia = Integer.parseInt(campoIDEmergencia.getText().trim());

            int dia = Integer.parseInt((String) comboDia.getSelectedItem());
            int mes = comboMes.getSelectedIndex() + 1;
            int anio = Integer.parseInt((String) comboAnio.getSelectedItem());
            String horaStr = (String) comboHora.getSelectedItem();
            String minutoStr = (String) comboMinuto.getSelectedItem();
            int hora = Integer.parseInt(horaStr != null ? horaStr : "0");
            int minuto = Integer.parseInt(minutoStr != null ? minutoStr : "0");

            String fecha = String.format("%04d-%02d-%02d %02d:%02d:00", anio, mes, dia, hora, minuto);
            String genero = (String) comboGenero.getSelectedItem();
            String presion = campoPresionArterial.getText().trim();
            int ritmoCardiaco = Integer.parseInt(campoRitmoCardiaco.getText().trim());
            int ritmoRespiratorio = Integer.parseInt(campoRitmoRespiratorio.getText().trim());

            String consciencia = rbAlerta.isSelected() ? "Alerta"
                    : rbConsciente.isSelected() ? "Consciente"
                            : "Inconsciente";

            String descripcion = areaDescripcion.getText().trim();
            String observaciones = areaObservaciones.getText().trim();

            String idCont = campoIDContacto.getText().trim();
            Integer idContacto = (!idCont.isEmpty() && idCont.matches("\\d+")) ? Integer.parseInt(idCont) : null;

            String nombreContacto = campoNombreContacto.getText().trim();
            String apellidosContacto = nombreContacto.contains(" ") ? nombreContacto.split(" ", 2)[1] : null;
            String correoContacto = campoCorreoContacto.getText().trim();
            String telefonoContacto = campoTelefonoContacto.getText().trim();
            String estado = (String) comboEstado.getSelectedItem();

            Accidente accidente = new Accidente(
                    idEmergencia, fecha, genero, presion, ritmoCardiaco, ritmoRespiratorio,
                    consciencia, descripcion, observaciones, idContacto,
                    nombreContacto, apellidosContacto, correoContacto,
                    telefonoContacto, estado);

            boolean exito = AccidenteDB.guardarAccidenteCompleto(accidente);

            if (exito) {
                mensajeLabel.setForeground(ColoresUDLAP.VERDE_PRIMARIO);
                mensajeLabel.setText("Accidente registrado correctamente.");
                limpiarCampos();
            } else {
                mensajeLabel.setForeground(ColoresUDLAP.ROJO_ERROR);
                mensajeLabel.setText("No se pudo registrar el accidente.");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            mensajeLabel.setForeground(ColoresUDLAP.ROJO_ERROR);
            mensajeLabel.setText("Error inesperado: " + ex.getMessage());
        }
    }

    private void limpiarCampos() {
        campoIDEmergencia.setText("");
        campoIDPaciente.setText("");
        campoNombrePaciente.setText("");
        campoCorreoPaciente.setText("");
        campoTelefonoPaciente.setText("");
        campoPresionArterial.setText("");
        campoRitmoCardiaco.setText("");
        campoRitmoRespiratorio.setText("");
        campoIDContacto.setText("");
        campoNombreContacto.setText("");
        campoCorreoContacto.setText("");
        campoTelefonoContacto.setText("");
        areaDescripcion.setText("");
        areaObservaciones.setText("");

        comboGenero.setSelectedIndex(0);
        comboDia.setSelectedIndex(0);
        comboMes.setSelectedIndex(0);
        comboAnio.setSelectedIndex(0);
        comboHora.setSelectedIndex(0);
        comboMinuto.setSelectedIndex(0);
        comboEstado.setSelectedIndex(0);

        grupoConsciencia.clearSelection();
    }

}
