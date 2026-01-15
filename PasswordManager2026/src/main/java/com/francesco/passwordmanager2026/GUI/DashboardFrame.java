package com.francesco.passwordmanager2026.GUI;

import com.francesco.passwordmanager2026.Controller.CredenzialiController;
import com.francesco.passwordmanager2026.GUI.Dialog.ModificaPasswordCredenzialeDialog;
import com.francesco.passwordmanager2026.GUI.Dialog.ModificaPasswordLoginDialog;
import com.francesco.passwordmanager2026.entity.CredenzialiAccesso;
import com.francesco.passwordmanager2026.GUI.Theme.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashboardFrame extends JFrame {

    private String emailUtente;
    private CredenzialiController credController;

    private JTable table;
    private DefaultTableModel tableModel;

    private JButton btnNuova;
    private JButton btnModifica;
    private JButton btnElimina;
    private JButton btnModificaPasswordLogin;
    private JButton btnLogout;

    private Map<Integer, String> passwordMap = new HashMap<>();
    private Map<Integer, Boolean> passwordVisibilityMap = new HashMap<>();

    public DashboardFrame(String emailUtente) {
        super("PasswordManager 2026 - Dashboard");

        this.emailUtente = emailUtente;
        this.credController = new CredenzialiController();

        initComponents();
        initLayout();
        initListeners();
        caricaCredenziali();

        configureFrame();
    }
    
    private void configureFrame() {
        setSize(1050, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        getContentPane().setBackground(UITheme.DARK_BG);
    }

    private void initComponents() {
        createButtons();
        createTable();
    }
    
    private void createButtons() {
        btnNuova = new StyledButton("Aggiungi", UITheme.SUCCESS_COLOR);
        btnModifica = new StyledButton("Modifica Password", UITheme.PRIMARY_COLOR);
        btnElimina = new StyledButton("Elimina", UITheme.DANGER_COLOR);
        btnModificaPasswordLogin = new StyledButton("Cambia Password", UITheme.WARNING_COLOR);
        btnLogout = new StyledButton("Logout", UITheme.TEXT_SECONDARY);

        btnModifica.setEnabled(false);
        btnElimina.setEnabled(false);
    }
    
    private void createTable() {
        tableModel = new DefaultTableModel(
                new Object[]{"ID", "Piattaforma", "Username", "Password", "2FA", "Azioni"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5;
            }
            
            @Override
            public Class<?> getColumnClass(int column) {
                return column == 5 ? JButton.class : Object.class;
            }
        };

        table = new JTable(tableModel);
        configureTableAppearance();
        configureTableColumns();
        setTableRenderers();
    }
    
    private void configureTableAppearance() {
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(UITheme.TABLE_ROW_HEIGHT);
        table.setFont(UITheme.TABLE_FONT);
        table.setBackground(UITheme.DARK_TABLE_ROW);
        table.setForeground(UITheme.TEXT_COLOR);
        table.setSelectionBackground(UITheme.PRIMARY_COLOR.darker());
        table.setSelectionForeground(UITheme.TEXT_COLOR);
        table.setShowGrid(true);
        table.setGridColor(UITheme.DARK_BG);
        table.setIntercellSpacing(new Dimension(1, 1));
    }
    
    private void configureTableColumns() {
        // Nascondi colonna ID
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setWidth(0);

        // Larghezze colonne
        table.getColumnModel().getColumn(1).setPreferredWidth(200); // Piattaforma
        table.getColumnModel().getColumn(2).setPreferredWidth(200); // Username
        table.getColumnModel().getColumn(3).setPreferredWidth(250); // Password
        table.getColumnModel().getColumn(4).setPreferredWidth(80);  // 2FA
        table.getColumnModel().getColumn(5).setPreferredWidth(150); // Azioni

        // Stile header
        JTableHeader header = table.getTableHeader();
        header.setBackground(UITheme.DARK_TABLE_HEADER);
        header.setForeground(UITheme.TEXT_COLOR);
        header.setFont(UITheme.HEADER_FONT);
        header.setPreferredSize(new Dimension(header.getWidth(), UITheme.TABLE_HEADER_HEIGHT));
    }
    
    private void setTableRenderers() {
        AlternatingRowRenderer centerRenderer = new AlternatingRowRenderer();
        PasswordTableCellRenderer passwordRenderer = new PasswordTableCellRenderer();
        
        // Renderer per colonne normali
        for (int i = 1; i < 5; i++) {
            if (i == 3) {
                table.getColumnModel().getColumn(i).setCellRenderer(passwordRenderer);
            } else {
                table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }
        }
        
        // Renderer e editor per colonna azioni
        table.getColumnModel().getColumn(5).setCellRenderer(new PasswordActionButton());
        table.getColumnModel().getColumn(5).setCellEditor(
            new PasswordActionEditor(new JCheckBox(), passwordMap, passwordVisibilityMap, tableModel, this)
        );
    }

    private void initLayout() {
        JPanel mainPanel = createMainPanel();
        JPanel headerPanel = createHeaderPanel();
        JPanel buttonPanel = createButtonPanel();
        JScrollPane scrollPane = createTableScrollPane();
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(UITheme.DARK_BG);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);
    }
    
    private JPanel createMainPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(UITheme.DARK_BG);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        return panel;
    }
    
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(UITheme.DARK_BG);
        
        JLabel titleLabel = new JLabel("Le tue Credenziali");
        titleLabel.setFont(UITheme.TITLE_FONT);
        titleLabel.setForeground(UITheme.TEXT_COLOR);
        
        JLabel userLabel = new JLabel(emailUtente);
        userLabel.setFont(UITheme.NORMAL_FONT);
        userLabel.setForeground(UITheme.TEXT_SECONDARY);
        
        panel.add(titleLabel, BorderLayout.WEST);
        panel.add(userLabel, BorderLayout.EAST);
        
        return panel;
    }
    
    private JPanel createButtonPanel() {
        JPanel credPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        credPanel.setBackground(UITheme.DARK_BG);
        credPanel.add(btnNuova);
        credPanel.add(btnModifica);
        credPanel.add(btnElimina);

        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        userPanel.setBackground(UITheme.DARK_BG);
        userPanel.add(btnModificaPasswordLogin);
        userPanel.add(btnLogout);

        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBackground(UITheme.DARK_BG);
        buttonPanel.add(credPanel, BorderLayout.WEST);
        buttonPanel.add(userPanel, BorderLayout.EAST);
        
        return buttonPanel;
    }
    
    private JScrollPane createTableScrollPane() {
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(UITheme.DARK_PANEL, 2));
        scrollPane.getViewport().setBackground(UITheme.DARK_TABLE_ROW);
        scrollPane.setBackground(UITheme.DARK_BG);
        return scrollPane;
    }

    private void initListeners() {
        setupTableSelectionListener();
        setupButtonListeners();
    }
    
    private void setupTableSelectionListener() {
        table.getSelectionModel().addListSelectionListener(e -> {
            boolean selected = table.getSelectedRow() != -1;
            btnModifica.setEnabled(selected);
            btnElimina.setEnabled(selected);
        });
    }
    
    private void setupButtonListeners() {
        btnNuova.addActionListener(e -> handleNuovaCredenziale());
        btnElimina.addActionListener(e -> handleEliminaCredenziale());
        btnModifica.addActionListener(e -> handleModificaPassword());
        btnModificaPasswordLogin.addActionListener(e -> handleModificaPasswordLogin());
        btnLogout.addActionListener(e -> handleLogout());
    }
    
    private void handleNuovaCredenziale() {
        new NuovaCredenzialeFrame(emailUtente, this).setVisible(true);
    }
    
    private void handleEliminaCredenziale() {
        int row = table.getSelectedRow();
        if (row == -1) return;

        int id = (int) tableModel.getValueAt(row, 0);

        int conferma = JOptionPane.showConfirmDialog(this,
                "Sei sicuro di voler eliminare questa credenziale?",
                "Conferma eliminazione",
                JOptionPane.YES_NO_OPTION);

        if (conferma == JOptionPane.YES_OPTION) {
            if (credController.eliminaCredenziale(id)) {
                JOptionPane.showMessageDialog(this, "Credenziale eliminata.");
                caricaCredenziali();
            } else {
                JOptionPane.showMessageDialog(this, "Errore durante l'eliminazione.");
            }
        }
    }
    
    private void handleModificaPassword() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Seleziona una credenziale.");
            return;
        }

        int id = (int) tableModel.getValueAt(row, 0);
        new ModificaPasswordCredenzialeDialog(this, id, this).setVisible(true);
    }
    
    private void handleModificaPasswordLogin() {
        new ModificaPasswordLoginDialog(this, emailUtente).setVisible(true);
    }
    
    private void handleLogout() {
        int conferma = JOptionPane.showConfirmDialog(this,
                "Sei sicuro di voler uscire?",
                "Conferma Logout",
                JOptionPane.YES_NO_OPTION);

        if (conferma == JOptionPane.YES_OPTION) {
            dispose();
            new LoginFrame().setVisible(true);
        }
    }

    public void caricaCredenziali() {
        clearTableData();
        List<CredenzialiAccesso> lista = credController.recuperaCredenzialiUtente(emailUtente);

        if (lista != null) {
            lista.forEach(this::addCredentialToTable);
        }
    }
    
    private void clearTableData() {
        tableModel.setRowCount(0);
        passwordMap.clear();
        passwordVisibilityMap.clear();
    }
    
    private void addCredentialToTable(CredenzialiAccesso c) {
        int id = c.getId();
        passwordMap.put(id, c.getPasswordP());
        passwordVisibilityMap.put(id, false);
        
        tableModel.addRow(new Object[]{
                id,
                c.getNomePiattaforma(),
                c.getUsername(),
                "••••••••",
                c.isA2fPresent() ? "Si" : "No",
                "Mostra/Copia"
        });
    }
}
