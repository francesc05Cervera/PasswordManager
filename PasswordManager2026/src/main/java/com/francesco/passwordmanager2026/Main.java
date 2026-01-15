package com.francesco.passwordmanager2026;

import javax.swing.SwingUtilities;
import com.francesco.passwordmanager2026.GUI.LoginFrame;

public class Main {

    public static void main(String[] args) {

        // Avvio thread grafico Swing
        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}