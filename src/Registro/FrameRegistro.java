package Registro;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import Utilidades.ColoresUDLAP;

public class FrameRegistro extends JPanel {
    private final JTextField[] campos;
    private final String[] etiquetas = {
            "ID:", "Nombre:", "Apellido Paterno:", "Apellido Materno:", "Correo:",
            "Edad:", "Altura (cm):", "Peso (kg):",
            "Enfermedades Preexistentes:", "Medicación:", "Alergias:"
    };

    public FrameRegistro() {
        setLayout(new GridBagLayout());
        setOpaque(false);
        campos = new JTextField[etiquetas.length];

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 12);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);

        // Título
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel titulo = new JLabel("Registro de Pacientes", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titulo.setForeground(ColoresUDLAP.TEXTO_PRINCIPAL);
        titulo.setBorder(new MatteBorder(0, 0, 2, 0, ColoresUDLAP.VERDE_PRIMARIO));
        add(titulo, gbc);

        gbc.gridwidth = 1;
        for (int i = 0; i < etiquetas.length; i++) {

            gbc.gridx = 0;
            gbc.gridy = i + 1;
            gbc.weightx = 0.3;

            JLabel label = new JLabel(etiquetas[i]);
            label.setFont(labelFont);
            label.setForeground(ColoresUDLAP.TEXTO_SECUNDARIO);
            add(label, gbc);

            gbc.gridx = 1;
            gbc.weightx = 0.7;

            campos[i] = new JTextField(25);
            campos[i].setFont(fieldFont);
            campos[i].setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(ColoresUDLAP.BORDE),
                    BorderFactory.createEmptyBorder(5, 8, 5, 8)));
            add(campos[i], gbc);
        }

        // Espacio adicional al final
        gbc.gridy = etiquetas.length + 1;
        gbc.weighty = 0.2;
        add(Box.createGlue(), gbc);
    }

    public JTextField[] obtenerCampos() {
        return campos;
    }
}
