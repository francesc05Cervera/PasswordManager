package com.francesco.passwordmanager2026.GUI;

import com.francesco.passwordmanager2026.Controller.CredenzialiController;
import com.francesco.passwordmanager2026.entity.CredenzialiAccesso;
import com.francesco.passwordmanager2026.GUI.Dialog.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class DashboardFrame extends JFrame {

    private String emailUtente;
    private CredenzialiController credController;

    private JTable table;
    private DefaultTableModel tableModel;

    private JButton btnNuova;
    private JButton btnModifica;
    private JButton btnElimina;
    private JButton btnModificaPassword;
    private JButton btnLogout;  // NUOVO


    public DashboardFrame(String emailUtente) {
        super("PasswordManager2026 - Dashboard");

        this.emailUtente = emailUtente;
        this.credController = new CredenzialiController();

        initComponents();
        initLayout();
        initListeners();
        caricaCredenziali();

        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setResizable(false);
    }

    private void initComponents() {

        btnNuova = new JButton("Aggiungi Credenziali");
        btnModifica = new JButton("Modifica Credenziali");
        btnElimina = new JButton("Elimina Credenziali");
        btnModificaPassword = new JButton("Modifica Password Login");
        btnLogout = new JButton("Logout");  // NUOVO

        // Disattivati finché non selezioni una riga
        btnModifica.setEnabled(false);
        btnElimina.setEnabled(false);

        tableModel = new DefaultTableModel(
                new Object[]{"ID", "Piattaforma", "Username", "Password", "2FA"}, 0  // Aggiungi "ID"
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // tabella non modificabile
            }
        };

        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Nascondi la colonna ID (rimane nei dati ma non è visibile)
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setWidth(0);
    }

    private void initLayout() {

    	  JPanel topPanel = new JPanel(new GridLayout(2, 3, 10, 10));  // 2 righe, 3 colonne
    	    topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));        
    	    topPanel.add(btnNuova);
        topPanel.add(btnModifica);
        topPanel.add(btnElimina);
        topPanel.add(btnModificaPassword);
        topPanel.add(btnLogout);  // NUOVO

        JScrollPane scrollPane = new JScrollPane(table);

        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void initListeners() {

        // Attiva/disattiva pulsanti in base alla selezione
        table.getSelectionModel().addListSelectionListener(e -> {
            boolean selected = table.getSelectedRow() != -1;
            btnModifica.setEnabled(selected);
            btnElimina.setEnabled(selected);
        });

        // CREA NUOVA CREDENZIALE
        btnNuova.addActionListener(e -> {
            new NuovaCredenzialeFrame(emailUtente, this).setVisible(true);
        });

        // ELIMINA CREDENZIALE
        btnElimina.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) return;

            int id = (int) tableModel.getValueAt(row, 0);

            int conferma = JOptionPane.showConfirmDialog(
                    this,
                    "Sei sicuro di voler eliminare questa credenziale?",
                    "Conferma eliminazione",
                    JOptionPane.YES_NO_OPTION
            );

            if (conferma == JOptionPane.YES_OPTION) {
                boolean ok = credController.eliminaCredenziale(id);
                if (ok) {
                    JOptionPane.showMessageDialog(this, "Credenziale eliminata.");
                    caricaCredenziali();
                } else {
                    JOptionPane.showMessageDialog(this, "Errore durante l'eliminazione.");
                }
            }
        });

     // LOGOUT
        btnLogout.addActionListener(e -> {
            int conferma = JOptionPane.showConfirmDialog(
                    this,
                    "Sei sicuro di voler uscire?",
                    "Conferma Logout",
                    JOptionPane.YES_NO_OPTION
            );

            if (conferma == JOptionPane.YES_OPTION) {
                dispose();  // Chiude la dashboard
                new LoginFrame().setVisible(true);  // Apre il login
            }
        });
        
        // MODIFICA CREDENZIALE
     // MODIFICA CREDENZIALE (solo password)
        btnModifica.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Seleziona una credenziale.");
                return;
            }

            int id = (int) tableModel.getValueAt(row, 0);
            new ModificaPasswordCredenzialeDialog(this, id, this).setVisible(true);
        });


      
     // MODIFICA PASSWORD LOGIN
        btnModificaPassword.addActionListener(e -> {
            new ModificaPasswordLoginDialog(this, emailUtente).setVisible(true);
        });

    }

    // Ricarica la tabella
 // Ricarica la tabella
    public void caricaCredenziali() {
        tableModel.setRowCount(0);

        List<CredenzialiAccesso> lista = credController.recuperaCredenzialiUtente(emailUtente);

        if (lista != null) {
            for (CredenzialiAccesso c : lista) {
                tableModel.addRow(new Object[]{
                        c.getId(),              // Aggiungi l'ID come prima colonna
                        c.getNomePiattaforma(),
                        c.getUsername(),
                        c.getPasswordP(),
                        c.isA2fPresent()
                });
            }
        }
    }

}