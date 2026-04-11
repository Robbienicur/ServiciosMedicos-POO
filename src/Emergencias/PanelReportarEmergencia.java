package Emergencias;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import Utilidades.ColoresUDLAP;
import Utilidades.PanelProvider;
import Utilidades.ui.CardPanel;

public class PanelReportarEmergencia extends JPanel implements PanelProvider {

    public PanelReportarEmergencia() {
        setLayout(new BorderLayout());
        setBackground(ColoresUDLAP.FONDO_GENERAL);
        setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        // Título
        JLabel titulo = new JLabel("Reportar Emergencia");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titulo.setForeground(ColoresUDLAP.TEXTO_PRINCIPAL);
        titulo.setBorder(BorderFactory.createCompoundBorder(
                new MatteBorder(0, 0, 2, 0, ColoresUDLAP.VERDE_PRIMARIO),
                BorderFactory.createEmptyBorder(0, 0, 20, 0)));
        add(titulo, BorderLayout.NORTH);

        // Panel de cards
        JPanel panelCards = new JPanel(new GridLayout(1, 2, 16, 0));
        panelCards.setOpaque(false);

        // Card Servicios Médicos (acento verde)
        CardPanel cardMedicos = new CardPanel(true);
        cardMedicos.setLayout(new BorderLayout(0, 8));
        cardMedicos.setBorder(BorderFactory.createCompoundBorder(
                new MatteBorder(4, 0, 0, 0, ColoresUDLAP.VERDE_PRIMARIO),
                BorderFactory.createEmptyBorder(20, 24, 20, 24)));
        cardMedicos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel lblMedicosTitulo = new JLabel("Llamar a Servicios Médicos");
        lblMedicosTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblMedicosTitulo.setForeground(ColoresUDLAP.TEXTO_PRINCIPAL);

        JLabel lblMedicosDesc = new JLabel("<html>Contacta de inmediato con el<br>personal médico de UDLAP.</html>");
        lblMedicosDesc.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblMedicosDesc.setForeground(ColoresUDLAP.TEXTO_SECUNDARIO);

        cardMedicos.add(lblMedicosTitulo, BorderLayout.NORTH);
        cardMedicos.add(lblMedicosDesc, BorderLayout.CENTER);

        cardMedicos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mostrarLlamada("Servicios Médicos");
            }
        });

        // Card Seguridad UDLAP (acento naranja)
        CardPanel cardSeguridad = new CardPanel(true);
        cardSeguridad.setLayout(new BorderLayout(0, 8));
        cardSeguridad.setBorder(BorderFactory.createCompoundBorder(
                new MatteBorder(4, 0, 0, 0, ColoresUDLAP.NARANJA_ACENTO),
                BorderFactory.createEmptyBorder(20, 24, 20, 24)));
        cardSeguridad.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel lblSeguridadTitulo = new JLabel("Llamar a Seguridad UDLAP");
        lblSeguridadTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblSeguridadTitulo.setForeground(ColoresUDLAP.TEXTO_PRINCIPAL);

        JLabel lblSeguridadDesc = new JLabel("<html>Solicita asistencia del departamento<br>de seguridad de la universidad.</html>");
        lblSeguridadDesc.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSeguridadDesc.setForeground(ColoresUDLAP.TEXTO_SECUNDARIO);

        cardSeguridad.add(lblSeguridadTitulo, BorderLayout.NORTH);
        cardSeguridad.add(lblSeguridadDesc, BorderLayout.CENTER);

        cardSeguridad.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mostrarLlamada("Seguridad UDLAP");
            }
        });

        panelCards.add(cardMedicos);
        panelCards.add(cardSeguridad);

        JPanel centro = new JPanel(new BorderLayout());
        centro.setOpaque(false);
        centro.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        centro.add(panelCards, BorderLayout.NORTH);
        add(centro, BorderLayout.CENTER);
    }

    private void mostrarLlamada(String destino) {
        JOptionPane.showMessageDialog(
                this,
                "Llamando a " + destino + "...\n\nEspere mientras se procesa la emergencia.",
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
