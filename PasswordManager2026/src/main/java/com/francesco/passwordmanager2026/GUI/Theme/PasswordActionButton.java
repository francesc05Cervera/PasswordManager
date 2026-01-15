package com.francesco.passwordmanager2026.GUI.Theme;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class PasswordActionButton extends JButton implements TableCellRenderer {
    
    public PasswordActionButton() {
        setupStyle();
    }
    
    private void setupStyle() {
        setOpaque(true);
        setBackground(UITheme.PRIMARY_COLOR);
        setForeground(Color.WHITE);
        setFont(new Font("Segoe UI", Font.BOLD, 11));
        setFocusPainted(false);
        setBorderPainted(false);
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        setText((value == null) ? "" : value.toString());
        return this;
    }
}
