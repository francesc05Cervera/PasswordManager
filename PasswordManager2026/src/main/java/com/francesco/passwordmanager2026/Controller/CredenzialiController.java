package com.francesco.passwordmanager2026.Controller;

import com.francesco.passwordmanager2026.DAO.CredenzialiDAO;
import com.francesco.passwordmanager2026.Service.DataCheck;
import com.francesco.passwordmanager2026.entity.CredenzialiAccesso;
import com.francesco.passwordmanager2026.Service.CryptoUtil;  // AGGIUNTO
import javax.crypto.SecretKey;  // AGGIUNTO

import java.sql.SQLException;
import java.util.List;

public class CredenzialiController {

    private CredenzialiDAO credDAO;
    
    // Variabili di sessione per mantenere i dati dell'utente loggato
    private String currentUserEmail;      // AGGIUNTO
    private String currentUserPassword;   // AGGIUNTO (password in chiaro mantenuta in memoria)

    public CredenzialiController() {
        credDAO = new CredenzialiDAO();
    }
    
    // METODO PER IMPOSTARE L'UTENTE LOGGATO (chiamalo dopo il login) - AGGIUNTO
    public void setCurrentUser(String email, String password) {
        this.currentUserEmail = email;
        this.currentUserPassword = password;
    }
    
    // METODO PER PULIRE LA SESSIONE (chiamalo al logout) - AGGIUNTO
    public void clearSession() {
        this.currentUserEmail = null;
        this.currentUserPassword = null;
    }

    // INSERIMENTO NUOVA CREDENZIALE - MODIFICATO
    public boolean inserisciCredenziale(String nomePiattaforma, String username, String passwordP,
                                        String utenteCheCreaRecord, boolean a2fPresent) {

        if (!DataCheck.VerifyInput_4StringAND1Boolean(nomePiattaforma, username, passwordP, utenteCheCreaRecord, a2fPresent))
            return false;
        
        if (currentUserEmail == null || currentUserPassword == null) {
            System.err.println("Errore: utente non loggato");
            return false;
        }

        try {
            // CRITTOGRAFIA: Deriva chiave e cifra la password
            SecretKey key = CryptoUtil.deriveKey(currentUserPassword, currentUserEmail);
            String encryptedPassword = CryptoUtil.encryptPassword(passwordP, key);
            
            CredenzialiAccesso nuova = new CredenzialiAccesso(
                    nomePiattaforma,
                    username,
                    encryptedPassword,  // Password cifrata
                    utenteCheCreaRecord,
                    a2fPresent
            );

            return credDAO.insert(nuova);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // MODIFICA PASSWORD DI UNA CREDENZIALE - MODIFICATO
    public boolean modificaPassword(int idCredenziale, String nuovaPassword) {

        if (!DataCheck.VerifyInput_1String(nuovaPassword))
            return false;
        
        if (currentUserEmail == null || currentUserPassword == null) {
            System.err.println("Errore: utente non loggato");
            return false;
        }

        try {
            // CRITTOGRAFIA: Cifra la nuova password
            SecretKey key = CryptoUtil.deriveKey(currentUserPassword, currentUserEmail);
            String encryptedPassword = CryptoUtil.encryptPassword(nuovaPassword, key);
            
            return credDAO.changePassword(idCredenziale, encryptedPassword);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ELIMINAZIONE CREDENZIALE - INVARIATO
    public boolean eliminaCredenziale(int idCredenziale) {

        if (!DataCheck.VerifyInput_1Int(idCredenziale))
            return false;

        try {
            return credDAO.delete(idCredenziale);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // RECUPERO DI TUTTE LE CREDENZIALI DI UN UTENTE - MODIFICATO
    public List<CredenzialiAccesso> recuperaCredenzialiUtente(String emailCreatore) {

        if (!DataCheck.VerifyInput_1String(emailCreatore))
            return null;

        try {
            List<CredenzialiAccesso> credenziali = credDAO.findByCreator(emailCreatore);
            
            // NOTA: le password sono ancora cifrate negli oggetti restituiti
            // Dovrai decifrarle quando le mostri nella GUI
            return credenziali;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    // METODO PER DECIFRARE UNA PASSWORD (usa nella GUI) - AGGIUNTO
    public String decryptPassword(String encryptedPassword) {
        if (currentUserEmail == null || currentUserPassword == null) {
            System.err.println("Errore: utente non loggato");
            return null;
        }
        
        try {
            SecretKey key = CryptoUtil.deriveKey(currentUserPassword, currentUserEmail);
            return CryptoUtil.decryptPassword(encryptedPassword, key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
