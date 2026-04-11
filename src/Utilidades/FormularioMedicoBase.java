package Utilidades;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class FormularioMedicoBase extends JPanel {

    protected final String[] etiquetas;
    protected final JComponent[] campos;

    public FormularioMedicoBase(String titulo, String[] etiquetas) {
        this.etiquetas = etiquetas;
        this.campos = new JComponent[etiquetas.length];

        setLayout(new BorderLayout());
        setOpaque(false);

        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setOpaque(false);
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        int row = 0;

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(ColoresUDLAP.TEXTO_PRINCIPAL);
        lblTitulo.setBorder(new MatteBorder(0, 0, 2, 0, ColoresUDLAP.VERDE_PRIMARIO));
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        panelFormulario.add(lblTitulo, gbc);
        gbc.gridwidth = 1;

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 12);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);

        for (int i = 0; i < etiquetas.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = row;

            JLabel label = new JLabel(etiquetas[i]);
            label.setFont(labelFont);
            label.setForeground(ColoresUDLAP.TEXTO_SECUNDARIO);
            panelFormulario.add(label, gbc);

            gbc.gridx = 1;

            if (isTextArea(i)) {
                JTextArea area = new JTextArea(3, 25);
                area.setFont(fieldFont);
                area.setLineWrap(true);
                area.setWrapStyleWord(true);
                campos[i] = area;
                panelFormulario.add(new JScrollPane(area), gbc);
            } else {
                JTextField field = new JTextField(25);
                field.setFont(fieldFont);
                campos[i] = field;
                panelFormulario.add(field, gbc);
            }
            row++;
        }

        // Espacio dinámico
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        panelFormulario.add(Box.createVerticalGlue(), gbc);

        JScrollPane scrollPane = new JScrollPane(panelFormulario);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setPreferredSize(null);
        add(scrollPane, BorderLayout.CENTER);
    }

    protected boolean isTextArea(int index) {
        return false;
    }

    public JTextField[] getCamposTexto(int... indices) {
        List<JTextField> lista = new ArrayList<>();
        for (int i : indices) {
            if (campos[i] instanceof JTextField) {
                lista.add((JTextField) campos[i]);
            }
        }
        return lista.toArray(new JTextField[0]);
    }

    public JTextArea getAreaTexto(int index) {
        if (campos[index] instanceof JTextArea) {
            return (JTextArea) campos[index];
        }
        return null;
    }

    public void limpiarCampos() {
        for (JComponent campo : campos) {
            if (campo instanceof JTextField) {
                ((JTextField) campo).setText("");
            }
            if (campo instanceof JTextArea) {
                ((JTextArea) campo).setText("");
            }
        }
    }

    public JComponent[] getCampos() {
        return campos;
    }
}
