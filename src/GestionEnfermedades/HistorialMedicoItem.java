package GestionEnfermedades;

import Utilidades.ColoresUDLAP;
import javax.swing.*;
import java.awt.*;

public class HistorialMedicoItem extends JPanel {
    public HistorialMedicoItem(String fecha, String diagnostico, String sintomas, String medicamentos, String receta) {
        setLayout(new BorderLayout());
        setBackground(ColoresUDLAP.FONDO_NEUTRO);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ColoresUDLAP.BORDE),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)));

        JTextArea area = new JTextArea(String.format("""
                Fecha: %s
                Diagnóstico: %s
                Síntomas: %s
                Medicamentos: %s
                Receta: %s
                """, fecha, diagnostico, sintomas, medicamentos, receta));
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setBackground(getBackground());
        area.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        area.setForeground(ColoresUDLAP.TEXTO_PRINCIPAL);
        add(area, BorderLayout.CENTER);
    }
}
