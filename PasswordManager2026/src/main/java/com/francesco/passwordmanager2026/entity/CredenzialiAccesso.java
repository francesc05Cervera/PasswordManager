package com.francesco.passwordmanager2026.entity;

public class CredenzialiAccesso {

    private int id; // PK generato dal DB
    private String nomePiattaforma;
    private String username;
    private String passwordP;
    private String utenteCheCreaRecord; // FK in DB
    private boolean a2fPresent;

    // Costruttore di default
    public CredenzialiAccesso() {
    }

    // Costruttore parametrico (senza ID)
    public CredenzialiAccesso(String nomePiattaforma, String username, String passwordP,
                              String utenteCheCreaRecord, boolean a2fPresent) {
        this.nomePiattaforma = nomePiattaforma;
        this.username = username;
        this.passwordP = passwordP;
        this.utenteCheCreaRecord = utenteCheCreaRecord;
        this.a2fPresent = a2fPresent;
    }

    // Getter e Setter
    public int getId() {
        return id;
    }

    // Lo userai dopo l'inserimento nel DB
    public void setId(int id) {
        this.id = id;
    }

    public String getNomePiattaforma() {
        return nomePiattaforma;
    }

    public void setNomePiattaforma(String nomePiattaforma) {
        this.nomePiattaforma = nomePiattaforma;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordP() {
        return passwordP;
    }

    public void setPasswordP(String passwordP) {
        this.passwordP = passwordP;
    }

    public String getUtenteCheCreaRecord() {
        return utenteCheCreaRecord;
    }

    public void setUtenteCheCreaRecord(String utenteCheCreaRecord) {
        this.utenteCheCreaRecord = utenteCheCreaRecord;
    }

    public boolean isA2fPresent() {
        return a2fPresent;
    }

    public void setA2fPresent(boolean a2fPresent) {
        this.a2fPresent = a2fPresent;
    }

    @Override
    public String toString() {
        return "CredenzialiAccesso{" +
                "id=" + id +
                ", nomePiattaforma='" + nomePiattaforma + '\'' +
                ", username='" + username + '\'' +
                ", utenteCheCreaRecord='" + utenteCheCreaRecord + '\'' +
                ", a2fPresent=" + a2fPresent +
                '}';
    }
}