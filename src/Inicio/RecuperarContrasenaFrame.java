package Inicio;

import Utilidades.ColoresUDLAP;
import Utilidades.ui.BotonUDLAP;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class RecuperarContrasenaFrame extends JFrame {
    private JTextField emailField;
    private JButton enviarBtn;

    public RecuperarContrasenaFrame() {
        setTitle("Recuperar Contraseña");
        setSize(420, 280);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel contenido = new JPanel(new GridBagLayout());
        contenido.setBackground(ColoresUDLAP.FONDO_GENERAL);
        contenido.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        setContentPane(contenido);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 2;

        // Título
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel titulo = new JLabel("Recuperar Contraseña", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titulo.setForeground(ColoresUDLAP.TEXTO_PRINCIPAL);
        contenido.add(titulo, gbc);

        // Campo correo
        gbc.gridy = 1;
        JLabel emailLabel = new JLabel("Correo Electrónico:");
        emailLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        emailLabel.setForeground(ColoresUDLAP.TEXTO_SECUNDARIO);
        contenido.add(emailLabel, gbc);

        gbc.gridy = 2;
        emailField = new JTextField();
        emailField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        emailField.setBorder(new CompoundBorder(
                BorderFactory.createLineBorder(ColoresUDLAP.BORDE),
                new EmptyBorder(8, 10, 8, 10)));
        contenido.add(emailField, gbc);

        // Botón
        gbc.gridy = 3;
        gbc.insets = new Insets(16, 8, 8, 8);
        enviarBtn = BotonUDLAP.primario("Enviar Enlace de Recuperación");
        getRootPane().setDefaultButton(enviarBtn);
        contenido.add(enviarBtn, gbc);

        enviarBtn.addActionListener(e -> {
            String email = emailField.getText();
            if (!email.isEmpty() && email.contains("@")) {
                JOptionPane.showMessageDialog(this, "Se ha enviado un enlace de recuperación a: " + email, "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, ingrese un correo válido.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RecuperarContrasenaFrame::new);
    }
}
