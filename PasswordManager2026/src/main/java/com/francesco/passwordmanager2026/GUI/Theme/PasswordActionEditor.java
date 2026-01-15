package com.francesco.passwordmanager2026.GUI.Theme;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.util.Map;

public class PasswordActionEditor extends DefaultCellEditor {
    
    private JButton button;
    private String label;
    private boolean isPushed;
    private int currentRow;
    
    private Map<Integer, String> passwordMap;
    private Map<Integer, Boolean> visibilityMap;
    private DefaultTableModel tableModel;
    private JFrame parentFrame;
    
    public PasswordActionEditor(JCheckBox checkBox, Map<Integer, String> passwordMap,
                                Map<Integer, Boolean> visibilityMap,
                                DefaultTableModel tableModel, JFrame parentFrame) {
        super(checkBox);
        
        this.passwordMap = passwordMap;
        this.visibilityMap = visibilityMap;
        this.tableModel = tableModel;
        this.parentFrame = parentFrame;
        
        button = new JButton();
        setupButtonStyle();
        
        button.addActionListener(e -> fireEditingStopped());
    }
    
    private void setupButtonStyle() {
        button.setOpaque(true);
        button.setBackground(UITheme.PRIMARY_COLOR);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 11));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
    }
    
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        label = (value == null) ? "" : value.toString();
        button.setText(label);
        isPushed = true;
        currentRow = row;
        return button;
    }
    
    @Override
    public Object getCellEditorValue() {
        if (isPushed) {
            handlePasswordAction();
        }
        isPushed = false;
        return label;
    }
    
    private void handlePasswordAction() {
        int id = (int) tableModel.getValueAt(currentRow, 0);
        boolean isVisible = visibilityMap.get(id);
        
        if (isVisible) {
            hideAndCopyPassword(id);
        } else {
            showPassword(id);
        }
    }
    
    private void showPassword(int id) {
        String password = passwordMap.get(id);
        tableModel.setValueAt(password, currentRow, 3);
        visibilityMap.put(id, true);
    }
    
    private void hideAndCopyPassword(int id) {
        String password = passwordMap.get(id);
        
        // Copia negli appunti
        Toolkit.getDefaultToolkit().getSystemClipboard()
                .setContents(new StringSelection(password), null);
        
        // Nascondi password
        tableModel.setValueAt("••••••••", currentRow, 3);
        visibilityMap.put(id, false);
        
        // Mostra messaggio
        JOptionPane.showMessageDialog(parentFrame,
                "Password copiata negli appunti!",
                "Info",
                JOptionPane.INFORMATION_MESSAGE);
    }
    
    @Override
    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }
}
