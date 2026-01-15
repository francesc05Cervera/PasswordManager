package com.francesco.passwordmanager2026.GUI.Theme;

import java.awt.*;

public class UITheme {
    
    // Colori principali
    public static final Color PRIMARY_COLOR = new Color(52, 152, 219);      // Blu
    public static final Color SUCCESS_COLOR = new Color(46, 204, 113);      // Verde
    public static final Color DANGER_COLOR = new Color(231, 76, 60);        // Rosso
    public static final Color WARNING_COLOR = new Color(241, 196, 15);      // Giallo
    public static final Color INFO_COLOR = new Color(155, 89, 182);         // Viola
    
    // Dark Mode Colors
    public static final Color DARK_BG = new Color(30, 33, 36);              // Sfondo principale
    public static final Color DARK_PANEL = new Color(42, 45, 48);           // Pannelli
    public static final Color DARK_TABLE_HEADER = new Color(24, 26, 27);    // Header tabella
    public static final Color DARK_TABLE_ROW = new Color(36, 39, 41);       // Riga tabella
    public static final Color DARK_TABLE_ALT_ROW = new Color(42, 45, 48);   // Riga alternata
    
    // Testi
    public static final Color TEXT_COLOR = new Color(236, 240, 241);        // Testo principale
    public static final Color TEXT_SECONDARY = new Color(149, 165, 166);    // Testo secondario
    
    // Font
    public static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 26);
    public static final Font HEADER_FONT = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 12);
    public static final Font TABLE_FONT = new Font("Consolas", Font.PLAIN, 13);
    public static final Font NORMAL_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    
    // Dimensioni
    public static final Dimension BUTTON_SIZE = new Dimension(160, 38);
    public static final int TABLE_ROW_HEIGHT = 35;
    public static final int TABLE_HEADER_HEIGHT = 45;
    
    private UITheme() {
        // Prevent instantiation
    }
}
