package Justificantes;

import Inicio.SesionUsuario;
import Utilidades.ColoresUDLAP;
import Utilidades.PanelManager;
import Utilidades.ui.CardPanel;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PanelJustificantesPacienteMenu extends JPanel {

    private final PanelManager panelManager;

    public PanelJustificantesPacienteMenu(PanelManager panelManager) {
        this.panelManager = panelManager;
        setLayout(new BorderLayout());
        setBackground(ColoresUDLAP.FONDO_GENERAL);
        setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        // Título
        JLabel titulo = new JLabel("Gestión de Justificantes Médicos");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titulo.setForeground(ColoresUDLAP.TEXTO_PRINCIPAL);
        titulo.setBorder(BorderFactory.createCompoundBorder(
                new MatteBorder(0, 0, 2, 0, ColoresUDLAP.VERDE_PRIMARIO),
                BorderFactory.createEmptyBorder(0, 0, 20, 0)));
        add(titulo, BorderLayout.NORTH);

        // Panel de cards
        JPanel panelCards = new JPanel(new GridLayout(1, 2, 16, 0));
        panelCards.setOpaque(false);

        // Card Solicitar Justificante (acento verde)
        CardPanel cardSolicitar = new CardPanel(true);
        cardSolicitar.setLayout(new BorderLayout(0, 8));
        cardSolicitar.setBorder(BorderFactory.createCompoundBorder(
                new MatteBorder(4, 0, 0, 0, ColoresUDLAP.VERDE_PRIMARIO),
                BorderFactory.createEmptyBorder(16, 20, 16, 20)));
        cardSolicitar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel lblSolicitarTitulo = new JLabel("Solicitar Justificante");
        lblSolicitarTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblSolicitarTitulo.setForeground(ColoresUDLAP.TEXTO_PRINCIPAL);

        JLabel lblSolicitarDesc = new JLabel("<html>Envía una nueva solicitud<br>de justificante médico.</html>");
        lblSolicitarDesc.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSolicitarDesc.setForeground(ColoresUDLAP.TEXTO_SECUNDARIO);

        cardSolicitar.add(lblSolicitarTitulo, BorderLayout.NORTH);
        cardSolicitar.add(lblSolicitarDesc, BorderLayout.CENTER);

        cardSolicitar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                FormularioJustificanteFrame panel = new FormularioJustificanteFrame(panelManager);
                panel.setValoresDesdeSesion();
                panelManager.mostrarPanelPersonalizado(panel);
            }
        });

        // Card Mis Justificantes (acento naranja)
        CardPanel cardMisJust = new CardPanel(true);
        cardMisJust.setLayout(new BorderLayout(0, 8));
        cardMisJust.setBorder(BorderFactory.createCompoundBorder(
                new MatteBorder(4, 0, 0, 0, ColoresUDLAP.NARANJA_ACENTO),
                BorderFactory.createEmptyBorder(16, 20, 16, 20)));
        cardMisJust.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel lblMisJustTitulo = new JLabel("Mis Justificantes");
        lblMisJustTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblMisJustTitulo.setForeground(ColoresUDLAP.TEXTO_PRINCIPAL);

        JLabel lblMisJustDesc = new JLabel("<html>Consulta el estado de tus<br>solicitudes de justificante.</html>");
        lblMisJustDesc.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblMisJustDesc.setForeground(ColoresUDLAP.TEXTO_SECUNDARIO);

        cardMisJust.add(lblMisJustTitulo, BorderLayout.NORTH);
        cardMisJust.add(lblMisJustDesc, BorderLayout.CENTER);

        cardMisJust.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                MisJustificantesPacientePanel panel = new MisJustificantesPacientePanel(
                        String.valueOf(SesionUsuario.getPacienteActual()),
                        panelManager
                );
                panelManager.mostrarPanelPersonalizado(panel);
            }
        });

        panelCards.add(cardSolicitar);
        panelCards.add(cardMisJust);

        JPanel centro = new JPanel(new BorderLayout());
        centro.setOpaque(false);
        centro.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        centro.add(panelCards, BorderLayout.NORTH);
        add(centro, BorderLayout.CENTER);
    }
}
