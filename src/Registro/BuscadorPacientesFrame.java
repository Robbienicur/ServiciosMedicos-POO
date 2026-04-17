package Registro;

import BaseDeDatos.ConexionSQLite;
import Utilidades.ColoresUDLAP;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class BuscadorPacientesFrame extends JFrame {

    private final JTextField campoBusqueda;
    private final JTable tablaResultados;
    private final DefaultTableModel modeloTabla;
    private int selectedId = -1;

    public BuscadorPacientesFrame() {
        setTitle("Buscar Paciente");
        setSize(600, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // --- Panel superior: campo de busqueda y boton ---
        JPanel panelBusqueda = new JPanel(new BorderLayout(5, 0));
        panelBusqueda.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));

        campoBusqueda = new JTextField();
        campoBusqueda.setFont(new Font("Arial", Font.PLAIN, 14));

        JButton botonBuscar = new JButton("Buscar");
        botonBuscar.setBackground(ColoresUDLAP.VERDE_SOLIDO);
        botonBuscar.setForeground(Color.WHITE);
        botonBuscar.setFont(new Font("Arial", Font.BOLD, 13));
        botonBuscar.setFocusPainted(false);

        panelBusqueda.add(new JLabel("Nombre: "), BorderLayout.WEST);
        panelBusqueda.add(campoBusqueda, BorderLayout.CENTER);
        panelBusqueda.add(botonBuscar, BorderLayout.EAST);

        add(panelBusqueda, BorderLayout.NORTH);

        // --- Tabla de resultados ---
        String[] columnas = {"ID", "Nombre", "Apellido Paterno", "Apellido Materno", "Correo"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaResultados = new JTable(modeloTabla);
        tablaResultados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaResultados.setFont(new Font("Arial", Font.PLAIN, 12));
        tablaResultados.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));

        JScrollPane scrollPane = new JScrollPane(tablaResultados);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 5, 10));
        add(scrollPane, BorderLayout.CENTER);

        // --- Panel inferior: boton Seleccionar ---
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelInferior.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        JButton botonSeleccionar = new JButton("Seleccionar");
        botonSeleccionar.setBackground(ColoresUDLAP.NARANJA_BARRA);
        botonSeleccionar.setForeground(Color.WHITE);
        botonSeleccionar.setFont(new Font("Arial", Font.BOLD, 13));
        botonSeleccionar.setFocusPainted(false);

        panelInferior.add(botonSeleccionar);
        add(panelInferior, BorderLayout.SOUTH);

        // --- Acciones ---
        botonBuscar.addActionListener(e -> buscarPacientes());
        campoBusqueda.addActionListener(e -> buscarPacientes());

        botonSeleccionar.addActionListener(e -> {
            int filaSeleccionada = tablaResultados.getSelectedRow();
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(this,
                        "Seleccione un paciente de la tabla.",
                        "Sin seleccion", JOptionPane.WARNING_MESSAGE);
                return;
            }
            selectedId = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
            dispose();
        });
    }

    private void buscarPacientes() {
        String texto = campoBusqueda.getText().trim();
        if (texto.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Ingrese un nombre para buscar.",
                    "Campo vacio", JOptionPane.WARNING_MESSAGE);
            return;
        }

        modeloTabla.setRowCount(0);
        String wildcard = "%" + texto + "%";
        String sql = "SELECT ID, Nombre, ApellidoPaterno, ApellidoMaterno, Correo " +
                "FROM InformacionAlumno " +
                "WHERE Nombre LIKE ? OR ApellidoPaterno LIKE ? OR ApellidoMaterno LIKE ?";

        try (Connection conn = ConexionSQLite.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, wildcard);
            ps.setString(2, wildcard);
            ps.setString(3, wildcard);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    modeloTabla.addRow(new Object[]{
                            rs.getInt("ID"),
                            rs.getString("Nombre"),
                            rs.getString("ApellidoPaterno"),
                            rs.getString("ApellidoMaterno"),
                            rs.getString("Correo")
                    });
                }
            }

            if (modeloTabla.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this,
                        "No se encontraron pacientes.",
                        "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al buscar pacientes:\n" + ex.getMessage(),
                    "Error de BD", JOptionPane.ERROR_MESSAGE);
        }
    }

    public int getSelectedId() {
        return selectedId;
    }
}
