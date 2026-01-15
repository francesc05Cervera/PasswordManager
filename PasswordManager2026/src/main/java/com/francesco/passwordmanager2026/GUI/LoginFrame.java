package com.francesco.passwordmanager2026.GUI;

import com.francesco.passwordmanager2026.Controller.AccountUtenteController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {

    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;

    private AccountUtenteController accountController;

    public LoginFrame() {
        super("PasswordManager2026 - Login");

        accountController = new AccountUtenteController();

        initComponents();
        initLayout();
        initListeners();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void initComponents() {
        emailField = new JTextField(20);
        passwordField = new JPasswordField(20);

        loginButton = new JButton("Accedi");
        registerButton = new JButton("Registrati");
    }

    private void initLayout() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Email label
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Email:"), gbc);

        // Email field
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(emailField, gbc);

        // Password label
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Password:"), gbc);

        // Password field
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(passwordField, gbc);

        // Login button
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(loginButton, gbc);

        // Register button
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(registerButton, gbc);

        add(panel);
    }

    private void initListeners() {

        // LOGIN
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String email = emailField.getText().trim();
                String password = new String(passwordField.getPassword());

                boolean ok = accountController.Login(email, password);

                if (ok) {
                    JOptionPane.showMessageDialog(LoginFrame.this,
                            "Login effettuato con successo!",
                            "Successo",
                            JOptionPane.INFORMATION_MESSAGE);

                    // Qui potrai aprire la Dashboard
                    new DashboardFrame(email).setVisible(true);
                    dispose();

                } else {
                    JOptionPane.showMessageDialog(LoginFrame.this,
                            "Email o password non corretti.",
                            "Errore",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // REGISTRAZIONE
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Apri la finestra di registrazione
                new RegistrazioneFrame().setVisible(true);
                // opzionale: chiudere il login
                // dispose();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}