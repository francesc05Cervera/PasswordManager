package com.francesco.passwordmanager2026.GUI;

import com.francesco.passwordmanager2026.Controller.AccountUtenteController;
import com.francesco.passwordmanager2026.GUI.Theme.*;

import javax.swing.*;
import java.awt.*;

public class RegistrazioneFrame extends JFrame {

    private AccountUtenteController accountController;

    private JTextField txtEmail;
    private JTextField txtTelefono;
    private JTextField txtNome;
    private JTextField txtCognome;
    private JPasswordField txtPassword;
    private JPasswordField txtConfermaPassword;
    private JCheckBox chkMostraPassword;
    private JButton btnRegistrati;
    private JButton btnTornaLogin;

    public RegistrazioneFrame() {
        super("PasswordManager 2026 - Registrazione");

        this.accountController = new AccountUtenteController();

        initComponents();
        initLayout();
        initListeners();

        setSize(550, 850);  // Aumentato da 750 a 850 (più alto)
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        getContentPane().setBackground(UITheme.DARK_BG);
    }


    private void initComponents() {
        txtEmail = createStyledTextField();
        txtTelefono = createStyledTextField();
        txtNome = createStyledTextField();
        txtCognome = createStyledTextField();
        txtPassword = createStyledPasswordField();
        txtConfermaPassword = createStyledPasswordField();
        chkMostraPassword = createStyledCheckBox();

        btnRegistrati = new StyledButton("Crea Account", UITheme.SUCCESS_COLOR);
        btnRegistrati.setPreferredSize(new Dimension(220, 45));

        btnTornaLogin = new StyledButton("Torna al Login", UITheme.TEXT_SECONDARY);
        btnTornaLogin.setPreferredSize(new Dimension(220, 45));
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField(25);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        field.setBackground(UITheme.DARK_PANEL);
        field.setForeground(UITheme.TEXT_COLOR);
        field.setCaretColor(UITheme.TEXT_COLOR);
        field.setPreferredSize(new Dimension(350, 42));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UITheme.TEXT_SECONDARY, 1),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        // Effetto focus
        field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                field.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(UITheme.SUCCESS_COLOR, 2),
                        BorderFactory.createEmptyBorder(10, 15, 10, 15)
                ));
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                field.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(UITheme.TEXT_SECONDARY, 1),
                        BorderFactory.createEmptyBorder(10, 15, 10, 15)
                ));
            }
        });

        return field;
    }

    private JPasswordField createStyledPasswordField() {
        JPasswordField field = new JPasswordField(25);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        field.setBackground(UITheme.DARK_PANEL);
        field.setForeground(UITheme.TEXT_COLOR);
        field.setCaretColor(UITheme.TEXT_COLOR);
        field.setPreferredSize(new Dimension(350, 42));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UITheme.TEXT_SECONDARY, 1),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        // Effetto focus
        field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                field.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(UITheme.SUCCESS_COLOR, 2),
                        BorderFactory.createEmptyBorder(10, 15, 10, 15)
                ));
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                field.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(UITheme.TEXT_SECONDARY, 1),
                        BorderFactory.createEmptyBorder(10, 15, 10, 15)
                ));
            }
        });

        return field;
    }

    private JCheckBox createStyledCheckBox() {
        JCheckBox checkBox = new JCheckBox("Mostra password");
        checkBox.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        checkBox.setForeground(UITheme.TEXT_SECONDARY);
        checkBox.setBackground(UITheme.DARK_BG);
        checkBox.setFocusPainted(false);
        checkBox.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return checkBox;
    }

    private void initLayout() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(UITheme.DARK_BG);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));  // Ridotto da 30 a 20

        // Header
        JPanel headerPanel = createHeaderPanel();

        // Form
        JPanel formPanel = createFormPanel();

        // Buttons
        JPanel buttonPanel = createButtonPanel();

        // Footer
        JPanel footerPanel = createFooterPanel();

        mainPanel.add(headerPanel);
        mainPanel.add(Box.createVerticalStrut(20));  // Ridotto da 30 a 20
        mainPanel.add(formPanel);
        mainPanel.add(Box.createVerticalStrut(15));  // Ridotto da 25 a 15
        mainPanel.add(buttonPanel);
        mainPanel.add(Box.createVerticalStrut(10));  // Ridotto da 15 a 10
        mainPanel.add(footerPanel);

        add(mainPanel);
    }


    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(UITheme.DARK_BG);

        // Icona
        JLabel iconLabel = new JLabel("👤");
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 55));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Titolo
        JLabel titleLabel = new JLabel("Crea il tuo Account");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setForeground(UITheme.TEXT_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Sottotitolo
        JLabel subtitleLabel = new JLabel("Proteggi le tue password in modo sicuro");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitleLabel.setForeground(UITheme.TEXT_SECONDARY);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(iconLabel);
        panel.add(Box.createVerticalStrut(12));
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(6));
        panel.add(subtitleLabel);

        return panel;
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(UITheme.DARK_BG);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 0, 5, 0);  // Ridotto da 8 a 5
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1.0;

        // Email
        JLabel lblEmail = createFieldLabel("Email");
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(lblEmail, gbc);

        gbc.gridy = 1;
        panel.add(txtEmail, gbc);

        // Nome
        JLabel lblNome = createFieldLabel("Nome");
        gbc.gridy = 2;
        panel.add(lblNome, gbc);

        gbc.gridy = 3;
        panel.add(txtNome, gbc);

        // Cognome
        JLabel lblCognome = createFieldLabel("Cognome");
        gbc.gridy = 4;
        panel.add(lblCognome, gbc);

        gbc.gridy = 5;
        panel.add(txtCognome, gbc);

        // Telefono
        JLabel lblTelefono = createFieldLabel("Telefono");
        gbc.gridy = 6;
        panel.add(lblTelefono, gbc);

        gbc.gridy = 7;
        panel.add(txtTelefono, gbc);

        // Password
        JLabel lblPassword = createFieldLabel("Password");
        gbc.gridy = 8;
        panel.add(lblPassword, gbc);

        gbc.gridy = 9;
        panel.add(txtPassword, gbc);

        // Conferma Password
        JLabel lblConfermaPassword = createFieldLabel("Conferma Password");
        gbc.gridy = 10;
        panel.add(lblConfermaPassword, gbc);

        gbc.gridy = 11;
        panel.add(txtConfermaPassword, gbc);

        // Checkbox mostra password
        gbc.gridy = 12;
        gbc.insets = new Insets(3, 0, 5, 0);  // Ridotto
        panel.add(chkMostraPassword, gbc);

        return panel;
    }


    private JLabel createFieldLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label.setForeground(UITheme.TEXT_COLOR);
        return label;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(UITheme.DARK_BG);

        btnRegistrati.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnTornaLogin.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(btnRegistrati);
        panel.add(Box.createVerticalStrut(12));
        panel.add(btnTornaLogin);

        return panel;
    }

    private JPanel createFooterPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(UITheme.DARK_BG);

        JLabel footerLabel = new JLabel("Registrandoti accetti i termini di servizio");
        footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        footerLabel.setForeground(UITheme.TEXT_SECONDARY);

        panel.add(footerLabel);

        return panel;
    }

    private void initListeners() {
        // Mostra/nascondi password
        chkMostraPassword.addActionListener(e -> {
            char echoChar = chkMostraPassword.isSelected() ? (char) 0 : '•';
            txtPassword.setEchoChar(echoChar);
            txtConfermaPassword.setEchoChar(echoChar);
        });

        // Registrazione
        btnRegistrati.addActionListener(e -> handleRegistrazione());

        // Torna al login
        btnTornaLogin.addActionListener(e -> handleTornaLogin());

        // Enter per registrarsi
        txtConfermaPassword.addActionListener(e -> handleRegistrazione());
    }

    private void handleRegistrazione() {
        String email = txtEmail.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String nome = txtNome.getText().trim();
        String cognome = txtCognome.getText().trim();
        String password = new String(txtPassword.getPassword());
        String confermaPassword = new String(txtConfermaPassword.getPassword());

        // Validazione
        if (email.isEmpty() || telefono.isEmpty() || nome.isEmpty() || 
            cognome.isEmpty() || password.isEmpty() || confermaPassword.isEmpty()) {
            showError("Tutti i campi sono obbligatori!");
            return;
        }

        if (!password.equals(confermaPassword)) {
            showError("Le password non coincidono!");
            txtPassword.setText("");
            txtConfermaPassword.setText("");
            return;
        }

        if (password.length() < 6) {
            showError("La password deve contenere almeno 6 caratteri!");
            return;
        }

        if (!email.contains("@")) {
            showError("Inserisci un'email valida!");
            return;
        }

        // Registra utente
        boolean success = accountController.RegistraNuovoUtente(email, telefono, nome, cognome, password);

        if (success) {
            showSuccess("Account creato con successo!");
            dispose();
            new LoginFrame().setVisible(true);
        } else {
            showError("Errore durante la registrazione. Email già esistente?");
        }
    }

    private void handleTornaLogin() {
        dispose();
        new LoginFrame().setVisible(true);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this,
                message,
                "Errore",
                JOptionPane.ERROR_MESSAGE);
    }

    private void showSuccess(String message) {
        JOptionPane.showMessageDialog(this,
                message,
                "Successo",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
