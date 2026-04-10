package Utilidades.ui;

import Utilidades.ColoresUDLAP;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BotonUDLAP extends JButton {

    private Color colorBase;
    private Color colorHover;

    private BotonUDLAP(String texto) {
        super(texto);
        setFont(new Font("Segoe UI", Font.BOLD, 13));
        setFocusPainted(false);
        setBorderPainted(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setPreferredSize(new Dimension(getPreferredSize().width, 40));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (isEnabled()) setBackground(colorHover);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                if (isEnabled()) setBackground(colorBase);
            }
        });
    }

    public static BotonUDLAP primario(String texto) {
        BotonUDLAP btn = new BotonUDLAP(texto);
        btn.colorBase = ColoresUDLAP.VERDE_PRIMARIO;
        btn.colorHover = ColoresUDLAP.VERDE_HOVER;
        btn.setBackground(btn.colorBase);
        btn.setForeground(Color.WHITE);
        btn.setOpaque(true);
        return btn;
    }

    public static BotonUDLAP secundario(String texto) {
        BotonUDLAP btn = new BotonUDLAP(texto);
        btn.colorBase = Color.WHITE;
        btn.colorHover = ColoresUDLAP.NARANJA_SUAVE;
        btn.setBackground(btn.colorBase);
        btn.setForeground(ColoresUDLAP.NARANJA_ACENTO);
        btn.setOpaque(true);
        btn.setBorderPainted(true);
        btn.setBorder(BorderFactory.createLineBorder(ColoresUDLAP.NARANJA_ACENTO, 2));
        return btn;
    }

    public static BotonUDLAP neutro(String texto) {
        BotonUDLAP btn = new BotonUDLAP(texto);
        btn.colorBase = ColoresUDLAP.FONDO_NEUTRO;
        btn.colorHover = ColoresUDLAP.BORDE;
        btn.setBackground(btn.colorBase);
        btn.setForeground(ColoresUDLAP.TEXTO_SECUNDARIO);
        btn.setOpaque(true);
        return btn;
    }
}
