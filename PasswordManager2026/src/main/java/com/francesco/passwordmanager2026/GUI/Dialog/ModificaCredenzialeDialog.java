package com.francesco.passwordmanager2026.GUI.Dialog;

import com.francesco.passwordmanager2026.Controller.CredenzialiController;
import com.francesco.passwordmanager2026.GUI.DashboardFrame;
import com.francesco.passwordmanager2026.GUI.Theme.*;

import javax.swing.*;
import java.awt.*;

public class ModificaCredenzialeDialog extends JDialog {

    private JPasswordField txtNuovaPassword;
    private JPasswordField txtConfermaPassword;
    private JCheckBox chkMostraPassword;
    private JButton btnSalva;
    private JButton btnAnnulla;

    private int idCredenziale;
    private CredenzialiController controller;
    private DashboardFrame dashboard;

    public ModificaCredenzialeDialog(JFrame parent, int idCredenziale, DashboardFrame dashboard) {
        super(parent, "Modifica Password Credenziale", true);

        this.idCredenziale = idCredenziale;
        this.dashboard = dashboard;
        this.controller = new CredenzialiController();

        initComponents();
        initLayout();
        initListeners();

        setSize(480, 420);
        setLocationRelativeTo(parent);
        setResizable(false);
        getContentPane().setBackground(UITheme.DARK_BG);
    }

    private void initComponents() {
        txtNuovaPassword = createStyledPasswordField();
        txtConfermaPassword = createStyledPasswordField();
        
        chkMostraPassword = createStyledCheckBox();
        
        btnSalva = new StyledButton("Salva", UITheme.SUCCESS_COLOR);
        btnSalva.setPreferredSize(new Dimension(160, 40));
        
        btnAnnulla = new StyledButton("Annulla", UITheme.TEXT_SECONDARY);
        btnAnnulla.setPreferredSize(new Dimension(160, 40));
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
        JLabel iconLabel = new JLabel("✏️");
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 35));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Titolo
        JLabel titleLabel = new JLabel("Modifica Password");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(UITheme.TEXT_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Sottotitolo
        JLabel subtitleLabel = new JLabel("Aggiorna la password della credenziale");
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

        // Nuova Password
        JLabel lblNuova = createFieldLabel("Nuova Password");
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(lblNuova, gbc);

        gbc.gridy = 1;
        panel.add(txtNuovaPassword, gbc);

        // Conferma Password
        JLabel lblConferma = createFieldLabel("Conferma Password");
        gbc.gridy = 2;
        panel.add(lblConferma, gbc);

        gbc.gridy = 3;
        panel.add(txtConfermaPassword, gbc);

        // Checkbox
        gbc.gridy = 4;
        gbc.insets = new Insets(8, 0, 5, 0);
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
            txtNuovaPassword.setEchoChar(echoChar);
            txtConfermaPassword.setEchoChar(echoChar);
        });

        btnAnnulla.addActionListener(e -> dispose());

        btnSalva.addActionListener(e -> salvaPassword());

        // Enter per salvare
        txtConfermaPassword.addActionListener(e -> salvaPassword());
    }

    private void salvaPassword() {
        String nuovaPassword = new String(txtNuovaPassword.getPassword());
        String confermaPassword = new String(txtConfermaPassword.getPassword());

        // Validazione input
        if (nuovaPassword.isEmpty() || confermaPassword.isEmpty()) {
            showError("Tutti i campi sono obbligatori.");
            return;
        }

        if (!nuovaPassword.equals(confermaPassword)) {
            showError("Le password non coincidono.");
            txtNuovaPassword.setText("");
            txtConfermaPassword.setText("");
            return;
        }

        if (nuovaPassword.length() < 4) {
            showError("La password deve contenere almeno 4 caratteri.");
            return;
        }

        // Salva la nuova password
        boolean successo = controller.modificaPassword(idCredenziale, nuovaPassword);

        if (successo) {
            showSuccess("Password aggiornata con successo!");
            dashboard.caricaCredenziali();
            dispose();
        } else {
            showError("Errore durante l'aggiornamento della password.");
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
