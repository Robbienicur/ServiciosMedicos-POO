package GestionCitas;

import Utilidades.ColoresUDLAP;
import Utilidades.PanelManager;
import Utilidades.ui.BotonUDLAP;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;

import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import BaseDeDatos.ConexionSQLite;

public class AgendaCitaFrame extends JPanel {

    private final int idPaciente;
    private final PanelManager panelManager;

    private JTextField campoNombre;
    private JTextField campoApellidos;
    private JTextField campoID;
    private JComboBox<String> comboServicio;
    private JComboBox<Integer> comboDia;
    private JComboBox<String> comboMes;
    private JComboBox<Integer> comboAño;
    private JComboBox<String> comboHora;
    private JComboBox<String> comboMinuto;
    private JLabel errorLabel;

    public AgendaCitaFrame(int idPaciente, PanelManager panelManager) {
        this.idPaciente = idPaciente;
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
        JLabel lblTitulo = new JLabel("Agendar Cita Médica", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(ColoresUDLAP.TEXTO_PRINCIPAL);
        lblTitulo.setBorder(BorderFactory.createCompoundBorder(
                new MatteBorder(0, 0, 2, 0, ColoresUDLAP.VERDE_PRIMARIO),
                BorderFactory.createEmptyBorder(0, 0, 12, 0)));
        add(lblTitulo, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;

        // ID
        gbc.gridx = 0;
        JLabel lblID = new JLabel("ID del Paciente:");
        lblID.setFont(labelFont);
        lblID.setForeground(ColoresUDLAP.TEXTO_SECUNDARIO);
        add(lblID, gbc);

        gbc.gridx = 1;
        campoID = new JTextField(String.valueOf(idPaciente), 20);
        campoID.setFont(fieldFont);
        campoID.setEditable(false);
        campoID.setBorder(getCampoBorde());
        add(campoID, gbc);

        // Nombre
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(labelFont);
        lblNombre.setForeground(ColoresUDLAP.TEXTO_SECUNDARIO);
        add(lblNombre, gbc);

        gbc.gridx = 1;
        campoNombre = new JTextField(20);
        campoNombre.setFont(fieldFont);
        campoNombre.setEditable(false);
        campoNombre.setBorder(getCampoBorde());
        add(campoNombre, gbc);

        // Apellidos
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel lblApellidos = new JLabel("Apellidos:");
        lblApellidos.setFont(labelFont);
        lblApellidos.setForeground(ColoresUDLAP.TEXTO_SECUNDARIO);
        add(lblApellidos, gbc);

        gbc.gridx = 1;
        campoApellidos = new JTextField(20);
        campoApellidos.setFont(fieldFont);
        campoApellidos.setEditable(false);
        campoApellidos.setBorder(getCampoBorde());
        add(campoApellidos, gbc);

        // Datos desde BD
        cargarDatosPersonales(idPaciente);

        // Servicio
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel lblServicio = new JLabel("Servicio:");
        lblServicio.setFont(labelFont);
        lblServicio.setForeground(ColoresUDLAP.TEXTO_SECUNDARIO);
        add(lblServicio, gbc);

        gbc.gridx = 1;
        comboServicio = new JComboBox<>(new String[] { "Consulta", "Enfermería", "Examen Médico" });
        comboServicio.setFont(fieldFont);
        comboServicio.setBackground(Color.WHITE);
        add(comboServicio, gbc);

        // Fecha
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel lblFecha = new JLabel("Fecha de la Cita:");
        lblFecha.setFont(labelFont);
        lblFecha.setForeground(ColoresUDLAP.TEXTO_SECUNDARIO);
        add(lblFecha, gbc);

        gbc.gridx = 1;
        JPanel panelFecha = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panelFecha.setOpaque(false);

        comboDia = new JComboBox<>();
        for (int d = 1; d <= 31; d++)
            comboDia.addItem(d);
        comboDia.setFont(fieldFont);

        comboMes = new JComboBox<>(new String[] {
                "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
                "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
        });
        comboMes.setFont(fieldFont);

        comboAño = new JComboBox<>();
        for (int a = LocalDate.now().getYear(); a <= 2030; a++)
            comboAño.addItem(a);
        comboAño.setFont(fieldFont);

        panelFecha.add(comboDia);
        panelFecha.add(comboMes);
        panelFecha.add(comboAño);
        add(panelFecha, gbc);

        // Hora
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel lblHora = new JLabel("Hora de la Cita:");
        lblHora.setFont(labelFont);
        lblHora.setForeground(ColoresUDLAP.TEXTO_SECUNDARIO);
        add(lblHora, gbc);

        gbc.gridx = 1;
        JPanel panelHora = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panelHora.setOpaque(false);

        comboHora = new JComboBox<>(new String[] {
                "08", "09", "10", "11", "12", "13", "14", "15",
                "16", "17", "18", "19", "20", "21"
        });
        comboHora.setFont(fieldFont);

        comboMinuto = new JComboBox<>(new String[] { "00", "30" });
        comboMinuto.setFont(fieldFont);

        panelHora.add(comboHora);
        panelHora.add(new JLabel(":"));
        panelHora.add(comboMinuto);
        add(panelHora, gbc);

        // Mensaje de error
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        errorLabel = new JLabel("", SwingConstants.CENTER);
        errorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        errorLabel.setForeground(ColoresUDLAP.ROJO_ERROR);
        add(errorLabel, gbc);

        // Botones
        gbc.gridy++;
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        panelBotones.setOpaque(false);

        JButton btnConfirmar = BotonUDLAP.primario("Confirmar");
        JButton btnCancelar = BotonUDLAP.neutro("Volver");

        btnConfirmar.addActionListener(e -> validarYConfirmarCita(idPaciente));
        btnCancelar.addActionListener(e -> panelManager.showPanel("panelGestionCitas"));

        panelBotones.add(btnConfirmar);
        panelBotones.add(btnCancelar);
        add(panelBotones, gbc);
    }

    private void cargarDatosPersonales(int id) {
        String sql = "SELECT Nombre, ApellidoPaterno, ApellidoMaterno FROM InformacionAlumno WHERE ID = ?";
        try (Connection conn = ConexionSQLite.conectar();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    campoNombre.setText(rs.getString("Nombre"));
                    campoApellidos.setText(rs.getString("ApellidoPaterno") + " " + rs.getString("ApellidoMaterno"));
                } else {
                    campoNombre.setText("Desconocido");
                    campoApellidos.setText("Desconocido");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void validarYConfirmarCita(int idPaciente) {
        String servicio = (String) comboServicio.getSelectedItem();
        int dia = (int) comboDia.getSelectedItem();
        int mes = comboMes.getSelectedIndex() + 1;
        int año = (int) comboAño.getSelectedItem();
        String hora = (String) comboHora.getSelectedItem();
        String minuto = (String) comboMinuto.getSelectedItem();

        // Validación de fecha usando la clase centralizada
        if (!ValidacionesCita.esFechaValida(dia, mes, año)) {
            errorLabel.setText("Fecha inválida (debe ser válida y futura)");
            return;
        }

        String fecha = String.format("%04d-%02d-%02d", año, mes, dia);
        String horaFinal = hora + ":" + minuto;

        try (Connection conn = ConexionSQLite.conectar()) {

            if (ValidacionesCita.estaCitaOcupada(fecha, horaFinal, servicio)) {
                Object[] opciones = { "Sí", "No" };
                int opcion = JOptionPane.showOptionDialog(
                        this,
                        "La cita ya está ocupada. ¿Deseas unirte a la lista de espera?",
                        "Horario ocupado",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        opciones,
                        opciones[0]
                );

                if (opcion == JOptionPane.YES_OPTION) {
                    new javax.swing.SwingWorker<Void, Void>() {
                        @Override
                        protected Void doInBackground() {
                            try {
                                ListaEsperaDAO.registrarEnEspera(String.valueOf(idPaciente), fecha, hora, servicio);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            return null;
                        }
                    }.execute();

                    errorLabel.setForeground(ColoresUDLAP.NARANJA_ACENTO);
                    errorLabel.setText("Registrado en lista de espera.");
                }
                return;
            }

            // Validación: ya tiene cita para ese servicio
            if (ValidacionesCita.pacienteYaTieneCitaParaServicio(idPaciente, servicio)) {
                errorLabel.setText("Ya tienes una cita para este servicio.");
                return;
            }

            // Inserción
            String sqlI = "INSERT INTO CitasMedicas(idPaciente,fecha,hora,servicio) VALUES(?,?,?,?)";
            try (PreparedStatement psI = conn.prepareStatement(sqlI)) {
                psI.setInt(1, idPaciente);
                psI.setString(2, fecha);
                psI.setString(3, horaFinal);
                psI.setString(4, servicio);
                psI.executeUpdate();
                errorLabel.setForeground(ColoresUDLAP.VERDE_PRIMARIO);
                errorLabel.setText("Cita agendada exitosamente.");
            }

        } catch (SQLException ex) {
            errorLabel.setForeground(ColoresUDLAP.ROJO_ERROR);
            errorLabel.setText("Error al registrar cita.");
            ex.printStackTrace();
        }
    }

    private Border getCampoBorde() {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ColoresUDLAP.BORDE),
                BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

}
