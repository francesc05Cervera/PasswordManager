package com.francesco.passwordmanager2026.GUI.Dialog;

import com.francesco.passwordmanager2026.Controller.AccountUtenteController;
import com.francesco.passwordmanager2026.DAO.AccountUtenteDAO;
import com.francesco.passwordmanager2026.entity.AccountUtente;

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

        setSize(400, 250);
        setLocationRelativeTo(parent);
        setResizable(false);
    }

    private void initComponents() {
        txtVecchiaPassword = new JPasswordField(20);
        txtNuovaPassword = new JPasswordField(20);
        txtConfermaPassword = new JPasswordField(20);
        
        btnConferma = new JButton("Conferma");
        btnAnnulla = new JButton("Annulla");
    }

    private void initLayout() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Vecchia Password
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(new JLabel("Vecchia Password:"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        mainPanel.add(txtVecchiaPassword, gbc);

        // Nuova Password
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        mainPanel.add(new JLabel("Nuova Password:"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        mainPanel.add(txtNuovaPassword, gbc);

        // Conferma Password
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        mainPanel.add(new JLabel("Conferma Password:"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        mainPanel.add(txtConfermaPassword, gbc);

        // Pulsanti
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(btnConferma);
        buttonPanel.add(btnAnnulla);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(buttonPanel, gbc);

        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
    }

    private void initListeners() {
        btnAnnulla.addActionListener(e -> dispose());

        btnConferma.addActionListener(e -> confermaModifica());
    }

    private void confermaModifica() {
        String vecchiaPassword = new String(txtVecchiaPassword.getPassword());
        String nuovaPassword = new String(txtNuovaPassword.getPassword());
        String confermaPassword = new String(txtConfermaPassword.getPassword());

        // Validazione input
        if (vecchiaPassword.isEmpty() || nuovaPassword.isEmpty() || confermaPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Tutti i campi sono obbligatori.", 
                "Errore", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!nuovaPassword.equals(confermaPassword)) {
            JOptionPane.showMessageDialog(this, 
                "La nuova password e la conferma non coincidono.", 
                "Errore", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (nuovaPassword.length() < 6) {
            JOptionPane.showMessageDialog(this, 
                "La nuova password deve contenere almeno 6 caratteri.", 
                "Errore", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Recupera l'account utente
            AccountUtente account = accountDAO.findUser(emailUtente);
            
            if (account == null) {
                JOptionPane.showMessageDialog(this, 
                    "Errore nel recupero dell'account.", 
                    "Errore", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Verifica che la vecchia password sia corretta
            if (!account.getPassword().equals(vecchiaPassword)) {
                JOptionPane.showMessageDialog(this, 
                    "La vecchia password non è corretta.", 
                    "Errore", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Cambia la password
            boolean successo = accountController.ChangePassword(account, nuovaPassword);
            
            if (successo) {
                JOptionPane.showMessageDialog(this, 
                    "Password modificata con successo!", 
                    "Successo", 
                    JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Errore durante la modifica della password.", 
                    "Errore", 
                    JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Errore nel database: " + ex.getMessage(), 
                "Errore", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}
