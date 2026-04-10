package Utilidades.ui;

import Utilidades.ColoresUDLAP;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CardPanel extends JPanel {

    private boolean hoverEffect;
    private boolean hovered = false;

    public CardPanel() {
        this(true);
    }

    public CardPanel(boolean hoverEffect) {
        this.hoverEffect = hoverEffect;
        setBackground(Color.WHITE);
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        if (hoverEffect) {
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    hovered = true;
                    repaint();
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    hovered = false;
                    repaint();
                }
            });
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int arc = 10;
        int shadowSize = hovered ? 6 : 3;
        int shadowAlpha = hovered ? 25 : 20;

        for (int i = 0; i < shadowSize; i++) {
            int alpha = Math.max(0, shadowAlpha - (i * 3));
            g2.setColor(new Color(0, 0, 0, alpha));
            g2.fillRoundRect(i, i + 1, getWidth() - (i * 2), getHeight() - (i * 2), arc, arc);
        }

        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arc, arc);

        g2.setColor(ColoresUDLAP.BORDE);
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arc, arc);

        g2.dispose();
        super.paintComponent(g);
    }
}
