package Justificantes;

import javax.swing.*;
import javax.swing.border.MatteBorder;

import Utilidades.ColoresUDLAP;
import Utilidades.PanelManager;
import Utilidades.ui.CardPanel;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PanelMenuJustificantes extends JPanel {

    private final PanelManager panelManager;

    public PanelMenuJustificantes(PanelManager panelManager) {
        this.panelManager = panelManager;

        setLayout(new BorderLayout());
        setBackground(ColoresUDLAP.FONDO_GENERAL);
        setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        // Título
        JLabel titulo = new JLabel("Gestión de Justificantes");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titulo.setForeground(ColoresUDLAP.TEXTO_PRINCIPAL);
        titulo.setBorder(BorderFactory.createCompoundBorder(
                new MatteBorder(0, 0, 2, 0, ColoresUDLAP.VERDE_PRIMARIO),
                BorderFactory.createEmptyBorder(0, 0, 20, 0)));
        add(titulo, BorderLayout.NORTH);

        // Panel de cards
        JPanel panelCards = new JPanel(new GridLayout(1, 2, 16, 0));
        panelCards.setOpaque(false);

        // Card Solicitudes (acento verde)
        CardPanel cardSolicitudes = new CardPanel(true);
        cardSolicitudes.setLayout(new BorderLayout(0, 8));
        cardSolicitudes.setBorder(BorderFactory.createCompoundBorder(
                new MatteBorder(4, 0, 0, 0, ColoresUDLAP.VERDE_PRIMARIO),
                BorderFactory.createEmptyBorder(16, 20, 16, 20)));
        cardSolicitudes.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel lblSolicitudesTitulo = new JLabel("Solicitudes");
        lblSolicitudesTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblSolicitudesTitulo.setForeground(ColoresUDLAP.TEXTO_PRINCIPAL);

        JLabel lblSolicitudesDesc = new JLabel("<html>Revisa y gestiona las solicitudes<br>de justificantes pendientes.</html>");
        lblSolicitudesDesc.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSolicitudesDesc.setForeground(ColoresUDLAP.TEXTO_SECUNDARIO);

        cardSolicitudes.add(lblSolicitudesTitulo, BorderLayout.NORTH);
        cardSolicitudes.add(lblSolicitudesDesc, BorderLayout.CENTER);

        cardSolicitudes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new SolicitudesJustificantesFrame(panelManager).setVisible(true);
            }
        });

        // Card Consulta Interna (acento naranja)
        CardPanel cardConsulta = new CardPanel(true);
        cardConsulta.setLayout(new BorderLayout(0, 8));
        cardConsulta.setBorder(BorderFactory.createCompoundBorder(
                new MatteBorder(4, 0, 0, 0, ColoresUDLAP.NARANJA_ACENTO),
                BorderFactory.createEmptyBorder(16, 20, 16, 20)));
        cardConsulta.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel lblConsultaTitulo = new JLabel("Consulta Interna");
        lblConsultaTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblConsultaTitulo.setForeground(ColoresUDLAP.TEXTO_PRINCIPAL);

        JLabel lblConsultaDesc = new JLabel("<html>Emite un justificante directamente<br>desde una consulta registrada.</html>");
        lblConsultaDesc.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblConsultaDesc.setForeground(ColoresUDLAP.TEXTO_SECUNDARIO);

        cardConsulta.add(lblConsultaTitulo, BorderLayout.NORTH);
        cardConsulta.add(lblConsultaDesc, BorderLayout.CENTER);

        cardConsulta.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                EmitirJustificanteDesdeConsultaFrame panel = new EmitirJustificanteDesdeConsultaFrame(panelManager);
                panelManager.mostrarPanelPersonalizado(panel);
            }
        });

        panelCards.add(cardSolicitudes);
        panelCards.add(cardConsulta);

        JPanel centro = new JPanel(new BorderLayout());
        centro.setOpaque(false);
        centro.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        centro.add(panelCards, BorderLayout.NORTH);
        add(centro, BorderLayout.CENTER);
    }
}
