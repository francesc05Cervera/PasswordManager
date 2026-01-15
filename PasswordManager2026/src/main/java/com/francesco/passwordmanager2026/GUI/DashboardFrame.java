package com.francesco.passwordmanager2026.GUI;

import com.francesco.passwordmanager2026.Controller.CredenzialiController;
import com.francesco.passwordmanager2026.entity.CredenzialiAccesso;

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

        // Disattivati finché non selezioni una riga
        btnModifica.setEnabled(false);
        btnElimina.setEnabled(false);

        tableModel = new DefaultTableModel(
                new Object[]{"Piattaforma", "Username", "Password", "2FA"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // tabella non modificabile
            }
        };

        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void initLayout() {

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        topPanel.add(btnNuova);
        topPanel.add(btnModifica);
        topPanel.add(btnElimina);
        topPanel.add(btnModificaPassword);

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

        // MODIFICA CREDENZIALE
        btnModifica.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) return;

            int id = (int) tableModel.getValueAt(row, 0);
            String piattaforma = (String) tableModel.getValueAt(row, 1);
            String username = (String) tableModel.getValueAt(row, 2);
            String password = (String) tableModel.getValueAt(row, 3);
            boolean a2f = (boolean) tableModel.getValueAt(row, 4);

            CredenzialiAccesso cred = new CredenzialiAccesso(piattaforma, username, password, emailUtente, a2f);
            cred.setId(id);
            new ModificaCredenzialeFrame(cred, this).setVisible(true);
        });

        // MODIFICA PASSWORD
        btnModificaPassword.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Seleziona una credenziale per modificare la password.");
                return;
            }

            int id = (int) tableModel.getValueAt(row, 0);
            new ModificaPasswordCredenzialeFrame(id, this).setVisible(true);
        });
    }

    // Ricarica la tabella
    public void caricaCredenziali() {
        tableModel.setRowCount(0);

        List<CredenzialiAccesso> lista = credController.recuperaCredenzialiUtente(emailUtente);

        if (lista != null) {
            for (CredenzialiAccesso c : lista) {
                tableModel.addRow(new Object[]{
                        
                        c.getNomePiattaforma(),
                        c.getUsername(),
                        c.getPasswordP(),
                        c.isA2fPresent()
                });
            }
        }
    }
}