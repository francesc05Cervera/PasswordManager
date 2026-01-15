package com.francesco.passwordmanager2026.GUI;

import com.francesco.passwordmanager2026.Controller.AccountUtenteController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistrazioneFrame extends JFrame {

    private JTextField emailField;
    private JTextField telefonoField;
    private JTextField nomeField;
    private JTextField cognomeField;
    private JPasswordField passwordField;
    private JButton registraButton;
    private JButton annullaButton;

    private AccountUtenteController accountController;

    public RegistrazioneFrame() {
        super("PasswordManager2026 - Registrazione");

        accountController = new AccountUtenteController();

        initComponents();
        initLayout();
        initListeners();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(450, 300);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void initComponents() {
        emailField = new JTextField(20);
        telefonoField = new JTextField(20);
        nomeField = new JTextField(20);
        cognomeField = new JTextField(20);
        passwordField = new JPasswordField(20);

        registraButton = new JButton("Crea Account");
        annullaButton = new JButton("Annulla");
    }

    private void initLayout() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        panel.add(emailField, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Telefono:"), gbc);
        gbc.gridx = 1;
        panel.add(telefonoField, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        panel.add(nomeField, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Cognome:"), gbc);
        gbc.gridx = 1;
        panel.add(cognomeField, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        panel.add(passwordField, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        panel.add(registraButton, gbc);
        gbc.gridx = 1;
        panel.add(annullaButton, gbc);

        add(panel);
    }

    private void initListeners() {

        registraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String email = emailField.getText().trim();
                String telefono = telefonoField.getText().trim();
                String nome = nomeField.getText().trim();
                String cognome = cognomeField.getText().trim();
                String password = new String(passwordField.getPassword());

                boolean ok = accountController.RegistraNuovoUtente(
                        email, telefono, nome, cognome, password
                );

                if (ok) {
                    JOptionPane.showMessageDialog(RegistrazioneFrame.this,
                            "Registrazione completata con successo!",
                            "Successo",
                            JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(RegistrazioneFrame.this,
                            "Registrazione fallita. Controlla i dati o l'esistenza dell'account.",
                            "Errore",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        annullaButton.addActionListener(e -> dispose());
    }
}