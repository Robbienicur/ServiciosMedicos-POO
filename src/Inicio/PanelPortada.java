package Inicio;

import Utilidades.ColoresUDLAP;

import javax.swing.*;
import java.awt.*;

public class PanelPortada extends JPanel {

    public PanelPortada() {
        setOpaque(false);
        setLayout(new GridBagLayout());

        JPanel center = new JPanel();
        center.setOpaque(false);
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

        // UDLAP logo
        JLabel lblLogo = new JLabel();
        lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
        try {
            java.net.URL logoUrl = getClass().getResource("/icons/udlap_logo.png");
            if (logoUrl != null) {
                ImageIcon iconoOriginal = new ImageIcon(logoUrl);
                Image imgEscalada = iconoOriginal.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                lblLogo.setIcon(new ImageIcon(imgEscalada));
            }
        } catch (Exception e) {
            // Si no se encuentra el logo, se omite
        }
        center.add(lblLogo);
        center.add(Box.createVerticalStrut(20));

        // Titulo principal
        JLabel lblTitulo = new JLabel("Bienvenido a Servicios Médicos");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(ColoresUDLAP.VERDE_PRIMARIO);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        center.add(lblTitulo);
        center.add(Box.createVerticalStrut(10));

        // Subtitulo
        JLabel lblSubtitulo = new JLabel("Sistema de gestión de servicios médicos UDLAP");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubtitulo.setForeground(ColoresUDLAP.TEXTO_SECUNDARIO);
        lblSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        center.add(lblSubtitulo);
        center.add(Box.createVerticalStrut(8));

        // Instruccion
        JLabel lblInstruccion = new JLabel("Seleccione una opción del menú lateral para comenzar");
        lblInstruccion.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblInstruccion.setForeground(ColoresUDLAP.TEXTO_SECUNDARIO);
        lblInstruccion.setAlignmentX(Component.CENTER_ALIGNMENT);
        center.add(lblInstruccion);

        add(center);
    }
}
