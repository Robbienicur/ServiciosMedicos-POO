package Emergencias;

import javax.swing.*;
import java.awt.*;
import Utilidades.ColoresUDLAP;
import Utilidades.ui.BotonUDLAP;

public class PanelControlLlamadaEmergencia extends JPanel {
    public PanelControlLlamadaEmergencia(JTextField[] campos) {
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        setBackground(ColoresUDLAP.FONDO_GENERAL);
        setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JButton botonRegistrar = BotonUDLAP.primario("Registrar Emergencia");
        add(botonRegistrar);

        JButton botonLimpiar = BotonUDLAP.secundario("Limpiar");
        add(botonLimpiar);

        JButton botonBuscar = BotonUDLAP.neutro("Buscar");
        add(botonBuscar);
    }
}
