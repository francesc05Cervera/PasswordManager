package com.francesco.passwordmanager2026.GUI.Dialog;

import com.francesco.passwordmanager2026.Controller.AccountUtenteController;
import com.francesco.passwordmanager2026.DAO.AccountUtenteDAO;
import com.francesco.passwordmanager2026.entity.AccountUtente;
import com.francesco.passwordmanager2026.GUI.Theme.*;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class ModificaPasswordLoginDialog extends JDialog {

    private String emailUtente;
    private AccountUtenteController accountController;
    private AccountUtenteDAO accountDAO;

    private JPasswordField txtVecchiaPassword;
    private JPasswordField txtNuovaPassword;
    private JPasswordField txtConfermaPassword;
    private JCheckBox chkMostraPassword;
    private JButton btnConferma;
    private JButton btnAnnulla;

    public ModificaPasswordLoginDialog(JFrame parent, String emailUtente) {
        super(parent, "Modifica Password Login", true);
        
        this.emailUtente = emailUtente;
        this.accountController = new AccountUtenteController();
        this.accountDAO = new AccountUtenteDAO();

        initComponents();
        initLayout();
        initListeners();

        setSize(480, 500);
        setLocationRelativeTo(parent);
        setResizable(false);
        getContentPane().setBackground(UITheme.DARK_BG);
    }

    private void initComponents() {
        txtVecchiaPassword = createStyledPasswordField();
        txtNuovaPassword = createStyledPasswordField();
        txtConfermaPassword = createStyledPasswordField();
        
        chkMostraPassword = createStyledCheckBox();
        
        btnConferma = new StyledButton("Conferma", UITheme.PRIMARY_COLOR);
        btnConferma.setPreferredSize(new Dimension(160, 40));
        
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
        JLabel iconLabel = new JLabel("🔐");
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 35));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Titolo
        JLabel titleLabel = new JLabel("Cambia Password");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(UITheme.TEXT_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Sottotitolo
        JLabel subtitleLabel = new JLabel("Aggiorna la tua password di accesso");
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

        // Vecchia Password
        JLabel lblVecchia = createFieldLabel("Vecchia Password");
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(lblVecchia, gbc);

        gbc.gridy = 1;
        panel.add(txtVecchiaPassword, gbc);

        // Nuova Password
        JLabel lblNuova = createFieldLabel("Nuova Password");
        gbc.gridy = 2;
        panel.add(lblNuova, gbc);

        gbc.gridy = 3;
        panel.add(txtNuovaPassword, gbc);

        // Conferma Password
        JLabel lblConferma = createFieldLabel("Conferma Password");
        gbc.gridy = 4;
        panel.add(lblConferma, gbc);

        gbc.gridy = 5;
        panel.add(txtConfermaPassword, gbc);

        // Checkbox
        gbc.gridy = 6;
        gbc.insets = new Insets(5, 0, 5, 0);
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

        panel.add(btnConferma);
        panel.add(btnAnnulla);

        return panel;
    }

    private void initListeners() {
        // Mostra/nascondi password
        chkMostraPassword.addActionListener(e -> {
            char echoChar = chkMostraPassword.isSelected() ? (char) 0 : '•';
            txtVecchiaPassword.setEchoChar(echoChar);
            txtNuovaPassword.setEchoChar(echoChar);
            txtConfermaPassword.setEchoChar(echoChar);
        });

        btnAnnulla.addActionListener(e -> dispose());

        btnConferma.addActionListener(e -> confermaModifica());

        // Enter per confermare
        txtConfermaPassword.addActionListener(e -> confermaModifica());
    }

    private void confermaModifica() {
        String vecchiaPassword = new String(txtVecchiaPassword.getPassword());
        String nuovaPassword = new String(txtNuovaPassword.getPassword());
        String confermaPassword = new String(txtConfermaPassword.getPassword());

        // Validazione input
        if (vecchiaPassword.isEmpty() || nuovaPassword.isEmpty() || confermaPassword.isEmpty()) {
            showError("Tutti i campi sono obbligatori.");
            return;
        }

        if (!nuovaPassword.equals(confermaPassword)) {
            showError("La nuova password e la conferma non coincidono.");
            txtNuovaPassword.setText("");
            txtConfermaPassword.setText("");
            return;
        }

        if (nuovaPassword.length() < 6) {
            showError("La nuova password deve contenere almeno 6 caratteri.");
            return;
        }

        if (vecchiaPassword.equals(nuovaPassword)) {
            showError("La nuova password deve essere diversa da quella vecchia.");
            return;
        }

        try {
            // Recupera l'account utente
            AccountUtente account = accountDAO.findUser(emailUtente);
            
            if (account == null) {
                showError("Errore nel recupero dell'account.");
                return;
            }

            // Verifica che la vecchia password sia corretta
            if (!account.getPassword().equals(vecchiaPassword)) {
                showError("La vecchia password non è corretta.");
                txtVecchiaPassword.setText("");
                return;
            }

            // Cambia la password
            boolean successo = accountController.ChangePassword(account, nuovaPassword);
            
            if (successo) {
                showSuccess("Password modificata con successo!");
                dispose();
            } else {
                showError("Errore durante la modifica della password.");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            showError("Errore nel database: " + ex.getMessage());
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
