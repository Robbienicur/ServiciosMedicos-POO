package Consultas;

import javax.swing.*;
import java.awt.*;
import Utilidades.ui.BotonUDLAP;

public class PanelControlConsultas extends JPanel {
    public PanelControlConsultas(JTextField[] campos, JTextArea areaTexto) {
        setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JButton botonGuardar = BotonUDLAP.primario("Guardar");
        botonGuardar.addActionListener(new GuardarConsulta(campos, areaTexto));
        add(botonGuardar);

        JButton botonBuscar = BotonUDLAP.secundario("Buscar");
        botonBuscar.addActionListener(new BuscarPaciente(campos));
        add(botonBuscar);

        JButton botonLimpiar = BotonUDLAP.neutro("Limpiar");
        botonLimpiar.addActionListener(new LimpiarCamposConsulta(campos, areaTexto));
        add(botonLimpiar);
    }
}
