package com.francesco.passwordmanager2026.GUI.Dialog;

import com.francesco.passwordmanager2026.Controller.CredenzialiController;
import com.francesco.passwordmanager2026.GUI.DashboardFrame;

import javax.swing.*;
import java.awt.*;

public class ModificaPasswordCredenzialeDialog extends JDialog {

    private JPasswordField txtNuovaPassword;
    private JPasswordField txtConfermaPassword;
    private JButton btnSalva;
    private JButton btnAnnulla;

    private int idCredenziale;
    private CredenzialiController controller;
    private DashboardFrame dashboard;

    public ModificaPasswordCredenzialeDialog(JFrame parent, int idCredenziale, DashboardFrame dashboard) {
        super(parent, "Modifica Password Credenziale", true);

        this.idCredenziale = idCredenziale;
        this.dashboard = dashboard;
        this.controller = new CredenzialiController();

        initComponents();
        initLayout();
        initListeners();

        setSize(400, 200);
        setLocationRelativeTo(parent);
        setResizable(false);
    }

    private void initComponents() {
        txtNuovaPassword = new JPasswordField(20);
        txtConfermaPassword = new JPasswordField(20);
        btnSalva = new JButton("Salva");
        btnAnnulla = new JButton("Annulla");
    }

    private void initLayout() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Nuova Password
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(new JLabel("Nuova Password:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        mainPanel.add(txtNuovaPassword, gbc);

        // Conferma Password
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        mainPanel.add(new JLabel("Conferma Password:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        mainPanel.add(txtConfermaPassword, gbc);

        // Pulsanti
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(btnSalva);
        buttonPanel.add(btnAnnulla);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(buttonPanel, gbc);

        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
    }

    private void initListeners() {
        btnAnnulla.addActionListener(e -> dispose());

        btnSalva.addActionListener(e -> salvaPassword());
    }

    private void salvaPassword() {
        String nuovaPassword = new String(txtNuovaPassword.getPassword());
        String confermaPassword = new String(txtConfermaPassword.getPassword());

        // Validazione input
        if (nuovaPassword.isEmpty() || confermaPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Tutti i campi sono obbligatori.",
                    "Errore",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!nuovaPassword.equals(confermaPassword)) {
            JOptionPane.showMessageDialog(this,
                    "Le password non coincidono.",
                    "Errore",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (nuovaPassword.length() < 4) {
            JOptionPane.showMessageDialog(this,
                    "La password deve contenere almeno 4 caratteri.",
                    "Errore",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Salva la nuova password
        boolean successo = controller.modificaPassword(idCredenziale, nuovaPassword);

        if (successo) {
            JOptionPane.showMessageDialog(this,
                    "Password aggiornata con successo!",
                    "Successo",
                    JOptionPane.INFORMATION_MESSAGE);
            dashboard.caricaCredenziali();
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Errore durante l'aggiornamento della password.",
                    "Errore",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
