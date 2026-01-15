package com.francesco.passwordmanager2026.GUI.Theme;

import javax.swing.*;
import java.awt.*;

public class StyledButton extends JButton {
    
    private Color backgroundColor;
    
    public StyledButton(String text, Color backgroundColor) {
        super(text);
        this.backgroundColor = backgroundColor;
        setupStyle();
        setupHoverEffect();
    }
    
    private void setupStyle() {
        setBackground(backgroundColor);
        setForeground(Color.WHITE);
        setFont(UITheme.BUTTON_FONT);
        setFocusPainted(false);
        setBorderPainted(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setPreferredSize(UITheme.BUTTON_SIZE);
    }
    
    private void setupHoverEffect() {
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (isEnabled()) {
                    setBackground(backgroundColor.brighter());
                }
            }
            
            public void mouseExited(java.awt.event.MouseEvent evt) {
                setBackground(backgroundColor);
            }
        });
    }
}
