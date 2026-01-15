package com.francesco.passwordmanager2026.GUI;

import com.francesco.passwordmanager2026.Controller.CredenzialiController;

import javax.swing.*;
import java.awt.*;

public class ModificaPasswordCredenzialeFrame extends JFrame {

    private JPasswordField passwordField;
    private JButton salvaButton;
    private JButton annullaButton;

    private int idCredenziale;
    private CredenzialiController controller;
    private DashboardFrame dashboard;

    public ModificaPasswordCredenzialeFrame(int idCredenziale, DashboardFrame dashboard) {
        super("Modifica Password");

        this.idCredenziale = idCredenziale;
        this.dashboard = dashboard;
        this.controller = new CredenzialiController();

        initComponents();
        initLayout();
        initListeners();

        setSize(350, 150);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void initComponents() {
        passwordField = new JPasswordField(20);
        salvaButton = new JButton("Aggiorna Password");
        annullaButton = new JButton("Annulla");
    }

    private void initLayout() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Nuova Password:"), gbc);

        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(salvaButton, gbc);

        gbc.gridx = 1;
        panel.add(annullaButton, gbc);

        add(panel);
    }

    private void initListeners() {

        salvaButton.addActionListener(e -> {

            String nuovaPassword = new String(passwordField.getPassword());

            boolean ok = controller.modificaPassword(idCredenziale, nuovaPassword);

            if (ok) {
                JOptionPane.showMessageDialog(this, "Password aggiornata!");
                dashboard.caricaCredenziali();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Errore durante l'aggiornamento.");
            }
        });

        annullaButton.addActionListener(e -> dispose());
    }
}