package Registro;

import java.awt.*;
import javax.swing.*;

public class FrameRegistro extends JPanel {
    private final JTextField[] campos;
    private final String[] etiquetas = {
            "ID:", "Nombre:", "Apellido Paterno:", "Apellido Materno:", "Edad:", "Altura (cm):", "Peso (kg):",
            "Enfermedades Preexistentes:", "Medicación:", "Alergias:"
    };

    public FrameRegistro() {
        setLayout(new GridLayout(etiquetas.length, 2, 5, 5));
        campos = new JTextField[etiquetas.length];

        for (int i = 0; i < etiquetas.length; i++) {
            add(new JLabel(etiquetas[i]));
            campos[i] = new JTextField(25);
            add(campos[i]);
        }
    }

    public JTextField[] obtenerCampos() {
        return campos;
    }
}
