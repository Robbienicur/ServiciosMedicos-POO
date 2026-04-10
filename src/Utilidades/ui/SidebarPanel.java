package Utilidades.ui;

import Utilidades.ColoresUDLAP;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class SidebarPanel extends JPanel {

    private final List<JPanel> items = new ArrayList<>();
    private int activeIndex = 0;
    private Consumer<Integer> onItemClick;

    public SidebarPanel(String userName, String userRole, String[] menuLabels, Consumer<Integer> onItemClick) {
        this.onItemClick = onItemClick;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(220, 0));
        setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(233, 236, 239)));

        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(233, 236, 239)),
            BorderFactory.createEmptyBorder(20, 16, 16, 16)
        ));

        JLabel logo = new JLabel("SM");
        logo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        logo.setForeground(Color.WHITE);
        logo.setOpaque(true);
        logo.setBackground(ColoresUDLAP.VERDE_PRIMARIO);
        logo.setHorizontalAlignment(SwingConstants.CENTER);
        logo.setPreferredSize(new Dimension(40, 40));
        logo.setMaximumSize(new Dimension(40, 40));
        header.add(logo);
        header.add(Box.createVerticalStrut(10));

        JLabel titleLabel = new JLabel("Servicios Médicos");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleLabel.setForeground(ColoresUDLAP.TEXTO_PRINCIPAL);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        header.add(titleLabel);
        header.add(Box.createVerticalStrut(2));

        JLabel userLabel = new JLabel(userName + " \u00b7 " + userRole);
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        userLabel.setForeground(ColoresUDLAP.TEXTO_SECUNDARIO);
        userLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        header.add(userLabel);

        add(header, BorderLayout.NORTH);

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBackground(Color.WHITE);
        menuPanel.setBorder(BorderFactory.createEmptyBorder(12, 8, 12, 8));

        for (int i = 0; i < menuLabels.length; i++) {
            JPanel item = createMenuItem(menuLabels[i], i);
            items.add(item);
            menuPanel.add(item);
            menuPanel.add(Box.createVerticalStrut(2));
        }

        add(menuPanel, BorderLayout.CENTER);
        updateActiveItem(0);
    }

    private JPanel createMenuItem(String label, int index) {
        JPanel item = new JPanel(new BorderLayout()) {
            @Override
            public Dimension getMaximumSize() {
                return new Dimension(Integer.MAX_VALUE, 40);
            }
        };
        item.setOpaque(false);
        item.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));

        JLabel text = new JLabel(label);
        text.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        text.setForeground(ColoresUDLAP.TEXTO_SECUNDARIO);
        item.add(text, BorderLayout.CENTER);

        item.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        item.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (index != activeIndex) {
                    item.setOpaque(true);
                    item.setBackground(ColoresUDLAP.FONDO_NEUTRO);
                    item.repaint();
                }
            }
            @Override
            public void mouseExited(MouseEvent e) {
                if (index != activeIndex) {
                    item.setOpaque(false);
                    item.repaint();
                }
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                updateActiveItem(index);
                if (onItemClick != null) onItemClick.accept(index);
            }
        });

        return item;
    }

    public void updateActiveItem(int index) {
        activeIndex = index;
        for (int i = 0; i < items.size(); i++) {
            JPanel item = items.get(i);
            JLabel text = (JLabel) ((BorderLayout) item.getLayout()).getLayoutComponent(BorderLayout.CENTER);
            if (i == index) {
                item.setOpaque(true);
                item.setBackground(ColoresUDLAP.VERDE_SUAVE);
                text.setForeground(ColoresUDLAP.VERDE_PRIMARIO);
                text.setFont(new Font("Segoe UI", Font.BOLD, 14));
            } else {
                item.setOpaque(false);
                text.setForeground(ColoresUDLAP.TEXTO_SECUNDARIO);
                text.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            }
            item.repaint();
        }
    }
}
