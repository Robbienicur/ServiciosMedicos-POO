package GestionCitas;

import Utilidades.ColoresUDLAP;
import Utilidades.PanelManager;
import Utilidades.ui.CardPanel;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PanelGestionCitas extends JPanel {

    private final int userId;
    private final PanelManager panelManager;

    public PanelGestionCitas(int userId, PanelManager panelManager) {
        this.userId = userId;
        this.panelManager = panelManager;

        setLayout(new BorderLayout());
        setBackground(ColoresUDLAP.FONDO_GENERAL);
        setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        // Título
        JLabel titulo = new JLabel("Gestión de Citas");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titulo.setForeground(ColoresUDLAP.TEXTO_PRINCIPAL);
        titulo.setBorder(BorderFactory.createCompoundBorder(
                new MatteBorder(0, 0, 2, 0, ColoresUDLAP.VERDE_PRIMARIO),
                BorderFactory.createEmptyBorder(0, 0, 20, 0)));
        add(titulo, BorderLayout.NORTH);

        // Panel de cards
        JPanel panelCards = new JPanel(new GridLayout(1, 2, 16, 0));
        panelCards.setOpaque(false);

        // Card Agendar Cita (acento verde)
        CardPanel cardAgendar = new CardPanel(true);
        cardAgendar.setLayout(new BorderLayout(0, 8));
        cardAgendar.setBorder(BorderFactory.createCompoundBorder(
                new MatteBorder(4, 0, 0, 0, ColoresUDLAP.VERDE_PRIMARIO),
                BorderFactory.createEmptyBorder(16, 20, 16, 20)));
        cardAgendar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel lblAgendarTitulo = new JLabel("Agendar Cita");
        lblAgendarTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblAgendarTitulo.setForeground(ColoresUDLAP.TEXTO_PRINCIPAL);

        JLabel lblAgendarDesc = new JLabel("<html>Programa una nueva cita médica<br>en el servicio que necesites.</html>");
        lblAgendarDesc.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblAgendarDesc.setForeground(ColoresUDLAP.TEXTO_SECUNDARIO);

        cardAgendar.add(lblAgendarTitulo, BorderLayout.NORTH);
        cardAgendar.add(lblAgendarDesc, BorderLayout.CENTER);

        cardAgendar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                panelManager.showPanel("agendarCita");
            }
        });

        // Card Modificar Cita (acento naranja)
        CardPanel cardModificar = new CardPanel(true);
        cardModificar.setLayout(new BorderLayout(0, 8));
        cardModificar.setBorder(BorderFactory.createCompoundBorder(
                new MatteBorder(4, 0, 0, 0, ColoresUDLAP.NARANJA_ACENTO),
                BorderFactory.createEmptyBorder(16, 20, 16, 20)));
        cardModificar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel lblModificarTitulo = new JLabel("Modificar Cita");
        lblModificarTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblModificarTitulo.setForeground(ColoresUDLAP.TEXTO_PRINCIPAL);

        JLabel lblModificarDesc = new JLabel("<html>Cambia la fecha, hora o servicio<br>de una cita existente.</html>");
        lblModificarDesc.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblModificarDesc.setForeground(ColoresUDLAP.TEXTO_SECUNDARIO);

        cardModificar.add(lblModificarTitulo, BorderLayout.NORTH);
        cardModificar.add(lblModificarDesc, BorderLayout.CENTER);

        cardModificar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                panelManager.showPanel("modificarCita");
            }
        });

        panelCards.add(cardAgendar);
        panelCards.add(cardModificar);

        JPanel centro = new JPanel(new BorderLayout());
        centro.setOpaque(false);
        centro.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        centro.add(panelCards, BorderLayout.NORTH);
        add(centro, BorderLayout.CENTER);
    }
}
