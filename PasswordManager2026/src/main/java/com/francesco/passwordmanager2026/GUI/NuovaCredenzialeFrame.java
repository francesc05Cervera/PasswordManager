package com.francesco.passwordmanager2026.GUI;

import com.francesco.passwordmanager2026.Controller.CredenzialiController;
import com.francesco.passwordmanager2026.GUI.Theme.*;

import javax.swing.*;
import java.awt.*;

public class NuovaCredenzialeFrame extends JDialog {

    private JTextField txtPiattaforma;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JCheckBox chkA2F;
    private JCheckBox chkMostraPassword;
    private JButton btnSalva;
    private JButton btnAnnulla;

    private String emailCreatore;
    private String passwordUtente;
    private CredenzialiController controller;
    private DashboardFrame dashboard;
	private String emailUtente;
	private DashboardFrame dashboardParent;
	private CredenzialiController credController;

    public NuovaCredenzialeFrame(String emailUtente, String passwordUtente, 
            DashboardFrame parent, CredenzialiController controller) 
    {
        super(parent, "Nuova Credenziale", true);  // parent, titolo, modal
			
			this.emailUtente = emailUtente;
			this.passwordUtente = passwordUtente;
			this.dashboardParent = parent;
			this.credController = controller;  // USA quello passato (già configurato)
			

        initComponents();
        initLayout();
        initListeners();

        setSize(480, 520);
        setLocationRelativeTo(dashboard);
        setResizable(false);
        getContentPane().setBackground(UITheme.DARK_BG);
    }

    private void initComponents() {
        txtPiattaforma = createStyledTextField();
        txtUsername = createStyledTextField();
        txtPassword = createStyledPasswordField();
        
        chkA2F = createStyledCheckBox("2FA Attivo");
        chkMostraPassword = createStyledCheckBox("Mostra password");
        
        btnSalva = new StyledButton("Salva", UITheme.SUCCESS_COLOR);
        btnSalva.setPreferredSize(new Dimension(160, 40));
        
        btnAnnulla = new StyledButton("Annulla", UITheme.TEXT_SECONDARY);
        btnAnnulla.setPreferredSize(new Dimension(160, 40));
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField(20);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBackground(UITheme.DARK_PANEL);
        field.setForeground(UITheme.TEXT_COLOR);
        field.setCaretColor(UITheme.TEXT_COLOR);
        field.setPreferredSize(new Dimension(320, 40));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UITheme.TEXT_SECONDARY, 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));

        // Effetto focus
        field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                field.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(UITheme.SUCCESS_COLOR, 2),
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
        field.setPreferredSize(new Dimension(320, 40));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UITheme.TEXT_SECONDARY, 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));

        // Effetto focus
        field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                field.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(UITheme.SUCCESS_COLOR, 2),
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

    private JCheckBox createStyledCheckBox(String text) {
        JCheckBox checkBox = new JCheckBox(text);
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
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Header
        JPanel headerPanel = createHeaderPanel();

        // Form
        JPanel formPanel = createFormPanel();

        // Buttons
        JPanel buttonPanel = createButtonPanel();

        mainPanel.add(headerPanel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(formPanel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(buttonPanel);

        add(mainPanel);
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(UITheme.DARK_BG);

        // Icona
        JLabel iconLabel = new JLabel("➕");
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 35));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Titolo
        JLabel titleLabel = new JLabel("Nuova Credenziale");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(UITheme.TEXT_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Sottotitolo
        JLabel subtitleLabel = new JLabel("Aggiungi una nuova credenziale sicura");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        subtitleLabel.setForeground(UITheme.TEXT_SECONDARY);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(iconLabel);
        panel.add(Box.createVerticalStrut(8));
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(4));
        panel.add(subtitleLabel);

        return panel;
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(UITheme.DARK_BG);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 0, 5, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1.0;

        // Piattaforma
        JLabel lblPiattaforma = createFieldLabel("Piattaforma");
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(lblPiattaforma, gbc);

        gbc.gridy = 1;
        panel.add(txtPiattaforma, gbc);

        // Username
        JLabel lblUsername = createFieldLabel("Username");
        gbc.gridy = 2;
        panel.add(lblUsername, gbc);

        gbc.gridy = 3;
        panel.add(txtUsername, gbc);

        // Password
        JLabel lblPassword = createFieldLabel("Password");
        gbc.gridy = 4;
        panel.add(lblPassword, gbc);

        gbc.gridy = 5;
        panel.add(txtPassword, gbc);

        // Checkboxes
        JPanel checkboxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        checkboxPanel.setBackground(UITheme.DARK_BG);
        checkboxPanel.add(chkMostraPassword);
        checkboxPanel.add(Box.createHorizontalStrut(20));
        checkboxPanel.add(chkA2F);

        gbc.gridy = 6;
        gbc.insets = new Insets(5, 0, 5, 0);
        panel.add(checkboxPanel, gbc);

        return panel;
    }

    private JLabel createFieldLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label.setForeground(UITheme.TEXT_COLOR);
        return label;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        panel.setBackground(UITheme.DARK_BG);

        panel.add(btnSalva);
        panel.add(btnAnnulla);

        return panel;
    }

    private void initListeners() {
        // Mostra/nascondi password
        chkMostraPassword.addActionListener(e -> {
            char echoChar = chkMostraPassword.isSelected() ? (char) 0 : '•';
            txtPassword.setEchoChar(echoChar);
        });

        // Salva
        btnSalva.addActionListener(e -> salvaNuovaCredenziale());

        // Annulla
        btnAnnulla.addActionListener(e -> dispose());

        // Enter per salvare
        txtPassword.addActionListener(e -> salvaNuovaCredenziale());
    }

    private void salvaNuovaCredenziale() {
        String piattaforma = txtPiattaforma.getText().trim();
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());
        boolean a2f = chkA2F.isSelected();

        // Validazione
        if (piattaforma.isEmpty() || username.isEmpty() || password.isEmpty()) {
            showError("Tutti i campi sono obbligatori!");
            return;
        }

        if (password.length() < 4) {
            showError("La password deve contenere almeno 4 caratteri!");
            return;
        }

        // Salva
     // CORRETTO:
        boolean ok = credController.inserisciCredenziale(
                piattaforma, username, password, emailUtente, a2f
        );


        if (ok) {
            showSuccess("Credenziale salvata con successo!");
         // CORRETTO:
            dashboardParent.caricaCredenziali();
            dispose();
        } else {
            showError("Errore durante il salvataggio.");
        }
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
