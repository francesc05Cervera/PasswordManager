package com.francesco.passwordmanager2026.GUI.Theme;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class AlternatingRowRenderer extends DefaultTableCellRenderer {
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        
        if (!isSelected) {
            c.setBackground(row % 2 == 0 ? UITheme.DARK_TABLE_ROW : UITheme.DARK_TABLE_ALT_ROW);
            c.setForeground(UITheme.TEXT_COLOR);
        }
        
        setHorizontalAlignment(SwingConstants.CENTER);
        
        return c;
    }
}
