package com.francesco.passwordmanager2026.entity;

import java.util.Objects;

public class AccountUtente {

    private String email;
    private String telefono;
    private String nome;
    private String cognome;
    private String password;

    // Costruttore di default
    public AccountUtente() {
    }

    // Costruttore parametrico
    public AccountUtente(String email, String telefono, String nome, String cognome, String password) {
        this.email = email;
        this.telefono = telefono;
        this.nome = nome;
        this.cognome = cognome;
        this.password = password;
    }

    // Getter e Setter
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // toString
    @Override
    public String toString() {
        return "AccountUtente{" +
                "email='" + email + '\'' +
                ", telefono='" + telefono + '\'' +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                '}';
        // Nota: per sicurezza NON includo la password nel toString
    }

  
}