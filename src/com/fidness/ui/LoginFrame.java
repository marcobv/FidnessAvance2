package com.fidness.ui;
import com.fidness.data.MockData;
import com.fidness.model.Usuario;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
public class LoginFrame extends JFrame {
    private JTextField txtCorreo; private JPasswordField txtContrasena;
    public LoginFrame() {
        super("Fidness - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(420,260);
        setLocationRelativeTo(null);
        initUI();
    }
    private void initUI() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6,6,6,6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        txtCorreo = new JTextField(20);
        txtContrasena = new JPasswordField(20);
        JButton btnLogin = new JButton("Iniciar sesión");
        JButton btnRegistro = new JButton("Crear cuenta");
        btnLogin.addActionListener(this::onLogin);
        btnRegistro.addActionListener(this::onRegistro);
        gbc.gridx=0; gbc.gridy=0; panel.add(new JLabel("Correo:"), gbc);
        gbc.gridx=1; panel.add(txtCorreo, gbc);
        gbc.gridx=0; gbc.gridy=1; panel.add(new JLabel("Contraseña:"), gbc);
        gbc.gridx=1; panel.add(txtContrasena, gbc);
        gbc.gridx=0; gbc.gridy=2; panel.add(btnLogin, gbc);
        gbc.gridx=1; panel.add(btnRegistro, gbc);
        add(panel);
    }
    private void onLogin(ActionEvent e) {
        String correo = txtCorreo.getText().trim();
        String pass = new String(txtContrasena.getPassword());
        if (MockData.validarLogin(correo, pass)) {
            Usuario u = MockData.findUsuarioByCorreo(correo);
            JOptionPane.showMessageDialog(this, u.mensajeBienvenida());
            new MainFrame().setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Credenciales inválidas.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void onRegistro(ActionEvent e) {
        JTextField txtNombre = new JTextField();
        JTextField txtCorreoR = new JTextField();
        JPasswordField txtPass1 = new JPasswordField();
        JPasswordField txtPass2 = new JPasswordField();
        JPanel form = new JPanel(new GridLayout(0,2,6,6));
        form.add(new JLabel("Nombre completo:")); form.add(txtNombre);
        form.add(new JLabel("Correo electrónico:")); form.add(txtCorreoR);
        form.add(new JLabel("Contraseña:")); form.add(txtPass1);
        form.add(new JLabel("Confirmar contraseña:")); form.add(txtPass2);
        int res = JOptionPane.showConfirmDialog(this, form, "Registro de usuario", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (res == JOptionPane.OK_OPTION) {
            String nombre = txtNombre.getText().trim();
            String correo = txtCorreoR.getText().trim();
            String p1 = new String(txtPass1.getPassword());
            String p2 = new String(txtPass2.getPassword());
            if (nombre.isBlank()) { JOptionPane.showMessageDialog(this, "Por favor ingresa tu nombre.", "Campo requerido", JOptionPane.WARNING_MESSAGE); return; }
            if (!MockData.correoValido(correo)) { JOptionPane.showMessageDialog(this, "El correo ingresado no tiene un formato válido.\nEjemplo: usuario@dominio.com", "Correo inválido", JOptionPane.WARNING_MESSAGE); return; }
            if (p1.isBlank() || p2.isBlank()) { JOptionPane.showMessageDialog(this, "Debes ingresar y confirmar tu contraseña.", "Campo requerido", JOptionPane.WARNING_MESSAGE); return; }
            if (!p1.equals(p2)) { JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden.", "Error de validación", JOptionPane.WARNING_MESSAGE); return; }
            if (p1.length() < 4) { JOptionPane.showMessageDialog(this, "La contraseña debe tener al menos 4 caracteres.", "Contraseña demasiado corta", JOptionPane.WARNING_MESSAGE); return; }
            boolean ok = MockData.registrarUsuario(nombre, correo, p1);
            if (ok) { JOptionPane.showMessageDialog(this, "✅ Cuenta creada exitosamente.\nAhora puedes iniciar sesión.", "Registro exitoso", JOptionPane.INFORMATION_MESSAGE); this.txtCorreo.setText(correo); this.txtContrasena.setText(""); }
            else { JOptionPane.showMessageDialog(this, "No se pudo registrar el usuario.\nEl correo ya está registrado o los datos son inválidos.", "Error al registrar", JOptionPane.ERROR_MESSAGE); }
        }
    }
}
