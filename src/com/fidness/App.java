package com.fidness;
import com.fidness.ui.LoginFrame;
public class App {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            try { javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) {}
            new LoginFrame().setVisible(true);
        });
    }
}
