package Utilidades;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import Utilidades.ui.BotonUDLAP;

public class PanelBotonesFormulario extends JPanel {

    public final JButton btnGuardar;
    public final JButton btnBuscar;
    public final JButton btnLimpiar;

    public PanelBotonesFormulario(String textoGuardar, String textoBuscar, String textoLimpiar) {
        setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        setOpaque(false);

        btnGuardar = BotonUDLAP.primario(textoGuardar);
        btnBuscar = BotonUDLAP.secundario(textoBuscar);
        btnLimpiar = BotonUDLAP.neutro(textoLimpiar);

        add(btnGuardar);
        add(btnBuscar);
        add(btnLimpiar);
    }

    public void setListeners(ActionListener guardar, ActionListener buscar, ActionListener limpiar) {
        btnGuardar.addActionListener(guardar);
        btnBuscar.addActionListener(buscar);
        btnLimpiar.addActionListener(limpiar);
    }
}
