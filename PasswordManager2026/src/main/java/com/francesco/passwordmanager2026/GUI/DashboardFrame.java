package com.francesco.passwordmanager2026.GUI;

import com.francesco.passwordmanager2026.Controller.CredenzialiController;
import com.francesco.passwordmanager2026.GUI.Dialog.ModificaPasswordCredenzialeDialog;
import com.francesco.passwordmanager2026.GUI.Dialog.ModificaPasswordLoginDialog;
import com.francesco.passwordmanager2026.entity.CredenzialiAccesso;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
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

    // Mappa per tenere traccia delle password vere
    private Map<Integer, String> passwordMap = new HashMap<>();
    private Map<Integer, Boolean> passwordVisibilityMap = new HashMap<>();

    // Colori tema DARK MODE
    private static final Color PRIMARY_COLOR = new Color(52, 152, 219);      // Blu
    private static final Color SUCCESS_COLOR = new Color(46, 204, 113);      // Verde
    private static final Color DANGER_COLOR = new Color(231, 76, 60);        // Rosso
    private static final Color WARNING_COLOR = new Color(241, 196, 15);      // Giallo
    private static final Color DARK_BG = new Color(30, 33, 36);              // Sfondo scuro
    private static final Color DARK_PANEL = new Color(42, 45, 48);           // Pannello scuro
    private static final Color DARK_TABLE_HEADER = new Color(24, 26, 27);    // Header tabella
    private static final Color DARK_TABLE_ROW = new Color(36, 39, 41);       // Riga tabella
    private static final Color DARK_TABLE_ALT_ROW = new Color(42, 45, 48);   // Riga alternata
    private static final Color TEXT_COLOR = new Color(236, 240, 241);        // Testo chiaro
    private static final Color TEXT_SECONDARY = new Color(149, 165, 166);    // Testo secondario

    public DashboardFrame(String emailUtente) {
        super("PasswordManager 2026 - Dashboard");

        this.emailUtente = emailUtente;
        this.credController = new CredenzialiController();

        initComponents();
        initLayout();
        initListeners();
        caricaCredenziali();

        setSize(1050, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        
        getContentPane().setBackground(DARK_BG);
    }

    private void initComponents() {

        // Bottoni con stili personalizzati
        btnNuova = createStyledButton("Aggiungi", SUCCESS_COLOR);
        btnModifica = createStyledButton("Modifica Password", PRIMARY_COLOR);
        btnElimina = createStyledButton("Elimina", DANGER_COLOR);
        btnModificaPasswordLogin = createStyledButton("Cambia Password", WARNING_COLOR);
        btnLogout = createStyledButton("Logout", TEXT_SECONDARY);

        // Disattivati finché non selezioni una riga
        btnModifica.setEnabled(false);
        btnElimina.setEnabled(false);

        tableModel = new DefaultTableModel(
                new Object[]{"ID", "Piattaforma", "Username", "Password", "2FA", "Azioni"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5; // Solo colonna azioni è cliccabile
            }
            
            @Override
            public Class<?> getColumnClass(int column) {
                return column == 5 ? JButton.class : Object.class;
            }
        };

        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(35);
        table.setFont(new Font("Consolas", Font.PLAIN, 13));
        table.setBackground(DARK_TABLE_ROW);
        table.setForeground(TEXT_COLOR);
        table.setSelectionBackground(PRIMARY_COLOR.darker());
        table.setSelectionForeground(TEXT_COLOR);
        table.setShowGrid(true);
        table.setGridColor(DARK_BG);
        table.setIntercellSpacing(new Dimension(1, 1));

        // Nascondi la colonna ID
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setWidth(0);

        // Larghezze colonne
        table.getColumnModel().getColumn(1).setPreferredWidth(200); // Piattaforma
        table.getColumnModel().getColumn(2).setPreferredWidth(200); // Username
        table.getColumnModel().getColumn(3).setPreferredWidth(250); // Password
        table.getColumnModel().getColumn(4).setPreferredWidth(80);  // 2FA
        table.getColumnModel().getColumn(5).setPreferredWidth(150); // Azioni

        // Stile header tabella
        JTableHeader header = table.getTableHeader();
        header.setBackground(DARK_TABLE_HEADER);
        header.setForeground(TEXT_COLOR);
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setPreferredSize(new Dimension(header.getWidth(), 45));

        // Renderer per righe alternate
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? DARK_TABLE_ROW : DARK_TABLE_ALT_ROW);
                    c.setForeground(TEXT_COLOR);
                }
                
                setHorizontalAlignment(SwingConstants.CENTER);
                return c;
            }
        };

        // Renderer speciale per colonna password
        table.getColumnModel().getColumn(3).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? DARK_TABLE_ROW : DARK_TABLE_ALT_ROW);
                    c.setForeground(TEXT_COLOR);
                }
                
                setHorizontalAlignment(SwingConstants.LEFT);
                setFont(new Font("Consolas", Font.PLAIN, 13));
                return c;
            }
        });

        // Renderer per colonna azioni (bottoni)
        table.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
        table.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor(new JCheckBox()));

        // Applica renderer a colonne normali
        for (int i = 1; i < 5; i++) {
            if (i != 3) { // Salta la colonna password che ha renderer speciale
                table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }
        }
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(160, 38));
        
        // Effetto hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (button.isEnabled()) {
                    button.setBackground(bgColor.brighter());
                }
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });
        
        return button;
    }

    private void initLayout() {
        
        // Pannello principale con padding
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(DARK_BG);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Header con titolo e info utente
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(DARK_BG);
        
        JLabel titleLabel = new JLabel("Le tue Credenziali");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setForeground(TEXT_COLOR);
        
        JLabel userLabel = new JLabel(emailUtente);
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        userLabel.setForeground(TEXT_SECONDARY);
        
        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(userLabel, BorderLayout.EAST);

        // Pannello per bottoni credenziali
        JPanel credPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        credPanel.setBackground(DARK_BG);
        credPanel.add(btnNuova);
        credPanel.add(btnModifica);
        credPanel.add(btnElimina);

        // Pannello per bottoni utente
        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        userPanel.setBackground(DARK_BG);
        userPanel.add(btnModificaPasswordLogin);
        userPanel.add(btnLogout);

        // Pannello bottoni
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBackground(DARK_BG);
        buttonPanel.add(credPanel, BorderLayout.WEST);
        buttonPanel.add(userPanel, BorderLayout.EAST);

        // Tabella con bordo
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(DARK_PANEL, 2));
        scrollPane.getViewport().setBackground(DARK_TABLE_ROW);
        scrollPane.setBackground(DARK_BG);

        // Assembla tutto
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(DARK_BG);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);
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
        btnModificaPasswordLogin.addActionListener(e -> {
            new ModificaPasswordLoginDialog(this, emailUtente).setVisible(true);
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
                dispose();
                new LoginFrame().setVisible(true);
            }
        });
    }

    // Ricarica la tabella
    public void caricaCredenziali() {
        tableModel.setRowCount(0);
        passwordMap.clear();
        passwordVisibilityMap.clear();

        List<CredenzialiAccesso> lista = credController.recuperaCredenzialiUtente(emailUtente);

        if (lista != null) {
            for (CredenzialiAccesso c : lista) {
                int id = c.getId();
                passwordMap.put(id, c.getPasswordP());
                passwordVisibilityMap.put(id, false); // Inizialmente nascoste
                
                tableModel.addRow(new Object[]{
                        id,
                        c.getNomePiattaforma(),
                        c.getUsername(),
                        "••••••••",  // Password nascosta di default
                        c.isA2fPresent() ? "Si" : "No",
                        "Mostra/Copia"  // Testo del bottone
                });
            }
        }
    }

    // Renderer per i bottoni nella tabella
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
            setBackground(PRIMARY_COLOR);
            setForeground(Color.WHITE);
            setFont(new Font("Segoe UI", Font.BOLD, 11));
            setFocusPainted(false);
            setBorderPainted(false);
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    // Editor per i bottoni nella tabella
    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String label;
        private boolean isPushed;
        private int currentRow;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.setBackground(PRIMARY_COLOR);
            button.setForeground(Color.WHITE);
            button.setFont(new Font("Segoe UI", Font.BOLD, 11));
            button.setFocusPainted(false);
            button.setBorderPainted(false);
            
            button.addActionListener(e -> {
                fireEditingStopped();
            });
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            isPushed = true;
            currentRow = row;
            return button;
        }

        public Object getCellEditorValue() {
            if (isPushed) {
                int id = (int) tableModel.getValueAt(currentRow, 0);
                
                // Toggle visibilità password
                boolean isVisible = passwordVisibilityMap.get(id);
                if (isVisible) {
                    // Nascondi e copia
                    String password = passwordMap.get(id);
                    Toolkit.getDefaultToolkit().getSystemClipboard()
                            .setContents(new StringSelection(password), null);
                    tableModel.setValueAt("••••••••", currentRow, 3);
                    passwordVisibilityMap.put(id, false);
                    JOptionPane.showMessageDialog(DashboardFrame.this, 
                            "Password copiata negli appunti!", 
                            "Info", 
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    // Mostra
                    String password = passwordMap.get(id);
                    tableModel.setValueAt(password, currentRow, 3);
                    passwordVisibilityMap.put(id, true);
                }
            }
            isPushed = false;
            return label;
        }

        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }
    }
}
