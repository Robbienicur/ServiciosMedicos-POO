package Utilidades.ui;

import Utilidades.ColoresUDLAP;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class CampoTextoUDLAP {

    private static final Font LABEL_FONT = new Font("Segoe UI", Font.PLAIN, 12);
    private static final Font FIELD_FONT = new Font("Segoe UI", Font.PLAIN, 14);

    public static JPanel crear(String label) {
        JPanel panel = new JPanel(new BorderLayout(0, 4));
        panel.setOpaque(false);

        JLabel lbl = new JLabel(label);
        lbl.setFont(LABEL_FONT);
        lbl.setForeground(ColoresUDLAP.TEXTO_SECUNDARIO);
        panel.add(lbl, BorderLayout.NORTH);

        JTextField field = new JTextField();
        field.setFont(FIELD_FONT);
        field.setForeground(ColoresUDLAP.TEXTO_PRINCIPAL);
        aplicarBordeFoco(field);
        panel.add(field, BorderLayout.CENTER);

        return panel;
    }

    public static JPanel crearPassword(String label) {
        JPanel panel = new JPanel(new BorderLayout(0, 4));
        panel.setOpaque(false);

        JLabel lbl = new JLabel(label);
        lbl.setFont(LABEL_FONT);
        lbl.setForeground(ColoresUDLAP.TEXTO_SECUNDARIO);
        panel.add(lbl, BorderLayout.NORTH);

        JPasswordField field = new JPasswordField();
        field.setFont(FIELD_FONT);
        field.setForeground(ColoresUDLAP.TEXTO_PRINCIPAL);
        aplicarBordeFoco(field);
        panel.add(field, BorderLayout.CENTER);

        return panel;
    }

    public static JPanel crearArea(String label, int rows) {
        JPanel panel = new JPanel(new BorderLayout(0, 4));
        panel.setOpaque(false);

        JLabel lbl = new JLabel(label);
        lbl.setFont(LABEL_FONT);
        lbl.setForeground(ColoresUDLAP.TEXTO_SECUNDARIO);
        panel.add(lbl, BorderLayout.NORTH);

        JTextArea area = new JTextArea(rows, 20);
        area.setFont(FIELD_FONT);
        area.setForeground(ColoresUDLAP.TEXTO_PRINCIPAL);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(area);
        scroll.setBorder(BorderFactory.createLineBorder(ColoresUDLAP.BORDE));
        area.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                scroll.setBorder(new CompoundBorder(
                    BorderFactory.createLineBorder(ColoresUDLAP.BORDE),
                    new MatteBorder(0, 0, 2, 0, ColoresUDLAP.VERDE_PRIMARIO)
                ));
            }
            @Override
            public void focusLost(FocusEvent e) {
                scroll.setBorder(BorderFactory.createLineBorder(ColoresUDLAP.BORDE));
            }
        });
        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }

    public static JTextField getTextField(JPanel wrapper) {
        for (Component c : wrapper.getComponents()) {
            if (c instanceof JTextField) return (JTextField) c;
        }
        return null;
    }

    public static JPasswordField getPasswordField(JPanel wrapper) {
        for (Component c : wrapper.getComponents()) {
            if (c instanceof JPasswordField) return (JPasswordField) c;
        }
        return null;
    }

    public static JTextArea getTextArea(JPanel wrapper) {
        for (Component c : wrapper.getComponents()) {
            if (c instanceof JScrollPane) {
                JScrollPane sp = (JScrollPane) c;
                Component view = sp.getViewport().getView();
                if (view instanceof JTextArea) return (JTextArea) view;
            }
        }
        return null;
    }

    private static void aplicarBordeFoco(JTextField field) {
        CompoundBorder defaultBorder = new CompoundBorder(
            BorderFactory.createLineBorder(ColoresUDLAP.BORDE),
            new EmptyBorder(8, 10, 8, 10)
        );
        CompoundBorder focusBorder = new CompoundBorder(
            new CompoundBorder(
                BorderFactory.createLineBorder(ColoresUDLAP.BORDE),
                new MatteBorder(0, 0, 2, 0, ColoresUDLAP.VERDE_PRIMARIO)
            ),
            new EmptyBorder(8, 10, 6, 10)
        );

        field.setBorder(defaultBorder);
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) { field.setBorder(focusBorder); }
            @Override
            public void focusLost(FocusEvent e) { field.setBorder(defaultBorder); }
        });
    }
}
