package Emergencias;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import Utilidades.ColoresUDLAP;
import Utilidades.ui.BotonUDLAP;

/**
 * Panel que reemplaza al antiguo MenuEmergenciaFrame.
 * Muestra dos opciones: registrar llamada y reporte de accidente,
 * más un botón de volver.
 */
public class PanelMenuEmergencia extends JPanel {
    private final JButton btnRegistrarLlamada;
    private final JButton btnReporteAccidente;
    private final JButton btnVolver;

    /**
     * @param menuListener ActionListener que recibirá los comandos:
     *                     "registrarLlamada", "reporteAccidente" y "volver"
     */
    public PanelMenuEmergencia(ActionListener menuListener) {
        setLayout(new BorderLayout());
        setBackground(ColoresUDLAP.FONDO_GENERAL);

        // Encabezado
        JLabel lblTitulo = new JLabel("Menú de Emergencia", SwingConstants.LEFT);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(ColoresUDLAP.TEXTO_PRINCIPAL);
        lblTitulo.setBorder(BorderFactory.createCompoundBorder(
                new MatteBorder(0, 0, 2, 0, ColoresUDLAP.VERDE_PRIMARIO),
                BorderFactory.createEmptyBorder(16, 24, 16, 24)));
        add(lblTitulo, BorderLayout.NORTH);

        // Cuerpo central con los dos botones
        JPanel centro = new JPanel(new GridBagLayout());
        centro.setBackground(ColoresUDLAP.FONDO_GENERAL);
        centro.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.gridx = 0;

        // Botón "Registrar Llamada de Emergencia"
        gbc.gridy = 0;
        btnRegistrarLlamada = BotonUDLAP.primario("Registrar Llamada de Emergencia");
        btnRegistrarLlamada.setActionCommand("registrarLlamada");
        btnRegistrarLlamada.addActionListener(menuListener);
        centro.add(btnRegistrarLlamada, gbc);

        // Botón "Llenar Reporte de Accidente"
        gbc.gridy = 1;
        btnReporteAccidente = BotonUDLAP.secundario("Llenar Reporte de Accidente");
        btnReporteAccidente.setActionCommand("reporteAccidente");
        btnReporteAccidente.addActionListener(menuListener);
        centro.add(btnReporteAccidente, gbc);

        add(centro, BorderLayout.CENTER);

        // Pie con botón "Volver"
        JPanel pie = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pie.setBackground(ColoresUDLAP.FONDO_GENERAL);
        btnVolver = BotonUDLAP.neutro("Volver");
        btnVolver.setActionCommand("volver");
        btnVolver.addActionListener(menuListener);
        pie.add(btnVolver);
        pie.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 20));
        add(pie, BorderLayout.SOUTH);
    }
}
