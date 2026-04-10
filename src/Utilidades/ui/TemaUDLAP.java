package Utilidades.ui;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.*;

public class TemaUDLAP {

    public static void inicializar() {
        try {
            FlatLightLaf.setup();
            UIManager.put("Button.arc", 8);
            UIManager.put("Component.arc", 8);
            UIManager.put("TextComponent.arc", 4);
            UIManager.put("Component.focusWidth", 1);
            UIManager.put("Component.focusColor", new Color(0, 102, 0));
            UIManager.put("Component.innerFocusWidth", 0);
            UIManager.put("Button.focusedBackground", null);
            UIManager.put("ScrollBar.thumbArc", 8);
            UIManager.put("ScrollBar.track", new Color(248, 249, 250));
            UIManager.put("Table.selectionBackground", new Color(232, 245, 233));
            UIManager.put("Table.selectionForeground", new Color(45, 52, 54));
            UIManager.put("List.selectionBackground", new Color(232, 245, 233));
            UIManager.put("List.selectionForeground", new Color(45, 52, 54));
            UIManager.put("TextField.placeholderForeground", new Color(99, 110, 114));
            UIManager.put("PasswordField.placeholderForeground", new Color(99, 110, 114));
            UIManager.put("TitlePane.background", Color.WHITE);
        } catch (Exception e) {
            System.err.println("Error inicializando tema: " + e.getMessage());
        }
    }
}
