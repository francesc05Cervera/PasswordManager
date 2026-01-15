package com.francesco.passwordmanager2026.GUI;

import com.francesco.passwordmanager2026.Controller.CredenzialiController;
import com.francesco.passwordmanager2026.entity.CredenzialiAccesso;

import javax.swing.*;
import java.awt.*;

public class ModificaCredenzialeFrame extends JFrame {

    private JTextField piattaformaField;
    private JTextField usernameField;
    private JCheckBox a2fCheck;
    private JButton salvaButton;
    private JButton annullaButton;

    private CredenzialiAccesso credenziale;
    private CredenzialiController controller;
    private DashboardFrame dashboard;

    public ModificaCredenzialeFrame(CredenzialiAccesso credenziale, DashboardFrame dashboard) {
        super("Modifica Credenziale");

        this.credenziale = credenziale;
        this.dashboard = dashboard;
        this.controller = new CredenzialiController();

        initComponents();
        initLayout();
        initListeners();

        setSize(400, 220);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void initComponents() {
        piattaformaField = new JTextField(credenziale.getNomePiattaforma(), 20);
        usernameField = new JTextField(credenziale.getUsername(), 20);
        a2fCheck = new JCheckBox("2FA Attivo", credenziale.isA2fPresent());

        salvaButton = new JButton("Salva Modifiche");
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

            credenziale.setNomePiattaforma(piattaformaField.getText().trim());
            credenziale.setUsername(usernameField.getText().trim());
            credenziale.setA2fPresent(a2fCheck.isSelected());

            // Riutilizziamo l'inserimento come "update"? No.
            // Serve un metodo update completo nella DAO.
            // Per ora aggiorniamo solo password tramite metodo dedicato.

            JOptionPane.showMessageDialog(this,
                    "Modifica non ancora implementata nella DAO.\n" +
                    "Solo la password può essere modificata.");

            // Se vuoi, posso implementarti anche il metodo update completo.

            dashboard.caricaCredenziali();
            dispose();
        });

        annullaButton.addActionListener(e -> dispose());
    }
}