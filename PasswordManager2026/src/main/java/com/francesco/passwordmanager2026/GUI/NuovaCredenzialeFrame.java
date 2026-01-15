package com.francesco.passwordmanager2026.GUI;

import com.francesco.passwordmanager2026.Controller.CredenzialiController;

import javax.swing.*;
import java.awt.*;

public class NuovaCredenzialeFrame extends JFrame {

    private JTextField piattaformaField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JCheckBox a2fCheck;
    private JButton salvaButton;
    private JButton annullaButton;

    private String emailCreatore;
    private CredenzialiController controller;
    private DashboardFrame dashboard;

    public NuovaCredenzialeFrame(String emailCreatore, DashboardFrame dashboard) {
        super("Nuova Credenziale");

        this.emailCreatore = emailCreatore;
        this.dashboard = dashboard;
        this.controller = new CredenzialiController();

        initComponents();
        initLayout();
        initListeners();

        setSize(400, 250);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void initComponents() {
        piattaformaField = new JTextField(20);
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        a2fCheck = new JCheckBox("2FA Attivo");

        salvaButton = new JButton("Salva");
        annullaButton = new JButton("Annulla");
    }

    private void initLayout() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Piattaforma:"), gbc);
        gbc.gridx = 1;
        panel.add(piattaformaField, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        panel.add(usernameField, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        panel.add(passwordField, gbc);
        row++;

        gbc.gridx = 1; gbc.gridy = row;
        panel.add(a2fCheck, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        panel.add(salvaButton, gbc);
        gbc.gridx = 1;
        panel.add(annullaButton, gbc);

        add(panel);
    }

    private void initListeners() {

        salvaButton.addActionListener(e -> {
            String piattaforma = piattaformaField.getText().trim();
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());
            boolean a2f = a2fCheck.isSelected();

            boolean ok = controller.inserisciCredenziale(
                    piattaforma, username, password, emailCreatore, a2f
            );

            if (ok) {
                JOptionPane.showMessageDialog(this, "Credenziale salvata!");
                dashboard.caricaCredenziali();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Errore nel salvataggio.");
            }
        });

        annullaButton.addActionListener(e -> dispose());
    }
}