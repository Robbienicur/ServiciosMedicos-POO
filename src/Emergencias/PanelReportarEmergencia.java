package Emergencias;

import javax.swing.*;
import java.awt.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import Utilidades.ColoresUDLAP;
import Utilidades.PanelProvider;
import Inicio.SesionUsuario;

public class PanelReportarEmergencia extends JPanel implements PanelProvider {

    private JTextArea descripcion;
    private JTextField ubicacion;
    private JComboBox<String> tipoEmergencia;
    private JComboBox<String> gravedad;

    public PanelReportarEmergencia() {
        setLayout(new BorderLayout());
        setBackground(ColoresUDLAP.BLANCO);

        // Titulo
        JLabel titulo = new JLabel("Reportar Emergencia", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setForeground(ColoresUDLAP.ROJO_SOLIDO);
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(titulo, BorderLayout.NORTH);

        // Panel central con formulario
        JPanel centro = new JPanel(new GridBagLayout());
        centro.setBackground(ColoresUDLAP.BLANCO);
        centro.setBorder(BorderFactory.createEmptyBorder(10, 60, 10, 60));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Font fieldFont = new Font("Arial", Font.PLAIN, 14);

        // Tipo de emergencia
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lblTipo = new JLabel("Tipo de Emergencia:");
        lblTipo.setFont(labelFont);
        centro.add(lblTipo, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        tipoEmergencia = new JComboBox<>(new String[]{
                "Medica", "Accidente", "Seguridad", "Otra"
        });
        tipoEmergencia.setFont(fieldFont);
        centro.add(tipoEmergencia, gbc);

        // Gravedad
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        JLabel lblGravedad = new JLabel("Gravedad:");
        lblGravedad.setFont(labelFont);
        centro.add(lblGravedad, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gravedad = new JComboBox<>(new String[]{
                "Rojo", "Naranja", "Amarillo", "Verde", "Azul"
        });
        gravedad.setFont(fieldFont);
        centro.add(gravedad, gbc);

        // Ubicacion
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        JLabel lblUbicacion = new JLabel("Ubicacion (edificio, salon):");
        lblUbicacion.setFont(labelFont);
        centro.add(lblUbicacion, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        ubicacion = new JTextField(25);
        ubicacion.setFont(fieldFont);
        ubicacion.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ColoresUDLAP.GRIS_CLARO),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        centro.add(ubicacion, gbc);

        // Descripcion
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        JLabel lblDescripcion = new JLabel("Descripcion:");
        lblDescripcion.setFont(labelFont);
        centro.add(lblDescripcion, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        descripcion = new JTextArea(5, 25);
        descripcion.setFont(fieldFont);
        descripcion.setLineWrap(true);
        descripcion.setWrapStyleWord(true);
        descripcion.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ColoresUDLAP.GRIS_CLARO),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        JScrollPane scrollDescripcion = new JScrollPane(descripcion);
        centro.add(scrollDescripcion, gbc);

        add(centro, BorderLayout.CENTER);

        // Panel de botones
        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 20, 0));
        panelBotones.setBackground(ColoresUDLAP.BLANCO);
        panelBotones.setBorder(BorderFactory.createEmptyBorder(15, 60, 25, 60));

        JButton btnMedicos = crearBotonEmergencia("Llamar a Servicios Medicos", ColoresUDLAP.VERDE_SOLIDO);
        btnMedicos.addActionListener(e -> registrarEmergencia());

        JButton btnSeguridad = crearBotonEmergencia("Llamar a Seguridad UDLAP", ColoresUDLAP.NARANJA_BARRA);
        btnSeguridad.addActionListener(e -> mostrarLlamadaSeguridad());

        panelBotones.add(btnMedicos);
        panelBotones.add(btnSeguridad);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private JButton crearBotonEmergencia(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 16));
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setPreferredSize(new Dimension(280, 50));
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return boton;
    }

    private void registrarEmergencia() {
        String textoDescripcion = descripcion.getText().trim();
        String textoUbicacion = ubicacion.getText().trim();

        if (textoUbicacion.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Debe indicar la ubicacion de la emergencia.",
                    "Campo requerido", JOptionPane.WARNING_MESSAGE);
            ubicacion.requestFocus();
            return;
        }

        if (textoDescripcion.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Debe describir la emergencia.",
                    "Campo requerido", JOptionPane.WARNING_MESSAGE);
            descripcion.requestFocus();
            return;
        }

        int idPaciente = SesionUsuario.getPacienteActual();
        String tipo = (String) tipoEmergencia.getSelectedItem();
        String nivel = (String) gravedad.getSelectedItem();
        Timestamp fechaIncidente = Timestamp.valueOf(LocalDateTime.now());

        boolean exito = EmergenciaDB.guardarEmergencia(
                idPaciente > 0 ? idPaciente : null,
                textoUbicacion,
                tipo,
                nivel,
                textoDescripcion,
                fechaIncidente,
                null,   // TelefonoContacto
                null    // IDResponsable
        );

        if (exito) {
            JOptionPane.showMessageDialog(this,
                    "Emergencia reportada exitosamente.\nServicios Medicos ha sido notificado.",
                    "Emergencia registrada", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this,
                    "No se pudo registrar la emergencia. Intente de nuevo.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        descripcion.setText("");
        ubicacion.setText("");
        tipoEmergencia.setSelectedIndex(0);
        gravedad.setSelectedIndex(0);
    }

    private void mostrarLlamadaSeguridad() {
        JOptionPane.showMessageDialog(
                this,
                "Llamando a Seguridad UDLAP...\n\nEspere mientras se procesa la emergencia.",
                "Llamada en curso",
                JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public JPanel getPanel() {
        return this;
    }

    @Override
    public String getPanelName() {
        return "reportarEmergencia";
    }
}
