package GestionEnfermedades;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import BaseDeDatos.ConexionSQLite;
import Utilidades.ColoresUDLAP;
import Utilidades.PanelProvider;

public class PanelHistorialMedico extends JPanel implements PanelProvider {
    private final int idPaciente;

    public PanelHistorialMedico(int idPaciente) {
        this.idPaciente = idPaciente;
        setLayout(new BorderLayout());
        setBackground(ColoresUDLAP.FONDO_GENERAL);

        // Título
        JLabel titulo = new JLabel("Expediente Médico del Paciente", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titulo.setForeground(ColoresUDLAP.TEXTO_PRINCIPAL);
        titulo.setBorder(BorderFactory.createCompoundBorder(
                new MatteBorder(0, 0, 2, 0, ColoresUDLAP.VERDE_PRIMARIO),
                BorderFactory.createEmptyBorder(20, 0, 20, 0)));
        add(titulo, BorderLayout.NORTH);

        // Contenedor de todo
        JPanel contenedorPrincipal = new JPanel();
        contenedorPrincipal.setLayout(new BoxLayout(contenedorPrincipal, BoxLayout.Y_AXIS));
        contenedorPrincipal.setBackground(ColoresUDLAP.FONDO_GENERAL);
        contenedorPrincipal.setBorder(BorderFactory.createEmptyBorder(16, 24, 16, 24));

        // Información del paciente
        JPanel panelInfo = obtenerDatosGenerales();
        if (panelInfo != null) {
            contenedorPrincipal.add(panelInfo);
        }

        contenedorPrincipal.add(Box.createVerticalStrut(15));

        // Historial de consultas
        for (HistorialMedicoItem item : obtenerHistorialConsultas()) {
            contenedorPrincipal.add(item);
            contenedorPrincipal.add(Box.createVerticalStrut(10));
        }

        JScrollPane scroll = new JScrollPane(contenedorPrincipal);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(ColoresUDLAP.FONDO_GENERAL);
        add(scroll, BorderLayout.CENTER);
    }

    private JPanel obtenerDatosGenerales() {
        String sql = """
                    SELECT a.Nombre, a.ApellidoPaterno, a.ApellidoMaterno,
                    r.Edad, r.Altura, r.Peso, r.EnfermedadesPreexistentes,
                    r.Medicacion, r.Alergias
                    FROM Registro r
                    JOIN InformacionAlumno a ON r.ID = a.ID
                    WHERE r.ID = ?
                """;

        try (Connection con = ConexionSQLite.conectar();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idPaciente);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String nombre = rs.getString("Nombre") + " " + rs.getString("ApellidoPaterno") + " "
                        + rs.getString("ApellidoMaterno");
                int edad = rs.getInt("Edad");
                float altura = rs.getFloat("Altura");
                float peso = rs.getFloat("Peso");
                String enf = rs.getString("EnfermedadesPreexistentes");
                String meds = rs.getString("Medicacion");
                String alerg = rs.getString("Alergias");

                JPanel panel = new JPanel(new GridLayout(0, 2, 8, 6));
                panel.setBackground(ColoresUDLAP.FONDO_CARD);
                panel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(ColoresUDLAP.BORDE),
                        BorderFactory.createEmptyBorder(12, 16, 12, 16)));

                agregarFilaDatos(panel, "Nombre completo:", nombre);
                agregarFilaDatos(panel, "Edad:", edad + " años");
                agregarFilaDatos(panel, "Altura:", String.format("%.2f m", altura));
                agregarFilaDatos(panel, "Peso:", String.format("%.2f kg", peso));
                agregarFilaDatos(panel, "Enfermedades preexistentes:", enf);
                agregarFilaDatos(panel, "Medicación actual:", meds);
                agregarFilaDatos(panel, "Alergias:", alerg);

                JPanel contenedor = new JPanel(new BorderLayout());
                contenedor.setOpaque(false);

                JLabel subtitulo = new JLabel("Datos Generales");
                subtitulo.setFont(new Font("Segoe UI", Font.BOLD, 14));
                subtitulo.setForeground(ColoresUDLAP.TEXTO_PRINCIPAL);
                subtitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));
                contenedor.add(subtitulo, BorderLayout.NORTH);
                contenedor.add(panel, BorderLayout.CENTER);
                return contenedor;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al obtener información general:\n" + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        return null;
    }

    private void agregarFilaDatos(JPanel panel, String etiqueta, String valor) {
        JLabel lblEtiqueta = new JLabel(etiqueta);
        lblEtiqueta.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblEtiqueta.setForeground(ColoresUDLAP.TEXTO_SECUNDARIO);

        JLabel lblValor = new JLabel(valor != null ? valor : "-");
        lblValor.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblValor.setForeground(ColoresUDLAP.TEXTO_PRINCIPAL);

        panel.add(lblEtiqueta);
        panel.add(lblValor);
    }

    private ArrayList<HistorialMedicoItem> obtenerHistorialConsultas() {
        ArrayList<HistorialMedicoItem> lista = new ArrayList<>();
        String sql = """
                    SELECT FechaConsulta, Sintomas, Medicamentos, Diagnostico, Receta
                    FROM Consultas
                    WHERE IDPaciente = ?
                    ORDER BY FechaConsulta DESC
                """;

        try (Connection con = ConexionSQLite.conectar();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idPaciente);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String fecha = rs.getString("FechaConsulta");
                String sintomas = rs.getString("Sintomas");
                String medicamentos = rs.getString("Medicamentos");
                String diagnostico = rs.getString("Diagnostico");
                String receta = rs.getString("Receta");

                lista.add(new HistorialMedicoItem(fecha, diagnostico, sintomas, medicamentos, receta));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al obtener el historial médico:\n" + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        return lista;
    }

    @Override
    public JPanel getPanel() {
        return this;
    }

    @Override
    public String getPanelName() {
        return "historialMedico";
    }
}
