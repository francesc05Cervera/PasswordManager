package com.francesco.passwordmanager2026.GUI;

import com.francesco.passwordmanager2026.Controller.AccountUtenteController;
import com.francesco.passwordmanager2026.GUI.Theme.*;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private AccountUtenteController accountController;

    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnRegistrati;
    private JCheckBox chkMostraPassword;

    public LoginFrame() {
        super("PasswordManager 2026 - Login");

        this.accountController = new AccountUtenteController();

        initComponents();
        initLayout();
        initListeners();

        setSize(500, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setBackground(UITheme.DARK_BG);
    }

    private void initComponents() {
        txtEmail = createStyledTextField();
        txtPassword = createStyledPasswordField();
        chkMostraPassword = createStyledCheckBox();
        
        btnLogin = new StyledButton("Accedi", UITheme.PRIMARY_COLOR);
        btnLogin.setPreferredSize(new Dimension(200, 45));
        
        btnRegistrati = new StyledButton("Crea Account", UITheme.SUCCESS_COLOR);
        btnRegistrati.setPreferredSize(new Dimension(200, 45));
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField(20);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBackground(UITheme.DARK_PANEL);
        field.setForeground(UITheme.TEXT_COLOR);
        field.setCaretColor(UITheme.TEXT_COLOR);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UITheme.TEXT_SECONDARY, 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        
        // Effetto focus
        field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                field.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(UITheme.PRIMARY_COLOR, 2),
                        BorderFactory.createEmptyBorder(8, 12, 8, 12)
                ));
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                field.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(UITheme.TEXT_SECONDARY, 1),
                        BorderFactory.createEmptyBorder(8, 12, 8, 12)
                ));
            }
        });
        
        return field;
    }

    private JPasswordField createStyledPasswordField() {
        JPasswordField field = new JPasswordField(20);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBackground(UITheme.DARK_PANEL);
        field.setForeground(UITheme.TEXT_COLOR);
        field.setCaretColor(UITheme.TEXT_COLOR);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UITheme.TEXT_SECONDARY, 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        
        // Effetto focus
        field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                field.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(UITheme.PRIMARY_COLOR, 2),
                        BorderFactory.createEmptyBorder(8, 12, 8, 12)
                ));
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                field.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(UITheme.TEXT_SECONDARY, 1),
                        BorderFactory.createEmptyBorder(8, 12, 8, 12)
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
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));

        // Header con logo e titolo
        JPanel headerPanel = createHeaderPanel();
        
        // Form panel
        JPanel formPanel = createFormPanel();
        
        // Button panel
        JPanel buttonPanel = createButtonPanel();
        
        // Footer panel
        JPanel footerPanel = createFooterPanel();

        mainPanel.add(headerPanel);
        mainPanel.add(Box.createVerticalStrut(40));
        mainPanel.add(formPanel);
        mainPanel.add(Box.createVerticalStrut(30));
        mainPanel.add(buttonPanel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(footerPanel);

        add(mainPanel);
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(UITheme.DARK_BG);

        // Icona/Logo
        JLabel iconLabel = new JLabel("🔐");
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 60));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Titolo
        JLabel titleLabel = new JLabel("PasswordManager 2026");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(UITheme.TEXT_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Sottotitolo
        JLabel subtitleLabel = new JLabel("Accedi al tuo account");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(UITheme.TEXT_SECONDARY);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(iconLabel);
        panel.add(Box.createVerticalStrut(15));
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(8));
        panel.add(subtitleLabel);

        return panel;
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(UITheme.DARK_BG);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        // Email
        JLabel lblEmail = new JLabel("Email");
        lblEmail.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblEmail.setForeground(UITheme.TEXT_COLOR);
        
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(lblEmail, gbc);

        gbc.gridy = 1;
        panel.add(txtEmail, gbc);

        // Password
        JLabel lblPassword = new JLabel("Password");
        lblPassword.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblPassword.setForeground(UITheme.TEXT_COLOR);
        
        gbc.gridy = 2;
        panel.add(lblPassword, gbc);

        gbc.gridy = 3;
        panel.add(txtPassword, gbc);

        // Checkbox mostra password
        gbc.gridy = 4;
        gbc.insets = new Insets(5, 0, 10, 0);
        panel.add(chkMostraPassword, gbc);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(UITheme.DARK_BG);

        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRegistrati.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(btnLogin);
        panel.add(Box.createVerticalStrut(15));
        panel.add(btnRegistrati);

        return panel;
    }

    private JPanel createFooterPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(UITheme.DARK_BG);

        JLabel footerLabel = new JLabel("© 2026 PasswordManager - Sicurezza garantita");
        footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        footerLabel.setForeground(UITheme.TEXT_SECONDARY);

        panel.add(footerLabel);

        return panel;
    }

    private void initListeners() {
        // Mostra/nascondi password
        chkMostraPassword.addActionListener(e -> {
            if (chkMostraPassword.isSelected()) {
                txtPassword.setEchoChar((char) 0);
            } else {
                txtPassword.setEchoChar('•');
            }
        });

        // Login
        btnLogin.addActionListener(e -> handleLogin());

        // Registrazione
        btnRegistrati.addActionListener(e -> handleRegistrazione());

        // Enter per login
        txtPassword.addActionListener(e -> handleLogin());
    }

    private void handleLogin() {
        String email = txtEmail.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (email.isEmpty() || password.isEmpty()) {
            showError("Compila tutti i campi!");
            return;
        }

        boolean loginOk = accountController.Login(email, password);

        if (loginOk) {
            dispose();
            new DashboardFrame(email).setVisible(true);
        } else {
            showError("Email o password errati!");
            txtPassword.setText("");
        }
    }

    private void handleRegistrazione() {
        dispose();
        new RegistrazioneFrame().setVisible(true);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this,
                message,
                "Errore",
                JOptionPane.ERROR_MESSAGE);
    }
}
