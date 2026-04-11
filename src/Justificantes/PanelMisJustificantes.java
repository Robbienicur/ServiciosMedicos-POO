package Justificantes;

import BaseDeDatos.ConexionSQLite;
import Utilidades.ColoresUDLAP;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

public class PanelMisJustificantes extends JPanel {
    private final int idAlumno;

    public PanelMisJustificantes(int idAlumno) {
        this.idAlumno = idAlumno;

        setLayout(new BorderLayout());
        setBackground(ColoresUDLAP.FONDO_GENERAL);

        JLabel titulo = new JLabel("Mis Justificantes", SwingConstants.LEFT);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titulo.setForeground(ColoresUDLAP.TEXTO_PRINCIPAL);
        titulo.setBorder(BorderFactory.createCompoundBorder(
                new MatteBorder(0, 0, 2, 0, ColoresUDLAP.VERDE_PRIMARIO),
                BorderFactory.createEmptyBorder(16, 24, 16, 24)));
        add(titulo, BorderLayout.NORTH);

        JPanel listaPanel = new JPanel();
        listaPanel.setLayout(new BoxLayout(listaPanel, BoxLayout.Y_AXIS));
        listaPanel.setBackground(ColoresUDLAP.FONDO_GENERAL);
        listaPanel.setBorder(BorderFactory.createEmptyBorder(8, 24, 16, 24));

        JScrollPane scroll = new JScrollPane(listaPanel);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(ColoresUDLAP.FONDO_GENERAL);
        add(scroll, BorderLayout.CENTER);

        for (JustificanteItem item : obtenerJustificantes()) {
            listaPanel.add(item);
            listaPanel.add(Box.createVerticalStrut(10));
        }
    }

    private java.util.List<JustificanteItem> obtenerJustificantes() {
        java.util.List<JustificanteItem> lista = new ArrayList<>();
        String sql = """
                    SELECT Fecha, Profesor, Motivo, Estado
                    FROM SolicitudesJustificantes
                    WHERE IDAlumno = ?
                    ORDER BY Fecha DESC
                """;

        try (Connection con = ConexionSQLite.conectar();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idAlumno);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String fecha = rs.getString("Fecha");
                String profesor = rs.getString("Profesor");
                String motivo = rs.getString("Motivo");
                String estado = rs.getString("Estado");
                lista.add(new JustificanteItem(fecha, profesor, motivo, estado));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al obtener justificantes:\n" + e.getMessage(),
                    "Error de BD", JOptionPane.ERROR_MESSAGE);
        }

        return lista;
    }

    static class JustificanteItem extends JPanel {
        public JustificanteItem(String fecha, String profesor, String motivo, String estado) {
            setLayout(new GridLayout(0, 2, 8, 4));
            setBackground(ColoresUDLAP.FONDO_CARD);
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(ColoresUDLAP.BORDE),
                    BorderFactory.createEmptyBorder(10, 14, 10, 14)));
            setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

            agregarFila("Fecha:", fecha);
            agregarFila("Profesor:", profesor);
            agregarFila("Motivo:", motivo);
            agregarFila("Estado:", estado);
        }

        private void agregarFila(String etiqueta, String valor) {
            JLabel lblEtiqueta = new JLabel(etiqueta);
            lblEtiqueta.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            lblEtiqueta.setForeground(ColoresUDLAP.TEXTO_SECUNDARIO);

            JLabel lblValor = new JLabel(valor != null ? valor : "-");
            lblValor.setFont(new Font("Segoe UI", Font.BOLD, 13));
            lblValor.setForeground(ColoresUDLAP.TEXTO_PRINCIPAL);

            add(lblEtiqueta);
            add(lblValor);
        }
    }
}
