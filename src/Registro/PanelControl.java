package Registro;

import javax.swing.*;
import java.awt.*;
import Utilidades.ui.BotonUDLAP;

public class PanelControl extends JPanel {
    public PanelControl(JTextField[] campos) {
        setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JButton botonAgregar = BotonUDLAP.primario("Guardar");
        botonAgregar.addActionListener(new AgregarRegistro(campos));
        add(botonAgregar);

        JButton botonBuscar = BotonUDLAP.secundario("Buscar");
        botonBuscar.addActionListener(new Buscar(campos));
        add(botonBuscar);

        JButton botonLimpiar = BotonUDLAP.neutro("Limpiar");
        botonLimpiar.addActionListener(new LimpiarCampos(campos));
        add(botonLimpiar);
    }
}
